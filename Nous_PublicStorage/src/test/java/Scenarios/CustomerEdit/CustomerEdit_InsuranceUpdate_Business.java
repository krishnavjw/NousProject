package Scenarios.CustomerEdit;

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
import org.openqa.selenium.WebDriver;
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
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.AddOrEditInsuranceModalWindow;
import Pages.CustDashboardPages.AddOrEditInsurancePage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MakePaymentPages.TransactionCompletePopup;
import Scenarios.Browser_Factory;

public class CustomerEdit_InsuranceUpdate_Business extends Browser_Factory {

	public ExtentTest logger;

	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";
	String scpath="";
	String image="";

	@DataProvider
	public Object[][] getPaymentsData() {
		return Excel.getCellValue_inlist(path, "CustomerEdit", "CustomerEdit", "CustomerEdit_InsuranceUpdate_Business");
	}

	@Test(dataProvider = "getPaymentsData")
	public void CustomerEdit_InsuranceUpdate_Business(Hashtable<String, String> tabledata) throws InterruptedException {
		try {
			logger = extent.startTest("CustomerEdit_InsuranceUpdate_Business", "CustomerEdit InsuranceUpdate starts ");

			if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerEdit").equals("Y"))) {
				resultFlag = "skip";
				throw new SkipException("Skipping the test");
			}

			Reporter.log("Test case started: " + testcaseName, true);
			Thread.sleep(5000);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			LoginPage loginPage = new LoginPage(driver);
			//String sitenumber=loginPage.get_SiteNumber();

			Thread.sleep(2000);
			loginPage.enterUserName(tabledata.get("UserName"));
			Thread.sleep(2000);

			Thread.sleep(2000);
			loginPage.enterPassword(tabledata.get("Password"));

			Thread.sleep(2000);
			loginPage.clickLogin();

			logger.log(LogStatus.PASS, "Login to Application  successfully");

			// =================Handling customer facing device========================
			Thread.sleep(5000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);

			String biforstNum = Bifrostpop.getBiforstNo();

			Reporter.log(biforstNum + "", true);

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			Thread.sleep(3000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			Reporter.log(tabs.size()+"",true);
			Thread.sleep(3000);
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

			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);

			//Verify that the user lands on the "PM Dashboard" screen after login and walkin cust title
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			String sitenumber=pmhomepage.get_SiteNumber();
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "Entered into advance search page");

			/*String sqlQuery = "Select Top 10 A.AccountNumber FROM AccountOrderItem AOI with(nolock)"
					+" Join AccountOrder AO with(nolock) on AO.AccountOrderID = AOI.AccountOrderID"
					+" Join Account A with(nolock) on A.AccountID = AO.AccountID"
					+" Join Customer C with(nolock) on C.CustomerID = A.CustomerID"
					+" join site s with(nolock) on s.siteid=aoi.siteid"
					+" join StorageOrderItem SOI with(nolock) on SOI.StorageOrderItemID = AOI.StorageOrderItemID "
					+" join rentalunit ru with(nolock) on ru.rentalunitid=soi.rentalunitid"
					+" join Type T with(nolock) on T.TypeID = C.CustomerTypeID "
					+" join Type T1 with(nolock) on T1.TypeID = SOI.paymentmethodtypeid"
					+" join accountorderitemproductoption aopp with(nolock) on aopp.accountorderitemid=aoi.accountorderitemid and aopp.expirationdate is null"
					+" join productoption po with(nolock) on po.productoptionid=aopp.productoptionid and po.productoptiontypeid=226"
					+" join cltransaction clt with(nolock) on clt.accountorderitemid=aoi.accountorderitemid"
					+" where 1 = 1"
					+" and s.sitenumber='"+sitenumber+"'"
					+" and soi.VacateDate is null"
					+" and c.customertypeid= 91"
					+" group by a.accountnumber"
					+" having sum((clt.amount * clt.quantity)+clt.discountamount) >0 and count(distinct ru.rentalunitid)=1"; 
			 */
			String sqlQuery = "Select Top 1 u.AccountNumber FROM vw_unitdetails u "
					+ "join accountorderitemproductoption aopp with(nolock) on aopp.accountorderitemid=u.accountorderitemid "
					+ "join productoption po with(nolock) on po.productoptionid=aopp.productoptionid "
					+ "where u.VacateDate is null "
					+ "and u.customertypeid=91 "
					+ "and aopp.productoptionid = (select top 1 aopp1.productoptionid from accountorderitemproductoption aopp1 where "
					+ "aopp1.accountorderitemid=u.accountorderitemid order by lastupdate desc) "
					+ "and not exists (select '1' from productoption po1 where po1.productoptionid=aopp.productoptionid and po1.productoptiontypeid=226)";
			
			String accNum  =DataBase_JDBC.executeSQLQuery(sqlQuery);
			System.out.println(accNum);

			//Advance search page
			Advance_Search advSearch= new Advance_Search(driver);

			advSearch.enterAccNum(accNum);
			logger.log(LogStatus.INFO, "Entered Account number"+ accNum);
			String scpath1=Generic_Class.takeScreenShotPath();
			String image1=logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "Navigated to customer dashboard",image1);

			jse.executeScript("window.scrollBy(0,-2000)", "");
			Thread.sleep(5000);

			advSearch.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "Click on Search button successfully");

			Thread.sleep(10000);
		
			Thread.sleep(1000);

			// Taking Screenshot
			String scpath2 = Generic_Class.takeScreenShotPath();
			String image2 = logger.addScreenCapture(scpath2);
			logger.log(LogStatus.PASS, "Navigated to Customer Dashboard");
			logger.log(LogStatus.INFO, "Navigated to Customer Dashboard successfully", image2);

			// Customer Dashboard
			Cust_AccDetailsPage cust_accdetails = new Cust_AccDetailsPage(driver);
			if (cust_accdetails.getCustDashboardTitle()
					.contains("Confirm Customer's Identity and Contact Information")) {

				Thread.sleep(8000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,
						"Customer Account Dashboard displayed successfully for an Individual Custome :"
								+ cust_accdetails.getCustDashboardTitle());
				logger.log(LogStatus.INFO, "Image", image);
			} else {
				Thread.sleep(8000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer Account Dashboard not  displayed successfully ");
				logger.log(LogStatus.INFO, "Image", image);
			}

			
			String avlSpace = driver.findElement(By.xpath("//div[@id='customerDashboard']//div[@class='unit']")).getText();
			
			Thread.sleep(2000);
			
			Acc_SpaceDetailsPage Acc_SpaceDetailsPage = new Acc_SpaceDetailsPage(driver);
			cust_accdetails.clickSpaceDetails_tab();

			Thread.sleep(5000);
			
			
			String insuranceAmountBeforeUpdate = Acc_SpaceDetailsPage.get_InsuranceAmt();
			Thread.sleep(4000);
			
			logger.log(LogStatus.INFO, "Insurance Amount Before Update is : " + insuranceAmountBeforeUpdate);
			
			String snap1=Generic_Class.takeScreenShotPath();
			String irm1=logger.addScreenCapture(snap1);	
			logger.log(LogStatus.INFO, "img",irm1);


			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			
			AddOrEditInsuranceModalWindow AddOrEditInsuranceModalWindow = new AddOrEditInsuranceModalWindow(driver);

			String actualLinkText = Acc_SpaceDetailsPage.getModifyInsuranceLinkText();

			if (actualLinkText.equalsIgnoreCase("change")) {
				Acc_SpaceDetailsPage.clk_ChangeInsuranceLink();
				logger.log(LogStatus.INFO, "Clicked on change insurance link");

				Thread.sleep(3000);

				if (AddOrEditInsuranceModalWindow.verify_AddEditInsuranceHdr()) {
					String scpath = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath, true);
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "Add/Edit Insurance modal window is opened");
					logger.log(LogStatus.INFO, "Image", image);
				} else {
					Thread.sleep(5000);
					String scpath = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath, true);
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Add/Edit Insurance modal window is not opened");
					logger.log(LogStatus.INFO, "Image", image);
				}
			}

			else if (actualLinkText.equalsIgnoreCase("add")) {
				Acc_SpaceDetailsPage.clk_AddInsuranceLink();
				logger.log(LogStatus.INFO, "Clicked on add insurance link");
				Thread.sleep(3000);

				if (AddOrEditInsuranceModalWindow.verify_AddEditInsuranceHdr()) {
					String scpath = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath, true);
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "Customer Must Be Present modal window displayed ");
					logger.log(LogStatus.INFO, "Image", image);
				} else {
					Thread.sleep(5000);
					String scpath = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath, true);
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "Customer Must Be Present modal window not displayed ");
					logger.log(LogStatus.INFO, "Image", image);
				}

			}

			AddOrEditInsuranceModalWindow.clk_LaunchBtn();
			logger.log(LogStatus.INFO, "Clicked on Launch button successfully");
			Thread.sleep(8000);

			AddOrEditInsurancePage AddOrEditInsurancePage = new AddOrEditInsurancePage(driver);
			
			//Add/Edit Insurance" screen should be displayed to the user 
			
			if (driver.findElement(By.xpath("//h3[text()='Add/Edit Insurance']")).isDisplayed()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Add/Edit Insurance screen displayed to the user ");
				logger.log(LogStatus.INFO, "Image", image);
			} else {
				Thread.sleep(5000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Add/Edit Insurance screen not displayed to the user");
				logger.log(LogStatus.INFO, "Image", image);
			}
						
			//Verify the Location, Spaces and Coverage Options (insurance) are properly displayed in a tabular format 
			

			if (AddOrEditInsurancePage.verify_LocationIsDisplayed() && AddOrEditInsurancePage.verify_SpaceIsDisplayed()
					&& AddOrEditInsurancePage.verify_CoverageOptionsIsDisplayed()) {

				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS,
						"Location, Spaces and Coverage Options (insurance) are properly displayed in a tabular format ");
				logger.log(LogStatus.INFO, "Image", image);
			} else {
				Thread.sleep(8000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL,
						"Location, Spaces and Coverage Options (insurance) not  displayed in a tabular format");
				logger.log(LogStatus.INFO, "Image", image);
			}

			
			
			//Verify on hover over the location# the following details are displayed
			if(driver.findElement(By.xpath("//a[@class='location-details']")).isDisplayed()){
			WebElement locationEle = driver.findElement(By.xpath("//a[@class='location-details']"));
			Actions builder=new Actions(driver);
			builder.moveToElement(locationEle).build().perform();
			Thread.sleep(10000);
			
			String scpath = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath, true);
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS,
					"Upon hover over Location Name, Location Address,Location Phone and Location Image displayed properly ");
			logger.log(LogStatus.INFO, "Image", image);
			
			}
			
			else {
				Thread.sleep(8000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL,
						"Upon hover over Location Name, Location Address,Location Phone and Location Image not  displayed properly ");
				logger.log(LogStatus.INFO, "Image", image);
			}
			
			 if (actualLinkText.equalsIgnoreCase("add")) {
				if (AddOrEditInsurancePage.get_DefaultInsuranceAmt().contains("None")) {
					String scpath = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath, true);
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS,
							"For non insured spaces the dropdown value is displayed as  None  ");
					logger.log(LogStatus.INFO, "Image", image);
				} else {
					Thread.sleep(8000);
					String scpath = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath, true);
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL,
							"For non insured spaces the dropdown value is not displayed as  None  ");
					logger.log(LogStatus.INFO, "Image", image);
				}
			}

			Thread.sleep(3000);

			AddOrEditInsurancePage.selectLastValueInDropDown(driver);
			logger.log(LogStatus.INFO, "Insurance amount field is editable in the current location");
			Thread.sleep(3000);
			
			String snap=Generic_Class.takeScreenShotPath();
			String irm=logger.addScreenCapture(snap);	
			logger.log(LogStatus.INFO, "img",irm);


			Thread.sleep(3000);

			if (AddOrEditInsurancePage.isApproveBtnDisplayed()) {
				
				logger.log(LogStatus.PASS, "Approve button is displayed in the Add or Edit Insurance Page");
				
			} else {
				
				logger.log(LogStatus.FAIL, "Approve button is not displayed in the Add or Edit Insurance Page");
			}

			
			
			
			if (AddOrEditInsurancePage.isClearBtnDisplayed()) {
				
				logger.log(LogStatus.PASS, "Clear button is displayed in the Add or Edit Insurance Page");
				
			} else {
				
				logger.log(LogStatus.FAIL, "Clear button is not displayed in the Add or Edit Insurance Page");
			
			}

			if (AddOrEditInsurancePage.isInformationTextDisplayed()) {
				
				logger.log(LogStatus.PASS, "Information text is displayed in the Add or Edit Insurance Page");
				
			} else {
				
				logger.log(LogStatus.FAIL, "Information text is not displayed in the Add or Edit Insurance Page");
				
			}

			if (AddOrEditInsurancePage.isSignaturePadDisplayed()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Signature Pad is displayed in the Add or Edit Insurance Page");
				logger.log(LogStatus.INFO, "Image", image);
			} else {
				Thread.sleep(8000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Signature Pad is not displayed in the Add or Edit Insurance Page");
				logger.log(LogStatus.INFO, "Image", image);
			}
			
			
			/*if (driver.findElement(By.xpath("//div[contains(text(),'Add/Edit insurance for')]")).isDisplayed()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Text related to change insurance on top of the Customer Signature Box is displayed");
				logger.log(LogStatus.INFO, "Image", image);
			} else {
				Thread.sleep(8000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Text related to change insurance on top of the Customer Signature Box is not displayed");
				logger.log(LogStatus.INFO, "Image", image);
			}
			*/
			
			AddOrEditInsurancePage.clk_confirmWithCustomerBtn();
			logger.log(LogStatus.INFO, "Clicked on Confirm with Customer button");

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switching to customer Screen");

			Thread.sleep(6000);
			
			//Verify CFS screen should be displayed  
			
			/*if (driver.findElement(By.xpath("//span[text()='Accept Insurance']")).isDisplayed()) {
				
				logger.log(LogStatus.PASS, "CFS screen  displayed to the customer");
				
			} else {
				
				logger.log(LogStatus.FAIL, "CFS screen  not displayed to the customer");
				
			}
			*/
			
			//Verify the below in the CFS screen
			boolean signaturepad=driver.findElement(By.xpath("//div[@class='signature-area']//canvas[@class='signature-pad']")).isDisplayed();
			boolean clearsignature_btn=driver.findElement(By.xpath("//button[contains(text(),'Clear Signature')]")).isDisplayed();
			boolean pleaseEdit_btn=driver.findElement(By.xpath("//button[contains(text(),'Please Edit')]")).isDisplayed();
			boolean accept_Btn=driver.findElement(By.xpath("//div[@class='signature-area']//canvas[@class='signature-pad']")).isDisplayed();		
			boolean PleaseSignHereTxt=driver.findElement(By.xpath("//div[text()='Please sign here']")).isDisplayed();		
					
				if(signaturepad && clearsignature_btn && pleaseEdit_btn && accept_Btn && PleaseSignHereTxt)	{
					
					String scpath = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath, true);
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.PASS, "All the information in the CFS screen displayed successfully");
					logger.log(LogStatus.INFO, "Image", image);
				} else {
					Thread.sleep(8000);
					String scpath = Generic_Class.takeScreenShotPath();
					Reporter.log(scpath, true);
					String image = logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "All the information in the CFS screen not  displayed ");
					logger.log(LogStatus.INFO, "Image", image);
				}
			
			AddOrEditInsurancePage.clickAllCheckboxesInAcceptInsurance(driver);
			logger.log(LogStatus.INFO, "Clicked on all available check boxes in Accept Insurance Page");

			AddOrEditInsurancePage.drawSignature(driver);
			logger.log(LogStatus.INFO, "Signature is drawn in the signature pad");

			AddOrEditInsurancePage.clk_acceptBtn();

			Thread.sleep(8000);
			driver.switchTo().window(tabs.get(0));
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
		

			Thread.sleep(8000);
			
            //Add/Edit Insurance" screen should be displayed to the user 
			
			if (driver.findElement(By.xpath("//h3[text()='Add/Edit Insurance']")).isDisplayed()) {
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Add/Edit Insurance screen displayed to the user ");
				logger.log(LogStatus.INFO, "Image", image);
			} else {
				Thread.sleep(5000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Add/Edit Insurance screen not displayed to the user");
				logger.log(LogStatus.INFO, "Image", image);
			}
			
			//verify customer authorisation section
			if(driver.findElement(By.xpath("//div[contains(text(),'Customer Authorization')]")).isDisplayed()){
				logger.log(LogStatus.PASS, "Customer authorisation section is displayed");
			}else{
				logger.log(LogStatus.FAIL, "Customer authorisation section is not displayed");
			}
			
			
			AddOrEditInsurancePage.clk_ApproveBtn();
		

			Thread.sleep(2000);

			/*if (!(AddOrEditInsurancePage.isApproveBtnDisplayed())) {
				
				logger.log(LogStatus.PASS, "Approve button is displayed in the Add or Edit Insurance Page");
			
			}

			else {
				
				logger.log(LogStatus.FAIL, "Approve button is not displayed in the Add or Edit Insurance Page");
				
			}

			if (!(AddOrEditInsurancePage.isClearBtnDisplayed())) {
				
				logger.log(LogStatus.PASS, "Clear button is displayed in the Add or Edit Insurance Page");
				
			}

			else {
				
				logger.log(LogStatus.FAIL, "Clear button is not displayed in the Add or Edit Insurance Page");
				
			}

			if (AddOrEditInsurancePage.isMakePaymentDisplayed()) {
				
				logger.log(LogStatus.PASS, "Make Payment Button is displayed in the Add or Edit Insurance Page");
				
			}

			else {
				
				logger.log(LogStatus.FAIL, "Make Payment Button is not displayed in the Add or Edit Insurance Page");
				
			}
*/
			// Click on Make Payment button
			AddOrEditInsurancePage.clk_MakePayment();
			Thread.sleep(4000);
			
			 snap=Generic_Class.takeScreenShotPath();
			 irm=logger.addScreenCapture(snap);	
			logger.log(LogStatus.INFO, "img",irm);

			Thread.sleep(8000);
			if (!driver
					.findElement(By
							.xpath("//button[@class='float-right psbutton-priority js-submit-transaction margin-left']"))
					.isDisplayed()) {
				Thread.sleep(2000);
				driver.findElement(By.xpath("//button[text()='Confirm With Customer']")).click();
				Thread.sleep(8000);
				// driver.findElement(By.xpath("//div[@class='container']/div[@class='command-row
				// clearfix-container
				// margin-top']/a[@id='confirmButton']")).click();
				// AddOrEditInsurancePage.clk_confirmWithCustomerBtn();
				// logger.log(LogStatus.INFO, "Clicked on Confirm with Customer
				// button");
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_PAGE_DOWN);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
				driver.switchTo().window(tabs.get(1));
			

				Thread.sleep(6000);

				// Click on Confirm button

				driver.findElement(
						By.xpath("//div[@class='footer-row clearfix-container']/button[@id='confirmButton']")).click();

				logger.log(LogStatus.PASS, "Clicked on Confirm button Successfully");
				
				Thread.sleep(6000);
				
				 snap=Generic_Class.takeScreenShotPath();
				 irm=logger.addScreenCapture(snap);	
				logger.log(LogStatus.INFO, "img",irm);

				
				

				driver.switchTo().window(tabs.get(0));
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_PAGE_DOWN);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
				Thread.sleep(5000);

				String TotalAmount = driver
						.findElement(By
								.xpath("//div[@id='payment-table']/section[@class='row orderTotal']/div[@class='second col']/span[@class='js-total']"))
						.getText();

				// PaymentsPage payments=new PaymentsPage(driver);
				// payments.selectPaymentMethod("Cash", driver);

				// Selecting Cash
				driver.findElement(By
						.xpath("//div[@id='payment-methods']//span[@class='k-widget k-dropdown k-header payment-method-dropdown']"))
						.click();
				Thread.sleep(2000);
				List<WebElement> ListWbEle = driver
						.findElements(By.xpath("//ul[contains(@class,'k-list k-reset ps-container')]//li"));
				for (WebElement ele : ListWbEle) {
					String actualWbEleText = ele.getText();
					if (actualWbEleText.equalsIgnoreCase("Cash"))
						ele.click();

				}

				// Enter Amount
				// driver.findElement(By.xpath("//input[@id='cashAmount']")).clear();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//input[@id='cashAmount']")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
				Thread.sleep(2000);
				driver.findElement(By.xpath("//input[@id='cashAmount']")).sendKeys(TotalAmount);
				
				Thread.sleep(3000);
				
				 snap=Generic_Class.takeScreenShotPath();
				 irm=logger.addScreenCapture(snap);	
				logger.log(LogStatus.INFO, "img",irm);

				// Click on Apply button

				driver.findElement(By.xpath("//a[text()='Apply']")).click();
				Thread.sleep(2000);

				driver.findElement(By.xpath("(//button[text()='Submit'])[1]")).click();

				// Enter EMP ID
				TransactionCompletePopup transPopup = new TransactionCompletePopup(driver);
				logger.log(LogStatus.INFO, "Transaction Complete Popup object created");
				Thread.sleep(3000);

				transPopup.enterEmpNum(tabledata.get("UserName"));
				logger.log(LogStatus.INFO, "Entered Employee Id successfully");
				Thread.sleep(3000);
				
				 snap=Generic_Class.takeScreenShotPath();
				 irm=logger.addScreenCapture(snap);	
				logger.log(LogStatus.INFO, "img",irm);

				Thread.sleep(6000);
				transPopup.clickOk_btn();
				logger.log(LogStatus.INFO, "Clicked on Ok button successfully");
				Thread.sleep(45000);
				
				Thread.sleep(12000);
				
				 snap=Generic_Class.takeScreenShotPath();
				 irm=logger.addScreenCapture(snap);	
				logger.log(LogStatus.INFO, "img",irm);

				try {
					driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
					Thread.sleep(15000);
				}

				catch (Exception ex) {
					ex.printStackTrace();

				}
				
				// Click on Space details tab
				cust_accdetails.clickSpaceDetails_tab();

				Thread.sleep(6000);

				String insuranceAmountafterUpdate = Acc_SpaceDetailsPage.get_InsuranceAmt();

				// Verify Insurance amount is updated
				if (!insuranceAmountBeforeUpdate.equals(insuranceAmountafterUpdate)) {
					
					logger.log(LogStatus.PASS, "Insurance Amount is updated successfully and Insurance amount Before  update is : " + insuranceAmountBeforeUpdate  +"Insurance amount After   update is :" + insuranceAmountafterUpdate);
					
				}

				else {
					
					logger.log(LogStatus.FAIL, "Insurance Amount is not  updated successfully");
					
				}
				
				Thread.sleep(2000);
				
				 snap=Generic_Class.takeScreenShotPath();
				 irm=logger.addScreenCapture(snap);	
				logger.log(LogStatus.INFO, "img",irm);
				
				((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
				Thread.sleep(5000);

				//To check in Account Activity
				
			       Calendar cal = Calendar.getInstance();
			                     SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.getDefault());
			                     String strTodaysDate = df.format(cal.getTime());

			                     //cust_accdetails.clk_DocumentsTab();
			         cust_accdetails.click_AccountActivities();
			                    
			                     Thread.sleep(25000);
			                     
			                     // Validating Account activities Tab
			                     
			                     
									if(driver.findElements(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td/div[text()='"+avlSpace+"']/../following-sibling::td[contains(text(),'Insurance Addendum')]")).size()!=0)
				                     {
				                           
				                           logger.log(LogStatus.PASS, "Insurance coverage is displayed  in Activities  Tab");
				                          driver.findElement(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td/div[text()='"+avlSpace+"']/../following-sibling::td[contains(text(),'Insurance Addendum')]/preceding-sibling::td[@class='k-hierarchy-cell']/a")).click();
				                          Thread.sleep(4000); 
				                          
				                           scpath=Generic_Class.takeScreenShotPath();
				                           image=logger.addScreenCapture(scpath);
				                           
				                           logger.log(LogStatus.INFO, "Image",image);

				                     }else{

				                           scpath=Generic_Class.takeScreenShotPath();
				                           image=logger.addScreenCapture(scpath);
				                           logger.log(LogStatus.FAIL, "Insurance coverage is not displayed  in Account Activities  Tab");
				                           logger.log(LogStatus.INFO, "Image",image);
				                           if(resultFlag.equals("pass"))
				                                  resultFlag="fail";
				                     }
				                     
									
									String xpath="//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td/div[text()='"+avlSpace+"']/../following-sibling::td[contains(text(),'Payment (Cash)')]";
									
				                     if(driver.findElements(By.xpath(xpath)).size()!=0)
				                     {
				                         
				                    	 
				                    	 
				                    	   driver.findElement(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td/div[text()='"+avlSpace+"']/../following-sibling::td[contains(text(),'Payment (Cash)')]/preceding-sibling::td[@class='k-hierarchy-cell']/a")).click();
				                    	   Thread.sleep(4000); 
				                           logger.log(LogStatus.PASS, "Payment Cash is displayed in Account Activities  Tab");
				                           
				                           
				                           
				                           scpath=Generic_Class.takeScreenShotPath();
				                           image=logger.addScreenCapture(scpath);
				                           
				                           
				                           logger.log(LogStatus.INFO, "Image",image);

				                     }else{

				                           scpath=Generic_Class.takeScreenShotPath();
				                           image=logger.addScreenCapture(scpath);
				                           logger.log(LogStatus.FAIL, "Payment Cash is not displayed in Account Activities  Tab");
				                           logger.log(LogStatus.INFO, "Image",image);
				                           if(resultFlag.equals("pass"))
				                                  resultFlag="fail";
				                     }


				                     
				                     if(driver.findElements(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td/div[text()='"+avlSpace+"']/../following-sibling::td[text()='Insurance Add']")).size()!=0)
				                     {				                    	 
				                    	   driver.findElement(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td/div[text()='"+avlSpace+"']/../following-sibling::td[text()='Insurance Add']/preceding-sibling::td[@class='k-hierarchy-cell']/a")).click();
				                    	   Thread.sleep(4000); 
				                           logger.log(LogStatus.PASS, "Insurance Add is  displayed in Account Activities  Tab");
				                           scpath=Generic_Class.takeScreenShotPath();
				                           image=logger.addScreenCapture(scpath);
				                           
				                           
				                           logger.log(LogStatus.INFO, "Image",image);

				                     }else{

				                           scpath=Generic_Class.takeScreenShotPath();
				                           image=logger.addScreenCapture(scpath);
				                           logger.log(LogStatus.FAIL, "Insurance Add is  not displayed in Account Activities  Tab");
				                           logger.log(LogStatus.INFO, "Image",image);
				                           if(resultFlag.equals("pass"))
				                                  resultFlag="fail";
				                     }
				                     
			                     //Click on Document Tab
			                     cust_accdetails.clk_DocumentsTab();
			                     Thread.sleep(40000); 
			                     
			                     logger.log(LogStatus.PASS , "Clicked on Document Tab");
			                   //div[@id='documents-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[contains(text(),'Insurance Brochure')]

			                     if(driver.findElements(By.xpath("//div[@id='documents-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[contains(text(),'Insurance Brochure')]")).size()!=0)
			                     {
			                           logger.log(LogStatus.PASS, "Insurance Brochure is dispalyed in document  Tab");
			                       
			                     }else{

			                           scpath=Generic_Class.takeScreenShotPath();
			                           image=logger.addScreenCapture(scpath);
			                           logger.log(LogStatus.FAIL, "Insurance Brochure is not dispalyed in document  Tab");
			                           logger.log(LogStatus.INFO, "Image",image);
			                           if(resultFlag.equals("pass"))
			                                  resultFlag="fail";
			                     }
			                     
			                     //div[@id='documents-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[contains(text(),'Generate via Propertywalkin Insurance)]
			                     
			                     if(driver.findElements(By.xpath("//div[@id='documents-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[contains(text(),'Insurance Contract')]")).size()!=0)
			                     {
			                           logger.log(LogStatus.PASS, "Insurance Contract is dispalyed in document  Tab");
			                     
			                     }else{

			                           scpath=Generic_Class.takeScreenShotPath();
			                           image=logger.addScreenCapture(scpath);
			                           logger.log(LogStatus.FAIL, "Insurance Contract is not dispalyed in document  Tab");
			                           logger.log(LogStatus.INFO, "Image",image);
			                           if(resultFlag.equals("pass"))
			                                  resultFlag="fail";
			                     }
			                     			                     
			                   //div[@id='documents-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[contains(text(),'Insurance Privacy Policy')]
			                     
			                     if(driver.findElements(By.xpath("//div[@id='documents-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[contains(text(),'Insurance Privacy Policy')]")).size()!=0)
			                     {
			                        logger.log(LogStatus.PASS, "Insurance Privacy Policy is dispalyed in document  Tab");
			                       
			                     }else{

			                           scpath=Generic_Class.takeScreenShotPath();
			                           image=logger.addScreenCapture(scpath);
			                           logger.log(LogStatus.FAIL, "Insurance Privacy Policy is not dispalyed in document  Tab");
			                           logger.log(LogStatus.INFO, "Image",image);
			                           if(resultFlag.equals("pass"))
			                                  resultFlag="fail";
			                     }
			                    			                     
			                     //div[@id='documents-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[contains(text(),'Insurance Certificate')]
			                if(driver.findElements(By.xpath("//div[@id='documents-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[contains(text(),'Insurance Certificate')]")).size()!=0)
			                     {
			                           logger.log(LogStatus.PASS, "Insurance Certificate is dispalyed in document  Tab");
			                       

			                     }else{

			                           scpath=Generic_Class.takeScreenShotPath();
			                           image=logger.addScreenCapture(scpath);
			                           logger.log(LogStatus.FAIL, "Insurance Certificate is not dispalyed in document  Tab");
			                           logger.log(LogStatus.INFO, "Image",image);
			                           if(resultFlag.equals("pass"))
			                                  resultFlag="fail";
			                     }
			                     
			       
				//Click on Space Details link (cancel The insurance)
				
		         	cust_accdetails.clickSpaceDetails_tab();
		         	Thread.sleep(8000);
				
				
				// Click on Change link
				Acc_SpaceDetailsPage.clk_ChangeInsuranceLink();
				Thread.sleep(2000);

				AddOrEditInsuranceModalWindow.clk_LaunchBtn();
				
				Thread.sleep(8000);
				selectFirstValueInDropDown(driver);
				Thread.sleep(3000);
				
				
				// Click on Confirm with customer
				driver.findElement(By
						.xpath("//div[@class='container']/div[@class='command-row clearfix-container margin-top']/a[@id='confirmButton']"))
						.click();
				// AddOrEditInsurancePage.clk_confirmWithCustomerBtn();
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_PAGE_DOWN);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
				driver.switchTo().window(tabs.get(1));
			
				Thread.sleep(6000);

				AddOrEditInsurancePage.clickAllCheckboxesInAcceptInsurance(driver);
				
				AddOrEditInsurancePage.drawSignature(driver);
				
				AddOrEditInsurancePage.clk_acceptBtn();
				Thread.sleep(10000);
			
				driver.switchTo().window(tabs.get(0));
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_PAGE_DOWN);
						robot.keyRelease(KeyEvent.VK_CONTROL);
						robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

			
				Thread.sleep(5000);

				// Click on Approve button
				// AddOrEditInsurancePage.clk_ApproveBtn();
				driver.findElement(By.xpath("//div[@id='secondarySignaturePanel']//a[text()='Approve']")).click();
				Thread.sleep(2000);
				// Click on Submit button
				driver.findElement(By.xpath("//a[@id='submitButton']")).click();
								
				Thread.sleep(2000);
				 snap=Generic_Class.takeScreenShotPath();
				 irm=logger.addScreenCapture(snap);	
				logger.log(LogStatus.INFO, "img",irm);

				Thread.sleep(3000);
				transPopup.enterEmpNum(tabledata.get("UserName"));
			
				Thread.sleep(6000);
				// transPopup.clickOk_btn();

				driver.findElement(By.xpath("//a[@class='psbutton-priority margin-left ok-button floatright']"))
						.click();
			
				Thread.sleep(10000);
				
				try {
					driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
					Thread.sleep(15000);
				}

				catch (Exception ex) {
					ex.printStackTrace();

				}
				
				cust_accdetails.clickSpaceDetails_tab();

				Thread.sleep(5000);
				
				String insuranceAmountAfterCancelling = Acc_SpaceDetailsPage.get_InsuranceAmt();
				Thread.sleep(4000);
				
				logger.log(LogStatus.INFO, "Insurance Amount After  cancelling  is : " + insuranceAmountAfterCancelling);
				
				 snap1=Generic_Class.takeScreenShotPath();
				 irm1=logger.addScreenCapture(snap1);	
				logger.log(LogStatus.INFO, "img",irm1);
				
				
				   cust_accdetails.click_AccountActivities();
                                      
                   Thread.sleep(25000);
                   
                   // Validating Account activities Tab
                   
                 //div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td/div[text()='"+avlSpace+"']/../following-sibling::td[contains(text(),'Insurance removed by')]
				
                   String xpath1="//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td/div[text()='"+avlSpace+"']/../following-sibling::td[contains(text(),'Insurance Cancel')]";
					
                   if(driver.findElements(By.xpath(xpath1)).size()!=0)
                   {
                  	   driver.findElement(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td/div[text()='"+avlSpace+"']/../following-sibling::td[contains(text(),'Insurance Cancel')]/preceding-sibling::td[@class='k-hierarchy-cell']/a")).click();
                  	   Thread.sleep(4000); 
                         logger.log(LogStatus.PASS, "Customer initiated cancel the insurance ");
                          scpath=Generic_Class.takeScreenShotPath();
                         image=logger.addScreenCapture(scpath);
                          logger.log(LogStatus.INFO, "Image",image);

                   }else{

                         scpath=Generic_Class.takeScreenShotPath();
                         image=logger.addScreenCapture(scpath);
                         logger.log(LogStatus.FAIL, "Cancelling of Insurance is not done");
                         logger.log(LogStatus.INFO, "Image",image);
                         if(resultFlag.equals("pass"))
                                resultFlag="fail";
                   }
				
				
                   //Click on Document Tab
                   
                  /* cust_accdetails.clk_DocumentsTab();
                   Thread.sleep(40000); 
                   
                   logger.log(LogStatus.PASS , "Clicked on Document Tab");
	
                   //div[@id='documents-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[contains(text(),'Insurance Brochure')]
	
                   if(driver.findElements(By.xpath(" //div[@id='documents-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[contains(text(),'Insurance Brochure')]")).size()!=0)
                   {
                       
                  	 
                  	 
                  
                         logger.log(LogStatus.PASS, "Insurance Brochure is dispalyed in document  Tab");
                         
                         
                         
                       

                   }else{

                         scpath=Generic_Class.takeScreenShotPath();
                         image=logger.addScreenCapture(scpath);
                         logger.log(LogStatus.PASS, "Insurance Brochure is not dispalyed in document  Tab");
                         logger.log(LogStatus.INFO, "Image",image);
                         if(resultFlag.equals("pass"))
                                resultFlag="fail";
                   }
                   
                   
                   snap1=Generic_Class.takeScreenShotPath();
    				 irm1=logger.addScreenCapture(snap1);	
    				logger.log(LogStatus.INFO, "img",irm1);
    				
				*/
				

			}
			
			
			
			//------------------------------Make Payment flow in not correct-------------
			// Else

						else {

							try {
								Thread.sleep(8000);
								driver.findElement(By.xpath("//input[@value='Continue']")).click();
							} catch (Exception e) {
								System.out.println("Pop up is not avalable, so continuing!!");
							}

							Thread.sleep(4000);
							String TotalAmount = driver
									.findElement(By
											.xpath("//div[@id='payment-table']/section[@class='row orderTotal']/div[@class='second col']/span[@class='js-total']"))
									.getText();
							Thread.sleep(4000);

							// PaymentsPage payments=new PaymentsPage(driver);
							// payments.selectPaymentMethod("Cash", driver);

							// Selecting Cash
							driver.findElement(By
									.xpath("//div[@id='payment-methods']//span[@class='k-icon k-i-arrow-s']"))
									.click();
							Thread.sleep(2000);
							List<WebElement> ListWbEle = driver
									.findElements(By.xpath("//ul[contains(@class,'k-list k-reset ps-container')]//li"));
							int size=ListWbEle.size();
							for (int i=0;i<size;i++) {
								String actualWbEleText = ListWbEle.get(i).getText();
								if (actualWbEleText.equalsIgnoreCase("Cash")){
									ListWbEle.get(i).click();
								     break;
								}
							}

							// Enter Amount
							// driver.findElement(By.xpath("//input[@id='cashAmount']")).clear();
							Thread.sleep(2000);
							driver.findElement(By.xpath("//input[@id='cashAmount']")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
							Thread.sleep(2000);
							driver.findElement(By.xpath("//input[@id='cashAmount']")).sendKeys(TotalAmount);

							// Click on Apply button

							driver.findElement(By.xpath("//a[text()='Apply']")).click();
							Thread.sleep(2000);

							driver.findElement(By.xpath("(//button[text()='Submit'])[1]")).click();

							// Enter EMP ID
							TransactionCompletePopup transPopup = new TransactionCompletePopup(driver);
							logger.log(LogStatus.INFO, "Transaction Complete Popup object created");
							Thread.sleep(3000);

							transPopup.enterEmpNum(tabledata.get("UserName"));
							logger.log(LogStatus.INFO, "Entered Employee Id successfully");

							Thread.sleep(6000);
							transPopup.clickOk_btn();
							logger.log(LogStatus.INFO, "Clicked on Ok button successfully");
							Thread.sleep(25000);

							// Click on Space details tab
							cust_accdetails.clickSpaceDetails_tab();

							Thread.sleep(2000);

							String insuranceAmountafterUpdate = Acc_SpaceDetailsPage.get_InsuranceAmt();

							// Verify Insurance amount is updated
							if (!insuranceAmountBeforeUpdate.equals(insuranceAmountafterUpdate)) {
								
								logger.log(LogStatus.PASS, "Insurance Amount is updated successfully and Insurance amount Before  update is : " + insuranceAmountBeforeUpdate  +"Insurance amount After   update is :" + insuranceAmountafterUpdate);
								
							}

							else {
								
								logger.log(LogStatus.FAIL, "Insurance Amount is not  updated successfully");
								
							}
							
							Thread.sleep(6000);
							
							
							  snap = Generic_Class.takeScreenShotPath();
							  irm = logger.addScreenCapture(snap);	
							logger.log(LogStatus.INFO, "img",irm);

							// Click on Chane link
							/*Acc_SpaceDetailsPage.clk_ChangeInsuranceLink();

							AddOrEditInsuranceModalWindow.clk_LaunchBtn();
						
							Thread.sleep(8000);
							selectFirstValueInDropDown(driver);
							Thread.sleep(3000);

							// Click on Confirm with customer
							driver.findElement(By
									.xpath("//div[@class='container']/div[@class='command-row clearfix-container margin-top']/a[@id='confirmButton']"))
									.click();
							// AddOrEditInsurancePage.clk_confirmWithCustomerBtn();

							driver.switchTo().window(tabs.get(1));
							

							Thread.sleep(6000);

							AddOrEditInsurancePage.clickAllCheckboxesInAcceptInsurance(driver);
						

							AddOrEditInsurancePage.drawSignature(driver);
						

							AddOrEditInsurancePage.clk_acceptBtn();
							Thread.sleep(10000);
							

							driver.switchTo().window(tabs.get(0));
						

							Thread.sleep(8000);

							//AddOrEditInsurancePage.clk_ApproveBtn();
							//Click on Approve button
							driver.findElement(By.xpath("//div[@id='secondarySignaturePanel']//a[text()='Approve']")).click();
						

							Thread.sleep(2000);

							// Click on Submit button

							driver.findElement(By.xpath("//a[@id='submitButton']")).click();
							

							// Enter EMP ID

						
							Thread.sleep(3000);

							transPopup.enterEmpNum(tabledata.get("UserName"));
							

							Thread.sleep(6000);
							// transPopup.clickOk_btn();

							driver.findElement(By.xpath("//a[@class='psbutton-priority margin-left ok-button floatright']"))
									.click();
							
							Thread.sleep(10000);*/

						}

					}

		catch (Exception ex) {
			resultFlag = "fail";
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "------------- page is not displayed", image);
			logger.log(LogStatus.FAIL, "------------- page is not displayed"+ ex);
			ex.printStackTrace();
		}

				}

				public void selectFirstValueInDropDown(WebDriver driver) {

					JavascriptExecutor js = (JavascriptExecutor) driver;

					WebElement dropdown = driver.findElement(By.xpath("//div[@id='insurance']//span[contains(@role,'listbox')]"));
					js.executeScript("arguments[0].click();", dropdown);

					List<WebElement> listInDropDown = driver.findElements(
							By.xpath("//ul[contains(@id,'ProductOptionSiteId_listbox')]//li[contains(@class,'k-item')]"));
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					
						e.printStackTrace();
					}
					js.executeScript("arguments[0].click();", listInDropDown.get(0));

				}
	
	

	@AfterMethod
	public void afterMethod() {

		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_InsuranceUpdate_Business", "Status",
					"Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_InsuranceUpdate_Business", "Status",
					"Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_InsuranceUpdate_Business", "Status",
					"Skip");
		}

		extent.endTest(logger);
		extent.flush();
	}

}
