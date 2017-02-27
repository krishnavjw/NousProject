package Pages.CustDashboardPages;

import java.util.List;

import org.apache.poi.ss.formula.functions.Subtotal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MerchandiseReturnPage {
	
	WebDriver driver;
	
	@FindBy(xpath="//div[@class='page-header container-heading clearfix-container']/h3[contains(text(),'Merchandise')]")
	public WebElement pagetitle;	
	
	@FindBy(xpath="//div[@id='customerscreenandscriptpanel']/ul/li/span[contains(text(),' Script')]")
	public WebElement Script;
	
	@FindBy(xpath="//div[@id='customerscreenandscriptpanel']/ul/li/span[contains(text(),' Customer Screen')]")
	public WebElement customerscreen;
	
	@FindBy(xpath="//div[@id='merchandise-return-reason']//span[text()='select']")
	public WebElement dropdown;
	
	@FindBy(xpath="//div[@id='ReturnReason-list']/ul[@id='ReturnReason_listbox']/li[contains(text(),'Other')]")
	public WebElement option_Other;
	
	@FindBy(xpath="//div[@id='merchandiseCart']//table/tbody/tr[1]/td[7]//a[@class='js-subtract psbutton-low-priority qtyicon floatleft']")
	public WebElement sub_Quantity;
		
	
	@FindBy(xpath="//div[@id='merchandiseCart']//table/tbody/tr[1]/td[7]//a[@class='js-add psbutton-low-priority qtyicon floatleft']")
	public WebElement add_Quantity;
	
	@FindBy(xpath="//button[text()='Calculate']")
	public WebElement calculate;
	
	@FindBy(xpath="//div[@id='merchandiseCart']//table/tbody/tr[1]/td[8]/div")
	public WebElement subtot_After_Calculate;
	
	@FindBy(xpath="//div[@id='merchandiseCart']//table/tbody/tr[1]/td[7]//div[@class='js-return-quantity floatleft vertical-center']")
	public WebElement retQuantity_After_Calculate;
	
	@FindBy(xpath="//span[@id='sales-tax-return-display']")
	public WebElement salesTaxReturn;
	
	@FindBy(xpath="//span[@id='return-total-display']")
	public WebElement totalReturn;
	
	@FindBy(xpath="//span[contains(text(),'Is enough cash on hand')]")
	public WebElement enoughCashOnHand_Question;
	
	@FindBy(xpath="//div[@id='cash-on-hand']//span[text()='Yes']/preceding-sibling::span[@class='button']")
	public WebElement yes_Radiobtn;
	
	@FindBy(xpath="//button[contains(text(),'Confirm With Customer')]")
	public WebElement ConfWithCust;
	
	@FindBy(linkText="Approve")
	public WebElement Approve;
	
	@FindBy(linkText="Clear")
	public WebElement Clear;
	
	@FindBy(xpath="//button[text()='Submit']")
	public WebElement Submit;

	public MerchandiseReturnPage(WebDriver driver)
	
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
		
	public boolean verify_pagetitle()
	{
		return pagetitle.isDisplayed();
	}
	
	public void get_options_reasonDrpdwn() throws InterruptedException{
		driver.findElement(By.xpath("//div[@id='merchandise-return-reason']//span[text()='select']")).click();
		Thread.sleep(2000);
		List<WebElement> reasons=driver.findElements(By.xpath("//div[@id='ReturnReason-list']/ul[@id='ReturnReason_listbox']/li[contains(text(),'SELECT')]/following-sibling::li"));
		System.out.println(reasons.size());
		
	for(int i=0;i<reasons.size();i++){
		String types=reasons.get(i).getText();
		System.out.println(types);
			}
		
	}
	
	
	
	public boolean verify_Script()
	{
		return Script.isDisplayed();
	}
	
	public boolean verify_customerscreen()
	{
		return customerscreen.isDisplayed();
	}
	
	public void click_dropdown(){
		dropdown.click();
			}
	
	public void click_option_Other(){
		option_Other.click();
			}
	
	public void verify_lineitems(){
		List<WebElement> lineitems=driver.findElements(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr"));
		System.out.println(lineitems.size());
	}
	
	/*public void verify_Productname(){
		List<WebElement> names=driver.findElements(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td[2]/div[1]"));
		System.out.println(names.size());
		for(WebElement prodnames:names){
			String Productnames=prodnames.getText();
			System.out.println(Productnames);}
		return driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr[1]/td[2]/div[1]")).getText();
		
	
		
	}
	
	public void verify_ImageCount(){
		List<WebElement> img=driver.findElements(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td[3]"));
		System.out.println(img.size());
		
		return driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr[1]/td[2]/div[1]")).getText();	
	}

	public void verify_Price(){
		List<WebElement> price=driver.findElements(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td[4]"));
		System.out.println(price.size());
		for(WebElement value:price){
			String item_price=value.getText();
			System.out.println(item_price);
		}
	}
		
		public void verify_quantity() {
		List<WebElement> quantity=driver.findElements(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td[5]"));
		System.out.println(quantity.size());
		for(WebElement item_quantity:quantity){
			String quant=item_quantity.getText();
			System.out.println(quant);
		}
		
	}

	public void verify_purchaseSubtotal() {
		List<WebElement> sub=driver.findElements(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td[6]"));
		System.out.println(sub.size());
		for(WebElement tot:sub){
			String purchaseSubtotal=tot.getText();
			System.out.println(purchaseSubtotal);
		}
		
		
	}
	
	public void verify_returnQuantity() {
		List<WebElement> ret=driver.findElements(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td[7]"));
		System.out.println(ret.size());
		for(WebElement tot:ret){
			String returnQuantity=tot.getText();
			System.out.println(returnQuantity);
		}
		
			}
	*/
	
	public String verify_Productname(){
		return driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr[1]/td[2]/div[1]")).getText();
	}
	
	public boolean verify_ImageCount(){
		return driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr[1]/td[3]/div[1]")).isDisplayed();
	}
	
	public String verify_Price(){
		return driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr[1]/td[4]")).getText();
	}
	
	public String verify_quantity(){
		return driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr[1]/td[5]/div[1]")).getText();
	}
	
	public String verify_purchaseSubtotal(){
		return driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr[1]/td[6]")).getText();
	}
	
	public String verify_returnQuantity(){
		return driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr[1]/td[7]//div[@class='js-return-quantity floatleft vertical-center']")).getText();
	}
	
	public boolean verify_Adjust_addBtn(){
		return driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td[7]//a[@class='js-add psbutton-low-priority qtyicon floatleft']")).isDisplayed();
	}
	
	public boolean verify_Adjust_subBtn(){
		return driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td[7]//a[@class='js-subtract psbutton-low-priority qtyicon floatleft']")).isDisplayed();
	}
	
	public String verify_returnSubtotal(){
		return driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr[1]/td[8]/div[1]")).getText();
	}
	
	
	//==============================================
	/*public void verify_Adjust_addBtn() {
		List<WebElement> ret=driver.findElements(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td[7]//a[@class='js-add psbutton-low-priority qtyicon floatleft']"));
		System.out.println(ret.size());
		for(WebElement tot:ret){
			String returnQuantity=tot.getText();
			System.out.println(returnQuantity);
		}
		
			}
	
	public void verify_Adjust_subBtn() {
		List<WebElement> ret=driver.findElements(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td[7]//a[@class='js-subtract psbutton-low-priority qtyicon floatleft']"));
		System.out.println(ret.size());
		for(WebElement tot:ret){
			String returnQuantity=tot.getText();
			System.out.println(returnQuantity);
		}
		
			}
	
	
	public void verify_returnSubtotal() {
		List<WebElement> retSub=driver.findElements(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr/td[8]"));
		System.out.println(retSub.size());
		for(WebElement tot:retSub){
			String returnSubtotal=tot.getText();
			System.out.println(returnSubtotal);
		}
	}*/
	
	public void click_addQuantity(){
		add_Quantity.click();
	}
	
	public void click_addQuantity_ofallitems(int i){
		WebElement count=driver.findElement(By.xpath("//div[@id='merchandiseCart']//table/tbody/tr["+i+"]/td[7]//a[@class='js-add psbutton-low-priority qtyicon floatleft']"));
		count.click();
	}
	
	
	public void click_calculatebtn(){
		calculate.click();
	}
	
	public String get_returnSub_AftCalculate(){
		return subtot_After_Calculate.getText();
	}
	
	public String get_retQuantity_After_Calculate(){
		return retQuantity_After_Calculate.getText();
	}
	
	public String get_salesTaxReturn(){
		return salesTaxReturn.getText();
	}
	
	public String get_totalReturn(){
		return totalReturn.getText();
	}
	
	public boolean verify_enoughCashOnHand_Question(){
		return enoughCashOnHand_Question.isDisplayed();
	}
	
	public void click_yes_Radiobtn(){
		yes_Radiobtn.click();
	}
	
	public void click_ConfWithCust(){
		ConfWithCust.click();
	}
	
	public void click_Clear(){
		Clear.click();
	}
	
	public void click_Approve(){
		Approve.click();
	}
	
	public void click_Submit(){
		Submit.click();
	}
	
	//========================================================
	
}
