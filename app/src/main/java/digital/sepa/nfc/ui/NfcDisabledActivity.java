package digital.sepa.nfc.ui;

import android.app.Activity;
import android.os.Bundle;
import digital.sepa.nfc.R;


/**
 * Very simple activity, simply displays a no nfc logo (we show this if NFC is
 * not available)
 * 
 * @author Johannes Zweng <johannes@zweng.at>
 */
public class NfcDisabledActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfc_disabled);
	}

}
