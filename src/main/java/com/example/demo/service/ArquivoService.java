package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.config.ArquivoStorageProperties;
import com.example.demo.exception.ArquivoNaoEncontradoException;
import com.example.demo.exception.UploadArquivoException;
import com.example.demo.entity.Arquivo;
import com.example.demo.repository.ArquivoRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ArquivoService {

    private final Path fileStorageLocation;
    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoStorageProperties fileStorageLocation, ArquivoRepository arquivoRepository) {
        this.fileStorageLocation = Paths.get(fileStorageLocation.getUploadDir()).toAbsolutePath().normalize();
        this.arquivoRepository = arquivoRepository;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new UploadArquivoException("Algo deu errado ao tentar criar a pasta", e);
        }
    }

    public String salvarArquivo(MultipartFile file) {
    	String nomeArquivo = StringUtils.cleanPath(file.getOriginalFilename());
    	try {
    	    if (nomeArquivo.contains("..")) {
    	        throw new UploadArquivoException("Arquivo inválido");
    	    }

    	    Path targetLocation = this.fileStorageLocation.resolve(nomeArquivo);
    	    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

    	    // Construa o link de download
    	    String linkDownload = ServletUriComponentsBuilder.fromCurrentContextPath()
    	            .path("/arquivos/downloadArquivo/")
    	            .path(nomeArquivo)
    	            .toUriString();

    	    Arquivo arquivo = new Arquivo();
    	    arquivo.setNomeArquivo(nomeArquivo);
    	    arquivo.setLinkDownload(linkDownload); // Use o link de download correto
    	    arquivo.setEstensaoArquivo(StringUtils.getFilenameExtension(nomeArquivo));
    	    arquivo.setSize(file.getSize());
    	    
    	    // Salvar no repositório (banco de dados)
    	    arquivoRepository.save(arquivo);

    	    return nomeArquivo;
    	} catch (Exception e) {
    	    log.error("Erro ao salvar o arquivo: ", e);
    	    throw new UploadArquivoException("Erro ao tentar salvar o arquivo", e);
    	}

    }



    public Resource carregarArquivo(String nomeArquivo) {
        try {
            Path filePath = this.fileStorageLocation.resolve(nomeArquivo).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new ArquivoNaoEncontradoException("Arquivo não encontrado");
            }
        } catch (Exception e) {
            throw new ArquivoNaoEncontradoException("Arquivo não encontrado");
        }
    }

    public String getContentType(HttpServletRequest request, Resource resource) {
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            log.error("Não foi possível determinar o tipo de arquivo", e);
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    // Novo método para buscar todos os arquivos
    public List<Arquivo> buscarTodosArquivos() {
        return arquivoRepository.findAll();
    }
}
