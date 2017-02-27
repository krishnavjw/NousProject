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

public class PropertyManagement_View_SearchGateAccessCodes extends Browser_Factory{


	public ExtentTest logger;
	String resultFlag="fail";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"PropertyManagement","PropertyManagement",  "PropertyManagement_View_SearchGateAccessCodes");
	}



	@Test(dataProvider="getLoginData")
	public void propertyManagement_View_SearchGateAccessCodes(Hashtable<String, String> tabledata) throws InterruptedException 
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
			logger=extent.startTest("PropertyManagement_View_SearchGateAccessCodes","Property Management View Search GateAccessCodes ");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "PM Logged in successfully");

			JavascriptExecutor jse = (JavascriptExecutor)driver;

		
			
			
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
			propAccess.clickViewPropAccessCodes();
			logger.log(LogStatus.INFO,"Selected View Prop Access Codes radio button");
			
			propAccess.clickLaunch_btn();
			logger.log(LogStatus.INFO,"Clicked on Launch button");
			
			Thread.sleep(8000);
			ViewPropertyMgmtPage viewprop= new ViewPropertyMgmtPage(driver);
			
			viewprop.enterSpaceNumber(tabledata.get("SpaceNum_Inactive"));
			logger.log(LogStatus.INFO,"entered Space NUmber");
			
			
			
			viewprop.enterFirstName(tabledata.get("Inactive_FirstName"));
			logger.log(LogStatus.INFO,"entered First Name in the Text Field");
			
			
			viewprop.enterLastName(tabledata.get("Inactive_LastName"));
			logger.log(LogStatus.INFO,"entered Last Name in the Text field");
			
			viewprop.clickSearch_btn();
			logger.log(LogStatus.INFO,"Clicked on Search button");
			
			if(viewprop.verifyActiveInactiveStatus(tabledata.get("SpaceNum_Inactive"),tabledata.get("Inactive_FirstName"),tabledata.get("Inactive_LastName"))){
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Verified that the searched Account is inactive");
				logger.log(LogStatus.PASS, "Verified that the searched Account is inactive",image);

			}else{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Searched Account is not inactive");
				logger.log(LogStatus.FAIL, "Searched Account is not inactive",image);

			}
			
	
			viewprop.clear_Search();
			logger.log(LogStatus.INFO, "Cleared the Search Field");
			
			
			viewprop.enterSpaceNumber(tabledata.get("SpaceNum_Active"));
			logger.log(LogStatus.INFO,"entered Space NUmber");
			
			
			
			viewprop.enterFirstName(tabledata.get("Active_FirstName"));
			logger.log(LogStatus.INFO,"entered First Name in the Text Field");
			
			
			viewprop.enterLastName(tabledata.get("Active_LastName"));
			logger.log(LogStatus.INFO,"entered Last Name in the Text field");
			
			viewprop.clickSearch_btn();
			logger.log(LogStatus.INFO,"Clicked on Search button");
			
			if(viewprop.verifyActiveInactiveStatus(tabledata.get("SpaceNum_Active"),tabledata.get("Active_FirstName"),tabledata.get("Active_LastName"))){
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Verified that the searched Account is Active");
				logger.log(LogStatus.PASS, "Verified that the searched Account is Active",image);

			}else{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Searched Account is not Active");
				logger.log(LogStatus.FAIL, "Searched Account is not Active",image);

			}
			
			
			if(viewprop.verifycustomerTypeColumn(tabledata.get("SpaceNum_Active"),tabledata.get("Active_FirstName"),tabledata.get("Active_LastName"))){
				
				logger.log(LogStatus.PASS, "Verified that the Customer Type Column is matching with DB");
			}
			else{
				logger.log(LogStatus.PASS, "Customer Type Column is not matching with DB");
			}
			
			
			if(viewprop.verifyNameColumn(tabledata.get("SpaceNum_Active"),tabledata.get("Active_FirstName"),tabledata.get("Active_LastName"))){
				
				logger.log(LogStatus.PASS, "Verified that the Name Column is matching with DB");
			}
			else{
				logger.log(LogStatus.PASS, "Name Column is not matching with DB");
			}
			
			
			if(viewprop.verifyCompanyName(tabledata.get("SpaceNum_Active"),tabledata.get("Active_FirstName"),tabledata.get("Active_LastName"))){
				
				logger.log(LogStatus.PASS, "Verified that the Company Name is matching with DB");
			}
			else{
				logger.log(LogStatus.PASS, "Company Name Column is not matching with DB");
			}
			
			
			if(viewprop.verifySpaceName(tabledata.get("SpaceNum_Active"),tabledata.get("Active_FirstName"),tabledata.get("Active_LastName"))){
				
				logger.log(LogStatus.PASS, "Verified that the Space Name is matching with DB");
			}
			else{
				logger.log(LogStatus.PASS, "Space Name Column is not matching with DB");
			}
			
	
	
			if(viewprop.verifyGateCodeName(tabledata.get("SpaceNum_Active"),tabledata.get("Active_FirstName"),tabledata.get("Active_LastName"))){
		
				logger.log(LogStatus.PASS, "Verified that the Gate Code Name is matching with DB");
			}
			else{
				logger.log(LogStatus.PASS, "Gate Code Name Column is not matching with DB");
			}
			
			
		
			if(viewprop.verifyaccessTypeColumn(tabledata.get("SpaceNum_Active"),tabledata.get("Active_FirstName"),tabledata.get("Active_LastName"))){
				
				logger.log(LogStatus.PASS, "Verified that the Access Type is matching with DB");
			}
			else{
				logger.log(LogStatus.PASS, "Access Type is not matching with DB");
			}
			
			
            if(viewprop.verifyaccessZoneColumn(tabledata.get("SpaceNum_Active"),tabledata.get("Active_FirstName"),tabledata.get("Active_LastName"))){
				
				logger.log(LogStatus.PASS, "Verified that the Access Zone is matching with DB");
			}
			else{
				logger.log(LogStatus.PASS, "Access Zone is not matching with DB");
			}
			
			viewprop.clear_Search();
			logger.log(LogStatus.INFO, "cleared Search Field");
			
			Thread.sleep(2000);
			
			viewprop.clickAccessStatus();
			Thread.sleep(1000);
			viewprop.clickAccessStatus_Active();
			
			viewprop.clickSearch_btn();
			
			Thread.sleep(10000);
			
			
			if(viewprop.verifyDefaultSorting(tabledata.get("SpaceNum_Active"),tabledata.get("Active_FirstName"),tabledata.get("Active_LastName"))){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Sorted by default using Status,Customer Type and Name");
				logger.log(LogStatus.PASS, "Sorted by default using Status,Customer Type and Name",image);
			}
			else{
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Data are not sorted by default using Status,Customer Type and Name");
				logger.log(LogStatus.FAIL, "Data are not sorted by default using Status,Customer Type and Name",image);
				
			}
			
			//mohana
			
			viewprop.clear_Search();
			logger.log(LogStatus.INFO, "cleared Search Field");
			
			Thread.sleep(2000);
			
			viewprop.clickAccessStatus();
			Thread.sleep(1000);
			viewprop.clickAccessStatus_Inactive();
			
			viewprop.clickSearch_btn();
			
			Thread.sleep(10000);
			
			
			if(viewprop.verifyDefaultSorting(tabledata.get("SpaceNum_Inactive"),tabledata.get("Inactive_FirstName"),tabledata.get("Inactive_LastName"))){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Sorted by default using Status,Customer Type and Name");
				logger.log(LogStatus.PASS, "Sorted by default using Status,Customer Type and Name",image);
			}
			else{
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Data are not sorted by default using Status,Customer Type and Name");
				logger.log(LogStatus.FAIL, "Data are not sorted by default using Status,Customer Type and Name",image);
				
			}
			
			//mohana
			viewprop.click_customerTypeHeader();
			
			Thread.sleep(2000);
			
			if(viewprop.verifySortingbyCustomerType()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Verified Sorting by Customer Type");
				logger.log(LogStatus.PASS, "Verified Sorting by Customer Type",image);
			}
			else{
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Data are not sorted by Customer Type");
				logger.log(LogStatus.FAIL, "Data are not sorted by Customer Type",image);
				
			}
				
			viewprop.click_nameHeader();
			Thread.sleep(2000);
				
			if(viewprop.verifySortingbyName()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Verified Sorting by Name");
				logger.log(LogStatus.PASS, "Verified Sorting by Name",image);
			}
			else{
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Data are not sorted by Name");
				logger.log(LogStatus.FAIL, "Data are not sorted by Name",image);
				
			}

			
			viewprop.click_CompanyNameHeader();
			Thread.sleep(2000);
			
			if(viewprop.verifySortingbyCompanyName()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Verified Sorting by Company Name");
				logger.log(LogStatus.PASS, "Verified Sorting by Company Name",image);
			}
			else{
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Data are not sorted by Company Name");
				logger.log(LogStatus.FAIL, "Data are not sorted by Company Name",image);
				
			}
			
			viewprop.click_GatecodeHeader();
			Thread.sleep(2000);

			if(viewprop.verifySortingbyGateCode()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Verified Sorting by Gate Code");
				logger.log(LogStatus.PASS, "Verified Sorting by Gate Code",image);
			}
			else{
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Data are not sorted by Gate Code");
				logger.log(LogStatus.FAIL, "Data are not sorted by Gate Code",image);
				
			}
				
			
			viewprop.click_AccessTypeHeader();
			Thread.sleep(2000);
			
			if(viewprop.verifySortingbyAccessType()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Verified Sorting by Access Type");
				logger.log(LogStatus.PASS, "Verified Sorting by Access Type",image);
			}
			else{
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Data are not sorted by Access Type");
				logger.log(LogStatus.FAIL, "Data are not sorted by Access Type",image);
				
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
			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_View_SearchGateAccessCodes" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_View_SearchGateAccessCodes" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "PropertyManagement","PropertyManagement_View_SearchGateAccessCodes" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);


	}


}
