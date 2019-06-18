package edu.handong.csee.java;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import edu.handong.csee.java.ExcelErrorException;

public class ZipReader {

	public void run(String[] args) {	
		String path = args[0];
		File dirFile=new File(path);
		File[] fileList=dirFile.listFiles();
		ExcelWriter result1 = new ExcelWriter();
		ExcelWriter result2 = new ExcelWriter();
		ExcelErrorException errors = new ExcelErrorException();
		boolean first = false, second = true;
		
		Runnable task1 = () -> { // Using thread #1
			for(File tempFile : fileList) {
				if(tempFile.isFile()) {
					String tempPath=tempFile.getParent();
				    String tempFileName=tempFile.getName();
				    
				    String fileName = tempFileName.split("[.]")[0];
				    
				    readFileInZip(tempPath + "\\" + tempFileName, result1, first, fileName);    
				  }
				}
			result1.saveFile(args[1] + "\\result1.xlsx");
			if(!errors.empty())
				errors.writeAFile(args[1] + "\\error.csv");
		};
		Runnable task2 = () -> { // Using thread #2
			for(File tempFile : fileList) {
				  if(tempFile.isFile()) {
				    String tempPath=tempFile.getParent();
				    String tempFileName=tempFile.getName();
				    
				    String fileName = tempFileName.split("[.]")[0];
				    
				    readFileInZip(tempPath + "\\" + tempFileName, result2, second, fileName, errors);    
				  }
			}
			result2.saveFile(args[1] + "\\result2.xlsx");
			if(!errors.empty())
				errors.writeAFile(args[1] + "\\error.csv");
		};
		Thread t1 = new Thread(task1);
		Thread t2 = new Thread(task2);
		t1.start();
		t2.start();
	}

	
	
	public void readFileInZip(String path, ExcelWriter result, boolean second, String fileName) {
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(path); // 전달된 주소에 있는 파일을 ZipFile클래스로써 생성. // 여기서 EXCEPTION 발생 가능
			ExcelReader myReader = new ExcelReader();
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries(); // 그 zip파일 안에있는 파일들의 리스트를 entries에 저장.
			ZipArchiveEntry entry = entries.nextElement(); // zip파일 내의 파일들을 순서대로.
			InputStream stream = zipFile.getInputStream(entry); // input 스트림 생성.	
		    
		    if(!second) {
			    result.setFirstData(myReader.getFirstData(stream), fileName); // result1 엑셀파일에 저장.
		    }else {
			    entry = entries.nextElement(); // 두번째 액셀 파일
			    stream = zipFile.getInputStream(entry); // input 스트림 생성
			    result.setSecondData(myReader.getSecondData(stream), fileName); // result2 엑셀파일에 저장.	    	
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}


/*
for(String value:myReader.getFirstData(stream)) { // 그 input stream을 getDate()메소드로 전달해, arrayList를 리턴받아 그것을 하나씩 출력한다. 
    System.out.println(value);
}
*/
/*
for(String value:myReader.getSecondData(stream)) { // 그 input stream을 getDate()메소드로 전달해, arrayList를 리턴받아 그것을 하나씩 출력한다. 
    System.out.println(value);
}
*/
/*
for(File tempFile : fileList) {
  if(tempFile.isFile()) {
    String tempPath=tempFile.getParent();
    String tempFileName=tempFile.getName();
    
    String fileName = tempFileName.split("[.]")[0];
    
    readFileInZip(tempPath + "\\" + tempFileName, result1, result2, fileName, errors);    
  }
}
*/