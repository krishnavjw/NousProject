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
import Pages.Walkin_Reservation_Lease.SpaceDashboard_OtherLocations;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
//import Pages.Walkin_Reservation_Lease.SpaceDashboard_Loc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Pages.Walkin_Reservation_Lease.ViewReservationPage;
import ProjectSpecificMethods.PS_GenericMethods;
import Scenarios.Browser_Factory;

public class WalkInReservation_CustomerWalkInToReserveASpaceAtSameSite_StandardStorage_Individual extends Browser_Factory{

	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");
	public static String FirstNm;

	@DataProvider
	public Object[][] getLoginData() 
	{

		return Excel.getCellValue_inlist(path, "Reservation","Reservation",  "WalkInReservation_CustomerWalkInToReserveASpaceAtSameSite_StandardStorage_Individual");
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
			logger=extent.startTest("WalkInReservation_CustomerWalkInToReserveASpaceAtSameSite_StandardStorage_Individual", "New Customer walk in to reserve a space");
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


			StandardStoragePage.click_Search();
			logger.log(LogStatus.INFO,"Clicked on Search button");
			Reporter.log("Clicked on Search button",true);

			Thread.sleep(5000);


			if(StandardStoragePage.verifySpaceSizeNotificationMsg().getText().contains("Please select a space size"))
			{
				Reporter.log("Information Msg 'Please select a space size' is displayed",true);
				logger.log(LogStatus.PASS,"Information Msg 'Please select a space size' is displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				Reporter.log("Information Msg 'Please select a space size' is not displayed",true);
				logger.log(LogStatus.FAIL,"Information Msg 'Please select a space size' is not displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				throw new Exception("Information Msg 'Please select a space size' is not displayed");
			}

			StandardStoragePage.ClickOnAvlSpaces();
			logger.log(LogStatus.INFO,"Clicked on available spaces checkbox in StandardStorage");
			Reporter.log("Clicked on available spaces",true);


			StandardStoragePage.click_Search();
			logger.log(LogStatus.INFO,"Clicked on Search button");
			Reporter.log("Clicked on Search button",true);

			Thread.sleep(10000);

			SpaceDashboard_ThisLoc SpaceDashboard_ThisLoc = new SpaceDashboard_ThisLoc(driver);

			if(SpaceDashboard_ThisLoc.isHoldButtonDisplayed() && SpaceDashboard_ThisLoc.isRentButtonDisplayed() && SpaceDashboard_ThisLoc.isReserveButtonDisplayed())
			{
				Reporter.log("Rent , Reserve, Hold buttons are displayed",true);
				logger.log(LogStatus.PASS,"Rent , Reserve, Hold buttons are displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				Reporter.log("Rent , Reserve, Hold buttons are not displayed",true);
				logger.log(LogStatus.FAIL,"Rent , Reserve, Hold buttons are not displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				throw new Exception("Rent , Reserve, Hold buttons are not displayed");
			}

			Thread.sleep(5000);
			StandardStoragePage.clk_OtherLocations();
			logger.log(LogStatus.PASS,"Clicked on OtherLocations tab");
			Reporter.log("Clicked on OtherLocations tab",true);
			//============Fetching location number and based on that clicking the radio button========================
			Thread.sleep(5000);
			List <WebElement> norows=driver.findElements(By.xpath("//div[@class='k-grid-content ps-container ps-active-y']//table//tbody//tr"));
			String Location=null;
			if(norows.size()>0){

				Thread.sleep(5000);
				Location=driver.findElement(By.xpath("//div[@class='k-grid-content ps-container ps-active-y']//table//tbody//tr[1]/td[8]")).getText();
				Reporter.log("Location number is:"+Location,true);
			}else{

				logger.log(LogStatus.INFO, "Application is not populating any data/Location details");
				throw new Exception("Application is not populating any data/Location details");

			}

			SpaceDashboard_OtherLocations otherLoc=new SpaceDashboard_OtherLocations(driver);

			//String Location="20499";
			Thread.sleep(5000);


			if(otherLoc.isReserveOffSiteDisplayed())
			{
				Reporter.log("ReserveOffsiteUnits button is displayed",true);
				logger.log(LogStatus.PASS,"ReserveOffsiteUnits button is displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				Reporter.log("ReserveOffsiteUnits button is not displayed",true);
				logger.log(LogStatus.FAIL,"ReserveOffsiteUnits button is not displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				throw new Exception("ReserveOffsiteUnits button is not displayed");
			}


			StandardStoragePage.clk_ThisLocations();
			logger.log(LogStatus.INFO, "Clicked on This Location tab");

			Thread.sleep(10000);

			List <WebElement> norows_ThisLoc=driver.findElements(By.xpath("//div[@class='k-grid-content ps-container ps-active-y']//table//tbody//tr"));
			String space=null;
			if(norows_ThisLoc.size()>0){
				Thread.sleep(5000);

				space=driver.findElement(By.xpath("//div[@class='k-grid-content ps-container ps-active-y']//table//tbody//tr[1]/td[4]")).getText();
				Reporter.log("space number is:"+space,true);
			}else{

				logger.log(LogStatus.INFO, "Application is not populating any data/space details");
				throw new Exception("Application is not populating any data/space details");

			}

			//String Space = "4008";
			WebElement RdBtn_Space_ThisLoc = driver.findElement(By.xpath("//td[@class='grid-cell-space'][text()='"+space+"']/../td/input[@name='selectedIds']"));
			logger.log(LogStatus.PASS, "check the radio button based on the space and click on the  reservation button");
			jse.executeScript("arguments[0].scrollIntoView()", RdBtn_Space_ThisLoc); 
			Thread.sleep(5000);
			jse.executeScript("arguments[0].click();", RdBtn_Space_ThisLoc);

			logger.log(LogStatus.PASS,"Clicked on check box of a space in this location: " + space);
			Reporter.log("Clicked on check box of a space in this location: " + space,true);
			//generics.Page_ScrollDown();
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");


			Thread.sleep(5000);
			SpaceDashboard_ThisLoc.click_Reserve();
			logger.log(LogStatus.PASS,"Clicked on Reserve button");
			Reporter.log("Clicked on Reserve button",true);


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
			jse.executeScript("arguments[0].scrollIntoView()", CreateReservation.btnMoveIndate()); 
			Thread.sleep(5000);
			jse.executeScript("arguments[0].click();", CreateReservation.btnMoveIndate());
			logger.log(LogStatus.PASS,"Clicked on MoveIn Date button");
			Reporter.log("Clicked on MoveIn Date button",true);
			Thread.sleep(5000);
			CreateReservation.SelectDateFromCalendar(tabledata.get("MoveInDate"));
			logger.log(LogStatus.PASS,"MoveIn date is selected in calendar");
			Reporter.log("MoveIn date is selected in calendar",true);
			Thread.sleep(5000);
			if(CreateReservation.btnLocationVisitDate().isEnabled())
			{
				jse.executeScript("arguments[0].click();", CreateReservation.btnLocationVisitDate());
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);

				logger.log(LogStatus.PASS,"Clicked on Location Visit Date button");
				logger.log(LogStatus.INFO, "Clicked on Location Visit Date button",image);

			}
			else
			{


				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL,"Location Visit date is disabled since MoveIn date is current day");
				logger.log(LogStatus.INFO, "Location Visit date is disabled since MoveIn date is current day",image);
				logger.log(LogStatus.INFO, "Image",image);
				throw new Exception("Location Visit date is disabled since MoveIn date is current day");

			}
			Thread.sleep(5000);
			CreateReservation.SelectDateFromCalendar(tabledata.get("LocVisitDt"));
			logger.log(LogStatus.PASS,"Location Visit date is selected in calendar");
			Reporter.log("Location Visit date is selected in calendar",true);	
			Thread.sleep(5000);


			String FN = enterFN();
			Thread.sleep(5000);


			CreateReservation.enter_FirstName(FirstNm);
			logger.log(LogStatus.PASS,"FirstName is entered in CreateReservation Page");
			Reporter.log("FirstName is entered in CreateReservation Page",true);


			Thread.sleep(5000);
			CreateReservation.enter_LastName(tabledata.get("LastName"));
			logger.log(LogStatus.PASS,"LastName is entered in CreateReservation Page");
			Reporter.log("LastName is entered in CreateReservation Page",true);
			Thread.sleep(5000);
			CreateReservation.sel_DropDownValFromPhNo(tabledata.get("Phone"));
			logger.log(LogStatus.PASS,"Phone type is selected in CreateReservation Page");
			Reporter.log("Phone type is selected in CreateReservation Page",true);
			Thread.sleep(5000);
			CreateReservation.enter_PhoneAreaCode(tabledata.get("PhoneAreacode"));
			Thread.sleep(5000);
			CreateReservation.enter_PhoneExchnge(tabledata.get("PhoneExchnge"));
			Thread.sleep(5000);
			CreateReservation.enter_PhoneLineNumber(tabledata.get("PhoneLneNo"));
			Thread.sleep(5000);
			logger.log(LogStatus.PASS,"Phone number is entered in CreateReservation Page");
			Reporter.log("Phone number is entered in CreateReservation Page",true);
			CreateReservation.enter_EmailAddr(tabledata.get("Emailaddrs"));
			logger.log(LogStatus.PASS,"Email is entered in CreateReservation Page");
			Reporter.log("Email is entered in CreateReservation Page",true);
			Thread.sleep(2000);
			//generics.Page_ScrollDown();
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			CreateReservation.clk_CreateReservationButton();
			logger.log(LogStatus.PASS,"Clicked on Create Reservation in CreateReservation page");
			Reporter.log("Clicked on Create Reservation in CreateReservation page",true);

			
			CreateReserVation_PopUp CreateReserVation_PopUp = new CreateReserVation_PopUp(driver);
			Thread.sleep(3000);
			CreateReserVation_PopUp.enter_NotesTextArea(tabledata.get("Notes"));
			logger.log(LogStatus.PASS,"Entered notes in CreateReservation popup");
			Reporter.log("Entered notes in CreateReservation popup",true);
			Thread.sleep(5000);
			CreateReserVation_PopUp.enter_EmpID(tabledata.get("EmpID"));
			logger.log(LogStatus.PASS,"Entered Employee ID in CreateReservation popup");
			Reporter.log("Entered Employee ID in CreateReservation popup",true);
			Thread.sleep(5000);
			CreateReserVation_PopUp.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS,"Clicked on CreateReservation in CreateReservation popup");
			Reporter.log("Clicked on CreateReservation in CreateReservation popup",true);
			Thread.sleep(15000);


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

			PM_HomePage.enter_NameOrResvtn(FirstNm);
			logger.log(LogStatus.INFO,"Entered name in find reservation textbox");
			Reporter.log("Entered name in find reservation textbox",true);
			PM_HomePage.clk_findReservation();
			logger.log(LogStatus.INFO,"Clicked on find reservation button");
			Reporter.log("Clicked on find reservation button",true);
			Thread.sleep(6000);

			ViewReservationPage ViewReservation = new ViewReservationPage(driver);
			String ReservationNo = ViewReservation.get_ReservationNumber();
			logger.log(LogStatus.INFO,"Reservation number is: " + ReservationNo);
			Reporter.log("Reservation number is: " + ReservationNo,true);

			String DB_ReservationNo =  DataBase_JDBC.executeSQLQuery("select ReservationID from Reservation where FirstName='"+FirstNm+"'");
			if(DB_ReservationNo.equals(ReservationNo))
			{
				logger.log(LogStatus.INFO,"Reservation is created in DB:" + DB_ReservationNo);
				Reporter.log("Reservation is created in DB:" + DB_ReservationNo,true);
				resultFlag="pass";
			}
			else
			{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO,"Reservation is not created in DB:" + DB_ReservationNo);
				Reporter.log("Reservation is not created in DB:" + DB_ReservationNo,true);
				throw new Exception("Reservation is not created in DB:");					
			}
			Thread.sleep(5000);
			String ReservationState =  DataBase_JDBC.executeSQLQuery("select Name from type where typeid=(Select ReservationStatusTypeID  from Reservation where ReservationID='"+DB_ReservationNo+"')");
			if(ReservationState.equals("Confirmed"))
			{
				logger.log(LogStatus.INFO,"Reservation state is 'Confirmed' in DB");
				Reporter.log("Reservation state is 'Unconfirmed' in DB" ,true);
				resultFlag="pass";
			}
			else
			{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO,"Reservation state is not 'Confirmed' in DB" );
				Reporter.log("Reservation state is not 'Confirmed' in DB" ,true);
				throw new Exception("Reservation state is not 'Confirmed' in DB");					
			}

			Thread.sleep(5000);
			String ReservationItemStatus =  DataBase_JDBC.executeSQLQuery("select Name from type where typeid=(Select StatusTypeID from ReservationItem where Reservationid = '"+DB_ReservationNo+"')");
			if(ReservationItemStatus.equals("Pending"))
			{
				logger.log(LogStatus.INFO,"Reservation Item Status is 'Pending' in DB");
				Reporter.log("Reservation Item Status is 'Pending' in DB" ,true);
				resultFlag="pass";
			}
			else
			{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO,"Reservation Item Status is not 'Pending' in DB" );
				Reporter.log("Reservation Item Status is not 'Pending' in DB" ,true);
				throw new Exception("Reservation Item Status is not 'Pending' in DB");					
			}


			String ReservationAccID =  DataBase_JDBC.executeSQLQuery("select ReservationAccountID from Reservation where ReservationID='"+DB_ReservationNo+"'");
			if(ReservationAccID.equals(""))
			{
				logger.log(LogStatus.INFO,"ReservationAccountID is 'NULL' in DB");
				Reporter.log("ReservationAccountID is 'NULL' in DB" ,true);
				resultFlag="pass";
			}
			else
			{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO,"ReservationAccountID is not 'NULL' in DB" );
				Reporter.log("ReservationAccountID is not 'NULL' in DB" ,true);
				throw new Exception("ReservationAccountID is not 'NULL' in DB");					
			}


		}catch(Exception e){
			Reporter.log("Exception:Reservation" + e,true);
			resultFlag="fail";
		}


	}

	public static String enterFN()
	{
		Generic_Class generics = new Generic_Class();
		FirstNm = "AUT" + generics.get_RandmString();
		return FirstNm;
	}

	@AfterMethod
	public void afterMethod(){

		Reporter.log("resultFlag: " + resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "Reservation","WalkInReservation_CustomerWalkInToReserveASpaceAtSameSite_StandardStorage_Individual" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Reservation","WalkInReservation_CustomerWalkInToReserveASpaceAtSameSite_StandardStorage_Individual" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Reservation","WalkInReservation_CustomerWalkInToReserveASpaceAtSameSite_StandardStorage_Individual" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}




}
