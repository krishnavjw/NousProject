package Scenarios.IssueManagement;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openxmlformats.schemas.officeDocument.x2006.relationships.impl.DmAttributeImpl;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.microsoft.sqlserver.jdbc.ISQLServerDataSource;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.DM_IssueDetailsPage;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.IssueManagementPage.CreateIssuePage;
import Pages.IssueManagementPage.CreateRentRateIncreaseObjectionIssuePage;
import Scenarios.Browser_Factory;
import sun.awt.datatransfer.ToolkitThreadBlockedHandler;

public class Verify_CreateRentIncreaseObjection_For_Bronze_Customer_Eligiblity_No extends Browser_Factory{
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");
	String siteid_tobeset;
	String accNum;

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"IssueManagement","IssueManagement", "Verify_CreateRentIncreaseObjection_For_Bronze_Customer_Eligiblity_No");
	}

	@Test(dataProvider="getLoginData")
	public void Verify_CreateRentIncreaseObjection_For_Bronze_Customer_Eligiblity_No(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("IssueManagement").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Verify_CreateRentIncreaseObjection_For_Bronze_Customer_Eligiblity_No","Issue Management - Create Rent Increase Objection For Bronze Customer, Eligiblity is No");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			
			String sqlqry = "select distinct top 1 accountnumber,siteid,UD.rentalunitnumber,unitcost as UnitRent "+
							"from vw_unitdetails UD with(nolock) "+
							"join customer C with(nolock) on C.customerid=UD.customerid "+
							"join customerclass cc with(nolock) on cc.customerclassid=c.customerclassid "+
							"join rentalunit ru with(nolock) on ru.rentalunitid=UD.rentalunitid "+
							"join storageorderitemratechange soirc with(nolock) on UD.storageorderitemid=soirc.storageorderitemid "+
							"left join issue iss with(nolock) on iss.accountid=UD.accountid and iss.rentalunitid=UD.rentalunitid and issuetypeid=3120 "+
							"where vacatedate is null "+
							"and cc.classname='Bronze' "+
							"and soirc.effectivedate  > getdate()-60 "+
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
			Thread.sleep(2000);
			
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
			
			
			Thread.sleep(5000);
			driver.findElement(By.id("continueLink")).click();
			Thread.sleep(8000);
			
					
			//================================== PM Home Page ===============================================
			
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			Thread.sleep(5000);
			
			String SiteNumber = pmhomepage.getLocation();
			logger.log(LogStatus.PASS, "Location number is:"+SiteNumber);
			
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
			logger.log(LogStatus.INFO, "Entered Account Number of Bronze Customer is : "+accNum);
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
			issuepage.select_RentRateIncreObjection();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Selected Rent Rate Increase Objection Option");
			String scpathac = Generic_Class.takeScreenShotPath();
			String imageac = logger.addScreenCapture(scpathac);
			logger.log(LogStatus.INFO, "Selected Rent Rate Increase Objection Option", imageac);
			Thread.sleep(1000);
			issuepage.click_ConfirmmBtn();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Confirm Button Successfully");
			
			
			//================================== Create Rent rate Increase ======================================
			
			CreateRentRateIncreaseObjectionIssuePage rentrate = new CreateRentRateIncreaseObjectionIssuePage(driver);
			if(rentrate.verify_pageTitle()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Create Rent Rate Increase Objection Issue page is displayed successfully");
				logger.log(LogStatus.INFO, "Create Rent Rate Increase Objection Issue page is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Create Rent Rate Increase Objection Issue page is not displayed");
				logger.log(LogStatus.INFO, "Create Rent Rate Increase Objection Issue page is not displayed",image);
			}
			Thread.sleep(2000);
			
			rentrate.select_Reason(tabledata.get("Reason"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Selected Reason in the Reason dropdown : "+tabledata.get("Reason"));
			rentrate.enterDescription(tabledata.get("Explanation"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Explanation in the text box :"+tabledata.get("Explanation"));
			String scpathac1 = Generic_Class.takeScreenShotPath();
			String imageac1 = logger.addScreenCapture(scpathac1);
			logger.log(LogStatus.INFO, "Entered Details in the page", imageac1);
			rentrate.click_CreateIssueBtn();
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
			
			if(eligible.equals("0")){
				logger.log(LogStatus.PASS, "Eligiblity is Zero is verified with database :"+eligible);
			}else{ 
				logger.log(LogStatus.FAIL, "Eligiblity is not verified with database:"+eligible);
			}
			Thread.sleep(2000);
			
			cust_accdetails.clk_Acc_ActivitiesTab();
			Thread.sleep(10000);
			logger.log(LogStatus.INFO, "Clicked on Account Activities Tab");
			boolean accAct = driver.findElement(By.xpath("//div[@id='activities-grid']//table/tbody/tr[1]/td[text()='Issue created of type Rent Rate Increase Objection']")).isDisplayed();
			
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
			
			boolean rentImportant = driver.findElements(By.xpath("//a[contains(text(),'Rent Rate Increase Objection')]")).size()>0;
			if(rentImportant){

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
			
			/*Thread.sleep(5000);
			driver.findElement(By.id("continueLink")).click();
			Thread.sleep(10000);*/
			
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "DM Dashboard is displayed Successfully",image1);
			logger.log(LogStatus.PASS, "DM Dashboard is displayed Successfully");
	
			
			String title = tasklist.get(1);
			Thread.sleep(2000);
			
			logger.log(LogStatus.PASS, "Call task is generated for DM : "+title);
			
			WebElement titleclick = driver.findElement(By.xpath("//div[@id='task-grid']//table/tbody/tr/td[4]/a[text()='"+title+"']"));
			jse.executeScript("arguments[0].scrollIntoView(true);",titleclick);
			Thread.sleep(1000);
			jse.executeScript("arguments[0].click();", titleclick);
			Thread.sleep(4000);
			
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
			
			issueDet.clk_DeclineRadioBtn();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked on Decline Radio button");
			issueDet.clk_PmToCallCustRadioBtn();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked on PM To Call Customer Radio button");
			issueDet.enterNotes(tabledata.get("Notes"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Notes :"+tabledata.get("Notes"));
			issueDet.enterEmployeeId(Dmloginid);
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Entered Employee id");
			
			String scpath2=Generic_Class.takeScreenShotPath();
			String image2=logger.addScreenCapture(scpath2);
			logger.log(LogStatus.INFO, "img",image2);
			
			issueDet.clk_ConfirmBtn();
			logger.log(LogStatus.PASS, "Clicked on Confirm Button Successfully");
			Thread.sleep(6000);
			
			
			String tit  = DataBase_JDBC.executeSQLQuery("Select title from task where locationcode='"+SiteNumber+"' and description='PM Call Task for Issue Management' order by createdt desc");
			
			logger.log(LogStatus.PASS, "Call Task is generated for PM :"+tit);
			Thread.sleep(2000);
			
			jse.executeScript("window.scrollBy(0,-1000)", "");
			
			Thread.sleep(3000);
			DM_HomePage dmhomepage = new DM_HomePage(driver);
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
			Thread.sleep(8000);
			
		/*	Thread.sleep(5000);
			driver.findElement(By.id("continueLink")).click();
			Thread.sleep(10000);*/
			
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Advance Search Link");
			
			advSearch.enterAccNum(accNum);
			logger.log(LogStatus.INFO, "Entered Account Number of Bronze Customer is : "+accNum);
			Thread.sleep(2000);
			String scpathacc1 = Generic_Class.takeScreenShotPath();
			String imageacc1 = logger.addScreenCapture(scpathacc1);
			logger.log(LogStatus.INFO, "Entered Account Number", imageacc1);
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
		
			if(totbalance.trim().equals(totbalance1.trim()) && nextbalance.trim().equals(nextbalance1.trim())){
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
			
			Thread.sleep(3000);
			
			
			
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
			Excel.setCellValBasedOnTcname(path, "IssueManagement","Verify_CreateRentIncreaseObjection_For_Bronze_Customer_Eligiblity_No" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "IssueManagement","Verify_CreateRentIncreaseObjection_For_Bronze_Customer_Eligiblity_No" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "IssueManagement","Verify_CreateRentIncreaseObjection_For_Bronze_Customer_Eligiblity_No" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
			

}
