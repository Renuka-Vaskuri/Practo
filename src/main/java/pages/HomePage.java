package pages;

import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import base.Base;

public class HomePage extends Base
{
	By locationBox=By.xpath("//input[@placeholder=\"Search location\"]");
	By cityOptions=By.xpath("//div[@class=\"c-omni-suggestion-group\"]/div/span/div[1]");
	List<WebElement> cities;
	By specializationOptions=By.xpath("//div[@class=\"c-omni-suggestion-group\"]/div/span/div");
	List<WebElement> specializations;
	
	public static String selectedSpecialization;
    public static String selectedCity;
	
	public void select() throws Exception
	{
		WebElement search=driver.findElement(locationBox);		//clears the value in city search box
		search.clear();
		Thread.sleep(4000);
		String citySelect = prop.getProperty("city");		//types city name given in properties file to search
		search.sendKeys(citySelect);
		Thread.sleep(4000);
		
		cities=driver.findElements(cityOptions);
		Thread.sleep(3000);
		Assert.assertTrue(cities.size()>1,"There are no cities to select.");	//checking whether there are more than one city or not
		Random random=new Random();		//selects one city randomly
		int randomIndex=random.nextInt(cities.size()-1)+1;
		WebElement city=cities.get(randomIndex);
		selectedCity=city.getText();
		city.click();
		Thread.sleep(4000);
		specializations=driver.findElements(specializationOptions);	
		Thread.sleep(3000);
		Assert.assertTrue(specializations.size()>1,"There are no specializations to select.");	//checking whether there are more than one specialization in selected city or not
		Random random1=new Random();		//selects one specialization randomly
		int randomIndex1=random1.nextInt(specializations.size()-1)+1;
		WebElement speciality=specializations.get(randomIndex1);
		selectedSpecialization=speciality.getText();
		speciality.click();
		Thread.sleep(4000);
	}
}
