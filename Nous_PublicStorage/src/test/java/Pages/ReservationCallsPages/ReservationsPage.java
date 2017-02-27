package Pages.ReservationCallsPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReservationsPage 
{
	 @FindBy(xpath="//div[@class='reservation-list container margin-top']/div[@class='container-heading gradient']/h3")
     private WebElement reservations_Title;
    
     @FindBy(xpath="//h3[text()='Reservation Calls']")
     private WebElement ReservationPageTitle;
    
     /*@FindBy(xpath="//div[@id='reservationGrid']//table/tbody//tr//td[text()='200000653']/..//td[@class='status bold']")
     private WebElement reservations_Status;*/
     
     
     @FindBy(xpath="//div[@id='locationInfoContainer']//td/h3[text()='District:']/../following-sibling::td/span[@class='locationInfoLeft']")
     private WebElement districtDropDwn;
    
     public ReservationsPage(WebDriver driver)
     {
            PageFactory.initElements(driver,this);
     }
    
     public String getReservations_Title()
     {
            return reservations_Title.getText();
     }
    
     /*public String getReservationStatus()
     {
            return reservations_Status.getText();
     }*/
    
     public boolean isReservationPageTitleDisplayed(){
           
            return ReservationPageTitle.isDisplayed();
     }
     
     
     public void selvalFromdistrictDropDwn(String input,WebDriver driver) throws Exception{
         
         districtDropDwn.click();
         Thread.sleep(2000);
         List<WebElement> list=driver.findElements((By.xpath("//ul[@id='SelectedSiteID_listbox']/li")));
         for(WebElement ele:list){
                String val=ele.getText();
                if(val.contains(input)){
                      ele.click();
                      break;
                }
               
         }
        
        
  }
 

}
