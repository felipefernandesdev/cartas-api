package com.hearthstone.cartas_api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.hearthstone.cartas_api.model.Baralho;
import com.hearthstone.cartas_api.model.Carta;
import com.hearthstone.cartas_api.model.ClasseCarta;
import com.hearthstone.cartas_api.model.TipoCarta;
import com.hearthstone.cartas_api.service.BaralhoService;

@SpringBootTest
class BaralhoControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private BaralhoService baralhoService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Baralho baralhoMago;
    private Carta cartaMago;
    private Carta cartaQualquer;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        baralhoMago = new Baralho("Baralho Mago Ofensivo", ClasseCarta.MAGO);
        baralhoMago.setId(1L);
        baralhoMago.setCartas(new ArrayList<>());

        cartaMago = new Carta("Bola de Fogo", "Causa 6 de dano", 6, 0, 4, TipoCarta.MAGIA, ClasseCarta.MAGO);
        cartaMago.setId(1L);

        cartaQualquer = new Carta("Espada Lendaria", "Arma comum", 3, 1, 1, TipoCarta.CRIATURA, ClasseCarta.QUALQUER);
        cartaQualquer.setId(3L);
    }

    @Test
    void deveListarTodosOsBaralhos() throws Exception {
        List<Baralho> baralhos = List.of(baralhoMago);
        when(baralhoService.listarTodos()).thenReturn(baralhos);

        mockMvc.perform(get("/baralho"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Baralho Mago Ofensivo")));
    }

    @Test
    void deveBuscarBaralhoPorId() throws Exception {
        when(baralhoService.buscarPorId(1L)).thenReturn(baralhoMago);

        mockMvc.perform(get("/baralho/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Baralho Mago Ofensivo")));
    }

    @Test
    void deveRetornar404QuandoBaralhoNaoEncontrado() throws Exception {
        when(baralhoService.buscarPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/baralho/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveCriarBaralhoComSucesso() throws Exception {
        when(baralhoService.salvar(any(Baralho.class))).thenReturn(baralhoMago);

        mockMvc.perform(post("/baralho")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(baralhoMago)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("Baralho Mago Ofensivo")));
    }

    @Test
    void deveAdicionarCartaAoBaralho() throws Exception {
        baralhoMago.getCartas().add(cartaMago);
        when(baralhoService.adicionarCarta(anyLong(), any(Carta.class))).thenReturn(baralhoMago);

        mockMvc.perform(put("/baralho/1/cartas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartaMago)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartas", hasSize(1)));
    }

    @Test
    void deveRetornar400QuandoAdicionarCartaInvalida() throws Exception {
        when(baralhoService.adicionarCarta(anyLong(), any(Carta.class)))
                .thenThrow(new RuntimeException("Carta nao pertence a classe do baralho"));

        mockMvc.perform(put("/baralho/1/cartas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartaMago)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveDetalharCartasDoBaralho() throws Exception {
        baralhoMago.getCartas().add(cartaMago);
        when(baralhoService.buscarPorId(1L)).thenReturn(baralhoMago);

        mockMvc.perform(get("/baralho/1/cartas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Bola de Fogo")));
    }

    @Test
    void deveRetornar404AoDetalharCartasBaralhoInexistente() throws Exception {
        when(baralhoService.buscarPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/baralho/99/cartas"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRemoverCartaDoBaralho() throws Exception {
        when(baralhoService.removerCarta(anyLong(), anyLong())).thenReturn(baralhoMago);

        mockMvc.perform(delete("/baralho/1/cartas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deveDeletarBaralhoComSucesso() throws Exception {
        doNothing().when(baralhoService).deletar(1L);

        mockMvc.perform(delete("/baralho/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveBuscarBaralhosPorNome() throws Exception {
        when(baralhoService.buscarPorNome("Baralho Mago Ofensivo")).thenReturn(List.of(baralhoMago));

        mockMvc.perform(get("/baralho").param("nome", "Baralho Mago Ofensivo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Baralho Mago Ofensivo")));
    }

    @Test
    void deveBuscarBaralhosPorClasse() throws Exception {
        when(baralhoService.buscarPorClasse(ClasseCarta.MAGO)).thenReturn(List.of(baralhoMago));

        mockMvc.perform(get("/baralho").param("classe", "MAGO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].classe", is("MAGO")));
    }
}
