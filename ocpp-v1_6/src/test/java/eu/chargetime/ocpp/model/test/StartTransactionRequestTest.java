package eu.chargetime.ocpp.model.test;

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.model.core.StartTransactionRequest;
import eu.chargetime.ocpp.utilities.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/*
 * ChargeTime.eu - Java-OCA-OCPP
 *
 * MIT License
 *
 * Copyright (C) 2016-2018 Thomas Volden <tv@chargetime.eu>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class StartTransactionRequestTest extends TestUtilities {
    StartTransactionRequest request;

    @Before
    public void setUp() throws Exception {
        request = new StartTransactionRequest();
    }

    @Test
    public void setConnectorId_zeroInteger_throwsPropertyConstraintException() {
        // Given
        Integer zero = 0;

        try {
            // When
            request.setConnectorId(zero);

            Assert.fail("Expected PropertyConstraintException");
        } catch (PropertyConstraintException ex) {
            // Then
            assertThat(ex.getFieldKey(), equalTo("connectorId"));
            assertThat(ex.getFieldValue(), equalTo(zero));
        }
    }

    @Test
    public void setIdTag_nullValue_throwsPropertyConstraintException() {
        // Given
        String nullValue = null;

        try {
            // When
            request.setIdTag(nullValue);

            Assert.fail("Expected PropertyConstraintException");
        } catch (PropertyConstraintException ex) {
            // Then
            assertThat(ex.getFieldKey(), equalTo("idTag"));
            assertThat(ex.getFieldValue(), equalTo(nullValue));
        }
    }

    @Test
    public void setIdTag_exceeds20Chars_throwsPropertyConstraintException() {
        // Given
        String longString = aString(21);

        try {
            // When
            request.setIdTag(longString);

            Assert.fail("Expected PropertyConstraintException");
        } catch (PropertyConstraintException ex) {
            // Then
            assertThat(ex.getFieldKey(), equalTo("idTag"));
            assertThat(ex.getFieldValue(), equalTo(longString));
        }
    }

    @Test
    public void setIdTag_string20_isSet() throws Exception {
        // Given
        String validString = aString(20);

        // When
        request.setIdTag(validString);

        // Then
        assertThat(request.getIdTag(), equalTo(validString));
    }

    @Test
    public void setConnectorId_positiveInteger_connectorIdIsSet() throws Exception {
        // Given
        Integer positive = 42;

        // When
        request.setConnectorId(positive);

        // Then
        assertThat(request.getConnectorId(), equalTo(positive));
    }

    @Test
    public void setMeterStart_anInteger_meterStartIsSet() {
        // Given
        Integer meterStart = 42;

        // When
        request.setMeterStart(meterStart);

        // Then
        assertThat(request.getMeterStart(), equalTo(meterStart));
    }

    @Test
    public void setReservationId_anInteger_reservationIdIsSet() {
        // Given
        Integer anInteger = 42;

        // When
        request.setReservationId(anInteger);

        // Then
        assertThat(request.getReservationId(), equalTo(anInteger));
    }

    @Test
    public void setTimestamp_calendarNow_timestampIsSet() {
        // Given
        Calendar now = Calendar.getInstance();

        // When
        request.setTimestamp(now);

        // Then
        assertThat(request.objTimestamp(), equalTo(now));
    }

    @Test
    public void validate_returnFalse() {
        // When
        boolean isValid = request.validate();

        // Then
        assertThat(isValid, is(false));
    }

    @Test
    public void validate_requiredFieldsAreSet_returnTrue() throws Exception {
        // Given
        request.setConnectorId(42);
        request.setIdTag("xxx");
        request.setMeterStart(42);
        request.setTimestamp(Calendar.getInstance());

        // When
        boolean isValid = request.validate();

        // Then
        assertThat(isValid, is(true));
    }

    @Test
    public void isTransactionRelated_returnsFalse() {
        // When
        boolean isTransactionRelated = request.transactionRelated();

        // Then
        assertThat(isTransactionRelated, is(true));
    }
}