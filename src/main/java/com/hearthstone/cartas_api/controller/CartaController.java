package com.hearthstone.cartas_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hearthstone.cartas_api.model.Carta;
import com.hearthstone.cartas_api.model.ClasseCarta;
import com.hearthstone.cartas_api.model.TipoCarta;
import com.hearthstone.cartas_api.repository.CartaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/carta")
public class CartaController {

    private final CartaRepository cartaRepository;

    public CartaController(CartaRepository cartaRepository) {
        this.cartaRepository = cartaRepository;
    }

    @GetMapping
    public List<Carta> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) ClasseCarta classe,
            @RequestParam(required = false) TipoCarta tipo) {

        if (nome != null) {
            return cartaRepository.findByNome(nome);
        }
        if (classe != null) {
            return cartaRepository.findByClasse(classe);
        }
        if (tipo != null) {
            return cartaRepository.findByTipo(tipo);
        }
        return cartaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Carta buscarPorId(@PathVariable Long id) {
        return cartaRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Carta criar(@Valid @RequestBody Carta carta) {
        return cartaRepository.save(carta);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        cartaRepository.deleteById(id);
    }
}
