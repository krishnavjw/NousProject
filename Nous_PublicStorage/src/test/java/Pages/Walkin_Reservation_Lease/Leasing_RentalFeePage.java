package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.gargoylesoftware.htmlunit.Page;

public class Leasing_RentalFeePage 
{
	@FindBy(xpath="//button[text()='Confirm With Customer']")
	private WebElement confirmCust_btn;
	
	@FindBy(xpath="//div[@id='payment-table']//section[@class='monthly-rent row']//div/span[@class='js-unit-rent']")
	private WebElement monthlyRent_CurrentDue;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='monthly-rent row']//div/span[@class='js-next-unit-rent']")
	private WebElement monthlyRent_NextDue;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='promotion alt row']/div[contains(text(),'Promotion')]")
	private WebElement promotionField;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='promotion alt row']//div/span[@class='js-unit-discount']")
	private WebElement promotion_CurrentDue;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='promotion alt row']//div/span[@class='js-next-unit-discount']")
	private WebElement promotion_NextDue;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='admin-fee alt row']//div[contains(text(),'One-Time Administrative Fee')]")
	private WebElement oneTimeAdministrativeFeeField;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='admin-fee alt row']//div/span[@class='js-unit-adminfee js-unit-product-option']")
	private WebElement oneTimeAdministrativeFeeCurrentDue;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='admin-fee alt row']//div/span[@class='js-next-unit-adminfee']")
	private WebElement oneTimeAdministrativeFeeNextDue;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='prorate row']//div[text()='Pay Through']")
	private WebElement payThroughField;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='prorate row']//div/span[@class='js-unit-paythrough']")
	private WebElement payThrougheCurrentDue;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='prorate row']//div[@class='third col']/span")
	private WebElement payThroughNextDue;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='unit-subtotal alt row']//div[contains(text(),'Subtotal')]")
	private WebElement spaceSubTotalField;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='unit-subtotal alt row']//div/span[@class='js-unit-subtotal']")
	private WebElement spaceSubTotalCurrentDue;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='unit-subtotal alt row']//div/span[@class='js-next-unit-subtotal']")
	private WebElement spaceSubTotalNextDue;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='payment-merchandise row']//span[text()='Add/Edit Cart']")
	private WebElement merchandiseAdd_Edit;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='payment-merchandise row']//span[@class='js-merchandise-amount']")
	private WebElement merchandiseDue;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='tax row']//span[@class='js-tax']")
	private WebElement salesTax;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='tax row']/div[contains(text(),'Sales Tax')]")
	private WebElement salesTaxField;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='row merchandise-subtotal']//div[contains(text(),'Merchandise Subtotal')]")
	private WebElement merchandiseSubtotalField;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='row merchandise-subtotal']//span[@class='js-merchandise-subtotal']")
	private WebElement merchandiseSubtotal;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='row orderTotal']/div[contains(text(),'Total')]")
	private WebElement grandTotalField;

	@FindBy(xpath="//div[@id='payment-table']//section[@class='row orderTotal']//span[@class='js-total']")
	private WebElement grandTotal;

	@FindBy(xpath="//a[@id='notes-add']")
	private WebElement addNotesLink;
	
	
	
	public Leasing_RentalFeePage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public void clickAddNotesBtn(){
		addNotesLink.click();
	}
	
	public boolean verifyPromotionField(){
		return promotionField.isDisplayed();

	}
	
	public String getMonthlyRentCurrentDue(){
		return monthlyRent_CurrentDue.getText();
	}

	public String getMonthlyRentNextDue(){
		return	monthlyRent_NextDue.getText();

	}

	public String getPromotionCurrentDue(){
		return	promotion_CurrentDue.getText();

	}

	public String getOneTimeAdministrativeFeeCurrenttDue(){    
		return	oneTimeAdministrativeFeeCurrentDue.getText();

	}
	public String getOneTimeAdministrativeFeeNexttDue(){
		return	oneTimeAdministrativeFeeNextDue.getText();

	}
	public String getPayThroughNexttDue(){
		return	payThroughNextDue.getText();

	}

	public String getPayThroughCurrenttDue(){
		return	payThrougheCurrentDue.getText();

	}

	public String getSpaceSubTotalCurrenttDue(){
		return	spaceSubTotalCurrentDue.getText();

	}

	public String getSpaceSubTotalNexttDue(){
		return	spaceSubTotalNextDue.getText();

	}

	public String getMerchandiseDue(){
		return	merchandiseDue.getText();

	}

	public String getSalesTaxDue(){
		return	salesTax.getText();

	}

	public String getGrandTotal(){
		return	grandTotal.getText();

	}

	public String getMerchandiseSubtotal(){
		return	merchandiseSubtotal.getText();

	}

	public boolean verifyOneTimeAdministrativeFeeField(){
		return oneTimeAdministrativeFeeField.isDisplayed();

	}

	public boolean verifyPayThroughField(){
		return payThroughField.isDisplayed();

	}

	public boolean verifyMerchandiseAddEditField(){
		return merchandiseAdd_Edit.isDisplayed();

	}

	public void clickAddMerchandise(){
		merchandiseAdd_Edit.click();

	}

	public boolean verifSalesTaxField(){
		return salesTaxField.isDisplayed();

	}

	public String getPromotionNextDue(){
		return	payThrougheCurrentDue.getText();

	}

	
	
	public void clickConfirmCust_btn()
	{
		confirmCust_btn.click();
	}

}
