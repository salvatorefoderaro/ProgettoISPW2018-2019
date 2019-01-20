package it.uniroma2.ispw.fersa.test;

import static org.junit.Assert.*;

import it.uniroma2.ispw.fersa.rentingManagement.IntervalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@RunWith(value= Parameterized.class)
public class IntervalDateTest {


    public IntervalDateTest(int expected, LocalDate begin, LocalDate end) {
        this.expected = expected;
        this.begin = begin;
        this.end = end;
    }
    private int expected;
    private LocalDate begin;
    private LocalDate end;


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {1, LocalDate.now(), LocalDate.of(2019, 2, 20)},
                {0, LocalDate.of(2019,5,7), LocalDate.of(2019, 6, 5)},
                {12, LocalDate.now(), LocalDate.of(2020, 1,20)},
                {24, LocalDate.now(), LocalDate.of(2021, 1, 20)}
        });
    }

    @Test
    public void getNumMonths() {
        IntervalDate intervalDate = new IntervalDate(begin, end);
        int period = intervalDate.getNumMonths();

        System.out.println("Expected: " + expected +  " Real: " + period);

        assertEquals(expected, period);
    }
}