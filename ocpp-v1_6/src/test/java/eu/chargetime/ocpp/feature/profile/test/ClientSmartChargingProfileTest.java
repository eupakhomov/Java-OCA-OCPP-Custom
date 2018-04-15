package eu.chargetime.ocpp.feature.profile.test;


import eu.chargetime.ocpp.feature.*;
import eu.chargetime.ocpp.feature.profile.ClientSmartChargingEventHandler;
import eu.chargetime.ocpp.feature.profile.ClientSmartChargingProfile;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.smartcharging.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/*
 * ChargeTime.eu - Java-OCA-OCPP
 *
 * MIT License
 *
 * Copyright (C) 2017 Emil Christopher Solli Melar <emil@iconsultable.no>
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
public class ClientSmartChargingProfileTest {

    private static final UUID SESSION_NULL = null;
    private ClientSmartChargingProfile smartCharging;

    @Mock
    private ClientSmartChargingEventHandler handler;

    @Before
    public void setup() {
        smartCharging = new ClientSmartChargingProfile(handler);
    }

    @Test
    public void handleRequest_SetChargingProfileRequest_callsHandleSetChargingProfileRequest() {
        // Given
        SetChargingProfileRequest request = new SetChargingProfileRequest();

        // When
        smartCharging.handleRequest(SESSION_NULL, request);

        // Then
        verify(handler, times(1)).handleSetChargingProfileRequest(request);
    }

    @Test
    public void handleRequest_SetChargingProfileRequest_returnsSetChargingProfileRequest() {
        // Given
        when(handler.handleSetChargingProfileRequest(any())).thenReturn(new SetChargingProfileConfirmation());
        SetChargingProfileRequest request = new SetChargingProfileRequest();

        // When
        Confirmation conf = smartCharging.handleRequest(SESSION_NULL, request);

        // Then
        assertThat(conf, instanceOf(SetChargingProfileConfirmation.class));
    }
}
