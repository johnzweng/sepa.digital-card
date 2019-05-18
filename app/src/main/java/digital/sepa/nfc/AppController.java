package digital.sepa.nfc;

import android.content.Context;
import digital.sepa.nfc.model.CardInfo;

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


}
