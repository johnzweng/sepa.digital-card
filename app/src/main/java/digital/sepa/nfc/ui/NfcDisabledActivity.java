package digital.sepa.nfc.ui;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import digital.sepa.nfc.R;


/**
 * Very simple activity, simply displays a no nfc logo (we show this if NFC is
 * not available)
 *
 * @author Johannes Zweng <johannes@zweng.at>
 */
public class NfcDisabledActivity extends Activity {

    private NfcAdapter nfc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_disabled);
        nfc = NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNfcAvailable()) {
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }
    }

    public void openNfcSettings(View view) {
        startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
    }

    /**
     * @return <code>true</code> if NFC is available on this device and enabled
     * in Adnroid system settings
     */
    private boolean isNfcAvailable() {
        return (nfc != null && nfc.isEnabled());
    }


}
