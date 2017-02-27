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
import Pages.AdvSearchPages.Advance_Search;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Verify_create_note_functions extends Browser_Factory {
	
	String path="./src/main/resources/Resources/PS_TestData.xlsx";
	public ExtentTest logger;
	String resultFlag="fail";
	String scpath;
	String image;

	
	@DataProvider
	public Object[][] getCustomerSearchData() 
	{
		return Excel.getCellValue_inlist(path, "AWB_V2.0","AWB_V2.0","Verify_create_note_functions");
	}
	

	@Test(dataProvider="getCustomerSearchData")	
	public void Verify_create_note_functions(Hashtable<String, String> tabledata) throws Exception 
	{
		
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("AWB_V2.0").equals("Y")))
		{
			 resultFlag="skip";
             throw new SkipException("Skipping the test");
		}
			
		try{
		    logger=extent.startTest("Verify_create_note_functions","Verify_create_note_functions");

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
			
			jse.executeScript("window.scrollBy(1500,0)", "");
			
			Thread.sleep(2000);
			
			if(buyerdashboard.isdisplaycreatenotebtn()){
				
				logger.log(LogStatus.PASS, "Create Note button is displayed");	
			}
			else{
				logger.log(LogStatus.FAIL, "Create Note button is not displayed");
			}
			
			Thread.sleep(2000);
			
			buyerdashboard.click_createnote();
			
			Thread.sleep(2000);
			
			
			
			if(buyerdashboard.isdisplaycreatenote_title()){
				
				logger.log(LogStatus.PASS, "Create Note model window is displayed");	
			}
			else{
				logger.log(LogStatus.FAIL, "Create Note  model window is not displayed");
			}
			
			
			Thread.sleep(2000);
			
			AuctionBuyerCreateNotepage createnote_AWB=new AuctionBuyerCreateNotepage(driver);
			
			if(createnote_AWB.isdisplay_default()){
			
				
				logger.log(LogStatus.PASS, "Category dropdown default displaying option is - SELECT ");	
			}
			else{
				logger.log(LogStatus.FAIL, "Category dropdown not  displaying default  option as - SELECT ");
			}
			
			Thread.sleep(2000);
			
			createnote_AWB.clk_categorydropdown();
			
			List<WebElement> cat_options=driver.findElements(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']//li"));
			
	
			for (int i=1;i<cat_options.size();i++){
				
				
				String optionname=driver.findElement(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']//li["+i+"]")).getText();
				logger.log(LogStatus.INFO, "Category dropdown displaying option -  "+optionname);
			}
			
			Thread.sleep(2000);
			
			try{
				if(createnote_AWB.isdisplay_default_applynote()){
					
					logger.log(LogStatus.PASS, "Apply Note dropdown default displaying option is - SELECT ");	
				}
				
			}catch(Exception e){
				logger.log(LogStatus.FAIL, "Apply Note dropdown not  displaying default  option as - SELECT ");
				
			}
			
			Thread.sleep(2000);
			
			createnote_AWB.click_applynotearrow();
			Thread.sleep(2000);
			List<WebElement> applynote_options=driver.findElements(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']//li"));
			
			
			for (int i=1;i<applynote_options.size();i++){
				
				String applynate=driver.findElement(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']//li["+i+"]")).getText();
				logger.log(LogStatus.PASS, "Category dropdown displaying option -  "+applynate);
			}
			
			Thread.sleep(2000);
			
			if(createnote_AWB.isdisplay_notetext()){
			
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Note Text box is displayed ");	
				logger.log(LogStatus.INFO, "Note Text box is displayed",image);
				
			}
			else{
				
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Note Text box is not  displayed  ");
				logger.log(LogStatus.INFO, "Note Text box is not displayed",image);
				
			}
			
			if(createnote_AWB.isdisplay_emptextbox()){
				logger.log(LogStatus.PASS, "Employee input  box is displayed ");	
			}
			else{
				logger.log(LogStatus.FAIL, "Employee input box is not  displayed  ");
			}
			
			Thread.sleep(2000);
			
			
			
			createnote_AWB.click_createnote();
			Thread.sleep(2000);
			
			if(createnote_AWB.isdisplay_notetexterrormsg()){
				
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Note Text box is displayed error message as - Please enter a note ");	
				logger.log(LogStatus.INFO, "Note Text box is displayed error msg",image);
				
			}
			else{
				
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Note Text box is not  displayed error message ");
				logger.log(LogStatus.INFO, "Note Text box is not displayed error message",image);
				
			}
			
			
			
			Thread.sleep(2000);
			
			if(createnote_AWB.isdisplay_emptexterrormsg()){
				
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Employee ID is displayed error message as - Please enter an employee number ");	
				logger.log(LogStatus.INFO, "Employee ID is displayed error msg",image);
				
			}
			else{
				
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Employee ID is not  displayed error message ");
				logger.log(LogStatus.INFO, "Employee ID is not displayed error message",image);
				
			}
			
			Thread.sleep(2000);
			
			if(createnote_AWB.isdisplay_createbtn()){
				
				
				logger.log(LogStatus.PASS, "Create Note Button is displayed");			
			}
			else{
				
				
				logger.log(LogStatus.FAIL, "Create Note Button is not displayed ");
			}
			
			Thread.sleep(2000);
			
			if(createnote_AWB.isdisplay_cancelbtn()){
				
				
				logger.log(LogStatus.PASS, "Cancel Button is displayed");			
			}
			else{
				
				
				logger.log(LogStatus.FAIL, "Cancel Button is not displayed ");
			}
			
			
			Thread.sleep(2000);
			
			if(createnote_AWB.isdisplay_recenthistory()){
				logger.log(LogStatus.PASS, "Recent Notes History section is displayed");			
			}
			else{
				logger.log(LogStatus.FAIL, "Recent Notes History section is not displayed ");
			}
			
			Thread.sleep(2000);
			
			if(createnote_AWB.isdisplay_headerdate()){
				logger.log(LogStatus.PASS, "Date--Header Displayed in Recent History section");			
			}
			else{
				logger.log(LogStatus.FAIL, "Date--Header is not  Displayed in Recent History section ");
			}

			Thread.sleep(2000);
			
			if(createnote_AWB.isdisplay_headertime()){
				logger.log(LogStatus.PASS, "Time--Header Displayed in Recent History section");			
			}
			else{
				logger.log(LogStatus.FAIL, "Time--Header is not  Displayed in Recent History section ");
			}

			Thread.sleep(2000);
			
			if(createnote_AWB.isdisplay_headercategory()){
				logger.log(LogStatus.PASS, "Category--Header Displayed in Recent History section");			
			}
			else{
				logger.log(LogStatus.FAIL, "Category--Header is not  Displayed in Recent History section ");
			}

			Thread.sleep(2000);
			
			if(createnote_AWB.isdisplay_headernote()){
				logger.log(LogStatus.PASS, "Note--Header Displayed in Recent History section");			
			}
			else{
				logger.log(LogStatus.FAIL, "Note--Header is not  Displayed in Recent History section ");
			}

			Thread.sleep(2000);
			
			if(createnote_AWB.isdisplay_headernotetaker()){
				logger.log(LogStatus.PASS, "Note Taker--Header Displayed in Recent History section");			
			}
			else{
				logger.log(LogStatus.FAIL, "Note Taker--Header is not  Displayed in Recent History section ");
			}
			
			
			Thread.sleep(2000);
			try{
				if(createnote_AWB.isdisplay_headerspace()){
					logger.log(LogStatus.PASS, "Space--Header Displayed in Recent History section");			
				}
				
			}catch(Exception e){
				logger.log(LogStatus.FAIL, "Space--Header is not  Displayed in Recent History section ");
				
			}
		
			
			
			//List<WebElement> notelistbeforeadding=driver.findElements(By.xpath("//div[@class='k-grid-content ps-container ps-active-y ps-active-x']//tr"));
			
			Thread.sleep(5000);
			
			createnote_AWB.click_catagoryarrow();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']//li[2]")).click();
			
			Thread.sleep(2000);
			
			createnote_AWB.enter_notetext("Entering note");
			
			Thread.sleep(2000);
			
			createnote_AWB.enter_empnum("930326");
			Thread.sleep(2000);
			
			createnote_AWB.click_createnote();
			Thread.sleep(5000);
			
			logger.log(LogStatus.INFO, " User returned to the underlying page");
			
			
			Thread.sleep(8000);
			jse.executeScript("window.scrollBy(1500,0)", "");
			buyerdashboard.click_createnote();
			
			Thread.sleep(8000);
			
			
			String notelistafteradding=driver.findElement(By.xpath("//div[contains(@class, 'k-grid-content ps-container')]//tr[1]/td[4]")).getText();
			
			
			if(notelistafteradding.equals("Entering note")){
				logger.log(LogStatus.PASS, "The Entry is  present in the Recent History");			
			}
			else{
				logger.log(LogStatus.FAIL, "The Entry is not   present in the Recent History ");
			}
			
			
			Thread.sleep(5000);

			createnote_AWB.click_createnote();
			Thread.sleep(2000);
			createnote_AWB.click_catagoryarrow();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']//li[2]")).click();
			
			Thread.sleep(2000);
			
			createnote_AWB.enter_notetext("Entering note");
			
			Thread.sleep(2000);
			
			createnote_AWB.enter_empnum("930326");
			Thread.sleep(2000);
			
			createnote_AWB.click_createnote();
			Thread.sleep(5000);
			
			logger.log(LogStatus.INFO, " User returned to the underlying page");
			jse.executeScript("window.scrollBy(1500,0)", "");
			
			Thread.sleep(2000);
			buyerdashboard.click_createnote();
			
			Thread.sleep(2000);
			
			
			WebElement noteoduplicate=driver.findElement(By.xpath("//div[contains(@class, 'k-grid-content ps-container')]//tr[2]/td[4]"));
			
			String noteduplicateentry=noteoduplicate.getText();
			
			if(notelistafteradding.equals(noteduplicateentry)){
				
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Duplicate Entry is  present in the Recent History");			
				logger.log(LogStatus.INFO, "Duplicate Entry is  present in the Recent History",image);
				
			}
			else{
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Duplicate Entry  is not   present in the Recent History ");
				logger.log(LogStatus.INFO, "Note Text box is not displayed error message",image);
				
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
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Verify_create_note_functions" , "Status", "Pass");
			
		}else if (resultFlag.equals("fail")){
			
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Verify_create_note_functions" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Verify_create_note_functions" , "Status", "Skip");
		}
		
		 extent.endTest(logger);
		 extent.flush();
		
	}

}
