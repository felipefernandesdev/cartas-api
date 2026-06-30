package com.hearthstone.cartas_api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hearthstone.cartas_api.model.Carta;
import com.hearthstone.cartas_api.model.ClasseCarta;
import com.hearthstone.cartas_api.model.TipoCarta;
import com.hearthstone.cartas_api.service.CartaService;

@SpringBootTest
class CartaControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private CartaService cartaService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Carta cartaMago;
    private Carta cartaCacador;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        cartaMago = new Carta("Bola de Fogo", "Causa 6 de dano", 6, 0, 4, TipoCarta.MAGIA, ClasseCarta.MAGO);
        cartaMago.setId(1L);

        cartaCacador = new Carta("Tiro Preciso", "Causa 5 de dano", 5, 2, 3, TipoCarta.CRIATURA, ClasseCarta.CACADOR);
        cartaCacador.setId(2L);
    }

    @Test
    void deveListarTodasAsCartas() throws Exception {
        List<Carta> cartas = Arrays.asList(cartaMago, cartaCacador);
        when(cartaService.buscar(null, null, null)).thenReturn(cartas);

        mockMvc.perform(get("/carta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Bola de Fogo")))
                .andExpect(jsonPath("$[1].nome", is("Tiro Preciso")));
    }

    @Test
    void deveBuscarCartaPorId() throws Exception {
        when(cartaService.buscarPorId(1L)).thenReturn(Optional.of(cartaMago));

        mockMvc.perform(get("/carta/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Bola de Fogo")))
                .andExpect(jsonPath("$.ataque", is(6)));
    }

    @Test
    void deveRetornar404QuandoCartaNaoEncontrada() throws Exception {
        when(cartaService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/carta/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveCriarCartaComSucesso() throws Exception {
        when(cartaService.salvar(any(Carta.class))).thenReturn(cartaMago);

        mockMvc.perform(post("/carta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartaMago)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("Bola de Fogo")));
    }

    @Test
    void deveRetornar400QuandoNomeEmBranco() throws Exception {
        Carta cartaInvalida = new Carta("", "Descricao", 5, 5, 5, TipoCarta.CRIATURA, ClasseCarta.MAGO);

        mockMvc.perform(post("/carta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartaInvalida)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar400QuandoAtaqueInvalido() throws Exception {
        Carta cartaInvalida = new Carta("Carta", "Descricao", 15, 5, 5, TipoCarta.CRIATURA, ClasseCarta.MAGO);

        mockMvc.perform(post("/carta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartaInvalida)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveDeletarCartaComSucesso() throws Exception {
        doNothing().when(cartaService).deletar(1L);

        mockMvc.perform(delete("/carta/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveBuscarCartasPorNome() throws Exception {
        when(cartaService.buscar("Bola de Fogo", null, null)).thenReturn(List.of(cartaMago));

        mockMvc.perform(get("/carta").param("nome", "Bola de Fogo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Bola de Fogo")));
    }

    @Test
    void deveBuscarCartasPorClasse() throws Exception {
        when(cartaService.buscar(null, ClasseCarta.MAGO, null)).thenReturn(List.of(cartaMago));

        mockMvc.perform(get("/carta").param("classe", "MAGO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].classe", is("MAGO")));
    }

    @Test
    void deveBuscarCartasPorTipo() throws Exception {
        when(cartaService.buscar(null, null, TipoCarta.MAGIA)).thenReturn(List.of(cartaMago));

        mockMvc.perform(get("/carta").param("tipo", "MAGIA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].tipo", is("MAGIA")));
    }
}
