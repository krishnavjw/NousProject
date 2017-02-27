package Scenarios.CustomerEdit;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import Pages.CustDashboardPages.Acc_CustomerInfoPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.EditAccountDetails.EditAccountDetails;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.MilitaryStatusPages.MilitaryStatus;
import Scenarios.Browser_Factory;

public class CustomerEdit_Military_Status_Update_Individual extends Browser_Factory {

	public ExtentTest logger;


	String path = Generic_Class.getPropertyValue("Excelpath");
	String resultFlag = "pass";
	WebDriverWait wait;

	@DataProvider
	public Object[][] getCustomerEditData() {
		return Excel.getCellValue_inlist(path, "CustomerEdit", "CustomerEdit",
				"CustomerEdit_Military_Status_Update_Individual");
	}

	@Test(dataProvider = "getCustomerEditData")
	public void CustomerEdit_Military_Status_Update_Individual(Hashtable<String, String> tabledata)
			throws InterruptedException {
		try {
			logger = extent.startTest("CustomerEdit_Military_Status_Update_Individual",
					"CustomerEdit_Military_Status_Update_Individual starts ");

			wait = new WebDriverWait(driver, 60);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			if (!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerEdit").equals("Y"))) {
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
            driver.get(Generic_Class.getPropertyValue("CustomerScreenPath_QA"));
            //driver.get("http://wc2qa.ps.com/CustomerScreen/Mount");  

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


//===============================================================================



			PM_Homepage pmhome = new PM_Homepage(driver);
			// Click on Advance Search

			pmhome.clk_AdvSearchLnk();
			Thread.sleep(2000);


			String location = pmhome.getLocation();

			// Advance search page
			Advance_Search advSearch = new Advance_Search(driver);


			String query="select top 1 accountnumber from vw_unitdetails vw with(nolock) "+
					"left join customermilitaryinfo cmi with(nolock) on cmi.customerid = vw.customerid "+
					"where vw.vacatedate is null "+
					"and vw.customertypeid = 90 "+
					"and not exists (select '1' from customermilitaryinfo cmi2 with(nolock) where cmi2.customerid = vw.customerid) "+
					"group by vw.accountnumber ";

			String AccountNumber = DataBase_JDBC.executeSQLQuery(query);
			Thread.sleep(6000);
			advSearch.enterAccNum(AccountNumber);

			//advSearch.enterAccNum(tabledata.get("AccountNumber"));
			logger.log(LogStatus.INFO, "Account number entered successfully : "+AccountNumber);

			String scpath11 = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath11, true);
			String image11 = logger.addScreenCapture(scpath11);
			logger.log(LogStatus.PASS, "img", image11);

			((JavascriptExecutor) driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(1000);

			advSearch.clk_SearchAccount();
			logger.log(LogStatus.INFO, "Clicked on search button ");


			Thread.sleep(8000);

			//String MilitaryStatusInitial = driver.findElement(By.xpath("//div[contains(text(),'Military Status:')]/following-sibling::div[contains(text(),'None')]")).getText();
			Acc_CustomerInfoPage custinfo=new Acc_CustomerInfoPage(driver);
			if(custinfo.verifymilitarystaus())
			{
				logger.log(LogStatus.PASS, " Militarystatus field is displayed");
			}else{
				logger.log(LogStatus.FAIL, "Militarystatus field is not displayed");
			}

			//verify if military status is active,verify view details link
			String status=custinfo.get_MilitaryStausText();
			String expStatus=tabledata.get("MilitaryStatus1");
			if(status.contains(expStatus)){

				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, " Militarystatus Initially is :"+expStatus);
				logger.log(LogStatus.INFO, " Militarystatus Initially is :"+expStatus,image);
			}

			else{
				scpath11 = Generic_Class.takeScreenShotPath();
				image11 = logger.addScreenCapture(scpath11);
				logger.log(LogStatus.INFO, "img", image11);
				logger.log(LogStatus.FAIL, " Militarystatus is not "+status);

			}



			Thread.sleep(3000);


			Cust_AccDetailsPage AccDetails = new Cust_AccDetailsPage(driver);
			Thread.sleep(3000);
		
	
			
			//========================Changing Military status from None to Active duty==========================================
			
			AccDetails.clk_editAcc_btn();
			logger.log(LogStatus.INFO, "Clicked on Edit Account details button ");

			// Verify Edit Account Details Modal window displayed
			EditAccountDetails editAccountDetails = new EditAccountDetails(driver);
			if (editAccountDetails.isEditAccDetailsPopUpDisplayed()) {

				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed ");
				logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed", image);
			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed ");
				logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed", image);
			}

			editAccountDetails.clk_MilitaryStatusRadioBtn();
			logger.log(LogStatus.INFO, "Clicked on Military Status Button");

			editAccountDetails.clk_YesRadioBtn();
			logger.log(LogStatus.INFO, "Clicked on Yes Radio Button");

			editAccountDetails.clk_LaunchBtn();
			logger.log(LogStatus.INFO, "Clicked on Launch Button");
						



			try {
                String scpath1 = Generic_Class.takeScreenShotPath();
                Reporter.log(scpath1, true);
                String image1 = logger.addScreenCapture(scpath1);

                logger.log(LogStatus.INFO, "img", image1);
                driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
                Thread.sleep(8000);
          } catch (Exception e) {
                String scpath1 = Generic_Class.takeScreenShotPath();
                Reporter.log(scpath1, true);
                String image1 = logger.addScreenCapture(scpath1);

                logger.log(LogStatus.INFO, "img", image1);
          }
			 

			if(driver.findElement(By.xpath("//h3[text()='Military Status']")).isDisplayed()){

				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, " Military information screen displayed  ");
				logger.log(LogStatus.PASS, " Military information screen displayed  ", image);
			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Military information screen not  displayed ");
				logger.log(LogStatus.FAIL, "Military information screen not displayed", image);
			}



			MilitaryStatus militaryStatus = new MilitaryStatus(driver);
			Thread.sleep(3000);

			militaryStatus.clk_DutyDropDown();
			logger.log(LogStatus.INFO, "Clicked on Duty Type Drop Down");
			Thread.sleep(2000);
			
            try
       {  
                   
                   WebElement webelement = driver.findElement(By.xpath("//div[@id='Form_MilitaryTypeId-list']//div[@class='ps-scrollbar-y-rail']/div[@class='ps-scrollbar-y']"));
           Actions dragger = new Actions(driver);
           dragger.moveToElement(webelement).clickAndHold().moveByOffset(0, -300).release().build().perform();
          Thread.sleep(5000);    
                                         
            }
            catch (Exception e)
            {
                e.printStackTrace();}
                 
                 Thread.sleep(6000);
             	driver.findElement(By.xpath("//div[@id='Form_MilitaryTypeId-list']//ul[@id='Form_MilitaryTypeId_listbox']/li[text()='"+tabledata.get("MilitaryStatus2")+"']")).click();
    			Thread.sleep(5000);
          

			String dutyType=driver.findElement(By.xpath("//span[@class='k-widget k-dropdown k-header js-military-type']//span[@class='k-input']")).getText();

			if ((tabledata.get("MilitaryStatus2")).contains(dutyType)){
				logger.log(LogStatus.PASS, "Selected Duty Type is: " + dutyType);
				scpath11 = Generic_Class.takeScreenShotPath();
				image11 = logger.addScreenCapture(scpath11);
				logger.log(LogStatus.INFO, "img", image11);

			}else{
				logger.log(LogStatus.FAIL, "Selected Duty Type is : " +dutyType);
				scpath11 = Generic_Class.takeScreenShotPath();
				image11 = logger.addScreenCapture(scpath11);
				logger.log(LogStatus.INFO, "img", image11);
			}



			//JavascriptExecutor js = (JavascriptExecutor) driver;

			Thread.sleep(5000);

			//Enter all the data in the Active duty page
			militaryStatus.enterBirthDetails(tabledata.get("BirthDate"));

			militaryStatus.enterIdentificationDetails(tabledata.get("IdentificationNumber"));

			militaryStatus.enterActivityStartDate(tabledata.get("ActivityDutyStartDate"));
			
			//militaryStatus.enterActivityStartDate("11112020");

			String data = Generic_Class.get_RandmString();
			//int randomNum = 1400 + (int)(Math.random() * 1600); 
			String MilitaryUnit="Alpha"+data;
			militaryStatus.enterMilitaryUnit(MilitaryUnit);

			militaryStatus.select_MilitaryBranch(driver, tabledata.get("ExpectedBranch"));

			militaryStatus.enterSupervisorFirstName(tabledata.get("SupervisorFirstName"));

			militaryStatus.enterSupervisorLastName(tabledata.get("SupervisorLastName"));

			militaryStatus.enterSupervisorRank(tabledata.get("Rank"));

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

				 

			//militaryStatus.selectPhoneType(driver, "Cell ");

			militaryStatus.selectPhoneType(driver);
			Thread.sleep(3000);

			militaryStatus.enterAreaCode(tabledata.get("AreaCode"));

			militaryStatus.enterExchangeCode(tabledata.get("Exchange"));

			militaryStatus.enterLineNumber(tabledata.get("LineNumber"));


			String scpath1 = Generic_Class.takeScreenShotPath();
			String image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO,"All the military information is filled");
			logger.log(LogStatus.INFO, "img", image1);

			jse.executeScript("window.scrollBy(0,1000)", "");
			Thread.sleep(2000);

			militaryStatus.ClickOnConfirmButton(driver);
			logger.log(LogStatus.INFO, "Clicked on Confirm Button");

			Thread.sleep(8000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			driver.switchTo().window(tabs.get(1));
			logger.log(LogStatus.INFO, "Switching to customer Screen");

			Thread.sleep(2000);

			//logger.log(LogStatus.INFO, "Waiting for Signature Pad");
			militaryStatus.WaitUntilVisibilityOfSignaturePad();


			militaryStatus.drawSignature(driver);
			Thread.sleep(2000);

			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "img", image1);

			militaryStatus.ClickOnAcceptButton();
			logger.log(LogStatus.INFO, "Clicked on Accept button in CFS screen");

			driver.switchTo().window(tabs.get(0));
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_PAGE_DOWN);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
			logger.log(LogStatus.INFO, "Switching to Main Screen");

			Thread.sleep(2000);

			militaryStatus.clickOnApproveButton();
			logger.log(LogStatus.INFO, "Clicked on Approve button");

			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "img", image1);

			militaryStatus.ClickOnSubmitButton();
			logger.log(LogStatus.INFO, "Clicked on Save button");

			militaryStatus.EnterNotes();

			militaryStatus.EnterEmployeeNumber(tabledata.get("UserName"));

			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "img", image1);

			militaryStatus.ClickOnContinue();
			logger.log(LogStatus.INFO, "Entered Employee Number and clicked on ok button successfully");


			try {
				scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				image1 = logger.addScreenCapture(scpath1);

				logger.log(LogStatus.INFO, "img", image1);
				driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
				Thread.sleep(20000);
			} catch (Exception e) {
				scpath1 = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath1, true);
				image1 = logger.addScreenCapture(scpath1);

				logger.log(LogStatus.INFO, "img", image1);
			}

			Thread.sleep(10000);
			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO, "img", image1);

			Cust_AccDetailsPage cust= new Cust_AccDetailsPage(driver);


			Thread.sleep(10000);
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.getDefault());
			String strTodaysDate = df.format(cal.getTime());


			cust.click_AccountActivities();
			Thread.sleep(40000);
			driver.findElement(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Military Status']/following-sibling::td[contains(text(),'Military status added')]/preceding-sibling::td[@class='k-hierarchy-cell']/a")).click();
			Thread.sleep(3000);

			String duty_Status=driver.findElement(By.xpath("//div[@class='activity-detail']//div/span[contains(text(),'Status')]/following-sibling::span")).getText();

			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "The Military status is updated to : "+duty_Status);
			logger.log(LogStatus.INFO, "img", image1);
			logger.log(LogStatus.INFO, "Account activity created successfully");
			             
               cust.click_custInfo_Tab();
               Thread.sleep(3000);
               
               
               militaryStatus.waitForElement(driver);

			if (militaryStatus.getDutyStatus().contains(tabledata.get("MilitaryStatus2"))) {

				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Duty Status is changed Successfully from None to : "+ tabledata.get("MilitaryStatus2"));
				logger.log(LogStatus.PASS, "Duty Status is changed Successfully", image);
			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Duty Status is not changed ");
				logger.log(LogStatus.FAIL, "Duty Status is not changed ", image);
			}
			
		/*	*************************************
			*************************************************************
			***********************************************************/
			
			
			//************************Changing Active to Not in Military*****************************

			AccDetails.clk_editAcc_btn();
			logger.log(LogStatus.PASS, "Clicked on Edit Account Details successfully");

			// Verify Edit Account Details Modal window displayed
			if (editAccountDetails.isEditAccDetailsPopUpDisplayed()) {

				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed ");
				logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed", image);
			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed ");
				logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed", image);
			}

			editAccountDetails.clk_MilitaryStatusRadioBtn();
			logger.log(LogStatus.INFO, "Clicked on Military Status Button");

			editAccountDetails.clk_YesRadioBtn();


			editAccountDetails.clk_LaunchBtn();


		
			Thread.sleep(3000);

			Thread.sleep(3000);

			militaryStatus.clk_DutyDropDown();
			logger.log(LogStatus.INFO, "Clicked on Duty Type Drop Down");
			Thread.sleep(2000);
			
            try
       {  
                   
            	WebElement webelement = driver.findElement(By.xpath("//div[@id='Form_MilitaryTypeId-list']//div[@class='ps-scrollbar-y-rail']/div[@class='ps-scrollbar-y']"));
           Actions dragger = new Actions(driver);
           dragger.moveToElement(webelement).clickAndHold().moveByOffset(0, 300).release().build().perform();
          Thread.sleep(5000);    
                                         
            }
            catch (Exception e)
            {
                e.printStackTrace();}
                 
                 Thread.sleep(3000);
                // driver.findElement(By.xpath("//ul[@id='Form_MilitaryTypeId_listbox']/li[text()='Not in Military']")).click();
             	driver.findElement(By.xpath("//div[@id='Form_MilitaryTypeId-list']//ul[@id='Form_MilitaryTypeId_listbox']/li[text()='"+tabledata.get("MilitaryStatus3")+"']")).click();
    			Thread.sleep(5000);
		

			logger.log(LogStatus.INFO, "Selected Duty Type is: " + tabledata.get("MilitaryStatus3"));

			updateMilitaryStatus(driver, tabledata.get("MilitaryStatus3"), tabledata.get("UserName"));
			Thread.sleep(8000);
			
			String status_NotInMil=militaryStatus.getDutyStatus();
			
			cust.click_AccountActivities();
			Thread.sleep(15000);
			String notINMiltary=driver.findElement(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Military Status']/following-sibling::td[contains(text(),'Military status removed by')]/preceding-sibling::td[@class='k-hierarchy-cell']/a")).getText();
			Thread.sleep(3000);

			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "The Military status is updated to : "+tabledata.get("MilitaryStatus3"));
			logger.log(LogStatus.INFO, "img", image1);
			logger.log(LogStatus.INFO, "Account activity created successfully");
			 
               cust.click_custInfo_Tab();
               Thread.sleep(3000);
               
               militaryStatus.waitForElement(driver);

   			if (militaryStatus.getDutyStatus().contains("None")) {

   				Thread.sleep(2000);
   				String scpath = Generic_Class.takeScreenShotPath();
   				Reporter.log(scpath, true);
   				String image = logger.addScreenCapture(scpath);
   				logger.log(LogStatus.PASS, "Duty Status is changed Successfully from Active to : "+ tabledata.get("MilitaryStatus3"));
   				logger.log(LogStatus.PASS, "Duty Status is changed Successfully", image);
   			}

   			else {
   				Thread.sleep(2000);
   				String scpath = Generic_Class.takeScreenShotPath();
   				Reporter.log(scpath, true);
   				String image = logger.addScreenCapture(scpath);
   				logger.log(LogStatus.FAIL, "Duty Status is not changed ");
   				logger.log(LogStatus.FAIL, "Duty Status is not changed ", image);
   			}
   			
               

       /*        
               ***************************************
               ***********************************************
               *********************************************/

			//=================Changed Military Status from  Not in Military to Inactive====================


			AccDetails.clk_editAcc_btn();
			logger.log(LogStatus.PASS, "Clicked on Edit Account Details successfully");

			// Verify Edit Account Details Modal window displayed
			if (editAccountDetails.isEditAccDetailsPopUpDisplayed()) {

				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed ");
				logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed", image);
			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed ");
				logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed", image);
			}

			editAccountDetails.clk_MilitaryStatusRadioBtn();
			logger.log(LogStatus.INFO, "Clicked on Military Status Button");

			editAccountDetails.clk_YesRadioBtn();


			editAccountDetails.clk_LaunchBtn();


			Thread.sleep(3000);

			militaryStatus.clk_DutyDropDown();
			logger.log(LogStatus.INFO, "Clicked on Duty Type Drop Down");
			Thread.sleep(2000);
			
           try
       {  
                   
                  // WebElement webelement = driver.findElement(By.xpath("//div[@class='ps-scrollbar-y']"));
        	   WebElement webelement = driver.findElement(By.xpath("//div[@id='Form_MilitaryTypeId-list']//div[@class='ps-scrollbar-y-rail']/div[@class='ps-scrollbar-y']"));
           Actions dragger = new Actions(driver);
           dragger.moveToElement(webelement).clickAndHold().moveByOffset(0, -300).release().build().perform();
          Thread.sleep(5000);    
                                         
            }
            catch (Exception e)
            {
                e.printStackTrace();}
                 
                 Thread.sleep(6000);
             	driver.findElement(By.xpath("//div[@id='Form_MilitaryTypeId-list']//ul[@id='Form_MilitaryTypeId_listbox']/li[text()='"+tabledata.get("MilitaryStatus4")+"']")).click();
    			Thread.sleep(5000);
		

			//logger.log(LogStatus.INFO, "Selected Duty Type is: " + tabledata.get("MilitaryStatus4"));
			
			String dutyType_Inactive=driver.findElement(By.xpath("//span[@class='k-widget k-dropdown k-header js-military-type']//span[@class='k-input']")).getText();

			if (tabledata.get("MilitaryStatus4").contains
					(dutyType_Inactive)){
				logger.log(LogStatus.PASS, "Selected Duty Type is: " + dutyType_Inactive);
				scpath11 = Generic_Class.takeScreenShotPath();
				image11 = logger.addScreenCapture(scpath11);
				logger.log(LogStatus.INFO, "img", image11);

			}else{
				logger.log(LogStatus.FAIL, "Selected Duty Type is: " +dutyType_Inactive);
				scpath11 = Generic_Class.takeScreenShotPath();
				image11 = logger.addScreenCapture(scpath11);
				logger.log(LogStatus.INFO, "img", image11);
			}



			//JavascriptExecutor js = (JavascriptExecutor) driver;

			Thread.sleep(5000);

			/*//Enter all the data in the Active duty page
			militaryStatus.enterBirthDetails(tabledata.get("BirthDate"));

			militaryStatus.enterIdentificationDetails(tabledata.get("IdentificationNumber"));

			militaryStatus.enterActivityStartDate(tabledata.get("ActivityDutyStartDate"));

			String data = Generic_Class.get_RandmString();
			//int randomNum = 1400 + (int)(Math.random() * 1600); 
			String MilitaryUnit="Alpha"+data;
			militaryStatus.enterMilitaryUnit(MilitaryUnit);

			militaryStatus.select_MilitaryBranch(driver, tabledata.get("ExpectedBranch"));

			militaryStatus.enterSupervisorFirstName(tabledata.get("SupervisorFirstName"));

			militaryStatus.enterSupervisorLastName(tabledata.get("SupervisorLastName"));

			militaryStatus.enterSupervisorRank(tabledata.get("Rank"));

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			//need to check
			
			driver.findElement(By.xpath("//label[text()='Phone']/../following-sibling::div//span[text()='Select']")).click();
			Thread.sleep(4000);
			driver.findElement(By.xpath("//ul[@id='Form_MilitarySupervisorPhoneType_listbox']/li[4]")).click();
			 

			//militaryStatus.selectPhoneType(driver, "Cell ");

			militaryStatus.selectPhoneType(driver);

			militaryStatus.enterAreaCode(tabledata.get("AreaCode"));

			militaryStatus.enterExchangeCode(tabledata.get("Exchange"));

			militaryStatus.enterLineNumber(tabledata.get("LineNumber"));


			 scpath1 = Generic_Class.takeScreenShotPath();
			 image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.INFO,"All the military information is filled");
			logger.log(LogStatus.INFO, "img", image1);*/

			jse.executeScript("window.scrollBy(0,1000)", "");
			Thread.sleep(2000);
			
			updateMilitaryStatus(driver, tabledata.get("MilitaryStatus4"), tabledata.get("UserName"));
			Thread.sleep(8000);
			
			String status_Inactive=militaryStatus.getDutyStatus();
			
			Thread.sleep(8000);
	
			
			cust.click_AccountActivities();
			Thread.sleep(15000);
			driver.findElement(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Military Status']/following-sibling::td[contains(text(),'Military status added')]/preceding-sibling::td[@class='k-hierarchy-cell']/a")).click();
			Thread.sleep(3000);

			String duty_Status1=driver.findElement(By.xpath("//div[@class='activity-detail']//div/span[contains(text(),'Status')]/following-sibling::span")).getText();

			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "The Military status is updated to : "+duty_Status1);
			logger.log(LogStatus.INFO, "img", image1);
			logger.log(LogStatus.INFO, "Account activity created successfully");
			             
               cust.click_custInfo_Tab();
               Thread.sleep(3000);
			 
               cust.click_custInfo_Tab();
               Thread.sleep(3000);
               
               militaryStatus.waitForElement(driver);

   			if (militaryStatus.getDutyStatus().contains(tabledata.get("MilitaryStatus4"))) {

   				Thread.sleep(2000);
   				String scpath = Generic_Class.takeScreenShotPath();
   				Reporter.log(scpath, true);
   				String image = logger.addScreenCapture(scpath);
   				logger.log(LogStatus.PASS, "Duty Status is changed Successfully from Not in Miltary to : "+ tabledata.get("MilitaryStatus4"));
   				logger.log(LogStatus.PASS, "Duty Status is changed Successfully", image);
   			}

   			else {
   				Thread.sleep(2000);
   				String scpath = Generic_Class.takeScreenShotPath();
   				Reporter.log(scpath, true);
   				String image = logger.addScreenCapture(scpath);
   				logger.log(LogStatus.FAIL, "Duty Status is not changed ");
   				logger.log(LogStatus.FAIL, "Duty Status is not changed ", image);
   			}
   			
   			

   	       /*        
   	               ***************************************
   	               ***********************************************
   	               *********************************************/
   			
			//==============================Inactive to Active====================================

		Thread.sleep(5000);
			AccDetails.clk_editAcc_btn();
			logger.log(LogStatus.PASS, "Clicked on Edit Account Details successfully");

			// Verify Edit Account Details Modal window displayed
			if (editAccountDetails.isEditAccDetailsPopUpDisplayed()) {

				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed ");
				logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed", image);
			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed ");
				logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed", image);
			}

			editAccountDetails.clk_MilitaryStatusRadioBtn();
			logger.log(LogStatus.INFO, "Clicked on Military Status Button");

			editAccountDetails.clk_YesRadioBtn();


			editAccountDetails.clk_LaunchBtn();


			militaryStatus.clk_DutyDropDown();
			logger.log(LogStatus.INFO, "Clicked on Duty Type Drop Down");
			Thread.sleep(2000);
			
            try
       {  
                   
                   //WebElement webelement = driver.findElement(By.xpath("//div[@class='ps-scrollbar-y']"));
            	WebElement webelement = driver.findElement(By.xpath("//div[@id='Form_MilitaryTypeId-list']//div[@class='ps-scrollbar-y-rail']/div[@class='ps-scrollbar-y']"));
           Actions dragger = new Actions(driver);
           dragger.moveToElement(webelement).clickAndHold().moveByOffset(0, -300).release().build().perform();
          Thread.sleep(5000);    
                                         
            }
            catch (Exception e)
            {
                e.printStackTrace();}
                 
                 Thread.sleep(6000);
             	driver.findElement(By.xpath("//div[@id='Form_MilitaryTypeId-list']//ul[@id='Form_MilitaryTypeId_listbox']/li[text()='"+tabledata.get("MilitaryStatus2")+"']")).click();
    			Thread.sleep(5000);
		

			logger.log(LogStatus.INFO, "Selected Duty Type is: " + tabledata.get("MilitaryStatus2"));

			updateMilitaryStatus(driver, tabledata.get("MilitaryStatus2"), tabledata.get("UserName"));
			Thread.sleep(8000);
			
			String status_Active=militaryStatus.getDutyStatus();
			
			cust.click_AccountActivities();
			Thread.sleep(15000);
			driver.findElement(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Military Status']/following-sibling::td[contains(text(),'Military status added')]/preceding-sibling::td[@class='k-hierarchy-cell']/a")).click();
			Thread.sleep(3000);

			String duty_Status2=driver.findElement(By.xpath("//div[@class='activity-detail']//div/span[contains(text(),'Status')]/following-sibling::span")).getText();

			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "The Military status is updated to : "+duty_Status2);
			logger.log(LogStatus.INFO, "img", image1);
			logger.log(LogStatus.INFO, "Account activity created successfully");
			             
          
			 
               cust.click_custInfo_Tab();
               Thread.sleep(3000);
               
               militaryStatus.waitForElement(driver);

   			if (militaryStatus.getDutyStatus().contains(tabledata.get("MilitaryStatus2"))) {

   				Thread.sleep(2000);
   				String scpath = Generic_Class.takeScreenShotPath();
   				Reporter.log(scpath, true);
   				String image = logger.addScreenCapture(scpath);
   				logger.log(LogStatus.PASS, "Duty Status is changed Successfully from Inactive to : "+ tabledata.get("MilitaryStatus2"));
   				logger.log(LogStatus.PASS, "Duty Status is changed Successfully", image);
   			}

   			else {
   				Thread.sleep(2000);
   				String scpath = Generic_Class.takeScreenShotPath();
   				Reporter.log(scpath, true);
   				String image = logger.addScreenCapture(scpath);
   				logger.log(LogStatus.FAIL, "Duty Status is not changed ");
   				logger.log(LogStatus.FAIL, "Duty Status is not changed ", image);
   			}
   			
               

       /*        
               ***************************************
               ***********************************************
               *********************************************/
			
		
			//=================================== Change Military Status From Active to Inactive ==============


			AccDetails.clk_editAcc_btn();
			logger.log(LogStatus.PASS, "Clicked on Edit Account Details successfully");

			// Verify Edit Account Details Modal window displayed
			if (editAccountDetails.isEditAccDetailsPopUpDisplayed()) {

				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed ");
				logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed", image);
			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed ");
				logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed", image);
			}

			editAccountDetails.clk_MilitaryStatusRadioBtn();
			logger.log(LogStatus.INFO, "Clicked on Military Status Button");

			editAccountDetails.clk_YesRadioBtn();


			editAccountDetails.clk_LaunchBtn();



			militaryStatus.clk_DutyDropDown();
			logger.log(LogStatus.INFO, "Clicked on Duty Type Drop Down");
			Thread.sleep(2000);
			
            try
       {  
                   
                 //  WebElement webelement = driver.findElement(By.xpath("//div[@class='ps-scrollbar-y']"));
            	WebElement webelement = driver.findElement(By.xpath("//div[@id='Form_MilitaryTypeId-list']//div[@class='ps-scrollbar-y-rail']/div[@class='ps-scrollbar-y']"));
           Actions dragger = new Actions(driver);
           dragger.moveToElement(webelement).clickAndHold().moveByOffset(0, -300).release().build().perform();
          Thread.sleep(5000);    
                                         
            }
            catch (Exception e)
            {
                e.printStackTrace();}
                 
                 Thread.sleep(6000);
             	driver.findElement(By.xpath("//div[@id='Form_MilitaryTypeId-list']//ul[@id='Form_MilitaryTypeId_listbox']/li[text()='"+tabledata.get("MilitaryStatus4")+"']")).click();
    			Thread.sleep(5000);
		

			logger.log(LogStatus.INFO, "Selected Duty Type is: " + tabledata.get("MilitaryStatus4"));

			updateMilitaryStatus(driver, tabledata.get("MilitaryStatus4"), tabledata.get("UserName"));
			Thread.sleep(8000);
			
			String status_inActive=militaryStatus.getDutyStatus();
			
			cust.click_AccountActivities();
			Thread.sleep(15000);
			driver.findElement(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Military Status']/following-sibling::td[contains(text(),'Military status added')]/preceding-sibling::td[@class='k-hierarchy-cell']/a")).click();
			Thread.sleep(3000);

			String duty_Status3=driver.findElement(By.xpath("//div[@class='activity-detail']//div/span[contains(text(),'Status')]/following-sibling::span")).getText();

			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "The Military status is updated to : "+duty_Status3);
			logger.log(LogStatus.INFO, "img", image1);
			logger.log(LogStatus.INFO, "Account activity created successfully");
			             
         			 
               cust.click_custInfo_Tab();
               Thread.sleep(3000);
               
               militaryStatus.waitForElement(driver);

   			if (militaryStatus.getDutyStatus().contains(tabledata.get("MilitaryStatus4"))) {

   				Thread.sleep(2000);
   				String scpath = Generic_Class.takeScreenShotPath();
   				Reporter.log(scpath, true);
   				String image = logger.addScreenCapture(scpath);
   				logger.log(LogStatus.PASS, "Duty Status is changed Successfully from Active to : "+ tabledata.get("MilitaryStatus4"));
   				logger.log(LogStatus.PASS, "Duty Status is changed Successfully", image);
   			}

   			else {
   				Thread.sleep(2000);
   				String scpath = Generic_Class.takeScreenShotPath();
   				Reporter.log(scpath, true);
   				String image = logger.addScreenCapture(scpath);
   				logger.log(LogStatus.FAIL, "Duty Status is not changed ");
   				logger.log(LogStatus.FAIL, "Duty Status is not changed ", image);
   			}
   			
               

       /*        
               ***************************************
               ***********************************************
               *********************************************/


//===============================Changing Military status from Inactive to  Not in Military=========================================
   			
   			
			AccDetails.clk_editAcc_btn();
			logger.log(LogStatus.PASS, "Clicked on Edit Account Details successfully");

			// Verify Edit Account Details Modal window displayed
			if (editAccountDetails.isEditAccDetailsPopUpDisplayed()) {

				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed ");
				logger.log(LogStatus.PASS, "Edit Account Details Modal window displayed", image);
			}

			else {
				Thread.sleep(2000);
				String scpath = Generic_Class.takeScreenShotPath();
				Reporter.log(scpath, true);
				String image = logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed ");
				logger.log(LogStatus.FAIL, "Edit Account Details Modal window displayed", image);
			}

			editAccountDetails.clk_MilitaryStatusRadioBtn();
			logger.log(LogStatus.INFO, "Clicked on Military Status Button");

			editAccountDetails.clk_YesRadioBtn();

			editAccountDetails.clk_LaunchBtn();




			militaryStatus.clk_DutyDropDown();
			logger.log(LogStatus.INFO, "Clicked on Duty Type Drop Down");
			Thread.sleep(2000);
			
            try
       {  
                   
                   //WebElement webelement = driver.findElement(By.xpath("//div[@class='ps-scrollbar-y']"));
            	WebElement webelement = driver.findElement(By.xpath("//div[@id='Form_MilitaryTypeId-list']//div[@class='ps-scrollbar-y-rail']/div[@class='ps-scrollbar-y']"));
           Actions dragger = new Actions(driver);
           dragger.moveToElement(webelement).clickAndHold().moveByOffset(0, 300).release().build().perform();
          Thread.sleep(5000);    
                                         
            }
            catch (Exception e)
            {
                e.printStackTrace();}
                 
                 Thread.sleep(6000);
             	driver.findElement(By.xpath("//div[@id='Form_MilitaryTypeId-list']//ul[@id='Form_MilitaryTypeId_listbox']/li[text()='"+tabledata.get("MilitaryStatus3")+"']")).click();
    			Thread.sleep(5000);
		

			logger.log(LogStatus.INFO, "Selected Duty Type is: " + tabledata.get("MilitaryStatus3"));

			updateMilitaryStatus(driver, tabledata.get("MilitaryStatus3"), tabledata.get("UserName"));
			Thread.sleep(8000);
			
			String status_notMil=militaryStatus.getDutyStatus();
			
			cust.click_AccountActivities();
			Thread.sleep(15000);
			String notMiltary=driver.findElement(By.xpath("//div[@id='activities-grid']//table/tbody//tr/td[text()='"+strTodaysDate+"']/following-sibling::td[text()='Military Status']/following-sibling::td[contains(text(),'Military status removed by')]/preceding-sibling::td[@class='k-hierarchy-cell']/a")).getText();
			Thread.sleep(3000);

			scpath1 = Generic_Class.takeScreenShotPath();
			image1 = logger.addScreenCapture(scpath1);
			logger.log(LogStatus.PASS, "The Military status is updated to : "+tabledata.get("MilitaryStatus3"));
			logger.log(LogStatus.INFO, "img", image1);
			logger.log(LogStatus.INFO, "Account activity created successfully");
			 
         			 
               cust.click_custInfo_Tab();
               Thread.sleep(3000);
               
               militaryStatus.waitForElement(driver);

   			if (militaryStatus.getDutyStatus().contains("None")) {

   				Thread.sleep(2000);
   				String scpath = Generic_Class.takeScreenShotPath();
   				Reporter.log(scpath, true);
   				String image = logger.addScreenCapture(scpath);
   				logger.log(LogStatus.PASS, "Duty Status is changed Successfully from Inactive to : "+ tabledata.get("MilitaryStatus3"));
   				logger.log(LogStatus.PASS, "Duty Status is changed Successfully", image);
   			}

   			else {
   				Thread.sleep(2000);
   				String scpath = Generic_Class.takeScreenShotPath();
   				Reporter.log(scpath, true);
   				String image = logger.addScreenCapture(scpath);
   				logger.log(LogStatus.FAIL, "Duty Status is not changed ");
   				logger.log(LogStatus.FAIL, "Duty Status is not changed ", image);
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

	public void updateMilitaryStatus(WebDriver driver, String input, String employeeId) throws InterruptedException, Exception {

		MilitaryStatus militaryStatus = new MilitaryStatus(driver);

		String scpath11 = Generic_Class.takeScreenShotPath();
		String image11 = logger.addScreenCapture(scpath11);
		logger.log(LogStatus.PASS, "img", image11);
		/*Thread.sleep(6000);

		militaryStatus.clk_DutyDropDown();
		Thread.sleep(4000);

		logger.log(LogStatus.INFO, "Clicked on Duty Type Drop Down");

		// Select Different Types of Military Active End Duty Date
		militaryStatus.getElement(input, driver);
		Thread.sleep(3000);
		logger.log(LogStatus.INFO, "Selected Duty Type is: " + input);*/

		try {
			militaryStatus.enterActiveDutyEndDate();
			Thread.sleep(3000);

		} catch (Exception e) {
			logger.log(LogStatus.INFO, "Date is not required for this Status, So continuing!!");
		}
		
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,1000)", "");
		Thread.sleep(2000);

		militaryStatus.ClickOnConfirmButton(driver);
		logger.log(LogStatus.INFO, "Clicked on Confirm Button");

		Thread.sleep(5000);

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("Current availble tabs are: " + tabs.size());
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
		driver.switchTo().window(tabs.get(1));
		logger.log(LogStatus.INFO, "Switching to customer Screen");

		Thread.sleep(2000);
		
		String scpath1 = Generic_Class.takeScreenShotPath();
		String image1 = logger.addScreenCapture(scpath1);
		logger.log(LogStatus.INFO, "img", image1);
		


		militaryStatus.WaitUntilVisibilityOfSignaturePad();


		militaryStatus.drawSignature(driver);

		 scpath1 = Generic_Class.takeScreenShotPath();
		 image1 = logger.addScreenCapture(scpath1);
		logger.log(LogStatus.INFO, "img", image1);

		militaryStatus.ClickOnAcceptButton();
		logger.log(LogStatus.INFO, "Clicked on Accept button in the CFS Screen successfully");
		

		driver.switchTo().window(tabs.get(0));
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);


		Thread.sleep(2000);

		militaryStatus.clickOnApproveButton();
		Thread.sleep(3000);
		logger.log(LogStatus.INFO, "Clicked on Approve button");

		scpath1 = Generic_Class.takeScreenShotPath();
		image1 = logger.addScreenCapture(scpath1);
		logger.log(LogStatus.INFO, "img", image1);

		militaryStatus.ClickOnSubmitButton();
		Thread.sleep(3000);
		logger.log(LogStatus.INFO, "Clicked on Save button");

		militaryStatus.EnterNotes();

		militaryStatus.EnterEmployeeNumber(employeeId);
		logger.log(LogStatus.INFO, "Entered Employee Number");

		scpath1 = Generic_Class.takeScreenShotPath();
		image1 = logger.addScreenCapture(scpath1);
		logger.log(LogStatus.INFO, "img", image1);

		militaryStatus.ClickOnContinue();
		logger.log(LogStatus.INFO, "Clicked on Continue Button");
		Thread.sleep(5000);
		
		try {
			scpath1 = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath1, true);
			image1 = logger.addScreenCapture(scpath1);

			logger.log(LogStatus.INFO, "img", image1);
			driver.findElement(By.xpath("//a[contains(text(),'OK')]")).click();
			Thread.sleep(20000);
		} catch (Exception e) {
			scpath1 = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath1, true);
			image1 = logger.addScreenCapture(scpath1);

			logger.log(LogStatus.INFO, "img", image1);
		} 
		
		/*militaryStatus.waitForElement(driver);

		if (militaryStatus.getDutyStatus().contains(input)) {

			Thread.sleep(2000);
			String scpath = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath, true);
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Duty Status is changed Successfully as :" + input);
			logger.log(LogStatus.PASS, "Duty Status is changed Successfully", image);
		}

		else {
			Thread.sleep(2000);
			String scpath = Generic_Class.takeScreenShotPath();
			Reporter.log(scpath, true);
			String image = logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Duty Status is not changed Successfully ");
			logger.log(LogStatus.FAIL, "Duty Status is not changed Successfully", image);
		}
		*/


		Thread.sleep(10000);
		scpath1 = Generic_Class.takeScreenShotPath();
		image1 = logger.addScreenCapture(scpath1);
		logger.log(LogStatus.INFO, "img", image1);

	
		


	}

	@AfterMethod
	public void afterMethod() {
		Reporter.log(resultFlag, true);

		if (resultFlag.equals("pass")) {
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_Military_Status_Update_Individual",
					"Status", "Pass");

		} else if (resultFlag.equals("fail")) {

			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_Military_Status_Update_Individual",
					"Status", "Fail");
		} else {
			Excel.setCellValBasedOnTcname(path, "CustomerEdit", "CustomerEdit_Military_Status_Update_Individual",
					"Status", "Skip");
		}
		extent.endTest(logger);
		extent.flush();
	}

}
