package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
	Optional<Curso> findByNomeCurso(
			@Param("nomeCurso") String nomeCurso);
}
