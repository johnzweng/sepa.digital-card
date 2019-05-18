package digital.sepa.nfc.model;

import static digital.sepa.nfc.util.Utils.*;

/**
 * Represents a single entry in the cards transaction log
 *
 * @author Johannes Zweng <johannes@zweng.at>
 */
public class EmvTransactionLogEntry extends AbstractTransactionLogEntry {

    private Byte cryptogramInformation;
    private byte[] applicationDefaultAction;
    private byte[] customerExclusiveData;
    // TAG "DF 3E"
    private Byte unknownByte;

    /**
     * @return the cryptogramInformation
     */
    public Byte getCryptogramInformationData() {
        return cryptogramInformation;
    }

    /**
     * @param cryptogramInformationData the cryptogramInformation to set
     */
    public void setCryptogramInformationData(byte cryptogramInformationData) {
        this.cryptogramInformation = cryptogramInformationData;
    }

    /**
     * @return the customerExclusiveData
     */
    public byte[] getCustomerExclusiveData() {
        return customerExclusiveData;
    }

    /**
     * @param customerExclusiveData the customerExclusiveData to set
     */
    public void setCustomerExclusiveData(byte[] customerExclusiveData) {
        this.customerExclusiveData = customerExclusiveData;
    }

    /**
     * @return the applicationDefaultAction
     */
    public byte[] getApplicationDefaultAction() {
        return applicationDefaultAction;
    }

    /**
     * @param applicationDefaultAction the applicationDefaultAction to set
     */
    public void setApplicationDefaultAction(byte[] applicationDefaultAction) {
        this.applicationDefaultAction = applicationDefaultAction;
    }

    /**
     * @return the unknownByte
     */
    public Byte getUnknownByte() {
        return unknownByte;
    }

    /**
     * @param unknownByte the unknownByte to set
     */
    public void setUnknownByte(byte unknownByte) {
        this.unknownByte = unknownByte;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(
                "EmvTransactionLogEntry [\n  - transactionTimestamp: ");

        sb.append(formatDateWithTime(transactionTimestamp));
        sb.append("\n  - includes time: " + _hasTime);
        sb.append("\n  - amount: ");
        sb.append(formatBalance(_amount) + "\n  - atc: " + _atc);
        sb.append("\n  - currency: " + _currency);
        sb.append("\n  - cryptogramInformationData: ");
        if (cryptogramInformation != null) {
            sb.append(byte2Hex(cryptogramInformation));
            sb.append("\n  - applicationDefaultAction: ");
            sb.append(bytesToHexNullAllowed(applicationDefaultAction));
        }
        if (customerExclusiveData != null) {
            sb.append("\n  - customerExclusiveData: ");
            sb.append(bytesToHex(customerExclusiveData));
        }
        if (unknownByte != null) {
            sb.append("\n  - unknownByte: " + byte2Hex(unknownByte));
        }
        sb.append("\n");
        return sb.toString();
    }
}
