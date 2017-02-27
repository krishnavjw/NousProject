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
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;

public class MakePayment_MiscCharges_NoChangeDue extends Browser_Factory{
 
	public ExtentTest logger;
	String resultFlag="pass";
	String path="./src/main/resources/Resources/PS_TestData.xlsx";

	@DataProvider
	public Object[][] getData() 
	{
		System.out.println(this.getClass().getSimpleName());
		return Excel.getCellValue_inlist(path, "Payments","Payments",  this.getClass().getSimpleName());
	}

	@Test(dataProvider="getData")
	public void MakePayment_MiscCharges_NoChangeDue(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Payments").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}	

		try{

			//Login to PS Application
			logger=extent.startTest("MakePayment_MiscCharges_NoChangeDue","Make payment for a customer who incurred misc charges with No Change Due");
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
			
			//Locate a customer from Home Page Advance Search
			PM_Homepage pmhomepage = new PM_Homepage(driver);
			String customerId = tabledata.get("AccountNum");
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
			String getTotalDue = custDetails.getTotalDue();
			if(getTotalDue.contains("$0.00")){
				
			
			custDetails.clickMakePayment_Btn();
			Thread.sleep(10000);
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Navigated to Payments Page");
			logger.log(LogStatus.PASS, "Image",image);
			
			PaymentsPage payments= new PaymentsPage(driver);
			String monthlyRent = payments.getMonthlyRent();
			String rentTax = payments.getRentTax();
			String miscCharge = payments.getMiscCharge();
			String totalDue = payments.getTotalDue();
			
			monthlyRent = monthlyRent.substring(1);
			rentTax = rentTax.substring(1);
			miscCharge = miscCharge.substring(1);
			totalDue = totalDue.substring(1);
			
			double dMonthlyRent = Double.parseDouble(monthlyRent);
			double dRentTax = Double.parseDouble(rentTax);
			double dMiscCharge = Double.parseDouble(miscCharge);
			double dTotalDue = Double.parseDouble(totalDue);
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			if(dTotalDue == (dMonthlyRent+dRentTax+dMiscCharge)){
				logger.log(LogStatus.PASS, "Total due matched with the sum of incurred charges");
				logger.log(LogStatus.PASS, "Monthly Rent: "+monthlyRent+"  Rent Tax: "+rentTax+"  Misc Charge: "+miscCharge+"  Total Due: "+totalDue);
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Total due not matched with the sum of incurred charges");
				logger.log(LogStatus.FAIL, "Monthly Rent: "+monthlyRent+"  Rent Tax: "+rentTax+"  Misc Charge: "+miscCharge+"  Total Due: "+totalDue);
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			
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
			
			payments.selectPaymentMethod("Cash", driver);
			Thread.sleep(5000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Cash Payment Option is selected");
			logger.log(LogStatus.PASS, "Image",image);
			
			payments.enter_CashAmount(Double.toString(dMiscCharge));
			Thread.sleep(2000);
			payments.clickapplybtn();
			Thread.sleep(6000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Payment entered");
			logger.log(LogStatus.PASS, "Image",image);
			payments.clickSubmitbtn();
			
			TransactionCompletePopup transPopup=new TransactionCompletePopup(driver);
			transPopup.enterEmpNum(tabledata.get("UserName"));
			transPopup.clickOk_btn();
			logger.log(LogStatus.PASS, "Payment made for the total due");
			Thread.sleep(20000);
			
			logger.log(LogStatus.INFO, "Navigating to the dashboard again to verify the payment");
			custDetails = new Cust_AccDetailsPage(driver);
			getTotalDue = custDetails.getTotalDue();
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			if(getTotalDue.contains("$0.00 ")){
				logger.log(LogStatus.PASS, "Payment made successfully and the total due is displayed as zero now");
				logger.log(LogStatus.PASS, "Image",image);
			}else{
				logger.log(LogStatus.FAIL, "Payment not successful as the total due is not displayed as zero now");
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			
			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer total due is not zero");
				logger.log(LogStatus.FAIL, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
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
			Excel.setCellValBasedOnTcname(path, "Payments",this.getClass().getSimpleName() , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "Payments",this.getClass().getSimpleName() , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Payments",this.getClass().getSimpleName() , "Status", "Skip");
		}


		extent.endTest(logger);
		extent.flush();

	}
}
