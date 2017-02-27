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
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Update_NoChecks_NoCredit_NoPayments_Flag extends Browser_Factory {
	
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerDashBoard","CustomerDashBoard", "Update_NoChecks_NoCredit_NoPayments_Flag");
	}

	@Test(dataProvider="getLoginData")
	public void Update_NoChecks_NoCredit_NoPayments_Flag(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Update_NoChecks_NoCredit_NoPayments_Flag","Customer DashBoard - Verify No Checks,No Credit Cards and No Payments Flag in Important Information");
			
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
					
			//================================== DM Home Page =====================================
			
			DM_HomePage dmhomepage=new DM_HomePage(driver);
			Thread.sleep(3000);
			
		
			//Verifying DM Dashboard is displayed
			if (dmhomepage.is_DMDashBoardTitle()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "DM Dashboard is displayed Successfully");
				logger.log(LogStatus.INFO, "DM Dashboard is displayed Successfully", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "DM Dashboard is not displayed");
				logger.log(LogStatus.INFO, "DM Dashboard is not displayed", image);
			}
			
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(3000);
			
			dmhomepage.click_advSearchLink();
			logger.log(LogStatus.INFO, "Clicked on Advance search link");
			Thread.sleep(8000);
			
			String qry = "select top 1 u.accountnumber from vw_unitdetails u "+
						"join customer c on c.customerid = u.customerid "+
						"join storageorderitem soi on soi.storageorderitemid = u.storageorderitemid "+
						"where u.vacatedate is null "+
						"and u.customertypeid = 90 "+
						"and c.nochecks = 0 "+
						"and c.nocreditcards = 0 "+
						"and soi.applynopayments = 0 ";
			
			String AccountNumber = DataBase_JDBC.executeSQLQuery(qry);
			logger.log(LogStatus.INFO, "Account Number fetched from database : "+AccountNumber);
		
			//================================== Advance Search Page==============================
			
			Advance_Search advSearch = new Advance_Search(driver);
			advSearch.enterAccNum(AccountNumber);
			logger.log(LogStatus.INFO, "Entered Account Number is : "+AccountNumber);
			Thread.sleep(2000);
			String scpath1 = Generic_Class.takeScreenShotPath();
			String image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "img", image1);
			Thread.sleep(1000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			advSearch.clickButton();
			logger.log(LogStatus.INFO, "Clicked on Search button Successfully");
			Thread.sleep(10000);
			
			//================================== Customer Dashboard ================================
			
			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			Thread.sleep(1000);
			
			
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
			logger.log(LogStatus.INFO, "Clicked on space details tab");
			Thread.sleep(4000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			
			Acc_SpaceDetailsPage spacedet = new Acc_SpaceDetailsPage(driver);
			Thread.sleep(1000);
			spacedet.click_paymentRestrictionModeChangeLink();
			logger.log(LogStatus.INFO, "Clicked on change link successfully");
			Thread.sleep(3000);
			
			
			//================== Change payment restriction Status ================================
			
			boolean changeTit = driver.findElement(By.xpath("//span[contains(text(),'Change Payment Restriction Status')]")).isDisplayed();
			
			if(changeTit){
				Thread.sleep(3000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Change Payment Restriction Status is displayed successfully");
				logger.log(LogStatus.INFO, "img",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Change Payment Restriction Status is not displayed");
				logger.log(LogStatus.INFO, "img",image);
			}
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//span[contains(text(),'No Checks (Applies to All Spaces)')]/preceding-sibling::span[@class='button']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//span[contains(text(),'No Credit Cards (Applies to All Spaces)')]/preceding-sibling::span[@class='button']")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//span[contains(text(),'No Payments (Applies to Space)')]/preceding-sibling::span[@class='button']")).click();
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "Checked all the check boxes");
			
			driver.findElement(By.id("noteText")).sendKeys(tabledata.get("Notes"));
			logger.log(LogStatus.INFO, "Entered Notes : "+tabledata.get("Notes"));
			Thread.sleep(2000);
			driver.findElement(By.id("employeeNumber")).sendKeys(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered Employee Number : "+tabledata.get("UserName"));
			Thread.sleep(2000);
			String scpath2=Generic_Class.takeScreenShotPath();
			String image2=logger.addScreenCapture(scpath2);
			logger.log(LogStatus.INFO, "img",image2);
			
			driver.findElement(By.xpath("//a[contains(text(),'Update')]")).click();
			logger.log(LogStatus.INFO, "Clicked on Update Button Successfully");
			Thread.sleep(9000);
			try{
			driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
			Thread.sleep(3000);
			}catch(Exception e){
				
			}
		
			
			
			//================== Customer Dashboard Important Information================================
			
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			
			if(cust_accdetails.verify_noCreditAndChecks()){
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "No Credit Cards/ No Checks Flag is displayed successfully");
				logger.log(LogStatus.INFO, "No Credit Cards/ No Checks Flag is displayed successfully", image);
			}

			else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "No Credit Cards/ No Checks Flag is not displayed");
				logger.log(LogStatus.INFO, "No Credit Cards/ No Checks Flag is not displayed", image);
			}
			Thread.sleep(2000);
			
			if(cust_accdetails.verify_NoPayments()){
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "No Payments Flag is displayed successfully");
				logger.log(LogStatus.INFO, "No Payments Flag is displayed successfully", image);
			}

			else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "No Payments Flag is not displayed");
				logger.log(LogStatus.INFO, "No Payments Flag is not displayed", image);
			}
			Thread.sleep(2000);
			
			String qry1 =  "select c.Nochecks, c.Nocreditcards, soi.ApplyNoPayments from vw_unitdetails u "+
						"join customer c on c.customerid = u.customerid "+
						"join storageorderitem soi on soi.storageorderitemid = u.storageorderitemid "+
						"where u.accountnumber = '"+AccountNumber+"' "+
						"and u.vacatedate is null ";
			
			ArrayList<String> list = DataBase_JDBC.executeSQLQuery_List(qry1);
			String check = list.get(0);
			String credit = list.get(1);
			String paym = list.get(2);
			
			
			if(check.equals("1")){
				logger.log(LogStatus.PASS, "Value fetched from Database for No Checks is : "+check);
			}else{
				logger.log(LogStatus.FAIL, "Value fetched from Database for No Checks is : "+check);
			}
			
			if(credit.equals("1")){
				logger.log(LogStatus.PASS, "Value fetched from Database for No Credit Cards is : "+credit);
			}else{
				logger.log(LogStatus.FAIL, "Value fetched from Database for No Credit Cards is : "+credit);
			}
			
			if(paym.equals("1")){
				logger.log(LogStatus.PASS, "Value fetched from Database for No Payments is : "+paym);
			}else{
				logger.log(LogStatus.FAIL, "Value fetched from Database for No Payments is : "+paym);
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
				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Update_NoChecks_NoCredit_NoPayments_Flag" , "Status", "Pass");

			}else if (resultFlag.equals("fail")){

				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Update_NoChecks_NoCredit_NoPayments_Flag" , "Status", "Fail");
			}else{
				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Update_NoChecks_NoCredit_NoPayments_Flag" , "Status", "Skip");
			}


			extent.endTest(logger);
			extent.flush();
			Reporter.log("Test case completed: " +testcaseName, true);

		}
				
				

}
