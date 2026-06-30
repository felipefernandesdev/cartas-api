package com.hearthstone.cartas_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hearthstone.cartas_api.model.Carta;
import com.hearthstone.cartas_api.model.ClasseCarta;
import com.hearthstone.cartas_api.model.TipoCarta;

public interface CartaRepository extends JpaRepository<Carta, Long> {

    List<Carta> findByNome(String nome);

    List<Carta> findByClasse(ClasseCarta classe);

    List<Carta> findByTipo(TipoCarta tipo);
}
