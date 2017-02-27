package Scenarios.CustomerDashboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import org.apache.xerces.util.SynchronizedSymbolTable;
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
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccActivitiesPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.VacatePages.VacateConfirmationPopUp;
import Pages.VacatePages.VacateNowPayment;
import Pages.VacatePages.Vacate_PendingUnvacateConfirmationPopUpPage;
import Pages.VacatePages.Vacate_VacateNowPopup;
import Scenarios.Browser_Factory;
import VacateNowPages.Vacate_MisChrgespage;
import VacateNowPages.Vacate_Selspacepage;

public class CustomerDashBoard_VerifyUnvacate_LaunchFucntion extends Browser_Factory {

	String path = Generic_Class.getPropertyValue("Excelpath");
	public ExtentTest logger;
	String resultFlag="pass";
	@DataProvider
	public Object[][] getDta() 
	{
		return Excel.getCellValue_inlist(path, "CustomerDashBoard","CustomerDashBoard","CustomerDashBoard_VerifyUnvacate_LaunchFucntion");
	}


	@Test(dataProvider="getDta")	
	public void CustomerDashBoard_VerifyUnvacate_LaunchFucntion(Hashtable<String, String> tabledata) throws Exception 
	{
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the  CustomerDashBoard_VerifyUnvacate_LaunchFucntion test");
		}
		try{
			logger=extent.startTest("CustomerDashBoard_VerifyUnvacate_LaunchFucntion", "Verify Unvacate Launch Fucntion");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			Thread.sleep(2000);
			String IpAddress=Generic_Class.getIPAddress();
			Thread.sleep(2000);

			String custquery="Select top 1 a.accountnumber,s.siteid,ru.rentalunitnumber,t.name as customertype,  count(distinct ru.rentalunitid) as NofCurrentunits, sum(clt.amount+clt.discountamount) as Currentbalance "+
					"from contact c with (nolock) "+
					"join customer cu with (nolock) on cu.contactid=c.contactid "+
					"join account a on a.customerid=cu.customerid "+
					"join accountorder ao with (nolock) on ao.accountid=a.accountid "+
					"join accountorderitem aoi with (nolock) on aoi.accountorderid=ao.accountorderid "+
					"join storageorderitem soi with (nolock) on soi.storageorderitemid=aoi.storageorderitemid "+ 
					"join rentalunit ru with (nolock) on ru.rentalunitid=soi.rentalunitid "+
					"join type t with (nolock) on t.typeid=cu.customertypeid "+
					"join site s with (nolock) on s.siteid=aoi.siteid "+
					"join cltransaction clt with(nolock) on clt.accountorderitemid=aoi.accountorderitemid "+
					"join glaccount gl with(nolock) on gl.glaccountid=clt.glaccountid "+
					"where cu.customertypeid=90 "+
					"and soi.vacatedate is null "+
					"group by a.accountnumber, ru.rentalunitnumber,t.name,s.siteid "+
					"having "+
					"sum(clt.amount+clt.discountamount)>0 "+
					"and count(distinct ru.rentalunitid)=1 ";

			Thread.sleep(1000);
			ArrayList<String> list=DataBase_JDBC.executeSQLQuery_List(custquery);
			String SiteId=list.get(1);
			System.out.println(list.get(1));
			String AccountNumber=list.get(0);
		    System.out.println(list.get(0));

			Thread.sleep(1000);
			String getSiteIDSetAlready="select * from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"+IpAddress+"' ";
			ArrayList<String> list1=DataBase_JDBC.executeSQLQuery_List(getSiteIDSetAlready);
			Thread.sleep(10000);
			String alreadySetSiteId=list1.get(2);

			Thread.sleep(1000);
			String updateQuery="Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid='"+alreadySetSiteId+"' ";
			DataBase_JDBC.executeSQLQuery(updateQuery);

			Thread.sleep(1000);
			String updateSiteID="Update siteparameter set paramvalue='"+IpAddress+"' where paramcode='IP_COMPUTER_FIRST' and siteid='"+SiteId+"' ";
			DataBase_JDBC.executeSQLQuery(updateSiteID);
			Thread.sleep(1000);


			driver.get(driver.getCurrentUrl());
			Thread.sleep(2000);
			driver.get(driver.getCurrentUrl());
			Thread.sleep(2000);

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
			logger.log(LogStatus.INFO, "searching Individual customer in existing customer module in PM DashBoard");
			logger.log(LogStatus.INFO, "fetching individual customer account number from database----->"+AccountNumber);
			Thread.sleep(2000);
			pmhomepage.enter_findCustAddrLocation(AccountNumber);
			logger.log(LogStatus.PASS, "Entered account number in Existing customer module search field");
			Thread.sleep(1000);
			String snap1=Generic_Class.takeScreenShotPath();
			String imr1=logger.addScreenCapture(snap1);
			logger.log(LogStatus.INFO, "img",imr1);
			pmhomepage.clk_findCustomer();
			logger.log(LogStatus.PASS, "Clicked on Find Customer Button in PM DashBoard");
			Thread.sleep(8000);

			Advance_Search advSearch=new Advance_Search(driver);
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
			Thread.sleep(2000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(4000);
			if(advSearch.get_CustomerAccountNum().equals(AccountNumber)){

				logger.log(LogStatus.PASS, "Advance search page is displayed with the search result successfully");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);

			}
			else{
				logger.log(LogStatus.FAIL, "Advanced Search page is not displayed");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Image",image);

			}
			Thread.sleep(1000);
			advSearch.clickOnAccNum();
			logger.log(LogStatus.INFO, "clicked on the account number of the customer in advance search page successfully");
			Thread.sleep(10000);	
			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			Thread.sleep(4000);
			if(cust_accdetails.isCustdbTitleDisplayed()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "customer account Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "img",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");
				logger.log(LogStatus.FAIL, "img ",image);
			}

			Thread.sleep(2000);

			logger.log(LogStatus.INFO, "Verifying customer information on the customer dashboard ");
			if(cust_accdetails.getcustCurrAccno().equals(AccountNumber)){

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

			Thread.sleep(3000);
			cust_accdetails.clickSpaceDetails_tab();
			logger.log(LogStatus.PASS, "Clicked On Space Details Tab successfully in customer dashbaord");
			Thread.sleep(3000);
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,2000)");
			Thread.sleep(2000);

			//click on  vacate Now button

			Acc_SpaceDetailsPage spacedetails=new Acc_SpaceDetailsPage(driver);
			spacedetails.click_VacateNow_Btn();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "Clicked On  Vacate Now button successfully");

			//verifying vacate now popup 
			Thread.sleep(3000);
			Vacate_VacateNowPopup vacateNowPopup=new Vacate_VacateNowPopup(driver);
				String scpath6=Generic_Class.takeScreenShotPath();
				String image2=logger.addScreenCapture(scpath6);
				logger.log(LogStatus.PASS, "Vacate now popup/ model window is displayed");
				logger.log(LogStatus.INFO, "img",image2);

			
			Thread.sleep(2000);
			vacateNowPopup.clickContinue_btn();
			logger.log(LogStatus.PASS, "Clicked on Continue Button Sucessfully");

			Vacate_Selspacepage SelSpacePage=new  Vacate_Selspacepage(driver); 
			Thread.sleep(2000);

			if(SelSpacePage.isSelectSpacesTxtDisplayed()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Select Spaces page is displayed sucessfully");
				logger.log(LogStatus.INFO, "Select Spaces page is displayed sucessfully",image);

			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Select Spaces page is not displayed sucessfully");
				logger.log(LogStatus.INFO, "Select Spaces page is not displayed sucessfully",image);
			}

			Thread.sleep(2000);
			SelSpacePage.click_Continuebutton();
			logger.log(LogStatus.PASS, "Clicked on Continue button successfully");
			Thread.sleep(5000);

		/*	Vacate_MisChrgespage MiscChargePage=new Vacate_MisChrgespage(driver);
			Thread.sleep(3000);
			MiscChargePage.clk_continue_Btn();
			logger.log(LogStatus.PASS, "Clicked on Continue button successfully");
			Thread.sleep(8000);
*/
			
			Vacate_MisChrgespage MiscChargePage=new Vacate_MisChrgespage(driver);
			Thread.sleep(2000);
			MiscChargePage.selectmischarges("Cleaning Fee",driver);
			Thread.sleep(2000);

			MiscChargePage.clk_addChaargeBtn();
			logger.log(LogStatus.PASS, "Clicked on Add Charge button Successfully ");
			Thread.sleep(2000);


			Thread.sleep(2000);
			MiscChargePage.enter_misc_note("Cleaning fee");
			logger.log(LogStatus.PASS, "Note Entered Successfully ");
			Thread.sleep(2000);
			MiscChargePage.clk_NoMaintenance();
			logger.log(LogStatus.PASS, "Click No for Maintenance Successfully ");
			Thread.sleep(2000);
			MiscChargePage.clk_continue_Btn();
			logger.log(LogStatus.PASS, "Click on continue button Successfully ");
			Thread.sleep(10000);
			
			VacateNowPayment pymnt = new VacateNowPayment(driver);
			Thread.sleep(2000);

			if(pymnt.isPaymentPageDisplayed()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Payment page is displayed successfully");
				logger.log(LogStatus.INFO, "Payment page is displayed successfully",image);

			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Payment page is not displayed");
				logger.log(LogStatus.INFO, "Payment page is not displayed",image);

			}

			Thread.sleep(5000);
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,2000)");
			Thread.sleep(4000);

			pymnt.clk_ConfirmWithCustomerBtn();
			logger.log(LogStatus.INFO, "clicked on Confirm with customer button successfully in payment screen");
			Thread.sleep(5000);

			driver.switchTo().window(tabs.get(1));
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			
			Thread.sleep(6000);

			String snap2=Generic_Class.takeScreenShotPath();
			String ims1=logger.addScreenCapture(snap2);
			logger.log(LogStatus.PASS, "customer facing screen is displayed successfully");
			logger.log(LogStatus.INFO, "img",ims1);

			Thread.sleep(2000); 

			driver.findElement(By.xpath("//button[@id='confirmButton']")).click();
			Thread.sleep(5000);

			driver.switchTo().window(tabs.get(0));
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(6000);

			Thread.sleep(5000);
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,2000)");
			Thread.sleep(4000);

			String pymtTotalDueAmt = pymnt.getTotalDue();
			logger.log(LogStatus.INFO, "toatl due amount in payment page is:"+pymtTotalDueAmt);

			pymnt.select_Cash("Cash", driver);
			logger.log(LogStatus.PASS, "Selected payment method as Cash");
			Thread.sleep(2000);


			pymnt.enter_CashAmount(pymtTotalDueAmt);
			logger.log(LogStatus.PASS, "Amount entered");
			Thread.sleep(2000);
			
			String snap5=Generic_Class.takeScreenShotPath();
			String ims5=logger.addScreenCapture(snap5);
			logger.log(LogStatus.INFO, "img",ims5);
			
	
			pymnt.click_ApplyButton();
		
			Thread.sleep(5000);
			((JavascriptExecutor)driver).executeScript("window.scrollBy(0,2000)");
			Thread.sleep(4000);

			pymnt.click_SubmitButton();
			logger.log(LogStatus.PASS, "Clicked on Submit button successfully ");
			Thread.sleep(5000);

			VacateConfirmationPopUp ConfirmationPopUp=new VacateConfirmationPopUp(driver); 
			ConfirmationPopUp.click_NoRadioButton();
			Thread.sleep(2000);
	

			ConfirmationPopUp.enter_Note_TextArea("Vacate Space");
			Thread.sleep(2000);

			ConfirmationPopUp.enter_EmpNumber(tabledata.get("UserName"));
			logger.log(LogStatus.PASS, "Employee No Entered successfully :" +tabledata.get("UserName"));
			Thread.sleep(2000);
			String snap6=Generic_Class.takeScreenShotPath();
			String ims6=logger.addScreenCapture(snap6);
			logger.log(LogStatus.INFO, "img",ims6);
			

			ConfirmationPopUp.click_ConfirmButton();
			Thread.sleep(15000);

			if(cust_accdetails.isCustdbTitleDisplayed()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "User returned to Customer Dashboard");
				logger.log(LogStatus.PASS, "User returned to Customer Dashboard",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "User didn't returned to Customer Dashboard");
				logger.log(LogStatus.FAIL, "User didn't returned to Customer Dashboard",image);
			}

			Thread.sleep(3000);
			try{

				if(cust_accdetails.isNextduenowDisplayed()){

					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "After vacate the space Next payment due details still displaying on the customer dashboard");
					logger.log(LogStatus.FAIL, "After vacate the space Next payment due details still displaying on the customer dashboard",image);

				}

			}catch(Exception e){


				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "After vacate the space Next payment due details are not  displaying on the customer dashboard and changes are reflecting in customerdashboard");
				logger.log(LogStatus.PASS, "After vacate the  space Next payment due details are not  displaying on the customer dashboard and changes are reflecting in customerdashboard",image);

			}

			String Nospaces=cust_accdetails.getCustSpaceNum();
			logger.log(LogStatus.PASS, "Space number in customer dashboard is:"+Nospaces);

			logger.log(LogStatus.PASS, "Unit is vacated successfully");
			/*Thread.sleep(2000);
			if(Nospaces.contains("No Spaces")){
				logger.log(LogStatus.PASS, "Unit is vacated successfully");

			}else{

				logger.log(LogStatus.FAIL, "Unit is not vacated");
			}*/

			Thread.sleep(3000);
			cust_accdetails.click_AccountActivities();
			logger.log(LogStatus.INFO, "Clicked on Account Activities tab in customer dashboard screen");

			Thread.sleep(15000);
			Cust_AccActivitiesPage Cust_AccActivitiesPage = new Cust_AccActivitiesPage(driver);

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY",Locale.getDefault());
			String strTodaysDate = df.format(cal.getTime());
			System.out.println(strTodaysDate);

			String snap3=Generic_Class.takeScreenShotPath();
			String ima3=logger.addScreenCapture(snap3);
			logger.log(LogStatus.INFO, "Image",ima3);
			logger.log(LogStatus.INFO, "checking vacate acivities in the account accitivities page");

			String xpath="//table/tbody//tr//td[text()='Vacate ']/preceding-sibling::td[text()='"+strTodaysDate+"']/../td/a";
			driver.findElement(By.xpath(xpath)).click();
			Thread.sleep(8000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(250,0)");
			Thread.sleep(2000);
			if(Cust_AccActivitiesPage.isUnvacteLinkDisplayed()){
				
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "UnVacate link is dispalyed in the account accitivities page successfully after vacating unit");
				logger.log(LogStatus.PASS, "img",image);
			}else{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "UnVacate link is not displayed after vacting unit in account accitivies page");
				logger.log(LogStatus.PASS, "img",image);
			}
            Thread.sleep(2000);
            ((JavascriptExecutor)driver).executeScript("window.scrollTo(250,0)");
            Cust_AccActivitiesPage.clkUnavteLink();
            logger.log(LogStatus.INFO, "Clicked on the unvacate link in account accitivites tab");
            
            Thread.sleep(3000);
            Vacate_PendingUnvacateConfirmationPopUpPage confpage=new Vacate_PendingUnvacateConfirmationPopUpPage(driver);
            if(confpage.isgPendingUnVacateConfirmationTitleDisplayed()){
            	String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Pending Unvacate Confirmation page is displayed successfully");
				logger.log(LogStatus.PASS, "img",image);
			}else{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Pending Unvacate Confirmation page is not displayed");
				logger.log(LogStatus.PASS, "img",image);
			}
            
            confpage.clkConfirmButton();
            logger.log(LogStatus.INFO, "Clicked on the confirm button without entering employee id");
            
            if(confpage.iserrorMsgDisplayed()){
            	
            	String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Verified employee id is mandatory field successfully");
				logger.log(LogStatus.PASS, "img",image);
			}else{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "employee id is not mandatory field");
				logger.log(LogStatus.PASS, "img",image);
			}
            
            
            
            
           /* if(confpage.isAccountNumDispalyed()){
            	
            	logger.log(LogStatus.PASS, "Customer account number present in the Pending Unvacate Confirmation page--->"+confpage.getAccountNumber());
            }else{
            	logger.log(LogStatus.FAIL, "Customer account number is not present in the Pending Unvacate Confirmation page");
            }
            
            if(confpage.isCustomerNameDisplayed()){
            	logger.log(LogStatus.PASS, "Customer name is displayed on the Pending Unvacate Confirmation page--->"+confpage.getCustomerName());
            	
            }else{
            	logger.log(LogStatus.FAIL, "Customer name is not displayed on the pending unvacate page");
            }
            
            if(confpage.isVacateDateDisplayed()){
            	logger.log(LogStatus.PASS, "vacate date is displayed on the Pending Unvacate Confirmation page--->"+confpage.getDateOfVacated());
            	
            }else{
            	logger.log(LogStatus.FAIL, "vacate date is not displayed on the pending unvacate page");
            }
            
            if(confpage.isSpaceNumDisplayed()){
            	logger.log(LogStatus.PASS, "space number is displayed on the Pending Unvacate Confirmation page--->"+confpage.getSpaceNumber());
            	String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "img",image);
            	
            	
            }else{
            	logger.log(LogStatus.FAIL, "space number is not displayed on the pending unvacate page");
            }

*/

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
				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashBoard_VerifyUnvacate_LaunchFucntion" , "Status", "Pass");
			}else if (resultFlag.equals("fail")){
				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashBoard_VerifyUnvacate_LaunchFucntion" , "Status", "Fail");
			}else{
				Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashBoard_VerifyUnvacate_LaunchFucntion" , "Status", "Skip");
			}
			extent.endTest(logger);
			extent.flush();
			Reporter.log("Test case completed: " +testcaseName, true);
		}


	}
