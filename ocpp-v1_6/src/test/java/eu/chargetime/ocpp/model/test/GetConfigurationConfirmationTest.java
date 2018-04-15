package eu.chargetime.ocpp.model.test;

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.model.core.GetConfigurationConfirmation;
import eu.chargetime.ocpp.model.core.KeyValueType;
import eu.chargetime.ocpp.utilities.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
public class GetConfigurationConfirmationTest extends TestUtilities {
    GetConfigurationConfirmation confirmation;

    @Before
    public void setUp() throws Exception {
        confirmation = new GetConfigurationConfirmation();
    }

    @Test
    public void setConfigurationKey_aKeyValueType_configurationKeyIsSet() {
        // Given
        KeyValueType[] keyValueType = aList(new KeyValueType());

        // When
        confirmation.setConfigurationKey(keyValueType);
        
        // Then
        assertThat(confirmation.getConfigurationKey(), equalTo(keyValueType));
    }

    @Test
    public void setUnknownKey_aListWithStringLength51_throwsPropertyConstraintException() {
        // Given
        String[] aList = aList(aString(51));

        try {
            // When
            confirmation.setUnknownKey(aList);

            Assert.fail("Expected PropertyConstraintException");
        } catch (PropertyConstraintException ex) {
            assertThat(ex.getFieldKey(), equalTo("unknownKey"));
            assertThat(ex.getFieldValue(), equalTo(aList));
        }
    }

    @Test
    public void setUnknownKey_aListWithStringLength50_unknownKeyIsSet() throws Exception {
        // Given
        String[] aList = aList(aString(50));

        // When
        confirmation.setUnknownKey(aList);

        // Then
        assertThat(confirmation.getUnknownKey(), equalTo(aList));
    }

    @Test
    public void setUnknownKey_aListWithAStringLength51_throwsPropertyConstraintException() {
        // Given
        String[] aList = aList(aString(50), aString(51), aString(50));

        try {
            // When
            confirmation.setUnknownKey(aList);

            Assert.fail("Expected PropertyConstraintException");
        } catch (PropertyConstraintException ex) {
            assertThat(ex.getFieldKey(), equalTo("unknownKey"));
            assertThat(ex.getFieldValue(), equalTo(aList));
        }
    }

    @Test
    public void setUnknownKey_aListWithMoreStringLength50_unknownKeyIsSet() throws Exception {
        // Given
        String[] aList = aList(aString(50), aString(50), aString(50));

        // When
        confirmation.setUnknownKey(aList);

        // Then
        assertThat(confirmation.getUnknownKey(), equalTo(aList));
    }

    @Test
    public void validate_returnTrue() {
        // When
        boolean isValid = confirmation.validate();

        // Then
        assertThat(isValid, is(true));
    }

    @Test
    public void validate_configurationKeyIsSet_configurationKeyIsValidated() {
        // Given
        KeyValueType keyValueType = mock(KeyValueType.class);
        confirmation.setConfigurationKey(aList(keyValueType));

        // When
        confirmation.validate();

        // Then
        verify(keyValueType, times(1)).validate();
    }

    @Test
    public void validate_configurationKeyIsInvalid_returnsFalse() {
        // Given
        KeyValueType keyValueType = mock(KeyValueType.class);
        when(keyValueType.validate()).thenReturn(false);
        confirmation.setConfigurationKey(aList(keyValueType));

        // When
        boolean isValid = confirmation.validate();

        // Then
        assertThat(isValid, is(false));
    }
}