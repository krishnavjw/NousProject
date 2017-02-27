package Pages.IssueManagementPage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.gargoylesoftware.htmlunit.Page;

public class CreateRentRateIncreaseObjectionIssuePage {
	private WebDriver driver;
	
	@FindBy(xpath="//h3[contains(text(),'Create Rent Rate Increase Objection Issue')]")
	private WebElement pagetitle;
	
	@FindBy(xpath="//form[@id='rentRateForm']//span[contains(text(),'Select Reason')]")
	private WebElement reasonDropdown;
	
	@FindBy(id="Description")
	private WebElement descriptionTxt;
	
	@FindBy(xpath="//a[contains(text(),'Create Issue')]")
	private WebElement createIssueBtn;
	
	@FindBy(xpath="//a[contains(text(),'Cancel')]")
	private WebElement CancelBtn;
	
	public CreateRentRateIncreaseObjectionIssuePage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	public boolean verify_pageTitle(){
		return pagetitle.isDisplayed();
	}
	
	public void select_Reason(String data) throws InterruptedException{
		reasonDropdown.click();
		Thread.sleep(2000);
		List<WebElement> list = driver.findElements(By.xpath("//div[@id='SelectedReason-list']/ul/li"));
		for(int i=0;i<list.size();i++){
			if(list.get(i).getText().equalsIgnoreCase(data)){
				list.get(i).click();
				break;
			}
		}
		
	}
	
	
	public void enterDescription(String des){
		descriptionTxt.sendKeys(des);
	}

	public void click_CreateIssueBtn(){
		createIssueBtn.click();
	}
	
	public void click_CancelBtn(){
		CancelBtn.click();
	}
}
