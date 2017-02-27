package Scenarios.CustomerEdit;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import Pages.CustInfoPages.Cust_EditAccountDetailsPage;
import Pages.CustInfoPages.Cust_ResaleOrTaxExemptionsPage;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class Customeredit_UpdateTaxExemption extends Browser_Factory{

	public ExtentTest logger;
	String resultFlag="fail";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerEdit","CustomerEdit",  "Customeredit_UpdateTaxExemption");
	}



	@Test(dataProvider="getLoginData")
	public void Customeredit_UpdateTaxExemption(Hashtable<String, String> tabledata) throws InterruptedException 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerEdit").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Thread.sleep(5000);

		try
		{

			//Login to the application as PM 
			logger=extent.startTest("Customeredit_UpdateTaxExemption","Customeredit_UpdateTaxExemption");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "DM Logged in successfully");

			JavascriptExecutor jse = (JavascriptExecutor)driver;

			//connecting to customer device
			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Reporter.log("object created successfully",true);
			Thread.sleep(5000);
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

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			Thread.sleep(3000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(3000);
		




			ArrayList<String> query=DataBase_JDBC.executeSQLQuery_List("Select distinct top 1 A.AccountNumber, C.FirstName,C.MiddleInitial, C.LastName, CU.CompanyName, CC.ClassName, SOI.VacateDate, CU.IsAuctionBuyer , t1.name as militarytype 	"
					+ "from Account A with(nolock) "
					+ "Join AccountAddress AA with(nolock) on AA.AccountID = A.AccountID "
					+ "Join Address Ad with(nolock) on AA.AddressID = Ad.AddressID "
					+ "Join Customer CU with(nolock) on A.CustomerID = CU.CustomerID "
					+ "Join Contact C with(nolock) on CU.ContactID = C.ContactID "
					+ "Join CustomerClass CC with(nolock) on CU.CustomerClassID = CC.CustomerClassID "
					+ "Join AccountOrder AO with(nolock) on A.AccountID = AO.AccountID "
					+ "Join AccountOrderItem AOI with(nolock) on AO.AccountOrderID  = AOI.AccountOrderID "
					+ "Join StorageOrderItem SOI with(nolock)on AOI.StorageOrderItemID = SOI.StorageOrderItemID "
					+ "Join Type T with(nolock) on T.TypeID = SOI.StorageOrderItemTypeID "
					+ "left join customermilitaryinfo cmi with(nolock) on cmi.customerid=Cu.customerid "
					+ "left join Type T1 with(nolock) on T1.typeid=cmi.militarytypeid "
					+ " "
					+ "Where 1=1 "
					+ " "
					+ "And SOI.VacateDate is Null "
					+ "And CU.CustomerTypeID=90 "
					//+ "--And CU.CustomerClassID=3 "
					//+ "--And CU.IsMilitary = 0 and cmi.customerid is null  -- Non military "
					+ "And t1.name='Active Duty'  and cmi.isactive=1 ");
			//+ "--And t1.name='InActive'  and cmi.isactive=1-- InActive military "
			//+ "--And CU.IsAuctionBuyer=0 "
			//+ "--and aoi.siteid=31");

			String accNum=query.get(0);

			Thread.sleep(1000);

			//====================================================



			Thread.sleep(5000);
			DM_HomePage dmhome = new DM_HomePage(driver);
			if (dmhome.is_DMDashBoardTitle()) {

				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "DM  Dashboard displayed successfully ");
				logger.log(LogStatus.PASS, "Image", image);
			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "DM  Dashboard not displayed   ");
				logger.log(LogStatus.FAIL, "DM  Dashboard not displayed ", image);
			}

			// Click on Advance Search
			dmhome.click_advSearchLink();
			logger.log(LogStatus.INFO, "clicked on Advance search link successfully");

			Advance_Search advsearch = new Advance_Search(driver);
			//logger.log(LogStatus.INFO, "Advance search object created successfully");

			// Fetching Acc num through Quer who is having Balance due
			//String accNum = DataBase_JDBC.executeSQLQuery(sqlQuery);
			advsearch.enterAccNum(accNum);

			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);

			logger.log(LogStatus.INFO, "Entered account number : "+accNum);

			advsearch.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "clicked on search button successfully");
			Thread.sleep(10000);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(250,0)", "");
			Thread.sleep(3000);
			js.executeScript("window.scrollBy(0,350)", "");

			Cust_AccDetailsPage cust = new Cust_AccDetailsPage(driver);

			if (cust.isCustdbTitleDisplayed()) {
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "customer Dashboard is displayed successfully", image);
			} else {
				if (resultFlag.equals("pass"))
					resultFlag = "fail";
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "customer Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "customer Dashboard is not displayed ", image);
			}
			Thread.sleep(5000);


			js.executeScript("window.scrollBy(500,0)", "");

			cust.click_EditAccDetails();
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "Clicked on Edit Account details link successfully");

			Cust_EditAccountDetailsPage edit = new Cust_EditAccountDetailsPage(driver);
			if(edit.verify_EditAccDetails_Title()){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit Account details Modal window is displayed successfully");
				logger.log(LogStatus.INFO, "Edit Account details Modal window successfully",image);
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Edit Account details Modal window not displayed");
				logger.log(LogStatus.INFO, "Edit Account details Modal window not displayed",image);

			}


			edit.clickResaleTaxExemptionRadioBtn();
			logger.log(LogStatus.INFO, "Clicked on other customer status radiobtn successfully");

			Thread.sleep(1000);
			edit.clickYesRadioBtn();
			logger.log(LogStatus.INFO, "Clicked on YES radio button successfully");
			Thread.sleep(1000);

			edit.clickLaunchBtn();
			logger.log(LogStatus.INFO, "Clicked on Launch button successfully");
			Thread.sleep(4000);


			Cust_ResaleOrTaxExemptionsPage taxexempt=new Cust_ResaleOrTaxExemptionsPage(driver);
			if(taxexempt.verify_ResaleOrTaxExemptions_Title()){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Resale or taxexempt page is displayed successfully");
				logger.log(LogStatus.INFO, "Resale or taxexempt page is displayed successfully",image);
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Resale or taxexempt page is not displayed");
				logger.log(LogStatus.INFO, "Resale or taxexempt page is not displayed",image);

			}

			if(taxexempt.verify_customerNoLongrPresent_chkbx()){
				logger.log(LogStatus.PASS, "Customer is no longer taxexempt checkbox is displayed");
			}
			else{
				logger.log(LogStatus.FAIL, "Customer is no longer taxexempt checkbox is notdisplayed");
			}

			taxexempt.click_customerNoLongrPresent_chkbx();
			logger.log(LogStatus.INFO, "Clicked Customer is no longer taxexempt checkbox ");


			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Tax Exemption data page is displayed");
			logger.log(LogStatus.INFO, "Image",image);

			taxexempt.enter_taxExemptNumber(tabledata.get("TaxExemptNumber"));
			
			taxexempt.select_State();
			Thread.sleep(2000);
			taxexempt.enter_expiry_month(tabledata.get("Month"));
			Thread.sleep(2000);
			taxexempt.enter_expiry_Date(tabledata.get("Date"));
			Thread.sleep(2000);
			taxexempt.enter_expiry_year(tabledata.get("Year"));
			Thread.sleep(2000);
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Entered taxexempt number,State and Expiration date successfully");
			
			driver.findElement(By.xpath("//input[@class='tax-exemption__unit-checkbox']/following-sibling::span[@class='button']")).click();

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			Thread.sleep(2000);

			taxexempt.click_UploadBtn();
			logger.log(LogStatus.INFO, "Clicked on Upload button successfully");

			Thread.sleep(3000);

			String pathfile = System.getProperty("user.dir");
			System.out.println("Path is :-- " + pathfile);
			uploadFile(pathfile + File.separator + "Test.png");

			Thread.sleep(3000);

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			
			//String taxexemptnumber_UI=taxexempt.get_taxExemptNumber().substring(6, 10);
			
			
			String taxexemptnumber_UINo = tabledata.get("TaxExemptNumber");
			
			String taxexemptnumber_UI = taxexemptnumber_UINo.substring(6, 10);
			
			System.out.println(taxexemptnumber_UI);
			
			
			
			String expirationdate_UI=tabledata.get("Year")+"-"+tabledata.get("Month")+"-"+tabledata.get("Date");
			System.out.println(expirationdate_UI);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);

			
			Thread.sleep(3000);
			taxexempt.click_confirmWithCustomer_Btn();
			logger.log(LogStatus.INFO, "Clicked on Confirm with customer button successfully");

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN); 
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Swithching to Customer facing screen successfully successfully");

			Thread.sleep(5000);



			String taxexeptNumIn_CFS=driver.findElement(By.xpath("//div[contains(text(),'Tax Exemption Number:')]")).getText().replaceFirst("Tax Exemption Number:", "");
			String StateIn_CFS=driver.findElement(By.xpath("//div[contains(text(),'State:')]")).getText().replaceFirst("State:", "");
			logger.log(LogStatus.INFO, "Tax exemption number in CFS is: "+taxexeptNumIn_CFS);
			logger.log(LogStatus.INFO, "State mentioned in CFS is: "+StateIn_CFS);
			Thread.sleep(2000);

			WebElement signature2 = driver
					.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions actionBuilder2 = new Actions(driver);
			Action drawAction2 = actionBuilder2.moveToElement(signature2, 660, 96).click().clickAndHold(signature2)
					.moveByOffset(120, 120).moveByOffset(60, 70).moveByOffset(-140, -140).release(signature2).build();
			drawAction2.perform();
			Thread.sleep(3000);

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);

			driver.findElement(By.xpath("//button[text()='Accept']")).click();


			logger.log(LogStatus.INFO, "clicking on Accept button ");

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			Thread.sleep(6000);
			driver.switchTo().window(tabs.get(0));
		
			Thread.sleep(1000);

			taxexempt.click_approve_Btn();
			logger.log(LogStatus.INFO, "Clicked on Approve button ");
			Thread.sleep(5000);

			taxexempt.click_save_Btn();
			logger.log(LogStatus.INFO, "Clicked on save button successfully");
			Thread.sleep(10000);

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Enter Employee number Modal window is displayed");
			logger.log(LogStatus.INFO, "Image",image);

			driver.findElement(By.xpath("//textarea[@name='notesText']")).sendKeys("Tax Exemption document uploaded");
			Thread.sleep(1000);
			driver.findElement(By.id("employeeNumber")).sendKeys(tabledata.get("UserName"));
			logger.log(LogStatus.INFO, "Entered Note and Employee id successfully");


			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);

			driver.findElement(By.partialLinkText("OK")).click();
			Thread.sleep(10000);

			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);

			js.executeScript("window.scrollBy(500,0)", "");

			
			try{
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(2000);
			}catch(Exception ex){}
			
			cust.click_EditAccDetails();
			Thread.sleep(4000);
			logger.log(LogStatus.INFO, "Clicked on Edit Account details link ");

			if(edit.verify_EditAccDetails_Title()){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit Account details Modal window is displayed successfully");
				logger.log(LogStatus.INFO, "Edit Account details Modal window successfully",image);
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Edit Account details Modal window not displayed");
				logger.log(LogStatus.INFO, "Edit Account details Modal window not displayed",image);

			}

	Thread.sleep(10000);
			edit.clickResaleTaxExemptionRadioBtn();
			logger.log(LogStatus.INFO, "Clicked on other customer status radiobtn ");

			Thread.sleep(5000);
			edit.clickYesRadioBtn();
	
			Thread.sleep(1000);

			edit.clickLaunchBtn();
			logger.log(LogStatus.INFO, "Clicked on Launch button ");
			Thread.sleep(8000);


			if(taxexempt.verify_ResaleOrTaxExemptions_Title()){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Resale or taxexempt page is displayed with tax exemption details successfully");
				logger.log(LogStatus.INFO, "Resale or taxexempt page is displayed with tax exemption details successfully",image);
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Resale or taxexempt page is displayed with tax exemption details is not displayed");
				logger.log(LogStatus.INFO, "Resale or taxexempt page is displayed with tax exemption details is not displayed",image);

			}

Thread.sleep(5000);
			ArrayList<String> lst=DataBase_JDBC.executeSQLQuery_List("select top 1 aoi.notax,aoit.AccountOrderItemTaxExemptionid,aoit.CustomerTaxExemptionid,ct.*,u.* from vw_unitdetails u "
					+"  join accountorderitem aoi on u.accountorderitemid=aoi.accountorderitemid "
					+"  left join AccountOrderItemTaxExemption aoit on aoi.accountorderitemid=aoit.accountorderitemid "
					+" left join CustomerTaxExemption ct on aoit.CustomerTaxExemptionid=ct.CustomerTaxExemptionid "
					+" where u.accountid='"+accNum+"'order by ExemptionExpirationDate desc" );
			
			

			//String noTax_flag=lst.get(0);
			String exemption_number=lst.get(4);
			String expirationdate_DB=lst.get(7);
			
			
			
			//logger.log(LogStatus.INFO, "After adding tax eption the notax flag is set to : "+noTax_flag);
			if(taxexemptnumber_UI.contains(exemption_number)){
			logger.log(LogStatus.INFO, "The last four digits of the tax exemption number in UI : "+taxexemptnumber_UI+" and DB :  "+exemption_number+" are matching");
			}else{
				logger.log(LogStatus.INFO, "The last four digits of the tax exemption number in UI : "+taxexemptnumber_UI+" and DB :  "+exemption_number+" are not matching");
				
			}
			
			if(expirationdate_UI.contains(expirationdate_DB)){
			logger.log(LogStatus.INFO, "The Expiration date in UI : "+expirationdate_UI+" and DB :  "+expirationdate_DB+" are matching");
			}else{

				logger.log(LogStatus.INFO, "The Expiration date in UI : "+expirationdate_UI+" and DB :  "+expirationdate_DB+" are matching");
			}










		}catch (Exception ex) {
			resultFlag = "fail";
			String scpath = Generic_Class.takeScreenShotPath();
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "------------- page is not displayed", image);
			logger.log(LogStatus.FAIL, "------------- page is not displayed"+ ex);
			ex.printStackTrace();
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
			exp.printStackTrace();
		}
	}

	public void setClipboardData(String string) {
		// StringSelection is a class that can be used for copy and paste
		// operations.
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}


	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerEdit","Customeredit_UpdateTaxExemption" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerEdit","Customeredit_UpdateTaxExemption" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerEdit","Customeredit_UpdateTaxExemption" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);


	}

}
