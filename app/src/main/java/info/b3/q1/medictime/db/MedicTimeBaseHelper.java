package info.b3.q1.medictime.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import info.b3.q1.medictime.db.MedicTimeDbSchema.*;
public class MedicTimeBaseHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "database.db";
    public MedicTimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ MedicamentTable.NAME + "("
                + "_id integer PRIMARY KEY AUTOINCREMENT, "
                + MedicamentTable.cols.UUID + ", "
                + MedicamentTable.cols.DUREE + ", "
                + MedicamentTable.cols.NOM + ", "
                + MedicamentTable.cols.MATIN + ", "
                + MedicamentTable.cols.MIDI + ", "
                + MedicamentTable.cols.SOIR + ")"
        );
        db.execSQL("CREATE TABLE " + PriseTable.NAME + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PriseTable.cols.UUID + " , "
                + PriseTable.cols.MEDOC_ID + " , "  // Clé étrangère faisant référence à l'ID de Medicament
                + PriseTable.cols.DEBUT + " , "
                + PriseTable.cols.FIN + " , "
                + PriseTable.cols.MATIN + " , "
                + PriseTable.cols.MIDI + " , "
                + PriseTable.cols.SOIR + " , "
                + "FOREIGN KEY(" + PriseTable.cols.MEDOC_ID + ") REFERENCES "
                + MedicamentTable.NAME + "(" + MedicamentTable.cols.UUID + "))"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
    }

}
