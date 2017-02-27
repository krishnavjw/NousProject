package Scenarios.MiscallaneousCharges;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
import Pages.MakePaymentPages.PaymentsPage;
import Pages.MiscallaneousCharges.Misccharges;
import Scenarios.Browser_Factory;

public class Verify_That_Misc_Charges_Can_Be_Added_For_Customer_Having_Currently_Renting_Spaces extends Browser_Factory {
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");
	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"Miscellaneous","Miscellaneous", "Verify_That_Misc_Charges_Can_Be_Added_For_Customer_Having_Currently_Renting_Spaces");
	}
	@Test(dataProvider="getLoginData")
	public void Customer_MiscellaneousCharges_testcaseCurrently_Renting_Spaces (Hashtable<String, String> tabledata) throws Exception 
	{
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Miscellaneous").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}
		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);
		try{
			logger=extent.startTest("Verify_That_Misc_Charges_Can_Be_Added_For_Customer_Having_Currently_Renting_Spaces","Customer_MiscellaneousCharges - Customer Having Currently Renting Space");
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);
			/*
			//=================Handling customer facing device========================
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);

			String biforstNum = Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum + "", true);

			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, "t");
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);


			Thread.sleep(5000);


			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			Reporter.log(tabs.size() + "", true);
			driver.switchTo().window(tabs.get(1));
			driver.get("http://wc2qa.ps.com/CustomerScreen/Mount");

			List<WebElement> biforstSystem = driver.findElements(
					By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
			for (WebElement ele : biforstSystem) {
				if (biforstNum.equalsIgnoreCase(ele.getText().trim())) {
					Reporter.log(ele.getText() + "", true);
					ele.click();
					break;
				}
			}

			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			driver.navigate().refresh();
			Thread.sleep(8000);
			driver.findElement(By
					.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']"))
			.click();
			Thread.sleep(10000);
			 */

			/*			Dashboard_BifrostHostPopUp Bifrostpop1= new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			Bifrostpop1.clickContiDevice();
			Thread.sleep(10000);*/
			Thread.sleep(7000);
			try
			{
				Dashboard_BifrostHostPopUp Bifrostpop1= new Dashboard_BifrostHostPopUp(driver);
				Bifrostpop1.clickContiDevice(); 
			}
			
			catch(Exception e)
			{
				System.out.println("");
			}
			
			
			Thread.sleep(2000);
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			logger.log(LogStatus.PASS, "Created object for the PM home page sucessfully");
			Thread.sleep(3000);
			if(pmhomepage.isexistingCustomerModelDisplayed()){
				logger.log(LogStatus.PASS, "Existing Customer module is present in the PM DashBoard sucessfully");
			}else{
				logger.log(LogStatus.FAIL, "Existing Customer module is not present in the PM DashBoard");
			}
			Thread.sleep(4000);
			if(pmhomepage.get_findACustomerAtThisLocText().contains(tabledata.get("FindCustomeratLocText"))){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Find a Customer at this Location is displayed successfully");
				logger.log(LogStatus.INFO, "Find a Customer at this Location is displayed successfully",image);
			}
			else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Find a Customer at this Location is not displayed ");
				logger.log(LogStatus.INFO, "Find a Customer at this Location is not displayed ",image);
			}
			Thread.sleep(3000);
			if(pmhomepage.get_findACustomerText().trim().contains(tabledata.get("CustomerButtontext"))){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Find Customer button is displayed successfully");
				logger.log(LogStatus.INFO, "Find Customer button is displayed successfully",image);
			}
			else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Find Customer button  is not displayed ");
				logger.log(LogStatus.INFO, "Find Customer button is not displayed ",image);
			}
			Thread.sleep(1000);
			pmhomepage.clk_AdvSearchLnk();
			logger.log(LogStatus.PASS, "clicked on advanced search link in the PM Dashboard sucessfully");
			Thread.sleep(6000);
			Advance_Search search=new Advance_Search(driver);
			logger.log(LogStatus.PASS, "creating object for advance search page sucessfully");
			if(search.verifyAdvSearchPageIsOpened())
			{
				logger.log(LogStatus.PASS, "Advanced Search page is opened");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else{
				logger.log(LogStatus.PASS, "Advanced Search page is not opened");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			String SiteNu=driver.findElement(By.xpath("//span[@class='header-location-nickname']")).getText();
			String[] sitenum=SiteNu.split("-");
			String SiteNumber=sitenum[0];
			logger.log(LogStatus.PASS, "location number is:"+SiteNumber);
			Thread.sleep(2000);
			String query1="select top 1 a.accountnumber "+
					"from account a "+
					"join accountorder ao on ao.accountid=a.accountid "+
					"join Customer c on c.CustomerID=a.CustomerID "+
					"join accountorderitem aoi on aoi.accountorderid=ao.accountorderid "+
					"join site s on s.siteid=aoi.siteid "+
					"join storageorderitem soi on soi.storageorderitemid=aoi.storageorderitemid "+
					"join rentalunit ru on ru.rentalunitid=soi.rentalunitid "+
					"join cltransaction clt on clt.accountorderitemid=aoi.accountorderitemid "+
					"where soi.vacatedate is null "+
					"and s.sitenumber='"+SiteNumber+"' and c.CustomerTypeID=91 "+
					"group by a.accountnumber, aoi.accountorderitemid, ru.rentalunitnumber "+
					"having sum(clt.amount + clt.discountamount)>0 "+
					"order by 1 ";
			String accnum=DataBase_JDBC.executeSQLQuery(query1);
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "Fetching account number from database and account number is:"+accnum);
			Thread.sleep(2000);
			search.enterAccNum(accnum);
			logger.log(LogStatus.INFO, "Enter existing customer Account Num in Account field successfully");
			Thread.sleep(3000);
			search.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "clicking on Search button successfully");
			Thread.sleep(8000);
			Cust_AccDetailsPage cust_accdetails= new Cust_AccDetailsPage(driver);
			Thread.sleep(3000);
			if(cust_accdetails.isCustdbTitleDisplayed()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "customer Dashboard is displayed successfully",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "customer Dashboard is not displayed ",image);
			}
			Thread.sleep(4000);
			String toatlDueNowAmtBeforeMakePymt=cust_accdetails.getTotalDue();
			logger.log(LogStatus.PASS, "Total due now amount before making payment in customer dashbaord is:"+toatlDueNowAmtBeforeMakePymt);
			Thread.sleep(10000);
			JavascriptExecutor js1 = (JavascriptExecutor)driver;
			js1.executeScript("window.scrollBy(1000,0)", "");
			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(4000);
			String spaceNum=cust_accdetails.spacenum_gettext();
			js1.executeScript("window.scrollBy(0,1000)", "");
			driver.findElement(By.xpath("//a[contains(text(),' Add Misc Fee')]")).click();
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "Add Misc Fee link ");
			logger.log(LogStatus.INFO, "Add Misc Fee link",image1);
			Thread.sleep(8000);
			PaymentsPage payments= new PaymentsPage(driver);
			payments.selectPaymentMethod_mischarges(spaceNum, driver);
			Thread.sleep(10000);
			Misccharges mischrge= new Misccharges(driver);
			payments.mischarges_dropdown(tabledata.get("mischarges_dropdown1"), driver);
			logger.log(LogStatus.INFO, "Select the space for mis charges ");
			Thread.sleep(5000);
			mischrge.Add_charges();
			Thread.sleep(5000);
			try{
				Thread.sleep(3000);
				mischrge.ledger_note(2, tabledata.get("Notes"));
				Thread.sleep(2000);
				mischrge.demage_charge();
				Thread.sleep(5000);
				payments.mischarges_dropdown(tabledata.get("mischarges_dropdown2"), driver);
				Thread.sleep(3000);
				mischrge.Add_charges();
				Thread.sleep(3000);
				mischrge.ledger_note(3,tabledata.get("Notes"));
				Thread.sleep(2000);
				mischrge.good_in_property();
				Thread.sleep(2000);
			}
			catch(Exception e){
				System.out.println("");
			}
			js1.executeScript("window.scrollBy(0,100)", "");
			mischrge.webchamp_checkbox();
			Thread.sleep(2000);
			String amount1=mischrge.misc_amount();
			Thread.sleep(2000);
			if(amount1.isEmpty()){
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Charge Amount is not displayed---------------- "+amount1);
				logger.log(LogStatus.INFO, "Charge Amount is not displayed ",image);
			}else{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Charge Amount is displayed----------------- "+amount1);
				logger.log(LogStatus.INFO, "Charge Amount is displayed",image);
			}
			payments.mischarges_dropdown(tabledata.get("mischarges_dropdown3"), driver);
			Thread.sleep(2000);
			mischrge.Add_charges();
			Thread.sleep(3000);
			boolean errormsg=driver.getPageSource().contains("Only one charge for Abandoned Goods is permitted");
			if(errormsg){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Only one charge for Abandoned Goods is permitted -Error message is displayed");
				logger.log(LogStatus.INFO, "Error message is displayed",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Only one charge for Abandoned Goods is permitted-Error message is not  displayed ");
				logger.log(LogStatus.INFO, "Error message is not displayed ",image);
			}
			js1.executeScript("window.scrollBy(0,250)", "");
			Thread.sleep(4000);
			mischrge.maintenance_issue_checkbox();
			Thread.sleep(2000);
			mischrge.maintenace("Walls", driver);
			Thread.sleep(3000);
			mischrge.clk_RemoveDamageBtn();
			Thread.sleep(3000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(250,500)");
			Thread.sleep(6000);
			mischrge.save_button();
			Thread.sleep(2000);
			driver.findElement(By.id("enteredEmployeeNumber")).sendKeys(tabledata.get("UserName"));
			Thread.sleep(2000);
			driver.findElement(By.xpath("//a[contains(text(),'Confirm')]")).click();
			Thread.sleep(4000);
			try {
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				logger.log(LogStatus.WARNING, "A error popup saying 'email confirmation failed'is displayed, Clicked on 'OK' to continue the flow");
			} catch (Exception e) {
				System.out.println("");
			}
			Thread.sleep(7000);
			cust_accdetails.click_AccountActivities();
			logger.log(LogStatus.INFO, "Clicked on Account Activities tab in customer dashboard screen");
			Thread.sleep(9000);
			boolean Miscellaneous_Chargedisplay=cust_accdetails.VerifyMiscellaneousCharge_Link();
			if(Miscellaneous_Chargedisplay){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "customer Activity page is displayed Miscellaneous_Charge with note");
				logger.log(LogStatus.INFO, "customer Activity page is displayed Miscellaneous_Charge with note",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "customer Activity page is not displayed Miscellaneous_Charge with note ");
				logger.log(LogStatus.INFO, "customer Activity page is not displayed Miscellaneous_Charge with note ",image);
			}
			Actions act= new Actions(driver);
			act.moveToElement(cust_accdetails.MiscellaneousChargeArrowbutton());
			cust_accdetails.MiscellaneousChargeArrowbutton().click();
			String bandoned_small=cust_accdetails.Abandoned_Goods_Small();
			Thread.sleep(3000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0,-500)");
			Thread.sleep(3000);
			String toatlDueNowAmtafterMakePymt=cust_accdetails.getTotalDue();
			Thread.sleep(3000);
			cust_accdetails.viewdetails_click();
			Thread.sleep(6000);
			String total_view_detail_due=cust_accdetails.viewdetails_total_due();
			if(total_view_detail_due.equals(toatlDueNowAmtafterMakePymt)){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Total due is displaying Correctly in Customer Account Dashboard. ");
				logger.log(LogStatus.INFO, "Total due is displaying Correctly in Customer Account Dashboard. ",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Total due is not displaying Correctly Customer Account Dashboard.  ");
				logger.log(LogStatus.INFO, "Total due is not displaying Correctly Customer Account Dashboard. ",image);
			}
			driver.findElement(By.xpath("//a[contains(text(),'Cancel')]")).click();
			Thread.sleep(5000);
			cust_accdetails.clickMakePayment_Btn();
			logger.log(LogStatus.INFO, "clicking on Make Payment button successfully in customer dashboard");
			Thread.sleep(5000);
			String value= cust_accdetails.Abandoned_Goods_gettext(spaceNum);
			if(value.contains(bandoned_small)){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Abandoned Goods Small displaying Correctly");
				logger.log(LogStatus.INFO, "Abandoned Goods Small displaying Correctly",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Abandoned Goods Small is not displaying Correctly ");
				logger.log(LogStatus.INFO, "TAbandoned Goods Small is  not displaying Correctly ",image);
			}
			/* String missvaluequery="Select top 1 Amount from CLTransaction where CLTransactionMasterID=( "

						+"Select top 1 CLTransactionMasterID from CLTransactionMaster where AccountID='"+accnum+"' order by LastUpdate desc)";
			 */
			String missvaluequery=" Select top 1 clt.amount from CLTransactionMaster cltm "+
					"join cltransaction clt on cltm.cltransactionmasterid=clt.cltransactionmasterid "+
					"join account a on cltm.accountid=a.accountid "+
					"where a.Accountnumber='"+accnum+"' order by cltm.LastUpdate desc ";  // Updated the Query in 110 Machine
			String missvalue=DataBase_JDBC.executeSQLQuery(missvaluequery);
			Thread.sleep(8000);
			logger.log(LogStatus.PASS, "Fetching Missvalue from database and account number is:"+missvalue);
			if(missvalue.contains(amount1)){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Abandoned Goods Small displaying Correctly in DB CLTransactionMaster and CLTransaction table--"+missvalue);
				logger.log(LogStatus.PASS, "Abandoned Goods Small displaying Correctly in UI --"+amount1);
				logger.log(LogStatus.INFO, "Abandoned Goods Small displaying Correctly in DB",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Abandoned Goods Small not displaying Correctly in DB CLTransactionMaster and CLTransaction table--"+missvalue);
				logger.log(LogStatus.FAIL, "Abandoned Goods Small not displaying Correctly in UI--"+amount1);
				logger.log(LogStatus.INFO, "Abandoned Goods Small is  not displaying Correctly in DB",image);
			}
			String mainvalue="select Maintenance from rentalunit ru "+
					"join ProductSite PS on (ps.ProductSiteID=ru.ProductSiteID) "+
					"where ru.rentalunitnumber='"+spaceNum+"' "+
					"and siteid=(select SiteID from Site where SiteNumber='"+SiteNumber+"')";
			String maintatencevalue=DataBase_JDBC.executeSQLQuery(mainvalue);
			Thread.sleep(8000);
			logger.log(LogStatus.PASS, "Fetching Missvalue from database and account number is:"+maintatencevalue);
			if(maintatencevalue.contains("1")){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "When abandoned good is added to the space, maintenance flag should be set to-----"+maintatencevalue);
				logger.log(LogStatus.INFO, "Abandoned Goods Small maintanace value set in DB to 1",image);
			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Abandoned Goods Small maintanace value not set in DB to 1 ");
				logger.log(LogStatus.INFO, "Abandoned Goods Small maintanace value not set in DB to 1",image);
			}
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
			Excel.setCellValBasedOnTcname(path, "Miscellaneous","Verify_That_Misc_Charges_Can_Be_Added_For_Customer_Having_Currently_Renting_Spaces" , "Status", "Pass");
		}else if (resultFlag.equals("fail")){
			Excel.setCellValBasedOnTcname(path, "Miscellaneous","Verify_That_Misc_Charges_Can_Be_Added_For_Customer_Having_Currently_Renting_Spaces" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "Miscellaneous","Verify_That_Misc_Charges_Can_Be_Added_For_Customer_Having_Currently_Renting_Spaces" , "Status", "Skip");
		}
		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);
	}
}
