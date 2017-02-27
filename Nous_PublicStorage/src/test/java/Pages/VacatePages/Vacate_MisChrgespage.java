package Pages.VacatePages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class Vacate_MisChrgespage {
	
	WebDriver driver;
	@FindBy(xpath="//form[@id='vacateMiscChargesForm']//div[@id='misc-charge-view")
	private WebElement miscCharges_Title;
	
	@FindBy(xpath="//div[@id='misc-charge-view']//span[text()='Select Charge']")
	private WebElement selCharge_List;
	
	
	@FindBy(xpath="//div[@id='misc-charge-view']//a[text()='Add Charge']")
	private WebElement addChaarge_Btn;
	
	@FindBy(xpath="//div[@class='added-charge charge-type-256 clearfix-container']//div/a[text()='Upload']")
	private WebElement abandoned_uploadBtn;
	
	
	@FindBy(xpath="//div[@class='added-charge charge-type-256 clearfix-container']//div/input[@class='amount webchamp-textbox valid']")
	private WebElement abandoned_AmtText;
	
	
	@FindBy(xpath="//div[@class='added-charge charge-type-256 clearfix-container']//div/textarea")
	private WebElement abandoned_Note;
	
	@FindBy(xpath="//div[@class='added-charge charge-type-256 clearfix-container']//div/a[text()='Remove']")
	private WebElement abandoned_Remove;
	
	@FindBy(xpath="//div[@class='added-charge charge-type-256 clearfix-container']//div//span[text()='Hazardous Materials']/preceding-sibling::span")
	private WebElement abandoned_HazardousMaterials_Chk;
	
	@FindBy(id="lnkCancel1")
	private WebElement cancel_Btn;
	
	@FindBy(id="lnkReserve")
	private WebElement continue_Btn;
	
	@FindBy(linkText="Back to Dashboard")
	private WebElement BackToDshBd_Btn;
	
	@FindBy(xpath="//div[@class='charge-options-container']//span[@class='k-widget k-dropdown k-header charge-options dropdown-options']//span[text()='Select Charge']")
	private WebElement selectCharge;
	
	@FindBy(xpath="//div[@class='added-charges-container']//div[@class='structure amount']//input[contains(@class,'amount webchamp-textbox')]")
	private WebElement misc_amount;
	
	@FindBy(xpath="(//div[@id='misc-charge-view']//div[@class='added-charges-container']//textarea[@class='note charge-note webchamp-textbox'])[1]")
	private WebElement misc_note;
	
	@FindBy(xpath="//div[@id='isMaintenanceIssue']//span[text()='No']")
	private WebElement no_maintenance;
	
	@FindBy(xpath="//input[@id='cashAmount']")
	private WebElement cashAmount;
	
	
	public Vacate_MisChrgespage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}	
	
	public String get_MiscChargeTitle()
	{
		
		return miscCharges_Title.getText();
	}
	
	public void sel_SelChargeByVisibleTxt(String value){
		
		Select sel=new Select(selCharge_List);
		sel.selectByVisibleText(value);
	}
	
	public void clk_addChaargeBtn(){
		addChaarge_Btn.click();
	}
	
	
	public void clk_abandoned_uploadBtn(){
		
		abandoned_uploadBtn.click();
	}
	
	public void ener_abandoned_AmtText(String amt){
		
		abandoned_AmtText.sendKeys(amt);
	}
	
	public void ener_abandoned_Note(String note){

		
		abandoned_Note.sendKeys(note);
	}
	
	public void clk_abandoned_Remove(){
		
		abandoned_Remove.click();
	}
	
	
	public void chk_abandoned_HazardousMaterials_Chk(){

		
		abandoned_HazardousMaterials_Chk.click();
	}
	
	public void clk_cancel_Btn(){
		
		cancel_Btn.click();
	}
	
	public void clk_continue_Btn(){
		
		continue_Btn.click();
	}
	
	public void clk_BackToDshBd_Btn(){
		
		BackToDshBd_Btn.click();
	}
	
	public void click_SelectCharge(){
		selectCharge.click();
	}
	
	public void enter_misc_amount(String text){
		misc_amount.clear();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		misc_amount.sendKeys(text);
	}
	
	public void enter_misc_note(String text){
		misc_note.sendKeys(text);
	}
	
	
   public void clk_NoMaintenance(){
		
		no_maintenance.click();
	}
   
   public void enter_CashAmount(String amt){
		
	   cashAmount.sendKeys(amt);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
