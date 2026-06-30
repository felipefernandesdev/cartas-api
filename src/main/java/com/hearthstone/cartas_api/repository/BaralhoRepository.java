package com.hearthstone.cartas_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hearthstone.cartas_api.model.Baralho;
import com.hearthstone.cartas_api.model.ClasseCarta;

public interface BaralhoRepository extends JpaRepository<Baralho, Long> {

    List<Baralho> findByNome(String nome);

    List<Baralho> findByClasse(ClasseCarta classe);
}
