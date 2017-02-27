package Scenarios.AWB_V2;

import java.awt.Robot;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import Pages.AWB2.AuctionBuyerDAshBoard;
import Pages.AdvSearchPages.Advance_Search;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Verify_uction_buyer_address_Phone_Email_ID extends Browser_Factory {
	
	String path="./src/main/resources/Resources/PS_TestData.xlsx";
	public ExtentTest logger;
	String resultFlag="fail";
	String scpath;
	String image;

	
	@DataProvider
	public Object[][] getCustomerSearchData() 
	{
		return Excel.getCellValue_inlist(path, "AWB_V2.0","AWB_V2.0","Verify_uction_buyer_address_Phone_Email_ID");
	}
	

	@Test(dataProvider="getCustomerSearchData")	
	public void Verify_uction_buyer_address_Phone_Email_ID(Hashtable<String, String> tabledata) throws Exception 
	{
		
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("AWB_V2.0").equals("Y")))
		{
			 resultFlag="skip";
             throw new SkipException("Skipping the test");
		}
			
		try{
		    logger=extent.startTest("Verify_uction_buyer_address_Phone_Email_ID","Verify_uction_buyer_address_Phone_Email_ID");

			//Login to PS Application
			LoginPage logpage = new LoginPage(driver);
			logpage.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Logged in successfully");
			
		
			Robot robot = new Robot();
			WebDriverWait wait = new WebDriverWait(driver, 40);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			
			Thread.sleep(10000);
			
			
			Dashboard_BifrostHostPopUp Bifrostpop1= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			//Bifrostpop1.clickContiDevice();
			driver.findElement(By.xpath("//a[@id='continueLink']")).click();
			Thread.sleep(10000);
        
		   PM_Homepage pmhome = new PM_Homepage(driver);
		   String scpath=Generic_Class.takeScreenShotPath();
		   Reporter.log(scpath,true);
		   String image=logger.addScreenCapture(scpath);
		   
		  
		   //Advance search page
		   Advance_Search advSearch= new Advance_Search(driver);
		   
		   pmhome.clk_AdvSearchLnk();
		   logger.log(LogStatus.INFO, "Clicked on Advanced Search link");
		   Thread.sleep(6000);
		   
		   
		      
		   advSearch.click_Auctionbuyer_radiobtn();
		   Thread.sleep(3000);
		   
		   String SQlQuery = "Select  distinct top 1 C.FirstName,s.sitenumber,A.accountnumber "
			   		+ "from Account A with(nolock) "
			   		+ "Join Customer CU with(nolock) on A.CustomerID = CU.CustomerID "
			   		+ "Join Contact C with(nolock) on CU.ContactID = C.ContactID "
			   		+ "join AuctionUnit au on au.buyeraccountid=a.accountid "
			   		+ "Join StorageOrderItem SOI with(nolock)on au.StorageOrderItemID = SOI.StorageOrderItemID "
			   		+ "Join AccountOrderItem AOI with(nolock) on aoi.StorageOrderItemID = SOI.StorageOrderItemID "
			   		+ "join site s with(nolock) on s.siteid=AOI.siteid "
			   		+ "Where 1=1 And CU.CustomerTypeID=90";
			   
			   List<String> AWBdata = DataBase_JDBC.executeSQLQuery_List(SQlQuery);
			   
			   
			   
			   
			
			   advSearch.enterLocationNumber(AWBdata.get(1));
			   Thread.sleep(1000);
			   
			   
			    advSearch.enterFirstName(AWBdata.get(0));
				logger.log(LogStatus.INFO, "Entered First name");
			
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			advSearch.clickSearchbtn();
			logger.log(LogStatus.INFO, "Click on Search button successfully");
			Thread.sleep(20000);
			
			String accNum= advSearch.gettext_firstAccnum_resultgrid();

				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "The Auction Buyer Account Number clicked---"+accNum);
				logger.log(LogStatus.INFO, "The Auction Buyer Account Number", image);

			
				Thread.sleep(2000);
			
			
			advSearch.clk_firstAccnum_resultgrid();
			
			Thread.sleep(10000);
			
			AuctionBuyerDAshBoard buyerdashboard= new AuctionBuyerDAshBoard(driver);
			
			
			Thread.sleep(2000);
			
			String buyernametype=buyerdashboard.get_Buyerdisplaytype();
			
			if(buyernametype.equals("Auction Buyer")){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "The Aution Buyer type is displayed");
				logger.log(LogStatus.INFO, "The Aution Buyer type is displayed",image);
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "The Aution Buyer type is displayed");
				logger.log(LogStatus.INFO, "The Aution Buyer type is displayed",image);

			}
			
			Thread.sleep(2000);
			//boolean addresdisplay=buyerdashboard.isdisplayaddress();
			
			if(buyerdashboard.isdisplayaddress()){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Auction Buyer Info- screen is displayed by default");
				logger.log(LogStatus.INFO, "Auction Buyer Info- screen is displayed by default",image);
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Auction Buyer Info- screen is not displayed by default");
				logger.log(LogStatus.INFO, "Auction Buyer Info- screen is not displayed by default",image);

			}
			
			Thread.sleep(2000);
			
			
			if(buyerdashboard.isdisplayaddress_official()){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Auction Buyer Info- Official Address is displayed");
				
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Auction Buyer Info- OfficialAddress is not displayed");
				
			}
			
			Thread.sleep(2000);
			
			try{
				if(buyerdashboard.isdisplayaddress_unofficial()){
					
					logger.log(LogStatus.INFO, "Auction Buyer Info- Unofficial Address is displayed");
					
				}
				
			}catch(Exception e){
				
				logger.log(LogStatus.INFO, "Auction Buyer Info- UnofficialAddress is not displayed");
			}
			
			
			
			
			Thread.sleep(2000);
			
			if(buyerdashboard.isdisplayphone_home()){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Auction Buyer Info- Home Phone Number displayed");
				
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Auction Buyer Info- Home Phone Number is not displayed");
				
			}
			
			Thread.sleep(2000);
			
			
			
			try{
				if(buyerdashboard.isdisplayphone_work()){
					
					logger.log(LogStatus.INFO, "Auction Buyer Info- Work Phone Number is displayed");
					
				}
				
			}catch(Exception e){
				
				logger.log(LogStatus.INFO, "Auction Buyer Info-Work Phone Number is not displayed");
			}
			
			
			
			
			Thread.sleep(2000);
			
			String buyeremail=buyerdashboard.get_email();
			
			if(buyeremail.contains(" No email addresses provided.")){
				
				logger.log(LogStatus.INFO, "The Email is displayed as--"+buyeremail);
				
			}
			else{
				logger.log(LogStatus.INFO, "The Email is displayed as--"+buyeremail);
				
			}
			
			Thread.sleep(2000);
			
			String buyeridentification=buyerdashboard.get_identification();
			
			if(buyeremail.contains("  No Identification Found")){
				
				logger.log(LogStatus.INFO, "The Identification  is displayed as--"+buyeremail);
				
			}
			else{
				logger.log(LogStatus.INFO, "The Identification is displayed as--"+buyeremail);
				
			}
			
			Thread.sleep(2000);
			
			
			
			
			
		}
		   
		   catch(Exception e)
		   {
			   Reporter.log("Exception: " + e,true);
			   resultFlag="fail";
			   logger.log(LogStatus.INFO, "Because of exception",image);
		   }
		  
		  }

    
		@AfterMethod
		public void afterMethod(){
		
		Reporter.log(resultFlag,true);
		
		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Verify_uction_buyer_address_Phone_Email_ID" , "Status", "Pass");
			
		}else if (resultFlag.equals("fail")){
			
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Verify_uction_buyer_address_Phone_Email_ID" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Verify_uction_buyer_address_Phone_Email_ID" , "Status", "Skip");
		}
		
		 extent.endTest(logger);
		 extent.flush();
		
	}

}
