package Scenarios.WalkinReservation;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.AdvSearch_PopUp;
import Pages.AdvSearchPages.Adv_SearchResultPage;
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.CreateReserVation_PopUp;
import Pages.Walkin_Reservation_Lease.CreateReservation;
import Pages.Walkin_Reservation_Lease.Hold_PopupPage;
import Pages.Walkin_Reservation_Lease.Leasing_ConfirmSpace;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.SpecificSpace;
//import Pages.Walkin_Reservation_Lease.SpaceDashboard_Loc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Pages.Walkin_Reservation_Lease.ViewReservationPage;
import ProjectSpecificMethods.PS_GenericMethods;
import Scenarios.Browser_Factory;

public class WalkInReservation_FindSpace_UsingSpaceNumber extends Browser_Factory{

	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");


	@DataProvider
	public Object[][] getLoginData() 
	{

		return Excel.getCellValue_inlist(path, "Reservation","Reservation",  "WalkInReservation_FindSpace_UsingSpaceNumber");
	}

	@Test(dataProvider="getLoginData")
	public void WalkInReservation_FindSpace_UsingSpaceNumber(Hashtable<String, String> tabledata) throws InterruptedException 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Reservation").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		try
		{
			logger=extent.startTest("WalkInReservation_FindSpace_UsingSpaceNumber", "New Customer find a space using space number ");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			Generic_Class generics = new Generic_Class();
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			Thread.sleep(6000);

			// login.clk_CustomerApproval();

			LoginPage login = new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.PASS,"Login to WC2 as PM is successful");

			//=================Handling customer facing device=================
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Bifrostpop.clickContiDevice();

			/*		String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);



		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			driver.switchTo().window(tabs.get(1));
			driver.get("http://wc2qa.ps.com/CustomerScreen/Mount");  

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

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
			 */

			//=====================================================================

			PM_Homepage PM_HomePage = new PM_Homepage(driver);
			Thread.sleep(5000);
			PM_HomePage.clk_findAndLeaseSpace();
			logger.log(LogStatus.PASS,"Clicked on Find And Lease A Space button");
			Reporter.log("Clicked on Find And Lease A Space button",true);
			Thread.sleep(8000);
			String WalkInPageURL = driver.getCurrentUrl();

			if(WalkInPageURL.contains("Walkin"))
			{
				Reporter.log("User is navigated to Walkin page",true);
				logger.log(LogStatus.PASS,"User is navigated to Walkin page");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				Reporter.log("User is not navigated to Walkin page",true);
				logger.log(LogStatus.FAIL,"User is not navigated to Walkin page");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				throw new Exception("User is not navigated to Walkin page");
			}

			StandardStoragePage StandardStoragePage = new StandardStoragePage(driver);

			if(StandardStoragePage.verify_StdStorageHdr())
			{

				Reporter.log("Standard Storage Tab is selected by default when walkin's page is launched",true);
				logger.log(LogStatus.PASS,"Standard Storage Tab is selected by default when walkin's page is launched");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				Reporter.log("Standard Storage Tab is not selected by default when walkin's page is launched",true);
				logger.log(LogStatus.FAIL,"Standard Storage Tab is not selected by default when walkin's page is launched");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				throw new Exception("Standard Storage Tab is not selected by default when walkin's page is launched");
			}

			if(StandardStoragePage.isSelected_yesradiobutton())
			{

				Reporter.log("New Customer Radio button is selected by default when walkin's page is launched",true);
				logger.log(LogStatus.PASS,"New Customer Radio button is selected by default when walkin's page is launched");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				Reporter.log("New Customer Radio button is not selected by default when walkin's page is launched",true);
				logger.log(LogStatus.FAIL,"New Customer Radio button is not selected by default when walkin's page is launched");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				throw new Exception("New Customer Radio button is not selected by default when walkin's page is launched");
			}


			if(StandardStoragePage.isdisplayed_StandardStorage())
			{
				Reporter.log("Standard Storage Tab is Displayed",true);
				logger.log(LogStatus.PASS,"Standard Storage Tab is Displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				Reporter.log("Standard Storage Tab is not displayed",true);
				logger.log(LogStatus.PASS,"Standard Storage Tab is not displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				throw new Exception("Standard Storage Tab is not displayed");
			}

			if(StandardStoragePage.isdisplayed_VehicleStorage())
			{
				Reporter.log("Vehicle Storage Tab is Displayed",true);
				logger.log(LogStatus.PASS,"Vehicle Storage Tab is Displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				Reporter.log("Vehicle Storage Tab is not displayed",true);
				logger.log(LogStatus.FAIL,"Vehicle Storage Tab is not displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				throw new Exception("Vehicle Storage Tab is not displayed");
			}


			if(StandardStoragePage.isdisplayed_SpecificStorage())
			{
				Reporter.log("Specific Space Tab is Displayed",true);
				logger.log(LogStatus.PASS,"Specific Space Tab is Displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				Reporter.log("Specific Space Tab is not displayed",true);
				logger.log(LogStatus.FAIL,"Specific Space Tab is not displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				throw new Exception("Specific Space Tab is not displayed");
			}

			StandardStoragePage.Clk_ChkBx_AvlSpace();
			logger.log(LogStatus.PASS,"Clicked on available bold space size  checkbox in StandardStorage sucessfully");

			StandardStoragePage.click_Search();
			logger.log(LogStatus.INFO, "Click on search button successfully");

			//=====================Fetching space number and based on that clicking the radio button========================
			Thread.sleep(10000);
			List <WebElement> rows=driver.findElements(By.xpath("//form[@id='frmReserveUnits']//div//table/tbody/tr"));
			String spacenum=null;
			logger.log(LogStatus.INFO,"Total number of avaiable sizes count is:"+rows.size());
			if(rows.size()>0){
				Thread.sleep(5000);

				spacenum=driver.findElement(By.xpath("//form[@id='frmReserveUnits']//div//table/tbody/tr[1]/td[4]")).getText();

				Reporter.log("space number is:"+spacenum,true);
			}else{

				logger.log(LogStatus.FAIL, "Application is not populating any data/space details with selected size dimension");
				throw new Exception("Application is not populating any data/space details");

			}


			Thread.sleep(2000);
			StandardStoragePage.click_SpecificSpace();
			Reporter.log("Clicked on Specific Space tab",true);
			logger.log(LogStatus.INFO,"Clicked on Specific Space tab");

			SpecificSpace SpecificSpace = new SpecificSpace(driver);
			SpecificSpace.Clk_SearchBtn();
			Reporter.log("Clicked on Search button",true);
			logger.log(LogStatus.INFO,"Clicked on Search button");

			Thread.sleep(5000);

			if(SpecificSpace.verify_NotificationMsg().contains("Please enter a specific space"))
			{
				Reporter.log("Information Msg 'Please enter a specific space' is Displayed",true);
				logger.log(LogStatus.PASS,"Information Msg 'Please enter a specific space' is Displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				Reporter.log("Information Msg 'Please enter a specific space' is not displayed",true);
				logger.log(LogStatus.FAIL,"Information Msg 'Please enter a specific space' is not displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				throw new Exception("Information Msg 'Please enter a specific space' is not displayed");
			}

			SpecificSpace.enter_SpaceNum(tabledata.get("InvalidSpaceNum"));
			Reporter.log("Entered invalid space number",true);
			logger.log(LogStatus.INFO,"Entered invalid space number");

			SpecificSpace.Clk_SearchBtn();
			Reporter.log("Clicked on Search button",true);
			logger.log(LogStatus.INFO,"Clicked on Search button");

			Thread.sleep(5000);

			if(SpecificSpace.verify_InfoMsg_NoSrchResult())
			{
				Reporter.log("Information Msg 'No Search Results at this location.' is Displayed when invalid space number is entered",true);
				logger.log(LogStatus.PASS,"Information Msg 'No Search Results at this location.' is Displayed  when invalid space number is entered");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				Reporter.log("Information Msg 'No Search Results at this location.' is not displayed when invalid space number is entered",true);
				logger.log(LogStatus.FAIL,"Information Msg 'Please enter a specific space' is not displayed when invalid space number is entered");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				throw new Exception("Information Msg 'No Search Results at this location.' is not displayed when invalid space number is entered");
			}

			SpecificSpace.enter_SpaceNum(spacenum);
			Reporter.log("Entered Space number",true);
			logger.log(LogStatus.INFO,"Entered Space number");

			SpecificSpace.Clk_SearchBtn();
			Reporter.log("Clicked on Search button",true);
			logger.log(LogStatus.INFO,"Clicked on Search button");

			List <WebElement> norows=driver.findElements(By.xpath("//div[@class='k-grid-content ps-container']//table//tbody//tr"));
			String space=null;
			if(norows.size()>0){

				Thread.sleep(5000);
				space=driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']//table//tbody//tr[1]/td[4]")).getText();
				Reporter.log("space number is:"+space,true);
			}else{

				logger.log(LogStatus.INFO, "Application is not populating any data/space details");

			}

			WebElement RdBtn_Space = driver.findElement(By.xpath("//td[@class='grid-cell-space'][text()='"+space+"']/../td/input[@name='selectedIds']"));
			logger.log(LogStatus.PASS, "check the radio button based on the space and click on the  reservation button");
			jse.executeScript("arguments[0].scrollIntoView()", RdBtn_Space); 
			Thread.sleep(5000);
			jse.executeScript("arguments[0].click();", RdBtn_Space);

			logger.log(LogStatus.PASS,"Clicked on check box of a space" + space);
			Reporter.log("Clicked on check box of a space" + space,true);


			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");


			SpecificSpace.Clk_ReserveBtn();
			logger.log(LogStatus.INFO,"Clicked on Reserve button");
			Reporter.log("Clicked on Reserve button",true);

			Thread.sleep(8000);

			CreateReservation CreateReservation = new CreateReservation(driver);
			Thread.sleep(3000);

			if(CreateReservation.verify_createReservationHdrIsDisplayed())
			{

				Reporter.log("Create Reservation Screen is displayed",true);
				logger.log(LogStatus.PASS,"Create Reservation Screen is displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				Reporter.log("Create Reservation Screen is not displayed",true);
				logger.log(LogStatus.FAIL,"Create Reservation Screen is not displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				throw new Exception("Create Reservation Screen is not displayed");
			}


			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");


			CreateReservation.clk_CancelReservationButton();
			logger.log(LogStatus.PASS,"Clicked on Cancel Reservation button");
			Reporter.log("Clicked on Cancel Reservation button",true);

			Thread.sleep(10000);

			Reporter.log("URL:" + driver.getCurrentUrl(),true);
			if(driver.getCurrentUrl().contains("Dashboard"))
			{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,"PM Dashboard screen is displayed");
				logger.log(LogStatus.INFO, "PM Dashboard screen is displayed",image);

			}
			else
			{


				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL,"PM Dashboard screen is not displayed");
				throw new Exception("PM Dashboard screen is not displayed:");			

			}

			PM_HomePage.clk_findAndLeaseSpace();
			logger.log(LogStatus.PASS,"Clicked on Find And Lease A Space button");
			Reporter.log("Clicked on Find And Lease A Space button",true);
			Thread.sleep(8000);

			StandardStoragePage.click_SpecificSpace();
			Reporter.log("Clicked on Specific Space tab",true);
			logger.log(LogStatus.INFO,"Clicked on Specific Space tab");

			Thread.sleep(5000);

			SpecificSpace.enter_SpaceNum(spacenum);
			Reporter.log("Entered Space number",true);
			logger.log(LogStatus.INFO,"Entered Space number");

			SpecificSpace.Clk_SearchBtn();
			Reporter.log("Clicked on Search button",true);
			logger.log(LogStatus.INFO,"Clicked on Search button");

			Thread.sleep(8000);


			if(norows.size()>0){

				Thread.sleep(5000);
				space=driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']//table//tbody//tr[1]/td[4]")).getText();
				Reporter.log("space number is:"+space,true);
			}else{

				logger.log(LogStatus.INFO, "Application is not populating any data/space details");

			}

			WebElement RdBtn_Space2 = driver.findElement(By.xpath("//td[@class='grid-cell-space'][text()='"+space+"']/../td/input[@name='selectedIds']"));
			jse.executeScript("arguments[0].scrollIntoView()", RdBtn_Space2); 
			Thread.sleep(5000);
			jse.executeScript("arguments[0].click();", RdBtn_Space2);

			logger.log(LogStatus.PASS,"Clicked on check box of a space" + space);
			Reporter.log("Clicked on check box of a space" + space,true);

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			SpecificSpace.Clk_RentBtn();
			logger.log(LogStatus.INFO,"Clicked on Rent button");
			Reporter.log("Clicked on Rent button",true);

			Thread.sleep(10000);



			Leasing_ConfirmSpace Leasing_ConfirmSpace = new Leasing_ConfirmSpace(driver);

			if(Leasing_ConfirmSpace.getTitle_LeasingPage().equals("Leasing"))
			{
				Reporter.log("Leasing Screen is displayed",true);
				logger.log(LogStatus.PASS,"Leasing Screen is displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				Reporter.log("Leasing Screen is not displayed",true);
				logger.log(LogStatus.FAIL,"Leasing Screen is not displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				throw new Exception("Leasing Screen is not displayed");
			}

			Leasing_ConfirmSpace.click_CancelLease();
			logger.log(LogStatus.INFO,"Clicked on Cancel Lease button");
			Reporter.log("Clicked on Cancel Lease button",true);

			Leasing_ConfirmSpace.Sel_ValFronCancelRsn("Desired Promotion Not Available");
			logger.log(LogStatus.INFO,"Cancel Lease reason is selected from dropdown");
			Reporter.log("Cancel Lease reason is selected from dropdown",true);

			Leasing_ConfirmSpace.enter_EmpId(tabledata.get("UserName"));
			logger.log(LogStatus.INFO,"Entered employee id");
			Reporter.log("Entered employee id",true);

			Leasing_ConfirmSpace.clk_onyesBtn();
			logger.log(LogStatus.INFO,"Clicked on Yes button");
			Reporter.log("Clicked on Yes button",true);

			Thread.sleep(10000);


			Reporter.log("URL:" + driver.getCurrentUrl(),true);
			if(driver.getCurrentUrl().contains("Dashboard"))
			{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,"PM Dashboard screen is displayed");
				logger.log(LogStatus.INFO, "PM Dashboard screen is displayed",image);

			}
			else
			{


				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL,"PM Dashboard screen is not displayed");
				throw new Exception("PM Dashboard screen is not displayed:");			

			}


			PM_HomePage.clk_findAndLeaseSpace();
			logger.log(LogStatus.PASS,"Clicked on Find And Lease A Space button");
			Reporter.log("Clicked on Find And Lease A Space button",true);
			Thread.sleep(8000);

			StandardStoragePage.click_SpecificSpace();
			Reporter.log("Clicked on Specific Space tab",true);
			logger.log(LogStatus.INFO,"Clicked on Specific Space tab");

			Thread.sleep(5000);

			SpecificSpace.enter_SpaceNum(spacenum);
			Reporter.log("Entered Space number",true);
			logger.log(LogStatus.INFO,"Entered Space number");

			SpecificSpace.Clk_SearchBtn();
			Reporter.log("Clicked on Search button",true);
			logger.log(LogStatus.INFO,"Clicked on Search button");

			Thread.sleep(8000);

			if(norows.size()>0){

				Thread.sleep(5000);
				space=driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']//table//tbody//tr[1]/td[4]")).getText();
				Reporter.log("space number is:"+space,true);
			}else{

				logger.log(LogStatus.INFO, "Application is not populating any data/space details");

			}

			WebElement RdBtn_Space1 = driver.findElement(By.xpath("//td[@class='grid-cell-space'][text()='"+space+"']/../td/input[@name='selectedIds']"));


			jse.executeScript("arguments[0].scrollIntoView()", RdBtn_Space1); 
			Thread.sleep(5000);
			jse.executeScript("arguments[0].click();", RdBtn_Space1);

			logger.log(LogStatus.PASS,"Clicked on check box of a space" + space);
			Reporter.log("Clicked on check box of a space" + space,true);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");


			SpecificSpace.Clk_HoldBtn();
			logger.log(LogStatus.INFO,"Clicked on Hold button");
			Reporter.log("Clicked on Hold button",true);

			Hold_PopupPage popUp=new Hold_PopupPage(driver);
			Thread.sleep(5000);
			Reporter.log("-------------------"+popUp.get_HoldPopTitle().trim(),true);
			if(popUp.get_HoldPopTitle().trim().contains(tabledata.get("HoldPopTitle")))
			{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "HoldPopTitle is displayed successfully");
				logger.log(LogStatus.INFO, "HoldPopTitle is displayed successfully",image);
			}
			else{

				resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "HoldPopTitle is not displayed ");
				logger.log(LogStatus.INFO, "HoldPopTitle is not displayed ",image);
				throw new Exception("HoldPopTitle is not displayed");

			}
			Thread.sleep(2000);
			popUp.clk_CancelBtn();
			logger.log(LogStatus.INFO,"Clicked on Cancel button in hold popup page");
			Reporter.log("Clicked on Cancel button in hold popup page",true);
			resultFlag="pass";

		}catch(Exception e){
			Reporter.log("Exception:Reservation" + e,true);
			resultFlag="fail";
		}


	}

	@AfterMethod
	public void afterMethod(){

		Reporter.log("resultFlag: " + resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "Reservation","WalkInReservation_FindSpace_UsingSpaceNumber" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Reservation","WalkInReservation_FindSpace_UsingSpaceNumber" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Reservation","WalkInReservation_FindSpace_UsingSpaceNumber" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}




}
