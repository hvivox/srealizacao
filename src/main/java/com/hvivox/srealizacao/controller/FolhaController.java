package com.hvivox.srealizacao.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hvivox.srealizacao.dto.FolhaDto;
import com.hvivox.srealizacao.exception.EntidadeNaoEncontradaException;
import com.hvivox.srealizacao.exception.FolhaNaoEncontradoException;
import com.hvivox.srealizacao.exception.NegocioException;
import com.hvivox.srealizacao.model.Folha;
import com.hvivox.srealizacao.service.*;
import com.hvivox.srealizacao.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("folhas")
public class FolhaController {
    
    @Autowired
    private FolhaService folhaService;
    
    @Autowired
    private PrioridadeService prioridadeService;
    @Autowired
    private RestricaoService restricaoService;
    @Autowired
    private GratidaoService gratidaoService;
    
    @Autowired
    private AprendizagemService aprendizagemService;
    
    @GetMapping
    public ResponseEntity<Page<Folha>> getAll(SpecificationTemplate.FolhaSpec spec, @PageableDefault(page = 0, size =
            10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        
        Page<Folha> folhaPage = folhaService.findAll(spec, pageable);
        //ResponseEntity está sendo usado como exemplo para monstrar a forma de utilização
        return ResponseEntity.status(HttpStatus.OK).body(folhaPage);
    }
    
    @GetMapping("{idFolha}")
    @ResponseStatus(HttpStatus.OK)
    public Folha getById(@PathVariable(value = "idFolha") Integer idFolha) {
        Folha folhaEncontrada = folhaService.buscarOuFalhar(idFolha);
        return folhaEncontrada;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Folha save(@RequestBody @Validated FolhaDto folhaDto) {
        Folha folha = new Folha();
        BeanUtils.copyProperties(folhaDto, folha);

        try {
            return folhaService.save(folha);
        } catch (FolhaNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
        
    }
    
    
    @PutMapping("/{idFolha}")
    public ResponseEntity<Object> update(@PathVariable Integer idFolha, @Valid @RequestBody FolhaDto folhaInputDto) {
        // log.debug("PUT Update dados {} e idFolha:{} ", folha.toString(), idFolha);
       /* ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);*/
        
        try {
            Folha folhaAtualizada = folhaService.update(folhaInputDto, idFolha);
            return ResponseEntity.status(HttpStatus.OK).body(folhaAtualizada);
        } catch (FolhaNaoEncontradoException e) {
            //retorna BAD_REQUEST | 400, a resposta na estrutura padrao do spring
            // Corrigi o problema da resposta ficar com o NOT_FOUND 404
           // log.debug( "CAUSA DO PROBLEMA {}", e.getCause() );
            throw new NegocioException(e.getMessage(), e);
            
        }
        
    }
    
    
    @GetMapping("/excel")
    public ResponseEntity<String> getAll() {
        
        folhaService.gerarExcelFolha();
        //ResponseEntity está sendo usado como exemplo para monstrar a forma de utilização
        //return ResponseEntity.status(HttpStatus.OK).body(folhaPage);
        return ResponseEntity.status(HttpStatus.OK).body("relat gerado");
    }
    
    
    
}
