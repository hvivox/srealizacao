package com.hvivox.srealizacao.controller;

import com.hvivox.srealizacao.dto.SheetDto;
import com.hvivox.srealizacao.exception.EntityNotFoundException;
import com.hvivox.srealizacao.exception.SheetNotFoundException;
import com.hvivox.srealizacao.exception.BusinessException;
import com.hvivox.srealizacao.model.Sheet;
import com.hvivox.srealizacao.service.*;
import com.hvivox.srealizacao.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Log4j2
@RestController
//@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("sheets")
public class SheetController {

    @Autowired
    private SheetService sheetService;

    @Autowired
    private PriorityService priorityService;
    @Autowired
    private RestrictionService restrictionService;
    @Autowired
    private GratitudeService gratitudeService;

    @Autowired
    private LearningService learningService;


    @GetMapping
    public ResponseEntity<Page<Sheet>> getAll(SpecificationTemplate.FolhaSpec spec, @PageableDefault(page = 0, size =
            10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Sheet> sheetPage = sheetService.findAll(spec, pageable);
        //ResponseEntity está sendo usado como exemplo para monstrar a forma de utilização
        return ResponseEntity.status(HttpStatus.OK).body(sheetPage);
    }


    @GetMapping("{sheetId}")
    @ResponseStatus(HttpStatus.OK)
    public Sheet getById(@PathVariable(value = "sheetId") Integer idSheet) {
        Sheet sheetFound = sheetService.findOrFail(idSheet);
        return sheetFound;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sheet save(@RequestBody @Validated SheetDto sheetDto) {
        Sheet sheet = new Sheet();
        BeanUtils.copyProperties(sheetDto, sheet);

        try {
            return sheetService.save(sheet);
        } catch (SheetNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }

    }


    @PutMapping("/{sheetId}")
    public ResponseEntity<Object> update(@PathVariable Integer sheetId, @Valid @RequestBody SheetDto sheetDto) {

       /* ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);*/
        // TODO SDFSFS
        try {
            Sheet sheetUpdated = sheetService.update(sheetDto, sheetId);
            return ResponseEntity.status(HttpStatus.OK).body(sheetUpdated);
        } catch (SheetNotFoundException e) {

            log.debug( "CAUSA DO PROBLEMA {}", e.getCause() );
            throw new BusinessException(e.getMessage(), e);

        }
    }


    @PatchMapping("/{sheetId}")
    public ResponseEntity<String> inactiveSheet(@PathVariable Integer sheetId) {
        try {

            Sheet sheetFound = sheetService.findOrFail(sheetId);

            if ((sheetFound instanceof Sheet) && sheetFound.getStatus()) {

                sheetFound.setStatus(false);
                sheetService.inactiveSheet(sheetFound);
                return ResponseEntity.status(HttpStatus.OK).body("Folha inativada com sucesso");
            }

            return ResponseEntity.status(HttpStatus.OK).body("Inativo ou não encontrado");

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping(path = "/excel", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getreportByDate(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

            System.out.println("data inicio " + startDateTime);
            System.out.println("data fim " + endDateTime);

            ByteArrayOutputStream baos = sheetService.gerarExcelFolha();
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; " + "filename" +
                    "=relatorio_de_vendas.xls").contentType(MediaType.APPLICATION_OCTET_STREAM).body(baos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
      /*  folhaService.gerarExcelFolha();
        return ResponseEntity.status(HttpStatus.OK).body("relat gerado");*/
    }

}
