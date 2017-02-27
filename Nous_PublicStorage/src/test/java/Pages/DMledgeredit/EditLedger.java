package Pages.DMledgeredit;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

public class EditLedger {

	WebDriver driver;
		
	@FindBy(xpath="//div[@id='general-ledger-adjustment-view']//span[@class='k-widget k-dropdown k-header unit-options dropdown-options']")
	private WebElement ledger_dropdown;

	
		
	
	
	@FindBy(xpath="//textarea[@id='AdjustmentNote']")
	private WebElement textare_ledger;

	public EditLedger(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);

	}

	
	
	public void Enter_note(String note){
		textare_ledger.sendKeys(note);
		
	}
	
	
	//div[@id='glaGrid']//table//tr[1]/td[3]//input

	public void ledger_valueentery() throws InterruptedException{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		List<WebElement> numledger= driver.findElements(By.xpath("//div[@id='glaGrid']//table//tr"));
			Thread.sleep(3000);
	        for (int i=1; i<numledger.size()-1;i++) {
	        	Thread.sleep(2000);
	      
         WebElement element =driver.findElement(By.xpath("//div[@id='glaGrid']//table//tr["+i+"]/td[3]//input"));
       
         Thread.sleep(2000);
         js.executeScript("arguments[0].scrollIntoView(true);",element);
         element.sendKeys("10.00");
         driver.findElement(By.xpath("//div[@id='glaGrid']//table//tr["+i+"]/td[1]")).click();
        
         
	        }
	}
	
	public void ledger_dropdown(String inputValue,WebDriver driver) throws InterruptedException{
		ledger_dropdown.click();
		Thread.sleep(2000);
		List<WebElement> ListWbEle1=driver.findElements(By.xpath("//div[@class='k-list-container k-popup k-group k-reset k-state-border-up']/ul/li"));
		
		
        int Size = ListWbEle1.size();
        for (int i = 0; i < Size; i++) {
        	
              if (ListWbEle1.get(i).getText().contains(inputValue)) {
            	  System.out.println("--"+ListWbEle1.get(i).getText());
            	  System.out.println("--"+inputValue);
                    Actions builder = new Actions(driver);
                    builder.moveToElement(ListWbEle1.get(i)).click().build().perform();
                    break;
              }
        }


	}
	
	@FindBy(xpath="//a[contains(text(),' Edit General Ledger')]")
	private WebElement editLedgerLink;
	
	public boolean isDisplayed_EditLedgerLink(){
		return editLedgerLink.isDisplayed();
	}
	
	
	public void clickONEditLedgerLink(){
		editLedgerLink.click();
	}


	
	
	
}



