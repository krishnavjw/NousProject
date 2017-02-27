package Pages.AdvSearchPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchingPopup {
	
	WebDriver driver;
	
	@FindBy(xpath="//div[@id='loadingResultsWarning']/div[@class='slow-message padding']/div[1]/a[contains(text(),'Ok')]")
	private WebElement okBtn;    
	
	public SearchingPopup(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
		this.driver = driver;
	}
	
	public WebElement getOkBtn(){
		return okBtn;
	}
	
	public void clickOkBtn(){
		okBtn.click();
	}
	
	public void click_OkBtn(){
		if(driver.findElements(By.xpath("//div[@id='loadingResultsWarning']//a[text()='Ok']")).size()!=0){
			driver.findElement(By.xpath("//div[@id='loadingResultsWarning']//a[text()='Ok']")).click();
		}
	}


}
