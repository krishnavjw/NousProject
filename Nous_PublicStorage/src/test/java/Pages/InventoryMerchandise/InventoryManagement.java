package Pages.InventoryMerchandise;

import java.util.List;

import javax.xml.crypto.Data;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import GenericMethods.DataBase_JDBC;

public class InventoryManagement 
{
	@FindBy(xpath="//div[@id='inventory-management-grid']//div[2]//table//tbody//tr//td[2]")
	List<WebElement> itemNames;
	
	@FindBy(xpath="//div[@id='inventory-management-grid']//div[2]//table//tbody//tr//td[3]")
	List<WebElement> dateofLastSale;
	
	@FindBy(xpath="//div[@id='inventory-management-grid']//div[2]//table//tbody//tr//td[4]")
	List<WebElement> current_Qty;
	
	@FindBy(xpath="//div[@id='inventory-management-grid']//div[2]//table//tbody//tr//td[5]/input")
	List<WebElement> count;
	
	@FindBy(xpath="//div[@id='inventory-management-grid']//div[2]//table//tbody//tr//td[6]")
	List<WebElement> adjValues;
	
	@FindBy(xpath="//div[@id='inventory-management-grid']//div[2]//table//tbody//tr//td[7]//span")
	List<WebElement> reasons_for_adj;
	
	@FindBy(xpath="//div[@id='inventory-management-grid']//div[2]//table//tbody//tr//td[8]//textarea")
	List<WebElement> explanation_field;
	
	@FindBy(xpath="//ul[@class='k-list k-reset ps-container ps-active-y']//li[2]")
	private WebElement reasonValue;

	@FindBy(xpath="//a[@id='submit']")
	private WebElement button_Submit;
	
	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement employeeid_input;
	
	@FindBy(xpath="//a[contains(text(),'Ok')]")
	private WebElement ok_Button;

	public InventoryManagement(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

	
	
	public void enterCount(WebDriver driver,String exp) throws InterruptedException
	{
		Actions act=new Actions(driver);
		List<WebElement> count_Values=driver.findElements(By.xpath("//div[@id='inventory-management-grid']//div[2]//table//tbody//tr//td[5]/input"));
		
		for(int i=1;i<=count_Values.size();i++)
		{
			WebElement count=driver.findElement(By.xpath("//div[@id='inventory-management-grid']//div[2]//table//tbody//tr["+i+"]//td[5]/input"));
			act.moveToElement(count).build().perform();
			//Thread.sleep(1000);
			count.clear();
			String currQty=driver.findElement(By.xpath("//div[@id='inventory-management-grid']//div[2]//table//tbody//tr["+i+"]//td[4]")).getText();
			System.out.println("Current Qty :  " + currQty);
			if(currQty.equalsIgnoreCase("0")){
				count.sendKeys("0");}
			else{
				count.sendKeys("48");
			}
			
			
			WebElement reasonforAdj=driver.findElement(By.xpath("//div[@id='inventory-management-grid']//div[2]//table//tbody//tr["+i+"]//td[7]/span[1]"));
			act.moveToElement(reasonforAdj).build().perform();
			reasonforAdj.click();
			//Thread.sleep(1000);
			act.sendKeys(Keys.DOWN).build().perform();
			
			
			WebElement explanation=driver.findElement(By.xpath("//div[@id='inventory-management-grid']//div[2]//table//tbody//tr["+i+"]//td[8]//textarea"));
			act.moveToElement(explanation).build().perform();
			explanation.sendKeys(exp);
			
			JavascriptExecutor jsx = (JavascriptExecutor)driver;
			jsx.executeScript("window.scrollBy(0,57)", "");
			Thread.sleep(50);
			
			 
			
		}
		
		
		
		
	}
	
	
	public void click_Submit(WebDriver driver){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(250,350)");
		Actions act=new Actions(driver);
		act.moveToElement(button_Submit).build().perform();
		act.click(button_Submit).build().perform();
		//button_Submit.click();
	}
	
	
	public void enter_EmployeeID(String empid){
		employeeid_input.sendKeys(empid);
				
	}
	
	public void click_OK(){
		ok_Button.click();
	}
	
	
	
	public String verify_QuantityonHand(String item)
	{
		String query=	"select ps.quantityonhand"+
						" from product p"+ 
						" join productsite ps on ps.productid=p.productid"+
						" join accountorderitem aoi on (aoi.siteid=ps.siteid and aoi.productid=ps.productid)"+
						" where ps.siteid=335"+
						" and p.producttypeid=46 and p.name='"+item+"'"+
						" group by p.productnumber, p.name,ps.quantityonhand";


		String qoh=DataBase_JDBC.executeSQLQuery(query);
		
		return qoh;

		

	}



}
