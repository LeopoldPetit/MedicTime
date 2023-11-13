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
    public List<String> getJoursAvecPrises() {
        Set<String> joursUniques = new HashSet<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Récupérer toutes les prises de la base de données
        List<Prise> allPrises = getPrises();

        // Itérer sur les prises pour obtenir les jours contenant des prises
        for (Prise prise : allPrises) {
            try {
                Date debut = dateFormat.parse(prise.getDebut());
                joursUniques.add(dateFormat.format(debut));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ArrayList<>(joursUniques);
    }
    public List<String> getTousLesJoursPossibles() {
        List<String> tousLesJours = new ArrayList<>();
        Set<String> joursSet = new HashSet<>();

        // Utiliser DateTimeFormatter pour le format "dd/MM/yyyy"
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        }

        List<Prise> allPrises = getPrises();

        // Parcourir toutes les prises
        for (Prise prise : allPrises) {
            // Convertir les chaînes de dates en objets LocalDate
            LocalDate dateDebut = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dateDebut = LocalDate.parse(prise.getDebut(), formatter);
            }
            LocalDate dateFin = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dateFin = LocalDate.parse(prise.getFin(), formatter);
            }

            // Ajouter tous les jours entre dateDebut et dateFin à la liste
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                while (!dateDebut.isAfter(dateFin)) {
                    String jour = dateDebut.format(formatter);

                    // Vérifier s'il y a déjà un jour équivalent dans l'ensemble
                    if (!joursSet.contains(jour)) {
                        tousLesJours.add(jour);
                        joursSet.add(jour); // Ajouter au set pour le suivi des doublons
                    }

                    dateDebut = dateDebut.plusDays(1); // Ajouter un jour
                }
            }
        }

        return tousLesJours;
    }
    public List<String> getJoursAvecPrisesTries() {
        List<String> joursTries = getJoursAvecPrises();

        // Triez la liste des jours
        Collections.sort(joursTries, new Comparator<String>() {
            @Override
            public int compare(String jour1, String jour2) {
                return jour1.compareTo(jour2);
            }
        });

        return joursTries;
    }
    public List<Prise> getPrisesByJour(String jour) {
        List<Prise> prisesDuJour = new ArrayList<>();
        List<UUID> prisesDejaAjoutees = new ArrayList<>(); // Utiliser une liste pour suivre les prises déjà ajoutées

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

            // Filtrer les prises en fonction du jour
            for (Prise prise : allPrises) {
                LocalDate debut = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    debut = LocalDate.parse(prise.getDebut(), formatter);
                }
                LocalDate fin = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    fin = LocalDate.parse(prise.getFin(), formatter);
                }

                // Vérifier si la prise a déjà été ajoutée en utilisant des critères spécifiques (par exemple, ID)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (!prisesDejaAjoutees.contains(prise.getId()) && !dateToFilter.isBefore(debut) && !dateToFilter.isAfter(fin)) {
                        prisesDuJour.add(prise);
                        prisesDejaAjoutees.add(prise.getId()); // Ajouter l'ID de la prise à la liste temporaire
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

    public  List<Prise> create10DifferentPrises() {


        // Date de début initiale
        String startDateString = "01/01/2023";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(startDateString));

            // Créer 10 prises avec des dates différentes
            for (int i = 0; i < 10; i++) {
                Prise prise = new Prise();
                Date debutDate = dateFormat.parse(dateFormat.format(calendar.getTime()));
                Date finDate = dateFormat.parse(dateFormat.format(calendar.getTime()));

                prise.setDebut(dateFormat.format(debutDate));
                prise.setFin(dateFormat.format(finDate));
                prise.setMedocId(i);
                prise.setMatin(true);
                prise.setMidi(true);
                prise.setSoir(true);
                addPrise(prise);

                // Ajouter un jour pour la prochaine prise
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public void createOnePrise(){
        Prise prise = new Prise();
        prise.setDebut("07/02/2023");
        prise.setFin("11/02/2023");
        prise.setMedocId(6);
        prise.setMatin(true);
        prise.setMidi(true);
        prise.setSoir(true);
        addPrise(prise);
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
