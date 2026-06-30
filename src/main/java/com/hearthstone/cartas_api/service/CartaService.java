package com.hearthstone.cartas_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hearthstone.cartas_api.model.Carta;
import com.hearthstone.cartas_api.model.ClasseCarta;
import com.hearthstone.cartas_api.model.TipoCarta;
import com.hearthstone.cartas_api.repository.CartaRepository;

@Service
public class CartaService {

    private final CartaRepository cartaRepository;

    public CartaService(CartaRepository cartaRepository) {
        this.cartaRepository = cartaRepository;
    }

    public List<Carta> buscar(String nome, ClasseCarta classe, TipoCarta tipo) {
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

    public Optional<Carta> buscarPorId(Long id) {
        return cartaRepository.findById(id);
    }

    public Carta salvar(Carta carta) {
        return cartaRepository.save(carta);
    }

    public void deletar(Long id) {
        cartaRepository.deleteById(id);
    }
}
