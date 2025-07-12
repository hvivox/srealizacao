package com.hvivox.srealizacao.controller;

import com.hvivox.srealizacao.model.Sheet;
import com.hvivox.srealizacao.model.Priority;
import com.hvivox.srealizacao.service.SheetService;
import com.hvivox.srealizacao.service.PriorityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log4j2
@RestController
@RequestMapping("/priorities")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PriorityController {
    @Autowired
    PriorityService priorityService;
    @Autowired
    SheetService sheetService;


    @GetMapping("sheet/{idSheet}")
    public ResponseEntity<List<Priority>> getPriorityBySheet(@PathVariable(value="idSheet") Integer idSheet ){
        Thread.currentThread().setName(String.valueOf(System.currentTimeMillis()));
        log.info("Buscar prioridades associadas a Folha ID: {} ", idSheet);
        return ResponseEntity.status(HttpStatus.OK).body( priorityService.findAllBySheet( idSheet ) );
    }


    @PostMapping
    public ResponseEntity<Priority> save(@RequestBody Priority priority){
        Thread.currentThread().setName(String.valueOf(System.currentTimeMillis()));
        log.info("Salvar prioridade {} ", priority.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(priorityService.save(priority));
    }

   @DeleteMapping("{folhaId}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteAllPriorities(@PathVariable(value = "folhaId") Integer folhaid ){
       Thread.currentThread().setName(String.valueOf(System.currentTimeMillis()));
        Sheet sheetFound = sheetService.findOrFail(folhaid);
        priorityService.deleteAllFromSheet( sheetFound.getId() );

        return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully.");
    }

}
