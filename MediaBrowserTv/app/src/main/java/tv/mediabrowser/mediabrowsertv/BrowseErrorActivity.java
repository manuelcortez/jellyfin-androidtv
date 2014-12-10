/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package tv.mediabrowser.mediabrowsertv;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

/*
 * BrowseErrorActivity shows how to use ErrorFragment
 */
public class BrowseErrorActivity extends Activity {
    private static int TIMER_DELAY = 3000;

    private ErrorFragment mErrorFragment;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        showError(getIntent().getStringExtra("Message"), getIntent().getBooleanExtra("Shutdown", false));
    }

    private void showError(final String msg, final Boolean shutDown) {
        mErrorFragment = new ErrorFragment();
        getFragmentManager().beginTransaction().add(R.id.main_browse_fragment, mErrorFragment).commit();

        final Handler display = new Handler();
        display.postDelayed(new Runnable() {
            @Override
            public void run() {
                mErrorFragment.setErrorContent(msg);
            }
        }, 250);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mErrorFragment.setErrorContent(msg);
                if (shutDown) System.exit(1);
            }
        }, TIMER_DELAY);
    }

}