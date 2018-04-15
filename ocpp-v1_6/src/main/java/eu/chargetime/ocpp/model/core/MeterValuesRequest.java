package eu.chargetime.ocpp.model.core;

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.model.Request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
 * Sent by the Charge Point to the Central System.
 *
 * @see MeterValue
 */
@XmlRootElement
@XmlType(propOrder = {"connectorId", "transactionId", "meterValue"})
public class MeterValuesRequest implements Request {

    private int connectorId;
    private int transactionId;
    private MeterValue[] meterValue;

    @Override
    public boolean validate() {
        boolean valid = true;
        valid &= this.connectorId >= 0;
        if (valid &= this.meterValue != null) {
            for (MeterValue current: this.meterValue)
                valid &= current != null && current.validate();
        }
        return valid;
    }

    /**
     * This contains a number (&gt;0) designating a connector of the Charge Point.
     * ‘0’ (zero) is used to designate the main power meter.
     *
     * @return Connector
     */
    public int getConnectorId() {
        return connectorId;
    }

    /**
     * Required. This contains a number (&gt;0) designating a connector of the Charge Point.
     * ‘0’ (zero) is used to designate the main power meter.
     *
     * @param connectorId                   integer, connector
     * @throws PropertyConstraintException  Value is 0 or negative.
     */
    @XmlElement
    public void setConnectorId(int connectorId) throws PropertyConstraintException {
        if (connectorId < 0)
            throw new PropertyConstraintException("connectorId", connectorId);

        this.connectorId = connectorId;
    }

    /**
     * The transaction to which these meter samples are related.
     *
     * @return transaction id.
     */
    public int getTransactionId() {
        return transactionId;
    }

    /**
     * Optional. The transaction to which these meter samples are related.
     *
     * @param transactionId integer, transaction id.
     */
    @XmlElement
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Required. The sampled meter values with timestamps.
     *
     * @param meterValue Array of {@link MeterValue}.
     */
    @XmlElement
    public void setMeterValue(MeterValue[] meterValue) {
        this.meterValue = meterValue;
    }

    /**
     * The sampled meter values with timestamps.
     *
     * @return Array of {@link MeterValue}.
     */
    public MeterValue[] getMeterValue() {
        return meterValue;
    }

    @Override
    public boolean transactionRelated() {
        return true;
    }
}
