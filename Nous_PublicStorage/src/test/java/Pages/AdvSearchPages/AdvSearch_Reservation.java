package Pages.AdvSearchPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdvSearch_Reservation {


	public AdvSearch_Reservation(WebDriver driver)
	{

		PageFactory.initElements(driver,this);
	}

	@FindBy(xpath="//form[@id='advancedSearchReservation']/div[@class='advanced-search-form']//div[@class='advancedsearch-criteria-reservation']//input[@id='SearchContract_FirstName']")
	private WebElement search_FirstName;
	
	@FindBy(xpath="//form[@id='advancedSearchReservation']/div[@class='advanced-search-form']//div[@class='advancedsearch-criteria-reservation']//input[@id='SearchContract_LastName']")
	private WebElement search_LastName;
	
	@FindBy(linkText="Back To Dashboard")
	private WebElement backToDashboard;
	
	
	@FindBy(id="SearchForTheThings")
	private WebElement search_btn;
	
	@FindBy(xpath="//div[@id='updateResultsPanel']//span[@id='TotalQueryResults']")
	private WebElement records_match;
	
	@FindBy(xpath="//div[@id='updateResultsPanel']//a[text()='RESET']")
	private WebElement reset_btn;
	
	@FindBy(xpath="//form[@id='advancedSearchReservation']//div[@id='SearchContract_AreaCode-wrapper']/input[@id='SearchContract_AreaCode']")
    private WebElement Ph_AreaCode;

    @FindBy(xpath="//form[@id='advancedSearchReservation']//div[@id='SearchContract_Exchange-wrapper']/input[@id='SearchContract_Exchange']")
    private WebElement Ph_Exchange;

           
    @FindBy(xpath="//form[@id='advancedSearchReservation']//div[@id='SearchContract_LineNumber-wrapper']/input[@id='SearchContract_LineNumber']")
    private WebElement ph_LnNumber;
    
	@FindBy(xpath="//form[@id='advancedSearchReservation']//div[@id='SearchContract_EmailAddress-wrapper']/input[@id='SearchContract_EmailAddress']")
	private WebElement email;
	
	@FindBy(xpath="//form[@id='advancedSearchReservation']//div[@id='SearchContract_CompanyName-wrapper']//input[@id='SearchContract_CompanyName']")
	private WebElement cmpny_Name;
	
	@FindBy(xpath="//form[@id='advancedSearchReservation']//span[text()='Zip Code:']/preceding-sibling::span[@class='button']")
	private WebElement search_Zipcode;
	
	@FindBy(xpath="(//form[@id='advancedSearchReservation']//input[@id='SearchContract_ZipCode'])[1]")
	private WebElement txt_ZipCode;
	
	@FindBy(xpath="//form[@id='advancedSearchReservation']//div[@class='padding-left']//span[text()='Distance']")
	private WebElement clk_Distance;
	
	@FindBy(xpath="//form[@id='advancedSearchReservation']/div[@class='advanced-search-form']//div[@class='filterItem']//label[@class='webchamp-checkbox bold padding']//span[@class='button']")
	private WebElement chk_SearchAllLoc;
	
	
	public void enter_FirstName(String text){
		search_FirstName.sendKeys(text);
	}
	
	public void enter_LastName(String text){
		search_LastName.sendKeys(text);
	}
	
	public void clickSearchbtn()
	{
		search_btn.click();
	}
	
	public String getText_records_match(){
		return records_match.getText();
	}
	
	public void click_Reset(){
		reset_btn.click();
	}
	
	public void enter_Area(String id)
	{
		Ph_AreaCode.sendKeys(id);          //check
	}

	public void clearenter_Area()
	{
		Ph_AreaCode.clear();
	}

	public void enter_Exc(String id)
	{
		Ph_Exchange.sendKeys(id);           //check
	}

	public void clearenter_Exc()
	{
		Ph_Exchange.clear();
	}

	public void enter_loc(String id)
	{
		ph_LnNumber.sendKeys(id);          //check
	}

	public void clearenter_loc()
	{
		ph_LnNumber.clear();
	}
	
	public void enter_Email(String id)
	{
		email.sendKeys(id);          //check
	}
	
	public void enter_CmpnyName(String text){
		cmpny_Name.sendKeys(text);
	}
	
	public void click_zipCode(){
		search_Zipcode.click();	 
	}
	
	public void enter_zipCode(String ZipCode)
	{
		txt_ZipCode.sendKeys(ZipCode);;
	}
	
	public void click_Distance()
	{
		clk_Distance.click();
	}
	
	public void click_SearchAllLocChkbox()
	{
		chk_SearchAllLoc.click();
	}
	
	public void click_BackToDashboard(){
		backToDashboard.click();
	}
	
	
}
