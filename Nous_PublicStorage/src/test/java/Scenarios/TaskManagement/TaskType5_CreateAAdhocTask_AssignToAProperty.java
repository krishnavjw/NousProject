package Scenarios.TaskManagement;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.TaskManagement.CreateTaskPage;
import Pages.TaskManagement.TaskStatusPage;
import Pages.TaskManagement.ViewTaskMastersPage;
import Scenarios.Browser_Factory;

public class TaskType5_CreateAAdhocTask_AssignToAProperty extends Browser_Factory {
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");


	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"TaskManagement","TaskManagement", "TaskType5_CreateAAdhocTask_AssignToAProperty");
	}

	@Test(dataProvider="getLoginData")
	public void TaskType5_CreateAAdhocTask_AssignToAProperty(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("TaskManagement").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("TaskType5_CreateAAdhocTask_AssignToAProperty","Task Type 5 - Create a Adhoc Task Assign To a Property");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			
			String sqlQuery = "select siteid from site where sitenumber='"+tabledata.get("SiteNumber")+"'";
			String siteid_tobeset = DataBase_JDBC.executeSQLQuery(sqlQuery);
			Thread.sleep(3000);
			
			
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

			
			
			
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);
			
			
			//=================Handling Customer Facing Device================================
			
			Thread.sleep(5000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
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
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
			
			
			Thread.sleep(5000);
			driver.findElement(By.id("continueLink")).click();
			Thread.sleep(10000);
					
			//================================== DM Home Page ===============================================
			
			
			
			Thread.sleep(4000);
			DM_HomePage homepage = new DM_HomePage(driver);
			if(homepage.is_DMDashBoardTitle()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "DM dashboard is displayed Successfully");
				logger.log(LogStatus.INFO, "img",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "DM dashboard is not displayed");
				logger.log(LogStatus.INFO, "img",image);

			}
			
			Thread.sleep(3000);
			
			// Navigate to the View Task Masters Screen
			System.out.println(tabledata.get("View_TaskMasters")); 
			driver.navigate().to("http://wc2aut.ps.com/TaskManagement/ViewTaskMasters");
			logger.log(LogStatus.INFO, "Navigated to the Master Task List Page");
			Thread.sleep(5000);
			
			boolean master = driver.findElement(By.xpath("//h3[contains(text(),'Master Task List')]")).isDisplayed();
			if(master){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Master Task List Page is displayed Successfully");
				logger.log(LogStatus.INFO, "img",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Master Task List Page is not displayed");
				logger.log(LogStatus.INFO, "img",image);

			}
			
			//Click on Create Task button
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,2000)", "");
			Thread.sleep(3000);
			js.executeScript("window.scrollBy(2000,0)", "");
			Thread.sleep(2000);
			
			
			ViewTaskMastersPage viewTaskMasters=new ViewTaskMastersPage(driver);
			viewTaskMasters.click_CreateTask();
			logger.log(LogStatus.PASS, "Clicked on Create Task button successfully");
			
			Thread.sleep(5000);
			
			//Select List Task from drop down and click on create
			
			viewTaskMasters.selectTaskOptionAdhoc(tabledata.get("Task_Type"),driver);
			logger.log(LogStatus.INFO, "Selected task type as Adhoc Task from the drop down menu");
			Thread.sleep(3000);
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "img",image1);
			Thread.sleep(3000);
			viewTaskMasters.clk_CreateBtn();
			Thread.sleep(6000);
			logger.log(LogStatus.PASS, "Clicked on Create button successfully");
			
			// Enter unique title for the task name			
			CreateTaskPage createTask=new CreateTaskPage(driver);
			
			String random = Generic_Class.get_RandmString();
			String TaskName="Task Type5 Adhoc "+random;
			createTask.enterTaskName(TaskName);
			logger.log(LogStatus.INFO, "Entered Task Name in the text field as : " + TaskName);
			
			// Enter Description, Select priority as Medium
			createTask.enterTaskDescription(tabledata.get("Description"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Task Description in the text Area: "+tabledata.get("Description"));
			
			createTask.selectPriorityListAdhoc("Medium Priority Task");
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Selected Medium Priority Task from the drop down field Priority");
			createTask.enterTimetoComplete("1");
			logger.log(LogStatus.INFO, "Entered Time to complete as 1 Day");
			Thread.sleep(2000);
			createTask.clk_DaysRadioBtn();
			
			//Selecting site and assign role
			createTask.selectPropertyGroupAdhoc("Site");
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Selected Site from property Group dropdown");
			
			createTask.clk_RoleRadioBtn();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Selected Role Radio Button in Assign To Successfully");
			createTask.selectAssignToValueAdhoc("Property Manager");
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Selected Propery Manager in Assign To dropdown");
			createTask.enterGroupid(tabledata.get("SiteNumber"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Sitenumber in Group Text box");
			String scpath3=Generic_Class.takeScreenShotPath();
			String image3=logger.addScreenCapture(scpath3);
			logger.log(LogStatus.INFO, "img",image3);
			Thread.sleep(2000);
			
			js.executeScript("window.scrollBy(2000,0)", "");
			Thread.sleep(2000);
			createTask.clickCreateButton(driver);
			logger.log(LogStatus.PASS, "Clicked on create button successfully");

			Thread.sleep(7000);

			createTask.enter_EmpID(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered username in the Employee id");
			Thread.sleep(2000);
			String ss = Generic_Class.takeScreenShotPath();
			String mm = logger.addScreenCapture(ss);
			logger.log(LogStatus.PASS, "img", mm);
			createTask.click_Ok_btn();
			logger.log(LogStatus.INFO, "Clicked on OK button successfully");
			Thread.sleep(2000);
			
			Thread.sleep(9000);
			
			String Qry = "select FORMAT(CreateDt,'yyyy-MM-dd'), FORMAT(DueDt,'yyyy-MM-dd') from task where tasktypeid=6878 and title='"+TaskName+"' and Locationcode='"+tabledata.get("SiteNumber")+"' ";
			ArrayList<String> list = DataBase_JDBC.executeSQLQuery_List(Qry);
			
			String createdt = list.get(0);
			String duedt = list.get(1);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			String CurrentDate = sdf.format(date);
			Reporter.log("Current Date : "+CurrentDate,true);
			
			c.setTime(sdf.parse(CurrentDate));
			c.add(Calendar.DATE, 1);  // number of days to add
			String duedt1 = sdf.format(c.getTime());
			
			
			
			if(CurrentDate.trim().equals(createdt.trim())){
				logger.log(LogStatus.PASS, "Current date: " +CurrentDate+ " is matched with database. Date: "+createdt);
			}else{
				logger.log(LogStatus.FAIL, "Current date: " +CurrentDate+ " is not matched with database. Date: "+createdt);
			}
			Thread.sleep(2000);
		
			if(duedt1.trim().equals(duedt.trim())){
				logger.log(LogStatus.PASS, "Due date: " +duedt1+ " is matched with database. Date: "+duedt);
			}else{
				logger.log(LogStatus.FAIL, "Due date: " +duedt1+ " is not matched with database. Date: "+duedt);
			}
			Thread.sleep(2000);


			jse.executeScript("window.scrollBy(0,-1000)", "");
			
			Thread.sleep(3000);
			homepage.log_off(driver);
			logger.log(LogStatus.INFO, "Clicked on the Logged out link in DM home page");
            Thread.sleep(8000);

			
			driver.navigate().refresh();
			Thread.sleep(5000);
			
			driver.navigate().refresh();
			Thread.sleep(5000);
			
			//===================== PM Dashboard ============================
			login.login(tabledata.get("PMUserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(6000);
			
			
			//=================Handling Customer Facing Device================================
			
		Thread.sleep(5000);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			String biforstNum1 = Bifrostpop.getBiforstNo();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			Thread.sleep(3000);			
			Reporter.log(tabs.size() + "", true);
			driver.switchTo().window(tabs.get(1));
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_AUT"));

			List<WebElement> biforstSystem1 = driver.findElements(
					By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
			for (WebElement ele : biforstSystem1) {
				if (biforstNum1.equalsIgnoreCase(ele.getText().trim())) {
					Reporter.log(ele.getText() + "", true);
					ele.click();
					break;
				}
			}
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
			
			
			Thread.sleep(5000);
			driver.findElement(By.id("continueLink")).click();
			Thread.sleep(10000);

			
			
			Thread.sleep(4000);
			PM_Homepage pm_homepage=new PM_Homepage(driver);
			if(pm_homepage.isexistingCustomerModelDisplayed()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "user navigated to PM dashboard");
				logger.log(LogStatus.INFO, "img",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "user navigated not to PM dashboard");
				logger.log(LogStatus.INFO, "img",image);

			}
			
			Thread.sleep(3000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(4000);
			
			
            List<WebElement> listTask = driver.findElements(By.xpath("//div[@id='task-grid']//div//table//tbody//tr//td/a"));
            int size=listTask.size();
            for(int i=0;i<size;i++){
	            if(listTask.get(i).getText().equals(TaskName)){
	                        driver.findElement(By.xpath("//div[@id='task-grid']//div//table//tbody//tr//td/a[text()='"+TaskName+"']")).click();
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
		
			
			//Creating object for Task Status page
			TaskStatusPage taskpage = new TaskStatusPage(driver);
			taskpage.clk_CompleteRadioBtn();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Selected Complete Radio button");
			taskpage.enter_Comment(tabledata.get("Comment"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Comment in Text box: "+tabledata.get("Comment"));
			Thread.sleep(8000);
			String spp=Generic_Class.takeScreenShotPath();
			String imm=logger.addScreenCapture(spp);
			logger.log(LogStatus.INFO, "img",imm);
			Thread.sleep(2000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(4000);
			taskpage.clk_SubmitBtn();
			logger.log(LogStatus.PASS, "Clicked on Submit button Successfully");
			Thread.sleep(4000);
			taskpage.enter_EmployeeNumber(tabledata.get("PMUserName"));
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
			
			pm_homepage.sel_ClosedOption();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Selected Closed Option in Task list");
			Thread.sleep(2000);
	
			
			List<WebElement> listTaskTitle = driver.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[4]/a"));
			int size1 = listTaskTitle.size();

			Thread.sleep(3000);

			Thread.sleep(3000);

			for (int i = size1; i > 0; i--) {

				if (listTaskTitle.get(i-1).getText().trim().equalsIgnoreCase(TaskName)) {

					List<WebElement> listTaskStatus = driver
							.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[2]"));
					int statusSize = listTaskStatus.size();

					for (int j = 0; j < statusSize; j++) {

						if (listTaskStatus.get(j).getText().equals("Completed Today")) {
							logger.log(LogStatus.PASS, "The task  has status of Completed Today");
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
			
			
			Thread.sleep(3000);

			String completeTxt = driver.findElement(By.xpath("//form[@id='viewTaskForm']//div[@class='task-status-section']/span[contains(text(),'Complete')]")).getText();
			
			if(completeTxt.trim().equalsIgnoreCase("Complete")){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Task Status is displayed as Complete");
				logger.log(LogStatus.INFO, "Task Status is displayed as Complete",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Task Status is not displayed as Complete");
				logger.log(LogStatus.INFO, "Task Status is not displayed as Complete",image);

			}
			
			
			Thread.sleep(2000);
			String qry1 = "select FORMAT(completionDT,'yyyy-MM-dd'),TaskStatusId from task where id='"+taskid+"' ";
			ArrayList<String> list1 = DataBase_JDBC.executeSQLQuery_List(qry1);
			
			String completionDt = list1.get(0);
			String taskstatusid = list1.get(1);
			
			if(CurrentDate.trim().equals(completionDt.trim()) & taskstatusid.equals("4") ){
				logger.log(LogStatus.PASS, "Current date: " +CurrentDate+ "and task status id: " +taskstatusid+ " are matched with database. Date: "+completionDt+ " and task status id is 4");
			}else{
				logger.log(LogStatus.FAIL, "Current date: " +CurrentDate+ "and task status id: " +taskstatusid+ " are not matched with database. Date: "+completionDt+ " and task status id is 4");
			}
			Thread.sleep(2000);
		

			
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+e);
		}

	}

	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "TaskManagement","TaskType5_CreateAAdhocTask_AssignToAProperty" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "TaskManagement","TaskType5_CreateAAdhocTask_AssignToAProperty" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "TaskManagement","TaskType5_CreateAAdhocTask_AssignToAProperty" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}

}

		