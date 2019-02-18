package it.uniroma2.ispw.fersa.rentingManagement.test;

import static org.junit.Assert.*;

import it.uniroma2.ispw.fersa.rentingManagement.entity.DateRange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@RunWith(value= Parameterized.class)
public class DateRangeTest {


    public DateRangeTest(int expected, LocalDate start, LocalDate end) {
        this.expected = expected;
        this.start = start;
        this.end = end;
    }
    private int expected;
    private LocalDate start;
    private LocalDate end;


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {1, LocalDate.of(2019, 1, 20), LocalDate.of(2019, 2, 20)},
                {0, LocalDate.of(2019,5,7), LocalDate.of(2019, 6, 5)},
                {12, LocalDate.of(2019, 1, 20), LocalDate.of(2020, 1,20)},
                {24, LocalDate.of(2019, 1, 20), LocalDate.of(2021, 1, 20)}
        });
    }

    @Test
    public void getNumMonths() {
        DateRange dateRange = new DateRange(start, end);
        int period = (int) dateRange.getNumMonths();

        System.out.println("Expected: " + expected +  " Real: " + period);

        assertEquals(expected, period);
    }
}