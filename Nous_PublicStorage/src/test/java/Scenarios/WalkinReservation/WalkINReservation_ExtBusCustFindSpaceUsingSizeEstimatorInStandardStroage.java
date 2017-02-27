package Scenarios.WalkinReservation;

import java.awt.Robot;
import java.awt.event.KeyEvent;
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
import Pages.HomePages.InProgress_HoldLnkPage;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.CreateReservation;
import Pages.Walkin_Reservation_Lease.Hold_PopupPage;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_OtherLocations;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Scenarios.Browser_Factory;

public class WalkINReservation_ExtBusCustFindSpaceUsingSizeEstimatorInStandardStroage extends Browser_Factory {

	public ExtentTest logger;
	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";


	@DataProvider
	public Object[][] getResvnData() 
	{
		return Excel.getCellValue_inlist(path, "Reservation","Reservation","WalkINReservation_ExtBusCustFindSpaceUsingSizeEstimatorInStandardStroage");
	}

	@Test(dataProvider="getResvnData")
	public void WalkINReservation_ExtBusCustFindSpaceUsingSizeEstimatorInStandardStroage(Hashtable<String, String> tabledata) throws Exception 
	{


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Reservation").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the WalkINReservation_ExtBusCustFindSpaceUsingSizeEstimatorInStandardStroage test");
		}


		try{
			logger=extent.startTest("WalkINReservation_ExtBusCustFindSpaceUsingSizeEstimatorInStandardStroage", "Find space using size, size estimator and space number - in standard storoge for existing business customer");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage loginPage = new LoginPage(driver);
			logger.log(LogStatus.PASS, "creating object for the Login Page sucessfully ");
			Thread.sleep(2000);
			loginPage.enterUserName(tabledata.get("UserName"));
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "entered username sucessfully");
			Thread.sleep(2000);
			loginPage.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.PASS, "entered password sucessfully");
			Thread.sleep(2000);
			loginPage.clickLogin();
			logger.log(LogStatus.PASS, "clicked on login in button sucessfully");
			logger.log(LogStatus.PASS, "Login to Application  successfully");

			//=================Handling customer facing device========================
			Thread.sleep(2000);
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");

			Bifrostpop.clickContiDevice();

			/*String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			driver.switchTo().window(tabs.get(1));
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

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(9000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(9000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);*/
			//=================================================================================
			Thread.sleep(4000);
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			logger.log(LogStatus.PASS, "User sucessfully logged into application");
			logger.log(LogStatus.PASS, "creating object for the PM home page sucessfully");
			if(pmhomepage.get_WlkInCustText().trim().contains(tabledata.get("walkInCustTitle"))){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Walk in customer window displayed sucessfully");
				logger.log(LogStatus.INFO, "Walk in customer window displayed sucessfully",image);
				resultFlag="pass";
			}
			else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Walk in customer window is not displayed");
				logger.log(LogStatus.INFO, "Walk in customer window is not displayed",image);

			}

			if(pmhomepage.isFindAndLeaseButtonDisplayed()){
				logger.log(LogStatus.PASS, "Find and Lease button displayed sucessfully on Walk in customer window");
			}else{

				logger.log(LogStatus.FAIL, "Find and Lease button is not displayed sucessfully on Walk in customer window");
			}

			if(pmhomepage.isFindAnExtReservationTextFieldDisplayed()){

				logger.log(LogStatus.PASS, "Or Find an Existing Reservation text field is dispalyed sucessfully on Walk in customer window");
			}else{

				logger.log(LogStatus.FAIL, "Or Find an Existing Reservation text field is not dispalyed sucessfully on Walk in customer window");
			}


			if(pmhomepage.isFindReservationDispalyed()){

				logger.log(LogStatus.PASS, "Find reservation button is dispalyed sucessfully on Walk in customer window");
			}else{

				logger.log(LogStatus.FAIL, "Find reservation button is not dispalyed sucessfully on Walk in customer window");
			}
			
			Thread.sleep(3000);
			pmhomepage.clk_AdvSearchLnk();
			logger.log(LogStatus.PASS, "clicked on advanced search link in the PM Dashboard sucessfully");


			Thread.sleep(6000);
			Advance_Search advSearch=new Advance_Search(driver);
			logger.log(LogStatus.PASS, "creating object for advance search page sucessfully");
			
			String SiteNumber=advSearch.getLocationNum();
			logger.log(LogStatus.PASS, "location number is:"+SiteNumber);

			Thread.sleep(2000);
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
					"and soi.StorageOrderItemTypeID=4301 "+
					"and soi.anticipatedvacatedate is null "+
					"group by a.accountnumber, aoi.accountorderitemid,s.sitenumber, ru.rentalunitnumber,soi.anticipatedvacatedate,T.description, p.Name "+
					"having sum(clt.amount + clt.discountamount) >0 ";
			

			String AccountNumber=DataBase_JDBC.executeSQLQuery(sqlQuery);
			Thread.sleep(2000);
		


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
			logger.log(LogStatus.INFO, "Enter existing customer Account Num in Account field successfully");

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

			Thread.sleep(3000);	
			StandardStoragePage stdStorage=new StandardStoragePage(driver);
			logger.log(LogStatus.PASS, "Creating object for the Standard stroage page sucessfully");

			Thread.sleep(6000);
			if(stdStorage.isdisplayed_StandardStorage()){
				logger.log(LogStatus.PASS, "Standard Stroage screen is dispalyed sucessfully");
			}else{
				logger.log(LogStatus.FAIL, "Standard Stroage screen is not dispalyed sucessfully");
			}


			Thread.sleep(1000);
			if(stdStorage.isdisplayed_VehicleStorage()){
				logger.log(LogStatus.PASS, "Vehicle Stroage screen is dispalyed sucessfully");
			}else{

				logger.log(LogStatus.FAIL, "Vehicle Stroage screen is not dispalyed sucessfully");
			}

			Thread.sleep(1000);
			if(stdStorage.isdisplayed_SpecificStorage()){
				logger.log(LogStatus.PASS, "Specific space screen is dispalyed sucessfully");
			}else{
				logger.log(LogStatus.FAIL, "Specific space screen is not dispalyed sucessfully");
			}

			Thread.sleep(1000);

			if(stdStorage.isSelected_ExtCustYesRadioButton()){
				logger.log(LogStatus.PASS, "New Customer Radio button selected by default when WALK-INS: FIND A SPACE page is launched sucessfully");

			}else{

				logger.log(LogStatus.FAIL, "New Customer Radio button is not selected by default when WALK-INS: FIND A SPACE page is launched sucessfully");
			}

			stdStorage.click_Search();
			logger.log(LogStatus.INFO, "Click on search button successfully without selecting any spaces");

			Thread.sleep(1000);
			boolean value=driver.findElement(By.xpath("//div[@class='notification alert k-notification-wrap']")).isDisplayed();
			if(value){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Error message Please select a space size is displayed successfully");
				logger.log(LogStatus.INFO, "Error message Please select a space size is displayed successfully",image);

			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Error message Please select a space size is not displayed ");
				logger.log(LogStatus.INFO, "Error message Please select a space size is not displayed ",image);
			}

			Thread.sleep(5000);
			//Use Size Calculator" in the Standard Storage search section 
			stdStorage.click_SizeEstimator();
			logger.log(LogStatus.INFO, "Click on SizeEstimator radio button successfully");

			
			Thread.sleep(5000);
			stdStorage.click_StudioAptmnt();
			logger.log(LogStatus.INFO, "Click on Studio Apartment radio button successfully");
			// click on king or queen mattress yes radio button
			stdStorage.clk_radio_matress_yes();

			//Select "Appliances" as store items
			stdStorage.click_Appliances();
			logger.log(LogStatus.INFO, "Click on Appliances radio button successfully");

			stdStorage.click_DriveupAccess();
			logger.log(LogStatus.INFO, "Click on drive up access check  box successfully");

			stdStorage.click_Search();
			logger.log(LogStatus.INFO, "Click on search button successfully");

			Thread.sleep(3000);
			SpaceDashboard_ThisLoc thisloc=new SpaceDashboard_ThisLoc(driver);
			logger.log(LogStatus.PASS, "creating object for the This location page sucessfully");

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			if(thisloc.isRentButtonDisplayed()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Rent button is dispalyed sucessfully in this loaction page ");
				logger.log(LogStatus.PASS, "Rent button is dispalyed sucessfully in this loaction page ",image);

			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Rent button is not dispalyed sucessfully in this loaction page ");
				logger.log(LogStatus.FAIL, "Rent button is not dispalyed sucessfully in this loaction page ",image);

			}

			if(thisloc.isReserveButtonDisplayed()){

				logger.log(LogStatus.PASS, "Reserve button is dispalyed sucessfully in this loaction page");
			}else{

				logger.log(LogStatus.FAIL, "Reserve button is not dispalyed sucessfully in this loaction page");
			}
			if(thisloc.isHoldButtonDisplayed()){

				logger.log(LogStatus.PASS, "Hold button is dispalyed sucessfully in this loaction page");
			}else{
				logger.log(LogStatus.FAIL, "Hold button is not dispalyed sucessfully in this loaction page");

			}

			Thread.sleep(6000);
			thisloc.click_otherLocations();
			logger.log(LogStatus.PASS, "clicked on the other location tab sucessfully");


			SpaceDashboard_OtherLocations other=new SpaceDashboard_OtherLocations(driver);
			logger.log(LogStatus.PASS, "created object for other location page sucessfully");

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			if(other.isReserveOffSiteDisplayed()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "ReserveOffSite button is dispalyed sucessfully in other loaction page ");
				logger.log(LogStatus.PASS, "ReserveOffSite button is dispalyed sucessfully in other loaction page ",image);


			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "ReserveOffSite button is not dispalyed sucessfully in other loaction page ");
				logger.log(LogStatus.FAIL, "ReserveOffSite button is not dispalyed sucessfully in other loaction page ",image);


			}

			Thread.sleep(1000);
			stdStorage.clk_ThisLocations();
			logger.log(LogStatus.PASS, "clicked on the this location tab sucessfully");

			//=====================Fetching space number and based on that clicking the radio button========================
			Thread.sleep(10000);
			List <WebElement> norows=driver.findElements(By.xpath("//form[@id='frmReserveUnits']//div//table/tbody/tr"));
			String space=null;
			logger.log(LogStatus.INFO,"Total number of avaiable sizes count is:"+norows.size());
			if(norows.size()>0){
				Thread.sleep(5000);

				space=driver.findElement(By.xpath("//form[@id='frmReserveUnits']//div//table/tbody/tr[1]/td[4]")).getText();

				Reporter.log("space number is:"+space,true);
			}else{

				logger.log(LogStatus.FAIL, "Application is not populating any data/space details with selected size dimension");
				throw new Exception("Application is not populating any data/space details");

			}
			Thread.sleep(2000);
			WebElement RdBtn_Space = driver.findElement(By.xpath("//td[@class='grid-cell-space'][text()='"+space+"']/../td/input[@name='selectedIds']"));
			logger.log(LogStatus.PASS, "check the radio button based on the space and click on the  reservation button");
			Thread.sleep(5000);
			jse.executeScript("arguments[0].scrollIntoView()", RdBtn_Space); 
			jse.executeScript("arguments[0].click();", RdBtn_Space);

			logger.log(LogStatus.PASS,"Clicked on check box of a space in this location: " + space);
			Reporter.log("Clicked on check box of a space in this location: " + space,true);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			thisloc.click_Hold();
			logger.log(LogStatus.PASS, "Click on the Hold button in the This location page sucessfully");

			Hold_PopupPage popUp=new Hold_PopupPage(driver);
			Thread.sleep(5000);
			if(tabledata.get("HoldPopTitle").contains(popUp.get_HoldPopTitle().trim()))
			{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "HoldPopTitle is displayed successfully");
				logger.log(LogStatus.INFO, "HoldPopTitle is displayed successfully",image);
			}
			else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "HoldPopTitle is not displayed ");
				logger.log(LogStatus.INFO, "HoldPopTitle is not displayed ",image);

			}

			Thread.sleep(5000);
			popUp.enter_EmpNumber(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "enter the employee id");
			Thread.sleep(5000);
			popUp.clk_OkBtn();
			logger.log(LogStatus.INFO, "Click on ok button");
			Thread.sleep(10000);

			Thread.sleep(5000);
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_ESCAPE);
			r.keyRelease(KeyEvent.VK_ESCAPE);

			Thread.sleep(5000);

			((JavascriptExecutor)driver).executeScript("window.scrollTo(250,0)");
			if(tabledata.get("InProgressTitle").contains(pmhomepage.get_InprogressText().trim()))
			{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Inprogress screen is displayed successfully in PM Dashboard");
				logger.log(LogStatus.INFO, "Inprogress screen is displayed successfully in PM Dashboard",image);
			}
			else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Inprogress screen is not displayed in PM Dashboard ");
				logger.log(LogStatus.INFO, "Inprogress screen is not displayed in PM Dashboard ",image);

			}


			Thread.sleep(5000);
			String empNmae=driver.findElement(By.xpath("//td[text()='"+space+"']/following-sibling::td")).getText();
			logger.log(LogStatus.INFO, "Employee name is diplaying in the Employee column in the Inprogress module:"+empNmae);
			Reporter.log(empNmae,true);
			Thread.sleep(5000);
			pmhomepage.clk_HoldBtnOnInprogessModule(space);
			logger.log(LogStatus.PASS, "clicked on the hold link on the inprogress module sucessfully");

			InProgress_HoldLnkPage inprogress=new InProgress_HoldLnkPage(driver);
			logger.log(LogStatus.PASS, "creating object for the inprogress module sucessfully ");
			Thread.sleep(5000);

			if(inprogress.get_SpaceNo().contains(space))
			{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Hold Sapce number is displayed sucessfully in inprogress ");
				logger.log(LogStatus.INFO, "Hold Sapce number is displayed sucessfully in inprogress",image);
			}
			else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Hold Sapce number is not displayed ");
				logger.log(LogStatus.INFO, "Hold Sapce number is not displayed ",image);

			}
			Thread.sleep(5000);
			inprogress.clk_ReserveRadioBtn();
			logger.log(LogStatus.INFO, "click on Reserve radio button");
			Thread.sleep(5000);
			inprogress.clk_ContinueBtn();

			logger.log(LogStatus.INFO, "click on Continue button");

			Thread.sleep(5000);
			CreateReservation cres=new CreateReservation(driver);

			if(tabledata.get("CreateReservatnTitle").contains(cres.get_createReservationtxt().trim()))
			{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Create Reservation page is displayed ");
				logger.log(LogStatus.INFO, "Create Reservation page is displayed",image);
			}
			else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Create Reservation page is not displayed ");
				logger.log(LogStatus.INFO, "Create Reservation page is not displayed ",image);

			}

		
		}
		catch(Exception e){
			e.getMessage();
			resultFlag="fail";
			Reporter.log("Exception: WalkINReservation_ExtIndCustFindSpaceUsingSizeEstimatorInStandardStroage"+e,true);
		}


	}

	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "Reservation","WalkINReservation_ExtBusCustFindSpaceUsingSizeEstimatorInStandardStroage", "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Reservation","WalkINReservation_ExtBusCustFindSpaceUsingSizeEstimatorInStandardStroage", "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Reservation","WalkINReservation_ExtBusCustFindSpaceUsingSizeEstimatorInStandardStroage", "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);


	}
	
	
}
