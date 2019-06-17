package edu.handong.csee.java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.CellType;

public class ExcelReader {
	
	public ArrayList<String> getData(String path, String tempFileName) { // String path를 인자로 전달받는 getData 메소드
		ArrayList<String> values = new ArrayList<String>();
		
		System.out.println(path);
		
		try (InputStream inp = new FileInputStream(path)) { // String타입의 주소값을 inputstream으로 변환
		    //InputStream inp = new FileInputStream("workbook.xlsx");
		    
		        Workbook wb = WorkbookFactory.create(inp);
		        Sheet sheet = wb.getSheetAt(0);
		        Row row = sheet.getRow(2);
		        Cell cell = row.getCell(1);
		        
		        if (cell == null)
		            cell = row.createCell(3);
		        else if(cell.getCellType() == CellType.NUMERIC)
		        	values.add(Double.toString(cell.getNumericCellValue()));
		        else
		        	values.add(cell.getStringCellValue());
		        
		    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return values;
	}
	
	public ArrayList<String> getFirstData(InputStream is) { // inputStream을 인자로 전달받는 getData 메소드
		ArrayList<String> values = new ArrayList<String>();
		
		try (InputStream inp = is) { // zip파일 내부에 들어있는 파일의 input 스트림
		    //InputStream inp = new FileInputStream("workbook.xlsx");
		    
		        Workbook wb = WorkbookFactory.create(inp); // 그 스트림의 workbook생성
		        Sheet sheet = wb.getSheetAt(0); // 그 workbook의 0번째 시트
		        //int cnt = 1;
		        
		        //values.add(tempFileName);
		        
		        for (Row row : sheet) { // 모든 셀들을 확인하기.
		        	if(row == sheet.getRow(0))
		        		continue;
		            for (Cell cell : row) {
		            	/*
		            	cnt++;			          
		            	if(cnt%8 == 0)
		            		values.add(tempFileName);
		            	*/			            	         
		            	if (cell == null)
				            cell = row.createCell(3);
				        else if(cell.getCellType() == CellType.NUMERIC)
				        	values.add(Double.toString(cell.getNumericCellValue()));
				        else
				        	values.add(cell.getStringCellValue()); // 그 셀의 내용을 string으로써 arrayList에 저장.	            		     
		            }
		        }	     
		    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return values; // 그 arrayList를 리턴
	}
}
