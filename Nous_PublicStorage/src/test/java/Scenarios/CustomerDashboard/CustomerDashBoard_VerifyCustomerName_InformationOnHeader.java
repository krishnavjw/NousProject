package Scenarios.CustomerDashboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.jboss.netty.util.internal.StackTraceSimplifier;
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
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class CustomerDashBoard_VerifyCustomerName_InformationOnHeader extends Browser_Factory {


	String path = Generic_Class.getPropertyValue("Excelpath");
	public ExtentTest logger;
	String resultFlag="pass";
	@DataProvider
	public Object[][] getDta() 
	{
		return Excel.getCellValue_inlist(path, "CustomerDashBoard","CustomerDashBoard","CustomerDashBoard_VerifyCustomerName_InformationOnHeader");
	}


	@Test(dataProvider="getDta")	
	public void CustomerDashBoard_VerifyCustomerName_InformationOnHeader(Hashtable<String, String> tabledata) throws Exception 
	{
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the  CustomerDashBoard_VerifyCustomerName_InformationOnHeader test");
		}
		try{
			logger=extent.startTest("CustomerDashBoard_VerifyCustomerName_InformationOnHeader", "Verify customer name and information on header");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage loginPage = new LoginPage(driver);
			String sitenumber=loginPage.get_SiteNumber();
			Thread.sleep(2000);
			loginPage.enterUserName(tabledata.get("UserName"));
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "entered username ");
			Thread.sleep(2000);
			loginPage.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.PASS, "entered password ");
			Thread.sleep(2000);
			loginPage.clickLogin();
			logger.log(LogStatus.PASS, "clicked on login in button");
			//=================Handling customer facing device========================
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
			//=====================================================================
			Thread.sleep(4000);
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			logger.log(LogStatus.PASS, "logged into application as PM  successfully");
			if(pmhomepage.isexistingCustomerModelDisplayed()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User lands on the PM DashBoard sucessfully");
				logger.log(LogStatus.INFO, "img",image);
				resultFlag="pass";
			}
			else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "User does not lands on the PM DashBaord");
				logger.log(LogStatus.INFO, "img",image);

			}
			Thread.sleep(3000);
			pmhomepage.clk_AdvSearchLnk();
			logger.log(LogStatus.PASS, "clicked on advanced search link in the PM Dashboard sucessfully");

			Thread.sleep(6000);
			Advance_Search advSearch=new Advance_Search(driver);
			Thread.sleep(4000);

			String query="Select top 1 A.AccountNumber, C.FirstName, C.LastName,CC.ClassName,CU.customertypeid,t1.name as militarytype "+
					"from Account A with(nolock) "+
					"Join AccountAddress AA with(nolock) on AA.AccountID = A.AccountID "+
					"Join Address Ad with(nolock) on AA.AddressID = Ad.AddressID "+
					"Join Customer CU with(nolock) on A.CustomerID = CU.CustomerID "+
					"Join Contact C with(nolock) on CU.ContactID = C.ContactID "+
					"Join CustomerClass CC with(nolock) on CU.CustomerClassID = CC.CustomerClassID "+
					"Join AccountOrder AO with(nolock) on A.AccountID = AO.AccountID "+
					"Join AccountOrderItem AOI with(nolock) on AO.AccountOrderID  = AOI.AccountOrderID "+
					"Join StorageOrderItem SOI with(nolock)on AOI.StorageOrderItemID = SOI.StorageOrderItemID "+
					"Join Type T with(nolock) on T.TypeID = SOI.StorageOrderItemTypeID "+
					"left join customermilitaryinfo cmi with(nolock) on cmi.customerid=Cu.customerid "+
					"left join Type T1 with(nolock) on T1.typeid=cmi.militarytypeid "+
					"Where 1=1 "+
					"And SOI.VacateDate is Null "+
					"And CU.CustomerTypeID=90 "+
					"And t1.name='Active Duty'  and cmi.isactive=1";

			ArrayList<String> list=DataBase_JDBC.executeSQLQuery_List(query);
			String AccountNumber=list.get(0);
			logger.log(LogStatus.INFO, "fetching individual customer account number from database----->"+AccountNumber);
			Thread.sleep(2000);

			advSearch.enterAccNum(AccountNumber);
			logger.log(LogStatus.INFO, "Entered account number---->"+AccountNumber);

			Thread.sleep(3000);
			if(advSearch.verifyAdvSearchPageIsOpened())
			{
				logger.log(LogStatus.PASS, "Advanced Search page is Displayed successfully");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);

			}
			else{
				logger.log(LogStatus.FAIL, "Advanced Search page is not opened");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Image",image);

			}


			Thread.sleep(3000);
			advSearch.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "clicking on Search button successfully");

			Cust_AccDetailsPage cust= new Cust_AccDetailsPage(driver);
			Thread.sleep(10000);
			if(cust.isCustdbTitleDisplayed()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "customer Dashboard is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");
				logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ",image);
			}
			logger.log(LogStatus.INFO, "Verifying customer information on the customer dashboard ");
			if(cust.getcustCurrAccno().equals(AccountNumber)){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Account number from the dashboard matches the account number entered for search");
				logger.log(LogStatus.INFO, "img",image);


			}else{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Account number from the dashboard matches does match with the account number entered for search");
				logger.log(LogStatus.INFO, "img",image);
			}

			if(cust.isSpaceNumDisplayed()){
				logger.log(LogStatus.INFO, "space number is displayed on the customer dashboard");
				logger.log(LogStatus.INFO, "Space number on customer dashboard--->"+cust.getCustSpaceNum());
			}else{
				logger.log(LogStatus.INFO, "multiple space number are displaying in the customer dashboard");
			}

			String str=driver.findElement(By.xpath("//div[@id='customerDashboard']//div//h1[@class='customer-name bold']")).getText().trim();
			String[] splitStr = str.split("\\s+");
			int sizearray = splitStr.length;
			String firstName=splitStr[0];	
			String lastName=splitStr[sizearray-1];

			Thread.sleep(2000);
			String custFirstName=list.get(1);
			String custLastName=list.get(2);


			if(custFirstName.contains(firstName)){
				
				logger.log(LogStatus.INFO, "Customer first name from database--->"+custFirstName+"Customer first name from UI--->"+firstName+"  are matching");

			}else{

				logger.log(LogStatus.FAIL, "Customer first name from Database and Customer first name in the customer dashboard are not matching");
			}

			if(custLastName.contains(lastName)){
				
				logger.log(LogStatus.INFO, "Customer last name from database--->"+custLastName+"Customer last name from UI--->"+lastName+"  are matching");
			}else{

				logger.log(LogStatus.FAIL, "Customer last name from Database and Customer last name in the customer dashboard are not matching");
			}

			logger.log(LogStatus.INFO, "Verifying type of customer against database");
			String type=list.get(4);
			String Query1="select name from type where typeid='"+type+"' ";
			String custType=DataBase_JDBC.executeSQLQuery(Query1);
			

			if(cust.getTypeOfCustomer().contains(custType)){

				logger.log(LogStatus.PASS, "Type of customer from database---->"+custType+" Type of customer from Customer dashboard--->"+cust.getTypeOfCustomer()+"  are matching");
				String snap3=Generic_Class.takeScreenShotPath();
				String imr3=logger.addScreenCapture(snap3);
				logger.log(LogStatus.INFO, "img",imr3);

			}else{
				logger.log(LogStatus.FAIL, "Type of customer from databse and type of customer from Customer dashboard are not matching");
			}

			//logger.log(LogStatus.INFO, "Verifying the Customer Classification level againest database");
			//String custLevel=list.get(3);


			//if(cust.getCustomerLevel().contains(custLevel)){

				//logger.log(LogStatus.INFO, "customer grade level from database-->"+custLevel+"customer grade level from UI-->"+cust.getCustomerLevel()+"  are matching");
			//}else{

				//logger.log(LogStatus.FAIL, "Customer Classification level from databse and Customer dashBoard are not matching");

			//}

			if(cust.isBackToDashBaordDisplayed()){

				logger.log(LogStatus.PASS, "Back to dashbaord header is displayed on customer dashboard successfully");
			}else{

				logger.log(LogStatus.FAIL, "Back to dashbaord header is not displayed on customer dashboard ");
			}

			if(cust.isCustdbTitleDisplayed()){
				logger.log(LogStatus.PASS, "Confirm Customer's Identity and Contact Information header is displayed successfully on customer dashboard");
				String snap4=Generic_Class.takeScreenShotPath();
				String imr4=logger.addScreenCapture(snap4);
				logger.log(LogStatus.INFO, "img",imr4);

			}else{

				logger.log(LogStatus.FAIL, "Confirm Customer's Identity and Contact Information header is not displayed  on customer dashboard");
			}

			if(cust.isSpaceDetailsTabDisplayed()){
				logger.log(LogStatus.PASS, "space details tab is  displayed on customer dashboard");
			}else{
				logger.log(LogStatus.FAIL, "space details tab is not displayed on customer dashboard");
			}

			if(cust.isAccountAcctivitiesTabDisplayed()){
				logger.log(LogStatus.PASS, "Account accitivities details tab is  displayed on customer dashboard");
			}else{
				logger.log(LogStatus.FAIL, "Account accitivities tab is not displayed on customer dashboard");
			}

			if(cust.verify_custInfo_Tab()){
				logger.log(LogStatus.PASS, "Customer information details tab is  displayed on customer dashboard");
			}else{
				logger.log(LogStatus.FAIL, "Customer information tab is not displayed on customer dashboard");
			}

			if(cust.verifyDocumentsTab()){
				logger.log(LogStatus.PASS, "documents details tab is  displayed on customer dashboard");
			}else{
				logger.log(LogStatus.FAIL, "documents  tab is not displayed on customer dashboard");
			}

			if(cust.verifyImportantInformationSection()){
				logger.log(LogStatus.PASS, "important information header is  displayed on customer dashboard");
			}else{
				logger.log(LogStatus.FAIL, "important information header is not displayed on customer dashboard");
			}

			if(cust.verify_CreateNote()){
				logger.log(LogStatus.PASS, "create note header is  displayed on customer dashboard");
			}else{
				logger.log(LogStatus.FAIL, "create note header is not displayed on customer dashboard");
			}

			if(cust.quicklinkDrpDownDispalyed()){
				logger.log(LogStatus.PASS, "quicklinks header is  displayed on customer dashboard");
			}else{
				logger.log(LogStatus.FAIL, "quicklinks header is not displayed on customer dashboard");
			}
			
			
			try{
			if(cust.is_makePayement_Link()){
				logger.log(LogStatus.PASS, "Make payment header is  displayed on customer dashboard");
			}else{
				logger.log(LogStatus.FAIL, "make payment header is not displayed on customer dashboard");
			}
			}catch(Exception e){
				
			}
			if(cust.verifyManageAutopayLink()){
				logger.log(LogStatus.PASS, "manage  autopay header is  displayed on customer dashboard");
			}else{
				logger.log(LogStatus.FAIL, "manage  autopay header is not displayed on customer dashboard");
			}

			if(cust.verifyViewDetailsLink()){
				logger.log(LogStatus.PASS, "view details header is  displayed on customer dashboard");
			}else{
				logger.log(LogStatus.FAIL, "view details header is not displayed on customer dashboard");
			}




		}catch(Exception e){
			resultFlag="fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "------------- page is not displayed",image);
			e.printStackTrace();
		}

	}

	@AfterMethod
	public void afterMethod(){
		Reporter.log(resultFlag,true);
		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashBoard_VerifyCustomerName_InformationOnHeader" , "Status", "Pass");
		}else if (resultFlag.equals("fail")){
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashBoard_VerifyCustomerName_InformationOnHeader" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashBoard_VerifyCustomerName_InformationOnHeader" , "Status", "Skip");
		}
		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);
	}

}
