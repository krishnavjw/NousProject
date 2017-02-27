package Pages.CustInfoPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Cust_ResaleOrTaxExemptionsPage {
	
	@FindBy(xpath="//h3[text()='Resale/Tax Exemptions']")
	private WebElement ResaleOrTaxExemptions_Title;
	
	@FindBy(xpath="//span[text()='Customer is no longer tax exempt']/preceding-sibling::span[@class='button']")
	private WebElement customerNoLongrPresent_chkbx;
	
	@FindBy(id="TaxExemptions__-index-__0__TaxExempt_EncryptedItem_EncryptedIdDisplay")
	private WebElement taxExemptNumber;
	
	@FindBy(xpath="//span[text()='No Expiration Date']/preceding-sibling::span[@class='button']")
	private WebElement noExpirationDateBtn;
	
	@FindBy(id="TaxExemptions__-index-__0__TaxExempt_ExpirationMonth")
	private WebElement expiry_month;
	
	@FindBy(id="TaxExemptions__-index-__0__TaxExempt_ExpirationDay")
	private WebElement expiry_Date;
	
	@FindBy(id="TaxExemptions__-index-__0__TaxExempt_ExpirationYear")
	private WebElement expiry_year;
	
	@FindBy(xpath="//a[text()='Upload']")
	private WebElement upload;
	
	@FindBy(id="confirmButton")
	private WebElement confirmWithCustomer;
	
	@FindBy(linkText="Approve")
	private WebElement approve_Btn;
	
	@FindBy(linkText="Save")
	private WebElement save_Btn;

	private WebDriver driver;
	
	public Cust_ResaleOrTaxExemptionsPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
		this.driver=driver;
	}

	public boolean verify_ResaleOrTaxExemptions_Title()
	{
		return ResaleOrTaxExemptions_Title.isDisplayed();
	}

	public boolean verify_customerNoLongrPresent_chkbx()
	{
		return customerNoLongrPresent_chkbx.isDisplayed();
	}

	public void click_customerNoLongrPresent_chkbx()
	{
		 customerNoLongrPresent_chkbx.click();;
	}
	
	public void select_State() throws InterruptedException{
		  driver.findElement(By.xpath("//span[text()='Select State']")).click();
          Thread.sleep(2000);
	List<WebElement> stateOptions = driver
            .findElements(By.xpath("//ul[@class='k-list k-reset ps-container ps-active-y']/li"));
for (WebElement allOptions : stateOptions) {
      if (allOptions.getText().equals("AA")) {
            allOptions.click();
            break;
      }
}}
	
	public void enter_taxExemptNumber(String value)
	{
		taxExemptNumber.sendKeys(value);
	}
	
	public void click_noExpirationDateBtn()
	{
		 noExpirationDateBtn.click();;
	}
	
	public void click_UploadBtn()
	{
		 upload.click();;
	}
	
	public void click_confirmWithCustomer_Btn()
	{
		 confirmWithCustomer.click();;
	}
	
	public void click_approve_Btn()
	{
		 approve_Btn.click();;
	}
	
	public void click_save_Btn()
	{
		 save_Btn.click();;
	}

	public void enter_expiry_month(String value)
	{
		expiry_month.sendKeys(value);
	}
	
	public void enter_expiry_Date(String value)
	{
		expiry_Date.sendKeys(value);
	}
	
	public void enter_expiry_year(String value)
	{
		expiry_year.sendKeys(value);
	}
	
	public String get_expiry_month()
	{
		return expiry_month.getText();
	}
	
	public String get_expiry_Date()
	{
		return expiry_Date.getText();
	}
	
	public String get_expiry_year()
	{
		return expiry_year.getText();
	}
	
	public String get_taxExemptNumber()
	{
		return taxExemptNumber.getText();
	}
	

}
