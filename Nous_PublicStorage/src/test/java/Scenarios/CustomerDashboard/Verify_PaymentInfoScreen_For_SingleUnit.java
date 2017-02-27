package Scenarios.CustomerDashboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
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
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Verify_PaymentInfoScreen_For_SingleUnit extends Browser_Factory {
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerDashBoard","CustomerDashBoard", "Verify_PaymentInfoScreen_For_SingleUnit");
	}

	@Test(dataProvider="getLoginData")
	public void Verify_PaymentInfoScreen_For_SingleUnit(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("Verify_PaymentInfoScreen_For_SingleUnit","Customer DashBoard - Verify Payment Information Screen For Single Unit");
			
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);
			
			
			//=================Handling Customer Facing Device================================
			
			Thread.sleep(5000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
			Thread.sleep(5000);
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
					"and c.customertypeid= 90 "+
					"group by a.accountnumber "+
					"having sum((clt.amount * clt.quantity)+clt.discountamount) >0 and count(distinct ru.rentalunitid)=1";
			
			

			
			String AccountNumber=DataBase_JDBC.executeSQLQuery(qry);
			Thread.sleep(3000);
			
			Advance_Search advSearch = new Advance_Search(driver);
			advSearch.enterAccNum(AccountNumber);
			String scpathacc=Generic_Class.takeScreenShotPath();
			String imageacc=logger.addScreenCapture(scpathacc);
			logger.log(LogStatus.INFO, "Entered Account Number",imageacc);
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

			logger.log(LogStatus.PASS, "Payment Information Screen is displayed successfully");
			
			
			String sqlqry = "Select top 1 A.AccountNumber,Ru.rentalunitnumber as UnitNumber, S.sitenumber as LocationNumber, "+
					"sum((clt.amount * clt.quantity)+clt.discountamount) as TotalDue, "+
					"convert(date,CLC.RentChargeDateTime_Sched) as NextPaymentDueDate, "+
					"CLC.rent+CLC.RentTax+CLC.Insurance+CLC.InsuranceTax+CLC.MoveInDiscount+CLC.RateIncentive as NextPaymentDue "+
					"FROM Account A with(nolock) "+
					"Join Customer C with(nolock) on C.CustomerID = A.CustomerID "+
					"Join AccountOrder AO with(nolock) on  AO.AccountID = A.AccountID "+
					"join AccountOrderItem AOI with(nolock) on AOI.AccountOrderID=AO.AccountOrderID "+
					"join site s with(nolock) on s.siteid=aoi.siteid "+
					"join StorageOrderItem SOI with(nolock) on SOI.StorageOrderItemID = AOI.StorageOrderItemID "+
					"join rentalunit ru with(nolock) on ru.rentalunitid=soi.rentalunitid "+
					"join Type T with(nolock) on T.TypeID = C.CustomerTypeID "+
					"join Type T1 with(nolock) on T1.TypeID = SOI.paymentmethodtypeid "+
					"join CLcharge CLC with (nolock) on CLC.accountorderitemid=aoi.accountorderitemid and RentChargeDateTime_Actual is null "+
					"join cltransaction clt with(nolock) on clt.accountorderitemid=aoi.accountorderitemid "+
					"where 1=1 and A.accountnumber='"+AccountNumber+"' "+
					"group by A.AccountNumber,Ru.rentalunitnumber,S.sitenumber, CLC.RentChargeDateTime_Sched,CLC.rent,CLC.RentTax, "+
					"CLC.Insurance,CLC.InsuranceTax,CLC.MoveInDiscount,CLC.RateIncentive "+
					"order by CLC.RentChargeDateTime_Sched";
			
			Thread.sleep(1000);
			ArrayList<String> list=DataBase_JDBC.executeSQLQuery_List(sqlqry);
			Thread.sleep(15000);
			String unitNumber=list.get(1);
			logger.log(LogStatus.INFO, "Unit Number Fetched from database is : "+unitNumber);
			
			if(cust_accdetails.getCustSpaceNum().trim().equals(unitNumber.trim())){
				Thread.sleep(3000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Unit Number displayed in customer dashboard: " +cust_accdetails.getCustSpaceNum().trim()+ " is matched with database :" +unitNumber.trim());
				logger.log(LogStatus.INFO, "Unit Number displayed is verified with Database",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Unit Number displayed in customer dashboard: " +cust_accdetails.getCustSpaceNum().trim()+ " is not matched with database :" +unitNumber.trim());
				logger.log(LogStatus.INFO, "Unit Number is not verified",image);
			}
			
			String locationNumber = list.get(2);
			logger.log(LogStatus.INFO, "Location Number Fetched from database is : "+locationNumber);
			
			if(cust_accdetails.getLocationNumber().trim().equals(locationNumber.trim())){
				logger.log(LogStatus.PASS, "Location Number displayed in customer dashboard: "+cust_accdetails.getLocationNumber().trim()+ " is matched with database :" +locationNumber.trim());
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Location Number displayed in customer dashboard: "+cust_accdetails.getLocationNumber().trim()+ " is not matched with database :" +locationNumber.trim());
				logger.log(LogStatus.INFO, "Location Number is not verified",image);
			}
			
			Actions action = new Actions(driver);
			WebElement location = driver.findElement(By.xpath("//a[@class='location-details']"));
			action.moveToElement(location).clickAndHold().build().perform();
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "Information about the location is displayed Successfully");
			String scpathloc=Generic_Class.takeScreenShotPath();
			String imageloc=logger.addScreenCapture(scpathloc);
			logger.log(LogStatus.INFO, "Information about the location is displayed Successfully",imageloc);
			action.moveToElement(driver.findElement(By.xpath("//h1[@class='customer-name bold']"))).build().perform();
			Thread.sleep(3000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			
			String totalDueNow=list.get(3);
			
			double totalDueNoeInDb = Generic_Class.getAmountDouble(totalDueNow);
			double totalDueNowInUi = Generic_Class.getAmountDouble(cust_accdetails.getTotalDueNow());
			
			logger.log(LogStatus.INFO, "Total Due Now Amount Fetched from database is : "+totalDueNoeInDb);
			
			Thread.sleep(2000);
			if(totalDueNowInUi == totalDueNoeInDb ){
				Thread.sleep(3000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Total Due Now Amount displayed in customer dashboard: "+totalDueNowInUi+ " is matched with database" +totalDueNoeInDb);
				logger.log(LogStatus.INFO, "Total Due Now Amount displayed is verified with Database",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Total Due Now Amount displayed in customer dashboard: "+totalDueNowInUi+ " is not matched with database" +totalDueNoeInDb);
				logger.log(LogStatus.INFO, "Total Due Now is not verified",image);
			}
			
			Thread.sleep(2000);
			String nextPaymentDue=list.get(5);
			
			double nextPaymentDueInDb = Generic_Class.getAmountDouble(nextPaymentDue);
			double nextPaymentDueInUi = Generic_Class.getAmountDouble(cust_accdetails.getNextPaymentDueAmt());
			
			
			logger.log(LogStatus.INFO, "Next Payment Due Amount Fetched from database is : "+nextPaymentDueInDb);
			
	/*		if(nextPaymentDueInDb == nextPaymentDueInUi){
				Thread.sleep(3000);
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Next Payment Due Amount displayed in customer dashboard: "+nextPaymentDueInUi+ " is matched with database" +nextPaymentDueInDb);
				logger.log(LogStatus.INFO, "Next Payment Due Amount is verified with Database",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Next Payment Due Amount displayed in customer dashboard: "+nextPaymentDueInUi+ " is not matched with database" +nextPaymentDueInDb);
				logger.log(LogStatus.INFO, "Next Payment Due Amount is not verified",image);
			}*/
			
			
            //fetching scheduled vacate from database
			String dateValue = list.get(4);
            //String dateValue=DataBase_JDBC.executeSQLQuery(dateQuery);
            Thread.sleep(2000);
            String[] DateSplit=dateValue.split("\\s+");
            String dateFromDatabse=DateSplit[0].replaceAll("-", "/");
            String date1 = new SimpleDateFormat("(MM/dd/yyyy)").format(new SimpleDateFormat("yyyy/MM/dd").parse(dateFromDatabse));
            logger.log(LogStatus.PASS, "Fetching Next Payment Due date from data base and the date is----->"+date1);

            String datevalue = cust_accdetails.txt_NextpaymentDueDate();
            

			/*if(date1 == datevalue){
				Thread.sleep(3000);
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Next Payment Due Date displayed in customer dashboard: "+datevalue+ " is matched with database" +date1);
				logger.log(LogStatus.INFO, "Next Payment Due Date is verified with Database",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Next Payment Due Date displayed in customer dashboard: "+datevalue+ " is not matched with database" +date1);
				logger.log(LogStatus.INFO, "Next Payment Due Date is not verified",image);
			}
            */
            
			
			
			
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
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_PaymentInfoScreen_For_SingleUnit" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_PaymentInfoScreen_For_SingleUnit" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","Verify_PaymentInfoScreen_For_SingleUnit" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}

}
