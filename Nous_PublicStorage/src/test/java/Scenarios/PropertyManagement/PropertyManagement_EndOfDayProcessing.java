package Scenarios.PropertyManagement;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.PropertyManagementPages.EndOfDayProcessing;
import Pages.PropertyManagementPages.PropertyManagementPage;
import Scenarios.Browser_Factory;

public class PropertyManagement_EndOfDayProcessing extends Browser_Factory{
	
	public ExtentTest logger;
	String resultFlag="fail";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"PropertyManagement","PropertyManagement",  "PropertyManagement_EndOfDayProcessing");
	}



	@Test(dataProvider="getLoginData",priority=4)
	public void propertyManagement_EndOfDayProcessing(Hashtable<String, String> tabledata) throws InterruptedException 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("PropertyManagement").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Thread.sleep(5000);

		try
		{

			//Login to the application as PM 
			logger=extent.startTest("PropertyManagement_EndOfDayProcessing","Property Management End of day processing ");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "PM Logged in successfully");

			JavascriptExecutor jse = (JavascriptExecutor)driver;

			//connecting to customer device
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Reporter.log("object created successfully",true);
			Thread.sleep(5000);
			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T); 
			Thread.sleep(5000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(5000);
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_QA"));  

			List<WebElement> biforstSystem=driver.findElements(By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
			for(WebElement ele:biforstSystem)
			{
				if(biforstNum.equalsIgnoreCase(ele.getText().trim()))
				{
					Reporter.log(ele.getText()+"",true);
					ele.click();
					break;
				}
			}

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "clicked on continue successfully");


			// Login into PM dashboard 
			PM_Homepage pm_home= new  PM_Homepage(driver);
			logger.log(LogStatus.INFO, "PM Home page object created successfully");
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "property management page is displayed ",image);

			Thread.sleep(8000);
			pm_home.clickmanageProp();
			logger.log(LogStatus.INFO,"Clicked on Property Mgmt link");
			
			


			Thread.sleep(4000);
			PropertyManagementPage propmgmt= new PropertyManagementPage(driver);
			propmgmt.clickEODProcessing();
			logger.log(LogStatus.INFO,"Clicked on End of day processing link");
			Thread.sleep(5000);
			
			
			EndOfDayProcessing eod= new EndOfDayProcessing(driver);
			
			if(eod.verify_page_Title()){
			 scpath=Generic_Class.takeScreenShotPath();
			 image=logger.addScreenCapture(scpath);
			 logger.log(LogStatus.PASS,"End of day processing page is displayed ");
			logger.log(LogStatus.INFO, "End of day processing page is displayed ",image);
			}else{
				 scpath=Generic_Class.takeScreenShotPath();
				 image=logger.addScreenCapture(scpath);
				 logger.log(LogStatus.FAIL, "End of day processing Page is not displayed ");
					logger.log(LogStatus.INFO, "End of day processing Page  is not displayed ",image);
				
			}
		
			//verify the colums available
			if(eod.verify_paymentType() && eod.verify_transactions() && eod.verify_systemTotal() && eod.verify_Manualcount() && eod.verify_comments())
			{
				logger.log(LogStatus.PASS,"The columns available are: "+eod.get_paymentType()+ ", "+eod.get_transactions()+ " ' "+eod.get_systemTotal()+ ", "+eod.get_Manualcount()+ ", "+eod.get_comments()+ " are available");
			}
			
			else{
				logger.log(LogStatus.FAIL,"The columns are not available");
			}
			
			//verify the data present in the columns
			eod.data_PaymentType();
			logger.log(LogStatus.INFO,"The data in Payment type column is: "+eod.data_PaymentType());
			eod.data_Transactions();
			logger.log(LogStatus.INFO,"The data in Transactions column is: "+eod.data_Transactions());
			eod.data_SystemTotal();
			logger.log(LogStatus.INFO,"The data in System total column is: "+eod.data_SystemTotal());
			eod.data_ManualCount();
			logger.log(LogStatus.INFO,"The data in Manual column column is: "+eod.data_ManualCount());
			eod.data_Comments();
			logger.log(LogStatus.INFO,"The data in comments column is: "+eod.data_Comments());
			
			
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		}catch(Exception ex){
			ex.printStackTrace();
			//In the catch block, set the variable resultFlag to “fail”
			resultFlag="fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			//logger.log(LogStatus.FAIL, "Validating Monthly rent and Promotions in Eligible Promotion Page",image);
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
		}


	}


	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_EndOfDayProcessing" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_EndOfDayProcessing" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_EndOfDayProcessing" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);


	}


}
