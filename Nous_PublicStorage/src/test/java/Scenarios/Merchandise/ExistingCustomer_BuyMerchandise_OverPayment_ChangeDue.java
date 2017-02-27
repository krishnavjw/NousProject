package Scenarios.Merchandise;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Buy_MerchandisePage;
import Pages.CustDashboardPages.CheckoutPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class ExistingCustomer_BuyMerchandise_OverPayment_ChangeDue extends Browser_Factory {
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
				"ExistingCustomer_BuyMerchandise_OverPayment_CheckHowpaymentIsAllocated");
	}

	@Test(dataProvider = "getCustSearchData")
	public void payment(Hashtable<String, String> tabledata) throws InterruptedException {
		try {
			logger = extent.startTest("ExistingCustomer_BuyMerchandise_OverPayment_CheckHowpaymentIsAllocated",
					"ExistingCustomer_BuyMerchandise_OverPayment_CheckHowpaymentIsAllocated");
			Reporter.log("Test case started: " + testcaseName, true);

			if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("Merchandise").equals("Y"))) {
				resultFlag = "skip";
				throw new SkipException("Skipping the test");
			}

			// Login To the Application
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as PM");

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
			Thread.sleep(2000);
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
			//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);


			// ===============================================================================

			// Verify that the user lands on the "PM Dashboard" screen after
			// login and walkin cust title

			PM_Homepage pmhomepage = new PM_Homepage(driver);
			Thread.sleep(3000);


			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "PM Dashboard",image);

			Thread.sleep(10000);
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "clicked on Advance search link successfully");

			Advance_Search advsearch = new Advance_Search(driver);


			// Fetching Acc num through Quer who is having Balance due
			String accNum = DataBase_JDBC.executeSQLQuery(query);
			advsearch.enterAccNum(accNum);
			advsearch.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "Entered account number and clicked on search button successfully"+accNum);
			Reporter.log("click on search button", true);
			Thread.sleep(15000);

			// js.executeScript("window.scrollTo(0,
			// document.body.scrollHeight)");

			// screenshot
			String scpath1 = Generic_Class.takeScreenShotPath();
			String image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "customer dashboard is displayed", image1);

			Cust_AccDetailsPage cust = new Cust_AccDetailsPage(driver);
			Thread.sleep(2000);

			// selecting Buy Merchandise from Quick linkd Dropdown

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(250,0)", "");
			Thread.sleep(5000);
			js.executeScript("window.scrollBy(0,250)", "");
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
				scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Buy merchandise page is displayed ");
				logger.log(LogStatus.INFO, "Buy merchandise page is displayed ",image1);
			}
			else{
				scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image1=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Buy merchandise page is not displayed  ");
				logger.log(LogStatus.INFO, "Buy merchandise page is not displayed ",image1);
			}


			// select any category and verify all the item details
			buy_Merch.click_All_radiobtn();
			logger.log(LogStatus.INFO, "All Radio button is selected");
			Thread.sleep(1000);

			if (buy_Merch.verify_itemName() && buy_Merch.verify_itemDesc() && buy_Merch.verify_SKU()
					&& buy_Merch.verify_QuantityOnHand()) {
				String itemname = buy_Merch.gettxt_itemName();
				String itemDesc = buy_Merch.gettxt_itemDesc();
				String sku = buy_Merch.gettxt_SKU();
				String quantityonHand = buy_Merch.gettxt_QuantityOnHand();
				// String priceperunit=buy_Merch.gettxt_pricePerUnit();

				logger.log(LogStatus.INFO,
						"The item details are:" + itemname + "," + itemDesc + "," + sku + "," + quantityonHand);

			} else {
				logger.log(LogStatus.INFO, "The item details are not available");
			}

			// enter quantity of items
			buy_Merch.enter_Quantity_item1(tabledata.get("Quantity_item1"));
			buy_Merch.enter_Quantity_item2(tabledata.get("Quantity_item2"));

			buy_Merch.click_AddItemsToCart_btn();
			logger.log(LogStatus.INFO, "Entered required quantity of items and clicked on Add items to cart button");
			Thread.sleep(5000);


			// verify script and customer screen tabs are in the buy Merchandise
			// screen
			if (buy_Merch.verify_Script() && buy_Merch.verify_customerscreen()) {

				String scpath2=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath2,true);
				String image2=logger.addScreenCapture(scpath2);
				logger.log(LogStatus.PASS, "The Script and customer screen tabs are displayed in the merchandise screen");
				logger.log(LogStatus.INFO, "The Script and customer screen tabs are displayed in the merchandise screen",image2);
			}else{
				String scpath2=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath2,true);
				String image2=logger.addScreenCapture(scpath2);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "The Script and customer screen tabs are  not displayed in the merchandise screen");
				logger.log(LogStatus.INFO, "The Script and customer screen tabs are  not displayed in the merchandise screen",image2);
			}

			buy_Merch.click_checkout_btn();
			logger.log(LogStatus.INFO, "Clicked on checkout button successfully");
			Thread.sleep(5000);

			// verify pm lands to checkout page
			CheckoutPage check = new CheckoutPage(driver);
			if (check.verify_pagetitle()) {
				scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				image1 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Checkout page is displayed");
				logger.log(LogStatus.INFO, "Checkout page is displayed ", image1);
			} else {
				scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				image1 = logger.addScreenCapture(scpath1);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				logger.log(LogStatus.FAIL, "Checkout page is not displayed ");
				logger.log(LogStatus.INFO, "Checkout page is not displayed", image1);
			}

			int i = check.get_Countofproducts();
			logger.log(LogStatus.INFO, "The no.of items in checkout is:" + i);

			// verify the total Amount of each product
			double product1_price = Double.parseDouble(check.get_Product1_Price().replace("$", ""));
			int product1_Quantity = Integer.parseInt(check.get_Product1_Quantity());
			double product2_price = Double.parseDouble(check.get_Product2_Price().replace("$", ""));
			int product2_Quantity = Integer.parseInt(check.get_Product2_Quantity());

			String Actualproduct1_total = check.get_Product1_Amt();
			String Actualproduct2_total = check.get_Product2_Amt();

			String expProduct1_total = "$" + (product1_price * product1_Quantity);
			String expProduct2_total = "$" + (product2_price * product2_Quantity);

			if ((Actualproduct1_total.equals(expProduct1_total)) && (Actualproduct2_total.equals(expProduct2_total))) {
				logger.log(LogStatus.PASS, "Actual item1 :"+Actualproduct1_total+ " and Expected item1 :"+expProduct1_total+ " values are matching");
				logger.log(LogStatus.PASS, "Actual item2 :" +Actualproduct2_total+" and Expected item2 :"+Actualproduct2_total+ "values are matching");
			}
			else{
				logger.log(LogStatus.FAIL, "Actual item1 :"+Actualproduct1_total+ " and Expected item1 :" +expProduct1_total+" values are not matching");
				logger.log(LogStatus.PASS, "Actual item2 :" +Actualproduct2_total+" and Expected item2 :" +Actualproduct2_total+" values are matching");
			}

			// verify promotion and coupon section should be displayed
			if (check.verify_promotion_Section()) {
				logger.log(LogStatus.PASS, "promotion and coupon section is displayed");
			} else {
				logger.log(LogStatus.FAIL, "promotion and coupon section is not displayed");
			}

			// click on confirm with customer
			check.click_ConfWithCust();
			logger.log(LogStatus.INFO, "Clicked on confirm with customer btn");
			Thread.sleep(6000);

			// switching to customer screen
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(3000);
			
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "image",image);

			logger.log(LogStatus.PASS, "Switching  to customer  screen");
			Reporter.log("Switching  to customer screen", true);
			Thread.sleep(10000);
			
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
		
			
			driver.findElement(By.id("confirmButton")).click();
			logger.log(LogStatus.PASS, "Clicked on Accept btn in customer facing screen");
			Thread.sleep(3000);

			// switched back to Main screen
		
			driver.switchTo().window(tabs.get(0));
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(3000);
			
			
			scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "PM Dashboard",image);
			logger.log(LogStatus.PASS, "Switching back to PM screen");
			Reporter.log("Switching back to PM screen", true);

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,1500)", "");

			// verify editcart and submit buttons are displayed
			if ((check.verify_Editcart()) && (check.verify_Submit())) {
				logger.log(LogStatus.PASS, "Editcart and submit buttons is displayed");
			} else {
				logger.log(LogStatus.FAIL, "Editcart and submit buttons is not displayed");
			}

			/*
			 * check.click_payDropdown(); Thread.sleep(2000);
			 * check.sel_MO_fromDropdown(); logger.log(LogStatus.INFO,
			 * "Selected MO option from dropdown"); Thread.sleep(2000);
			 * 
			 * check.clickmanualentry(); logger.log(LogStatus.INFO,
			 * "Clicked on manual entry button"); Thread.sleep(3000);
			 * 
			 * check.Enter_routingNumber(tabledata.get("CheckRoutingNum"));
			 * check.Enter_CheckAccountNumber(tabledata.get("CheckAccNum"));
			 * check.Enter_checkNumber(tabledata.get("CheckNum"));
			 * Thread.sleep(2000);
			 * 
			 * check.entermoreAmount(); logger.log(LogStatus.INFO,
			 * "Entered  Amount more than total Amount"); Thread.sleep(3000);
			 * 
			 * jse.executeScript("scroll(0, 2000);");
			 * 
			 * check.click_Apply(); logger.log(LogStatus.INFO,
			 * "Clicked on Apply btn");
			 * 
			 * check.click_No_RadioBtn(); Thread.sleep(2000);
			 * 
			 * //Verify Overlap message
			 * if(check.getOverPaymentErrorMessage().contains(
			 * "Cannot Accept Overpayment")){
			 * 
			 * String scpath1=Generic_Class.takeScreenShotPath();
			 * Reporter.log(scpath1,true); String
			 * image1=logger.addScreenCapture(scpath1);
			 * logger.log(LogStatus.PASS,
			 * "Cannot Accept Overpayment error message is displayed successfully  "
			 * ); logger.log(LogStatus.INFO,
			 * "Cannot Accept Overpayment error message is displayed successfully  "
			 * ,image1); }else{ String
			 * scpath1=Generic_Class.takeScreenShotPath();
			 * Reporter.log(scpath1,true); String
			 * image1=logger.addScreenCapture(scpath1);
			 * if(resultFlag.equals("pass")) resultFlag="fail";
			 * logger.log(LogStatus.FAIL,
			 * "Cannot Accept Overpayment error message is not  displayed  ");
			 * logger.log(LogStatus.INFO,
			 * "Cannot Accept Overpayment error message is not  displayed  "
			 * ,image1); }
			 * 
			 * 
			 * check.click_RemoveBtn(); Thread.sleep(2000);
			 * logger.log(LogStatus.INFO, "Clicked on Remove btn");
			 */

			//verify the payment method dropdown
			check.verify_payDropdown();
			logger.log(LogStatus.INFO, "Payment dropdown is displayed in the checkout page");

			check.click_payDropdown();
			Thread.sleep(2000);
			check.sel_Cash_fromDropdown();
			logger.log(LogStatus.INFO, "Selected cash option from dropdown");
			Thread.sleep(2000);

			String total = driver.findElement(By.xpath("//span[@class='js-merchandise-total']")).getText().replace("$",
					"");
			double amount = Double.parseDouble(total) + 10;
			String Amount = "$" + amount;

			driver.findElement(By.id("cashAmount")).clear();
			driver.findElement(By.id("cashAmount")).sendKeys(Amount);
			// check.txt_Amount();
			Thread.sleep(2000);

			jse.executeScript("scroll(0, 2000);");

			check.click_Apply();
			logger.log(LogStatus.INFO, "Clicked on Apply btn");

			if (check.verify_EnoughCashOnhand_Msg()) {
				scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				image1 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Is enough cash on hand message displayed succesfully");
				logger.log(LogStatus.INFO, "Is enough cash on hand message displayed succesfully", image1);
			} else {
				scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				image1 = logger.addScreenCapture(scpath1);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				logger.log(LogStatus.FAIL, "Is enough cash on hand msg  is not  displayed  ");
				logger.log(LogStatus.INFO, "Is enough cash on hand msg is not  displayed  ", image1);
			}
			Thread.sleep(2000);

			check.click_Yes_RadioBtn();
			logger.log(LogStatus.INFO, "Clicked on Yes radio button");

			String changedue = check.get_changedue();
			System.out.println(changedue);

			check.click_CollectSignature();
			logger.log(LogStatus.INFO, "Clicked on Collect signature button");
			Thread.sleep(3000);

			// switching to customer screen
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.PASS, "Switching  to customer  screen");
			Reporter.log("Switching  to customer screen", true);
			Thread.sleep(10000);

			String changeDueInCFS = driver
					.findElement(By
							.xpath("//div[contains(text(),'Change Due')]/following-sibling::div[@class='data-row-last__second-col--primary']"))
					.getText();
			System.out.println(changeDueInCFS);
			Thread.sleep(2000);

			if (changedue.equals(changeDueInCFS)) {
				logger.log(LogStatus.PASS,
						"Actual change due:" + changedue + " and CFS change due:" + changeDueInCFS + " are same");

			} else {
				logger.log(LogStatus.PASS,
						"Actual change due:" + changedue + " and CFS change due:" + changeDueInCFS + " are not same");
			}
			WebElement signature1 = driver
					.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));

			Actions actionBuilder = new Actions(driver);
			Action drawAction1 = actionBuilder.moveToElement(signature1, 660, 96).click().clickAndHold(signature1)
					.moveByOffset(120, 120).moveByOffset(60, 70).moveByOffset(-140, -140).release(signature1).build();
			drawAction1.perform();
			Thread.sleep(6000);
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);


			jse.executeScript("window.scrollBy(0,2000)", "");
			driver.findElement(By.xpath("//button[text()='Accept']")).click();
			logger.log(LogStatus.PASS, "Clicked on Accept button in customer facing screen");
			Thread.sleep(3000);

			// switched back to Main screen
			
			driver.switchTo().window(tabs.get(0));
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			
			Thread.sleep(3000);
			
			logger.log(LogStatus.PASS, "Switching back to PM screen");
			Reporter.log("Switching back to PM screen", true);
			
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);


			check.click_Approve();
			logger.log(LogStatus.INFO, "Clicked on approve btn successfully");

			jse.executeScript("window.scrollBy(0,2000)", "");
			Thread.sleep(3000);

			check.click_Submit();
			logger.log(LogStatus.INFO, "Clicked on Submit button successfully");

			// Verify Change due: "I have give cash to the cust" question be
			// displayed

			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "Transaction complete Modal window is displayed");
			logger.log(LogStatus.INFO, "Transaction complete Modal window is displayed", image1);


			if (!driver
					.findElement(By
							.xpath("//label//span[text()='I have given cash change to the customer']/preceding-sibling::span[@class='button']"))
					.isSelected()) {

				scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				image1 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS,
						"Change due: " + "I have given cash to the customer" + "question is not   displayed");
				logger.log(LogStatus.INFO,
						"Change due: " + "I have given cash to the customer" + "question is not  displayed", image1);
			} else {
				scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				image1 = logger.addScreenCapture(scpath1);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				logger.log(LogStatus.FAIL, "Change due: " + "I have give cash to the customer" + "question  displaying   ");
				logger.log(LogStatus.INFO, "Change due: " + "I have give cash to the customer" + "question displaying ",
						image1);
			}

			// Optional Note field should be present

			if (driver.findElement(By.xpath("//textarea[@id='notesText']")).isDisplayed()) {

				scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				image1 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, " Note field displayed succesfully");
				logger.log(LogStatus.INFO, "Note field displayed succesfully ", image1);
			} else {
				scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				image1 = logger.addScreenCapture(scpath1);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				logger.log(LogStatus.FAIL, "Note field not  displayed   ");

			}

			//Transaction Window 
			driver.findElement(By.id("employeeNumber")).sendKeys(tabledata.get("UserName"));

			driver.findElement(By.xpath("//label//span[text()='I have given cash change to the customer']/preceding-sibling::span[@class='button']")).click();
			logger.log(LogStatus.PASS, "Clicked on Cash Change to The Customer Checkbox successfully");
			Thread.sleep(8000);

			driver.findElement(By.partialLinkText("Ok")).click();
			logger.log(LogStatus.PASS, "Form saved and transaction  completed.");
			Thread.sleep(8000);
			
			try{
				driver.findElement(By.xpath("//a[contains(text(),'OK']")).click();
			}catch(Exception ex){
			
			}

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Navigated to customer Dashboard",image);

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
					scpath1=Generic_Class.takeScreenShotPath();
					image1=logger.addScreenCapture(scpath1);
					logger.log(LogStatus.FAIL, "Account Activity not created successfully");
					logger.log(LogStatus.INFO, "Account Activity not created successfully",image1);
				}
			}







		}
		catch(Exception ex){
			ex.printStackTrace();
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			resultFlag = "fail";
			Reporter.log("Exception ex: " + ex, true);
			logger.log(LogStatus.FAIL, "Test Script fail due to exception" +ex);
			logger.log(LogStatus.INFO, "Exception",image1);
		}





	}

	@AfterMethod
	public void afterMethod() {

		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "Merchandise",
					"ExistingCustomer_BuyMerchandise_OverPayment_CheckHowpaymentIsAllocated", "Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "Merchandise",
					"ExistingCustomer_BuyMerchandise_OverPayment_CheckHowpaymentIsAllocated", "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "Merchandise",
					"ExistingCustomer_BuyMerchandise_OverPayment_CheckHowpaymentIsAllocated", "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);

	}
}
