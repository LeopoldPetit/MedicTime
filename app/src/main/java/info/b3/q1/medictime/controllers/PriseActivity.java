package info.b3.q1.medictime.controllers;

import androidx.fragment.app.Fragment;

public class PriseActivity extends SingleFragmentPriseActivity {
    @Override
    protected Fragment createFragment() {
        return new PriseFragment();

    }
}
