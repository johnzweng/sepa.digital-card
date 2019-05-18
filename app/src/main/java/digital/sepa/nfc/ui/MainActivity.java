package digital.sepa.nfc.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import digital.sepa.nfc.AppController;
import digital.sepa.nfc.R;
import digital.sepa.nfc.exceptions.NoSmartCardException;
import digital.sepa.nfc.iso7816emv.EmvCardReader;
import digital.sepa.nfc.model.CardInfo;
import digital.sepa.nfc.util.Utils;

import java.io.IOException;

import static digital.sepa.nfc.util.Utils.TAG;
import static digital.sepa.nfc.util.Utils.displaySimpleAlertDialog;

/**
 * Startup activity
 *
 * @author Johannes Zweng <johannes@zweng.at>
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class MainActivity extends Activity implements NfcAdapter.ReaderCallback {

    // for NFC stuff
    private PendingIntent pendingIntent;
    private IntentFilter[] filters;
    private String[][] techLists;
    private NfcAdapter nfcAdapter;

    // View elements
    private View viewNfcLogo;
    private View viewTextViewShowCard;
    private View viewProgressStatus;

    private CardInfo cardReadingResults;
    private ReadNfcCardTask readCardTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find view elements
        viewProgressStatus = findViewById(R.id.read_card_status);
        viewNfcLogo = findViewById(R.id.imageViewNfcLogo);
        viewTextViewShowCard = findViewById(R.id.textViewYourCardPlease);

        // NFC stuff
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        filters = new IntentFilter[]{new IntentFilter(
                NfcAdapter.ACTION_TECH_DISCOVERED)};
        techLists = new String[][]{{"android.nfc.tech.NfcA"}};
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isNfcAvailable()) {
            startActivity(new Intent(this, NfcDisabledActivity.class));
            this.finish();
            return;
        }

        if (nfcAdapter != null) {
            //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //                Log.i(TAG, "NFC enableReaderMode without P2P only NFC A");
            //                nfcAdapter.enableReaderMode(this, this,
            //                        (NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK | NfcAdapter.FLAG_READER_NFC_A),
            //                        null);
            //            } else {
            Log.i(TAG, "enabling foreground NFC dispatch");
            nfcAdapter.enableForegroundDispatch(this, pendingIntent,
                    filters, techLists);
            //            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //                Log.i(TAG, "NFC disableReaderMode");
            //                nfcAdapter.disableReaderMode(this);
            //            } else {
            Log.i(TAG, "disabling foreground NFC dispatch");
            nfcAdapter.disableForegroundDispatch(this);
            //            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showProgressAnimation(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //		case R.id.action_about:
            //			showAboutDialog(getFragmentManager());
            //			return true;
            //		case R.id.action_changelog:
            //			showChangelogDialog(getFragmentManager(), true);
            //			return true;
            case R.id.action_settings:
                Intent i = new Intent();
                i.setComponent(new ComponentName(getApplicationContext(),
                        SettingsActivity.class));
                startActivity(i);
                return true;
        }
        return false;
    }

    /**
     * Callback when using reader mode
     *
     * @param tag read nfc tag
     */
    @Override
    public void onTagDiscovered(Tag tag) {
        handleTag(tag);
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent()");
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            handleTag(tag);
        }
    }

    /**
     * @return <code>true</code> if NFC is available on this device and enabled
     * in Adnroid system settings
     */
    private boolean isNfcAvailable() {
        return (nfcAdapter != null && nfcAdapter.isEnabled());
    }

    /**
     * Show or hide the progress animation..
     *
     * @param show
     */
    private void showProgressAnimation(final boolean show) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewProgressStatus.setVisibility(show ? View.VISIBLE : View.GONE);
                viewNfcLogo.setVisibility(show ? View.GONE : View.VISIBLE);
                viewTextViewShowCard.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });
    }

    /**
     * Called whenever we detect a NFC Tag
     */
    private void handleTag(Tag tag) {
        showProgressAnimation(true);
        if (readCardTask != null) {
            return;
        }
        showProgressAnimation(true);
        readCardTask = new ReadNfcCardTask(tag);
        readCardTask.execute((Void) null);
    }

    /**
     * Asynchronous task to read the card)
     */
    public class ReadNfcCardTask extends AsyncTask<Void, Void, Boolean> {
        private final static int ERROR_TAG_LOST = -1;
        private final static int ERROR_IO_EX = -2;
        private final static int ERROR_NO_SMARTCARD = -3;
        private Tag nfcTag;
        private int error;

        public ReadNfcCardTask(Tag tag) {
            super();
            this.nfcTag = tag;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            AppController ctl = AppController.getInstance();
            try {
                EmvCardReader reader = new EmvCardReader(
                        nfcTag, MainActivity.this);
                reader.connectIsoDep();
                // read setting value
                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(MainActivity.this);
                cardReadingResults = reader.readAllCardData(prefs.getBoolean(
                        "perform_full_file_scan", false));
                ctl.setCardInfo(cardReadingResults);
                reader.disconnectIsoDep();
            } catch (NoSmartCardException nsce) {
                Log.w(TAG,
                        "Catched NoSmartCardException during reading the card",
                        nsce);
                error = ERROR_NO_SMARTCARD;
                return false;
            } catch (TagLostException tle) {
                Log.w(TAG, "Catched TagLostException during reading the card",
                        tle);
                error = ERROR_TAG_LOST;
                return false;
            } catch (IOException e) {
                Log.e(TAG, "Catched IOException during reading the card", e);
                error = ERROR_IO_EX;
                return false;
            }
            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected void onPostExecute(final Boolean success) {
            readCardTask = null;

            try {
                if (success) {
                    Log.d(TAG, "reading card finished successfully");
                    if (!cardReadingResults.isSupportedCard()) {
                        displaySimpleAlertDialog(
                                MainActivity.this,
                                getResources()
                                        .getString(
                                                R.string.dialog_title_error_unsupported_card),
                                getResources()
                                        .getString(
                                                R.string.dialog_text_error_unsupported_card),
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        showProgressAnimation(false);
                                    }
                                });

                    } else if (cardReadingResults.getPersonalAccounNumber() == null) {
                        displaySimpleAlertDialog(
                                MainActivity.this,
                                getResources().getString(R.string.dialog_title_pan_not_found),
                                getResources().getString(R.string.dialog_text_pan_not_found, cardReadingResults.getCardType()),
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        showProgressAnimation(false);
                                    }
                                });
                    } else {
                        Log.d(TAG, "card is supported");
                        // show results page
                        String url = Utils.getPanUrl(cardReadingResults.getPersonalAccounNumber());
                        Log.d(TAG, "opening URL: " + url);
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        MainActivity.this.finish();
                    }
                } else {
                    if (error == ERROR_TAG_LOST) {
                        displaySimpleAlertDialog(
                                MainActivity.this,
                                getResources().getString(
                                        R.string.dialog_title_error_card_lost),
                                getResources().getString(
                                        R.string.dialog_text_error_card_lost),
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        showProgressAnimation(false);
                                    }
                                });
                    } else if (error == ERROR_NO_SMARTCARD) {
                        displaySimpleAlertDialog(
                                MainActivity.this,
                                getResources().getString(
                                        R.string.dialog_title_error_no_smartcard),
                                getResources().getString(
                                        R.string.dialog_text_error_no_smartcard),
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        showProgressAnimation(false);
                                    }
                                });
                    }
                    // In this case we still open the result Activity for allowing
                    // the user to inspect the stacktrace in the Log tab
                    else if (error == ERROR_IO_EX) {
                        displaySimpleAlertDialog(
                                MainActivity.this,
                                getResources().getString(
                                        R.string.dialog_title_error_ioexception),
                                getResources().getString(
                                        R.string.dialog_text_error_ioexception),
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        showProgressAnimation(false);
                                    }
                                });

                    } else {
                        displaySimpleAlertDialog(
                                MainActivity.this,
                                getResources().getString(
                                        R.string.dialog_title_error_unknown),
                                getResources().getString(
                                        R.string.dialog_text_error_unknown),
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        showProgressAnimation(false);
                                    }
                                });
                    }
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, getString(R.string.toast_exception) + " " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            readCardTask = null;
            showProgressAnimation(false);
        }
    }
}
