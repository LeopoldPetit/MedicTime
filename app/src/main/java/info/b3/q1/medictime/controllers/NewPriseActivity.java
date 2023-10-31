package info.b3.q1.medictime.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import info.b3.q1.medictime.R;
import info.b3.q1.medictime.models.Medicament;
import info.b3.q1.medictime.models.MedocListe;

public class NewPriseActivity extends AppCompatActivity {
    private LinearLayout mContainer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_prises_fragment);
        mContainer = (LinearLayout) findViewById(R.id.medoc_layout_container);
        updateUI();
    }
    private void updateUI() {
        mContainer.removeAllViews();
        MedocListe lab = MedocListe.get(this);
        for (final Medicament medicament : lab.getMedocs()) {
            View medocView = getMedocView(medicament);
            mContainer.addView(medocView);
        }
    }

    private View getMedocView(Medicament medicament) {
        TextView textView = new TextView(getApplicationContext());
        textView.setText(medicament.getName());
        setClickOnMedocView(medicament, textView);
        return textView;
    }
    private void setClickOnMedocView(final Medicament medicament, TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        NewPriseActivity.class);
                intent.putExtra(MedocFragment.MEDOC_ID, medicament.getId());
                startActivity(intent);
            }
        });
    }



}
