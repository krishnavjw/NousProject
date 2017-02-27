package Scenarios.Merchandise;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import Pages.CustDashboardPages.Buy_MerchandisePage;
import Pages.CustDashboardPages.CheckoutPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class ExistingCust_buyingmerchandise_usingBuymerchandiselink_DMdashboard_Taxexempt extends Browser_Factory {

	public ExtentTest logger;

	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";

	String query = "select  top 1 a.accountnumber, soi.vacatedate, soi.StorageOrderItemTypeID, AOI.notax NoTax, T.description as Customer_Type, TS.Description As Authorized_Access"
			+ " from contact c" + " join customer cu on cu.contactid=c.contactid"
			+ " join account a on a.customerid=cu.customerid" + " join accountorder ao on ao.accountid=a.accountid"
			+ " join accountorderitem aoi on aoi.accountorderid=ao.accountorderid"
			+ " join storageorderitem soi on soi.storageorderitemid=aoi.storageorderitemid"
			+ " join type t on t.typeid=soi.StorageOrderItemTypeID"
			+ " inner join CLTransactionMaster CTM on CTM.AccountID = A.AccountID"
			+ " inner join CLPayment CLP on CLP.CLTransactionMasterID = CTM.CLTransactionMasterID"
			+ " left join StorageOrderItemContact SOIC on SOIC.StorageOrderItemID = SOI.StorageOrderItemID "
			+ " left join Type TS on TS.TypeID = SOIC.ContactTypeID"
			+ " left join AccountOrderItemProductOption AOIP on AOIP.AccountOrderItemID = AOI.AccountOrderItemID"
			+ " where AOI.notax = 0 and soi.vacatedate is null and soi.StorageOrderItemTypeID=4300"
			+ " and AOI.notax = 0 and CLP.PaymentSourceTypeID = 258 and soic.contacttypeid = 3152 "
			+ " AND aoip.accountorderitemproductoptionid is null";

	@DataProvider
	public Object[][] getCustSearchData() {
		return Excel.getCellValue_inlist(path, "Merchandise", "Merchandise",
				"ExistingCust_buyingmerchandise_usingBuymerchandiselink_DMdashboard_Taxexempt");
	}

	@Test(dataProvider = "getCustSearchData")
	public void payment(Hashtable<String, String> tabledata) throws InterruptedException {
		try {
			logger = extent.startTest("ExistingCust_buyingmerchandise_usingBuymerchandiselink_DMdashboard_Taxexempt",
					"ExistingCust_buyingmerchandise_usingBuymerchandiselink_DMdashboard_Taxexempt");
			Reporter.log("Test case started: " + testcaseName, true);

			if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("Merchandise").equals("Y"))) {
				resultFlag = "skip";
				throw new SkipException("Skipping the test");
			}

			// Login To the Application
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as DM");

			String scpath = null;

			String image = null;



			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);

			String biforstNum=Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum+"",true);

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			Thread.sleep(5000);

			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(5000);
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
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(10000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);


			// ===============================================================================

			// Verify that the user lands on the "PM Dashboard" screen after
			// login and walkin cust title

			/*
			 * PM_Homepage pmhomepage=new PM_Homepage(driver);
			 * Thread.sleep(3000); logger.log(LogStatus.INFO,
			 * "PM Home page object created successfully");
			 * 
			 * 
			 * Thread.sleep(10000); pmhomepage.clk_AdvSearchLnk();
			 * Thread.sleep(3000); logger.log(LogStatus.INFO,
			 * "clicked on findcustomer successfully");
			 */

			DM_HomePage dmhome = new DM_HomePage(driver);
			if (dmhome.is_DMDashBoardTitle()) {

				Thread.sleep(2000);
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "DM  Dashboard displayed successfully ");
				logger.log(LogStatus.PASS, "Image", image);
			}

			else {
				Thread.sleep(2000);
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "DM  Dashboard not displayed   ");
				logger.log(LogStatus.FAIL, "DM  Dashboard not displayed ", image);
			}

			PM_Homepage pmhome = new PM_Homepage(driver);
			Thread.sleep(1000);

			// Select location
			driver.findElement(By.xpath("//form[@id='QuickSearchForm']//span[@class='k-input']")).click();
			Thread.sleep(2000);

			List<WebElement> AllOptions = driver.findElements(By.xpath("//div[@id='siteId-list']/ul/li"));
			int totalSize = AllOptions.size();
			for (int i = 0; i < totalSize; i++) {
				if (i == 1) {
					AllOptions.get(i).click();
					break;
				}
			}

			// Click on Advance Search
			pmhome.clk_DMAdvSearchLnk();
			logger.log(LogStatus.INFO, "clicked on Advance search link successfully");

			Advance_Search advsearch = new Advance_Search(driver);


			// Fetching Acc num through Quer who is having Balance due
			String accNum = DataBase_JDBC.executeSQLQuery(query);
			advsearch.enterAccNum(accNum);
			logger.log(LogStatus.INFO, "Entered account num and clicked on search btn successfully: "+accNum);
			advsearch.clickSearchAccbtn();
			Thread.sleep(10000);

			// js.executeScript("window.scrollTo(0,
			// document.body.scrollHeight)");

			// screenshot

			Cust_AccDetailsPage cust = new Cust_AccDetailsPage(driver);
			Thread.sleep(2000);



			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image ",image);

			// selecting Buy Merchandise from Quick linkd Dropdown

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(250,0)", "");
			Thread.sleep(5000);
			js.executeScript("window.scrollBy(0,250)", "");
			Thread.sleep(5000);
			driver.findElement(
					By.xpath("//div[@class='actions clearfix-container']//span[contains(text(),'Quick Links')]"))
			.click();

			JavascriptExecutor je = (JavascriptExecutor) driver;
			// Identify the WebElement which will appear after scrolling down
			WebElement element = driver
					.findElement(By.xpath("//ul[@class='k-list k-reset ps-container ps-active-y']/li[3]"));
			// now execute query which actually will scroll until that element
			// is not appeared on page.
			je.executeScript("arguments[0].scrollIntoView(true);", element);
			element.click();
			logger.log(LogStatus.INFO, "Selected Buy Merchandise option from Quick links dropdown successfully");
			Reporter.log("Selected Buy Merchandise option from Quick links dropdown successfully", true);
			Thread.sleep(5000);

			Buy_MerchandisePage buy_Merch = new Buy_MerchandisePage(driver);

			if(buy_Merch.verify_pagetitle()){
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String  image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Buy merchandise page is displayed ");
				logger.log(LogStatus.INFO, "Buy merchandise page is displayed ",image1);
			}
			else{
				String  scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String  image1=logger.addScreenCapture(scpath1);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Buy merchandise page is not displayed  ");
				logger.log(LogStatus.INFO, "Buy merchandise page is not displayed ",image1);
			}
			//select any category and verify all the item details

			// Verify UI Elements

			Thread.sleep(1000);

			if (driver.findElements(By.xpath("//div[@id='productStack']//div[contains(text(),'SKU ')]")).size() > 0) {
				logger.log(LogStatus.PASS, "SKU information is displayed successfully ");
			} else {

				logger.log(LogStatus.FAIL, "SKU information is not  displayed  ");
			}

			Thread.sleep(3000);
			if (driver.findElements(By.xpath("//div[@id='productStack']//div[text()='Quantity:']")).size() > 0) {
				logger.log(LogStatus.PASS, "Quantity information is displayed successfully ");
			} else {

				logger.log(LogStatus.FAIL, "Quantity information is not  displayed  ");
			}

			Thread.sleep(3000);

			if (driver
					.findElements(By
							.xpath("//div[@id='productStack']//div[@class='floatleft product-details-container']//div[@class='bold']"))
					.size() > 0) {
				logger.log(LogStatus.PASS, "Price per unit displayed successfully ");
			} else {

				logger.log(LogStatus.FAIL, "Price per unit is not  displayed  ");
			}
			Thread.sleep(3000);

			// Get Item Name

			String productNameinDeccriptionSection = driver.findElement(By.xpath("//div[@id='productStack']//div[@class='merchandise-description']//div[contains(text(),'Lock')][@class='title bold half-margin-bottom']")).getText();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "product name is displayed:"+productNameinDeccriptionSection);
			// enter quantity of items
			// buy_Merch.enter_Quantity_item1(tabledata.get("Quantity_item1"));

			driver.findElement(By.xpath("//div[contains(text(),'Lock')]/following-sibling::div//input"))
			.sendKeys(tabledata.get("Quantity_item1"));
			Thread.sleep(3000);

			// Click on Add Items To cart
			buy_Merch.click_AddItemsToCart_btn();
			logger.log(LogStatus.INFO, "Entered required quantity of items and clicked on Add items to cart button");
			Thread.sleep(5000);

			// Get Product Name in Cart Section
			String productNameinAddCartSection = driver
					.findElement(By
							.xpath("//div[@id='merchandiseCart']//div[@class='k-grid-content ps-container']/table/tbody/tr//td[1]/div[contains(text(),'Lock')]"))
					.getText();

			if (productNameinDeccriptionSection.equals(productNameinAddCartSection)) {

				Thread.sleep(2000);
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Item Name displayed successfully in Add Cart Section ");
				logger.log(LogStatus.PASS, "Item Name displayed successfully in Add Cart Section ", image);
			}

			else {
				Thread.sleep(2000);
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Item Name not displayed successfully in Add Cart Section   ");
				logger.log(LogStatus.FAIL, "Item Name not displayed successfully in Add Cart Section  ", image);
			}

			Thread.sleep(2000);
			if (driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td[2]/div[1]/div"))
					.isDisplayed()) {
				logger.log(LogStatus.PASS, "Current Quantity in cart Displayed successfully");

			}

			else {

				logger.log(LogStatus.FAIL, "Current Quantity in cart  not Displayed  ");
			}

			Thread.sleep(2000);

			if (driver
					.findElement(By
							.xpath("//div[@id='Merchandise']//div[@class='cart-bottom-row']//div[text()='Subtotal:']"))
					.isDisplayed()) {
				logger.log(LogStatus.PASS, "Subtotal  Displayed successfully in Add Cart Section ");

			}

			else {

				logger.log(LogStatus.FAIL, "Subtotal  not Displayed in Add Cart Section  ");
			}

			// Click on CheckOut Link
			
			js.executeScript("scroll(0, 2000);");
			Thread.sleep(3000);

			buy_Merch.click_checkout_btn();
			logger.log(LogStatus.INFO, "Clicked on checkout button successfully");
			Thread.sleep(5000);

			logger.log(LogStatus.INFO, "Checkout screen displayed");
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			// Get Total Amount Before Tax Exempt
			String amountWithTax = driver
					.findElement(By
							.xpath("//div[@id='merchandise-payment-table']//section[@class='row merchandise-total']/div[contains(text(),'Total:')]//following-sibling::div[@class='second col']/span"))
					.getText();
			Thread.sleep(5000);



			// Click on "Add Tax Exempt" Link
			CheckoutPage check = new CheckoutPage(driver);

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			//============================================================
			//Fetching salestax from DB and comparing from UI

			String ActualSubtotal=check.get_subtotalAmount().replace("$", "");
			String salestax_Before=check.get_salestax();
			String taxquery="select s.taxpercent, s.* from sitetax s "+
					"	join type t on s.taxtypeid=t.typeid  "+
					" where s.siteid="+tabledata.get("SiteId")+ "and t.name='Merchandise' and s.expirationdate is null ";
			ArrayList<String> salestaxpercent=DataBase_JDBC.executeSQLQuery_List(taxquery);
			String taxpercent=salestaxpercent.get(0);
			System.out.println("The salestax percentage is : "+taxpercent );
			logger.log(LogStatus.INFO,"The salestax percentage is : "+taxpercent );

			double salestaxAmount=((Double.parseDouble(ActualSubtotal))*(Double.parseDouble(taxpercent)))/100;

			//double salestax_DB=Math.ceil(salestaxAmount);
			//double salestax_DB = Math.ceil(salestaxAmount*100)/100;
			double salestax_DB = Math.floor(salestaxAmount*100)/100;
			System.out.println("The sales tax amount is: "+salestax_DB);
			String saletax_calc="$"+salestax_DB;

			if(saletax_calc.equals(salestax_Before)){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "The salestax  in UI : "+salestax_Before+" and Calculated : "+saletax_calc+" are same");
				logger.log(LogStatus.INFO, "The salestax  in UI : "+salestax_Before+" and Calculated : "+saletax_calc+" are same",image);
			}
			else{
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				resultFlag="fail";
				logger.log(LogStatus.FAIL, "The salestax  in UI : "+salestax_Before+" and Caluclated : "+saletax_calc+" are not same");
				logger.log(LogStatus.INFO, "The salestax  in UI : "+salestax_Before+" and Caluclated : "+saletax_calc+" are not same",image);
			}

			String amountbefore_taxexempt=(Double.parseDouble(ActualSubtotal))+salestax_Before;
			logger.log(LogStatus.INFO, "The total Amount before tax exempt is: "+amountbefore_taxexempt);

			//==========================================================
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			check.click_addTaxExempt_lnk();
			logger.log(LogStatus.INFO, "Clicked on Add Tax Exempt link successfully");
			Thread.sleep(5000);

			// driver.findElement(By
			// .xpath("//span[contains(@class,'stateDrop
			// tax-exemption__state')][span[contains(@class,'k-dropdown-wrap')]/span[@class='k-input']]")).click();
			logger.log(LogStatus.INFO, "Add Tax Exempt Section shown in the Merchandise screen");
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);
			driver.findElement(By.xpath("//span[text()='Select State']")).click();
			Thread.sleep(2000);

			List<WebElement> stateOptions = driver
					.findElements(By.xpath("//ul[@id='TaxExemptModel_StateCode_listbox']/li"));
			for (WebElement allOptions : stateOptions) {
				if (allOptions.getText().equals("AA")) {
					allOptions.click();
					break;
				}
			}

			// Click on No expiration date
			check.click_NoExpirationDate_Chk();
			Thread.sleep(3000);
			check.enter_TaxExemptNumber("1234");

			Thread.sleep(3000);
			check.click_upload_btn();
			Thread.sleep(3000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);

			String pathfile = System.getProperty("user.dir");
			System.out.println("Path is :-- " + pathfile);
			uploadFile(pathfile + File.separator + "Test.png");

			Thread.sleep(3000);

			logger.log(LogStatus.INFO, "Tax Exempt applied successfully ");

			String salestax_After=check.get_salestax();

			if(salestax_After.equals(salestax_Before)){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "The salestax  before Tax exempt : "+salestax_Before+" and after Tax exempt : "+salestax_After+" are same");
				logger.log(LogStatus.INFO, "The salestax before Tax exempt : "+salestax_Before+" and after Tax exempt  : "+salestax_After+" are same",image);
			}
			else{
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				resultFlag="fail";
				logger.log(LogStatus.PASS, "The salestax  before Tax exempt : "+salestax_Before+" and after Tax exempt  : "+salestax_After+" are not same");
				logger.log(LogStatus.INFO, "The salestax  before Tax exempt : "+salestax_Before+" and after Tax exempt  : "+salestax_After+" are not same",image);
			}

			String amountafter_taxexempt=check.get_totalAmount();
			logger.log(LogStatus.INFO, "The total Amount After tax exempt is: "+amountafter_taxexempt);


			// logger.log(LogStatus.PASS, "Clicked on Add Tax Exempt link
			// successfully");

			// Click on Confirm with customer
			driver.findElement(By.xpath("//button[text()='Confirm With Customer']")).click();
			Thread.sleep(6000);

			driver.switchTo().window(tabs.get(1));
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			logger.log(LogStatus.INFO, "On clicking Confirm with Customer, User redirected to PS: CFS for customer confirmation  ", image);

			Thread.sleep(10000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);
			js.executeScript("window.scrollBy(0,250)", "");

			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.PASS, "Clicked on Accept btn in customer facing screen");
			Thread.sleep(4000);

			// switched back to Main screen
			driver.switchTo().window(tabs.get(0));
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(4000);

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			/*// Get Total Amount After Tax Exempt
			String amountafterTaxExempt = driver
					.findElement(By
							.xpath("//div[@id='merchandise-payment-table']//section[@class='row merchandise-total']/div[contains(text(),'Total:')]//following-sibling::div[@class='second col']/span"))
					.getText();
			Thread.sleep(2000);
			if (!amountWithTax.equals(amountafterTaxExempt)) {
				Thread.sleep(2000);
				 scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				 image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Tax Exempt applied successfully :" + "With Tax amont is : " + amountWithTax
						+ "Amount with Tax Exempt : " + amountafterTaxExempt);
				logger.log(LogStatus.PASS, "Tax Exempt applied successfully ", image);
			}

			else {
				Thread.sleep(2000);
				 scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				 image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Tax Exempt not applied    ");
				logger.log(LogStatus.FAIL, "Tax Exempt not applied   ", image);
			}*/

			JavascriptExecutor jse = (JavascriptExecutor) driver;

			check.click_payDropdown();
			Thread.sleep(2000);

			logger.log(LogStatus.INFO, "Payment methods drop down displayed on Checkout screen");
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);
			check.sel_Cash_fromDropdown();
			logger.log(LogStatus.INFO, "Selected cash option from dropdown");
			Thread.sleep(2000);
			jse.executeScript("scroll(0, 2000);");

			/*
			 * String total = driver.findElement(By.xpath(
			 * "//span[@class='js-merchandise-total']")).getText().replace("$",
			 * ""); double amount = Double.parseDouble(total) + 10; String
			 * Amount = "$" + amount;
			 */

			driver.findElement(By.id("cashAmount")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
			Thread.sleep(2000);

			driver.findElement(By.id("cashAmount")).sendKeys(check.get_totalAmount());

			Thread.sleep(2000);

			check.click_Apply();
			logger.log(LogStatus.PASS, "Clicked on Apply btn successfully");
			Thread.sleep(2000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			check.click_Submit();
			logger.log(LogStatus.PASS, "Clicked on submit  btn successfully");

			// Transaction Window
			driver.findElement(By.id("employeeNumber")).sendKeys(tabledata.get("UserName"));
			Thread.sleep(2000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			driver.findElement(By.partialLinkText("Ok")).click();
			logger.log(LogStatus.PASS, "Form saved and transaction  completed.");

			Thread.sleep(15000);

			//Commented from here

			// selecting Buy Merchandise from Quick linkd Dropdown

			try{
				driver.findElement(By.partialLinkText("OK")).click();
				Thread.sleep(2000);
			}catch(Exception ex){}

			Thread.sleep(15000);

			/*js.executeScript("window.scrollBy(250,0)", "");
			Thread.sleep(5000);
			js.executeScript("window.scrollBy(0,250)", "");
			driver.findElement(
					By.xpath("//div[@class='actions clearfix-container']//span[contains(text(),'Quick Links')]"))
					.click();

			// Identify the WebElement which will appear after scrolling down
			WebElement element1 = driver
					.findElement(By.xpath("//ul[@class='k-list k-reset ps-container ps-active-y']/li[3]"));
			// now execute query which actually will scroll until that element
			// is not appeared on page.
			je.executeScript("arguments[0].scrollIntoView(true);", element1);
			element1.click();
			logger.log(LogStatus.INFO, "Selected Buy Merchandise option from Quicklinks dropdown successfully");
			Reporter.log("Selected Buy Merchandise option successfully", true);
			Thread.sleep(5000);


			  if (buy_Merch.verify_itemName() && buy_Merch.verify_itemDesc() &&
			  buy_Merch.verify_SKU() && buy_Merch.verify_QuantityOnHand()) {
			  String itemname = buy_Merch.gettxt_itemName(); String itemDesc =
			  buy_Merch.gettxt_itemDesc(); String sku = buy_Merch.gettxt_SKU();
			  String quantityonHand = buy_Merch.gettxt_QuantityOnHand(); //
			  String priceperunit=buy_Merch.gettxt_pricePerUnit();

			  logger.log(LogStatus.INFO, "The item details are:" + itemname +
			  "," + itemDesc + "," + sku + "," + quantityonHand);

			  } else { logger.log(LogStatus.FAIL,
			  "The item details are not available"); }


			// enter quantity of items

			// buy_Merch.enter_Quantity_item1(tabledata.get("Quantity_item1"));

			driver.findElement(By.xpath("//div[contains(text(),'Lock')]/following-sibling::div//input")).sendKeys("2");

			buy_Merch.click_AddItemsToCart_btn();
			logger.log(LogStatus.INFO, "Entered required quantity of items and clicked on Add items to cart button");
			Thread.sleep(5000);

			// Verify remove item using the Remove link
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td[4]/div/a")).click();
			Thread.sleep(2000);

			if (driver
					.findElement(
							By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td/b[text()='NO ITEMS TO DISPLAY']"))
					.isDisplayed()) {

				Thread.sleep(2000);
				 scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				 image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Ability to remove item using the Remove link is working as expected ");
				logger.log(LogStatus.PASS, "Ability to remove item using the Remove link  is working as expected ",
						image);
			}

			else {
				Thread.sleep(2000);
				Reporter.log(scpath, true);
				 image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Ability to remove item using the Remove link is not working as expected  ");
				logger.log(LogStatus.FAIL, "Ability to remove item using the Remove link is not working as expected  ",
						image);
			}

			// Ability to Remove All using Remove all button
			// buy_Merch.enter_Quantity_item1("2");
			driver.findElement(By.xpath("//div[contains(text(),'Lock')]/following-sibling::div//input")).sendKeys("2");

			Thread.sleep(2000);

			buy_Merch.click_AddItemsToCart_btn();
			logger.log(LogStatus.INFO, "Entered required quantity of items and clicked on Add items to cart button");
			Thread.sleep(5000);

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			driver.findElement(By.xpath("//a[@id='clearCart']")).click();
			Thread.sleep(2000);

			if (driver
					.findElement(
							By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td/b[text()='NO ITEMS TO DISPLAY']"))
					.isDisplayed()) {

				Thread.sleep(2000);
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				 image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Ability to remove item using the Remove All link is working as expected ");
				logger.log(LogStatus.PASS, "Ability to remove item using the Remove All link  is working as expected ",
						image);
			}

			else {
				Thread.sleep(2000);
				 scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				 image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL,
						"Ability to remove item using the Remove All link is not working as expected  ");
				logger.log(LogStatus.FAIL,
						"Ability to remove item using the Remove All link is not working as expected  ", image);
			}

			// Verify When the user clicks the checkout button option, then the
			// checkout screen must be displayed.

			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[contains(text(),'Lock')]/following-sibling::div//input")).sendKeys("1");

			buy_Merch.click_AddItemsToCart_btn();
			logger.log(LogStatus.INFO, "Entered required quantity of items and clicked on Add items to cart button");
			Thread.sleep(8000);

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			// Click on Checkout Button
			buy_Merch.click_checkout_btn();
			logger.log(LogStatus.INFO, "Clicked on checkout button successfully");
			Thread.sleep(5000);

			// verify user lands to checkout page

			if (check.verify_pagetitle()) {
				String scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				String image1 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Checkout page is displayed");
				logger.log(LogStatus.INFO, "Checkout page is displayed ", image1);
			} else {
				String scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				String image1 = logger.addScreenCapture(scpath1);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				logger.log(LogStatus.FAIL, "Checkout page is not displayed ");
				logger.log(LogStatus.INFO, "Checkout page is not displayed", image1);
			}

			// Verify Buy Merchandise Screens must display
			if (driver.findElement(By.xpath("//th[text()='Product']")).isDisplayed()) {
				logger.log(LogStatus.PASS, "Buy Merchandise Screens displayed successfully ");
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
			}

			else {
				logger.log(LogStatus.FAIL, "Buy Merchandise Screens not displayed  ");
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
			}

			if (driver.findElements(By.xpath("//div[contains(text(),' Subtotal:')]/following-sibling::div/span"))
					.size() > 0) {
				logger.log(LogStatus.PASS, " Subtoatl is calculated  successfully ");
			} else {
				logger.log(LogStatus.FAIL, "Subtoatl is not calculated   ");
			}

			if (driver
					.findElement(By
							.xpath("//div[@id='merchandise-payment-table']/div[@class='row-group merchandise-group']/section[@class='unit-header row']/div[1]"))
					.isDisplayed()) {
				logger.log(LogStatus.PASS, " Promotions section displayed successfully ");
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
			}

			else {
				logger.log(LogStatus.FAIL, "Promotions section not  displayed ");
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
			}

			// Click on Confirm with customer button
			driver.findElement(By.xpath("//button[text()='Confirm With Customer']")).click();
			Thread.sleep(6000);

			driver.switchTo().window(tabs.get(1));

			logger.log(LogStatus.PASS, "Switching  to customer  screen");
			Reporter.log("Switching  to customer screen", true);
			Thread.sleep(10000);

			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.PASS, "Clicked on Accept btn in customer facing screen");
			Thread.sleep(4000);

			// switched back to Main screen
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(4000);
			logger.log(LogStatus.PASS, "Switching back to PM screen");
			Reporter.log("Switching back to PM screen", true);

			// Validate the submit button and Edit Cart 
			if (driver.findElement(By.xpath("//button[text()='Submit']")).isDisplayed()) {
				logger.log(LogStatus.PASS, " Submit button displayed successfully ");
			}

			else {
				logger.log(LogStatus.FAIL, "Submit button not displayed");
			}

			// Click on Edit Cart
			driver.findElement(By.xpath("//a[text()='Edit Cart']")).click();
			Thread.sleep(4000);

			if (buy_Merch.verify_pagetitle()) {
				logger.log(LogStatus.PASS, "Buy merchandise page is displayed ");
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
			} else {
				logger.log(LogStatus.FAIL, "Buy merchandise page is not displayed  ");
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
			}

			driver.findElement(By.xpath("//span[text()='Boxes ']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath(" //div[text()='Box File Letter']/following-sibling::div//input"))
					.sendKeys("2");

			buy_Merch.click_AddItemsToCart_btn();
			logger.log(LogStatus.INFO, "Entered required quantity of items and clicked on Add items to cart button");
			Thread.sleep(5000);

			buy_Merch.click_checkout_btn();
			logger.log(LogStatus.INFO, "Clicked on checkout button successfully");
			Thread.sleep(8000);

			driver.findElement(By.xpath("//button[text()='Confirm With Customer']")).click();
			Thread.sleep(6000);

			driver.switchTo().window(tabs.get(1));

			logger.log(LogStatus.PASS, "Switching  to customer  screen");
			Reporter.log("Switching  to customer screen", true);
			Thread.sleep(10000);

			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.PASS, "Clicked on Accept btn in customer facing screen");
			Thread.sleep(4000);

			// switched back to Main screen
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(4000);
			logger.log(LogStatus.PASS, "Switching back to PM screen");
			Reporter.log("Switching back to PM screen", true);

			// Make payment

			check.click_payDropdown();
			Thread.sleep(2000);
			check.sel_Cash_fromDropdown();
			logger.log(LogStatus.INFO, "Selected cash option from dropdown");
			Thread.sleep(2000);

			String totalAmount = driver.findElement(By.xpath("//span[@class='js-merchandise-total']")).getText();

			driver.findElement(By.id("cashAmount")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
			Thread.sleep(2000);

			driver.findElement(By.id("cashAmount")).sendKeys(totalAmount);
			// check.txt_Amount();
			Thread.sleep(2000);

			jse.executeScript("scroll(0, 2000);");

			check.click_Apply();
			logger.log(LogStatus.INFO, "Clicked on Apply btn");

			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			check.click_Submit();
			logger.log(LogStatus.INFO, "Clicked on Submit btn successfully");

			// Transaction Window
			driver.findElement(By.id("employeeNumber")).sendKeys(tabledata.get("UserName"));

			Thread.sleep(8000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			driver.findElement(By.partialLinkText("Ok")).click();
			logger.log(LogStatus.PASS, "Form saved and transaction  completed.");
			//commented till here
			 */
			Thread.sleep(8000);

			logger.log(LogStatus.INFO, "Navigated to Customer Dashboard screen");
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image", image);

			/*		try{
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(2000);
			}catch(Exception ex){}

			try{
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(2000);
			}catch(Exception ex){}*/

			cust.click_AccountActivities();

			Thread.sleep(8000);
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			String CurrentDate = sdf.format(date);                
			List<WebElement> resultRows = driver.findElements(By.xpath("//div[@id='activities-grid']//div[@class='k-grid-content ps-container ps-active-y']/table/tbody/tr"));

			int rowSize = resultRows.size();
			for(int i1=1; i1<=rowSize; i1++){

				String Date = resultRows.get(i1-1).findElement(By.xpath("./td[2]")).getText();
				System.out.println("date"+Date);



				if(Date.equals(CurrentDate)){
					logger.log(LogStatus.INFO, "date verified :"+Date);
					String Description = resultRows.get(i1-1).findElement(By.xpath("./td[7]")).getText();
					if(Description.contains("Payment ")) {
						logger.log(LogStatus.INFO, "Account Activity created successfully :"+Description);
						break;}

				}
				else{
					if(resultFlag.equals("pass"))
						resultFlag="fail";    
					scpath=Generic_Class.takeScreenShotPath();
					image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Account Activity not created successfully");
					logger.log(LogStatus.INFO, "Account Activity not created successfully",image);
				}
			}











		} catch (Exception ex) {
			ex.printStackTrace();
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			resultFlag = "fail";
			Reporter.log("Exception ex: " + ex, true);
			logger.log(LogStatus.FAIL, "Test Script fail due to exception");
			logger.log(LogStatus.INFO, "Exception",image1);

		}

	}

	public void uploadFile(String fileLocation) {
		try {
			/*
			 * WebElement element = driver .findElement((By.xpath(
			 * "//*[@id='overlayContainer']/div/div[2]/div[1]/div/div/span")));
			 * element.click();
			 */
			Thread.sleep(3000);
			// Setting clipboard with file location
			setClipboardData(fileLocation);
			// native key strokes for CTRL, V and ENTER keys
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);


		} catch (Exception exp) {
			String scpath1=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath1,true);
			String image1=logger.addScreenCapture(scpath1);
			if(resultFlag.equals("pass"))
				resultFlag="fail";
			logger.log(LogStatus.FAIL, "Exception "+exp);
			logger.log(LogStatus.INFO, "Exception",image1);
			//exp.printStackTrace();
		}
	}

	public void setClipboardData(String string) {
		// StringSelection is a class that can be used for copy and paste
		// operations.
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	@AfterMethod
	public void afterMethod() {

		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "Merchandise",
					"ExistingCust_buyingmerchandise_usingBuymerchandiselink_DMdashboard_Taxexempt", "Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "Merchandise",
					"ExistingCust_buyingmerchandise_usingBuymerchandiselink_DMdashboard_Taxexempt", "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "Merchandise",
					"ExistingCust_buyingmerchandise_usingBuymerchandiselink_DMdashboard_Taxexempt", "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);

	}

}
