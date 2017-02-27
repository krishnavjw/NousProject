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
import Pages.AdvSearchPages.AdvSearch_Reservation;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class AdvResSearch_SameLoc_Email extends Browser_Factory{
	public ExtentTest logger;
	String resultFlag="pass";
	String path="./src/main/resources/Resources/PS_TestData.xlsx";

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "CustomerSearch","CustomerSearch",  "AdvResSearch_SameLoc_Email");
	}
	

	@Test(dataProvider="getCustSearchData")
	public void advResSearch_SameLoc_Email(Hashtable<String, String> tabledata) throws InterruptedException 
	{


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerSearch").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}	

		try{


			//Login to PS Application
			logger=extent.startTest("AdvResSearch_SameLoc_Email","Advance Search for a reservation using Email - Same Location");
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
	        
	        Thread.sleep(15000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
			
			
			
			//Verify that a text entry field is present with text "Enter Name or Reservation Number"
			//and "Find Reservation" button is available next to the field
			PM_Homepage pmhomepage = new PM_Homepage(driver);
			String location = pmhomepage.getLocation();
			String getElemValue = pmhomepage.getInnerText_EnterNameOrResvtn();
			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image=logger.addScreenCapture(scpath);
			if(getElemValue.equalsIgnoreCase("Enter Name or Reservation Number")){
				logger.log(LogStatus.PASS, "Text entry field is present with text 'Enter Name or Reservation Number'");
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Text entry field with text 'Enter Name or Reservation Number' is not present");
				logger.log(LogStatus.INFO, "Image",image);
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
			
			
			//Navigate to Advanced Reservation Search Page
			pmhomepage.enter_NameOrResvtn("000");
			Thread.sleep(2000);
			pmhomepage.clk_findReservation();
			Thread.sleep(5000);
			logger.log(LogStatus.PASS, "Navigated to Advanced Reservation Search page");
			
			
			//Enter email of a customer and search for all matching reservations
			logger.log(LogStatus.INFO, "Email Search for a valid reservation");
			AdvSearch_Reservation advRes = new AdvSearch_Reservation(driver);
			advRes.enter_Email(tabledata.get("EmailId"));
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);
			advRes.clickSearchbtn();
			Thread.sleep(15000);
			
			//Fetch the matching results from db
			String sqlQuery = "select top 1 r.lastname, r.firstname, p.phonenumber, e.email, r.reservationid"+
					" from reservation r"+
					" join type t with (nolock) on t.typeid=r.reservationstatustypeid"+
					" join phone p on p.phoneid = r.phoneid"+
					" join emailaddress e on e.emailaddressid = r.emailaddressid"+
					" join reservationitem ri on r.reservationid = ri.reservationid"+
					" left join storageorderitem soi on soi.reservationitemid = ri.reservationitemid"+
					" where e.email = '"+tabledata.get("EmailId")+"' and"+
					" r.siteid = (select siteid from site where sitenumber = "+location+")"+
					" and t.name<> 'rented'"+
					" and datediff (dd, r.expirationdate,getutcdate() ) <= 30"+
					"   order by r.lastname asc";
			ArrayList<String> resValues = DataBase_JDBC.executeSQLQuery_List(sqlQuery);
			
			sqlQuery = "select  count(*) "+
					   " from reservation r"+
					   " join type t with (nolock) on t.typeid=r.reservationstatustypeid"+
					   " join phone p on p.phoneid = r.phoneid "+
					   " join emailaddress e on e.emailaddressid = r.emailaddressid"+
					   " join reservationitem ri on r.reservationid = ri.reservationid"+
					   " left join storageorderitem soi on soi.reservationitemid = ri.reservationitemid"+
					   " where e.email = '"+tabledata.get("EmailId")+"' and "+
					   " r.siteid = (select siteid from site where sitenumber = "+location+")"+
					   " and t.name<> 'rented'"+
						" and datediff (dd, r.expirationdate,getutcdate() ) <= 30";
			String result = DataBase_JDBC.executeSQLQuery(sqlQuery);	
			int dbCount = Integer.parseInt(result);
			
			
			//Verify the DB results with the UI results
			((JavascriptExecutor) driver).executeScript("window.scrollBy(-2000,0)");
			String getText = advRes.getText_records_match().toLowerCase();
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			if(dbCount == 0){
				if(getText.contains("no results found")){
					logger.log(LogStatus.PASS, "Zero records found in DB. Hence 0 results shown in UI. Number of records matched between DB and UI");
					logger.log(LogStatus.PASS, "Image",image);
				}else{
					logger.log(LogStatus.INFO, "DB Count: "+dbCount);
					logger.log(LogStatus.INFO, "Web Message: "+getText);
					logger.log(LogStatus.FAIL, "Number of records not matched between DB and UI");
					logger.log(LogStatus.FAIL, "Image",image);
					if(resultFlag.equals("pass"))
						resultFlag="fail";
				}
			}else{
			if(getText.contains(dbCount+" records matched")){
				logger.log(LogStatus.INFO, "DB Count: "+dbCount);
				logger.log(LogStatus.INFO, "Web Message: "+getText);
				logger.log(LogStatus.PASS, "Number of records matched between DB and UI");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				logger.log(LogStatus.INFO, "DB Count: "+dbCount);
				logger.log(LogStatus.INFO, "Web Message: "+getText);
				logger.log(LogStatus.FAIL, "Number of records not matched between DB and UI");
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			}
			
			
			//Verify that the results displayed on the search results matches the data retrieved from the db
			int count = 0;
			for(int i=0; i<resValues.size(); i++){
				
				getText = driver.findElement(By.xpath("//div[@class='searchResult k-grid k-widget']/div[@class='k-grid-content ps-container']/table/tbody/tr/td["+(i+2)+"]")).getText().trim();
				if(i==2){
				getText = getText.replaceAll(" ", "");
		        getText = getText.replace("(", "");
		        getText = getText.replace(")", "");
		        getText = getText.replace("-", "");
				}
				if(resValues.get(i) == null){
					if(getText.equals("")){
						count++;
					}
				}else{
				if(resValues.get(i).toString().trim().equalsIgnoreCase(getText)){
					count++;
					logger.log(LogStatus.PASS, "Expected Result:"+resValues.get(i).toString().trim()+" and Actual Result:"+getText+" in advance search results grid");
				}
				}
			}
			
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			if(count == resValues.size()){
				logger.log(LogStatus.PASS, "Results displayed in the application matched with the db result");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Results displayed in the application not matched with the db result");
				logger.log(LogStatus.FAIL, "Image",image);
				getText = driver.findElement(By.xpath("//div[@class='searchResult k-grid k-widget']/div[contains(@class,'k-grid-content ps-container')]/table/tbody/tr")).getText().trim();
				logger.log(LogStatus.INFO, "Actual Result: "+getText);
				for(int i=0; i<resValues.size(); i++){
					logger.log(LogStatus.INFO, "Expected Result: "+resValues.get(i).toString().trim());
				}
			}
			
			
			//Verify the results displayed for an invalid customer email
			logger.log(LogStatus.INFO, "Reservation Search for an Invalid Email");
			advRes.click_Reset();
			Thread.sleep(15000);
			advRes.enter_Email("blochikirlo@ca.com");
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);
			advRes.clickSearchbtn();
			Thread.sleep(15000);
			
			((JavascriptExecutor) driver).executeScript("window.scrollBy(-2000,0)");
			getText = advRes.getText_records_match().toLowerCase();
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			if(getText.contains("no results found matching your criteria")){
				logger.log(LogStatus.PASS, "No results found matching your criteria -- message displayed as expected");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "No results found matching your criteria -- message not displayed as expected");
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			
			
			//Do not enter any details for customer name and verify the message
			logger.log(LogStatus.INFO, "Blank search for a customer email and verify the message");
			advRes.click_Reset();
			Thread.sleep(15000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);
			advRes.clickSearchbtn();
			Thread.sleep(15000);
			
			((JavascriptExecutor) driver).executeScript("window.scrollBy(-2000,0)");
			getText = advRes.getText_records_match().toLowerCase();
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			if(getText.contains("no criteria was entered")){
				logger.log(LogStatus.PASS, "No criteria was entered -- message displayed as expected");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "No criteria was entered -- message not displayed as expected");
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
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
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","AdvResSearch_SameLoc_Email" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerSearch","AdvResSearch_SameLoc_Email" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","AdvResSearch_SameLoc_Email" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();



	}

}
