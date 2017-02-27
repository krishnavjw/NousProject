package Pages.VacatePages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ViewDetailsPopupPage {

	WebDriver driver;
	@FindBy(xpath="//div[@class='k-window-titlebar k-header']/span")
	private WebElement viewDetailspopup_title;

	@FindBy(xpath="//div[@id='paymentDetailsDialog']//div[@class='space']//div[@class='unit']")
	private WebElement spaceNumber;

	@FindBy(xpath="(//div[@id='paymentDetailsDialog']//div[@class='space__payment-details']//section[@class='space__payment-details__row']/div[contains(text(),'Monthly Rent')]/following-sibling::div[@class='space__payment-details__row__detail space__payment-details__row__detail--due-now'])[1]")
	private WebElement monthlyRent;

	@FindBy(xpath="(//div[@id='paymentDetailsDialog']//div[@class='space__payment-details']//section[@class='space__payment-details__row']/div[contains(text(),'Monthly Rent')]/following-sibling::div[@class='space__payment-details__row__detail space__payment-details__row__detail--due-now'])[2]")
	private WebElement monthlyRentTax;

	@FindBy(xpath="//div[@class='k-window-content k-content']//div//a[contains(text(),'Close')]")
	private WebElement close_Btn;


	public ViewDetailsPopupPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}

	/*public String getMonthlyRentValue(){
		return monthlyRent.getText();
	}

	public String getMonthlyRentTaxValue(){
		return monthlyRentTax.getText();
	}*/

	public boolean isViewDetailsPopupDisplayed(){
		return viewDetailspopup_title.isDisplayed();
	}

	public void clickClose_Btn(){
		close_Btn.click();
	}


	public String getInsuranceValue(){
		String insurance=null;
		List<WebElement> elements= driver.findElements(By.xpath("//div[@id='paymentDetailsDialog']//div[@class='space__payment-details']//section[@class='space__payment-details__row']/div[contains(text(),'Insurance')]/following-sibling::div[@class='space__payment-details__row__detail space__payment-details__row__detail--due-now']"));
		if(elements.size()!=0){
			insurance=elements.get(0).getText();
		}
		return insurance;
	}

	public String getMonthlyRentValue(){
		String monthlyRent=null;
		List<WebElement> elements= driver.findElements(By.xpath("//div[@id='paymentDetailsDialog']//div[@class='space__payment-details']//section[@class='space__payment-details__row']/div[contains(text(),'Monthly Rent')]/following-sibling::div[@class='space__payment-details__row__detail space__payment-details__row__detail--due-now']"));
		if(elements.size()!=0){
			monthlyRent=elements.get(0).getText();
		}
		return monthlyRent;
	}
	
	public String getMonthlyRentTaxValue() throws InterruptedException{
		String monthlyRentTax=null;
		List<WebElement> elements= driver.findElements(By.xpath("//div[@id='paymentDetailsDialog']//div[@class='space__payment-details']//section[@class='space__payment-details__row']/div[contains(text(),'Monthly Rent Tax')]/following-sibling::div[@class='space__payment-details__row__detail space__payment-details__row__detail--due-now']"));
		Thread.sleep(15000);
		if(elements.size()!=0){
			monthlyRentTax=elements.get(0).getText();
		}
		return monthlyRentTax;
	}
	
}
