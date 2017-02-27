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

public class Validate_move_appointments_module_by_district_numbers_shouldberight extends Browser_Factory{

	
	public ExtentTest logger;

	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "RMDashboard","RMDashboard","Auction_approvalbutonshowsfor_DM_under_unit_details");
	}

	@Test(dataProvider="getCustSearchData")
	public void Validate_move_appointments_module_by_district_numbers_shouldberight(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("Validate_move_appointments_module_by_district_numbers_shouldberight", "Validate_move_appointments_module_by_district_numbers_shouldberight");
			Reporter.log("Test case started: " +testcaseName, true); 


			if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("RMDashboard").equals("Y")))
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
				
				
				rmhomepage.label_movin_appointement();
				
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Mive in Appointment is displayed in RM DashBoard");
				logger.log(LogStatus.INFO, "Mive in Appointment is displayed in RM DashBoard",image1);
		
			}
			catch(Exception ex){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Mive in Appointment is not displayed in RM DashBoard ");
				logger.log(LogStatus.INFO, "Mive in Appointment is not displayed in RM DashBoard ",image);
			}
			
			Thread.sleep(3000);	
			
			try{
				
				
				rmhomepage.label_selectdate();
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Select Date is displayed along with the a drop down in the header of the module.");
				logger.log(LogStatus.INFO, "Select Date is displayed along with the a drop down in the header of the module",image1);
		
			}
			catch(Exception ex){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Select Date is not  displayed in the header of the module");
				logger.log(LogStatus.INFO, "Select Date is not displayed in the header of the module",image);
			}
			
			
			SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd");
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			dt = c.getTime();
			String commitmentdate=formatter1.format(dt);
			
			jse.executeScript("window.scrollBy(1000,0)", "");
			
			String datedefault=rmhomepage.appointemt_defaultdate();
			
			
			if (commitmentdate.contains(datedefault)){
				logger.log(LogStatus.PASS, "The default filter for today’s date.----"+datedefault+"---Todays date--"+commitmentdate);
				
				
			} else {
				
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				
				logger.log(LogStatus.FAIL, "The default filter not displaying today’s date----"+datedefault+"---Todays date--"+commitmentdate);
				
			}	
			
			
			rmhomepage.clk_appointemt_defaultdate();
			
			
			List<WebElement> dateoptions= driver.findElements(By.xpath("//div[@id='move-in-appointments-filter-list']/ul/li"));
			
			
			for(int i=1;i<dateoptions.size()+1;i++){
				
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd");
				Date dt1 = new Date();
				Calendar c1 = Calendar.getInstance(); 
				c1.setTime(dt1); 
				c1.add(Calendar.DATE, i-1);
				dt1 = c1.getTime();
				String commitmentdate1=formatter.format(dt1);
				
				
				String dateoption =driver.findElement(By.xpath("//div[@id='move-in-appointments-filter-list']/ul/li["+i+"]")).getText();
				if (commitmentdate1.contains(dateoption)){
					logger.log(LogStatus.PASS, "The displayed  date.----"+dateoption+"---Actual date--"+commitmentdate1);
					
					
				} else {
					
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					
					logger.log(LogStatus.FAIL, "The displayed date----"+dateoption+"---Actual date--"+commitmentdate1);
					break;
				}	
				
				
				
			}
			
			
		
			String ipadd = Generic_Class.getIPAddress();
			
			String siteid = "select SiteID from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"
					+ ipadd+ "'";
			
			String siteid_ipsdd_data = DataBase_JDBC.executeSQLQuery(siteid);
			Thread.sleep(5000);
			
			rmhomepage.clk_appointemt_defaultdate();
			
			driver.findElement(By.xpath("//div[@id='move-in-appointments-filter-list']/ul/li[1]")).click();
			
			
			String appointment_data_todaydate = "select count(*) from appointment ap "
					+ "join type t on ap.appointmenttypeid=t.typeid "
					+ "where  siteid='"+siteid_ipsdd_data+"' and appointmenttypeid=4308 "
					+ "and CONVERT(DATE,enddatetime) = CONVERT(DATE, dbo.fn_SiteDateTime(SiteID, GETUTCDATE()+1))";
			
											
		
			
			String appointment_data_todaydate_db = DataBase_JDBC.executeSQLQuery(appointment_data_todaydate);
			
			
			String appointment_count=driver.findElement(By.xpath("//div[@id='moveInAppointmentsGrid']//table//td[2]")).getText();
			
			
			
			if (appointment_count.equals(appointment_data_todaydate_db)){
				logger.log(LogStatus.PASS, "The Move in Appointment list in UI ----"+appointment_count+"---IN DataBAse--"+appointment_data_todaydate_db);
				
				
			} else {
				
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				
				logger.log(LogStatus.FAIL, "The Move in Appointment list in UI not matching ----"+appointment_count+"---IN DataBAse--"+appointment_data_todaydate_db);
				
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
			Excel.setCellValBasedOnTcname(path, "RMDashboard","Validate_move_appointments_module_by_district_numbers_shouldberight" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "RMDashboard","Validate_move_appointments_module_by_district_numbers_shouldberight" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "RMDashboard","Validate_move_appointments_module_by_district_numbers_shouldberight" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}