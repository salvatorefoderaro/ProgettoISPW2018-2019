package Test.testUpdateavailability;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Suite.SuiteClasses(value = {beforeTest.class, updateAvailabilityTest.class, afterTest.class})
public class updateAvailabilityTestSuite {
    //todo press play : esecuzione del test in ordine ascendente automatica
}