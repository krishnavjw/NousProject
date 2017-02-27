package Pages.DelinquentCustomersPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DelinquentCustomersPage {

	@FindBy(xpath="//div[@id='call-list-container']//div/h3")
	public WebElement pageTitle;
	
	@FindBy(xpath="//div[@id='locationInfoContainer']//table//tbody//tr//td//span[@class='k-widget k-dropdown k-header locationSelect']")
	public WebElement districtDropdown;
	
	@FindBy(xpath="//div[@id='locationInfoContainer']//table//tbody//tr//td//label[text()='Overdue DTM Calls :']")
	public WebElement overdueDTMCalls;
	
	@FindBy(xpath="//div[@id='locationInfoContainer']//table//tbody//tr//td//label[text()='Due DTM Calls :']")
	public WebElement dueDTMCalls;
	
	
	public DelinquentCustomersPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}

	public String getPageTitle(){
		return pageTitle.getText();
	}
	
	public void clk_locationDropdown(){
		districtDropdown.click();
	}

	public boolean verifydueDTMCalls(){
		return dueDTMCalls.isDisplayed();
	}
	
	public boolean verifyoverdueDTMCalls(){
		return overdueDTMCalls.isDisplayed();
	}
}
