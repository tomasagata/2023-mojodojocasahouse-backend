package org.mojodojocasahouse.extra.tests.model;

import java.math.BigDecimal;
import java.sql.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mojodojocasahouse.extra.model.ExtraExpense;
import org.mojodojocasahouse.extra.model.ExtraUser;

public class ExtraExpenseTest {
    @Test
    void testGettingIdOfMadridTripExpenseReturnsNull() {
        ExtraUser unmanagedUser = new ExtraUser(
                "Michael",
                "Jordan",
                "michaelj@gmail.com",
                "somepassword"
        );
        ExtraExpense unmanagedExpense = new ExtraExpense(
                unmanagedUser,
                "Madrid trip",
                new BigDecimal(100),
                Date.valueOf("2018-12-9"),
                "test",
                (short) 1
        );

        Assertions.assertThat(unmanagedExpense.getId()).isNull();
    }
    
    @Test
    void testGettingUserOfMadridTripExpenseReturnsMichaelJordan() {
        ExtraUser unmanagedUser = new ExtraUser(
                "Michael",
                "Jordan",
                "michaelj@gmail.com",
                "somepassword"
        );
        ExtraExpense unmanagedExpense = new ExtraExpense(
                unmanagedUser,
                "Madrid trip",
                new BigDecimal(100),
                Date.valueOf("2018-12-9"),
                "test",
                (short) 1
        );

        Assertions.assertThat(unmanagedExpense.getUserId()).isEqualTo(unmanagedUser);
    }
    @Test
    void testGettingConceptOfMadridTripExpenseReturnsMadridTrip() {
        ExtraUser unmanagedUser = new ExtraUser(
                "Michael",
                "Jordan",
                "michaelj@gmail.com",
                "somepassword"
        );
        ExtraExpense unmanagedExpense = new ExtraExpense(
                unmanagedUser,
                "Madrid trip",
                new BigDecimal(100),
                Date.valueOf("2018-12-9"),
                "test",
                (short) 1
        );

        Assertions.assertThat(unmanagedExpense.getConcept()).isEqualTo("Madrid trip");
    }
    @Test
    void testGettingAmountOfMadridTripReturnsOneHundred() {
        ExtraUser unmanagedUser = new ExtraUser(
                "Michael",
                "Jordan",
                "michaelj@gmail.com",
                "somepassword"
        );
        ExtraExpense unmanagedExpense = new ExtraExpense(
                unmanagedUser,
                "Madrid trip",
                new BigDecimal(100),
                Date.valueOf("2018-12-9"),
                "test",
                (short) 1
        );

        Assertions.assertThat(unmanagedExpense.getAmount()).isEqualTo(new BigDecimal(100));
    }
    @Test
    void testGettingDateOfMadridTripExpenseReturnsNinthOfDecemberTwentyEighteen() {
        ExtraUser unmanagedUser = new ExtraUser(
                "Michael",
                "Jordan",
                "michaelj@gmail.com",
                "somepassword"
        );
        ExtraExpense unmanagedExpense = new ExtraExpense(
                unmanagedUser,
                "Madrid trip",
                new BigDecimal(100),
                Date.valueOf("2018-12-9"),
                "test",
                (short) 1
        );

        Assertions.assertThat(unmanagedExpense.getDate()).isEqualTo(Date.valueOf("2018-12-9"));
    }
    @Test
    void testGettingIconofMadridTripExpenseReturnsShortOne() {
        ExtraUser unmanagedUser = new ExtraUser(
                "Michael",
                "Jordan",
                "michaelj@gmail.com",
                "somepassword"
        );
        ExtraExpense unmanagedExpense = new ExtraExpense(
                unmanagedUser,
                "Madrid trip",
                new BigDecimal(100),
                Date.valueOf("2018-12-9"),
                "test",
                (short) 1
        );

        Assertions.assertThat(unmanagedExpense.getIconId()).isEqualTo((short) 1);
    }

}
