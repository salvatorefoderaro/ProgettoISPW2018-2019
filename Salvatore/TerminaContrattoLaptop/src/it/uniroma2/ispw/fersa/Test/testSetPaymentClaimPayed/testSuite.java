package it.uniroma2.ispw.fersa.Test.testSetPaymentClaimPayed;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import it.uniroma2.ispw.fersa.Test.testInsertPaymentClaim.test;

@RunWith(Suite.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Suite.SuiteClasses(value = {beforeTest.class, test.class, it.uniroma2.ispw.fersa.Test.testSetPaymentClaimPayed.test.class, afterTest.class})
public class testSuite {
    //todo press play : esecuzione del test in ordine ascendente automatica
}