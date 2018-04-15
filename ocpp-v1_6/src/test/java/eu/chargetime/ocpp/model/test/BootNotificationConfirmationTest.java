package eu.chargetime.ocpp.model.test;

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.model.core.BootNotificationConfirmation;
import eu.chargetime.ocpp.model.core.RegistrationStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/*
 ChargeTime.eu - Java-OCA-OCPP
 Copyright (C) 2015-2016 Thomas Volden <tv@chargetime.eu>

 MIT License

 Copyright (C) 2016-2018 Thomas Volden

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
public class BootNotificationConfirmationTest {
    BootNotificationConfirmation confirmation;

    @Before
    public void setUp() throws Exception {
        confirmation = new BootNotificationConfirmation();
    }

    @Test
    public void setCurrentTime_now_currentTimeIsSet() {
        // Given
        Calendar now = Calendar.getInstance();

        // When
        confirmation.setCurrentTime(now);

        // Then
        assertThat(confirmation.objCurrentTime(), equalTo(now));
    }

    @Test
    public void setInterval_anInteger_intervalIsSet() throws Exception {
        // Given
        int anInterval = 41;

        // When
        confirmation.setInterval(anInterval);

        // Then
        assertThat(confirmation.getInterval(), equalTo(anInterval));
    }

    @Test
    public void setInterval_aNegativeValue_throwsPropertyConstraintException() {
        // Given
        int aNegativeValue = - 42;

        // When
        try {
            confirmation.setInterval(aNegativeValue);

            Assert.fail("Expected PropertyConstraintException");
        } catch (PropertyConstraintException ex) {
            // Then
            assertThat(ex.getFieldKey(), equalTo("interval"));
            assertThat(ex.getFieldValue(), equalTo(aNegativeValue));
        }
    }

    @Test
    public void setStatus_enumValue_statusIsAccepted() throws Exception {
        // Given
        RegistrationStatus status = RegistrationStatus.Accepted;

        // When
        confirmation.setStatus(status);

        // Then
        assertThat(confirmation.getStatus(), equalTo(status));
    }

    @Test
    public void validate_currentTimeAndIntervalAndStatusIsSet_returnTrue() throws Exception {
        // Given
        confirmation.setCurrentTime(Calendar.getInstance());
        confirmation.setInterval(42);
        confirmation.setStatus(RegistrationStatus.Accepted);

        // When
        boolean isValid = confirmation.validate();

        // Then
        assertThat(isValid, is(true));
    }

    @Test
    public void validate_returnFalse() {
        // When
        boolean isValid = confirmation.validate();

        // Then
        assertThat(isValid, is(false));
    }
}