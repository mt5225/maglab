package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ProductImageServiceTest.class, ProductServiceTest.class, CustomerServiceTest.class })
public class AllTests {

}
