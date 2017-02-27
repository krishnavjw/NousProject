package Scenarios.CustomerDashboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Verify_NoPayment_Flag_ImportantInformation extends Browser_Factory {
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerDashBoard","CustomerDashBoard", "Verify_NoPayment_Flag_ImportantInformation");
	}

	@Test(dataProvider="getLoginData")
	public void Verify_NoChecks_NoCredit_Flag_ImportantInformation(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Verify_NoPayment_Flag_ImportantInformation","Customer DashBoard - Verify No Payments Flag in Important Information");
			
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

			List<WebElement> biforstSystem = driver.findElements(
					By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
			for (WebElement ele : biforstSystem) {
				if (biforstNum.equalsIgnoreCase(ele.getText().trim())) {
					Reporter.log(ele.getText() + "", true);
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
			
		/*	String qry = "Select top 1 A.AccountNumber from Account A with(nolock) "+
					"Join AccountAddress AA with(nolock) on AA.AccountID = A.AccountID "+
					"Join Address Ad with(nolock) on AA.AddressID = Ad.AddressID "+
					"Join Customer CU with(nolock) on A.CustomerID = CU.CustomerID "+
					"Join Contact C with(nolock) on CU.ContactID = C.ContactID "+
					"Join CustomerClass CC with(nolock) on CU.CustomerClassID = CC.CustomerClassID "+
					"Join AccountOrder AO with(nolock) on A.AccountID = AO.AccountID "+
					"Join AccountOrderItem AOI with(nolock) on AO.AccountOrderID  = AOI.AccountOrderID "+
					"Join StorageOrderItem SOI with(nolock)on AOI.StorageOrderItemID = SOI.StorageOrderItemID "+
					"Join Type T with(nolock) on T.TypeID = SOI.StorageOrderItemTypeID "+
					"left join customermilitaryinfo cmi with(nolock) on cmi.customerid=Cu.customerid "+
					"left join Type T1 with(nolock) on T1.typeid=cmi.militarytypeid "+
					"Where 1=1 and AOI.siteid=(select siteid from site where sitenumber='"+SiteNumber+"') "+
					"Order by A.LastUpdate asc";
			
			String AccountNumber=DataBase_JDBC.executeSQLQuery(qry);
			Thread.sleep(3000);*/
			
			String updateQuery1="Update Customer SET NoChecks=0, NoCreditCards=0 WHERE customerid="+
					" (select customerid from account where AccountNumber='"+tabledata.get("Account Number")+"')";
			
			DataBase_JDBC.executeSQLQuery(updateQuery1);
			Thread.sleep(10000);
			
			Advance_Search advSearch = new Advance_Search(driver);
			logger.log(LogStatus.INFO, "Created Object for advance Search page");
			advSearch.enterAccNum("PS15008540");
			Thread.sleep(2000);
			String scpathacc=Generic_Class.takeScreenShotPath();
			String imageacc=logger.addScreenCapture(scpathacc);
			logger.log(LogStatus.INFO, "Entered Account Number",imageacc);
			logger.log(LogStatus.INFO, "Entered Account Number is : " +tabledata.get("Account Number"));
			
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
			
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			if(cust_accdetails.verifyImportantInformationSection()){
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Important Information Section is displayed successfully");
				logger.log(LogStatus.INFO, "Important Information Section is displayed successfully", image);
			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Important Information Section is not displayed");
				logger.log(LogStatus.INFO, "Important Information Section is not displayed", image);
			}
			
			boolean check = driver.findElements(By.xpath("//div[contains(text(),'No Checks')]")).size()>0;
			
			if(check == false){
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "No Checks Flag is not displayed");
				logger.log(LogStatus.INFO, "No Checks Flag is not displayed", image);
			}else{
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "No Checks Flag is displayed");
				logger.log(LogStatus.INFO, "No Checks Flag is displayed", image);
			}
			Thread.sleep(6000);

			boolean credit = driver.findElements(By.xpath("//div[contains(text(),'No Credit Cards')]")).size()>0;
			
			if(credit == false){
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "No Credit Cards Flag is not displayed");
				logger.log(LogStatus.INFO, "No Credit Cards Flag is not displayed", image);
			}else{
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "No Credit Cards Flag is displayed");
				logger.log(LogStatus.INFO, "No Credit Cards Flag is displayed", image);
			}
			
			boolean creditcheck = driver.findElements(By.xpath("//div[contains(text(),'No Credit Cards/No Checks')]")).size()>0;
			
			if(creditcheck == false){
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "No Credit Cards/No Checks Flag is not displayed");
				logger.log(LogStatus.INFO, "No Credit Cards/No Checks Flag is not displayed", image);
			}else{
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "No Credit Cards/No Checks Flag is displayed");
				logger.log(LogStatus.INFO, "No Credit Cards/No Checks Flag is displayed", image);
			}
			Thread.sleep(3000);
			
			if(cust_accdetails.verify_NoPayments()){
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "No Payments Flag is displayed Successfully");
				logger.log(LogStatus.INFO, "No Payments Flag is displayed Successfully", image);
			}else{
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "No Payments Flag is not displayed");
				logger.log(LogStatus.INFO, "No Payments Flag is not displayed", image);
			}


			String updateQuery2="Update Customer SET NoChecks=0, NoCreditCards=0  NoPayments=0 WHERE customerid=(select customerid from account where AccountNumber='PS15008540')";
			DataBase_JDBC.executeSQLQuery(updateQuery2);
			Thread.sleep(10000);
			
			
			
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
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_NoPayment_Flag_ImportantInformation" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_NoPayment_Flag_ImportantInformation" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_NoPayment_Flag_ImportantInformation" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}

}
