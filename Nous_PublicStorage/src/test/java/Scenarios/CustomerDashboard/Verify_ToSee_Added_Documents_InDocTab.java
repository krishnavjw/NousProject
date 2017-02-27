package Scenarios.CustomerDashboard;


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
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.host.dom.Document;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import GenericMethods.DataBase_JDBC;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.AddDocumentPopUpPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.Documents;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Verify_ToSee_Added_Documents_InDocTab extends Browser_Factory {
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");
	String uploadpath=Generic_Class.getPropertyValue("uploadFile");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerDashBoard","CustomerDashBoard", "Verify_ToSee_Added_Documents_InDocTab");
	}

	@Test(dataProvider="getLoginData")
	public void Verify_ToSee_Added_Documents_InDocTab(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Verify_ToSee_Added_Documents_InDocTab","Customer DashBoard - Verify To See Added Documents In Document Tab");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);
			
			
			//=================Handling Customer Facing Device================================
			
			Thread.sleep(5000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
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
			
			String qry ="Select Top 1 A.AccountNumber "+
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
					"and c.customertypeid= 90 "+
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

			
			String spaceNumber = cust_accdetails.getCustSpaceNum().trim();
			logger.log(LogStatus.INFO, "Space Number for the rented space");
			Reporter.log("Space Number : "+spaceNumber,true);
			cust_accdetails.clk_DocumentsTab();
			Thread.sleep(3000);
			Documents doc = new Documents(driver);
			logger.log(LogStatus.INFO, "Clicked on Documents Tab Successfully");
			if(doc.verify_AddDocument()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Add Document Button is displayed successfully");
				logger.log(LogStatus.INFO, "Add Document Button is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Add Document Button is not displayed");
				logger.log(LogStatus.INFO, "Add Document Button is not displayed",image);
			}
			
			//Clicking on Add document
			doc.Clk_AddDocument();
			Thread.sleep(4000);
			logger.log(LogStatus.INFO, "Clicked on Add document Button");
			AddDocumentPopUpPage addDoc = new AddDocumentPopUpPage(driver);
			if(addDoc.verify_title()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Add Document Popup Page is displayed successfully");
				logger.log(LogStatus.INFO, "Add Document Popup Page is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Add Document Popup Page is not displayed");
				logger.log(LogStatus.INFO, "Add Document Popup Page is not displayed",image);
			}
			
			addDoc.sel_DocType();
			Thread.sleep(4000);
			logger.log(LogStatus.INFO, "Selected Value in Document Type Dropdown");
			addDoc.clk_SpaceDropdown();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[@class='k-animation-container']//ul/li[contains(text(),'"+spaceNumber+"')]")).click();
			Thread.sleep(2000);
			addDoc.clk_UploadBtn();
			Thread.sleep(3000);
			Generic_Class uploadfile= new Generic_Class();
			uploadfile.uploadFileUsingrobot(uploadpath);
			Thread.sleep(20000);
			logger.log(LogStatus.INFO, "pdf file uploaded in the Document");
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "pdf file uploaded",image1);
			Thread.sleep(2000);
			addDoc.clk_SaveBtn();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Save Button Successfully");
			
			//SimpleDateFormat stf = new SimpleDateFormat("hh:mm a");
			//Date time = new Date();
			//String CurrentTime = stf.format(time);
			//Reporter.log("Current Time : "+CurrentTime,true);
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			String CurrentDate = sdf.format(date);
			Reporter.log("Current Date : "+CurrentDate,true);
			
			
			String qry1 = "select dbo.fn_sitedatetime((select siteid from site where sitenumber='"+SiteNumber+"'),getutcdate())";
			String siteTime = DataBase_JDBC.executeSQLQuery(qry1);
			
			
            Thread.sleep(2000);
            String[] DateSplit=siteTime.split("\\s+");
            String dateFromDatabse=DateSplit[1];
            String date1 = new SimpleDateFormat("hh:mm a").format(new SimpleDateFormat("HH:mm:ss").parse(dateFromDatabse));
			
			Thread.sleep(1000);
			if(doc.get_Date().trim().equals(CurrentDate.trim())){
				logger.log(LogStatus.PASS, "Current Date "+CurrentDate.trim()+ " is matched with the created date "+doc.get_Date().trim());
			}else{

				logger.log(LogStatus.FAIL, "Current Date "+CurrentDate.trim()+ " is not matched with the created date "+doc.get_Date().trim());
			}
			
			Thread.sleep(1000);
			if(doc.get_Time().trim().equals(date1)){
				logger.log(LogStatus.PASS, "Current Time "+date1+ " is matched with the created time "+doc.get_Time().trim());
			}else{
				logger.log(LogStatus.FAIL, "Current Time "+date1+ " is not matched with the created time "+doc.get_Time().trim());
			}

			Thread.sleep(1000);
			if(doc.get_Space().equals(spaceNumber)){
				logger.log(LogStatus.PASS, "Space Number  "+spaceNumber+ " is matched with the created space "+doc.get_Space());
			}else{
				logger.log(LogStatus.FAIL, "Space Number  "+spaceNumber+ " is not matched with the created space "+doc.get_Space());
			}
			
			Thread.sleep(1000);
			if(doc.get_Format().equalsIgnoreCase(tabledata.get("Format"))){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Format " +tabledata.get("Format")+ " is matched with created format" +doc.get_Format());
				logger.log(LogStatus.INFO, "Format is matched with created format",image);
			}else{
				logger.log(LogStatus.FAIL, "Format " +tabledata.get("Format")+ " is matched with created format" +doc.get_Format());
			}
			
			Thread.sleep(4000);
			doc.clk_ViewBtn();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked on View button Successfully");
			//driver.switchTo().window(tabs.get(1));
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Document is  displayed in new window");
			logger.log(LogStatus.INFO, "Document is  displayed in new window",image);
	
			
			
			
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
				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_ToSee_Added_Documents_InDocTab" , "Status", "Pass");

			}else if (resultFlag.equals("fail")){

				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_ToSee_Added_Documents_InDocTab" , "Status", "Fail");
			}else{
				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_ToSee_Added_Documents_InDocTab" , "Status", "Skip");
			}


			extent.endTest(logger);
			extent.flush();
			Reporter.log("Test case completed: " +testcaseName, true);

		}


}
