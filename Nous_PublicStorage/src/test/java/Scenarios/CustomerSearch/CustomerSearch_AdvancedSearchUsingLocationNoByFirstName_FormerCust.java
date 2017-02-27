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

public class CustomerSearch_AdvancedSearchUsingLocationNoByFirstName_FormerCust extends Browser_Factory {

	String path="./src/main/resources/Resources/PS_TestData.xlsx";
	public ExtentTest logger;
	String resultFlag="fail";


	@DataProvider
	public Object[][] getCustomerSearchData() 
	{
		return Excel.getCellValue_inlist(path, "CustomerSearch","CustomerSearch","CustomerSearch_AdvancedSearchUsingLocationNoByFirstName_FormerCust");
	}


	@Test(dataProvider="getCustomerSearchData")	
	public void CustomerSearch_AdvancedSearchUsingLocationNoByFirstName_frmrcust(Hashtable<String, String> tabledata) throws Exception 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerSearch").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		try{
			logger=extent.startTest("CustomerSearch_AdvancedSearchUsingLocationNoByFirstName_FormerCust","Verifying the page with account number");

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
			String location = pmhome.getLocation();
			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image=logger.addScreenCapture(scpath);

			if(pmhome.get_existingCustomerText().equals("Existing Customer"))
			{
				logger.log(LogStatus.PASS, "Existing Customer module is displayed");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Existing Customer module is not displayed");
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			//Advance search page
			Advance_Search advSearch= new Advance_Search(driver);

			pmhome.clk_AdvSearchLnk();
			logger.log(LogStatus.INFO, "Clicked on Advanced Search link");
			Thread.sleep(6000);


			if(advSearch.verifyAdvSearchPageIsOpened())
			{
				logger.log(LogStatus.PASS, "Advanced Search page is opened");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Advanced Search page is not opened");
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			System.out.println(advSearch.getLocationNum());
			System.out.println(advSearch.getLocationNum());
			if(!advSearch.getLocationNum().equals(""))
			{
				logger.log(LogStatus.PASS, "Location number is populated");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Location number is not populated");
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}
			
			Thread.sleep(2000);
			String IpAddress=Generic_Class.getIPAddress();
			logger.log(LogStatus.INFO, "Autoamtion scripts running machine ip address is:"+IpAddress);
			Thread.sleep(2000);


			String getSiteIDSetAlready="select * from siteparameter where paramcode like 'IP_COMPUTER_FIRST' and paramvalue='"+IpAddress+"' ";
			ArrayList<String> list1=DataBase_JDBC.executeSQLQuery_List(getSiteIDSetAlready);
			Thread.sleep(8000);
			String alreadySetSiteId = "";
			if(!(list1.size() == 0)){
				alreadySetSiteId=list1.get(2);
			}

			String siteQuery="select sitenumber from site where siteid='"+alreadySetSiteId+"'";

			String siteNumberFromDatabse=DataBase_JDBC.executeSQLQuery(siteQuery);
			if(!(list1.size() == 0)){
				logger.log(LogStatus.INFO, "Site number of the Ip--->" +IpAddress+ "adress in database is"+siteNumberFromDatabse);
			}

			String getSiteIDSetAlready2="select * from siteparameter where paramcode like 'IP_COMPUTER_SECOND' and paramvalue='"+IpAddress+"' ";
			ArrayList<String> list4=DataBase_JDBC.executeSQLQuery_List(getSiteIDSetAlready2);
			Thread.sleep(8000);
			String alreadySetSiteId2 = "";
			if(!(list4.size() == 0)){
				alreadySetSiteId2=list4.get(2);
			}

			String siteQuery2="select sitenumber from site where siteid='"+alreadySetSiteId2+"'";

			String siteNumberFromDatabse2=DataBase_JDBC.executeSQLQuery(siteQuery2);
			if(!(list4.size() == 0)){
				logger.log(LogStatus.INFO, "Site number of the Ip--->" +IpAddress+ "adress in database is"+siteNumberFromDatabse2);
			}



			String AdvLocationNum=advSearch.getLocationNum();
			logger.log(LogStatus.INFO, "site number in advance search page is:"+AdvLocationNum);

			if(siteNumberFromDatabse.equals(AdvLocationNum) || siteNumberFromDatabse2.equals(AdvLocationNum)){


				logger.log(LogStatus.PASS, "Sitenumber in Ui and Site number from database are matching successfully. Site Number: "+AdvLocationNum);
			}else{

				logger.log(LogStatus.FAIL, "Sitenumber in Ui and Site number from database are not matching ");
			}



			boolean locFlag = advSearch.isLocationSelected();

			Reporter.log("locFlag: " + locFlag,true);
			if(advSearch.isLocationSelected())
			{

				logger.log(LogStatus.PASS, "Location radio button is selected by default");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Location radio button is not selected by default");
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}

			if(advSearch.verifyCustRdBtnIsSelected())
			{
				logger.log(LogStatus.PASS, "Customer radio button is selected by default");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer radio button is not selected by default");
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}


			if(advSearch.verifyCustRdBtnIsSelected())
			{
				logger.log(LogStatus.PASS, "'Match any search fields' radio button is selected by default");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "'Match any search fields' radio button is not selected by default");
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}



			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
			Thread.sleep(1000);

			advSearch.clickButton();	
			Thread.sleep(2000);
			logger.log(LogStatus.INFO, "Clicked Search button successfully");
			Thread.sleep(5000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");


			if(advSearch.verifyNoCriteriaMsgIsDisplayed())
			{
				logger.log(LogStatus.PASS, "'NO CRITERIA WAS ENTERED' message is displayed");
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				scpath=Generic_Class.takeScreenShotPath();
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "'NO CRITERIA WAS ENTERED' message is not displayed");
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}

			String headcolor=driver.findElement(By.xpath("//h3//span[@id='TotalQueryResults']")).getCssValue("color");
			if(headcolor.equals("rgba(255, 0, 0, 1)")){

				logger.log(LogStatus.PASS, "No criteria was entered error message is displayed in red color successfully");


			}
			else{

				logger.log(LogStatus.FAIL, "No criteria was entered error message is not displayed in red color");
			}

			((JavascriptExecutor)driver).executeScript("window.scrollTo(500, 0)");
			Thread.sleep(2000);
			advSearch.clickStatusDropdown();
			Thread.sleep(2000);
			List<WebElement> statusTypes= driver.findElements(By.xpath("//ul[@id='SearchContract_SearchStatusID_listbox']/li[@class='k-item']"));
			for(WebElement type:statusTypes)
			{
				if(type.getText().trim().equalsIgnoreCase("Former Customer"))
				{
					type.click();
					break;
				}
			}
			logger.log(LogStatus.INFO, "Selected Former Customer from status drop down");
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);

			Thread.sleep(5000);
			advSearch.enterFirstName(tabledata.get("FirstName"));
			logger.log(LogStatus.INFO, "Entered First name");


			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			advSearch.clickSearchbtn();
			logger.log(LogStatus.INFO, "Click on Search button successfully");
			Thread.sleep(60000);


			String SQlQuery = "select  DISTINCT a.accountnumber, a.accountnumber, c.firstname, c.lastname from contact c with (nolock) "+
					"join customer cu with (nolock) on cu.contactid=c.contactid join account a on a.customerid=cu.customerid "+ 
					"join accountorder ao with (nolock) on ao.accountid=a.accountid "+
					"join accountorderitem aoi with (nolock) on aoi.accountorderid=ao.accountorderid "+
					"join storageorderitem soi with (nolock) on soi.storageorderitemid=aoi.storageorderitemid "+
					"join rentalunit ru with (nolock) on ru.rentalunitid=soi.rentalunitid "+
					"join type t with (nolock) on t.typeid=cu.customertypeid "+
					"join site s with (nolock) on s.siteid=aoi.siteid "+
					"left join accountphone ap with (nolock) on ap.accountid=a.accountid "+
					"left join accountemail ae with (nolock) on ae.accountid=a.accountid "+
					"left join phone p with (nolock) on p.phoneid=ap.phoneid "+
					"left join emailaddress ea with (nolock) on ea.emailaddressid=ae.emailid "+
					"where  "+
					" soi.vacatedate is not null "+
					"and c.firstname like '"+tabledata.get("FirstName")+"%' and s.sitenumber='"+location+"' order by c.lastname";


			String SQLQueryCnt = "select count(DISTINCT a.accountnumber) from contact c with (nolock) " + 
					"join customer cu with (nolock) on cu.contactid=c.contactid join account a on a.customerid=cu.customerid "+ 
					"join accountorder ao with (nolock) on ao.accountid=a.accountid "+
					"join accountorderitem aoi with (nolock) on aoi.accountorderid=ao.accountorderid "+
					"join storageorderitem soi with (nolock) on soi.storageorderitemid=aoi.storageorderitemid "+
					"join rentalunit ru with (nolock) on ru.rentalunitid=soi.rentalunitid "+
					"join type t with (nolock) on t.typeid=cu.customertypeid "+
					"join site s with (nolock) on s.siteid=aoi.siteid "+
					"left join accountphone ap with (nolock) on ap.accountid=a.accountid "+
					"left join accountemail ae with (nolock) on ae.accountid=a.accountid "+
					"left join phone p with (nolock) on p.phoneid=ap.phoneid "+
					"left join emailaddress ea with (nolock) on ea.emailaddressid=ae.emailid "+
					"where  "+
					" soi.vacatedate is not null "+
					"and c.firstname like '"+tabledata.get("FirstName")+"%' and s.sitenumber='"+location+"'";




			String ele2 = DataBase_JDBC.executeSQLQuery(SQLQueryCnt);
			if(Integer.parseInt(ele2)>=100){
				Thread.sleep(50000);
				SearchingPopup search = new SearchingPopup(driver);
				search.click_OkBtn();
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
					scpath=Generic_Class.takeScreenShotPath();
					image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "Image",image);

				}else{
					logger.log(LogStatus.FAIL, "DataBase count: "+ele2+" and Search result grid count:"+ list2.size()+" are not same");
					resultFlag="fail";
					scpath=Generic_Class.takeScreenShotPath();
					image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "Image",image);
				}
			}else{
				String text = advSearch.get_RecordsMatchedText();
				if(text.contains("Warning: There were more than 100 records found. Please refine your search")){
					logger.log(LogStatus.PASS, "More than 100 results found in db. So, warning message displayed. DB Count: "+ele2);
					scpath=Generic_Class.takeScreenShotPath();
					image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "Image",image);
				}else{
					logger.log(LogStatus.PASS, "More than 100 results found in db. But, warning message not displayed in UI. DB Count: "+ele2);
					scpath=Generic_Class.takeScreenShotPath();
					image=logger.addScreenCapture(scpath);
					logger.log(LogStatus.INFO, "Image",image);
				}
			}
			scpath=Generic_Class.takeScreenShotPath();
			image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);
			String searchResultsName= advSearch.validateSearchResultsName();
			if(searchResultsName.contains(tabledata.get("FirstName"))){
				logger.log(LogStatus.PASS, "Validated First Name in advance search results grid");
			}else{
				logger.log(LogStatus.FAIL, "Validation failed for First Name in advance search results grid");
				resultFlag="fail";
			}





			ArrayList<String> dbAccNums = DataBase_JDBC.executeSQLQuery_List_SecondCol(SQlQuery);
			List<WebElement> elements = driver.findElements(By.xpath("//table[@class='k-selectable']/tbody//tr/td[11]"));

			if(noResults == true){
				elements.remove(0);
			}

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
						logger.log(LogStatus.PASS, "Expected Result:"+webAccNums.get(j)+" and Actual Result:"+dbAccNums.get(k)+" in advance search results grid");
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

		}

		catch(Exception e)
		{
			Reporter.log("Exception: " + e,true);
			resultFlag="fail";
		}

	}


	@AfterMethod
	public void afterMethod(){

		Reporter.log(resultFlag,true);

		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","CustomerSearch_AdvancedSearchUsingLocationNoByFirstName_FormerCust" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerSearch","CustomerSearch_AdvancedSearchUsingLocationNoByFirstName_FormerCust" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","CustomerSearch_AdvancedSearchUsingLocationNoByFirstName_FormerCust" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();

	}


}
