package Scenarios.Payments;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

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
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;

public class MakeOverPayment_VerifyLedger extends Browser_Factory{

	public ExtentTest logger;
	String resultFlag="pass";
	String path="./src/main/resources/Resources/PS_TestData.xlsx";

	@DataProvider
	public Object[][] getData() 
	{
		return Excel.getCellValue_inlist(path, "Payments","Payments",  "MakeOverPayment_VerifyLedger");
	}


	@Test(dataProvider="getData")
	public void MakeOverPayment_VerifyLedger(Hashtable<String, String> tabledata) throws InterruptedException 
	{


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Payments").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}	

		try{

			//Login to PS Application
			logger=extent.startTest("MakeOverPayment_VerifyLedger","Make an overpayment on the customers account - verify the ledger");
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as PM");

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);

			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			driver.switchTo().window(tabs.get(1));
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath")); 

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

			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
	
			
			//Go to customer dashboard, select Make a payment for payment at home location and navigate to Payment Screen
			PM_Homepage pmhomepage = new PM_Homepage(driver);
			String location = pmhomepage.getLocation();
			String sqlQuery = "select top 1 a.accountnumber from"+
							 " account a"+
							 " join accountorder ao on ao.accountid=a.accountid"+
							 " join accountorderitem aoi on aoi.accountorderid=ao.accountorderid"+
							 " join site s on s.siteid=aoi.siteid"+
							 " join storageorderitem soi on soi.storageorderitemid=aoi.storageorderitemid"+
							 " left join Type T on T.TypeID = SOI.StorageOrderItemTypeID"+
							 " join rentalunit ru on ru.rentalunitid=soi.rentalunitid"+
							 " join productsite ps on ps.productsiteid= ru.ProductSiteid"+
							 " join Product p on p.ProductID = ps.ProductID"+
							 " join cltransaction clt on clt.accountorderitemid=aoi.accountorderitemid"+
							 " where soi.vacatedate is null"+
							 " and s.sitenumber='"+location+"'"+
							 " and soi.StorageOrderItemTypeID=4300"+
							 " and soi.anticipatedvacatedate is null"+
							 " group by a.accountnumber, aoi.accountorderitemid,s.sitenumber, ru.rentalunitnumber,soi.anticipatedvacatedate,T.description, p.Name"+
							 " having sum(clt.amount + clt.discountamount) >0";
			String customerId = DataBase_JDBC.executeSQLQuery(sqlQuery);
			pmhomepage.enter_findCustAddrLocation(customerId);
			pmhomepage.clk_findCustomer();
			Thread.sleep(15000);
			
			driver.findElement(By.xpath("//div[@id='updateResultsPanel']//a[text()='"+customerId+"']")).click();
			Thread.sleep(15000);
			
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to Customer Account Dashboard");
			logger.log(LogStatus.PASS, "Image",image);
			
			Cust_AccDetailsPage custDetails = new Cust_AccDetailsPage(driver);
			custDetails.clickMakePayment_Btn();
			Thread.sleep(10000);
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to Payments Page");
			logger.log(LogStatus.PASS, "Image",image);
			
			PaymentsPage payments= new PaymentsPage(driver);
			payments.clickOnConfirmWithCustomer_Btn();
			Thread.sleep(10000);
			driver.switchTo().window(tabs.get(1));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			Thread.sleep(10000);
			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(6000);
			
			
			//Select "Cash" from Payment Methods dropdown
			payments.selectPaymentMethod("Cash", driver);
			Thread.sleep(5000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Cash Payment Option is selected");
			logger.log(LogStatus.PASS, "Image",image);
			String due = payments.getTotalAmount();
			due = due.substring(1);
			double totalDue = Double.parseDouble(due);
			
			
			
			//Verify when customer pays amount greater than amount due with cash 
			//Verify if the indication "Is enough cash on hand to provide change for this transaction? Yes/No" is displayed 
			double extraAmount = totalDue+1;
			payments.enter_CashAmount(Double.toString(extraAmount));
			Thread.sleep(2000);
			payments.clickapplybtn();
			Thread.sleep(5000);
			String getMessage = payments.getText_ExtraCashMessage();
			logger.log(LogStatus.PASS, "Greater amount than total is accepted");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			if(getMessage.contains("Is enough cash on hand to provide change for this transaction?") & getMessage.contains(" Yes") & getMessage.contains("No")){
				logger.log(LogStatus.PASS, "Indication displayed -- Is enough cash on hand to provide change for this transaction? Yes/No");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Indication not displayed -- Is enough cash on hand to provide change for this transaction? Yes/No");
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			
			payments.click_CollectSignature();
			Thread.sleep(5000);
			String getNotification = payments.getText_Notification();
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			if(getNotification.contains("Please select if there is enough cash on hand.")){
				logger.log(LogStatus.PASS, "Message displayed -- Please select if there is enough cash on hand.");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Message not displayed -- Please select if there is enough cash on hand.");
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			
			logger.log(LogStatus.INFO, "Selecting the Yes option");
			payments.click_EnoughCash_Yes();
			Thread.sleep(2000);
			payments.click_CollectSignature();
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(2000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			List<WebElement> signatures = driver.findElements(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			if(signatures.size() > 0){
				logger.log(LogStatus.PASS, "confirm change signature screen is displayed to indicate that the change is provided after clicking on Yes button");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "confirm change signature screen is not displayed to indicate that the change is provided after clicking on Yes button");
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			WebElement signature = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions actionBuilder = new Actions(driver);          
			Action drawAction = actionBuilder.moveToElement(signature,660,96).click().clickAndHold(signature)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature).build();
			drawAction.perform();
			Thread.sleep(6000);
			payments.clickAccept_Btn();
			
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			payments.clickApprove_Btn();
			Thread.sleep(6000);
			payments.clickSubmitbtn();
			
			TransactionCompletePopup transPopup=new TransactionCompletePopup(driver);
			getMessage = transPopup.getMessage_txnmessage();
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			if(getMessage.contains("I have given cash change to the customer")){
				logger.log(LogStatus.PASS, "Indication that PM has given the cash is displayed.");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Indication that PM has given the cash is not displayed.");
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			
			transPopup.click_CancelBtn();
			Thread.sleep(3000);
			payments.clickCancelbtn();
			Thread.sleep(10000);
			
			custDetails = new Cust_AccDetailsPage(driver);
			custDetails.clickMakePayment_Btn();
			Thread.sleep(10000);
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to Payments Page");
			logger.log(LogStatus.PASS, "Image",image);
			
			payments= new PaymentsPage(driver);
			payments.clickOnConfirmWithCustomer_Btn();
			Thread.sleep(10000);
			driver.switchTo().window(tabs.get(1));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(6000);
			driver.findElement(By.id("confirmButton")).click();
			Thread.sleep(10000);
			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			Thread.sleep(6000);
			
			
			//Select "Cash" from Payment Methods dropdown
			payments.selectPaymentMethod("Cash", driver);
			Thread.sleep(5000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Cash Payment Option is selected");
			logger.log(LogStatus.PASS, "Image",image);
			due = payments.getTotalAmount();
			due = due.substring(1);
			totalDue = Double.parseDouble(due);
			
			
			//Verify when customer pays amount greater than amount due with cash 
			//Verify if the indication "Is enough cash on hand to provide change for this transaction? Yes/No" is displayed 
			extraAmount = totalDue+1;
			payments.enter_CashAmount(Double.toString(extraAmount));
			Thread.sleep(2000);
			payments.clickapplybtn();
			Thread.sleep(5000);
			getMessage = payments.getText_ExtraCashMessage();
			logger.log(LogStatus.PASS, "Greater amount than total is accepted");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			if(getMessage.contains("Is enough cash on hand to provide change for this transaction?") & getMessage.contains(" Yes") & getMessage.contains("No")){
				logger.log(LogStatus.PASS, "Indication displayed -- Is enough cash on hand to provide change for this transaction? Yes/No");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Indication not displayed -- Is enough cash on hand to provide change for this transaction? Yes/No");
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			
			logger.log(LogStatus.INFO, "Selecting the No option");
			payments.click_EnoughCash_No();
			Thread.sleep(2000);
			payments.click_CollectSignature();
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(2000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			signatures = driver.findElements(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			if(signatures.size() > 0){
				logger.log(LogStatus.PASS, "confirm cash to credit screen is displayed to indicate that the change is applied as credit to customer account. ");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "confirm cash to credit screen is displayed to indicate that the change is applied as credit to customer account. ");
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			signature = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			actionBuilder = new Actions(driver);          
			drawAction = actionBuilder.moveToElement(signature,660,96).click().clickAndHold(signature)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature).build();
			drawAction.perform();
			Thread.sleep(6000);
			payments.clickAccept_Btn();
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			payments.clickApprove_Btn();
			Thread.sleep(6000);
			
			String getText = driver.findElement(By.xpath("//div[@id='payment-methods']//div[@id='payment-methods']")).getText();
			if(getText.contains("Overpayment to be Applied to Future Balances")){
				logger.log(LogStatus.PASS, "Message found -- Overpayment to be Applied to Future Balances");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Message not found -- Overpayment to be Applied to Future Balances");
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			
			payments.clickSubmitbtn();
			
			
			getMessage = driver.findElement(By.xpath("//div[@id='transaction-complete-modal']")).getText();
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			if(!getMessage.contains("I have given cash change to the customer")){
				logger.log(LogStatus.PASS, "Indication that PM has given the cash is not displayed as expected");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Indication that PM has given the cash is displayed which is not expected");
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			
			transPopup.click_CancelBtn();
			Thread.sleep(3000);
			payments.clickCancelbtn();
			Thread.sleep(10000);

		}catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
		}
	}
	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "Payments","MakeOverPayment_VerifyLedger" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Payments","MakeOverPayment_VerifyLedger" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Payments","MakeOverPayment_VerifyLedger" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();

	}
}
