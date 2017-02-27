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

public class CustomerDashboard_VerifyCustomer_AuctionBuyer extends Browser_Factory{

	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerDashBoard","CustomerDashBoard",  "CustomerDashboard_VerifyCustomer_AuctionBuyer");
	}



	@Test(dataProvider="getLoginData")
	public void CustomerDashboard_VerifyCustomer_AuctionBuyer(Hashtable<String, String> tabledata) throws InterruptedException 
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
			logger=extent.startTest("CustomerDashboard_VerifyCustomer_AuctionBuyer","Verify Auction Buyer customer");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Clicked on Login button successfully");

			JavascriptExecutor jse = (JavascriptExecutor)driver;

			//=================== Customer Facing Screen =========================//
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

			
			Thread.sleep(5000);

			//======================= PM Dashboard ====================
			
			PM_Homepage pmhomepage= new  PM_Homepage(driver);
			Thread.sleep(5000);
			
			pmhomepage.clk_AdvSearchLnk();;
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Clicked on advance search link successfully");
			
			Advance_Search advsrch=new 	Advance_Search(driver);
			
			advsrch.search_All();
			logger.log(LogStatus.INFO, "Search all locations checkbox is checked");
			Thread.sleep(2000);
			advsrch.click_auctionBuyer_radiobtn();
			logger.log(LogStatus.INFO, "Selected Auction Buyer radio button successfully");
			Thread.sleep(3000);
			
			String varname1 = "Select distinct top 1 A.AccountNumber, C.FirstName,C.MiddleInitial, C.LastName, CU.CompanyName, CC.ClassName, SOI.VacateDate, CU.IsAuctionBuyer , t1.name as militarytype "+
					"from Account A with(nolock) "+
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
					"Where 1=1 "+
					"And SOI.VacateDate is Null "+
					"And CU.CustomerTypeID=90 "+
					"And CU.IsAuctionBuyer=1 ";
			
			ArrayList<String> query=DataBase_JDBC.executeSQLQuery_List(varname1);
			String firstname=query.get(1);
			String lastname=query.get(3);
			
			String auctionBuyer_indicator=query.get(7);
			
			advsrch.enterFirstName(firstname);
			Thread.sleep(1000);
			advsrch.enterLastName(lastname);
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "Entered the required Auction buyer data");
			
			jse.executeScript("window.scrollBy(1000,0)", "");
			jse.executeScript("window.scrollBy(0,250)", "");
	
			Thread.sleep(5000);
			//screenshot
			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String  image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "image",image);
			Thread.sleep(3000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			
			advsrch.match_AllField();
			

			Thread.sleep(1000);
			jse.executeScript("window.scrollBy(1000,0)", "");
			jse.executeScript("window.scrollBy(0,500)", "");
			advsrch.clickSearchbtn();
			Thread.sleep(10000);
			
			logger.log(LogStatus.INFO, "Selected Match all search fields and clicked on search button");
			
			//screenshot
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "image",image);
			
			Thread.sleep(10000);
			
			//verify the auction buyer indicator is displayed in the grid
			
			boolean flag=advsrch.verify_autionBuyer_indicator();
				
			if(flag){
				 scpath=Generic_Class.takeScreenShotPath();
				 Reporter.log(scpath,true);
				 image=logger.addScreenCapture(scpath);
				 logger.log(LogStatus.PASS, "Auction Buyer indicator is displayed successfully : "+flag+ " and the flag is set to : "+auctionBuyer_indicator+" in Database");
				 logger.log(LogStatus.INFO, "Auction Buyer indicator is  displayed successfully "+flag+ " and the flag is set to : "+auctionBuyer_indicator+" in Database",image);
			}
			else{
	
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Auction Buyer indicator is not displayed"+flag);
				logger.log(LogStatus.INFO, "Auction Buyer indicator is not displayed"+flag,image);
		
			}
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			
			advsrch.click_accnum_Grid();
			logger.log(LogStatus.INFO, "Clicked on the accountnumber link in the result grid");
			Thread.sleep(12000);

			Cust_AccDetailsPage accPage = new Cust_AccDetailsPage(driver);
			Thread.sleep(1000);
			
			if(accPage.isDisplayedAuctionLink()){
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Customer's Auction Buyer Dashboard is displayed succefully");
			logger.log(LogStatus.INFO, "image",image);
			}else{
				
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer's Auction Buyer Dashboard is displayed");
				logger.log(LogStatus.INFO, "img",image);
	
			}
			Thread.sleep(1000);
			
			accPage.Click_Auctionlink();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Auction Buyer link in Customer Dashboard");
			
			boolean auctionBuyerTxt = driver.findElement(By.xpath("//div[@id='customerDashboard']//span[(text()='Auction Buyer')]")).isDisplayed();
			
			if(auctionBuyerTxt)
			{
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer's Auction Buyer Page is displayed succefully");
				logger.log(LogStatus.INFO, "image",image);
				
			}else{
				
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer's Auction Buyer Page is not displayed");
				logger.log(LogStatus.INFO, "img",image);
	
			}
			
			
			driver.findElement(By.xpath("//a[contains(text(),'Back To Dashboard')]")).click();
			Thread.sleep(8000);
			logger.log(LogStatus.INFO, "Clicked on back to dashboard Button");
	
			
			
			
			String qry1 = "select top 1 A.accountnumber from auctionunit au with(nolock) "+
					"join account a with(nolock) on a.accountid=au.buyeraccountid and au.deleted=0 "+
					"join customer cu with(nolock) on cu.customerid=a.customerid "+
					"left join vw_unitdetails UW on UW.accountnumber=a.accountnumber "+
					"where UW.accountnumber is null ";
			
			String AccountNumber = DataBase_JDBC.executeSQLQuery(qry1);
			Thread.sleep(3000);
			
			pmhomepage.clk_AdvSearchLnk();;
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Clicked on advance search link successfully");
			
			advsrch.enterAccNum(AccountNumber);
			Thread.sleep(3000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Entered Account Number who is only Auction Buyer and not a customer: "+AccountNumber);
			logger.log(LogStatus.INFO, "image",image);
			Thread.sleep(2000);
			
			advsrch.clickButton();
			logger.log(LogStatus.INFO, "Clicked on search button");
			Thread.sleep(8000);
			
			
			boolean auctionBuyerTxt1 = driver.findElement(By.xpath("//div[@id='customerDashboard']//span[(text()='Auction Buyer')]")).isDisplayed();
			
			if(auctionBuyerTxt1){
				
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer's Auction Buyer Page is displayed succefully");
				logger.log(LogStatus.INFO, "image",image);
				}else{
					
					 scpath=Generic_Class.takeScreenShotPath();
					Reporter.log(scpath,true);
					if(resultFlag.equals("pass"))
						resultFlag="fail";
					 image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Customer's Auction Buyer Page is not displayed");
					logger.log(LogStatus.INFO, "img",image);
		
				}

			
			
			
			
			
			
			
			
			
			

	}catch(Exception ex){
		ex.printStackTrace();
		resultFlag="fail";
		String scpath=Generic_Class.takeScreenShotPath();
		String image=logger.addScreenCapture(scpath);
		logger.log(LogStatus.INFO, "img",image);
		logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
	}


}


@AfterMethod
public void afterMethod(){

	Reporter.log(resultFlag,true);

	if(resultFlag.equals("pass")){
		Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashboard_VerifyCustomer_AuctionBuyer" , "Status", "Pass");

	}else if (resultFlag.equals("fail")){

		Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashboard_VerifyCustomer_AuctionBuyer" , "Status", "Fail");
	}else{
		Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashboard_VerifyCustomer_AuctionBuyer" , "Status", "Skip");
	}

	extent.endTest(logger);
	extent.flush();
	Reporter.log("Test case completed: " +testcaseName, true);


	}

}
