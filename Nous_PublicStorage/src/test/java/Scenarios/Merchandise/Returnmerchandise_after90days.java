package Scenarios.Merchandise;

import java.awt.Robot;
import java.awt.event.KeyEvent;
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
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.MerchandiseReturnPage;
import Pages.CustDashboardPages.Merchandise_TransactionHistory;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Returnmerchandise_after90days extends Browser_Factory {

	public ExtentTest logger;

	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";

	String query = "" + "Select top 1  A.AccountNumber  from CLTransactionMaster CLTM with (nolock) "
			+ "Join Account A with (nolock) on CLTM.AccountID = A.AccountID " + "where 1=1 "
			+ "And CLTM.CLTransactionTypeID = 239 "
			+ "And datediff(dd, CLTM.RecordDateTime, DATEADD(day, DATEDIFF(day, 0, GETDATE()), 0)) >91 "
			+ "order by CLTM.RecordDateTime  desc";

	@DataProvider
	public Object[][] getCustSearchData() {
		return Excel.getCellValue_inlist(path, "Merchandise", "Merchandise", "Returnmerchandise_after90days");
	}

	@Test(dataProvider = "getCustSearchData")
	public void payment(Hashtable<String, String> tabledata) throws InterruptedException {
		try {
			logger = extent.startTest("Returnmerchandise_after90days", "Returnmerchandise_after90days");
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
            Bifrostpop.clickContiDevice();
            Thread.sleep(2000);
          /*  String biforstNum=Bifrostpop.getBiforstNo();

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
            driver.get("http://wc2qa.ps.com/CustomerScreen/Mount");  

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
*/
			// ===============================================================================

			// Verify that the user lands on the "PM Dashboard" screen after
			// login and walkin cust title

			PM_Homepage pmhomepage = new PM_Homepage(driver);
			Thread.sleep(3000);
			logger.log(LogStatus.PASS, "PM Home page object created successfully");

			Thread.sleep(10000);
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(3000);
			logger.log(LogStatus.INFO, "clicked on Advance search link successfully");

			Advance_Search advsearch = new Advance_Search(driver);
			logger.log(LogStatus.PASS, "Adv search object created successfully");

			// Fetching Acc num through Quer who is having Balance due
		/*	String accNum = DataBase_JDBC.executeSQLQuery(query);
			advsearch.enterAccNum(accNum);*/
			
			advsearch.enterAccNum(tabledata.get("Account Number"));
			Thread.sleep(2000);
			advsearch.clickSearchAccbtn();

			logger.log(LogStatus.PASS, "Entered account number and clicked on search buttonn successfully : "+tabledata.get("Account Number"));
			Reporter.log("click on serach button", true);
			Thread.sleep(150000);
			

			//screenshot
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "customer dashboard is displayed",image);


			Cust_AccDetailsPage cust = new Cust_AccDetailsPage(driver);
			Thread.sleep(2000);
			
			String customername_dashboard=cust.get_customerName().trim();
			
			String accNumber_dashboard=cust.getcustCurrAccno().trim();

			/*// Click on Account activities tab
			cust.click_AccountActivities();
			logger.log(LogStatus.PASS, "Entered acc num and clicked on search btn successfully");

			driver.findElement(By.xpath("//span[text()='View All Activities']")).click();
			Thread.sleep(8000);
			logger.log(LogStatus.PASS, "Clicked on view All Activity tab successfully");

			JavascriptExecutor jse = (JavascriptExecutor) driver;
			WebElement merchandiseSale = driver
					.findElement(By.xpath("//tr[td[text()='Merchandise Sale ']]/td[@class='k-hierarchy-cell']"));
			jse.executeScript("arguments[0].scrollIntoView(true);", merchandiseSale);
			Thread.sleep(3000);
			jse.executeScript("arguments[0].click();", merchandiseSale);

			List<WebElement> AllMerchandiseLink = driver
					.findElements(By.xpath("//tr[td[text()='Merchandise Sale ']]/td[@class='k-hierarchy-cell']"));
			int size = AllMerchandiseLink.size();
			AllMerchandiseLink.get(0).click();

			jse.executeScript("window.scrollBy(2000,0)", "");
			Thread.sleep(6000);
			// Click on Return Merchandise
			driver.findElement(
					By.xpath("//table/tbody//tr//td[@class='k-detail-cell']//div[@class='return-merchandise']/a"))
					.click();
			Thread.sleep(8000);

			if (driver.findElement(By.xpath("//h3[text()='Merchandise Return']")).isDisplayed()) {

				String scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				String image1 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Merchandise Return screen landed successfully");
				logger.log(LogStatus.INFO, "Merchandise Return screen landed successfully ", image1);
			} else {
				String scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				String image1 = logger.addScreenCapture(scpath1);
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				logger.log(LogStatus.FAIL, "Merchandise Return screen not  landed ");
				logger.log(LogStatus.INFO, "Merchandise Return screen not  landed ", image1);
			}

			// Verify the page elements on the merchandise retun screen 
			if (driver.findElement(By.xpath("//span[text()='Date:']")).isDisplayed()) {

				logger.log(LogStatus.PASS, "Date Column displayed successfully");

			} else {

				logger.log(LogStatus.FAIL, "Date Column not displayed ");

			}

			if (driver.findElement(By.xpath("//span[text()='Time:']")).isDisplayed()) {

				logger.log(LogStatus.PASS, "Time Column displayed successfully");

			} else {

				logger.log(LogStatus.FAIL, "Time Column not displayed ");

			}

			if (driver.findElement(By.xpath("//span[text()='Receipt:']")).isDisplayed()) {

				logger.log(LogStatus.PASS, "Receipt Column displayed successfully");

			} else {

				logger.log(LogStatus.FAIL, "Receipt Column not displayed ");

			}

			if (driver.findElement(By.xpath("//span[text()='Payment Type:']")).isDisplayed()) {

				logger.log(LogStatus.PASS, "Payment Type Column displayed successfully");

			} else {

				logger.log(LogStatus.FAIL, "Payment Type Column not displayed ");

			}

			if (driver.findElement(By.xpath("//span[text()='Reason for Return:']")).isDisplayed()) {

				logger.log(LogStatus.PASS, "Reason for Return Column displayed successfully");

			} else {

				logger.log(LogStatus.FAIL, "Reason for Return Column not displayed ");

			}

			if (driver.findElement(By.xpath("//span[text()='SELECT']")).isDisplayed()) {

				logger.log(LogStatus.PASS, "Reason for Return: by default selected as SELECT");

			} else {

				logger.log(LogStatus.FAIL, "Reason for Return: by default not selected as SELECT ");

			}

			// Verify the presence of script module and Customer Screen module 
			jse.executeScript("window.scrollBy(-2000,0)", "");
			Thread.sleep(2000);

			if (driver.findElement(By.xpath("//span[contains(text(),'Script')]")).isDisplayed()) {

				logger.log(LogStatus.PASS, "Script window is displayed in Merchandise return screen   ");

			} else {

				logger.log(LogStatus.FAIL, "Script window is not  displayed in Merchandise return screen ");

			}

			if (driver.findElement(By.xpath("//span[contains(text(),'Customer Screen')]")).isDisplayed()) {

				logger.log(LogStatus.PASS, "Customer Screen window is displayed in Merchandise return screen   ");

			} else {

				logger.log(LogStatus.FAIL, "Customer Screen window is not  displayed in Merchandise return screen ");

			}

			// Verify the merchandise purchased list 
			jse.executeScript("window.scrollBy(-2000,0)", "");
			if (driver.findElement(By.xpath("//th[text()='Product']")).isDisplayed()) {
				if (driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]/div/div[1]")).isDisplayed()) {
					logger.log(LogStatus.PASS,
							"Product coulumn  displayed with  product name, SKU number  and Description ");
				}
			}

			else {

				logger.log(LogStatus.FAIL,
						"Product coulumn not  displayed with  product name, SKU number  and Description ");
			}

			if (driver.findElements(By.xpath("//table/tbody/tr[1]/td[4]")).size() > 0) {

				logger.log(LogStatus.PASS, "Price column  displayed the total price cust paid.  ");

			}

			else {

				logger.log(LogStatus.FAIL, "Price column not  displayed the total price cust paid. ");
			}

			if (driver.findElements(By.xpath("//table/tbody/tr[1]/td[5]/div")).size() > 0) {

				logger.log(LogStatus.PASS, "Quantity Column displayed with purchased quantity  ");

			}

			else {

				logger.log(LogStatus.FAIL, "Quantity Column not displayed with purchased quantity ");
			}

			// verify the page elements in the merchandise return screen
			MerchandiseReturnPage merchreturn = new MerchandiseReturnPage(driver);

			if (merchreturn.verify_pagetitle()) {
				logger.log(LogStatus.PASS, " merchandise return page is displayed ");
			} else {
				logger.log(LogStatus.FAIL, "merchandise return page is not displayed  ");
			}

			// verify script and customer screen tabs are available
			if (merchreturn.verify_Script() && merchreturn.verify_customerscreen()) {

				logger.log(LogStatus.PASS,
						"The Script and customer screen tabs are displayed in the merchandise screen");
			} else {
				logger.log(LogStatus.FAIL,
						"The Script and customer screen tabs are  not displayed in the merchandise screen");
			}

			// select reason for return
			merchreturn.click_dropdown();
			Thread.sleep(1000);
			merchreturn.click_option_Other();
			logger.log(LogStatus.INFO, "Selected reason");

			merchreturn.verify_Productname();
			merchreturn.verify_ImageCount();
			merchreturn.verify_Price();
			merchreturn.verify_quantity();
			merchreturn.verify_purchaseSubtotal();

			merchreturn.verify_returnQuantity();
			logger.log(LogStatus.PASS, "Return quantity column  displayed successfully ");

			merchreturn.verify_Adjust_addBtn();
			logger.log(LogStatus.PASS, "Adjustment increase button  displayed successfully ");

			merchreturn.verify_Adjust_subBtn();
			logger.log(LogStatus.PASS, "Adjustment Decrease button  displayed successfully ");

			// ========================================
			String price = driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr[1]/td[4]"))
					.getText().replace("$", "");
			double price1 = Double.parseDouble(price);
			String quantity = driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr[1]/td[5]"))
					.getText().replace("$", "");
			double quantity1 = Double.parseDouble(quantity);
			String Actpurchasesubtotal = driver
					.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr[1]/td[6]")).getText()
					.replace("$", "");
			// double
			// Actpurchasesubtotal1=Double.parseDouble(Actpurchasesubtotal);

			double ExppurchaseSubtotal1 = price1 * quantity1;

			String ExppurchaseSubtotal12 = "$" + Actpurchasesubtotal;

			String ExppurchaseSubtotal = "$" + ExppurchaseSubtotal1;
			if (ExppurchaseSubtotal12.equals(ExppurchaseSubtotal)) {
				logger.log(LogStatus.PASS, "Actual purchase subtotal:" + Actpurchasesubtotal
						+ "and Expected Subtotal are  :" + ExppurchaseSubtotal + " are same");
			} else {
				logger.log(LogStatus.FAIL, "Actual purchase subtotal:" + Actpurchasesubtotal
						+ "and Expected Subtotal are  :" + ExppurchaseSubtotal + " are not same");
			}

			
			// Verify Return quantity should NOT go beyond the purchased
			// quantity 
			driver.findElement(By.xpath("//table/tbody/tr[1]/td[7]/div[1]/a[2]/span")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//table/tbody/tr[1]/td[7]/div[1]/a[2]/span")).click();
			logger.log(LogStatus.PASS, "Clicked on increase adjustment button successfully");
			String returnQuantityItem = driver.findElement(By.xpath("//table/tbody/tr[1]/td[7]/div[1]/div")).getText();

			if (quantity.equals(returnQuantityItem)) {
				logger.log(LogStatus.PASS, "Return quantity  NOT going beyond the purchased quantity ");
			} else {
				logger.log(LogStatus.FAIL, "Return quantity   going beyond the purchased quantity");
			}

			// Verify the return subtotalcolumn 

			// Verify the return total amount 

			// add return quantity
			merchreturn.click_addQuantity();
			Thread.sleep(10000);
			logger.log(LogStatus.INFO, "selected required return quantity");

			// JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,250)", "");

			merchreturn.click_calculatebtn();
			Thread.sleep(10000);
			logger.log(LogStatus.INFO, "After selecting the quantity clicked on calculate btn to get total");

			String ActretSubtotal1 = merchreturn.get_returnSub_AftCalculate().replace("$", "");
			String ActretSubtotal = "$" + ActretSubtotal1;
			logger.log(LogStatus.INFO, "ActualSubtotal:" + ActretSubtotal);

			String returnQuantityAft_Calculate = merchreturn.get_retQuantity_After_Calculate();
			double ExpRetSubtotal = (Double.parseDouble(ActretSubtotal1))
					* (Integer.parseInt(returnQuantityAft_Calculate));
			String expSubtotal = "$" + ExpRetSubtotal;
			logger.log(LogStatus.INFO, "expSubtotal:" + expSubtotal);
			System.out.println(expSubtotal);
			if (ActretSubtotal.equals(expSubtotal)) {
				logger.log(LogStatus.PASS,
						"Actual:" + ActretSubtotal + " and Expected values " + expSubtotal + "are matching");
			} else {
				logger.log(LogStatus.FAIL,
						"Actual:" + ActretSubtotal + " and Expected values " + expSubtotal + " are not matching");
			}

			String salesTax = merchreturn.get_salesTaxReturn().replace("$", "");
			String ActreturnTotal1 = merchreturn.get_totalReturn().replace("$", "");
			String ActreturnTotal = "$" + ActreturnTotal1;
			logger.log(LogStatus.INFO, "Actual return total:" + ActreturnTotal);

			if (driver.findElement(By.xpath("//span[text()='Discounts Applied']")).isDisplayed()) {

				String discountamount = driver
						.findElement(By
								.xpath("//div[@id='merchandisediscounts']//div[@class='k-grid-content ps-container']/table/tbody/tr/td[3]"))
						.getText().replace("$", "");

				double discountAmount = Double.parseDouble(discountamount);

				double ActReturnSubtotal = Double.parseDouble(ActretSubtotal1);

				double AfterdiscountReturnSubtotal = (ActReturnSubtotal + discountAmount);

				double expRetTotal = (Double.parseDouble(salesTax)) + AfterdiscountReturnSubtotal;
				String expTotalret = "$" + expRetTotal;
				System.out.println(expTotalret);
				logger.log(LogStatus.INFO, "expreturntotal:" + expTotalret);
				if (ActreturnTotal.equals(expTotalret)) {

					String scpath1 = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath1, true);
					String image1 = logger.addScreenCapture(scpath1);

					logger.log(LogStatus.PASS,
							"Actual:" + ActreturnTotal + " and Expected values " + expTotalret + "are matching",
							image1);

				} else {

					String scpath1 = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath1, true);
					String image1 = logger.addScreenCapture(scpath1);
					logger.log(LogStatus.FAIL,
							"Actual:" + ActreturnTotal + " and Expected values " + expTotalret + " are not matching");
				}

			}

			else {

				double expRetTotal = (Double.parseDouble(salesTax)) + (Double.parseDouble(ActretSubtotal1));
				String expTotalret = "$" + expRetTotal;
				System.out.println(expTotalret);
				logger.log(LogStatus.INFO, "expreturntotal:" + expTotalret);
				if (ActreturnTotal.equals(expTotalret)) {

					String scpath1 = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath1, true);
					String image1 = logger.addScreenCapture(scpath1);
					logger.log(LogStatus.PASS,
							"Actual:" + ActreturnTotal + " and Expected values " + expTotalret + "are matching",
							image1);
				} else {

					String scpath1 = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath1, true);
					String image1 = logger.addScreenCapture(scpath1);

					logger.log(LogStatus.FAIL,
							"Actual:" + ActreturnTotal + " and Expected values " + expTotalret + " are not matching");
				}

			}

			double expRetTotal = (Double.parseDouble(salesTax)) + (Double.parseDouble(ActretSubtotal1));
			String expTotalret = "$" + expRetTotal;
			System.out.println(expTotalret);
			logger.log(LogStatus.INFO, "expreturntotal:" + expTotalret);
			if (ActreturnTotal.equals(expTotalret)) {
				logger.log(LogStatus.PASS,
						"Actual:" + ActreturnTotal + " and Expected values " + expTotalret + "are matching");
			} else {
				logger.log(LogStatus.FAIL,
						"Actual:" + ActreturnTotal + " and Expected values " + expTotalret + " are not matching");
			}

			
			 * try{ Alert alt = driver.switchTo().alert(); alt.accept();
			 * }catch(Exception e) { logger.log(LogStatus.FAIL,
			 * "Test Script fail due to exception"); }
			 

			// verify enough cash on hand Question
			if (merchreturn.verify_enoughCashOnHand_Question()) {
				logger.log(LogStatus.PASS, "Is enough cash on hand to refund this transaction? is displaying");
			} else {
				logger.log(LogStatus.FAIL, "Is enough cash on hand to refund this transaction? is not displaying");
			}

			// click on yes radiobtn
			jse.executeScript("window.scrollBy(0,2000)", "");
			merchreturn.click_yes_Radiobtn();
			logger.log(LogStatus.INFO, "Clicked yes radio btn");

			// click on confirm with customer
			jse.executeScript("window.scrollBy(0,2000)", "");
			merchreturn.click_ConfWithCust();
			logger.log(LogStatus.INFO, "Clicked on confirm with customer btn");
			Thread.sleep(6000);

			// switching to customer screen
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.PASS, "Switching  to customer  screen");
			Reporter.log("Switching  to customer screen", true);
			Thread.sleep(10000);

			// Verify the return details displayed in CFS 
			String ReturnSubTotalinCFS = driver.findElement(By.xpath("//table/tbody/tr/td[4]/span")).getText();
			if (ActretSubtotal.equals(ReturnSubTotalinCFS)) {

				String scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				String image1 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Return details displayed in CFS and Merchandise Return screen are same ",
						image1);

			}

			else {

				String scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				String image1 = logger.addScreenCapture(scpath1);

				logger.log(LogStatus.FAIL,
						"Return details displayed in CFS and Merchandise Return screen are not  same");
			}

			if (driver.findElement(By.xpath("//th[text()='Product']")).isDisplayed()
					&& driver.findElement(By.xpath("//th[text()='Price']")).isDisplayed()
					&& driver.findElement(By.xpath("//th[text()='Return Quantity']")).isDisplayed()
					&& driver.findElement(By.xpath("//th[text()='Return Subtotal']")).isDisplayed()) {
				String scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				String image1 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS,
						"Return details Product, Price,Quantity ,Subtotal coulmn displayed successfully in CFS screen",
						image1);

			}

			else {

				String scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				String image1 = logger.addScreenCapture(scpath1);

				logger.log(LogStatus.FAIL,
						"Return details Product, Price,Quantity ,Subtotal coulmn not displayed  in CFS screen");
			}
			
			
			
			
			
			
			

			WebElement signature1 = driver
					.findElement(By.xpath("//div[@class='signature-container']/canvas[@class='signature-pad']"));

			Actions actionBuilder1 = new Actions(driver);
			Action drawAction1 = actionBuilder1.moveToElement(signature1, 660, 96).click().clickAndHold(signature1)
					.moveByOffset(120, 120).moveByOffset(60, 70).moveByOffset(-140, -140).release(signature1).build();
			drawAction1.perform();
			Thread.sleep(6000);

			jse.executeScript("window.scrollBy(0,2000)", "");
			
			//click Clear 
			driver.findElement(By.xpath("//button[contains(text(),'Clear Signature')]")).click();
			logger.log(LogStatus.PASS, "Upon clicking on Clear signature button ,signature is cleared ");
			Thread.sleep(6000);
			
			

			WebElement signature12 = driver
					.findElement(By.xpath("//div[@class='signature-container']/canvas[@class='signature-pad']"));

			Actions actionBuilder12 = new Actions(driver);
			Action drawAction12 = actionBuilder12.moveToElement(signature12, 660, 96).click().clickAndHold(signature12)
					.moveByOffset(120, 120).moveByOffset(60, 70).moveByOffset(-140, -140).release(signature12).build();
			drawAction12.perform();
			Thread.sleep(6000);

			
			
			
			
			
			
			driver.findElement(By.xpath("//button[text()='Accept']")).click();
			logger.log(LogStatus.PASS, "Clicked on Accept btn in customer facing screen");
			Thread.sleep(3000);
			
			
			
			
			
			
			

			// switched back to Main screen
			driver.switchTo().window(tabs.get(0));
			logger.log(LogStatus.PASS, "Switching back to PM screen");
			Reporter.log("Switching back to PM screen", true);

			jse.executeScript("window.scrollBy(0,1500)", "");
			merchreturn.click_Approve();
			logger.log(LogStatus.INFO, "Clicked on approve btn successfully");

			merchreturn.click_Submit();
			logger.log(LogStatus.INFO, "Clicked on Submit btn successfully");

			// enter emp id and click ok
			driver.findElement(By.id("employeeNumber")).sendKeys(tabledata.get("UserName"));
			Thread.sleep(4000);
			driver.findElement(By.xpath("//label//span[text()='I have given cash to the customer']/preceding-sibling::span[@class='button']")).click();
			logger.log(LogStatus.INFO, "clicked on checkbox  successfully");
			driver.findElement(By.partialLinkText("Ok")).click();
			logger.log(LogStatus.INFO, "Form   saved and transaction   completed successfully ");

			Thread.sleep(8000);

			// verify new record should be created in db

			String name_DB = DataBase_JDBC.executeSQLQuery("select name from type where typeid="
					+ " (select top 1 CLTransactionTypeID from cltransactionmaster where  " + "ipaddress='"
					+ tabledata.get("ipaddress") + "'" + " and description='" + tabledata.get("Description") + "')");
			logger.log(LogStatus.INFO, "New Record of refund has been created in db" + name_DB);
			*/
			
			//selecting Return Merchandise from Quick links Dropdown
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(250,0)", "");
			Thread.sleep(5000);
			js.executeScript("window.scrollBy(0,250)", "");
			driver.findElement(By.xpath("//div[@class='actions clearfix-container']//span[contains(text(),'Quick Links')]")).click();

			JavascriptExecutor je = (JavascriptExecutor) driver;
			//Identify the WebElement which will appear after scrolling down
			WebElement element =driver.findElement(By.xpath("//ul[@class='k-list k-reset ps-container ps-active-y']/li[4]"));
			// now execute query which actually will scroll until that element is not appeared on page.
			je.executeScript("arguments[0].scrollIntoView(true);",element);
			element.click();
			logger.log(LogStatus.INFO, "Selected Return Merchandise option from Quick links dropdown successfully");
			Reporter.log("Selected  Return Merchandise option from Quick links dropdown successfully",true);

			Thread.sleep(3000);
			Merchandise_TransactionHistory trans= new Merchandise_TransactionHistory(driver);

			if(trans.verify_pagetitle()){
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Merchandise_TransactionHistory page is displayed ");
				logger.log(LogStatus.INFO, "Merchandise_TransactionHistory page is displayed ",image1);
			}else{
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Merchandise_TransactionHistory page is displayed ");
				logger.log(LogStatus.INFO, "Merchandise_TransactionHistory page is displayed ",image1);
			}
			Thread.sleep(3000);
			
			String customername=trans.get_CustomerName();
			
			
			if(customername.contains(customername_dashboard)){
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Customer name from Merchandise_TransactionHistory page : "+customername+ "and customer dashboard : " +customername_dashboard+"are same");
				logger.log(LogStatus.INFO, "Customer name from Merchandise_TransactionHistory page : "+customername+ "and customer dashboard : " +customername_dashboard+"are same" ,image1);
			}else{
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Customer name from Merchandise_TransactionHistory page : "+customername+ "and customer dashboard : " +customername_dashboard+"are not same");
				logger.log(LogStatus.INFO, "Customer name from Merchandise_TransactionHistory page : "+customername+ "and customer dashboard : " +customername_dashboard+"are not same",image1);
			}
			
			
			
			String accNumber=trans.get_accNumber();
		
			if(accNumber.contains(accNumber_dashboard)){
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1,true);
				String image1=logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Customer Account  number from Merchandise_TransactionHistory page : "+accNumber+ "and customer dashboard : " +accNumber_dashboard+"are same");
				logger.log(LogStatus.INFO, "Customer Account  number from Merchandise_TransactionHistory page : "+accNumber+ "and customer dashboard : " +accNumber_dashboard+"are same",image1);
			}else{
				String scpath1=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image1=logger.addScreenCapture(scpath);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				logger.log(LogStatus.FAIL, "Customer Account  number from Merchandise_TransactionHistory page : "+accNumber+ "and customer dashboard : " +accNumber_dashboard+"are not  same");
				logger.log(LogStatus.INFO, "Customer Account  number from Merchandise_TransactionHistory page : "+accNumber+ "and customer dashboard : " +accNumber_dashboard+"are not same" ,image1);
			}
			
			String message=trans.get_noMerchadiseTransactMsg();
			logger.log(LogStatus.PASS,"The message : "+message+" is displayed");
			//screenshot
			 scpath=Generic_Class.takeScreenShotPath();
			 image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "The message : "+message+" is displayed",image);
			
			trans.click_cancel_Btn();
			logger.log(LogStatus.INFO, "Clicked on cancel button successfully");
			
			
			
			
			
			

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

	@AfterMethod
	public void afterMethod() {

		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "Merchandise", "Returnmerchandise_after90days", "Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "Merchandise", "Returnmerchandise_after90days", "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "Merchandise", "Returnmerchandise_after90days", "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);

	}

}
