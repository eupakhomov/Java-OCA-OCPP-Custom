package eu.chargetime.ocpp.test;
/*
    ChargeTime.eu - Java-OCA-OCPP
    
    MIT License

    Copyright (C) 2016-2018 Thomas Volden <tv@chargetime.eu>

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

import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.SessionInformation;
import eu.chargetime.ocpp.model.core.*;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.UUID;
import java.util.function.BiConsumer;

public class DummyHandlers {

    private boolean riggedToFail;

    private Request receivedRequest;
    private Confirmation receivedConfirmation;

    private String currentIdentifier;
    private UUID currentSessionIndex;

    public ServerCoreEventHandler createServerCoreEventHandler() {
        return new ServerCoreEventHandler() {
            @Override
            public AuthorizeConfirmation handleAuthorizeRequest(Serializable sessionIndex, AuthorizeRequest request) {
                receivedRequest = request;
                AuthorizeConfirmation confirmation = new AuthorizeConfirmation();
                IdTagInfo tagInfo = new IdTagInfo();
                tagInfo.setStatus(AuthorizationStatus.Accepted);
                Calendar calendar = Calendar.getInstance();
                calendar.set(2018, 1, 1, 1, 1, 1);
                tagInfo.setExpiryDate(calendar);
                confirmation.setIdTagInfo(tagInfo);
                return failurePoint(confirmation);
            }

            @Override
            public BootNotificationConfirmation handleBootNotificationRequest(Serializable sessionIndex, BootNotificationRequest request) {
                receivedRequest = request;
                BootNotificationConfirmation confirmation = new BootNotificationConfirmation();
                try {
                    confirmation.setInterval(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                confirmation.setCurrentTime(Calendar.getInstance());
                confirmation.setStatus(RegistrationStatus.Accepted);
                return failurePoint(confirmation);
            }

            @Override
            public DataTransferConfirmation handleDataTransferRequest(Serializable sessionIndex, DataTransferRequest request) {
                receivedRequest = request;
                DataTransferConfirmation confirmation = new DataTransferConfirmation();
                confirmation.setStatus(DataTransferStatus.Accepted);
                return failurePoint(confirmation);
            }

            @Override
            public HeartbeatConfirmation handleHeartbeatRequest(Serializable sessionIndex, HeartbeatRequest request) {
                receivedRequest = request;
                HeartbeatConfirmation confirmation = new HeartbeatConfirmation();
                confirmation.setCurrentTime(Calendar.getInstance());
                return failurePoint(confirmation);
            }

            @Override
            public MeterValuesConfirmation handleMeterValuesRequest(Serializable sessionIndex, MeterValuesRequest request) {
                receivedRequest = request;
                return failurePoint(new MeterValuesConfirmation());
            }

            @Override
            public StartTransactionConfirmation handleStartTransactionRequest(Serializable sessionIndex, StartTransactionRequest request) {
                receivedRequest = request;
                IdTagInfo tagInfo = new IdTagInfo();
                tagInfo.setStatus(AuthorizationStatus.Accepted);

                StartTransactionConfirmation confirmation = new StartTransactionConfirmation();
                confirmation.setIdTagInfo(tagInfo);
                return failurePoint(confirmation);
            }

            @Override
            public StatusNotificationConfirmation handleStatusNotificationRequest(Serializable sessionIndex, StatusNotificationRequest request) {
                receivedRequest = request;
                StatusNotificationConfirmation confirmation = new StatusNotificationConfirmation();
                return failurePoint(confirmation);
            }

            @Override
            public StopTransactionConfirmation handleStopTransactionRequest(Serializable sessionIndex, StopTransactionRequest request) {
                receivedRequest = request;
                StopTransactionConfirmation confirmation = new StopTransactionConfirmation();
                return failurePoint(confirmation);
            }
        };
    }

    public ServerEvents generateServerEventsHandler() {
        return new ServerEvents() {
            @Override
            public void newSession(Serializable sessionIndex, SessionInformation information) {
                currentSessionIndex = (UUID) sessionIndex;
                currentIdentifier = information.getIdentifier();
            }

            @Override
            public void lostSession(Serializable identity) {
                currentSessionIndex = null;
                currentIdentifier = null;
                // clear
                receivedConfirmation = null;
                receivedRequest = null;
            }
        };
    }

    public BiConsumer<Confirmation, Throwable> generateWhenCompleteHandler() {
        return (confirmation, throwable) -> receivedConfirmation = confirmation;
    }

    private <T extends Confirmation> T failurePoint(T confirmation) {
        if (riggedToFail) {
            riggedToFail = false;
            throw new RuntimeException();
        }
        return confirmation;
    }

    public boolean wasLatestRequest(Type requestType) {
        return requestType != null && receivedRequest != null && requestType.equals(receivedRequest.getClass());
    }

    public <T extends Request> T getReceivedRequest(T requestType) {
        if (wasLatestRequest(requestType.getClass()))
            return (T) receivedRequest;
        return null;
    }

    public boolean wasLatestConfirmation(Type confirmationType) {
        return confirmationType != null && receivedConfirmation != null && confirmationType.equals(receivedConfirmation.getClass());
    }

    public <T extends Confirmation> T getReceivedConfirmation(T confirmationType) {
        if (wasLatestConfirmation(confirmationType.getClass()))
            return (T) receivedConfirmation;
        return null;
    }


    public void setRiggedToFail(boolean riggedToFail) {
        this.riggedToFail = riggedToFail;
    }

    public boolean isRiggedToFail() {
        return riggedToFail;
    }

    public String getCurrentIdentifier() {
        return currentIdentifier;
    }

    public UUID getCurrentSessionIndex() {
        return currentSessionIndex;
    }
}
