package com.hvivox.srealizacao.controller;

import com.hvivox.srealizacao.dto.FolhaDto;
import com.hvivox.srealizacao.model.*;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        
        return ResponseEntity.status(HttpStatus.OK).body(folhaPage);
    }
    
    @GetMapping("{idFolha}")
    public Folha getById(@PathVariable(value = "idFolha") Integer idFolha) {
        Folha folhaEncontrada = folhaService.findById(idFolha);
        return folhaEncontrada;
    }
    
    
    @PostMapping
    public ResponseEntity<Folha> save(@RequestBody @Validated FolhaDto folhaDto) {
        Folha folha = new Folha();
        BeanUtils.copyProperties( folhaDto, folha  );
        return ResponseEntity.status(HttpStatus.CREATED).body(folhaService.save(folha));
    }
    
    
    @PutMapping("/{idFolha}")
    @Transactional
    public ResponseEntity<Object> update(@PathVariable Integer idFolha,
                                         @Valid @RequestBody FolhaDto folhaInputDto ) {
        // log.debug("PUT Update dados {} e idFolha:{} ", folha.toString(), idFolha);
        Folha folhaInput = new Folha();
        BeanUtils.copyProperties( folhaInputDto, folhaInput  );
        
        Folha folhaEncontrada = folhaService.findById(idFolha);
       // Folha folhaInput = new Folha();
        if (folhaEncontrada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Folha não encontrada!");
            
        } else {
            Folha folhaAtualizada = folhaService.update(folhaEncontrada, folhaInput);
            return ResponseEntity.status(HttpStatus.OK).body(folhaAtualizada);
            
        }
    }
    
}
