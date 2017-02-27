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

public class EmailInbox extends Browser_Factory{

	String path="./src/main/resources/Resources/PS_TestData.xlsx";
	public ExtentTest logger;
	String resultFlag="pass";

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "Email","Email","Email Inbox");
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
			Thread.sleep(8000);
			
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
			
						
			//Verify the header elements of the screen in the left.
			
			boolean isLeftHeaderElementDisplayed=emailhome.isleftHeaderElementsDisplayed();
			if(isLeftHeaderElementDisplayed){
				logger.log(LogStatus.PASS, "Header elements of the Screen in left are displayed");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Header elements of the Screen in left are not displayed");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			
			boolean isRightHeaderElementDisplayed=emailhome.isRightHeaderElementDisplayed();
			if(isRightHeaderElementDisplayed){
				logger.log(LogStatus.PASS, "Header elements of the Screen in Right are displayed");
				
			}else{
				logger.log(LogStatus.FAIL, "Header elements of the Screen in Right are not displayed");
				resultFlag="fail";
			}
			
			// Verify the property 'Unread messages' in Email
			
			
			boolean isInboxUnreadCountDisplayed=emailhome.isInboxUnreadCountDisplayed();
			if(isInboxUnreadCountDisplayed){
				logger.log(LogStatus.PASS, "Unread count in Inbox is displayed");
				
			}else{
				logger.log(LogStatus.FAIL, "Unread count in Inbox is not displayed");
				
				resultFlag="fail";
			}
			
			
			//Verify that only one set of email folders are displayed
			
			boolean isemailFoldersDisplayed=emailhome.isInboxFolderDisplayed();
			isemailFoldersDisplayed=emailhome.isDraftsFolderDisplayed();
			isemailFoldersDisplayed=emailhome.isSentFolderDisplayed();
			isemailFoldersDisplayed=emailhome.isTrashFolderDisplayed();
			
			if(isemailFoldersDisplayed){
				logger.log(LogStatus.PASS, "Only one set of email folders are displayed");
				
			}else{
				logger.log(LogStatus.FAIL, "email folders are not displayed as expected");
				
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
			
			// Verify if Inbox folder is highlighted indicating user is on Inbox Page
			
			boolean isInboxFolderHighlighted = emailhome.isinboxFolderHighlighted();
			if(isInboxFolderHighlighted){
				logger.log(LogStatus.PASS, "Inbox folder is highlighted by default");
				
			}else{
				logger.log(LogStatus.FAIL, "Inbox folder is not highlighted by default");
				
				resultFlag="fail";
			}
			
			// Verify that each email message are listed individually in the message area.
			
			boolean isEmailMessagesDisplayed = emailhome.isEmailMessagesDisplayed();
			if(isEmailMessagesDisplayed){
				logger.log(LogStatus.PASS, "Email Messages are listed individually in the message area");
				
			}else{
				logger.log(LogStatus.FAIL, "Email Messages are not listed individually in the message area");
				
				resultFlag="fail";
			}
			
			
			// Verify if filter text is available with drop down selection
			
			boolean isFilterTextAvailable=emailhome.isFilterTextDisplayed();
			if(isFilterTextAvailable){
				logger.log(LogStatus.PASS, "Filter text is available with drop down selection ");
				
			}else{
				logger.log(LogStatus.FAIL, "Filter text is not available with drop down selection");
				
				resultFlag="fail";
			}
			
			
			// Click on the Filter drop down and verify the options available
			
			emailhome.click_FilterDropDown();
			
			boolean verifyingDropDownValues=emailhome.verifyFilterDropDownValues(driver);
			if(verifyingDropDownValues){
				logger.log(LogStatus.PASS, "Options are displayed properly in the Filter Drop Down ");
				
			}else{
				logger.log(LogStatus.FAIL, "Options are not displayed from the filter drop down");
				
				resultFlag="fail";
			}
			
			
			// Verify that default selection of the filter drop down is All option.
			
			boolean verifyDefaultSelectionFromDropDown=emailhome.verifyDefaultSelectionFromDropDown();
			if(verifyDefaultSelectionFromDropDown){
				logger.log(LogStatus.PASS, "Default Selection of the filter drop down is All");
				
			}else{
				logger.log(LogStatus.FAIL, "All option is not selected by default");
				
				resultFlag="fail";
			}
			
			
			//Verify if Search fiels is available on all the folder pages
			
			emailhome.clickInboxFolder();
			boolean searchFieldDisplayed=emailhome.isSearchFieldDisplayed();
			
			emailhome.clickDraftsFolder();
			searchFieldDisplayed=searchFieldDisplayed&&emailhome.isSearchFieldDisplayed();
			
			emailhome.clickSentEmailFolder();
			searchFieldDisplayed=searchFieldDisplayed&&emailhome.isSearchFieldDisplayed();
			
			emailhome.clickSentEmailFolder();
			searchFieldDisplayed=searchFieldDisplayed&&emailhome.isSearchFieldDisplayed();
			
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
			
			
			//Verify if New Message button is available
			
			if(emailhome.isNewMessageButtonDisplayed()){
				logger.log(LogStatus.PASS, "New Message button is displayed in the Email Home Page");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "New Message button is not displayed in the Email Home Page");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			
			// Verify if number of unread messages for the user is displayed next to the Inbox
			
			emailhome.clickInboxFolder();
			Thread.sleep(2000);
			if(emailhome.verifyUnreadEmailCountInbox(driver)){
				logger.log(LogStatus.PASS, "Unread messages for the user is displayed next to Inbox");
				
			}else{
				logger.log(LogStatus.FAIL, "Unread messages count is not displayed to the inbox");
				
				resultFlag="fail";
			}
			
			
			
			// Verify if the following columns are displayed in the Inbox Area
			
			if(emailhome.verifyColumnsinInboxPage()){
				logger.log(LogStatus.PASS, "Flag,Attachment,From,Subject & Date columns are displayed properly in the Inbox Area");
				
			}else{
				logger.log(LogStatus.FAIL, "Columns are not displayed properly in the Inbox Area");
				
				resultFlag="fail";
			}
			
			
			// Verify if the messages in Inbox page are sorted from newest to oldest
			
			if(emailhome.verifySortingofDates(driver)){
				logger.log(LogStatus.PASS, "Dates are sorted from newest to oldest");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Dates are not sorted from newest to oldest");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			
			// Verify the content of Flag Column
			
			if(emailhome.verifyFlagColumnContent()){
				logger.log(LogStatus.PASS, "Flag Column content is displaying an icon");
				
			}else{
				logger.log(LogStatus.FAIL, "Flag column content is not displaying an icon");
				
				resultFlag="fail";
			}
			
			
			//Click on Flag Column header and verify if messages are sorted by flagged/un-flagged content
			
			emailhome.click_flagColumnHeader();
			if(emailhome.verifySortingByFlag(driver)){
				logger.log(LogStatus.PASS, "messages are sorted by flag/unflag");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "messages are not sorted by flag/unflag");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			//Click on Flag Area for a message in the Inbox folder
			emailhome.click_flagColumnContent();
			Thread.sleep(3000);
			emailhome.verifyFlagStatusUpdate();
			
			
			//Verify the content of Attachment Column 
			emailhome.isAttachmentContentDisplayed();
			Thread.sleep(3000);
			
			//Click Attachment Column Header and verify the sorting order
			emailhome.clickAttachmentColumnHeaderDisplayed();
			Thread.sleep(2000);
			if(emailhome.verifySortingByAttachment(driver)){
				logger.log(LogStatus.PASS, "messages are sorted by AttachmentColumn Header");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "messages are not sorted by Attachment Column Header");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			
			// Verify content present in the From Column
			
			if(emailhome.isfromColumnContentDisplayed()){
				logger.log(LogStatus.PASS, "From Column content is displayed");
				
			}else{
				logger.log(LogStatus.FAIL, "From column content is not displayed");
				
				resultFlag="fail";
			}
			
			
			
			// Verify if icon is displayed to indicate Forwarded to or Reply to 
			
			if(emailhome.isIconStatusDisplayed()){
				logger.log(LogStatus.PASS, "Email Status Icon is displayed");
				
			}else{
				logger.log(LogStatus.FAIL, "Email Status Icon is not displayed");
				
				resultFlag="fail";
			}
			
			// Verify content of Subject Column
			
			if(emailhome.isSubjectContentDisplayed()){
				logger.log(LogStatus.PASS, "Email Subject content is displayed");
				
			}else{
				logger.log(LogStatus.FAIL, "Email Subject content is not displayed");
				
				resultFlag="fail";
			}
		
			
			// Verify content of Date Column
			
			if(emailhome.isDateColumnContentDisplayed()){
				logger.log(LogStatus.PASS, "Email Date content is displayed");
				
			}else{
				logger.log(LogStatus.FAIL, "Email Date content is not displayed");
				
				resultFlag="fail";
			}
			
			
			// Verify if script is displayed for Email Expiration
			
			if(emailhome.isEmailExpirationDisplayed()){
				logger.log(LogStatus.PASS, "Email expiration content is displayed");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Email expiration content is not displayed");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			
			// Verify if pop up script is shown on moving over location
			
			if(emailhome.islocationNumberDisplayed()){
				logger.log(LogStatus.PASS, "location number content is displayed");
				
			}else{
				logger.log(LogStatus.FAIL, "location number content is not displayed");
				
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
