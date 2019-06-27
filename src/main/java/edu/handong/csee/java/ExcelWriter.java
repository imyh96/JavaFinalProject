package edu.handong.csee.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
	
    private Workbook xlsxWb = new XSSFWorkbook();;
    private Sheet sheet = xlsxWb.createSheet();
    private Row row = null;
    private Cell cell = null;
    private int rowCnt = 0;
    private int cnt = 8;
    
    
    
    public void setFirstData(ArrayList<String> values, String fileName) {
    	if(rowCnt == 0) {
    		// 첫 번째 줄
            row = sheet.createRow(rowCnt);
            // 첫 번째 줄에 Cell 설정하기-------------
            row.createCell(0).setCellValue("학생 번호");       
            row.createCell(1).setCellValue("제목");
            row.createCell(2).setCellValue("요약문 (300자 내외)");
            row.createCell(3).setCellValue("핵심어\r\n" + "(keyword,쉼표로 구분)");
            row.createCell(4).setCellValue("조회날짜");
            row.createCell(5).setCellValue("실제자료조회\r\n" + "출처 (웹자료링크)");
            row.createCell(6).setCellValue("원출처 (기관명 등)");
            row.createCell(7).setCellValue("제작자\r\n" + "(Copyright 소유처)");
    	}
        cnt = 8;
        for(String value: values) {
        	if(cnt == 8) {
        		rowCnt++;
        		row = sheet.createRow(rowCnt);
        		row.createCell(0).setCellValue(fileName); 
        		cnt = 1;
        	}
        	row.createCell(cnt).setCellValue(value);
        	cnt++;
        }
    }
    
    
    
    public void setSecondData(ArrayList<String> values, String fileName) {
    	if(rowCnt == 0) {
    		// 첫 번째 줄
            row = sheet.createRow(rowCnt);
            // 첫 번째 줄에 Cell 설정하기-------------
            row.createCell(0).setCellValue("학생 번호");       
            row.createCell(1).setCellValue("제목(반드시 요약문 양식에 입력한 제목과 같아야 함.)");
            row.createCell(2).setCellValue("표/그림 일련번호");
            row.createCell(3).setCellValue("자료유형(표,그림,…)");
            row.createCell(4).setCellValue("자료에 나온 표나 그림 설명(캡션)");
            row.createCell(5).setCellValue("자료가 나온 쪽번호");
    	}
        cnt = 6;
        for(String value: values) {
        	if(cnt == 6) {
        		rowCnt++;
        		row = sheet.createRow(rowCnt);
        		row.createCell(0).setCellValue(fileName); 
        		cnt = 1;
        	}
        	row.createCell(cnt).setCellValue(value);
        	cnt++;
        }
    }
    
    
    public void saveFile(String saveAdress) {
    	try {
            File xlsxFile = new File(saveAdress);
            FileOutputStream fileOut = new FileOutputStream(xlsxFile);
            xlsxWb.write(fileOut);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            
        } catch (IOException e) {
            e.printStackTrace();
        }         
    }
    
}
