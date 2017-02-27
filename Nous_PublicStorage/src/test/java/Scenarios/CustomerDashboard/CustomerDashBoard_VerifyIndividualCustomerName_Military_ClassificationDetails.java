package Scenarios.CustomerDashboard;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import Pages.CustDashboardPages.Acc_CustomerInfoPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class CustomerDashBoard_VerifyIndividualCustomerName_Military_ClassificationDetails extends Browser_Factory {

	String path = Generic_Class.getPropertyValue("Excelpath");
	public ExtentTest logger;
	String resultFlag="pass";
	@DataProvider
	public Object[][] getDta() 
	{
		return Excel.getCellValue_inlist(path, "CustomerDashBoard","CustomerDashBoard","CustomerDashBoard_VerifyIndividualCustomerName_Military_ClassificationDetails");
	}


	@Test(dataProvider="getDta")	
	public void CustomerDashBoard_VerifyIndividualCustomerName_Military_ClassificationDetails(Hashtable<String, String> tabledata) throws Exception 
	{
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerDashBoard").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the  CustomerDashBoard_VerifyIndividualCustomerName_Military_ClassificationDetails test");
		}
		try{

			logger=extent.startTest("CustomerDashBoard_VerifyIndividualCustomerName_Military_ClassificationDetails", "Verify individual Customer name, Military and Classification details");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			Thread.sleep(2000);
			String IpAddress=Generic_Class.getIPAddress();

			JavascriptExecutor jse = (JavascriptExecutor)driver;
			LoginPage loginPage = new LoginPage(driver);
			String sitenumber=loginPage.get_SiteNumber();
			Thread.sleep(2000);
			loginPage.login(tabledata.get("UserName"), tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Click on Login button successfully");
			Thread.sleep(2000);
			
			
			//=================Handling customer facing device========================
			
			Thread.sleep(5000);
			Dashboard_BifrostHostPopUp Bifrostpop = new Dashboard_BifrostHostPopUp(driver);
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
			driver.get(Generic_Class.getPropertyValue("CustomerScreenPath"));

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
			
			
			//=====================================================================
			Thread.sleep(4000);
			
			DM_HomePage dmhome = new DM_HomePage(driver);
			
			if(dmhome.is_DMDashBoardTitle()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "DM DashBoard is displayed sucessfully");
				logger.log(LogStatus.INFO, "img",image);
				resultFlag="pass";
			}
			else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "DM DashBoard is not displayed");
				logger.log(LogStatus.INFO, "img",image);

			}
			
			String qry = "select top 1 vw.accountnumber, vw.FirstName, vw.lastname, t.name, 'Military', cc.classname from vw_unitdetails vw "+
					"join customer c with(nolock) on c.customerid = vw.customerid "+
					"join customerclass cc with(nolock) on cc.customerclassid = c.customerclassid "+
					"join customermilitaryinfo cmi with(nolock) on cmi.customerid = vw.customerid "+
					"join type t with(nolock) on t.typeid = c.customertypeid "+
					"where vw.vacatedate is null "+
					"and vw.customertypeid = 90 "+
					"and cmi.militarytypeid = 1702 ";
			
			ArrayList<String> list1 = DataBase_JDBC.executeSQLQuery_List(qry);
			
			
			logger.log(LogStatus.INFO, "Searching Individual customer in existing customer module in DM DashBoard");
			String AccountNumber=list1.get(0);
			logger.log(LogStatus.INFO, "Fetching individual customer account number from database----->"+AccountNumber);
			Thread.sleep(2000);
			
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(2000);
			dmhome.clk_advancedSearchLnk();
			logger.log(LogStatus.INFO, "Clicked on Advance search link");
			Thread.sleep(5000);
			
			Thread.sleep(5000);
			Advance_Search advSearch= new Advance_Search(driver);

			advSearch.enterAccNum(AccountNumber);
			Thread.sleep(3000);
			String snap1=Generic_Class.takeScreenShotPath();
			String imr1=logger.addScreenCapture(snap1);
			logger.log(LogStatus.INFO, "img",imr1);
			logger.log(LogStatus.INFO, "Entered Account number :" +AccountNumber);

			jse.executeScript("window.scrollBy(0,-2000)", "");
			Thread.sleep(5000);

			advSearch.clickSearchAccbtn();
			logger.log(LogStatus.INFO, "Clicked on Search button successfully");	
			
			Thread.sleep(10000);	
			Cust_AccDetailsPage cust= new Cust_AccDetailsPage(driver);
			Thread.sleep(4000);
			if(cust.isCustdbTitleDisplayed()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Customer Dashboard is displayed successfully");
				logger.log(LogStatus.INFO, "img",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer Dashboard is not displayed ");
				logger.log(LogStatus.INFO, "img ",image);
			}

			Thread.sleep(2000);

			logger.log(LogStatus.INFO, "Verifying Customer Information on the Customer Dashboard ");
			if(cust.getcustCurrAccno().equals(AccountNumber)){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Account number from the dashboard " +cust.getcustCurrAccno()+ " are matched with the database : "+AccountNumber);
				logger.log(LogStatus.INFO, "img",image);


			}else{

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Account number from the dashboard " +cust.getcustCurrAccno()+ " are not matched with the database : "+AccountNumber);
				logger.log(LogStatus.INFO, "img",image);
			}

			if(cust.isSpaceNumDisplayed()){
				logger.log(LogStatus.INFO, "Space number is displayed on the customer dashboard--->"+cust.getCustSpaceNum());
			}else{
				logger.log(LogStatus.INFO, "Multiple space number are displaying in the customer dashboard");
			}

			String str=driver.findElement(By.xpath("//div[@id='customerDashboard']//div//h1[@class='customer-name bold']")).getText().trim();
			String[] splitStr = str.split("\\s+");
			int sizearray = splitStr.length;
			String firstName=splitStr[0];
			String lastName=splitStr[sizearray-1];
			Thread.sleep(2000);
			String custFirstName=list1.get(1);
			String custLastName=list1.get(2);
			
			if(custFirstName.contains(firstName)){
				
				logger.log(LogStatus.PASS, "Customer first name from database--->"+custFirstName+"   Customer first name from UI--->"+firstName +" are matching");

			}else{

				logger.log(LogStatus.FAIL, "Customer first name from Database and Customer first name in the customer dashboard are not matching");
			}

			if(custLastName.contains(lastName)){
				
				logger.log(LogStatus.PASS, "Customer last name from database--->"+custLastName+"  Customer last name from UI--->"+lastName+" are matching");
			}else{

				logger.log(LogStatus.FAIL, "Customer last name from Database and Customer last name in the customer dashboard are not matching");
			}
			

			logger.log(LogStatus.INFO, "Verifying type of customer against database");
			String custType=list1.get(3);
			

			if(cust.getTypeOfCustomer().contains(custType)){

				logger.log(LogStatus.PASS, "Type of customer from database-->"+custType+"  Type of customer from Customer dashboard---> "+cust.getTypeOfCustomer()+"  are matching");
				String snap3=Generic_Class.takeScreenShotPath();
				String imr3=logger.addScreenCapture(snap3);
				logger.log(LogStatus.INFO, "img",imr3);

			}else{
				logger.log(LogStatus.FAIL, "Type of customer from database-->"+custType+"  Type of customer from Customer dashboard---> "+cust.getTypeOfCustomer()+"  are not matching");
			}
			
			String military = list1.get(4);
			
			if(cust.getTypeOfCustomer().contains(military)){

				logger.log(LogStatus.PASS, "Type of customer from database-->"+military+"  Type of customer from Customer dashboard---> "+cust.getTypeOfCustomer()+"  are matching");

			}else{
				logger.log(LogStatus.FAIL, "Type of customer from database-->"+military+"  Type of customer from Customer dashboard---> "+cust.getTypeOfCustomer()+"  are not matching");
			}
			
			

			logger.log(LogStatus.INFO, "Verifying the Customer Classification level against database");
			String custLevel=list1.get(5);
			

			if(cust.getCustomerLevel().contains(custLevel)){
				
				logger.log(LogStatus.PASS, "Customer grade level from database-->"+custLevel+"   customer grade level from UI-->"+cust.getCustomerLevel()+"  are matching");
			}else{

				logger.log(LogStatus.FAIL, "Customer grade level from database-->"+custLevel+"   customer grade level from UI-->"+cust.getCustomerLevel()+"  are not matching");

			}

		/*	Thread.sleep(2000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(4000);

			Acc_CustomerInfoPage custpage=new Acc_CustomerInfoPage(driver);
			if(custpage.get_MilitaryStausText().contains(tabledata.get("status"))){
				
			    logger.log(LogStatus.PASS, "Military status is Active for the customer in customer dashboard");
				String snap2=Generic_Class.takeScreenShotPath();
				String imr2=logger.addScreenCapture(snap2);
				logger.log(LogStatus.INFO, "img",imr2);

			}else{

				logger.log(LogStatus.INFO, "Military status is not Active for the customer");
			}*/

		}catch(Exception e){
			resultFlag="fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "------------- page is not displayed",image);
			e.printStackTrace();
		}

	}

	@AfterMethod
	public void afterMethod(){
		Reporter.log(resultFlag,true);
		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashBoard_VerifyIndividualCustomerName_Military_ClassificationDetails" , "Status", "Pass");
		}else if (resultFlag.equals("fail")){
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashBoard_VerifyIndividualCustomerName_Military_ClassificationDetails" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerDashBoard","CustomerDashBoard_VerifyIndividualCustomerName_Military_ClassificationDetails" , "Status", "Skip");
		}
		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);
	}
}
