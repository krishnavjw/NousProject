package Pages.IssueManagementPage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FeeAdjustmentRequestPage {
	private WebDriver driver;
	public FeeAdjustmentRequestPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//h3[contains(text(),'Fee Adjustment Request')]")
	private WebElement pagetitle;
	
	@FindBy(xpath="//form[@id='feeAdjustmentForm']//span[contains(text(),'Select Reason')]")
	private WebElement reasonDropdown;
	
	@FindBy(id="Description")
	private WebElement descriptionTxt;
	
	@FindBy(xpath="//a[contains(text(),'Create Issue')]")
	private WebElement createIssueBtn;
	
	@FindBy(xpath="//a[contains(text(),'Cancel')]")
	private WebElement CancelBtn;
	
	@FindBy(xpath="//td[contains(text(),'Lien Fee')]/preceding-sibling::td[contains(text(),'$')]")
	private WebElement contestFee_LienFee;
	
	@FindBy(xpath="//span[span[span[text()='select']]]")
	private WebElement reasonDrpDown;
	
	@FindBy(xpath="//td[text()='Lien Fee']/preceding-sibling::td//input[@name='selectedIds']")
	private WebElement lienFeeRdBtn;
	
	@FindBy(xpath="//a[contains(text(),'OK')]")
	private WebElement acceptAlert_OKBtn;
	
	@FindBy(xpath="//td[text()='Lien Fee 2']/preceding-sibling::td//input[@name='selectedIds']")
	private WebElement lienFee2RdBtn;
	
	public String getContestFee_LienFee(){
		return contestFee_LienFee.getText();
	}
	
	public void clkReasonDropDown(){
		reasonDrpDown.click();
	}
	
	public void clk_lienFee_rdBtn(){
		lienFeeRdBtn.click();
	}
	
	public void acceptAlert_OKBtn(){
		acceptAlert_OKBtn.click();
	}
	
	public void clk_lienFee2_rdBtn(){
		lienFee2RdBtn.click();
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
