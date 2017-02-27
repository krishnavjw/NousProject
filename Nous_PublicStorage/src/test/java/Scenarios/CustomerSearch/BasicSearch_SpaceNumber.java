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

public class BasicSearch_SpaceNumber extends Browser_Factory {
	
	String path="./src/main/resources/Resources/PS_TestData.xlsx";
	public ExtentTest logger;
	String resultFlag="pass";

	
	@DataProvider
	public Object[][] getCustSearchSpace() 
	{
		return Excel.getCellValue_inlist(path, "CustomerSearch","CustomerSearch","BasicSearch_SpaceNumber");
	}
	

	@Test(dataProvider="getCustSearchSpace")	
	public void BasicSearchSpaceNumber(Hashtable<String, String> tabledata) throws Exception 
	{
		
		if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerSearch").equals("Y")))
		{
			 resultFlag="skip";
             throw new SkipException("Skipping the test");
		}
			
		try{
		    logger=extent.startTest("BasicSearchSpaceNumber","Customer Search with Space Number");

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
			Thread.sleep(5000);
	        
	        Advance_Search advSearch= new Advance_Search(driver);
	        logger.log(LogStatus.INFO, "Advance Search page object is created successfully");
	      
	        
	        //Verify the text entry field is present with text "Find a Customer at this Location"
	        PM_Homepage pmhomepage=new PM_Homepage(driver);     

	        String getTextTextEntryFieldData = "Find a Customer at this Location:";
	        String getTextTextEntryFieldWeb = pmhomepage.get_findACustomerAtThisLocText().trim();
	      
	        if(getTextTextEntryFieldData.equalsIgnoreCase(getTextTextEntryFieldWeb))
	        {

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
	        
	        //Verify the "Find Customer" button is available
	        String getCustomerButtonNameData = "Find Customer";
	        String getCustomerButtonNameWeb = pmhomepage.get_findACustomerText().trim();
	        if(getCustomerButtonNameData.equalsIgnoreCase(getCustomerButtonNameWeb))
	        {

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
	                logger.log(LogStatus.FAIL, "Find Customer button is not displayed ");
	                logger.log(LogStatus.INFO, "Find Customer button is not displayed ",image);

	        }
	        Thread.sleep(5000);
	        
	        //Verify User should view the module Existing Customer
	        if(pmhomepage.get_existingCustomerText().equalsIgnoreCase("Existing Customer")){

	                String scpath=Generic_Class.takeScreenShotPath();
	                Reporter.log(scpath,true);
	                String image=logger.addScreenCapture(scpath);
	                logger.log(LogStatus.PASS, "Existing Customer module displayed successfully:"+pmhomepage.get_existingCustomerText());
	                logger.log(LogStatus.INFO, "Existing Customer module displayed successfully",image);
	        }
	        else{

	                String scpath=Generic_Class.takeScreenShotPath();
	                Reporter.log(scpath,true);
	                if(resultFlag.equals("pass"))
	                                resultFlag="fail";
	                String image=logger.addScreenCapture(scpath);
	                logger.log(LogStatus.FAIL, "Existing Customer module not displayed");
	                logger.log(LogStatus.INFO, "Existing Customer module not displayed",image);

	        }
	        
	        
	        //Enter "Space Number" of an existing customer at current location and click on "Find Customer" button

	        pmhomepage.enter_findCustAddrLocation(tabledata.get("SpaceNumber"));
	        logger.log(LogStatus.INFO, "Existing Customer Space Number is entered successfully");
	        Thread.sleep(5000);
	        pmhomepage.clk_findCustomer();
	        logger.log(LogStatus.INFO, "Click on Find Customer Button successfully");
	        Thread.sleep(5000);
	        String location = advSearch.getLocationNum();
	        Thread.sleep(5000);
	        String actualRecord=advSearch.get_RecordsMatchedText();
	        if(actualRecord.contains("Records Matched")){
	                String scpath=Generic_Class.takeScreenShotPath();
	                Reporter.log(scpath,true);
	                String image=logger.addScreenCapture(scpath);
	                logger.log(LogStatus.PASS, "Actual Matching record displayed successfully");
	                logger.log(LogStatus.INFO, "Actual Matching record displayed successfully ",image);
	        }
	        else{

	                String scpath=Generic_Class.takeScreenShotPath();
	                Reporter.log(scpath,true);
	                if(resultFlag.equals("pass"))
	                                resultFlag="fail";
	                String image=logger.addScreenCapture(scpath);
	                logger.log(LogStatus.FAIL, "Actual Matching record is not displayed ");
	                logger.log(LogStatus.INFO, "Actual Matching record is not displayed ",image);

	        }

	        String SQLQuery = "select DISTINCT a.accountnumber, a.accountnumber, c.lastname from contact c with (nolock) "+ 
					"join customer cu with (nolock) on cu.contactid=c.contactid join account a on a.customerid=cu.customerid "
					+"join accountorder ao with (nolock) on ao.accountid=a.accountid "
					+"join accountorderitem aoi with (nolock) on aoi.accountorderid=ao.accountorderid "
					+"join storageorderitem soi with (nolock) on soi.storageorderitemid=aoi.storageorderitemid "
					+"join rentalunit ru with (nolock) on ru.rentalunitid=soi.rentalunitid "
					+"join type t with (nolock) on t.typeid=cu.customertypeid "
					+"join site s with (nolock) on s.siteid=aoi.siteid "
					+"left join accountphone ap with (nolock) on ap.accountid=a.accountid "
					+"left join accountemail ae with (nolock) on ae.accountid=a.accountid "
					+"left join phone p with (nolock) on p.phoneid=ap.phoneid "
					+"left join emailaddress ea with (nolock) on ea.emailaddressid=ae.emailid "
					+"where soi.vacatedate is null and ru.rentalunitnumber='"+tabledata.get("SpaceNumber")+"' and s.sitenumber='"+location+"' order by c.lastname";


			String SQLQueryCnt = "select count(DISTINCT a.accountnumber) from contact c with (nolock) " + 
					"join customer cu with (nolock) on cu.contactid=c.contactid join account a on a.customerid=cu.customerid "
					+"join accountorder ao with (nolock) on ao.accountid=a.accountid "
					+"join accountorderitem aoi with (nolock) on aoi.accountorderid=ao.accountorderid "
					+"join storageorderitem soi with (nolock) on soi.storageorderitemid=aoi.storageorderitemid "
					+"join rentalunit ru with (nolock) on ru.rentalunitid=soi.rentalunitid "
					+"join type t with (nolock) on t.typeid=cu.customertypeid "
					+"join site s with (nolock) on s.siteid=aoi.siteid "
					+"left join accountphone ap with (nolock) on ap.accountid=a.accountid "
					+"left join accountemail ae with (nolock) on ae.accountid=a.accountid "
					+"left join phone p with (nolock) on p.phoneid=ap.phoneid "
					+"left join emailaddress ea with (nolock) on ea.emailaddressid=ae.emailid "
					+"where soi.vacatedate is null and ru.rentalunitnumber='"+tabledata.get("SpaceNumber")+"' and s.sitenumber='"+location+"'";

			String ele2 = DataBase_JDBC.executeSQLQuery(SQLQueryCnt);
			
			

			((JavascriptExecutor)driver).executeScript("window.scrollTo(-2000, 0)");
			((JavascriptExecutor)driver).executeScript("window.scrollTo(0, 500)");
			
			

			boolean noResults = false;
			 List<WebElement> list2=driver.findElements(By.xpath("//table[@class='k-selectable']/tbody/tr"));
			 if(list2.get(0).getText().equalsIgnoreCase("NO ITEMS TO DISPLAY")){
				 list2.remove(0);
				 noResults = true;
			 }
			 
			 /*  if(list2.size()== Integer.parseInt(ele2)){

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
       }*/

			  String scpath=Generic_Class.takeScreenShotPath();
				String image=logger.addScreenCapture(scpath);
				logger.log(LogStatus.INFO, "Image",image);
            String searchResultsName= advSearch.validateSearchResultsName();
            if(searchResultsName.contains(tabledata.get("SpaceNumber"))){
                  logger.log(LogStatus.PASS, "Validated Space Number Search in advance search results grid");
            }else{
            	 logger.log(LogStatus.FAIL, "Validation failed for Space Number Search in advance search results grid");
            	 resultFlag="fail";
            }

            
			
			
			
            ArrayList<String> dbAccNums = DataBase_JDBC.executeSQLQuery_List_SecondCol(SQLQuery);
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

		
	        Thread.sleep(5000);
	        advSearch.click_backToDashboardbtn();
	        
	        
	        //Enter details Space number of a non existing customer at current location and click on "Find Customer" button and verify info message
	        pmhomepage.enter_findCustAddrLocation("AAYGG");
	        logger.log(LogStatus.INFO, "Non existing customer Space Number entered successfully ");
	        Thread.sleep(5000);
	        pmhomepage.clk_findCustomer();
	        logger.log(LogStatus.INFO, "Clicked on Find Customer Button successfully");

	        Thread.sleep(5000);
	        String searchResultTxt=advSearch.get_RecordsMatchedText();
	        
	        if(searchResultTxt.contains("NO RESULTS FOUND MATCHING YOUR CRITERIA")){
	        	if(advSearch.get_RecordsMatchedColor().equals("rgba(255, 0, 0, 1)")){
	        	
	                scpath=Generic_Class.takeScreenShotPath();
	                Reporter.log(scpath,true);
	                image=logger.addScreenCapture(scpath);
	                logger.log(LogStatus.PASS, "Information message verified with red colour :"+searchResultTxt);
	                logger.log(LogStatus.INFO, "Information message verified with red colour ",image);
	        	}
	        }
	        else{

	                scpath=Generic_Class.takeScreenShotPath();
	                Reporter.log(scpath,true);
	                if(resultFlag.equals("pass"))
	                          resultFlag="fail";
	                image=logger.addScreenCapture(scpath);
	                logger.log(LogStatus.FAIL, "Information Message is not verified ");
	                logger.log(LogStatus.INFO, "Information Message is not verified ",image);

	        }
	       ( (JavascriptExecutor)driver).executeScript("window.scrollBy(0,500)", 0);
	       Thread.sleep(2000);
	        advSearch.click_backToDashboardbtn();
	        Thread.sleep(4000);
	        logger.log(LogStatus.INFO, "Clicked on Back To Dashboard Button successfully");
	        
	        
	        
	        //Verify the information message When Clicking on Find Customer Button
	        PM_Homepage pmHome =new PM_Homepage(driver);
	        pmHome.clk_findCustomer();
	        logger.log(LogStatus.INFO, "Clicked on find customer button without entering anything");
	        Thread.sleep(10000);
	        
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
			
	        
	        
	        
		}catch(Exception e){
			e.printStackTrace();
			resultFlag = "fail";
			logger.log(LogStatus.FAIL, "Test script failed due to the exception "+e);
		}
	  }


		@AfterMethod
		public void afterMethod(){
		
		Reporter.log(resultFlag,true);
		
		if(resultFlag.equals("pass")){
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","BasicSearch_SpaceNumber" , "Status", "Pass");
			
		}else if (resultFlag.equals("fail")){
			
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","BasicSearch_SpaceNumber" , "Status", "Fail");
		}else{
			Excel.setCellValBasedOnTcname(path, "CustomerSearch","BasicSearch_SpaceNumber" , "Status", "Skip");
			}
			
			 extent.endTest(logger);
			 extent.flush();
			
		}
	

}
