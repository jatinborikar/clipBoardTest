package support.EventListener;

import org.apache.log4j.Logger;
import org.testng.*;


public class TestStatusListener extends TestListenerAdapter {

    private static final Logger logger = Logger.getLogger(TestStatusListener.class);
    private static final TestStatusListener testStatusListener;
    private ITestNGMethod iInvokedMethod;

    public ITestNGMethod getInvokedMethod() {
        return iInvokedMethod;
    }

    public void setInvokedMethod(ITestNGMethod iInvokedMethod) {
        this.iInvokedMethod = iInvokedMethod;
    }

    static {
        testStatusListener = new TestStatusListener();
    }

    public static TestStatusListener getTestStatusListener(){
        return testStatusListener;
    }

    @Override
    public void onStart(ITestContext testContext) {
        super.onStart(testContext);
        logger.info(String.format("TEST SUITE STARTED |{%s}|",testContext.getSuite().getName()));
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
        logger.info(String.format("TEST SUITE FINISHED |{%s}|",testContext.getSuite().getName()));
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        setInvokedMethod(tr.getMethod());
        if (tr.getName() != null) {
            logger.info(String.format("TEST_END_SUCCESSFULLY |{%s},{%s}|",returnMethodName(),
                    tr.getEndMillis() - tr.getStartMillis()));
        }
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        setInvokedMethod(tr.getMethod());
        if (tr.getThrowable() != null)
            logger.error(String.format("Test completed with exception |{%s}|",returnMethodName(),
                    tr.getEndMillis() - tr.getStartMillis()));
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        setInvokedMethod(tr.getMethod());
        logger.debug(String.format("Test skipped |{%s}|",returnMethodName()));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
        super.onTestFailedButWithinSuccessPercentage(tr);
    }

    @Override
    public void onConfigurationFailure(ITestResult itr) {
        super.onConfigurationFailure(itr);
    }

    @Override
    public void onConfigurationSkip(ITestResult itr) {
        super.onConfigurationSkip(itr);
    }

    @Override
    public void onConfigurationSuccess(ITestResult itr) {
        super.onConfigurationSuccess(itr);
    }

    @Override
    public void onTestStart(ITestResult result) {
        super.onTestStart(result);
        setInvokedMethod(result.getMethod());
        logger.info(String.format("Test started |{%s}|",returnMethodName()));
    }

    public String returnMethodName() {
        ITestNGMethod method = getInvokedMethod();
        return method.getRealClass().getSimpleName() + "." + method.getMethodName();
    }
}

