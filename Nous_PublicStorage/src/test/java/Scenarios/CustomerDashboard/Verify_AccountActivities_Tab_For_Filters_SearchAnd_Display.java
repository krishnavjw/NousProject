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
import Pages.CustDashboardPages.Cust_AccActivitiesPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Verify_AccountActivities_Tab_For_Filters_SearchAnd_Display extends Browser_Factory {
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerDashBoard","CustomerDashBoard", "Verify_AccountActivities_Tab_For_Filters_SearchAnd_Display");
	}

	@Test(dataProvider="getLoginData")
	public void Verify_AccountActivities_Tab_For_Filters_SearchAnd_Display(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Verify_AccountActivities_Tab_For_Filters_SearchAnd_Display","Customer DashBoard - Verify Account Activities Tab For Filters Search And Display");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);
			
			
			
			//=================Handling Customer Facing Device=============================
			
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
			
			String qry ="Select Top 1 A.AccountNumber "+
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
					"group by a.accountnumber "+
					"having sum((clt.amount * clt.quantity)+clt.discountamount) >0 and count(distinct ru.rentalunitid)=1";
					
			
			String AccountNumber=DataBase_JDBC.executeSQLQuery(qry);
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
			Thread.sleep(8000);
			
			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			logger.log(LogStatus.PASS, "Created object for Customer dash board page");
			
			if(cust_accdetails.isCustdbTitleDisplayed()){

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
			
			cust_accdetails.clk_Acc_ActivitiesTab();
			Thread.sleep(10000);
			logger.log(LogStatus.INFO, "Clicked on Account Activities Tab Successfully");
			
			Cust_AccActivitiesPage accpage = new Cust_AccActivitiesPage(driver);
			logger.log(LogStatus.INFO, "Created Object for Account Activities page");
			Thread.sleep(2000);

			if(accpage.verify_last20Act()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Last 20 Activities is selected as default");
				logger.log(LogStatus.INFO, "Last 20 Activities is selected as default",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Last 20 Activities is not selected as default");
				logger.log(LogStatus.INFO, "Last 20 Activities is not selected as default",image);
			}
			Thread.sleep(2000);
			
			if(accpage.verify_viewAllAct()){
				logger.log(LogStatus.PASS, "View All Activities is displayed Successfully");
			}else{

				logger.log(LogStatus.FAIL, "View All Activities is not displayed");
			}
			
			if(accpage.verify_viewAllNotes()){
				logger.log(LogStatus.PASS, "View All Notes is displayed Successfully");
			}else{

				logger.log(LogStatus.FAIL, "View All Notes is not displayed");
			}
			
			
			accpage.clk_viewAllActRadioBtn();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked on View All Activities Radio Button");
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			if(accpage.verify_DateRange()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Date Range Filter is displayed when view All Activities radio button is selected");
				logger.log(LogStatus.INFO, "Date Range Filter is displayed when view All Activities radio button is selected",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Date Range Filter is not displayed");
				logger.log(LogStatus.INFO, "Date Range Filter is not displayed",image);
			}
			Thread.sleep(2000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0,2000)");
			Thread.sleep(2000);
			
			accpage.sel_Filter("Autopay");
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Selected Filter from Dropdown :Autopay ");
			
			boolean noactivities = driver.findElements(By.xpath("//b[contains(text(),'NO ACTIVITIES TO DISPLAY')]")).size()>0;
			
			if(noactivities == true){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "No Activities To display for the Selected Filter :"+tabledata.get("FilterName"));
				logger.log(LogStatus.INFO, "No Activities To display for the Selected Filter",image);

			}else{
				String data = driver.findElement(By.xpath("//div[@id='activities-grid']//table/tbody/tr[1]/td[4]")).getText();
			//	accpage.verify_FilteredValues(tabledata.get("FilterName"));
				if(data.trim().equals(tabledata.get("FilterName"))){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Results are displayed Correctly as per the filtered Criteria : "+data);
				logger.log(LogStatus.INFO, "Results are displayed Correctly as per the filtered Criteria",image);
				}
			
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
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_AccountActivities_Tab_For_Filters_SearchAnd_Display" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_AccountActivities_Tab_For_Filters_SearchAnd_Display" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_AccountActivities_Tab_For_Filters_SearchAnd_Display" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
			

}
