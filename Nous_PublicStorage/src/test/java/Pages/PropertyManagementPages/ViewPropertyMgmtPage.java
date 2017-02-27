package Pages.PropertyManagementPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.thoughtworks.selenium.webdriven.commands.GetText;

import GenericMethods.DataBase_JDBC;

public class ViewPropertyMgmtPage 
{
	@FindBy(xpath="//div[@id='searchPanel']//input[@id='SpaceNumber']")
	private WebElement spaceNumber;
	
	@FindBy(xpath="//span[span[text()='Select Filter']]/span/span")
	private WebElement accessStatus_dropdown;
	
	@FindBy(xpath="(//ul[@id='AccessStatus_listbox']/li[text()='Active'])[2]")
	private WebElement accessStatus_Active;
	
	@FindBy(xpath="(//ul[@id='AccessStatus_listbox']/li[text()='Inactive'])[2]")
	private WebElement accessStatus_Inactive;
			
	@FindBy(id="AccountNumber")
	private WebElement acocuntNumber;
	
	@FindBy(id="FirstName")
	private WebElement firstName;
	
	@FindBy(id="LastName")
	private WebElement lastName;
	
	@FindBy(id="CompanyName")
	private WebElement companyName;
	
	@FindBy(id="btnSearch")
	private WebElement search_btn;
	

	@FindBy(xpath="//tr[td[text()='Customer']]/td[1]")
	private WebElement activeStatusinGrid;
	
	@FindBy(xpath="//tr[td[text()='Customer']]/td[3]")
	private WebElement customerTypeinGrid;
	
	@FindBy(xpath="//tr[td[text()='Customer']]/td[4]")
	private WebElement nameColumninGrid;
	
	@FindBy(xpath="//tr[td[text()='Customer']]/td[5]")
	private WebElement companyNameinGrid;
	
	@FindBy(xpath="//tr[td[text()='Customer']]/td[6]")
	private WebElement spaceinGrid;
	
	@FindBy(xpath="//tr[td[text()='Customer']]/td[7]")
	private WebElement gateCodeinGrid;
	
	@FindBy(xpath="//tr[td[text()='Customer']]/td[8]")
	private WebElement accessTypeinGrid;
	
	@FindBy(xpath="//tr[td[text()='Customer']]/td[9]")
	private WebElement accessZoneinGrid;
	
	@FindBy(xpath="//a[@id='btnClear']")
	private WebElement clearSearchButton;
	
	@FindBy(xpath="//tr[th[text()='Active Status']]/th[3]")
	private WebElement customerTypeHeader;
	
	@FindBy(xpath="//tr[th[text()='Active Status']]/th[4]")
	private WebElement nameHeader;
	
	@FindBy(xpath="//tr[th[text()='Active Status']]/th[5]")
	private WebElement companyNameHeader;
	
	@FindBy(xpath="//tr[th[text()='Active Status']]/th[7]")
	private WebElement gateCodeHeader;
	
	@FindBy(xpath="//tr[th[text()='Active Status']]/th[8]")
	private WebElement accessTypeHeader;
	
	
	public ViewPropertyMgmtPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	public void enterSpaceNumber(String spaceNum)
	{
		spaceNumber.sendKeys(spaceNum);
	}
	
	public void clickAccessStatus()
	{
		accessStatus_dropdown.click();
	}
	
	public void clickAccessStatus_Active()
	{
		accessStatus_Active.click();
		
	}
	
	public void clickAccessStatus_Inactive()
	{
		accessStatus_Inactive.click();
		
	}
	
	public void enterAccountNumber(String accNum)
	{
		acocuntNumber.sendKeys(accNum);
	}
	
	public void enterFirstName(String fN)
	{
		firstName.sendKeys(fN);
	}
	
	public void enterLastName(String lN)
	{
		lastName.sendKeys(lN);
	}
	
	public void enterCompanyName(String company)
	{
		companyName.sendKeys(company);
	}
	
	public void clickSearch_btn()
	{
		search_btn.click();
	}
	
	//=============Anjana====================
	@FindBy(xpath="//span[text()='Non-customers only']/preceding-sibling::span[@class='button']")
	private WebElement non_customersonly_chkbx;
	
	public void click_non_customersonly_chkbx()
	{
		non_customersonly_chkbx.click();
		
	}
	
	@FindBy(linkText="View All Access Codes")
	private WebElement viewAllAccCodes_link;
	
	public void click_viewAllAccCodes_link()
	{
		viewAllAccCodes_link.click();
		
	}
	
	@FindBy(linkText="View")
	private WebElement view_link;
	
	public void click_view_link()
	{
		view_link.click();
		
	}

	public boolean verifyActiveInactiveStatus(String space,String fname,String lname) {
		
		String query="select case when cg.disableaccess=1 then 'Inactive' else 'Active' end as ActiveStatus"+
				" from contact c with(nolock)"+
				" join contactgate cg with(nolock) on cg.contactid=c.contactid"+
				" join rentalunit ru with(nolock) on ru.rentalunitid=cg.rentalunitid"+
				" join productsite PS on ps.productsiteid=ru.productsiteid"+
				" left join gatecontrollertimezone gc with(nolock) on gc.gatecontrollertimezoneid=cg.gatecontrollertimezoneid"+
				" left join gatecontrollerkeypad Gk with(nolock) on gk.gatecontrollerkeypadid=cg.gatecontrollerkeypadid"+
				" left join gatesystem GS with(nolock) on gs.gatesystemid=gk.gatesystemid"+
				" left join customer cu on cu.contactid=c.contactid"+
				" where PS.siteid=90 and c.FirstName='"+fname+"' and c.LastName='"+lname+"'"+
				" and ru.rentalunitnumber='"+space+"'";
		
			
		String res=DataBase_JDBC.executeSQLQuery(query);
		
		System.out.println(res);
		
		if(res.equalsIgnoreCase(activeStatusinGrid.getText())){
			return true;
		}
		
		else
		{
			return false;
		}
	}
	
	
	public void clear_Search() throws InterruptedException{
		clearSearchButton.click();
		Thread.sleep(1000);
	}
	
	
	public boolean verifycustomerTypeColumn(String space,String fname,String lname){
		
		String query="select case when cu.customertypeid in (90,91,5056,92) then 'Customer' else 'Non Customer' end as CustomerType"+
				" from contact c with(nolock)"+
				" join contactgate cg with(nolock) on cg.contactid=c.contactid"+
				" join rentalunit ru with(nolock) on ru.rentalunitid=cg.rentalunitid"+
				" join productsite PS on ps.productsiteid=ru.productsiteid"+
				" left join gatecontrollertimezone gc with(nolock) on gc.gatecontrollertimezoneid=cg.gatecontrollertimezoneid"+
				" left join gatecontrollerkeypad Gk with(nolock) on gk.gatecontrollerkeypadid=cg.gatecontrollerkeypadid"+
				" left join gatesystem GS with(nolock) on gs.gatesystemid=gk.gatesystemid"+
				" left join customer cu on cu.contactid=c.contactid"+
				" where PS.siteid=90 and c.FirstName='"+fname+"' and c.LastName='"+lname+"'"+
				" and ru.rentalunitnumber='"+space+"'";
		
			
		String res=DataBase_JDBC.executeSQLQuery(query);
		
		System.out.println(res);
		
		if(res.equalsIgnoreCase(customerTypeinGrid.getText())){
			return true;
		}
		
		else
		{
			return false;
		}
		
	}
	
public boolean verifyaccessTypeColumn(String space,String fname,String lname){
		
		String query="select gc.description"+
				" from contact c with(nolock)"+
				" join contactgate cg with(nolock) on cg.contactid=c.contactid"+
				" join rentalunit ru with(nolock) on ru.rentalunitid=cg.rentalunitid"+
				" join productsite PS on ps.productsiteid=ru.productsiteid"+
				" left join gatecontrollertimezone gc with(nolock) on gc.gatecontrollertimezoneid=cg.gatecontrollertimezoneid"+
				" left join gatecontrollerkeypad Gk with(nolock) on gk.gatecontrollerkeypadid=cg.gatecontrollerkeypadid"+
				" left join gatesystem GS with(nolock) on gs.gatesystemid=gk.gatesystemid"+
				" left join customer cu on cu.contactid=c.contactid"+
				" where PS.siteid=90 and c.FirstName='"+fname+"' and c.LastName='"+lname+"'"+
				" and ru.rentalunitnumber='"+space+"'";
		
			
		String res=DataBase_JDBC.executeSQLQuery(query);
		
		System.out.println(res);
		
		if(res.equalsIgnoreCase(accessTypeinGrid.getText())){
			return true;
		}
		
		else
		{
			return false;
		}
		
	}

public boolean verifyaccessZoneColumn(String space,String fname,String lname){
	
	String query="select gk.description"+
			" from contact c with(nolock)"+
			" join contactgate cg with(nolock) on cg.contactid=c.contactid"+
			" join rentalunit ru with(nolock) on ru.rentalunitid=cg.rentalunitid"+
			" join productsite PS on ps.productsiteid=ru.productsiteid"+
			" left join gatecontrollertimezone gc with(nolock) on gc.gatecontrollertimezoneid=cg.gatecontrollertimezoneid"+
			" left join gatecontrollerkeypad Gk with(nolock) on gk.gatecontrollerkeypadid=cg.gatecontrollerkeypadid"+
			" left join gatesystem GS with(nolock) on gs.gatesystemid=gk.gatesystemid"+
			" left join customer cu on cu.contactid=c.contactid"+
			" where PS.siteid=90 and c.FirstName='"+fname+"' and c.LastName='"+lname+"'"+
			" and ru.rentalunitnumber='"+space+"'";
	
		
	String res=DataBase_JDBC.executeSQLQuery(query);
	
	System.out.println(res);
	
	if(res.equalsIgnoreCase(accessZoneinGrid.getText())){
		return true;
	}
	
	else
	{
		return false;
	}
	
}
	
	
	public boolean verifyNameColumn(String space,String fname,String lname){
		String query="select c.firstName"+
				" from contact c with(nolock)"+
				" join contactgate cg with(nolock) on cg.contactid=c.contactid"+
				" join rentalunit ru with(nolock) on ru.rentalunitid=cg.rentalunitid"+
				" join productsite PS on ps.productsiteid=ru.productsiteid"+
				" left join gatecontrollertimezone gc with(nolock) on gc.gatecontrollertimezoneid=cg.gatecontrollertimezoneid"+
				" left join gatecontrollerkeypad Gk with(nolock) on gk.gatecontrollerkeypadid=cg.gatecontrollerkeypadid"+
				" left join gatesystem GS with(nolock) on gs.gatesystemid=gk.gatesystemid"+
				" left join customer cu on cu.contactid=c.contactid"+
				" where PS.siteid=90 and c.FirstName='"+fname+"' and c.LastName='"+lname+"'"+
				" and ru.rentalunitnumber='"+space+"'";
		
			
		String res=DataBase_JDBC.executeSQLQuery(query);
		
		System.out.println(res);
		
		if((nameColumninGrid.getText().contains(res))){
			return true;
		}
		
		else
		{
			return false;
		}
	}
	
	
	public boolean verifyCompanyName(String space,String fname,String lname){
		String query="select Cu.CompanyName"+
				" from contact c with(nolock)"+
				" join contactgate cg with(nolock) on cg.contactid=c.contactid"+
				" join rentalunit ru with(nolock) on ru.rentalunitid=cg.rentalunitid"+
				" join productsite PS on ps.productsiteid=ru.productsiteid"+
				" left join gatecontrollertimezone gc with(nolock) on gc.gatecontrollertimezoneid=cg.gatecontrollertimezoneid"+
				" left join gatecontrollerkeypad Gk with(nolock) on gk.gatecontrollerkeypadid=cg.gatecontrollerkeypadid"+
				" left join gatesystem GS with(nolock) on gs.gatesystemid=gk.gatesystemid"+
				" left join customer cu on cu.contactid=c.contactid"+
				" where PS.siteid=90 and c.FirstName='"+fname+"' and c.LastName='"+lname+"'"+
				" and ru.rentalunitnumber='"+space+"'";
		
			
		String res=DataBase_JDBC.executeSQLQuery(query);
		
		System.out.println(res);
		
		if((companyNameinGrid.getText().contains(res))){
			return true;
		}
		
		else
		{
			return false;
		}
	}
	
	
	public boolean verifySpaceName(String space,String fname,String lname){
		String query="select Ru.rentalunitnumber as Space"+
				" from contact c with(nolock)"+
				" join contactgate cg with(nolock) on cg.contactid=c.contactid"+
				" join rentalunit ru with(nolock) on ru.rentalunitid=cg.rentalunitid"+
				" join productsite PS on ps.productsiteid=ru.productsiteid"+
				" left join gatecontrollertimezone gc with(nolock) on gc.gatecontrollertimezoneid=cg.gatecontrollertimezoneid"+
				" left join gatecontrollerkeypad Gk with(nolock) on gk.gatecontrollerkeypadid=cg.gatecontrollerkeypadid"+
				" left join gatesystem GS with(nolock) on gs.gatesystemid=gk.gatesystemid"+
				" left join customer cu on cu.contactid=c.contactid"+
				" where PS.siteid=90 and c.FirstName='"+fname+"' and c.LastName='"+lname+"'"+
				" and ru.rentalunitnumber='"+space+"'";
		
			
		String res=DataBase_JDBC.executeSQLQuery(query);
		
		System.out.println(res);
		
		if((spaceinGrid.getText().contains(res))){
			return true;
		}
		
		else
		{
			return false;
		}
	}
	
	
	public boolean verifyGateCodeName(String space,String fname,String lname){
		String query="select Cg.Gatecode"+
				" from contact c with(nolock)"+
				" join contactgate cg with(nolock) on cg.contactid=c.contactid"+
				" join rentalunit ru with(nolock) on ru.rentalunitid=cg.rentalunitid"+
				" join productsite PS on ps.productsiteid=ru.productsiteid"+
				" left join gatecontrollertimezone gc with(nolock) on gc.gatecontrollertimezoneid=cg.gatecontrollertimezoneid"+
				" left join gatecontrollerkeypad Gk with(nolock) on gk.gatecontrollerkeypadid=cg.gatecontrollerkeypadid"+
				" left join gatesystem GS with(nolock) on gs.gatesystemid=gk.gatesystemid"+
				" left join customer cu on cu.contactid=c.contactid"+
				" where PS.siteid=90 and c.FirstName='"+fname+"' and c.LastName='"+lname+"'"+
				" and ru.rentalunitnumber='"+space+"'";
		
			
		String res=DataBase_JDBC.executeSQLQuery(query);
		
		System.out.println(res);
		
		if((gateCodeinGrid.getText().contains(res))){
			return true;
		}
		
		else
		{
			return false;
		}
	}
	
	@FindBy(xpath="//div[@id='accessItems-grid']//tbody//tr/td[4]")
	List<WebElement> namesListinGrid;
	
	
	public boolean verifyDefaultSorting(String space,String fname,String lname){
		String str="";
		
		for(int i=0;i<=namesListinGrid.size();i++){
			if(namesListinGrid.get(i).getText().equalsIgnoreCase(str)){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
	}
	
	
	
	public boolean verifySortingbyCustomerType(){
		String str="";
		
		for(int i=0;i<=namesListinGrid.size();i++){
			if(namesListinGrid.get(i).getText().contains(str)){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
	}
	
	
	public boolean verifySortingbyName(){
		String str="";
		
		for(int i=0;i<=namesListinGrid.size();i++){
			if(namesListinGrid.get(i).getText().contains(str)){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
	}
	
	public boolean verifySortingbyCompanyName(){
		String str="";
		
		for(int i=0;i<=namesListinGrid.size();i++){
			if(namesListinGrid.get(i).getText().contains(str)){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
		
	}
	
	
	public boolean verifySortingbyGateCode(){
		String str="";
		
		for(int i=0;i<=namesListinGrid.size();i++){
			if(namesListinGrid.get(i).getText().contains(str)){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
		
	}
	
	public boolean verifySortingbyAccessType(){
		String str="";
		
		for(int i=0;i<=namesListinGrid.size();i++){
			if(namesListinGrid.get(i).getText().contains(str)){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
		
	}
	
	
	
	public void click_customerTypeHeader(){
		customerTypeHeader.click();
	}
	
	
	public void click_nameHeader(){
		nameHeader.click();
		
	}
	
	
	
	public void click_CompanyNameHeader(){
		companyNameHeader.click();
	}
	
	
	public void click_GatecodeHeader(){
		gateCodeHeader.click();
	}
	
	
	public void click_AccessTypeHeader(){
		accessTypeHeader.click();
	}
}
