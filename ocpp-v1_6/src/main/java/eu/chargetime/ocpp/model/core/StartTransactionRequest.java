package eu.chargetime.ocpp.model.core;

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.utilities.ModelUtil;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Calendar;

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
 */
@XmlRootElement
@XmlType(propOrder = {"connectorId", "idTag", "timestamp", "meterStart", "reservationId"})
public class StartTransactionRequest implements Request {
    private Integer connectorId;
    private String idTag;
    private Integer meterStart;
    private Integer reservationId;
    private Calendar timestamp;

    @Override
    public boolean validate() {
        boolean valid = true;
        valid &= connectorId != null && connectorId > 0;
        valid &= ModelUtil.validate(idTag, 20);
        valid &= meterStart != null;
        valid &= timestamp != null;
        return valid;
    }

    /**
     * This identifies which connector of the Charge Point is used.
     *
     * @return connector.
     */
    public Integer getConnectorId() {
        return connectorId;
    }

    /**
     * Required. This identifies which connector of the Charge Point is used.
     *
     * @param connectorId integer. value &gt; 0
     * @throws PropertyConstraintException Value was 0 or negative.
     */
    @XmlElement
    public void setConnectorId(Integer connectorId) throws PropertyConstraintException {
        if (connectorId <= 0)
            throw new PropertyConstraintException("connectorId", connectorId);

        this.connectorId = connectorId;
    }

    /**
     * This contains the identifier for which a transaction has to be started.
     *
     * @return the IdToken.
     */
    public String getIdTag() {
        return idTag;
    }

    /**
     * Required. This contains the identifier for which a transaction has to be started.
     *
     * @param idTag a String with max length 20
     * @throws PropertyConstraintException  field isn't filled out correct.
     */
    @XmlElement
    public void setIdTag(String idTag) throws PropertyConstraintException {
        if (!ModelUtil.validate(idTag, 20))
            throw new PropertyConstraintException("idTag", idTag, "Exceeded limit");

        this.idTag = idTag;
    }

    /**
     * This contains the meter value in Wh for the connector at start of the transaction.
     *
     * @return Wh at start.
     */
    public Integer getMeterStart() {
        return meterStart;
    }

    /**
     * Required. This contains the meter value in Wh for the connector at start of the transaction.
     *
     * @param meterStart    integer, Wh at start.
     */
    @XmlElement
    public void setMeterStart(Integer meterStart) {
        this.meterStart = meterStart;
    }

    /**
     * This contains the id of the reservation that terminates as a result of this transaction.
     *
     * @return reservation.
     */
    public Integer getReservationId() {
        return reservationId;
    }

    /**
     * Optional. This contains the id of the reservation that terminates as a result of this transaction.
     *
     * @param reservationId integer, reservation.
     */
    @XmlElement
    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    /**
     * This contains the date and time on which the transaction is started.
     *
     * @return start time.
     */
    public Calendar getTimestamp() {
        return timestamp;
    }

    /**
     * This contains the date and time on which the transaction is started.
     *
     * @return start time.
     */
    @Deprecated
    public Calendar objTimestamp() {
        return timestamp;
    }

    /**
     * Required. This contains the date and time on which the transaction is started.
     *
     * @param timestamp Calendar, start time.
     */
    @XmlElement
    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean transactionRelated() {
        return true;
    }
}
