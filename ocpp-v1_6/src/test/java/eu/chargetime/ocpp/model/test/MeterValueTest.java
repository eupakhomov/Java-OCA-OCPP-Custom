package eu.chargetime.ocpp.model.test;

import eu.chargetime.ocpp.model.core.MeterValue;
import eu.chargetime.ocpp.model.core.SampledValue;
import eu.chargetime.ocpp.utilities.TestUtilities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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
@RunWith(MockitoJUnitRunner.class)
public class MeterValueTest extends TestUtilities{
    MeterValue meterValue;
    @Mock
    private SampledValue sampledValueMock;

    @Before
    public void setUp() throws Exception {
        meterValue = new MeterValue();
    }

    @Test
    public void setTimestamp_now_timestampIsSet() throws Exception {
        // Given
        Calendar now = Calendar.getInstance();

        // When
        meterValue.setTimestamp(now);

        // Then
        assertThat(meterValue.objTimestamp(), equalTo(now));
    }

    @Test
    public void validate_returnFalse() {
        // When
        boolean isValid = meterValue.validate();

        // Then
        assertThat(isValid, is(false));
    }

    @Test
    public void validate_sampledValueIsSet_validatesSampledValue() throws Exception {
        // Given
        meterValue.setTimestamp(Calendar.getInstance());
        meterValue.setSampledValue(aList(sampledValueMock));

        // When
        meterValue.validate();

        // Then
        verify(sampledValueMock, times(1)).validate();
    }

    @Test
    public void validate_TimestampAndSampledValueIsValid_returnTrue() throws Exception {
        // Given
        meterValue.setTimestamp(Calendar.getInstance());
        meterValue.setSampledValue(aList(sampledValueMock));
        when(sampledValueMock.validate()).thenReturn(true);

        // When
        boolean isValid = meterValue.validate();

        // Then
        assertThat(isValid, is(true));
    }
}