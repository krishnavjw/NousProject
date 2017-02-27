package Pages.IssueManagementPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateRentIncreasePage {

	
	private WebDriver driver;

	@FindBy(xpath="//input[@id='SearchContract_AccountNumber']") 
	private WebElement account_no;
	
	@FindBy(xpath="//a[@id='SearchForTheAccount']") 
	private WebElement search_btn;
	
	@FindBy(xpath="//div[@id='customerDashboard']/div/div[2]/div[2]/div[2]/span/span/span[2]/span") 
	private WebElement quicklink_dropdown;
	
	@FindBy(xpath="//div[14]/div/ul/li[2]") 
	private WebElement create_issue;
	
	@FindBy(xpath="//div[@id='addDocumentDialog']//span[contains(text(),'Select Issue Type')]")
	private WebElement issueTypeDropdown;
	
	@FindBy(xpath="//div[@class='k-list-container k-popup k-group k-reset']//ul/li[contains(text(),'Rent Rate Increase Objection')]")
	private WebElement rentRateIncre;
	
	@FindBy(xpath="//a[contains(text(),'Confirm')]")
	private WebElement confirmBtn;
	
	@FindBy(xpath="//form[@id='rentRateForm']/div[4]/span[3]/span/span/span[2]/span")
	private WebElement reason_dropdown;
	
	@FindBy(xpath="//ul[@id='SelectedReason_listbox']/li[2]")
	private WebElement reason_option;
	
	
	@FindBy(xpath="//textarea[@id='Description']")
	private WebElement entr_explanation;
	
	@FindBy(xpath="//div[@id='createRentRateIssue']/div[1]/div[2]/a[2]")
	private WebElement create_issue_btn;
	
	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement employeeid;
	
	@FindBy(xpath="//div/a[@class='psbutton-priority margin-left ok-button floatright'][contains(text(), 'Submit')]")
	private WebElement employeeid_submitBtn;
	
	@FindBy(xpath="//div[@id='customerDashboard']/div/div[3]/div/div/ul/li[3]")
	private WebElement Account_Activities_Btn;
	
	@FindBy(xpath="//div[@id='customerDashboard']//div[@class='important-info-title title'][contains(text(),'Open Issues:')]")
	private WebElement Open_Issues;
	
	@FindBy(xpath="//div[@id='customerDashboard']//div[@class='textcontent']/a[@class='bold'][contains(text(),'Rent Rate Increase Objection')]")
	private WebElement Issues_RentIncrease;
	
	@FindBy(xpath="//div[@id='activities-grid']/div[2]/table/tbody/tr[1]/td[7]")
	private WebElement Issue_Created;
	
	@FindBy(xpath="//div[@id='divToUpdate']//span[@class='half-margin-left left-column__right-align'][contains(text(),'Assigned to:')]")
	private WebElement AssignedTo;
	
	@FindBy(xpath="//div[@id='divToUpdate']//span[@class='right-column'][contains(text(),'District Manager')]")
	private WebElement DM;
	
	@FindBy(xpath="//div[@id='usernav']/div/ul/li[2]/a")
	private WebElement logoff;
	
	@FindBy(xpath="//div[25]/div[2]/div[2]/div/a[1]")
	private WebElement popup_logoff;
	
	@FindBy(xpath="//div[@id='feeGrid']/div[2]/table/tbody/tr[3]/td[1]/input")
	private WebElement fee;
	
	
	@FindBy(xpath="//form[@id='feeAdjustmentForm']/div/div/div[2]/div[4]/div[1]/span/span/span[2]/span")
	private WebElement fee_reason_dropdown;
	
	@FindBy(xpath="//ul[@id='SelectedReason_listbox']/li[2]")
	private WebElement fee_select_reason;
	
	@FindBy(xpath="//a[@id='lnkCreateIssue'][contains(text(),'Create Issue')]")
	private WebElement fee_createIssue_Button;
	
	@FindBy(xpath="//div[@id='customerDashboard']//div[@class='textcontent']/a[@class='bold'][contains(text(),'Fee Adjustment Request')][1]")
	private WebElement Issues_FeeAdjust;
	
	@FindBy(xpath="//form[@id='rentRateForm']/div[1]/div[2]/div[1]/label/span[1]")
	private WebElement space_DTM_Stage;
	
	@FindBy(xpath="//a[@class='psbutton-normal floatleft margin-right'][contains(text(),'Back To Dashboard')]")
	private WebElement back_to_dashboard;
	
	@FindBy(xpath="//div[@id='issueDecisionTemplate']//span[text()='Approve']/preceding-sibling::span[@class='button']")
	private WebElement approve_radioButton;
	
	@FindBy(xpath="//div[@id='call-screen-container']//div/label/span[1]")
	private WebElement phone_no_checkbox;
	
	@FindBy(xpath="//div[@class='call-screen-call-result']//span[@class='k-select']/span[@class='k-icon k-i-arrow-s'][contains(text(),'select')]")
	private WebElement call_result;
	
	@FindBy(xpath="//div[@class='k-animation-container']/div/ul/li[3]")
	private WebElement call_result_option;
	
	@FindBy(xpath="(//div/input[@placeholder='Enter Notes...'])[1]")
	private WebElement Enter_comments;
	
	@FindBy(xpath="//a[@id='saveCallButton']")
	private WebElement comments_submit;
	
	@FindBy(xpath="//div[@id='feeGrid']/div[2]/table/tbody/tr[5]/td[1]/input")
	private WebElement space_fee_adjustment;
	
	@FindBy(xpath="//div[@id='divToUpdate']/div[2]/div[13]/span[2]/span/label[1]/span[1]")
	private WebElement override_consession_yes;
	
	@FindBy(xpath="//div[18]/div[2]/div[2]/div/a[1]")
	private WebElement confirm_yes;
	
	@FindBy(xpath="//div[@id='concessionList']/span[2]/span/span/span[2]/span")
	private WebElement consession_dropdown;
	
	@FindBy(xpath="//ul[@id='concessionsDropDownList_listbox']/li[2]")
	private WebElement consession_dropdown_option;
	
	@FindBy(xpath="//div[22]/div[2]/div[2]/div/a[1]")
	private WebElement save_and_close;
	
	
	
	public CreateRentIncreasePage(WebDriver driver){
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	
	public void enter_text(String text){
		account_no.sendKeys(text);
	}
	
	public void clk_searchButton(){
		search_btn.click();
	}
	
	public void clk_quicklink_dropdown(){
		quicklink_dropdown.click();
	}
	
	public void clk_create_issue(){
		create_issue.click();
	}
	
	public void click_IssueTypeDropDown(){
		issueTypeDropdown.click();
	}
	
	public void select_RentRateIncreObjection(){
		rentRateIncre.click();
	}
	
	public void click_ConfirmmBtn(){
		confirmBtn.click();
	}
	
	public void click_reason_dropdown(){
		reason_dropdown.click();
	}
	
	public void select_reason(){
		reason_option.click();
	}
	
	public void enter_explanation(String exp){
		entr_explanation.sendKeys(exp);
	}
	
	public void clk_submit(){
		create_issue_btn.click();
	}
	
	public void enter_empid(String id){
		employeeid.sendKeys(id);
	}
	
	public void clk_empid_submit(){
		employeeid_submitBtn.click();
	}
	
	public void clk_AccountActivities(){
		Account_Activities_Btn.click();
	}
	
	public boolean display_OpenIssue(){
		return Open_Issues.isDisplayed();
	}
	
	public void clk_rentIncrease(){
		Issues_RentIncrease.click();
	}
	
	public boolean display_IssueCreated(){
		return Issue_Created.isDisplayed();
	}
	
	public boolean display_AssignedTo(){
		return AssignedTo.isDisplayed();
	}
	
	public boolean display_DistrictMgr(){
		return DM.isDisplayed();
	}
	
	public void clk_logoff(){
		logoff.click();
	}
	
	public void clk_logoff_poup(){
		popup_logoff.click();
	}
	
	public void clk_fee(){
		fee.click();
	}
	
	public void clk_fee_reason_dropdown(){
		fee_reason_dropdown.click();
	}
	
	public void select_fee_reason(){
		fee_select_reason.click();
	}
	
	public void clk_fee_createIssue(){
		fee_createIssue_Button.click();
	}
	
	public void clk_fee_AdjustIsuue(){
		Issues_FeeAdjust.click();
	}
	
	public void clk_space_DTM_Lien(){
		space_DTM_Stage.click();
	}
	
	public void clk_back_to_dashboard(){
		back_to_dashboard.click();
	}
	
	public void clk_approve_radiobutton(){
		approve_radioButton.click();
	}
	
	public void clk_phone_no(){
		phone_no_checkbox.click();
	}
	
	public void clk_call_result(){
		call_result.click();
	}
	
	public void clk_call_result_option(){
		call_result_option.click();
	}
	
	public void enter_comments(String com){
		Enter_comments.sendKeys(com);
	}
	
	public void clk_comments_submit(){
		comments_submit.click();
	}
	
	public void clk_space_fee(){
		space_fee_adjustment.click();
	}
	
	public void clk_consession_yes(){
		override_consession_yes.click();
	}
	
	public void clk_consession_dropdown(){
		consession_dropdown.click();
	}
	
	public void clk_consession_option(){
		consession_dropdown_option.click();
	}
	
	public void clk_save_and_close(){
		save_and_close.click();
	}
	
	public void clk_confirm_yes(){
		confirm_yes.click();
	}
}

