package digital.sepa.nfc.model;

import java.util.Date;

/**
 * base class for transaction log entries
 *
 * @author john
 */
public abstract class AbstractTransactionLogEntry {

    protected Date transactionTimestamp;
    protected long _amount;
    protected int _atc;
    protected String _currency;
    private byte[] _rawEntry;
    protected boolean _hasTime;

    /**
     * @return the transactionTimestamp
     */
    public Date getTransactionTimestamp() {
        return transactionTimestamp;
    }

    /**
     * @param transactionTimestamp the transactionTimestamp to set
     * @param includesTime         if timestamp also contains time (not just date)
     */
    public void setTransactionTimestamp(Date transactionTimestamp,
                                        boolean includesTime) {
        this.transactionTimestamp = transactionTimestamp;
        this._hasTime = includesTime;
    }

    /**
     * @return the _amount
     */
    public long getAmount() {
        return _amount;
    }

    /**
     * @param amount the _amount to set
     */
    public void setAmount(long amount) {
        this._amount = amount;
    }

    /**
     * @return the _currency
     */
    public String getCurrency() {
        return _currency;
    }

    /**
     * @param currency the _currency to set
     */
    public void setCurrency(String currency) {
        this._currency = currency;
    }

    /**
     * @return the _atc (application transaction counter)
     */
    public int getAtc() {
        return _atc;
    }

    /**
     * @param atc the _atc (application transaction counter) to set
     */
    public void setAtc(int atc) {
        this._atc = atc;
    }

    /**
     * @return the _rawEntry
     */
    public byte[] getRawEntry() {
        return _rawEntry;
    }

    /**
     * @param rawEntry the _rawEntry to set
     */
    public void setRawEntry(byte[] rawEntry) {
        this._rawEntry = rawEntry;
    }

    /**
     * @return <code>true</code> if timestamp contains date + time,
     * <code>false</code> otherwise
     */
    public boolean hasTime() {
        return _hasTime;
    }

}