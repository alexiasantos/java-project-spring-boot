package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Curso;
import com.example.demo.service.AvaliacaoCursoService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/avaliacoes")
public class AvaliacaoCursoController {

	private final AvaliacaoCursoService avaliacaoService;

	@PostMapping
	public ResponseEntity<String> avaliar(
			@RequestParam Long idEstudante,
			@RequestParam String nomeCurso,
			@RequestParam Integer notaAvaliacao) {
		return avaliacaoService.avaliar(idEstudante, nomeCurso,
				notaAvaliacao);
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Curso> cadastrarCurso(
			@RequestBody Curso curso) {
		return avaliacaoService.cadastrarCurso(curso);
	}

	@GetMapping("/{id}")
	public Page<Curso> buscarCursos(
			@RequestParam(defaultValue = "0") Integer pagina,
			@RequestParam(defaultValue = "5") Integer itemsPorPagina) {
		return avaliacaoService
				.buscarCursos(PageRequest.of(pagina, itemsPorPagina));
	}

}
