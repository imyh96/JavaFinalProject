package edu.handong.csee.java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.CellType;

import edu.handong.csee.java.ExcelErrorException;

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
	
	public ArrayList<String> getFirstData(InputStream is, String fileName, ExcelErrorException errors) { // inputStream을 인자로 전달받는 getData 메소드
		ArrayList<String> values = new ArrayList<String>(); // Using generic data structure ArrayList #1
		
		try (InputStream inp = is) { // zip파일 내부에 들어있는 파일의 input 스트림
		        Workbook wb = WorkbookFactory.create(inp); // 그 스트림의 workbook생성
		        Sheet sheet = wb.getSheetAt(0); // 그 workbook의 0번째 시트
		        
		        for (Row row : sheet) { // 모든 셀들을 확인하기.
		        	
		        	if(row.getLastCellNum() != 7) {
						throw errors; // Customized Exception
					}
		        	if(row == sheet.getRow(0))
		        		continue;
		        	
		            for (Cell cell : row) {           	         
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
			}catch (ExcelErrorException e) {
				e.saveErrorFile(fileName);
			}
		
		return values; // 그 arrayList를 리턴
	}
	
	public ArrayList<String> getSecondData(InputStream is, String fileName, ExcelErrorException errors) { // inputStream을 인자로 전달받는 getData 메소드
		ArrayList<String> values = new ArrayList<String>(); // Using generic data structure ArrayList #2
		
		try (InputStream inp = is) { // zip파일 내부에 들어있는 파일의 input 스트림
		        Workbook wb = WorkbookFactory.create(inp); // 그 스트림의 workbook생성
		        wb.setMissingCellPolicy(MissingCellPolicy.CREATE_NULL_AS_BLANK);
		        Sheet sheet = wb.getSheetAt(0); // 그 workbook의 0번째 시트
		        
		        for (int i = 0; i <= sheet.getLastRowNum(); i++) { // 모든 셀들을 확인하기.
		        	Row row = sheet.getRow(i);
		        	/*
		        	if(row.getLastCellNum() != 4) {
						throw errors; // Customized Exception
					}
					*/
		        	if(i == 0 || i == 1) // 첫번째 row나 두번째 row일경우 스킵.
		        		continue;
		            for (int j = 0; j < row.getLastCellNum(); j++) {
		            	Cell cell = row.getCell(j);	
		            	
		            	if (cell == null)
				            cell = row.createCell(3);
				        if(cell.getCellType() == CellType.NUMERIC)
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
			}/* catch (ExcelErrorException e) {
				e.saveErrorFile(fileName);
			}*/
		
		return values; // 그 arrayList를 리턴
	}
}

/*
 
 for (int i = 0; i <= sheet.getLastRowNum(); i++) { // 모든 셀들을 확인하기.
		        	Row row = sheet.getRow(i);
		        	if(i == 0 || i == 1) // 첫번째 row나 두번째 row일경우 스킵.
		        		continue;
		            for (int j = 0; j <= row.getLastCellNum(); j++) {
		            	Cell cell = row.getCell(j);
 
 for (Row row : sheet) { // 모든 셀들을 확인하기.
		        	if(row == sheet.getRow(0) || row == sheet.getRow(1)) // 첫번째 row나 두번째 row일경우 스킵.
		        		continue;
		            for (Cell cell : row) {	       		            	         
		            	if (cell == null)
				            cell = row.createCell(3);
				        else if(cell.getCellType() == CellType.NUMERIC)
				        	values.add(Double.toString(cell.getNumericCellValue()));
				        else
				        	values.add(cell.getStringCellValue()); // 그 셀의 내용을 string으로써 arrayList에 저장.	            		     
		            }
		        }
*/
