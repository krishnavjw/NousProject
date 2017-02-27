package Scenarios.RMDashboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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
import Pages.AWB.AuctionManagementPage;
import Pages.AWB.Auction_modelwindow;
import Pages.AWB.PropertyManagementPage;
import Pages.AWB.SelectFilterOptions;
import Pages.AWB.UnitDetailsPage;
import Pages.DTMCallsPages.DTMCallscreen;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.HomePages.RMHomepage;
import Scenarios.Browser_Factory;

public class Validate_view_all_reservations_link_and_data_in_it_DM extends Browser_Factory{

	
	public ExtentTest logger;

	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "DMDashboard","DMDashboard","Validate_view_all_reservations_link_and_data_in_it_DM");
	}

	@Test(dataProvider="getCustSearchData")
	public void Validate_view_all_reservations_link_and_data_in_it_DM(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("Validate_view_all_reservations_link_and_data_in_it_DM", "Validate_view_all_reservations_link_and_data_in_it_DM");
			Reporter.log("Test case started: " +testcaseName, true); 


			if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("DMDashboard").equals("Y")))
			{
				resultFlag="skip";
				throw new SkipException("Skipping the test");
			}

			//Login To the Application
			
		
			
			LoginPage loginPage=new LoginPage(driver);		
			loginPage.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Login to Application  successfully");
			Thread.sleep(8000);

			JavascriptExecutor jse = (JavascriptExecutor)driver;
			RMHomepage rmhomepage= new RMHomepage(driver);
			try{
				
				
				rmhomepage.label_label_Reservation_Calls();
				
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Reservation Calls is displayed in DM DashBoard");
				logger.log(LogStatus.INFO, "Reservation Calls is displayed in DM DashBoard",image1);
		
			}
			catch(Exception ex){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Reservation Calls is not displayed in DM DashBoard ");
				logger.log(LogStatus.INFO, "Reservation Calls is not displayed in DM DashBoard ",image);
			}
			
			Thread.sleep(3000);	
			
			
			
			
			boolean errormsg= driver.getPageSource().contains("NO ITEMS TO DISPLAY");
			
			if(errormsg){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Data is not displaying'");
				logger.log(LogStatus.INFO, "Data is not displaying ",image1);
						
			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.PASS, "Data is  displaying ");
				logger.log(LogStatus.INFO, "Data is displaying ",image);
				
				
				
			}
			
			
			
			
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			Reporter.log("Exception ex: " + ex,true);
			logger.log(LogStatus.FAIL,"Test Script fail due to exception");
		}




	}


	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "RMDashboard","Validate_view_all_reservations_link_and_data_in_it" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "RMDashboard","Validate_view_all_reservations_link_and_data_in_it" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "RMDashboard","Validate_view_all_reservations_link_and_data_in_it" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}