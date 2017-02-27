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

public class ComposeandDraftEmail extends Browser_Factory{

	String path="./src/main/resources/Resources/PS_TestData.xlsx";
	public ExtentTest logger;
	String resultFlag="pass";

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "Email","Email","Compose Email");
	}

	@Test(dataProvider="getData")	
	public void emailCompose(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Email").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		try{
			
			logger=extent.startTest(this.getClass().getSimpleName(),"Advanced Search --ID Num");
			
			
			String currIPAddress=Generic_Class.getIPAddress();
			
			System.out.println("current ip address  " + currIPAddress);
			
			DataBase_JDBC.executeSQLQuery("Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid=2211");
		    Thread.sleep(5000);
		    
		    DataBase_JDBC.executeSQLQuery("Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid=142");
		    Thread.sleep(5000);
		    
		    DataBase_JDBC.executeSQLQuery("Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and paramvalue="+currIPAddress);
		    Thread.sleep(5000);
		    
		    
			String updateQuery2 = "Update siteparameter set paramvalue='"+currIPAddress+"' where paramcode='IP_COMPUTER_FIRST' and siteid=2211";
		    DataBase_JDBC.executeSQLQuery(updateQuery2);
            Thread.sleep(5000);
            
            logger.log(LogStatus.INFO, "Switched the current System to the Site ID :  2211");
			
			
			

			//Login to PS Application
		
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as PM");
			String scpath, image;
			
		/*	Thread.sleep(10000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);*/
			
			try{
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
			
			js.executeScript("window.scrollBy(2000,0)", "");
			Thread.sleep(5000);
			
			//Click on the Create New Message button.
			InboxPage inbox = new InboxPage(driver);
			inbox.click_CreateNewMessage();
			Thread.sleep(5000);
			
			ComposeEmailPage compose = new ComposeEmailPage(driver);
			if(compose.exists_ComposeHeader()){
				logger.log(LogStatus.PASS, "After clicking on Create New Message button, navigated to Compose Email page");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "After clicking on Create New Message button, navigated to Compose Email page");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			//Click any where on “To:” or “Cc:” field
			compose.click_To_AddRecipient();
			Thread.sleep(5000);
			
			ContactPage contact = new ContactPage(driver);
			if(contact.exists_ContactHeader()){
				logger.log(LogStatus.PASS, "Contact pop-up window displayed after clicking on Add Recipient button");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Contact pop-up window not displayed after clicking on Add Recipient button");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			//Select the email address of any department under the additional contacts.
			contact.click_ExpandContactButton();
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//div[@id='SelectEmailDialog']/div[contains(@class,'address-book-grid')]//table/tbody/tr[2]/td[5]/label/span[@class='button']")).click();
			Thread.sleep(2000);
			
			contact.click_SaveBtn();
			Thread.sleep(5000);
			
			String text = compose.getData_ToField();
			if(!text.equals("")){
				logger.log(LogStatus.PASS, "Selected email address added to To field");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Selected email address not added to To field");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			//Enter more than one recipients in To field
			compose.click_cc_AddRecipient();
			Thread.sleep(5000);
			
			contact.click_ExpandContactButton();
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//div[@id='SelectEmailDialog']/div[contains(@class,'address-book-grid')]//table/tbody/tr[3]/td[5]/label/span[@class='button']")).click();
			Thread.sleep(2000);
			
			contact.click_SaveBtn();
			Thread.sleep(5000);
			
			logger.log(LogStatus.PASS, "Multiple recipients entered in To field");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			
			//Add the subject to subject field.
			Random r = new Random();
			int value = r.nextInt(10000000);
			String subject = "Test Mail "+value;
			compose.enter_Subject(subject);
			
			logger.log(LogStatus.PASS, "Added the subject");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			
			//Enter the email text in the email body .
			String message = "Test Message "+value;
			compose.enter_MailBody(message,driver);
			
			logger.log(LogStatus.PASS, "Entered text in the body");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			
			//Click on Save to Drafts button.
			
			compose.clickOnSavetoDrafts();
			
			logger.log(LogStatus.PASS, "Clicked on Save to Drafts button");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			
			
			Thread.sleep(3000);

			//Verify if the email is saved in Drafts Section
			
			boolean draftVerify=compose.verifyDraftMail();
			if(draftVerify)
			{
				logger.log(LogStatus.PASS, "Verified email in the Drafts Section");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Email is not saved in the drafts section");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			
//--------------------------------Enter in To Field without entering in the body and click on Save to Drafts Button---------------------------------
			
			
			
			//Click on the Create New Message button.
			inbox.click_CreateNewMessage();
			Thread.sleep(5000);
			
			
			if(compose.exists_ComposeHeader()){
				logger.log(LogStatus.PASS, "After clicking on Create New Message button, navigated to Compose Email page");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "After clicking on Create New Message button, navigated to Compose Email page");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			//Click any where on “To:” or “Cc:” field
			compose.click_To_AddRecipient();
			Thread.sleep(5000);
			
			
			if(contact.exists_ContactHeader()){
				logger.log(LogStatus.PASS, "Contact pop-up window displayed after clicking on Add Recipient button");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Contact pop-up window not displayed after clicking on Add Recipient button");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			//Select the email address of any department under the additional contacts.
			contact.click_ExpandContactButton();
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//div[@id='SelectEmailDialog']/div[contains(@class,'address-book-grid')]//table/tbody/tr[2]/td[5]/label/span[@class='button']")).click();
			Thread.sleep(2000);
			
			contact.click_SaveBtn();
			Thread.sleep(5000);
			
			String text2 = compose.getData_ToField();
			if(!text.equals("")){
				logger.log(LogStatus.PASS, "Selected email address added to To field");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Selected email address not added to To field");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			//Enter more than one recipients in To field
			compose.click_cc_AddRecipient();
			Thread.sleep(5000);
			
			contact.click_ExpandContactButton();
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//div[@id='SelectEmailDialog']/div[contains(@class,'address-book-grid')]//table/tbody/tr[3]/td[5]/label/span[@class='button']")).click();
			Thread.sleep(2000);
			
			contact.click_SaveBtn();
			Thread.sleep(5000);
			
			logger.log(LogStatus.PASS, "Multiple recipients entered in To field");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			
			//Add the subject to subject field.
			value = r.nextInt(10000000);
			subject = "Test Mail "+value;
			compose.enter_Subject(subject);
			
			logger.log(LogStatus.PASS, "Added the subject");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			
			
			//Click on Save to Drafts button.
			
			compose.clickOnSavetoDrafts();
			
			logger.log(LogStatus.PASS, "Clicked on Save to Drafts button");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			
			
			Thread.sleep(3000);

			//Verify if the email is saved in Drafts Section
			
			draftVerify=compose.verifyDraftMail();
			if(draftVerify)
			{
				logger.log(LogStatus.PASS, "Verified email in the Drafts Section");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Email is not saved in the drafts section");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			
			
			
			//Click on Send button and Verify if the Email is sent Successfully
			
			emailhome.openmailfromDraft();
			logger.log(LogStatus.PASS, "Opened Email from Drafts section");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			
			Thread.sleep(3000);

			compose.clickSendButton();
			logger.log(LogStatus.PASS, "Clicked on Send button");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			
			Thread.sleep(3000);
			
			emailhome.openSentEmailsMenu();
			logger.log(LogStatus.PASS, "Clicked on Sent Emails Menu");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			Thread.sleep(3000);
			
			
			emailhome.openmailfromSent();
			logger.log(LogStatus.PASS, "Opened email from Sent Items");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			Thread.sleep(3000);
			
			
			boolean sentEmail=emailhome.verifySentMail();
			if(sentEmail){
			logger.log(LogStatus.PASS, "Verified that the Email is Sent");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);}
			else{
				logger.log(LogStatus.FAIL, "Email is not sent");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
				}
			Thread.sleep(3000);
			
			
			
		
			
			
	
			
			String updateQuery3 = "Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid=2211";
			  DataBase_JDBC.executeSQLQuery(updateQuery3);
			  Thread.sleep(3000);
			  
			String updateQuery4 = "Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid=142";
			  DataBase_JDBC.executeSQLQuery(updateQuery4);
			  Thread.sleep(3000);
			  
			  
			String updateQuery5= "Update siteparameter set paramvalue='"+currIPAddress+"' where paramcode='IP_COMPUTER_FIRST' and siteid=142";
			
			  DataBase_JDBC.executeSQLQuery(updateQuery5);
			  Thread.sleep(3000);
			  
			  logger.log(LogStatus.INFO, "Switched the current System to Site ID :  142 ");
			  
			  
			  
			 
			  homepage.logout(driver);
			  
			  

              Thread.sleep(2000);
              driver.get("http://wc2qa.ps.com/LogIn");
             /* driver.get(driver.getCurrentUrl());*/
              Thread.sleep(6000);  

			
			
			
              
        	
			
          	//Login to PS Application
      		
  			login.login(tabledata.get("UserName"),tabledata.get("Password"));
  			logger.log(LogStatus.INFO, "User logged in successfully as PM");
  		
  			
  		/*	Thread.sleep(10000);
  			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
  			Thread.sleep(5000);*/
  			
  			try{
  				Dashboard_BifrostHostPopUp Bifrostpop1 = new Dashboard_BifrostHostPopUp(driver);
  			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
  			Bifrostpop1.clickContiDevice();
  			Thread.sleep(10000);
  			}
  			
  			catch(Exception e){
  				System.out.println("");
  			}
  			
  			 
  			js.executeScript("window.scrollBy(0,450)", "");
  			Thread.sleep(2000);
  			
  			
  			//Click on the Email text in the Footer of the PM dashboard
  			
  			homepage.click_EmailLink();
  			Thread.sleep(5000);
  			
  			
  			isPresent_EmailHeader = emailhome.exists_EmailHeader();
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
			
  			try{
  			
  			emailhome.openemailfromInbox();
  			logger.log(LogStatus.INFO, "Opened the Email from Inbox  ");
  			}
  			catch(Exception e){
  				logger.log(LogStatus.FAIL, "Searched email is not present in the Inbox Folder");
  				 
  			}
  			Thread.sleep(5000);
  			
  			if(emailhome.verifyMailInbox()){
  				logger.log(LogStatus.PASS, "Verified email from the Inbox after switching to other site");
  				scpath=Generic_Class.takeScreenShotPath();
  				image=logger.addScreenCapture(scpath);
  				logger.log(LogStatus.INFO, "Image",image);
  			}else{
  				logger.log(LogStatus.FAIL, "Email is not present in the Inbox after Switching to Site");
  				scpath=Generic_Class.takeScreenShotPath();
  				image=logger.addScreenCapture(scpath);
  				logger.log(LogStatus.INFO, "Image",image);
  				resultFlag="fail";  				
  			}
			
			
			
		
			
			
			
			
			emailhome.clickDraftsFolder();
			logger.log(LogStatus.PASS, "Opened Drafts Folder ");
			Thread.sleep(2000);

			
			if(emailhome.verifyifOlderMessagesareDeleted(driver)){
				logger.log(LogStatus.PASS, "Messages older than 14 days are deleted from drafts folder");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);}
				else{
					logger.log(LogStatus.FAIL, "Messages older than 14 days are not deleted from Drafts Folder");
					scpath=Generic_Class.takeScreenShotPath();
					image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "Image",image);
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
