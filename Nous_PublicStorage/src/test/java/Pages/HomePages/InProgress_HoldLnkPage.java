package Pages.HomePages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class InProgress_HoldLnkPage {
	WebDriver driver;



	@FindBy(xpath="//input[@id='transactionDialogHoldID']/following-sibling::div[@class='hold-transaction-label editor-label float-left']")
	private WebElement get_SpaceNo;

	@FindBy(xpath="//span[text()='Reserve (1 or more)']/preceding-sibling::span")
	private WebElement clk_ReserveRadioBtn;



	@FindBy(xpath="//a[contains(text(),'Continue')]")
	private WebElement clk_ContinueBtn;


	@FindBy(xpath="//a[contains(text(),'Cancel')]")
	private WebElement clk_CancelBtn;

	@FindBy(xpath="//span[text()='Rent (1 or more)']/preceding-sibling::span")
	private WebElement RentRadioBtn;

	@FindBy(xpath="//span[span[text()='SELECT']]")
	private WebElement dropDown_ReasonForCancellation;

	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement empIdEdt;

	@FindBy(xpath="//span[text()='Cancel (All Space)']/preceding-sibling::span")
	private WebElement clk_CancelRadioBtn;


	@FindBy(xpath="//div//span[text()='SELECT']/..")
	private WebElement clk_ReasonForCancelDropDwnBtn;

	@FindBy(xpath="//ul[@class='k-list k-reset ps-container ps-active-y']/li")
	private List<WebElement> ls_ReasonForCancelDropDwnValues;

	@FindBy(id="employeeNumber")
	private WebElement enter_EmpNumber;
	
	@FindBy(xpath="//span[text()='Rent (1 or more)']")
	private WebElement rentbtnText;
	
	@FindBy(xpath="//span[text()='Reserve (1 or more)']")
	private WebElement reserveBtnText;
	
	@FindBy(xpath="//span[text()='Cancel (All Space)']")
	private WebElement cancelBtnText;

	public InProgress_HoldLnkPage(WebDriver driver){

		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean isRentBtnDisplayed(){
		
		return rentbtnText.isDisplayed();
	}
	public boolean isReserveBtnDisplayed(){
		
		return reserveBtnText.isDisplayed();
	}
	
	public boolean isCancelBtnDisplayed(){
		
		return cancelBtnText.isDisplayed();
	}
	
	
	
	

	public String get_SpaceNo(){

		return get_SpaceNo.getText();
	}


	public void clk_ReserveRadioBtn(){

		clk_ReserveRadioBtn.click();
	}


	public void clk_ContinueBtn(){

		clk_ContinueBtn.click();
	}

	public void clk_CancelBtn(){

		clk_CancelBtn.click();
	}

	public void clk_RentRadioBtn(){
		RentRadioBtn.click();
	}
	public boolean isDisplayed_dropDown_ReasonForCancellation(){
		return dropDown_ReasonForCancellation.isDisplayed();
	}

	public void enter_empIdEdt(String userId){
		empIdEdt.sendKeys(userId);
	}

	public void sel_DropDownValFromPhNo(String value){
		clk_ReasonForCancelDropDwnBtn.click();
		List<WebElement> lsPhType = ls_ReasonForCancelDropDwnValues;
		for(int i=0;i<lsPhType.size();i++)
		{
			String PhType = lsPhType.get(i).getText().trim();
			Reporter.log("PhType:"+PhType,true);
			if(PhType.equals(value))
			{
				lsPhType.get(i).click();
				break;
			}
		}

	}

	public void clk_CancelRadioBtn(){

		clk_CancelRadioBtn.click();
	}

	public void enter_EmpNumber(String empno){

		enter_EmpNumber.sendKeys(empno);
	}

}
