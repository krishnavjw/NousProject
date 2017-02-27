package Scenarios.PropertyManagement;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import Pages.HomePages.DM_HomePage;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.HomePages.RMHomepage;
import Pages.PropertyManagementPages.PropertyManagementPage;
import Pages.PropertyManagementPages.SpaceStatusPage;
import Scenarios.Browser_Factory;

public class PropertyManagement_UpdateSpaceStatusForSpace_UnitOccupiedByPS extends Browser_Factory{
	
	public ExtentTest logger;
	String resultFlag="fail";
	String path=Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getLoginData() 
	{
		return Excel.getCellValue_inlist(path,"PropertyManagement","PropertyManagement",  this.getClass().getSimpleName());
	}

	@Test(dataProvider="getLoginData")
	public void propertyManagement_UpdateSpaceStatusForSpace_UnitOccupiedByPS(Hashtable<String, String> tabledata) throws InterruptedException 
	{
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("PropertyManagement").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		Thread.sleep(5000);

		try
		{

			//Login to the application as PM 
			logger=extent.startTest(this.getClass().getSimpleName(),"Property Management - Update Space Status for Space - Unit Occupied by Public Storage");
			testcaseName=tabledata.get("TestCases");
			Reporter.log("Test case started: " +testcaseName, true);

			String sqlQuery = "select top 1 ru.rentalunitnumber, ps.siteid from rentalunit ru with(nolock) "+
							  " join productsite ps with(nolock) on ru.productsiteid=ps.productsiteid  "+
							  " where ru.rentalstatustypeid=65 order by ps.siteid desc";
			ArrayList<String> result = DataBase_JDBC.executeSQLQuery_List(sqlQuery);
			String siteIdToBeSet = result.get(1);
			
			Thread.sleep(2000);
            String IpAddress=Generic_Class.getIPAddress();
            Thread.sleep(5000);
            String getSiteIDSetAlready="select * from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"+IpAddress+"' ";
            ArrayList<String> list1=DataBase_JDBC.executeSQLQuery_List(getSiteIDSetAlready);
            Thread.sleep(10000);
            String alreadySetSiteId=list1.get(2);

            Thread.sleep(5000);
            String updateQuery="Update siteparameter set paramvalue='' where paramcode='IP_COMPUTER_FIRST' and siteid='"+alreadySetSiteId+"' ";
            DataBase_JDBC.executeSQLQuery(updateQuery);

            Thread.sleep(5000);
            String updateSiteID="Update siteparameter set paramvalue='"+IpAddress+"' where paramcode='IP_COMPUTER_FIRST' and siteid='"+siteIdToBeSet+"' ";
            DataBase_JDBC.executeSQLQuery(updateSiteID);
            Thread.sleep(5000);

			

			LoginPage login= new LoginPage(driver);
			login.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "PM Logged in successfully");

			JavascriptExecutor jse = (JavascriptExecutor)driver;

			//connecting to customer device
			Thread.sleep(10000);
			/*Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Bifrostpop.clickContiDevice();*/
			driver.findElement(By.id("continueLink")).click();
			Thread.sleep(10000);
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);

			// Login into PM dashboard 
			PM_Homepage pm_home= new  PM_Homepage(driver);
			String siteNumber = pm_home.getLocation();
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(8000);
			pm_home.clickmanageProp();
			logger.log(LogStatus.INFO,"Clicked on Property Mgmt link");
			Thread.sleep(10000);

			PropertyManagementPage propmgmt= new PropertyManagementPage(driver);
			if(propmgmt.verify_PageTitle()){
				logger.log(LogStatus.PASS, "Property Management page displayed");
			}else{
				logger.log(LogStatus.FAIL, "Property Management page not displayed");
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
			Thread.sleep(4000);
			
			propmgmt.clickspaceStatus();
			logger.log(LogStatus.INFO,"Clicked on Space Status Link");
			Thread.sleep(10000);
			
			

			SpaceStatusPage spaceStatus = new SpaceStatusPage(driver);
			
			if(spaceStatus.isDisplayed_SpaceStatus_Header()){
				logger.log(LogStatus.PASS, "Space Status page displayed");
			}else{
				logger.log(LogStatus.PASS, "Space Status page not displayed");
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
			Thread.sleep(5000);
			
			
			String spaceNum = result.get(0);
	
			spaceStatus.clk_LockStatusDropdown();
			Thread.sleep(6000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
			
			ArrayList<String> uiValues = new ArrayList<String>();
			
			Actions dragger = new Actions(driver);
			WebElement draggablePartOfScrollbar = driver.findElement(By.xpath("(//ul[contains(@class,'k-list k-reset ps-container ps-active-y')])[1]//div[@class='ps-scrollbar-y']"));
			Thread.sleep(3000);
			List<WebElement> lockStatus = driver.findElements(By.xpath("(//ul[contains(@class,'k-list k-reset ps-container ps-active-y')])[1]//li[@class='k-item']"));
			String lkStatus="";
			int count=1;
			for(WebElement lockValues: lockStatus){	
				
				if(count>3){
					dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0, 40).release().build().perform();
					Thread.sleep(1000);
				}
				count++;
				lkStatus =lockValues.getText().trim();
				uiValues.add(lkStatus);
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
			
			boolean found = false;
			String[] expValues = {"Customer Requested Lock Cut", "DTM Lock Cut","The lock appears to have been tampered with","The unit had no lock on it","The unit was improperly locked","Unit locked in an open position"};
			for(int i=0; i<expValues.length; i++){
				found = false;
				for(int k=0; k<uiValues.size(); k++){
					if(expValues[i].equals(uiValues.get(k))){
						found = true;
						break;
					}
				}
				
				if(found){
					logger.log(LogStatus.PASS, "Lock Status: "+expValues[i]+" shown under Lock Status dropdown");
				}else{
					logger.log(LogStatus.FAIL, "Lock Status: "+expValues[i]+" not shown under Lock Status dropdown");
				}
			}
			
			

			spaceStatus.clk_MaintanenceStatusDropdown();
			Thread.sleep(6000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
			
			uiValues.clear();
			uiValues = new ArrayList<String>();
			
			WebElement draggablePartOfScrollbar1 = driver.findElement(By.xpath("(//ul[contains(@class,'k-list k-reset ps-container ps-active-y')])[2]//div[@class='ps-scrollbar-y']"));
			Thread.sleep(3000);
			count = 1;

			List<WebElement> maintanenceStatus = driver.findElements(By.xpath("(//ul[contains(@class,'k-list k-reset ps-container ps-active-y')])[2]//li[@class='k-item']"));
			String maintanenceStatusValue="";
			for(WebElement maintanenceValues: maintanenceStatus){
				
				if(count>3){
					dragger.moveToElement(draggablePartOfScrollbar1).clickAndHold().moveByOffset(0, 30).release().build().perform();
					Thread.sleep(1000);
				}
				count++;
				maintanenceStatusValue = maintanenceValues.getText().trim();
				uiValues.add(maintanenceStatusValue);

			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
			
			
			found = false;
			String[] expValuesmaint = {"Ceiling","Door Repair","Hasp","Walls", "Water Intrusion"};
			for(int i=0; i<expValuesmaint.length; i++){
				found = false;
				for(int k=0; k<uiValues.size(); k++){
					if(expValuesmaint[i].equals(uiValues.get(k))){
						found = true;
						break;
					}
				}
				
				if(found){
					logger.log(LogStatus.PASS, "Maintenance Status: "+expValuesmaint[i]+" shown under Maintenance Status dropdown");
				}else{
					logger.log(LogStatus.FAIL, "Maintenance Status: "+expValuesmaint[i]+" not shown under Maintenance Status dropdown");
				}
			}
			

			spaceStatus.enterSpaceNumber(spaceNum);
			Thread.sleep(3000);
			scpath=Generic_Class.takeScreenShotPath();
			logger.log(LogStatus.PASS, "Entered the Space Number : " +spaceNum);
			logger.log(LogStatus.INFO, "Screenshot Below: "+logger.addScreenCapture(scpath));
			Thread.sleep(5000);

			spaceStatus.clk_UpdateBtn();
			Thread.sleep(10000);
			
			if(driver.findElements(By.xpath("//div[@class='k-window-titlebar k-header']//span[text()='Update Status for Space "+spaceNum+"']")).size() != 0){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Update Status for Space "+spaceNum+" page is displayed");
				logger.log(LogStatus.INFO, "img",image);
			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Update Status for Space "+spaceNum+" page is not displayed");
				logger.log(LogStatus.INFO, "img",image);
			}
			
			

			if(driver.findElements(By.xpath("//div[@id='employeeNumberEntry']//div[text()='New Status:']/following-sibling::div//span[@class='k-input']")).size()!=0){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "New Status Dropdown is not displayed in Update Status for Space popup (PM User)");
				logger.log(LogStatus.INFO, "img",image);

			}else{

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "New Status Dropdown is displayed in Update Status for Space popup (PM User)");
				logger.log(LogStatus.INFO, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}

			driver.findElement(By.xpath("//div[@class='text-align-right']//a[contains(text(),'Cancel')]")).click();
			Thread.sleep(4000);
			
			if(spaceStatus.isDisplayed_SpaceStatus_Header()){
				logger.log(LogStatus.PASS, "Space Status page displayed");
			}else{
				logger.log(LogStatus.PASS, "Space Status page not displayed");
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			
			
			spaceStatus.clk_BackToDashboard_Btn();
			Thread.sleep(4000);
			if(pm_home.isFindAndLeaseButtonDisplayed()){
				logger.log(LogStatus.PASS, "PM Dashboard page is displayed");
			}else{
				logger.log(LogStatus.FAIL, "PM Dashboard page is not displayed");
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			/*
			 * Logout and login back to change according to site id set
			 * 
			 */

			Actions act = new Actions(driver);
			WebElement user = driver.findElement(By.xpath(("//div[@id='usernav']")));
			WebElement logoff1 = driver.findElement(By.xpath("//a[contains(text(),'Log Off')]"));

			act.moveToElement(user).build().perform();
			jse.executeScript("arguments[0].click();", logoff1);
			Thread.sleep(15000);

			WebElement logoff2 = driver.findElement(By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'Log Off')]"));
			jse.executeScript("arguments[0].click();", logoff2);
			Thread.sleep(8000);
			logger.log(LogStatus.INFO, "Logged off from the application");
			
			
			driver.navigate().refresh();

			//Fetching the DM Login & RM Login
			sqlQuery = "select EntityParentId as DivisionCode from  ws.vw_DraStructureModel where entityid = '"+siteNumber+"'";
			String divisionCode = DataBase_JDBC.executeSQLQuery(sqlQuery);
			sqlQuery = "select EmployeeNumber as DMLogin, SupervisorID as RMLogin from employee_hris where divisioncode='"+divisionCode+"' and JobTitle='AM'";
			ArrayList<String> logins = DataBase_JDBC.executeSQLQuery_List(sqlQuery);
			String dmLogin = logins.get(0);
			String rmLogin = logins.get(1);

			// DM Login 
			login.login(dmLogin, tabledata.get("Password_DM"));
			logger.log(LogStatus.INFO, "Logged in with DM login");
			Thread.sleep(10000);
			/*Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Bifrostpop.clickContiDevice();*/
			driver.findElement(By.id("continueLink")).click();
			Thread.sleep(10000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);

			DM_HomePage dmHome= new  DM_HomePage(driver);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(8000);

			dmHome.clickmanageProp();
			logger.log(LogStatus.INFO,"Clicked on Property Mgmt link");
			Thread.sleep(10000);

			propmgmt= new PropertyManagementPage(driver);
			if(propmgmt.verify_PageTitle()){
				logger.log(LogStatus.PASS, "Property Management page displayed");
			}else{
				logger.log(LogStatus.FAIL, "Property Management page not displayed");
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
			Thread.sleep(4000);

			propmgmt.clickspaceStatus();
			logger.log(LogStatus.INFO,"Clicked on Space Status Link");
			Thread.sleep(15000);
			propmgmt.select_PropNum(siteNumber);
			Thread.sleep(2000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
			Thread.sleep(4000);
			propmgmt.click_OkBtn();
			Thread.sleep(15000);


			spaceStatus = new SpaceStatusPage(driver);
			if(spaceStatus.isDisplayed_SpaceStatus_Header()){
				logger.log(LogStatus.PASS, "Space Status page displayed");
			}else{
				logger.log(LogStatus.PASS, "Space Status page not displayed");
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
			Thread.sleep(5000);

			spaceStatus.clk_LockStatusDropdown();
			Thread.sleep(6000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);

			uiValues.clear();
			uiValues = new ArrayList<String>();

			dragger = new Actions(driver);
			draggablePartOfScrollbar = driver.findElement(By.xpath("(//ul[contains(@class,'k-list k-reset ps-container ps-active-y')])[1]//div[@class='ps-scrollbar-y']"));
			Thread.sleep(3000);
			lockStatus.clear();
			lockStatus = driver.findElements(By.xpath("(//ul[contains(@class,'k-list k-reset ps-container ps-active-y')])[1]//li[@class='k-item']"));
			lkStatus="";
			count=1;
			for(WebElement lockValues: lockStatus){	

				if(count>3){
					dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0, 40).release().build().perform();
					Thread.sleep(1000);
				}
				count++;
				lkStatus =lockValues.getText().trim();
				uiValues.add(lkStatus);
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);

			found = false;
			for(int i=0; i<expValues.length; i++){
				found = false;
				for(int k=0; k<uiValues.size(); k++){
					if(expValues[i].equals(uiValues.get(k))){
						found = true;
						break;
					}
				}

				if(found){
					logger.log(LogStatus.PASS, "Lock Status: "+expValues[i]+" shown under Lock Status dropdown");
				}else{
					logger.log(LogStatus.FAIL, "Lock Status: "+expValues[i]+" not shown under Lock Status dropdown");
				}
			}



			spaceStatus.clk_MaintanenceStatusDropdown();
			Thread.sleep(6000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);

			uiValues.clear();
			uiValues = new ArrayList<String>();

			draggablePartOfScrollbar1 = driver.findElement(By.xpath("(//ul[contains(@class,'k-list k-reset ps-container ps-active-y')])[2]//div[@class='ps-scrollbar-y']"));
			Thread.sleep(3000);
			count = 1;
			maintanenceStatus.clear();
			maintanenceStatus = driver.findElements(By.xpath("(//ul[contains(@class,'k-list k-reset ps-container ps-active-y')])[2]//li[@class='k-item']"));
			maintanenceStatusValue="";
			for(WebElement maintanenceValues: maintanenceStatus){

				if(count>3){
					dragger.moveToElement(draggablePartOfScrollbar1).clickAndHold().moveByOffset(0, 30).release().build().perform();
					Thread.sleep(1000);
				}
				count++;
				maintanenceStatusValue = maintanenceValues.getText().trim();
				uiValues.add(maintanenceStatusValue);

			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);


			found = false;
			for(int i=0; i<expValuesmaint.length; i++){
				found = false;
				for(int k=0; k<uiValues.size(); k++){
					if(expValuesmaint[i].equals(uiValues.get(k))){
						found = true;
						break;
					}
				}

				if(found){
					logger.log(LogStatus.PASS, "Maintenance Status: "+expValuesmaint[i]+" shown under Maintenance Status dropdown");
				}else{
					logger.log(LogStatus.FAIL, "Maintenance Status: "+expValuesmaint[i]+" not shown under Maintenance Status dropdown");
				}
			}


			spaceStatus.enterSpaceNumber(spaceNum);
			Thread.sleep(3000);
			scpath=Generic_Class.takeScreenShotPath();
			logger.log(LogStatus.PASS, "Entered the Space Number : " +spaceNum);
			logger.log(LogStatus.INFO, "Screenshot Below: "+logger.addScreenCapture(scpath));
			Thread.sleep(5000);

			spaceStatus.clk_UpdateBtn();
			Thread.sleep(10000);

			if(driver.findElements(By.xpath("//div[@class='k-window-titlebar k-header']//span[text()='Update Status for Space "+spaceNum+"']")).size() != 0){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Update Status for Space "+spaceNum+" page is displayed");
				logger.log(LogStatus.INFO, "img",image);
			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Update Status for Space "+spaceNum+" page is not displayed");
				logger.log(LogStatus.INFO, "img",image);
			}



			if(driver.findElements(By.xpath("//div[@id='employeeNumberEntry']//div[text()='New Status:']/following-sibling::div//span[@class='k-input']")).size()!=0){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "New Status Dropdown is displayed in Update Status for Space popup (DM User)");
				logger.log(LogStatus.INFO, "img",image);

			}else{

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "New Status Dropdown is not displayed in Update Status for Space popup (DM User)");
				logger.log(LogStatus.INFO, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			
			
			spaceStatus.clickDropdown_NewStatus();
			Thread.sleep(6000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
			
			uiValues.clear();
			uiValues = new ArrayList<String>();
			
			
			List<WebElement> newStatus = driver.findElements(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']/ul[contains(@class,'k-list k-reset')]/li[@class='k-item']"));
			String newStatusValue="";
			for(WebElement newStatusElem: newStatus){

				newStatusValue = newStatusElem.getText().trim();
				uiValues.add(newStatusValue);

			}
			
			found = false;
			String[] expValuesNewStatus = {"Unit Vacant"};
			for(int i=0; i<expValuesNewStatus.length; i++){
				found = false;
				for(int k=0; k<uiValues.size(); k++){
					if(expValuesNewStatus[i].equals(uiValues.get(k))){
						found = true;
						break;
					}
				}

				if(found){
					logger.log(LogStatus.PASS, "New Status: "+expValuesNewStatus[i]+" shown under New Status dropdown");
				}else{
					logger.log(LogStatus.FAIL, "New Status: "+expValuesNewStatus[i]+" not shown under New Status dropdown");
				}
			}

			driver.findElement(By.xpath("//div[@class='text-align-right']//a[contains(text(),'Cancel')]")).click();
			Thread.sleep(4000);

			if(spaceStatus.isDisplayed_SpaceStatus_Header()){
				logger.log(LogStatus.PASS, "Space Status page displayed");
			}else{
				logger.log(LogStatus.PASS, "Space Status page not displayed");
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);


			spaceStatus.clk_BackToDashboard_Btn();
			Thread.sleep(4000);
			if(dmHome.verifyAdvancedSearchLnk()){
				logger.log(LogStatus.PASS, "DM Dashboard page is displayed");
			}else{
				logger.log(LogStatus.FAIL, "DM Dashboard page is not displayed");
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			/*
			 * Logout and login back to change according to site id set
			 * 
			 */

			act = new Actions(driver);
			user = driver.findElement(By.xpath(("//div[@id='usernav']")));
			logoff1 = driver.findElement(By.xpath("//a[contains(text(),'Log Off')]"));

			act.moveToElement(user).build().perform();
			jse.executeScript("arguments[0].click();", logoff1);
			Thread.sleep(15000);

			logoff2 = driver.findElement(By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'Log Off')]"));
			jse.executeScript("arguments[0].click();", logoff2);
			Thread.sleep(8000);
			logger.log(LogStatus.INFO, "Logged off from the application");


			// RM Login
			login.login(rmLogin, tabledata.get("Password_RM"));
			logger.log(LogStatus.INFO, "Logged in with RM login");
			Thread.sleep(10000);
			/*Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
			Bifrostpop.clickContiDevice();*/
			try{
			driver.findElement(By.id("continueLink")).click();
			Thread.sleep(10000);
			}catch(Exception ex){
				
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);

			RMHomepage rmHome= new  RMHomepage(driver);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(8000);

			rmHome.clickmanageProp();
			logger.log(LogStatus.INFO,"Clicked on Property Mgmt link");
			Thread.sleep(10000);

			propmgmt= new PropertyManagementPage(driver);
			if(propmgmt.verify_PageTitle()){
				logger.log(LogStatus.PASS, "Property Management page displayed");
			}else{
				logger.log(LogStatus.FAIL, "Property Management page not displayed");
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
			Thread.sleep(4000);

			propmgmt.clickspaceStatus();
			logger.log(LogStatus.INFO,"Clicked on Space Status Link");
			Thread.sleep(15000);
			propmgmt.select_PropNum(siteNumber);
			Thread.sleep(2000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
			Thread.sleep(4000);
			propmgmt.click_OkBtn();
			Thread.sleep(15000);



			spaceStatus = new SpaceStatusPage(driver);
			if(spaceStatus.isDisplayed_SpaceStatus_Header()){
				logger.log(LogStatus.PASS, "Space Status page displayed");
			}else{
				logger.log(LogStatus.PASS, "Space Status page not displayed");
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
			Thread.sleep(5000);

			spaceStatus.clk_LockStatusDropdown();
			Thread.sleep(6000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);

			uiValues.clear();
			uiValues = new ArrayList<String>();

			dragger = new Actions(driver);
			draggablePartOfScrollbar = driver.findElement(By.xpath("(//ul[contains(@class,'k-list k-reset ps-container ps-active-y')])[1]//div[@class='ps-scrollbar-y']"));
			Thread.sleep(3000);
			lockStatus.clear();
			lockStatus = driver.findElements(By.xpath("(//ul[contains(@class,'k-list k-reset ps-container ps-active-y')])[1]//li[@class='k-item']"));
			lkStatus="";
			count=1;
			for(WebElement lockValues: lockStatus){	

				if(count>3){
					dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0, 40).release().build().perform();
					Thread.sleep(1000);
				}
				count++;
				lkStatus =lockValues.getText().trim();
				uiValues.add(lkStatus);
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);

			found = false;
			for(int i=0; i<expValues.length; i++){
				found = false;
				for(int k=0; k<uiValues.size(); k++){
					if(expValues[i].equals(uiValues.get(k))){
						found = true;
						break;
					}
				}

				if(found){
					logger.log(LogStatus.PASS, "Lock Status: "+expValues[i]+" shown under Lock Status dropdown");
				}else{
					logger.log(LogStatus.FAIL, "Lock Status: "+expValues[i]+" not shown under Lock Status dropdown");
				}
			}



			spaceStatus.clk_MaintanenceStatusDropdown();
			Thread.sleep(6000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);

			uiValues.clear();
			uiValues = new ArrayList<String>();

			draggablePartOfScrollbar1 = driver.findElement(By.xpath("(//ul[contains(@class,'k-list k-reset ps-container ps-active-y')])[2]//div[@class='ps-scrollbar-y']"));
			Thread.sleep(3000);
			count = 1;
			maintanenceStatus.clear();
			maintanenceStatus = driver.findElements(By.xpath("(//ul[contains(@class,'k-list k-reset ps-container ps-active-y')])[2]//li[@class='k-item']"));
			maintanenceStatusValue="";
			for(WebElement maintanenceValues: maintanenceStatus){

				if(count>3){
					dragger.moveToElement(draggablePartOfScrollbar1).clickAndHold().moveByOffset(0, 30).release().build().perform();
					Thread.sleep(1000);
				}
				count++;
				maintanenceStatusValue = maintanenceValues.getText().trim();
				uiValues.add(maintanenceStatusValue);

			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);


			found = false;
			for(int i=0; i<expValuesmaint.length; i++){
				found = false;
				for(int k=0; k<uiValues.size(); k++){
					if(expValuesmaint[i].equals(uiValues.get(k))){
						found = true;
						break;
					}
				}

				if(found){
					logger.log(LogStatus.PASS, "Maintenance Status: "+expValuesmaint[i]+" shown under Maintenance Status dropdown");
				}else{
					logger.log(LogStatus.FAIL, "Maintenance Status: "+expValuesmaint[i]+" not shown under Maintenance Status dropdown");
				}
			}


			spaceStatus.enterSpaceNumber(spaceNum);
			Thread.sleep(3000);
			scpath=Generic_Class.takeScreenShotPath();
			logger.log(LogStatus.PASS, "Entered the Space Number : " +spaceNum);
			logger.log(LogStatus.INFO, "Screenshot Below: "+logger.addScreenCapture(scpath));
			Thread.sleep(5000);

			spaceStatus.clk_UpdateBtn();
			Thread.sleep(10000);

			if(driver.findElements(By.xpath("//div[@class='k-window-titlebar k-header']//span[text()='Update Status for Space "+spaceNum+"']")).size() != 0){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Update Status for Space "+spaceNum+" page is displayed");
				logger.log(LogStatus.INFO, "img",image);
			}else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Update Status for Space "+spaceNum+" page is not displayed");
				logger.log(LogStatus.INFO, "img",image);
			}


			if(driver.findElements(By.xpath("//div[@id='employeeNumberEntry']//div[text()='New Status:']/following-sibling::div//span[@class='k-input']")).size() > 0){
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "New Status Dropdown is displayed in Update Status for Space popup (RM User)");
				logger.log(LogStatus.INFO, "img",image);

			}else{

				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "New Status Dropdown is not displayed in Update Status for Space popup (RM User)");
				logger.log(LogStatus.INFO, "Image",image);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			
			
			spaceStatus.clickDropdown_NewStatus();
			Thread.sleep(6000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "img",image);
			
			uiValues.clear();
			uiValues = new ArrayList<String>();
			
			
			newStatus.clear();
			newStatus = driver.findElements(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']/ul[contains(@class,'k-list k-reset')]/li[@class='k-item']"));
			newStatusValue="";
			for(WebElement newStatusElem: newStatus){

				newStatusValue = newStatusElem.getText().trim();
				uiValues.add(newStatusValue);

			}
			
			found = false;																					
			String[] expValuesNewStatus2 = {"Unrentable Ops","Unit Vacant","Unrentable Construction/Dev","Unrentable Facilities Department"};
			for(int i=0; i<expValuesNewStatus2.length; i++){
				found = false;
				for(int k=0; k<uiValues.size(); k++){
					if(expValuesNewStatus2[i].equals(uiValues.get(k))){
						found = true;
						break;
					}
				}

				if(found){
					logger.log(LogStatus.PASS, "New Status: "+expValuesNewStatus2[i]+" shown under New Status dropdown");
				}else{
					logger.log(LogStatus.FAIL, "New Status: "+expValuesNewStatus2[i]+" not shown under New Status dropdown");
				}
			}
			
			

			driver.findElement(By.xpath("//div[@class='text-align-right']//a[contains(text(),'Cancel')]")).click();
			Thread.sleep(4000);

			if(spaceStatus.isDisplayed_SpaceStatus_Header()){
				logger.log(LogStatus.PASS, "Space Status page displayed");
			}else{
				logger.log(LogStatus.PASS, "Space Status page not displayed");
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);


			spaceStatus.clk_BackToDashboard_Btn();
			Thread.sleep(4000);
			if(rmHome.verifyAdvancedSearchLnk()){
				logger.log(LogStatus.PASS, "RM Dashboard page is displayed");
			}else{
				logger.log(LogStatus.FAIL, "RM Dashboard page is not displayed");
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			/*
			 * Logout and login back to change according to site id set
			 * 
			 */

			act = new Actions(driver);
			user = driver.findElement(By.xpath(("//div[@id='usernav']")));
			logoff1 = driver.findElement(By.xpath("//a[contains(text(),'Log Off')]"));

			act.moveToElement(user).build().perform();
			jse.executeScript("arguments[0].click();", logoff1);
			Thread.sleep(15000);

			logoff2 = driver.findElement(By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'Log Off')]"));
			jse.executeScript("arguments[0].click();", logoff2);
			Thread.sleep(8000);
			logger.log(LogStatus.INFO, "Logged off from the application");


		}catch(Exception ex){
			ex.printStackTrace();
			resultFlag="fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Image",image);
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+ex);
		}
	}

	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "PropertyManagement",this.getClass().getSimpleName() , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "PropertyManagement",this.getClass().getSimpleName() , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "PropertyManagement",this.getClass().getSimpleName() , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
		Reporter.log("Test case completed: " +testcaseName, true);
	}
}
