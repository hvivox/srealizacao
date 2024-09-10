package com.hvivox.srealizacao.service;
import com.hvivox.srealizacao.dto.SheetDto;
import com.hvivox.srealizacao.exception.EntityInUseException;
import com.hvivox.srealizacao.exception.SheetNotFoundException;
import com.hvivox.srealizacao.model.*;
import com.hvivox.srealizacao.model.Sheet;
import com.hvivox.srealizacao.repository.SheetRepository;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Log4j2
@Service
public class SheetService {

    private static final String MSG_FOLHA_EM_USO = "Folha de código %d não pode ser removida, pois está em uso";

    private static final String MSG_FOLHA_NAO_ENCONTRADA = "Não existe folha com cadastro de código %d";

    private static final String MSG_ENTIDADE_NAO_ENCONTRADA = "Entidade não encontrada";

    @Autowired
    SheetRepository folhaRepository;

    @Autowired
    private PriorityService prioridadeService;

    @Autowired
    private RestrictionService restricaoService;

    @Autowired
    private GratitudeService gratidaoService;

    @Autowired
    private LearningService aprendizagemService;


    public Page<Sheet> findAll(Specification<Sheet> spec, Pageable pageable) {
        return folhaRepository.findAll(spec, pageable);
    }


    public Sheet findOrFail(Integer idFolha) {
        return folhaRepository.findById(idFolha).orElseThrow(() -> new SheetNotFoundException(idFolha));
    }


    @Transactional
    public Sheet save(Sheet folha) {

        log.debug("POST Salvar dados {} ", folha.toString());
        List<Priority> prioridades = new ArrayList<>();
        List<Restriction> restricoes = new ArrayList<>();
        List<Gratitude> gratidoes = new ArrayList<>();
        List<Learning> aprendizagens = new ArrayList<>();

        for (Priority prioridadeEncontrada : folha.getPriorityList()) {
            Priority prioridade = new Priority();
            BeanUtils.copyProperties(prioridadeEncontrada, prioridade);
            prioridade.setSheet(folha);
            prioridades.add(prioridade);
        }

        for (Restriction restricaoEncontrada : folha.getRestrictionList()) {
            Restriction restricao = new Restriction();
            BeanUtils.copyProperties(restricaoEncontrada, restricao);
            restricao.setSheet(folha);
            restricoes.add(restricao);
        }

        for (Gratitude gratidaoEncontrada : folha.getGratitudeList()) {
            Gratitude gratidao = new Gratitude();
            BeanUtils.copyProperties(gratidaoEncontrada, gratidao);
            gratidao.setSheet(folha);
            gratidoes.add(gratidao);
        }

        for (Learning aprendizagemEncontrada : folha.getLearningList()) {
            Learning aprendizagem = new Learning();
            BeanUtils.copyProperties(aprendizagemEncontrada, aprendizagem);
            aprendizagem.setSheet(folha);
            aprendizagens.add(aprendizagem);
        }

        folha.setPriorityList(prioridades);
        folha.setRestrictionList(restricoes);
        folha.setGratitudeList(gratidoes);
        folha.setLearningList(aprendizagens);

        return folhaRepository.save(folha);
    }


    @Transactional
    public Sheet update(SheetDto folhaInputDto, Integer idFolha) {

        Sheet folhaInput = new Sheet();
        BeanUtils.copyProperties(folhaInputDto, folhaInput);

        Sheet folhaEncontrada = findOrFail(idFolha);
        folhaEncontrada.setFocus(folhaInput.getFocus());
        folhaEncontrada.setRealizationDate(folhaInput.getRealizationDate());
        folhaEncontrada.setDayNote(folhaInput.getDayNote());
        folhaEncontrada.setObservation(folhaInput.getObservation());
        folhaEncontrada.setStatus(folhaInput.getStatus());

        Sheet folhaObjectID = new Sheet();
        folhaObjectID.setId(folhaEncontrada.getId());

        prioridadeService.deleteAllFromSheet(folhaEncontrada.getId());
        if (folhaInput.getPriorityList() != null && !folhaInput.getPriorityList().isEmpty()) {

            for (Priority prioridadeEncontrada : folhaInput.getPriorityList()) {
                Priority prioridade = new Priority();
                BeanUtils.copyProperties(prioridadeEncontrada, prioridade, "folha");

                prioridade.setSheet(folhaObjectID);
                log.debug("atributo prioridade {}", prioridade);
                folhaEncontrada.getPriorityList().add(prioridade);
            }

        }

        restricaoService.deleteAllFromSheet(folhaEncontrada.getId());

        if (folhaInput.getRestrictionList() != null && !folhaInput.getRestrictionList().isEmpty()) {

            for (Restriction restricaoEncontrada : folhaInput.getRestrictionList()) {

                Restriction restricao = new Restriction();
                BeanUtils.copyProperties(restricaoEncontrada, restricao, "folha");
                restricao.setSheet(folhaObjectID);
                folhaEncontrada.getRestrictionList().add(restricao);
            }
        }

        gratidaoService.deleteAllFromSheet(folhaEncontrada.getId());
        if (folhaInput.getGratitudeList() != null && !folhaInput.getGratitudeList().isEmpty()) {
            for (Gratitude gratidaoEncontrada : folhaInput.getGratitudeList()) {
                Gratitude gratidao = new Gratitude();
                BeanUtils.copyProperties(gratidaoEncontrada, gratidao, "folha");
                gratidao.setSheet(folhaObjectID);
                folhaEncontrada.getGratitudeList().add(gratidao);
            }

        }


        aprendizagemService.deleteAllFromSheet(folhaEncontrada.getId());
        if (folhaInput.getLearningList() != null && !folhaInput.getLearningList().isEmpty()) {

            for (Learning aprendizagemEncontrada : folhaInput.getLearningList()) {
                Learning aprendizagem = new Learning();
                BeanUtils.copyProperties(aprendizagemEncontrada, aprendizagem, "folha");
                aprendizagem.setSheet(folhaObjectID);
                folhaEncontrada.getLearningList().add(aprendizagem);
            }

        }

        return folhaRepository.save(folhaEncontrada);

    }


    @Transactional
    public void delete(Integer idFolha) {

        try {
            folhaRepository.deleteById(idFolha);
        } catch (EmptyResultDataAccessException e) {
            throw new SheetNotFoundException(String.format(MSG_FOLHA_NAO_ENCONTRADA, idFolha));
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_FOLHA_EM_USO, idFolha));
        }
    }


    public ByteArrayOutputStream gerarExcelFolha() {
        log.info("Iniciando a criação da planilha");
        Workbook workbook = new HSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Relatório de Vendas");

        List<Sheet> folhaEntradaList = folhaRepository.findAll();

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
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        // Estilo de moeda
        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.setDataFormat(createHelper.createDataFormat().getFormat("R$#,##0.00"));

        log.info("Quantidade de linhas da planilha: {}", folhaEntradaList.size());
        int rowNum = 1;
        for (Sheet folha : folhaEntradaList) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(folha.getFocus());

            // sublista
            List<Priority> prioridades = folha.getPriorityList();
            String prioridadesStr = prioridades.stream().map(Priority::getDescription).collect(Collectors.joining("\n"
            ));

            row.createCell(1).setCellValue(prioridadesStr);
            row.getCell(1).setCellStyle(wrappedStyle);
            row.createCell(2).setCellValue(folha.getObservation());
            row.createCell(3).setCellValue(convertToDateViaInstant(folha.getRealizationDate()));
            row.getCell(3).setCellStyle(dateStyle);
            row.createCell(4).setCellValue(folha.getDayNote());
            row.getCell(4).setCellStyle(currencyStyle);
        }

        log.info("Auto ajuste das colunas");
        for (int i = 0; i <= 4; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {

            workbook.write(byteArrayOutputStream);
            workbook.close();


            /*String file = "src/main/resources/relatorio_de_vendas.xls";
            FileOutputStream fileOut = new FileOutputStream(file);
            log.info("gravando o arquivo no caminho: {}", file );
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream;
    }


    private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }


    public void inactiveSheet(Sheet folhaEncontrada) {
        folhaRepository.save(folhaEncontrada);
    }
}
