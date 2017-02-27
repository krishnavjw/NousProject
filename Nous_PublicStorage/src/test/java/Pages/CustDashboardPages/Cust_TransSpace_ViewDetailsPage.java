package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Cust_TransSpace_ViewDetailsPage 
{
	@FindBy(className="unit")
	private WebElement Cust_Space;
	
	@FindBy(xpath="//div[contains(text(),'Total   Due Now:')]/following-sibling::div[@class='total-due-now__details__detail total-due-now__details__detail--total']")
	private WebElement Cust_TotalDueNow;
	
	@FindBy(xpath="//div[@class='command-row clearfix-container']/a[contains(text(),'Print')]")
	private WebElement print_btn;

	@FindBy(xpath="//div[@class='command-row clearfix-container']/a[contains(text(),'Close')]")
	private WebElement close_btn;
	
	
	
	public Cust_TransSpace_ViewDetailsPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}

}
