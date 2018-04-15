package eu.chargetime.ocpp.feature.profile;

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.feature.*;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.core.*;

import java.io.Serializable;
import java.util.ArrayList;
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

/**
 * The core feature profile contains the features from OCPP v. 1.5
 * <p>
 * Contains methods to create outgoing client requests.
 */
public class ClientCoreProfile implements Profile
{
    ClientCoreEventHandler eventHandler;
    ArrayList<Feature> features;

    /**
     * Set up handler for client core feature requests.

     * @param handler   call back methods for client events
     */
    public ClientCoreProfile(ClientCoreEventHandler handler) {
        features = new ArrayList<>();
        eventHandler = handler;

        features.add(new BootNotificationFeature(null));
        features.add(new AuthorizeFeature(null));
        features.add(new ChangeAvailabilityFeature(this));
        features.add(new GetConfigurationFeature(this));
        features.add(new ChangeConfigurationFeature(this));
        features.add(new ClearCacheFeature(this));
        features.add(new DataTransferFeature(this));
        features.add(new HeartbeatFeature(this));
        features.add(new MeterValuesFeature(this));
        features.add(new RemoteStartTransactionFeature(this));
        features.add(new RemoteStopTransactionFeature(this));
        features.add(new ResetFeature(this));
        features.add(new StartTransactionFeature(this));
        features.add(new StatusNotificationFeature(this));
        features.add(new StopTransactionFeature(this));
        features.add(new UnlockConnectorFeature(this));
    }

    /**
     * Create a client {@link AuthorizeRequest} with required values.
     *
     * @param idToken   required identification token.
     * @return an instance of {@link AuthorizeRequest}.
     * @throws PropertyConstraintException  cast if requires fields isn't filled out correctly.
     * @see AuthorizeRequest
     * @see AuthorizeFeature
     */
    public AuthorizeRequest createAuthorizeRequest(String idToken) throws PropertyConstraintException {
        return new AuthorizeRequest(idToken);
    }

    /**
     * Create a client {@link BootNotificationRequest} with required values.
     *
     * @param vendor    required. Vendor name.
     * @param model     required. Charge box model.
     * @return an instance of {@link BootNotificationRequest}
     * @see BootNotificationRequest
     * @see BootNotificationFeature
     */
    public BootNotificationRequest createBootNotificationRequest(String vendor, String model) {
        return new BootNotificationRequest(vendor, model);
    }

    /**
     * Create a client {@link DataTransferRequest} with required values.
     *
     * @param vendorId  required. Vendor identification.
     * @return an instance of {@link DataTransferRequest}.
     * @see DataTransferRequest
     * @see DataTransferFeature
     */
    public DataTransferRequest createDataTransferRequest(String vendorId) {
        return new DataTransferRequest(vendorId);
    }

    /**
     * Create a client {@link HeartbeatRequest}.
     *
     * @return an instance of {@link HeartbeatRequest}
     * @see HeartbeatRequest
     * @see HeartbeatFeature
     */
    public HeartbeatRequest createHeartbeatRequest() {
        return new HeartbeatRequest();
    }

    /**
     * Create a client {@link MeterValuesRequest} with one {@link SampledValue} and one {@link MeterValue}
     *
     * @param connectorId   required. Identification of connector.
     * @param timestamp     required. Time of sample.
     * @param value         required. Value of sample.
     * @return an instance of {@link MeterValuesRequest}.
     * @throws PropertyConstraintException  thrown if a required field isn't filled out correctly.
     * @see MeterValuesRequest
     * @see MeterValuesFeature
     */
    public MeterValuesRequest createMeterValuesRequest(Integer connectorId, Calendar timestamp, String value) throws PropertyConstraintException {
        SampledValue sampledValue = new SampledValue();
        sampledValue.setValue(value);
        return createMeterValuesRequest(connectorId, timestamp, sampledValue);
    }

    /**
     * Create a client {@link MeterValuesRequest} with some {@link SampledValue}s and one {@link MeterValue}.
     *
     * @param connectorId   required. Identification of connector.
     * @param timestamp     required. Time of sample.
     * @param sampledValues required. Params list of {@link SampledValue}s
     * @return an instance of {@link MeterValuesRequest}
     * @throws PropertyConstraintException  thrown if a required field isn't filled out correctly.
     * @see MeterValuesRequest
     * @see MeterValuesFeature
     */
    public MeterValuesRequest createMeterValuesRequest(Integer connectorId, Calendar timestamp, SampledValue... sampledValues) throws PropertyConstraintException {
        MeterValue meterValue = new MeterValue();
        meterValue.setTimestamp(timestamp);
        meterValue.setSampledValue(sampledValues);
        return createMeterValuesRequest(connectorId, meterValue);
    }

    /**
     * Create a client {@link MeterValuesRequest} with some {@link MeterValue}s.
     *
     * @param connectorId   required. Identification of connector.
     * @param meterValues   required. Params list of {@link MeterValue}s
     * @return an instance of {@link MeterValuesRequest}
     * @throws PropertyConstraintException  thrown if a required field isn't filled out correctly.
     * @see MeterValuesRequest
     * @see MeterValuesFeature
     */
    public MeterValuesRequest createMeterValuesRequest(Integer connectorId, MeterValue... meterValues) throws PropertyConstraintException {
        MeterValuesRequest request = new MeterValuesRequest();
        request.setConnectorId(connectorId);
        request.setMeterValue(meterValues);
        return request;
    }

    /**
     * Create a client {@link StartTransactionRequest} with required values.
     *
     * @param connectorId   required. Identification of the connector.
     * @param idTag         required. Authorization identification tag.
     * @param meterStart    required. The initial value of the meter.
     * @param timestamp     required. Time of start.
     * @return an instance of {@link StartTransactionRequest}.
     * @throws PropertyConstraintException thrown if a required field isn't filled out correctly.
     * @see StartTransactionRequest
     * @see StartTransactionFeature
     */
    public StartTransactionRequest createStartTransactionRequest(Integer connectorId, String idTag, Integer meterStart, Calendar timestamp) throws PropertyConstraintException {
        StartTransactionRequest request = new StartTransactionRequest();
        request.setConnectorId(connectorId);
        request.setIdTag(idTag);
        request.setMeterStart(meterStart);
        request.setTimestamp(timestamp);
        return request;
    }

    /**
     * Create a client {@link StatusNotificationRequest} with required values.
     *
     * @param connectorId   required. Identification of the connector.
     * @param errorCode     required. {@link ChargePointErrorCode} of the connector.
     * @param status        required. {@link ChargePointStatus} of the connector.
     * @return an instance of {@link StatusNotificationRequest}.
     * @throws PropertyConstraintException thrown if a required field isn't filled out correctly.
     * @see StatusNotificationRequest
     * @see StatusNotificationFeature
     */
    public StatusNotificationRequest createStatusNotificationRequest(Integer connectorId, ChargePointErrorCode errorCode, ChargePointStatus status) throws PropertyConstraintException {
        StatusNotificationRequest request = new StatusNotificationRequest();
        request.setConnectorId(connectorId);
        request.setErrorCode(errorCode);
        request.setStatus(status);
        return request;
    }

    /**
     * Create a client {@link StopTransactionRequest} with required values.
     *
     * @param meterStop        required. The final value of the meter.
     * @param timestamp        required. Time of stop.
     * @param transactionId    required. The identification of the transaction.
     * @return an instance of {@link StopTransactionRequest}.
     */
    public StopTransactionRequest createStopTransactionRequest(int meterStop, Calendar timestamp, int transactionId) {
        StopTransactionRequest request = new StopTransactionRequest();
        request.setMeterStop(meterStop);
        request.setTimestamp(timestamp);
        request.setTransactionId(transactionId);
        return request;
    }

    @Override
    public Feature[] getFeatureList() {
        return features.toArray(new Feature[0]);
    }

    @Override
    public Confirmation handleRequest(Serializable sessionIndex, Request request) {
        Confirmation result = null;

        if (request instanceof ChangeAvailabilityRequest) {
            result = eventHandler.handleChangeAvailabilityRequest((ChangeAvailabilityRequest) request);
        } else if (request instanceof GetConfigurationRequest) {
            result = eventHandler.handleGetConfigurationRequest((GetConfigurationRequest) request);
        } else if (request instanceof ChangeConfigurationRequest) {
            result = eventHandler.handleChangeConfigurationRequest((ChangeConfigurationRequest) request);
        } else if (request instanceof ClearCacheRequest) {
            result = eventHandler.handleClearCacheRequest((ClearCacheRequest) request);
        } else if (request instanceof DataTransferRequest) {
            result = eventHandler.handleDataTransferRequest((DataTransferRequest)request);
        } else if (request instanceof RemoteStartTransactionRequest) {
            result = eventHandler.handleRemoteStartTransactionRequest((RemoteStartTransactionRequest) request);
        } else if (request instanceof RemoteStopTransactionRequest) {
            result = eventHandler.handleRemoteStopTransactionRequest((RemoteStopTransactionRequest) request);
        } else if (request instanceof ResetRequest) {
            result = eventHandler.handleResetRequest((ResetRequest) request);
        } else if (request instanceof UnlockConnectorRequest) {
            result = eventHandler.handleUnlockConnectorRequest((UnlockConnectorRequest) request);
        }

        return result;
    }
}
