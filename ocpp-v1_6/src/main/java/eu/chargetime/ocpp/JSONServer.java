package eu.chargetime.ocpp;
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

import eu.chargetime.ocpp.feature.profile.Profile;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.wss.WssFactoryBuilder;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.protocols.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

public class JSONServer implements IServerAPI<UUID> {

    private static final Logger logger = LoggerFactory.getLogger(JSONServer.class);

    public final Draft draftOcppOnly =
            new Draft_6455(Collections.emptyList(),
                    Collections.singletonList(new Protocol("ocpp1.6")));
    private final WebSocketListener listener;
    private final Server server;
    private final FeatureRepository featureRepository;

    /**
     * The core feature profile is required as a minimum.
     *
     * @param coreProfile implementation of the core feature profile.
     */
    public JSONServer(ServerCoreProfile coreProfile) {
        featureRepository = new FeatureRepository();
        ServerSessionFactory sessionFactory = new ServerSessionFactory(featureRepository);
        this.listener = new WebSocketListener(sessionFactory, draftOcppOnly);
        server = new Server(this.listener, featureRepository, new PromiseRepository());
        featureRepository.addFeatureProfile(coreProfile);
    }

    public void enableWSS(WssFactoryBuilder wssFactoryBuilder) {
        listener.enableWSS(wssFactoryBuilder);
    }

    public void setPingInterval(int interval) {
        // Set ping interval in seconds
        listener.setPingInterval(interval);
    }

    @Override
    public void addFeatureProfile(Profile profile) {
        featureRepository.addFeatureProfile(profile);
    }

    @Override
    public void closeSession(UUID session) {
        server.closeSession(session);
    }

    @Override
    public void open(String host, int port, ServerEvents serverEvents) {
        logger.info("Feature repository: {}", featureRepository);

        server.open(host, port, serverEvents);
    }

    @Override
    public void close() {
        server.close();
    }

    @Override
    public boolean isClosed() {
        return listener.isClosed();
    }

    @Override
    public CompletionStage<Confirmation> send(UUID session, Request request) throws OccurenceConstraintException, UnsupportedFeatureException, NotConnectedException {
        return server.send(session, request);
    }
}
