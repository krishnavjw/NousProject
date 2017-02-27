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

public class Verify_AuctionBuyer_with_name_auction_account_customer_account extends Browser_Factory {
	
	String path="./src/main/resources/Resources/PS_TestData.xlsx";
	public ExtentTest logger;
	String resultFlag="fail";
	String scpath;
	String image;

	
	@DataProvider
	public Object[][] getCustomerSearchData() 
	{
		return Excel.getCellValue_inlist(path, "AWB_V2.0","AWB_V2.0","Verify_AuctionBuyer_with_name_auction_account_customer_account");
	}
	

	@Test(dataProvider="getCustomerSearchData")	
	public void Verify_AuctionBuyer_with_name_auction_account_customer_account_mtd(Hashtable<String, String> tabledata) throws Exception 
	{
		
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("AWB_V2.0").equals("Y")))
		{
			 resultFlag="skip";
             throw new SkipException("Skipping the test");
		}
			
		try{
		    logger=extent.startTest("Verify_AuctionBuyer_with_name_auction_account_customer_account","Verify landing in Auction buyer dashboard from Search page - with name , auction account, customer account if he is also a customer");

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
			
			String buyername=buyerdashboard.get_Buyername();
			Thread.sleep(2000);
			
			if(buyername.contains(AWBdata.get(0))){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "The Aution Buyer name is displayed in DashBoard-- "+buyername+" with seached name--"+AWBdata.get(0));
				
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "The Aution Buyer name is not equal --"+buyername+" with seached name--"+AWBdata.get(0));
			

			}
			
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
			String buyeraccnum=buyerdashboard.get_Buyeraccnum();
			
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "The Auction Buyer Auction Account Number---"+buyeraccnum);
		
			
			Thread.sleep(2000);
			
			
			
			
			
			
			
			
			
			
			
			
			// search by customer Number
			buyerdashboard.click_backbtn();
			Thread.sleep(10000);
			
				pmhome.clk_AdvSearchLnk();
			   logger.log(LogStatus.INFO, "Clicked on Advanced Search link");
			   Thread.sleep(6000);
			   
			  
			   
			   
			   advSearch.enterAccNum(accNum);

				//advSearch.enterAccNum(tabledata.get("AccountNumber"));
				logger.log(LogStatus.INFO, "Enter Account Number in Account field successfully");
				Thread.sleep(8000);
				
				 scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Advance search page searching with  Customer Account Number of Auction Buyer");
				logger.log(LogStatus.INFO, "Advance search page searching with  Customer Account Number of Auction Buyer", image);


				//  click Search button
				advSearch.clickSearchAccbtn();
				logger.log(LogStatus.INFO, "clicking on Search button successfully in advance search page");
				Thread.sleep(6000);
				
				try{
					if(buyerdashboard.isdisplaytotal_due()){
						scpath=Generic_Class.takeScreenShotPath();
						Reporter.log(scpath,true);
						image=logger.addScreenCapture(scpath);
						logger.log(LogStatus.INFO, "Auction Buyer Dash Board after searching with Customer Account Number");
						logger.log(LogStatus.INFO, "Auction Buyer Dash Board after searching with Customer Account Number", image);
					}
					
				}catch(Exception e){
					scpath=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath,true);
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "Auction Buyer dont have  a Standard Customer Account.");
					logger.log(LogStatus.INFO, "Auction Buyer dont have  a Standard Customer Account",image);
					
				}
				
				
 
				 
			
			
			
			
		}
		   
		   catch(Exception e)
		   {
			   Reporter.log("Exception: " + e,true);
			   resultFlag="fail";
		   }
		  
		  }

    
		@AfterMethod
		public void afterMethod(){
		
		Reporter.log(resultFlag,true);
		
		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Verify_AuctionBuyer_with_name_auction_account_customer_account" , "Status", "Pass");
			
		}else if (resultFlag.equals("fail")){
			
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Verify_AuctionBuyer_with_name_auction_account_customer_account" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Verify_AuctionBuyer_with_name_auction_account_customer_account" , "Status", "Skip");
		}
		
		 extent.endTest(logger);
		 extent.flush();
		
	}

}
