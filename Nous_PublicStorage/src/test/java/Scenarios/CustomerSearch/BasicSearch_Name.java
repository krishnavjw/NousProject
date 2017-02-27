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

public class BasicSearch_Name extends Browser_Factory {

	public ExtentTest logger;
	String resultFlag="pass";
	String path = Generic_Class.getPropertyValue("Excelpath");

	@DataProvider
	public Object[][] BasicSearch() 
	{
		return Excel.getCellValue_inlist(path, "CustomerSearch","CustomerSearch","BasicSearch_Name");
	}

	@Test(dataProvider="BasicSearch")
	public void BasicSearch_Name(Hashtable<String, String> tabledata) throws InterruptedException 
	{


		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerSearch").equals("Y")))
		{
			resultFlag="skip";
			throw new SkipException("Skipping the test");
		}

		try{

			//Login To the Application
			logger=extent.startTest("BasicSearch_Name", "Search in basic search ");
			LoginPage loginPage = new LoginPage(driver);		
			loginPage.login(tabledata.get("UserName"),tabledata.get("Password"));
			logger.log(LogStatus.INFO, "Login to Application  successfully");


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


			//Verify that a text entry field is present with text "Find a Customer at this Location
			PM_Homepage pmhomepage=new PM_Homepage(driver);	

			 String getTextTextEntryFieldData = "Find a Customer at this Location:";
			String getTextTextEntryFieldWeb = pmhomepage.get_findACustomerAtThisLocText().trim();
			if(getTextTextEntryFieldData.equalsIgnoreCase(getTextTextEntryFieldWeb)){

				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "TextEntryField is displayed successfully :"+getTextTextEntryFieldWeb);
				logger.log(LogStatus.INFO, "TextEntryField is displayed successfully",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "TextEntryField is not displayed ");
				logger.log(LogStatus.INFO, "TextEntryField is not displayed ",image);

			}
			Thread.sleep(5000);
			//Verify Find Customer" button is available
			 String getCustomerButtonNameData = "Find Customer";
			String getCustomerButtonNameWeb = pmhomepage.get_findACustomerText().trim();
			if(getCustomerButtonNameData.equalsIgnoreCase(getCustomerButtonNameWeb)){

				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);

				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Find Customer button is displayed successfully :"+getCustomerButtonNameWeb);
				logger.log(LogStatus.INFO, "Find Customer button is displayed successfully",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Find Customer button  is not displayed ");
				logger.log(LogStatus.INFO, "Find Customer button is not displayed ",image);

			}
			Thread.sleep(5000);
			
			//Verify User should view the module Existing Customer
			if(pmhomepage.get_existingCustomerText().equalsIgnoreCase("Existing Customer")){

				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);

				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Existing Customer module displayed  successfully:"+pmhomepage.get_existingCustomerText());
				logger.log(LogStatus.INFO, "Existing Customer module displayed  successfully",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Existing Customer module not displayed  successfully ");
				logger.log(LogStatus.INFO, "Existing Customer module not displayed  successfully ",image);

			}
			
			
			

			//Enter "Name" of an existing customer at current location and click on "Find Customer" button

			pmhomepage.enter_findCustAddrLocation(tabledata.get("FirstName").toLowerCase());
			logger.log(LogStatus.PASS, " existing customer First name entered successfully in lower case");
			Thread.sleep(5000);
			pmhomepage.clk_findCustomer();
			logger.log(LogStatus.PASS, "Click on Find Customer Button successfully");

			Thread.sleep(5000);
			Advance_Search advSearch= new Advance_Search(driver);
			logger.log(LogStatus.INFO, "Advance Search page object is created successfully");
			Thread.sleep(5000);
			String actualRecord=advSearch.get_RecordsMatchedText();
			if(actualRecord.contains("Records Matched")){
				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Actual Matching record  displayed successfully");
				logger.log(LogStatus.INFO, "Actual Matching record  displayed successfully ",image);
			}
			else{

				String scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Actual Matching record  not displayed ");
				logger.log(LogStatus.INFO, "Actual Matching record  not displayed ",image);

			}

			
			
			LoginPage lp=new LoginPage(driver);
			String location=lp.get_SiteNumber();
			logger.log(LogStatus.PASS, "Site no captured successfully :"+location);
			
			//DB validation
			String FirstName=tabledata.get("FirstName");
			String sqlQuery1= "select count(distinct a.accountnumber) from contact c with (nolock)"+
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
					" where c.lastname like '%"+tabledata.get("FirstName")+"%'  and s.sitenumber='"+location+"'"+
					" and soi.vacatedate is null";
			String sqlQuery2= "select count(distinct a.accountnumber) from contact c with (nolock)"+
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
					" where c.firstname like '%"+tabledata.get("FirstName")+"%'  and s.sitenumber='"+location+"'"+
					" and soi.vacatedate is null";
			
			
			
			String dbCount1 = DataBase_JDBC.executeSQLQuery(sqlQuery1);
			String dbCount2 = DataBase_JDBC.executeSQLQuery(sqlQuery2);
			int record= Integer.parseInt(dbCount1)+Integer.parseInt(dbCount2);
			if(record > 0){
		
				logger.log(LogStatus.PASS, "Customer Search DB validation successful");
			}else{
				logger.log(LogStatus.FAIL, "Customer Search DB validation failed. No records found in DB with the given customer name");
				if(resultFlag.equals("pass"))
					resultFlag="fail";
			}
			Thread.sleep(8000);
		
		
			
			boolean noResults = false;
			 List<WebElement> list2=driver.findElements(By.xpath("//table[@class='k-selectable']/tbody/tr"));
			 if(list2.get(0).getText().equalsIgnoreCase("NO ITEMS TO DISPLAY")){
				 list2.remove(0);
				 noResults = true;
			 }
			 
			 	if(record<100){
	            if(list2.size()== record){

	                  logger.log(LogStatus.PASS, "DataBase count: "+record+" and Search result grid count:"+ list2.size()+" are same");
	                  String scpath=Generic_Class.takeScreenShotPath();
	  				String image=logger.addScreenCapture(scpath);
	  				logger.log(LogStatus.INFO, "Image",image);

	            }else{
	                  logger.log(LogStatus.FAIL, "DataBase count: "+record+" and Search result grid count:"+ list2.size()+" are not same");
	                  resultFlag="fail";
	                  String scpath=Generic_Class.takeScreenShotPath();
		  				String image=logger.addScreenCapture(scpath);
		  				logger.log(LogStatus.INFO, "Image",image);
	            }
			 	}else{
			 		String text = advSearch.get_RecordsMatchedText();
			 		if(text.contains("Warning: There were more than 100 records found. Please refine your search")){
			 			 logger.log(LogStatus.PASS, "More than 100 results found in db. So, warning message displayed. DB Count: "+record);
			 			 String scpath=Generic_Class.takeScreenShotPath();
			  				String image=logger.addScreenCapture(scpath);
			  				logger.log(LogStatus.INFO, "Image",image);
			 		}else{
			 			 logger.log(LogStatus.FAIL, "More than 100 results found in db. But, warning message not displayed in UI. DB Count: "+record);
			 			 String scpath=Generic_Class.takeScreenShotPath();
			  				String image=logger.addScreenCapture(scpath);
			  				logger.log(LogStatus.INFO, "Image",image);
			 		}
			 	}
			 	 String scpath=Generic_Class.takeScreenShotPath();
	  				String image=logger.addScreenCapture(scpath);
	  				logger.log(LogStatus.INFO, "Image",image);
           String searchResultsName= advSearch.validateSearchResultsName();
           if(searchResultsName.toLowerCase().contains(tabledata.get("FirstName").toLowerCase())){
                 logger.log(LogStatus.PASS, "Validated First Name in advance search results grid");
           }else{
           	 logger.log(LogStatus.FAIL, "Validation failed for First Name in advance search results grid");
           	 resultFlag="fail";
           }

           
			
			
			
           List<WebElement> uILN= driver.findElements(By.xpath("//table[@class='k-selectable']/tbody//tr/td[3]"));
           List<WebElement> uIFN= driver.findElements(By.xpath("//table[@class='k-selectable']/tbody//tr/td[4]"));
           
           if(noResults == true){
        	   uILN.remove(0);
        	   uIFN.remove(0);
           }
           int count=0;
           for(int i=0; i<uILN.size(); i++){
        	   String lastName = uILN.get(i).getText().toLowerCase();
        	   String firstName = uIFN.get(i).getText().toLowerCase();
        	   if(lastName.contains(tabledata.get("FirstName").toLowerCase()) || firstName.contains(tabledata.get("FirstName").toLowerCase())){
        		   count++;
        		   if(count>=5){
        			   break;
        		   }
        	   }
           }if(count == uILN.size()){
            	
           }else if(count!=5){
           	logger.log(LogStatus.FAIL, "Data mismatch between DB & UI");
           	 resultFlag="fail";
           	 scpath=Generic_Class.takeScreenShotPath();
  				image=logger.addScreenCapture(scpath);
  				logger.log(LogStatus.INFO, "Image",image);
           }

			

			
			
			
			
			
			
			
			
           ( (JavascriptExecutor)driver).executeScript("window.scrollBy(0,500)", 0);
	       Thread.sleep(2000);
			
			
			Thread.sleep(5000);
			advSearch.click_backToDashboardbtn();
		
			
			//Enter details " Name or phone" of a non existing customer at current location and click on "Find Customer" button and verify info message
			pmhomepage.enter_findCustAddrLocation("Nousinfo");
			logger.log(LogStatus.PASS, " Non existing customer First name entered successfully ");
			Thread.sleep(5000);
			pmhomepage.clk_findCustomer();
			logger.log(LogStatus.PASS, "Click on Find Customer Button successfully");

			Thread.sleep(5000);
		
			logger.log(LogStatus.INFO, "Advance Search page object is created successfully");
			Thread.sleep(5000);
			String searchResultTxt=advSearch.get_RecordsMatchedText();
			if(searchResultTxt.contains("NO RESULTS FOUND MATCHING YOUR CRITERIA")){
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Info Message is  successfully verified :"+searchResultTxt);
				logger.log(LogStatus.INFO, "Info Message is  successfully verified ",image);
			}
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Info Message is not  verified ");
				logger.log(LogStatus.INFO, "Info Message is not   verified ",image);

			}
			
			if(searchResultTxt.contains("NO RESULTS FOUND MATCHING YOUR CRITERIA")){
	        	if(advSearch.get_RecordsMatchedColor().equals("rgba(215, 0, 0, 1)")){
	        	
	                String scpath5=Generic_Class.takeScreenShotPath();
	                Reporter.log(scpath5,true);
	                String image5=logger.addScreenCapture(scpath5);
	                logger.log(LogStatus.PASS, "Information message verified with red colour :"+searchResultTxt);
	                logger.log(LogStatus.INFO, "Information message verified with red colour ",image5);
	        	}
	        }
			
			
			
			( (JavascriptExecutor)driver).executeScript("window.scrollBy(0,500)", 0);
		       Thread.sleep(2000);
			
			advSearch.click_backToDashboardbtn();
			Thread.sleep(4000);
			logger.log(LogStatus.PASS, "Click on Back To Dashboard Button successfully");
			
			//Do not enter any details on basic search and  click on "Find Customer" button
			pmhomepage.clk_findCustomer();
			logger.log(LogStatus.PASS, "Click on Find Customer Button successfully");
			
			//Verify Error message
			String errormsg="Please enter Name, Phone #, Space #, Account #, Driver License #, Military #, Passport #, State Issue #, or Email to search";
			Thread.sleep(4000);
			//String text =driver.findElement(By.xpath("html/body//div[@class='k-widget k-window']//div[contains(@class,'modal-content')]")).getText();
			String text =driver.findElement(By.xpath("html/body")).getText();
			if(text.contains(errormsg))
			{
				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.PASS, "Error Message is  successfully verified :"+errormsg);
				logger.log(LogStatus.INFO, "Error Message is  successfully verified ",image);
			}
			
			else{

				scpath=Generic_Class.takeScreenShotPath();
				Reporter.log(scpath,true);
				if(resultFlag.equals("pass"))
					resultFlag="fail";
				image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.FAIL, "Error Message is not  verified ");
				logger.log(LogStatus.INFO, "Error Message is not   verified ",image);

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
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","BasicSearch_Name" , "Status", "Pass");

		}else if (resultFlag.equals("fail")){

			Excel.setCellValBasedOnTcname(path, "CustomerSearch","BasicSearch_Name" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","BasicSearch_Name" , "Status", "Skip");
		}

		extent.endTest(logger);
		extent.flush();
	}
	
	
	
	
	
	
	
	
}
