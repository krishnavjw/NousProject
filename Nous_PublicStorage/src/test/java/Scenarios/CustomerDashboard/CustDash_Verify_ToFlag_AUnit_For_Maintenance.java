package Scenarios.CustomerDashboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.FlagForMaintce;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class CustDash_Verify_ToFlag_AUnit_For_Maintenance extends Browser_Factory{
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerDashBoard","CustomerDashBoard", "CustDash_Verify_ToFlag_AUnit_For_Maintenance");
	}

	@Test(dataProvider="getLoginData")
	public void CustDash_Verify_ToFlag_AUnit_For_Maintenance(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("CustDash_Verify_ToFlag_AUnit_For_Maintenance","Customer DashBoard - Verify To Flag A unit For Maintenance");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);
			
			
	//=================Handling Customer Facing Device================================
			
			Thread.sleep(5000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			String biforstNum = Bifrostpop.getBiforstNo();
			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			Thread.sleep(3000);			
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			Reporter.log(tabs.size() + "", true);
			driver.switchTo().window(tabs.get(1));
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath"));

			Thread.sleep(3000);
			List<WebElement> biforstSystem = driver.findElements(
					By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
			for (WebElement ele : biforstSystem) {
				if (biforstNum.equalsIgnoreCase(ele.getText().trim())) {
					Reporter.log(ele.getText() + "", true);
					ele.click();
					break;
				}
			}
			Thread.sleep(3000);
			
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
					
			//================================== PM Home Page ===============================================
			
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			logger.log(LogStatus.PASS, "Created object for the PM home page Sucessfully");
			Thread.sleep(3000);
			
			String SiteNumber = pmhomepage.getLocation();
			logger.log(LogStatus.PASS, "location number is:"+SiteNumber);
			
			//Verifying PM Dash Board is displayed
			if (pmhomepage.get_WlkInCustText().trim().equalsIgnoreCase(tabledata.get("walkInCustomerTitle"))) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User Lands in PM Dashboard Successfully");
				logger.log(LogStatus.INFO, "User Lands in PM Dashboard Successfully", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "PM Dashboard is not displayed");
				logger.log(LogStatus.INFO, "PM Dashboard is not displayed", image);

			}
			
			if (pmhomepage.get_existingCustomerText().trim().equalsIgnoreCase(tabledata.get("ExistingCustomerTitle"))) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Existing Customer is displaying in PM Dashboard Successfully");
				logger.log(LogStatus.INFO, "Existing Customer is displaying in PM Dashboard Successfully", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Existing Customer is not displayed");
				logger.log(LogStatus.INFO, "Existing Customer is not displayed", image);

			}
			
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Advance Search link in PM home page");
							
			String qry = "select top 1 u.accountnumber, u.rentalunitnumber from vw_unitdetails u "+
					"join rentalunit ru with(nolock) on ru.rentalunitid=u.rentalunitid "+
					"join site s on s.siteid = u.siteid "+
					"where u.vacatedate is null "+
					"and u.customertypeid = 90 "+
					"and s.sitenumber = '"+SiteNumber+"' "+
					"and ru.maintenance = 0 "+
				    "group by u.accountnumber, u.rentalunitnumber "+
					"having count(u.accountorderitemid) = 1";
			
			ArrayList<String> list1=DataBase_JDBC.executeSQLQuery_List(qry);
			Thread.sleep(3000);
			String AccountNumber=list1.get(0);
			String rentalunitnumber = list1.get(1);
			Thread.sleep(3000);
			
			Advance_Search advSearch = new Advance_Search(driver);
			logger.log(LogStatus.INFO, "Created Object for advance Search page");
			advSearch.enterAccNum(AccountNumber);
			logger.log(LogStatus.INFO, "Entered Account Number is : "+AccountNumber);
			Thread.sleep(2000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			advSearch.clickButton();
			logger.log(LogStatus.INFO, "Clicked on Search button Successfully");
			Thread.sleep(10000);
			
			//================================== Customer Dashboard ===============================================
			
			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			logger.log(LogStatus.PASS, "Created object for Customer dash board page");
			
			if(cust_accdetails.isCustdbTitleDisplayed()){
				Thread.sleep(3000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "Customer Dashboard is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer Dashboard is not displayed");
				logger.log(LogStatus.INFO, "Customer Dashboard is not displayed",image);
			}
			Thread.sleep(3000);
			
			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(6000);
			//mohana
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			//((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "Clicked on Space details Tab Successfully", image1);
			Acc_SpaceDetailsPage spaceDetail = new Acc_SpaceDetailsPage(driver);
			logger.log(LogStatus.INFO, "Created object For Space Details Page");
			spaceDetail.click_FlagForMaintenance();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked on Flag For Maintenance Link Successfully");
			
			FlagForMaintce flag = new FlagForMaintce(driver);
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Created Object for Flag for maintenance");
			//Verifying Flag For Maintenance
			if(flag.verify_FlagTitle()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Flag For Maintenance Popup is displayed successfully");
				logger.log(LogStatus.INFO, "Flag For Maintenance Popup is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Flag For Maintenance Popup is not displayed");
				logger.log(LogStatus.INFO, "Flag For Maintenance Popup is not displayed",image);
			}
			
			flag.select_reason(tabledata.get("ReasonForFlagging"));
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Selected reason for flag : "+tabledata.get("ReasonForFlagging"));
			flag.enter_Notes(tabledata.get("Notes"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Notes : "+tabledata.get("Notes"));
			flag.enter_EmployeId(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered Employee Number : "+tabledata.get("UserName"));
			Thread.sleep(2000);
			String scpath2=Generic_Class.takeScreenShotPath();
			String image2=logger.addScreenCapture(scpath2);
			logger.log(LogStatus.INFO, "Entered Employee Number",image2);
			flag.click_UpdateBtn();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Update button Successfully");
			
			driver.navigate().refresh();
			Thread.sleep(3000);
			
			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(6000);
			
			//verifying in space details tab
			if(spaceDetail.verify_MaintenanceNeeded()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Maintenance Needed is displayed successfully in Space tab");
				logger.log(LogStatus.INFO, "Maintenance Needed is displayed successfully in Space tab",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Maintenance Needed is not displayed");
				logger.log(LogStatus.INFO, "Maintenance Needed is not displayed",image);
			}
			Thread.sleep(2000);


			String sqlqry = "Select A.accountnumber, ru.rentalunitnumber, case when ru.Maintenance=1 then 'Maintenance Needed' else 'Not On Maintenance' end as Maintenance, t.name as MaintenanceReason "+
					"FROM Account A with(nolock) "+
					"Join Customer C with(nolock) on C.CustomerID = A.CustomerID "+
					"Join AccountOrder AO with(nolock) on  AO.AccountID = A.AccountID "+
					"join AccountOrderItem AOI with(nolock) on AOI.AccountOrderID=AO.AccountOrderID "+
					"join site s with(nolock) on s.siteid=aoi.siteid "+
					"join StorageOrderItem SOI with(nolock) on SOI.StorageOrderItemID = AOI.StorageOrderItemID "+
					"join rentalunit ru with(nolock) on ru.rentalunitid=soi.rentalunitid "+
					"left join type t with(nolock) on t.typeid=ru.MaintenanceReasonTypeID where 1=1 "+
					"and A.accountnumber='"+AccountNumber+"' "+
					"and ru.rentalunitnumber='"+rentalunitnumber+"'";
			
			Thread.sleep(1000);
			ArrayList<String> list=DataBase_JDBC.executeSQLQuery_List(sqlqry);
			Thread.sleep(15000);
			String maintreason = list.get(3);
			
			logger.log(LogStatus.INFO, "Maintenance Reason fetched from database is :"+maintreason);
			Thread.sleep(2000);
			
			if(tabledata.get("ReasonForFlagging").equals(maintreason)){
				logger.log(LogStatus.PASS, "Entered Reason " +tabledata.get("ReasonForFlagging")+ " is matched with database " +maintreason);;
			}else{
				logger.log(LogStatus.FAIL, "Entered Reason " +tabledata.get("ReasonForFlagging")+ " is not matched with database " +maintreason);;

			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+e);
		}

	}

	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustDash_Verify_ToFlag_AUnit_For_Maintenance" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustDash_Verify_ToFlag_AUnit_For_Maintenance" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustDash_Verify_ToFlag_AUnit_For_Maintenance" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}


}
