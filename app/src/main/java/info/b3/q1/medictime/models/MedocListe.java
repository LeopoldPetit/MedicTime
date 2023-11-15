package info.b3.q1.medictime.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import info.b3.q1.medictime.db.MedicTimeBaseHelper;
import info.b3.q1.medictime.db.MedicTimeCursorWrapper;
import info.b3.q1.medictime.db.MedicTimeDbSchema;

public class MedocListe {
    private static MedocListe sMedocListe;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public MedocListe(Context context) {
        mContext = context;
        mDatabase = new MedicTimeBaseHelper(mContext).getWritableDatabase();
    }

    public static MedocListe get(Context context) {
        if (sMedocListe == null) {
            sMedocListe = new MedocListe(context);
        }
        return sMedocListe;
    }
    public void addMedoc(Medicament medoc) {
        mDatabase.insert(MedicTimeDbSchema.MedicamentTable.NAME, null, getContentValues(medoc));
    }
    public void updateMedoc(Medicament medoc) {
        String uuidString = medoc.getId().toString();
        mDatabase.update(MedicTimeDbSchema.MedicamentTable.NAME,
                getContentValues(medoc),
                MedicTimeDbSchema.MedicamentTable.cols.UUID + " = ?",
                new String[]{uuidString});
    }

    public void deleteAllMedocs() {
        mDatabase.delete(MedicTimeDbSchema.MedicamentTable.NAME, null, null);
    }

    public boolean doesIdAvailable(int idToCheck) {
        List<Medicament> medocs = getMedocs();

        for (Medicament medoc : medocs) {
            if (medoc.getId() == idToCheck) {
                return false;
            }
        }
        return true;
    }
    public String getMedocName(Integer id) {
        MedicTimeCursorWrapper cursor =
                queryMedocs(MedicTimeDbSchema.MedicamentTable.cols.UUID + " = ? ",
                        new String[]{id.toString()}
                );
        try {
            if (cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getMedicament().getName();
        } finally {
            cursor.close();
        }
    }
    public List<Medicament> getMedocs() {
        ArrayList<Medicament> medocs = new ArrayList<>();
        MedicTimeCursorWrapper cursor = queryMedocs(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                medocs.add(cursor.getMedicament());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return medocs;
    }
    private ContentValues getContentValues(Medicament medoc) {
        ContentValues values = new ContentValues();
        values.put(MedicTimeDbSchema.MedicamentTable.cols.UUID, medoc.getId().toString());
        values.put(MedicTimeDbSchema.MedicamentTable.cols.NOM, medoc.getName());
        values.put(MedicTimeDbSchema.MedicamentTable.cols.DUREE, medoc.getDuree());
        values.put(MedicTimeDbSchema.MedicamentTable.cols.MATIN, medoc.isMatin());
        values.put(MedicTimeDbSchema.MedicamentTable.cols.MIDI, medoc.isMidi());
        values.put(MedicTimeDbSchema.MedicamentTable.cols.SOIR, medoc.isSoir());
        return values;
    }
    private MedicTimeCursorWrapper queryMedocs(String whereClause, String[] whereArgs) {
        android.database.Cursor cursor = mDatabase.query(
                MedicTimeDbSchema.MedicamentTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new MedicTimeCursorWrapper(cursor);
    }
}
