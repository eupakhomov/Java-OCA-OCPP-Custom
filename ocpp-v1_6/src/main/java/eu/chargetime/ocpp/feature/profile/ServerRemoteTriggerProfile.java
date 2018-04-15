package eu.chargetime.ocpp.feature.profile;

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.feature.Feature;
import eu.chargetime.ocpp.feature.TriggerMessageFeature;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageRequest;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageRequestType;

import java.io.Serializable;
import java.util.HashSet;


/*
 ChargeTime.eu - Java-OCA-OCPP
 Copyright (C) 2017 Emil Christopher Solli Melar <emil@iconsultable.no>

 MIT License

 Copyright (C) 2017 Emil Christopher Solli Melar

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

public class ServerRemoteTriggerProfile implements Profile {

    private HashSet<Feature> features;

    public ServerRemoteTriggerProfile() {

        features = new HashSet<>();
        features.add(new TriggerMessageFeature(this));
    }

    @Override
    public Feature[] getFeatureList() {
        return features.toArray(new Feature[0]);
    }

    @Override
    public Confirmation handleRequest(Serializable sessionIndex, Request request) {
        return null;
    }

    public TriggerMessageRequest createTriggerMessageRequest(TriggerMessageRequestType type) throws PropertyConstraintException {
        return createTriggerMessageRequest(type, null);
    }

    public TriggerMessageRequest createTriggerMessageRequest(TriggerMessageRequestType type, Integer connectorId) throws PropertyConstraintException {
        TriggerMessageRequest request = new TriggerMessageRequest(type);
        request.setConnectorId(connectorId);
        return request;
    }
}