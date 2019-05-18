package digital.sepa.nfc;

import android.content.Context;
import digital.sepa.nfc.model.CardInfo;
import digital.sepa.nfc.util.Utils;

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
	private static volatile AppController _instance = null;

	private CardInfo _cardInfo;
	private StringBuilder _log;

	/**
	 * Get singleton object
	 */
	public static synchronized AppController getInstance() {
		if (_instance == null) {
			_instance = new AppController();
		}
		return _instance;
	}

	/**
	 * Private consructor
	 */
	private AppController() {
		this._cardInfo = null;
		this._log = new StringBuilder();
	}

	/**
	 * @return the _cardInfo
	 */
	public CardInfo getCardInfo() {
		return _cardInfo;
	}

	/**
	 * @return the _cardInfo
	 */
	public CardInfo getCardInfoNullSafe(Context ctx) {
		if (_cardInfo == null) {
			return new CardInfo(ctx);
		}
		return _cardInfo;
	}

	/**
	 * @param _cardInfo
	 *            the _cardInfo to set
	 */
	public void setCardInfo(CardInfo cardInfo) {
		this._cardInfo = cardInfo;
	}

	/**
	 * Append line to log
	 * 
	 * @param msg
	 */
	public void log(String msg) {
		_log.append(Utils.getFullTimestampString());
		_log.append(": ");
		_log.append(msg);
		_log.append("\n");
	}

	/**
	 * @return full log
	 */
	public String getLog() {
		return _log.toString();
	}

	/**
	 * clear log
	 */
	public void clearLog() {
		_log = new StringBuilder();
	}
}
