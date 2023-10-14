package com.hvivox.srealizacao.service;


import com.hvivox.srealizacao.dto.FolhaDto;
import com.hvivox.srealizacao.exception.EntidadeEmUsoException;
import com.hvivox.srealizacao.exception.FolhaNaoEncontradoException;
import com.hvivox.srealizacao.model.*;
import com.hvivox.srealizacao.repository.FolhaRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import java.time.LocalDateTime;
import java.util.Date;


@Log4j2
@Service
public class FolhaService {
    
    private static final String MSG_FOLHA_EM_USO
            = "Folha de código %d não pode ser removida, pois está em uso";
    
    private static final String MSG_FOLHA_NAO_ENCONTRADA
            = "Não existe folha com cadastro de código %d";
    
    private static final String MSG_ENTIDADE_NAO_ENCONTRADA = "Entidade não encontrada";
    
    @Autowired
    FolhaRepository folhaRepository;
    
    @Autowired
    private PrioridadeService prioridadeService;
    
    @Autowired
    private RestricaoService restricaoService;
    
    @Autowired
    private GratidaoService gratidaoService;
    
    @Autowired
    private AprendizagemService aprendizagemService;
    

    
    public Page<Folha> findAll(Specification<Folha> spec, Pageable pageable) {
        return folhaRepository.findAll(spec, pageable);
    }
    
    
    public Folha buscarOuFalhar(Integer idFolha) {
        return folhaRepository.findById(idFolha)
                .orElseThrow(() -> new FolhaNaoEncontradoException(idFolha));
    }
    
    
    @Transactional
    public Folha save(Folha folha) {
    
        log.debug("POST Salvar dados {} ", folha.toString());
        List<Prioridade> prioridades = new ArrayList<>();
        List<Restricao> restricoes = new ArrayList<>();
        List<Gratidao> gratidoes = new ArrayList<>();
        List<Aprendizagem> aprendizagens = new ArrayList<>();
    
        for (Prioridade prioridadeEncontrada : folha.getPrioridadeList()) {
            Prioridade prioridade = new Prioridade();
            BeanUtils.copyProperties(prioridadeEncontrada, prioridade);
            prioridade.setFolha(folha);
            prioridades.add(prioridade);
        }
    
        for (Restricao restricaoEncontrada : folha.getRestricaoList()) {
            Restricao restricao = new Restricao();
            BeanUtils.copyProperties(restricaoEncontrada, restricao);
            restricao.setFolha(folha);
            restricoes.add(restricao);
        }
    
        for (Gratidao gratidaoEncontrada : folha.getGratidaoList()) {
            Gratidao gratidao = new Gratidao();
            BeanUtils.copyProperties(gratidaoEncontrada, gratidao);
            gratidao.setFolha(folha);
            gratidoes.add(gratidao);
        }
    
        for (Aprendizagem aprendizagemEncontrada : folha.getAprendizagemList()) {
            Aprendizagem aprendizagem = new Aprendizagem();
            BeanUtils.copyProperties(aprendizagemEncontrada, aprendizagem);
            aprendizagem.setFolha(folha);
            aprendizagens.add(aprendizagem);
        }
    
        folha.setPrioridadeList(prioridades);
        folha.setRestricaoList(restricoes);
        folha.setGratidaoList(gratidoes);
        folha.setAprendizagemList(aprendizagens);
        
        return folhaRepository.save(folha);
    }
    
    @Transactional
    public Folha update(FolhaDto folhaInputDto, Integer idFolha) {
    
        Folha folhaInput = new Folha();
        BeanUtils.copyProperties(folhaInputDto, folhaInput);
        
        Folha folhaEncontrada = buscarOuFalhar(idFolha);
        //folhaEncontrada.setId(folhaEncontrada.getId());
        folhaEncontrada.setFoco(folhaInput.getFoco());
        folhaEncontrada.setDtarealizacao(folhaInput.getDtarealizacao());
        folhaEncontrada.setNotadia(folhaInput.getNotadia());
        folhaEncontrada.setObservacao(folhaInput.getObservacao());
        folhaEncontrada.setStatus(folhaInput.getStatus());
        
        Folha folhaObjectID = new Folha();
        folhaObjectID.setId(folhaEncontrada.getId());
    
        prioridadeService.deleteTodosDaFolha(folhaEncontrada.getId());
        if (!folhaInput.getPrioridadeList().isEmpty()) {
            
            for (Prioridade prioridadeEncontrada : folhaInput.getPrioridadeList()) {
                Prioridade prioridade = new Prioridade();
                BeanUtils.copyProperties(prioridadeEncontrada, prioridade, "folha");
                
                prioridade.setFolha(folhaObjectID);
                 log.debug("atributo prioridade {}", prioridade);
                folhaEncontrada.getPrioridadeList().add(prioridade);
            }
    
        }
    
        restricaoService.deleteTodosDaFolha(folhaEncontrada.getId());
        if (!folhaInput.getRestricaoList().isEmpty()) {
    
            for (Restricao restricaoEncontrada : folhaInput.getRestricaoList()) {
                
                Restricao restricao = new Restricao();
                BeanUtils.copyProperties(restricaoEncontrada, restricao, "folha");
                restricao.setFolha(folhaObjectID);
                folhaEncontrada.getRestricaoList().add(restricao);
            }
    
        }
    
    
        gratidaoService.deleteTodosDaFolha(folhaEncontrada.getId());
        if (!folhaInput.getGratidaoList().isEmpty()) {
            for (Gratidao gratidaoEncontrada : folhaInput.getGratidaoList()) {
                Gratidao gratidao = new Gratidao();
                BeanUtils.copyProperties(gratidaoEncontrada, gratidao, "folha");
                gratidao.setFolha(folhaObjectID);
                folhaEncontrada.getGratidaoList().add(gratidao);
            }
    
        }
    
    
        aprendizagemService.deleteTodosDaFolha(folhaEncontrada.getId());
        if (!folhaInput.getAprendizagemList().isEmpty()) {
            
            for (Aprendizagem aprendizagemEncontrada : folhaInput.getAprendizagemList()) {
                Aprendizagem aprendizagem = new Aprendizagem();
                BeanUtils.copyProperties(aprendizagemEncontrada, aprendizagem, "folha");
                aprendizagem.setFolha(folhaObjectID);
                folhaEncontrada.getAprendizagemList().add(aprendizagem);
            }
    
        }
        
        return folhaRepository.save(folhaEncontrada);
        
    }
    

    
    @Transactional
    public void delete(Integer idFolha) {
        
        try {
            folhaRepository.deleteById( idFolha );
        } catch (EmptyResultDataAccessException e) {
            throw new FolhaNaoEncontradoException(
                    String.format(MSG_FOLHA_NAO_ENCONTRADA, idFolha ));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_FOLHA_EM_USO, idFolha ));
        }
    }
    
    
    public void gerarExcelFolha() {
        log.info("Iniciando a criação da planilha");
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatório de Vendas");
        
        List<Folha> folhaEntradaList = folhaRepository.findAll();
        
        log.info("Configurando o cabeçalho da planilha");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID do Pedido");
        headerRow.createCell(1).setCellValue("Lista de Itens");
        headerRow.createCell(2).setCellValue("Nome do Consultor");
        headerRow.createCell(3).setCellValue("Data da Compra");
        headerRow.createCell(4).setCellValue("Valor Total");
        
        CreationHelper createHelper = workbook.getCreationHelper();
        
        //Estilo para quebrar linha
        CellStyle wrappedStyle = workbook.createCellStyle();
        wrappedStyle.setWrapText(true);
        
        // Estilo de data
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        
        // Estilo de moeda
        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.setDataFormat(createHelper.createDataFormat().getFormat("R$#,##0.00"));
        
        log.info("Quantidade de linhas da planilha: {}", folhaEntradaList.size() );
        int rowNum = 1;
        for (Folha folha : folhaEntradaList) {
            Row row = sheet.createRow(rowNum++);
            
            row.createCell(0).setCellValue(folha.getFoco());
            
            // sublista
            List<Prioridade> prioridades = folha.getPrioridadeList();
            String prioridadesStr = prioridades.stream()
                    .map(Prioridade::getDescricao)
                    .collect(Collectors.joining("\n"));
            
            row.createCell(1).setCellValue(prioridadesStr);
            row.getCell(1).setCellStyle(wrappedStyle);
            row.createCell(2).setCellValue(folha.getObservacao());
            row.createCell(3).setCellValue(convertToDateViaInstant(folha.getDtarealizacao()));
            row.getCell(3).setCellStyle(dateStyle);
            row.createCell(4).setCellValue(folha.getNotadia());
            row.getCell(4).setCellStyle(currencyStyle);
        }
        
        log.info("Auto ajuste das colunas" );
        for (int i = 0; i <= 4; i++) {
            sheet.autoSizeColumn(i);
        }
        
        
        try  {
            String file = "src/main/resources/relatorio_de_vendas.xls";
            FileOutputStream fileOut = new FileOutputStream(file);
            log.info("gravando o arquivo no caminho: {}", file );
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
    
}
