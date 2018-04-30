package com.java.spring.test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.java.spring.output.VehicleType;
import com.java.spring.serviceImpl.ReadVehicleDetails;
import com.java.spring.utils.Constants;

import junit.framework.Assert;

public class InvokeWebPageTest {
	
	WebDriver driver;
	
	// instance of singleton class
	public static InvokeWebPageTest instanceOfInvokeWebPageTestclass=null;
	
	// @FindBy annotation is used to fine web elements
	@FindBy(xpath="//*[@id=\"pr3\"]/div/ul/li[2]/span[2]/strong")
    WebElement model;
	
	@FindBy(xpath="//*[@id=\"pr3\"]/div/ul/li[3]/span[2]/strong")
	WebElement color;
	
	// Constructor
    public InvokeWebPageTest(){
    	System.setProperty("webdriver.chrome.driver",Constants.CHROME_DRIVER_PATH);
		driver= new ChromeDriver();
		PageFactory.initElements(driver, this); // initElements will initialize all web elements to prevent Null pointer Exception
    }
    
	// To create instance of this class
    public static InvokeWebPageTest getInstanceOfInvokeWebPageTestclass(){
        if(instanceOfInvokeWebPageTestclass==null){
        	instanceOfInvokeWebPageTestclass = new InvokeWebPageTest();
        }
        return instanceOfInvokeWebPageTestclass;
    }
    
    // Using Page Object Model design pattern
    By startNowBtn = By.className("pub-c-button");
    By vehicleRegNo = By.className("form-control");
    By continueBtn = By.className("button");
    By backLink = By.linkText("Back");
    
    
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
					this.pageLoad();
					driver.get(Constants.GOV_URL);
					driver.findElement(startNowBtn).click(); // Click on Start now button
					if(null!=vehType && vehType.length>=1) { // if there are more than one vehicle in the file						
						for(int i=0;i<vehType.length;i++) {
							Thread.sleep(900);// page wait - Note: Just for Demo
							driver.findElement(vehicleRegNo).sendKeys(vehType[i].getRegNo()); // Enter the vehicle registration number
							Thread.sleep(900);//  page wait - Note: Just for Demo
							driver.findElement(continueBtn).click(); // click on continue button
							String make = model.getText(); // make of the vehicle value in the webpage
							System.out.println("Vehicle Model from webpage: "+make);
							System.out.println("Vehicle Model from CSV file: "+vehType[i].getModel());
							Assert.assertEquals(make, vehType[i].getModel());
							String vehicleColor = color.getText(); // color of the vehicle value in the webpage
							System.out.println("Vehicle Color from webpage :"+vehicleColor);
							System.out.println("Vehicle Color from CSV file :"+vehType[i].getColor());
							Assert.assertEquals(vehicleColor, vehType[i].getColor());
							Thread.sleep(2000); // page wait - Note: Just for Demo
						    driver.findElement(backLink).click(); // click on back link to check for next vehicle details	
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

	/*
	 * Initial setup for the web page to load
	 */
	public void pageLoad()
	{
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize(); // Maximize the window
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // Timeouts for page loading
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.get(Constants.GOV_URL);
	}
}
