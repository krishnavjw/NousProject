package ProjectSpecificMethods;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;





public class PS_GenericMethods 
{
	public static void advSearchResult_ClickAccNum(WebDriver driver, List<WebElement> allRowval, String firtName, String lastName, String space)
	{
	
		//List<WebElement> allRowValues=driver.findElements(By.xpath("//div[@class='k-grid-content ps-container ps-active-y']/table/tbody/tr[contains(@class,'k-master-row')]"));
		
		Reporter.log(""+allRowval.size(),true);
		
		//for(WebElement rowValues:allRowval )
		for(int i=1;i<=allRowval.size();i++)
		{
			int flag=0;
			
			//Select select=new Select(rowValues);
			//List<WebElement> values=select.getOptions();
			List<WebElement> values = driver.findElements(By.xpath("(//div[@class='k-grid-content ps-container ps-active-y']/table/tbody/tr[contains(@class,'k-master-row')])["+i+"]/td"));
			for(WebElement colVal: values)
			{
//				Reporter.log("colVal 1"+colVal.getText(),true);
//				
//				String fn=colVal.getText();
//				if(fn.equals(colVal.getText().trim()))
//				{
//					Reporter.log(colVal.getText(),true);
//					flag++;
//				}
				
				if(lastName.equalsIgnoreCase(colVal.getText().trim()))
				{
					Reporter.log(colVal.getText(),true);
					flag++;
				}
					
				else if(space.equalsIgnoreCase(colVal.getText().trim()))
				{
					Reporter.log(colVal.getText(),true);
					flag++;
				}
						
			Reporter.log("flag"+ flag,true);
			
			if(flag==2)
			{
				//String accNum=DataBase_JDBC.executeSQLQuery("select AccountNumber from Table where FirstName=firstName && LastName=lastName");
				//div[@class='k-grid-content ps-container ps-active-y']/table/tbody//tr//td/a[text()='16926278']
				String accNum="21589973";
				String xapth= "//div[@class='k-grid-content ps-container ps-active-y']/table/tbody//tr//td/a[text()='"+accNum+"']";
				driver.findElement(By.xpath(xapth)).click();
				break;
				
			}
			
			
			}
			
		}
	}
	
	// For Future dates send +ve integer and for Past dates send -ve integer
	
	public static String getCalendarDate(int NumDaysToAdd)
	{
	       
	       Date date = new Date();
	       GregorianCalendar cal = new GregorianCalendar();
           cal.setTime(date);
           cal.add(Calendar.DATE, NumDaysToAdd);
	       SimpleDateFormat ft = new SimpleDateFormat ("E MM/dd/yyyy");
	       return ft.format(cal.getTime());
	       
	 }


//	public static Date addDays(Date date, int days) {
//	              GregorianCalendar cal = new GregorianCalendar();
//	              cal.setTime(date);
//	              cal.add(Calendar.DATE, days);
//	                           
//	              return cal.getTime();
//	       }

	
	
//	public static void login(WebDriver driver, String UN, String pwd)
//	{
//		LoginPage login=new LoginPage(driver);
//		login.userName.sendKeys(UN);
//		login.Pwd.sendKeys(pwd);
//		login.login_btn.click();
//		
//		
//	}

}
