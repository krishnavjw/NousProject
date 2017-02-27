package Scenarios.IssueManagement;

import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
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
import Pages.CustDashboardPages.ImportantInformationSection;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.IssueManagementPage.ConfirmCallCompletionMW;
import Pages.IssueManagementPage.CreateRentIncreasePage;
import Pages.IssueManagementPage.FeeAdjustmentRequestPage;
import Pages.IssueManagementPage.IssueApprovalPage;
import Pages.IssueManagementPage.IssueDecisionModelWindow;
import Pages.IssueManagementPage.IssueDetailsPage;
import Scenarios.Browser_Factory;

public class IMTC18_CreateaFeeAdjustmentRequestForaFee_DTMFeeBronzeCustomer_LienFee_EligibilityisNO extends Browser_Factory{
	public ExtentTest logger;
	String path=Generic_Class.getPropertyValue("Excelpath");
	String resultFlag="pass";
	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "IssueManagement","IssueManagement","CreateaFeeAdjustmentRequestForaFee_DTMFeeBronzeCustomer_LienFee_EligibilityisNO");
	}
	@Test(dataProvider="getCustSearchData")
	public void CreateaFeeAdjustmentRequestForaFee_DTMFeeBronzeCustomer_LienFee_EligibilityisNO(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		try{
			logger=extent.startTest("CreateaFeeAdjustmentRequestForaFee_DTMFeeBronzeCustomer_LienFee_EligibilityisNO", "CreateaFeeAdjustmentRequestForaFee_DTMFeeBronzeCustomer_LienFee_EligibilityisNO");
			Reporter.log("Test case started: " +testcaseName, true);

			if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("IssueManagement").equals("Y")))
			{
				resultFlag="skip";
				throw new SkipException("Skipping the test");
			}
			/*
			 * Select any bronze customer who had lien fee charged on the space
			 */
			String AccountNo=DataBase_JDBC.executeSQLQuery("select top 1 AccountNumber from vw_unitdetails UD with(nolock)"
					+ " join customer C with(nolock) on C.customerid=UD.customerid"
					+ " join customerclass cc with(nolock) on cc.customerclassid=c.customerclassid"
					+ " join clactivity clt with(nolock) on clt.accountorderitemid=UD.accountorderitemid"
					+ " where vacatedate is null and UD.paidthrudate>getdate() and"
					+ " clt.clactivitytypeid=372 and cc.classname='Bronze' and clt.recorddatetime >getutcdate ()-90");
			logger.log(LogStatus.INFO, "DB: fetched AccountNo: "+AccountNo+"");
			/*
			 * Set siteid 
			 */
			//To fetch the site number based on account number
			
			String siteNumber=DataBase_JDBC.executeSQLQuery("Select SiteNumber from vw_unitdetails where accountid="+AccountNo+"");

			logger.log(LogStatus.INFO, "DB:fetched site number based on account number: "+siteNumber+"");
			//To fetch site id based on Site number
			String newSiteId=DataBase_JDBC.executeSQLQuery("select siteid from site where sitenumber = "+siteNumber+"");
			logger.log(LogStatus.INFO, "DB: fetch site id based on Site number,newSiteId: "+newSiteId+"");

			//To fetch the existing site id basedon param value
			String existingSiteId=DataBase_JDBC.executeSQLQuery("select siteid from siteparameter where paramvalue='10.140.0.139'");
			logger.log(LogStatus.INFO, "DB: fetch the existing site id basedon param value,existingSiteId: "+existingSiteId+"");

			//reset the param value of existing siteId to null 
			DataBase_JDBC.executeSQLQuery("Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid="+existingSiteId+"");
			logger.log(LogStatus.PASS, "DB: updated the param value of existing site id to null");

			//To Reset the paramvalue of existing siteid to null
			DataBase_JDBC.executeSQLQuery("DB: Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid="+newSiteId+"");
			logger.log(LogStatus.PASS, "updated the param value of new site id to null");

			//To set the paramValue to a new siteId
			/*
			 * param value hard coded
			 */
			DataBase_JDBC.executeSQLQuery("DB: Update siteparameter set paramvalue='10.140.0.139' where paramcode='IP_COMPUTER_FIRST' and siteid="+newSiteId+"");
			logger.log(LogStatus.PASS, "updated the param value to the new site id"); 

			// Login To the Application
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			LoginPage login = new LoginPage(driver);
			login.login(tabledata.get("PMLogin"), tabledata.get("Password"));
			Thread.sleep(30000);
			driver.findElement(By.xpath("//div[@id='header-logo-container']/a/img")).click();
			String scpath, image;
			Thread.sleep(10000);

			//navigate to and verify advance search page
			PM_Homepage homepage=new PM_Homepage(driver);
			homepage.clk_AdvSearchLnk();
			Thread.sleep(7000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "user navigated to Advance search page");
			logger.log(LogStatus.INFO, "img", image);
			CreateRentIncreasePage rentpage= new CreateRentIncreasePage(driver);
			//step1:Select any bronze customer who had lien fee charged on the space

			rentpage.enter_text(AccountNo);
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "entered the account number");
			rentpage.clk_searchButton();
			logger.log(LogStatus.INFO, "clicked on the search button");
			Thread.sleep(70000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "user navigated to Customer dashboard page");
			logger.log(LogStatus.INFO, "img", image);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(2000,0)");
			Thread.sleep(2000);

			//step2:on customer dashboard select create issue from quick links 

			rentpage.clk_quicklink_dropdown();
			Thread.sleep(2000);
			rentpage.clk_create_issue();
			Thread.sleep(4000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "user navigated to issue type modal window");
			logger.log(LogStatus.INFO, "img", image); 

			//step3:Select "Fee adjustment" in issue type MW & click on confirm

			rentpage.click_IssueTypeDropDown();
			Thread.sleep(2000);
			List<WebElement> issuelist = driver.findElements(By.xpath("//ul[@id='issueTypeCombo_listbox']/li"));
			Actions act=new Actions(driver);
			for(WebElement ele:issuelist)
			{
				act.moveToElement(ele).build().perform();
				System.out.println(ele.getText());
				if(ele.getText().trim().equalsIgnoreCase("Fee Adjustment Request"))
				{
					act.moveToElement(ele).click().build().perform();
					break;
				}
				Thread.sleep(2000);
			}
			Thread.sleep(2000);
			rentpage.click_ConfirmmBtn();
			Thread.sleep(7000);

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "user navigated to fee adjustment request screen");
			logger.log(LogStatus.INFO, "img", image);

			//step4:On the create Fee asjustment issue screen,select lien fee
			/*
			 * if an issue is already created for lien fee , selected the option lien Fee2
			 */
			FeeAdjustmentRequestPage feeAdjustmentRequestPage=new FeeAdjustmentRequestPage(driver);
			feeAdjustmentRequestPage.clk_lienFee_rdBtn();
			Thread.sleep(2000);
			try {
				rentpage.clk_fee_reason_dropdown();
			} catch (WebDriverException e) {
				Thread.sleep(2000);
				feeAdjustmentRequestPage.acceptAlert_OKBtn();
				Thread.sleep(2000);
				feeAdjustmentRequestPage.clk_lienFee2_rdBtn();
				Thread.sleep(2000);
			}
			
			feeAdjustmentRequestPage.clkReasonDropDown();
			Thread.sleep(1000);
			List<WebElement> feeReasonOption = driver.findElements(By.xpath("//li[@id='SelectedReason_option_selected']/following-sibling::li"));
			for(WebElement ele:feeReasonOption)
			{
				Thread.sleep(2000);
				act.moveToElement(ele).build().perform();
				String feeReasonOptions=ele.getText();
				Thread.sleep(2000);
				if(ele.getText().trim().equalsIgnoreCase(tabledata.get("feeReasonOption")))
				{
					logger.log(LogStatus.INFO, "feeReasonOptions:"+feeReasonOptions+"");
					//act.moveToElement(ele).click().build().perform();
					ele.click();
					break;
				}
				Thread.sleep(2000);
			}
			Thread.sleep(2000);

			//step5:Enter the explanation & click on create issue 

			rentpage.enter_explanation(tabledata.get("Explanation"));
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(2000);
			rentpage.clk_fee_createIssue();
			Thread.sleep(4000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "user navigated to employee id modal window");
			logger.log(LogStatus.INFO, "img", image);

			//step6:"Enter employee id & click on submit

			rentpage.enter_empid(tabledata.get("PMLogin"));
			Thread.sleep(2000);
			rentpage.clk_empid_submit();
			Thread.sleep(7000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "user navigated to back to customer dashboard");
			logger.log(LogStatus.INFO, "img", image);
			//step7: verify the eligibility should be "NO"

			String eligibility=DataBase_JDBC.executeSQLQuery("select iseligible from issue where accountid='"+AccountNo+"'");
			if(eligibility.equalsIgnoreCase("0")){
				logger.log(LogStatus.PASS, "DB: Verified the eligibility of customer , eligibility is:"+eligibility+"which indicates the eligibility is NO"); 

			}else{
				logger.log(LogStatus.FAIL, "DB: Eligibility is:"+eligibility+"which indicates the eligibility is not 'NO'");
			}

			//step8: Verify account activities, issue created activity is generated and step10:Validate record in the database for created issue 


			String issueid=DataBase_JDBC.executeSQLQuery("Select IssueID from issue where accountid="+AccountNo+"");

			logger.log(LogStatus.PASS, "DB: issue is created , issueid is:"+issueid+"");

			//Click on Account Activities and verify issue created activity is generated	
			rentpage.clk_AccountActivities();
			Thread.sleep(7000);
			List<WebElement> listArrow = driver.findElements(By.xpath("//div[@id='activities-grid']/div[2]//tbody/tr/td[1]"));
			if(listArrow.size()>0){

				for(int i=0; i<=listArrow.size()-1; i++){
					listArrow.get(i).click();
					Thread.sleep(2000);
					String issueNoFRomUI = driver.findElement(By.xpath("//span[contains(text(),'Issue:')][1]/following-sibling::span/a")).getText();

					if(issueid.equals(issueNoFRomUI)){
						logger.log(LogStatus.INFO, "DB: issue id: "+issueid+"");
						logger.log(LogStatus.INFO, "GUI: issue id: "+issueNoFRomUI+"");
						logger.log(LogStatus.PASS, "verified the issue created activity is generated and it is displayed in GUI and reflected to DB" );
						break;
					}

				} 
			}else{
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "issue created is not displayed under account activities");
				logger.log(LogStatus.INFO, "img", image);
			}
			Thread.sleep(2000);  
			//step9:verify the open issue is displayed in important info section on cust dashboard
			ImportantInformationSection impInfoSec=new ImportantInformationSection(driver);
			if(impInfoSec.getFeeAdjustmentRequest().isDisplayed()){
				logger.log(LogStatus.PASS, "verified the fee adjustment request under Important information section");
			}else{
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Fee adjustment request is not present under Importnt information section");
				logger.log(LogStatus.INFO, "img", image);
			}


			//step11: Verify the task is generated for DM  approval
			String entityParentId=DataBase_JDBC.executeSQLQuery("select EntityParentId from  ws.vw_DraStructureModel where entityid = '"+siteNumber+"'");
			String taskForDMApproval=DataBase_JDBC.executeSQLQuery("select title from task where title like '%Fee Adjustment Request for%' and description='DM Issue Management Approval Task' and completiondt is null and locationcode='"+entityParentId+"' and tableid='"+issueid+"' order by id desc");
			if(taskForDMApproval.contains(issueid)){
				logger.log(LogStatus.PASS, "issue is created , taskForDMApproval title is:"+taskForDMApproval+"");
			}else{
				logger.log(LogStatus.FAIL, "current issue for DM approval is not found");
			}


			//step12: Login as DM & open the task from DM task list 

			//Click on Logoff
			jse.executeScript("window.scrollBy(0,-1000)", "");

			Thread.sleep(3000);
			homepage.log_off(driver);
			logger.log(LogStatus.INFO, "Clicked on the Logged out link in PM home page");
			Thread.sleep(8000);


			driver.navigate().refresh();
			Thread.sleep(5000);

			driver.navigate().refresh();
			Thread.sleep(5000);

			/*
			 * To fetch the DM login
			 */
			String DMLogin=DataBase_JDBC.executeSQLQuery("select EmployeeNumber as DMLogin from employee_hris where divisioncode='"+entityParentId+"' and JobTitle='AM'");
			logger.log(LogStatus.PASS, "DM Login is: "+DMLogin+"");
			//Login as DM 	
			JavascriptExecutor jses = (JavascriptExecutor) driver;
			LoginPage login_DM = new LoginPage(driver);
			login.login(DMLogin, tabledata.get("Password"));
			Thread.sleep(20000);
			driver.findElement(By.xpath("//div[@id='header-logo-container']/a/img")).click();
			Thread.sleep(7000);
			logger.log(LogStatus.INFO, "DMLogin is sucessfully done");

			//Open the task from DM Tasklist
			Actions action=new Actions(driver);
			Thread.sleep(4000);
			List<WebElement> taskList = driver.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[4]"));

			for(WebElement ele:taskList)
			{
				action.moveToElement(ele).build().perform();
				System.out.println(ele.getText());
				if(ele.getText().trim().equalsIgnoreCase("Approve [Issue #"+issueid+"] Fee Adjustment Request for [Property # 26422] [Space # B198]"))
				{
					action.moveToElement(ele).click().build().perform();
					break;
				}

				Thread.sleep(2000);
			}
			Thread.sleep(7000);

			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(7000);


			driver.findElement(By.xpath("//a[contains(@href,'/Issue/ViewFeeAdjustmentRequest?issueId="+issueid+"')]")).click();
			Thread.sleep(7000);
			logger.log(LogStatus.INFO, "Clicked on the issue under DM tasks");
			Thread.sleep(7000);



			//verify the fee contested
			IssueApprovalPage issueApprovalPage=new IssueApprovalPage(driver);
			String feeContested=issueApprovalPage.getFeeContested();
			String expFeeContested=feeAdjustmentRequestPage.getContestFee_LienFee();
			if(expFeeContested.contains(feeContested)){
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Fee contested matched with charged lien fee");
				logger.log(LogStatus.INFO, "img", image);
			}else{
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Fee contested not matched with charged lien fee");
				logger.log(LogStatus.INFO, "img", image);
			}

			//step13: Click on continue 

			issueApprovalPage.clkContinueBtn();
			logger.log(LogStatus.PASS, "clicked on continue button");
			Thread.sleep(7000);


			//IssueDecisionModelWindow actions
			IssueDecisionModelWindow issueDecisionMW=new IssueDecisionModelWindow(driver);
			//step14: Select  Decision as :"Decline"

			issueDecisionMW.clkDeclineRdBtn();
			logger.log(LogStatus.INFO, "Clicked on the decline radio button in Issue decision model window");
			Thread.sleep(1000);
			//step 15: Select who to call as :"Approver to call customer"


			issueDecisionMW.clkPMToCallRdBtn();
			logger.log(LogStatus.INFO, "Clicked on the PM to call radio button as DM to call option is not available");
			Thread.sleep(1000);
			//step15: Enter the notes & employeeid 


			issueDecisionMW.enterNotesEdit(tabledata.get("NotesToEnter"));
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "notes taken from the external resource and Entered in notes edit box in Issue decision model window");
			issueDecisionMW.enterEmpIdEdit(DMLogin);
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "Entered the BMLogin Id in Employeenumber edit box in Issue decision model window");
			//step16: Click on confrim

			issueDecisionMW.clkConfirmBtn();
			Thread.sleep(7000);
			logger.log(LogStatus.INFO, "Clicked on the Confirm button button in Issue decision model window");

			//step17: Verify call task is generated for DM to call customer 

			String callTaskForDMToCallCust=DataBase_JDBC.executeSQLQuery("select title from task where title like '%Fee Adjustment Request for%' and description = 'DM Call Task for Issue Management' and completiondt is null and locationcode='"+entityParentId+"' order by id desc");
			if(callTaskForDMToCallCust.contains(issueid)){
				logger.log(LogStatus.PASS, "Call task generated for DM to call customer, call task title is: "+callTaskForDMToCallCust+"");
			}else{
				logger.log(LogStatus.FAIL, "Call task not generated for DM to call customer, call task title is: "+callTaskForDMToCallCust+"");
			}
			//step18: click on back to dash board and open the call task from DM Dash board
			// IssueManagementList Page
			Pages.IssueManagementPage.IssueManagementList issueManagementListPage= new Pages.IssueManagementPage.IssueManagementList(driver);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(2000);
			issueManagementListPage.clkBackToDashboardTab();
			logger.log(LogStatus.INFO, "clicked on the back to dash board tab from Issue management list page");
			Thread.sleep(7000);

			//Logoff and login as DM and click on task
			jse.executeScript("window.scrollBy(0,-1000)", "");

			Thread.sleep(3000);
			homepage.log_off(driver);
			logger.log(LogStatus.INFO, "Clicked on the Logged out link in PM home page");
			Thread.sleep(8000);


			driver.navigate().refresh();
			Thread.sleep(5000);

			driver.navigate().refresh();
			Thread.sleep(5000);



			logger.log(LogStatus.PASS, "DM Login is: "+DMLogin+"");
			//Login as DM 	

			login.login(DMLogin, tabledata.get("Password"));
			Thread.sleep(20000);
			driver.findElement(By.xpath("//div[@id='header-logo-container']/a/img")).click();
			Thread.sleep(7000);
			logger.log(LogStatus.INFO, "DMLogin is sucessfully done");

			//Open the task from DM Tasklist

			Thread.sleep(4000);
			List<WebElement> taskList2 = driver.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody/tr/td[4]/a"));

			for(WebElement ele:taskList2)
			{
				action.moveToElement(ele).build().perform();
				System.out.println(ele.getText());
				if(ele.getText().trim().contains(issueid))
				{
					action.moveToElement(ele).click().build().perform();
					break;
				}

				Thread.sleep(2000);
			}

			try{
				Thread.sleep(2000);
				((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
				Thread.sleep(2000);
				logger.log(LogStatus.INFO, "Clicked on call task from DM Dash board");
				driver.findElement(By.xpath("//a[contains(@href,'/Issue/ViewFeeAdjustmentRequest?issueId="+issueid+"')]")).click();
				Thread.sleep(7000);
			}catch (Exception e){
				logger.log(LogStatus.FAIL, "Task under 'OPEN' staus is not available in DM Dash board to click and continue with the flow");
				e.getMessage();
			}


			//step19: select phone number
			//issue details page
			IssueDetailsPage issueDetailsPage=new IssueDetailsPage(driver);
			issueDetailsPage.clkPhoneNumChxBox();
			Thread.sleep(2000);
			//step20: select call result as "Reached" and enter any comments

			logger.log(LogStatus.INFO, "Clicked on phone number check box in Issue details page");
			issueDetailsPage.clkCallResultDropDown();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Clicked on call result drop down in Issue details page");
			/*
			 * Select option from drop down
			 */
			List<WebElement> callResultOptions = driver.findElements(By.xpath("//li[@id='callR160545473_option_selected']/following-sibling::li"));
			Thread.sleep(2000);
			for(WebElement ele:callResultOptions)
			{
				Thread.sleep(2000);
				act.moveToElement(ele).build().perform();
				Thread.sleep(2000);
				if(ele.getText().trim().equalsIgnoreCase(tabledata.get("CallResultDrpDownOption")))
				{
					act.moveToElement(ele).click().build().perform();
					break;
				}
				Thread.sleep(2000);
			}
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "option selected according to the test data provided in external resource");
			issueDetailsPage.enterCommentsEdt(tabledata.get("CallCustComments"));
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "comments entered as test data provided in external resource");

			//step21:Click on submit calls

			issueDetailsPage.clkSubmitCallsBtn();
			Thread.sleep(7000);
			logger.log(LogStatus.INFO, "Clicked on submit calls button under Issue details page");

			//step:::: missed navigation steps in test case
			//confirm call completion model window
			ConfirmCallCompletionMW confCallCompMWPage=new ConfirmCallCompletionMW(driver);
			confCallCompMWPage.enterEmpNumEdt(DMLogin);
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "Entered DMLogin id in Employee number edit box in confirm call completion model window");
			confCallCompMWPage.clkSaveAndCloseBtn();
			Thread.sleep(7000);
			logger.log(LogStatus.INFO, "clicked on save & close button in confirm call completion model window");

			//step22: Verify the task change to completed status
			String taskStatus=DataBase_JDBC.executeSQLQuery("select top 1 t1.name as IssueStatus from issue iss with(nolock) "
					+ "join type t with(nolock) on iss.issuetypeid=t.typeid "
					+ "join type t1 with(nolock) on iss.issuestatustypeid=t1.typeid "
					+ "join task ta with(nolock) on iss.issueid=ta.tableid "
					+ "join taskstatus ts with(nolock) on ta.taskstatusid=ts.id "
					+ "join vw_unitdetails u  with(nolock) on iss.accountid=u.accountid "
					+ "where issueid="+issueid+" order by ta.createdt");

			logger.log(LogStatus.PASS, "Verified the issue/task status, task statusis:"+taskStatus+"");

			//issue management list page
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			issueManagementListPage.clkBackToDashboardTab();
			logger.log(LogStatus.INFO, "Clicked on back to dash board tab");
			Thread.sleep(7000);

			//step23: verify the fee should not be waived off
			/*
			 * Verify the account activity “Adjustment credit” should not be created and also compare 
			 * the before and after “Balance “  in the account activities.Balance should be same.
			 * There should be no change in the balance
			 */

		}
		catch(Exception ex){
			ex.printStackTrace();
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			resultFlag = "fail";
			Reporter.log("Exception ex: " + ex, true);
			logger.log(LogStatus.FAIL, "Test Script fail due to exception");
			logger.log(LogStatus.INFO, "Exception",image);
		}
	}
	@AfterMethod
	public void afterMethod(){
		Reporter.log(resultFlag,true);
		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "IssueManagement","CreateaFeeAdjustmentRequestForaFee_DTMFeeBronzeCustomer_LienFee_EligibilityisNO" , "Status", "Pass");
		}else if (resultFlag.equals("fail")){
			Excel.setCellValBasedOnTcname(path, "IssueManagement","CreateaFeeAdjustmentRequestForaFee_DTMFeeBronzeCustomer_LienFee_EligibilityisNO" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "IssueManagement","CreateaFeeAdjustmentRequestForaFee_DTMFeeBronzeCustomer_LienFee_EligibilityisNO" , "Status", "Skip");
		}
		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);
	}

}
