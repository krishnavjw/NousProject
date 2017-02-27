package Scenarios;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.gargoylesoftware.htmlunit.javascript.host.dom.Document;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import Pages.AdvSearchPages.Adv_SearchResultPage;
import Pages.AdvSearchPages.Advance_Search;
import Pages.CustDashboardPages.Acc_SpaceDetailsPage;
import Pages.CustDashboardPages.Cust_AccDetailsPage;
import Pages.HomePages.Dashboard_BifrostHostPopUp;
import Pages.HomePages.LoginPage;
import Pages.HomePages.PM_Homepage;
import Pages.VacatePages.ScheduleVacate_SelectSpacePage;
import ProjectSpecificMethods.PS_GenericMethods;

public class CustSpace_ScheduleVacate extends Browser_Factory
{
	public ExtentTest logger;
	
	@Test
	public void scheduleVacate_Space() throws InterruptedException
	{
		String conPath=Generic_Class.getPropertyValue("CPATH");
		String status=Excel.getCellValue_ByColName(conPath,0,"CustSpace_ScheduleVacate","Flag");
		if((status.toString()).equals("No"))
		{
			Reporter.log("True",true);
			throw new SkipException("Skipping the TestDemo TestCase");
		
		}
			
		
    	logger=extent.startTest("CustSpace_ScheduleVacate","Schedule customer Space for Vacate ");
		LoginPage login= new LoginPage(driver);
		logger.log(LogStatus.INFO, "Login Page object is created successfully");
		login.enterUserName("123015");
		logger.log(LogStatus.INFO, "UserName entered successfully");
		login.enterPassword("password");
	    logger.log(LogStatus.INFO, "Password entered successfully");
	    login.clickLogin();
	    logger.log(LogStatus.INFO, "Click on Login button successfully");
	    
	    Dashboard_BifrostHostPopUp Bifrostpop= new Dashboard_BifrostHostPopUp(driver);
		logger.log(LogStatus.INFO, "PopUp window object is created successfully");

		Bifrostpop.clickContiDevice();
		logger.log(LogStatus.INFO, "Click on Continue without device button successfully");
		
		PM_Homepage hp= new PM_Homepage(driver);
		logger.log(LogStatus.INFO, "Home Page object is created successfully");
		
		hp.clk_AdvSearchLnk();
		logger.log(LogStatus.INFO, "Click on Advance Search link successfully");
		
	    Thread.sleep(10000);
		Advance_Search advSearch= new Advance_Search(driver);
		logger.log(LogStatus.INFO, "Advance Search page object is created successfully");
		
		
		//advSearch.enterLocationNumber("21601");
		logger.log(LogStatus.INFO, "Enter the Location number into Location field successfully");
		
		Thread.sleep(1000);
		
		advSearch.enterLastName("smith");
		logger.log(LogStatus.INFO, "Enter the Location number into Location field successfully");
		
	
        ((JavascriptExecutor)driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
		Thread.sleep(2000);

		advSearch.clickSearchbtn();
		logger.log(LogStatus.INFO, "clicking on advance Search button successfully");
		
		
		Adv_SearchResultPage searchResult= new Adv_SearchResultPage(driver);
		
		//searchResult.clickAdvSearchResult_AccNum("smith", "A090");
		
		Cust_AccDetailsPage cust_dash= new Cust_AccDetailsPage(driver);
		
		cust_dash.clickSpaceDetails_tab();
		
		Acc_SpaceDetailsPage spaceDel= new Acc_SpaceDetailsPage(driver);
		spaceDel.clickSchVacate_Btn();
		
		ScheduleVacate_SelectSpacePage selectSpace= new ScheduleVacate_SelectSpacePage(driver);
		
		selectSpace.clickCalendar_Field("A090");
		
		String date=PS_GenericMethods.getCalendarDate(3);
		
		//((JavascriptExecutor)driver).executeScript(Document.getElementByxpath("//div[@id='unitgridtabstrip']//table/tbody//tr//td//div[starts-with(@id,'schedule-vacate-date')]/span").value=date);
		
	}

}
