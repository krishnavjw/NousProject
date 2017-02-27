package Scenarios.CustomerSearch;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.ViewReservationPage;
import Scenarios.Browser_Factory;

public class ReservationSearch_InvalidResNum_SameLoc extends Browser_Factory{
	
	public ExtentTest logger;
	String resultFlag="pass";
	String path="./src/main/resources/Resources/PS_TestData.xlsx";


	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "CustomerSearch","CustomerSearch",  "ReservationSearch_InvalidResNum_SameLoc");
	}


	@Test(dataProvider="getCustSearchData")
	public void invalidResNumSearch_SameLoc(Hashtable<String, String> tabledata) throws InterruptedException 
	{


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerSearch").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}	

		try{

			//Login to PS Application
			logger=extent.startTest("ReservationSearch_InvalidResNum_SameLoc","Search using Invalid Reservation Number -- Same Location");
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as PM");

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);

			String biforstNum=Bifrostpop.getBiforstNo();

			 //driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
		
	        Robot robot = new Robot();
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_T);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_T); 
	        
	        Thread.sleep(5000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(15000);
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

			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			 // driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_PAGE_DOWN);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
	        
	        Thread.sleep(10000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
	
			
			//Verify that a text entry field is present with text "Enter Name or Reservation Number"
			//and "Find Reservation" button is available next to the field
			PM_Homepage pmhomepage = new PM_Homepage(driver);
			String getElemValue = pmhomepage.getInnerText_EnterNameOrResvtn();
			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image=logger.addScreenCapture(scpath);
			if(getElemValue.equalsIgnoreCase("Enter Name or Reservation Number")){
				logger.log(LogStatus.PASS, "Text entry field is present with text 'Enter Name or Reservation Number'");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Text entry field with text 'Enter Name or Reservation Number' is not present");
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			
			boolean findResElem = pmhomepage.IsElemPresent_FindReservation();
			if(findResElem){
				logger.log(LogStatus.PASS, "Find Reservation button is available next to the field");
			}else{
				logger.log(LogStatus.FAIL, "Find Reservation button is not available");
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			
			
			//Enter a valid Reservation Number
			logger.log(LogStatus.INFO, "   *** Reservation Search with Valid Reservation Number ***   ");
			String location = pmhomepage.getLocation();
			String sqlQuery = "select top 1 reservationid from reservation where SiteID = (select siteid from site where sitenumber = "+location+" ) "
					+ " and reservationstatustypeid = 127 and employeeid >0 order by reservationid desc";
			String resId = DataBase_JDBC.executeSQLQuery(sqlQuery);
			
			pmhomepage.enter_NameOrResvtn(resId);
			pmhomepage.clk_findReservation();
			Thread.sleep(10000);
			
			ViewReservationPage viewRes = new ViewReservationPage(driver);
			boolean viewResHdrExists = viewRes.IsViewResDisplayed();
			
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			if(viewResHdrExists == true){
				logger.log(LogStatus.PASS, "View Reservation page displayed successfully");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "View Reservation page not displayed");
				logger.log(LogStatus.FAIL, "Image",image);
			}
			
			
			sqlQuery = "select firstname+' '+lastname from reservation where reservationid = "+resId;
			String customerNameDB  = DataBase_JDBC.executeSQLQuery(sqlQuery);

			
			//Verify the reservation details
			String getReservationInfo = viewRes.getResInfo();
			String getCustomerName = viewRes.getCustomerName();
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			if((getReservationInfo.contains("Reservation Number: "+resId)) && getCustomerName.equalsIgnoreCase(customerNameDB)){
				logger.log(LogStatus.PASS, "Reservation details matched. Reservation Number: "+resId+"  Customer Name: "+customerNameDB);
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Reservation details not matched");
				logger.log(LogStatus.FAIL, "Image",image);	
			}
			
			
			//Enter a Invalid Reservation Number
			logger.log(LogStatus.INFO, "   *** Reservation Search with Invalid Reservation Number ***   ");
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
			viewRes.clk_BackToDashBoard();
			Thread.sleep(15000);
			pmhomepage.enter_NameOrResvtn(tabledata.get("ReservationNO"));
			logger.log(LogStatus.INFO, "Entered Invalid Reservation Num");
			pmhomepage.clk_findReservation();
			Thread.sleep(10000);
			
			Advance_Search advSrch = new Advance_Search(driver);
			String getText = advSrch.get_RecordsMatchedText().toLowerCase();
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			if(getText.contains("no reservation results found matching the specified criteria")){
				logger.log(LogStatus.PASS, "Error message displayed correctly. no reservation results found matching the specified criteria");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Error message not displayed correctly. no reservation results found matching the specified criteria");
				logger.log(LogStatus.FAIL, "Image",image);
			}
			
			
			//Do not enter any number and click search
			logger.log(LogStatus.INFO, "   *** Do not enter any number and click search ***   ");
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,document.body.ScrollHeight)");
			viewRes.clk_BackToDashBoard();
			Thread.sleep(5000);
			pmhomepage.clk_findReservation();
			Thread.sleep(8000);
			//String notificationAlert = pmhomepage.getNotificationAlert();
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			//if(notificationAlert.contains("Please enter Name or Reservation Number to search")){
			//if(!(driver.findElements(By.xpath("//div[@class='k-window-content k-content']/div[contains(text(),'Please enter Name or Reservation Number to search')]")).size()==0)){
			String errormsg="Please enter Name or Reservation Number to search";
			Thread.sleep(4000);
			//String text =driver.findElement(By.xpath("html/body//div[@class='k-widget k-window']//div[contains(@class,'modal-content')]")).getText();
			String text =driver.findElement(By.xpath("html/body")).getText();
			if(text.contains(errormsg))
			{
				logger.log(LogStatus.PASS, "Warning message displayed correctly. Please enter Name or Reservation Number to search");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Warning message not displayed correctly. Please enter Name or Reservation Number to search");
				logger.log(LogStatus.FAIL, "Image",image);
			}
			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
		}
	}

	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","ReservationSearch_InvalidResNum_SameLoc" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerSearch","ReservationSearch_InvalidResNum_SameLoc" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","ReservationSearch_InvalidResNum_SameLoc" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();



	}

}
