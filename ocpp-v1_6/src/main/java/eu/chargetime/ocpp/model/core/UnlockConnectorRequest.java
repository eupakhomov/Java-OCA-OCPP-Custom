package eu.chargetime.ocpp.model.core;

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.model.Request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

/**
 * Sent by the Central System to the Charge Point.
 */
@XmlRootElement
public class UnlockConnectorRequest implements Request {
    private Integer connectorId;

    @Override
    public boolean validate() {
        return connectorId != null;
    }

    /**
     * This contains the identifier of the connector to be unlocked.
     *
     * @return connector.
     */
    public Integer getConnectorId() {
        return connectorId;
    }

    /**
     * Required. This contains the identifier of the connector to be unlocked.
     *
     * @param connectorId integer, value &gt; 0.
     * @throws PropertyConstraintException Value was zero or negative.
     */
    @XmlElement
    public void setConnectorId(Integer connectorId) throws PropertyConstraintException {
        if (connectorId <= 0)
            throw new PropertyConstraintException("connectorId", connectorId);

        this.connectorId = connectorId;
    }

    @Override
    public boolean transactionRelated() {
        return false;
    }
}
