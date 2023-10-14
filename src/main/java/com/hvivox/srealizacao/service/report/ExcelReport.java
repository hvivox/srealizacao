package com.hvivox.srealizacao.service.report;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class ExcelReport {
    
    public static void main(String[] args) {
        
        Workbook workbook = new HSSFWorkbook();  // Cria um workbook para XLS
        Sheet sheet = workbook.createSheet("Relatório de Vendas"); // Cria uma planilha
        
        // Criar a linha do cabeçalho e configurar os cabeçalhos
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID do Pedido");
        headerRow.createCell(1).setCellValue("Lista de Itens");
        headerRow.createCell(2).setCellValue("Nome do Consultor");
        headerRow.createCell(3).setCellValue("Data da Compra");
        headerRow.createCell(4).setCellValue("Valor Total");
        
        // Exemplo de dados para inserir
        int orderId = 1;
        String itemsList = "Item1, Item2";
        String consultantName = "João";
        Date purchaseDate = new Date();
        double totalValue = 300.50;
        
        // Criar uma linha de dados
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(orderId);
        dataRow.createCell(1).setCellValue(itemsList);
        dataRow.createCell(2).setCellValue(consultantName);
        dataRow.createCell(3).setCellValue(purchaseDate.toString());
        dataRow.createCell(4).setCellValue(totalValue);
        
        // Auto-size columns
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }
        
        // Escrever o arquivo
        try (FileOutputStream fileOut = new FileOutputStream("relatorio_de_vendas.xls")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Fechar o workbook
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}