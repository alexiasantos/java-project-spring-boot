package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String titulo;

	// ManyToOne = dizemos que este etributo fará parte de um relacionamento onde
	// haverá vários livros para um estudante.
	@ManyToOne
	// nullable = Não permitirá cadastrar um livro sem que exista um estudante
	// associado
	// aqui dizemos que haverá uma coluna chamada estudante_id para coletar o id do
	// estudante que o livro está associado
	@JoinColumn(name = "estudante_id", nullable = false)
	@JsonIgnore
	private Estudante estudante;

}
