package testScripts_walkin_Reservation;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.Walkin_Reservation_Lease.StandardStoragePage;
import Scenarios.Browser_Factory;

public class Customer_walksinto_changespace_onreservation extends Browser_Factory{

	ExtentTest logger;
	
	String resultFlag="fail";
	
	String path=Generic_Class.getPropertyValue("Excelpath");
	 
	    @DataProvider
		public Object[][] getData() {
		
			return Excel.getCellValue_inlist(path, "Reservation","Reservation",  "custWalkInto_ChngeSpcOnReservn");
		}
	    
	    @Test(dataProvider="getData")
		public void custWalkInto_ChngeSpcOnReservn(Hashtable<String, String> tabledata) throws InterruptedException 
		{
		
			if(!(tabledata.get("Runmode").equals("Y") && tabledata.get("Reservation").equals("Y"))){
				resultFlag="skip";
				  throw new SkipException("Skipping the test");
			}
			
			try{
				logger=extent.startTest("custWalkInto_ChngeSpcOnReservn", "ustomer walks in to change space on reservation");
				
				LoginPage loginPage=new LoginPage(driver);
				loginPage.login(tabledata.get("UserName"), tabledata.get("Password"));
				
				PM_Homepage Pmpage=new PM_Homepage(driver);
				
				if(tabledata.get("walkInCustTitle").equalsIgnoreCase(Pmpage.get_WlkInCustText().trim())){
					
					String scpath=Generic_Class.takeScreenShotPath();
			    	String image=logger.addScreenCapture(scpath);
			    	logger.log(LogStatus.PASS, "PM Dashboard is displayed successfully");
			    	logger.log(LogStatus.INFO, "PM Dashboard is displayed successfully",image);}
				
				else{
				String scpath=Generic_Class.takeScreenShotPath();
		    	String image=logger.addScreenCapture(scpath);
		    	logger.log(LogStatus.FAIL, "PM Dashboard is not displayed ");
		    	logger.log(LogStatus.INFO, "PM Dashboard is not displayed ",image);}
				Pmpage.clk_findAndLeaseSpace();
				logger.log(LogStatus.INFO, "Click on the find and lease button");
				
				StandardStoragePage stdpage=new StandardStoragePage(driver);
				if(stdpage.isdisplayed_StandardStorage()==true){
					
					String scpath=Generic_Class.takeScreenShotPath();
			    	String image=logger.addScreenCapture(scpath);
			    	logger.log(LogStatus.PASS, "satandard stroage page is displayed successfully");
			    	logger.log(LogStatus.INFO, "satandard stroage page is successfully",image);}
					
			     else{
					String scpath=Generic_Class.takeScreenShotPath();
			    	String image=logger.addScreenCapture(scpath);
			    	logger.log(LogStatus.PASS, "satandard stroage page is not displayed ");
			    	logger.log(LogStatus.INFO, "satandard stroage page is  not displayed",image);}
				
				
			
				resultFlag="pass";
				
				
				
			   }catch(Exception e){
				   e.getMessage();
			   }
	 
	
        }
	    
	   
	    
	    @AfterMethod
		public void afterMethod(){
			
			if(resultFlag.equals("pass")){
				
				Excel.setCellValBasedOnTcname(path, "Reservation","custWalkInto_ChngeSpcOnReservn" , "Status", "Pass");
				
			}else if (resultFlag.equals("fail")){
				
				Excel.setCellValBasedOnTcname(path, "Reservation","custWalkInto_ChngeSpcOnReservn" , "Status", "Fail");
			}else{
				Excel.setCellValBasedOnTcname(path, "Reservation","custWalkInto_ChngeSpcOnReservn" , "Status", "Skip");
			}
			
			
			 extent.endTest(logger);
			  extent.flush();
			
			
			
		}
	    
}
