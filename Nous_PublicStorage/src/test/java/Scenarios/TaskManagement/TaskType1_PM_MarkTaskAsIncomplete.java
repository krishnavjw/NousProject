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

public class TaskType1_PM_MarkTaskAsIncomplete extends Browser_Factory {
	

	
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
		return Excel.getCellValue_inlist(path,"TaskManagement","TaskManagement", "TaskType1_PM_MarkTaskAsIncomplete");
	}

	@Test(dataProvider="getLoginData")
	public void TaskType1_PM_MarkTaskAsIncomplete(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("TaskManagement").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "TaskType1_PM_MarkTaskAsIncomplete is Skipped");
			throw new SkipException("Skipping the test ");
		}


		try{
			logger=extent.startTest("TaskType1_PM_MarkTaskAsIncomplete", "Task Type 1 - PM Mark Task as Incomplete");
			testcaseName = tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);
			
			wait = new WebDriverWait(driver, 60);
			

			
			String sqlQuery ="select top 1 s.siteid, s.sitenumber,t.* from Taskmaster TM with(nolock) "
		            + "join groupsite gs on gs.groupid=tm.propertygroupid "
		            + "join site s on s.siteid=gs.siteid "
		            + "join task t on t.locationcode=s.sitenumber "
		            + "left join taskitem ti on ti.taskid=t.id "
		            + "where t.tasktypeid=6878 "
		            + "and ti.taskid is null "
		            + "--and s.sitenumber='20630' "
		            + "and t.createdt <getutcdate() and t.completiondt is null";

					try { 

						ArrayList<String> results = DataBase_JDBC.executeSQLQuery_List(sqlQuery);
						System.out.println(results);

						siteid_tobeset = results.get(0);
						
						String TaskTitle = results.get(3);
						
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
					
		//========================== Login To the Application==========================
					
					JavascriptExecutor jse = (JavascriptExecutor)driver;
					LoginPage login= new LoginPage(driver);
					login.login(tabledata.get("UserName"), tabledata.get("Password"));
					Thread.sleep(10000);
					
				/*	((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
					Thread.sleep(4000);	
					
					driver.findElement(By.xpath("//a[@id='continueLink']")).click();
				    Thread.sleep(7000);*/
					
		// ================= Handling customer facing device ==========================
					
				    Thread.sleep(5000);
					Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
					logger.log(LogStatus.INFO, "PopUp window object is created successfully");
					String biforstNum = Bifrostpop.getBiforstNo();
					Robot robot=new Robot();
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_T);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyRelease(KeyEvent.VK_T);
					Thread.sleep(10000);			
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
					Thread.sleep(15000);
					driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
					Thread.sleep(5000);
					
					
		//================================== PM Dashboard =====================================================
					
					ArrayList<String> results = DataBase_JDBC.executeSQLQuery_List(sqlQuery);
					System.out.println(results);

					String TaskTitle = results.get(3);
					
				
			Thread.sleep(4000);
			PM_Homepage homepage=new PM_Homepage(driver);
			if(homepage.isexistingCustomerModelDisplayed()){
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
			
			
             Actions act=new Actions(driver);
			
			
			List<WebElement> taskList = driver.findElements(By.xpath("//div[@id='task-grid']//div//table//tbody//tr//td/a"));
			act=new Actions(driver);
			for(WebElement ele:taskList)
			{
				act.moveToElement(ele).build().perform();
				System.out.println(ele.getText());
				if(ele.getText().trim().contains(TaskTitle))
				{
					act.moveToElement(ele).click().build().perform();
					break;
				}
				
				
			}
			
			
			Thread.sleep(3000);
			
			
			
			
			Thread.sleep(15000);
			logger.log(LogStatus.PASS, "Clicked on Task list link successfully .Task Name is : " + TaskTitle );
			
			
			
			
			String url = driver.getCurrentUrl();
			String arr[] = url.split("/");
			String taskid=arr[5];
			logger.log(LogStatus.INFO,"Displayed task id in url: " +taskid);
			Thread.sleep(3000);
			
			//Creating object 
			TaskStatusPage taskpage = new TaskStatusPage(driver);
			
			 String TaskDueTime=driver.findElement(By.xpath("//span[contains(text(),'Task Due:')]/following-sibling::span")).getText();
			 
			   String[] arr1 = TaskDueTime.split("/2017");
			   TaskDueTime = arr1[0];
			   Reporter.log(TaskDueTime,true);
			 
			 
			 
			 Thread.sleep(2000);
			
			
			//===============Select incomplete , don’t enter commnets and click on Submit========================
			
			
			taskpage.clk_IncompleteRadioBtn();
			
			logger.log(LogStatus.PASS, "Selected InComplete Radio button");
			Thread.sleep(2000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(5000);
			taskpage.clk_SubmitBtn();
			logger.log(LogStatus.PASS, "Clicked on Submit button Successfully");
			Thread.sleep(5000);
			String ErrorMessage = driver.findElement(By.xpath("//span[text()='Please enter a comment']")).getText();
			if(driver.findElement(By.xpath("//span[text()='Please enter a comment']")).isDisplayed()){
				logger.log(LogStatus.INFO, "Error message displayed successfully as : " + ErrorMessage );
			}
			
			 String scpath = Generic_Class.takeScreenShotPath();
			 String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "img", image);

			
			//===============Select incomplete ,  enter commnets and click on Submit========================
			taskpage.enter_Comment("Task is Incompleted");
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "Entered Comment in Text box: "+" Task is Incompleted");
			Thread.sleep(8000);
		
		
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
			Thread.sleep(15000);
			logger.log(LogStatus.INFO, "Selected Closed Option in Task list");
			Thread.sleep(2000);
	
			
						 
			 Actions act1=new Actions(driver);
				
				List<WebElement> dueDateList=driver.findElements(By.xpath("//div[@id='task-grid']//table/tbody/tr/td[6]"));
				  boolean flag=false;
				
				for(WebElement elem:dueDateList)
				{
					act1=new Actions(driver);
					act1.moveToElement(elem).build().perform();
					
				 if(elem.getText().contains(TaskDueTime))
				 {
					 ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,200)", "");
						Thread.sleep(4000);
					 
					 WebElement taskList1 = driver.findElement(By.xpath("//tr[td[contains(text(),'"+TaskDueTime+"')]]/td[4]/a"));
					 act1.moveToElement(taskList1).click().build().perform();
					 flag=true;
					break;			
				 }
				 
				}
				 
					
				
			/*	if(flag=true) {
					String Status = driver.findElement(By.xpath("//form[@id='viewTaskForm']//span[contains(text(),'Task Status:')]//following-sibling::span")).getText();
					Thread.sleep(5000);
					logger.log(LogStatus.INFO, "The task is completed and the status is :" + Status);
					Thread.sleep(9000);
					 scpath = Generic_Class.takeScreenShotPath();
					 image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "img", image);
				}
				
			
				else{

					logger.log(LogStatus.INFO, "The task is not completed");
					 scpath = Generic_Class.takeScreenShotPath();
					 image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "img", image);
				}*/
				
				
			 
			 
		
			
			String qry1 = "select FORMAT(completionDT,'yyyy-MM-dd'),TaskStatusId from task where id='"+taskid+"' ";
			ArrayList<String> list = DataBase_JDBC.executeSQLQuery_List(qry1);
			
			String completionDt = list.get(0);
			String taskstatusid = list.get(1);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String CurrentDate = sdf.format(date);
			Reporter.log("Current Date : "+CurrentDate,true);
			

			
			if(CurrentDate.trim().equals(completionDt.trim()) & taskstatusid.equals("3") ){
				logger.log(LogStatus.PASS, "Current date: " +CurrentDate+  " are matched with database. Date: "+completionDt + "and task status id: " + taskstatusid);
			}else{
				logger.log(LogStatus.FAIL, "Current date: " +CurrentDate+ "and task status id: " +taskstatusid+ " are not matched with database. Date: "+completionDt);
			}
			Thread.sleep(2000);
			
			
	
			

		}

		catch (Exception ex) {
			ex.printStackTrace();
			resultFlag = "fail";
			
			String scpath = Generic_Class.takeScreenShotPath();
			 String image = logger.addScreenCapture(scpath);
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
