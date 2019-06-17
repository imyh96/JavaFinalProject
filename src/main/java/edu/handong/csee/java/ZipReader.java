package edu.handong.csee.java;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public class ZipReader {

	public void run(String[] args) {
		
		String path = args[0]; // �ϴ� data������ �ּҰ� ���޵Ǿ��ٰ� ����.
		
		File dirFile=new File(path);
		File[] fileList=dirFile.listFiles();
		
		for(File tempFile : fileList) {
		  if(tempFile.isFile()) {
		    String tempPath=tempFile.getParent();
		    String tempFileName=tempFile.getName();
		    
		    readFileInZip(tempPath + "\\" + tempFileName);
		    
		    //System.out.println("Path="+tempPath);
		    //System.out.println("FileName="+tempFileName);
		  }
		}
		
		//readFileInZip("C:\\Users\\YOHAN\\Desktop\\data\\0001.zip"); // run�� �������ڸ� data������ �� ��ü�� �������� �ϱ�.(args 0~4)
	}

	public void readFileInZip(String path) {
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(path); // ���޵� �ּҿ� �ִ� ������ ZipFileŬ�����ν� ����.
			
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries(); // �� zip���� �ȿ��ִ� ���ϵ��� ����Ʈ�� entries�� ����.
			/*
		    while(entries.hasMoreElements()){ // entries�� ���̻� ������ ���� �� ����,
		    */
		    	ZipArchiveEntry entry = entries.nextElement(); // zip���� ���� ���ϵ��� �������.
		        InputStream stream = zipFile.getInputStream(entry); // input ��Ʈ�� ����.
		    
		        ExcelReader myReader = new ExcelReader();
		        
		        for(String value:myReader.getFirstData(stream)) { // �� input stream�� getDate()�޼ҵ�� ������, arrayList�� ���Ϲ޾� �װ��� �ϳ��� ����Ѵ�. 
		        	System.out.println(value);
		        }
		    /*    
		    }
		    */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
