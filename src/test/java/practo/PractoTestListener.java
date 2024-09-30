package practo;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class PractoTestListener implements ITestListener 
{
    @Override
    public void onTestStart(ITestResult result) 
    {
        System.out.println("Test started: " + result.getMethod().getMethodName());
    }
    @Override
    public void onTestSuccess(ITestResult result) 
    {
        System.out.println("Test succeeded: " + result.getMethod().getMethodName());
    }
    @Override
    public void onTestFailure(ITestResult result) 
    {
        System.out.println("Test failed: " + result.getMethod().getMethodName());
        System.out.println("Failure reason: " + result.getThrowable());
    }
    @Override
    public void onTestSkipped(ITestResult result) 
    {
        System.out.println("Test skipped: " + result.getMethod().getMethodName());
    }
    @Override
    public void onStart(ITestContext context) 
    {
        System.out.println("Test context started: " + context.getName());
    }
    @Override
    public void onFinish(ITestContext context) 
    {
        System.out.println("Test context finished: " + context.getName());
    }
}
