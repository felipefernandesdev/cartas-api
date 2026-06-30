package com.hearthstone.cartas_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hearthstone.cartas_api.model.Baralho;
import com.hearthstone.cartas_api.model.Carta;
import com.hearthstone.cartas_api.model.ClasseCarta;
import com.hearthstone.cartas_api.repository.CartaRepository;
import com.hearthstone.cartas_api.service.BaralhoService;

@RestController
@RequestMapping("/baralho")
public class BaralhoController {

    private final BaralhoService baralhoService;
    private final CartaRepository cartaRepository;

    public BaralhoController(BaralhoService baralhoService, CartaRepository cartaRepository) {
        this.baralhoService = baralhoService;
        this.cartaRepository = cartaRepository;
    }

    @GetMapping
    public List<Baralho> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) ClasseCarta classe) {

        if (nome != null) {
            return baralhoService.buscarPorNome(nome);
        }
        if (classe != null) {
            return baralhoService.buscarPorClasse(classe);
        }
        return baralhoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Baralho buscarPorId(@PathVariable Long id) {
        return baralhoService.buscarPorId(id);
    }

    @GetMapping("/{id}/cartas")
    public List<Carta> buscarCartas(@PathVariable Long id) {
        Baralho baralho = baralhoService.buscarPorId(id);
        if (baralho == null) {
            return List.of();
        }
        return baralho.getCartas();
    }

    @PostMapping
    public Baralho criar(@RequestBody Baralho baralho) {
        return baralhoService.salvar(baralho);
    }

    @PutMapping("/{id}/cartas")
    public ResponseEntity<?> adicionarCarta(@PathVariable Long id, @RequestBody Carta carta) {
        try {
            Baralho baralho = baralhoService.adicionarCarta(id, carta);
            return ResponseEntity.ok(baralho);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/cartas/{cartaId}")
    public ResponseEntity<?> removerCarta(@PathVariable Long id, @PathVariable Long cartaId) {
        try {
            Baralho baralho = baralhoService.removerCarta(id, cartaId);
            return ResponseEntity.ok(baralho);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        baralhoService.deletar(id);
    }
}
