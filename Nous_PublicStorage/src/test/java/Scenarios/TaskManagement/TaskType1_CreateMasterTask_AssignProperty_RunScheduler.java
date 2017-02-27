
package Scenarios.TaskManagement;

import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

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
import Pages.TaskManagement.CreateTaskPage;
import Pages.TaskManagement.ViewTaskMastersPage;
import Scenarios.Browser_Factory;

public class TaskType1_CreateMasterTask_AssignProperty_RunScheduler extends Browser_Factory {

	String path = "./src/main/resources/Resources/PS_TestData.xlsx";
	public ExtentTest logger;
	String resultFlag = "pass";

	@DataProvider
	public Object[][] getData() {
		return Excel.getCellValue_inlist(path, "TaskManagement", "TaskManagement", "TaskType1_CreateMasterTask_AssignProperty_RunScheduler");
	}

	@Test(dataProvider = "getData") 
	public void emailCompose(Hashtable<String, String> tabledata) throws Exception {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("TaskManagement").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}

		try {
			// Login to PS Application as DM
			logger = extent.startTest(this.getClass().getSimpleName(), "TaskType1_CreateMasterTask_AssignProperty_RunScheduler");
			LoginPage login = new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as DM");
			String scpath, image;

			/*
			 * Thread.sleep(10000); driver.findElement(By.xpath(
			 * "//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']"
			 * )).click(); Thread.sleep(5000);
			 */

			/*
			 * try{ Dashboard_BifrostHostPopUp Bifrostpop1 = new
			 * Dashboard_BifrostHostPopUp(driver); logger.log(LogStatus.INFO,
			 * "PopUp window object is created successfully");
			 * Bifrostpop1.clickContiDevice(); Thread.sleep(10000); }
			 * catch(Exception e){ ; }
			 */

			// Navigate to the View Task Masters Screen
			
			Thread.sleep(15000);
			
			
			
			
			System.out.println(tabledata.get("View_TaskMasters"));
			//driver.navigate().to(tabledata.get("View_TaskMasters"));
			
			
			driver.navigate().to("http://wc2qa.ps.com/TaskManagement/ViewTaskMasters");
			logger.log(LogStatus.INFO, "Navigated to the View Task Masters Screen");
			Thread.sleep(9000);

			scpath = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath, true);
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);

			// Click on Create Task button

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,2000)", "");
			Thread.sleep(3000);

			js.executeScript("window.scrollBy(2000,0)", "");
			Thread.sleep(2000);

			ViewTaskMastersPage viewTaskMasters = new ViewTaskMastersPage(driver);

			viewTaskMasters.click_CreateTask();
			logger.log(LogStatus.INFO, "Clicked on Create Task button ");

			Thread.sleep(5000);

			// Verify A pop up window with drop down values should be displayed

			scpath = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath, true);
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);

			// Select Simple Task from drop down and click on create

			viewTaskMasters.selectTaskOption(tabledata.get("Task_Type"), driver);
			logger.log(LogStatus.INFO, "Selected task type as Simple Task from the drop down menu");
			Thread.sleep(3000);

			scpath = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath, true);
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);

			// Enter unique title for the task name
			CreateTaskPage createTask = new CreateTaskPage(driver);

		   driver.findElement(By.xpath("(//a[contains(text(),'Create')])[2]")).click();
			Thread.sleep(8000);

			if (driver.findElement(By.xpath("//h3[contains(text(),'Create SimpleTask')]")).isDisplayed()) {

				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User navigated to  create simple task page");
				logger.log(LogStatus.PASS, "img", image);

			}

			Thread.sleep(3000);

			//String uuid = UUID.randomUUID().toString();
			
			
			
			String uuid = Generic_Class.get_RandmString();
		
			String TaskName="TaskType1"+uuid;
		

			
			createTask.enterTaskName(TaskName);
			logger.log(LogStatus.INFO, "Entered Task Name in the text field as : " + TaskName);

			// Enter Description, Select priority as Medium
			createTask.enterTaskDescription("Description for Task Test1");
			logger.log(LogStatus.INFO, "Entered Task Description in the text Area ");

			createTask.selectPriority("Medium Priority Task");
			logger.log(LogStatus.INFO, "Selected Medium Priority Task from the drop down field Priority ");

			Thread.sleep(3000);
			// Select values in the Create Task Page

			createTask.selectRadioButtonValue("Rollover", driver);
			logger.log(LogStatus.INFO, "Selected the option Rollover from the Radio button field  ");
			Thread.sleep(3000);

			logger.log(LogStatus.INFO, "Selected Tomorrow's date from the date field Scheduler Start Date");

			logger.log(LogStatus.INFO, "Not Selected any value from the date field Scheduler End Date");

			createTask.selectRadioButtonValue("Active", driver);
			logger.log(LogStatus.INFO, "Selected Suspend Task as Active");

			createTask.enterTimetoComplete("1");
			logger.log(LogStatus.INFO, "Entered Time to complete as 1 Day");

			createTask.enterStartTime("09", "30");
			logger.log(LogStatus.INFO, "Entered Start Time value in the Text Field");

			createTask.selectRadioButtonValue("After Open", driver);
			logger.log(LogStatus.INFO, "Selected AM from the Start Time Field");

			createTask.selectSchedulerFrequency("Daily");
			logger.log(LogStatus.INFO, "Selected Scheduler Frequency as Daily");

			createTask.selectDeliveryOptionDay("Monday,Tuesday,Wednesday,Thursday,Friday", driver);
			logger.log(LogStatus.INFO, "Selected Days from Delivery options");

			js.executeScript("window.scrollBy(0,500)", "");

			Thread.sleep(2000);

			createTask.selectPropertyGroup("Group name");
			logger.log(LogStatus.INFO, "Selected Group1test from property group drop down field ");
			Thread.sleep(8000);

			createTask.selectAssignToValue(tabledata.get("AssignedTo"));
			logger.log(LogStatus.INFO, "Selected Property Manager from Assign to drop down field ");

			Thread.sleep(5000);

			js.executeScript("window.scrollBy(2000,0)", "");

			Thread.sleep(2000);

			createTask.clickCreateButton(driver);

			Thread.sleep(7000);

			createTask.enter_EmpID(tabledata.get("UserName"));
			Thread.sleep(2000);
			createTask.click_Ok_btn();

			logger.log(LogStatus.INFO, "Enterd EmpID and clicked on OK button successfully ");
			Thread.sleep(2000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);

			Thread.sleep(9000);

			// Verify that there is a new record on the master task List page (
			// verify the Title)
			
			
			
			
			

			List<WebElement> listTaskname = driver
					.findElements(By.xpath("//table[@id='taskMasterList']/tbody/tr/td[4]"));
			int size=listTaskname.size();
         
			if (listTaskname.get(size-1).getText().contains(TaskName)) {
				logger.log(LogStatus.PASS, "New Record is   created in the Master Task List page and Task Name is : " + TaskName);
				Thread.sleep(2000);
			}
			
			else{
				logger.log(LogStatus.FAIL, "New Record is not  created in the Master Task List page");
				
			}
			
			
		
			
			js.executeScript("window.scrollBy(0,2000)", "");
			Thread.sleep(4000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);

			
			
			
			
			
			
			
			

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
