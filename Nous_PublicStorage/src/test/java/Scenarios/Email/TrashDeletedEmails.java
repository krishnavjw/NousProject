package Scenarios.Email;

import java.util.Hashtable;
import java.util.Random;

import org.openqa.selenium.By;
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
import Pages.EmailPages.ComposeEmailPage;
import Pages.EmailPages.ContactPage;
import Pages.EmailPages.EmailHomePage;
import Pages.EmailPages.InboxPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class TrashDeletedEmails extends Browser_Factory{

	String path="./src/main/resources/Resources/PS_TestData.xlsx";
	public ExtentTest logger;
	String resultFlag="pass";

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "Email","Email","TrashDeletedEmails");
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
			logger=extent.startTest(this.getClass().getSimpleName(),"Advanced Search --ID Num");
			
			
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
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
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
			}else{
				logger.log(LogStatus.FAIL, "Inbox folder is not displayed as default view");
				resultFlag="fail";
			}
			
			
			// Click on Single email and verify message in Inbox Folder
			
			emailhome.openEmailMessage();
			Thread.sleep(3000);
			if(emailhome.verifyMailMessageDisplayed()){
				logger.log(LogStatus.PASS, "Email Message is displayed in Inbox Folder");
					}else{
				logger.log(LogStatus.FAIL, "Email message is not displayed in the Inbox Folder");
				
				resultFlag="fail";
			}
			emailhome.clickBackButton();
			
			
			//Hover over any unread message and verify if Delete icon is displayed
			
	
			Thread.sleep(5000);
			js.executeScript("window.scrollBy(250,0)", "");
			emailhome.movetoDeleteIcon(driver);
			Thread.sleep(2000);
			if(emailhome.verifyDeleteIconDisplayed()){
				logger.log(LogStatus.PASS, "Delete Icon is displayed for email in Inbox folder");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Delete Icon is not displayed for email in Inbox folder");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			Thread.sleep(2000);
			String deletemailSubject=emailhome.click_Delete();
			logger.log(LogStatus.INFO, "Clicked on Delete button for the email ");
			Thread.sleep(5000);
			
			
			//Open Trash Folder
			emailhome.clickTrashFolder();
			Thread.sleep(2000);
			if(emailhome.verifyMailinTrashFolder(deletemailSubject,driver)){
				logger.log(LogStatus.PASS, "Verified the deleted email in Trash Folder");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Deleted Email is not displayed in the Trash Folder");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
		}
		
		catch(Exception ex)
		{
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
