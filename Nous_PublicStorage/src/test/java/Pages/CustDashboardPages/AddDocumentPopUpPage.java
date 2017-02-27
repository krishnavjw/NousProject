package Pages.CustDashboardPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddDocumentPopUpPage {
	
	private WebDriver driver;
	
	@FindBy(xpath="//span[contains(text(),'Add Document')]")
	private WebElement title;
	
	@FindBy(xpath="//div[@id='addDocumentDialog']//span[contains(text(),'SELECT')]")
	private WebElement documentTypeDropDown;
	
	@FindBy(xpath="//div[@id='addDocumentDialog']//span[contains(text(),'None')]")
	private WebElement spaceDropDown;
	
	@FindBy(xpath="//a[contains(text(),'Upload')]")
	private WebElement uploadBtn;
	
	@FindBy(xpath="//a[contains(text(),'Scan')]")
	private WebElement scanBtn;
	
	@FindBy(xpath="//a[contains(text(),'Save')]")
	private WebElement saveBtn;
	
	@FindBy(xpath="//a[contains(text(),'Cancel')]")
	private WebElement cancelBtn;
	
	
	public AddDocumentPopUpPage(WebDriver driver)
	{
		
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	
	public boolean verify_title(){
		return title.isDisplayed();
	}
	
	public void sel_DocType() throws InterruptedException{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		documentTypeDropDown.click();
		Thread.sleep(2000);
		List<WebElement> docList = driver.findElements(By.xpath("//div[@class='k-animation-container']//ul[@class='k-list k-reset ps-container ps-active-y']/li"));
		for(int i=0;i<docList.size();i++){
			if(i==1){
				//jse.executeScript("scrollBy(0, 1000)");
				//Thread.sleep(2000);
				//jse.executeScript("arguments[0].scrollIntoView()", docList.get(i)); 
				//jse.executeScript("arguments[0].click();", docList.get(i));
				docList.get(i).click();
			}
		}
	}
	

	public void clk_SpaceDropdown(){
		spaceDropDown.click();
	}
	
	public void clk_UploadBtn(){
		uploadBtn.click();
	}
	
	public void clk_SaveBtn(){
		saveBtn.click();
	}
	
	public void clk_CancelBtn(){
		cancelBtn.click();
	}
}
