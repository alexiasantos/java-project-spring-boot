package com.example.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RelatorioService {
	@Async
	public void gerarRelatorio() throws InterruptedException {
		log.info("Tarefa iniciada com sucesso. ");
		Thread.sleep(5000);
		log.info("Tarefa finalizada com sucesso!");
	}
}
