package digital.sepa.nfc;

import android.content.Context;
import android.util.Log;
import digital.sepa.nfc.model.CardInfo;
import digital.sepa.nfc.util.Utils;

import static digital.sepa.nfc.util.Utils.TAG;

/**
 * Very simple dummy-style controller class of this app. At the moment simply
 * used to pass the reading result around..
 *
 * @author Johannes Zweng <johannes@zweng.at>
 */
public class AppController {



    /**
     * singleton instance
     */
    private static volatile AppController instance = null;

    private CardInfo cardInfo;
    private StringBuilder log;

    /**
     * Get singleton object
     */
    public static synchronized AppController getInstance() {
        if (instance == null) {
            instance = new AppController();
        }
        return instance;
    }

    /**
     * Private consructor
     */
    private AppController() {
        this.cardInfo = null;
        this.log = new StringBuilder();
    }

    /**
     * @return the cardInfo
     */
    public CardInfo getCardInfo() {
        return cardInfo;
    }

    /**
     * @return the cardInfo
     */
    public CardInfo getCardInfoNullSafe(Context ctx) {
        if (cardInfo == null) {
            return new CardInfo(ctx);
        }
        return cardInfo;
    }

    /**
     * @param cardInfo the cardInfo to set
     */
    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    /**
     * Append line to log
     *
     * @param msg
     */
    public void log(String msg) {
        log.append(Utils.getFullTimestampString());
        log.append(": ");
        log.append(msg);
        log.append("\n");
        Log.d(TAG, msg);
    }

    /**
     * @return full log
     */
    public String getLog() {
        return log.toString();
    }

    /**
     * clear log
     */
    public void clearLog() {
        log = new StringBuilder();
    }
}
