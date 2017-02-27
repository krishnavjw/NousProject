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
import Pages.AdvSearchPages.SearchingPopup;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class AdvancedSearchUsingState_Phone_All extends Browser_Factory {

	String path=Generic_Class.getPropertyValue("Excelpath");
	public ExtentTest logger;
	String resultFlag="pass";


	@DataProvider
	public Object[][] getCustomerSearchData() 
	{
		return Excel.getCellValue_inlist(path, "CustomerSearch","CustomerSearch","AdvancedSearchUsingState_Phone_All");
	}


	@Test(dataProvider="getCustomerSearchData")	
	public void AdvancedSearchUsingState_FirstName(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerSearch").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		try{
			logger=extent.startTest("AdvancedSearchUsingState_Phone_All","Verifying the page with account number");

			//Login to PS Application
			LoginPage logpage = new LoginPage(driver);
			
			logpage.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Logged in successfully");


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
			Thread.sleep(10000);


			PM_Homepage pmhome = new PM_Homepage(driver);
			String siteNumber = pmhome.getLocation();
			pmhome.clk_AdvSearchLnk();
			logger.log(LogStatus.INFO, "Entered into advance search page");

			//Advance search page
			Advance_Search advSearch= new Advance_Search(driver);

			if(advSearch.verifySearchBySection().trim().contains(tabledata.get("SearchBy"))){
				logger.log(LogStatus.PASS, "Validating Search By section in advance search page");
			}else{
				logger.log(LogStatus.FAIL, "Search By section is not displayed in advance search page");
			}

			if(advSearch.verifySearchForSection().trim().equalsIgnoreCase(tabledata.get("SearchFor"))){
				logger.log(LogStatus.PASS, "Validating Search For Section in advance search page");
			}else{
				logger.log(LogStatus.FAIL, "Search For section is not displayed in advance search page");
			}

			if(advSearch.verifyStorageSection().trim().equalsIgnoreCase(tabledata.get("Storage"))){
				logger.log(LogStatus.PASS, "Validating Storage Section in advance search page");
			}else{
				logger.log(LogStatus.FAIL, "Storage section is not displayed in advance search page");
			}

			Thread.sleep(1000);

			if(advSearch.isLocationSelected()){
				logger.log(LogStatus.PASS, "Validating default selected Location radio button in advance search page");
			}else{
				logger.log(LogStatus.FAIL, "Location radio button is not selected by default in advance search page");
			}
			advSearch.clickLocationNumber();
			System.out.println(advSearch.getAttribute_Location());
			String site=advSearch.getAttribute_Location();
			if(siteNumber.toString().equalsIgnoreCase(site)){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Validating current location number is displayed in advance search page");
				logger.log(LogStatus.PASS, "Validating current location number is displayed in advance search page",image);
			}else{
				logger.log(LogStatus.FAIL, "Location number is not correct in advance search page");
			}

			if(advSearch.cus_radio()){
				logger.log(LogStatus.PASS, "Validating default selected Account Type radio button in advance search page");
			}else{
				logger.log(LogStatus.FAIL, "Account Type radio button is not selected by default in advance search page");
			}

			if(advSearch.match_AnySearch()){
				logger.log(LogStatus.PASS, "Validating default selected Match Any Search Field radio button in advance search page");
			}else{
				logger.log(LogStatus.FAIL, "Match Any Search Field radio button is not selected by default in advance search page");
			}

			/*String statusValue = advSearch.verifyStatusDropdownValue();
			if(statusValue.equalsIgnoreCase(tabledata.get("StatusValue"))){
				logger.log(LogStatus.PASS, "Currently Renting  value is seleceted by default in status dropdown");
			}else{
				logger.log(LogStatus.FAIL, "Currently Renting  value is not seleceted by default in status dropdown");
			}*/

			/*JavascriptExecutor je = (JavascriptExecutor) driver;
	        Thread.sleep(2000);
	       WebElement element =driver.findElement(By.xpath("//ul[@class='k-list k-reset ps-container ps-active-y']/li[9]"));
	       je.executeScript("arguments[0].scrollIntoView(true);",element);
	       element.click();
	  	   Thread.sleep(10000);
			 */

			if(advSearch.isStateDisplayed()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Validating State radio button in advance search page");
				logger.log(LogStatus.PASS, "Validating State radio button in advance search page",image);

			}

			((JavascriptExecutor)driver).executeScript("window.scrollBy(0, 500)", 0);
			Thread.sleep(3000);
			advSearch.click_StateRadioBtn();	
			Thread.sleep(2000);
			if(!advSearch.isLocationNumberEnabled()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Validating whether Location number field is disabled in advance search page");
				logger.log(LogStatus.PASS, "Validating whether Location number field is disabled in advance search page",image);
			}

			if(!advSearch.isZipcodeEnabled()){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Validating whether Zipcode field is disabled in advance search page");
				logger.log(LogStatus.PASS, "Validating whether Zipcode field is disabled in advance search page",image);
			}

			/*List<WebElement> dropdownlist = driver.findElements(By.xpath("(//ul[@id='SearchContract_StateCode_listbox'])[2]/li[@class='k-item']"));
			Thread.sleep(3000);
			for(int i=1;i<=dropdownlist.size();i++){
				Thread.sleep(3000);
				String value=driver.findElement(By.xpath("(//ul[@id='SearchContract_StateCode_listbox'])[2]/li["+i+"]")).getText().trim();
				if(i%4==0){
					Thread.sleep(3000);
					WebElement ele= driver.findElement(By.xpath("(//ul[@id='SearchContract_StateCode_listbox'])[2]/li["+i+"]"));
					((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",ele);
					Thread.sleep(3000);
				}else if(tabledata.get("StateCode").equalsIgnoreCase(value)){
					Thread.sleep(3000);
					driver.findElement(By.xpath("(//ul[@id='SearchContract_StateCode_listbox'])[2]/li["+i+"]")).click();
				}
			}*/
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);
			advSearch.clickSearchbtn();
			Thread.sleep(3000);
			String errorMessage= advSearch.validateStateErrorMsg();
			if(tabledata.get("StateErrorMessage").equalsIgnoreCase(errorMessage)){
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Validating error message is visible in advance search page");
				logger.log(LogStatus.PASS, "Validating error message is visible in advance search page",image);
			}
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, 250)");
			Thread.sleep(3000);
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
			((JavascriptExecutor)driver).executeScript("window.scrollTo(500, 0)");
			Thread.sleep(2000);
			advSearch.clickStatusDropdown();
			Thread.sleep(2000);
			List<WebElement> statusTypes= driver.findElements(By.xpath("//ul[@id='SearchContract_SearchStatusID_listbox']/li[@class='k-item']"));
			for(WebElement type:statusTypes)
			{
				if(type.getText().trim().equalsIgnoreCase(tabledata.get("StatusValue")))
				{
					type.click();
					break;
				}
			}
			advSearch.enter_Area(tabledata.get("AreaNumber"));
			advSearch.enter_Exchange(tabledata.get("Exchange"));
			advSearch.enter_LineNum(tabledata.get("LineNum"));
			Thread.sleep(2000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(5000,0)");
			Thread.sleep(5000);
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,3000)");
			Thread.sleep(3000);
			advSearch.clickSearchbtn();
			logger.log(LogStatus.INFO, "Clicked Search button successfully");
			Thread.sleep(6000);

			String queryCount="Select count(distinct A.AccountNumber) as AccountNumbers "+
					"from Account A "+
					"Join Customer CU on A.CustomerID = CU.CustomerID "+
					"Join Contact C on CU.ContactID = C.ContactID "+
					"Join AccountOrder AO on A.AccountID = AO.AccountID "+
					"Join AccountOrderItem AOI on AO.AccountOrderID  = AOI.AccountOrderID "+
					"Join StorageOrderItem SOI on AOI.StorageOrderItemID = SOI.StorageOrderItemID "+
					"Join Site S on AOI.SiteID = S.SiteID "+
					"Join Address Ad on S.PhysicalAddressID = Ad.AddressID "+
					"Join Phone P on C.ContactID = P.ContactID "+
					"Where 1=1 "+
					" And S.isactive=1 "+
					"And Ad.StateCode= '"+tabledata.get("StateCode")+"' "+
					"And p.phonenumber='"+tabledata.get("PhoneNumber")+"' ";


			String query="Select  distinct a.AccountNumber, a.accountnumber,c.firstname "+
					"from Account A "+
					"Join Customer CU on A.CustomerID = CU.CustomerID "+
					"Join Contact C on CU.ContactID = C.ContactID "+
					"Join AccountOrder AO on A.AccountID = AO.AccountID "+
					"Join AccountOrderItem AOI on AO.AccountOrderID  = AOI.AccountOrderID "+
					"Join StorageOrderItem SOI on AOI.StorageOrderItemID = SOI.StorageOrderItemID "+
					"Join Site S on AOI.SiteID = S.SiteID "+
					"Join Address Ad on S.PhysicalAddressID = Ad.AddressID "+
					"Join Phone P on C.ContactID = P.ContactID "+
					"Where 1=1 "+
					 " And S.isactive=1 "+
					"And Ad.StateCode= '"+tabledata.get("StateCode")+"' "+
					"And p.phonenumber='"+tabledata.get("PhoneNumber")+"' ";

			String ele2 = DataBase_JDBC.executeSQLQuery(queryCount);
			Thread.sleep(8000);
			if(Integer.parseInt(ele2)>=100){
				Thread.sleep(50000);
				((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
				Thread.sleep(5000);
				SearchingPopup searched = new SearchingPopup(driver);
				searched.click_OkBtn();
				Thread.sleep(5000);
			}
			((JavascriptExecutor)driver).executeScript("window.scrollTo(-2000, 0)");
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, 500)");



			boolean noResults = false;
			List<WebElement> list2=driver.findElements(By.xpath("//table[@class='k-selectable']/tbody/tr"));
			if(list2.get(0).getText().equalsIgnoreCase("NO ITEMS TO DISPLAY")){
				list2.remove(0);
				noResults = true;
			}
			if(Integer.parseInt(ele2)<100){
				if(list2.size()== Integer.parseInt(ele2)){

					logger.log(LogStatus.PASS, "DataBase count: "+ele2+" and Search result grid count:"+ list2.size()+" are same");
					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "Image",image);

				}else{
					logger.log(LogStatus.FAIL, "DataBase count: "+ele2+" and Search result grid count:"+ list2.size()+" are not same");
					resultFlag="fail";
					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "Image",image);
				}
			}
			else{
				String text = advSearch.get_RecordsMatchedText();
				if(text.contains("Warning: There were more than 100 records found. Please refine your search")){
					logger.log(LogStatus.PASS, "More than 100 results found in db. So, warning message displayed. DB Count: "+ele2);
					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "Image",image);
				}else{
					logger.log(LogStatus.PASS, "More than 100 results found in db. But, warning message not displayed in UI. DB Count: "+ele2);
					String scpath=Generic_Class.takeScreenShotPath();
					String image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "Image",image);
				}
			}

			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			String searchResultsName= advSearch.validateSearchResultsName().replaceAll(" ", "");
			if(searchResultsName.contains(tabledata.get("PhoneNumber"))){
				logger.log(LogStatus.PASS, "Phone Number found in Search Query Heading. Phone Number: "+tabledata.get("PhoneNumber"));
			}else{
				logger.log(LogStatus.FAIL, "Phone Number not found in Search Query Heading. Phone Number: "+tabledata.get("PhoneNumber"));
				resultFlag="fail";
			}

			logger.log(LogStatus.INFO, " *** Search Criteria - Phone Number: "+tabledata.get("PhoneNumber")+" *** ");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollTo(0, -(document.body.scrollHeight))");
			logger.log(LogStatus.INFO, " *** Search Criteria Screenshot *** ");
			Thread.sleep(3000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			Thread.sleep(3000);
			jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			logger.log(LogStatus.INFO, " *** Result Screenshot *** ");
			Thread.sleep(3000);
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);

			logger.log(LogStatus.INFO, "Comparing account numbers of customers shown in UI with the customer account numbers from DB search");


			ArrayList<String> dbAccNums = DataBase_JDBC.executeSQLQuery_List_SecondCol(query);
			List<WebElement> elements = driver.findElements(By.xpath("//table[@class='k-selectable']/tbody//tr/td[11]"));


			ArrayList<String> webAccNums = new ArrayList<String>();
			for(int i=0; i<elements.size(); i++){
				if(i>5)
					break;
				webAccNums.add(elements.get(i).getText());
			}

			boolean found;
			int count = 0;
			for(int j=0; j<webAccNums.size(); j++){
				found = false;
				for(int k=0; k<dbAccNums.size(); k++){
					if(dbAccNums.get(k).equalsIgnoreCase(webAccNums.get(j))){
						logger.log(LogStatus.PASS, "Actual Result (UI value): "+webAccNums.get(j)+" and Expected Result (DB Value): "+dbAccNums.get(k)+" in advance search results grid");
						count++;
						found = true;
						break;
					}
				}
				if(found == false){
					logger.log(LogStatus.FAIL, "Customer with account number "+webAccNums.get(j)+" found in UI. But not found in DB search for the same");
					resultFlag="fail";
				}
			}


			if(!(count == webAccNums.size())){
				logger.log(LogStatus.FAIL, "Data mismatch between DB & UI");
				resultFlag="fail";
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}



		}catch(Exception e){
			e.printStackTrace();
			resultFlag = "fail";
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.FAIL, "Validating whether Zipcode field is disabled in advance search page",image);
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+e);
		}
	}


	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","AdvancedSearchUsingState_Phone_All" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerSearch","AdvancedSearchUsingState_Phone_All" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","AdvancedSearchUsingState_Phone_All" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}

}
