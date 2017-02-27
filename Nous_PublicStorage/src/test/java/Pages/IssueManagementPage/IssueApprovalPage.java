package Pages.IssueManagementPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IssueApprovalPage {
	private WebDriver driver;
	public IssueApprovalPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//span[text()='Fee Contested: ']/following-sibling::div//td[@id='feeContested']")
	private WebElement feeContested;

	@FindBy(xpath="//a[@id='approvedeclineButton']")
	private WebElement continueBtn;

	public String getFeeContested(){
		return feeContested.getText();
	}

	public void clkContinueBtn(){
		continueBtn.click();
	}
}
