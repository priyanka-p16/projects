package com.java.spring.main;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.java.spring.output.FileDetails;
import com.java.spring.service.GetFileDetails;
import com.java.spring.test.InvokeWebPageTest;
import com.java.spring.utils.Constants;

public class GetServiceBean {
	/*
	 * This is the method to execute the application and invoke the webpage
	 */
	@SuppressWarnings({ "resource", "unchecked" })
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(Constants.XML_PATH);
		GetFileDetails getDetails = (GetFileDetails) context.getBean(Constants.FILE_DETAILS_BEAN);
		Object[] fileDetails = (Object[]) getDetails.RetrieveFileDetails(Constants.DIRECTORY_PATH);
		List<FileDetails> filesList = (List<FileDetails>) fileDetails[0];
		System.out.println("Number of files retrieved: "+filesList.size());
		List<String> csvFiles = (List<String>) fileDetails[1];
		InvokeWebPageTest test = new InvokeWebPageTest();
		test.invokeWebPage(csvFiles);
	}	
}
