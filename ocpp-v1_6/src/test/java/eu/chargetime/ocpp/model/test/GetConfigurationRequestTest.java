package eu.chargetime.ocpp.model.test;

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.model.core.GetConfigurationRequest;
import eu.chargetime.ocpp.utilities.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
public class GetConfigurationRequestTest extends TestUtilities {
    GetConfigurationRequest request;

    @Before
    public void setUp() throws Exception {
        request = new GetConfigurationRequest();
    }

    @Test
    public void setKey_stringLength51_throwsPropertyConstraintException() {
        // Given
        String[] aList = aList(aString(51));

        try {
            // When
            request.setKey(aList);

            Assert.fail("Expected PropertyConstraintException");
        } catch (PropertyConstraintException ex) {
            assertThat(ex.getFieldKey(), equalTo("key"));
            assertThat(ex.getFieldValue(), equalTo(aList));
        }
    }

    @Test
    public void setKey_stringLength50_keyIsSet() throws Exception {
        // Given
        String[] aList = aList(aString(50));

        // When
        request.setKey(aList);

        // Then
        assertThat(request.getKey(), equalTo(aList));
    }

    @Test
    public void setKey_listWithOneStringLength51_throwsPropertyConstraintException() {
        // Given
        String[] aList = aList(aString(50), aString(51), aString(50));

        try {
            // When
            request.setKey(aList);

            Assert.fail("Expected PropertyConstraintException");
        } catch (PropertyConstraintException ex) {
            assertThat(ex.getFieldKey(), equalTo("key"));
            assertThat(ex.getFieldValue(), equalTo(aList));
        }
    }

    @Test
    public void setKey_listWithStringLength50_keyIsSet() throws Exception {
        // Given
        String[] aList = aList(aString(50), aString(50), aString(50));

        // When
        request.setKey(aList);

        // Then
        assertThat(request.getKey(), equalTo(aList));
    }

    @Test
    public void validate_returnTrue() {
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
        assertThat(isTransactionRelated, is(false));
    }
}