package eu.chargetime.ocpp.model.test;

import eu.chargetime.ocpp.model.core.MeterValue;
import eu.chargetime.ocpp.model.core.Reason;
import eu.chargetime.ocpp.model.core.StopTransactionRequest;
import eu.chargetime.ocpp.utilities.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

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
public class StopTransactionRequestTest extends TestUtilities {
    StopTransactionRequest request;

    @Before
    public void setUp() throws Exception {
        request = new StopTransactionRequest();
    }

    @Test
    public void setIdTag_anIdToken_idTagIsSet() throws Exception {
        // Given
        String idTag = "xxx";

        // When
        request.setIdTag(idTag);

        // Then
        assertThat(request.getIdTag(), equalTo(idTag));
    }

    @Test
    public void setMeterStop_anInteger_meterStopIsSet() {
        // Given
        Integer anInteger = 42;

        // When
        request.setMeterStop(anInteger);

        // Then
        assertThat(request.getMeterStop(), equalTo(anInteger));
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
    public void setTransactionId_anInteger_transactionIdIsSet() {
        // Given
        Integer anInteger = 42;

        // When
        request.setTransactionId(anInteger);

        // Then
        assertThat(request.getTransactionId(), equalTo(anInteger));
    }

    @Test
    public void setReason_reason_throwsPropertyConstraintException() throws Exception {
        // Given
        Reason reason = Reason.DeAuthorized;

        // When
        request.setReason(reason);

        // Then
        assertThat(request.objReason(), equalTo(reason));
    }

    @Test
    public void setTransactionData_listOfMeterValues_transactionDataIsSet() {
        // Given
        MeterValue[] meterValues = aList(mock(MeterValue.class));

        // When
        request.setTransactionData(meterValues);

        // Then
        assertThat(request.getTransactionData(), equalTo(meterValues));
    }

    @Test
    public void validate_returnFalse() {
        // When
        boolean isValid = request.validate();

        // Then
        assertThat(isValid, is(false));
    }

    @Test
    public void validate_meterStopAndTimestampAndTransactionIdIsSet_returnTrue() {
        // Given
        request.setMeterStop(42);
        request.setTimestamp(Calendar.getInstance());
        request.setTransactionId(42);

        // When
        boolean isValid = request.validate();

        // Then
        assertThat(isValid, is(true));
    }

    @Test
    public void validate_transactionDataIsSet_transactionDataIsValidated() {
        // Given
        MeterValue meterValue = mock(MeterValue.class);
        request.setTransactionData(aList(meterValue));

        // When
        request.validate();

        // Then
        verify(meterValue, times(1)).validate();
    }

    @Test
    public void validate_aMeterValueIsNotValid_returnFalse() {
        // Given
        request.setMeterStop(42);
        request.setTimestamp(Calendar.getInstance());
        request.setTransactionId(42);

        MeterValue meterValue = mock(MeterValue.class);
        request.setTransactionData(aList(meterValue));

        // When
        when(meterValue.validate()).thenReturn(false);
        boolean isValid = request.validate();

        // Then
        assertThat(isValid, is(false));
    }

    @Test
    public void isTransactionRelated_returnsFalse() {
        // When
        boolean isTransactionRelated = request.transactionRelated();

        // Then
        Assert.assertThat(isTransactionRelated, is(true));
    }
}