package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.RelatorioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/asyncs")
@EnableAsync
@AllArgsConstructor
public class AsyncController {
	private RelatorioService relatorioService;

	@GetMapping
	public ResponseEntity<String> gerarRelatorio()
			throws InterruptedException {

		relatorioService.gerarRelatorio();
		return ResponseEntity.ok("Relatorio iniciada com sucesso!");
	}
}
