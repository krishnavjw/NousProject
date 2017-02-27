package Scenarios.CustomerDashboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openxmlformats.schemas.drawingml.x2006.main.ThemeDocument;
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
import Pages.CustDashboardPages.ChangePaymentCommitmentPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Scenarios.Browser_Factory;

public class Verify_To_See_Can_Able_To_Change_PaymentCommitment_Date extends Browser_Factory {
	
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerDashBoard","CustomerDashBoard", "Verify_To_See_Can_Able_To_Change_PaymentCommitment_Date");
	}

	@Test(dataProvider="getLoginData")
	public void Verify_To_See_Can_Able_To_Change_PaymentCommitment_Date(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Verify_To_See_Can_Able_To_Change_PaymentCommitment_Date","Customer DashBoard - Verify to see can able to change Payment Commitment Date");
			
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
					
	
			//================================== DM Home Page ===============================================
			
			DM_HomePage dmhomepage=new DM_HomePage(driver);
			Thread.sleep(3000);
			
		
			//Verifying DM Dashboard is displayed
			if (dmhomepage.is_DMDashBoardTitle()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "DM Dashboard is displayed Successfully");
				logger.log(LogStatus.INFO, "DM Dashboard is displayed Successfully", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "DM Dashboard is not displayed");
				logger.log(LogStatus.INFO, "DM Dashboard is not displayed", image);
			}
			
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(3000);
			
			dmhomepage.click_advSearchLink();
			logger.log(LogStatus.INFO, "Clicked on Advance search link");
			Thread.sleep(8000);
			
			String qry = "select top 1 u.accountnumber from vw_unitdetails u "+
					"join phonecall pc on u.StorageOrderItemID = PC.OwnerTableItemID "+
					"join type t on t.typeid = pc.calltypeid "+
					"where u.vacatedate is null "+
					"and u.customertypeid = 90 "+
					"and pc.paymentcommitmentdate > getutcdate() "+
					"and pc.calltypeid = 6013 ";
			
			String AccountNumber = DataBase_JDBC.executeSQLQuery(qry);
			logger.log(LogStatus.INFO, "Account Number fetched from database : "+AccountNumber);
			
			//================================== Advance Search Page==============================
			
			Advance_Search advSearch = new Advance_Search(driver);
			advSearch.enterAccNum(AccountNumber);
			logger.log(LogStatus.INFO, "Entered Account Number is : "+AccountNumber);
			
			Thread.sleep(2000);
			String scpath1 = Generic_Class.takeScreenShotPath();
			String image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "img", image1);
			Thread.sleep(1000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			advSearch.clickButton();
			logger.log(LogStatus.INFO, "Clicked on Search button Successfully");
			Thread.sleep(10000);
			
			//================================== Customer Dashboard ================================
			
			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			Thread.sleep(1000);
			
			//String spacenum = cust_accdetails.getCustSpaceNum();
			
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
			
			cust_accdetails.click_PaymentDate();
			logger.log(LogStatus.INFO, "Clicked on Payment Commitment Date link");
			Thread.sleep(3000);
			
			ChangePaymentCommitmentPage payPage = new ChangePaymentCommitmentPage(driver);
			
			if(payPage.isDisplayed_Title()){
				Thread.sleep(3000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Change Payment Commitment Page is displayed successfully");
				logger.log(LogStatus.INFO, "img",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Change Payment Commitment Page is displayed");
				logger.log(LogStatus.INFO, "img",image);
			}
			Thread.sleep(3000);
			
			payPage.click_Selectbutton();
			Thread.sleep(2000);
			payPage.SelectDateFromCalendar(tabledata.get("EnterDate"));
			logger.log(LogStatus.INFO, "Entered Date New Payment Commitment Date");
			Thread.sleep(3000);
			payPage.enterNotes(tabledata.get("Notes"));
			logger.log(LogStatus.INFO, "Entered Notes : "+tabledata.get("Notes"));
			Thread.sleep(2000);
			payPage.enterEmployeeNum(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered Employee Number : "+tabledata.get("UserName"));
			Thread.sleep(3000);
			String scpath2=Generic_Class.takeScreenShotPath();
			String image2=logger.addScreenCapture(scpath2);
			logger.log(LogStatus.INFO, "img",image2);
			Thread.sleep(2000);
			
			payPage.click_ConfirmBtn();
			logger.log(LogStatus.PASS, "Clicked on Confirm button Successfully");
			Thread.sleep(6000);
			
			try{
				payPage.click_OkBtn();
				Thread.sleep(4000);
			}catch(Exception e){
				
			}
			
			Thread.sleep(3000);
			
			String dateValue = cust_accdetails.getPaymentDate();
			Thread.sleep(2000);
			
			String[] Datesplit = dateValue.split("\\s+");
			String dataFromSplit =Datesplit[1].replaceAll("/", "-");
			String dateFromUI = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM-dd-yyyy").parse(dataFromSplit));
			Thread.sleep(2000);
			
			String qry1 ="select pc.paymentcommitmentdate from phonecall pc "+
					     "join vw_unitdetails u on u.StorageOrderItemID = PC.OwnerTableItemID "+
					     "where u.accountnumber = '"+AccountNumber+"' "+
					     "and u.vacatedate is null ";
			
			
			Thread.sleep(1000);
			
			ArrayList<String> dbdate = DataBase_JDBC.executeSQLQuery_List(qry1);
			String dateFromDb = dbdate.get(0).substring(0, 10).trim();
			Thread.sleep(1000);
				
		
			if(dateFromDb.trim().equals(dateFromUI)){
				
			Thread.sleep(3000);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Date From UI " +dateFromUI+ " is matched with the database date: "+dateFromDb);
			logger.log(LogStatus.INFO, "img",image);
			
			}else{

			if(resultFlag.equals("pass"))
				resultFlag="fail";

			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Date From UI " +dateFromUI+ " is not matched with the database date: "+dateFromDb);
			logger.log(LogStatus.INFO, "img",image);
			
			}
				
			String qry2 = "select startdatetime from appointment "+
			     	  "where description like '%"+AccountNumber+"%' "+
			     	  "order by recorddatetime desc ";
			
			
			ArrayList<String> dbdate1 = DataBase_JDBC.executeSQLQuery_List(qry2);
			String startdateFromDb = dbdate1.get(0).substring(0, 10).trim();
			Thread.sleep(1000);
				
			
			if(dateFromDb.trim().equals(startdateFromDb.trim())){
				
				logger.log(LogStatus.PASS, "Date From Payment Commitment " +dateFromDb+ " is matched with the database date: "+startdateFromDb);
			
			}else{

				logger.log(LogStatus.FAIL, "Date From Payment Commitment " +dateFromDb+ " is not matched with the database date: "+startdateFromDb);
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
				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_To_See_Can_Able_To_Change_PaymentCommitment_Date" , "Status", "Pass");

			}else if (resultFlag.equals("fail")){

				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_To_See_Can_Able_To_Change_PaymentCommitment_Date" , "Status", "Fail");
			}else{
				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_To_See_Can_Able_To_Change_PaymentCommitment_Date" , "Status", "Skip");
			}


			extent.endTest(logger);
			extent.flush();
			Reporter.log("Test case completed: " +testcaseName, true);

		}
				
				

}
