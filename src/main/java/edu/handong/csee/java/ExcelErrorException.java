package edu.handong.csee.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ExcelErrorException extends Exception{
	
	private ArrayList<String> filenames = new ArrayList<String>();
	
	public void saveErrorFile(String filename) {
		filenames.add(filename);
	}
	
	public boolean empty() {
		return filenames.isEmpty();
	}
	
	public void writeAFile(String targetFileName) {
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(targetFileName);
		} catch(FileNotFoundException e) {
			System.out.println("Error opening the file" + targetFileName + ". Creating directory.");
			Path path = Paths.get(targetFileName);
			try {
				path = Files.createFile(path);
			} catch(IOException d) {
				System.out.println("Error creating file" + targetFileName); 
				System.exit(0);
			}
			try {
				outputStream = new PrintWriter(targetFileName);
			} catch(FileNotFoundException f) {
				System.out.println("Error opening the file" + targetFileName);
				System.exit(0);
			}
		}
		for(String names: filenames) {
			outputStream.println(names);
		}
		outputStream.close();
	}
}
