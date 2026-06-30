package com.hearthstone.cartas_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hearthstone.cartas_api.model.Carta;
import com.hearthstone.cartas_api.model.ClasseCarta;
import com.hearthstone.cartas_api.model.TipoCarta;
import com.hearthstone.cartas_api.repository.CartaRepository;

@ExtendWith(MockitoExtension.class)
class CartaServiceTest {

    @Mock
    private CartaRepository cartaRepository;

    @InjectMocks
    private CartaService cartaService;

    private Carta cartaMago;
    private Carta cartaCacador;

    @BeforeEach
    void setUp() {
        cartaMago = new Carta("Bola de Fogo", "Causa 6 de dano", 6, 0, 4, TipoCarta.MAGIA, ClasseCarta.MAGO);
        cartaMago.setId(1L);

        cartaCacador = new Carta("Tiro Preciso", "Causa 5 de dano", 5, 2, 3, TipoCarta.CRIATURA, ClasseCarta.CACADOR);
        cartaCacador.setId(2L);
    }

    @Test
    void deveBuscarTodasAsCartas() {
        when(cartaRepository.findAll()).thenReturn(Arrays.asList(cartaMago, cartaCacador));

        List<Carta> resultado = cartaService.buscar(null, null, null);

        assertEquals(2, resultado.size());
        verify(cartaRepository).findAll();
    }

    @Test
    void deveBuscarCartaPorNome() {
        when(cartaRepository.findByNome("Bola de Fogo")).thenReturn(List.of(cartaMago));

        List<Carta> resultado = cartaService.buscar("Bola de Fogo", null, null);

        assertEquals(1, resultado.size());
        assertEquals("Bola de Fogo", resultado.get(0).getNome());
    }

    @Test
    void deveBuscarCartaPorClasse() {
        when(cartaRepository.findByClasse(ClasseCarta.MAGO)).thenReturn(List.of(cartaMago));

        List<Carta> resultado = cartaService.buscar(null, ClasseCarta.MAGO, null);

        assertEquals(1, resultado.size());
        assertEquals(ClasseCarta.MAGO, resultado.get(0).getClasse());
    }

    @Test
    void deveBuscarCartaPorTipo() {
        when(cartaRepository.findByTipo(TipoCarta.CRIATURA)).thenReturn(List.of(cartaCacador));

        List<Carta> resultado = cartaService.buscar(null, null, TipoCarta.CRIATURA);

        assertEquals(1, resultado.size());
        assertEquals(TipoCarta.CRIATURA, resultado.get(0).getTipo());
    }

    @Test
    void deveBuscarCartaPorId() {
        when(cartaRepository.findById(1L)).thenReturn(Optional.of(cartaMago));

        Optional<Carta> resultado = cartaService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Bola de Fogo", resultado.get().getNome());
    }

    @Test
    void deveRetornarVazioQuandoIdNaoExiste() {
        when(cartaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Carta> resultado = cartaService.buscarPorId(99L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void deveSalvarCarta() {
        when(cartaRepository.save(any(Carta.class))).thenReturn(cartaMago);

        Carta resultado = cartaService.salvar(cartaMago);

        assertEquals("Bola de Fogo", resultado.getNome());
        verify(cartaRepository).save(cartaMago);
    }

    @Test
    void deveDeletarCarta() {
        cartaService.deletar(1L);

        verify(cartaRepository).deleteById(1L);
    }
}
