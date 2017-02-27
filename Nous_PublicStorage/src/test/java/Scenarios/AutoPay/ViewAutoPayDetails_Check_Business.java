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
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;

public class ViewAutoPayDetails_Check_Business extends Browser_Factory {
	String path=Generic_Class.getPropertyValue("Excelpath");
	public ExtentTest logger;
	String resultFlag="pass";


	@DataProvider
	public Object[][] getAutopayCheck() 
	{
		return Excel.getCellValue_inlist(path, "AutoPay","AutoPay","ViewAutoPayDetails_Check_Business");
	}


	@Test(dataProvider="getAutopayCheck")	
	public void ViewAutoPayDetails_Check_Business(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("AutoPay").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		try{
			logger=extent.startTest("ViewAutoPayDetails_Check_Business","Autopay business customer with check");


			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);


			//Login to PS Application
			LoginPage logpage = new LoginPage(driver);

			String siteNumber= logpage.get_SiteNumber();
			logpage.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Logged in successfully");


			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Reporter.log("object created successfully",true);
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
			driver.navigate().refresh();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "clicked on continue successfully");


			PM_Homepage pmhome = new PM_Homepage(driver);
			Thread.sleep(5000);
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "Navigated to PM Dashboard");
			logger.log(LogStatus.PASS, "Navigated to PM Dashboard",image1);
			
			pmhome.clk_AdvSearchLnk();
			logger.log(LogStatus.INFO, "Entered into advance search page");

			//Advance search page
			Advance_Search advSearch= new Advance_Search(driver);

			String query="select top 1 u.accountnumber from vw_unitdetails u with(nolock) "+
					"join autopay ap with(nolock) on u.storageorderitemid=ap.storageorderitemid "+
					//"join cltransaction clt with(nolock) on clt.accountorderitemid=u.accountorderitemid "+
					"join accountemail ae with(nolock) on ae.accountid = u.accountid "+
					"join site s with(nolock) on s.siteid = u.siteid "+
					"where u.vacatedate is null "+
					"and u.customertypeid = 91 "+
					"and ap.isactive = 1 "+
					"and s.isactive = 1 "+
					"and ap.ActivityTypeID in (4606,4607) "+
					"and ap.AccountCheckID is not null "+
					"and ae.emailid > 0 "+
					"and 1 = (select count(u2.accountorderitemid) from vw_unitdetails u2 where u2.accountid = u.accountid) "+
					"group by u.accountnumber ";
					//"having sum(clt.amount+clt.discountamount) >0 ";

			String accNum=DataBase_JDBC.executeSQLQuery(query);

			Thread.sleep(8000);
			advSearch.enterAccNum(accNum);

			logger.log(LogStatus.INFO, "Account number entered successfully" +accNum);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Customer landed on Customer Account Dashboard successfully",image);

			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(1000);

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
				logger.log(LogStatus.PASS, "Autopay Status is ON in Space Details Tab");
				logger.log(LogStatus.INFO, "Autopay Status is ON in Space Details Tab",image);

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
			Thread.sleep(6000);
			if(driver.findElements(By.xpath("//a[@class='psbutton-priority margin-left ok-button']")).size()!=0){
				driver.findElement(By.xpath("//a[@class='psbutton-priority margin-left ok-button']")).click();
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Alert Ok popup displyed");
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.INFO, "Confirmation Ok popup is not  displyed");
			}
			Thread.sleep(6000);
			//Verifying the last 4 digit is available in the screen

			//String xpath_Check=driver.findElement(By.xpath("//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/span[contains(text(),'Checking')]")).getText();

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

			/*
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


			/*     //Back to dashboard
           preferences.clickBkToBashbtn();
           logger.log(LogStatus.INFO, "Redirected to customer dashboard");

           pmhome.clk_AdvSearchLnk();
		   logger.log(LogStatus.INFO, "Entered into advance search page");

		   //Advance search page

		   advSearch.enterAccNum(tabledata.get("CustomerAccNum"));
		   logger.log(LogStatus.INFO, "Account number entered successfully");

		   ((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
		   Thread.sleep(1000);

		   advSearch.clickButton();	
		   Thread.sleep(2000);
		   logger.log(LogStatus.INFO, "Clicked Search button successfully");

			 */


			//Clicking on cancel button redirecting to Customer dashboard
			/*preferences.clickCancelbtn();
			logger.log(LogStatus.INFO, "Clicked on cancel button successfully");
			Thread.sleep(5000);

			Cust_AccDetailsPage acc = new Cust_AccDetailsPage(driver);
			acc.click_AccountActivities();
			Thread.sleep(8000);
			logger.log(LogStatus.INFO, "Clicked on  Acount Activities Tab");

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			String time = sdf.format(date);


			List<WebElement> resultRows = driver.findElements(By.xpath("//div[@id='activities-grid']/div[@class='k-grid-content ps-container']/table[@role='treegrid']/tbody/tr"));
			int rowSize = resultRows.size();

			for(int i=1; i<=rowSize; i++){

				String getText = resultRows.get(i-1).findElement(By.xpath("./td[2]")).getText();
				System.out.println("date "+getText);

				if(getText.equals(time)){
					logger.log(LogStatus.INFO, "Current date verified with the payment date");
					String getText1 = resultRows.get(i-1).findElement(By.xpath("./td[7]")).getText();
					if(getText1.contains("AutoPay Confirmation")) {
						String scpath=Generic_Class.takeScreenShotPath();
						String image=logger.addScreenCapture(scpath);
						logger.log(LogStatus.PASS, "Payment made is verified successfully in account activities tab");
						logger.log(LogStatus.INFO, "Payment made is verified successfully in account activities tab",image);
						break;
					}else{

						String scpath=Generic_Class.takeScreenShotPath();
						Reporter.log(scpath,true);
						String image=logger.addScreenCapture(scpath);
						if(resultFlag.equals("pass"))
							resultFlag="fail";
						logger.log(LogStatus.FAIL, "Autopay is not confirmed with the customer in account activities tab");
						logger.log(LogStatus.INFO, "Autopay is not confirmed with the customer in account activities tab",image);

					}

				} 
			}*/
			driver.findElement(By.xpath("//div[@id='editAutoPayPreferences']//a[text()='Cancel']")).click();
			Thread.sleep(6000);
			
			//Verify to see account activity created
			/*cust_accdetails.click_AccountActivities();
			Thread.sleep(8000);

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.getDefault());
			String strTodaysDate = df.format(cal.getTime());


			if(driver.findElements(By.xpath("//div[@id='activities-grid']//table//tbody//tr//td[text()='Autopay']/..//td/div[contains(text(),'Autopay turned on')]")).size()!=0){
				 scpath=Generic_Class.takeScreenShotPath();
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "AutoPay activity is verified for the space - Customer Account Dashboard");
				logger.log(LogStatus.PASS, "AutoPay activity is verified for the space",image);

			}else{

				 scpath=Generic_Class.takeScreenShotPath();
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "AutoPay activity is not present - Customer Account Dashboard");
				logger.log(LogStatus.INFO, "AutoPay activity is not present for the space",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}*/
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

	Reporter.log(resultFlag,true);

	if(resultFlag.equals("pass")){
		Excel.setCellValBasedOnTcname(path, "AutoPay","ViewAutoPayDetails_Check_Business" , "Status", "Pass");

	}else if (resultFlag.equals("fail")){

		Excel.setCellValBasedOnTcname(path, "AutoPay","ViewAutoPayDetails_Check_Business" , "Status", "Fail");
	}else{
		Excel.setCellValBasedOnTcname(path, "AutoPay","ViewAutoPayDetails_Check_Business" , "Status", "Skip");
	}

	extent.endTest(logger);
	extent.flush();
	Reporter.log("Test case completed: " +testcaseName, true);

}

}
