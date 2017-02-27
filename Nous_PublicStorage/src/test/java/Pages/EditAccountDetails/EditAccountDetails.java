package Pages.EditAccountDetails;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EditAccountDetails {


	@FindBy(xpath="//div/span[text()='Edit Account Details']")
	private WebElement get_EditAccDetailsTitle;

	@FindBy(xpath="//div[text()='Select Workflow']")
	private WebElement get_SelectWorkflowtxt;

	@FindBy(xpath="//span[text()='Customer Information']")
	private WebElement get_CustomerInformationRadioBtnTxt;

	@FindBy(xpath="//span[text()='Emergency Contact']")
	private WebElement get_EmergencyContactRadioBtnTxt;


	@FindBy(xpath="//span[text()='Authorized Access']")
	private WebElement get_AuthorizedAccessRadioBtnTxt;

	@FindBy(xpath="//span[text()='Military Status']")
	private WebElement get_MilitaryStatusRadioBtnTxt;

	@FindBy(xpath="//span[text()='Email Communication Preferences']")
	private WebElement get_EmailCommunicationPreferencesRadioBtnTxt;

	@FindBy(xpath="//span[text()='Manage Property/Space Access']")
	private WebElement get_ManagePropertySpaceAccessRadioBtnTxt;

	@FindBy(xpath="//span[text()='Other Customer Status']")
	private WebElement get_OtherCustomerStatusRadioBtnTxt;

	@FindBy(xpath="//span[text()='Resale/Tax Exemption']")
	private WebElement get_ResaleTaxExemptionRadioBtnTxt;

	@FindBy(xpath="//div[text()='Is the customer present?']")
	private WebElement get_thecustomerpresentRadioBtnTxt;

	@FindBy(xpath="//span[text()='Yes']/preceding-sibling::span")
	private WebElement clk_YesRadioBtn;

	@FindBy(xpath="//span[text()='No']/preceding-sibling::span")
	private WebElement clk_NoRadioBtn;
	@FindBy(xpath = "//span[text()='Customer Information']/preceding-sibling::span[@class='button']")
	private WebElement CustomerInformationRadioBtn;

	@FindBy(xpath="//a[contains(text(),'Cancel')]")
	private WebElement clk_CancelBtn;

	@FindBy(xpath="//a[contains(text(),'Launch')]")
	private WebElement clk_LaunchBtn;

	@FindBy(xpath="//span[text()='Merge Accounts']")
	private WebElement clk_MergeAccountRadioBtn;

	@FindBy(xpath = "//span[text()='Military Status']/preceding-sibling::span[@class='button']")
	private WebElement MilitaryStatusBtn; 






	public EditAccountDetails(WebDriver driver){

		PageFactory.initElements(driver, this);
	}

	public String get_EditAccDetailsTitle(){

		return get_EditAccDetailsTitle.getText().trim();
	}

	public boolean isEditAccDetailsPopUpDisplayed(){

		return get_EditAccDetailsTitle.isDisplayed();
	}

	public boolean isSelectWorkflowtxtDisplayed(){

		return get_SelectWorkflowtxt.isDisplayed();
	}

	public boolean isCustomerInformationRadioBtnTxtDispalyed(){

		return get_CustomerInformationRadioBtnTxt.isDisplayed();
	}

	public boolean isEmergencyContactRadioBtnTxtDispalyed(){

		return get_EmergencyContactRadioBtnTxt.isDisplayed();
	}

	public boolean isAuthorizedAccessRadioBtnTxtDispalyed(){

		return get_AuthorizedAccessRadioBtnTxt.isDisplayed();
	}
	public boolean isMilitaryStatusRadioBtnTxtDispalyed(){

		return get_MilitaryStatusRadioBtnTxt.isDisplayed();
	}

	public boolean isEmailCommunicationPreferencesRadioBtnTxtDispalyed(){

		return get_EmailCommunicationPreferencesRadioBtnTxt.isDisplayed();
	}

	public boolean isManagePropertySpaceAccessRadioBtnTxtDispalyed(){

		return get_ManagePropertySpaceAccessRadioBtnTxt.isDisplayed();
	}
	public boolean isOtherCustomerStatusRadioBtnTxtDispalyed(){

		return get_OtherCustomerStatusRadioBtnTxt.isDisplayed();
	}
	public boolean isResaleTaxExemptionRadioBtnTxtDispalyed(){

		return get_ResaleTaxExemptionRadioBtnTxt.isDisplayed();
	}

	public boolean isthecustomerpresentRadioBtnTxtDispalyed(){

		return get_thecustomerpresentRadioBtnTxt.isDisplayed();
	}


	public boolean isYesRadioBtnTxtDispalyed(){

		return clk_YesRadioBtn.isDisplayed();
	}

	public void clk_YesRadioBtn(){
		clk_YesRadioBtn.click();
	}


	public boolean isNoRadioBtnTxtDispalyed(){

		return clk_NoRadioBtn.isDisplayed();
	}

	public void clk_NoRadioBtn(){
		clk_NoRadioBtn.click();
	}

	public boolean isCancelBtnDisplayed(){

		return clk_CancelBtn.isDisplayed();
	}

	public void clk_CancelBtn(){

		clk_CancelBtn.click();
	}

	public boolean isLaunchBtnDisplayed(){

		return clk_LaunchBtn.isDisplayed();
	}

	public void clk_LaunchBtn(){

		clk_LaunchBtn.click();
	}


	public void clk_CustomerInformationRadiobtn(){

		get_CustomerInformationRadioBtnTxt.click();
	}





	public void clk_OtherCustomerStatusRadioBtn(){

		get_OtherCustomerStatusRadioBtnTxt.click();
	}


	public void clk_yesRadioBtn(){

		clk_YesRadioBtn.click();
	}

	public boolean isMergeAccountRadioBtnDisplayed(){

		return clk_MergeAccountRadioBtn.isDisplayed();
	}

	public void clk_MergeAccountRadioBtn(){

		clk_MergeAccountRadioBtn.click();
	}

	public void clk_MilitaryStatusRadioBtn() {
		MilitaryStatusBtn.click();
	} 

	public void clk_CustomerInfoRadioBtn() {
		CustomerInformationRadioBtn.click();
	}
	
	public void clk_EmergencycontactRadioBtn() {
		get_EmergencyContactRadioBtnTxt.click();
	}

}
