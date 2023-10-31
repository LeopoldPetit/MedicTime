package info.b3.q1.medictime.controllers;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import org.jetbrains.annotations.Nullable;

import info.b3.q1.medictime.R;
import info.b3.q1.medictime.models.Medicament;
import info.b3.q1.medictime.models.MedocListe;

public class MedocFragment  extends androidx.fragment.app.Fragment {
    public static final String MEDOC_ID = "medoc_id";
    protected Medicament mMedicament;
    private EditText mNom;
    private EditText mDuree;
    private CheckBox mMatin;
    private CheckBox mMidi;
    private CheckBox mSoir;

    @Override
    public void onCreate(@androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mMedicament = new Medicament();
        java.util.UUID medoc_id = (java.util.UUID) getActivity().getIntent().getSerializableExtra(MEDOC_ID);
        mMedicament = MedocListe.get(getContext()).getMedoc(medoc_id);
    }

    @Nullable
    @Override
    public View onCreateView(@androidx.annotation.NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup container, @Nullable android.os.Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.add_medoc_fragment, container, false);
        mNom = (EditText) v.findViewById(R.id.editNameText);
        mDuree = (EditText) v.findViewById(R.id.editDefaultDurée);
        mMatin = (CheckBox) v.findViewById(R.id.Matin_CheckBox);
        mMidi = (CheckBox) v.findViewById(R.id.Midi_CheckBox);
        mSoir = (CheckBox) v.findViewById(R.id.Soir_CheckBox);



        mNom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mMedicament.setName(s.toString());
                MedocListe.get(getContext()).updateMedoc(mMedicament);
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {
            }
        });
        mDuree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                mMedicament.setDuree(java.lang.Integer.parseInt(s.toString()));
                MedocListe.get(getContext()).updateMedoc(mMedicament);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mMatin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mMedicament.setMatin(isChecked);
                MedocListe.get(getContext()).updateMedoc(mMedicament);
            }
        });
        mMidi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mMedicament.setMidi(isChecked);
                MedocListe.get(getContext()).updateMedoc(mMedicament);
            }
        });
        mSoir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mMedicament.setSoir(isChecked);
                MedocListe.get(getContext()).updateMedoc(mMedicament);
            }
        });
        return v;
    }
}
