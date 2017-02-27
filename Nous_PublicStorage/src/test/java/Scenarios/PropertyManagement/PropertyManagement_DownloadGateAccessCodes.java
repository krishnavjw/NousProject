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
import Pages.PropertyManagementPages.DownloadAllPropertyAccessCodesPopUp;
import Pages.PropertyManagementPages.DownloadCompletePopUp;
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

public class PropertyManagement_DownloadGateAccessCodes extends Browser_Factory{


	public ExtentTest logger;
	String resultFlag="fail";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"PropertyManagement","PropertyManagement",  "PropertyManagement_DownloadGateAccessCodes");
	}



	@Test(dataProvider="getLoginData")
	public void PropertyManagement_DownloadGateAccessCodes(Hashtable<String, String> tabledata) throws InterruptedException 
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
			logger=extent.startTest("PropertyManagement_DownloadGateAccessCodes","Property Management View Search GateAccessCodes ");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "PM Logged in successfully");

			JavascriptExecutor jse = (JavascriptExecutor)driver;

			//connecting to customer device
		/*	Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Reporter.log("object created successfully",true);
			Thread.sleep(5000);
			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);
						
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T); 
			
			Thread.sleep(4000);
			
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			Thread.sleep(4000);
			driver.switchTo().window(tabs.get(1));
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

			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			
			
			Thread.sleep(3000);
			driver.switchTo().window(tabs.get(0));

			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "clicked on continue successfully");   */
			
			
			
			Dashboard_BifrostHostPopUp bifPop= new Dashboard_BifrostHostPopUp(driver);
			Reporter.log("object created successfully",true);
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
			propAccess.clickDownloadAllPropAccessCode();
			logger.log(LogStatus.INFO,"Selected View Prop Access Codes radio button");
			
			propAccess.clickLaunch_btn();
			logger.log(LogStatus.INFO,"Clicked on Launch button");
			
			DownloadAllPropertyAccessCodesPopUp download= new DownloadAllPropertyAccessCodesPopUp(driver);
			
			Thread.sleep(2000);
			
			download.verifyEmployeeIDField();
			logger.log(LogStatus.PASS,"Verified Employee ID in the Download Pop up");
			
			download.verifyDownloadButton();
			logger.log(LogStatus.PASS,"Verified Download button in the Pop up");
			
			
			download.enterEmployeeId(tabledata.get("EmployeeId"));
			System.out.println("employee id " + tabledata.get("EmployeeId"));
			logger.log(LogStatus.INFO,"Entered Employee ID in the text field : " + tabledata.get("EmployeeId") );
			
			download.clickDownload_btn();
			logger.log(LogStatus.INFO,"Clicked on Download button ");
			
			
			Thread.sleep(20000);
			
			DownloadCompletePopUp popUp= new DownloadCompletePopUp(driver);
			
			if(popUp.verifyDownloadCompleted_title())
			{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Download Completed Popup is displayed sucessfully");
				logger.log(LogStatus.INFO, "Download Completed Popup is displayed sucessfully",image);
			}else{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Download Completed Popup is not displayed ");
				logger.log(LogStatus.INFO, "Download Completed Popup is not displayed ",image);
			}
			
			String msg=popUp.getDownloadedAccessCodeMeg();
			String numOfCodes=msg.substring(0,4);
			logger.log(LogStatus.PASS, "Sucessfully downloaded :"+ msg +"in PopUP");
			logger.log(LogStatus.PASS, "Number of Gate Codes Downloaded :"+ numOfCodes);
			
			String numOfCodesFromDb = popUp.verifyNumOfGateCodes();
			
			if(numOfCodes.equals(numOfCodesFromDb))
			{
				logger.log(LogStatus.PASS, "Number of Gate Codes Downloaded matched DB");
			}
				else{
					logger.log(LogStatus.FAIL, "Number of Gate Codes Downloaded does not match with DB");
			}
			
			popUp.clickOk_btn();
			

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
			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_DownloadGateAccessCodes" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_DownloadGateAccessCodes" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_DownloadGateAccessCodes" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);


	}


}
