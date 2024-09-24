package base;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
public class Base 
{
	public static WebDriver driver;
	public static ExtentSparkReporter htmlreport;
	public static ExtentReports report;
	public static ExtentTest test;
	public static Properties prop;
	public static void reportSetUp()  //reporter set up
	{
		htmlreport=new ExtentSparkReporter(new File("c:\\Practo\\ExtentReports.html"));
		htmlreport.config().setReportName("Practo");
		htmlreport.config().setDocumentTitle("Practo Functional Test");
		htmlreport.config().setTheme(Theme.DARK);
		report=new ExtentReports();
		report.setSystemInfo("Environment", "Test Environment");
		report.setSystemInfo("TesterName", "Renu");
		report.attachReporter(htmlreport);
	}
	
	@BeforeMethod
	@Parameters({"x"})
	public static void driverSetUp(String br) throws Exception //driver set up
	{
		prop=new Properties();
		FileInputStream input = new FileInputStream("src/main/java/config/Practoconfig.properties");
		prop.load(input);
		if(br.matches("edge"))
		{
			 driver=new EdgeDriver();
		}
		if(br.matches("chrome"))
		{
			driver=new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}
	public static void openUrl()
	{
		 String Url = prop.getProperty("url");
		 driver.get(Url);
	}
	public static void close()  //close browser
	{
		driver.close();
	}
	public static void saveReport()  //save the report
	{
		report.flush();
	}
}
