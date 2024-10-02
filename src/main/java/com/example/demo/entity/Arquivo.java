package com.example.demo.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "arquivo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Arquivo {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomeArquivo;
	private String linkDownload;
	private String estensaoArquivo;
	private long size;
}
