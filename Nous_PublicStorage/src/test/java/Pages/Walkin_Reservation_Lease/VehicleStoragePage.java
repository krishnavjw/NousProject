package Pages.Walkin_Reservation_Lease;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import GenericMethods.DataBase_JDBC;

public class VehicleStoragePage {

	@FindBy(xpath="//span[text()='Car / SUV / Truck']/../../span[@class='button']")
	private WebElement car;
	
	@FindBy(xpath="//span[text()='RV or Boat']/../../span[@class='button']")
	private WebElement boat;
	
	@FindBy(xpath="//span[text()='Oversized']/../../span[@class='button']")
	private WebElement oversized;
	
	@FindBy(xpath="//span[text()='Covered']/preceding-sibling::span[@class='button']")
	private WebElement covered;
	
	@FindBy(xpath="//span[text()='Uncovered']/preceding-sibling::span[@class='button']")
	private WebElement uncovered;
	
	@FindBy(xpath="//span[text()='Enclosed']/preceding-sibling::span[@class='button']")
	private WebElement enclosed;
	
	@FindBy(xpath="//span[text()='Car / SUV / Truck']/following-sibling::span[1]")
	private WebElement Car_lenght;
	
	@FindBy(xpath="//span[text()='RV or Boat']/following-sibling::span[1]")
	private WebElement boat_lenght;
	
	@FindBy(xpath="//span[text()='Oversized']/following-sibling::span[1]")
	private WebElement oversized_lenght;
	
	
	@FindBy(xpath="//span[text()='Covered']")
	private WebElement covered_txt;
	
	
	@FindBy(xpath="//span[text()='Uncovered']")
	private WebElement uncovered_txt;
	
	
	@FindBy(xpath="//span[text()='Enclosed']")
	private WebElement enclosed_txt;
	
	@FindBy(xpath="//span[text()='Car / SUV / Truck']")
	private WebElement Car_type;
	
	@FindBy(xpath="//a[@id='lnkRentNow']")
	private WebElement Btn_Rent;
	
	@FindBy(xpath="//h4[text()='Select Size of Vehicle to be Stored']")
	private WebElement selSizeVehicleStored;
	
	@FindBy(xpath="//h4[text()='Select Space Type']")
	private WebElement selSpaceTypeSectionTitle;
	
	public void click_Car(){
		car.click();
	}
	
	public boolean isCoveredOptionDisplayed(){
		
		return covered.isDisplayed();
	}
	
	public boolean isSelSpaceTypeSectionDisplayed(){
		
		return selSpaceTypeSectionTitle.isDisplayed();
	}
	
   public boolean isUnCoveredOptionDisplayed(){
		
		return uncovered.isDisplayed();
	}
   
   public boolean isEnclosedOptionDisplayed(){
		
		return enclosed.isDisplayed();
	}
	
	public boolean isSelectSizeVehicleToStoredTitle(){
		return selSizeVehicleStored.isDisplayed();
	}
	
	public void click_vehiclestoragetab(){
		vehicleStorage_Tab.click();
	}
	

	public boolean verify_car_chkbx(){
		return car.isDisplayed();
	}
	
	public void clk_RentBtn()
	{
		Btn_Rent.click();
	}
	
	
	public boolean verify_boat_chkbx(){
		return boat.isDisplayed();
	}
	
	
	public boolean verify_ovrsz_chkbx(){
		return oversized.isDisplayed();
	}
	
	public void click_Boat(){
		boat.click();
	}
	
	public void click_Oversized(){
		oversized.click();
	}
	
	public void click_Covered(){
		covered.click();
	}
	
	public void click_Uncovered(){
		uncovered.click();
		
	}
	
	public void click_Enclosed(){
		enclosed.click();
	}
	
	@FindBy(id="search-button")
	private WebElement Btn_Search;
	
	@FindBy(xpath="//span[@class='count']")
	private WebElement Txt_NoOfSpacesCnt;
	
	@FindBy(xpath="//div[@id='choose-size-search-type']/ul/li[2]/span[2]")
	private WebElement vehicleStorage_Tab;

	@FindBy(xpath="//span[text()='Car / SUV / Truck']")
	private WebElement boat_type;
	
	@FindBy(xpath="//span[text()='Car / SUV / Truck']")
	private WebElement oversized_type;
	
	public VehicleStoragePage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public boolean vechicalesize_car(){
		
		String sqlquery="Query to get  size of the car ";
		String car_lenght=DataBase_JDBC.executeSQLQuery(sqlquery);
		if(car_lenght.equalsIgnoreCase(Car_lenght.getText())) {
			return true;
		}
		return false;
		
	}
	
	public boolean vechicalesize_boat(){
		
		String sqlquery="Query to get  size of the boat ";
		String car_lenght=DataBase_JDBC.executeSQLQuery(sqlquery);
		if(car_lenght.equalsIgnoreCase(boat_lenght.getText())) {
			return true;
		}
		return false;
		
	}
	public boolean vechicalesize_oversized(){
	
	String sqlquery="Query to get  size of the oversize vechicles ";
	String car_lenght=DataBase_JDBC.executeSQLQuery(sqlquery);
	if(car_lenght.equalsIgnoreCase(oversized_lenght.getText())) {
		return true;
	}
	return false;
	
	}
	

	public boolean isdisplayed_car(){	

		return 	car.isDisplayed();

	}
	public boolean isdisplayed_boat(){	

		return 	boat.isDisplayed();
    }
	
	public boolean isRVorBoatDisplayed(){
		
		return boat.isDisplayed();
	}
	
	public void clk_SearchBtn()
	{
		Btn_Search.click();
	}
	
	public String get_NoOfspaces()
	{
		return Txt_NoOfSpacesCnt.getText();
	}
	
	public boolean isdisplayed_vehicleStoragetab() {
		return 	vehicleStorage_Tab.isDisplayed();
	}
	
	public boolean verify_car(){
		return Car_type.isDisplayed();
	}
	
	public boolean verify_car_length(){
		return Car_lenght.isDisplayed();
	}
	
	public boolean verify_boat(){
		return boat_type.isDisplayed();
	}
	
	public boolean verify_boat_length(){
		return boat_lenght.isDisplayed();
	}
	
	public boolean verify_oversized(){
		return oversized_type.isDisplayed();
	}
	
	public boolean verify_oversized_length(){
		return oversized_lenght.isDisplayed();
	}
	
	public boolean verify_covered_chkbox(){
		return covered.isDisplayed();
	}
	
	public boolean verify_uncovered_chkbox(){
		return uncovered.isDisplayed();
	}
	
	public boolean verify_enclosed_chkbox(){
		return enclosed.isDisplayed();
	}
	
	public boolean verify_covered(){
		return covered_txt.isDisplayed();
	}
	
	public boolean verify_uncovered(){
		return uncovered_txt.isDisplayed();
	}
	
	public boolean verify_enclosed(){
		return enclosed_txt.isDisplayed();
	}
	
	
	
}
