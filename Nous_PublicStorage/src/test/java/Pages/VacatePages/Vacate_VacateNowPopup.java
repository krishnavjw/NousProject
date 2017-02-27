package Pages.VacatePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Vacate_VacateNowPopup {

	WebDriver driver;
	WebDriverWait wait;

	public Vacate_VacateNowPopup(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
		wait=new WebDriverWait(driver,60);
	}

	@FindBy(id="vacate-now-modal-template_wnd_title")private WebElement vacateNowPopup_Title;

	@FindBy(xpath="//span[text()='Yes']/preceding-sibling::span")private WebElement skipYes_rdoBtn;

	@FindBy(xpath="//div[@id='skip-message']")private WebElement skipMsg;

	@FindBy(id="skipVacate")private WebElement skipNo_rdoBtn;

	@FindBy(id="confirm-unvacate-button")private WebElement continue_btn;

	@FindBy(id="cancel-unvacate-button")private WebElement cancel_btn;

	public String getVacateNowPopup_Title(){
		wait.until(ExpectedConditions.visibilityOf(vacateNowPopup_Title));
		return vacateNowPopup_Title.getText().trim();
	}

	public void clickSkipYes_rdoBtn(){
		wait.until(ExpectedConditions.visibilityOf(skipYes_rdoBtn));
		skipYes_rdoBtn.click();
	}

	public String getSkipMsg(){
		wait.until(ExpectedConditions.visibilityOf(skipMsg));
		return skipMsg.getText().trim();
	}

	public void clickSkipNo_rdoBtn(){
		wait.until(ExpectedConditions.visibilityOf(skipNo_rdoBtn));
		skipNo_rdoBtn.click();
	}

	public void clickContinue_btn(){
		wait.until(ExpectedConditions.visibilityOf(continue_btn));
		continue_btn.click();
	}

	public void cliCkcancel_btn(){
		wait.until(ExpectedConditions.visibilityOf(cancel_btn));
		cancel_btn.click();
	}

}
