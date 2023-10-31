package info.b3.q1.medictime.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import info.b3.q1.medictime.db.MedicTimeBaseHelper;
import info.b3.q1.medictime.db.MedicTimeCursorWrapper;
import info.b3.q1.medictime.db.MedicTimeDbSchema;

public class PrisesListe {
    private static PrisesListe sPrisesListe;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static PrisesListe get(Context context) {
        if (sPrisesListe == null) {
            sPrisesListe = new PrisesListe(context);
        }
        return sPrisesListe;
    }

    private PrisesListe(Context context) {
        mContext = context;
        mDatabase = new MedicTimeBaseHelper(mContext).getWritableDatabase();
    }
    public void addPrise(Prise prise) {
        mDatabase.insert(MedicTimeDbSchema.PriseTable.NAME, null, getContentValues(prise));
    }
    public void updatePrise(Prise prise) {
        String uuidString = prise.getId().toString();
        ContentValues values = getContentValues(prise);
        mDatabase.update(MedicTimeDbSchema.PriseTable.NAME,
                values,
                MedicTimeDbSchema.PriseTable.cols.UUID + " = ?",
                new String[]{uuidString});
    }
    public Prise getPrise(UUID id) {
        MedicTimeCursorWrapper cursor =
                queryPrises(MedicTimeDbSchema.PriseTable.cols.UUID + " = ? ",
                        new String[]{String.valueOf(id)}
                );
        try {
            if (cursor.getCount() == 0)
                return null;
            cursor.moveToFirst();
            return cursor.getPrise();
        } finally {
            cursor.close();
        }
    }
    public List<Prise> getPrises() {
        ArrayList<Prise> prises = new ArrayList<>();
        MedicTimeCursorWrapper cursor = queryPrises(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                prises.add(cursor.getPrise());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return prises;
    }

    private ContentValues getContentValues(Prise prise) {
        ContentValues values = new ContentValues();
        values.put(MedicTimeDbSchema.PriseTable.cols.UUID, prise.getId().toString());
        values.put(MedicTimeDbSchema.PriseTable.cols.MEDOC_ID, prise.getMedocId());
        values.put(MedicTimeDbSchema.PriseTable.cols.DEBUT, prise.getDebut());
        values.put(MedicTimeDbSchema.PriseTable.cols.FIN, prise.getFin());
        values.put(MedicTimeDbSchema.PriseTable.cols.MATIN, prise.isMatin());
        values.put(MedicTimeDbSchema.PriseTable.cols.MIDI, prise.isMidi());
        values.put(MedicTimeDbSchema.PriseTable.cols.SOIR, prise.isSoir());
        return values;
    }
    private MedicTimeCursorWrapper queryPrises(String whereClause, String[] whereArgs) {
        android.database.Cursor cursor = mDatabase.query(
                MedicTimeDbSchema.PriseTable.NAME,
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
