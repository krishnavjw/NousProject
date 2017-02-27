package Scenarios.AutoPay;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
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
import Pages.AutoPayPages.ChangeAutoPayPage;
import Pages.AutoPayPages.EmployeeIDPopUpPage;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;

public class EditAutopay_Individual_Check extends Browser_Factory{

	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");


	@DataProvider
	public Object[][] getLoginData() 
	{

		return Excel.getCellValue_inlist(path, "AutoPay","AutoPay", "EditAutopay_Individual_Check");
	}

	@Test(dataProvider="getLoginData")
	public void EditAutopay_Individual_Check(Hashtable<String, String> tabledata) throws InterruptedException
	{

		logger=extent.startTest("EditAutopay_Individual_Check","Enroll into autopay with Check for Individual customer");

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("AutoPay").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "EditAutopay_Individual_Check is Skipped");
			throw new SkipException("Skipping the test EditAutopay_Ind_Check");
		}

		try
		{
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			Thread.sleep(3000);
			LoginPage login= new LoginPage(driver);
			logger.log(LogStatus.INFO, "Login Page object is created successfully");
			String siteNumber=login.get_SiteNumber();

			login.enterUserName(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "UserName entered successfully");

			login.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Password entered successfully");

			login.clickLogin();
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			//=================Facing customer device===========================

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			String biforstNum=Bifrostpop.getBiforstNo();
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T); 
			Thread.sleep(8000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Thread.sleep(6000);
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
			Thread.sleep(8000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
			//======================================================================

			PM_Homepage hp= new PM_Homepage(driver);
			Thread.sleep(5000);
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "Navigated to PM Dashboard");
			logger.log(LogStatus.PASS, "Navigated to PM Dashboard",image1);			hp.clk_AdvSearchLnk();
			logger.log(LogStatus.INFO, "clicking on Advance Search link successfully");

			Thread.sleep(8000);
			Advance_Search advSearch= new Advance_Search(driver);


			String query="select top 1 u.accountnumber from vw_unitdetails u with(nolock) "+
					"join autopay ap with(nolock) on u.storageorderitemid=ap.storageorderitemid "+
					"join customer c with(nolock) on c.contactid=u.contactid "+
					"join accountemail ae with(nolock) on ae.accountid = u.accountid "+
					"join site s with(nolock) on s.siteid = u.siteid "+
					"where u.vacatedate is null "+
					"and u.customertypeid = 90 "+
					"and s.isactive = 1 "+
					"and c.nochecks = 0 "+
					"and ap.isactive = 1 "+
					"and ap.ActivityTypeID in (4606,4607) "+
					"and ap.AccountCreditCardID is not null "+
					"and ae.emailid > 0 "+
					"and 1 = (select count(u2.accountorderitemid) from vw_unitdetails u2 where u2.accountid = u.accountid) "+
					"group by u.accountnumber ";
					//"having sum(clt.amount+clt.discountamount) >0 ";

			String accNum=DataBase_JDBC.executeSQLQuery(query);

			Thread.sleep(8000);
			System.out.println(accNum);
			advSearch.enterAccNum(accNum);
			logger.log(LogStatus.INFO, "Enter Account Num in Account field successfully");
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Account Number entered in customer Dashboard successfully : " +accNum);
			logger.log(LogStatus.INFO, "Account Number entered in customer Dashboard successfully",image);


			Thread.sleep(8000);
			advSearch.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "clicking on Search button successfully");

			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			Thread.sleep(8000);

			if(cust_accdetails.isCustdbTitleDisplayed()){

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "customer Dashboard is displayed successfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "customer Dashboard is not displayed ",image);}

			Thread.sleep(5000);
			cust_accdetails.clickSpaceDetails_tab();
			logger.log(LogStatus.INFO, "Click on Space Details Tab on customer dashboard successfully");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Checking Autopay Status for the Customer before Changing");
			logger.log(LogStatus.PASS, "Checking Autopay status for the Customer before Changing"+image);

			Acc_SpaceDetailsPage spaceTab=new Acc_SpaceDetailsPage(driver);
			Thread.sleep(8000);

			String spaceNum=spaceTab.get_SpaceNumber();
			logger.log(LogStatus.INFO, "Fetching customer sapce number in space details tab and space number is:"+spaceNum);
			//div[@id='space-details-grid']//div//table//tbody//tr//td//div//a[contains(text(),'Change')]

			if(driver.findElements(By.xpath("//div[@id='space-details-grid']//div//table//tbody//tr//td//div//a[contains(text(),'Change')]")).size()!=0){
				driver.findElement(By.xpath("//div[@id='space-details-grid']//div//table//tbody//tr//td//div//a[contains(text(),'Change')]")).click();
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Clicking on Change link to Edit payment mode in Autopay in Space Details tab");
				logger.log(LogStatus.INFO, "Clicking on Change link to Edit payment mode in Autopay in Space Details tab",image);
			}else{
				logger.log(LogStatus.INFO, "Change link is not  displyed in Space Details tab");
			}

			Thread.sleep(8000);
			Autopay_PreferencesPage preferences= new Autopay_PreferencesPage(driver);
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
			if(preferences.getPageTitle().equalsIgnoreCase("AutoPay Preferences")){


				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "AutoPay Preferences is displayed successfully");
				logger.log(LogStatus.INFO, "AutoPay Preferences is displayed successfully",image);

			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "AutoPay Preferences is not displayed ");
				logger.log(LogStatus.INFO, "AutoPay Preferences is not displayed ",image);}


			Thread.sleep(5000);
			String loc=driver.findElement(By.xpath("//span[text()='"+spaceNum+"']/../preceding-sibling::td//a")).getText();
			logger.log(LogStatus.PASS, "location number is displayed sucessfully and location number is:"+loc);


			Thread.sleep(5000);
			boolean changeAutopay=driver.findElement(By.xpath("//span[text()='"+spaceNum+"']/../following-sibling::td/a[text()='Change AutoPay']")).isDisplayed();

			if(changeAutopay){
				logger.log(LogStatus.PASS, "Change link is displayed on AutoPay Preferences screen");	

			}else{

				logger.log(LogStatus.FAIL, "Change link is not displayed on AutoPay Preferences screen");	
			}



			WebElement ele=driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']//tr//td//span[text()='"+spaceNum+"']/../following-sibling::td//a[text()='Change AutoPay']"));
			ele.click();
			logger.log(LogStatus.INFO, "click on the cahnge autopay link on the autopay preference page");

			ChangeAutoPayPage changeautopaypage=new ChangeAutoPayPage(driver);
			Thread.sleep(8000);
			changeautopaypage.selectPaymentMethod(tabledata.get("PaymentMethod"), driver);
			logger.log(LogStatus.INFO, "select the check option  from the list and click on the check option");

			Thread.sleep(2000);
			changeautopaypage.clickmanualentry();
			logger.log(LogStatus.INFO, "click on the enter manually tab");

			Thread.sleep(2000);
			changeautopaypage.Enter_routingNumber(tabledata.get("CheckRoutingNum"));
			logger.log(LogStatus.INFO, "Enter Bank Routing Number");

			Thread.sleep(2000);
			changeautopaypage.Enter_accountNumber(tabledata.get("CheckAccNum"));
			logger.log(LogStatus.INFO, "Enter Checking Account Number");

			Thread.sleep(5000);
			changeautopaypage.clickOnConfirmWithCustomer_Btn();
			logger.log(LogStatus.INFO, "click on confirm with customer button");


			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(1));
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(6000);

			WebElement signature = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions actionBuilder = new Actions(driver);          
			Action drawAction = actionBuilder.moveToElement(signature,660,96).click().clickAndHold(signature)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature).build();
			drawAction.perform();
			Thread.sleep(6000);
			//Validating CFS
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer");
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer "+image);
			Thread.sleep(5000);
			WebElement clickaccept=driver.findElement(By.xpath("//button[text()='Accept']"));
			clickaccept.click();
			logger.log(LogStatus.INFO, "Cust Signature and click on Accept button successfully");

			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(0));
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			logger.log(LogStatus.INFO, "Switch to Main page successfully");


			Thread.sleep(8000);
			changeautopaypage.click_SignatureApproveBtn();
			logger.log(LogStatus.INFO, "click on approve button successfully");
			Thread.sleep(6000);
			changeautopaypage.clickSubmitbtn();
			logger.log(LogStatus.INFO, "click on submit button");

			EmployeeIDPopUpPage empidpage=new EmployeeIDPopUpPage(driver);
			Thread.sleep(3000);
			empidpage.enter_EmpId(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Enter employee id successfully");

			Thread.sleep(3000);
			//Validating CFS
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Entered Employee ID");
			logger.log(LogStatus.PASS, "Entered Employee ID"+image);

			Thread.sleep(5000);
			empidpage.clk_OkBtn();
			logger.log(LogStatus.INFO, "click on ok button successfully");

			Thread.sleep(10000);
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
			cust_accdetails.clickSpaceDetails_tab();
			logger.log(LogStatus.INFO, "Click on Space Details Tab on customer dashboard successfully");


			Thread.sleep(6000);
			if(spaceTab.verifyAutopay_Status())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Autopay Status is edit/enabled successfully",image);

			}

			Thread.sleep(8000);
			//Verify to see account activity created
			cust_accdetails.click_AccountActivities();
			Thread.sleep(8000);

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.getDefault());
			String strTodaysDate = df.format(cal.getTime());



			if(driver.findElements(By.xpath("//div[@id='activities-grid']//table//tbody//tr//td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Autopay']/..//td/div[contains(text(),'Autopay changed')]")).size()!=0)
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "AutoPay Status is changed and  enabled for the space - Customer Account Dashboard");
				logger.log(LogStatus.PASS, "AutoPay Status is changed and  enabled for the space after changing the payment mode",image);

			}else{

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "AutoPay Status is not changed and not enabled - Customer Account Dashboard");
				logger.log(LogStatus.FAIL, "AutoPay Status is not changed and not enabled - Customer Account Dashboard",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			/*cust_accdetails.clickOnManageAutoPay_Lnk();
			logger.log(LogStatus.INFO, "click on the manage autopay link on customer dash board");
			Thread.sleep(8000);

			WebElement ele1=driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']//tr//td//span[text()='"+spaceNum+"']/../following-sibling::td//a[text()='Change AutoPay']"));
			ele1.click();
			logger.log(LogStatus.INFO, "click on the cahnge autopay link on the autopay preference page");


			Thread.sleep(8000);
			changeautopaypage.selectPaymentMethod(tabledata.get("PaymentMethod"), driver);
			logger.log(LogStatus.INFO, "select the check option  from the list and click on the check option");

			Thread.sleep(2000);
			changeautopaypage.clickmanualentry();
			logger.log(LogStatus.INFO, "click on the enter manually tab");

			Thread.sleep(2000);
			changeautopaypage.Enter_routingNumber(tabledata.get("ChangeRoutingNum"));
			logger.log(LogStatus.INFO, "Editing Bank Routing Number");

			Thread.sleep(2000);
			changeautopaypage.Enter_accountNumber(tabledata.get("ChangeAccNum"));
			logger.log(LogStatus.INFO, "Editing Checking Account Number");

			Thread.sleep(5000);
			changeautopaypage.clickOnConfirmWithCustomer_Btn();
			logger.log(LogStatus.INFO, "click on confirm with customer button");


			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(1));
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(6000);

			WebElement signature1 = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions actionBuilder1 = new Actions(driver);          
			Action drawAction1 = actionBuilder1.moveToElement(signature1,660,96).click().clickAndHold(signature1)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature1).build();
			drawAction1.perform();
			Thread.sleep(6000);
			WebElement clickaccept1=driver.findElement(By.xpath("//button[text()='Accept']"));
			clickaccept1.click();
			logger.log(LogStatus.INFO, "Cust Signature and click on Accept button successfully");

			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(0));
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			logger.log(LogStatus.INFO, "Switch to Main page successfully");


			Thread.sleep(8000);
			changeautopaypage.click_SignatureApproveBtn();
			logger.log(LogStatus.INFO, "click on approve button successfully");


			Thread.sleep(6000);
			changeautopaypage.clickSubmitbtn();
			logger.log(LogStatus.INFO, "Click on Submit button successfully");

			Thread.sleep(5000);
			empidpage.enter_EmpId(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Enter employee id successfully");

			Thread.sleep(3000);
			empidpage.clk_OkBtn();
			logger.log(LogStatus.INFO, "Click on Ok button successfully");*/

			logger.log(LogStatus.PASS, "Edit autopay for individual customer is successful");

		}catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Autopay Status is enabled successfully",image);
		}


	}

	@AfterMethod
	public void afterMethod(){

		System.out.println(" In After Method");
		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path,"AutoPay","EditAutopay_Individual_Check" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){    
			Excel.setCellValBasedOnTcname(path,"AutoPay","EditAutopay_Individual_Check" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "AutoPay","EditAutopay_Individual_Check" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);
	}


}
