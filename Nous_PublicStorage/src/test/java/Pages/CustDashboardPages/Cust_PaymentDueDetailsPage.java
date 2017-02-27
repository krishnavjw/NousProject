package Pages.CustDashboardPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Cust_PaymentDueDetailsPage {

	
	@FindBy(xpath="//span[text()='Payment Due Details']")
	private WebElement isPymtDueDetailsTitleDisplayed;
	
	@FindBy(xpath="//div[contains(text(),'Monthly Rent')]/following-sibling::div[@class='space__payment-details__row__detail space__payment-details__row__detail--due-now']")
	private WebElement get_MonthlyRentAmount;
	
	@FindBy(xpath="//div[contains(text(),'Total Due Now:')]/following-sibling::div[@class='total-due-now__details__detail total-due-now__details__detail--total']")
	private WebElement get_TotalDueNowAmount;
	
	@FindBy(xpath="//a[contains(text(),'Cancel')]")
	private WebElement clk_CancelBtn;
	
	
	
	public Cust_PaymentDueDetailsPage(WebDriver driver){
		
		PageFactory.initElements(driver, this);
		
	}
	
	
	public boolean isPymtDueDetailsTitleDisplayed(){
		
		return isPymtDueDetailsTitleDisplayed.isDisplayed();
	}
	
	
	public String get_MonthlyRentAmount(){
		
		return get_MonthlyRentAmount.getText().trim();
	}
	
	public String get_TotalDueNowAmount(){
		
		return get_TotalDueNowAmount.getText();
	}
	
	public void clk_CancelBtn(){
		clk_CancelBtn.click();
	}
	
	public double getDoubleAmount(String inputValue) {

		String addValue = "";

		char[] c = inputValue.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == ',' || c[i] == '$') {
				c[i] = ' ';
			} else if (Character.getNumericValue(c[i]) >= 0 && Character.getNumericValue(c[i]) <= 9 || c[i] == '.'
					|| c[i] == '-') {
				addValue = addValue + c[i];
			} else {
				System.out.println("Not able to handle");
			}
		}
		return Double.parseDouble(addValue);

	}
	
}
