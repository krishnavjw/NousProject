package Scenarios.WalkinReservation;

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

import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Pages.Walkin_Reservation_Lease.VehicleStoragePage;
import Scenarios.Browser_Factory;

public class WalkInReservation_FindSpace_VehicleSearch extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");
	public static String FirstNm;

	@DataProvider
	public Object[][] getLoginData() 
	{

		return Excel.getCellValue_inlist(path, "Reservation","Reservation",  "WalkInReservation_FindSpace_VehicleSearch");
	}

	@Test(dataProvider="getLoginData")
	public void WalkInReservation_CustomerWalkInToReserveASpaceAtSameSite_StandardStorage_Individual(Hashtable<String, String> tabledata) throws InterruptedException 
	{



		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Reservation").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		try
		{
			logger=extent.startTest("WalkInReservation_FindSpace_VehicleSearch", "Customer walk in to reserve a space");

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
				logger.log(LogStatus.FAIL,"Vehicle Storage Tab is Displayed");
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
			
			StandardStoragePage.click_VehicleStorage();
			logger.log(LogStatus.INFO,"Clicked on vehicle storage tab");
			Reporter.log("Clicked on vehicle storage tab",true);
			
			
			Thread.sleep(5000);
			VehicleStoragePage VehicleStoragePage = new VehicleStoragePage(driver);
			VehicleStoragePage.click_Car();
			logger.log(LogStatus.INFO,"Clicked on car checkbox in vehicle storage");
			Reporter.log("Clicked on car checkbox in vehicle storage",true);
			
			VehicleStoragePage.clk_SearchBtn();
			logger.log(LogStatus.INFO,"Clicked on Search button in vehicle storage");
			Reporter.log("Clicked on Search button in vehicle storage",true);
			
			
			Thread.sleep(5000);
			//List <WebElement> norows=driver.findElements(By.xpath("//div[@class='grid space-grid k-grid k-widget']//table//tbody//tr"));
			int NoOfSpaces = Integer.parseInt(VehicleStoragePage.get_NoOfspaces());
			if(NoOfSpaces!=0){
				Reporter.log("Application is populating data for vehicle search",true);
				logger.log(LogStatus.PASS, "Application is populating data for vehicle search");
				
			}else{
				Reporter.log("Application is not populating data for vehicle search",true);
				logger.log(LogStatus.FAIL, "Application is not populating any data for vehicle search");
				throw new Exception("Application is not populating any data for vehicle search");

			}
			resultFlag="pass";
		}
		catch(Exception e){
			Reporter.log("Exception:Reservation" + e,true);
			resultFlag="fail";
		}	
	}



	@AfterMethod
	public void afterMethod(){

		Reporter.log("resultFlag: " + resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "Reservation","WalkInReservation_FindSpace_VehicleSearch" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Reservation","WalkInReservation_FindSpace_VehicleSearch" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Reservation","WalkInReservation_FindSpace_VehicleSearch" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();

	}
}
