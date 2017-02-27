package Scenarios.AWB_V2;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
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
import Pages.AWB2.AuctionBuyer_Edit_tax;
import Pages.AdvSearchPages.Advance_Search;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Edit_tax_exemption_function extends Browser_Factory {
	
	String path="./src/main/resources/Resources/PS_TestData.xlsx";
	public ExtentTest logger;
	String resultFlag="fail";
	String scpath;
	String image;

	
	@DataProvider
	public Object[][] getCustomerSearchData() 
	{
		return Excel.getCellValue_inlist(path, "AWB_V2.0","AWB_V2.0","Edit_tax_exemption_function");
	}
	

	@Test(dataProvider="getCustomerSearchData")	
	public void Edit_tax_exemption_function(Hashtable<String, String> tabledata) throws Exception 
	{
		
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("AWB_V2.0").equals("Y")))
		{
			 resultFlag="skip";
             throw new SkipException("Skipping the test");
		}
			
		try{
		    logger=extent.startTest("Edit_tax_exemption_function","Edit_tax_exemption_function");

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
		   
		   jse.executeScript("window.scrollBy(1500,0)", "");
		   //Advance search page
		   Advance_Search advSearch= new Advance_Search(driver);
		   
		  // pmhome.clk_AdvSearchLnk();
		   driver.findElement(By.xpath("//a[@id='linkAdvancedSearch']")).click();
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
		   
		   
		   
		   
		   String verifydocdb_brfore= "select d.url "
			 		+ "from document d with(nolock) "
			 		+ "join CustomerTaxExemption ct with(nolock) on d.ownerid=ct.CustomerTaxExemptionID "
			 		+ "join customer cu  with(nolock) on ct.CustomerID=cu.CustomerID "
			 		+ "join account a with(nolock) on cu.customerid=a.customerid "
			 		+ "join contact c with(nolock) on cu.contactid=c.contactid "
			 		+ "where a.accountid='"+AWBdata.get(2)+"'";
			 
			 
				   
				   List<String> verifydocuploadedbefore= DataBase_JDBC.executeSQLQuery_List(verifydocdb_brfore);
		   
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
			
			
			int doc_size_before;
			buyerdashboard.click_document();
			try{
				
				List<WebElement> list_docs= driver.findElements(By.xpath("//div[@class='k-grid-content ps-container']//tr"));
				doc_size_before=list_docs.size();
			}catch(Exception e){
				doc_size_before=0;
				
			}
			Thread.sleep(2000);
			
			driver.findElement(By.xpath("//span[contains(text(),'Auction Buyer Info')]")).click();
			
			
			Thread.sleep(2000);
			// ----------------------------edit tax excempt--------------------
			
			buyerdashboard.click_edittax();
			
			Thread.sleep(10000);
			
			AuctionBuyer_Edit_tax AB_Edit_tax=new AuctionBuyer_Edit_tax(driver);
			
			
			if(AB_Edit_tax.isdisplay_tax_yes_radio()){
				
				logger.log(LogStatus.PASS, "Tax Exempt -- Yes and NO radio button displayed ");
				
			}
			else{
				logger.log(LogStatus.FAIL, "Tax Exempt -- Yes and No radio button is not displayed ");

			}
			
			/*Thread.sleep(2000);
			
			if(AB_Edit_tax.isdisplay_tax_no_radio()){
				
				logger.log(LogStatus.PASS, "Tax Exempt -- NO radio button displayed ");
				
			}
			else{
				logger.log(LogStatus.FAIL, "Tax Exempt -- No radio button is not displayed ");

			}
			*/
			
			Thread.sleep(2000);
			
			AB_Edit_tax.select_tax_yes_radio();
			
			Thread.sleep(2000);
			
			
			
			if(AB_Edit_tax.isdisplay_tax_num_input() && AB_Edit_tax.isdisplay_tax_num_label()){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit Tax Exempt screen displaying -Tax Exempt Number label with input box");
				logger.log(LogStatus.INFO, "Edit Tax Exempt screen displaying -Tax Exempt Number label with input box",image);
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Edit Tax Exempt screen not displaying -Tax Exempt Number label with input box");
				logger.log(LogStatus.INFO, "Edit Tax Exempt screen not displaying -Tax Exempt Number label with input box",image);

			}
			
			
			Thread.sleep(2000);
			
			List<WebElement> statelist=driver.findElements(By.xpath("//input[@id='DisplayTaxExempt_TaxExempt_StateCode']"));
			
			if(statelist.size()>0){
				
				logger.log(LogStatus.PASS, "State Dropdown is displayed ");
				
			}
			else{
				logger.log(LogStatus.FAIL, "State Dropdown is not displayed  ");

			}
			
			Thread.sleep(2000);
			
			if(AB_Edit_tax.isdisplay_tax_expirationdate_label()){
				
				logger.log(LogStatus.PASS, "Tax Exempt -- Expiration date label ");
				
			}
			else{
				logger.log(LogStatus.FAIL, "Tax Exempt -- Expiration date label not displayed");

			}
			
			Thread.sleep(2000);
			
			if(AB_Edit_tax.isdisplay_tax_month() && AB_Edit_tax.isdisplay_tax_day() && 
					AB_Edit_tax.isdisplay_tax_year()){
				
				logger.log(LogStatus.PASS, "Tax Exempt -- Expiration date with Month , Day, year input boxes displayed ");
				
			}
			else{
				logger.log(LogStatus.FAIL, "Tax Exempt -- Expiration date with Month , Day, year input boxes not displayed");

			}
			
			Thread.sleep(2000);
			
			if(AB_Edit_tax.isdisplay_scan_btn()){
				
				logger.log(LogStatus.PASS, "Tax Exempt -- Scan button is  displayed ");
				
			}
			else{
				logger.log(LogStatus.FAIL, "Tax Exempt -- Scan button is not displayed");

			}
			
			Thread.sleep(2000);
			
			if(AB_Edit_tax.isdisplay_upload_btn()){
				
				logger.log(LogStatus.PASS, "Tax Exempt -- Upload button is  displayed ");
				
			}
			else{
				logger.log(LogStatus.FAIL, "Tax Exempt -- Upload button is not displayed");

			}
			
			Thread.sleep(2000);
			
			List<WebElement> cancel_btn=driver.findElements(By.xpath("//a[contains(text(),'Cancel')]"));
			
			if(cancel_btn.size()>0){
				
				logger.log(LogStatus.PASS, "Cancel button is displayed ");
				
			}
			else{
				logger.log(LogStatus.FAIL, "Cancel button is not displayed  ");

			}
			
			Thread.sleep(2000);
			
			List<WebElement> save_btn=driver.findElements(By.xpath("//a[contains(text(),'Save')]"));
			
			if(cancel_btn.size()>0){
				
				logger.log(LogStatus.PASS, "'Save' button is displayed ");
				
			}
			else{
				logger.log(LogStatus.FAIL, "'Save' button is not displayed  ");

			}
			Thread.sleep(2000);
			
			List<WebElement> back_btn=driver.findElements(By.xpath("//a[contains(text(),'Back to Dashboard')]"));
			
			if(cancel_btn.size()>0){
				
				logger.log(LogStatus.PASS, "'BAck to Dashboard' button is displayed ");
				
			}
			else{
				logger.log(LogStatus.FAIL, "'BAck to Dashboard' button is not displayed  ");

			}
			Thread.sleep(2000);
			
			AB_Edit_tax.select_tax_yes_radio();
			Thread.sleep(2000);
			AB_Edit_tax.entertax_num_input(tabledata.get("Tax_num"));
			Thread.sleep(2000);
			AB_Edit_tax.select_dropdown();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//ul[@id='DisplayTaxExempt_TaxExempt_StateCode_listbox']/li[2]")).click();
			Thread.sleep(2000);
			
			AB_Edit_tax.get_tax_month(tabledata.get("Month"));
			
			AB_Edit_tax.get_tax_day(tabledata.get("day"));
			AB_Edit_tax.get_tax_year(tabledata.get("year"));
			
			Thread.sleep(2000);
			AB_Edit_tax.clk_upload_btn();
			Thread.sleep(1000);

            String pathfile = System.getProperty("user.dir");
            System.out.println("Path is :-- " + pathfile);
            uploadFile(pathfile + File.separator + "per1.png");

            Thread.sleep(3000);

            driver.findElement(By.xpath("//a[contains(text(),'Save')]")).click();
            
            AuctionBuyerCreateNotepage createnote_AWB=new AuctionBuyerCreateNotepage(driver);
            Thread.sleep(2000);
            AB_Edit_tax.enter_notetext("Entering note");
			
			Thread.sleep(2000);
			
			createnote_AWB.enter_empnum("122666");
			Thread.sleep(2000);
			
			driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
			Thread.sleep(5000);
			
			buyerdashboard.click_document();
			int doc_size_after;
			try{
				
				List<WebElement> list_docs= driver.findElements(By.xpath("//div[@class='k-grid-content ps-container']//tr"));
				doc_size_after=list_docs.size();
			}catch(Exception e){
				doc_size_after=0;
				
			}
			Thread.sleep(2000);
			if(doc_size_before<doc_size_after){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "'Tax document is added in List ");
				logger.log(LogStatus.INFO, "Tax document is added in List",image);
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "'Tax document is not added in List ");
				logger.log(LogStatus.INFO, "Tax document is not added in List",image);

			}
				
			Thread.sleep(2000);
			
			 String verifydoc= "select d.url "
			 		+ "from document d with(nolock) "
			 		+ "join CustomerTaxExemption ct with(nolock) on d.ownerid=ct.CustomerTaxExemptionID "
			 		+ "join customer cu  with(nolock) on ct.CustomerID=cu.CustomerID "
			 		+ "join account a with(nolock) on cu.customerid=a.customerid "
			 		+ "join contact c with(nolock) on cu.contactid=c.contactid "
			 		+ "where a.accountid='"+AWBdata.get(2)+"'";
			 
			 
				   
				   List<String> verifydocuploadedafter= DataBase_JDBC.executeSQLQuery_List(verifydoc);
				   
				   
				   Thread.sleep(2000);
					if(verifydocuploadedbefore.size()<verifydocuploadedafter.size()){
						
						logger.log(LogStatus.PASS, "'Tax document is added in DATA BASE in URL--- "+verifydocuploadedafter.get(0));
						
					}
					else{

						
					
						logger.log(LogStatus.FAIL, "'Tax document is not added in DATABASE");
						

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
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Edit_tax_exemption_function" , "Status", "Pass");
			
		}else if (resultFlag.equals("fail")){
			
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Edit_tax_exemption_function" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "AWB_V2.0","Edit_tax_exemption_function" , "Status", "Skip");
		}
		
		 extent.endTest(logger);
		 extent.flush();
		
	}
		
		
		public void uploadFile(String fileLocation) {
            try {

                   Thread.sleep(3000);
                   // Setting clipboard with file location
                   setClipboardData(fileLocation);
                   // native key strokes for CTRL, V and ENTER keys
                   Robot robot = new Robot();
                   robot.keyPress(KeyEvent.VK_CONTROL);
                   robot.keyPress(KeyEvent.VK_V);
                   robot.keyRelease(KeyEvent.VK_V);
                   robot.keyRelease(KeyEvent.VK_CONTROL);
                   robot.keyPress(KeyEvent.VK_ENTER);
                   robot.keyRelease(KeyEvent.VK_ENTER);
            } catch (Exception exp) {
                   exp.printStackTrace();
            }
     }

     public void setClipboardData(String string) {
            // StringSelection is a class that can be used for copy and paste
            // operations.
            StringSelection stringSelection = new StringSelection(string);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
     }


}
