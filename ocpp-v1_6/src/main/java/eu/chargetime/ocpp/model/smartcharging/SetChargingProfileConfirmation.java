package eu.chargetime.ocpp.model.smartcharging;

import eu.chargetime.ocpp.model.Confirmation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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

@XmlRootElement(name = "setChargingProfileResponse")
public class SetChargingProfileConfirmation implements Confirmation {
    public SetChargingProfileConfirmation() {
    }

    /**
     * Set required values.
     *
     * @param status the {@link ChargingProfileStatus}, see {@link #setStatus(ChargingProfileStatus)}.
     */
    public SetChargingProfileConfirmation(ChargingProfileStatus status) {
        setStatus(status);
    }
    private ChargingProfileStatus status;
    /**
     * This indicates the success or failure of the change of the charging profile.
     *
     * @return the {@link ChargingProfileStatus}.
     */
    public ChargingProfileStatus getStatus() {
        return status;
    }

    /**
     * This indicates the success or failure of the change of the charging profile.
     * @return the {@link ChargingProfileStatus}.
     */
    @Deprecated
    public ChargingProfileStatus objStatus() {
        return status;
    }

    /**
     * Required. This indicates the success or failure of the change of the charging profile.
     *
     * @param status                        the {@link ChargingProfileStatus}.
     */
    @XmlElement
    public void setStatus(ChargingProfileStatus status) {
        this.status = status;
    }


    @Override
    public boolean validate() {
        return this.status != null;
    }
}
