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
			zipFile = new ZipFile(path); // ���޵� �ּҿ� �ִ� ������ ZipFileŬ�����ν� ����. // ���⼭ EXCEPTION �߻� ����
			ExcelReader myReader = new ExcelReader();
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries(); // �� zip���� �ȿ��ִ� ���ϵ��� ����Ʈ�� entries�� ����.
			ZipArchiveEntry entry = entries.nextElement(); // zip���� ���� ���ϵ��� �������.
			InputStream stream = zipFile.getInputStream(entry); // input ��Ʈ�� ����.	
		    
		    if(!second) {
			    result.setFirstData(myReader.getFirstData(stream), fileName); // result1 �������Ͽ� ����.
		    }else {
			    entry = entries.nextElement(); // �ι�° �׼� ����
			    stream = zipFile.getInputStream(entry); // input ��Ʈ�� ����
			    result.setSecondData(myReader.getSecondData(stream), fileName); // result2 �������Ͽ� ����.	    	
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}


/*
for(String value:myReader.getFirstData(stream)) { // �� input stream�� getDate()�޼ҵ�� ������, arrayList�� ���Ϲ޾� �װ��� �ϳ��� ����Ѵ�. 
    System.out.println(value);
}
*/
/*
for(String value:myReader.getSecondData(stream)) { // �� input stream�� getDate()�޼ҵ�� ������, arrayList�� ���Ϲ޾� �װ��� �ϳ��� ����Ѵ�. 
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