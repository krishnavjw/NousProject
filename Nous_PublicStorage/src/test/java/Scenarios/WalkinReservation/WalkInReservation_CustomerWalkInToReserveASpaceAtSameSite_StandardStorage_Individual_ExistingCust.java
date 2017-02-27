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

public class WalkInReservation_CustomerWalkInToReserveASpaceAtSameSite_StandardStorage_Individual_ExistingCust extends Browser_Factory{

	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");


	@DataProvider
	public Object[][] getLoginData() 
	{

		return Excel.getCellValue_inlist(path, "Reservation","Reservation",  "WalkInReservation_CustomerWalkInToReserveASpaceAtSameSite_StandardStorage_Individual");
	}

	@Test(dataProvider="getLoginData")
	public void WalkInReservation_CustomerWalkInToReserveASpaceAtSameSite_StandardStorage_Individual(Hashtable<String, String> tabledata)   
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Reservation").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		try
		{
			logger=extent.startTest("WalkInReservation_CustomerWalkInToReserveASpaceAtSameSite_StandardStorage_Individual", "Existing individual Customer walk in to reserve a space");
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
					"having sum(clt.amount + clt.discountamount) >0 ";

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
			//((JavascriptExecutor)driver).executeScript("window.scrollTo(1000,0)");
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
            
			Thread.sleep(5000);
			cust.sel_quickLinks_dropDown(driver);			
			logger.log(LogStatus.INFO, "Selected Add space option in customer dashboard to hold space for customer sucessfully");
			Reporter.log("Selected Add space option successfully",true);


			Thread.sleep(5000);	
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
				logger.log(LogStatus.PASS, "Existing Customer Radio button selected by default when WALK-INS: FIND A SPACE page is launched sucessfully");

			}else{

				logger.log(LogStatus.FAIL, "Existing Customer Radio button is not selected by default when WALK-INS: FIND A SPACE page is launched sucessfully");
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

			stdStorage.ClickOnAvlSpaces();
			logger.log(LogStatus.INFO,"Clicked on available spaces checkbox in StandardStorage");
			Reporter.log("Clicked on available spaces",true);
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
			thisloc.click_Reserve();
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

			String FN =CreateReservation.get_ExtCustName().trim();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "Existing customer name is:"+FN);

			Thread.sleep(2000);
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

			pmhomepage.enter_NameOrResvtn(FN);
			logger.log(LogStatus.INFO,"Entered name in find reservation textbox");
			Reporter.log("Entered name in find reservation textbox",true);
			pmhomepage.clk_findReservation();
			logger.log(LogStatus.INFO,"Clicked on find reservation button");
			Reporter.log("Clicked on find reservation button",true);
			Thread.sleep(6000);

			ViewReservationPage ViewReservation = new ViewReservationPage(driver);
			String ReservationNo = ViewReservation.get_ReservationNumber();
			logger.log(LogStatus.INFO,"Reservation number is: " + ReservationNo);
			Reporter.log("Reservation number is: " + ReservationNo,true);

			String DB_ReservationNo =  DataBase_JDBC.executeSQLQuery("select ReservationID from Reservation where FirstName='"+FN+"'");
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
