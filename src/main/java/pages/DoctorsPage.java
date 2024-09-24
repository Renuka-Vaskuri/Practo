package pages;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import base.Base;

public class DoctorsPage extends Base
{
	By visitButton=By.xpath("//*[@id=\"container\"]/div/div[4]/div/div[1]/div/div[3]/div[1]/div/div[2]/div/div/div[2]/div/button");
	By doctor=By.xpath("//*[@id=\"container\"]/div/div[4]/div/div[1]/div/div[3]/div[1]/div/div[1]/div[2]/a/div/h2");
	By todayTab=By.xpath("//*[@id=\"container\"]/div/div[4]/div/div[1]/div/div[3]/div[2]/div/div/div/div[2]/div[1]/div[2]/div[1]/div[1]");
	By timeSlot=By.xpath("//*[@id=\"container\"]/div/div[4]/div/div[1]/div/div[3]/div[2]/div/div/div/div[2]/div[2]/div[3]/div[2]/div[1]/span");
	By dropdown=By.xpath("//*[@id=\"container\"]/div/div[3]/div/div/header/div[1]/div/div[4]/i");
	By doctorFee=By.xpath("//*[@id=\"container\"]/div/div[4]/div/div[1]/div/div[5]/div[1]/div/div[1]/div[2]/div[2]/div[3]");
	By feeOption0_500 = By.xpath("//span[contains(text(),'₹0-₹500')]");
	By feeOptionAbove500 = By.xpath("//span[contains(text(),'Above ₹500')]");
	By feeOptionAbove1000 = By.xpath("//span[contains(text(),'Above ₹1000')]");
	By feeOptionAbove2000 = By.xpath("//span[contains(text(),'Above ₹2000')]");

	public static String selectedDoctor;
	public static String selectedDate;
	public static String selectedTime;
	
	public void slot()
	{
		selectedDoctor=driver.findElement(doctor).getText();
		System.out.println("Selected Doctor is: "+selectedDoctor);   //prints the selected doctor's name
		driver.findElement(visitButton).click();   //clicking on Book clinic visit button
		driver.findElement(todayTab).click();   //clicking on tomorrow tab
		LocalDate today=LocalDate.now();
		selectedDate=today.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));    //formatting the date
		System.out.println("Selected Date is: "+selectedDate);   //prints selected date which is formatted
		WebElement todayTimeSlot = driver.findElement(timeSlot);   //selecting time slot
		String selectedSlot=todayTimeSlot.getText();
		todayTimeSlot.click();
	    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("hh:mm a");   //formatting time slot which is selected
	    LocalTime time = LocalTime.parse(selectedSlot, inputFormatter);
	    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("h:mm a");
	    selectedTime = time.format(outputFormatter);
	    System.out.println("Selected Time is: "+selectedTime);   //prints selected time slot
	}
	
	public void selectFeeFilter(String feeOption)   //takes fee filter option from user and click on that button
	{
		driver.findElement(dropdown).click();
		switch (feeOption) {
        case "₹0-₹500":
            driver.findElement(feeOption0_500).click();
            break;
        case "Above ₹500":
            driver.findElement(feeOptionAbove500).click();
            break;
        case "Above ₹1000":
            driver.findElement(feeOptionAbove1000).click();
            break;
        case "Above ₹2000":
            driver.findElement(feeOptionAbove2000).click();
            break;
        default:
            System.out.println("Invalid fee option selected");
    }
    System.out.println("Selected fee option: " + feeOption); //prints selected fee option
	}
	
	public boolean validateDoctorFees(String selectedFeeOption)  //defines min and max fee for each fee range
	{
		boolean feeMatch=true;
		int minFee = 0; 
        int maxFee = Integer.MAX_VALUE;
        switch (selectedFeeOption) 
        {
            case "₹0-₹500":
                maxFee = 500;
                break;
            case "Above ₹500":
                minFee = 501;
                break;
            case "Above ₹1000":
                minFee = 1001;
                break;
            case "Above ₹2000":
                minFee = 2001;
                break;
            default:
                System.out.println("Invalid fee option for validation");
                feeMatch=false;
                break;
        }
		List<WebElement> feesElements = driver.findElements(doctorFee);  //finds all consultation fee of all doctors as a list
		for (WebElement feeElement : feesElements)
		{
			String feeText = feeElement.getText().replaceAll("[^\\d]", ""); // Remove non-digit characters from displayed consultation fee
            int doctorFees = Integer.parseInt(feeText); //converts String into integer
            if (doctorFees < minFee || doctorFees > maxFee)   //checks whether the doctors consultation fee are in range as per selected filter or not
            {
               feeMatch=false;
               break;
            }
        }
		return feeMatch;
	}
}