package info.b3.q1.medictime.controllers;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import info.b3.q1.medictime.R;
import info.b3.q1.medictime.models.Medicament;
import info.b3.q1.medictime.models.MedocListe;
import info.b3.q1.medictime.models.Prise;
import info.b3.q1.medictime.models.PrisesListe;

public class PriseFragment extends androidx.fragment.app.Fragment{
    public static final String PRISE_ID = "prise_id";
    protected Prise mPrise;
    private EditText mDebut;
    private EditText mFin;
    private CheckBox mMatin;
    private CheckBox mMidi;
    private CheckBox mSoir;
    private LinearLayout mContainer;

    @Override
    public void onCreate(@androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mPrise = new Prise();
        java.util.UUID prise_id = (java.util.UUID) getActivity().getIntent().getSerializableExtra(PRISE_ID);
        mPrise = PrisesListe.get(getContext()).getPrise(prise_id);

    }
    public void AfficherMedoc() {

        mContainer.removeAllViews();
        MedocListe lab = MedocListe.get(this.getContext());
        for (final Medicament medicament : lab.getMedocs()) {
            View medocView = getMedocView(medicament);
            mContainer.addView(medocView);
        }
    }
    private View getMedocView(Medicament medicament) {
        TextView textView = new TextView(this.getContext());
        textView.setText(medicament.getName());
        return textView;
    }



    @Nullable
    @Override
    public android.view.View onCreateView(@androidx.annotation.NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup container, @Nullable android.os.Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        android.view.View v = inflater.inflate(R.layout.add_prises_fragment, container, false);
        mDebut = (EditText) v.findViewById(R.id.edit_Start_Text_Date);
        mFin = (EditText) v.findViewById(R.id.edit_End_Text_Date);
        mMatin = (CheckBox) v.findViewById(R.id.Matin_CheckBox);
        mMidi = (CheckBox) v.findViewById(R.id.Midi_CheckBox);
        mSoir = (CheckBox) v.findViewById(R.id.Soir_CheckBox);
        mContainer = (LinearLayout) v.findViewById(R.id.medoc_layout_container);
        mDebut.setText(mPrise.getDebut());
        mFin.setText(mPrise.getFin());
        mMatin.setChecked(mPrise.isMatin());
        mMidi.setChecked(mPrise.isMidi());
        mSoir.setChecked(mPrise.isSoir());
        AfficherMedoc();




        mDebut.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mPrise.setDebut(s.toString());
                PrisesListe.get(getContext()).updatePrise(mPrise);
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {
            }
        });
        mFin.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mPrise.setFin(s.toString());
                PrisesListe.get(getContext()).updatePrise(mPrise);
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {
            }
        });
        return v;
    }


}
