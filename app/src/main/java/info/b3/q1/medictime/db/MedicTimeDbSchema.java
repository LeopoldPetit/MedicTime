package info.b3.q1.medictime.db;

public abstract class MedicTimeDbSchema {
    public static final class MedicamentTable {
        public static final String NAME = "medicaments";
        public static final class cols{
            public static final String UUID="uuid";
            public static final String NOM="nom";
            public static final String DUREE="durée";
            public static final String MATIN="matin";
            public static final String MIDI="midi";
            public static final String SOIR="soir";


        }
    }
    public static final class PriseTable {
        public static final String NAME = "Prises";
        public static final class cols{
            public static final String UUID="uuid";
            public static final String MEDOC_ID="medocId";
            public static final String DEBUT="debut";
            public static final String FIN="fin";
            public static final String MATIN="matin";
            public static final String MIDI="midi";
            public static final String SOIR="soir";


        }
    }
}
