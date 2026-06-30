package com.hearthstone.cartas_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hearthstone.cartas_api.model.Baralho;
import com.hearthstone.cartas_api.model.Carta;
import com.hearthstone.cartas_api.model.ClasseCarta;
import com.hearthstone.cartas_api.repository.BaralhoRepository;

@Service
public class BaralhoService {

    private final BaralhoRepository baralhoRepository;

    public BaralhoService(BaralhoRepository baralhoRepository) {
        this.baralhoRepository = baralhoRepository;
    }

    public List<Baralho> listarTodos() {
        return baralhoRepository.findAll();
    }

    public Baralho buscarPorId(Long id) {
        return baralhoRepository.findById(id).orElse(null);
    }

    public List<Baralho> buscarPorNome(String nome) {
        return baralhoRepository.findByNome(nome);
    }

    public List<Baralho> buscarPorClasse(ClasseCarta classe) {
        return baralhoRepository.findByClasse(classe);
    }

    public Baralho salvar(Baralho baralho) {
        return baralhoRepository.save(baralho);
    }

    public void deletar(Long id) {
        baralhoRepository.deleteById(id);
    }

    public Baralho adicionarCarta(Long baralhoId, Carta carta) {
        Baralho baralho = baralhoRepository.findById(baralhoId)
                .orElseThrow(() -> new RuntimeException("Baralho nao encontrado"));

        if (baralho.getCartas().size() >= 30) {
            throw new RuntimeException("Baralho ja atingiu o limite de 30 cartas");
        }

        if (carta.getClasse() != ClasseCarta.QUALQUER
                && carta.getClasse() != baralho.getClasse()) {
            throw new RuntimeException("Carta nao pertence a classe do baralho");
        }

        long qtdIgual = baralho.getCartas().stream()
                .filter(c -> c.getId().equals(carta.getId()))
                .count();

        if (qtdIgual >= 2) {
            throw new RuntimeException("Ja existem 2 cartas iguais no baralho");
        }

        baralho.getCartas().add(carta);
        return baralhoRepository.save(baralho);
    }

    public Baralho removerCarta(Long baralhoId, Long cartaId) {
        Baralho baralho = baralhoRepository.findById(baralhoId)
                .orElseThrow(() -> new RuntimeException("Baralho nao encontrado"));

        baralho.getCartas().removeIf(c -> c.getId().equals(cartaId));
        return baralhoRepository.save(baralho);
    }
}
