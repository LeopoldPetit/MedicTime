package info.b3.q1.medictime.controllers;

import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import info.b3.q1.medictime.R;
import info.b3.q1.medictime.models.Medicament;
import info.b3.q1.medictime.models.MedocListe;
import info.b3.q1.medictime.models.Prise;
import info.b3.q1.medictime.models.PrisesListe;

public class PriseFragment extends androidx.fragment.app.Fragment{
    protected Prise mPrise;
    private EditText mDebut;
    private EditText mFin;
    private CheckBox mMatin;
    private CheckBox mMidi;
    private CheckBox mSoir;
    private LinearLayout mContainer;
    private Button mAddButton;
    private Button mAddMedocButton;


    @Override
    public void onCreate(@androidx.annotation.Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNewPrise();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void createNewPrise() {
        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        }

        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        // Vérification pour s'assurer que currentDate n'est pas null
        if (currentDate != null) {
            String formattedDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formattedDate = currentDate.format(formatter);
            }
            mPrise = new Prise();
            mPrise.setMedocId(1);
            mPrise.setDebut(formattedDate);
            mPrise.setFin(formattedDate);
            mPrise.setMatin(true);
            mPrise.setMidi(true);
            mPrise.setSoir(true);
        }
    }
    public void AfficherMedoc() {
        mContainer.removeAllViews();
        MedocListe lab = MedocListe.get(this.getContext());
        for (final Medicament medicament : lab.getMedocs()) {
            View medocView = getMedocView(medicament);
            mContainer.addView(medocView);
        }
    }
    private View getMedocView(final Medicament medicament) {
        final TextView textView = new TextView(this.getContext());
        textView.setTextColor(ContextCompat.getColor(this.getContext(), R.color.white));
        textView.setText(medicament.getName());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(64, 0, 0, 0);
        textView.setLayoutParams(layoutParams);
        setClickOnMedocView(medicament, textView);
        return textView;
    }

    private void setClickOnMedocView(final Medicament medicament, final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPrise.setMedocId(medicament.getId());
                PrisesListe.get(getContext()).updatePrise(mPrise);

                // Changer la couleur du texte au clic pour le médicament sélectionné
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.red));

                // Réinitialiser la couleur des autres médicaments
                resetOtherMedocColors(textView);
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(getContext(),
                        MedocActivity.class);
                intent.putExtra(MedocFragment.MEDOC_ID, medicament.getId());
                startActivity(intent);
                return true;
            }

        });
    }

    private void resetOtherMedocColors(TextView selectedTextView) {
        ViewGroup parentLayout = (ViewGroup) selectedTextView.getParent();
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View child = parentLayout.getChildAt(i);
            if (child instanceof TextView && child != selectedTextView) {
                ((TextView) child).setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            }
        }
    }

    public void setClickOnAddButton(View addButton) {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrisesListe lab = PrisesListe.get(getContext());
                lab.addPrise(mPrise);
                requireActivity().onBackPressed();
            }
        });
    }
    private void setClickOnAddMedocButton(View addButton) {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),
                        MedocActivity.class);
                startActivity(intent);
            }
        });
    }


    @Nullable
    @Override
    public android.view.View onCreateView(@androidx.annotation.NonNull android.view.LayoutInflater inflater, @Nullable android.view.ViewGroup container, @Nullable android.os.Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        android.view.View v = inflater.inflate(R.layout.prise_fragment, container, false);
        mDebut = (EditText) v.findViewById(R.id.edit_Start_Text_Date);
        mFin = (EditText) v.findViewById(R.id.edit_End_Text_Date);
        mMatin = (CheckBox) v.findViewById(R.id.Matin_CheckBox);
        mMidi = (CheckBox) v.findViewById(R.id.Midi_CheckBox);
        mSoir = (CheckBox) v.findViewById(R.id.Soir_CheckBox);
        mContainer = (LinearLayout) v.findViewById(R.id.medoc_layout_container);
        mAddButton = (Button) v.findViewById(R.id.Ajouter_Button);
        mAddMedocButton = (Button) v.findViewById(R.id.add_medic_button);
        mDebut.setText(mPrise.getDebut());
        mFin.setText(mPrise.getFin());
        mMatin.setChecked(mPrise.isMatin());
        mMidi.setChecked(mPrise.isMidi());
        mSoir.setChecked(mPrise.isSoir());
        setClickOnAddButton(mAddButton);
        setClickOnAddMedocButton(mAddMedocButton);
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
        mMatin.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.CompoundButton compoundButton, boolean isChecked) {
                mPrise.setMatin(isChecked);
                PrisesListe.get(getContext()).updatePrise(mPrise);
            }
        });
        mMidi.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.CompoundButton compoundButton, boolean isChecked) {
                mPrise.setMidi(isChecked);
                PrisesListe.get(getContext()).updatePrise(mPrise);
            }
        });
        mSoir.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.CompoundButton compoundButton, boolean isChecked) {
                mPrise.setSoir(isChecked);
                PrisesListe.get(getContext()).updatePrise(mPrise);
            }
        });

        return v;
    }


}
