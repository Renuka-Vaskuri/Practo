package practo;

import pages.Appointment;
import pages.DoctorsPage;
import pages.HomePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import base.Base;

public class Practotest extends Base
{
	HomePage h=new HomePage();
	DoctorsPage d=new DoctorsPage();
	Appointment a=new Appointment();
	ExtentTest test;
	
	@BeforeSuite
	public void report()   //reporter set up before starting suite
	{
		reportSetUp();
	}
	@BeforeMethod
	public void setUp() throws Exception
	{
		openUrl();   //opening url before starting suite
	}
	
	@Test(priority=1)
	public void homepage() throws Exception
	{
		h.select("Hyderabad");   //passing the argument to search city box
		test=report.createTest("Validate Home Page");  //creating test in extent report
		if (h.verifySpecialization() && h.verifyCity())  //test passes if both methods are true
		{
			System.out.println("Specialization and City matches");
        	test.log(Status.PASS, "Home Page Test passed");
		}
		else  //test fails otherwise
		{
			System.out.println("Specialization and City doesn't matched");
        	test.log(Status.FAIL, "Home Page Test failed");
		}
	}
	
	@Test(priority=2)
	public void appointment() throws Exception
	{
		h.select("Hyderabad");
		d.slot();
		a.display();
		
		test=report.createTest("Validate Appointment Details");  //test passes if appointment details matches
		if((DoctorsPage.selectedDate).equals(Appointment.displayedDate) && (Appointment.doctorDetails).contains(DoctorsPage.selectedDoctor) && (DoctorsPage.selectedTime).equals(Appointment.displayedTime))
		{
			System.out.println("\nAppointment details are same as selected.");
        	test.log(Status.PASS, "Appointment details matched");
		}
		else
		{
			System.out.println("\nAppointment details are not same as selected.");
        	test.log(Status.FAIL, "Appointment Details doesn't matched");
		}
		
	}
	
	@Test(priority=3)
	public void fee() throws Exception
	{
		h.select("Hyderabad");
		d.selectFeeFilter("Above ₹500");
		
		test=report.createTest("Validate Fee ");
		if(d.validateDoctorFees("Above ₹500"))   //test passes if consultation fee is in the filter range 
		{
			System.out.println("\nConsultation fee is within the range.");
        	test.log(Status.PASS, "Fee validation success");
		}
		else
		{
			System.out.println("\nConsultation fee is not in range.");
        	test.log(Status.FAIL, "Fee validation failed");
		}
	}
	@AfterMethod
	public void closeDriver()
	{
		close();
	}
	@AfterSuite
	public void flush()
	{
		saveReport();
	}
}

