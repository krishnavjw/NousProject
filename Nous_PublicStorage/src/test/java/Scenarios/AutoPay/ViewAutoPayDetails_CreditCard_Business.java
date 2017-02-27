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
import org.openqa.selenium.Keys;
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
import Pages.AutoPayPages.Autopay_PreferencesPage;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustInfoPages.Cust_CustomerInfoPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class ViewAutoPayDetails_CreditCard_Business extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");


	@DataProvider
	public Object[][] getAutopayCheck() 
	{
		//String path="./src/main/resources/Resources/PS_TestData.xlsx";

		return Excel.getCellValue_inlist(path, "AutoPay","AutoPay", "ViewAutoPayDetails_CreditCard_Business");



	}


	@Test(dataProvider="getAutopayCheck")
	public void ViewAutoPayDetails_CreditCard_Business(Hashtable<String, String> tabledata) throws InterruptedException
	{
		logger=extent.startTest("ViewAutoPayDetails_CreditCard_Business","View Autopay details");

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("AutoPay").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "ViewAutoPayDetails_CreditCard_Business is Skipped");
			throw new SkipException("Skipping the test");
		}

		try
		{
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			String siteNumber= login.get_SiteNumber();
			logger.log(LogStatus.INFO, "Login Page object is created successfully");

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
			Thread.sleep(6000);
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
			Thread.sleep(3000);

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
					"and ap.AccountCreditCardID is not null "+
					"and ae.emailid > 0 "+
					"and 1 = (select count(u2.accountorderitemid) from vw_unitdetails u2 where u2.accountid = u.accountid) "+
					"group by u.accountnumber ";
					//"having sum(clt.amount+clt.discountamount) >0 ";

			String accNum=DataBase_JDBC.executeSQLQuery(query);

			Thread.sleep(8000);
			advSearch.enterAccNum(accNum);
			logger.log(LogStatus.INFO, "Enter Account Num in Account field successfully :"+accNum);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Customer landed on Customer Account Dashboard successfully",image);
			advSearch.clickSearchAccbtn();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "clicking on Search button successfully");

			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			Thread.sleep(8000);
			if(cust_accdetails.verifyCustAccDashboard()){
				logger.log(LogStatus.INFO, "Customer landed on Customer Account Dashboard successfully");
				 scpath=Generic_Class.takeScreenShotPath();
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer landed on Customer Account Dashboard successfully",image);
			}
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

			
			Thread.sleep(5000);
			String spaceNum=cust_accdetails.getCustSpaceNum();
			Thread.sleep(6000);
			cust_accdetails.clickOnManageAutoPay_Lnk();
			logger.log(LogStatus.INFO, "Click on Manage Auto pay button successfully");
			Autopay_PreferencesPage preferences= new Autopay_PreferencesPage(driver);
			Thread.sleep(6000);
			String Title=preferences.getPageTitle();
			if(Title.equalsIgnoreCase("AutoPay Preferences"))
			{ 

				 scpath=Generic_Class.takeScreenShotPath();
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "AutoPay Preferences is displayed successfully :"+Title);
				logger.log(LogStatus.INFO, "AutoPay Preferences is displayed successfully",image);

			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";    
				 scpath=Generic_Class.takeScreenShotPath();
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "AutoPay Preferences is not displayed ");
				logger.log(LogStatus.INFO, "AutoPay Preferences is not displayed ",image);
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
			String xpath="//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/span[contains(text(),'ending')]";
			Thread.sleep(6000);
			if(driver.findElements(By.xpath(xpath)).size()!=0){
				String creditCardValue=driver.findElement(By.xpath(xpath)).getText();
				System.out.println(creditCardValue);

				creditCardValue= creditCardValue.substring(creditCardValue.length()-4);
				System.out.println("Value present for Check Number is :" + creditCardValue);
				logger.log(LogStatus.PASS, "Value present for Card Number is : "+creditCardValue);

					 scpath=Generic_Class.takeScreenShotPath();
					 image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "Autopay is enabled for the customer with Credit Card :"+creditCardValue);
					logger.log(LogStatus.PASS, "Autopay is enabled for the customer with Credit Card",image);
				}

				else if(driver.findElements(By.xpath("//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/span[contains(text(),'American Express')]")).size()!=0){
					String creditCardValue=driver.findElement(By.xpath("//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/span[contains(text(),'American Express')]")).getText();
					System.out.println(creditCardValue);

					creditCardValue= creditCardValue.substring(creditCardValue.length()-4);
					System.out.println("Value present for Card Number is :" + creditCardValue);
					logger.log(LogStatus.PASS, "Value present for Card Number is : "+creditCardValue);

					 scpath=Generic_Class.takeScreenShotPath();
					 image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "Autopay is  enabled for the customer with Credit Card :"+creditCardValue);
					logger.log(LogStatus.PASS, "Autopay is  enabled for the customer with Credit Card",image);
				}
				else if(driver.findElements(By.xpath("//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/span[contains(text(),'Mastercard')]")).size()!=0){
					String creditCardValue=driver.findElement(By.xpath("//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/span[contains(text(),'Mastercard')]")).getText();
					System.out.println(creditCardValue);

					creditCardValue= creditCardValue.substring(creditCardValue.length()-4);
					System.out.println("Value present for Card Number is :" + creditCardValue);
					logger.log(LogStatus.PASS, "Value present for Card Number is : "+creditCardValue);

					 scpath=Generic_Class.takeScreenShotPath();
					 image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "Autopay is  enabled for the customer with Credit Card :"+creditCardValue);
					logger.log(LogStatus.PASS, "Autopay is  enabled for the customer with Credit Card",image);
				}
				else if(driver.findElements(By.xpath("//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/span[contains(text(),'Discover')]")).size()!=0){
					String creditCardValue=driver.findElement(By.xpath("//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/span[contains(text(),'Discover')]")).getText();
					System.out.println(creditCardValue);

					creditCardValue= creditCardValue.substring(creditCardValue.length()-4);
					System.out.println("Value present for Card Number is :" + creditCardValue);
					logger.log(LogStatus.PASS, "Value present for Card Number is : "+creditCardValue);

					 scpath=Generic_Class.takeScreenShotPath();
					 image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "Autopay is not enabled for the customer with Credit Card :"+creditCardValue);
					logger.log(LogStatus.PASS, "Autopay is not enabled for the customer with Credit Card",image);
				}else{
				 scpath=Generic_Class.takeScreenShotPath();
				 image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Autopay is not  enabled for the customer with Credit Card ");
				logger.log(LogStatus.FAIL, "Autopay is not enabled for the customer with Credit Card",image);
			}

			//Click on back to Dashboard
			/*Cust_CustomerInfoPage custPage=new Cust_CustomerInfoPage(driver);
			custPage.click_backToDashboard();
			Thread.sleep(6000);

			hp.clk_AdvSearchLnk();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "clicking on Advance Search link successfully");

			advSearch.enterAccNum(tabledata.get("CustomerAccNum"));
			logger.log(LogStatus.INFO, "Enter Account Num in Account field successfully");

			advSearch.clickSearchAccbtn();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "clicking on Search button successfully");*/

			//Click on Account Activity tab
			/*Cust_AccDetailsPage acc = new Cust_AccDetailsPage(driver);
			acc.click_AccountActivities();
			Thread.sleep(8000);
			logger.log(LogStatus.INFO, "Clicked on  Acount Activities Tab");

			//Verify to see account activity created
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			String CurrentDate = sdf.format(date);			
			List<WebElement> resultRows = driver.findElements(By.xpath("//div[@id='activities-grid']//div[@class='k-grid-content ps-container ps-active-y']/table/tbody/tr"));

			int rowSize = resultRows.size();
			for(int i=1; i<=rowSize; i++){

				String Date = resultRows.get(i-1).findElement(By.xpath("./td[2]")).getText();
				System.out.println("date"+Date);

				if(Date.equals(CurrentDate)){
					logger.log(LogStatus.INFO, "date verified :"+Date);
					String Description = resultRows.get(i-1).findElement(By.xpath("./td[7]")).getText();
					if(Description.equals("AutoPay Confirmation")) {
						logger.log(LogStatus.INFO, "Account Activity created successfully :"+Description);
						break;

					}
					else{
						if(resultFlag.equals("pass"))
							resultFlag="fail";    
						String scpath=Generic_Class.takeScreenShotPath();
						String image=logger.addScreenCapture(scpath);
						logger.log(LogStatus.FAIL, "Autopay is not enabled for the customer with Credit Card");
						logger.log(LogStatus.INFO, "Autopay is not enabled for the customer with Credit Card ",image);
					}
				}
			}
			 */

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

		System.out.println(" In After Method");
		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path,"AutoPay","ViewAutoPayDetails_CreditCard_Business" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){    

			Excel.setCellValBasedOnTcname(path,"AutoPay","ViewAutoPayDetails_CreditCard_Business" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "AutoPay","ViewAutoPayDetails_CreditCard_Business" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}



}
