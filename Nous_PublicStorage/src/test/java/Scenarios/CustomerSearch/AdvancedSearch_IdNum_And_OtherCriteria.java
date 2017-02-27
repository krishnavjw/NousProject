package Scenarios.CustomerSearch;

import java.awt.Robot;
import java.awt.event.KeyEvent;
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
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Acc_CustomerInfoPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.CustDashboardPages.EmergencyContact_EmployeeIdPage;
import Pages.CustInfoPages.Cust_CustomerInfoPage;
import Pages.CustInfoPages.Cust_EditAccountDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class AdvancedSearch_IdNum_And_OtherCriteria extends Browser_Factory{
	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"CustomerSearch","CustomerSearch", "AdvancedSearch_IdNum_And_OtherCriteria");
	}

	@Test(dataProvider="getLoginData")
	public void AdvancedSearch_IdNum_And_OtherCriteria(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerSearch").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Reporter.log("Test Case Started", true);
		Thread.sleep(5000);


		try{
			logger=extent.startTest("AdvancedSearch_IdNum_And_OtherCriteria","Customer Search - Advanced Search ID number and other criteria");
			
			Thread.sleep(5000);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			String scpath, image;
			Thread.sleep(2000);
			
			
			//=================Handling Customer Facing Device================================
			
			Thread.sleep(5000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
			logger.log(LogStatus.INFO, "PopUp window object is created successfully");
			String biforstNum = Bifrostpop.getBiforstNo();
			Robot robot=new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_T);
			Thread.sleep(3000);			
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			Reporter.log(tabs.size() + "", true);
			driver.switchTo().window(tabs.get(1));
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_aut"));

			List<WebElement> biforstSystem = driver.findElements(
					By.xpath("//div[@class='scrollable-area']//span[@class='bifrost-label vertical-center']"));
			for (WebElement ele : biforstSystem) {
				if (biforstNum.equalsIgnoreCase(ele.getText().trim())) {
					Reporter.log(ele.getText() + "", true);
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
			Thread.sleep(5000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);
			
			
			//================================== PM Home Page ===============================================
			
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			Thread.sleep(3000);
			
			String SiteNumber = pmhomepage.getLocation();
			logger.log(LogStatus.PASS, "location number is:"+SiteNumber);
			
			//Verifying PM Dash Board is displayed
			if (pmhomepage.get_WlkInCustText().trim().equalsIgnoreCase("Walk-In Customer")) {
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "PM Dashboard is displayed Successfully");
				logger.log(LogStatus.INFO, "PM Dashboard is displayed Successfully", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "PM Dashboard is not displayed");
				logger.log(LogStatus.INFO, "PM Dashboard is not displayed", image);

			}
			Thread.sleep(2000);
			
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Advance Search link in PM home page");
			
			//Advance search page
			Advance_Search advSearch= new Advance_Search(driver);
			
			advSearch.click_StateDropdown();	
			Thread.sleep(3000);	
			int numberOfPixelsToDragTheScrollbarDown = 1;
			Actions dragger = new Actions(driver);
			WebElement draggablePartOfScrollbar = driver.findElement(By.xpath("(//ul[@id='SearchContract_StateCode_listbox'])[2]//div[@class='ps-scrollbar-y-rail']//div[@class='ps-scrollbar-y']"));
			Thread.sleep(3000);
			List<WebElement> stateCodes = driver.findElements(By.xpath("(//ul[@id='SearchContract_StateCode_listbox'])[2]//li[@class='k-item']"));
			Thread.sleep(3000);


			for(int i=1;i<= stateCodes.size(); i++)
			{
				WebElement ele= driver.findElement(By.xpath("((//ul[@id='SearchContract_StateCode_listbox'])[2]//li[@class='k-item'])["+i+"]"));
				String value= ele.getText().trim();
				System.out.println(value);
				if(value.equalsIgnoreCase("CA"))
				{
					ele.click();
					break;
				}else if(i<=7)      
				{
					dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0, numberOfPixelsToDragTheScrollbarDown).release().build().perform();
					numberOfPixelsToDragTheScrollbarDown+=1;
					Thread.sleep(1000);

				}

			}
			Thread.sleep(1000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(500, 0)");
			Thread.sleep(3000);
			advSearch.clickStatusDropdown();
			Thread.sleep(2000);
			List<WebElement> statusTypes= driver.findElements(By.xpath("//ul[@id='SearchContract_SearchStatusID_listbox']/li[@class='k-item']"));
			Thread.sleep(2000);
			for(WebElement type:statusTypes)
			{
				if(type.getText().trim().equalsIgnoreCase("All"))
				{
					Thread.sleep(2000);
					type.click();
					break;
				}
			}
			Thread.sleep(1000);
			advSearch.enterLastName(tabledata.get("LastName"));
			Thread.sleep(1000);
			logger.log(LogStatus.INFO, "Entered Last Name is: "+tabledata.get("LastName"));
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img", image);
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);
			advSearch.clickSearchbtn();
			logger.log(LogStatus.INFO, "Clicked Search button successfully");
			Thread.sleep(6000);
			
			
			
			try{
			String accNum = driver.findElement(By.xpath("//div[@id='updateResultsPanel']//table//tbody//tr//td[11]/a")).getText();
			logger.log(LogStatus.INFO, "Account Number is : "+accNum);
			
			advSearch.click_accnum_Grid();
			logger.log(LogStatus.INFO, "Clicked on Account Number");
			Thread.sleep(4000);
			
			Acc_CustomerInfoPage custInfo = new Acc_CustomerInfoPage(driver);
			
			//String getAcc = custInfo.getAccNum();
			String cusName = driver.findElement(By.xpath("//h1[@class='customer-name bold']")).getText().trim();
			logger.log(LogStatus.INFO, "Customer Name is : "+cusName);

			Thread.sleep(2000);
			
			((JavascriptExecutor) driver).executeScript("window.scrollBy(2000,0)");
			Thread.sleep(2000);
			
			custInfo.click_EditAccountDetails();
			Thread.sleep(5000);

			Cust_EditAccountDetailsPage editAcc = new Cust_EditAccountDetailsPage(driver);
			editAcc.clickCustInfoRadioBtn();
			logger.log(LogStatus.INFO, "Selected Customer Info radio button");
			Thread.sleep(2000);
			editAcc.clickYesRadioBtn();
			logger.log(LogStatus.INFO, "Selected Yes radio button");
			Thread.sleep(1000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			Thread.sleep(1000);
			editAcc.clickLaunchBtn();
			Thread.sleep(5000);

			logger.log(LogStatus.PASS, "Navigated to Customer Information Screen");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			
			Cust_CustomerInfoPage info = new Cust_CustomerInfoPage(driver);
			info.clk_selectIdentificationType();
			Thread.sleep(2000);
			
			List<WebElement> iden= driver.findElements(By.xpath("//ul[@id='IndividualInformationModel_ContactIdentification_IdentificationTypeID_listbox']/li"));
			Thread.sleep(2000);
			for(WebElement type:iden)
			{
				if(type.getText().trim().equalsIgnoreCase("Passport"))
				{
					Thread.sleep(2000);
					type.click();
					break;
				}
			}
			Thread.sleep(1000);
			
			info.clk_Country();
			Thread.sleep(2000);
			
			
			int numberOfPixelsToDragTheScrollbarDow = 1;
			Actions dragger1 = new Actions(driver);
			WebElement draggablePartOfScrollbar1 = driver.findElement(By.xpath("//ul[@id='IndividualInformationModel_ContactIdentification_CountryTypeID_listbox']/div[@class='ps-scrollbar-y-rail']//div[@class='ps-scrollbar-y']"));
			Thread.sleep(3000);
			List<WebElement> listCountry1 = driver.findElements(By.xpath("//ul[@id='IndividualInformationModel_ContactIdentification_CountryTypeID_listbox']/li"));
			Thread.sleep(3000);

			for(int i=1;i<= listCountry1.size(); i++)
			{
				WebElement ele= driver.findElement(By.xpath("//ul[@id='IndividualInformationModel_ContactIdentification_CountryTypeID_listbox']/li["+i+"]"));
				String value= ele.getText().trim();
				System.out.println(value);
				if(value.equalsIgnoreCase("India"))
				{
					ele.click();
					break;
				}else if(i<=12)      
				{
					dragger1.moveToElement(draggablePartOfScrollbar1).clickAndHold().moveByOffset(0, numberOfPixelsToDragTheScrollbarDow).release().build().perform();
					numberOfPixelsToDragTheScrollbarDow+=1;
					Thread.sleep(1000);

				}

			}
			Thread.sleep(1000);
			info.enterLicenseNumber(tabledata.get("PassportNumber"));
			logger.log(LogStatus.INFO, "Entered Passport ID Number : "+tabledata.get("PassportNumber"));
			Thread.sleep(3000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);

			js = (JavascriptExecutor)driver; 
			js.executeScript("window.scrollBy(0,3000)", "");

			info.clk_verifyButton();
			Thread.sleep(5000);
			
			try{
				boolean error = driver.findElement(By.xpath("//div[contains(text(),'Email Address is required')]")).isDisplayed();
				if(error){
					info.clickNoEmailCheckBox();
					Thread.sleep(2000);
					
					js = (JavascriptExecutor)driver; 
					js.executeScript("window.scrollBy(0,3000)", "");

					info.clk_verifyButton();
					Thread.sleep(7000);
				}
				
			}catch(Exception e){
				
			}

			info.clk_ConfirmBtn();
			Thread.sleep(5000);

			driver.switchTo().window(tabs.get(1));
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

			WebElement signature = driver.findElement(By.xpath("//div[@class='signature-area']/canvas[@class='signature-pad']"));
			Actions actionBuilder = new Actions(driver);          
			Action drawAction = actionBuilder.moveToElement(signature,660,96).click().clickAndHold(signature)
					.moveByOffset(120, 120).moveByOffset(60,70).moveByOffset(-140,-140).release(signature).build();
			drawAction.perform();
			Thread.sleep(5000);
			logger.log(LogStatus.INFO, "Got signature from Customer");
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img", image);
			driver.findElement(By.xpath("//button[text()='Accept']")).click();
			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

			info.clk_ApproveBtn();
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "Customer approved the changes from CFS");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);

			info.clk_SaveBtn();
			Thread.sleep(5000);

			EmergencyContact_EmployeeIdPage empId = new EmergencyContact_EmployeeIdPage(driver);
			empId.enter_EmployeeId(tabledata.get("UserName"));
			Thread.sleep(3000);
			empId.click_ContinueBtn();
			Thread.sleep(20000);
			
			try{
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(6000);
			}catch(Exception e){
				
			}
			
			Cust_AccDetailsPage accDet = new Cust_AccDetailsPage(driver);
			accDet.click_BackToDashboard();
			Thread.sleep(8000);
			
			
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(6000);
			logger.log(LogStatus.INFO, "Clicked on Advance Search link in PM home page");
			
			//Advance search page
			
			advSearch.click_StateDropdown();	
			Thread.sleep(3000);	
			int numberOfPixelsToDragTheScrollbarDownn = 1;
			Actions dragger2 = new Actions(driver);
			WebElement draggablePartOfScrollbar2 = driver.findElement(By.xpath("(//ul[@id='SearchContract_StateCode_listbox'])[2]//div[@class='ps-scrollbar-y-rail']//div[@class='ps-scrollbar-y']"));
			Thread.sleep(3000);
			List<WebElement> stateCodes2 = driver.findElements(By.xpath("(//ul[@id='SearchContract_StateCode_listbox'])[2]//li[@class='k-item']"));
			Thread.sleep(3000);


			for(int i=1;i<= stateCodes2.size(); i++)
			{
				WebElement ele= driver.findElement(By.xpath("((//ul[@id='SearchContract_StateCode_listbox'])[2]//li[@class='k-item'])["+i+"]"));
				String value= ele.getText().trim();
				System.out.println(value);
				if(value.equalsIgnoreCase("CA"))
				{
					ele.click();
					break;
				}else if(i<=7)      
				{
					dragger2.moveToElement(draggablePartOfScrollbar2).clickAndHold().moveByOffset(0, numberOfPixelsToDragTheScrollbarDownn).release().build().perform();
					numberOfPixelsToDragTheScrollbarDownn+=1;
					Thread.sleep(1000);

				}

			}
			Thread.sleep(1000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(500, 0)");
			Thread.sleep(3000);
			advSearch.clickStatusDropdown();
			Thread.sleep(2000);
			List<WebElement> statusTypes2= driver.findElements(By.xpath("//ul[@id='SearchContract_SearchStatusID_listbox']/li[@class='k-item']"));
			Thread.sleep(2000);
			for(WebElement type:statusTypes2)
			{
				if(type.getText().trim().equalsIgnoreCase("All"))
				{
					Thread.sleep(2000);
					type.click();
					break;
				}
			}
			Thread.sleep(1000);
			advSearch.enterLastName(tabledata.get("LastName"));
			logger.log(LogStatus.INFO, "Entered Last Name is: "+tabledata.get("LastName"));
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(4000);
			advSearch.enterIdNum(tabledata.get("PassportNumber"));
			logger.log(LogStatus.INFO, "Entered Passport id : "+tabledata.get("PassportNumber"));
			Thread.sleep(2000);
			scpath = Generic_Class.takeScreenShotPath();
			image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img", image);
			Thread.sleep(2000);
			
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);
			advSearch.clickSearchbtn();
			logger.log(LogStatus.INFO, "Clicked Search button successfully");
			Thread.sleep(6000);
			
			
			String accNum1 = driver.findElement(By.xpath("//div[@id='updateResultsPanel']//table//tbody//tr//td[11]/a")).getText();
			
			advSearch.click_accnum_Grid();
			logger.log(LogStatus.INFO, "Clicked on Account Number");
			Thread.sleep(6000);
			
			if(accNum.equals(accNum1)){
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Account Number : "+accNum+ " is matched successfully "+accNum1);
				logger.log(LogStatus.INFO, "img", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Account Number : "+accNum+ " is not matched successfully "+accNum1);
				logger.log(LogStatus.INFO, "img", image);

			}
			
			String cusName1 = driver.findElement(By.xpath("//h1[@class='customer-name bold']")).getText().trim();
			if(cusName.equals(cusName1)){
				scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer Name : "+cusName+ " is matched successfully "+cusName1);
				logger.log(LogStatus.INFO, "img", image);
			} else {

				if (resultFlag.equals("pass"))
					resultFlag = "fail";

				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer Name : "+cusName+ " is not matched successfully "+cusName1);
				logger.log(LogStatus.INFO, "img", image);

			}
			
			
			}catch(Exception e){
				logger.log(LogStatus.INFO, "No records found for the searched criteria");
				Thread.sleep(1000);
				scpath = Generic_Class.takeScreenShotPath();
				image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "img", image);
				
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
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","AdvancedSearch_IdNum_And_OtherCriteria" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerSearch","AdvancedSearch_IdNum_And_OtherCriteria" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","AdvancedSearch_IdNum_And_OtherCriteria" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
	}

}
