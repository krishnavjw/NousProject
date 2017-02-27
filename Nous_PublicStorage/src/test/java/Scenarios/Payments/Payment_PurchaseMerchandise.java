package Scenarios.Payments;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
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
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.MerchandisePage;
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;

public class Payment_PurchaseMerchandise extends Browser_Factory {
	
	public ExtentTest logger;
	String resultFlag="pass";
	String path="./src/main/resources/Resources/PS_TestData.xlsx";

	@DataProvider
	public Object[][] getPurchasemerchandise() 
	{
		return Excel.getCellValue_inlist(path, "Payments","Payments", "Payment_PurchaseMerchandise");
	}
	

	@Test(dataProvider="getPurchasemerchandise")
	public void Payment_PurchaseMerchandise(Hashtable<String, String> tabledata) throws InterruptedException 
	{


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Payments").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}	

		try{
			
			//Login to PS Application
			logger=extent.startTest("Payment_PurchaseMerchandise","Purchase Merchandise with Payment");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);
			
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
			
			PM_Homepage pmhomepage = new PM_Homepage(driver);
			logger.log(LogStatus.INFO, "PM homepage object created");
			
			//Click on Advance Search link
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(5000);
			logger.log(LogStatus.PASS, "Clicked on Advance search link successfully");
			
			Advance_Search advsearch=new Advance_Search(driver);
			logger.log(LogStatus.INFO, "Created Object for Advance Search page");
			
			//Enter Account Number in Advance search page
			advsearch.enterAccNum(tabledata.get("AccountNum"));
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "Account Number Entered successfully :"+tabledata.get("AccountNum"));
	  		advsearch.clickSearchAccbtn();
	  		Thread.sleep(6000);
	  		logger.log(LogStatus.INFO, "Clicked on Search button successfully");
	  		Reporter.log("Clicked on Search button", true); 
	  		Thread.sleep(10000);
			
	  		//Customer Dashboard Page
	  		Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
	  		logger.log(LogStatus.INFO, "Customer Account Details Page object created");
			Thread.sleep(8000);
			cust_accdetails.clickMakePayment_Btn();
			logger.log(LogStatus.PASS, "Clicked on Make Payment button successfully");
			Thread.sleep(8000);
			
			//Select Date and Click on Add/Edit Cart
			PaymentsPage payments= new PaymentsPage(driver);
			logger.log(LogStatus.INFO, "Payment page object created");
			Thread.sleep(8000);
			payments.selectPaymentThrough();
			logger.log(LogStatus.INFO, "Selected Date from Pay Through Dropdown");
			Thread.sleep(5000);
			payments.clk_AddEditCart();
			logger.log(LogStatus.INFO, "Clicked on Add/Edit Cart Successfully");
			Thread.sleep(5000);
			
			//Enter Quantity and Click on Close button in merchandise page
			MerchandisePage merchandisePage = new MerchandisePage(driver);
			logger.log(LogStatus.INFO, "Created Object for merchandise page");
			merchandisePage.clk_AllOption();
			logger.log(LogStatus.INFO , "Selected All Check Box");
			Thread.sleep(5000);
			merchandisePage.enter_Quantity("2");
			logger.log(LogStatus.INFO, "Entered Quantity in the textbox");
			Thread.sleep(5000);
			merchandisePage.clk_AddItemsToCart();
			logger.log(LogStatus.INFO, "Items are added in the cart");
			Thread.sleep(5000);
			merchandisePage.clk_CloseBtn();
			logger.log(LogStatus.PASS, "Clicked on close button successfully");
			Thread.sleep(8000);
			
			//Select Promotion and Click on Apply button
			payments.selectPromotion();
			logger.log(LogStatus.INFO, "Selected Promotion in the merchandise");
			Thread.sleep(5000);
			payments.clk_ApplybtnMer();
			logger.log(LogStatus.INFO, "Clicked on Apply button successfully");
			Thread.sleep(8000);
			
			//Confirm with customer in the Customer Facing Screen
			payments.clickOnConfirmWithCustomer_Btn();
			logger.log(LogStatus.INFO, "Clicked on Confirm with Customer Button successfully");
			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(8000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switch to Customer Facing screen successfully");
			Thread.sleep(8000);
			String totalAmnt = driver.findElement(By.xpath("//span[@id='balance']")).getText().trim();
			logger.log(LogStatus.INFO, "Total due now amount:"+totalAmnt);
			Thread.sleep(2000);
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.INFO, "Clicked on Confirm button successfully");
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(6000);
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switched back to Main page successfully");
			Thread.sleep(8000);
			
			//Select Cash in payment method, enter Amount and click on Submit button
			payments.selectPaymentMethod("All", driver);
			logger.log(LogStatus.INFO, "Selected the Cash option from Payment dropdown successfully");
			Thread.sleep(8000);
			payments.clear_CashAmount();
			Thread.sleep(3000);
			payments.enter_CashAmount(totalAmnt);
			logger.log(LogStatus.INFO, "Entered Amount in the Payment textbox");
			Thread.sleep(6000);
			payments.clickapplybtn();
			logger.log(LogStatus.INFO, "Clicked on Apply button");
			Thread.sleep(8000);
			payments.clickSubmitbtn();
			logger.log(LogStatus.INFO, "Clicked on Submit button successfully");
			Thread.sleep(5000);
			
			//Enter Employee Number and click on Ok button
			Thread.sleep(6000);
			TransactionCompletePopup transPopup=new TransactionCompletePopup(driver);
			logger.log(LogStatus.INFO, "Transaction Complete Popup object created");
			Thread.sleep(3000);
			transPopup.enterEmpNum(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered Employee Id successfully");
			Thread.sleep(6000);
			transPopup.clickOk_btn();
			logger.log(LogStatus.PASS, "Clicked on Ok button successfully");
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
			Excel.setCellValBasedOnTcname(path, "Payments","Payment_PurchaseMerchandise" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Payments","Payment_PurchaseMerchandise" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Payments","Payment_PurchaseMerchandise" , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);

	}
	

}
