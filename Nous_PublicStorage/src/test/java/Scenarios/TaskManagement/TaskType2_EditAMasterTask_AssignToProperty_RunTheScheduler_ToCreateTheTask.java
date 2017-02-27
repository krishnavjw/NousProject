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
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.jmx.snmp.tasks.Task;

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.TaskManagement.CreateTaskPage;
import Scenarios.Browser_Factory;

public class TaskType2_EditAMasterTask_AssignToProperty_RunTheScheduler_ToCreateTheTask extends Browser_Factory {
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"TaskManagement","TaskManagement", "TaskType2_EditAMasterTask_AssignToProperty_RunTheScheduler_ToCreateTheTask");
	}

	@Test(dataProvider="getLoginData")
	public void TaskType2_EditAMasterTask_AssignToProperty_RunTheScheduler_ToCreateTheTask(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("TaskManagement").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("TaskType2_EditAMasterTask_AssignToProperty_RunTheScheduler_ToCreateTheTask","Task Type 2- Edit A Master Task Assign To The Property And Run The Schedular To Create the Task");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);
			
			
			//=================Handling Customer Facing Device================================
			
		/*	Thread.sleep(5000);
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
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath"));

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
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
			*/
			
			Thread.sleep(5000);
			driver.findElement(By.id("continueLink")).click();
			Thread.sleep(10000);
		
					
			//================================== PM Home Page ===============================================
			
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
			driver.navigate().to("http://wc2qa.ps.com/TaskManagement/ViewTaskMasters");
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
			

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			List<WebElement> listTaskname = driver.findElements(By.xpath("//table[@id='taskMasterList']/tbody/tr/td[4]"));
			int size1=listTaskname.size();
			String TaskName = listTaskname.get(size1-1).getText();
			Thread.sleep(2000);
			
			List<WebElement> TaskEditlist = driver.findElements(By.xpath("//table[@id='taskMasterList']/tbody/tr/td[9]"));
			int size = TaskEditlist.size();
			TaskEditlist.get(size-1).click();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Edit Button in the Master Task List for the Task name :"+TaskName);
			
			boolean editTitle = driver.findElement(By.xpath("//h3[contains(text(),'Edit List Task')]")).isDisplayed();
			if(editTitle){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit List Task Page is displayed Successfully");
				logger.log(LogStatus.INFO, "img",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Edit List Task Page is not displayed");
				logger.log(LogStatus.INFO, "img",image);
			}
			
			
			CreateTaskPage createTask=new CreateTaskPage(driver);
			// Edit Description, Select priority as High
			createTask.enterTaskDescription(tabledata.get("Description"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Task Description in the text Area: "+tabledata.get("Description"));
			
			createTask.selectHighPriority("High Priority Task");
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Selected High Priority Task from the drop down field Priority");
			
			//Click on add button and enter task
			createTask.clk_AddBtn();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked on Add button");
			createTask.enter_TaskItem3(tabledata.get("TaskItemContent3"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered value in task item content: "+tabledata.get("TaskItemContent3"));
			String scpath2=Generic_Class.takeScreenShotPath();
			String image2=logger.addScreenCapture(scpath2);
			logger.log(LogStatus.INFO, "img",image2);
			
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			createTask.enterTimetoComplete("3");
			logger.log(LogStatus.INFO, "Entered Time to complete as 3 Days");
			Thread.sleep(2000);
			
			driver.findElement(By.xpath("//div[@id='DeliveryOptionsDayCheckboxes']/label[6]/span[1]")).click();
			Thread.sleep(2000);
			
			Thread.sleep(5000);
			String scpath3=Generic_Class.takeScreenShotPath();
			String image3=logger.addScreenCapture(scpath3);
			logger.log(LogStatus.INFO, "img",image3);
			Thread.sleep(2000);

			jse.executeScript("window.scrollBy(2000,0)", "");
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
			

			Thread.sleep(3000);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Calendar ca = Calendar.getInstance();
			Date date = new Date();
			String CurrentDate = sdf.format(date);
			Reporter.log("Current Date : "+CurrentDate,true);
			
			c.setTime(sdf.parse(CurrentDate));
			c.add(Calendar.DATE, 8);  // number of days to add
			String scheduler = sdf.format(c.getTime());
			Reporter.log("Scheduler date : "+scheduler,true);
			
			
			ca.setTime(sdf.parse(CurrentDate));
			ca.add(Calendar.DATE, 3);
			String duedt = sdf.format(ca.getTime());
			
			
			Thread.sleep(6000);
			
			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			Thread.sleep(6000);	
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			Reporter.log(tabs.size() + "", true);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(5000);
			String url = "http://172.18.12.8:9004/api/task/generate/scheduler/run?utcDateTime='"+scheduler+"'T00:00:00.0000000Z&taskType=6878";
			driver.get(url); //http://172.18.12.8:9004/api/task/generate/scheduler/run?utcDateTime=%272017-01-02%27T00:00:00.0000000Z&taskType=6878
			Thread.sleep(40000);
			
	
			String qry1 = 	"select distinct FORMAT(DueDt,'yyyy-MM-dd'),s.sitenumber,ti.taskid, ti.content,t.* from Taskmaster TM with(nolock) "+
							"join groupsite gs on gs.groupid=tm.propertygroupid  "+
							"join site s on s.siteid=gs.siteid "+
							"join task t on t.locationcode=s.sitenumber "+
							"join taskitem ti on ti.taskid=t.id "+
							"where t.tasktypeid=6878 "+
							"and t.title='"+TaskName+"' order by ti.taskid ";
			
			ArrayList<String> result = DataBase_JDBC.executeSQLQuery_List(qry1);
			
			String duedt1 = result.get(0);
			if(duedt == duedt1){
				logger.log(LogStatus.PASS, "Duedate : " +duedt+ "is matched with database" +duedt1);
			}
			else{
				logger.log(LogStatus.FAIL, "Duedate : " +duedt+ "is not matched with database" +duedt1);

			}
			
			String com = result.get(13);
            Thread.sleep(2000);
            String[] DateSplit=com.split("\\s+");
            String dateFromDatabse=DateSplit[0];

            if(CurrentDate == dateFromDatabse){
				logger.log(LogStatus.PASS, "Completion date : " +CurrentDate+ "is matched with database" +dateFromDatabse);
			}
			else{
				logger.log(LogStatus.FAIL, "Completion date : " +CurrentDate+ "is not matched with database" +dateFromDatabse);
			}
			

			
			
			
			
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
			Excel.setCellValBasedOnTcname(path, "TaskManagement","TaskType2_EditAMasterTask_AssignToProperty_RunTheScheduler_ToCreateTheTask" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "TaskManagement","TaskType2_EditAMasterTask_AssignToProperty_RunTheScheduler_ToCreateTheTask" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "TaskManagement","TaskType2_EditAMasterTask_AssignToProperty_RunTheScheduler_ToCreateTheTask" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}

	

}
