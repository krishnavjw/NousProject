package Pages.AWB2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;


public class AuctionBuyer_Edit_tax {
	
	private WebDriver driver;
	
	@FindBy(xpath="//label[@class='webchamp-radio-button tax-options margin-right no-longer-exempt__checkbox']")
	private WebElement radio_yes;
	
	@FindBy(xpath="//label[@class='webchamp-radio-button tax-options margin-right no-longer-exempt__checkbox']/input[@value='True']")
	private WebElement radio_no;
	
	
	@FindBy(xpath="//div[@class='tax-exemption__number-label floatleft']")
	private WebElement tax_exempt_num_label;
	
	@FindBy(xpath="//input[@id='DisplayTaxExempt_TaxExempt_EncryptedItem_EncryptedIdDisplay']")
	private WebElement tax_exempt_num_input;
	
	
	@FindBy(xpath="//div[contains(text(),' Expiration Date:')]")
	private WebElement tax_expirationdate;
	
	
	@FindBy(xpath="//input[@id='DisplayTaxExempt_TaxExempt_ExpirationMonth']")
	private WebElement tax_month;
	
	@FindBy(xpath="//input[@id='DisplayTaxExempt_TaxExempt_ExpirationDay']")
	private WebElement tax_day;
	
	
	@FindBy(xpath="//input[@id='DisplayTaxExempt_TaxExempt_ExpirationYear']")
	private WebElement tax_year;
	
	
	@FindBy(xpath="//a[contains(text(),'Scan')]")
	private WebElement scan_btn;

	
	@FindBy(xpath="//a[contains(text(),'Upload')]")
	private WebElement upload_btn;
	
	@FindBy(xpath="//span[contains(text(),'select')]")
	private WebElement selectdtate;
	
	@FindBy(xpath="//textarea[@id='notesText']")
	private WebElement notetextbox;
	
	
	
	public AuctionBuyer_Edit_tax(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	
	public boolean isdisplay_tax_yes_radio(){
		return radio_yes.isDisplayed();
	} 
	
	public boolean isdisplay_tax_no_radio(){
		return radio_no.isDisplayed();
	} 
	
	public void select_tax_yes_radio(){
		radio_yes.click();
	} 
	
	public boolean isdisplay_tax_num_label(){
		return tax_exempt_num_label.isDisplayed();
	} 
	
	public boolean isdisplay_tax_num_input(){
		return tax_exempt_num_input.isDisplayed();
	} 
	
	public boolean isdisplay_tax_expirationdate_label(){
		return tax_expirationdate.isDisplayed();
	} 
	
	
	public boolean isdisplay_tax_month(){
		return tax_month.isDisplayed();
	} 
	
	public boolean isdisplay_tax_day(){
		return tax_day.isDisplayed();
	} 
	public boolean isdisplay_tax_year(){
		return tax_year.isDisplayed();
	} 
	
	public boolean isdisplay_scan_btn(){
		return scan_btn.isDisplayed();
	
	} 
	
	public boolean isdisplay_upload_btn(){
		return upload_btn.isDisplayed();
	
	} 
	
	public void entertax_num_input(String tax_num){
		 tax_exempt_num_input.sendKeys(tax_num);
	} 
	
	public void select_dropdown(){
		selectdtate.click();
	} 
	
	public void get_tax_month(String month){
		 tax_month.sendKeys(month);
	} 
	public void get_tax_day(String day){
		tax_day.sendKeys(day);
	} 
	public void get_tax_year(String yr){
		tax_year.sendKeys(yr);
	} 
	
	public void clk_upload_btn(){
		upload_btn.click();
	
	} 
	public void enter_notetext(String note){
		 notetextbox.sendKeys(note);
	} 
	
}
