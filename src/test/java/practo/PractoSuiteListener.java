package practo;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class PractoSuiteListener implements ISuiteListener 
{
    @Override
    public void onStart(ISuite suite) 
    {
        System.out.println("Suite started: " + suite.getName());
    }
    @Override
    public void onFinish(ISuite suite) 
    {
        System.out.println("Suite finished: " + suite.getName());
    }
}
