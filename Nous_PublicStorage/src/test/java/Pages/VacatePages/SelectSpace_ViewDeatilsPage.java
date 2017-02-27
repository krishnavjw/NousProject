package Pages.VacatePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SelectSpace_ViewDeatilsPage {

	
	@FindBy(xpath="//span[text()='View Details']")
	private WebElement isViewDetailsPageText;
	
	@FindBy(xpath="//div[contains(text(),' Total Due Now:')]/following-sibling::div")
	private WebElement get_ToatlDueNowAmt;
	
	@FindBy(xpath="//a[contains(text(),'Close')]")
	private WebElement clk_CloseBtn;
	
	
	
	public SelectSpace_ViewDeatilsPage(WebDriver driver){
		
		PageFactory.initElements(driver, this);
	}
	
	public boolean isViewDetailsPageDisplyed(){
		
		return isViewDetailsPageText.isDisplayed();
	}
	
	public String get_ToatlDueNowAmount(){
		
		return get_ToatlDueNowAmt.getText();
	}
	
	public void clk_CloseButton(){
		
		clk_CloseBtn.click();
	}
	
	
	
	
}
