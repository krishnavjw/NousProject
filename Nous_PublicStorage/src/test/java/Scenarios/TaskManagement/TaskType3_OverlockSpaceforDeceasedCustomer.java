package Scenarios.TaskManagement;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustInfoPages.Cust_EditAccountDetailsPage;
import Pages.EditAccountDetails.OtherStatuses;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.TaskManagement.OverlockSpaceTaskPage;
import Pages.TaskManagement.TaskStatusPage;
import Scenarios.Browser_Factory;

public class TaskType3_OverlockSpaceforDeceasedCustomer extends Browser_Factory{

	public ExtentTest logger;
	String resultFlag = "pass";
	String path = Generic_Class.getPropertyValue("Excelpath");
	WebDriverWait wait;
	String siteid_tobeset;
	String AccountNumber;
	String scpath;
	String image;
	// String taskid;

	@DataProvider
	public Object[][] getLoginData() {
		return Excel.getCellValue_inlist(path, "TaskManagement", "TaskManagement", "TaskType3_OverlockSpaceforDeceasedCustomer");
	}

	@Test(dataProvider = "getLoginData")
	public void TaskType3_Complete_CountPettyCash_AMTask(Hashtable<String, String> tabledata) throws Exception {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("TaskManagement").equals("Y"))) {
			resultFlag = "skip";
			logger.log(LogStatus.SKIP, "TaskType3_OverlockSpaceforDeceasedCustomer is Skipped");
			throw new SkipException("Skipping the test ");
		}

		try {
			logger = extent.startTest("TaskType3_OverlockSpaceforDeceasedCustomer", "Task Type 3 - InComplete CountPettyCash AMTask");
			testcaseName = tabledata.get("TestCases");
			Reporter.log("Test case started: " + testcaseName, true);

			String sqlQuery = "select  top 1 aoi.siteid from Account A "
					+ "join accountorder ao with(nolock) on ao.accountid=a.accountid "
					+ "join accountorderitem aoi with(nolock) on aoi.accountorderid=ao.accountorderid "
					+ "join storageorderitem soi with(nolock) on soi.storageorderitemid=aoi.storageorderitemid "
					+ "join rentalunit ru with(nolock) on ru.rentalunitid= soi.rentalunitid "
					+ "join cltransaction clt with(nolock) on clt.accountorderitemid=aoi.accountorderitemid "
					+ "join Clpayment CLP with(nolock) on CLP.cltransactionmasterid=CLT.cltransactionmasterid "
					+ "join Type T with(nolock) on T.typeid=CLP.paymentsourcetypeid " + "where t.name='Check' "
					+ "and soi.vacatedate is null " + "and aoi.siteid=6 "
					+ "and datediff(dd, clt.recorddatetime, getutcdate())>1";
			try {

				ArrayList<String> results = DataBase_JDBC.executeSQLQuery_List(sqlQuery);
				System.out.println(results);

				siteid_tobeset = results.get(0);
				// logger.log(LogStatus.INFO, "Site id related to account & to
				// be set is: " + siteid_tobeset);
				System.out.println("Site Id to be set is:   " + siteid_tobeset);

			} catch (Exception e) {
				logger.log(LogStatus.INFO, "No Details are fetched from database query");
			}

			// Fetching IP Address & changing it accordingly

			String ipadd = Generic_Class.getIPAddress();
			Thread.sleep(1000);

			String current_siteid_query = "select SiteID from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"
					+ ipadd + "'";
			String current_siteid = DataBase_JDBC.executeSQLQuery(current_siteid_query);
			System.out.println("Current Site Id is:   " + current_siteid);

			if (!(current_siteid.equals(siteid_tobeset))) {

				// Make the currently assigned site id to null
				String allocate_currentsiteidtonull_query = "Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid='"
						+ current_siteid + "'";
				DataBase_JDBC.executeSQLQuery(allocate_currentsiteidtonull_query);

				// Allocate new site id to current ip address
				String allocateip_to_newid = "Update siteparameter set paramvalue='" + ipadd
						+ "' where paramcode='IP_COMPUTER_FIRST' and siteid='" + siteid_tobeset + "'";
				DataBase_JDBC.executeSQLQuery(allocateip_to_newid);

			}

			// Login To the Application

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			LoginPage login = new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			Thread.sleep(12000);
			logger.log(LogStatus.PASS, "User logged in");
			
			
			
			
			
			// =================Handling customer facing
			// device========================
			Thread.sleep(2000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
		

			//Bifrostpop.clickContiDevice();


			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			Thread.sleep(5000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(1));
			//driver.get("http://wc2qa.ps.com/CustomerScreen/Mount");  
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_AUT"));

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

			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(12000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click(); 
			Thread.sleep(5000);
	
			// =================================================================================

// =================================================================================

	
			Thread.sleep(5000);
			
			
			// ================= Handling customer facing device
			// ==========================

			// ================================== PM Dashboard
			// =====================================================

			Thread.sleep(4000);
			PM_Homepage homepage = new PM_Homepage(driver);
			if (homepage.isexistingCustomerModelDisplayed()) {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User navigated to PM dashboard");
				logger.log(LogStatus.INFO, "img", image);
			} else {

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "user navigated not to PM dashboard");
				logger.log(LogStatus.INFO, "img", image);

			}

			Thread.sleep(3000);
			
			
			
			
			String sqlQuery1 = "select top 1 accountnumber, rentalunitnumber "
            + " from vw_unitdetails UD with(nolock) "
            + " join customer C with(nolock) on C.customerid=UD.customerid "
            + " where vacatedate is null "
            + " and siteid=6  "
            + " and c.isdeceased=0";
			

			//String AccountNo = DataBase_JDBC.executeSQLQuery(sqlQuery1);
			String AccountNo = "5064246";
				
				
			
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(4000);

			homepage.clk_AdvSearchLnk();
			Thread.sleep(3000);
			
			
			
			Advance_Search advSearch=new Advance_Search(driver);
			if(advSearch.verifyAdvSearchPageIsOpened())
			{
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "User navigated to Customer Search Page");
			logger.log(LogStatus.INFO, "img", image);
			
				
			}
			else
			{
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Unable to navigate to Customer Search Page");
				logger.log(LogStatus.INFO, "img", image);
			}
			
			
			
			advSearch.enterAccNum(AccountNo);
			Thread.sleep(3000);
			
			
			advSearch.clickSearchAccbtn();
			
			
			
			Actions act=new Actions(driver);
			
			/*advSearch.enterFirstName("james");
			Actions act=new Actions(driver);
			act.sendKeys(Keys.ENTER).build().perform();
			Thread.sleep(3000);
			
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Searched for Customer ");
			logger.log(LogStatus.INFO, "img", image);
			
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(10000);
			
			
			advSearch.click_FirstAccountNumber();
			logger.log(LogStatus.INFO, "Clicked on Account Number for the Customer ");

			Thread.sleep(3000);
			*/
			Cust_AccDetailsPage custAcc=new Cust_AccDetailsPage(driver);
			
			
			if(custAcc.verifyCustAccDashboard())
			{
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Navigated to Customer Account Details page ");
				logger.log(LogStatus.INFO, "img", image);
			}
			else
			{
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Unable to navigate to customer account details page ");
				logger.log(LogStatus.INFO, "img", image);
				
			}
			
			String customerName = driver.findElement(By.xpath("//h1[@class='customer-name bold']")).getText();
			String[] parts = customerName.split(" ");
			String firstName = parts[0]; // 004
			String lastName = parts[1]; 
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(4000);
			
			custAcc.clk_EditAccountDetailsBtn();
			logger.log(LogStatus.INFO, "Clicked on the button 'Edit Account Details' ");
			
			Thread.sleep(2000);
			
			
			
			
			Cust_EditAccountDetailsPage editAccPage=new Cust_EditAccountDetailsPage(driver);
			
			editAccPage.clickOtherCustomerStatusRadioBtn();
			logger.log(LogStatus.INFO, "Clicked on the Radio button 'Other Customer Status' ");
			Thread.sleep(2000);
			
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img", image);
			
			
			
			editAccPage.clickLaunchBtn();
			logger.log(LogStatus.INFO, "Clicked on the Launch button ");
			
			Thread.sleep(2000);
			
			
			OtherStatuses otherStatus=new OtherStatuses(driver);
			if(otherStatus.OtherStatusesTitleDisplayed()){
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Navigated to Other Statuses page ");
				logger.log(LogStatus.INFO, "img", image);
				
			}
			else{
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Unable to navigate to Other Statuses Page ");
				logger.log(LogStatus.INFO, "img", image);
				
			}
			
			
			Thread.sleep(5000);
			
			otherStatus.click_Deceasednotifiedcheckbox();
			logger.log(LogStatus.INFO, "Selected Notified check box on Deceased section");
			
			
			otherStatus.click_SaveButton();
			logger.log(LogStatus.INFO, "Clicked on Save button ");
			
			Thread.sleep(3000);
			
			
			otherStatus.enter_EmployeeID(tabledata.get("UserName"));
			
			otherStatus.enter_Notes("Notes");
			
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img", image);
			
			Thread.sleep(2000);
			
			logger.log(LogStatus.PASS, "Entered Employee ID and Notes in the Pop up window ");
			
			otherStatus.click_Continue();
			
			
			logger.log(LogStatus.INFO, "Clicked on Continue button  ");
			
			
			
			
			
			Thread.sleep(10000);
			
			try{
			
			//otherStatus.click_OKButton();
				
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
			
			
			}
			
			catch(Exception ex){
				ex.getMessage();
			}
			
			Thread.sleep(5000);

			
			
			custAcc.click_BackToDashboard();
			logger.log(LogStatus.INFO, "Clicked on Back to Dashboard button   ");
			Thread.sleep(20000);
			
			
			logger.log(LogStatus.INFO, "Navigated to PM Dashboard Successfully  ");
			
			
			List<WebElement> taskList = driver.findElements(By.xpath("//div[@id='task-grid']//div//table//tbody//tr//td/a"));
			act=new Actions(driver);
			for(WebElement ele:taskList)
			{
				act.moveToElement(ele).build().perform();
				System.out.println(ele.getText());
				if(ele.getText().trim().contains(lastName))
				{
					act.moveToElement(ele).click().build().perform();
					break;
				}
				
				Thread.sleep(1000);
			}
			
			
			Thread.sleep(3000);
			
			
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to the Screen - Overlock Spaces for the Deceased Customer ");
			logger.log(LogStatus.INFO, "img", image);
			
			Thread.sleep(7000);
			
			String url=driver.getCurrentUrl();
			String taskid="";
			String arr[]=url.split("ViewTask/");
			//arr = arr[1].split("&");
			taskid=arr[1];
			
			
			logger.log(LogStatus.INFO, "Selected Task ID is " + taskid);
			
			OverlockSpaceTaskPage overlock=new OverlockSpaceTaskPage(driver);
			
			overlock.SelectDateFromCalendar("0");
			Thread.sleep(2000);
			
			logger.log(LogStatus.INFO, "Selected Date " + overlock.getselectedDateValue() + "from the Field 'Date of Overlock'");
			String selectedDateinUI = overlock.getselectedDateValue();
			String data[]=selectedDateinUI.split(" ");
			selectedDateinUI=data[1];
			
			overlock.select_CompleteRadioButton();
			
			logger.log(LogStatus.INFO, "Selected Complete From the Radio button field ");
			
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(10000);
			
			
			Thread.sleep(2000);

			
			overlock.click_Submitbutton();
			
			logger.log(LogStatus.INFO, "Clicked on Submit button  ");
			
			Thread.sleep(2000);

			overlock.enterEmployeeID(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered Employee ID  " + tabledata.get("UserName"));
			
			Thread.sleep(2000);
			
			overlock.click_ConfirmButton();
			logger.log(LogStatus.INFO, "Clicked on Confirm Button  " );
			
			
			Thread.sleep(5000);
			
			
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Navigated back to PM Dashboard   " );
			logger.log(LogStatus.INFO, "img", image);
			
			
			Thread.sleep(12000);
			
			//In the task list module, select the filter as Closed

			homepage.sel_ClosedOption();
			Thread.sleep(15000);
			logger.log(LogStatus.INFO, "Selected Closed Option in Task list");
			Thread.sleep(2000);

			List<WebElement> listTaskTitle1 = driver
					.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[4]/a"));
			int size1 = listTaskTitle1.size();

			Thread.sleep(3000);

			for (int i = 0; i < size1; i++) {

				
				
				//lastName;
				if (listTaskTitle1.get(i).getText().contains(lastName)) {

					List<WebElement> listTaskStatus = driver
							.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[2]"));
					int statusSize = listTaskStatus.size();

					for (int j = 0; j < statusSize; j++) {

						if (listTaskStatus.get(j).getText().equals("Completed Today")) {
							logger.log(LogStatus.PASS, "The task  has status of Completed today ");
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",listTaskTitle1.get(i));
							Thread.sleep(8000);

							scpath = Generic_Class.takeScreenShotPath();
							image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.PASS, "img", image);

							Thread.sleep(5000);

							listTaskTitle1.get(i).click();
							break;

						}
					}

					break;

				}

				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", listTaskTitle1.get(i));

			}

			Thread.sleep(8000);
		
			
			
			AccountNumber=driver.findElement(By.xpath("//div[@class='task-data-section double-padding-bottom']//div[@class='right-column']/div/span")).getText();
			if(AccountNumber.contains(AccountNo)){
			
				logger.log(LogStatus.PASS, "Account no matched : and Account no is : " + AccountNumber );
				Thread.sleep(8000);

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);
			}
			
			else{
				logger.log(LogStatus.FAIL, "Account no not  matched :");
			}
			
			Thread.sleep(2000);
			
			String qry1 = "select FORMAT(completionDT,'yyyy-MM-dd'),TaskStatusId from task where id='"+taskid+"' ";
			ArrayList<String> list = DataBase_JDBC.executeSQLQuery_List(qry1);
			
			String completionDt = list.get(0);
			String taskstatusid = list.get(1);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String CurrentDate = sdf.format(date);
			Reporter.log("Current Date : "+CurrentDate,true);
			
			
			/*if(removedate.trim() == removedatedisplayed.trim()){
				logger.log(LogStatus.PASS, "Displayed Date "+removedatedisplayed+ "is matched with selected date: "+removedate);
			}else{
				logger.log(LogStatus.FAIL, "Displayed Date "+removedatedisplayed+ "is not matched with selected date: "+removedate);

			}*/
			
			if(CurrentDate.trim().equals(completionDt.trim()) & taskstatusid.equals("4") ){
				logger.log(LogStatus.PASS, "Current date: " +CurrentDate+ "and task status id: " +taskstatusid+ " are matched with database. Date: "+completionDt+ " and task status id is : " + taskstatusid );
			}else{
				logger.log(LogStatus.FAIL, "Current date: " +CurrentDate+ "and task status id: " +taskstatusid+ " are not matched with database. Date: "+completionDt+ " and task status id is : " + taskstatusid);
			}
			Thread.sleep(2000);
		
			
			
			Thread.sleep(4000);
			
			String sqlQuery3="select ts.id as TaskID, Ts.Title, ts.Taskstatusid, Tss.DisplayName as TaskStatus, SubString(TaskDetailJson, (CHARINDEX('\"', TaskDetailJson, 0)), 38) As EnteredDate "
               + " from task ts "
               + " join taskstatus tss with(nolock) on tss.id=ts.taskstatusid "
               + " where ts.id='"+taskid+"'";
			 ArrayList<String> enteredDatelist = DataBase_JDBC.executeSQLQuery_List(sqlQuery3);
			 String enterdDate = enteredDatelist.get(4);
			 
			 String str[]=enterdDate.split(":");
			 String s1=str[1];
			 String s2[]=s1.split("T");
			 String ActualEnterddateFromDB=s2[0].trim();
			 ActualEnterddateFromDB = ActualEnterddateFromDB.substring(1);
			 
			 String dateFromDatabse=ActualEnterddateFromDB.replaceAll("-", "/");
			 Reporter.log("Data value---------->"+dateFromDatabse);
             String date1 = new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy/MM/dd").parse(dateFromDatabse));
          			
		
			if(selectedDateinUI.equals(date1)){
				
				logger.log(LogStatus.PASS, "Enterd Date from UI and updated Date from DB are matched : Date selected from UI is : " + selectedDateinUI +"Updated Date in DB :" + date1);

			}
			
			else{
				logger.log(LogStatus.PASS, "Selected Date from UI and Date from DB are not matched ");
			}
			
			
			
			
			
			
			
			Thread.sleep(10000);
			
		
		}

		catch (Exception ex) {
			ex.printStackTrace();
			resultFlag = "fail";
			
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);
			logger.log(LogStatus.FAIL, "Test script failed due to the exception " + ex);
		}
	}

	@AfterMethod
	public void afterMethod() {

		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "TaskManagement", this.getClass().getSimpleName(), "Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "TaskManagement", this.getClass().getSimpleName(), "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "TaskManagement", this.getClass().getSimpleName(), "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}
	

}
