package pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import base.Base;

public class Appointment extends Base
{
	By details=By.xpath("//div[@class=\"c-doctor-info-card\"]/div[2]");
	By date=By.xpath("//div[@class=\"pure-u-1-2\"]/span[2]");
	By time=By.xpath("//div[@class=\"pure-u-1-2 u-t-right\"]/span[2]");
	
	public static String doctorDetails; 
	public static String displayedDate;
	public static String displayedTime;
	
	public void display() throws Exception	//prints the displayed time,slot and doctor details after selecting one slot
	{
		System.out.println("\nAppointment Details:");
		doctorDetails=driver.findElement(details).getText();
		Thread.sleep(5000);
		System.out.println("Doctor Details are:"+doctorDetails);  //prints appointment details
		String displayDate=driver.findElement(date).getText();
		DateTimeFormatter inputFormatter1 = DateTimeFormatter.ofPattern("MMM dd, yyyy");  //formats the displayed date into required format
		LocalDate date = LocalDate.parse(displayDate, inputFormatter1);
		DateTimeFormatter outputFormatter1 = DateTimeFormatter.ofPattern("MM-dd");
		displayedDate = date.format(outputFormatter1);
	    System.out.println("Displayed Date is:"+displayedDate);   //prints displayed date in appointment details
	    displayedTime=driver.findElement(time).getText();
		System.out.println("Displayed Time is:"+displayedTime);   //prints displayed time in appointment details
	}
}
