package com.example.demo.controller;



import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.entity.Arquivo;
import com.example.demo.service.ArquivoService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/arquivos")
@AllArgsConstructor
public class ArquivoController {
	private final ArquivoService arquivoService;
	
	
	// o método uploadArquivo será chamado quando uma requisição POST for feita para /upload
	@PostMapping("/upload")
	public ResponseEntity<Arquivo> uploadArquivo(@RequestParam("file") MultipartFile file) {
		//multipartfile permite que você envie arquivos como parte de uma solicitação HTTP
	    String nomeArquivo = arquivoService.salvarArquivo(file);
	    String caminhoArquivo = ServletUriComponentsBuilder.fromCurrentContextPath()
	            .path("/arquivos/downloadArquivo/")
	            .path(nomeArquivo)
	            .toUriString();

	    Arquivo arquivo = new Arquivo(null, nomeArquivo, caminhoArquivo, file.getContentType(), file.getSize());
	    return ResponseEntity.ok(arquivo);
	}


	
	@GetMapping("/downloadArquivo/{nomeArquivo}")
	public ResponseEntity <Resource> downloadArquivo(@PathVariable String nomeArquivo,HttpServletRequest request){
		Resource resource = arquivoService.carregarArquivo(nomeArquivo);
		String contentType = arquivoService.getContentType(request, resource);
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; nomeArquivo=\""+resource.getFilename()+"\"").body(resource);
	}
	
	// Endpoint para buscar todos os arquivos
    @GetMapping
    public ResponseEntity<List<Arquivo>> listarTodosArquivos() {
        List<Arquivo> arquivos = arquivoService.buscarTodosArquivos();
        return ResponseEntity.ok(arquivos);
    }
}
