package Pages.VacatePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Vacate_PendingUnvacateConfirmationPopUpPage {

	@FindBy(xpath="//div/span[@id='confirmPendingUnvacateDialog_wnd_title']")
	private WebElement get_PendingUnVacateConfTitle;
	
	@FindBy(xpath="//div//div[contains(text(),'Account Number:')]/following-sibling::div")
	private WebElement get_Accnum;
	
	@FindBy(xpath="//div//div[contains(text(),'Customer Name:')]/following-sibling::div")
	private WebElement get_CustName;
	
	@FindBy(xpath="//div[contains(text(),' Date Vacated:')]/following-sibling::div")
	private WebElement get_DateOfvacateDate;
	
	@FindBy(xpath="//div[contains(text(),'Space Number:')]/following-sibling::div/span")
	private WebElement get_SpaceNumber;
	
	@FindBy(xpath="//a[text()='Confirm']")
	private WebElement clk_ConfirmBtn;
	
	@FindBy(xpath="//div/span[contains(text(),'Please enter an employee number.')]")
	private WebElement get_ErrorMsg;
	
	
	
	public Vacate_PendingUnvacateConfirmationPopUpPage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	public boolean isgPendingUnVacateConfirmationTitleDisplayed(){
		
		return get_PendingUnVacateConfTitle.isDisplayed();
	}
	
	public boolean isAccountNumDispalyed(){
		
		return get_Accnum.isDisplayed();
	}
	
	
	public void clkConfirmButton(){
		
		clk_ConfirmBtn.click();
	}
	
	public boolean iserrorMsgDisplayed(){
		
		return get_ErrorMsg.isDisplayed();
	}
	
	public String getAccountNumber(){
		
		return get_Accnum.getText();
	}
	
	public String getCustomerName(){
		return get_CustName.getText().trim();
	}
	
	public boolean isCustomerNameDisplayed(){
		
		return get_CustName.isDisplayed();
	}
	
	public String getDateOfVacated(){
		
		return get_DateOfvacateDate.getText().trim();
	}
	public boolean isVacateDateDisplayed(){
		
		return get_DateOfvacateDate.isDisplayed();
	}
	
	public String getSpaceNumber(){
		
		return get_SpaceNumber.getText().trim();
	}
	
	public boolean isSpaceNumDisplayed(){
		
		return get_SpaceNumber.isDisplayed();
	}
	
	
	
}
