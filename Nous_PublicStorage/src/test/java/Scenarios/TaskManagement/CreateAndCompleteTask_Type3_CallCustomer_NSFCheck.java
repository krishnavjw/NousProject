package Scenarios.TaskManagement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import Pages.CustDashboardPages.Cust_AccActivitiesPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.ReversePaymentPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.TaskManagement.CreateTaskPage;
import Pages.TaskManagement.TaskStatusPage;
import Scenarios.Browser_Factory;

public class CreateAndCompleteTask_Type3_CallCustomer_NSFCheck extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag = "pass";
	String path = Generic_Class.getPropertyValue("Excelpath");
	WebDriverWait wait;
	String siteid_tobeset;

	String scpath;
	String image;

	@DataProvider
	public Object[][] getData() {
		return Excel.getCellValue_inlist(path, "TaskManagement", "TaskManagement",
				"CreateAndCompleteTask_Type3_CallCustomer_NSFCheck");
	}

	@Test(dataProvider = "getData")
	public void emailCompose(Hashtable<String, String> tabledata) throws Exception {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("TaskManagement").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}

		try {

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

			Thread.sleep(4000);

			String query_TaskTitle = "select top 1 t.title from Taskmaster TM with(nolock)join groupsite gs with(nolock) on gs.groupid=tm.propertygroupid join site s with(nolock) on s.siteid=gs.siteid join task t with(nolock) on t.locationcode=s.sitenumber left join taskitem ti with(nolock) on ti.taskid=t.id and ti.taskid is null and t.createdt <getutcdate() and t.completiondt is null and t.duedt > getutcdate()";

			String TaskTitlefromDB = DataBase_JDBC.executeSQLQuery(query_TaskTitle);

			Thread.sleep(12000);
			Thread.sleep(2000);
			driver.get(driver.getCurrentUrl());
			Thread.sleep(2000);
			driver.get(driver.getCurrentUrl());
			Thread.sleep(5000);

			// Login to PS Application as PM
			logger = extent.startTest(this.getClass().getSimpleName(),
					"CreateAndCompleteTask_Type3_CallCustomer_NSFCheck");
			LoginPage login = new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as PM");
			
			
			Thread.sleep(2000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
		

			Bifrostpop.clickContiDevice();
			
			
			String scpath, image;

			Thread.sleep(15000);
			
	
			Thread.sleep(5000);
			
			PM_Homepage PM_HomePage = new PM_Homepage(driver);
			
			JavascriptExecutor js = (JavascriptExecutor) driver;

			Thread.sleep(3000);
			PM_HomePage.clk_AdvSearchLnk();
			logger.log(LogStatus.PASS, "clicked on advanced search link in the PM Dashboard sucessfully");

			Thread.sleep(6000);
			Advance_Search advSearch = new Advance_Search(driver);
			logger.log(LogStatus.PASS, "creating object for advance search page sucessfully");

			Thread.sleep(1000);
			
			 String sqlQuery1 = "select top 1  A.accountnumber, ru.rentalunitnumber,aoi.siteid, t.name as paymenttype, t.typeid "
					 + "from Account A with(nolock) "
					 + " join accountorder ao with(nolock) on ao.accountid=a.accountid "
					 + " join accountorderitem aoi with(nolock) on aoi.accountorderid=ao.accountorderid "
					 + " join storageorderitem soi with(nolock) on soi.storageorderitemid=aoi.storageorderitemid "
					 + " join rentalunit ru with(nolock) on ru.rentalunitid= soi.rentalunitid "
					 + " join cltransaction clt with(nolock) on clt.accountorderitemid=aoi.accountorderitemid "
					 + " join Clpayment CLP with(nolock) on CLP.cltransactionmasterid=CLT.cltransactionmasterid "
					 + " join Type T with(nolock) on T.typeid=CLP.paymentsourcetypeid "
					 + " where t.name='Check' "
					 + " and soi.vacatedate is null "
					 + " and aoi.siteid=6 "
					 + "and datediff(dd, clt.recorddatetime, getutcdate())>1 "
					 + "order by clt.recorddatetime";
			
	
			 ArrayList<String> AccountNumberlist = DataBase_JDBC.executeSQLQuery_List(sqlQuery1);
			  Thread.sleep(6000);
			  
			 
			 String AccountNumber = AccountNumberlist.get(0);
			 
			 //String AccountNumber ="2232600130";
			 
			//String AccountNumber = "2232600113";
			System.out.println("the account number is -----" + AccountNumber);

			Thread.sleep(2000);
			advSearch.enterAccNum(AccountNumber);
			logger.log(LogStatus.INFO, "Enter existing customer Account Number in Account field successfully");

			Thread.sleep(3000);
			advSearch.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "clicking on Search button successfully");
			Thread.sleep(8000);
			
			
			

			String customerName = driver.findElement(By.xpath("//div[@class='customer-categories bold']/span[1]")).getText();
			String[] parts = customerName.split(" ");
			String firstName = parts[0]; // 004
			String lastName = parts[1]; 
			
			
			
			
			
			
			
			
			Cust_AccDetailsPage cust = new Cust_AccDetailsPage(driver);

			cust.click_AccountActivities();
			logger.log(LogStatus.PASS, "Clicked on Account Activities tab ");
			Thread.sleep(25000);

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);

			cust.clk_viewAllActivities_RadioBtn();
			Thread.sleep(15000);
			// Expand Payment(check)activity and click on NSF check

			List<WebElement> listTskName = driver.findElements(By.xpath("//div[@id='activities-grid']/div[2]/table/tbody/tr//td[text()='Payment (Check)']"));
			// listTskName.get(0).click();
			int sizelist = listTskName.size();
			
			for (int i = 0; i < sizelist; i++) {
				if (listTskName.get(i).getText().contains("Payment (Check)")) {
					
					List<WebElement> listofArrow = driver.findElements(By.xpath("//div[@id='activities-grid']/div[2]/table/tbody/tr//td[text()='Payment (Check)']/preceding-sibling::td[@class='k-hierarchy-cell']//a[@class='k-icon k-plus']"));
					int statusSize = listofArrow.size();
					Thread.sleep(8000);
					String value = listofArrow.get(1).getAttribute("class");
					
					listofArrow.get(1).click();
					break;

				}

				
				js.executeScript("arguments[0].scrollIntoView(true);", listTskName.get(i));
					
					
					
					
				
				}

				
			
			
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(5000);

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);
			Thread.sleep(5000);

			Cust_AccActivitiesPage cust1 = new Cust_AccActivitiesPage(driver);
			cust1.click_NFSCheck();

			logger.log(LogStatus.PASS, "Clicked on NFS Check ");

			Thread.sleep(4000);

			// Verify User Should land on reverse payment page

			ReversePaymentPage payment = new ReversePaymentPage(driver);
			if (payment.isDisplayedReversepaymentTitle()) {
				logger.log(LogStatus.PASS, "User navigated  to reverse payment page");
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "img", image);
				Thread.sleep(5000);
			} else {
				logger.log(LogStatus.FAIL, "User not  navigated  to reverse payment page");
			}

			// Select Apply Bad Charges check box , enter notes and and click on
			// Reverse Returned Check button
			
			
			
			
			
			
			
			
			
			
			payment.clk_applyBadCheckCharge_YesRadioBtn();
			Thread.sleep(3000);

			payment.enter_Note("Reverse payment");

			Thread.sleep(3000);
			payment.clk_ReverseReturnedCheckBtn();

			logger.log(LogStatus.PASS, "Clicked on Bad Charges check box amd Reverse Returned Check button");

			Thread.sleep(8000);

			TaskStatusPage taskpage = new TaskStatusPage(driver);

			taskpage.enter_EmployeeNumber(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered username");
			Thread.sleep(4000);
			String TransactionReversedDate = driver.findElement(By.xpath("//div[@id='transaction-complete-modal']//div[contains(text(),'Date:')]/following-sibling::div")).getText();
			
			
			Thread.sleep(4000);
			String scpath2 = Generic_Class.takeScreenShotPath();
			String image2 = logger.addScreenCapture(scpath2);
			logger.log(LogStatus.INFO, "img", image2);
			Thread.sleep(2000);
			CreateTaskPage createTask = new CreateTaskPage(driver);
			// createTask.click_Ok_btn();
			driver.findElement(By.xpath("//div[@class='command-row clearfix-container']/a[contains(text(),'Ok')]"))
					.click();
			logger.log(LogStatus.PASS, "Clicked on OK Button suceessfully");
			Thread.sleep(15000);

			if (cust.isCustdbTitleDisplayed()) {
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "img", image);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "img", image);
			}
			Thread.sleep(5000);

			// Click on back to dashboard

			cust.click_BackToDashboard();
			logger.log(LogStatus.PASS, "Clicked on Back To Dashboard Button suceessfully");
			Thread.sleep(8000);

			// Verify User should navigate back to PM dashboard

			if (PM_HomePage.isexistingCustomerModelDisplayed()) {
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User navigated to PM Dashboard successfully");
				logger.log(LogStatus.INFO, "img", image);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "User not  navigated to PM Dashboard ");
				logger.log(LogStatus.INFO, "img", image);
			}

			// Verify that in task List module there is a "Call Customer for NSF
			// check" task displayed for that customer

			List<WebElement> listTaskTitle = driver
					.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[4]/a"));
			int size = listTaskTitle.size();

			Thread.sleep(3000);

			boolean flag = false;
			for (int i = 0; i < size; i++) {

				if (listTaskTitle.get(i).getText().contains(lastName)) {
					logger.log(LogStatus.PASS, "NSF check task displayed for the customer ");

					Thread.sleep(8000);

					scpath = Generic_Class.takeScreenShotPath();
					image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "img", image);

					Thread.sleep(5000);

					listTaskTitle.get(i).click();
					break;

				}
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", listTaskTitle.get(i));

			}

			logger.log(LogStatus.PASS, "Clicked on task Successfully");

			Thread.sleep(20000);

			String url = driver.getCurrentUrl();
			String arr[] = url.split("/");
			String taskid = arr[5];
			logger.log(LogStatus.INFO, "Displayed task id in url: " + taskid);
			Thread.sleep(3000);

			// Creating object
			// TaskStatusPage taskpage = new TaskStatusPage(driver);

			// driver.findElement(By.xpath("//div[@id='CustomerCalledDate']/span")).click();
			String accountNoinTaskPage = driver.findElement(By.xpath("//div[@class='right-column']/div/span/a/.."))
					.getText();

			// TaskStatusPage taskpage = new TaskStatusPage(driver);

			if (accountNoinTaskPage.contains(AccountNumber)) {
				logger.log(LogStatus.PASS, "Account No matched for customer:");
			}

			else {
				logger.log(LogStatus.FAIL, "Account No not  matched for customer:");
			}

			taskpage.SelectDateFromCalendar("0", driver);
			
			
			
			String dateSelecetd = driver.findElement(By.xpath("//div[@id='CustomerCalledDate']/span")).getText();
			
			String[] val = dateSelecetd.split(" ");
			String Date = val[1];
			
			
		
			
			
			
			
			
			

			Thread.sleep(3000);
			taskpage.clk_CompleteRadioBtn();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Selected Complete Radio button");
			taskpage.enter_Comment("Task is completed");
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Comment in Text box: " + " Task is completed");
			Thread.sleep(8000);
			String scpath1 = Generic_Class.takeScreenShotPath();
			String image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "img", image1);
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(4000);

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);

			taskpage.clk_SubmitBtn();
			logger.log(LogStatus.PASS, "Clicked on Submit button Successfully");
			Thread.sleep(4000);
			taskpage.enter_EmployeeNumber(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered username");
			Thread.sleep(4000);
			scpath2 = Generic_Class.takeScreenShotPath();

			taskpage.clk_ConfirmBtn();
			logger.log(LogStatus.PASS, "Clicked on Confirm Button suceessfully");
			Thread.sleep(15000);

			// ==================================Back to PM Dashboard
			// ============================================

			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(9000);

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);
			logger.log(LogStatus.PASS, "User Navigated to PM Dashboard");
			Thread.sleep(8000);

			PM_HomePage.sel_ClosedOption();
			Thread.sleep(15000);
			logger.log(LogStatus.INFO, "Selected Closed Option in Task list");
			Thread.sleep(2000);
					
			DateFormat dateFormat = new SimpleDateFormat("MM/dd");
			Date currentDay = new Date();
			String TodayDate = dateFormat.format(currentDay);	
			

			List<WebElement> listTaskTitle1 = driver
					.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[4]/a"));
			int size1 = listTaskTitle1.size();
			
			
			
			
			
			
			

			Thread.sleep(3000);

			for (int i = 0; i < size1; i++) {

				if (listTaskTitle1.get(i).getText().contains(lastName)) {

					List<WebElement> listTaskStatus = driver
							.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[2]"));
					int statusSize = listTaskStatus.size();

					for (int j = 0; j < statusSize; j++) {

						if (listTaskStatus.get(j).getText().equals("Completed Today")) {
							
							
							List<WebElement> DateList = driver.findElements(By.xpath("//div[@id='task-grid']//table/tbody/tr/td[6]"));
							
							int datesize=DateList.size();
							
							for (int k = 0; k < datesize; k++) {
							
							
							if(TransactionReversedDate.contains(TodayDate)){
								logger.log(LogStatus.PASS, "The task  has status of Completed today ");
							
								((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",listTaskTitle1.get(i));
								Thread.sleep(2000);
								break;
								

						}
							
						}
							
							break;
							
						}
					}

					break;

				}

				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", listTaskTitle1.get(i));

			}
			
			/*Thread.sleep(8000);
			if (accountNoinTaskPage.contains(AccountNumber)) {
				logger.log(LogStatus.PASS, "Account No matched for customer:  " + AccountNumber);
			}

			else {
				logger.log(LogStatus.FAIL, "Account No not  matched for customer:");
			}

			
			*/

			Thread.sleep(8000);

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);

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
			
			
			
			String sqlQuery3="select ts.id as TaskID, Ts.Title, ts.Taskstatusid, Tss.DisplayName as TaskStatus, SubString(TaskDetailJson, (CHARINDEX(',', TaskDetailJson, 0)+1), 46) As EnteredDate "
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
		          			
				
					if(Date.equals(date1)){
						
						logger.log(LogStatus.PASS, "Enterd Date from UI and updated Date from DB are matched : Date selected from UI is : " + Date +"Updated Date in DB :" + date1);

					}
					
					else{
						logger.log(LogStatus.PASS, "Selected Date from UI and Date from DB are not matched ");
					}
			
			
			
			
			
			
			

		} catch (Exception ex) {
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
