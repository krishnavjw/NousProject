package Scenarios.InventoryMerchandise;


import java.util.Hashtable;






import org.openqa.selenium.JavascriptExecutor;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.InventoryMerchandise.InventoryManagement;
import Pages.PropertyManagementPages.PropertyManagementPage;
import Scenarios.Browser_Factory;

public class DatabaseValidationforCountAdjustments extends Browser_Factory
{
	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "InventoryMerchandise","InventoryMerchandise", "DatabaseValidationforCountAdjustments");
	}

	@Test(dataProvider="getData")
	public void ValidateBasicSearchFunction(Hashtable<String, String> tabledata) throws InterruptedException
	{
		logger=extent.startTest("DatabaseValidationforCountAdjustments","DatabaseValidationforCountAdjustments");

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("InventoryMerchandise").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "ValidateMissedPMTasksandLateTasks is Skipped");
			throw new SkipException("Skipping the test");
		}

		try
		{
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			String siteNumber = login.get_SiteNumber();
			login.enterUserName(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "UserName entered successfully");
			login.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Password entered successfully");
			login.clickLogin();
			logger.log(LogStatus.INFO, "Clicked on Login button successfully");
			Thread.sleep(5000);

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			
			try{
			
			Bifrostpop.clickContiDevice();
			
			Thread.sleep(3000);
			
			}
			catch(Exception e){
				System.out.println("");
			}
			

			
			Thread.sleep(5000);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to PM Dashboard");
			logger.log(LogStatus.INFO, "Navigated to PM Dashboard",image);

			JavascriptExecutor jsx = (JavascriptExecutor)driver;
			jsx.executeScript("window.scrollBy(0,450)", "");
			Thread.sleep(2000);
			
			
			PM_Homepage pmhome=new PM_Homepage(driver);
			pmhome.clickmanageProp();
			logger.log(LogStatus.INFO, "Clicked on Manage Property Link from PM Dashboard");
			Thread.sleep(5000);
			
			logger.log(LogStatus.PASS, "Navigated to Property Management Screen successfully");	
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Navigated to Property Management Screen successfully",image);
			
			
			PropertyManagementPage propMgmt=new PropertyManagementPage(driver);
			propMgmt.click_InventoryManagement();
			logger.log(LogStatus.INFO, "Clicked on the link 'Inventory Management'");
			Thread.sleep(5000);

			
			logger.log(LogStatus.PASS, "Navigated to Inventory Management Screen successfully");	
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Navigated to Inventory Management Screen successfully",image);
			
			
			InventoryManagement invMgmt=new InventoryManagement(driver);
			
			String qohbefore = invMgmt.verify_QuantityonHand(tabledata.get("itemname"));
			
			logger.log(LogStatus.PASS, "Verified the Quantity on Hand with DB before adjustment. Item->" + tabledata.get("itemname") + ",   Quantity->" + qohbefore);
			
			
			invMgmt.enterCount(driver,tabledata.get("Reason"));
			
			
			invMgmt.click_Submit(driver);
			logger.log(LogStatus.INFO, "Clicked on Submit button ");
			
			Thread.sleep(5000);
			
			
			invMgmt.enter_EmployeeID(tabledata.get("UserName"));
			Thread.sleep(3000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Entered employee ID  "+tabledata.get("UserName")+" in the input text field ");
			logger.log(LogStatus.INFO, "Entered employee ID in the input text field ",image);
						
			
			Thread.sleep(3000);
			invMgmt.click_OK();
			
			
			Thread.sleep(10000);
			
			String qohafter = invMgmt.verify_QuantityonHand(tabledata.get("itemname"));
			
			logger.log(LogStatus.PASS, "Verified the Quantity on Hand with DB after adjustment. Item->" + tabledata.get("itemname") + ",   Quantity->" + qohafter);
			
			

		

		}catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Test script failed due to the exception",image);
		}

	}       


	@AfterMethod
	public void afterMethod(){

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path,"InventoryMerchandise","DatabaseValidationforCountAdjustments" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){    
			Excel.setCellValBasedOnTcname(path,"InventoryMerchandise","DatabaseValidationforCountAdjustments" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "InventoryMerchandise","DatabaseValidationforCountAdjustments" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}

