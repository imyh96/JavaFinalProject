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
    		// ù ��° ��
            row = sheet.createRow(rowCnt);
            // ù ��° �ٿ� Cell �����ϱ�-------------
            row.createCell(0).setCellValue("�л� ��ȣ");       
            row.createCell(1).setCellValue("����");
            row.createCell(2).setCellValue("��๮ (300�� ����)");
            row.createCell(3).setCellValue("�ٽɾ�\r\n" + "(keyword,��ǥ�� ����)");
            row.createCell(4).setCellValue("��ȸ��¥");
            row.createCell(5).setCellValue("�����ڷ���ȸ\r\n" + "��ó (���ڷḵũ)");
            row.createCell(6).setCellValue("����ó (����� ��)");
            row.createCell(7).setCellValue("������\r\n" + "(Copyright ����ó)");
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
    		// ù ��° ��
            row = sheet.createRow(rowCnt);
            // ù ��° �ٿ� Cell �����ϱ�-------------
            row.createCell(0).setCellValue("�л� ��ȣ");       
            row.createCell(1).setCellValue("����(�ݵ�� ��๮ ��Ŀ� �Է��� ����� ���ƾ� ��.)");
            row.createCell(2).setCellValue("ǥ/�׸� �Ϸù�ȣ");
            row.createCell(3).setCellValue("�ڷ�����(ǥ,�׸�,��)");
            row.createCell(4).setCellValue("�ڷῡ ���� ǥ�� �׸� ����(ĸ��)");
            row.createCell(5).setCellValue("�ڷᰡ ���� �ʹ�ȣ");
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
