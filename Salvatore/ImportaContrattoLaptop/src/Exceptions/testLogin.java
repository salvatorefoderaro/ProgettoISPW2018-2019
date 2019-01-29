package Exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import Entity.TypeOfUser;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class testLogin {
    @Test
    @DisplayName("Test picture")
    void testLogin() throws FileNotFoundException {

        TypeOfUser b = TypeOfUser.RENTER;
        System.out.println(b.getDirectionCode());

        String test = "Resource/test1.jpeg";
    }
}
