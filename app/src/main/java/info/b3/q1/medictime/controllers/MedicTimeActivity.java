package info.b3.q1.medictime.controllers;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.List;

import info.b3.q1.medictime.R;
import info.b3.q1.medictime.models.Prise;
import info.b3.q1.medictime.models.PrisesListe;

public class MedicTimeActivity extends AppCompatActivity {
    private LinearLayout mContainer;
    private LinearLayout mMatinContainer;
    private LinearLayout mMidiContainer;
    private LinearLayout mSoirContainer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medic_time_page);
        mContainer = (LinearLayout) findViewById(R.id.prises_layout_container);
        updateUI();
    }
    private void updateUI() {
        mContainer.removeAllViews();
        PrisesListe lab = PrisesListe.get(this);
        for (String jour : lab.getTousLesJoursPossibles()) {
            List<Prise> prises = lab.getPrisesByJour(jour);
            View jourFrag = getLayoutInflater().inflate(R.layout.list_prise_fragment, null);
            ((TextView) jourFrag.findViewById(R.id.jour_Text)).setText(jour);

            // Initialiser les conteneurs à l'intérieur de jourFrag
            LinearLayout matinContainer = jourFrag.findViewById(R.id.matin_container);
            LinearLayout midiContainer = jourFrag.findViewById(R.id.midi_container);
            LinearLayout soirContainer = jourFrag.findViewById(R.id.soir_container);
            matinContainer.removeAllViews();
            midiContainer.removeAllViews();
            soirContainer.removeAllViews();

            for (final Prise prise : prises) {
                // Utiliser les conteneurs existants dans jourFrag
                if (prise.isMatin()) {
                    TextView textView = new TextView(this);
                    textView.setTextColor(ContextCompat.getColor(this, R.color.white));
                    textView.setText(prise.getMedocId().toString());
                    matinContainer.addView(textView);
                }
                if (prise.isMidi()) {
                    TextView textView = new TextView(this);
                    textView.setTextColor(ContextCompat.getColor(this, R.color.white));
                    textView.setText(prise.getMedocId().toString());
                    midiContainer.addView(textView);
                }
                if (prise.isSoir()) {
                    TextView textView = new TextView(this);
                    textView.setTextColor(ContextCompat.getColor(this, R.color.white));
                    textView.setText(prise.getMedocId().toString());
                    soirContainer.addView(textView);
                }
            }

            mContainer.addView(jourFrag);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }


    private View getPriseView(Prise prise) {
        TextView textView = new TextView(getApplicationContext());
        textView.setText("prise: Début: " + prise.getDebut() +" Fin: " + prise.getFin() );
        setClickOnPriseView(prise, textView);
        return textView;
    }
    private void setClickOnPriseView(final Prise prise, View textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        PriseActivity.class);
                intent.putExtra(PriseFragment.PRISE_ID, prise.getId());
                startActivity(intent);
            }
        });
    }

}
