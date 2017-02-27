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
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.TaskManagement.TaskStatusPage;
import Scenarios.Browser_Factory;

public class TaskType3_Complete_CountPettyCash_AMTask extends Browser_Factory{

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
		return Excel.getCellValue_inlist(path, "TaskManagement", "TaskManagement", "TaskType3_Complete_CountPettyCash_AMTask");
	}

	@Test(dataProvider = "getLoginData")
	public void TaskType3_Complete_CountPettyCash_AMTask(Hashtable<String, String> tabledata) throws Exception {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("TaskManagement").equals("Y"))) {
			resultFlag = "skip";
			logger.log(LogStatus.SKIP, "TaskType3_Complete_CountPettyCash_AMTask is Skipped");
			throw new SkipException("Skipping the test ");
		}

		try {
			logger = extent.startTest("TaskType3_Complete_CountPettyCash_AMTask", "Task Type 3 - Complete CountPettyCash AMTask");
			testcaseName = tabledata.get("TestCases");
			Reporter.log("Test case started: " + testcaseName, true);

			wait = new WebDriverWait(driver, 60);

			String sqlQuery = "select top 1 s.siteid,s.sitenumber,t.* from Taskmaster TM with(nolock)join groupsite gs with(nolock) on gs.groupid=tm.propertygroupid join site s with(nolock) on s.siteid=gs.siteid join task t with(nolock) on t.locationcode=s.sitenumber left join taskitem ti with(nolock) on ti.taskid=t.id and ti.taskid is null and t.createdt <getutcdate() and t.completiondt is null and t.duedt > getutcdate()";
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

			// Login To the Application

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			LoginPage login= new LoginPage(driver);
			String siteNumber = login.get_SiteNumber();
			login.enterUserName(tabledata.get("UserName"));
			
			login.enterPassword(tabledata.get("Password"));
		
			login.clickLogin();
			logger.log(LogStatus.INFO, "User Loged in  successfully");
			Thread.sleep(5000);

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
		

			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);

			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T); 
			Thread.sleep(8000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			driver.switchTo().window(tabs.get(1));

			// Navigating to CFS
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_QA"));  

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
			Thread.sleep(8000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
			
//=========================================================================================================

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

			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(4000);

			// String TaskTitle =
			// driver.findElement(By.xpath("//div[@id='task-grid']//table//tr[1]/td[4]/a")).getText();

			// Need to click task Based on task fetched from DB

			List<WebElement> listTaskTitle1 = driver
					.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[4]/a"));
			int size1 = listTaskTitle1.size();
			// ((JavascriptExecutor)
			// driver).executeScript("arguments[0].scrollIntoView(true);",
			// listTaskTitle1.get(size1-1));

			Thread.sleep(3000);

			boolean flag1 = false;
			for (int i = 0; i < size1; i++) {

				if (listTaskTitle1.get(i).getText().equals(TaskTitlefromDB)) {
					
					
					
					logger.log(LogStatus.PASS, "Count Petty Cash Morning task displayed on Task List module ");
					
					scpath = Generic_Class.takeScreenShotPath();
					image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "img", image);
					Thread.sleep(4000);
					listTaskTitle1.get(i).click();
					logger.log(LogStatus.PASS, "Clicked on Task successfully and task title is : " + TaskTitlefromDB);

					break;

				}
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
						listTaskTitle1.get(i));
			}

			Thread.sleep(5000);

		
			// driver.findElement(By.xpath("//div[@id='task-grid']//table//tr[1]/td[4]/a")).click();
			Thread.sleep(15000);
		

			String url = driver.getCurrentUrl();
			String arr[] = url.split("/");
			String taskid = arr[5];
			logger.log(LogStatus.INFO, "Displayed task id in url: " + taskid);
			Thread.sleep(3000);

			// Creating object
			TaskStatusPage taskpage = new TaskStatusPage(driver);

			taskpage.enter_Amount(tabledata.get("Amount"));
			logger.log(LogStatus.PASS, "Entered Amount : " +tabledata.get("Amount") );
			Thread.sleep(3000);
			taskpage.clk_CompleteRadioBtn();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Selected Complete Radio button");
			taskpage.enter_Comment("Task is completed");
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Comment in Text box: "+" Task is completed");
			Thread.sleep(8000);
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "img",image1);
			Thread.sleep(2000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(4000);
			
			
		
			taskpage.clk_SubmitBtn();
			logger.log(LogStatus.PASS, "Clicked on Submit button Successfully");
			Thread.sleep(4000);
			taskpage.enter_EmployeeNumber(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered username");
			Thread.sleep(4000);
			String scpath2 = Generic_Class.takeScreenShotPath();
			String image2 = logger.addScreenCapture(scpath2);
			logger.log(LogStatus.INFO, "img", image2);
			Thread.sleep(2000);
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
			Thread.sleep(5000);
			
			
			homepage.sel_ClosedOption();
			Thread.sleep(15000);
			logger.log(LogStatus.INFO, "Selected Closed Option in Task list");
			Thread.sleep(2000);
			
			
			
			List<WebElement> listTaskTitle = driver
					.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[4]/a"));
			int size = listTaskTitle.size();

			Thread.sleep(3000);

			boolean flag = false;
			for (int i = 0; i < size; i++) {

				if (listTaskTitle.get(i).getText().equals(TaskTitlefromDB)) {

					List<WebElement> listTaskStatus = driver
							.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[2]"));
					int statusSize = listTaskStatus.size();

					for (int j = 0; j < statusSize; j++) {

						if (listTaskStatus.get(j).getText().equals("Completed Today")) {
							logger.log(LogStatus.PASS, "The task  has status of Completed today ");
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
									listTaskTitle.get(i));
							Thread.sleep(4000);

							scpath = Generic_Class.takeScreenShotPath();
							image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.PASS, "img", image);

							Thread.sleep(5000);

							listTaskTitle.get(i).click();
							break;

						}
					}

					break;

				}

				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", listTaskTitle.get(i));

			}

			Thread.sleep(5000);
			
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);

			
			
			
			
			Thread.sleep(4000);
			String qry1 = " select FORMAT(completionDT,'yyyy-MM-dd') from task where id='"+taskid+"' ";
			String completionDt = DataBase_JDBC.executeSQLQuery(qry1);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String CurrentDate = sdf.format(date);
			Reporter.log("Current Date : "+CurrentDate,true);
			
			if(CurrentDate.trim().equals(completionDt.trim())){
				logger.log(LogStatus.PASS, "Current date " +CurrentDate+ " is matched with database date in task table "+completionDt);
			}else{
				logger.log(LogStatus.FAIL, "Current date " +CurrentDate+ " is not matched with database date in task table "+completionDt);
			}
			Thread.sleep(2000);
			

			

		}

		catch (Exception ex) {
			ex.printStackTrace();
			resultFlag = "fail";
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
