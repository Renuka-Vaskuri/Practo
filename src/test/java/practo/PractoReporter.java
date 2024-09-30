package practo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

public class PractoReporter implements IReporter 
{
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) 
    {
        try (StringWriter writer = new StringWriter();
             PrintWriter printWriter = new PrintWriter(writer)) 
        {
            printWriter.println("Custom TestNG Practo Report");
            printWriter.println("===========================");
            for (ISuite suite : suites) 
            {
                printWriter.println("Suite: " + suite.getName());
                for (ISuiteResult suiteResult : suite.getResults().values()) 
                {
                    ITestContext context = suiteResult.getTestContext();
                    printWriter.println("Test: " + context.getName());
                    printWriter.println("Total tests: " + context.getAllTestMethods().length);
                    printWriter.println("Passed tests: " + context.getPassedTests().size());
                    printWriter.println("Failed tests: " + context.getFailedTests().size());
                    printWriter.println("Skipped tests: " + context.getSkippedTests().size());
                    for (ITestResult result : context.getFailedTests().getAllResults()) 
                    {
                        printWriter.println("Failed Test: " + result.getMethod().getMethodName());
                        printWriter.println("Exception: " + result.getThrowable());
                    }
                }
            }
            System.out.println(writer.toString());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
