package Scenarios.MiscallaneousCharges;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;

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
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.DMledgeredit.EditLedger;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Scenarios.Browser_Factory;


public class MiscCharges_Verify_The_Edit_General_Ledger extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");
	JavascriptExecutor js1;
	DM_HomePage dmhomepage;
	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"Miscellaneous","Miscellaneous", "Verify_The_Edit_General_Ledger");
	}
	@Test(dataProvider="getLoginData")
	public void Verify_The_Edit_General_Ledger(Hashtable<String, String> tabledata) throws Exception 
	{
		if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("Miscellaneous").equals("Y"))) {
			resultFlag = "skip";
			throw new SkipException("Skipping the test");
		}
		Reporter.log("Test Case Started", true);
		js1 = (JavascriptExecutor)driver;
		try {
			logger = extent.startTest("Verify_The_Edit_General_Ledger",
					"Customer Miscellaneous Charges General Ledger- RM Login");
			LoginPage login = new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Entered username, password and clicked on login button");

			// =================Handling customer facing
			// device========================
			/*
			 * Dashboard_BifrostHostPopUp Bifrostpop = new
			 * Dashboard_BifrostHostPopUp(driver); logger.log(LogStatus.INFO,
			 * "PopUp window object is created successfully");
			 * 
			 * String biforstNum = Bifrostpop.getBiforstNo();
			 * 
			 * Reporter.log(biforstNum + "", true);
			 * 
			 * //driver.findElement(By.cssSelector("body")).sendKeys(Keys.
			 * CONTROL, "t"); Robot robot = new Robot();
			 * robot.keyPress(KeyEvent.VK_CONTROL);
			 * robot.keyPress(KeyEvent.VK_T);
			 * robot.keyRelease(KeyEvent.VK_CONTROL);
			 * robot.keyRelease(KeyEvent.VK_T);
			 * 
			 * 
			 * Thread.sleep(5000);
			 * 
			 * 
			 * ArrayList<String> tabs = new
			 * ArrayList<String>(driver.getWindowHandles());
			 * Reporter.log(tabs.size() + "", true);
			 * driver.switchTo().window(tabs.get(1));
			 * driver.get("http://wc2qa.ps.com/CustomerScreen/Mount");
			 * 
			 * List<WebElement> biforstSystem = driver.findElements( By.xpath(
			 * "//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"
			 * )); for (WebElement ele : biforstSystem) { if
			 * (biforstNum.equalsIgnoreCase(ele.getText().trim())) {
			 * Reporter.log(ele.getText() + "", true); ele.click(); break; } }
			 * 
			 * //driver.findElement(By.cssSelector("body")).sendKeys(Keys.
			 * CONTROL, Keys.PAGE_DOWN); robot.keyPress(KeyEvent.VK_CONTROL);
			 * robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			 * robot.keyRelease(KeyEvent.VK_CONTROL);
			 * robot.keyRelease(KeyEvent.VK_PAGE_DOWN); Thread.sleep(5000);
			 * driver.switchTo().window(tabs.get(0));
			 * driver.navigate().refresh(); Thread.sleep(8000);
			 * driver.findElement(By.xpath(
			 * "//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']"
			 * )) .click(); Thread.sleep(10000);
			 */
			/*
			 * Dashboard_BifrostHostPopUp Bifrostpop1= new
			 * Dashboard_BifrostHostPopUp(driver); logger.log(LogStatus.INFO,
			 * "PopUp window object is created successfully");
			 * Bifrostpop1.clickContiDevice(); Thread.sleep(8000);
			 */
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
			dmhomepage=new DM_HomePage(driver);
			dmhomepage.clk_DistrictDropDown();
			logger.log(LogStatus.INFO, "Clicked on District drop down");
			Thread.sleep(10000);
			dmhomepage.selDistrict();
			logger.log(LogStatus.INFO, "Selected the first option from district drop down");
			Thread.sleep(12000);
			dmhomepage.clk_SiteDropDown();
			String location = dmhomepage.getSiteId();
			logger.log(LogStatus.INFO, "Captured the location from site ID dropdown, lacation:  "+location);
			Thread.sleep(4000);
			dmhomepage.selSiteId();
			logger.log(LogStatus.INFO, "location is selected from Site droopdown");
			js1.executeScript("window.scrollBy(1000,0)", "");
			Thread.sleep(4000);
			dmhomepage.clk_advancedSearchLnk();
			logger.log(LogStatus.PASS, "Clicked on advanced search link in the RM Dashboard sucessfully");
			Thread.sleep(8000);
			Advance_Search search = new Advance_Search(driver);
			if (search.verifyAdvSearchPageIsOpened()) {
				logger.log(LogStatus.PASS, "Advanced Search page is opened");
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
			} else {
				logger.log(LogStatus.FAIL, "Advanced Search page is not opened");
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image", image);
				resultFlag = "fail";
			}
			logger.log(LogStatus.INFO, "Created Object for Advance Search");
			String sqlQuery ="Select A.accountnumber From AccountOrderItem AOI "
					+ "INNER JOIN Site S ON S.SiteID = AOI.SiteID "
					+ "INNER JOIN StorageOrderItem SOI ON AOI.storageOrderItemID = SOI.storageOrderItemID "
					+ "INNER JOIN AccountOrder AO  ON AO.AccountOrderID = AOI.AccountOrderID "
					+ "INNER JOIN Account A ON A.AccountID = AO.AccountID "
					+ "join rentalunit ru on ru.rentalunitid=soi.rentalunitid "
					+ "join cltransaction clt on clt.accountorderitemid=aoi.accountorderitemid "
					+ "Where soi.VacateDate is null and soi.vacatenoticedate is null "
					+ "and s.sitenumber='"+location+"' group by  A.AccountID,A.accountnumber "
					+ "having sum(clt.amount + clt.discountamount)>0 order by 1";
			//String accnum = DataBase_JDBC.executeSQLQuery(sqlQuery);
			String accnum = "10034615";
			if (!accnum.equalsIgnoreCase("")) {
				logger.log(LogStatus.INFO, "SQL query fetched Account number from DB is :" + accnum);
			} else {
				resultFlag = "fail";
				logger.log(LogStatus.INFO, "SQL query failed to fetch the Account number from DB");
				throw new SkipException("SQL Query failed to fetch the account number from DB");
			}
			search.enterAccNum(accnum);
			logger.log(LogStatus.INFO, "Entered the Account Number in Account field successfully");
			search.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "Clicked on Search button successfully");
			Thread.sleep(10000);
			Cust_AccDetailsPage cust_accdetails = new Cust_AccDetailsPage(driver);
			if (cust_accdetails.isCustdbTitleDisplayed()) {
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "Customer Dashboard is displayed successfully", image);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				String scpath = Generic_Class.takeScreenShotPath();
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "Customer Dashboard is not displayed ", image);
				throw new SkipException("Customer dash board is not displayed");
			}
			logger.log(LogStatus.PASS,"Total due now amount before adding adjustment fee:  " + cust_accdetails.getTotalDue());
			String toatlDueNowAmtBefore = cust_accdetails.getTotalDue().replace("$", "").replace(",", "");
			double totalDueBeforeAdjustment = Double.parseDouble(toatlDueNowAmtBefore);
			cust_accdetails.clickSpaceDetails_tab();
			Thread.sleep(4000);
			EditLedger ledgeredit = new EditLedger(driver);
			Thread.sleep(7000);
			js1.executeScript("window.scrollBy(500,0)", "");
			if (ledgeredit.isDisplayed_EditLedgerLink()) {
				String scpath1 = Generic_Class.takeScreenShotPath();
				String image1 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.PASS, "Ledger link is displaying");
				logger.log(LogStatus.INFO, "Ledger link is displaying", image1);
				js1.executeScript("window.scrollBy(0,500)", "");
				ledgeredit.clickONEditLedgerLink();
				Thread.sleep(8000);
			} else {
				String scpath1 = Generic_Class.takeScreenShotPath();
				String image1 = logger.addScreenCapture(scpath1);
				logger.log(LogStatus.FAIL, "Ledger link is not displayed");
				logger.log(LogStatus.INFO, "Ledger link is not displayed", image1);
				throw new SkipException("ledger link is not displayed under space details tab");
			}
			ArrayList<String> ledgerNamesDB = DataBase_JDBC.executeSQLQuery_List("select name from GLAccount where Adjustable='1'");
			List<WebElement> ledgerNamesGUI = driver.findElements(By.xpath("//div[@id='glaGrid']//table/tbody[@role='rowgroup']/tr/td[1]/span"));
			if(ledgerNamesGUI.size()==ledgerNamesDB.size()){
				logger.log(LogStatus.PASS, "Size of ledgers from UI and DB are matched");
				logger.log(LogStatus.INFO, "Size of ledgers from UI:  "+ledgerNamesGUI.size());
				logger.log(LogStatus.INFO, "Size of ledgers from DB:  "+ledgerNamesDB.size());
			}else{
				logger.log(LogStatus.WARNING, "Size of ledgers from UI and DB are not matched");
				logger.log(LogStatus.INFO, "Size of ledgers from UI:  "+ledgerNamesGUI.size());
				logger.log(LogStatus.INFO, "Size of ledgers from DB:  "+ledgerNamesDB.size());
				String[] ExpectedLedgerNames = tabledata.get("ExpectedLedgerNames").split(";");
				logger.log(LogStatus.INFO, "Count of Ledgers Expected" + ExpectedLedgerNames.length);
				logger.log(LogStatus.INFO, "Count of Ledgers from UI" + ledgerNamesGUI.size());
				logger.log(LogStatus.INFO, "Count of Ledgers from DB" + ledgerNamesDB.size());
				boolean flag = false;
				for (int i = 0; i < ExpectedLedgerNames.length; i++) {
					flag = false;
					for (int j = 0; j < ledgerNamesGUI.size(); j++) {
						if (ExpectedLedgerNames[i].equalsIgnoreCase(ledgerNamesGUI.get(j).getText())) {
							logger.log(LogStatus.PASS, "Expected Ledger labe present in UI: " + ExpectedLedgerNames[j]);
							flag = true;
							break;
						}
					}
					if (!flag) {
						logger.log(LogStatus.FAIL, "Expected Ledger labe not present is UI :" + ExpectedLedgerNames[i]);
					}
				}
				String[] adjustAmount = tabledata.get("DataToValidate").split(";");
				List<WebElement> legderslableEditboxes = driver.findElements(By.xpath("//input[@id='Adjustment']"));
				for (int k = 0; k < adjustAmount.length; k++) {
					Double newTotalSum=00.00;
					Double adjustmentSum=00.00;
					logger.log(LogStatus.PASS,"Total due now amount before adding adjustment fee: $ " + totalDueBeforeAdjustment);
					for (int j = 0; j < legderslableEditboxes.size(); j++) {
						Thread.sleep(4000);
						WebElement adjustmentEdt=driver.findElement(By.xpath("//div[@id='glaGrid']//table/tbody[@role='rowgroup']/tr["+(j+1)+"]/td[3]//input"));
						WebElement ledgerNameWb=driver.findElement(By.xpath("//div[@id='glaGrid']//table/tbody[@role='rowgroup']/tr["+(j+1)+"]/td[1]/span"));
						String ledgerlable=ledgerNameWb.getText();
						if(adjustmentEdt.isDisplayed()){						
							adjustmentEdt.sendKeys(adjustAmount[k]);
							adjustmentEdt.sendKeys(Keys.TAB);
							logger.log(LogStatus.INFO, "Entered the adjustment of  $ "+adjustAmount[k]+" for the label '"+ledgerlable+"'");
						}else{
							Thread.sleep(4000);
							adjustmentEdt.sendKeys(adjustAmount[k]);
							adjustmentEdt.sendKeys(Keys.TAB);
							logger.log(LogStatus.INFO, "Entered the adjustment of  $ "+adjustAmount[k]+" for the label '"+ledgerlable+"'");
						}
						Thread.sleep(4000);
						String newTotalSumString=driver.findElement(By.xpath("//div[@id='newTotalSum']")).getText();
						newTotalSum= Double.parseDouble(newTotalSumString.replace("$", "").replace(",", ""));
						logger.log(LogStatus.INFO, "New total:  $"+newTotalSum);
						String adjustmentString=driver.findElement(By.xpath("//div[@id='adjustmentSum']")).getText();
						adjustmentSum= Double.parseDouble(adjustmentString.replace("$", "").replace(",", ""));
						logger.log(LogStatus.INFO, "Cumulative sum of adjustments added:  $"+adjustmentSum);
						List<WebElement> newTotalListWb=driver.findElements(By.xpath("//div[@id='glaGrid']//table/tbody[@role='rowgroup']/tr/td[4]/div"));
						List<String> newTotalListUI=new ArrayList<String>();
						ListIterator<WebElement> itr=newTotalListWb.listIterator();
						while(itr.hasNext()){
							newTotalListUI.add(itr.next().toString());
						}
						String EditGeneralLedgerPageurl=driver.getCurrentUrl();
						String orderItemId=EditGeneralLedgerPageurl.split("OrderItemId=")[1];
						String newTotalListquery="Select ISNULL(sum((clt.amount*clt.quantity)+clt.discountamount),0.00) as Balance from glaccount gl with(nolock) left join cltransaction clt with(nolock) on clt.glaccountid=gl.glaccountid and clt.accountorderitemid="+orderItemId+" where gl.glaccountid in (2,3,4,5,7,8,26,27,30,45,47,110,120,121,122,123,124,252,253,254,255,256) group by gl.name order by gl.name";
						List<String> newTotalListDB=DataBase_JDBC.executeSQLQuery_List(newTotalListquery);
						if(newTotalListUI.size()==newTotalListDB.size()){
							logger.log(LogStatus.PASS, "New total sizes are matched between UI and DB");
							if(newTotalListUI.contains(newTotalListDB)){
								logger.log(LogStatus.PASS, "New total data are matched between UI and DB");
							}
						}else{
							logger.log(LogStatus.FAIL, "New total sizes are not matched between UI and DB");
							logger.log(LogStatus.INFO, "New total sizeUI:  "+newTotalListUI.size());
							logger.log(LogStatus.INFO, "New total sizeDB:  "+newTotalListDB.size());
						}
					}
					driver.findElement(By.id("AdjustmentNote")).sendKeys("Some Text");
					logger.log(LogStatus.INFO, "Entered the text: 'Some Text' in 'Must provide a descriptive note' text field");
					Thread.sleep(4000);
					driver.findElement(By.xpath("//a[contains(text(),'Save')]")).click();
					logger.log(LogStatus.INFO, "Clicked on save button");
					Thread.sleep(8000);
					driver.findElement(By.id("enteredEmployeeNumber")).sendKeys(tabledata.get("UserName"));
					logger.log(LogStatus.INFO, "Entered employee id:  "+tabledata.get("UserName"));
					Thread.sleep(4000);
					driver.findElement(By.xpath("//a[contains(text(),'Confirm')]")).click();
					logger.log(LogStatus.INFO, "Clicked on Confirm button");
					Thread.sleep(10000);
					if (cust_accdetails.isCustdbTitleDisplayed()) {
						String scpath = Generic_Class.takeScreenShotPath();
						String image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.PASS, "Customer Dashboard is displayed successfully");
						logger.log(LogStatus.INFO, "Customer Dashboard is displayed successfully", image);
					} else {
						if (resultFlag.equals("pass"))
							resultFlag = "fail";
						String scpath = Generic_Class.takeScreenShotPath();
						String image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.FAIL, "Customer Dashboard is not displayed ");
						logger.log(LogStatus.INFO, "Customer Dashboard is not displayed ", image);
						throw new SkipException("Customer dash board is not displayed, terminated the execution");
					}
					String AftertoatlDueNowAmt = cust_accdetails.getTotalDue().replace("$", "").replace(",", "");
					double toatlDueNowAmtAfterAdjustment = Double.parseDouble(AftertoatlDueNowAmt);
					driver.findElement(By.xpath("//a[contains(text(),'View Details')]")).click();
					Thread.sleep(6000);
					WebElement total_view_detail_due = driver.findElement(By.xpath("//div[@class='total-due-now__details__detail total-due-now__details__detail--total']"));
					js1.executeScript("arguments[0].scrollIntoView(true);", total_view_detail_due);
					String total_view_detailString = total_view_detail_due.getText().replace("$", "").replace(",", "");
					Double total_view_detail = Double.parseDouble(total_view_detailString);
					if (total_view_detail == (toatlDueNowAmtAfterAdjustment)) {
						String scpath = Generic_Class.takeScreenShotPath();
						String image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.PASS, "Total due is displaying Correctly");
						logger.log(LogStatus.INFO, "Total due is displaying Correctly", image);
					} else {
						if (resultFlag.equals("pass"))
							resultFlag = "fail";
						String scpath = Generic_Class.takeScreenShotPath();
						String image = logger.addScreenCapture(scpath);
						logger.log(LogStatus.FAIL, "Total due is not displaying Correctly ");
						logger.log(LogStatus.INFO, "Total due is not displaying Correctly ", image);
					}
					Thread.sleep(4000);
					try {
						driver.findElement(By.partialLinkText("Cancel")).click();
						Thread.sleep(4000);
					} catch (Exception e) {
						//	driver.findElement(By.xpath("//div[contains(text(),'Location')]")).click();
						Thread.sleep(4000);
						driver.findElement(By.xpath("//div[contains(@style,'padding-top: 40px; min-height: 50px; display: block; to')]/div/div[@id='paymentDetailsDialog']/..")).sendKeys(Keys.ESCAPE);
						Thread.sleep(4000);
					}
					if(newTotalSum==(totalDueBeforeAdjustment+adjustmentSum)){
						logger.log(LogStatus.PASS, "Tota due now before adjustment and New total after adjustment are sucessfully validated");
						logger.log(LogStatus.INFO, "Total due before Adjust ment:  $ "+totalDueBeforeAdjustment);
						logger.log(LogStatus.INFO, "Sum of adjusted amounts:  $ "+adjustmentSum);
						logger.log(LogStatus.INFO, "New total sum:  $ "+newTotalSum);
					}else{
						logger.log(LogStatus.FAIL, "Tota due now before adjustment and New total after adjustment are not as expected");
						logger.log(LogStatus.INFO, "Total due before Adjust ment:  $ "+totalDueBeforeAdjustment);
						logger.log(LogStatus.INFO, "Sum of adjusted amounts:  $ "+adjustmentSum);
						logger.log(LogStatus.INFO, "New total sum:  $ "+newTotalSum);
					}
					Thread.sleep(4000);
					cust_accdetails.clickSpaceDetails_tab();
					Thread.sleep(8000);
					if (ledgeredit.isDisplayed_EditLedgerLink()) {
						String scpath1 = Generic_Class.takeScreenShotPath();
						String image1 = logger.addScreenCapture(scpath1);
						logger.log(LogStatus.PASS, "Ledger link is displaying");
						logger.log(LogStatus.INFO, "Ledger link is displaying", image1);
						js1.executeScript("window.scrollBy(0,500)", "");
						ledgeredit.clickONEditLedgerLink();
						Thread.sleep(8000);
					} else {
						String scpath1 = Generic_Class.takeScreenShotPath();
						String image1 = logger.addScreenCapture(scpath1);
						logger.log(LogStatus.FAIL, "Ledger link is not displayed");
						logger.log(LogStatus.INFO, "Ledger link is not displayed", image1);
						throw new SkipException("ledger link is not displayed under space details tab");
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			resultFlag = "fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception " + e);
		}
	}
	@AfterMethod
	public void afterMethod(){
		Reporter.log(resultFlag, true);
		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "Miscellaneous", "Verify_The_Edit_General_Ledger", "Status", "Pass");
		} else if (resultFlag.equals("fail")) {
			Excel.setCellValBasedOnTcname(path, "Miscellaneous", "Verify_The_Edit_General_Ledger", "Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "Miscellaneous", "Verify_The_Edit_General_Ledger", "Status", "Skip");
		}
		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " + testcaseName, true);
	}
}
