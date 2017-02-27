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
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.TaskManagement.TaskStatusPage;
import Scenarios.Browser_Factory;

public class TaskType2_PM_To_InComplete_The_Task extends Browser_Factory {
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");
	WebDriverWait wait;
	String siteid_tobeset;
	String AccountNumber;
	//String taskid;

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"TaskManagement","TaskManagement", "TaskType2_PM_To_InComplete_The_Task");
	}

	@Test(dataProvider="getLoginData")
	public void TaskType2_PM_To_InComplete_The_Task(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("TaskManagement").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "TaskType2_PM_To_InComplete_The_Task is Skipped");
			throw new SkipException("Skipping the test ");
		}


		try{
			logger=extent.startTest("TaskType2_PM_To_InComplete_The_Task", "Task Type 2 - PM to Incomplete the task");
			testcaseName = tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);
			
			wait = new WebDriverWait(driver, 60);
			
			String sqlQuery = "select top 1 s.siteid,s.sitenumber,ti.taskid, ti.content,t.* from Taskmaster TM with(nolock) "+
					"join groupsite gs on gs.groupid=tm.propertygroupid "+
					"join site s on s.siteid=gs.siteid "+
					"join task t on t.locationcode=s.sitenumber "+
					"join taskitem ti on ti.taskid=t.id "+
					"where t.tasktypeid=6878 -- simple task "+
					"and t.createdt <getutcdate() and t.completiondt is null ";


			try { 

				ArrayList<String> results = DataBase_JDBC.executeSQLQuery_List(sqlQuery);
				System.out.println(results);

				siteid_tobeset = results.get(0);
				//logger.log(LogStatus.INFO, "Site id related to account & to be set is:  " + siteid_tobeset);
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
			Thread.sleep(12000);
			Thread.sleep(2000);
			driver.get(driver.getCurrentUrl());
			Thread.sleep(2000);
			driver.get(driver.getCurrentUrl());
			Thread.sleep(5000);	
			
			// Login To the Application
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			Thread.sleep(10000);
			
			// ================= Handling customer facing device ==========================
			
			Thread.sleep(5000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
			String biforstNum = Bifrostpop.getBiforstNo();
			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			Thread.sleep(3000);
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			Reporter.log(tabs.size() + "", true);
			driver.switchTo().window(tabs.get(1));
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_AUT"));
			List<WebElement> biforstSystem = driver.findElements(
					By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
			for (WebElement ele : biforstSystem) {
				if (biforstNum.equalsIgnoreCase(ele.getText().trim())) {
					Reporter.log(ele.getText() + "", true);
					ele.click();
					break;
				}
			}
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);	
			
		
			
			//================================== PM Dashboard =====================================================
			
			String query ="select top 1 t.title from Taskmaster TM with(nolock) "
					+ "join groupsite gs on gs.groupid=tm.propertygroupid " + "join site s on s.siteid=gs.siteid "
					+ "join task t on t.locationcode=s.sitenumber " + "join taskitem ti on ti.taskid=t.id "
					+ "where t.tasktypeid=6878 " + "and t.createdt <getutcdate() and t.completiondt is null";
			
			/*ArrayList<String> res = DataBase_JDBC.executeSQLQuery_List(query);
			String Tasknamefromdb = res.get(5);*/
			
			String Tasknamefromdb = DataBase_JDBC.executeSQLQuery(query);
			
			Thread.sleep(2000);
			
			Thread.sleep(4000);
			PM_Homepage homepage=new PM_Homepage(driver);
			if(homepage.isexistingCustomerModelDisplayed()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "PM dashboard is displayed Successfully");
				logger.log(LogStatus.INFO, "img",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "PM dashboard is not displayed");
				logger.log(LogStatus.INFO, "img",image);

			}
			
			Thread.sleep(3000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(4000);
			

		   List<WebElement> listTask = driver.findElements(By.xpath("//div[@id='task-grid']//div//table//tbody//tr//td/a"));
            int size=listTask.size();
            for(int i=0;i<size;i++){
	            if(listTask.get(i).getText().equals(Tasknamefromdb)){
	                        driver.findElement(By.xpath("//div[@id='task-grid']//div//table//tbody//tr//td/a[text()='"+Tasknamefromdb+"']")).click();
	                        break;
	            }
	            if(i%2==0){
	                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",listTask.get(i));
	            }
            }

			
			Thread.sleep(15000);
			logger.log(LogStatus.PASS, "Clicked on Task list link successfully");
			
			String url = driver.getCurrentUrl();
			String arr[] = url.split("/");
			String taskid=arr[5];
			logger.log(LogStatus.INFO,"Displayed task id in url: " +taskid);
			Thread.sleep(3000);
			
			String viewtaskTitle = driver.findElement(By.xpath("//div[@id='viewTaskPage']//h3")).getText();
			logger.log(LogStatus.INFO, "Displayed Title in view Task page: "+viewtaskTitle);
			Thread.sleep(2000);
		
			
			//Creating object for task status page
			TaskStatusPage taskpage = new TaskStatusPage(driver);
			
			taskpage.clk_TaskCheckbox(driver);
			logger.log(LogStatus.PASS, "Clicked on Task Checkbox ");
			Thread.sleep(4000);
			
			taskpage.clk_IncompleteRadioBtn();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Selected InComplete Radio button");
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(4000);
			taskpage.clk_SubmitBtn();
			logger.log(LogStatus.PASS, "Clicked on Submit button Successfully");
			Thread.sleep(4000);
			if(taskpage.verify_ErrorMessage()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Error message is displayed successfully");
				logger.log(LogStatus.INFO, "Error message is displayed successfully",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Error message is not displayed");
				logger.log(LogStatus.INFO, "Error message is not displayed",image);

			}
			
			taskpage.enter_Comment(tabledata.get("Comment"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Comment in Text box: "+tabledata.get("Comment"));
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
			String scpath2=Generic_Class.takeScreenShotPath();
			String image2=logger.addScreenCapture(scpath2);
			logger.log(LogStatus.INFO, "img",image2);
			Thread.sleep(2000);
			taskpage.clk_ConfirmBtn();
			logger.log(LogStatus.PASS, "Clicked on Confirm Button suceessfully");
			Thread.sleep(15000);
			
			
			//==================================Back to PM Dashboard ============================================
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(4000);
			
			homepage.sel_ClosedOption();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Selected Closed Option in Task list");
			Thread.sleep(2000);
			
			
			List<WebElement> listTaskTitle = driver.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[4]/a"));
			int size1 = listTaskTitle.size();

			Thread.sleep(3000);

			Thread.sleep(3000);

			for (int i = size1; i > 0; i--) {

				if (listTaskTitle.get(i-1).getText().trim().equalsIgnoreCase(Tasknamefromdb.trim())) {

					List<WebElement> listTaskStatus = driver
							.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[2]"));
					int statusSize = listTaskStatus.size();

					for (int j = 0; j < statusSize; j++) {

						if (listTaskStatus.get(j).getText().equals("Completed Today")) {
							logger.log(LogStatus.PASS, "The task  has status of Completed Today ");
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
									listTaskTitle.get(i-1));
							Thread.sleep(4000);

							String scpath = Generic_Class.takeScreenShotPath();
							String image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.PASS, "img", image);

							Thread.sleep(5000);

							listTaskTitle.get(i-1).click();
							break;

						}
					}

					break;

				}

				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", listTaskTitle.get(i-1));

			}
			
			
			String incompleteTxt = driver.findElement(By.xpath("//form[@id='viewTaskForm']//div[@class='task-status-section']/span[contains(text(),'Incomplete')]")).getText();
			
			if(incompleteTxt.trim().equalsIgnoreCase("Incomplete")){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Task Status is displayed as Incomplete");
				logger.log(LogStatus.INFO, "Task Status is displayed as Incomplete",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Task Status is not displayed as Incomplete");
				logger.log(LogStatus.INFO, "Task Status is not displayed as Incomplete",image);

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
			
			if(CurrentDate.trim().equals(completionDt.trim()) ){
				logger.log(LogStatus.PASS, "Current date: " +CurrentDate+ " is matched with database. Date: "+completionDt );
			}else{
				logger.log(LogStatus.FAIL, "Current date: " +CurrentDate+ " is not matched with database. Date: "+completionDt);
			}
			Thread.sleep(2000);
		
	
			
		}
		catch(Exception ex){
			resultFlag="fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Failed due to exception",image);
			ex.printStackTrace();

		}

	}

	@AfterMethod
	public void afterMethod(){

		if(resultFlag.equals("pass")){

			Excel.setCellValBasedOnTcname(path, "TaskManagement","TaskType2_PM_To_InComplete_The_Task" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "TaskManagement","TaskType2_PM_To_InComplete_The_Task" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "TaskManagement","TaskType2_PM_To_InComplete_The_Task" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}




}
