package Pages.AdvSearchPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import GenericMethods.DataBase_JDBC;

public class Advance_Search {

	WebDriver driver;

	@FindBy(id = "SearchForTheAccount")
	private WebElement search_btnClick;

	@FindBy(id = "SearchContract_AccountNumber")
	private WebElement search_accNo;

	@FindBy(xpath = "//form[@id='searchForm']//div[@id='SearchContract_FirstName-wrapper']/input[@id='SearchContract_FirstName']")
	private WebElement search_FirstName;

	@FindBy(xpath = "//form[@id='searchForm']//div[@id='SearchContract_LastName-wrapper']/input[@id='SearchContract_LastName']")
	private WebElement search_LastName;

	@FindBy(xpath = "//form[@id='searchForm']//input[@id='SearchContract_PropertyNumber']")
	private WebElement search_LocationNumber;

	@FindBy(xpath="//form[@id='searchForm']//input[@id='SearchByPropertyNumber']")
    private WebElement search_Location_Radio1;

    @FindBy(xpath="//form[@id='searchForm']//input[@id='SearchByPropertyNumber']/following-sibling::span[@class='button']")
    private WebElement search_Location_Radio;

	@FindBy(xpath = "//form[@id='searchForm']//span[text()='Zip Code:']/preceding-sibling::span[@class='button']")
	private WebElement search_Zipcode;

	// Added 15-09-2016
	@FindBy(xpath = "//form[@id='searchForm']//input[@id='SearchByState']/following-sibling::span[@class='button']")
	private WebElement search_State_radioBtn;

	@FindBy(xpath = "//form[@id='searchForm']//span[@class='k-widget k-dropdown k-header k-ddl ddlState']")
	private WebElement state_Dropdown;

	@FindBy(xpath = "(//ul[@id='SearchContract_StateCode_listbox'])[2]/li[@class='k-item']")
	private WebElement state_DropdownListbox;

	@FindBy(xpath = "//form[@id='searchForm']//input[@id='SearchContract_ZipCode']")
	private WebElement search_ZipcodeField;

	@FindBy(xpath = "//span[text()='Include Inactive/ Former Locations']/preceding-sibling::span[@class='button']")
	private WebElement search_IncludeInactive;

	@FindBy(xpath = "//form[@id='searchForm']//div[@id='stateError']")
	private WebElement stateErrorMessage;

	@FindBy(xpath = "//div[@id='updateResultsPanel']//span[@id='TotalQueryResults']/font")
	private WebElement searchResultsCount;

	@FindBy(xpath = "//span[@id='SearchQueryItems']")
	private WebElement searchResultsName;

	@FindBy(xpath = "//form[@id='searchForm']//span[@class='k-widget k-dropdown k-header k-ddl ddlRentalStatus']//span[@class='k-input']")
	private WebElement statusDropdownValue;

	@FindBy(xpath = "//form[@id='searchForm']//div[@class='location-column']//h3")
	private WebElement storageSection;

	@FindBy(xpath = "//form[@id='searchForm']//div[@class='criteria-row double-padding-bottom']/h3")
	private WebElement searchForSection;

	@FindBy(xpath = "//form[@id='accountSearchForm']//div[@class='container-heading gradient']/following-sibling::h3")
	private WebElement searchBySection;

	@FindBy(xpath = "//span[text()='Include WebChamp 1 Properties']/preceding-sibling::span[@class='button']")
	private WebElement search_IncludeWebChamp;

	@FindBy(xpath = "//form[@id='searchForm']//label[@class='webchamp-radio-button']/input[@id='SearchContract_CustomerSearchType']")
	private WebElement AccType_Cm;

	@FindBy(xpath = "//span[text()='Auction Buyers']/preceding-sibling::span[@class='button']")
	private WebElement AccType_AuctionBuyer;

	@FindBy(xpath = "//span[text()='National Accounts']/preceding-sibling::span[@class='button']")
	private WebElement AccType_NtAcc;

	@FindBy(xpath = "//form[@id='searchForm']//div[@id='SearchContract_CompanyName-wrapper']/input[@id='SearchContract_CompanyName']")
	public WebElement Search_CmpName;

	@FindBy(xpath = "//form [@id='accountSearchForm']//h3[text()='Customer Search']")
	private WebElement advSearchPage_Title;

	@FindBy(xpath = "//form[@id='searchForm']//h3[contains(text(),'Storage Property Location')]")
	private WebElement storagePropertyLocSection_Title;

	@FindBy(xpath = "//form[@id='searchForm']//div[@id='SearchContract_AreaCode-wrapper']/input[@id='SearchContract_AreaCode']")
	private WebElement Ph_AreaCode;

	@FindBy(xpath = "//form[@id='searchForm']//div[@id='SearchContract_Exchange-wrapper']/input[@id='SearchContract_Exchange']")
	private WebElement Ph_Exchange;

	@FindBy(xpath = "//form[@id='searchForm']//div[@id='SearchContract_LineNumber-wrapper']/input[@id='SearchContract_LineNumber']")
	private WebElement ph_LnNumber;

	@FindBy(xpath = "//form[@id='searchForm']//div[@id='SearchContract_IdentificationNumber-wrapper']/input[@id='SearchContract_IdentificationNumber']")
	private WebElement id_Number;

	@FindBy(xpath = "//form[@id='searchForm']//div[@id='SearchContract_EmailAddress-wrapper']/input[@id='SearchContract_EmailAddress']")
	private WebElement search_Email;

	@FindBy(xpath = "//form[@id='searchForm']//div[@id='SearchContract_RentalUnitNumber-wrapper']/input[@id='SearchContract_RentalUnitNumber']")
	private WebElement search_Space;

	@FindBy(xpath = "//form[@id='searchForm']//input[@id='rbMatchAny']")
	private WebElement match_searchField;

	@FindBy(xpath = "//form[@id='searchForm']//input[@id='rbMatchAll']")
	private WebElement match_AllsearchField;

	@FindBy(xpath = "//a[text()='RESET']")
	private WebElement reset_btn;

	@FindBy(id = "SearchForTheThings")
	private WebElement search_btn;

	@FindBy(id = "SearchForTheAccount")
	private WebElement searchAcc_btn;

	@FindBy(linkText = "Back To Dashboard")
	private WebElement backToDashboard_btn;

	@FindBy(id = "TotalQueryResults")
	private WebElement txt_RecordsMatched;

	@FindBy(id = "TotalQueryResults")
	private WebElement txt_NonExCustSearchRes;

	@FindBy(id = "SearchContract_ReservationNumber")
	private WebElement txt_Reservation;

	@FindBy(xpath = "//div[@id='status']//span[@class='k-input']")
	private WebElement status_DropDown;

	@FindBy(id = "SearchForTheReservation")
	private WebElement search_btn_res;

	@FindBy(xpath = "//form[@id='searchForm']//div[@class='searchAllLocations']//span[text()='Search All Locations']/preceding-sibling::span[@class='button']")
	private WebElement search_AllLoc;

	@FindBy(xpath = "//div[@id='AdvancedSearchCustomer']//div//label//input[@id='SearchByPropertyNumber']")
	private WebElement chk_Location_Radio;

	@FindBy(xpath = "(//div//div[@class='criteria-row double-padding-bottom']//h3[contains(text(),'Search For:')])[2]")
	private WebElement txt_SearchFor;

	@FindBy(xpath = "//form[@id='accountSearchForm']//div/h3[contains(text(),'search by')]")
	private WebElement txt_SearchBy;

	@FindBy(xpath = "(//input[@id='SearchContract_ZipCode'])[2]")
	private WebElement txt_ZipCode;

	@FindBy(xpath = "//a[@id='SearchForTheAccount']")
	private WebElement clk_SearchAccount;

	@FindBy(xpath = "//form[@id='searchForm']//div[@id='zipCodeError']")
	private WebElement txt_ZipCodeErrorMsg;

	@FindBy(xpath = "//form[@id='searchForm']//div[@class='padding-left']//span[text()='Distance']")
	private WebElement clk_Distance;

	@FindBy(xpath = "(//input[@id='SearchContract_PropertyNumber'])[2]")
	private WebElement txt_locationnum;

	@FindBy(xpath = "(//span[@class='k-dropdown-wrap k-state-disabled'])[3]")
	private WebElement txt_statedropdown;

	@FindBy(xpath = "(//input[@id='SearchContract_EmailAddress'])[2]")
	private WebElement search_Email_adv;

	@FindBy(xpath = "//form[@id='accountSearchForm']/div/h3[contains(text(),'search by an acc')]")
	private WebElement searchBySection_Title;

	@FindBy(xpath = "//form[@id='searchForm']/div/div[2]//h3[contains(text(),'Search For:')]")
	private WebElement searchForSection_Title;

	@FindBy(id = "TotalQueryResults")
	private WebElement noCriteriaEntered_EM;

	@FindBy(id = "TotalQueryResults")
	private WebElement noResultsFound_EM;

	@FindBy(xpath = "//form[@id='searchForm']//label[@class='webchamp-radio-button']/input")
	private WebElement CustAccType_radio;

	@FindBy(xpath = "//div[@id='searchByAccountNumber']//h3[text()='Customer Search']")
	private WebElement Hdr_CustSearch;
	
	@FindBy(xpath = "//span[contains(text(),'No criteria was entered')]")
	private WebElement Txt_NoCriteria;
	
	@FindBy(xpath = "//span[text()='Currently Renting']/following-sibling::span/span")
	private WebElement Status_DrpDwn;
	
	@FindBy(xpath="//span[@id='TotalQueryResults']")
	private WebElement queryResult;

	@FindBy(xpath="//span[@id='TotalQueryResults']")
	private WebElement queryresult;
	
	@FindBy(xpath="//div[@id='updateResultsPanel']//table//tbody//tr//td[11]/a")
    private WebElement accnum_Grid;
	
	@FindBy(xpath="(//div[@class='k-grid-content ps-container']//tr[1]/td/a)[2]")
	private WebElement First_accountnum_from_searchresult;
	
	@FindBy(xpath="//label/span[text()='Auction Buyers']/preceding-sibling::span[@class='button']")
	private WebElement autionBuyer_link;

	@FindBy(xpath="//label/span[text()='Auction Buyers']/preceding-sibling::span[@class='button']")
	private WebElement autionBuyer_indicator;
	
	@FindBy(xpath="//div[@class='k-grid-content ps-container']/table/tbody/tr/td/a[@class='acctnumber']")
	private WebElement get_AccNum;
	
	
	public Advance_Search(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public String getQueryResultMessage(){
		return queryResult.getText();
	}

	public boolean verifyCustRdBtnIsSelected() {
		return AccType_Cm.isSelected();
	}

	public boolean verifyNoCriteriaMsgIsDisplayed() {
		return Txt_NoCriteria.isDisplayed();
	}

	public boolean verifyAdvSearchPageIsOpened() {
		return Hdr_CustSearch.isDisplayed();
	}

	public void enter_zipCode(String ZipCode) {
		txt_ZipCode.sendKeys(ZipCode);
	}

	public void enterLocationNumber(String locNum) {
		search_LocationNumber.clear();
		search_LocationNumber.sendKeys(locNum);
	}

	public void enterFirstName(String fn) {
		search_FirstName.sendKeys(fn);
	}

	public void enterLastName(String ln) {
		search_LastName.sendKeys(ln);
	}
	
	public void clk_ResetBtn(){
		
		reset_btn.click();
	}

	public void enterAccNum(String accNum) {
		search_accNo.clear();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		search_accNo.sendKeys(accNum);
	}

	public void enterCmpName(String cmpName) {
		Search_CmpName.sendKeys(cmpName);
	}

	public void enterEmail(String email) {
		search_Email.sendKeys(email);
	}

	public void enterSpaceNum(String spaceNum) {
		search_Space.sendKeys(spaceNum);
	}

	public void enterIdNum(String id) {
		id_Number.sendKeys(id);
	}

	public void clickSearchbtn() {
		search_btn.click();
	}

	public void clickSearchAccbtn() {
		searchAcc_btn.click();
	}

	public String get_RecordsMatchedText() {

		return txt_RecordsMatched.getText();
	}

	public void click_backToDashboardbtn() {
		backToDashboard_btn.click();
	}

	public String get_txt_NonExCustSearchRes() {

		return txt_NonExCustSearchRes.getText();
	}

	public void enterReservationNo(String fn) {
		txt_Reservation.sendKeys(fn);
	}

	public String getLocationNum() {
		return search_LocationNumber.getAttribute("value");
	}

	public boolean cus_radio() {
		return AccType_Cm.isSelected();
	}

	public boolean match_AnySearch() {
		return match_searchField.isSelected();
	}

	public void match_AllField() {
		match_AllsearchField.click();
	}

	public void search_All() {
		search_AllLoc.click();
	}

	public void enter_Area(String id) {
		Ph_AreaCode.sendKeys(id); // check
	}

	public void clearenter_Area() {
		Ph_AreaCode.clear();
	}

	public void enter_Exchange(String id) {
		Ph_Exchange.sendKeys(id); // check
	}

	public void clearenter_Exc() {
		Ph_Exchange.clear();
	}

	public void enter_LineNum(String id) {
		ph_LnNumber.sendKeys(id); // check
	}

	public void clearenter_loc() {
		ph_LnNumber.clear();
	}

	public void clearFirstName() {
		search_FirstName.clear();
	}

	public void clearLastName() {
		search_LastName.clear();
	}

	public void clearSpaceNum() {
		search_Space.clear();
	}

	public void clearEmail() {
		search_Email.clear();
	}

	public void clearIdNum() {
		id_Number.clear();
	}

	public boolean verifyDashboard() {
		return backToDashboard_btn.isDisplayed();
	}

	public void clickSearchbtn_Res() {
		search_btn_res.click();
	}

	public String getAttribute_LocationRadio() {
		return chk_Location_Radio.getAttribute("checked");
	}

	public void clickButton() {
		search_btnClick.click();
	}

	public String get_txtSearchFor() {
		return txt_SearchFor.getText();
	}

	public String get_txt_SearchBy() {
		return txt_SearchBy.getText();
	}

	public boolean isLocationSelected()
    {
           return  search_Location_Radio1.isSelected();
    
    }


	// 15-09-2016
	public boolean isStateDisplayed() {
		return search_State_radioBtn.isDisplayed();
	}

	public void click_StateRadioBtn() {
		search_State_radioBtn.click();
	}

	public void click_StateDropdown() {
		state_Dropdown.click();
	}

	public boolean isLocationNumberEnabled() {
		return search_LocationNumber.isEnabled();
	}

	public String getLocationNumber() {
		return search_LocationNumber.getAttribute("value");
	}

	public void clickLocationNumber() {
		search_LocationNumber.click();
	}

	public boolean isZipcodeEnabled() {
		return search_ZipcodeField.isEnabled();
	}

	public String validateStateErrorMsg() {
		return stateErrorMessage.getText();
	}

	public String validateSearchResultsCount() {
		return searchResultsCount.getText();
	}

	public String validateSearchResultsName() {
		return searchResultsName.getText();
	}

	public String verifyStatusDropdownValue() {
		return statusDropdownValue.getText();
	}

	public void clickStatusDropdown() {
		statusDropdownValue.click();
	}

	public String verifySearchForSection() {
		return searchForSection.getText();
	}

	public String verifyStorageSection() {
		return storageSection.getText();
	}

	public String verifySearchBySection() {
		return searchBySection.getText();
	}

	public boolean isLocationEnabled() {
		return txt_locationnum.isEnabled();

	}

	public boolean isStatedropdownEnabled() {
		return txt_statedropdown.isEnabled();
	}

	public void enterEmail_adv(String email) {
		search_Email_adv.sendKeys(email);
	}

	public void clear_ZipCode() {
		txt_ZipCode.clear();
	}

	public String getAttribute_Zipcode() {
		return txt_ZipCode.getAttribute("value");
	}

	public String getAttribute_Location() {
		return search_LocationNumber.getAttribute("value");
	}

	public void clk_SearchAccount() {
		clk_SearchAccount.click();
	}

	public boolean isZipCodeDisplayed() {
		return search_Zipcode.isDisplayed();
	}

	public boolean isLocationDisplayed() {
		return search_Location_Radio.isDisplayed();
	}

	public void click_zipCode() {
		search_Zipcode.click();
	}

	public String get_txt_ZipCodeErrorMsg() {
		return txt_ZipCodeErrorMsg.getText();
	}

	public void click_Distance() {
		clk_Distance.click();
	}

	public String getadvSearchPage_Title() {
		return advSearchPage_Title.getText();
	}

	public String getstoragePropertyLocSection_Title() {
		return storagePropertyLocSection_Title.getText();
	}

	public void clearsearch_accNo() {
		search_accNo.clear();
	}

	public String getsearchBySection_Title() {
		return searchBySection_Title.getText();
	}

	public String getsearchForSection_Title() {
		return searchForSection_Title.getText().trim();
	}

	public boolean isAccType_Cm() {
		return CustAccType_radio.isSelected();
	}

	public String getstatus_DropDown() {
		return status_DropDown.getText();
	}

	public boolean ismatch_searchField() {
		return match_searchField.isSelected();
	}

	public String getnoCriteriaEntered_EM() {
		return noCriteriaEntered_EM.getText();
	}

	public String getnoResultsFound_EM() {
		return noResultsFound_EM.getText();
	}

	public boolean verifyCustomerInfoWithDB(String SQLQueryCount, String SQLQuery, String xpath) {
		Boolean flag = false;
		try {
			String TxtRwCnt = driver.findElement(By.xpath("//span[@id='TotalQueryResults']")).getText();
			String[] ArrRwCnt = TxtRwCnt.split(" ");
			String RwCnt;
			if (ArrRwCnt[0].equals("NO"))
				RwCnt = "0";
			else
				RwCnt = ArrRwCnt[0];

			String DBRwCnt = DataBase_JDBC.executeSQLQuery(SQLQueryCount);
			Reporter.log("RwCnt: " + RwCnt, true);
			Reporter.log("DBRwCnt: " + DBRwCnt, true);
			if (RwCnt.equals(DBRwCnt)) {
				Reporter.log("Row count is matching with DB", true);

				int IntRwCnt = Integer.parseInt(RwCnt);
				if (!(IntRwCnt == 0)) {
					List<WebElement> Ls = driver.findElements(By.xpath(xpath));
					Thread.sleep(100);

					String[] SortLs = new String[Ls.size()];
					for (int l = 0; l < Ls.size(); l++) {
						SortLs[l] = Ls.get(l).getText();
					}
					List<String> Ls_DB = DataBase_JDBC.executeSQLQuery_List(SQLQuery);

					int count = 0;
					for (int j = 0; j < SortLs.length; j++) {

						for (int i = 0; i < Ls_DB.size(); i++) {
							Reporter.log("DB FN :" + i + Ls_DB.get(i), true);
							Reporter.log(j + SortLs[j], true);
							SortLs[j] = SortLs[j].replaceAll(" ", "");
							SortLs[j] = SortLs[j].replace("(", "");
							SortLs[j] = SortLs[j].replace(")", "");
							SortLs[j] = SortLs[j].replace("-", "");

							// Reporter.log(Ls.get(i).getText(),true);
							Thread.sleep(100);
							if (Ls_DB.get(i).equalsIgnoreCase(SortLs[j])) {
								count++;
								Reporter.log("count is:" + count, true);
								Ls_DB.remove(i);
								break;

							}

						}
						if (count > 5)
							break;

					}
					if (count == 5) {
						flag = true;
					} else if (count == SortLs.length)
						flag = true;
					else
						flag = false;
				}

			} else {
				Reporter.log("Row count is not matching with DB", true);
				flag = false;
			}
		} catch (Exception e) {
			Reporter.log("Ex: " + e, true);
			flag = false;
		}
		return flag;
	}

	public void Sel_StatusDrpDwn(String status) {
		Status_DrpDwn.click();
		List<WebElement> ls = driver.findElements(By.xpath("//ul[@id='SearchContract_SearchStatusID_listbox']/li"));
		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i).getText().equals(status)) {
				ls.get(i).click();
				break;
			}
		}

	}

	public String get_RecordsMatchedColor() {
		return txt_RecordsMatched.getCssValue("color");
	}
	public String getResultsText(){
		String result = queryresult.getText().trim();
		return result;
	}
	
	
	
	//AWB2.0
	
	
	public void click_Auctionbuyer_radiobtn() {
		AccType_AuctionBuyer.click();
	}
	
	
	public void clk_firstAccnum_resultgrid(){
		
		First_accountnum_from_searchresult.click();
	}
	
	public String gettext_firstAccnum_resultgrid(){
		
		return First_accountnum_from_searchresult.getText();
	}
	
	public void click_accnum_Grid(){
	       accnum_Grid.click();
	}
	
	public boolean verify_autionBuyer_indicator(){
		return autionBuyer_indicator.isDisplayed();
	}
	
	
	public String get_CustomerAccountNum(){
		return get_AccNum.getText().trim();
	}
	
	public void clickOnAccNum(){
		get_AccNum.click();
		
	}
	
	
	public void click_auctionBuyer_radiobtn(){
		autionBuyer_link.click();
	}
	
}
