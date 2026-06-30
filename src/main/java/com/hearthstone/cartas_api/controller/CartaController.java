package com.hearthstone.cartas_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.hearthstone.cartas_api.service.CartaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/carta")
public class CartaController {

    private final CartaService cartaService;

    public CartaController(CartaService cartaService) {
        this.cartaService = cartaService;
    }

    @GetMapping
    public List<Carta> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) ClasseCarta classe,
            @RequestParam(required = false) TipoCarta tipo) {
        return cartaService.buscar(nome, classe, tipo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carta> buscarPorId(@PathVariable Long id) {
        return cartaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Carta> criar(@Valid @RequestBody Carta carta) {
        Carta cartaSalva = cartaService.salvar(carta);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaSalva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cartaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
