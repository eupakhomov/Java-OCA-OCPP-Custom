package eu.chargetime.ocpp.model.test;

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.model.core.AuthorizeRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
public class AuthorizeRequestTest {

    AuthorizeRequest request;

    @Before
    public void setup() {
        request = new AuthorizeRequest();
    }

    @Test
    public void  setIdToken_stringLength20_idTokenIsSet() throws Exception {
        // Given
        String stringLength20 = "12345678901234567890";

        // When
        request.setIdTag(stringLength20);

        // Then
        assertThat(request.getIdTag(), equalTo(stringLength20));
    }

    @Test
    public void setIdToken_exceed20chars_throwsPropertyConstraintException() {
        // Given
        String illegalValue = "1234567890123456789012";

        // When
        try {
            request.setIdTag(illegalValue);

            Assert.fail("Expected exception");
        // Then
        } catch (PropertyConstraintException ex) {
            assertThat(ex.getFieldKey(), equalTo("idTag"));
            assertThat(ex.getFieldValue(), equalTo(illegalValue ));
        }

    }

    @Test
    public void validate_idTagIsNull_returnFalse() {
        // When
        boolean isValid = request.validate();

        // Then
        assertThat(isValid, is(false));
    }

    @Test
    public void validate_idTagSet_returnTrue() throws Exception{
        // Given
        request.setIdTag("42");

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