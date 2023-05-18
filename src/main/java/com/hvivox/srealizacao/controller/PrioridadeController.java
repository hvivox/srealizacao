package com.hvivox.srealizacao.controller;

import com.hvivox.srealizacao.model.Prioridade;
import com.hvivox.srealizacao.service.FolhaService;
import com.hvivox.srealizacao.service.PrioridadeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log4j2
@RestController
@RequestMapping("/prioridades")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PrioridadeController {
    @Autowired
    PrioridadeService prioridadeService;
    @Autowired
    FolhaService folhaService;


    @GetMapping("folha/{idFolha}")
    public ResponseEntity<List<Prioridade>> getPrioridadeByFolha(@PathVariable(value="idFolha") Integer idFolha ){
        log.debug("POST getPrioridadeByFolha IDFolha {} ", idFolha);
        return ResponseEntity.status(HttpStatus.OK).body( prioridadeService.findAllByFolha( idFolha ) );
    }

    @PostMapping
    public ResponseEntity<Prioridade> save(@RequestBody Prioridade prioridade){
        log.debug("POST salvar dados {} ", prioridade.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(prioridadeService.save(prioridade));

    }

   /* @DeleteMapping("{folhaId}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deletarTodasPrioridades(@PathVariable(value = "folhaId") Integer folhaid ){
      //  log.debug("DELETE deletar todas as prioridades com ID {} ", courseId);
        Optional<Folha> folhaOptional = folhaService.findById(folhaid);
        if (!folhaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
        }

        prioridadeService.deleteTodosDaFolha( folhaOptional.get().getId() );
        //log.info("Prioridade deleteTodosDaFolha realizado com sucesso IDFolha {} ", courseId);
        return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully.");
    }*/

}
