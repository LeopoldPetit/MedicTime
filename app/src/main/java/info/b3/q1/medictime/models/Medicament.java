package info.b3.q1.medictime.models;

import java.util.UUID;

public class Medicament {
    protected Integer mId;
    public String mName;
    public Integer mDuree;

    public Boolean mMatin;
    public Boolean mMidi;
    public Boolean mSoir;

    public Medicament() {
        this.mId = 0;
        this.mName = "Nom par Défaut";
        this.mDuree=10;
        this.mMatin = false;
        this.mMidi = false;
        this.mSoir = false;

    }
    public Medicament(Integer id) {
        mId = id;
    }
    public Integer getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Integer getDuree() {
        return mDuree;
    }

    public void setDuree(Integer duree) {
        mDuree = duree;
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

    public void setId(int i) {
        mId = i;
    }
}
