package com.example.demo.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EstudanteSchedule {
	// schedule faz agendamento de tarefas
	// @Scheduled(fixedDelay = 2000)
	@Scheduled(cron = "0 32 19 * * *")
	public void executarTarefa() {
		log.info("Tarefa Executada");
	}
}
