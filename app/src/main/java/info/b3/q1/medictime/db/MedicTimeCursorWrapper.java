package info.b3.q1.medictime.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import info.b3.q1.medictime.models.Medicament;
import info.b3.q1.medictime.models.Prise;

public class MedicTimeCursorWrapper extends CursorWrapper {

    public MedicTimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Medicament getMedicament(){
        Integer id =
                Integer.valueOf(getString(getColumnIndex(MedicTimeDbSchema.MedicamentTable.cols.UUID)));
        String name =
                getString(getColumnIndex(MedicTimeDbSchema.MedicamentTable.cols.NOM));
        Integer duree =
                getInt(getColumnIndex(MedicTimeDbSchema.MedicamentTable.cols.DUREE));
        int isMatin =
                getInt(getColumnIndex(MedicTimeDbSchema.MedicamentTable.cols.MATIN));
        int isMidi =
                getInt(getColumnIndex(MedicTimeDbSchema.MedicamentTable.cols.MIDI));
        int isSoir =
                getInt(getColumnIndex(MedicTimeDbSchema.MedicamentTable.cols.SOIR));
        Medicament medicament = new Medicament(id);
        medicament.setName(name);
        medicament.setDuree(duree);
        medicament.setMatin(isMatin!=0);
        medicament.setMidi(isMidi!=0);
        medicament.setSoir(isSoir!=0);
        return medicament;
    }
    public Prise getPrise(){
        String uuidString =
                getString(getColumnIndex(MedicTimeDbSchema.PriseTable.cols.UUID));
        Integer medoc_id =
                getInt(getColumnIndex(MedicTimeDbSchema.PriseTable.cols.MEDOC_ID));
        String debut =
                getString(getColumnIndex(MedicTimeDbSchema.PriseTable.cols.DEBUT));
        String fin =
                getString(getColumnIndex(MedicTimeDbSchema.PriseTable.cols.FIN));
        int isMatin =
                getInt(getColumnIndex(MedicTimeDbSchema.PriseTable.cols.MATIN));
        int isMidi =
                getInt(getColumnIndex(MedicTimeDbSchema.PriseTable.cols.MIDI));
        int isSoir =
                getInt(getColumnIndex(MedicTimeDbSchema.PriseTable.cols.SOIR));
        Prise prise = new Prise(UUID.fromString(uuidString));
        prise.setMedocId(medoc_id);
        prise.setDebut(debut);
        prise.setFin(fin);
        prise.setMatin(isMatin!=0);
        prise.setMidi(isMidi!=0);
        prise.setSoir(isSoir!=0);
        return prise;
    }

}
