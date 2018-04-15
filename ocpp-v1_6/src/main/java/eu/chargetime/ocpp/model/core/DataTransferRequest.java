package eu.chargetime.ocpp.model.core;

import eu.chargetime.ocpp.PropertyConstraintException;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.utilities.ModelUtil;

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
 * Sent either by the Central System to the Charge Point or vice versa.
 */
@XmlRootElement
@XmlType(propOrder = {"vendorId", "messageId", "data"})
public class DataTransferRequest implements Request {

    private String vendorId;
    private String messageId;
    private String data;

    public DataTransferRequest() {}

    /**
     * Set required fields.
     *
     * @param vendorId Vendor identification, see {@link #setVendorId(String)}.
     */
    public DataTransferRequest(String vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public boolean validate() {
        return isValidVendorId(this.vendorId);
    }

    /**
     * This identifies the Vendor specific implementation.
     *
     * @return String, Vendor identification.
     */
    public String getVendorId() {
        return vendorId;
    }

    /**
     * Required. This identifies the Vendor specific implementation.
     *
     * @param vendorId String, max 255 characters, case insensitive.
     * @throws PropertyConstraintException Value exceeds 255 characters.
     */
    @XmlElement
    public void setVendorId(String vendorId) throws PropertyConstraintException {
        if (!isValidVendorId(vendorId))
            throw new PropertyConstraintException("vendorId", vendorId);

        this.vendorId = vendorId;
    }

    private boolean isValidVendorId(String vendorId) {
        return ModelUtil.validate(vendorId, 255);
    }

    /**
     * Additional identification field.
     *
     * @return Additional identification.
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Optional. Additional identification field.
     *
     * @param messageId                     String, max 50 characters, case insensitive.
     * @throws PropertyConstraintException  Value exceeds 50 characters.
     */
    @XmlElement
    public void setMessageId(String messageId) throws PropertyConstraintException {
        if (!isValidMessageId(messageId))
            throw new PropertyConstraintException("messageId", messageId);

        this.messageId = messageId;
    }

    private boolean isValidMessageId(String messageId) {
        return ModelUtil.validate(messageId, 50);
    }

    /**
     * Data without specified length or format.
     *
     * @return data.
     */
    public String getData() {
        return data;
    }

    /**
     * Optional. Data without specified length or format.
     *
     * @param data  String, data.
     */
    @XmlElement
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean transactionRelated() {
        return false;
    }
}
