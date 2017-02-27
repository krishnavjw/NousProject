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

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Pages.Walkin_Reservation_Lease.VehicleStoragePage;
import Scenarios.Browser_Factory;

public class WalkInReservation_FindSpace_VehicleSearch_Existing extends Browser_Factory {

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

            if(PM_HomePage.isFindAndLeaseButtonDisplayed()){
                logger.log(LogStatus.PASS, "Find and Lease button displayed sucessfully on Walk in customer window");
}else{

                logger.log(LogStatus.FAIL, "Find and Lease button is not displayed sucessfully on Walk in customer window");
}

if(PM_HomePage.isFindAnExtReservationTextFieldDisplayed()){

                logger.log(LogStatus.PASS, "Or Find an Existing Reservation text field is dispalyed sucessfully on Walk in customer window");
}else{

                logger.log(LogStatus.FAIL, "Or Find an Existing Reservation text field is not dispalyed sucessfully on Walk in customer window");
}


if(PM_HomePage.isFindReservationDispalyed()){

                logger.log(LogStatus.PASS, "Find reservation button is dispalyed sucessfully on Walk in customer window");
}else{

                logger.log(LogStatus.FAIL, "Find reservation button is not dispalyed sucessfully on Walk in customer window");
}



Thread.sleep(3000);
PM_HomePage.clk_AdvSearchLnk();
logger.log(LogStatus.PASS, "clicked on advanced search link in the PM Dashboard sucessfully");


Thread.sleep(6000);
Advance_Search advSearch=new Advance_Search(driver);
logger.log(LogStatus.PASS, "creating object for advance search page sucessfully");


Thread.sleep(4000);
String SiteNumber=advSearch.getLocationNum();
logger.log(LogStatus.PASS, "location number is:"+SiteNumber);

Thread.sleep(1000);
String sqlQuery= "select Top 1 a.accountnumber  from account a "+
                                "join accountorder ao on ao.accountid=a.accountid "+
                                "join accountorderitem aoi on aoi.accountorderid=ao.accountorderid "+
                                "join site s on s.siteid=aoi.siteid "+
                                "join storageorderitem soi on soi.storageorderitemid=aoi.storageorderitemid "+
                                "left join Type T on T.TypeID = SOI.StorageOrderItemTypeID "+
                                "join rentalunit ru on ru.rentalunitid=soi.rentalunitid "+
                                "join productsite ps on ps.productsiteid= ru.ProductSiteid "+
                                "join Product p on p.ProductID = ps.ProductID "+
                                "join cltransaction clt on clt.accountorderitemid=aoi.accountorderitemid "+
                                "where soi.vacatedate is null "+
                                "and s.sitenumber='"+SiteNumber+"'" +
                                "and soi.StorageOrderItemTypeID=4300 "+
                                "and soi.anticipatedvacatedate is null "+
                                "group by a.accountnumber, aoi.accountorderitemid,s.sitenumber, ru.rentalunitnumber,soi.anticipatedvacatedate,T.description, p.Name "+
                                "having sum(clt.amount + clt.discountamount) >=0 ";

String AccountNumber=DataBase_JDBC.executeSQLQuery(sqlQuery);
Thread.sleep(6000);

if(advSearch.verifyAdvSearchPageIsOpened())
{
                logger.log(LogStatus.PASS, "Advanced Search page is opened");
                String scpath=Generic_Class.takeScreenShotPath();
                String image=logger.addScreenCapture(scpath);
                logger.log(LogStatus.INFO, "Image",image);

}
else{
                logger.log(LogStatus.PASS, "Advanced Search page is not opened");
                String scpath=Generic_Class.takeScreenShotPath();
                String image=logger.addScreenCapture(scpath);
                logger.log(LogStatus.INFO, "Image",image);
                resultFlag="fail";
}

Thread.sleep(2000);
advSearch.enterAccNum(AccountNumber);
logger.log(LogStatus.INFO, "Enter existing customer Account Number in Account field successfully");

Thread.sleep(3000);
advSearch.clickSearchAccbtn();
logger.log(LogStatus.INFO, "clicking on Search button successfully");

Cust_AccDetailsPage cust= new Cust_AccDetailsPage(driver);
Thread.sleep(8000);

if(cust.isCustdbTitleDisplayed()){

                String scpath=Generic_Class.takeScreenShotPath();
                String image=logger.addScreenCapture(scpath);
                logger.log(LogStatus.PASS, "customer Dashboard is displayed successfully");
                logger.log(LogStatus.INFO, "customer Dashboard is displayed successfully",image);
}else{

                if(resultFlag.equals("pass"))
                                resultFlag="fail";

                String scpath=Generic_Class.takeScreenShotPath();
                String image=logger.addScreenCapture(scpath);
                logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");
                logger.log(LogStatus.INFO, "customer Dashboard is not displayed ",image);
}

Thread.sleep(2000);
cust.sel_quickLinks_dropDown(driver);                                
logger.log(LogStatus.INFO, "Selected Add space option in customer dashboard to hold space for customer sucessfully");
Reporter.log("Selected Add space option successfully",true);


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

			/*if(StandardStoragePage.isSelected_yesradiobutton())
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
			}*/


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
