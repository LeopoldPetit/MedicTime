package info.b3.q1.medictime.models;

import java.util.UUID;

public class Medicament {
    protected java.util.UUID mId;
    protected String mName;
    protected Integer mDuree;

    protected Boolean mMatin;
    protected Boolean mMidi;
    protected Boolean mSoir;

    public Medicament() {
        this(UUID.randomUUID());
        this.mName = "Nom par Défaut";
        this.mDuree=10;
        this.mMatin = false;
        this.mMidi = false;
        this.mSoir = false;

    }
    public Medicament(UUID id) {
        mId = id;
    }
    public UUID getId() {
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

    public Boolean getMatin() {
        return mMatin;
    }

    public void setMatin(Boolean matin) {
        mMatin = matin;
    }

    public Boolean getMidi() {
        return mMidi;
    }

    public void setMidi(Boolean midi) {
        mMidi = midi;
    }

    public Boolean getSoir() {
        return mSoir;
    }

    public void setSoir(Boolean soir) {
        mSoir = soir;
    }
}
