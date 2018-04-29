package com.java.spring.serviceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.springframework.context.annotation.Bean;

import com.java.spring.output.FileDetails;
import com.java.spring.service.GetFileDetails;
import com.java.spring.utils.Constants;

public class GetFileDetailsImpl implements GetFileDetails{
	/*
	 * This is the implementation method to retrieve the details of the file like
	 * name of the file, size, type and extension
	 */
	@Bean
	public Object[] RetrieveFileDetails(String path) {
		Object[] fileDetails = new Object[2];
		File[] files = new File(path).listFiles();
		FileDetails details;
		List<FileDetails> detList = null;
		List<String> fileNames = new ArrayList<String>();
		try {
			if(null!=files){
				detList = new ArrayList<FileDetails>();
				for (File file : files) {
					details = new FileDetails();
					if(file.isFile()) {
						// retrieve the file name excluding the extension
						details.setFileName(file.getName().substring(0, file.getName().lastIndexOf(Constants.DOT_DELIMITER)));
						System.out.println("File Name: " + details.getFileName());

						//retrieving the size of the file in bytes
						details.setFileSize(file.length());
						System.out.println("File Size: " + details.getFileSize() + " bytes");

						// retrieving the type of the file if it is text/plain or application/xlsx
						details.setFileMimeType(new MimetypesFileTypeMap().getContentType(file));
						System.out.println("Mime Type: " + details.getFileMimeType());

						// retrieving the extension of the file like pdf or docx or xls
						details.setFileExtension(file.getName().substring(file.getName().lastIndexOf(".") + 1));
						System.out.println("File extension: "+details.getFileExtension());
						detList.add(details);
						RetrieveSpecificCSVFileType(file,fileNames);
					}
				}
				System.out.println("Size of the list retrieved from directory: "+detList.size());
				System.out.println("Number of CSV files retrieved: "+fileNames.size());
				fileDetails[0] = detList;
				fileDetails[1] = fileNames;
			}
		}
		catch(Exception ex) {
			System.out.println("Exception in retrieveing file details: "+ex.getMessage());
			ex.printStackTrace();
		}
		return fileDetails;		
	}

	/*
	 * This method is used to retrieve the files having csv format from the directory
	 * 
	 */
	private void RetrieveSpecificCSVFileType(File file,List<String> fileNames) {
		if(file.getName().contains(Constants.CSV_FORMAT)) {
			fileNames.add(file.getName());
		}
	}
}
