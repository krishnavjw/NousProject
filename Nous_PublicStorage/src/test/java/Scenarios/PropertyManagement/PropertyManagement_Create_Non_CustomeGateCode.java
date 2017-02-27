package Scenarios.PropertyManagement;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
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
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.PropertyManagementPages.CreateNon_CustomerPropertyAccessCodePage;
import Pages.PropertyManagementPages.PropertyAccessManagementPopUp;
import Pages.PropertyManagementPages.PropertyManagementPage;
import Pages.PropertyManagementPages.ViewPropertyMgmtPage;
import Pages.Walkin_Reservation_Lease.BuyMerchandisePage;
import Pages.Walkin_Reservation_Lease.Leasing_AddNote;
import Pages.Walkin_Reservation_Lease.Leasing_AuthorizedAccessContactsPage;
import Pages.Walkin_Reservation_Lease.Leasing_ConfirmSpace;
import Pages.Walkin_Reservation_Lease.Leasing_ContactInfoPage;
import Pages.Walkin_Reservation_Lease.Leasing_EligiblePromotionsPage;
import Pages.Walkin_Reservation_Lease.Leasing_EmergencyConatctsPage;
import Pages.Walkin_Reservation_Lease.Leasing_LeaseQuestionairePage;
import Pages.Walkin_Reservation_Lease.Leasing_MilitaryInformationPage;
import Pages.Walkin_Reservation_Lease.Leasing_PaymentMethodsPage;
import Pages.Walkin_Reservation_Lease.Leasing_RentalFeePage;
import Pages.Walkin_Reservation_Lease.Leasing_ReviewNApprovePage;
import Pages.Walkin_Reservation_Lease.Leasing_TransactionCompletePage;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Scenarios.Browser_Factory;

public class PropertyManagement_Create_Non_CustomeGateCode extends Browser_Factory{


	public ExtentTest logger;
	String resultFlag="fail";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"PropertyManagement","PropertyManagement",  "PropertyManagement_Create_Non_CustomeGateCode");
	}



	@Test(dataProvider="getLoginData")
	public void PropertyManagement_Create_Non_CustomeGateCode(Hashtable<String, String> tabledata) throws InterruptedException 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("PropertyManagement").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Thread.sleep(5000);

		try
		{

			//Login to the application as PM 
			logger=extent.startTest("PropertyManagement_Create_Non_CustomeGateCode","Property Management View Search GateAccessCodes ");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "PM Logged in successfully");

			JavascriptExecutor jse = (JavascriptExecutor)driver;

			/*	//connecting to customer device
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Reporter.log("object created successfully",true);
			Thread.sleep(5000);
			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T); 
			Thread.sleep(5000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(5000);
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

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "clicked on continue successfully");*/


			Dashboard_BifrostHostPopUp bifPop= new Dashboard_BifrostHostPopUp(driver);
			Thread.sleep(5000);
			bifPop.clickContiDevice();

			Thread.sleep(4000);


			// Login into PM dashboard 
			PM_Homepage pm_home= new  PM_Homepage(driver);
			logger.log(LogStatus.INFO, "PM Home page object created successfully");

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			Thread.sleep(8000);
			pm_home.clickmanageProp();
			logger.log(LogStatus.INFO,"Clicked on Property Mgmt link");

			Thread.sleep(4000);
			PropertyManagementPage propmgmt= new PropertyManagementPage(driver);
			propmgmt.clickPropAccessMgmt();
			logger.log(LogStatus.INFO,"Clicked on Property Access Mgmt");

			Thread.sleep(8000);
			PropertyAccessManagementPopUp propAccess= new PropertyAccessManagementPopUp(driver);
			propAccess.clickCreateNonCustGateCode();
			logger.log(LogStatus.INFO,"Selected View Prop Access Codes radio button");

			propAccess.clickLaunch_btn();
			logger.log(LogStatus.INFO,"Clicked on Launch button");

			Thread.sleep(8000);
			CreateNon_CustomerPropertyAccessCodePage accessCodePage= new CreateNon_CustomerPropertyAccessCodePage(driver);
			logger.log(LogStatus.INFO, "PM Non Customer Property Access code Page object created successfully");

			accessCodePage.enterFirstName(tabledata.get("FirstName"));
			logger.log(LogStatus.INFO, "Entered First Name '"+ tabledata.get("FirstName") +"' in the Text Field");


			accessCodePage.enterLastName(tabledata.get("LastName"));
			logger.log(LogStatus.INFO, "Entered Last Name '" + tabledata.get("LastName") + "' in the Text Field");

			accessCodePage.enterCompanyName(tabledata.get("CompanyName"));
			logger.log(LogStatus.INFO, "Entered Company Name '" + tabledata.get("CompanyName") + "' in the Text Field");

			accessCodePage.selectPhoneType(tabledata.get("PhoneType"));
			logger.log(LogStatus.INFO, "Selected phone type '"+ tabledata.get("PhoneType") + "' from the drop down field");


			accessCodePage.enterPhoneAreaCode(tabledata.get("AreaCode"));
			logger.log(LogStatus.INFO, "Entered Area Code '" + tabledata.get("AreaCode") + "'in the Text Field");

			accessCodePage.enterPhoneExchange(tabledata.get("Exchange"));
			logger.log(LogStatus.INFO, "Entered Exchange '"+ tabledata.get("Exchange") +"'in the Text Field");

			accessCodePage.enterPhoneLineNum(tabledata.get("PhoneLineNum"));
			logger.log(LogStatus.INFO, "Entered PhoneLineNum '"+ tabledata.get("PhoneLineNum") +"' in the Text Field");

			accessCodePage.enterEmail(tabledata.get("Email"));
			logger.log(LogStatus.INFO, "Entered Email '" + tabledata.get("Email") + "' in the Text Field");


			if(accessCodePage.verifyZone())
			{
			accessCodePage.selectAccessType("00");
			logger.log(LogStatus.INFO, "Selected Access Type from the drop down field");
			Thread.sleep(3000);

			}else
			{
				logger.log(LogStatus.INFO, "Access Zone is not displayed");
			}
			
			/*driver.findElement(By.xpath("//div[@class='js-access-zone clearfix-container']//span[@class='k-dropdown-wrap k-state-default']")).click();
			Thread.sleep(3000);

			List<WebElement> accessZone = driver.findElements(By.xpath("//ul[@id='AccessZone_listbox']/li[@class='k-item']"));

			for(WebElement accessZoneValue: accessZone)
			{
				Thread.sleep(3000);	
				accessZoneValue.click();
				break;
			}

			driver.findElement(By.xpath("//div[@class='access-type-container floatleft']//span[@class='k-widget k-dropdown k-header']")).click();

			Thread.sleep(3000);

			List<WebElement> accessType= driver.findElements(By.xpath("//ul[@id='AccessType_listbox']/li[@class='k-item']"));

			for(WebElement accessTypeValue: accessType)
			{
				Thread.sleep(3000);	
				accessTypeValue.click();
				break;
			}	*/

			if(accessCodePage.verifyYesRadio_Btn() & accessCodePage.verifyNoRadio_Btn()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Auto Generate Code radio buttons displayed");
				logger.log(LogStatus.PASS, "Auto Generate Code radio buttons displayed",image);

			}else{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Auto Generate Code radio buttons not displayed");
				logger.log(LogStatus.FAIL, "Auto Generate Code radio buttons not displayed",image);

			}

			accessCodePage.clickNoRadio_Btn();
			logger.log(LogStatus.INFO, "Clicked on No Radio button");

			accessCodePage.entergateCode(tabledata.get("GateCode_Duplicate"));
			logger.log(LogStatus.INFO, "Entered Duplicate Gate Code in the Text Field : " + tabledata.get("GateCode_Duplicate"));


			accessCodePage.enterEmployeeId(tabledata.get("EmployeeId"));
			logger.log(LogStatus.INFO, "Entered Employee ID in the text field: "+ tabledata.get("EmployeeId"));

			accessCodePage.click_createButton();
			logger.log(LogStatus.INFO, "Clicked on Create Button ");

			Thread.sleep(2000);

			if(accessCodePage.verifyDuplicateGateCodeMessage()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Duplicate Gate Code Message is displayed");
				logger.log(LogStatus.PASS, "Duplicate Gate Code Message is displayed",image);

			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Duplicate Gate Code Message is not displayed");
				logger.log(LogStatus.FAIL, "Duplicate Gate Code Message is not displayed",image);

			}


			accessCodePage.entergateCode(tabledata.get("GateCode_New"));
			logger.log(LogStatus.INFO, "Entered New Gate Code in the Text Field : "+ tabledata.get("GateCode_New"));


			accessCodePage.click_createButton();
			logger.log(LogStatus.INFO, "Clicked on Create Button ");

			Thread.sleep(2000);


			if(accessCodePage.verify_newGateCodeMessage()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Autogenerate Code Selected as No, Manually entered and Non Customer Property Access Gate created Message is displayed");
				logger.log(LogStatus.PASS, "Non Customer Property Access Code created Message is displayed",image);

			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Autogenerate Code Selected as No, Manually entered and Non Customer Property Access Code created Message is not displayed");
				logger.log(LogStatus.FAIL, "Non Customer Property Access Code created Message is not displayed",image);

			}

			accessCodePage.click_OKButton();

			Thread.sleep(2000);







			propmgmt.clickPropAccessMgmt();
			logger.log(LogStatus.INFO,"Clicked on Property Access Mgmt");

			Thread.sleep(8000);

			propAccess.clickCreateNonCustGateCode();
			logger.log(LogStatus.INFO,"Selected View Prop Access Codes radio button");

			propAccess.clickLaunch_btn();
			logger.log(LogStatus.INFO,"Clicked on Launch button");

			Thread.sleep(8000);



			accessCodePage.enterFirstName(tabledata.get("FirstName"));


			accessCodePage.enterLastName(tabledata.get("LastName"));

			accessCodePage.enterCompanyName(tabledata.get("CompanyName"));

			accessCodePage.selectPhoneType(tabledata.get("PhoneType"));


			accessCodePage.enterPhoneAreaCode(tabledata.get("AreaCode"));

			accessCodePage.enterPhoneExchange(tabledata.get("Exchange"));

			accessCodePage.enterPhoneLineNum(tabledata.get("PhoneLineNum"));

			accessCodePage.enterEmail(tabledata.get("Email"));

				if(accessCodePage.verifyZone())
			{
				accessCodePage.selectAccessType("01");
				Thread.sleep(3000);

			}else
			{
			}
			 
			/*driver.findElement(By.xpath("//div[@class='js-access-zone clearfix-container']//span[@class='k-dropdown-wrap k-state-default']")).click();
			Thread.sleep(3000);

			 accessZone = driver.findElements(By.xpath("//ul[@id='AccessZone_listbox']/li[@class='k-item']"));

			for(WebElement accessZoneValue: accessZone)
			{
				Thread.sleep(3000);	
				accessZoneValue.click();
				break;
			}

			driver.findElement(By.xpath("//div[@class='access-type-container floatleft']//span[@class='k-widget k-dropdown k-header']")).click();

			Thread.sleep(3000);

			 accessType= driver.findElements(By.xpath("//ul[@id='AccessType_listbox']/li[@class='k-item']"));

			for(WebElement accessTypeValue: accessType)
			{
				Thread.sleep(3000);	
				accessTypeValue.click();
				break;
			}	*/



			accessCodePage.clickYesRadio_Btn();
			logger.log(LogStatus.INFO, "Clicked on Yes Radio button");

			accessCodePage.enterEmployeeId(tabledata.get("EmployeeId"));
			logger.log(LogStatus.INFO, "Entered Employee ID in the text field");

			accessCodePage.click_createButton();
			logger.log(LogStatus.INFO, "Clicked on Create Button ");

			Thread.sleep(5000);

			if(accessCodePage.verify_newGateCodeMessage()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "When Autogenerate Code is selected as Yes, Non Customer Property Access Code created Message is displayed");
				logger.log(LogStatus.PASS, "Non Customer Property Access Code created Message is displayed",image);

			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "When Autogenerate Code is selected as Yes, Non Customer Property Access Code created Message is not displayed");
				logger.log(LogStatus.FAIL, "Non Customer Property Access Code created Message is not displayed",image);

			}





		}catch(Exception ex){
			ex.printStackTrace();
			//In the catch block, set the variable resultFlag to “fail”
			resultFlag="fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Validating Monthly rent and Promotions in Eligible Promotion Page",image);
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
		}


	}


	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_Create_Non_CustomeGateCode" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_Create_Non_CustomeGateCode" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_Create_Non_CustomeGateCode" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);


	}


}
