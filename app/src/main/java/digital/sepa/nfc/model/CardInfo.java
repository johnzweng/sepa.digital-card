package digital.sepa.nfc.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
    private int pinRetryCounter;
    private String personalAccounNumber;
    private String cardType = "<unknown>";
    private String quickCurrency;
    private Context ctx;

    private List<QuickTransactionLogEntry> quickLog;
    private List<EmvTransactionLogEntry> transactionLog;
    private List<InfoKeyValuePair> infoKeyValuePairs;

    private final static String MAESTRO = "Maestro";
    private final static String VISA_CREDIT = "VISA Credit";
    private final static String MASTERCARD_CREDIT = "Mastercard";

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
    }

    /**
     * @param maestroCard true if is a maestro card
     */
    public void setMaestroCard(boolean maestroCard) {
        this.maestroCard = maestroCard;
        this.cardType = MAESTRO;
    }

    /**
     * @param visaCard true if is a VISA creditcard
     */
    public void setVisaCreditCard(boolean visaCard) {
        this.visaCard = visaCard;
        this.cardType=VISA_CREDIT;
    }

    /**
     * @param masterCarrd true if is a Mastercard creditcard
     */
    public void setMasterCard(boolean masterCarrd) {
        this.masterCard = masterCarrd;
        this.cardType = MASTERCARD_CREDIT;
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
    }

    @Override
    public String toString() {
        return "CardInfo{" +
                "nfcTagId=" + Arrays.toString(nfcTagId) +
                ", quickCard=" + quickCard +
                ", maestroCard=" + maestroCard +
                ", containsTxLogs=" + containsTxLogs +
                ", visaCard=" + visaCard +
                ", masterCard=" + masterCard +
                ", pinRetryCounter=" + pinRetryCounter +
                ", personalAccounNumber='" + personalAccounNumber + '\'' +
                ", quickCurrency='" + quickCurrency + '\'' +
                ", ctx=" + ctx +
                ", quickLog=" + quickLog +
                ", transactionLog=" + transactionLog +
                ", infoKeyValuePairs=" + infoKeyValuePairs +
                '}';
    }

    public String getPersonalAccounNumber() {
        return personalAccounNumber;
    }

    public void setPersonalAccounNumber(String personalAccounNumber) {
        this.personalAccounNumber = personalAccounNumber;
    }

    public String getCardType() {
        return cardType;
    }
}
