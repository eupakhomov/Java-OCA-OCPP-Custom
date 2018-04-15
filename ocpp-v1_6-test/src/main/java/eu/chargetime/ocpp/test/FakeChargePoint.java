package eu.chargetime.ocpp.test;

import eu.chargetime.ocpp.*;
import eu.chargetime.ocpp.feature.profile.*;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.core.*;
import eu.chargetime.ocpp.model.firmware.GetDiagnosticsConfirmation;
import eu.chargetime.ocpp.model.firmware.GetDiagnosticsRequest;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageConfirmation;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageRequest;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageStatus;
import eu.chargetime.ocpp.model.smartcharging.ChargingProfileStatus;
import eu.chargetime.ocpp.model.smartcharging.SetChargingProfileConfirmation;
import eu.chargetime.ocpp.model.smartcharging.SetChargingProfileRequest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

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
public class FakeChargePoint
{
    private IClientAPI client;
    private Confirmation receivedConfirmation;
    private Request receivedRequest;
    private final ClientCoreProfile core;
    private final ClientSmartChargingProfile smartCharging;
    private final ClientRemoteTriggerProfile remoteTrigger;
    private final ClientFirmwareManagementProfile firmware;
    private Throwable receivedException;
    private String url;

    public FakeChargePoint() throws MalformedURLException {
        this(clientType.JSON);
    }

    public enum clientType {
        JSON, SOAP
    }

    public FakeChargePoint(clientType type) throws MalformedURLException {
        core = new ClientCoreProfile(new ClientCoreEventHandler() {
            @Override
            public ChangeAvailabilityConfirmation handleChangeAvailabilityRequest(ChangeAvailabilityRequest request) {
                receivedRequest = request;
                return new ChangeAvailabilityConfirmation(AvailabilityStatus.Accepted);
            }

            @Override
            public GetConfigurationConfirmation handleGetConfigurationRequest(GetConfigurationRequest request) {
                receivedRequest = request;
                return new GetConfigurationConfirmation();
            }

            @Override
            public ChangeConfigurationConfirmation handleChangeConfigurationRequest(ChangeConfigurationRequest request) {
                receivedRequest = request;
                ChangeConfigurationConfirmation confirmation = new ChangeConfigurationConfirmation();
                confirmation.setStatus(ConfigurationStatus.Accepted);
                return confirmation;
            }

            @Override
            public ClearCacheConfirmation handleClearCacheRequest(ClearCacheRequest request) {
                receivedRequest = request;
                ClearCacheConfirmation confirmation = new ClearCacheConfirmation();
                confirmation.setStatus(ClearCacheStatus.Accepted);
                return confirmation;
            }

            @Override
            public DataTransferConfirmation handleDataTransferRequest(DataTransferRequest request) {
                receivedRequest = request;
                DataTransferConfirmation confirmation = new DataTransferConfirmation();
                confirmation.setStatus(DataTransferStatus.Accepted);
                return confirmation;
            }

            @Override
            public RemoteStartTransactionConfirmation handleRemoteStartTransactionRequest(RemoteStartTransactionRequest request) {
                receivedRequest = request;
                return new RemoteStartTransactionConfirmation(RemoteStartStopStatus.Accepted);
            }

            @Override
            public RemoteStopTransactionConfirmation handleRemoteStopTransactionRequest(RemoteStopTransactionRequest request) {
                receivedRequest = request;
                return new RemoteStopTransactionConfirmation(RemoteStartStopStatus.Accepted);
            }

            @Override
            public ResetConfirmation handleResetRequest(ResetRequest request) {
                receivedRequest = request;
                return new ResetConfirmation(ResetStatus.Accepted);
            }

            @Override
            public UnlockConnectorConfirmation handleUnlockConnectorRequest(UnlockConnectorRequest request) {
                receivedRequest = request;
                return new UnlockConnectorConfirmation(UnlockStatus.Unlocked);
            }
        });

        smartCharging = new ClientSmartChargingProfile(new ClientSmartChargingEventHandler() {
            @Override
            public SetChargingProfileConfirmation handleSetChargingProfileRequest(SetChargingProfileRequest request) {
                receivedRequest = request;
                return new SetChargingProfileConfirmation(ChargingProfileStatus.Accepted);
            }
        });

        remoteTrigger = new ClientRemoteTriggerProfile(new ClientRemoteTriggerHandler() {
            @Override
            public TriggerMessageConfirmation handleTriggerMessageRequest(TriggerMessageRequest request) {
                receivedRequest = request;
                return new TriggerMessageConfirmation(TriggerMessageStatus.Accepted);
            }
        });

        firmware = new ClientFirmwareManagementProfile(new ClientFirmwareManagementEventHandler() {
            @Override
            public GetDiagnosticsConfirmation handleGetDiagnosticsRequest(GetDiagnosticsRequest request) {
                receivedRequest = request;
                return new GetDiagnosticsConfirmation();
            }
        });

        switch (type) {
            case JSON:
                client = new JSONClient(core, "testdummy");
                url = "ws://127.0.0.1:8887";
                break;
            case SOAP:
                client = new SOAPClient("me", new URL("http://127.0.0.1:8889"), core);
                url = "http://127.0.0.1:8890";
                break;
        }

        client.addFeatureProfile(smartCharging);
        client.addFeatureProfile(remoteTrigger);
        client.addFeatureProfile(firmware);
    }

    public void connect() {
        try {
            client.connect(url, new ClientEvents() {
                @Override
                public void connectionOpened() {
                    
                }

                @Override
                public void connectionClosed() {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendBootNotification(String vendor, String model) throws Exception {
        Request request = core.createBootNotificationRequest(vendor, model);
        send(request);
    }

    public void sendAuthorizeRequest(String idToken) throws Exception {
        Request request = core.createAuthorizeRequest(idToken);
        send(request);
    }

    public void sendIncompleteAuthorizeRequest() throws Exception {
        Request request = new AuthorizeRequest();
        send(request);
    }

    public void sendHeartbeatRequest() throws Exception {
        Request request = core.createHeartbeatRequest();
        send(request);
    }

    public void sendMeterValuesRequest() throws Exception {
        try {
            Request request = core.createMeterValuesRequest(42, Calendar.getInstance(), "42");
            send(request);
        } catch (PropertyConstraintException ex) {
            ex.printStackTrace();
        }
    }

    public void sendStartTransactionRequest() throws Exception {
        try {
            Request request = core.createStartTransactionRequest(41, "some id", 42, Calendar.getInstance());
            send(request);
        } catch (PropertyConstraintException ex) {
            ex.printStackTrace();
        }
    }

    public void sendStopTransactionRequest() throws Exception {
        StopTransactionRequest request = core.createStopTransactionRequest(42, Calendar.getInstance(), 42);
        send(request);
    }

    public void sendDataTransferRequest(String vendorId, String messageId, String data) {
        try {
            DataTransferRequest request = core.createDataTransferRequest(vendorId);
            request.setMessageId(messageId);
            request.setData(data);

            send(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendStatusNotificationRequest() {
        try {
            StatusNotificationRequest request = core.createStatusNotificationRequest(42, ChargePointErrorCode.NoError, ChargePointStatus.Available);
            send(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void send(Request request) throws Exception {
        try {
            client.send(request).whenComplete((s, ex) -> {
                receivedConfirmation = s;
                receivedException = ex;
            });
        } catch (UnsupportedFeatureException ex) {
            ex.printStackTrace();
        }
    }

    public boolean hasReceivedBootConfirmation(String status) {
        if (receivedConfirmation instanceof BootNotificationConfirmation)
            return ((BootNotificationConfirmation) receivedConfirmation).getStatus().toString().equals(status);
        return false;
    }

    public boolean hasReceivedAuthorizeConfirmation(String status) {
        if (receivedConfirmation instanceof AuthorizeConfirmation)
            return ((AuthorizeConfirmation) receivedConfirmation).getIdTagInfo().getStatus().toString().equals(status);
        return false;
    }

    public boolean hasReceivedDataTransferConfirmation(String status) {
        if (receivedConfirmation instanceof DataTransferConfirmation)
            return ((DataTransferConfirmation) receivedConfirmation).getStatus().toString().equals(status);
        return false;
    }

    public boolean hasReceivedHeartbeatConfirmation() {
        return (receivedConfirmation instanceof HeartbeatConfirmation);
    }

    public boolean hasReceivedMeterValuesConfirmation() {
        return (receivedConfirmation instanceof MeterValuesConfirmation);
    }

    public boolean hasReceivedStartTransactionConfirmation() {
        return (receivedConfirmation instanceof StartTransactionConfirmation);
    }

    public boolean hasReceivedStatusNotificationConfirmation() {
        return (receivedConfirmation instanceof StatusNotificationConfirmation);
    }

    public boolean hasReceivedStopTransactionConfirmation() {
        return (receivedConfirmation instanceof StopTransactionConfirmation);
    }

    public void disconnect() {
        client.disconnect();
    }

    public boolean hasHandledGetDiagnosticsRequest() {
        return receivedRequest instanceof GetDiagnosticsRequest;
    }

    public boolean hasHandledChangeAvailabilityRequest() {
        return receivedRequest instanceof ChangeAvailabilityRequest;
    }

    public boolean hasHandledGetConfigurationRequest() {
        return receivedRequest instanceof GetConfigurationRequest;
    }

    public boolean hasHandledChangeConfigurationRequest() {
        return receivedRequest instanceof ChangeConfigurationRequest;
    }

    public boolean hasHandledClearCacheRequest() {
        return receivedRequest instanceof ClearCacheRequest;
    }

    public boolean hasHandledDataTransferRequest() {
        return receivedRequest instanceof DataTransferRequest;
    }

    public boolean hasHandledRemoteStartTransactionRequest() {
        return receivedRequest instanceof RemoteStartTransactionRequest;
    }

    public boolean hasHandledRemoteStopTransactionRequest() {
        return receivedRequest instanceof RemoteStopTransactionRequest;
    }

    public boolean hasHandledResetRequest() {
        return receivedRequest instanceof ResetRequest;
    }

    public boolean hasHandledUnlockConnectorRequest() {
        return receivedRequest instanceof UnlockConnectorRequest;
    }

    public boolean hasReceivedError() {
        return receivedException != null;
    }
}
