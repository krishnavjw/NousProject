package Pages.MiscallaneousCharges;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Misccharges {

	WebDriver driver;
		
	@FindBy(xpath="//a[contains(text(),'Add Charge')]")
	private WebElement Add_charges;

	
	@FindBy(xpath="//div[@id='glaGrid']//table//tr")
	private WebElement ledger_note;
	
	
	
	@FindBy(xpath="//label[@id='damages-in-space']/span[@class='button']")
	private WebElement demage_charge;
	
	@FindBy(xpath="//label[@id='goods-in-property']/span[@class='button']")
	private WebElement good_in_property;
	
	
	@FindBy(xpath="(//label[@class='webchamp-checkbox']/span[@class='button'])[1]")
	private WebElement webchamp_checkbox;
	
	@FindBy(xpath="(//div[@class='maintenance-issue-radio-buttons floatleft']/ul/li/label/span[@class='button'])[1]")
	private WebElement maintenance_issue_checkbox;
	
	@FindBy(xpath="//div[@id='maintenance-required-container']//span[@class='k-widget k-dropdown k-header half-margin-left']")
	private WebElement maintainance;
	
	@FindBy(xpath="//a[contains(text(),'Save')]")
	private WebElement save;
	
	
	@FindBy(xpath="//a[@onclick='MiscellaneousCharge.removeCharge(253)']")
	private WebElement removeDamage;
	
	@FindBy(xpath="//a[@onclick='MiscellaneousCharge.removeCharge(252)']")
	private WebElement removecleaning;
	
	
	

	public Misccharges(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);

	}

	public void maintenance_issue_checkbox(){
		maintenance_issue_checkbox.click();
		
	}
	
	public void Add_charges(){
		Add_charges.click();
		
	}
	
	public void save_button(){
		save.click();
		
	}
	
	public void webchamp_checkbox(){
		webchamp_checkbox.click();
		
	}
	
	public void demage_charge(){
		demage_charge.click();
		
	}
	
	public void good_in_property(){
		good_in_property.click();
		
	}
	
	public void ledger_note(int notenum, String note){
		driver.findElement(By.xpath("(//div[@class='structure note']/textarea[@class='note charge-note webchamp-textbox'])["+notenum+"]")).sendKeys(note);
		
	}
	
	public String misc_amount(int notenum){
		return driver.findElement(By.xpath("(//input[@class=' amount webchamp-textbox'])["+notenum+"]")).getText();
		
	}
	
	public void maintenace(String inputValue,WebDriver driver) throws InterruptedException{
		maintainance.click();
		Thread.sleep(2000);
		List<WebElement> ListWbEle2=driver.findElements(By.xpath("//div[@id='MaintenanceReason-list']/ul/li"));
		
		for(int i=0; i<ListWbEle2.size();i++){
			System.out.println(" the elements are--"+ListWbEle2.get(i).getText());
		}	
		
		int Size = ListWbEle2.size();
        for (int i = 0; i < Size; i++) {
        	
              if (ListWbEle2.get(i).getText().equals(inputValue)) {
            	  System.out.println("--"+ListWbEle2.get(i).getText());
            	  System.out.println("--"+inputValue);
                    Actions builder = new Actions(driver);
                    builder.moveToElement(ListWbEle2.get(i)).click().build().perform();
                    break;
              }
        }

	}
	
	
	
	public String misc_amount_abandonedgoodsfeesmall(){
		return driver.findElement(By.xpath("//div[@class='added-charge charge-type-254 clearfix-container margin-bottom']//input[@class=' amount webchamp-textbox']")).getAttribute("value");
	}
	
	public void clk_RemoveDamageBtn()
	{
		removeDamage.click();
	}
	
	public void clk_RemovecleaningBtn()
	{
		removecleaning.click();
	}


	
	public String misc_amount(){
		return driver.findElement(By.xpath("//div[contains(@class,'added-charge charge-type-254')]//input[@class=' amount webchamp-textbox']")).getAttribute("value");
		
	}
	
	
	
	
}



