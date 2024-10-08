package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Estudante;

@Repository
public interface EstudanteRepository
		extends JpaRepository<Estudante, Long> {

	@Query(value = " SELECT e.* FROM api.estudante e left join api.avaliacao_curso ac ON ac.estudante_id = e.id where ac.estudante_id is null ", nativeQuery = true)
	List<Estudante> findByAvaliacaoCursosEstudanteIsNullNativeQuery();

	@Query(value = " SELECT e FROM Estudante e join AvaliacaoCurso ac where ac.estudante.id is null ")
	List<Estudante> findByAvaliacaoCursosEstudanteIsNullJPQL();

	List<Estudante> findByAvaliacaoCursosEstudanteIsNull();
}
