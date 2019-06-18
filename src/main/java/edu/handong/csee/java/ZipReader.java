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
		
		/*
		Runnable task1 = () -> {
			for(File tempFile : fileList) {
				if(tempFile.isFile()) {
					String tempPath=tempFile.getParent();
				    String tempFileName=tempFile.getName();
				    
				    String fileName = tempFileName.split("[.]")[0];
				    
				    readFirstFileInZip(tempPath + "\\" + tempFileName, result1, result2, fileName, errors);    
				  }
				}
		};
		
		Runnable task2 = () -> {
			for(File tempFile : fileList) {
				  if(tempFile.isFile()) {
				    String tempPath=tempFile.getParent();
				    String tempFileName=tempFile.getName();
				    
				    String fileName = tempFileName.split("[.]")[0];
				    
				    readSecondFileInZip(tempPath + "\\" + tempFileName, result1, result2, fileName, errors);    
				  }
				}
		};
		
		Thread t1 = new Thread(task1);
		Thread t2 = new Thread(task2);
		t1.start();
		t2.start();
		*/
		
		
		for(File tempFile : fileList) {
		  if(tempFile.isFile()) {
		    String tempPath=tempFile.getParent();
		    String tempFileName=tempFile.getName();
		    
		    String fileName = tempFileName.split("[.]")[0];
		    
		    readFileInZip(tempPath + "\\" + tempFileName, result1, result2, fileName, errors);    
		  }
		}
		
		result1.saveFile(args[1] + "\\result1.csv");
		result2.saveFile(args[1] + "\\result2.csv");
	
		if(!errors.empty())
			errors.writeAFile(args[1] + "\\error.csv");
	
		
	}

	
	public void readFileInZip(String path, ExcelWriter result1, ExcelWriter result2, String fileName, ExcelErrorException errors) {
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(path); // ���޵� �ּҿ� �ִ� ������ ZipFileŬ�����ν� ����. // ���⼭ EXCEPTION �߻� ����
			
			ExcelReader myReader = new ExcelReader();
						
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries(); // �� zip���� �ȿ��ִ� ���ϵ��� ����Ʈ�� entries�� ����.
			
		    ZipArchiveEntry entry = entries.nextElement(); // zip���� ���� ���ϵ��� �������.
		    InputStream stream = zipFile.getInputStream(entry); // input ��Ʈ�� ����.	
		    if(stream == null) {
				throw errors; // Customized Exception
			}
		    
		    result1.setFirstData(myReader.getFirstData(stream), fileName); // result1 �������Ͽ� ����.
		   
		    
		    entry = entries.nextElement(); // �ι�° �׼� ����
		    if(entry == null) {
				throw errors; // Customized Exception
			}
		    stream = zipFile.getInputStream(entry); // input ��Ʈ�� ����
		    
		    /*
		    for(String value:myReader.getSecondData(stream)) { // �� input stream�� getDate()�޼ҵ�� ������, arrayList�� ���Ϲ޾� �װ��� �ϳ��� ����Ѵ�. 
		        System.out.println(value);
		    }
		    */
		    
		    result2.setSecondData(myReader.getSecondData(stream), fileName); // result2 �������Ͽ� ����.
		    
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (ExcelErrorException e) {
			e.saveErrorFile(fileName);
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

*/