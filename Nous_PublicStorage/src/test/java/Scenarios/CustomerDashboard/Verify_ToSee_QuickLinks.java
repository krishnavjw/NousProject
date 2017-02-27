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
import Pages.CustDashboardPages.Buy_MerchandisePage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.Documents;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Verify_ToSee_QuickLinks extends Browser_Factory{
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerDashBoard","CustomerDashBoard", "Verify_ToSee_QuickLinks");
	}

	@Test(dataProvider="getLoginData")
	public void Verify_ToSee_QuickLinks(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Verify_ToSee_QuickLinks","Customer DashBoard - Verify To See Quick Links");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);
			
			
			//=================Handling Customer Facing Device================================
			
			Thread.sleep(5000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
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
			
			String qry = "Select Top 1 A.AccountNumber "+
					"FROM Account A with(nolock) "+
					"Join Customer C with(nolock) on C.CustomerID = A.CustomerID "+
					"Join AccountOrder AO with(nolock) on  AO.AccountID = A.AccountID "+
					"join AccountOrderItem AOI with(nolock) on AOI.AccountOrderID=AO.AccountOrderID "+
					"join site s with(nolock) on s.siteid=aoi.siteid "+
					"join StorageOrderItem SOI with(nolock) on SOI.StorageOrderItemID = AOI.StorageOrderItemID "+ 
					"join rentalunit ru with(nolock) on ru.rentalunitid=soi.rentalunitid "+
					"join Type T with(nolock) on T.TypeID = C.CustomerTypeID "+
					"join Type T1 with(nolock) on T1.TypeID = SOI.paymentmethodtypeid "+
					"join cltransaction clt with(nolock) on clt.accountorderitemid=aoi.accountorderitemid "+
					"where 1 = 1 and s.sitenumber='"+SiteNumber+"' "+
					"and soi.VacateDate is null "+
					"and c.customertypeid= 90 "+
					"and c.donotrent = 0 "+
					"group by a.accountnumber "+
					"having sum((clt.amount * clt.quantity)+clt.discountamount) >0 and count(distinct ru.rentalunitid)=1";
			
			String AccountNumber=DataBase_JDBC.executeSQLQuery(qry);
			Thread.sleep(3000);
			
			Advance_Search advSearch = new Advance_Search(driver);
			logger.log(LogStatus.INFO, "Created Object for advance Search page");
			advSearch.enterAccNum(AccountNumber);
			Thread.sleep(2000);
			String scpathacc=Generic_Class.takeScreenShotPath();
			String imageacc=logger.addScreenCapture(scpathacc);
			logger.log(LogStatus.INFO, "Entered Account Number",imageacc);
			logger.log(LogStatus.INFO, "Entered Account Number is : "+AccountNumber);
			Thread.sleep(2000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			advSearch.clickButton();
			logger.log(LogStatus.INFO, "Clicked on Search button Successfully");
			Thread.sleep(10000);
			
			//================================== Customer Dashboard ===============================================
			
			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			
			
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
			
			if(cust_accdetails.verify_QuickLink()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Quick Link Dropdown is displayed successfully");
				logger.log(LogStatus.INFO, "Quick Link Dropdown is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Quick Link Dropdown is not displayed");
				logger.log(LogStatus.INFO, "Quick Link Dropdown is not displayed",image);
			}
			
			Thread.sleep(2000);
			//clicking on create issue option
			cust_accdetails.clk_quicklink();
			Thread.sleep(3000);
			String scpathq=Generic_Class.takeScreenShotPath();
			String imagei=logger.addScreenCapture(scpathq);
			logger.log(LogStatus.PASS, "Quick Link Dropdown values displayed successfully");
			logger.log(LogStatus.INFO, "Quick Link Dropdown values displayed successfully",imagei);
			Thread.sleep(5000);
			
		
			cust_accdetails.clk_CreateIssue();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO,"Clicked on Create issue option Successfully");
			
			boolean issue = driver.findElement(By.xpath("//span[contains(text(),'Select Issue Type')]")).isDisplayed();
			if(issue){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Select Issue Type Popup is displayed successfully");
				logger.log(LogStatus.INFO, "Select Issue Type Popup is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Select Issue Type Popup is not displayed");
				logger.log(LogStatus.INFO, "Select Issue Type Popup is not displayed",image);
			}
			
			driver.findElement(By.xpath("//a[contains(text(),'Cancel')]")).click();
			Thread.sleep(6000);
			
			//Clicking on Buy merchandise option 
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			
			cust_accdetails.clk_quicklink();
			Thread.sleep(2000);
			cust_accdetails.clk_BuyMerchandise();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO,"Clicked on Buy Merchandise option Successfully");
			
			/*boolean merchandise = driver.findElement(By.xpath("//h3[contains(text(),'Buy Merchandise')]")).isDisplayed();
			if(merchandise){*/
			Buy_MerchandisePage buy_Merch=new Buy_MerchandisePage(driver);
			if(buy_Merch.verify_pagetitle()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Buy Merchandise Page is displayed successfully");
				logger.log(LogStatus.INFO, "Buy Merchandise page is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Buy Merchandise page is not displayed");
				logger.log(LogStatus.INFO, "Buy Merchandise page is not displayed",image);
			}
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);

			
			driver.findElement(By.xpath("//a[@id='cancelButton']")).click();
			Thread.sleep(6000);
			
			//Clicking on return merchandise option 
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			
			cust_accdetails.clk_quicklink();
			Thread.sleep(2000);
			cust_accdetails.clk_ReturnMerchandise();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO,"Clicked on Return Merchandise option Successfully");
			
			boolean merchandiseTrans = driver.findElement(By.xpath("//span[contains(text(),'Merchandise Transaction History')]")).isDisplayed();
			if(merchandiseTrans){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Return Merchandise Page is displayed successfully");
				logger.log(LogStatus.INFO, "Return Merchandise page is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Return Merchandise page is not displayed");
				logger.log(LogStatus.INFO, "Return Merchandise page is not displayed",image);
			}
			
			driver.findElement(By.xpath("//a[contains(text(),'Cancel')]")).click();
			Thread.sleep(6000);

			//Clicking on Add Space option 
			
			//cust_accdetails.clk_quicklink();
			driver.findElement(By.xpath("//div[@class='actions clearfix-container']//span[contains(text(),'Return Merchandise')]")).click();
			Thread.sleep(2000);
			cust_accdetails.clk_AddSpace();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO,"Clicked on Add Space option Successfully");
			
			boolean standard = driver.findElement(By.xpath("//span[contains(text(),'Standard Storage')]")).isDisplayed();
			if(standard){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Add Space Page is displayed successfully");
				logger.log(LogStatus.INFO, "Add Space page is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Add Space Page is not displayed");
				logger.log(LogStatus.INFO, "Add Space Page is not displayed",image);
			}
			
			driver.findElement(By.xpath("//a[contains(text(),'Back To Dashboard')]")).click();
			logger.log(LogStatus.INFO, "Clicked on Back to dashboard button Successfully");
			Thread.sleep(10000);
			
			if (pmhomepage.get_WlkInCustText().trim().equalsIgnoreCase(tabledata.get("walkInCustomerTitle"))) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "PM Dashboard is displayed Successfully");
				logger.log(LogStatus.INFO, "PM Dashboard is displayed Successfully", image);
			
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "PM Dashboard is not displayed");
				logger.log(LogStatus.INFO, "PM Dashboard is not displayed", image);

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
				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_ToSee_QuickLinks" , "Status", "Pass");

			}else if (resultFlag.equals("fail")){

				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_ToSee_QuickLinks" , "Status", "Fail");
			}else{
				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_ToSee_QuickLinks" , "Status", "Skip");
			}


			extent.endTest(logger);
			extent.flush();
			Reporter.log("Test case completed: " +testcaseName, true);

		}

}
