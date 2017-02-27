package Pages.InventoryMerchandise;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Inventary{

	WebDriver driver;
		
	@FindBy(xpath="//a[text()='Submit')]")
	private WebElement Submit;

	
	@FindBy(xpath="//div[@class='k-window-titlebar k-header']//span[contains(text(),'Info')]/../following-sibling::div//div[@class='double-padding']")
	private WebElement Error_msg;
	
	
	@FindBy(xpath="//div[@id='inventory-management-grid']//table//tr[1]/td[1]")
	private WebElement Productid_first;
	
	
	@FindBy(xpath="//a[contains(text(),'OK')]")
	private WebElement OK;

	
	@FindBy(xpath="(//ul[@class='k-list k-reset ps-container ps-active-y'])[1]/li[2]")
	private WebElement Inventory_Count_Adjust;
	
	
	@FindBy(xpath="(//ul[@class='k-list k-reset ps-container ps-active-y'])[2]/li[3]")
	private WebElement destroy_option;

	public Inventary(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);

	}

	
	
	public void clk_Submit(){
		Submit.click();
		
	}
	
	public void clk_Inventory_Count_Adjust(){
		Inventory_Count_Adjust.click();
		
	}
	
	public void clk_Destroy_sec_property(){
		destroy_option.click();
		
	}
	
	
	
	
	public void clk_OK(){
		OK.click();
		
	}
	
	
	
	public WebElement btnSubmit(){
		return driver.findElement(By.xpath("//a[@id='submit']"));
		
	}
	
	public boolean display_errormsg(){
		return Error_msg.isDisplayed();
		
	}
	
	public String get_Productid_first(){
		return Productid_first.getText();
		
	}
	
	
	
	public String product_coulmn(int i){
		return driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']/table//tr["+i+"]/td[1]")).getText();
		
	}
	
	
	public String productname_coulmn(int i){
		return driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']/table//tr["+i+"]/td[2]")).getText();
		
	}
	
	
	
	public String quantityonhand_coulmn(int i){
		return driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']/table//tr["+i+"]/td[4]")).getText();
		
	}
	
	
	public void clk_quantitycount_total(){
		driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']/table//tr[1]/td[6]")).click();
	}
	
	public String get_quantitycount_total(){
		return driver.findElement(By.xpath("//div[@class='k-grid-content ps-container']/table//tr[1]/td[6]")).getText();
	}
	
	public void product_count_input(String productnumber1,String inputvalue ){
		driver.findElement(By.xpath("//input[@data-productnumber='"+productnumber1+"']")).sendKeys(inputvalue);
	}
	
	public WebElement reason_dropdown(){
		return driver.findElement(By.xpath("//div[@id='inventory-management-grid']//div[2]//table//tbody//tr[1]//td[7]/span[1]"));
		
	}
	
	public WebElement reason_dropdown_sec_property(){
		return driver.findElement(By.xpath("//div[@id='inventory-management-grid']//div[2]//table//tbody//tr[2]//td[7]/span[1]"));
		
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
                  int qty=Integer.parseInt(currQty);
                  String qty1=Integer.toString(qty-1);
                  if(currQty.equalsIgnoreCase("0")){
                        count.sendKeys("0");}
                  else{
                        count.sendKeys(qty1);
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
}



