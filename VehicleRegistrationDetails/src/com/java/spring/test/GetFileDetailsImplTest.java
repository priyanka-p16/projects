package com.java.spring.test;

import java.util.List;

import org.junit.Test;

import com.java.spring.output.FileDetails;
import com.java.spring.serviceImpl.GetFileDetailsImpl;
import com.java.spring.utils.Constants;

import junit.framework.Assert;
/*
 * This class is used to test the methods in GetFileDetailsImpl class
 * 
 */
@SuppressWarnings("deprecation")
public class GetFileDetailsImplTest {
	@Test
	public void retrieveFileDetails_testSuccess()
	{
		String path = Constants.DIRECTORY_PATH;
		GetFileDetailsImpl getFileDetailsImpl = new GetFileDetailsImpl();
		Object[] files = getFileDetailsImpl.RetrieveFileDetails(path);
		List<FileDetails> filesList = (List<FileDetails>) files[0];
		List<String> csvFiles = (List<String>) files[1];
		Assert.assertEquals(14,filesList.size());	
		Assert.assertEquals(1,csvFiles.size());		
		TestElements(filesList);
	}
	
	/*
	 * Method to test the retrieved file details
	 */
	private static void TestElements(List<FileDetails> filesList) {
		FileDetails[] details = new FileDetails[filesList.size()];
		filesList.toArray(details);
		Assert.assertEquals(Constants.PNG_FILE_EXTN,details[0].getFileExtension());
		Assert.assertEquals(11548, details[8].getFileSize());
		Assert.assertEquals("text/plain",details[4].getFileMimeType());
		Assert.assertEquals("main",details[9].getFileName());
	}
}
