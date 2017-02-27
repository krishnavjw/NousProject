package Scenarios.AutoPay;

import java.awt.Robot;
import java.awt.event.KeyEvent;
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
import org.openqa.selenium.interactions.Action;
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
import Pages.AutoPayPages.Autopay_PreferencesPage;
import Pages.AutoPayPages.StopAutoPayPopupPage;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;

public class UnenrollAutopay_Individual_Check extends Browser_Factory{
	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");


	@DataProvider
	public Object[][] getLoginData() 
	{
		//String path="./src/main/resources/Resources/PS_TestData.xlsx";

		return Excel.getCellValue_inlist(path, "AutoPay","AutoPay", "UnenrollAutopay_Individual_Check");
	}


	@Test(dataProvider="getLoginData")
	public void UnenrollAutopay_Individual_Check(Hashtable<String, String> tabledata) throws InterruptedException
	{
		logger=extent.startTest("UnenrollAutopay_Individual_Check","UnEnroll/stop/cancel Autopay for an Individual customer ");

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("AutoPay").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "UnenrollAutopay_Individual_Check is Skipped");
			throw new SkipException("Skipping the test");
		}

		try
		{

			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			logger.log(LogStatus.INFO, "Login Page object is created successfully");

			String siteNumber=login.get_SiteNumber();

			login.enterUserName(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "UserName entered successfully");

			login.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Password entered successfully");

			login.clickLogin();
			logger.log(LogStatus.INFO, "Click on Login button successfully");

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");

			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T); 
			Thread.sleep(8000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(5000);
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_QA"));  

			List<WebElement> biforstSystem=driver.findElements(By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
			for(WebElement ele:biforstSystem)
			{
				if(biforstNum.equalsIgnoreCase(ele.getText().trim()))
				{
					Reporter.log(ele.getText()+"",true);
					ele.click();
					break;
				}
			}

			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);


			PM_Homepage hp= new PM_Homepage(driver);
			Thread.sleep(5000);
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "Navigated to PM Dashboard");
			logger.log(LogStatus.PASS, "Navigated to PM Dashboard",image1);

			hp.clk_AdvSearchLnk();
			logger.log(LogStatus.INFO, "clicking on Advance Search link successfully");

			Advance_Search advSearch= new Advance_Search(driver);

			String query="select top 1 u.accountnumber from vw_unitdetails u with(nolock) "+
					"join autopay ap with(nolock) on u.storageorderitemid=ap.storageorderitemid "+
					//"join cltransaction clt with(nolock) on clt.accountorderitemid=u.accountorderitemid "+
					"join accountemail ae with(nolock) on ae.accountid = u.accountid "+
					"join site s with(nolock) on s.siteid = u.siteid "+
					"where u.vacatedate is null "+
					"and u.customertypeid = 90 "+
					"and s.isactive = 1 "+
					"and ap.isactive = 1 "+
					"and ap.ActivityTypeID in (4606,4607) "+
					"and ap.AccountCheckID is not null "+
					"and ae.emailid > 0 "+
					"and 1 = (select count(u2.accountorderitemid) from vw_unitdetails u2 where u2.accountid = u.accountid) "+
					"group by u.accountnumber ";
					//"having sum(clt.amount+clt.discountamount) >0 ";

			String accNum=DataBase_JDBC.executeSQLQuery(query);

			Thread.sleep(8000);
			advSearch.enterAccNum(accNum);
			//advSearch.enterAccNum("10882967");

			logger.log(LogStatus.INFO, "Enter Account Num in Account field successfully" +accNum);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);

			advSearch.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "clicked on Search button successfully");

			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			Thread.sleep(8000);
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Customer Account Dashboard displayed successfully for an Individual Customer");
			logger.log(LogStatus.INFO, "Image",image);

			Thread.sleep(6000);
			cust_accdetails.clickSpaceDetails_tab();
			logger.log(LogStatus.INFO, "Click on Space Details Tab successfully");

			Thread.sleep(6000);
			Acc_SpaceDetailsPage spaceTab=new Acc_SpaceDetailsPage(driver);

			if(spaceTab.verifyAutopay_Status())
			{
				 scpath=Generic_Class.takeScreenShotPath();
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Autopay Status is ON in Space Details Tab before Unenrollment");
				logger.log(LogStatus.INFO, "Autopay Status is ON in Space Details Tab before UnEnrollment",image);

			}else{
				 scpath=Generic_Class.takeScreenShotPath();
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Autopay Status is NOT ON in Space Details Tab");
				logger.log(LogStatus.INFO, "Autopay Status is NOT ON in Space Details Tab",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			String spaceNum=cust_accdetails.getCustSpaceNum();
			Thread.sleep(6000);
			cust_accdetails.clickOnManageAutoPay_Lnk();
			logger.log(LogStatus.INFO, "Click on Manage Auto pay button successfully");
			logger.log(LogStatus.PASS, "AutoPay preference screen displayed");

			Thread.sleep(6000);
			String xpath="//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/span[contains(text(),'Checking')]";
			Thread.sleep(6000);
			if(driver.findElement(By.xpath(xpath)).isDisplayed())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Auto pay Status is checking in Method column");
				logger.log(LogStatus.PASS, "Auto pay Status enabled");
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Auto pay Status not enabled");
				logger.log(LogStatus.INFO, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			//Verifying the last 4 digit is available in the screen

			/*String xpath_Check=driver.findElement(By.xpath("//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/span[contains(text(),'Checking')]")).getText();

			String checkValue=driver.findElement(By.xpath(xpath_Check)).getText();
			System.out.println(checkValue);

			checkValue= checkValue.substring(checkValue.length()-4);
			System.out.println("Value present for Check Number is :" + checkValue);
			boolean isNumber= true;
			try{
				Integer.parseInt(checkValue);
			}catch(Exception e){
				isNumber=false;

			}

			if(isNumber){
				 scpath=Generic_Class.takeScreenShotPath();
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Autopay is enabled for the customer with Credit Card :"+checkValue);
				logger.log(LogStatus.PASS, "Autopay is enabled for the customer with Credit Card",image);
			}

			else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";    
				 scpath=Generic_Class.takeScreenShotPath();
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Autopay is not enabled for the customer with Credit Card");
				logger.log(LogStatus.INFO, "Autopay is not enabled for the customer with Credit Card ",image);
			}*/

			boolean	xpath_Check=driver.findElement(By.xpath("//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/span[contains(text(),'Checking')]")).isDisplayed();
			if(xpath_Check){

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Autopay is enabled for the customer with Check");
				logger.log(LogStatus.PASS, "Autopay is enabled for the customer with Check",image);
			}

			else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";    
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Autopay is not enabled for the customer with Check");
				logger.log(LogStatus.INFO, "Autopay is not enabled for the customer with Check ",image);
			}
			if(driver.findElements(By.xpath("//a[@class='psbutton-priority margin-left ok-button']")).size()!=0){
				driver.findElement(By.xpath("//a[@class='psbutton-priority margin-left ok-button']")).click();
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Alert Ok popup displyed");
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.INFO, "Confirmation Ok popup is not  displyed");
			}

			//Click on stop Auto pay and verify in Auto pay status in Space Details tab

			String xpath_StopAutoPay="//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/a[contains(text(),'Stop')]";
			driver.findElement(By.xpath(xpath_StopAutoPay)).click();
			logger.log(LogStatus.INFO, "Click on Stop Auto pay link successfully");
			Thread.sleep(6000);

			StopAutoPayPopupPage stopAutoPay = new StopAutoPayPopupPage(driver);
			stopAutoPay.enterEmployeeID(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Enter the Employee ID successfully");
			Thread.sleep(6000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Enter the Employee ID");
			logger.log(LogStatus.PASS, "Enter the Employee ID",image);
			Thread.sleep(4000);
			
			stopAutoPay.clickConfirm_Btn();
			logger.log(LogStatus.INFO, "Click on Confirm button successfully");
			Thread.sleep(8000);

			String checkStatus=driver.findElement(By.xpath("//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/span[contains(text(),'Auto')]")).getText();
			System.out.println(checkStatus);
			if(checkStatus.contains("AutoPay not enabled for this space")){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "AutoPay Status is disabled for the space");
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "AutoPay Status is not disabled");
				logger.log(LogStatus.INFO, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			/*boolean confirmationOk = driver.findElement(By.xpath("//a[@class='psbutton-priority margin-left ok-button']")).isDisplayed();
			if(confirmationOk){
				driver.findElement(By.xpath("//a[@class='psbutton-priority margin-left ok-button']")).click();
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Confirmation Ok popup displyed");
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Confirmation Ok popup is not  displyed");
			}*/
			
			Thread.sleep(20000);
			
			if(driver.findElements(By.xpath("//a[@class='psbutton-priority margin-left ok-button']")).size()!=0){
				driver.findElement(By.xpath("//a[@class='psbutton-priority margin-left ok-button']")).click();
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Alert Ok popup displyed");
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.INFO, "Confirmation Ok popup is not  displyed");
			}

			driver.findElement(By.xpath("//div[@id='editAutoPayPreferences']//a[text()='Cancel']")).click();
			Thread.sleep(6000);
			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(6000);
			if(spaceTab.verifyStopAutopay_Status())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "AutoPay Status is disabled for the space - Customer Account Dashboard after Unenrollment");
				logger.log(LogStatus.INFO, "AutoPay Status is disabled for the space - Customer Account Dashboard after Unenrollment",image);

			}else{

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "AutoPay Status is not disabled for the space - Customer Account Dashboard after Unenrollment");
				logger.log(LogStatus.INFO, "AutoPay Status is not disabled for the space - Customer Account Dashboard after Unenrollment",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			//Verify to see account activity created
			cust_accdetails.click_AccountActivities();
			Thread.sleep(8000);

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.getDefault());
			String strTodaysDate = df.format(cal.getTime());


			if(driver.findElements(By.xpath("//div[@id='activities-grid']//table//tbody//tr//td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Autopay']/..//td/div[contains(text(),'Autopay turned off')]")).size()!=0)
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "AutoPay Status is disabled for the space - Customer Account Dashboard");
				logger.log(LogStatus.INFO, "AutoPay Status is disabled for the space - Customer Account Dashboard",image);

			}else{

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "AutoPay Status is not disabled for the space - Customer Account Dashboard");
				logger.log(LogStatus.INFO, "AutoPay Status is not disabled for the space - Customer Account Dashboard",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}


		}catch(Exception ex){
			ex.printStackTrace();
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			//In the catch block, set the variable resultFlag to “fail”
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
			logger.log(LogStatus.FAIL, "Autopay is cancelled for the customer with Check ",image);

		}

	}       

	@AfterMethod
	public void afterMethod(){

		System.out.println(" In After Method");
		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path,"AutoPay","UnenrollAutopay_Individual_Check" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){    
			//			String scpath=Generic_Class.takeScreenShotPath();
			//			String image=logger.addScreenCapture(scpath);
			//			logger.log(LogStatus.FAIL, "Create Reservation page is not displayed",image);
			Excel.setCellValBasedOnTcname(path,"AutoPay","UnenrollAutopay_Individual_Check" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "AutoPay","UnenrollAutopay_Individual_Check" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}


}



