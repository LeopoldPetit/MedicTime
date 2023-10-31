package info.b3.q1.medictime.models;

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
}
