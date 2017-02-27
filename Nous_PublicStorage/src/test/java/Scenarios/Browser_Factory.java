package Scenarios;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import GenericMethods.Excel;
import GenericMethods.Generic_Class;
import GenericMethods.NousListeners;


@Listeners(NousListeners.class)
public class Browser_Factory
{
	public WebDriver driver;
	public String testcaseName ="";

	String workingDirectory= System.getProperty("user.dir");
	String extent_path = Generic_Class.getPropertyValue("ExtentReportPath");
	String path=workingDirectory+(extent_path.replaceFirst(".",""));
	public ExtentTest logger;
	public ExtentReports extent;
	private static int extent_flag=1;
	
	@BeforeClass
	public void BrowserLaunch()
	{	
		
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("chrome.switches","--disable-extensions");
		System.setProperty("webdriver.chrome.driver","./src/main/resources/BrowserDrivers/chromedriver.exe");
		Reporter.log("Browser Launched Successfully",true);
		driver=new ChromeDriver(options);
		driver.get(Generic_Class.getPropertyValue("App_URL_AUT"));
	//driver.get("http://wc2qa.ps.com/LogIn");
		driver.manage().window().maximize();
		String versionName=driver.findElement(By.xpath("//div[@class='footer']")).getText().trim();
		String[] temp=versionName.split("-");
		versionName = (((temp[0].trim()).replace(" ", "_")).replaceAll("\\.", "_")).replace("#", "");
		System.out.println(versionName);
		path=workingDirectory+(extent_path.replaceFirst(".",""))+versionName+".html";
		System.out.println(path);
		
		if(extent_flag == 1){
			extent=new ExtentReports(path,true);
		}else{
			extent=new ExtentReports(path,false);
		}
		extent_flag++;
		
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);

	}
	
	@AfterClass
	public void closeBrowser()
	{
		extent.close();
		driver.quit();
	}

	@AfterSuite
	public void executionResultFile() throws IOException
	{
		/*Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf= new SimpleDateFormat("dd-mm-yy-hh:mm");
		String timestamp=sdf.format(cal.getTime());
		File srcHtmlPath=new File(Generic_Class.getPropertyValue("ExtentReportPath"));
		File destHtmlPath=new File("./src/main/resources/ExtentReports_BackUp/"+"MileStoneI_"+System.currentTimeMillis()+".html");
		Excel.copyHtmlFile(srcHtmlPath,destHtmlPath);
		String srcPath=Generic_Class.getPropertyValue("Excelpath");
		String destPath="./src/main/resources/Excel_Reports/"+"ExcelReport_"+timestamp+".xlsx";

		//TestDate file is copy to Excel Report file
		Excel.msExcel_Copy_File(srcPath,destPath);*/
		
		File srcHtmlPath=new File(Generic_Class.getPropertyValue("ExtentReportPath"));
		File destHtmlPath=new File(Generic_Class.getPropertyValue("ExtentReportBackUpPath")+"BackUp"+System.currentTimeMillis()+".html");
		Excel.copyHtmlFile(srcHtmlPath,destHtmlPath);
		String srcPath=Generic_Class.getPropertyValue("Excelpath");
		String destPath="./src/main/resources/Excel_Reports/"+"ExcelReport_"+System.currentTimeMillis()+".xlsx";

		//TestDate file is copy to Excel Report file
		Excel.msExcel_Copy_File(srcPath,destPath);

	}


}
