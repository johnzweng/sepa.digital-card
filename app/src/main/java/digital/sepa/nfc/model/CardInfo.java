package digital.sepa.nfc.model;

import android.content.Context;
import digital.sepa.nfc.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static digital.sepa.nfc.util.Utils.bytesToHex;
import static digital.sepa.nfc.util.Utils.formatBalance;

/**
 * Represents the data read from a bankomat card.
 *
 * @author Johannes Zweng <johannes@zweng.at>
 */
public class CardInfo {

    private byte[] nfcTagId;
    private boolean quickCard;
    private boolean maestroCard;
    private boolean containsTxLogs;
    private boolean visaCard;
    private boolean masterCard;
    private long quickBalance;
    private int pinRetryCounter;
    private String quickCurrency;
    private Context ctx;

    private List<QuickTransactionLogEntry> quickLog;
    private List<EmvTransactionLogEntry> transactionLog;
    private List<InfoKeyValuePair> infoKeyValuePairs;

    /**
     * Constructor
     */
    public CardInfo(Context ctx) {
        // create empty list
        this.transactionLog = new ArrayList<EmvTransactionLogEntry>();
        this.quickLog = new ArrayList<QuickTransactionLogEntry>();
        this.infoKeyValuePairs = new ArrayList<InfoKeyValuePair>();
        this.pinRetryCounter = -1;
        this.quickCurrency = "<unknown, or parsing error>";
        this.ctx = ctx;
    }

    /**
     * @return the nfcTagId
     */
    public byte[] getNfcTagId() {
        return nfcTagId;
    }

    /**
     * @param nfcTagId the nfcTagId to set
     */
    public void setNfcTagId(byte[] nfcTagId) {
        this.nfcTagId = nfcTagId;
        this.addKeyValuePair(new InfoKeyValuePair(ctx.getResources()
                .getString(R.string.lbl_nfc_tag_id), "0x"
                + bytesToHex(nfcTagId)));
    }

    /**
     * @return the quickLog
     */
    public List<QuickTransactionLogEntry> getQuickLog() {
        return quickLog;
    }

    /**
     * @param quickLog the quickLog to set
     */
    public void setQuickLog(List<QuickTransactionLogEntry> quickLog) {
        this.quickLog = quickLog;
    }

    /**
     * @return the transactionLog
     */
    public List<EmvTransactionLogEntry> getTransactionLog() {
        return transactionLog;
    }

    /**
     * @param transactionLog the transactionLog to set
     */
    public void setTransactionLog(List<EmvTransactionLogEntry> transactionLog) {
        this.transactionLog = transactionLog;
    }

    /**
     * @return the infoKeyValuePairs
     */
    public List<InfoKeyValuePair> getInfoKeyValuePairs() {
        return infoKeyValuePairs;
    }

    /**
     * Add a info key-value pair
     *
     * @param pair
     */
    public void addKeyValuePair(InfoKeyValuePair pair) {
        infoKeyValuePairs.add(pair);
    }

    /**
     * @param headerName
     */
    public void addSectionHeader(String headerName) {
        infoKeyValuePairs.add(new InfoKeyValuePair(headerName));
    }

    /**
     * Add a list of key-value pairs
     *
     * @param pairs
     */
    public void addKeyValuePairs(List<InfoKeyValuePair> pairs) {
        infoKeyValuePairs.addAll(pairs);
    }

    /**
     * @return the quickCard
     */
    public boolean isQuickCard() {
        return quickCard;
    }

    /**
     * @param quickCard the quickCard to set
     */
    public void setQuickCard(boolean quickCard) {
        this.quickCard = quickCard;
        if (quickCard) {
            this.addKeyValuePair(new InfoKeyValuePair(ctx.getResources()
                    .getString(R.string.lbl_is_quick_card), quickCard ? ctx
                    .getResources().getString(R.string.yes) : ctx
                    .getResources().getString(R.string.no)));
        }
    }

    /**
     * @return <code>true</code> if is a maestro card
     */
    public boolean isMaestroCard() {
        return maestroCard;
    }

    /**
     * @return true if is a VISA card
     */
    public boolean isVisaCard() {
        return visaCard;
    }

    /**
     * @return true if is a VISA card
     */
    public boolean isMasterCard() {
        return masterCard;
    }

    /**
     * @return true if is one of the supported card types
     */
    public boolean isSupportedCard() {
        return quickCard || maestroCard || masterCard || visaCard;
    }

    /**
     * @return true if is (one of the supported) EMV cards (not quick)
     */
    public boolean isEmvCard() {
        return maestroCard || masterCard || visaCard;
    }

    /**
     * @return true card contains TX logs
     */
    public boolean containsTxLogs() {
        return containsTxLogs;
    }

    /**
     * @param containsTxLogs true if card seems to contain TX logs
     */
    public void setContainsTxLogs(boolean containsTxLogs) {
        this.containsTxLogs = containsTxLogs;
        this.addKeyValuePair(new InfoKeyValuePair(ctx.getResources()
                .getString(R.string.lbl_contains_emv_log_entry_tag),
                containsTxLogs ? ctx.getResources().getString(R.string.yes)
                        : ctx.getResources().getString(R.string.no)));
    }

    /**
     * @param maestroCard true if is a maestro card
     */
    public void setMaestroCard(boolean maestroCard) {
        this.maestroCard = maestroCard;
        if (maestroCard) {
            this.addKeyValuePair(new InfoKeyValuePair(ctx.getResources()
                    .getString(R.string.lbl_is_maestro_card),
                    maestroCard ? ctx.getResources().getString(R.string.yes)
                            : ctx.getResources().getString(R.string.no)));
        }
    }

    /**
     * @param visaCard true if is a VISA creditcard
     */
    public void setVisaCard(boolean visaCard) {
        this.visaCard = visaCard;
        // do not show this label, if it is no VISA card
        if (visaCard) {
            this.addKeyValuePair(new InfoKeyValuePair(ctx.getResources()
                    .getString(R.string.lbl_is_visa_card), visaCard ? ctx
                    .getResources().getString(R.string.yes) : ctx
                    .getResources().getString(R.string.no)));
        }
    }

    /**
     * @param masterCarrd true if is a Mastercard creditcard
     */
    public void setMasterCard(boolean masterCarrd) {
        this.masterCard = masterCarrd;
        // do not show this label, if it is no Mastercard
        if (masterCarrd) {
            this.addKeyValuePair(new InfoKeyValuePair(ctx.getResources()
                    .getString(R.string.lbl_is_mastercard), masterCarrd ? ctx
                    .getResources().getString(R.string.yes) : ctx
                    .getResources().getString(R.string.no)));
        }
    }

    /**
     * @return the quickBalance
     */
    public long getQuickBalance() {
        return quickBalance;
    }

    /**
     * @param quickBalance the quickBalance to set
     */
    public void setQuickBalance(long quickBalance) {
        this.quickBalance = quickBalance;
        this.addKeyValuePair(new InfoKeyValuePair(ctx.getResources()
                .getString(R.string.lbl_quick_balance),
                formatBalance(quickBalance)));
    }

    /**
     * @return the quick currency
     */
    public String getQuickCurrency() {
        return quickCurrency;
    }

    /**
     * @param quickCurrency the quickCurrency to set
     */
    public void setQuickCurrency(String quickCurrency) {
        this.quickCurrency = quickCurrency;
        this.addKeyValuePair(new InfoKeyValuePair(ctx.getResources()
                .getString(R.string.lbl_quick_currency), quickCurrency));
    }

    /**
     * @return the pin retry counter
     */
    public int getPinRetryCounter() {
        return pinRetryCounter;
    }

    /**
     * @param pinRetryCounter the pinRetryCounter to set
     */
    public void setPinRetryCounter(int pinRetryCounter) {
        this.pinRetryCounter = pinRetryCounter;
        this.addKeyValuePair(new InfoKeyValuePair(ctx.getResources()
                .getString(R.string.lbl_remaining_pin_retries), Integer
                .toString(pinRetryCounter)));
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CardInfo [nfcTagId=" + Arrays.toString(nfcTagId)
                + ", quickCard=" + quickCard + ", maestroCard="
                + maestroCard + ", visaCard=" + visaCard
                + ", quickBalance=" + quickBalance + ", pinRetryCounter="
                + pinRetryCounter + ", quickCurrency=" + quickCurrency
                + ", ctx=" + ctx + ", transactionLog=" + transactionLog
                + ", infoKeyValuePairs=" + infoKeyValuePairs + "]";
    }

}
