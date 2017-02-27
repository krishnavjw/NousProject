package Scenarios.Email;

import java.util.Hashtable;

import org.openqa.selenium.JavascriptExecutor;
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
import Pages.EmailPages.EmailHomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;



public class SearchFieldinEmailScreen extends Browser_Factory{

	String path="./src/main/resources/Resources/PS_TestData.xlsx";
	public ExtentTest logger;
	String resultFlag="pass";

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "Email","Email","Search_Field");
	}

	@Test(dataProvider="getData")	
	public void emailCompose(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Email").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		try
		{


			//Login to PS Application
			logger=extent.startTest(this.getClass().getSimpleName(),"Search Field in Email Screen");
			
			
			String currIPAddress=Generic_Class.getIPAddress();
			
			System.out.println("current ip address  " + currIPAddress);
			
			DataBase_JDBC.executeSQLQuery("Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid=142");
		    Thread.sleep(5000);
			
			DataBase_JDBC.executeSQLQuery("Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid=2211");
		    Thread.sleep(5000);
			
		    DataBase_JDBC.executeSQLQuery("Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and paramvalue="+currIPAddress);
			Thread.sleep(5000);
			    
			    
			String updateQuery2 = "Update siteparameter set paramvalue='"+currIPAddress+"' where paramcode='IP_COMPUTER_FIRST' and siteid=2211";
			DataBase_JDBC.executeSQLQuery(updateQuery2);
	        Thread.sleep(5000);
	        logger.log(LogStatus.INFO, "Switched the current System to the Site ID :  2211");
	        
	        Thread.sleep(5000);
	        
	        
	        
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as PM");
			String scpath, image;
			
		/*	Thread.sleep(10000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);*/
			
			try
			{
				Dashboard_BifrostHostPopUp Bifrostpop1 = new Dashboard_BifrostHostPopUp(driver);
			
	
			Bifrostpop1.clickContiDevice();
			Thread.sleep(10000);
			}
			catch(Exception e)
			{
				System.out.println("");
			}
			
			JavascriptExecutor js = (JavascriptExecutor)driver; 
			js.executeScript("window.scrollBy(0,450)", "");
			Thread.sleep(2000);
			
			
			//Click on the Email text in the Footer of the PM dashboard
			PM_Homepage homepage = new PM_Homepage(driver);
			homepage.click_EmailLink();
			Thread.sleep(5000);
			
			EmailHomePage emailhome = new EmailHomePage(driver);
			boolean isPresent_EmailHeader = emailhome.exists_EmailHeader();
			if(isPresent_EmailHeader){
				logger.log(LogStatus.PASS, "Navigated to Email Home Page");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Did not navigated to Email Home Page");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
	
			
			//Verify that Inbox folder is displayed as default view.
			
			boolean isDefault_InboxFolder = emailhome.default_InboxFolder();
			if(isDefault_InboxFolder){
				logger.log(LogStatus.PASS, "Inbox folder is displayed as default view");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Inbox folder is not displayed as default view");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			
			
			//Verify if Search field is available on all the folder pages
			
			emailhome.clickInboxFolder();
			boolean searchFieldDisplayed=emailhome.isSearchFieldDisplayed();
			Thread.sleep(2000);
			
			emailhome.clickDraftsFolder();
			searchFieldDisplayed=searchFieldDisplayed&&emailhome.isSearchFieldDisplayed();
			Thread.sleep(2000);
			
			emailhome.clickSentEmailFolder();
			searchFieldDisplayed=searchFieldDisplayed&&emailhome.isSearchFieldDisplayed();
			Thread.sleep(2000);
			
			emailhome.clickSentEmailFolder();
			searchFieldDisplayed=searchFieldDisplayed&&emailhome.isSearchFieldDisplayed();
			Thread.sleep(2000);
			
			if(searchFieldDisplayed)
			{
				logger.log(LogStatus.PASS, "Default Selection of the filter drop down is All");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "All option is not selected by default");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			
			// Enter one term in the Search Field and verify
			
			
			
			emailhome.clickInboxFolder();
			Thread.sleep(2000);
			
			driver.navigate().refresh();
			
			emailhome.enterSearchField(tabledata.get("Search_OneTerm"),driver);
			Thread.sleep(10000);
			if(emailhome.verifySearchedSubject(tabledata.get("Search_OneTerm"),driver)){
				logger.log(LogStatus.PASS, "Verified the email content after searching with one term:  " + tabledata.get("Search_OneTerm"));
				logger.log(LogStatus.PASS, "Number of mails filtered after search " + emailhome.resultsCount(driver));
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Email content is not filtered after searching with one term:  " + tabledata.get("Search_OneTerm"));
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			
	        // Enter more than one term separated by spaces in the Search Field and verify 
			
			
			driver.navigate().refresh();
			Thread.sleep(2000);
			emailhome.enterSearchField(tabledata.get("Search_Multipleterm"),driver);
			Thread.sleep(10000);
			if(emailhome.verifySearchedSubject(tabledata.get("Search_OneTerm"),driver)){
				logger.log(LogStatus.PASS, "Verified the email content after searching with more than one term :  " +tabledata.get("Search_Multipleterm") );
				logger.log(LogStatus.PASS, "Number of mails filtered after search " + emailhome.resultsCount(driver));
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Email content is not filtered when searching with more than one term:  " + tabledata.get("Search_Multipleterm"));
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			
			// Verify search term in Subject, Senders in the current mail folder
			
			driver.navigate().refresh();
			Thread.sleep(2000);
			
			emailhome.enterSearchField(tabledata.get("Search_Subject"),driver);
			Thread.sleep(10000);
			if(emailhome.verifySearchedSubject(tabledata.get("Search_Subject"),driver)){
				logger.log(LogStatus.PASS, "Verified the email content after searching with subject content:   " + tabledata.get("Search_Subject"));
				logger.log(LogStatus.PASS, "Number of mails filtered after search " + emailhome.resultsCount(driver));
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Email content is not filtered when searching with subject content:  " + tabledata.get("Search_Subject"));
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			
			driver.navigate().refresh();
			Thread.sleep(2000);
			
			emailhome.enterSearchField(tabledata.get("Search_From"),driver);
			Thread.sleep(10000);
			if(emailhome.verifySearchedFrom(tabledata.get("Search_From"))){
				logger.log(LogStatus.PASS, "Verified the email content after searching with From content:  " + tabledata.get("Search_From"));
				logger.log(LogStatus.PASS, "Number of mails filtered after search " + emailhome.resultsCount(driver));
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Email content is not filtered when searching with From content:  " + tabledata.get("Search_From"));
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			
		
			
			
		
		
		
		}
		
		
		catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
		}
	}

	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "Email",this.getClass().getSimpleName() , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Email",this.getClass().getSimpleName() , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Email",this.getClass().getSimpleName() , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}

}
