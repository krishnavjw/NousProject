package Scenarios.TaskManagement;

import java.text.DateFormat;
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
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.TaskManagement.SpaceStatusPage;
import Pages.TaskManagement.TaskStatusPage;
import Scenarios.Browser_Factory;

public class TaskType3_Customer_Requested_LockCut  extends Browser_Factory{
	
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
		return Excel.getCellValue_inlist(path, "TaskManagement", "TaskManagement", "TaskType3_Customer_Requested_LockCut");
	}

	@Test(dataProvider = "getLoginData")
	public void CustomerRequest(Hashtable<String, String> tabledata) throws Exception {

		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("TaskManagement").equals("Y"))) {
			resultFlag = "skip";
			logger.log(LogStatus.SKIP, "TaskType3_Customer_Requested_LockCut is Skipped");
			throw new SkipException("Skipping the test ");
		}
		try {
			logger = extent.startTest("TaskType3_Customer_Requested_LockCut", "TaskType3_Customer_Requested_LockCut");
			testcaseName = tabledata.get("TestCases");
			Reporter.log("Test case started: " + testcaseName, true);
			
			
			// Login To the Application

						JavascriptExecutor jse = (JavascriptExecutor) driver;
						LoginPage login = new LoginPage(driver);
						login.login(tabledata.get("UserName"), tabledata.get("Password"));
						Thread.sleep(20000);

						
						driver.findElement(By.xpath("//div[@id='header-logo-container']/a/img")).click();
						
						String scpath, image;
						
						Thread.sleep(7000);
						
						PM_Homepage homepage = new PM_Homepage(driver);
						if (homepage.isexistingCustomerModelDisplayed()) {
							 scpath = Generic_Class.takeScreenShotPath();
							 image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.PASS, "User navigated to PM dashboard");
							logger.log(LogStatus.INFO, "img", image);
						} else {

							 scpath = Generic_Class.takeScreenShotPath();
							 image = logger.addScreenCapture(scpath);
							logger.log(LogStatus.FAIL, "user navigated not to PM dashboard");
							logger.log(LogStatus.INFO, "img", image);

						}

						Thread.sleep(7000);
						
						
						((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
						Thread.sleep(7000);
						
			    	//click on property management tab
						
						homepage.clk_PM_PropertyManagement_link();
						Thread.sleep(8000);
						
						scpath = Generic_Class.takeScreenShotPath();
						image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.PASS, "User navigated to property management page");
						logger.log(LogStatus.INFO, "img", image);
						
                   //click on space status
						
						driver.findElement(By.xpath("//a[@class='link-button margin-right'][contains(text(),'Space Status')]")).click();
						Thread.sleep(5000);	
						
						scpath = Generic_Class.takeScreenShotPath();
						image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.PASS, "User navigated to space status page");
						logger.log(LogStatus.INFO, "img", image);
							
				  //click on status filter dropdown
						
						SpaceStatusPage statuspage = new SpaceStatusPage(driver);
						statuspage.clk_statusfilterdropdown();
						
						Thread.sleep(5000);	
						
						scpath = Generic_Class.takeScreenShotPath();
						image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.PASS, "User clicked on status filter dropdown");
						logger.log(LogStatus.INFO, "img", image);
							
                 //select vacant unit from status filter dropdown
						
						List<WebElement> unitList = driver.findElements(By.xpath("//div[13]/div/ul/li"));
	                     Actions act=new Actions(driver);
	                     for(WebElement ele:unitList)
	                     {
	                           act.moveToElement(ele).build().perform();
	                        
	                           if(ele.getText().trim().equalsIgnoreCase("Unit Occupied"))
	                           {
	                                  act.moveToElement(ele).click().build().perform();
	                                  break;
	                           }
	                           
	                           Thread.sleep(2000);
	                     }
	                     Thread.sleep(7000);
						
					//click on view button
	                     
		                    
	                     statuspage.clk_view();
					     Thread.sleep(8000);	
					     
	                     scpath = Generic_Class.takeScreenShotPath();
						 image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.PASS, "User can see all vacant units");
						logger.log(LogStatus.INFO, "img", image);
						
				//click on any space		
						
						ArrayList<WebElement> results = (ArrayList<WebElement>) driver.findElements(By.xpath("//div[@id='space-status-grid']/div[2]/table/tbody/tr/td[1]/div"));
						System.out.println(results);
						WebElement space = results.get(0);
						String spaceno=results.get(0).getText();
						System.out.println(spaceno);
						space.click();
						
						 Thread.sleep(7000);
						
						 scpath = Generic_Class.takeScreenShotPath();
						 image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.PASS, "User click on any space and space no is :" +spaceno );
						logger.log(LogStatus.INFO, "img", image);
						
						
               //click on Lock Status Dropdown and Select Customer Requested Lock Cut
						
						
						
						statuspage.clk_on_LockStatus();
						 Thread.sleep(3000);
						
						 List<WebElement> dropdownList = driver.findElements(By.xpath("//div[22]/div/ul/li"));
	                     Actions action=new Actions(driver);
	                     for(WebElement ele:dropdownList)
	                     {
	                           action.moveToElement(ele).build().perform();
	                        
	                           if(ele.getText().trim().contains(spaceno))
	                           {
	                                  action.moveToElement(ele).click().build().perform();
	                                  break;
	                           }
	                           
	                           Thread.sleep(2000);
	                     }
	                     Thread.sleep(7000);
						 
				
						 
						scpath = Generic_Class.takeScreenShotPath();
						image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.PASS, "User click on any reason");
						logger.log(LogStatus.INFO, "img", image);	
						
						
						
				     //Enter notes, emplyoeeid and click on update
						 
						 statuspage.enter_text("Lock Cut Requested");
						 Thread.sleep(7000);
						 statuspage.enter_emp(tabledata.get("UserName"));
						 Thread.sleep(7000);
						 statuspage.clk_update();
						 Thread.sleep(7000);
						 
						scpath = Generic_Class.takeScreenShotPath();
						image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.PASS, "User click on update");
						logger.log(LogStatus.INFO, "img", image);
						
				        Thread.sleep(10000);
				        
				        
				        
				        
				        jse.executeScript("window.scrollBy(0,-1000)", "");
		    			
		    			Thread.sleep(8000);
		    			homepage.log_off(driver);
		    			logger.log(LogStatus.INFO, "Clicked on the Logged out link in pM home page");
		               Thread.sleep(8000);

		    			
		    			driver.navigate().refresh();
		    			Thread.sleep(5000);
		    			
		    			driver.navigate().refresh();
		    			Thread.sleep(5000);
		    			
		    			//================================== DM login ======================================
		    			
		    			login.login(tabledata.get("DMUserName"), tabledata.get("Password"));
		    			logger.log(LogStatus.INFO, "Click on Login button successfully");
		    			Thread.sleep(6000);
		    			
		    			/*Thread.sleep(5000);
		    			driver.findElement(By.id("continueLink")).click();
		    			Thread.sleep(10000);*/
		    			
		    			 String scpath1 = Generic_Class.takeScreenShotPath();
		    			 String image1 = logger.addScreenCapture(scpath1);
		    			logger.log(LogStatus.INFO, "DM Dashboard is displayed Successfully",image1);
		    	
		    			//Actions act=new Actions(driver);
				        
				        
				        
				        
				        
				        
				        
				        
			           //================Login as DM and Execute query      
				        
				        
						
				      //verify tasklist with the particular space and click on the task
						 
						 
					
	                     
			     /*	//Enter a date, task status completed and click on submit	
			 
	                  statuspage.clk_on_date(); 
	                  Thread.sleep(2000);
	                  
	               
	                   ArrayList<WebElement> datelist = (ArrayList<WebElement>) driver.findElements(By.xpath("//div[@id='dualDatePicker']//table/tbody/tr/td/a"));
	                   WebElement date = datelist.get(0);
					   date.click();
	                   Thread.sleep(2000);
	                   
	                  statuspage.clk_on_completedStatus();
	                  Thread.sleep(2000);
	                  
	                  statuspage.clk_on_submit();
	                  Thread.sleep(5000); 
	                  
	                   scpath = Generic_Class.takeScreenShotPath();
					   image = logger.addScreenCapture(scpath);
					   logger.log(LogStatus.PASS, "User navigate to Employee modal Window");
					   logger.log(LogStatus.INFO, "img", image);  
					   
			          //Enter Employee id and click confirm
			 
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
	                  Thread.sleep(5000);

	                  PM_HomePage.sel_ClosedOption();
	                  Thread.sleep(15000);
	                  logger.log(LogStatus.INFO, "Selected Closed Option in Task list");
	                  Thread.sleep(2000);

	                  List<WebElement> listTaskTitle1 = driver
	                              .findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[4]/a"));
	                  int size1 = listTaskTitle1.size();

	                  Thread.sleep(3000);

	                  for (int i = 0; i < size1; i++) {

	                        if (listTaskTitle1.get(i).getText().contains("Call C. ANONYMOUS about NSF Check")) {

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

	                  Thread.sleep(5000);

	                  scpath = Generic_Class.takeScreenShotPath();
	                  image = logger.addScreenCapture(scpath);
	                  logger.log(LogStatus.PASS, "img", image);

	                  Thread.sleep(4000);
	                  String qry1 = " select FORMAT(completionDT,'yyyy-MM-dd') from task where id='" + taskid + "' ";
	                  String completionDt = DataBase_JDBC.executeSQLQuery(qry1);

	                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                  Date date = new Date();
	                  String CurrentDate = sdf.format(date);
	                  Reporter.log("Current Date : " + CurrentDate, true);

	                  if (CurrentDate.trim().equals(completionDt.trim())) {
	                        logger.log(LogStatus.PASS,
	                                    "Current date " + CurrentDate + " is matched with database date in task table " + completionDt);
	                  } else {
	                        logger.log(LogStatus.FAIL, "Current date " + CurrentDate
	                                    + " is not matched with database date in task table " + completionDt);
	                  }
	                  Thread.sleep(2000);

	                     
	                     
						 
			*/	
		    			
		    			
		    			
		    			
		    			List<WebElement> taskList = driver.findElements(By.xpath("//div[@id='task-grid']//table/tbody/tr/td[4]/a"));
		    			act=new Actions(driver);
		    			for(WebElement ele:taskList)
		    			{
		    				act.moveToElement(ele).build().perform();
		    				System.out.println(ele.getText());
		    				if(ele.getText().trim().contains(spaceno))
		    				{
		    					act.moveToElement(ele).click().build().perform();
		    					break;
		    				}
		    				
		    				Thread.sleep(1000);
		    			}
		    			
		    			
		    			Thread.sleep(3000);	
		    			
		    			
		    			scpath = Generic_Class.takeScreenShotPath();
		    			image = logger.addScreenCapture(scpath);
		    			logger.log(LogStatus.PASS, "Navigated to the Lock Cut screen ");
		    			logger.log(LogStatus.INFO, "img", image);
		    			
		    			Thread.sleep(7000);
		    			
		    			String url=driver.getCurrentUrl();
		    			String taskid="";
		    			String arr[]=url.split("ViewTask/");
		    			//arr = arr[1].split("&");
		    			taskid=arr[1];
		    			
		    			
		    			logger.log(LogStatus.INFO, "Selected Task ID is " + taskid);
		    			
		    			
		    			
		    			
		    			
		    	       //Enter a date, select task status as completed and click on submit
		    			
		    			 TaskStatusPage taskpage = new TaskStatusPage(driver);

		    			

		    			taskpage.SelectDateFromCalendar_LockCut("0", driver);
		    			
		    			
		    			
		    			String dateSelecetd = driver.findElement(By.xpath("//div[@id='CustomerRequestedLockCutDate']/span")).getText();
		    			
		    			String[] val = dateSelecetd.split(" ");
		    			String Date = val[1];		
		    			
		    			logger.log(LogStatus.INFO, "Date selected and Date is : " + Date);
		    			
		    			
		    			
		    			
		    			
		    			
		    			
		    			

		    			Thread.sleep(3000);
		    			taskpage.clk_CompleteRadioBtn();
		    			Thread.sleep(2000);
		    			logger.log(LogStatus.INFO, "Selected Complete Radio button");
		    			taskpage.enter_Comment("Task is completed");
		    			Thread.sleep(2000);
		    			logger.log(LogStatus.INFO, "Entered Comment in Text box: " + " Task is completed");
		    			Thread.sleep(8000);
		    			String scpath11 = Generic_Class.takeScreenShotPath();
		    			String image11 = logger.addScreenCapture(scpath11);
		    			logger.log(LogStatus.INFO, "img", image11);
		    			Thread.sleep(2000);
		    			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
		    			Thread.sleep(4000);

		    			scpath = Generic_Class.takeScreenShotPath();
		    			image = logger.addScreenCapture(scpath);
		    			logger.log(LogStatus.PASS, "img", image);

		    			taskpage.clk_SubmitBtn();
		    			logger.log(LogStatus.PASS, "Clicked on Submit button Successfully");
		    			Thread.sleep(4000);
		    			taskpage.enter_EmployeeNumber(tabledata.get("DMUserName"));
		    			logger.log(LogStatus.INFO, "Entered username");
		    			Thread.sleep(4000);
		    			String scpath2 = Generic_Class.takeScreenShotPath();

		    			taskpage.clk_ConfirmBtn();
		    			logger.log(LogStatus.PASS, "Clicked on Confirm Button suceessfully");
		    			Thread.sleep(15000);

		    			// ==================================Back to PM Dashboard
		    			// ============================================

		    			/*((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
		    			Thread.sleep(9000);

		    			scpath = Generic_Class.takeScreenShotPath();
		    			image = logger.addScreenCapture(scpath);
		    			logger.log(LogStatus.PASS, "img", image);
		    			logger.log(LogStatus.PASS, "User Navigated to PM Dashboard");
		    			Thread.sleep(8000);

		    			homepage.sel_ClosedOption();
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
		    			
		    			Thread.sleep(8000);
		    			if (accountNoinTaskPage.contains(AccountNumber)) {
		    				logger.log(LogStatus.PASS, "Account No matched for customer:  " + AccountNumber);
		    			}

		    			else {
		    				logger.log(LogStatus.FAIL, "Account No not  matched for customer:");
		    			}

		    			
		    			
		    			
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
		    			
		    			
		    			if(removedate.trim() == removedatedisplayed.trim()){
		    				logger.log(LogStatus.PASS, "Displayed Date "+removedatedisplayed+ "is matched with selected date: "+removedate);
		    			}else{
		    				logger.log(LogStatus.FAIL, "Displayed Date "+removedatedisplayed+ "is not matched with selected date: "+removedate);

		    			}
		    			
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
		    			
		    			
		    			
		    			*/
		    			
		    			
		    			
		    			
						
						
						
						
						
						
						
						
						

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