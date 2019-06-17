package edu.handong.csee.java;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public class ZipReader {

	public void run(String[] args) {
		
		String path = args[0]; // 일단 data폴더의 주소가 전달되었다고 가정.
		
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
		
		//readFileInZip("C:\\Users\\YOHAN\\Desktop\\data\\0001.zip"); // run의 전달인자를 data폴더의 각 개체의 제목으로 하기.(args 0~4)
	}

	public void readFileInZip(String path) {
		ZipFile zipFile;
		try {
			zipFile = new ZipFile(path); // 전달된 주소에 있는 파일을 ZipFile클래스로써 생성.
			
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries(); // 그 zip파일 안에있는 파일들의 리스트를 entries에 저장.
			/*
		    while(entries.hasMoreElements()){ // entries에 더이상 파일이 없을 때 까지,
		    */
		    	ZipArchiveEntry entry = entries.nextElement(); // zip파일 내의 파일들을 순서대로.
		        InputStream stream = zipFile.getInputStream(entry); // input 스트림 생성.
		    
		        ExcelReader myReader = new ExcelReader();
		        
		        for(String value:myReader.getFirstData(stream)) { // 그 input stream을 getDate()메소드로 전달해, arrayList를 리턴받아 그것을 하나씩 출력한다. 
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
