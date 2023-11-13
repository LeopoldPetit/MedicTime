package info.b3.q1.medictime.models;

import android.os.Build;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Prise {
    protected java.util.UUID mId;
    protected Integer mMedocId;
    protected String mDebut;
    protected String mFin;
    protected Boolean mMatin;
    protected Boolean mMidi;
    protected Boolean mSoir;
    public Prise() {
        this(UUID.randomUUID());
        this.mMedocId = 0;
        this.mDebut="20/10/23";
        this.mFin="30/10/23";
        this.mMatin = false;
        this.mMidi = false;
        this.mSoir = false;

    }
    public Prise(UUID id) {
        mId = id;
    }
    public UUID getId() {
        return mId;
    }

    public Integer getMedocId() {
        return mMedocId;
    }

    public void setMedocId(Integer medocId) {
        mMedocId = medocId;
    }

    public String getDebut() {
        return mDebut;
    }

    public void setDebut(String debut) {
        mDebut = debut;
    }

    public String getFin() {
        return mFin;
    }

    public void setFin(String fin) {
        mFin = fin;
    }

    public Boolean isMatin() {
        return mMatin;
    }

    public void setMatin(Boolean matin) {
        mMatin = matin;
    }

    public Boolean isMidi() {
        return mMidi;
    }

    public void setMidi(Boolean midi) {
        mMidi = midi;
    }

    public Boolean isSoir() {
        return mSoir;
    }

    public void setSoir(Boolean soir) {
        mSoir = soir;
    }
    public List<String> getTousLesJours() {
        List<String> jours = new ArrayList<>();

        // Utiliser DateTimeFormatter pour le format "yy/MM/dd"
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
        }

        // Convertir les chaînes de dates en objets LocalDate
        LocalDate dateDebut = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateDebut = LocalDate.parse(mDebut, formatter);
        }
        LocalDate dateFin = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateFin = LocalDate.parse(mFin, formatter);
        }

        // Ajouter tous les jours entre dateDebut et dateFin à la liste
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            while (!dateDebut.isAfter(dateFin)) {
                jours.add(dateDebut.format(formatter));
                dateDebut = dateDebut.plusDays(1); // Ajouter un jour
            }
        }

        return jours;
    }
}
