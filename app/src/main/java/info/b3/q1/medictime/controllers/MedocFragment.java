package info.b3.q1.medictime.controllers;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
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
    private Button mAddButton;

    @Override
    public void onCreate(@androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNewMedoc();

    }
    private Integer createId() {
        return MedocListe.get(getContext()).getMedocs().size() + 2;
    }
    private void createNewMedoc() {
        mMedicament = new Medicament();
        mMedicament.setName("Medicament");
        mMedicament.setId(createId());
        mMedicament.setDuree(1);
        mMedicament.setMatin(true);
        mMedicament.setMidi(true);
        mMedicament.setSoir(true);
    }
    public void setClickOnAddButton(View addButton) {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MedocListe lab = MedocListe.get(getContext());
                lab.addMedoc(mMedicament);
                requireActivity().onBackPressed();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@androidx.annotation.NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup container, @Nullable android.os.Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.medoc_fragment, container, false);
        mNom = (EditText) v.findViewById(R.id.editNameText);
        mDuree = (EditText) v.findViewById(R.id.editDefaultDurée);
        mMatin = (CheckBox) v.findViewById(R.id.Matin_CheckBox);
        mMidi = (CheckBox) v.findViewById(R.id.Midi_CheckBox);
        mSoir = (CheckBox) v.findViewById(R.id.Soir_CheckBox);
        mAddButton = (Button) v.findViewById(R.id.Ajouter_Button);
        mNom.setText(mMedicament.getName());
        mDuree.setText(java.lang.Integer.toString(mMedicament.getDuree()));
        mMatin.setChecked(mMedicament.isMatin());
        mMidi.setChecked(mMedicament.isMidi());
        mSoir.setChecked(mMedicament.isSoir());
        setClickOnAddButton(mAddButton);



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
