package digital.sepa.nfc.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import digital.sepa.nfc.AppController;
import digital.sepa.nfc.R;

// TODO: maybe also add share action for general and transations fragment

/**
 * Activity for displaying the results (hosts fragements in tabs).
 *
 * @author Johannes Zweng <johannes@zweng.at>
 */
public class ResultActivity extends FragmentActivity {

    private static AppController _controller = AppController.getInstance();
    private Fragment _fragmentResultInfos;
    private Fragment _fragmentResultEmxTxList;
    private Fragment _fragmentResultQuickTxList;
    private Fragment _fragmentResultLog;
    private boolean _showQuickLog;
    private boolean _showEmvLog;
    private int _numLogTabs;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager _viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    //	@Override
    //	public boolean onCreateOptionsMenu(Menu menu) {
    //		// Inflate the menu; this adds items to the action bar if it is present.
    //		getMenuInflater().inflate(R.menu.main_menu, menu);
    //		// Locate MenuItem with ShareActionProvider
    //		MenuItem item = menu.findItem(R.id.action_share);
    //		// Fetch and store ShareActionProvider
    //		ShareActionProvider shareActionProvider = (ShareActionProvider) item
    //				.getActionProvider();
    //
    //		// set the log content as share content
    //		Intent shareIntent = new Intent();
    //		shareIntent.setAction(Intent.ACTION_SEND);
    //		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
    //				getResources().getString(R.string.action_share_subject));
    //		shareIntent.putExtra(Intent.EXTRA_TEXT, AppController.getInstance()
    //				.getLog());
    //		shareIntent.setType("text/plain");
    //		shareActionProvider.setShareIntent(shareIntent);
    //		return true;
    //	}

    //	/**
    //	 * Called whenever we call invalidateOptionsMenu()
    //	 */
    //	@Override
    //	public boolean onPrepareOptionsMenu(Menu menu) {
    //		// show share action only on Tab 2 (Log)
    //		// (tab index starts with 0)
    //		if (_viewPager.getCurrentItem() == 2 && _numLogTabs == 1) {
    //			menu.findItem(R.id.action_share).setVisible(true);
    //		} else if (_viewPager.getCurrentItem() == 3 && _numLogTabs == 2) {
    //			menu.findItem(R.id.action_share).setVisible(true);
    //		} else {
    //			menu.findItem(R.id.action_share).setVisible(false);
    //		}
    //		return true;
    //	}
    //
    //	@Override
    //	public boolean onOptionsItemSelected(MenuItem item) {
    //		switch (item.getItemId()) {
    //		case R.id.action_about:
    //			showAboutDialog(getFragmentManager());
    //			return true;
    //		case R.id.action_changelog:
    //			showChangelogDialog(getFragmentManager(), true);
    //			return true;
    //		case R.id.action_settings:
    //			Intent i = new Intent();
    //			i.setComponent(new ComponentName(getApplicationContext(),
    //					SettingsActivity.class));
    //			startActivity(i);
    //			return true;
    //		}
    //		return false;
    //	}


}
