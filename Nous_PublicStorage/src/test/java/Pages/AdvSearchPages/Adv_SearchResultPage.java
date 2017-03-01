package Pages.AdvSearchPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import GenericMethods.Excel;
import GenericMethods.Generic_Class;

public class Adv_SearchResultPage 
{
	//test line
	WebDriver driver;
	
	@FindBy(xpath="//div[@class='k-grid-content ps-container ps-active-y']/table/tbody/tr[contains(@class,'k-master-row')]")
	private List<WebElement> allRowVals;    
	
	@FindBy(xpath="//table[@class='k-selectable']/tbody//tr//td//div[@class='padding-top float-left address']//div[@class='address-data']")
	private WebElement cust_Address; 
	
	public Adv_SearchResultPage(WebDriver driver)
	
	{
		driver=this.driver;
		PageFactory.initElements(driver,this);
	}
	
	
	
	public void clickAdvSearchResult_AccNum(String lastName, String space, String accNum)
	{
		//List<WebElement> allRowValues=driver.findElements(By.xpath("//div[@class='k-grid-content ps-container ps-active-y']/table/tbody/tr[contains(@class,'k-master-row')]"));
		
				//Reporter.log(""+allRowval.size(),true);
				
				//for(WebElement rowValues:allRowval )
				for(int i=1;i<=allRowVals.size();i++)
				{
					int flag=0;
					
					//Select select=new Select(rowValues);
					//List<WebElement> values=select.getOptions();
					List<WebElement> values = driver.findElements(By.xpath("(//div[@class='k-grid-content ps-container ps-active-y']/table/tbody/tr[contains(@class,'k-master-row')])["+i+"]/td"));
					for(WebElement colVal: values)
					{
//						Reporter.log("colVal 1"+colVal.getText(),true);
//						
//						String fn=colVal.getText();
//						if(fn.equals(colVal.getText().trim()))
//						{
//							Reporter.log(colVal.getText(),true);
//							flag++;
//						}
						
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
						//String accNum="19421043";
						
						String xapth= "//div[@class='k-grid-content ps-container ps-active-y']/table/tbody//tr//td/a[text()='"+accNum+"']";
						driver.findElement(By.xpath(xapth)).click();
						break;
						
					}
					
					
					}
				
	}
	
  }
	
	
	public void clickExpandlink_ResultGrid(String lastName, String space)
	{
		for(int i=1;i<=allRowVals.size();i++)
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
				
				if(flag==2)
				{
					String xpath="//table/tbody/tr["+i+"]/td[@class='k-hierarchy-cell']";
					driver.findElement(By.xpath(xpath)).click();
				}
					
				}
	}
}
	
	
	
//-----------------------------------------------------------------------------------------------
		//Method : spaceValdtnOnGrid_ClickOnAccLnk
		//Description : Method is used to validate the space on grid and if present means click on account number to navigate to the customer dashboard
		//Author : Testing Team
	

	public void spaceValdtnOnGrid_ClickOnAccLnk(String space,String accNum){
        
        String xpath_start="//div[@class='k-grid-content ps-container']/table/tbody/tr[";
        String xpath_end="]/td[6]";
        int i=1;
        
        while(isElementPresent(xpath_start+i+xpath_end)){
              
              String spaceno=driver.findElement(By.xpath(xpath_start+i+xpath_end)).getText();
              if(space.equalsIgnoreCase(spaceno.trim())){
                    
              
                    String xpath="//div[@class='k-grid-content ps-container']/table/tbody//tr//td/a[text()='"+accNum+"']";
                    
                    driver.findElement(By.xpath(xpath)).click();
                    break;
              
              }
              }
                          
              i++;
        }
        
 
  public boolean isElementPresent(String elementXpath){
        int count=driver.findElements(By.xpath(elementXpath)).size();
        if(count==0)
        return false;
        else
        return true;
        }


//-----------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
		
}
	

