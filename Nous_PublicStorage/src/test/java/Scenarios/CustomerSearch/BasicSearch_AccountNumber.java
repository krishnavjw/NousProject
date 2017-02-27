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
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Scenarios.Browser_Factory;

public class BasicSearch_AccountNumber extends Browser_Factory {
	
    public ExtentTest logger;
    String resultFlag="pass";
    String path = Generic_Class.getPropertyValue("Excelpath");

    @DataProvider
    public Object[][] getCustSearchAccount() 
    {
        return Excel.getCellValue_inlist(path, "CustomerSearch","CustomerSearch","BasicSearch_AccountNumber");
    }

    @Test(dataProvider="getCustSearchAccount")
    public void BasicSearchAccountNumber(Hashtable<String, String> tabledata) throws InterruptedException 
    {

        if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("CustomerSearch").equals("Y")))
        {
            resultFlag="skip";
            throw new SkipException("Skipping the test");
        }

        try{

        //Login To the Application
        logger=extent.startTest("BasicSearchAccountNumber", "Customer Search with Account Number");
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
		Thread.sleep(16000);
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


        //Verify the text entry field is present with text "Find a Customer" at this Location
        PM_Homepage pmhomepage=new PM_Homepage(driver);   
        String location = pmhomepage.getLocation();

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
                logger.log(LogStatus.FAIL, "TextEntryField is not displayed");
                logger.log(LogStatus.INFO, "TextEntryField is not displayed",image);

        }
        Thread.sleep(5000);
        
        //Verify "Find Customer" button is available
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
        
        String sqlQuery = "select top 1 a.accountnumber"+
                " from contact c"+
                " join customer cu on cu.contactid=c.contactid"+
                " join account a on a.customerid=cu.customerid"+
                " join accountorder ao on ao.accountid=a.accountid"+
                " join accountorderitem aoi on aoi.accountorderid=ao.accountorderid"+
                " join storageorderitem soi on soi.storageorderitemid=aoi.storageorderitemid"+
                " join rentalunit ru on ru.rentalunitid=soi.rentalunitid"+
                " join type t on t.typeid=cu.customertypeid"+
                " join site s on s.siteid=aoi.siteid"+
                " where soi.vacatedate is null"+
                " and cu.customertypeid=90"+
                " and s.isactive=1"+
                " and  s.sitenumber='"+location+"'";

        String accNum = DataBase_JDBC.executeSQLQuery(sqlQuery);
        //String accNum = tabledata.get("AccountNumber");

        //Enter "Account Number" of an existing customer at current location and click on "Find Customer" button

        pmhomepage.enter_findCustAddrLocation(accNum);
        logger.log(LogStatus.PASS, "Existing Customer Account Number entered successfully");
        Thread.sleep(5000);
        pmhomepage.clk_findCustomer();
        logger.log(LogStatus.PASS, "Clicked on Find Customer Button successfully");

        Thread.sleep(5000);
        Advance_Search advSearch= new Advance_Search(driver);
        logger.log(LogStatus.INFO, "Advance Search page object is created successfully");
        Thread.sleep(5000);
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
        
        //Scrolling to verify with Account Number
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(2000,0)");
        Thread.sleep(2000);
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

        Thread.sleep(4000);
        
        //Verify that Customer Account Number search is on the database fields 
       
        sqlQuery = "select count(*) "+
                "from contact c "+
                "join customer cu on cu.contactid=c.contactid "+
                "join account a on a.customerid=cu.customerid "+
                "join accountorder ao on ao.accountid=a.accountid "+
                "join accountorderitem aoi on aoi.accountorderid=ao.accountorderid "+
                "join storageorderitem soi on soi.storageorderitemid=aoi.storageorderitemid "+
                "join rentalunit ru on ru.rentalunitid=soi.rentalunitid "+
                "join type t on t.typeid=cu.customertypeid "+
                "join site s on s.siteid=aoi.siteid "+
                "where soi.vacatedate is null "+
                "and a.accountnumber='"+accNum+"' and s.sitenumber='"+location+"'";

                                        
        
        String ele3 = DataBase_JDBC.executeSQLQuery(sqlQuery);
		Thread.sleep(15000);
		
		int numberOfRec = Integer.parseInt(ele3);
		if(numberOfRec>0){
			
			List<WebElement> resultRows = driver.findElements(By.xpath("//table[@class='k-selectable']/tbody/tr"));
			int rowSize = resultRows.size();
			int count = 0;
			for(int i=1; i<=rowSize; i++){
				
				String getText = resultRows.get(i-1).findElement(By.xpath("./td[11]")).getText();
		        
		        if(getText.contains(accNum)){
		        	count++;
		        }
			}
			
			if(count == rowSize){
				
				   String scpath=Generic_Class.takeScreenShotPath();
				   Reporter.log(scpath,true);
				   String image=logger.addScreenCapture(scpath);
				   logger.log(LogStatus.INFO, "Account Number search results verified with the db result",image);
				   logger.log(LogStatus.PASS, "Account Number search results verified with the db result");
				   
			}else{
				
				   String scpath=Generic_Class.takeScreenShotPath();
				   Reporter.log(scpath,true);
				   String image=logger.addScreenCapture(scpath);
				   if(resultFlag.equals("pass"))
	                   resultFlag="fail";
				   logger.log(LogStatus.INFO, "Account Number search results not matched with the db result",image);
				   logger.log(LogStatus.FAIL, "Account Number search results not matched with the db result ");
			}
			
		}else{
			
				   String scpath=Generic_Class.takeScreenShotPath();
				   Reporter.log(scpath,true);
				   String image=logger.addScreenCapture(scpath);
				   if(resultFlag.equals("pass"))
                         resultFlag="fail";
				   logger.log(LogStatus.INFO, "Account Number search results not matched with the db result. No Items to display message not displayed ",image);
				   logger.log(LogStatus.FAIL, "Account Number search results not matched with the db result. No Items to display message not displayed ");
			
		}

		Thread.sleep(5000);
	    advSearch.click_backToDashboardbtn();
        
        //Enter details Account Number of a non existing customer at current location and click on "Find Customer" button and verify info message
        pmhomepage.enter_findCustAddrLocation("1114587454");
        logger.log(LogStatus.INFO, "Non existing Customer Account Number entered successfully ");
        Thread.sleep(5000);
        pmhomepage.clk_findCustomer();
        logger.log(LogStatus.INFO, "Clicked on Find Customer Button successfully");

        Thread.sleep(10000);
        String searchResultTxt=advSearch.get_RecordsMatchedText();
        if(searchResultTxt.contains("NO RESULTS FOUND MATCHING YOUR CRITERIA")){
        	if(advSearch.get_RecordsMatchedColor().equals("rgba(255, 0, 0, 1)")){
                String scpath=Generic_Class.takeScreenShotPath();
                Reporter.log(scpath,true);
                String image=logger.addScreenCapture(scpath);
                logger.log(LogStatus.PASS, "Information Message is successfully verified with red colour :"+searchResultTxt);
                logger.log(LogStatus.INFO, "Information Message is successfully verified with red colour",image);
        	}
        }
        else{

                String scpath=Generic_Class.takeScreenShotPath();
                Reporter.log(scpath,true);
                if(resultFlag.equals("pass"))
                                resultFlag="fail";
                String image=logger.addScreenCapture(scpath);
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
        logger.log(LogStatus.INFO, "Clicked on find customer button without entering");
        Thread.sleep(10000);
        
      //Verify Error message
		String errormsg="Please enter Name, Phone #, Space #, Account #, Driver License #, Military #, Passport #, State Issue #, or Email to search";
		Thread.sleep(4000);
		//String text =driver.findElement(By.xpath("html/body//div[@class='k-widget k-window']//div[contains(@class,'modal-content')]")).getText();
		String text =driver.findElement(By.xpath("html/body")).getText();
		if(text.contains(errormsg))
		{
			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			String image=logger.addScreenCapture(scpath);
			logger.log(LogStatus.PASS, "Error Message is  successfully verified :"+errormsg);
			logger.log(LogStatus.INFO, "Error Message is  successfully verified ",image);
		}
		
		else{

			String scpath=Generic_Class.takeScreenShotPath();
			Reporter.log(scpath,true);
			if(resultFlag.equals("pass"))
				resultFlag="fail";
			String image=logger.addScreenCapture(scpath);
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
                    Excel.setCellValBasedOnTcname(path, "CustomerSearch","BasicSearch_AccountNumber" , "Status", "Pass");

            }else if (resultFlag.equals("fail")){

                    Excel.setCellValBasedOnTcname(path, "CustomerSearch","BasicSearch_AccountNumber" , "Status", "Fail");
            }else{
                    Excel.setCellValBasedOnTcname(path, "CustomerSearch","BasicSearch_AccountNumber" , "Status", "Skip");
            }

            extent.endTest(logger);
            extent.flush();
    }
    
    
    

	

}
