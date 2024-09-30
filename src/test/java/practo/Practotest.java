package practo;

import pages.Appointment;
import pages.DoctorsPage;
import pages.HomePage;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import base.Base;

public class PractoTest extends Base
{
	HomePage h=new HomePage();
	DoctorsPage d=new DoctorsPage();
	Appointment a=new Appointment();
	ExtentTest test; 
	public static boolean slotValidate=false;
	public static boolean homePageValidate=false;
	public static boolean feeValidate=false;
	
	@BeforeSuite
	public void report()   //reporter set up before starting suite
	{
		reportSetUp();
	}
	
	@BeforeMethod
	public void setUp() throws Exception
	{
		openUrl();   //opening url before testing each method
	}
	
	@Test(priority=1)
	public void homePage() throws Exception  //validating home page functionality
	{
		h.select();
		test=report.createTest("Validate Home Page");  //creating test in extent report
		if (d.verifyCity() && d.verifySpecialization())  //test passes if both methods are true
		{
			homePageValidate=true;
			System.out.println("Specialization and City matches");
        	test.log(Status.PASS, "Home Page Test passed");
		}
		else  //test fails otherwise
		{
			System.out.println("Specialization and City doesn't matched");
        	test.log(Status.FAIL, "Home Page Test failed");
		}
		Assert.assertTrue(homePageValidate,"Home page test failed.");	//test fails if homePageValidate is false
	}
	
	@Test(priority=2)
	public void bookingSlots() throws Exception	//validating booking slots functionality
	{
		h.select();
		d.selectAvailableTimeSlot();
		test=report.createTest("Validate Booking Slots Functionality");
		if (d.num>0)
		{
			a.display();
			Thread.sleep(2000);
			if((DoctorsPage.selectedDate).equals(Appointment.displayedDate) && (Appointment.doctorDetails).contains(DoctorsPage.selectedDoctor) && ((DoctorsPage.selectedTime).trim()).equals((Appointment.displayedTime).trim()))
			{
				slotValidate=true;
				System.out.println("\nAppointment details are same as selected.");
	        	test.log(Status.PASS, "Booking slots functionality is working properly.");
			}
			else
			{
				System.out.println("\nAppointment details are not same as selected.");
	        	test.log(Status.FAIL, "Booking slots functionality is working properly.");
			}
		}
		else
		{
			slotValidate=true;
			System.out.println("There are no doctors with click visit buttons.");
        	test.log(Status.PASS, "Booking slots functionality is working properly.");
		}
		Assert.assertTrue(slotValidate,"Slot booking test failed.");	//test fails if slotValidate is false
	}
	
	@Test(priority=3)
	public void consultationFee() throws Exception	//validating consultation fee
	{
		h.select();
		d.validateDoctorFees();
		test=report.createTest("Validate consultation Fee ");
		if(d.feeMatch==true)   //test passes if consultation fee is in the filter range 
		{
			feeValidate=true;
			System.out.println("\nConsultation fee is within the range.");
        	test.log(Status.PASS, "Consultation Fee validation success");
		}
		else
		{
			System.out.println("\nConsultation fee is not in range.");
        	test.log(Status.FAIL, "Consultation Fee validation failed");
		}
		Assert.assertTrue(feeValidate,"Consultation fee test failed.");	//test fails if feeValidate is false
	}
	
	@AfterMethod
	public void closeDriver() 	//closing the browser after every method
	{
		close();
	}
	@AfterSuite
	public void flush()	//saving the report after suite execution
	{
		saveReport();
	}
}

