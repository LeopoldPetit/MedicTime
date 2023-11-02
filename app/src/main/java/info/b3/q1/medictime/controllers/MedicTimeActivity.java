package info.b3.q1.medictime.controllers;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import info.b3.q1.medictime.R;
import info.b3.q1.medictime.models.Prise;
import info.b3.q1.medictime.models.PrisesListe;

public class MedicTimeActivity extends AppCompatActivity {
    private LinearLayout mContainer;

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
        for (final Prise prise : lab.getPrises()) {
            View priseView = getPriseView(prise);
            mContainer.addView(priseView);
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
