package info.b3.q1.medictime.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    public List<String> getTousLesJoursPossibles() {
        List<String> tousLesJours = new ArrayList<>();
        Set<String> joursSet = new HashSet<>();
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        List<Prise> allPrises = getPrises();

        for (Prise prise : allPrises) {
            LocalDate dateDebut = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dateDebut = LocalDate.parse(prise.getDebut(), formatter);
            }
            LocalDate dateFin = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dateFin = LocalDate.parse(prise.getFin(), formatter);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                while (!dateDebut.isAfter(dateFin)) {
                    String jour = dateDebut.format(formatter);

                    if (!joursSet.contains(jour)) {
                        tousLesJours.add(jour);
                        joursSet.add(jour);
                    }
                    dateDebut = dateDebut.plusDays(1);
                }
            }
        }

        return tousLesJours;
    }
    public List<Prise> getPrisesByJour(String jour) {
        List<Prise> prisesDuJour = new ArrayList<>();
        List<UUID> prisesDejaAjoutees = new ArrayList<>();

        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        try {
            LocalDate dateToFilter = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dateToFilter = LocalDate.parse(jour, formatter);
            }

            List<Prise> allPrises = getPrises();

            for (Prise prise : allPrises) {
                LocalDate debut = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    debut = LocalDate.parse(prise.getDebut(), formatter);
                }
                LocalDate fin = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    fin = LocalDate.parse(prise.getFin(), formatter);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (!prisesDejaAjoutees.contains(prise.getId()) && !dateToFilter.isBefore(debut) && !dateToFilter.isAfter(fin)) {
                        prisesDuJour.add(prise);
                        prisesDejaAjoutees.add(prise.getId());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prisesDuJour;
    }
    public void deleteAllPrises() {
        mDatabase.delete(MedicTimeDbSchema.PriseTable.NAME, null, null);
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
