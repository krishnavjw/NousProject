package Pages.EditAccountDetails;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OtherStatuses {

	WebDriver driver;
	@FindBy(xpath = "//div/h3[text()='Other Statuses']")
	private WebElement OtherStatusesTitle;

	@FindBy(xpath = "//div[text()='Bankruptcy']")
	private WebElement Bankruptcy_txt;

	@FindBy(xpath = "//div[text()='Deceased']")
	private WebElement Deceased_txt;

	@FindBy(xpath = "//div[text()='Do Not Rent']")
	private WebElement DoNotRent_txt;

	@FindBy(xpath = "//div[@id='bankruptcy-container']//input[@id='IsBankruptcyDeclared']//following-sibling::span[@class='button']")
	private WebElement Declared_chk;

	@FindBy(xpath = "//span[@class='k-select']/span[@class='k-icon k-i-arrow-s']")
	private WebElement Type_Drpdown;
	
	
	@FindBy(xpath = "(//a[text()='Scan'])[1]")
	private WebElement Scan_button;
	
	
	@FindBy(xpath = "(//a[text()='Upload'])[1]")
	private WebElement Upload_button;
	
	@FindBy(xpath = "//textarea[@id='BankruptcyNote']")
	private WebElement BankruptcyNote_txtArea;
	
	
	@FindBy(xpath = "//div[@id='bankruptcy-container']//div[@class='vertical-center floatleft']")
	private WebElement infoMessage_txt;
	
	
	
	

	public OtherStatuses(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		

	}

	public boolean OtherStatusesTitleDisplayed() {
		return OtherStatusesTitle.isDisplayed();

	}

	public boolean Bankruptcy_txtDisplayed() {
		return Bankruptcy_txt.isDisplayed();

	}

	public boolean Deceased_txtDisplayed() {
		return Deceased_txt.isDisplayed();

	}

	public boolean DoNotRent_txtDisplayed() {
		return DoNotRent_txt.isDisplayed();

	}

	public void click_declaredCheckbox() {

		Declared_chk.click();
	}
	
	public void click_NotifiedCheckbox() {

		driver.findElement(By.xpath("//span[text()='Notified']/preceding-sibling::span")).click();
	}
	
	
	
	
	
	
	//span[text()='Notified']/preceding-sibling::span
	
	
	

	public boolean Upload_buttonDisplayed() {

		return Upload_button.isDisplayed();
	}
	
	
	
	public boolean Scan_buttonDisplayed() {

		return Scan_button.isDisplayed();
	}
	
	
	public boolean typeDropdownDisplayed() {

		return Type_Drpdown.isDisplayed();
	}
	
	
	public boolean BankruptcyNote_txtAreaDisplayed() {

		return BankruptcyNote_txtArea.isDisplayed();
	}
	
	public String get_InfoMessageTxt(){
		
		 return infoMessage_txt.getText();
	}
	
	
	public void select_BankRuptcyType() throws InterruptedException{
		driver.findElement(By.xpath("//span[@class='k-icon k-i-arrow-s']")).click();
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//ul[@id='BankruptTypeId_listbox']/li[2]")).click();
		
		
	}
	
	
	
	
	
	
	
	
	
	

}
