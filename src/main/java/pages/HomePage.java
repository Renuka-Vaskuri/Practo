package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import base.Base;

public class HomePage extends Base
{
	By locationBox=By.xpath("//*[@id=\"c-omni-container\"]/div/div[1]/div[1]/input");
	By citySearch=By.xpath("//*[@id=\"c-omni-container\"]/div/div[1]/div[1]/input");
	By cityClicked=By.xpath("//*[@id=\"c-omni-container\"]/div/div[1]/div[2]/div[2]/div[3]/span/div[1]");
	By selectSpecialization=By.xpath("//*[@id=\"c-omni-container\"]/div/div[2]/div[2]/div/div[2]/span[1]/div");
	By cityShow=By.xpath("//*[@id=\"container\"]/div/div[4]/div/div[1]/div/div[1]/div[1]/h1");
	By title=By.xpath("//span[text()=\"Gynecologist/Obstetrician\"]");
	List<WebElement> specialities;
	public static String selectedSpecialization;
	public static String displayedSpeciality;
	public static String selectedCity;
	
	public void select(String typeCity) throws Exception
	{
		driver.findElement(locationBox).clear();    //clears the value in city search box
		Thread.sleep(2000);
		driver.findElement(citySearch).sendKeys(typeCity);   //typeCity parameter takes city value 
		Thread.sleep(2000);
//		search.sendKeys(Keys.ARROW_DOWN);  //selecting the third option as city
//		search.sendKeys(Keys.ARROW_DOWN);
//		search.sendKeys(Keys.ARROW_DOWN);
//		search.sendKeys(Keys.ENTER);
		WebElement city=driver.findElement(cityClicked);   //select the city
		selectedCity=city.getText();
		city.click();
		Thread.sleep(4000);
//		speciality.sendKeys(Keys.ARROW_DOWN);
//		speciality.sendKeys(Keys.ARROW_DOWN);
//		speciality.sendKeys(Keys.ENTER);
		WebElement speciality=driver.findElement(selectSpecialization);   //selecting  required specialization
		selectedSpecialization=speciality.getText();
		speciality.click();
		Thread.sleep(2000);
	}
	public boolean verifySpecialization() throws Exception
	{
		boolean allMatch=true;   //initializing allMatch variable as true
		specialities=driver.findElements(title);   //finds list of elements
		Thread.sleep(5000);
		for (WebElement w:specialities)   //checks selected specialization and displayed doctors specialization same or not
		{
			displayedSpeciality=w.getText();
			if (!displayedSpeciality.trim().equalsIgnoreCase(selectedSpecialization.trim()))   //returns false if not matched
			{
				allMatch=false;
				break;
			}
		}
		return allMatch;
	}
	public boolean verifyCity() throws Exception   //checks whether displayed doctors are from selected city or not
	{
		boolean cityMatch=false;
		String displayedCity=driver.findElement(cityShow).getText();
		if ((displayedCity.toLowerCase()).contains(selectedCity.toLowerCase()))
		{
			cityMatch=true;  //return true if displayed doctors are from selected city
		}
		return cityMatch;
	}
}
