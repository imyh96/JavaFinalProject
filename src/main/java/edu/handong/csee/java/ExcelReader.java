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
	
	public ArrayList<String> getData(String path, String tempFileName) { // String path�� ���ڷ� ���޹޴� getData �޼ҵ�
		ArrayList<String> values = new ArrayList<String>(); 
		
		System.out.println(path);
		
		try (InputStream inp = new FileInputStream(path)) { // StringŸ���� �ּҰ��� inputstream���� ��ȯ
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
	
	public ArrayList<String> getFirstData(InputStream is, String fileName, ExcelErrorException errors) { // inputStream�� ���ڷ� ���޹޴� getData �޼ҵ�
		ArrayList<String> values = new ArrayList<String>(); // Using generic data structure ArrayList #1
		
		try (InputStream inp = is) { // zip���� ���ο� ����ִ� ������ input ��Ʈ��
		        Workbook wb = WorkbookFactory.create(inp); // �� ��Ʈ���� workbook����
		        Sheet sheet = wb.getSheetAt(0); // �� workbook�� 0��° ��Ʈ
		        
		        for (Row row : sheet) { // ��� ������ Ȯ���ϱ�.
		        	
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
				        	values.add(cell.getStringCellValue()); // �� ���� ������ string���ν� arrayList�� ����.	            		     
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
		
		return values; // �� arrayList�� ����
	}
	
	public ArrayList<String> getSecondData(InputStream is, String fileName, ExcelErrorException errors) { // inputStream�� ���ڷ� ���޹޴� getData �޼ҵ�
		ArrayList<String> values = new ArrayList<String>(); // Using generic data structure ArrayList #2
		
		try (InputStream inp = is) { // zip���� ���ο� ����ִ� ������ input ��Ʈ��
		        Workbook wb = WorkbookFactory.create(inp); // �� ��Ʈ���� workbook����
		        wb.setMissingCellPolicy(MissingCellPolicy.CREATE_NULL_AS_BLANK);
		        Sheet sheet = wb.getSheetAt(0); // �� workbook�� 0��° ��Ʈ
		        
		        for (int i = 0; i <= sheet.getLastRowNum(); i++) { // ��� ������ Ȯ���ϱ�.
		        	Row row = sheet.getRow(i);
		        	/*
		        	if(row.getLastCellNum() != 4) {
						throw errors; // Customized Exception
					}
					*/
		        	if(i == 0 || i == 1) // ù��° row�� �ι�° row�ϰ�� ��ŵ.
		        		continue;
		            for (int j = 0; j < row.getLastCellNum(); j++) {
		            	Cell cell = row.getCell(j);	
		            	
		            	if (cell == null)
				            cell = row.createCell(3);
				        if(cell.getCellType() == CellType.NUMERIC)
				        	values.add(Double.toString(cell.getNumericCellValue()));
				        else
				        	values.add(cell.getStringCellValue()); // �� ���� ������ string���ν� arrayList�� ����.	            		     
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
		
		return values; // �� arrayList�� ����
	}
}

/*
 
 for (int i = 0; i <= sheet.getLastRowNum(); i++) { // ��� ������ Ȯ���ϱ�.
		        	Row row = sheet.getRow(i);
		        	if(i == 0 || i == 1) // ù��° row�� �ι�° row�ϰ�� ��ŵ.
		        		continue;
		            for (int j = 0; j <= row.getLastCellNum(); j++) {
		            	Cell cell = row.getCell(j);
 
 for (Row row : sheet) { // ��� ������ Ȯ���ϱ�.
		        	if(row == sheet.getRow(0) || row == sheet.getRow(1)) // ù��° row�� �ι�° row�ϰ�� ��ŵ.
		        		continue;
		            for (Cell cell : row) {	       		            	         
		            	if (cell == null)
				            cell = row.createCell(3);
				        else if(cell.getCellType() == CellType.NUMERIC)
				        	values.add(Double.toString(cell.getNumericCellValue()));
				        else
				        	values.add(cell.getStringCellValue()); // �� ���� ������ string���ν� arrayList�� ����.	            		     
		            }
		        }
*/
