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
import com.thoughtworks.selenium.webdriven.commands.Click;

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Acc_CustomerInfoPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class CustomerDashboard_Viewdetails_ActiveMilitary extends Browser_Factory{

	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerDashBoard","CustomerDashBoard",  "CustomerDashboard_Viewdetails_ActiveMilitary");
	}



	@Test(dataProvider="getLoginData")
	public void propertyManagement_Edit_NonCustomerPropertyAccess(Hashtable<String, String> tabledata) throws InterruptedException 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Thread.sleep(5000);

		try
		{

			//Login to the application as PM 
			logger=extent.startTest("CustomerDashboard_Viewdetails_ActiveMilitary","Verify Active Military status info");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "PM Logged in successfully");

			JavascriptExecutor jse = (JavascriptExecutor)driver;

			//connecting to customer device
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Reporter.log("object created successfully",true);
			Thread.sleep(5000);
			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T); 
			Thread.sleep(5000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(5000);
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath"));  

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

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "clicked on continue successfully");


			// Login into PM dashboard 
			PM_Homepage pmhomepage= new  PM_Homepage(driver);
			logger.log(LogStatus.INFO, "PM Home page object created successfully");

			//screenshot
			//Verify User should view the module Existing Customer
			if(pmhomepage.get_existingCustomerText().equalsIgnoreCase("Existing Customer")){

				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);

				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Existing Customer module displayed successfully:"+pmhomepage.get_existingCustomerText());
				logger.log(LogStatus.INFO, "Existing Customer module displayed successfully",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Existing Customer module not displayed");
				logger.log(LogStatus.INFO, "Existing Customer module not displayed",image);

			}

			Thread.sleep(3000);


			ArrayList<String> query=DataBase_JDBC.executeSQLQuery_List("Select distinct top 1 A.AccountNumber, C.FirstName,C.MiddleInitial, C.LastName, CU.CompanyName, CC.ClassName, SOI.VacateDate, CU.IsAuctionBuyer , t1.name as militarytype 	"
					+ "from Account A with(nolock) "
					+ "Join AccountAddress AA with(nolock) on AA.AccountID = A.AccountID "
					+ "Join Address Ad with(nolock) on AA.AddressID = Ad.AddressID "
					+ "Join Customer CU with(nolock) on A.CustomerID = CU.CustomerID "
					+ "Join Contact C with(nolock) on CU.ContactID = C.ContactID "
					+ "Join CustomerClass CC with(nolock) on CU.CustomerClassID = CC.CustomerClassID "
					+ "Join AccountOrder AO with(nolock) on A.AccountID = AO.AccountID "
					+ "Join AccountOrderItem AOI with(nolock) on AO.AccountOrderID  = AOI.AccountOrderID "
					+ "Join StorageOrderItem SOI with(nolock)on AOI.StorageOrderItemID = SOI.StorageOrderItemID "
					+ "Join Type T with(nolock) on T.TypeID = SOI.StorageOrderItemTypeID "
					+ "left join customermilitaryinfo cmi with(nolock) on cmi.customerid=Cu.customerid "
					+ "left join Type T1 with(nolock) on T1.typeid=cmi.militarytypeid "
					+ " "
					+ "Where 1=1 "
					+ " "
					+ "And SOI.VacateDate is Null "
					+ "And CU.CustomerTypeID=90 "
					//+ "--And CU.CustomerClassID=3 "
					//+ "--And CU.IsMilitary = 0 and cmi.customerid is null  -- Non military "
					+ "And t1.name='Active Duty'  and cmi.isactive=1 ");
					//+ "--And t1.name='InActive'  and cmi.isactive=1-- InActive military "
					//+ "--And CU.IsAuctionBuyer=0 "
					//+ "--and aoi.siteid=31");
			
			String accNum=query.get(0);

			Thread.sleep(1000);
			

			pmhomepage.clk_AdvSearchLnk();;



			/*
			 * verifying the advance search page
			 */
			Advance_Search advSearch= new Advance_Search(driver);
			/*if("Customer Search".equalsIgnoreCase(advSearch.getadvSearchPage_Title())){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Advance search page is displayed sucessfully is displayed successfully");
				logger.log(LogStatus.INFO, "Advance search page is displayed successfully",image);
			}
			else{
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Advance search page is not displayed ");
				logger.log(LogStatus.INFO, "Advance search page is not displayed ",image);
			}
*/
			Advance_Search advsearch=new Advance_Search(driver);

			advsearch.enterAccNum(accNum);
					logger.log(LogStatus.INFO, "Account number entered is : " +accNum);
			Thread.sleep(2000);
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "img",image1);
			//advsearch.enterAccNum(tabledata.get("Account Number"));


			advsearch.clk_SearchAccount();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "Clicked on search button");
			Thread.sleep(5000);

			Cust_AccDetailsPage cust=new Cust_AccDetailsPage(driver);
			Thread.sleep(5000);
			if(cust.Verify_CustDashboard()){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "Customer dashboard is  displayed successfully",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer dashboard is  not displayed");
				logger.log(LogStatus.INFO, "Customer dashboard is  not displayed",image);

			}
			
		

			if(cust.verify_custInfo_Tab()){
				logger.log(LogStatus.PASS, "Customer info tab is displayed successfully");

			}else{
				logger.log(LogStatus.FAIL, "Customer info tab is not displayed ");
			}

			Acc_CustomerInfoPage custinfo=new Acc_CustomerInfoPage(driver);
			if(custinfo.verifymilitarystaus())
			{
				logger.log(LogStatus.PASS, " Militarystatus field is displayed");
			}else{
				logger.log(LogStatus.FAIL, "Militarystatus field is not displayed");
			}

			//verify if military status is active,verify view details link
			String status=custinfo.get_MilitaryStausText().substring(0, 11);;
			String expStatus=tabledata.get("status");
			if(status.contains(expStatus)){
				logger.log(LogStatus.INFO, " Militarystatus is :"+status);
				custinfo.verify_viewdetails_link();
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "view details link is displayed");
				logger.log(LogStatus.INFO, "view details link is displayed",image);
			}

			else{
				logger.log(LogStatus.FAIL, " Militarystatus is not Active:"+status);
							
			}

			//JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,2000)", "");
		
			custinfo.click_viewdetails_link();
			logger.log(LogStatus.INFO, "Clicked on View Details link successfully");
			Thread.sleep(3000);
			
			
			//verify the MW should be displayed
			boolean mwindow=driver.findElement(By.xpath("//span[text()='Military Status Details']")).isDisplayed();
if(mwindow){
	String scpath=Generic_Class.takeScreenShotPath();
	Reporter.log(scpath,true);
	String image=logger.addScreenCapture(scpath);
	logger.log(LogStatus.PASS, " Military status details Modal window is displayed");
	logger.log(LogStatus.INFO, " Military status details Modal window is displayed",image);
}
else{

	String scpath=Generic_Class.takeScreenShotPath();
	Reporter.log(scpath,true);
	if(resultFlag.equals("pass"))
		resultFlag="fail";
	String image=logger.addScreenCapture(scpath);
	logger.log(LogStatus.FAIL, "Military status details Modal window is not displayed");
	logger.log(LogStatus.INFO, "Military status details Modal window is  not displayed",image);

}

driver.findElement(By.partialLinkText("Close")).click();
Thread.sleep(1000);

			//screenshot
			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String  image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "image",image);
			
			






















		}catch(Exception ex){
			ex.printStackTrace();
			//In the catch block, set the variable resultFlag to “fail”
			resultFlag="fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			//logger.log(LogStatus.FAIL, "Validating Monthly rent and Promotions in Eligible Promotion Page",image);
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
		}


	}


	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashboard_Viewdetails_ActiveMilitary" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashboard_Viewdetails_ActiveMilitary" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashboard_Viewdetails_ActiveMilitary" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);


	}

}
