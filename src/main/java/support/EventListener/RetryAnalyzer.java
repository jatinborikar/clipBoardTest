/*
 * For retrying the failure test runs automatically during the test run itself, 
 * we need to implement the IRetryAnalyzer interface provided by TestNG. 
 * The IRetryAnalyzer interface provide methods to control retrying the test runs. 
 * Here, we will override the retry() method of IRetryAnalyzer to make sure the test 
 * runs in case of failure with specified number of retry limit.
 */

package support.EventListener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;


public class RetryAnalyzer implements IRetryAnalyzer {
	
	//Counter to keep track of retry attempts
	int retryAttemptsCounter = 0;
	
	//The max limit to retry running of failed test cases
	//Set the value to the number of times we want to retry
	int maxRetryLimit=0;

	//Method to attempt retries for failure tests
	public boolean retry(ITestResult result) {
		if (!result.isSuccess()) {
			if(retryAttemptsCounter < maxRetryLimit){
				retryAttemptsCounter++;
				return true;
			}
		}
		return false;
	}
}