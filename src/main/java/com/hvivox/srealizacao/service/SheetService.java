package com.hvivox.srealizacao.service;
import com.hvivox.srealizacao.dto.SheetDto;
import com.hvivox.srealizacao.exception.EntityInUseException;
import com.hvivox.srealizacao.exception.SheetNotFoundException;
import com.hvivox.srealizacao.model.*;
import com.hvivox.srealizacao.model.Sheet;
import com.hvivox.srealizacao.repository.SheetRepository;
import jakarta.transaction.Transactional;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Log4j2
@Service
public class SheetService {

    private static final String MSG_SHEET_IN_USE = "Folha de código %d não pode ser removida, pois está em uso";

    private static final String MSG_SHEET_NOT_FOUND = "Não existe folha com cadastro de código %d";

    private static final String MSG_ENTITY_NOT_FOUND = "Entidade não encontrada";

    @Autowired
    SheetRepository sheetRepository;

    @Autowired
    private PriorityService priorityService;

    @Autowired
    private RestrictionService restrictionService;

    @Autowired
    private GratitudeService gratitudeService;

    @Autowired
    private LearningService learningService;


    public Page<Sheet> findAll(Specification<Sheet> spec, Pageable pageable) {
        return sheetRepository.findAll(spec, pageable);
    }


    public Sheet findOrFail(Integer idFolha) {
        return sheetRepository.findById(idFolha).orElseThrow(() -> new SheetNotFoundException(idFolha));
    }


    @Transactional
    public Sheet save(Sheet sheet) {
        log.debug("POST Salvar dados {} ", sheet.toString());

        sheet.setPriorityList(copyAndSetSheet(sheet.getPriorityList(), sheet, Priority::new));
        sheet.setRestrictionList(copyAndSetSheet(sheet.getRestrictionList(), sheet, Restriction::new));
        sheet.setGratitudeList(copyAndSetSheet(sheet.getGratitudeList(), sheet, Gratitude::new));
        sheet.setLearningList(copyAndSetSheet(sheet.getLearningList(), sheet, Learning::new));

        return sheetRepository.save(sheet);
    }
    //Metodo generico para SALVAR as listas de prioridades, restrições, gratidões e aprendizagens
    private <T extends ISheetAssociation> List<T> copyAndSetSheet(List<T> originalList, Sheet sheet, Supplier<T> constructor) {
        List<T> newList = new ArrayList<>();
        for (T item : originalList) {
            T newItem = constructor.get();
            BeanUtils.copyProperties(item, newItem);
            newItem.setSheet(sheet);
            newList.add(newItem);
        }
        return newList;
    }


    @Transactional
    public Sheet update(SheetDto SheetInputDto, Integer idSheet) {
        Sheet sheetInput = new Sheet();
        BeanUtils.copyProperties(SheetInputDto, sheetInput);

        Sheet sheetFound = findOrFail(idSheet);
        updateSheetDetails(sheetFound, sheetInput);

        Sheet sheetObjectID = new Sheet();
        sheetObjectID.setId(sheetFound.getId());
        deleteAllFromSheet(sheetFound.getId());

        updateSheetLists(sheetInput, sheetFound, sheetObjectID);

        return sheetRepository.save(sheetFound);
    }

    private void updateSheetDetails(Sheet sheetFound, Sheet sheetInput) {
        sheetFound.setFocus(sheetInput.getFocus());
        sheetFound.setRealizationDate(sheetInput.getRealizationDate());
        sheetFound.setDayNote(sheetInput.getDayNote());
        sheetFound.setObservation(sheetInput.getObservation());
        sheetFound.setStatus(sheetInput.getStatus());
    }

    private void updateSheetLists(Sheet sheetInput, Sheet sheetEncontrada, Sheet sheetObjectID) {
        updateList(sheetInput.getPriorityList(), sheetEncontrada.getPriorityList(), sheetObjectID, Priority::new);
        updateList(sheetInput.getRestrictionList(), sheetEncontrada.getRestrictionList(), sheetObjectID, Restriction::new);
        updateList(sheetInput.getGratitudeList(), sheetEncontrada.getGratitudeList(), sheetObjectID, Gratitude::new);
        updateList(sheetInput.getLearningList(), sheetEncontrada.getLearningList(), sheetObjectID, Learning::new);
    }

    //Metodo generico para atualizar as listas de prioridades, restrições, gratidões e aprendizagens
    private <T extends ISheetAssociation > void updateList(List<T> inputList, List<T> foundList, Sheet sheetObjectID, Supplier<T> constructor) {
        if (inputList != null && !inputList.isEmpty()) {
            for (T item : inputList) {
                T newItem = constructor.get();
                BeanUtils.copyProperties(item, newItem, "folha");
                newItem.setSheet(sheetObjectID);
                foundList.add(newItem);
            }
        }
    }


    @Transactional
    public void deleteAllFromSheet(Integer idSheet) {
        Sheet sheet = findOrFail(idSheet);
        priorityService.deleteAllFromSheet(sheet.getId());
        restrictionService.deleteAllFromSheet(sheet.getId());
        gratitudeService.deleteAllFromSheet(sheet.getId());
        learningService.deleteAllFromSheet(sheet.getId());
    }

    @Transactional
    public void delete(Integer idSheet) {

        try {
            sheetRepository.deleteById(idSheet);
        } catch (EmptyResultDataAccessException e) {
            throw new SheetNotFoundException(String.format(MSG_SHEET_NOT_FOUND, idSheet));
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_SHEET_IN_USE, idSheet));
        }
    }


    public ByteArrayOutputStream gerarExcelFolha() {
        log.info("Iniciando a criação da planilha");
        Workbook workbook = new HSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Relatório de Vendas");

        List<Sheet> folhaEntradaList = sheetRepository.findAll();

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
        sheetRepository.save(folhaEncontrada);
    }
}
