/* we need to set the retryAnalyzer attribute in each of the @Test annotation. 
 * To deal with this we can implement IAnnotationTransformer interface. 
 * This interface provides the capability to alter the testNG annotation at runtime.
 * 
 */

package support.EventListener;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class FailureRetryListener implements IAnnotationTransformer {
	
	//Overriding the transform method to set the RetryAnalyzer
	public void transform(ITestAnnotation testAnnotation, Class testClass, 
			Constructor testConstructor, Method testMethod)	{
		IRetryAnalyzer retry = testAnnotation.getRetryAnalyzer();

		if (retry == null)
			testAnnotation.setRetryAnalyzer(RetryAnalyzer.class);
	}
}
