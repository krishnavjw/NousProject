package Pages.AdvSearchPages;


	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.FindBy;
	import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

	public class AdvSearch_PopUp
	{
		//public WebDriver driver;
		@FindBy(xpath="//div[@id='loadingResultsDialog']//div/a[text()='CONTINUE SEARCH']")
		public WebElement contiSearch_btn;
		
		@FindBy(xpath="//div[@class='k-window-titlebar k-header']/span[@id='loadingResultsDialog_wnd_title']")
		public WebElement PopupTitle;
		
		public AdvSearch_PopUp(WebDriver driver)
		{
			PageFactory.initElements(driver, this);
		}
		
		public String verifyPopupTitle()
		{
			return PopupTitle.getText();
		}
		
		public void clickContiSearchbtn(WebDriver driver)
		{
			new WebDriverWait(driver,20).until(ExpectedConditions.visibilityOf(contiSearch_btn));
			contiSearch_btn.click();
		}

	}



