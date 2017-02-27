package Scenarios.CustomerSearch;

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
import Pages.AdvSearchPages.SearchingPopup;
import Pages.CustDashboardPages.Acc_CustomerInfoPage;
import Pages.CustDashboardPages.EmergencyContact_EmployeeIdPage;
import Pages.CustInfoPages.Cust_CustomerInfoPage;
import Pages.CustInfoPages.Cust_EditAccountDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class AdvancedSearch_IdNum extends Browser_Factory{

	String path="./src/main/resources/Resources/PS_TestData.xlsx";
	public ExtentTest logger;
	String resultFlag="pass";

	@DataProvider
	public Object[][] getCustomerSearchData() 
	{
		return Excel.getCellValue_inlist(path, "CustomerSearch","CustomerSearch",this.getClass().getSimpleName());
	}

	@Test(dataProvider="getCustomerSearchData")	
	public void CustomerSearch_AdvancedSearch_IdNum(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerSearch").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}


		try{

			//Login to PS Application
			logger=extent.startTest(this.getClass().getSimpleName(),"Advanced Search --ID Num");
			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "User logged in successfully as PM");
			String scpath, image;

			Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);

			String biforstNum=Bifrostpop.getBiforstNo();

			 //driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,"t");
		
	        Robot robot = new Robot();
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_T);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_T); 
	        
	        Thread.sleep(5000);
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			Thread.sleep(15000);
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

			Thread.sleep(5000);
			driver.switchTo().window(tabs.get(0));
			 // driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL,Keys.PAGE_DOWN);
	        robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_PAGE_DOWN);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
	        
	        Thread.sleep(10000);
			driver.findElement(By.xpath("//div[@id='cfsConnectionDialog']//div[@class='after-connected padding-top']//p/input[@class='psbutton-low-priority']")).click();
			Thread.sleep(5000);

			PM_Homepage pmhomepage = new PM_Homepage(driver);
			pmhomepage.clk_AdvSearchLnk();
			Thread.sleep(10000);

			Advance_Search advSearch= new Advance_Search(driver);
			advSearch.enterAccNum(tabledata.get("AccountNumber"));
			JavascriptExecutor js = (JavascriptExecutor)driver; 
			js.executeScript("window.scrollBy(2000,0)", "");
			advSearch.clickButton();
			Thread.sleep(40000);

			Acc_CustomerInfoPage custInfo = new Acc_CustomerInfoPage(driver);
			String getAcc = custInfo.getAccNum();
			if(getAcc.equals(tabledata.get("AccountNumber"))){

				logger.log(LogStatus.PASS, "Navigated to Customer Dashboard");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				
				custInfo.click_EditAccountDetails();
				Thread.sleep(5000);
				
				Cust_EditAccountDetailsPage editAcc = new Cust_EditAccountDetailsPage(driver);
				editAcc.clickCustInfoRadioBtn();
				Thread.sleep(2000);
				editAcc.clickYesRadioBtn();
				Thread.sleep(1000);
				editAcc.clickLaunchBtn();
				Thread.sleep(5000);
				
				logger.log(LogStatus.PASS, "Navigated to Customer Information Screen");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				
				Cust_CustomerInfoPage info = new Cust_CustomerInfoPage(driver);
				String license = info.getLicense();
				if(license.equals("A1259871")){
					license = "A1246835";
				}else{
					license = "A1259871";
				}
				info.enterLicenseNumber(license);
				
				logger.log(LogStatus.INFO, "Changed the ID Num");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				
				js = (JavascriptExecutor)driver; 
				js.executeScript("window.scrollBy(0,3000)", "");
				
				info.clk_verifyButton();
				Thread.sleep(5000);
				
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
				empId.click_ContinueBtn();
				Thread.sleep(15000);
				
				//Verifying the changes done
				custInfo = new Acc_CustomerInfoPage(driver);
				String idNum = custInfo.getIdentificationId();
				if(idNum.contains("****"+license.substring(4))){
					
					logger.log(LogStatus.PASS, "ID Num updated successfully to "+license);
					scpath=Generic_Class.takeScreenShotPath();
					image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "Image",image);
					
					custInfo.click_BackToDashboard();
					Thread.sleep(10000);
					
					pmhomepage.clk_AdvSearchLnk();
					Thread.sleep(10000);
					
					advSearch= new Advance_Search(driver);
					advSearch.search_All();
					Thread.sleep(3000);
					advSearch.clickStatusDropdown();
					Thread.sleep(2000);
					List<WebElement> statusTypes= driver.findElements(By.xpath("//ul[@id='SearchContract_SearchStatusID_listbox']/li[@class='k-item']"));
					for(WebElement type:statusTypes)
					{
						if(type.getText().trim().equalsIgnoreCase("All"))
						{
							type.click();
							break;
						}
					}
					Thread.sleep(4000);
					advSearch.enterIdNum(license);
					logger.log(LogStatus.INFO, "Entered the new ID number in search");
					scpath=Generic_Class.takeScreenShotPath();
					image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "Image",image);
					
					js = (JavascriptExecutor)driver; 
					js.executeScript("window.scrollBy(3000,0)", "");
					js.executeScript("window.scrollBy(0,5000)", "");
					advSearch.clickSearchbtn();
					Thread.sleep(45000);
					
					boolean found = false;
					List<WebElement> elements = driver.findElements(By.xpath("//table[@class='k-selectable']/tbody//tr/td[11]"));
					for(int i=0; i<elements.size(); i++){
						if(i>7){
							break;
						}
						if(elements.get(i).getText().equals(tabledata.get("AccountNumber"))){
							found = true;
							break;
						}
					}
					
					if(found){
						logger.log(LogStatus.PASS, "Id number search is successful");
						logger.log(LogStatus.PASS, "Id number search returned the correct account number, the one which we updated now");
						scpath=Generic_Class.takeScreenShotPath();
						image=logger.addScreenCapture(scpath);
						logger.log(LogStatus.INFO, "Image",image);
					}else{
						logger.log(LogStatus.FAIL, "Id number search is successful");
						logger.log(LogStatus.FAIL, "Id number search returned the correct account number, the one which we updated now");
						scpath=Generic_Class.takeScreenShotPath();
						image=logger.addScreenCapture(scpath);
						logger.log(LogStatus.INFO, "Image",image);
						if(resultFlag.equals("pass"))
							resultFlag="fail";
					}
				}else{
					scpath=Generic_Class.takeScreenShotPath();
					image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.FAIL, "ID Num not updated");
					logger.log(LogStatus.INFO, "Image",image);
					if(resultFlag.equals("pass"))
						resultFlag="fail";
				}
			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Navigated to a different customer dashboard");
				logger.log(LogStatus.INFO, "Image",image);
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
			Excel.setCellValBasedOnTcname(path, "CustomerSearch",this.getClass().getSimpleName() , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerSearch",this.getClass().getSimpleName() , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerSearch",this.getClass().getSimpleName() , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}

}
