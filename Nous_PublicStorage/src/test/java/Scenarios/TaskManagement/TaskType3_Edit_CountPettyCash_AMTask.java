package Scenarios.TaskManagement;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import Pages.HomePages.LoginPage;
import Pages.TaskManagement.CreateTaskPage;
import Pages.TaskManagement.TaskStatusPage;
import Pages.TaskManagement.ViewTaskMastersPage;
import Scenarios.Browser_Factory;

public class TaskType3_Edit_CountPettyCash_AMTask extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag = "pass";
	String path = Generic_Class.getPropertyValue("Excelpath");
	WebDriverWait wait;
	String siteid_tobeset;
	String AccountNumber;
	String scpath;
	String image;
	

	@DataProvider
	public Object[][] getData() {
		return Excel.getCellValue_inlist(path, "TaskManagement", "TaskManagement", "TaskType3_Edit_CountPettyCash_AMTask");
	}

	@Test(dataProvider = "getData")
	public void emailCompose(Hashtable<String, String> tabledata) throws Exception {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("TaskManagement").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}

		try {
			
			/*String sqlQuery = "select top 1 s.siteid,s.sitenumber,t.* from Taskmaster TM with(nolock)join groupsite gs with(nolock) on gs.groupid=tm.propertygroupid join site s with(nolock) on s.siteid=gs.siteid join task t with(nolock) on t.locationcode=s.sitenumber left join taskitem ti with(nolock) on ti.taskid=t.id and ti.taskid is null and t.createdt <getutcdate() and t.completiondt is null and t.duedt > getutcdate()";
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
			
			*/
			
			
			
			
			// Login to PS Application as DM
			logger = extent.startTest(this.getClass().getSimpleName(), "TaskType3_Edit_CountPettyCash_AMTask");
			LoginPage login = new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as DM");
			String scpath, image;

		/*	((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(4000);	
			
			driver.findElement(By.xpath("//a[@id='continueLink']")).click();
			
			Thread.sleep(15000);*/
			
			


			
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			//driver.navigate().to(tabledata.get("View_TaskMasters"));
			
			
			driver.navigate().to("http://wc2aut.ps.com/TaskManagement/ViewTaskMasters");
			logger.log(LogStatus.INFO, "Navigated to the View Task Masters Screen");
			Thread.sleep(9000);

			scpath = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath, true);
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);

			
			
			//======================Click on Edit Link on the record with title Count Petty Cash Morning========================
			
			Thread.sleep(9000);
			
			
			Actions act1=new Actions(driver);
			
			List<WebElement> taskNames1=driver.findElements(By.xpath("//table[@id='taskMasterList']/tbody/tr/td[4]"));
			
			
			for(WebElement elem:taskNames1)
			{
				act1=new Actions(driver);
				act1.moveToElement(elem).build().perform();
				
			 if(elem.getText().equals("Count Petty Cash - Morning"))
			 {
					js.executeScript("window.scrollBy(0,200)", "");
					Thread.sleep(4000);
				 
				 WebElement taskList1 = driver.findElement(By.xpath("//table[@id='taskMasterList']/tbody//tr//td[text()='Count Petty Cash - Morning']/following-sibling::td/a[text()='Edit']"));
				act1.moveToElement(taskList1).click().build().perform();
				break;			
			 }
				
				
			
			
			}
			
			TaskStatusPage taskpage = new TaskStatusPage(driver);
			if(taskpage.isTaskPageTitleDisplayed()){
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit Simple Task page displayed");
				logger.log(LogStatus.PASS, "img", image);

			}
			
			
			String timeToCompleteBeforeEdit = driver.findElement(By.xpath("//input[@id='TimeToComplete']")).getAttribute("value");
			
			logger.log(LogStatus.INFO, "Time To Complete Before Edit is :" + timeToCompleteBeforeEdit);
			
			
			
          /* boolean isSelectedSuspendedRadioBtn = driver.findElement(By.xpath("//span[text()='Suspend']/preceding-sibling::span")).isSelected();
			if(isSelectedSuspendedRadioBtn==false){
			logger.log(LogStatus.INFO, " Before Edit Suspended Radio button not selected ");
			
			}
			Thread.sleep(5000);
			
			//Start Editing fields
			
			 driver.findElement(By.xpath("//span[text()='Suspend']/preceding-sibling::span")).click();
			
			 logger.log(LogStatus.INFO, " After Editing Suspended Radio button selected ");
			 */
			 
			 driver.findElement(By.xpath("//input[@id='TimeToComplete']")).clear();
			 Thread.sleep(2000);
			 driver.findElement(By.xpath("//input[@id='TimeToComplete']")).sendKeys("2");
			logger.log(LogStatus.PASS, "Edited Time to complete as : 2 ");
			
				
			 
			 
			 
			 
			 
			 
		 
			String startTimeBeforeEdit = driver.findElement(By.xpath("//input[@id='StartTimeHh']")).getAttribute("value");
			
			
			CreateTaskPage createTask = new CreateTaskPage(driver);
			createTask.enterStartTime("11", "00");
			logger.log(LogStatus.PASS, "Edit the start time as 11 ");
			Thread.sleep(5000);
		
			
		
			js.executeScript("window.scrollBy(0,2000)", "");
			Thread.sleep(4000);
			js.executeScript("window.scrollBy(2000,0)", "");
			Thread.sleep(4000);
			createTask.click_submitBtn();
			logger.log(LogStatus.PASS, "Clicked on Submit button ");
			
			createTask.enter_EmpID(tabledata.get("UserName"));
			Thread.sleep(2000);
			createTask.click_Ok_btn();

			logger.log(LogStatus.INFO, "Enterd EmpID and clicked on OK button successfully ");
			Thread.sleep(2000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);

			Thread.sleep(9000);
	
			
			Actions act11=new Actions(driver);
			
			List<WebElement> taskNames11=driver.findElements(By.xpath("//table[@id='taskMasterList']/tbody/tr/td[4]"));
			
			
			for(WebElement elem:taskNames11)
			{
				act11=new Actions(driver);
				act11.moveToElement(elem).build().perform();
				
			 if(elem.getText().equals("Count Petty Cash - Morning"))
			 {
					js.executeScript("window.scrollBy(0,200)", "");
					Thread.sleep(4000);
				 
				 WebElement taskList1 = driver.findElement(By.xpath("//table[@id='taskMasterList']/tbody//tr//td[text()='Count Petty Cash - Morning']/following-sibling::td/a[text()='Edit']"));
				act11.moveToElement(taskList1).click().build().perform();
				break;			
			 }
				
				
			
			
			}
			
			
			Thread.sleep(8000);
			
			String startTimeAfterEdit = driver.findElement(By.xpath("//input[@id='StartTimeHh']")).getAttribute("value");
			if(startTimeBeforeEdit.equals(startTimeAfterEdit)){
				logger.log(LogStatus.FAIL, "Start Time is not edited");
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "img", image);

			}
			
			else{
				logger.log(LogStatus.PASS, "Start Time is  edited : Before edit Start time is :" + startTimeBeforeEdit +"After Edit Start time is : " +startTimeAfterEdit );
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "img", image);

			}
			
			
			
			String timeToCompleteAfterEdit = driver.findElement(By.xpath("//input[@id='TimeToComplete']")).getAttribute("value");
			logger.log(LogStatus.INFO, "Time To Complete Before Edit is :" + timeToCompleteBeforeEdit + "Time To Complete After Edit is : " + timeToCompleteAfterEdit);
			
			
			
			//Code for Revert Back
			
			
			createTask.enterStartTime("09", "00");
		
			Thread.sleep(5000);
		

			driver.findElement(By.xpath("//span[text()='After Open']/preceding-sibling::span")).click();
			
			Thread.sleep(3000);
			driver.findElement(By.xpath("//span[text()='Active']/preceding-sibling::span")).click();
			
			
			
			driver.findElement(By.xpath("//input[@id='TimeToComplete']")).clear();
			driver.findElement(By.xpath("//input[@id='TimeToComplete']")).sendKeys("1");
			
			
			
			
			
		
			//span[text()='After Open']/preceding-sibling::span
			Thread.sleep(5000);
		
			js.executeScript("window.scrollBy(0,2000)", "");
			Thread.sleep(4000);
			js.executeScript("window.scrollBy(2000,0)", "");
			Thread.sleep(4000);
			createTask.click_submitBtn();
		
			Thread.sleep(5000);
			createTask.enter_EmpID(tabledata.get("UserName"));
			Thread.sleep(9000);
		
			
			
			
			

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
