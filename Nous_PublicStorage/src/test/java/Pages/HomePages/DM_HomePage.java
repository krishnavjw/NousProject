package Pages.HomePages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import GenericMethods.DataBase_JDBC;

public class DM_HomePage {
	WebDriver driver;

	@FindBy(xpath="//a[contains(text(),'Advanced Search')]")
	private WebElement advancedSearchLnk;

	@FindBy(xpath="//span[@class='header-location-nickname']")
	private WebElement Sitenumberof_DM;


	@FindBy(xpath = "//h1[text()='Customer Search']")
	private WebElement DMDashBoardTitle;

	@FindBy(xpath = "//a[contains(text(),'Advanced Search')]")
	private WebElement advSearchLink;


	@FindBy(xpath = "//span[@id='TotalQueryResults' and contains(text(),'No results found matching your criteria')]")
	public WebElement errorMessage;

	@FindBy(xpath="//form[@id='QuickSearchForm']/span[1]//span[contains(text(),'select')]")
	private WebElement districtSearchDropdown;

	@FindBy(xpath="//div[@id='districtId-list']//ul[@id='districtId_listbox']/li[2]")
	private WebElement selDistrict;

	@FindBy(xpath="//form[@id='QuickSearchForm']/span[2]//span[contains(text(),'select')]")
	private WebElement siteDropdown;

	@FindBy(xpath="//div[@id='siteId-list']//ul[@id='siteId_listbox']/li[2]")
	private WebElement selSite;

	@FindBy(xpath="//a/span[text()='Manage Property']")
	private WebElement managePropLnk;

	@FindBy(xpath="//div[@id='appointmentsPanel_new']/div/h3")
	public WebElement openIssuesModule;

	@FindBy(xpath="//div[@id='dashboard-issue-header']/a[@href='/Dashboard/LoadViewAllIssuesBySite']")
	public WebElement viewAllIssuesLink;

	@FindBy(xpath="//div[@id='issue-grid']//table/thead/tr/th[text()='Location']")
	public WebElement columnHeader_Location_OpenIssues;

	@FindBy(xpath="//div[@id='issue-grid']//table/thead/tr/th[text()='Total Issues']")
	public WebElement columnHeader_TotalIssues_OpenIssues;

	@FindBy(xpath="//div[@id='issue-grid']//table/thead/tr/th[contains(text(),'Age of Oldest Open Issue')]")
	public WebElement columnHeader_AgeOfOldestOpenIssue_OpenIssues;

	@FindBy(xpath="//h1[contains(text(),'Task List')]")
	private WebElement TaskListModule;

	@FindBy(xpath="//h4[text()='Filter:']/following-sibling::span/span/span[@class='k-input']")
	private WebElement get_DefaultDropDownValueFromTaskList;

	@FindBy(xpath="//h4[text()='Filter:']/following-sibling::span/span")
	private WebElement clk_TaskListDropDwn;
	//=====Task list module header xpath===========
	@FindBy(xpath="//div[@id='task-grid']/div[@class='k-grid-header']//tr/th[@data-field='StatusDesc']")
	private WebElement Statusheader;

	@FindBy(xpath="//div[@id='task-grid']/div[@class='k-grid-header']//tr/th[@data-field='PriorityName']")
	private WebElement Priorityheader;

	@FindBy(xpath="//div[@id='task-grid']/div[@class='k-grid-header']//tr/th[@data-field='Title']")
	private WebElement Nameheader;

	@FindBy(xpath="//div[@id='task-grid']/div[@class='k-grid-header']//tr/th[@data-field='DueDate']")
	private WebElement Dueheader;

	@FindBy(xpath="//input[@id='SearchString']")
	public WebElement searchField_Txt;

	@FindBy(xpath="//a[@id='linkSearch']")
	public WebElement search_Btn;

	@FindBy(xpath="//form[@id='QuickSearchForm']//span[@class='k-widget k-dropdown k-header margin-right']")
	public WebElement districtDropdown;


	@FindBy(xpath="//a[@id='linkAdvancedSearch']")
	public WebElement advanceSearchLink;

	@FindBy(xpath="//h3[contains(text(),'Reservation Calls')]")
	public WebElement label_Reservation_Calls;

	@FindBy(xpath="//div[@id='dashboard-issue-header']//h3[contains(text(),'Missed')]")
	private WebElement missedDMTasksHeader;

	@FindBy(xpath="//form[@id='QuickSearchForm']//span[@class='k-input']")
	private WebElement location_drpdown;

	@FindBy(xpath="//ul[@id='utilitybar-wrapper']//li//span[text()='Manage Property']")
	private WebElement manageProp_link;


	//===============================================


	public void clk_ManagePropertyLink(){

		managePropLnk.click();
	}

	public void select_Location(){
		List<WebElement> AllOptions = driver.findElements(By.xpath("//div[@id='siteId-list']/ul/li"));
		int totalSize = AllOptions.size();
		for (int i = 0; i < totalSize; i++) {
			if (i == 1) {
				AllOptions.get(i).click();
				break;
			}
		}
	}


	public void clickmanageProp()
	{
		manageProp_link.click();
	}

	public boolean verifyAdvancedSearchLnk(){

		return advanceSearchLink.isDisplayed();
	}

	public boolean label_label_Reservation_Calls(){
		return label_Reservation_Calls.isDisplayed();
	}


	public boolean isMissedDMTasksListDisplayed()
	{
		return missedDMTasksHeader.isDisplayed();
	}


	public void clicklocation_drpdown()
	{
		location_drpdown.click();
	}

	public boolean verifyAllPMTasks(String district,WebDriver driver)
	{


		String query="select distinct title from task where locationcode='"+district+"' and taskstatusid=1";

		Actions act=new Actions(driver);

		List<String> queryResult=DataBase_JDBC.executeSQLQuery_List(query);
		boolean res=true;
		List<WebElement> uiResultElements=driver.findElements(By.xpath("//div[@id='task-grid']/div[2]/table/tbody//td[4]/a"));
		List<String> uiResultTasks=new ArrayList<String>();

		for(WebElement ele:uiResultElements){
			act.moveToElement(ele).build().perform();
			String text=ele.getText();
			if(text.length()!=0){

				uiResultTasks.add(text);}
		}

		System.out.println("query result size"+queryResult.size());
		System.out.println("ui result size"+uiResultTasks.size());

		if(queryResult.size()!=uiResultTasks.size()){
			return true;
		}


		Collections.sort(queryResult);
		Collections.sort(uiResultTasks);



		return queryResult.equals(uiResultTasks);
		//return res;
	}



	public String selectDistrictValue(WebDriver driver){
		List<WebElement> ListWbEle=driver.findElements(By.xpath("//ul[@id='siteId_listbox']//li[@class='k-item']"));
		String actualWbEleText = "";
		for (WebElement ele : ListWbEle) {
			actualWbEleText=ele.getText();
			ele.click();
			break;

		}
		return actualWbEleText;

	}

	public DM_HomePage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}


	public boolean verifyDistrictDropdown(){
		return districtDropdown.isDisplayed();
	}

	public boolean verifySearch_Btn(){
		return search_Btn.isDisplayed();
	}


	public void clk_advancedSearchLnk(){

		advancedSearchLnk.click();
	}

	public void log_off(WebDriver driver) throws InterruptedException{
		Actions act=new Actions(driver);
		JavascriptExecutor js = ((JavascriptExecutor) driver);

		WebElement user = driver.findElement(By.xpath(("//div[@id='usernav']")));
		WebElement logoff1 = driver.findElement(By.xpath("//a[contains(text(),'Log Off')]"));

		act.moveToElement(user).build().perform();
		js.executeScript("arguments[0].click();", logoff1);
		Thread.sleep(5000);

		WebElement logoff2 = driver.findElement(By.xpath("//div[@class='command-row clearfix-container']//a[contains(text(),'Log Off')]"));
		js.executeScript("arguments[0].click();", logoff2);
		Thread.sleep(8000);
	}


	public void clk_SearchBtn(){

		search_Btn.click();
	}

	public boolean verifySearchField(){

		return searchField_Txt.isDisplayed();
	}



	public String Sitenumber_DM(){

		String site= Sitenumberof_DM.getText();

		String s= site.substring(8);

		return s;
	}



	public boolean is_DMDashBoardTitle(){
		return DMDashBoardTitle.isDisplayed();
	}


	public void click_advSearchLink() {
		advSearchLink.click();
	}

	public String get_DMDashBoardTitle() {
		return DMDashBoardTitle.getText();
	}



	public boolean isDMDashBoardDisplayed(){
		return DMDashBoardTitle.isDisplayed();

	}

	public boolean verify_AdvSearchLink(){

		return advSearchLink.isDisplayed();
	}


	public String getErrorMessageDisplayed(WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].scrollIntoView(true);", errorMessage);
		return errorMessage.getText();
	}

	public void clk_DistrictDropDown(){
		districtSearchDropdown.click();
	}

	public void selDistrict(){
		selDistrict.click();
	}

	public void clk_SiteDropDown(){
		siteDropdown.click();
	}

	public void selSiteId(){
		selSite.click();
	}

	public String getSiteId(){
		return selSite.getText();
	}

	public boolean verifyOpenIssuesModule(){
		return openIssuesModule.isDisplayed();
	}

	public boolean verifyviewAllIssuesLink(){
		return viewAllIssuesLink.isDisplayed();
	}

	public void clk_viewAllIssuesLink(){

		viewAllIssuesLink.click();
	}

	public String getHeaderName(){
		String headerName= openIssuesModule.getText();
		return headerName;
	}

	public boolean isExists_Location_OpenIssues(){
		return columnHeader_Location_OpenIssues.isDisplayed();
	}

	public boolean isExists_TotalIssues_OpenIssues(){
		return columnHeader_TotalIssues_OpenIssues.isDisplayed();
	}

	public boolean isExists_AgeOfOldestOpenIssue_OpenIssues(){
		return columnHeader_AgeOfOldestOpenIssue_OpenIssues.isDisplayed();
	}

	//=============Get Task List Header=======================
	public String getStatusHeaderName(){

		return Statusheader.getText().trim();
	}

	public String getPriorityHeaderName(){

		return Priorityheader.getText().trim();
	}


	public String getNameheader(){

		return Nameheader.getText().trim();
	}


	public String getDueheader(){

		return Dueheader.getText().trim();
	}



	public boolean isTaskListModuleDispalyed(){

		return TaskListModule.isDisplayed();
	}

	public String getDefaultDropDownValueFromTaskListModule(){

		return get_DefaultDropDownValueFromTaskList.getText().trim();
	}

	public void clk_TaskListDropDown(){

		clk_TaskListDropDwn.click();
	}

	public void selectTaskListDropDownValues(String val,WebDriver driver) throws Exception{
		clk_TaskListDropDwn.click();
		Thread.sleep(2000);
		List<WebElement> list=driver.findElements(By.xpath("//div[@id='task-filter-list']//ul[@id='task-filter_listbox']//li"));
		Thread.sleep(1000);
		for(WebElement ele:list){
			String option=ele.getText().trim();
			if(option.contains(val)){	
				ele.click();
				break;

			}
		}

	}

	@FindBy(xpath="//div[@class='header-location']//span[contains(text(),'District #')]")
	private WebElement districtnum;
	public String get_districtnum()
	{
		return districtnum.getText();
	}

	@FindBy(xpath="//div[@id='reservationGrid']//table//tbody//tr[1]/td[2]")
	private WebElement lateOrTotalCalls;
	public String get_lateOrTotalCalls()
	{
		return lateOrTotalCalls.getText();
	}

	public void enter_Search(String value){

		searchField_Txt.sendKeys(value);
	}


	@FindBy(xpath="//div[@id='reservationGrid']//table//tbody/tr[1]/td[1]/a")
	private WebElement locationNum_resercalls;
	public String get_locationNum_resercalls()
	{
		return locationNum_resercalls.getText();
	}

	@FindBy(xpath="//div[@id='reservationCallsPanel_new']//h3[text()='Reservation Calls']/preceding-sibling::a")
	private WebElement viewAllReservations;
	public void click_viewAllReservations()
	{
		viewAllReservations.click();
	}



	public String selectLocationValue(WebDriver driver){
		List<WebElement> ListWbEle=driver.findElements(By.xpath("//ul[@id='siteId_listbox']//li[@class='k-item']"));
		//List<WebElement> ListWbEle=driver.findElements(By.xpath("//ul[@id='districtId_listbox']//li[@class='k-item']"));
		String actualWbEleText = "";
		for (WebElement ele : ListWbEle) {
			actualWbEleText=ele.getText();
			ele.click();
			break;

		}
		return actualWbEleText;

	}


	public List<String> allPMTasks(String district,WebDriver driver)
	{


		String query="select distinct title from task where locationcode='"+district+"' and taskstatusid=1";

		List<String> queryResult=DataBase_JDBC.executeSQLQuery_List(query);


		return queryResult;
	}

	public boolean verifyMissedPropertyManagerTasksinDistrict(String district,WebDriver driver)
	{


		String query= "select X.sitenumber from "+ 
				"(select t.name as calltype,r.lastname,r.firstname,r.expectedmoveindate,S.sitenumber, "+
				"case when calltypeid in (4315,4316) and datediff(dd,scheduledcalldatetime, getutcdate())>=1 then 0 else 1 end as active, "+
				"case when calltypeid=4314 and datediff(mi,scheduledcalldatetime, r.recorddatetime)<2 and datediff(mi,scheduledcalldatetime, getutcdate())>30 then 1 else 0 end as IsLate1, "+
				"case when calltypeid in (4507,4506) "+ 
				"and getutcdate()>DATEADD(hh,28,DATEADD(dd, 0, DATEDIFF(dd, 0,DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), scheduledcalldatetime)))) then 1 else 0 end as IsLate2, "+
				"case when calltypeid=4314 and datediff(mi,scheduledcalldatetime, r.recorddatetime)>2 "+ 
				"and getutcdate()>DATEADD(hh,28,DATEADD(dd, 0, DATEDIFF(dd, 0,DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), scheduledcalldatetime)))) then 1 else 0 end as IsLate3 "+
				"from phonecall pc with(nolock) "+
				"join type t with(nolock) on t.typeid=pc.calltypeid "+
				"join reservation r with(nolock) on r.reservationid=pc.ownertableitemid "+
				"join site s with(nolock) on s.siteid=r.siteid "+
				"join Division D with(nolock) on D.Name=S.sitenumber and d.expirationdate is null "+
				"join Division D1 with(nolock) on D1.divisionid=D.parentdivisionid and d1.expirationdate is null "+
				"where r.reservationstatustypeid in (127, 128) "+
				"and pc.scheduledcalldatetime < getutcdate() "+
				"and iscallcompleted is null "+
				"and D1.Name='"+district+"') X "+ 
				"group by X.sitenumber ";

		List<String> queryResult=DataBase_JDBC.executeSQLQuery_List(query);

		List<WebElement> uiResultElements=driver.findElements(By.xpath("//div[@id='missed-property-tasks-grid']//tr//td//a"));
		List<String> uiResultTasks=new ArrayList<String>();

		for(WebElement ele:uiResultElements){
			String text=ele.getText();
			uiResultTasks.add(text);
		}

		if(queryResult.size()!=uiResultTasks.size()){
			return false;
		}


		Collections.sort(queryResult);
		Collections.sort(uiResultTasks);



		return queryResult.equals(uiResultTasks);
	}

	public List<String> missedMissedPropertyManagerTasksinDistrict(String district,WebDriver driver){

		String query= "select X.sitenumber from "+ 
				"(select t.name as calltype,r.lastname,r.firstname,r.expectedmoveindate,S.sitenumber, "+
				"case when calltypeid in (4315,4316) and datediff(dd,scheduledcalldatetime, getutcdate())>=1 then 0 else 1 end as active, "+
				"case when calltypeid=4314 and datediff(mi,scheduledcalldatetime, r.recorddatetime)<2 and datediff(mi,scheduledcalldatetime, getutcdate())>30 then 1 else 0 end as IsLate1, "+
				"case when calltypeid in (4507,4506) "+ 
				"and getutcdate()>DATEADD(hh,28,DATEADD(dd, 0, DATEDIFF(dd, 0,DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), scheduledcalldatetime)))) then 1 else 0 end as IsLate2, "+
				"case when calltypeid=4314 and datediff(mi,scheduledcalldatetime, r.recorddatetime)>2 "+ 
				"and getutcdate()>DATEADD(hh,28,DATEADD(dd, 0, DATEDIFF(dd, 0,DATEADD(mi, DATEDIFF(mi, GETUTCDATE(), GETDATE()), scheduledcalldatetime)))) then 1 else 0 end as IsLate3 "+
				"from phonecall pc with(nolock) "+
				"join type t with(nolock) on t.typeid=pc.calltypeid "+
				"join reservation r with(nolock) on r.reservationid=pc.ownertableitemid "+
				"join site s with(nolock) on s.siteid=r.siteid "+
				"join Division D with(nolock) on D.Name=S.sitenumber and d.expirationdate is null "+
				"join Division D1 with(nolock) on D1.divisionid=D.parentdivisionid and d1.expirationdate is null "+
				"where r.reservationstatustypeid in (127, 128) "+
				"and pc.scheduledcalldatetime < getutcdate() "+
				"and iscallcompleted is null "+
				"and D1.Name='"+district+"') X "+ 
				"group by X.sitenumber ";


		List<String> queryResult=DataBase_JDBC.executeSQLQuery_List(query);


		return queryResult;

	}



}
