package pages;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import base.Base;

public class DoctorsPage extends Base
{
	HomePage h=new HomePage();
	By specialityShow=By.xpath("//div[@class=\"u-d-flex\"]/div[2]/div[1]/div[1]/span");
	List<WebElement> specialitiesShow;
	By cityShow=By.xpath("/html/body/div/div/div/div[4]/div/div[1]/div[1]/div[1]");
	By visitButtons=By.xpath("//button[contains(text(), 'Book Clinic Visit')]");
	public static List<WebElement> buttons;
	By doctorName=By.xpath("//div[@class=\"u-pos-rel c-slots-header__daybar \"]/div[2]/div/ancestor::div[8]/preceding-sibling::div/div/div/div[2]/a/div/h2");
	By todayTab=By.xpath("//div[text()=\"today\"]");
	By secondTab=By.xpath("//div[@class=\"u-pos-rel c-slots-header__daybar \"]/div[2]/div[1]");
	List<WebElement> slotsList;
	By timeSlot=By.xpath("//div[@class=\"c-day-slot\"]/div/div/div/span");
	By noTimeSlotMessage=By.xpath("//div[contains(text(),\"No slots available\")]");
	
	By dropdown=By.xpath("//span[contains(text(),\"All Filters\")]");
	By doctorFee=By.xpath("//div[@class=\"u-border-general--bottom\"]/div/div/div[1]/div[2]/div[2]/div[3]/span[1]/span");
	By feeOption0_500 = By.xpath("//span[contains(text(),'₹0-₹500')]");
	By feeOptionAbove500 = By.xpath("//span[contains(text(),'Above ₹500')]");
	By feeOptionAbove1000 = By.xpath("//span[contains(text(),'Above ₹1000')]");
	By feeOptionAbove2000 = By.xpath("//span[contains(text(),'Above ₹2000')]");
	
	public static String displayedSpeciality;
	public static String displayedCity;
	public static String selectedDoctor;
    public static String selectedDate;
    public static String selectedTime;
    DateTimeFormatter inputFormatter;
    DateTimeFormatter outputFormatter;
    public int num;
    public boolean feeMatch;
	
	public boolean verifyCity() throws Exception	//checks whether displayed doctors are from selected city or not
	{
		boolean cityMatch=false;
		displayedCity=driver.findElement(cityShow).getText();
		if ((displayedCity.toLowerCase()).contains(HomePage.selectedCity.toLowerCase()))
		{
			cityMatch=true;	//return true if displayed doctors are from selected city
		}
		return cityMatch;
	}
	
	public boolean verifySpecialization() throws Exception
	{
		boolean allMatch=true;	//initializing allMatch variable as true
		Thread.sleep(7000);
		specialitiesShow=driver.findElements(specialityShow);	//finds list of elements
		for (WebElement w:specialitiesShow)	//checks selected specialization and displayed doctors specialization are same or not
		{
			displayedSpeciality=w.getText();
			if (!displayedSpeciality.isEmpty())
			{
				if(!(displayedSpeciality.trim()).equalsIgnoreCase(HomePage.selectedSpecialization.trim()))
				{
					allMatch=false;	//returns false if not matched
					break;
				}
			}
		}
		return allMatch;
	}
    
    public void selectAvailableTimeSlot() throws Exception
    {
    	buttons=driver.findElements(visitButtons);  //finds list of click visit buttons
    	num=buttons.size();
    	if (num>0) 		//continues if there are any click visit buttons
    	{ 	
    		Random random=new Random();		//clicking on one click visit button randomly
    		int randomIndex=random.nextInt(num);
    		WebElement selectedButton=buttons.get(randomIndex);
    		selectedButton.click();	
    		if (driver.findElements(todayTab).size() > 0)		//continues if there is any today tab
    		{
    			driver.findElement(todayTab).click();		//clicking on today tab
    			Thread.sleep(4000);
    			slotsList=driver.findElements(timeSlot);		//listing the time slots available in today tab
    			
    			if (!slotsList.isEmpty())   //continues if today's slots available
    			{
    				Random random1=new Random();		//clicking on any today slot randomly
    				int randomIndex1=random1.nextInt(slotsList.size());
    				WebElement selectedTimeSlot=slotsList.get(randomIndex1);
    				String time=selectedTimeSlot.getText();
    				selectedDoctor=selectedTimeSlot.findElement(doctorName).getText();
    				System.out.println("Selected Doctor is:"+selectedDoctor);		//printing the selected doctor name
    				selectedTimeSlot.click();
    				Thread.sleep(1000);
    				String hour=time.substring(0,2);		//takes the hour part of time slot
    				int hr=Integer.parseInt(hour);
    				if (hr <10)		//formats the time if hour<10
    				{
    					inputFormatter = DateTimeFormatter.ofPattern("hh:mm a");
    					LocalTime time1 = LocalTime.parse(time, inputFormatter);
    					outputFormatter = DateTimeFormatter.ofPattern("h:mm a");
    					selectedTime = time1.format(outputFormatter);
    				}
    				else
    				{
    					selectedTime=time;
    				}
    				selectedDate=LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"));		//formats today date into required format
    				System.out.println("Selected Date is :"+selectedDate);		//prints selected date
    				System.out.println("Selected Time is :"+selectedTime);		//prints selected time slot
    			}
    			else		//if today slots not available,checks for tomorrow slots
    			{
    				System.out.println("No slots available for today,checking for tomorrow's slots...");
    				driver.findElement(secondTab).click();		//clicking on tomorrow tab 
    				Thread.sleep(4000);
    				slotsList=driver.findElements(timeSlot);		//listing the slots available for tomorrow
    				
    				Random random1=new Random();		//selecting any slot for tomorrow randomly
    				int randomIndex1=random1.nextInt(slotsList.size());
    				WebElement selectedTimeSlot=slotsList.get(randomIndex1);
    				String time=selectedTimeSlot.getText();
    				selectedDoctor=selectedTimeSlot.findElement(doctorName).getText();		
    				System.out.println("Selected Doctor is:"+selectedDoctor);		//prints selected doctor name
    				selectedTimeSlot.click();
    				Thread.sleep(1000);
    				String hour=time.substring(0,2);		//takes the hour part of time slot
    				int hr=Integer.parseInt(hour);
    				if (hr <10)		//formats the time if hour<10
    				{
    					inputFormatter = DateTimeFormatter.ofPattern("hh:mm a");		
    					LocalTime time1 = LocalTime.parse(time, inputFormatter);
    					outputFormatter = DateTimeFormatter.ofPattern("h:mm a");
    					selectedTime = time1.format(outputFormatter);
    				}
    				else
    				{
    					selectedTime=time;
    				}
    				selectedDate=LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("MM-dd"));		//formats tomorrow date into required format
    				System.out.println("Selected Date is :"+selectedDate);		//prints selected date
    				System.out.println("Selected Time is :"+selectedTime);		//prints selected time slot
    			}
    		}
    		else		//runs if there is some dates instead of today tab
    		{
    			WebElement tab=driver.findElement(secondTab);		//as there will be no slots for first tab ,it finds second tab whatever the date it is
    			String tabText=tab.getText();
    			tab.click();
    			Thread.sleep(4000);
    			slotsList=driver.findElements(timeSlot);		//listing the slots available for that date
    			
    			if (!slotsList.isEmpty())		//runs if slots available
    			{
    				Random random1=new Random();		//selecting any slot for that date randomly
    				int randomIndex1=random1.nextInt(slotsList.size());
    				WebElement selectedTimeSlot=slotsList.get(randomIndex1);
    				String time=selectedTimeSlot.getText();
    				selectedDoctor=selectedTimeSlot.findElement(doctorName).getText();
    				System.out.println("Selected Doctor is:"+selectedDoctor);		//prints selected doctor name
    				selectedTimeSlot.click();
    				Thread.sleep(1000);
    				String hour=time.substring(0,2);		//takes the hour part of time slot
    				int hr=Integer.parseInt(hour);
    				if (hr <10)		//formats the time if hour<10
    				{
    					inputFormatter = DateTimeFormatter.ofPattern("hh:mm a");
    					LocalTime time1 = LocalTime.parse(time, inputFormatter);
    					outputFormatter = DateTimeFormatter.ofPattern("h:mm a");
    					selectedTime = time1.format(outputFormatter);
    				}
    				else
    				{
    					selectedTime=time;
    				}
    				inputFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM");		//formats that selected date into required format
    				LocalDate date = LocalDate.parse(tabText, inputFormatter);
    				outputFormatter = DateTimeFormatter.ofPattern("MM-dd");
    				selectedDate = date.format(outputFormatter);
    				System.out.println("Selected Date is :"+selectedDate);		//prints selected date
    				System.out.println("Selected Time is :"+selectedTime);		//prints selected time slot
    			}
    		}  
    	}
    }
    
	public boolean validateDoctorFees() throws Exception  
	{
		driver.findElement(dropdown).click();	//clicking on all filters drop-down
		feeMatch=true;
		int minFee = 0; 
        int maxFee = Integer.MAX_VALUE;
        String FeeOption=prop.getProperty("option");	//taking filter option from properties file
        switch (FeeOption) //based on filter option given in properties file, clicks on that particular button and defines min and max
        {
            case "₹0-₹500":
            	driver.findElement(feeOption0_500).click();
                maxFee = 500;
                break;
            case "Above ₹500":
            	driver.findElement(feeOptionAbove500).click();
                minFee = 500;
                break;
            case "Above ₹1000":
            	driver.findElement(feeOptionAbove1000).click();
                minFee = 1000;
                break;
            case "Above ₹2000":
            	driver.findElement(feeOptionAbove2000).click();
                minFee = 2000;
                break;
            default:
            	System.out.println("Invalid fee option selected");
                return false;
        }
        System.out.println("Selected fee option: " + FeeOption); //prints selected fee option
    	Thread.sleep(7000);
		List<WebElement> feesElements = driver.findElements(doctorFee);  //finds all consultation fee of all doctors as a list
		for (WebElement feeElement : feesElements)
		{
			String feeText = feeElement.getText().replaceAll("[^\\d]", ""); // Remove non-digit characters from displayed consultation fee
            if (feeText.length()>0) 
            {
            	int doctorFees = Integer.parseInt(feeText); //converts String into integer
            	if (doctorFees < minFee || doctorFees > maxFee)   //checks whether the doctors consultation fee are in range as per selected filter or not
            	{
            		feeMatch=false;
            		break;
            	}
            }
        }
		return feeMatch;
	}
}