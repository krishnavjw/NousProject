package Scenarios.WalkinReservationLease;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.CreateReservation;
import Pages.Walkin_Reservation_Lease.CreateReservation_PopUp;
import Pages.Walkin_Reservation_Lease.SpaceDashboard_ThisLoc;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Scenarios.Browser_Factory;

public class CreateReservation_NewCustomer_Individual extends Browser_Factory {

	ExtentTest logger;
	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";

	@AfterMethod
	public void afterMethod() {

		if (resultFlag.equals("pass")) {

			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "CreateReservation_NewCustomer_Individual",
					"Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "CreateReservation_NewCustomer_Individual",
					"Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "WalkinReservationLease", "CreateReservation_NewCustomer_Individual",
					"Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);

	}

	@Test(dataProvider = "getData")
	public void CreateReservation_NewCustomer_Individual(Hashtable<String, String> tabledata)
			throws InterruptedException {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("WalkinReservationLease").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}

		try {

			logger = extent.startTest("CreateReservation_NewCustomer_Individual",
					"Customer walks in - makes a reservation for a space that requires deposit");
			testcaseName = tabledata.get("TestCases");
			Reporter.log("Test case started: " + testcaseName, true);

			LoginPage loginPage = new LoginPage(driver);
			loginPage.login(tabledata.get("UserName"), tabledata.get("Password"));

			Robot robot = new Robot();
			WebDriverWait wait = new WebDriverWait(driver, 40);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			
			Thread.sleep(3000);
			
			
			Dashboard_BifrostHostPopUp Bifrostpop1= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			try {
				Bifrostpop1.clickContiDevice();
			} catch (Exception e) {
				System.out.println("Pop up is not avalable, so continuing!!");
			}

			Thread.sleep(10000);
			// ========================================= PM Dash Board ==================================================

			PM_Homepage Pmpage = new PM_Homepage(driver);

			String location = Pmpage.getLocation();
			Thread.sleep(5000);
			if (tabledata.get("walkInCustTitle").contains(Pmpage.get_WlkInCustText().trim())) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "PM Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "PM Dashboard is displayed successfully", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "PM Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "PM Dashboard is not displayed ", image);

			}

			Pmpage.clk_findAndLeaseSpace();
			logger.log(LogStatus.INFO, "Click on the find and lease button");

			StandardStoragePage StandardStoragePage = new StandardStoragePage(driver);
			Thread.sleep(5000);

			

			//============================= Verify Standard Storage selected by default=======================================
			if (driver.findElement(By.xpath("//div[@id='choose-size-search-type']/ul/li[@class='k-state-active standard-storage k-item k-tab-on-top k-state-default k-first']"))
					.isDisplayed()) {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				
				logger.log(LogStatus.PASS, "Standard Storage tab default selected ");
				
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Standard Storage tab not default selected  ");
				logger.log(LogStatus.INFO, "Standard Storage tab not default selected  ", image);

			}

			//========================= Verify that for PM, in WALK-INS: FIND A SPACE screen Standard Storage,Vechicle Storage and Specific Space Tabs==========
			if (StandardStoragePage.isdisplayed_StandardStorage()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				logger.log(LogStatus.PASS, "Find a space page is displayed successfully");
				
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Find a space page is not displayed successfully");
			
				logger.log(LogStatus.INFO, "Standard Storage tab not displayed successfully ", image);
			}

			if (StandardStoragePage.isdisplayed_VehicleStorage()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				
				logger.log(LogStatus.PASS, "Vehicle Storage tab displayed successfully");
				
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Vehicle Storage tab not displayed successfully ");
				logger.log(LogStatus.INFO, "Vehicle Storage tab not displayed successfully ", image);
			}

			if (StandardStoragePage.isdisplayed_SpecificStorage()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Specific Storage tab displayed successfully");
				logger.log(LogStatus.INFO, "Specific Storage tab displayed successfully", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Specific Storage tab not displayed successfully ");
				logger.log(LogStatus.INFO, "Specific Storage tab not displayed successfully ", image);
			}

			if (StandardStoragePage.isSelected_yesradiobutton()) {
				logger.log(LogStatus.INFO,
						"Yes Radio button selected by default when WALK-INS: FIND A SPACE page is launched sucessfully for a New Cusomter");

			} else {
				logger.log(LogStatus.FAIL,
						"Yes Radio button is not selected by default when WALK-INS: FIND A SPACE page is launched sucessfully for a New Cusomter");
			}

			StandardStoragePage.Clk_OnAvlSpace();
			logger.log(LogStatus.INFO, "Clicked on available spaces checkbox in StandardStorage");
			Thread.sleep(2000);
			String scpath1 = Generic_Class.takeScreenShotPath();
			
			String image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "selecting checkboxes ");
			logger.log(LogStatus.INFO, "selecting checkboxes ", image1);
			StandardStoragePage.click_Search();
			logger.log(LogStatus.INFO, "Clicked on Search button");
			Reporter.log("Clicked on Search button", true);
			Thread.sleep(5000);

			String size = StandardStoragePage.getAvlSpace_Size();
			size = size.replaceAll("'", "");
			size = size.replaceAll(" ", "");
			Reporter.log("space size is--->:" + size, true);

			Reporter.log("Clicked on available spaces", true);
			String query = "SELECT RentalUnitNumber AS Space FROM DBO.RentalUnit RU"
					+ " INNER JOIN DBO.Productsite PS ON RU.ProductSiteID = PS.ProductSiteID"
					+ " INNER JOIN DBO.Site S ON S.SiteID = PS.SiteID "
					+ " INNER JOIN DBO.StorageProductFeature SPF ON PS.StorageProductFeatureID = SPF.StorageProductFeatureID"
					+ " Inner JOIN DBO.Type T1 ON T1.TypeID=RU.RentalStatusTypeID "
					+ " LEFT JOIN DBO.Type T3 ON T3.TypeID=SPF.DoorTypeID "
					+ " LEFT JOIN DBO.Type T4 ON T4.TypeID=RU.RentalLockStatusTypeID" + " WHERE 1 = 1 "
					+ " AND S.Sitenumber = '" + location + "'" + " AND T1.Name Like 'Vacant%'"
					/*+ " AND CAST(CAST(ISNULL(Width, 0) AS integer) AS varchar)+'X'+CAST(CAST(ISNULL(Length, 0) AS integer) AS varchar) Like '"
					+ size + "'"*/
					+ " ORDER BY CASE WHEN RentalUnitNumber LIKE 'APT%' THEN '1' ELSE RentalUnitNumber END";

			ArrayList<String> avlSpaces = DataBase_JDBC.executeSQLQuery_List(query);

			List<WebElement> uiSpaces = driver
					.findElements(By.xpath("//table/tbody//tr//td[@class='grid-cell-space']"));
			int cnt = 1;

			Actions dragger = new Actions(driver);
			WebElement draggablePartOfScrollbar = driver
					.findElement(By.xpath("//div[@class='ps-scrollbar-y-rail']//div[@class='ps-scrollbar-y']"));
			for (WebElement space : uiSpaces) {
				String spacenum = space.getText();
				System.out.println(spacenum);

				for (int i = 0; i < avlSpaces.size(); i++) {
					if (spacenum.equalsIgnoreCase(avlSpaces.get(i))) {
						logger.log(LogStatus.PASS, "Space Number from Database is: " + avlSpaces.get(i)
								+ "Space Number in UI is:  " + spacenum);
						if (cnt >= 3) {
							dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0, 100)
									.release().build().perform();
							Thread.sleep(1000);
						}
						cnt++;
						break;
					}
				}
			}
			Thread.sleep(3000);
			// Click On Other Location tab
			SpaceDashboard_ThisLoc spacedashboard = new SpaceDashboard_ThisLoc(driver);

			
			spacedashboard.click_otherLocations();
			Thread.sleep(5000);
			spacedashboard.click_thisLocations();
			Thread.sleep(8000);
			String path = "//div[@id='onsiteUnitGrid']/div[2]/table/tbody/tr[1]/td[1]/input";
			System.out.println("Path is:  " + path);


			WebElement RdBtn_Space = driver.findElement(By.xpath(path));
			logger.log(LogStatus.PASS,
					"check the radio button based on the space and click on the  reservation button");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", RdBtn_Space);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", RdBtn_Space);
			Thread.sleep(5000);

			// generics.Page_ScrollDown();
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			Thread.sleep(5000);

			// Verify Rent button is available at bottom
			if (spacedashboard.isDisplayed_Rentbutton()) {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				
				logger.log(LogStatus.PASS, "At the bottom Rent button displayed successfully ");
				
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "At the bottom Rent button not displayed successfully ");
				logger.log(LogStatus.INFO, "At the bottom Rent button not displayed successfully ", image);
			}

			// Verify Reserve button is available at bottom
			if (spacedashboard.isDisplayed_Reservebutton()) {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				
				logger.log(LogStatus.PASS, "At the bottom Reserve button displayed successfully ");
			
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "At the bottom Reserve button not displayed successfully  ");
				logger.log(LogStatus.INFO, "At the bottom Reserve button not  displayed successfully  ", image);
			}

			// Verify Hold button is available at bottom
			if (spacedashboard.isDisplayed_Holdbutton()) {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, " At the bottom Hold button displayed successfully ");
				logger.log(LogStatus.INFO, "At the bottom Hold button displayed successfully", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "At the bottom Hold button not displayed successfully ");
				logger.log(LogStatus.INFO, "At the bottom Hold button not displayed successfully ", image);
			}

			// Click On Reserve button
			spacedashboard.click_Reserve();
			logger.log(LogStatus.PASS, "Clicked on Reserve button successfully ");
			Thread.sleep(10000);
			
			WebElement move_in_cost= driver.findElement(By.xpath("//td[@class='grid-cell-rent cost']"));
			WebElement move_in_subtotal = driver.findElement(By.xpath("//div[@class='move-in-subtotal floatright big-bold fixed-col']"));
			WebElement admin_fee_amount = driver.findElement(By.xpath("//div[@class='admin-fee-amount floatright fixed-col']"));
			WebElement admin_fee_tax = driver.findElement(By.xpath("//div[@class='admin-fee-taxes floatright fixed-col']"));
			WebElement rent_tax = driver.findElement(By.xpath("//div[@class='move-in-rent-taxes floatright fixed-col']"));
			WebElement overall_total_move_in = driver.findElement(By.xpath("//div[@class='overall-total-move-in big-bold floatright fixed-col']"));

			
			double moveincost = Generic_Class.getDoubleAmount(move_in_cost.getText());
			double moveinsub = Generic_Class.getDoubleAmount(move_in_subtotal.getText());
			double movein_admin_fee_amount = Generic_Class.getDoubleAmount(admin_fee_amount.getText());
			
			double movein_admin_fee_tax;
			
			try{
				
				movein_admin_fee_tax = Generic_Class.getDoubleAmount(admin_fee_tax.getText());
				
			}catch(Exception ex){
				
				 movein_admin_fee_tax=0.0;
				
			}
			
			double  movein_rent_tax;
			
			try{
				
				movein_rent_tax = Generic_Class.getDoubleAmount(rent_tax.getText());
				
			}catch(Exception ex){
				
				movein_rent_tax=0.0;
				
			}
			
			
			
			
			
			double moveintotle = Generic_Class.getDoubleAmount(overall_total_move_in.getText());

			double movein_grandtotal_ui=moveinsub + movein_admin_fee_amount+movein_admin_fee_tax+movein_rent_tax;
			
			
			Double db1 = new Double(moveintotle);
            Double db2 = new Double(movein_grandtotal_ui);

           
			
            if (db1.intValue() == db2.intValue()) {
				
				logger.log(LogStatus.PASS, "Total Cost displayed for Move In Cost is Maching");
				logger.log(LogStatus.INFO, "Move-In Cost column is displaying after adding SubTotal + Administrative Fee + Admin Tax + Rent tax ----"+movein_grandtotal_ui);
				
				logger.log(LogStatus.INFO, "Move-In Cost column is displaying in--Total Cost For This Space: "+moveintotle);
			} else {

				logger.log(LogStatus.FAIL, "Total Cost displayed for Move In Cost is not  Maching");

				logger.log(LogStatus.INFO, "Move-In Cost column is displaying after adding SubTotal + Administrative Fee + Admin Tax + Rent tax ----"+movein_grandtotal_ui);
				
				logger.log(LogStatus.INFO, "Move-In Cost column is displaying in--Total Cost For This Space: "+moveintotle);
				
			}

			
            
            
            
			
			WebElement monthly_rent_subtotal = driver.findElement(By.xpath("//div[@class='rent-subtotal floatright big-bold fixed-col']"));
			WebElement monthlyrent_tax = driver.findElement(By.xpath("//div[@class='rent-taxes floatright fixed-col']"));

			WebElement monthly_rent_grandtotal = driver.findElement(By.xpath("//div[@class='overall-total-rent big-bold floatright fixed-col']"));
			
			

			double monthly_rentsubtotal = Generic_Class.getDoubleAmount(monthly_rent_subtotal.getText());
			
			
			double  monthly_renttax;
			
			try{
				
				monthly_renttax = Generic_Class.getDoubleAmount(monthlyrent_tax.getText());
				
			}catch(Exception ex){
				
				monthly_renttax=0.0;
				
			}

			
			

			double overalltotalrent = Generic_Class.getDoubleAmount(monthly_rent_grandtotal.getText());
			
			double rent_grandtotal_ui=monthly_rentsubtotal + monthly_renttax;
			
			Double db3 = new Double(overalltotalrent);
            Double db4 = new Double(rent_grandtotal_ui);
			

            if (db3.intValue() == db4.intValue()) {
				
				logger.log(LogStatus.PASS, "Total Cost displayed for Monthly Rent  is Maching");
				logger.log(LogStatus.INFO, "Monthly rent column  is displaying after adding SubTotal +  Rent tax ----"+rent_grandtotal_ui);
				
				logger.log(LogStatus.INFO, "Monthly rent column is displaying in--Total Cost For This Space: "+overalltotalrent);
			} else {

				logger.log(LogStatus.FAIL, "Total Cost displayed for Monthly rent Cost is not  Maching");

				logger.log(LogStatus.INFO, "Monthly rent column is displaying after adding SubTotal + Rent tax ----"+rent_grandtotal_ui);
				
				logger.log(LogStatus.INFO, "Monthly rent column is displaying in--Total Cost For This Space: "+overalltotalrent);
				
			}
			
			Thread.sleep(3000);
			String scpath3=Generic_Class.takeScreenShotPath();
			
			String image3=logger.addScreenCapture(scpath3);
			logger.log(LogStatus.PASS, " Reservation screen display ");
			logger.log(LogStatus.INFO, "Reservation screen display", image3);
			
			CreateReservation res = new CreateReservation(driver);
			logger.log(LogStatus.PASS, "Create Reservation Page is displayed successfully");

			jse.executeScript("arguments[0].scrollIntoView()", res.btnMoveIndate());
			Thread.sleep(5000);
			jse.executeScript("arguments[0].click();", res.btnMoveIndate());
			logger.log(LogStatus.PASS, "Clicked on MoveIn Date button");
			Reporter.log("Clicked on MoveIn Date button", true);
			Thread.sleep(5000);

			res.SelectDateFromCalendar(tabledata.get("MoveInDate"));
			Reporter.log("MoveIn date is selected in calendar", true);

			Thread.sleep(5000);
			String FirstName = "Nous" + Generic_Class.get_RandmString();
			Thread.sleep(1000);

			res.enter_FirstName(FirstName);
			

			String LastName = "Automation" + Generic_Class.get_RandmString();
			Thread.sleep(1000);

			res.enter_LastName(LastName);
			
			Thread.sleep(1000);

			res.sel_DropDownValFromPhNo(tabledata.get("Phone"));
			logger.log(LogStatus.PASS, "Phone type is selected in CreateReservation Page");
			Reporter.log("Phone type is selected in CreateReservation Page", true);
			Thread.sleep(2000);

			res.enter_PhoneAreaCode(tabledata.get("PhoneAreacode"));
			
			Thread.sleep(1000);

			res.enter_PhoneExchnge(tabledata.get("PhoneExchnge"));
		
			Thread.sleep(1000);

			res.enter_PhoneLineNumber(tabledata.get("PhoneLneNo"));
			
			Thread.sleep(1000);

			res.enter_EmailAddr(tabledata.get("Emailaddrs"));
			
			Thread.sleep(1000);

			// generics.Page_ScrollDown();
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(2000);

			// Click on Create Reservation button
			res.clk_CreateReservationButton();
			Thread.sleep(3000);
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");
			Thread.sleep(3000);
			CreateReservation_PopUp rserservationpopup = new CreateReservation_PopUp(driver);

			if (rserservationpopup.get_CreateResvPopUpHeader().equalsIgnoreCase("Create Reservation")) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Create Reservation modal window displayed successfully--"
						+ rserservationpopup.get_CreateResvPopUpHeader());
				logger.log(LogStatus.INFO, "Create Reservation modal window displayed successfully", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Create Reservation modal window not  displayed successfully ");
				logger.log(LogStatus.INFO, "Create Reservation modal window not displayed successfully ", image);
			}

			// Do not enter any EMP ID and click "Create Reservation" button
			rserservationpopup.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");

			Thread.sleep(2000);

			// Verify Information Msg "Please enter an employere number" Should
			// be displayed in red color.
			if (rserservationpopup.get_empErrorMessage().contains("Please enter an employee number.")) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,
						"Error message verified successfully " + rserservationpopup.get_empErrorMessage());
				logger.log(LogStatus.INFO, "Error message verified successfully", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Error message not verified successfully ");
				logger.log(LogStatus.INFO, "Error message not  verified successfully ", image);
			}

			Thread.sleep(2000);

			// Enter a invalid employee number
			rserservationpopup.enter_EmpID("1245687");
			logger.log(LogStatus.PASS, "Wrong Employee Number entered successfully ");

			Thread.sleep(2000);

			rserservationpopup.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");
			Thread.sleep(4000);

			Thread.sleep(2000);

			// Verify Information Msg "Please enter a valid employere number" Should be displayed in red color.
			
			if (rserservationpopup.get_empErrorMessage().contains("Please enter a valid employee number.")) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,
						"Error message verified successfully " + rserservationpopup.get_empErrorMessage());
				logger.log(LogStatus.INFO, "Error message verified successfully", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Error message not verified successfully ");
				logger.log(LogStatus.INFO, "Error message not  verified successfully ", image);
			}

			String headcolor = driver
					.findElement(By
							.xpath("//div[@id='employeeNumber-wrapper']/div[@class='input-validation-message']//span"))
					.getCssValue("color");
			Thread.sleep(2000);
			System.out.println("Head Color:  " + headcolor);

			if (headcolor.equals("rgba(223, 32, 32, 1)")) {
				logger.log(LogStatus.PASS, "Error message is displayed in red color successfully");

			} else {
				logger.log(LogStatus.FAIL, "Error message is not displayed in red color");
			}

			
			
			
			
			// Enter a valid employere number and click "Create Reservation"
			
			rserservationpopup.clear_EmpID();
			Thread.sleep(2000);
			rserservationpopup.enter_EmpID(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "employee no entered successfully ");

			rserservationpopup.clk_CreateResvationBtn();
			logger.log(LogStatus.PASS, "Click On Reservation button successfully ");
			Thread.sleep(7000);

			

			// Verify User is navigated to PM Dashboard Page
			if (tabledata.get("walkInCustTitle").contains(Pmpage.get_WlkInCustText().trim())) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "PM Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "PM Dashboard is displayed successfully", image);
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "PM Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "PM Dashboard is not displayed ", image);

			}
			

			//============================ DB Validation===============================================
			
			
			String sqlQuery2 = " select top 1 reservationid from reservation where firstname = '" + FirstName
					+ "' and lastname = '" + LastName + "' "
					+ " and siteid = (select siteid from site where sitenumber = " + location + ")"
					+ " order by lastupdate desc ";
			String DB_ReservationNo = DataBase_JDBC.executeSQLQuery(sqlQuery2);
			
			Thread.sleep(4000);
			
			
			String sqlQuery1 = " select count(*) from reservation where firstname = '" + FirstName
					+ "' and lastname = '" + LastName + "' ";
			String resultDB = DataBase_JDBC.executeSQLQuery(sqlQuery1);
			int record = Integer.parseInt(resultDB);

			if (record > 0) {
				logger.log(LogStatus.PASS, "Create Reservation validation successful with reservation Number--"+DB_ReservationNo);
			} else {
				logger.log(LogStatus.FAIL, "Create  Reservation validation not successful with reservation Number--"+DB_ReservationNo);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
			}
			Thread.sleep(4000);

		

			ArrayList<String> DB_ReservationData = DataBase_JDBC
					.executeSQLQuery_List("select top 1  p.phonenumber, e.email" + " from reservation r"
							+ " join phone p on p.phoneid = r.phoneid"
							+ " join emailaddress e on e.emailaddressid = r.emailaddressid"
							+ " where r.reservationid = '" + DB_ReservationNo + "'");
			Thread.sleep(3000);

			String phoneNumber = tabledata.get("PhoneAreacode") + tabledata.get("PhoneExchnge")
					+ tabledata.get("PhoneLneNo");
			String emailId = tabledata.get("Emailaddrs");

			
			if (phoneNumber.equalsIgnoreCase(DB_ReservationData.get(0))
					&& emailId.equalsIgnoreCase(DB_ReservationData.get(1))) {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Phone Number and Email Id are matching with UI and DB--- for reservation ID::"+DB_ReservationNo);
				logger.log(LogStatus.INFO, "Phone Number and Email Id are matching with UI and DB", image);
				logger.log(LogStatus.INFO,
						"Expected Values: Phone Number-" + phoneNumber + "Expected Values: Email Id-" + emailId);
				logger.log(LogStatus.INFO, "Actual Values: Phone Number-" + DB_ReservationData.get(0)
						+ "Expected Values: Email Id-" + DB_ReservationData.get(1));
			} else {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Phone Number and Email Id are not matching with UI and DB-- for Resrvation ID::"+DB_ReservationNo);
				logger.log(LogStatus.FAIL, "Phone Number and Email Id are not matching with UI and DB", image);
				logger.log(LogStatus.INFO,
						"Expected Values: Phone Number-" + phoneNumber + "Expected Values: Email Id-" + emailId);
				logger.log(LogStatus.INFO, "Actual Values: Phone Number-" + DB_ReservationData.get(0)
						+ "Expected Values: Email Id-" + DB_ReservationData.get(1));
			}
			
			
 //=========================DB Validation of the dispalyed values =======================
			
			Thread.sleep(4000);
			driver.findElement(By.xpath("//input[@id='findReservation']")).sendKeys(DB_ReservationNo);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//span[contains(text(),'Find Reservation')]")).click();
			
			Thread.sleep(8000);
            
            String query1 = "Select  psp.productprice as MoveIncost, pos.amount as AdminFee, "
            		+ "max(case when st.taxtypeid=919 then Cast((st.Taxpercent*pos.amount)/100 as float) end) as AdminFeeTax, "
            		+ "max(case when st.taxtypeid=151 then Cast((st.Taxpercent*psp.productprice)/100 as float) end) as RentTax "
            		+ "from reservation R "
            		+ "join reservationitem RI on RI.reservationid=R.Reservationid "
            		+ "join productsiteprice psp on psp.productsitepriceid=ri.productsitepriceid "
            		+ "join productoptionsite pos on pos.siteid=r.siteid and pos.expirationdate is null and pos.productoptionid=5 "
            		+ "left join sitetax st on st.siteid=r.siteid and st.expirationdate is null and st.taxtypeid in (151, 919) "
            		+ " where r.reservationid='"+DB_ReservationNo+"' "
            		+ "group by R.reservationid, psp.productprice,psp.productprice,pos.amount";

			ArrayList<String> monthlyvaluesfromdb = DataBase_JDBC.executeSQLQuery_List(query1);
			
			double moveincostdb = Generic_Class.getDoubleAmount(monthlyvaluesfromdb.get(0));
			
			double movein_admin_fee_amount_db = Generic_Class.getDoubleAmount(monthlyvaluesfromdb.get(1));
			
		
			double  movein_admin_fee_tax_db;
			
			try{
				
				movein_admin_fee_tax_db = Generic_Class.getDoubleAmount(monthlyvaluesfromdb.get(2));
				
			}catch(Exception ex){
				
				movein_admin_fee_tax_db=0.0;
				
			}
			
			
			double movein_rent_tax_db;
			try{
				
				movein_rent_tax_db = Generic_Class.getDoubleAmount(monthlyvaluesfromdb.get(3));	
				
			}catch(Exception ex){
				
				movein_rent_tax_db=0.0;
				
			}
            
			 if (moveincost == moveincostdb) {
					
					logger.log(LogStatus.PASS, "Move In Cost displayed in UI--"+moveincost+"-Movin Cost disploayed in DB--"+moveincostdb);
					
				} else {

					logger.log(LogStatus.FAIL, "Move In Cost displayed in UI--"+moveincost+"-Movin Cost disploayed in DB--"+moveincostdb);
				}
			 Thread.sleep(2000);
			 	if (movein_admin_fee_amount == movein_admin_fee_amount_db) {
					
					logger.log(LogStatus.PASS, "Admin Fee  displayed  for MovinCost in UI--"+movein_admin_fee_amount+"-Admin Fee  displayed  for MovinCost in  DB--"+movein_admin_fee_amount_db);
					
				} else {

					logger.log(LogStatus.FAIL, "Admin Fee  displayed  for MovinCost in UI--"+movein_admin_fee_amount+"-Admin Fee  displayed  for MovinCost in  DB--"+movein_admin_fee_amount_db);
				}
				
			 	 Thread.sleep(2000);
				 	if (movein_admin_fee_tax == movein_admin_fee_tax_db) {
						
						logger.log(LogStatus.PASS, "Admin Fee TAX displayed  for MovinCost in UI--"+movein_admin_fee_tax+"-Admin Fee TAX displayed  for MovinCost in  DB--"+movein_admin_fee_tax_db);
						
					} else {

						logger.log(LogStatus.FAIL, "Admin Fee TAX displayed  for MovinCost in UI--"+movein_admin_fee_tax+"-Admin Fee TAX displayed  for MovinCost in  DB--"+movein_admin_fee_tax_db);
					}
			 	
				 	Thread.sleep(2000);
				 	if (movein_rent_tax == movein_rent_tax_db) {
						
						logger.log(LogStatus.PASS, "Rent TAX displayed  for MovinCost in UI--"+movein_rent_tax+"-Rent TAX displayed  for MovinCost in  DB--"+movein_rent_tax_db);
						
					} else {

						logger.log(LogStatus.FAIL, "Rent TAX displayed  for MovinCost in UI--"+movein_rent_tax+"-Rent TAX displayed  for MovinCost in  DB--"+movein_rent_tax_db);
					}
			 	
				 	Thread.sleep(2000);
			
			
			
			

		}

		catch (Exception ex) {
			ex.printStackTrace();
			resultFlag = "fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception " + ex);
		}

	}

	@DataProvider
	public Object[][] getData() {

		return Excel.getCellValue_inlist(path, "WalkinReservationLease", "WalkinReservationLease",
				"CreateReservation_NewCustomer_Individual");
	}

}
