package Scenarios.IssueManagement;

import java.util.ArrayList;
import java.util.Hashtable;

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

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.DMCallsPage;
import Pages.CustDashboardPages.DM_IssueDetailsPage;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.IssueManagementPage.CreateIssuePage;
import Pages.IssueManagementPage.FeeAdjustmentRequestPage;
import Scenarios.Browser_Factory;

public class CreateFeeAdjustmentRequest_For_DTMFee_GoldCustomer_Eligible extends Browser_Factory{
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");
	String accNum;
	String siteid_tobeset;

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"IssueManagement","IssueManagement", "CreateFeeAdjustmentRequest_For_DTMFee_GoldCustomer_Eligible");
	}

	@Test(dataProvider="getLoginData")
	public void CreateFeeAdjustmentRequest_For_DTMFee_GoldCustomer_Eligible(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("IssueManagement").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("CreateFeeAdjustmentRequest_For_DTMFee_GoldCustomer_Eligible","Issue Management - Create Fee Adjustment Request for DTM fee Gold Customer, Eligiblity is Yes");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			
			
			String sqlqry ="select distinct top 1 accountnumber,siteid, UD.rentalunitnumber,unitcost as UnitRent "+
							"from vw_unitdetails UD with(nolock) "+
							"join customer C with(nolock) on C.customerid=UD.customerid "+
							"join customerclass cc with(nolock) on cc.customerclassid=c.customerclassid "+
							"join clactivity clt with(nolock) on clt.accountorderitemid=UD.accountorderitemid "+
							"left join cltransactionmaster clm with(nolock) on clm.accountid=UD.accountid and cltransactiontypeid=2009 and clm.lastupdate>getutcdate()-120 "+
							"left join issue iss with(nolock) on iss.accountid=UD.accountid and iss.rentalunitid=UD.rentalunitid and issuetypeid=3122 "+
							"where vacatedate is null "+
							"and UD.paidthrudate>getdate() "+
							"and clt.clactivitytypeid=372 "+
							"and cc.classname='Gold' "+
							"and clt.recorddatetime >getutcdate ()-90 "+
							"and clm.cltransactionmasterid is null "+
							"and iss.issueid is null ";
					
			
			try { 

				ArrayList<String> results = DataBase_JDBC.executeSQLQuery_List(sqlqry);
				System.out.println(results);

				siteid_tobeset = results.get(1);
				System.out.println("Site Id to be set is:   " + siteid_tobeset);

				accNum = results.get(0);
				System.out.println("Account Number is:   " + accNum);


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
					
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(4000);
			
			//=================Handling Customer Facing Device================================
			
			/*Thread.sleep(5000);
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
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_aut"));

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
			Thread.sleep(5000);*/
			
			try{
			Thread.sleep(5000);
			driver.findElement(By.id("continueLink")).click();
			Thread.sleep(8000);
			}catch(Exception e){
				
			}
			
					
			//================================== PM Home Page ===============================================
			
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			Thread.sleep(3000);
			
			String SiteNumber = pmhomepage.getLocation();
			logger.log(LogStatus.PASS, "location number is:"+SiteNumber);
			
			String divcode = DataBase_JDBC.executeSQLQuery("select EntityParentId as DivisionCode from  ws.vw_DraStructureModel where entityid ='"+SiteNumber+"'");
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Division Code for the SiteNumber " +SiteNumber+ " is " +divcode);
			Thread.sleep(1000);
			
			ArrayList<String> DmRmlogin = DataBase_JDBC.executeSQLQuery_List("select EmployeeNumber as DMLogin, SupervisorID as RMLogin from employee_hris where divisioncode='"+divcode+"' and JobTitle='AM'"); 
			String Dmloginid = DmRmlogin.get(0);
			logger.log(LogStatus.INFO, "DM Login Id for the  SiteNumber " +SiteNumber+ " is " +Dmloginid);
			String Rmloginid = DmRmlogin.get(1); 
			logger.log(LogStatus.INFO, "RM Login Id for the  SiteNumber " +SiteNumber+ " is " +Rmloginid);
			Thread.sleep(5000);
			
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
			
			Thread.sleep(3000);
			
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Advance Search Link");
			
			Advance_Search advSearch = new Advance_Search(driver);
			advSearch.enterAccNum(accNum);
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "Entered Account Number of Gold Customer is : "+accNum);
			Thread.sleep(2000);
			String scpathacc = Generic_Class.takeScreenShotPath();
			String imageacc = logger.addScreenCapture(scpathacc);
			logger.log(LogStatus.INFO, "Entered Account Number", imageacc);
			Thread.sleep(1000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			advSearch.clickButton();
			logger.log(LogStatus.INFO, "Clicked on Search button Successfully");
			Thread.sleep(10000);
			
			//================================== Customer Dashboard ===========================================
			
			//Verifying Customer Dash Board is displayed
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
			
			
			Thread.sleep(2000);
			String totbalance = cust_accdetails.getTotalDue();
			logger.log(LogStatus.INFO, "Total Balance Displaying before creating issue : "+totbalance);
			String nextbalance = cust_accdetails.getNextPaymentDueAmount();
			logger.log(LogStatus.INFO, "Next Payment Due Amount before creating issue : "+nextbalance);
			Thread.sleep(2000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			
			//Clicking on create issue option
			cust_accdetails.clk_quicklink();
			logger.log(LogStatus.INFO, "Clicked on Quick link Dropdown");
			Thread.sleep(2000);
			cust_accdetails.clk_CreateIssue();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO,"Clicked on Create Issue option Successfully");
			
			
			//================================== Create Issue page ===============================================
			
			CreateIssuePage issuepage = new CreateIssuePage(driver);
			if(issuepage.verify_Title()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Issue Type Model Window is displayed successfully");
				logger.log(LogStatus.INFO, "Issue Type Model Window is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Issue Type Model Window is not displayed");
				logger.log(LogStatus.INFO, "Issue Type Model Window is not displayed",image);
			}
			Thread.sleep(2000);
		
			issuepage.click_IssueTypeDropDown();
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Clicked on Issue Type Dropdown");
			issuepage.select_FeeAdjusReq();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Selected Fee Adjustment Request Option");
			String scpathac = Generic_Class.takeScreenShotPath();
			String imageac = logger.addScreenCapture(scpathac);
			logger.log(LogStatus.INFO, "Selected Fee Adjustment Request Option", imageac);
			Thread.sleep(1000);
			issuepage.click_ConfirmmBtn();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Confirm Button Successfully");

			
			
			//================================== Fee Adjustment Request ======================================
			
			FeeAdjustmentRequestPage feeadjus = new FeeAdjustmentRequestPage(driver);
			if(feeadjus.verify_pageTitle()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Fee Adjustment Request page is displayed successfully");
				logger.log(LogStatus.INFO, "Fee Adjustment Request page is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Fee Adjustment Request page is not displayed");
				logger.log(LogStatus.INFO, "Fee Adjustment Request page is not displayed",image);
			}
			Thread.sleep(3000);
			
			
			try{
				//boolean warn = driver.findElement(By.xpath("//form[@id='feeAdjustmentForm']//div[text()='Please select at least one fee.']")).isDisplayed();
				//Thread.sleep(2000);
				driver.findElement(By.xpath("//form[@id='feeAdjustmentForm']//table//tr[1]//input[@name='selectedIds']")).click();
				Thread.sleep(2000);
			}catch(Exception e){
				
			}
			
			feeadjus.select_Reason(tabledata.get("Reason"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Selected Reason in the Reason dropdown : "+tabledata.get("Reason"));
			feeadjus.enterDescription(tabledata.get("Explanation"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Explanation in the text box :"+tabledata.get("Explanation"));
			String scpathac1 = Generic_Class.takeScreenShotPath();
			String imageac1 = logger.addScreenCapture(scpathac1);
			logger.log(LogStatus.INFO, "Entered Details in the page", imageac1);
			Thread.sleep(1000);
			feeadjus.click_CreateIssueBtn();
			Thread.sleep(4000);
			
			driver.findElement(By.id("employeeNumber")).sendKeys(tabledata.get("UserName"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Employee Number is :"+tabledata.get("UserName"));
			String scpa1=Generic_Class.takeScreenShotPath();
			String ima1=logger.addScreenCapture(scpa1);
			logger.log(LogStatus.INFO, "img",ima1);
			Thread.sleep(2000);
			driver.findElement(By.xpath("//a[contains(text(),'Submit')]")).click();
			logger.log(LogStatus.PASS, "Clicked on Submit Button Successfully");
			Thread.sleep(8000);
			
			
			String issuelist = "Select * from issue where accountid='"+accNum+"' order by lastupdate desc";
			
			ArrayList<String> listOfIssue = DataBase_JDBC.executeSQLQuery_List(issuelist);
			
			String eligible = listOfIssue.get(20);
			
			if(eligible.equals("1")){
				logger.log(LogStatus.PASS, "Eligiblity is 1 is verified with database :"+eligible);
			}else{ 
				logger.log(LogStatus.FAIL, "Eligiblity is 1 not verified with database :"+eligible);
			}
			Thread.sleep(2000);
			
			cust_accdetails.clk_Acc_ActivitiesTab();
			Thread.sleep(8000);
			logger.log(LogStatus.INFO, "Clicked on Account Activities Tab");
			boolean accAct = driver.findElement(By.xpath("//div[@id='activities-grid']//table/tbody/tr[1]/td[text()='Issue created of type Fee Adjustment Request']")).isDisplayed();
			
			if(accAct){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Created Issue is displayed in Account Activities tab Successfully");
				logger.log(LogStatus.INFO, "Created Issue is displayed in Account Activities tab Successfully",image);
			}else{ 

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Created issue is not displayed in Account Activities tab");
				logger.log(LogStatus.INFO, "Created issue is not displayed in Account Activities tab",image);
			}
			Thread.sleep(2000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			
			boolean feeadjus1 = driver.findElements(By.xpath("//a[contains(text(),'Fee Adjustment Request')]")).size()>0;
			if(feeadjus1){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Open issue is displayed in Important Information Section Successfully");
				logger.log(LogStatus.INFO, "Open issue is displayed in Important Information Section Successfully",image);
			}else{ 

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Open issue is not displayed in Important Information Section");
				logger.log(LogStatus.INFO, "Open issue is not displayed in Important Information Section",image);
			}
			Thread.sleep(2000);
			
			
			String recorddescription = listOfIssue.get(17);
			if(recorddescription.equals(tabledata.get("Explanation"))){
				logger.log(LogStatus.PASS, "Record Created "+tabledata.get("Explanation")+ " is matched with the database "+recorddescription);
			}else{
				logger.log(LogStatus.FAIL, "Record Created "+tabledata.get("Explanation")+ " is not matched with the database "+recorddescription);
			}
			
			String qry1 = "Select * from task where locationcode='"+divcode+"' order by createdt desc";
			ArrayList<String> tasklist = DataBase_JDBC.executeSQLQuery_List(qry1);
			
			
			jse.executeScript("window.scrollBy(0,-1000)", "");
			
			Thread.sleep(3000);
			pmhomepage.logout(driver);
			logger.log(LogStatus.INFO, "Clicked on the Logged out link in PM home page");
            Thread.sleep(8000);

			
			driver.navigate().refresh();
			Thread.sleep(5000);
			
			driver.navigate().refresh();
			Thread.sleep(5000);
			
			//================================== DM login ======================================
			
			login.login(Dmloginid, tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(6000);
			
			try{
			Thread.sleep(5000);
			driver.findElement(By.id("continueLink")).click();
			Thread.sleep(10000);
			}catch(Exception e){
				
			}
			
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "DM Dashboard is displayed Successfully",image1);
	
			
			String title = tasklist.get(1);
			logger.log(LogStatus.PASS, "Call Task is generated for DM : "+title);
			Thread.sleep(2000);
			
			WebElement titleclick = driver.findElement(By.xpath("//div[@id='task-grid']//table/tbody/tr/td[4]/a[text()='"+title+"']"));
			Thread.sleep(2000);
			jse.executeScript("arguments[0].scrollIntoView(true);",titleclick);
			Thread.sleep(1000);
			jse.executeScript("arguments[0].click();", titleclick);
			Thread.sleep(4000);
			
			
			DM_IssueDetailsPage issueDet = new DM_IssueDetailsPage(driver);
			
			if(issueDet.verify_Title()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Issue Detail Page is displayed successfully");
				logger.log(LogStatus.INFO, "Issue Detail Page is displayed successfully",image);
			}else{ 

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Issue Detail Page is not displayed successfully");
				logger.log(LogStatus.INFO, "Issue Detail Page is not displayed successfully",image);
			}
			Thread.sleep(2000);
			
			String concession = driver.findElement(By.id("feeContested")).getText();
			String conces = concession.trim().substring(1);
			
			logger.log(LogStatus.INFO, "Concession Amount displayed in Issue page is :" +conces);
			Thread.sleep(2000);
			
			
			issueDet.clk_ContinueBtn();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on continue button");
			
			if(issueDet.verify_PageTitleIssue()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Issue Decision Page is displayed successfully");
				logger.log(LogStatus.INFO, "Issue Decision Page is displayed successfully",image);
			}else{ 

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Issue Decision Page is not displayed successfully");
				logger.log(LogStatus.INFO, "Issue Decision Page is not displayed successfully",image);
			}
			Thread.sleep(2000);
			
			issueDet.clk_ApproveRadioBtn();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked on Approve Radio button");
			issueDet.clk_ApproveToCalCust();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked on Approve To Call Customer Radio button");
			issueDet.enterNotes(tabledata.get("Notes"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Notes :"+tabledata.get("Notes"));
			issueDet.enterEmployeeId(Dmloginid);
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Entered Employee id: "+Dmloginid);
			
			String scpath2=Generic_Class.takeScreenShotPath();
			String image2=logger.addScreenCapture(scpath2);
			logger.log(LogStatus.INFO, "Entered details in Issue Detail page",image2);
			
			Thread.sleep(2000);
			issueDet.clk_ConfirmBtn();
			logger.log(LogStatus.PASS, "Clicked on Confirm Button Successfully");
			Thread.sleep(22000);
			
			Thread.sleep(8000);
			driver.findElement(By.xpath("//a[text()='Back To Dashboard']")).click();
			logger.log(LogStatus.INFO, "Clicked on back to dashboard button");
			Thread.sleep(8000);
			
			String qry2 = "Select * from task where description like '%DM call%' and title like '%Fee adjustment%' and locationcode='"+divcode+"' order by id desc";
			ArrayList<String> tasklist2 = DataBase_JDBC.executeSQLQuery_List(qry2);
			Thread.sleep(8000);
			
			
			
			String dmcalltitle = tasklist2.get(1);
			Thread.sleep(1000);
			String issueNum= tasklist2.get(24);
			Thread.sleep(1000);
			
			logger.log(LogStatus.PASS, "Call Task is generated for DM : "+dmcalltitle);
			
			WebElement calltitle = driver.findElement(By.xpath("//div[@id='task-grid']//table/tbody/tr/td[4]/a[contains(text(),'Call Customer about [Issue # "+issueNum+"] Fee Adjustment Request')]"));
			Thread.sleep(2000);
			jse.executeScript("arguments[0].scrollIntoView(true);",calltitle);
			Thread.sleep(1000);
			jse.executeScript("arguments[0].click();", calltitle);
			Thread.sleep(6000);
			
			
			DMCallsPage dmcalls = new DMCallsPage(driver);
			String s=Generic_Class.takeScreenShotPath();
			String i=logger.addScreenCapture(s);
			logger.log(LogStatus.PASS, "Navigated to DM Calls Page successfully");
			logger.log(LogStatus.INFO, "Navigated to DM Calls Page successfully",i);
			Thread.sleep(2000);
			
			String concession1 = driver.findElement(By.id("feeContested")).getText();
			String conces1 = concession1.trim().substring(1);
			logger.log(LogStatus.INFO, "Fee contested displayed in issue details page : "+conces1);
			
			try{
			String lastAdjus = driver.findElement(By.xpath("//span[contains(text(),'Last Adjustment:')]/following-sibling::div//tr[1]/td[1]")).getText().trim();
			logger.log(LogStatus.INFO, "Last Adjustment displayed : "+lastAdjus);
			Thread.sleep(2000);
			}catch(Exception e){
				
			}
			
			dmcalls.clk_PhnChkBx();
			logger.log(LogStatus.INFO, "Clicked on Phone number check box");
			Thread.sleep(3000);
			dmcalls.sel_DropDownValFromReason(tabledata.get("ReasonCall"));
			logger.log(LogStatus.INFO, "Selected Reason from the Reason Dropdown is: "+tabledata.get("ReasonCall"));
			Thread.sleep(3000);
			logger.log(LogStatus.INFO,"Selected Reason in the Call Result Dropdown");
			
			dmcalls.enterComment(tabledata.get("Comment"));
			logger.log(LogStatus.INFO, "Entered Comment in Comments text box");
			Thread.sleep(3000);
			String scpathpay=Generic_Class.takeScreenShotPath();
			String imagepay=logger.addScreenCapture(scpathpay);
			logger.log(LogStatus.INFO, "img",imagepay);
			Thread.sleep(2000);
			dmcalls.clk_SubmitCallsBtn();
			Thread.sleep(3000);
			logger.log(LogStatus.PASS, "Clicked on Submit Calls Button Successfully");
			dmcalls.enterEmpNum(Dmloginid);
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Employee number is: "+Dmloginid);
			String scpathpa=Generic_Class.takeScreenShotPath();
			String imagepa=logger.addScreenCapture(scpathpa);
			logger.log(LogStatus.INFO, "img",imagepa);
			Thread.sleep(2000);
			dmcalls.clk_saveCloseBtn();
			logger.log(LogStatus.PASS, "Clicked on Save And Close button Successfully");
			Thread.sleep(10000);
			
			
			String issuelist1 = "select iss.issueid,t.name as IssueType,t1.name as IssueStatus,ta.title, "+
					"ta.description,ta.locationcode,ta.createdt,ta.completiondt,ts.displayname as TaskStatus "+
					"from issue iss with(nolock) "+
					"join type t with(nolock) on iss.issuetypeid=t.typeid "+
					"join type t1 with(nolock) on iss.issuestatustypeid=t1.typeid "+
					"join task ta with(nolock) on iss.issueid=ta.tableid "+
					"join taskstatus ts with(nolock) on ta.taskstatusid=ts.id "+
					"join vw_unitdetails u  with(nolock) on iss.accountid=u.accountid "+
					"where u.accountid='"+accNum+"' "+
					"order by ta.createdt ";
			
			Thread.sleep(3000);
			ArrayList<String> issuelist11 = DataBase_JDBC.executeSQLQuery_List(issuelist1);
			String issueStatus = issuelist11.get(2);
			
			if(issueStatus.trim().equalsIgnoreCase("Completed")){
				logger.log(LogStatus.PASS, "Issue Status is : Completed is verified with database : "+issueStatus);
			}else{
				logger.log(LogStatus.PASS, "Issue Status is : Completed is verified with database : "+issueStatus);
			}
				
			Thread.sleep(3000);

			
			DM_HomePage dmhomepage = new DM_HomePage(driver);
			jse.executeScript("window.scrollBy(0,-1000)", "");
			
			Thread.sleep(3000);
			dmhomepage.log_off(driver);
			logger.log(LogStatus.INFO, "Clicked on the Logged out link in DM home page");
            Thread.sleep(8000);

			driver.navigate().refresh();
			Thread.sleep(5000);
			
			driver.navigate().refresh();
			Thread.sleep(5000);
			
			//================================== PM login ======================================
			
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(6000);
			
			try{
			Thread.sleep(5000);
			driver.findElement(By.id("continueLink")).click();
			Thread.sleep(10000);
			}catch(Exception e){
				
			}
			
			String scpath23=Generic_Class.takeScreenShotPath();
			String image23=logger.addScreenCapture(scpath23);
			logger.log(LogStatus.INFO, "PM Dashboard is displayed Successfully",image23);
			
			Thread.sleep(3000);
			
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Advance Search Link");
			
			advSearch.enterAccNum(accNum);
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "Entered Account Number of Gold Customer is : "+accNum);
			Thread.sleep(2000);
			String scr1234 = Generic_Class.takeScreenShotPath();
			String ii22 = logger.addScreenCapture(scr1234);
			logger.log(LogStatus.INFO, "Entered Account Number", ii22);
			Thread.sleep(1000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			advSearch.clickButton();
			logger.log(LogStatus.INFO, "Clicked on Search button Successfully");
			Thread.sleep(10000);
			
			
			String totbalance1 = cust_accdetails.getTotalDue();
			logger.log(LogStatus.INFO, "Total Due Displaying after creating issue : "+totbalance1);
			Thread.sleep(1000);
			String nextbalance1 = cust_accdetails.getNextPaymentDueAmount();
			logger.log(LogStatus.INFO, "Next Payment Due Amount after creating issue : "+nextbalance1);
			Thread.sleep(2000);
		
		/*	if(totbalance.trim().equals(totbalance1.trim()) && nextbalance.trim().equals(nextbalance1.trim())){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Total Due now " +totbalance+ " and Next Payment Due " +nextbalance+ 
						" are matched with the database Total Due now: " +totbalance1+ " and Next Payment Due : " +nextbalance1);
				logger.log(LogStatus.INFO, "img",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Total Due now " +totbalance+ " and Next Payment Due " +nextbalance+ 
						" are not matched with the database Total Due now: " +totbalance1+ " and Next Payment Due : " +nextbalance1);
				logger.log(LogStatus.INFO, "img",image);
			}
			*/
			Thread.sleep(3000);
			
			
			Thread.sleep(3000);
			cust_accdetails.clk_Acc_ActivitiesTab();
			logger.log(LogStatus.INFO, "Clicked on Account Activities Tab");
			Thread.sleep(10000);
			jse.executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			
			
			try{
			String bal = driver.findElement(By.xpath("(//div[@id='activities-grid']//table/tbody//td[text()='Adjustment Credit']/following-sibling::td[2])[1]")).getText();
			//String balance = bal.trim().substring(2);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Balance is verified in Account Activities : "+bal);
			logger.log(LogStatus.INFO, "img",image);
			Thread.sleep(3000);
			
			}catch(Exception e){
				logger.log(LogStatus.INFO, "Adjustment credit is not displayed in Account Activities");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "img",image);
				Thread.sleep(2000);
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
			Excel.setCellValBasedOnTcname(path, "IssueManagement","CreateFeeAdjustmentRequest_For_DTMFee_GoldCustomer_Eligible" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "IssueManagement","CreateFeeAdjustmentRequest_For_DTMFee_GoldCustomer_Eligible" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "IssueManagement","CreateFeeAdjustmentRequest_For_DTMFee_GoldCustomer_Eligible" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
	
}
