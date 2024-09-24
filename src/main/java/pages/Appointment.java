package pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import base.Base;

public class Appointment extends Base
{
	By details=By.xpath("//*[@id=\"container\"]/div[2]/div/div[1]/div/div[1]/div[3]/div/div[2]");
	By Date=By.xpath("//*[@id=\"container\"]/div[2]/div/div[1]/div/div[1]/div[2]/div[1]/div[1]/span[2]");
	By Time=By.xpath("//*[@id=\"container\"]/div[2]/div/div[1]/div/div[1]/div[2]/div[1]/div[2]/span[2]");
	public static String doctorDetails; 
	public static String displayedDate;
	public static String displayedTime;
	
	public void display() throws Exception
	{
		System.out.println("\nAppointment Details:");
		doctorDetails=driver.findElement(details).getText();
		Thread.sleep(5000);
		System.out.println("Doctor Details are: "+doctorDetails);  //prints appointment details
		String displayDate=driver.findElement(Date).getText();
		DateTimeFormatter inputFormatter1 = DateTimeFormatter.ofPattern("MMM dd, yyyy");
		LocalDate date = LocalDate.parse(displayDate, inputFormatter1);
		DateTimeFormatter outputFormatter1 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		displayedDate = date.format(outputFormatter1);
	    System.out.println("Displayed Date is: "+displayedDate);   //prints displayed date in appointment details
	    displayedTime=driver.findElement(Time).getText();
		System.out.println("Displayed Time is: "+displayedTime);   //prints displayed time in appointment details
	}
}
