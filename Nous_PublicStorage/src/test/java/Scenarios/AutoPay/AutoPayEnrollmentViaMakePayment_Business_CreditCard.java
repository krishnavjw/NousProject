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
import Pages.AutoPayPages.EmployeeIDPopUpPage;
import Pages.AutoPayPages.StopAutoPayPopupPage;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Pages.Walkin_Reservation_Lease.Leasing_TransactionCompletePage;
import Scenarios.Browser_Factory;

public class AutoPayEnrollmentViaMakePayment_Business_CreditCard extends Browser_Factory{
	public ExtentTest logger;
	String resultFlag="pass";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "AutoPay","AutoPay", "AutoPayEnrollmentViaMakePayment_Business_CreditCard");
	}


	@Test(dataProvider="getData")
	public void AutoPayEnrollmentViaMakePayment_Business_CreditCard(Hashtable<String, String> tabledata) throws InterruptedException
	{
		logger=extent.startTest("AutoPayEnrollmentViaMakePayment_Business_CreditCard","AutoPay Enrollment via Make Payment - Business Customer - Check Payment");


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("AutoPay").equals("Y")))
		{
			resultFlag="skip";
			logger.log(LogStatus.SKIP, "AutoPayEnrollmentViaMakePayment_Business_CreditCard is Skipped");
			throw new SkipException("Skipping the test");
		}

		try
		{
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			String siteNumber = login.get_SiteNumber();
			login.enterUserName(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "UserName entered successfully");
			login.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Password entered successfully");
			login.clickLogin();
			logger.log(LogStatus.INFO, "Clicked on Login button successfully");
			Thread.sleep(5000);

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");

			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);

			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T); 
			Thread.sleep(8000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(8000);
			// Navigating to CFS
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
			Thread.sleep(5000);


			Advance_Search advSearch= new Advance_Search(driver);

			String query="select top 1 u.accountnumber from vw_unitdetails u with(nolock) "+
					"left join autopay ap on ap.storageorderitemid = u.storageorderitemid "+
					"join cltransaction clt with(nolock) on clt.accountorderitemid=u.accountorderitemid "+
					"join customer c with(nolock) on c.customerid = u.customerid "+
					"join accountemail ae with(nolock) on ae.accountid = u.accountid "+
					"join site s with(nolock) on s.siteid = u.siteid "+
					"and not exists (select '1' from autopay ap where ap.storageorderitemid = u.storageorderitemid) "+
					"where u.vacatedate is null "+
					"and u.customertypeid = 91 "+
					"and c.nocreditcards = 0 "+
					"and ae.emailid > 0 "+
					"and s.isactive = 1 "+
					"and 1 = (select count(u2.accountorderitemid) from vw_unitdetails u2 where u2.accountid = u.accountid) "+
					"group by u.accountnumber "+
					"having sum(clt.amount+clt.discountamount)>0 ";

			String accNum=DataBase_JDBC.executeSQLQuery(query);

			Thread.sleep(8000);
			advSearch.enterAccNum(accNum);
			logger.log(LogStatus.INFO, "Enter Account Num in Account field successfully");
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Account Number entered in customer Dashboard successfully" +accNum);
			logger.log(LogStatus.PASS, "Account Number entered in customer Dashboard successfully",image);


			advSearch.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "clicked on Search button successfully");

			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			Thread.sleep(10000);
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Customer Account Dashboard displayed successfully for a Business Customer");
			logger.log(LogStatus.INFO, "CUstomer Account Dashboard displayed",image);

			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(3000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Checking Autopay Status for the Customer before Enrollment");
			logger.log(LogStatus.PASS, "Checking Autopay status for the Customer before Enrollment"+image);

			cust_accdetails.clickMakePayment_Btn();
			logger.log(LogStatus.INFO, "Clicking on Make Payment button successfully");
			Thread.sleep(20000);

			PaymentsPage payments= new PaymentsPage(driver);
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
			String amount = payments.getTotal();
			System.out.println("Amount : "+amount);
			payments.clickOnConfirmWithCustomer_Btn();
			logger.log(LogStatus.INFO, "clicked on Confirm with customer button successfully");
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(1));
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(6000);
			//Validating CFS
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer");
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer "+image);
			Thread.sleep(4000);

			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "clicking on Confirm button successfully");
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			logger.log(LogStatus.INFO, "Switch to Main page successfully");

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);

			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='payment-methods']//span[@class='k-widget k-dropdown k-header payment-method-dropdown']")).click();
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']/ul/li[contains(text(),'Credit')]")).click();
			logger.log(LogStatus.INFO, "select the credit card option  from the list and click on the credit card option");

			Thread.sleep(6000);
			payments.clickmanualentryCC();
			logger.log(LogStatus.INFO, "click on the enter manually tab");
			Thread.sleep(10000);


			// Performing task in Credit card popup //
			if(driver.findElement(By.xpath("//div[@class='k-widget k-window']//span[contains(text(),'Manual Credit Card Entry')]")).isDisplayed()){
				logger.log(LogStatus.INFO, "Credit Card Popup is displayed");
				driver.findElement(By.xpath("//div[@class='k-widget k-window']//span[contains(text(),'Manual Credit Card Entry')]")).click();
			}else{
				logger.log(LogStatus.INFO, "Credit Card Popup is not displayed");
			}

			WebElement frame=driver.findElement(By.xpath("//div[iframe[@id='iframe_Creditcard_info']]/iframe"));
			driver.switchTo().frame(frame);

			//Entering Credit Card Number
			driver.findElement(By.xpath("//input[@id='cardNumber']")).sendKeys("4111111111111111");
			Thread.sleep(5000);
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);

			//Selecting Expiry Month
			robot.keyPress(KeyEvent.VK_0);
			robot.keyRelease(KeyEvent.VK_0);
			robot.keyPress(KeyEvent.VK_0);
			robot.keyRelease(KeyEvent.VK_0);
			robot.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(5000);

			//Selecting Expiry Year
			robot.keyPress(KeyEvent.VK_2);
			robot.keyRelease(KeyEvent.VK_2);
			robot.keyPress(KeyEvent.VK_2);
			robot.keyRelease(KeyEvent.VK_2);
			Thread.sleep(5000);

			//Clicking on Accept Button
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);


			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Entered details in Credit Card popup successfully");
			logger.log(LogStatus.PASS, "Entered details in Credit Card popup successfully",image);

			Thread.sleep(10000);
			driver.findElement(By.xpath("//input[@id='cardholderName']")).click(); 
			Thread.sleep(5000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, 250)");

			driver.findElement(By.xpath("//input[@id='cardholderName']")).sendKeys("Test Name"); 
			Thread.sleep(5000);

			payments.clickCCField();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyPress(KeyEvent.VK_DELETE);
			robot.keyRelease(KeyEvent.VK_DELETE);

			Thread.sleep(5000);
			payments.enterCCAmount(amount);
			logger.log(LogStatus.INFO, "Entered Amount into Amount field");

			Thread.sleep(6000);
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Credit card details and amount details entered successfully");
			logger.log(LogStatus.INFO, "Card Details and amount entered",image);
			Thread.sleep(4000);

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);

			payments.clickCCAutopayCheckBox();
			logger.log(LogStatus.INFO, "Clicked on Autopay check box successfully");


			payments.clickapplybtn();
			logger.log(LogStatus.INFO, "Click on Apply button successfully");

			Thread.sleep(5000);
			payments.click_CollectSignature();
			logger.log(LogStatus.INFO, "Click on Collect signature button successfully");

			Thread.sleep(6000);
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(6000);

			WebElement signature1 = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions actionBuilder1 = new Actions(driver);          
			Action drawAction1 = actionBuilder1.moveToElement(signature1,660,96).click().clickAndHold(signature1)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature1).build();
			drawAction1.perform();
			Thread.sleep(6000);
			//Validating CFS
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer");
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer "+image);
			Thread.sleep(5000);
			payments.clickAccept_Btn();
			logger.log(LogStatus.INFO, "Cust Signature and click on Accept button successfully");

			Thread.sleep(6000);
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			driver.switchTo().window(tabs.get(0));

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			payments.clickApprove_Btn();
			logger.log(LogStatus.INFO, "Click on Approve button successfully");

			Thread.sleep(6000);
			/*

			payments.clickOnConfirmWithCustomer_Btn();
			logger.log(LogStatus.INFO, "click on confirm with customer button");

			// Navigating to Customer Facing Screen
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
			payments.click_SignatureApproveBtn();
			logger.log(LogStatus.INFO, "click on approve button successfully");
			 */
			//mohana
			Thread.sleep(6000);
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switch to Customer interaction screen successfully");
			Thread.sleep(6000);

			WebElement signature2 = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions actionBuilder2 = new Actions(driver);          
			Action drawAction2 = actionBuilder2.moveToElement(signature2,660,96).click().clickAndHold(signature2)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature2).build();
			drawAction2.perform();
			Thread.sleep(6000);
			//Validating CFS
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer");
			logger.log(LogStatus.PASS, "Validating CFS Screen and confirming with Customer "+image);
			Thread.sleep(5000);
			payments.clickAccept_Btn();
			logger.log(LogStatus.INFO, "Cust Signature and click on Accept button successfully");

			Thread.sleep(6000);
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			driver.switchTo().window(tabs.get(0));

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			//payments.clickApprove_Btn();
			driver.findElement(By.xpath("(//a[contains(text(),'Approve')])[2]")).click();
			logger.log(LogStatus.INFO, "Click on Approve button successfully");
			Thread.sleep(6000);
			//mohana
			payments.clickSubmitbtn();
			logger.log(LogStatus.INFO, "click on submit button");

			EmployeeIDPopUpPage empidpage=new EmployeeIDPopUpPage(driver);
			Thread.sleep(3000);
			empidpage.enter_EmpId(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Enter employee id successfully");
			Thread.sleep(3000);

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Enter Employee ID");
			logger.log(LogStatus.PASS, "Enter Employee ID"+image);
			Thread.sleep(5000);			
			//empidpage.clk_OkBtn();
			driver.findElement(By.xpath("//a[contains(text(),'Ok')]")).click();
			logger.log(LogStatus.INFO, "click on ok button successfully");

			Thread.sleep(30000);

			if(driver.findElements(By.xpath("//a[contains(text(),'OK')]")).size()!=0){
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}else{
				logger.log(LogStatus.INFO, "Confirmation Ok popup is not  displyed");
			}

			Thread.sleep(6000);
			cust_accdetails.clickSpaceDetails_tab();
			logger.log(LogStatus.INFO, "Click on Space Details Tab on customer dashboard successfully");

			Thread.sleep(6000);

			Acc_SpaceDetailsPage spaceTab=new Acc_SpaceDetailsPage(driver);
			if(spaceTab.verifyAutopay_Status())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Autopay Status is ON and enrolled and verified in Space Details Tab");
				logger.log(LogStatus.INFO, "Autopay Status is ON and enrolled and verified in Space Details Tab",image);

			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Autopay Status is NOT enrolled in Space Details Tab");
				logger.log(LogStatus.INFO, "Autopay Status is NOT enrolled and verified in Space Details Tab ",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			String spaceNum=cust_accdetails.getCustSpaceNum();
			Thread.sleep(6000);
			cust_accdetails.clickOnManageAutoPay_Lnk();
			logger.log(LogStatus.INFO, "Click on Manage Auto pay button successfully");
			logger.log(LogStatus.PASS, "AutoPay preference screen displayed");

			Thread.sleep(6000);
			if(driver.findElements(By.xpath("//a[@class='psbutton-priority margin-left ok-button']")).size()!=0){
				driver.findElement(By.xpath("//a[@class='psbutton-priority margin-left ok-button']")).click();
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Alert Ok popup displyed");
			}else{
				logger.log(LogStatus.INFO, "Confirmation Ok popup is not  displyed");
			}
			Thread.sleep(6000);
			String xpath="//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/span[contains(text(),'Visa ending')]";
			Thread.sleep(6000);
			if(driver.findElement(By.xpath(xpath)).isDisplayed())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Auto pay Status is checking in Method column");
				logger.log(LogStatus.PASS, "Auto pay Status enabled");
				logger.log(LogStatus.INFO, "Auto pay Status enabled",image);
			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Auto pay Status not enabled");
				logger.log(LogStatus.INFO, "Auto pay Status not enabled",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			driver.findElement(By.xpath("//div[@id='editAutoPayPreferences']//a[text()='Cancel']")).click();
			Thread.sleep(6000);

			//Verify to see account activity created
			cust_accdetails.click_AccountActivities();
			Thread.sleep(8000);

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.getDefault());
			String strTodaysDate = df.format(cal.getTime());


			if(driver.findElements(By.xpath("//div[@id='activities-grid']//table//tbody//tr//td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Autopay']/..//td/div[contains(text(),'Autopay turned on')]")).size()!=0)
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "AutoPay Status is enabled for the space - Customer Account Dashboard");
				logger.log(LogStatus.INFO, "Image",image);

			}else{

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "AutoPay Status is not enabled - Customer Account Dashboard");
				logger.log(LogStatus.INFO, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			//Click on stop Auto pay and verify in Auto pay status in Space Details tab

			/*String xpath_StopAutoPay="//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/a[contains(text(),'Stop')]";
			driver.findElement(By.xpath(xpath_StopAutoPay)).click();
			logger.log(LogStatus.INFO, "Click on Stop Auto pay link successfully");
			Thread.sleep(6000);

			StopAutoPayPopupPage stopAutoPay = new StopAutoPayPopupPage(driver);
			stopAutoPay.enterEmployeeID(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Enter the Employee ID successfully");
			Thread.sleep(6000);
			stopAutoPay.clickConfirm_Btn();
			logger.log(LogStatus.INFO, "Click on Confirm button successfully");
			Thread.sleep(8000);

			String checkValue=driver.findElement(By.xpath("//div[@id='autoPayPreferences-grid']//table/tbody//tr//td/span[text()='"+spaceNum+"']/../..//td/span[contains(text(),'Auto')]")).getText();
			System.out.println(checkValue);
			if(checkValue.contains("AutoPay not enabled for this space")){
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

			driver.findElement(By.xpath("//div[@id='editAutoPayPreferences']//a[text()='Cancel']")).click();
			Thread.sleep(6000);
			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(6000);
			if(spaceTab.verifyStopAutopay_Status())
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "AutoPay Status is disabled for the space - Customer Account Dashboard");
				logger.log(LogStatus.INFO, "Image",image);

			}else{

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "AutoPay Status is not disabled - Customer Account Dashboard");
				logger.log(LogStatus.INFO, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}*/

		}catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Test Script failed due to exception",image);
		}

	}       


	@AfterMethod
	public void afterMethod(){

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path,"AutoPay","AutoPayEnrollmentViaMakePayment_Business_CreditCard" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){    
			Excel.setCellValBasedOnTcname(path,"AutoPay","AutoPayEnrollmentViaMakePayment_Business_CreditCard" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "AutoPay","AutoPayEnrollmentViaMakePayment_Business_CreditCard" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
}
