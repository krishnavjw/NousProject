package Pages.HomePages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import GenericMethods.DataBase_JDBC;

public class RMHomepage {

	@FindBy(xpath="//a[@id='linkAdvancedSearch']")
	public WebElement advanceSearchLink;

	@FindBy(xpath="//input[@id='SearchString']")
	public WebElement searchField_Txt;

	@FindBy(xpath="//a[@id='linkSearch']")
	public WebElement search_Btn;

	@FindBy(xpath="//div[@id='reservationCallsPanel_new']//div/a")
	public WebElement reservationCalls_Lnk;

	@FindBy(xpath="//div[@id='collectionCallsPanel_new']//div/a")
	public WebElement collectionCalls_Lnk;

	@FindBy(xpath="//form[@id='QuickSearchForm']//span[@class='k-widget k-dropdown k-header margin-right']")
	public WebElement districtDropdown;

	@FindBy(xpath="//form[@id='QuickSearchForm']//span[@class='k-widget k-dropdown k-header margin-right district-dropdown']")
	public WebElement locationDropdown;


	@FindBy(xpath="//div[@id='dashboard-issue-header']/h3")
	public WebElement openIssuesModule;

	@FindBy(xpath="//div[@id='dashboard-issue-header']/a")
	public WebElement viewAllIssuesLink;

	@FindBy(xpath="//div[@id='collectionCallsPanel_new']/div/a")
	public WebElement viewAllDelinquentLink;

	@FindBy(xpath="//div[@id='issue-grid']//table/thead/tr/th[text()='District']")
	public WebElement columnHeader_District_OpenIssues;

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

	@FindBy(xpath="//h1[text()='Customer Search']")
	private WebElement title;

	//=====Task list module header xpath===========
	@FindBy(xpath="//div[@id='task-grid']/div[@class='k-grid-header']//tr/th[@data-field='StatusDesc']")
	private WebElement Statusheader;

	@FindBy(xpath="//div[@id='task-grid']/div[@class='k-grid-header']//tr/th[@data-field='PriorityName']")
	private WebElement Priorityheader;

	@FindBy(xpath="//div[@id='task-grid']/div[@class='k-grid-header']//tr/th[@data-field='Title']")
	private WebElement Nameheader;

	@FindBy(xpath="//div[@id='task-grid']/div[@class='k-grid-header']//tr/th[@data-field='DueDate']")
	private WebElement Dueheader;

	@FindBy(xpath="//h3[contains(text(),'Move-in Appointments')]")
	public WebElement label_movin_appointement;
	
	@FindBy(xpath="//h4[contains(text(),'Select Date:')]")
	public WebElement label_selectdate;
	
	@FindBy(xpath="//h4[contains(text(),'Select Date:')]/following-sibling::span/span/span")
	public WebElement appointment_defaultdate;
	
	
	@FindBy(xpath="//div[@id='dashboard-issue-header']//h3[contains(text(),'Missed')]")
	private WebElement missedDMTasksHeader;
	
	@FindBy(xpath="//div[h3[contains(text(),'Missed')]]/a")
	private WebElement missedDMTasks_ViewAll;
	
	
	@FindBy(xpath="//div[@id='tasksGrid']//td[1]")
	private List<WebElement> districtDatainGrid;
	
	
	@FindBy(xpath="//div[@id='reservationCallsPanel_new']/div[@class='container-heading gradient']/a")
    private WebElement eyeImage;
	
	
	@FindBy(xpath="//div[@id='reservationGrid']/div[2]/table/tbody/tr[1]/td[1]/a")
    private WebElement District_Link;
	
	@FindBy(xpath="//div[@id='collectionCallsPanel_new']//h3[text()='Collection Calls']/preceding-sibling::a")
	public WebElement viewAlldilinquentlink;
	
	@FindBy(xpath="//div[@id='collectionsGrid']//table//tbody//tr[1]//td/a")
	public WebElement districtnumLink_inCollCalls;
	
	@FindBy(xpath="//div[@id='collectionsGrid']//table//tbody//tr[1]//td[2]")
	public WebElement minimumrequiredtoday;
	
	@FindBy(xpath="//div[@id='collectionsGrid']//table//tbody//tr[1]//td[3]")
	public WebElement callsmadetoday;
	
	
	@FindBy(xpath="//div[@id='collectionsGrid']//table//tbody//tr[1]//td[4]")
	public WebElement overallQueue;
	
	@FindBy(xpath="//div[@id='collectionsGrid']//table//tbody//tr[1]//td[5]")
	public WebElement status;
	
	
	@FindBy(xpath="//div[@id='tasksPanel_new']//h3[text()='Missed District Manager Task List']")
	public WebElement missedDistrictManagerTaskList;
	
	
	@FindBy(xpath="//div[@id='dashboard-issue-header']//h3[text()='Missed District Manager Task List']/following-sibling::a")
	public WebElement viewallissues_icon;
	
	@FindBy(xpath="//a/span[text()='Manage Property']")
	private WebElement managePropLnk;
	
	@FindBy(xpath="//ul[@id='utilitybar-wrapper']//li//span[text()='Manage Property']")
	private WebElement manageProp_link;
	
	
	//===============================================

	public RMHomepage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	public void clickmanageProp()
	{
		manageProp_link.click();
	}

	public boolean isExists_District_OpenIssues(){
		return columnHeader_District_OpenIssues.isDisplayed();
	}

	public boolean isExists_TotalIssues_OpenIssues(){
		return columnHeader_TotalIssues_OpenIssues.isDisplayed();
	}

	public boolean isExists_AgeOfOldestOpenIssue_OpenIssues(){
		return columnHeader_AgeOfOldestOpenIssue_OpenIssues.isDisplayed();
	}
	
	public void clk_EyeImage(){
		 
        eyeImage.click();
 }
	
	
	public void Clk_District_Link(){
		 
		District_Link.click();
 }
	
	
	
	
	@FindBy(xpath="//h3[contains(text(),'Reservation Calls')]")
	public WebElement label_Reservation_Calls;

	public void clk_advancedSearchLnk(){

		advanceSearchLink.click();
	}

	public boolean verifyAdvancedSearchLnk(){

		return advanceSearchLink.isDisplayed();
	}
	public void enter_Search(String value){

		searchField_Txt.sendKeys(value);
	}

	public boolean verifySearchField(){

		return searchField_Txt.isDisplayed();
	}
	public void clk_SearchBtn(){

		search_Btn.click();
	}


	public void clk_locationDropdown(){
		locationDropdown.click();
	}

	public boolean verifyLocationDropdown(){
		return locationDropdown.isDisplayed();
	}

	public boolean verifyDistrictDropdown(){
		return districtDropdown.isDisplayed();
	}

	public boolean verifySearch_Btn(){
		return search_Btn.isDisplayed();
	}
	public void clk_districtDropdown(){
		districtDropdown.click();
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
	
	public void clk_ManagePropertyLink(){

		managePropLnk.click();
	}
	

	public String getHeaderName(){
		String headerName= openIssuesModule.getText();
		return headerName;
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

	public boolean verifyviewAllDelinquentLink(){
		return viewAllDelinquentLink.isDisplayed();
	}

	public void clk_viewAllDelinquentLink(){

		viewAllDelinquentLink.click();
	}


	public boolean isTitleDisplayed(){

		return title.isDisplayed();
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

	//==========================================================
	public boolean isTaskListModuleDispalyed(){

		return TaskListModule.isDisplayed();
	}

	public String getDefaultDropDownValueFromTaskListModule(){

		return get_DefaultDropDownValueFromTaskList.getText().trim();
	}

	public void clk_TaskListDropDown(){

		clk_TaskListDropDwn.click();
	}
	
	
	public boolean label_movin_appointement(){
		return label_movin_appointement.isDisplayed();
	}
	
	
	public boolean label_selectdate(){
		return label_selectdate.isDisplayed();
	}
	
	
	public String appointemt_defaultdate(){
		return  appointment_defaultdate.getText();
		
	}
	
	public void clk_appointemt_defaultdate(){
		  appointment_defaultdate.click();
		
	}
	
	public boolean label_label_Reservation_Calls(){
		return label_Reservation_Calls.isDisplayed();
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
	
	
	

	
	
	public boolean isMissedDMTasksListDisplayed()
	{
		return missedDMTasksHeader.isDisplayed();
	}
	
	public void click_ViewAllMissedDMTasksList()
	{
		missedDMTasks_ViewAll.click();
	}
	
	
	public void click_ViewAllMissedPMTasksList()
	{
		missedDMTasks_ViewAll.click();
	}
	
	public boolean verifyMissedTaskswithinDistrict(String district)
	{
		boolean result=true;
		for(WebElement ele: districtDatainGrid)
		{
			String districtUI=ele.getText();
			System.out.println("districtUI    " + districtUI);
			if(districtUI.length()!=0)
			{
				if(districtUI.equalsIgnoreCase(district))
				{
					result=result && true;
				}
				
				else
				{
					result=false;
				}
			}
		}
		return result;
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
	
	
	
	public List<String> allPMTasks(String district,WebDriver driver)
	{
		
		
		String query="select distinct title from task where locationcode='"+district+"' and taskstatusid=1";

		List<String> queryResult=DataBase_JDBC.executeSQLQuery_List(query);
		
			
		return queryResult;
	}
	
	
	
	public boolean verifycollectionCalls_Lnk(){
		return collectionCalls_Lnk.isDisplayed();
	}
	
	
	public boolean verify_viewAlldilinquentlink(){
		return viewAlldilinquentlink.isDisplayed();
	}
	
	
	public String get_HeadersInCollectionCalls(WebDriver driver){
		List<WebElement> ListWbEle=driver.findElements(By.xpath("//div[@id='collectionsGrid']//table//thead//tr/th"));
		String actualWbEleText = "";
		for (WebElement ele : ListWbEle) {
			actualWbEleText=actualWbEleText+", "+ele.getText().trim();
			
		}
		return actualWbEleText;

	}
	
	
	public void clk_districtnumLink_inCollCalls(){

		districtnumLink_inCollCalls.click();
	}
	
	
	public String get_minimumrequiredtoday(){

		return minimumrequiredtoday.getText();
	}
	
	
	public String get_callsmadetoday(){

		return callsmadetoday.getText();
	}
	
	
	
	
	public String get_overallQueue(){

		return overallQueue.getText();
	}
	
	
	
	
	public boolean verify_Status(){

		return status.isDisplayed();
	}
	
	
	
	
	
	public boolean verify_missedDistrictManagerTaskList(){
		return missedDistrictManagerTaskList.isDisplayed();
	}
	
	
	
	
	public void clk_viewallissues_icon(){

		viewallissues_icon.click();
	}
	
	public String get_Status(WebDriver driver){
		String value="null";
	List<WebElement> lst=driver.findElements(By.xpath("//div[@id='tasksGrid']//table//tbody//tr/td[4]"));
	for(WebElement ele:lst){
		 value=ele.getText();
	}
	return value;
	
	}

}
