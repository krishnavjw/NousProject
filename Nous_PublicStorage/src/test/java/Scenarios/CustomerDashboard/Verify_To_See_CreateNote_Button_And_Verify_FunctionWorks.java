package Scenarios.CustomerDashboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.CreateNotePage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Verify_To_See_CreateNote_Button_And_Verify_FunctionWorks extends Browser_Factory{
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerDashBoard","CustomerDashBoard", "Verify_To_See_CreateNote_Button_And_Verify_FunctionWorks");
	}

	@Test(dataProvider="getLoginData")
	public void Verify_To_See_CreateNote_Button_And_Verify_FunctionWorks(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Verify_To_See_CreateNote_Button_And_Verify_FunctionWorks","Customer DashBoard - Verify To see Create Note Button And Verify Function works");
			
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
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
					
			//================================== PM Home Page ===============================================
			
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			logger.log(LogStatus.PASS, "Created object for the PM home page Sucessfully");
			Thread.sleep(3000);
			
			String SiteNumber = pmhomepage.getLocation();
			logger.log(LogStatus.PASS, "location number is:"+SiteNumber);
			
			//Verifying PM Dash Board is displayed
			if (pmhomepage.get_WlkInCustText().trim().equalsIgnoreCase(tabledata.get("walkInCustomerTitle"))) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User Lands in PM Dashboard Successfully");
				logger.log(LogStatus.INFO, "User Lands in PM Dashboard Successfully", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "PM Dashboard is not displayed");
				logger.log(LogStatus.INFO, "PM Dashboard is not displayed", image);

			}
			
			if (pmhomepage.get_existingCustomerText().trim().equalsIgnoreCase(tabledata.get("ExistingCustomerTitle"))) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Existing Customer is displaying in PM Dashboard Successfully");
				logger.log(LogStatus.INFO, "Existing Customer is displaying in PM Dashboard Successfully", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Existing Customer is not displayed");
				logger.log(LogStatus.INFO, "Existing Customer is not displayed", image);

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
			Advance_Search advSearch = new Advance_Search(driver);
			logger.log(LogStatus.INFO, "Created Object for advance Search page");
			advSearch.enterAccNum(AccountNumber);
			logger.log(LogStatus.INFO, "Entered Account Number is : "+AccountNumber);
			Thread.sleep(2000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			advSearch.clickButton();
			logger.log(LogStatus.INFO, "Clicked on Search button Successfully");
			Thread.sleep(10000);
			
			//================================== Customer Dashboard ===============================================
			
			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			logger.log(LogStatus.PASS, "Created object for Customer dash board page");
			
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
			
			if(cust_accdetails.verify_CreateNote()){
				Thread.sleep(3000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Create Note is displayed below Important Information successfully");
				logger.log(LogStatus.INFO, "Create Note is displayed below Important Information successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Create Note is not displayed");
				logger.log(LogStatus.INFO, "Create Note is not displayed",image);
			}
			Thread.sleep(3000);
			
			cust_accdetails.clk_CreateNoteBtn();
			Thread.sleep(4000);
			logger.log(LogStatus.INFO,"Clicked on Create Note Button Successfully");
			
			//================================== Create Note Page ===============================================
			
			CreateNotePage createnote = new CreateNotePage(driver);
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Created Object for Create Note Page");
			
			if(createnote.verify_CreateNoteTitle()){
				Thread.sleep(3000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Create Note page is displayed successfully");
				logger.log(LogStatus.INFO, "Create Note page is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Create Note page is not displayed");
				logger.log(LogStatus.INFO, "Create Note page is not displayed",image);
			}
			Thread.sleep(6000);
			
			if(createnote.verify_CategoryDropdown()){
				logger.log(LogStatus.PASS, "Category Dropdown is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Category Dropdown is not displayed");
			}
			
			if(createnote.verify_applyNoteToDropdown()){
				logger.log(LogStatus.PASS, "Apply Note To Dropdown is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Apply Note To Dropdown is not displayed");
			}
			
			if(createnote.verify_NoteTxt()){
				logger.log(LogStatus.PASS, "Note Text Box is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Note Text Box is not displayed");
			}
			
			if(createnote.verify_EmployeeTxt()){
				logger.log(LogStatus.PASS, "Employee Text Box is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Employee Text Box is not displayed");
			}
			
			if(createnote.verify_CreateNoteBtn()){
				logger.log(LogStatus.PASS, "Create Note button is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Create Note button is not displayed");
			}
			
			if(createnote.verify_CancelBtn()){
				logger.log(LogStatus.PASS, "Cancel button is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Cancel button is not displayed");
			}
			
			if(createnote.verify_RecentNotesHistory()){
				logger.log(LogStatus.PASS, "Recent Notes History is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Recent Notes History is not displayed");
			}
			
			if(createnote.verify_Date()){
				logger.log(LogStatus.PASS, "Date Column is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Date Column is not displayed");
			}
			
			if(createnote.verify_Time()){
				logger.log(LogStatus.PASS, "Time Column is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Time Column is not displayed");
			}
			
			if(createnote.verify_Category()){
				logger.log(LogStatus.PASS, "Category Column is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Category Column is not displayed");
			}
			
			if(createnote.verify_Space()){
				logger.log(LogStatus.PASS, "Space Column is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Space Column is not displayed");
			}
			
			if(createnote.verify_Note()){
				logger.log(LogStatus.PASS, "Note Column is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Note Column is not displayed");
			}
			
			Thread.sleep(6000);
			if(createnote.verify_Notetaker()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "NoteTaker Column is displayed successfully");
				logger.log(LogStatus.INFO, "NoteTaker Column is displayed successfully",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "NoteTaker Column is not displayed");
				logger.log(LogStatus.INFO, "NoteTaker Column is not displayed",image);
			}
			Thread.sleep(3000);
			
			//Verifying the validation messages
			createnote.clk_CreateNoteBtn();
			Thread.sleep(2000);
			
			if(createnote.verify_CatValidation()){
				logger.log(LogStatus.PASS, "Please select a category validation message is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Please select a category validation message is not displayed");
			}
			
			if(createnote.verify_applyValidation()){
				logger.log(LogStatus.PASS, "Please select an option validation message is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Please select an option validation message is not displayed");
			}
			
			if(createnote.verify_NoteValidation()){
				logger.log(LogStatus.PASS, "Please enter a note validation message is displayed successfully");
			}else{
				logger.log(LogStatus.FAIL, "Please enter a note validation message is not displayed");
			}
			Thread.sleep(2000);
			
			if(createnote.verify_empValidation()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Please enter an employee number is displayed successfully");
				logger.log(LogStatus.INFO, "Please enter an employee number is displayed successfully",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Please enter an employee number is not displayed");
				logger.log(LogStatus.INFO, "Please enter an employee number is not displayed",image);
			}
			Thread.sleep(3000);
			
			//================================== Enter valid details ===============================================
			
			createnote.select_Category(tabledata.get("Category"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Selected Category from the Category Dropdown : "+tabledata.get("Category"));
			createnote.select_ApplyNoteTo(tabledata.get("ApplyNoteTo"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Selected Apply Note to from the dropdown : "+tabledata.get("ApplyNoteTo"));
			createnote.enterNote(tabledata.get("Notes"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Note in Note text box : "+tabledata.get("Notes"));
			createnote.enterEmployeeNumber(tabledata.get("UserName"));
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered Employee Number : "+tabledata.get("UserName"));
			Thread.sleep(4000);
			String scpathre=Generic_Class.takeScreenShotPath();
			String imagere=logger.addScreenCapture(scpathre);
			logger.log(LogStatus.INFO, "Entered Valid values",imagere);
			createnote.clk_CreateNoteBtn();
			Thread.sleep(4000);
			logger.log(LogStatus.PASS, "Clicked on Create Note Button Successfully");
			
			cust_accdetails.clk_CreateNoteBtn();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Create Note button");
			Thread.sleep(3000);
			boolean notes = driver.findElement(By.xpath("//div[@id='customerNoteDialog']//td[contains(text(),'"+tabledata.get("Notes")+"')]")).isDisplayed();
			if(notes){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Entered Notes is displayed in Recent Notes History successfully");
				logger.log(LogStatus.INFO, "Entered Notes is displayed in Recent Notes History successfully",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Entered Notes is not displayed");
				logger.log(LogStatus.INFO, "Entered Notes is not displayed",image);
			}
			Thread.sleep(6000);
			
			
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
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_To_See_CreateNote_Button_And_Verify_FunctionWorks" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_To_See_CreateNote_Button_And_Verify_FunctionWorks" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_To_See_CreateNote_Button_And_Verify_FunctionWorks" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
			
			

}
