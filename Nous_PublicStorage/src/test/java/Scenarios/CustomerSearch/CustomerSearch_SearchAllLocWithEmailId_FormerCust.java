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

public class CustomerSearch_SearchAllLocWithEmailId_FormerCust extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] getCustSearchData() 
	{
		return Excel.getCellValue_inlist(path, "CustomerSearch","CustomerSearch","CustomerSearch_SearchAllLocWithEmailId_FormerCust");
	}

	@Test(dataProvider="getCustSearchData")
	public void CustomerSearch_SearchAllLocWithEmailId_FormerCust(Hashtable<String, String> tabledata) throws InterruptedException 
	{

		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerSearch").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the CustomerSearch_SearchAllLocWithEmailId_FormerCust test");
		}

		try{

			logger=extent.startTest("CustomerSearch_SearchAllLocWithEmailId_FormerCust", "customer search in all location using former customer Email ID");
			Thread.sleep(5000);
			LoginPage loginPage = new LoginPage(driver);
			logger.log(LogStatus.PASS, "creating object for the Login Page sucessfully ");
			loginPage.enterUserName(tabledata.get("UserName"));
			Thread.sleep(2000);
			logger.log(LogStatus.PASS, "entered username sucessfully");
			Thread.sleep(2000);
			loginPage.enterPassword(tabledata.get("Password"));
			logger.log(LogStatus.PASS, "entered password sucessfully");
			Thread.sleep(2000);
			loginPage.clickLogin();
			logger.log(LogStatus.PASS, "clicked on login in button sucessfully");
			logger.log(LogStatus.PASS, "Login to Application  successfully");
			//=================Handling customer facing device========================
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
			//=================================================================================
			PM_Homepage pmhomepage=new PM_Homepage(driver);	
			logger.log(LogStatus.PASS, "creating object for the PM home page sucessfully");

			Thread.sleep(3000);
			if(pmhomepage.isexistingCustomerModelDisplayed()){

				logger.log(LogStatus.PASS, "Existing Customer module is present in the PM DashBoard sucessfully");
			}else{
				logger.log(LogStatus.FAIL, "Existing Customer module is not present in the PM DashBoard");
			}

			Thread.sleep(4000);
			if(pmhomepage.get_findACustomerAtThisLocText().contains(tabledata.get("TextEntryField"))){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Find a Customer at this Location is displayed successfully");
				logger.log(LogStatus.INFO, "Find a Customer at this Location is displayed successfully",image);
			}
			else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Find a Customer at this Location is not displayed ");
				logger.log(LogStatus.INFO, "Find a Customer at this Location is not displayed ",image);

			}

			Thread.sleep(3000);
			if(pmhomepage.get_findACustomerText().trim().contains(tabledata.get("CustomerButtonName"))){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Find Customer button is displayed successfully");
				logger.log(LogStatus.INFO, "Find Customer button is displayed successfully",image);
			}
			else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Find Customer button  is not displayed ");
				logger.log(LogStatus.INFO, "Find Customer button is not displayed ",image);

			}

			Thread.sleep(3000);
			pmhomepage.clk_AdvSearchLnk();
			logger.log(LogStatus.PASS, "clicked on advanced search link in the PM Dashboard sucessfully");

			Thread.sleep(6000);
			Advance_Search search=new Advance_Search(driver);
			logger.log(LogStatus.PASS, "creating object for advance search page sucessfully");

			if(search.verifyAdvSearchPageIsOpened())
			{
				logger.log(LogStatus.PASS, "Advanced Search page is opened");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);

			}
			else{
				logger.log(LogStatus.PASS, "Advanced Search page is not opened");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}


			boolean locFlag = search.isLocationSelected();

			if(search.isLocationSelected())
			{

				logger.log(LogStatus.PASS, "Location radio button is selected by default");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);logger.log(LogStatus.FAIL, "Location radio button is not selected by default");
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}

			if(search.verifyCustRdBtnIsSelected())
			{
				logger.log(LogStatus.PASS, "Customer radio button is selected by default");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Customer radio button is not selected by default");
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}


			if(search.verifyCustRdBtnIsSelected())
			{
				logger.log(LogStatus.PASS, "'Match any search fields' radio button is selected by default");
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);logger.log(LogStatus.INFO, "Image",image);
			}
			else
			{
				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "'Match any search fields' radio button is not selected by default");
				logger.log(LogStatus.INFO, "Image",image);
				resultFlag="fail";
			}

			Thread.sleep(2000);
			search.search_All();
			logger.log(LogStatus.PASS, "checked search with all locations check box sucessfully");


			Thread.sleep(5000);
			if(!search.isLocationEnabled()){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "After click on the Search all location check box Location Text box is grayed out sucessfully");
				logger.log(LogStatus.INFO, "After click on the Search all location check box Location Text box is grayed out sucessfully",image);
			}else{

				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "After click on the Search all location check box Location Text box is not grayed out");
				logger.log(LogStatus.INFO, "After click on the Search all location check box Location Text box is not grayed out ",image);

			}

			Thread.sleep(5000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(5000);
			search.clickSearchbtn();
			logger.log(LogStatus.PASS, "clicked on the seatch button next to reset button sucessfully");


			Thread.sleep(8000);
			((JavascriptExecutor)driver).executeScript("window.scrollBy(-1000,0)");
			Thread.sleep(5000);
			String errormsg=driver.findElement(By.xpath("//h3//span[@id='TotalQueryResults']")).getText().trim().toLowerCase();
			Reporter.log("error message"+errormsg,true);
			if(errormsg.contains("no criteria was entered")){

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Information Msg NO CRITERIA WAS ENTERED displayed in red color sucessfully");
				logger.log(LogStatus.INFO, "Information Msg NO CRITERIA WAS ENTERED displayed in red color sucessfully",image);

			}else{
				if(resultFlag.equals("pass"))
					resultFlag="fail";

				String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Information Msg NO CRITERIA WAS ENTERED is not displayed");
				logger.log(LogStatus.INFO, "Information Msg NO CRITERIA WAS ENTERED is not displayed ",image);}

			String headcolor=driver.findElement(By.xpath("//h3//span[@id='TotalQueryResults']")).getCssValue("color");
			if(headcolor.equals("rgba(255, 0, 0, 1)")){

				logger.log(LogStatus.PASS, "No criteria was entered error message is displayed in red color successfully");


			}
			else{

				logger.log(LogStatus.FAIL, "No criteria was entered error message is not displayed in red color");
			}



			Thread.sleep(2000);
			((JavascriptExecutor)driver).executeScript("window.scrollTo(500, 0)");
			Thread.sleep(2000);
			search.clickStatusDropdown();
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
			String scpath=Generic_Class.takeScreenShotPath();
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.INFO, "Image",image);

			search.enterEmail(tabledata.get("EmailId"));
			logger.log(LogStatus.PASS, "entered customer first name sucessfully in Search for module");

			String SQLQuery = "select DISTINCT a.accountnumber, a.accountnumber, ea.email from contact c with (nolock) "+ 
					" join customer cu with (nolock) on cu.contactid=c.contactid join account a on a.customerid=cu.customerid"+
					" join accountorder ao with (nolock) on ao.accountid=a.accountid"+ 
					" join accountorderitem aoi with (nolock) on aoi.accountorderid=ao.accountorderid"+
					" join storageorderitem soi with (nolock) on soi.storageorderitemid=aoi.storageorderitemid"+ 
					" join rentalunit ru with (nolock) on ru.rentalunitid=soi.rentalunitid"+ 
					" join type t with (nolock) on t.typeid=cu.customertypeid"+ 
					" join site s with (nolock) on s.siteid=aoi.siteid"+
					" left join accountphone ap with (nolock) on ap.accountid=a.accountid"+ 
					" left join accountemail ae with (nolock) on ae.accountid=a.accountid"+ 
					" left join phone p with (nolock) on p.phoneid=ap.phoneid"+ 
					" left join emailaddress ea with (nolock) on ea.emailaddressid=ae.emailid"+  
					" where "+
					"  soi.vacatedate is not null "+
					" and s.isactive=1"+
					" and ea.email = '"+tabledata.get("EmailId")+"'";



			String SQLQueryCnt = "select count(DISTINCT a.accountnumber) from contact c with (nolock) " + 
					" join customer cu with (nolock) on cu.contactid=c.contactid join account a on a.customerid=cu.customerid"+
					" join accountorder ao with (nolock) on ao.accountid=a.accountid"+ 
					" join accountorderitem aoi with (nolock) on aoi.accountorderid=ao.accountorderid"+
					" join storageorderitem soi with (nolock) on soi.storageorderitemid=aoi.storageorderitemid"+ 
					" join rentalunit ru with (nolock) on ru.rentalunitid=soi.rentalunitid"+ 
					" join type t with (nolock) on t.typeid=cu.customertypeid"+ 
					" join site s with (nolock) on s.siteid=aoi.siteid"+
					" left join accountphone ap with (nolock) on ap.accountid=a.accountid"+ 
					" left join accountemail ae with (nolock) on ae.accountid=a.accountid"+ 
					" left join phone p with (nolock) on p.phoneid=ap.phoneid"+ 
					" left join emailaddress ea with (nolock) on ea.emailaddressid=ae.emailid"+ 
					" where "+ 
					"  soi.vacatedate is not null "+
					" and s.isactive=1"+
					" and ea.email = '"+tabledata.get("EmailId")+"'";

			String ele2 = DataBase_JDBC.executeSQLQuery(SQLQueryCnt);

			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(3000);
			search.clickSearchbtn();
			logger.log(LogStatus.INFO, "Click on Search button successfully");
			Thread.sleep(20000);
			if(Integer.parseInt(ele2)>=100){
				Thread.sleep(50000);
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
				String text = search.get_RecordsMatchedText();
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
			String searchResultsName= search.validateSearchResultsName();
			if(searchResultsName.contains(tabledata.get("EmailId"))){
				logger.log(LogStatus.PASS, "Validated Email in advance search results grid");
			}else{
				logger.log(LogStatus.FAIL, "Validation failed for Email in advance search results grid");
				resultFlag="fail";
			}


			ArrayList<String> dbAccNums = DataBase_JDBC.executeSQLQuery_List_SecondCol(SQLQuery);
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
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","CustomerSearch_SearchAllLocWithEmailId_FormerCust" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerSearch","CustomerSearch_SearchAllLocWithEmailId_FormerCust" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","CustomerSearch_SearchAllLocWithEmailId_FormerCust" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
	}










}
