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
import Pages.AWB2.AuctionBuyerCreateNotepage;
import Pages.AWB2.AuctionBuyerDAshBoard;
import Pages.AWB2.AuctionBuyer_EditBuyerdetails;
import Pages.AdvSearchPages.Advance_Search;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Edit_buyer_details_function extends Browser_Factory {
	
	String path="./src/main/resources/Resources/PS_TestData.xlsx";
	public ExtentTest logger;
	String resultFlag="fail";
	String scpath;
	String image;

	
	@DataProvider
	public Object[][] getCustomerSearchData() 
	{
		return Excel.getCellValue_inlist(path, "AWB_V2.0","AWB_V2.0","Edit_buyer_details_function");
	}
	

	@Test(dataProvider="getCustomerSearchData")	
	public void Edit_buyer_details_function(Hashtable<String, String> tabledata) throws Exception 
	{
		
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("AWB_V2.0").equals("Y")))
		{
			 resultFlag="skip";
             throw new SkipException("Skipping the test");
		}
			
		try{
		    logger=extent.startTest("Edit_buyer_details_function","Edit_buyer_details_function");

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
			
			
			try{
				
				driver.findElement(By.xpath("//a[contains(text(),'Auction Buyer')]")).click();
				logger.log(LogStatus.INFO, "Clicking Auction Buyer link from Customer DashBoard" );
				
			}catch(Exception e){
				
				logger.log(LogStatus.INFO, "Navigating to Auction Buyer DashBoard" );
			}
			
			
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
			
			//jse.executeScript("window.scrollBy(1500,0)", "");
			
			Thread.sleep(2000);
			
			
			String adressbefore= buyerdashboard.get_adress();
			Thread.sleep(2000);
			String firstnamebefore= buyerdashboard.get_Buyername();
			Thread.sleep(2000);
			String emailbefore= buyerdashboard.get_emailupdate();
			Thread.sleep(2000);
			buyerdashboard.click_editbuyer();
			
			Thread.sleep(8000);
			
			AuctionBuyer_EditBuyerdetails editbuyer=new AuctionBuyer_EditBuyerdetails(driver);
			
			// UI validations for Auction Buyer
			
			String fnverify=editbuyer.get_firstname();
			
			if(firstnamebefore.contains(fnverify)){
				
				logger.log(LogStatus.PASS, "The Edit Auction Buyer Verify firstname --"+firstnamebefore);
				
			}
			else{
				logger.log(LogStatus.FAIL, "The Edit Auction Buyer Verify firstname --"+firstnamebefore);

			}
			Thread.sleep(2000);
			
			String addressverify=editbuyer.get_address1();
			
			if(adressbefore.contains(addressverify)){
				
				logger.log(LogStatus.PASS, "The Edit Auction Buyer Verify Address --"+adressbefore);
				
			}
			else{
				logger.log(LogStatus.FAIL, "The Edit Auction Buyer Verify Address--"+adressbefore);

			}
			
			Thread.sleep(2000);
		
			/*String emailverify=editbuyer.get_email();
			
			if(emailverify.contains(emailbefore)){
				
				logger.log(LogStatus.PASS, "The Edit Auction Buyer Verify Email --"+emailbefore);
				
			}
			else{
				logger.log(LogStatus.FAIL, "The Edit Auction Buyer Verify Email --"+emailbefore);

			}*/
			
			// Updating the Auction Buyer
			editbuyer.enter_Firstname(tabledata.get("Firstname"));
			Thread.sleep(1000);
			editbuyer.enter_address1(tabledata.get("Address1"));
			Thread.sleep(1000);
			
			editbuyer.clk_satedropdown();
			Thread.sleep(2000);
			editbuyer.select_statte();
			Thread.sleep(2000);
			editbuyer.enter_statepin("9999");
			Thread.sleep(2000);
			editbuyer.enter_areacode(tabledata.get("Areacode"));
			Thread.sleep(1000);
			editbuyer.enter_exchange(tabledata.get("exchange"));
			Thread.sleep(1000);
			editbuyer.enter_location(tabledata.get("location"));
			Thread.sleep(1000);
			jse.executeScript("window.scrollBy(0,1500)", "");
			Thread.sleep(1000);
			editbuyer.enter_email(tabledata.get("email"));
			Thread.sleep(1000);
			editbuyer.clk_save();
			Thread.sleep(1000);
			editbuyer.enter_note("enter nore");
			Thread.sleep(1000);
			editbuyer.enter_emp(tabledata.get("UserName"));
			Thread.sleep(1000);
			editbuyer.clk_continue();
			
			
			Thread.sleep(5000);
			
			String adress1= buyerdashboard.get_adress();
			
			if(adress1.contains(tabledata.get("Address1"))){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "The Auction Buyer Dash Board updated the adress--"+tabledata.get("Address1"));
				logger.log(LogStatus.INFO, "The Auction Buyer Dash Board updated the adress",image);
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "The Auction Buyer Dash Board not updated the adress--"+tabledata.get("Address1"));
				logger.log(LogStatus.INFO, "he Auction Buyer Dash Board not updated the adress",image);

			}
			
			Thread.sleep(2000);
			
			String firstname= buyerdashboard.get_Buyername();
			
			if(firstname.contains(tabledata.get("Firstname"))){
				
				logger.log(LogStatus.PASS, "The Auction Buyer Dash Board updated the Firstname--"+tabledata.get("Firstname"));
				
			}
			else{
				logger.log(LogStatus.FAIL, "The Auction Buyer Dash Board not updated the Firstname--"+tabledata.get("Firstname"));

			}
			
			
			Thread.sleep(2000);
			
			String emailupdate= buyerdashboard.get_emailupdate();
			
			if(emailupdate.contains(tabledata.get("email"))){
				
				logger.log(LogStatus.PASS, "The Auction Buyer Dash Board updated the Email--"+tabledata.get("email"));
				
			}
			else{
				logger.log(LogStatus.FAIL, "The Auction Buyer Dash Board not updated the Email--"+tabledata.get("email"));

			}
			
			
			//DB Verification pending
			
			String verifydocdb_data= "select c.FirstName as Name, "
					+ "addr.addressline1,ea.email "
					+ "from account a "
					+ "join accountaddress aa on aa.accountid =  a.accountid  "
					+ "join address addr on addr.addressid = aa.addressid and addr.isofficial = 1 "
					+ "join accountphone ap on ap.accountid = a.accountid  "
					+ "join phone ph on ph.phoneid = ap.phoneid and ph.isofficial = 1 "
					+ "join customer cu on a.customerid=cu.customerid "
					+ "join contact c on cu.contactid=c.contactid "
					+ "join accountemail ae on a.accountid=ae.accountid "
					+ "left join emailaddress ea on ae.emailid=ea.emailaddressid and ea.isofficial=1 "
			 		+ "where a.accountid='"+AWBdata.get(2)+"'";
			 
			 
				   
				   List<String> verifydata= DataBase_JDBC.executeSQLQuery_List(verifydocdb_data);
		   
				   if(verifydata.get(1).contains(tabledata.get("Address1"))){
						
						logger.log(LogStatus.PASS, "The Auction Buyer Dash Board updated the adress in DB--"+verifydata.get(1));
						
					}
					else{

						
						logger.log(LogStatus.FAIL, "The Auction Buyer Dash Board not updated the adress in DB--"+verifydata.get(1));
					

					}  
				   
				   if(verifydata.get(0).contains(tabledata.get("Firstname"))){
						
						logger.log(LogStatus.PASS, "The Auction Buyer Dash Board updated the Firstname in DB--"+verifydata.get(0));
						
					}
					else{
						logger.log(LogStatus.FAIL, "The Auction Buyer Dash Board not updated the Firstname--"+verifydata.get(0));

					}
			
				   if(verifydata.get(2).contains(tabledata.get("email"))){
						
						logger.log(LogStatus.PASS, "The Auction Buyer Dash Board updated the Email in DB--"+verifydata.get(2));
						
					}
					else{
						logger.log(LogStatus.FAIL, "The Auction Buyer Dash Board not updated the Email DB--"+verifydata.get(2));

					}
			
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
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Edit_buyer_details_function" , "Status", "Pass");
			
		}else if (resultFlag.equals("fail")){
			
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Edit_buyer_details_function" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Edit_buyer_details_function" , "Status", "Skip");
		}
		
		 extent.endTest(logger);
		 extent.flush();
		
	}

}
