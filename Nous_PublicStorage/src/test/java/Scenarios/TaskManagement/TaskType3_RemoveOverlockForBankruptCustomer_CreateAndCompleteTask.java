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
import Pages.CustInfoPages.Cust_OtherStatusesPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.TaskManagement.TaskStatusPage;
import Scenarios.Browser_Factory;

public class TaskType3_RemoveOverlockForBankruptCustomer_CreateAndCompleteTask extends Browser_Factory{
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");


	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"TaskManagement","TaskManagement", "TaskType3_RemoveOverlockForBankruptCustomer_CreateAndCompleteTask");
	}

	@Test(dataProvider="getLoginData")
	public void TaskType3_RemoveOverlockForBankruptCustomer_CreateAndCompleteTask(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("TaskManagement").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("TaskType3_RemoveOverlockForBankruptCustomer_CreateAndCompleteTask","Task Type 3 - Remove Overlock For Bankrupt Customer, Create and Complete Task");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			
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
					
			
	
			//================================== PM Home Page ===============================================
			
			
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			
			String SiteNumber = pmhomepage.getLocation();
			logger.log(LogStatus.PASS, "location number is:"+SiteNumber);
			Thread.sleep(3000);
			
			//Verifying PM Dash Board is displayed
			if (pmhomepage.get_WlkInCustText().trim().equalsIgnoreCase("Walk-In Customer")) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "PM Dashboard is displayed Successfully");
				logger.log(LogStatus.INFO, "PM Dashboard is displayed Successfully", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "PM Dashboard is not displayed");
				logger.log(LogStatus.INFO, "PM Dashboard is not displayed", image);

			}
			
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Advance Search link in PM home page");
			
			String qry = "Select Top 1 A.AccountNumber "+
					"FROM Account A with(nolock) "+
					"Join Customer C with(nolock) on C.CustomerID = A.CustomerID "+
					"Join AccountOrder AO with(nolock) on  AO.AccountID = A.AccountID "+
					"join AccountOrderItem AOI with(nolock) on AOI.AccountOrderID=AO.AccountOrderID "+
					"join site s with(nolock) on s.siteid=aoi.siteid "+
					"join StorageOrderItem SOI with(nolock) on SOI.StorageOrderItemID = AOI.StorageOrderItemID "+ 
					"join rentalunit ru with(nolock) on ru.rentalunitid=soi.rentalunitid "+
					"join Type T with(nolock) on T.TypeID = C.CustomerTypeID "+
					"join Type T1 with(nolock) on T1.TypeID = SOI.paymentmethodtypeid "+
					"join cltransaction clt with(nolock) on clt.accountorderitemid=aoi.accountorderitemid "+
					"where 1 = 1 and s.sitenumber='"+SiteNumber+"' "+
					"and soi.VacateDate is null "+
					"group by a.accountnumber "+
					"having sum((clt.amount * clt.quantity)+clt.discountamount) >0 and count(distinct ru.rentalunitid)=1";
			
			String AccountNumber=DataBase_JDBC.executeSQLQuery(qry);
			Thread.sleep(3000);
			
			String updateQuery1="UPDATE CUSTOMER SET IsBankrupt = 0 ,BankruptTypeId =853 , IsBankruptVerified = 0 where customerid=(select customerid from account where AccountNumber='"+AccountNumber+"')";
			DataBase_JDBC.executeSQLQuery(updateQuery1);
			Thread.sleep(5000);
			
			Advance_Search advSearch = new Advance_Search(driver);
			advSearch.enterAccNum(AccountNumber);
			Thread.sleep(4000);
			String scpathacc=Generic_Class.takeScreenShotPath();
			String imageacc=logger.addScreenCapture(scpathacc);
			logger.log(LogStatus.INFO, "Entered Account Number",imageacc);
			logger.log(LogStatus.INFO, "Entered Account Number is : "+AccountNumber);
			Thread.sleep(4000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			advSearch.clickButton();
			logger.log(LogStatus.INFO, "Clicked on Search button Successfully");
			Thread.sleep(10000);
			
			//================================== Customer Dashboard ===============================================
			
			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			
			if(cust_accdetails.isCustdbTitleDisplayed()){
				Thread.sleep(3000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "Customer Dashboard is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer Dashboard is not displayed");
				logger.log(LogStatus.INFO, "Customer Dashboard is not displayed",image);
			}
			Thread.sleep(3000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			cust_accdetails.clk_EditAccountDetailsBtn();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked on Edit Account Details button");
			
			Cust_EditAccountDetailsPage editdetail = new Cust_EditAccountDetailsPage(driver);
			Thread.sleep(2000);
			editdetail.clickOtherCustomerStatusRadioBtn();
			logger.log(LogStatus.INFO, "Selected Other Customer Status Radio button");
			Thread.sleep(2000);
			String scpa=Generic_Class.takeScreenShotPath();
			String ima=logger.addScreenCapture(scpa);
			logger.log(LogStatus.INFO, "img",ima);
			Thread.sleep(1000);
			editdetail.clickLaunchBtn();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked on Launch button");
			Thread.sleep(3000);
			
			Cust_OtherStatusesPage otherstatus = new Cust_OtherStatusesPage(driver);
			Thread.sleep(2000);
			
			if (otherstatus.verify_pageTitle()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User Lands in Other Statuses Page Successfully");
				logger.log(LogStatus.INFO, "User Lands in Other Statuses Page Successfully", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Other Statuses Page is not displayed");
				logger.log(LogStatus.INFO, "Other Statuses Page is not displayed", image);

			}

			Thread.sleep(2000);
			otherstatus.clk_DeclaredChkBx();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Declared check box is checked");
			try{
			otherstatus.select_Type();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Selected Type in the dropdown");
			}catch(Exception e){
			}
			String scpa1=Generic_Class.takeScreenShotPath();
			String ima1=logger.addScreenCapture(scpa1);
			logger.log(LogStatus.INFO, "img",ima1);
			
			otherstatus.click_Savebtn();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked on save button Successfully");
			otherstatus.enterNotes(tabledata.get("Notes"));
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Entered Notes in Notes textbox : "+tabledata.get("Notes"));
			otherstatus.entEmployee(tabledata.get("UserName"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Employee id: "+tabledata.get("UserName"));
			String scpa2=Generic_Class.takeScreenShotPath();
			String ima2=logger.addScreenCapture(scpa2);
			logger.log(LogStatus.INFO, "img",ima2);
			otherstatus.clk_ContinueBtn();
			Thread.sleep(20000);
			
			try{
				otherstatus.clk_ErrorOkBtn();
				Thread.sleep(3000);
			}catch(Exception e){
			}
			
			Thread.sleep(8000);
			driver.findElement(By.linkText("Back To Dashboard")).click();
			Thread.sleep(10000);
			
			
			
			//================================== PM Home Page ===============================================
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			
			
            List<WebElement> listTask = driver.findElements(By.xpath("//div[@id='task-grid']//div//table//tbody//tr//td/a"));
            int size=listTask.size();
            for(int i=0;i<size;i++){
	            if(listTask.get(i).getText().startsWith("Remove overlock on spaces for Bankrupt Customer")){
	                        driver.findElement(By.xpath("//div[@id='task-grid']//div//table//tbody//tr//td/a[contains(text(),'Remove overlock on spaces for Bankrupt Customer')]")).click();
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
			taskpage.clk_DateOverlocked();
			Thread.sleep(2000);
			taskpage.SelectDateFromCalendar(tabledata.get("SelectDate"));
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Selected date from the Calendar");
			
			String removeoverdate = driver.findElement(By.xpath("//form[@id='viewTaskForm']//div[@id='RemoveOverlockDate']/span")).getText();
			String removedate = removeoverdate.substring(3);
			logger.log(LogStatus.INFO, "Overlocked Checked/Removed date is: "+removedate.trim());
			
			taskpage.clk_CompleteRadioBtn();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Selected Complete Radio button");
			taskpage.enter_Comment(tabledata.get("Comment"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Comment in Text box: "+tabledata.get("Comment"));
			Thread.sleep(3000);
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
			
			pmhomepage.sel_ClosedOption();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Selected Closed Option in Task list");
			Thread.sleep(2000);
			
			
			List<WebElement> listTaskTitle = driver.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[4]/a"));
			int size1 = listTaskTitle.size();

			Thread.sleep(3000);

			Thread.sleep(3000);

			for (int i = size1; i > 0; i--) {

				if (listTaskTitle.get(i-1).getText().trim().equalsIgnoreCase(viewtaskTitle)) {

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
			
			
			Thread.sleep(5000);
			
			String removedate1 = driver.findElement(By.xpath("//form[@id='viewTaskForm']//div[@class='editor-label-with-validation-spacing']")).getText();
			String splitdate = removedate1.trim().substring(0, 8);
			String removedatedisplayed = new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("MM/dd/yyyy").parse(splitdate));

			//String removedatedisplayed = splitdate[0];
			
			
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
				logger.log(LogStatus.PASS, "Current date: " +CurrentDate+ "and task status id: " +taskstatusid+ " are matched with database. Date: "+completionDt+ " and task status id is 4");
			}else{
				logger.log(LogStatus.FAIL, "Current date: " +CurrentDate+ "and task status id: " +taskstatusid+ " are not matched with database. Date: "+completionDt+ " and task status id is 4");
			}
			Thread.sleep(2000);
		
			
			
			

			
			
			
			
			
			}catch(Exception e){
				e.printStackTrace();
				resultFlag="fail";
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "img",image);
				logger.log(LogStatus.FAIL, "Test script failed due to the exception "+e);
			}

		}

		@AfterMethod
		public void afterMethod(){

			Reporter.log(resultFlag,true);

			if(resultFlag.equals("pass")){
				Excel.setCellValBasedOnTcname(path, "TaskManagement","TaskType3_RemoveOverlockForBankruptCustomer_CreateAndCompleteTask" , "Status", "Pass");

			}else if (resultFlag.equals("fail")){

				Excel.setCellValBasedOnTcname(path, "TaskManagement","TaskType3_RemoveOverlockForBankruptCustomer_CreateAndCompleteTask" , "Status", "Fail");
			}else{
				Excel.setCellValBasedOnTcname(path, "TaskManagement","TaskType3_RemoveOverlockForBankruptCustomer_CreateAndCompleteTask" , "Status", "Skip");
			}


			extent.endTest(logger);
			extent.flush();
			Reporter.log("Test case completed: " +testcaseName, true);

		}


}
