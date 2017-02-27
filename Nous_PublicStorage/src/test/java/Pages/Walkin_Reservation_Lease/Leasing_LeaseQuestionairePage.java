package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Leasing_LeaseQuestionairePage 
{
	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header k-ddl storage-options-dropdown']//span[text()='Select']")
	private WebElement storageContent;

	@FindBy(xpath="//div[text()='Add Insurance?']/following-sibling::div//span[text()='Yes']/preceding-sibling::span")
	private WebElement addInsurance_yes;

	@FindBy(xpath="//div[text()='Add Insurance?']/following-sibling::div//span[text()='No']/preceding-sibling::span")
	private WebElement addInsurance_No;

	@FindBy(xpath="//form[@id='addendumsForm']//div[@class='padding lease-addendums-storage-unit']//span[@class='k-widget k-dropdown k-header k-ddl insurance-dropdown']")
	private WebElement coverage_list;


	@FindBy(xpath="//form[@id='addendumsForm']//div[@class='padding lease-access-keypad-zone']//span[@class='k-widget k-dropdown k-header access-zone']")
	private WebElement accessZone;

	@FindBy(xpath="//form[@id='addendumsForm']//div[@class='padding lease-access-keypad-zone']//span[@class='k-widget k-dropdown k-header keypad-zone']")
	private WebElement ketpadZone;

	@FindBy(id="confirmWithCustomer")
	private WebElement confirmCust;

	@FindBy(xpath="//a[@id='notes-add']")
	private WebElement addNotesLink;

	@FindBy(id="saveandproceed")
	private WebElement saveAndProceedBtn;

	@FindBy(xpath = "//a[text()='Save and Proceed']")
	private WebElement saveandproceed;

	public Leasing_LeaseQuestionairePage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	public void clickAddNotesBtn(){
		addNotesLink.click();
	}

	public void clickSaveAndProceed(){
		saveandproceed.click();
	}


	public void clickStorageContent()
	{
		storageContent.click();
	}

	public void clickAddInsuranceYes()
	{
		addInsurance_yes.click();
	}

	public void clickAddInsuranceNo()
	{
		addInsurance_No.click();
	}

	public void clickCoverageList()
	{
		coverage_list.click();
	}



	public void clickAccessZone()
	{
		accessZone.click();
	}

	public void clickKeypadZone()
	{
		ketpadZone.click();
	}

	public void clickConfirmCust()
	{
		confirmCust.click();
	}


	public void clickSaveAndProceedBtn()
	{
		saveAndProceedBtn.click();
	}

	public boolean isInsuranceYesRadioBtnDisplayed(){

		return	addInsurance_yes.isDisplayed();
	}
	public boolean verifySaveAndProceedBtn(){
		return saveAndProceedBtn.isDisplayed();
	}



}
