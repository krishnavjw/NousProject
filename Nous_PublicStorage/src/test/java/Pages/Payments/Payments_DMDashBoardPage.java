package Pages.Payments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Payments_DMDashBoardPage {
	// WebDriver driver;
	WebDriverWait wait;
	

	@FindBy(xpath="//form[@id='QuickSearchForm']//span[text()='select']")
	private WebElement loc_dropdown;
	
	@FindBy(xpath="//div[@id='siteId-list']/ul[@id='siteId_listbox']/li[2]")
	private WebElement sel_locationvalue;
	
	@FindBy(xpath = "//h1[text()='Customer Search']")
	private WebElement DMDashBoardTitle;

	@FindBy(xpath = "//a[contains(text(),'Advanced Search')]")
	private WebElement advSearchLink;

	public Payments_DMDashBoardPage(WebDriver driver) {
		// this.driver=driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 60);
	}

	

	public void click_advSearchLink() {
		advSearchLink.click();
	}

	public String get_DMDashBoardTitle() {
		return DMDashBoardTitle.getText();
	}
	
	public boolean is_DMDashBoardTitle(){
		return DMDashBoardTitle.isDisplayed();
	}
	
	//---------Anjana------------------------
			public void click_loc_dropdwn() {
				loc_dropdown.click();
			}
			
			public void click_sel_locationvalue() {
				sel_locationvalue.click();
			}

	
}
