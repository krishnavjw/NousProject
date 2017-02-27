package Pages.CustDashboardPages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Acc_SpaceDetailsPage 
{
	private WebDriver driver;
	
	@FindBy(xpath="//div[@class='floatleft bold total']")
	private WebElement monthly_rent;

	@FindBy(xpath="//div[span[text()='Mini Storage']]/preceding-sibling::div/span[@class='space-rentalunitnumber']")
	private List<WebElement> listOfAllSpace;

	@FindBy(xpath="//a[contains(text(),'Emergency Contact')]")
	private WebElement emrgyCnt_btn;

	@FindBy(xpath="//a[contains(text(),'Vacate Now')]")
	private WebElement vacateNw_btn;

	@FindBy(xpath="//a[contains(text(),'Authorized Users')]")
	private WebElement authUsers_btn;

	@FindBy(xpath="//a[contains(text(),'Scheduled Vacate')]")
	private WebElement schVacate_btn;

	@FindBy(xpath="//a[contains(text(),'Transfer Space')]")
	private WebElement tansSpace_btn;

	@FindBy(xpath="//a[contains(text(),'Flag For Maintenance')]")
	private WebElement flagMain_btn;

	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header addendum-dropdown']/span")
	private WebElement addendumToSpace_List;

	@FindBy(xpath="//div[@class='k-grid-content ps-container']/table/tbody//tr//td//div[@id='scheduledVacateSection']//a[text()='Change']")
	private WebElement VacateSchChange_link;

	@FindBy(xpath="//div[@class='floatleft section-2']//div[text()='AutoPay Status:']/following-sibling::div//div[contains(text(),'On')]")
	private WebElement autopay_Status;

	@FindBy(xpath="//div[@class='floatleft section-2']//div[text()='AutoPay Status:']/following-sibling::div//div[contains(text(),'Off')]")
	private WebElement autopay_DisabledStatus;

	@FindBy(xpath="//div[@class='floatleft section-2']//div[text()='AutoPay Status:']/following-sibling::div//div[contains(text(),'Off')]/a")
	private WebElement addAutopay_Link;

	@FindBy(xpath="//div[@id='space-details-grid']//div[text()='Gate Code:']/following-sibling::div")
	private WebElement gateCode;

	@FindBy(xpath="//span[contains(text(),'Vacate Scheduled')]/following-sibling::span/a")
	private WebElement get_ScheduleVacteDate;


	@FindBy(xpath="//span[contains(text(),'Vacate Scheduled')]/following-sibling::span/a")
	private WebElement clk_VacateScheduleLnk;
	@FindBy(xpath = "//div[text()='Payment Restriction:']/following-sibling::div[@class='floatleft rent-itemization-spacer']/a")
	private WebElement paymentRestrictionModeChangeLink;

	@FindBy(xpath = "//div[text()='Payment Restriction:']/following-sibling::div[@class='floatleft']/div")
	private List<WebElement> paymentRestrictionStatuses;
	

	@FindBy(xpath="//span[@class='space-rentalunitnumber']")
    private WebElement spaceNum;
	
	@FindBy(xpath = "//div[@class='floatleft insurance']")
	private WebElement txt_InsuranceAmt;
	
	@FindBy(xpath = "//a[contains(@class,'-insurance small-link')]")
	private WebElement lnk_modifyInsurance;
	
	@FindBy(xpath = "//a[@class='change-insurance small-link']")
	private WebElement lnk_ChangeInsurance;

	@FindBy(xpath = "//a[@class='add-insurance small-link']")
	private WebElement lnk_addInsurance;
	
	@FindBy(xpath = "//div[@id='space-details-grid']//table//tbody//tr//td//span[@class='space-rentalunitnumber']")
	private List<WebElement> multipleSpaces;

	@FindBy(xpath="//span[contains(text(),'Vacate Scheduled')]/following-sibling::span/a")
	private WebElement clk_VacateScheduleDateLnk;
	
	@FindBy(xpath="//a[contains(text(),'Unvacate')]")
	private WebElement UnvateLnk;

	@FindBy(xpath="//div[@class='maintenance-needed']")
	private WebElement maintenanceNeeded;
	
	@FindBy(xpath = "//a[contains(text(),'Transfer Space')]")
	private WebElement lnk_transfer;

	

	public Acc_SpaceDetailsPage(WebDriver driver)
	{
		this.driver= driver;
		PageFactory.initElements(driver,this);
	}

	public void clickSchVacate_Btn()
	{
		schVacate_btn.click();
	}

	public void clickVacateSchChange_link()
	{
		VacateSchChange_link.click();
	}

	public boolean verifyAutopay_Status()
	{
		return autopay_Status.isDisplayed();
	}

	public boolean verifyStopAutopay_Status()
	{
		return autopay_DisabledStatus.isDisplayed();
	}

	public boolean verifyAddAutopayLink(){
		return addAutopay_Link.isDisplayed();
	}
	public String get_SpaceNumber(){

		String space=null;
		for(WebElement ele:listOfAllSpace){

			space=ele.getText().trim();
			break;
		}
		return space;

	}

	public String get_ScheduleVacteDate(){

		return get_ScheduleVacteDate.getText().trim();
	}

	public boolean isScheduleVacateDateDisplayed(){

		return get_ScheduleVacteDate.isDisplayed();
	}

	public String get_GateCode() {
		String[] temp = gateCode.getText().split(" ");
		return temp[0].trim();
	}
	public void click_VacateNow_Btn()
	{
		vacateNw_btn.click();
	}

	public void clk_VacateScheduleLnk(){

		clk_VacateScheduleLnk.click();
	}
	public void click_paymentRestrictionModeChangeLink() {
		paymentRestrictionModeChangeLink.click();
	}

	public ArrayList<String> getTokens() {
		ArrayList<String> tokens = new ArrayList<String>();
		for (int i = 0; i < paymentRestrictionStatuses.size(); i++) {
			tokens.add(paymentRestrictionStatuses.get(i).getText());
		}
		return tokens;
	}
	
	
	public String[] getAllSpaceNumbers() {

		String[] space = new String[listOfAllSpace.size()];

		for (int i = 0; i < listOfAllSpace.size(); i++) {

			space[i] = listOfAllSpace.get(i).getText();
		}

		return space;
	}
	
	
	public void clk_AuthorisedUsersLink()
	{
	authUsers_btn.click();
	}
	
	public String get_spaceNum(){
        return spaceNum.getText();
 }
	
	public String get_InsuranceAmt() {
		return txt_InsuranceAmt.getText();
	}
	
	public String getModifyInsuranceLinkText() {
		return lnk_modifyInsurance.getText();
	}
	
	public void clk_ChangeInsuranceLink() {
		lnk_ChangeInsurance.click();
	}

	public void clk_AddInsuranceLink() {
		lnk_addInsurance.click();
	}
	
	public void click_EmergencyContact(){
		emrgyCnt_btn.click();
	}
	
	public List<WebElement> getMultipleSpaces(){
		return multipleSpaces;
	}
	
public void clk_VacateScheduleDateLnk(){
		
		clk_VacateScheduleDateLnk.click();
	}
	
	public boolean isUnvateLnkIsDisplayed(){
		
		return UnvateLnk.isDisplayed();
	}
	
	public void click_FlagForMaintenance(){
		flagMain_btn.click();
	}
	
	public boolean verify_MaintenanceNeeded(){
		return maintenanceNeeded.isDisplayed();
	}
	
	public void clickTransfer_Btn(WebDriver driver) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", lnk_transfer);
		Thread.sleep(2000);
		lnk_transfer.click();
	}
	

}
