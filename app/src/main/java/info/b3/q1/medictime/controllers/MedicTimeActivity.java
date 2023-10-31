package info.b3.q1.medictime.controllers;

import androidx.fragment.app.Fragment;

public class MedicTimeActivity extends SingleFragmentMedocActivity {
    @Override
    protected Fragment createFragment() {
        return new MedocFragment();

    }
}
