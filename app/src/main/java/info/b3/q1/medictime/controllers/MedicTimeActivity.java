package info.b3.q1.medictime.controllers;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import info.b3.q1.medictime.R;
import info.b3.q1.medictime.models.MedocListe;
import info.b3.q1.medictime.models.Prise;
import info.b3.q1.medictime.models.PrisesListe;

public class MedicTimeActivity extends AppCompatActivity {
    private LinearLayout mContainer;
    private Button mAddButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medic_time_page);
        mAddButton = (Button) findViewById(R.id.add_button);
        setClickOnAddButton(mAddButton);
        mContainer = (LinearLayout) findViewById(R.id.prises_layout_container);
        updateUI();
    }
    private void updateUI() {
        mContainer.removeAllViews();
        PrisesListe lab = PrisesListe.get(this);
        List<String> JoursTriees = sortAndFilterDates(lab.getTousLesJoursPossibles());
        for (String jour : JoursTriees) {
            List<Prise> prises = lab.getPrisesByJour(jour);
            View jourFrag = getLayoutInflater().inflate(R.layout.list_prise_fragment, null);
            ((TextView) jourFrag.findViewById(R.id.jour_Text)).setText(jour);
            LinearLayout matinContainer = jourFrag.findViewById(R.id.matin_container);
            LinearLayout midiContainer = jourFrag.findViewById(R.id.midi_container);
            LinearLayout soirContainer = jourFrag.findViewById(R.id.soir_container);
            matinContainer.removeAllViews();
            midiContainer.removeAllViews();
            soirContainer.removeAllViews();

            for (final Prise prise : prises) {
                if (prise.isMatin()) {
                    matinContainer.addView(getPriseView(prise));
                }
                if (prise.isMidi()) {
                    midiContainer.addView(getPriseView(prise));
                }
                if (prise.isSoir()) {
                    soirContainer.addView(getPriseView(prise));
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
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        MedocListe lab = MedocListe.get(this);
        String medocName= lab.getMedocName(prise.getMedocId());
        textView.setText(medocName);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTypeface(null, Typeface.BOLD);

        return textView;
    }

    private void setClickOnAddButton(View addButton) {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        PriseActivity.class);
                startActivity(intent);
            }
        });
    }
    public static List<String> sortAndFilterDates(List<String> dates) {
        DateTimeFormatter formatter;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        } else {
            formatter = null;
        }
        LocalDate currentDate;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        } else {
            currentDate = null;
        }
        List<LocalDate> localDateList = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localDateList = dates.stream()
                    .map(date -> LocalDate.parse(date, formatter))
                    .filter(date -> !date.isBefore(currentDate)) // Filtrer les dates antérieures
                    .collect(Collectors.toList());
        }
        Collections.sort(localDateList);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return localDateList.stream()
                    .map(date -> date.format(formatter))
                    .collect(Collectors.toList());
        }
        else {
            return null;
        }
    }

}
