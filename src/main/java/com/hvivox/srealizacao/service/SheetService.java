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

        folha.setPriorityList(copyAndSetSheet(folha.getPriorityList(), folha, Priority::new));
        folha.setRestrictionList(copyAndSetSheet(folha.getRestrictionList(), folha, Restriction::new));
        folha.setGratitudeList(copyAndSetSheet(folha.getGratitudeList(), folha, Gratitude::new));
        folha.setLearningList(copyAndSetSheet(folha.getLearningList(), folha, Learning::new));

        return folhaRepository.save(folha);
    }
    //Metodo generico para SALVAR as listas de prioridades, restrições, gratidões e aprendizagens
    private <T extends ISheetAssociation> List<T> copyAndSetSheet(List<T> originalList, Sheet folha, Supplier<T> constructor) {
        List<T> newList = new ArrayList<>();
        for (T item : originalList) {
            T newItem = constructor.get();
            BeanUtils.copyProperties(item, newItem);
            newItem.setSheet(folha);
            newList.add(newItem);
        }
        return newList;
    }


    @Transactional
    public Sheet update(SheetDto folhaInputDto, Integer idFolha) {
        Sheet folhaInput = new Sheet();
        BeanUtils.copyProperties(folhaInputDto, folhaInput);

        Sheet folhaEncontrada = findOrFail(idFolha);
        updateSheetDetails(folhaEncontrada, folhaInput);

        Sheet folhaObjectID = new Sheet();
        folhaObjectID.setId(folhaEncontrada.getId());
        deleteAllFromSheet(folhaEncontrada.getId());

        updateSheetLists(folhaInput, folhaEncontrada, folhaObjectID);

        return folhaRepository.save(folhaEncontrada);
    }

    private void updateSheetDetails(Sheet folhaEncontrada, Sheet folhaInput) {
        folhaEncontrada.setFocus(folhaInput.getFocus());
        folhaEncontrada.setRealizationDate(folhaInput.getRealizationDate());
        folhaEncontrada.setDayNote(folhaInput.getDayNote());
        folhaEncontrada.setObservation(folhaInput.getObservation());
        folhaEncontrada.setStatus(folhaInput.getStatus());
    }

    private void updateSheetLists(Sheet folhaInput, Sheet folhaEncontrada, Sheet folhaObjectID) {
        updateList(folhaInput.getPriorityList(), folhaEncontrada.getPriorityList(), folhaObjectID, Priority::new);
        updateList(folhaInput.getRestrictionList(), folhaEncontrada.getRestrictionList(), folhaObjectID, Restriction::new);
        updateList(folhaInput.getGratitudeList(), folhaEncontrada.getGratitudeList(), folhaObjectID, Gratitude::new);
        updateList(folhaInput.getLearningList(), folhaEncontrada.getLearningList(), folhaObjectID, Learning::new);
    }

    //Metodo generico para atualizar as listas de prioridades, restrições, gratidões e aprendizagens
    private <T extends ISheetAssociation > void updateList(List<T> inputList, List<T> foundList, Sheet folhaObjectID, Supplier<T> constructor) {
        if (inputList != null && !inputList.isEmpty()) {
            for (T item : inputList) {
                T newItem = constructor.get();
                BeanUtils.copyProperties(item, newItem, "folha");
                newItem.setSheet(folhaObjectID);
                foundList.add(newItem);
            }
        }
    }


    @Transactional
    public void deleteAllFromSheet(Integer idFolha) {
        Sheet folha = findOrFail(idFolha);
        prioridadeService.deleteAllFromSheet(folha.getId());
        restricaoService.deleteAllFromSheet(folha.getId());
        gratidaoService.deleteAllFromSheet(folha.getId());
        aprendizagemService.deleteAllFromSheet(folha.getId());
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
