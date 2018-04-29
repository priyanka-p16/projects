package com.java.spring.test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.java.spring.output.VehicleType;
import com.java.spring.serviceImpl.ReadVehicleDetails;
import com.java.spring.utils.Constants;

import junit.framework.Assert;

public class InvokeWebPageTest {	
	/*
	 * Method used to read the csv file containing details and open the website to verify if the expected details are correct
	 * Also the method is used to check for more vehicle details from the page
	 */
	public void invokeWebPage(List<String> csvFile)
	{
		try {
			System.out.println("Invoking the webpage");
			VehicleType[] vehType = null;
			for(String fileName: csvFile)
			{
				if(fileName.equals(Constants.FILE_NAME)) {
					vehType = ReadVehicleDetails.ReadVehicleRegDetails(fileName);
					System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
					WebDriver driver = new ChromeDriver();
					driver.manage().deleteAllCookies();
					driver.manage().window().maximize(); // Maximize the window
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // Timeouts for page loading
					driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
					driver.get(Constants.GOV_URL);
					driver.findElement(By.className("pub-c-button")).click(); // Click on Start now button
					if(null!=vehType && vehType.length>=1) { // if there are more than one vehicle in the file
						for(int i=0;i<vehType.length;i++) {
							Thread.sleep(500);// page wait
							driver.findElement(By.className("form-control")).sendKeys(vehType[i].getRegNo()); // Enter the vehicle registration number
							Thread.sleep(500);// page wait
							driver.findElement(By.className("button")).click(); // click on continue button
							WebElement model = driver.findElement(By.xpath("//*[@id=\"pr3\"]/div/ul/li[2]/span[2]/strong")); // make of the vehicle value in the page
							String make = model.getText();
							System.out.println("Vehicle Model from webpage: "+make);
							System.out.println("Vehicle Model from CSV file: "+vehType[i].getModel());
							Assert.assertEquals(make, vehType[i].getModel());
							WebElement color = driver.findElement(By.xpath("//*[@id=\"pr3\"]/div/ul/li[3]/span[2]/strong")); // color of the vehicle value in the page
							String vehicleColor = color.getText();
							System.out.println("Vehicle Color from webpage :"+vehicleColor);
							System.out.println("Vehicle Color from CSV file :"+vehType[i].getColor());
							Assert.assertEquals(vehicleColor, vehType[i].getColor());
							Thread.sleep(900);
							//driver.findElement(By.className("back-to-previous")).click();	// click on back link to check for next vehicle details				
						    driver.findElement(By.linkText("Back")).click();
						}
					}
					driver.quit();
				}
			}
		} 
		catch (Exception e) {
			System.out.println("Exception in invoking the webpage: "+e.getMessage());
			e.printStackTrace();
		}
	}	

}
