package com.hearthstone.cartas_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hearthstone.cartas_api.model.Baralho;
import com.hearthstone.cartas_api.model.Carta;
import com.hearthstone.cartas_api.model.ClasseCarta;
import com.hearthstone.cartas_api.model.TipoCarta;
import com.hearthstone.cartas_api.repository.BaralhoRepository;

@ExtendWith(MockitoExtension.class)
class BaralhoServiceTest {

    @Mock
    private BaralhoRepository baralhoRepository;

    @InjectMocks
    private BaralhoService baralhoService;

    private Baralho baralhoMago;
    private Carta cartaMago;
    private Carta cartaCacador;
    private Carta cartaQualquer;

    @BeforeEach
    void setUp() {
        baralhoMago = new Baralho("Baralho Mago Ofensivo", ClasseCarta.MAGO);
        baralhoMago.setId(1L);
        baralhoMago.setCartas(new ArrayList<>());

        cartaMago = new Carta("Bola de Fogo", "Causa 6 de dano", 6, 0, 4, TipoCarta.MAGIA, ClasseCarta.MAGO);
        cartaMago.setId(1L);

        cartaCacador = new Carta("Tiro Preciso", "Causa 5 de dano", 5, 2, 3, TipoCarta.CRIATURA, ClasseCarta.CACADOR);
        cartaCacador.setId(2L);

        cartaQualquer = new Carta("Espada Lendaria", "Arma comum", 3, 1, 1, TipoCarta.CRIATURA, ClasseCarta.QUALQUER);
        cartaQualquer.setId(3L);
    }

    @Test
    void deveAdicionarCartaAoBaralho() {
        when(baralhoRepository.findById(1L)).thenReturn(Optional.of(baralhoMago));
        when(baralhoRepository.save(any(Baralho.class))).thenReturn(baralhoMago);

        Baralho resultado = baralhoService.adicionarCarta(1L, cartaMago);

        assertNotNull(resultado);
        assertEquals(1, resultado.getCartas().size());
    }

    @Test
    void deveAdicionarCartaQualquerClasse() {
        when(baralhoRepository.findById(1L)).thenReturn(Optional.of(baralhoMago));
        when(baralhoRepository.save(any(Baralho.class))).thenReturn(baralhoMago);

        Baralho resultado = baralhoService.adicionarCarta(1L, cartaQualquer);

        assertNotNull(resultado);
        assertEquals(1, resultado.getCartas().size());
    }

    @Test
    void deveLancarExcecaoQuandoBaralhoNaoEncontrado() {
        when(baralhoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> baralhoService.adicionarCarta(99L, cartaMago));

        assertEquals("Baralho nao encontrado", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoBaralhoAtingeLimite30Cartas() {
        List<Carta> cartas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Carta carta = new Carta("Carta " + i, "Descricao", 1, 1, 1, TipoCarta.CRIATURA, ClasseCarta.MAGO);
            carta.setId((long) (i + 10));
            cartas.add(carta);
        }
        baralhoMago.setCartas(cartas);

        when(baralhoRepository.findById(1L)).thenReturn(Optional.of(baralhoMago));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> baralhoService.adicionarCarta(1L, cartaMago));

        assertEquals("Baralho ja atingiu o limite de 30 cartas", exception.getMessage());
        verify(baralhoRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoCartaNaoPertenceClasse() {
        when(baralhoRepository.findById(1L)).thenReturn(Optional.of(baralhoMago));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> baralhoService.adicionarCarta(1L, cartaCacador));

        assertEquals("Carta nao pertence a classe do baralho", exception.getMessage());
        verify(baralhoRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoJaExistem2CartasIguais() {
        baralhoMago.getCartas().add(cartaMago);
        baralhoMago.getCartas().add(cartaMago);

        when(baralhoRepository.findById(1L)).thenReturn(Optional.of(baralhoMago));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> baralhoService.adicionarCarta(1L, cartaMago));

        assertEquals("Ja existem 2 cartas iguais no baralho", exception.getMessage());
        verify(baralhoRepository, never()).save(any());
    }

    @Test
    void devePermitirAdicionar2CartasIguais() {
        baralhoMago.getCartas().add(cartaMago);

        when(baralhoRepository.findById(1L)).thenReturn(Optional.of(baralhoMago));
        when(baralhoRepository.save(any(Baralho.class))).thenReturn(baralhoMago);

        Baralho resultado = baralhoService.adicionarCarta(1L, cartaMago);

        assertNotNull(resultado);
    }

    @Test
    void deveRemoverCartaDoBaralho() {
        baralhoMago.getCartas().add(cartaMago);

        when(baralhoRepository.findById(1L)).thenReturn(Optional.of(baralhoMago));
        when(baralhoRepository.save(any(Baralho.class))).thenReturn(baralhoMago);

        Baralho resultado = baralhoService.removerCarta(1L, 1L);

        assertNotNull(resultado);
        assertEquals(0, resultado.getCartas().size());
    }

    @Test
    void deveListarTodosOsBaralhos() {
        when(baralhoRepository.findAll()).thenReturn(List.of(baralhoMago));

        List<Baralho> resultado = baralhoService.listarTodos();

        assertEquals(1, resultado.size());
    }

    @Test
    void deveBuscarBaralhoPorId() {
        when(baralhoRepository.findById(1L)).thenReturn(Optional.of(baralhoMago));

        Baralho resultado = baralhoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Baralho Mago Ofensivo", resultado.getNome());
    }

    @Test
    void deveRetornarNullQuandoBaralhoNaoEncontrado() {
        when(baralhoRepository.findById(99L)).thenReturn(Optional.empty());

        Baralho resultado = baralhoService.buscarPorId(99L);

        assertEquals(null, resultado);
    }

    @Test
    void deveBuscarBaralhoPorNome() {
        when(baralhoRepository.findByNome("Baralho Mago Ofensivo")).thenReturn(List.of(baralhoMago));

        List<Baralho> resultado = baralhoService.buscarPorNome("Baralho Mago Ofensivo");

        assertEquals(1, resultado.size());
    }

    @Test
    void deveBuscarBaralhoPorClasse() {
        when(baralhoRepository.findByClasse(ClasseCarta.MAGO)).thenReturn(List.of(baralhoMago));

        List<Baralho> resultado = baralhoService.buscarPorClasse(ClasseCarta.MAGO);

        assertEquals(1, resultado.size());
    }

    @Test
    void deveDeletarBaralho() {
        baralhoService.deletar(1L);

        verify(baralhoRepository).deleteById(1L);
    }
}
