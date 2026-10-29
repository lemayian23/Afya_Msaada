package ke.afyamsaada.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "afyamsaada.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FACILITIES_TABLE = "CREATE TABLE " + AfyaMsaadaContract.FacilityEntry.TABLE_NAME + " (" +
                AfyaMsaadaContract.FacilityEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AfyaMsaadaContract.FacilityEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                AfyaMsaadaContract.FacilityEntry.COLUMN_COUNTY + " TEXT NOT NULL, " +
                AfyaMsaadaContract.FacilityEntry.COLUMN_LOCATION + " TEXT NOT NULL, " +
                AfyaMsaadaContract.FacilityEntry.COLUMN_CONTACT_PHONE + " TEXT NOT NULL, " +
                AfyaMsaadaContract.FacilityEntry.COLUMN_SERVICES + " TEXT NOT NULL, " +
                AfyaMsaadaContract.FacilityEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                AfyaMsaadaContract.FacilityEntry.COLUMN_IS_VERIFIED + " INTEGER DEFAULT 1);";

        String CREATE_RESOURCES_TABLE = "CREATE TABLE " + AfyaMsaadaContract.ResourceEntry.TABLE_NAME + " (" +
                AfyaMsaadaContract.ResourceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AfyaMsaadaContract.ResourceEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                AfyaMsaadaContract.ResourceEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                AfyaMsaadaContract.ResourceEntry.COLUMN_SUMMARY + " TEXT NOT NULL, " +
                AfyaMsaadaContract.ResourceEntry.COLUMN_FULL_CONTENT + " TEXT NOT NULL, " +
                AfyaMsaadaContract.ResourceEntry.COLUMN_HEALTH_RISKS + " TEXT NOT NULL, " +
                AfyaMsaadaContract.ResourceEntry.COLUMN_SUPPORT_TIPS + " TEXT NOT NULL);";

        db.execSQL(CREATE_FACILITIES_TABLE);
        db.execSQL(CREATE_RESOURCES_TABLE);

        seedData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AfyaMsaadaContract.FacilityEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AfyaMsaadaContract.ResourceEntry.TABLE_NAME);
        onCreate(db);
    }

    private void seedData(SQLiteDatabase db) {
        // Seed Facilities
        insertFacility(db, "NACADA 1192 National Toll-Free Helpline", "Nairobi", "NBI HQ / Nationwide", "1192", "24/7 Telephone Counseling, Emergency Intervention & Referrals", "Public Helpline", 1);
        insertFacility(db, "Mathari National Teaching & Referral Hospital", "Nairobi", "Thika Road, Muthaiga, Nairobi", "0202337694", "Inpatient Detoxification, Psychiatric Care & Rehabilitation", "Public Hospital", 1);
        insertFacility(db, "Coast General Teaching & Referral Hospital Rehab Unit", "Mombasa", "Kisauni, Mombasa", "0722207868", "Outpatient Counseling, Methadone Maintenance & Addiction Care", "Public Hospital", 1);
        insertFacility(db, "Asumbi Treatment Centre", "Homabay", "Asumbi, Homa Bay County", "0720448833", "Residential Rehabilitation, Group Therapy & Aftercare", "NGO / Faith-Based", 1);
        insertFacility(db, "Mirema Service Center (NACADA)", "Nairobi", "Mirema Drive, Kasarani", "1192", "Outpatient Assessment & Family Support Services", "Public Agency", 1);
        insertFacility(db, "Nakuru Level 5 Hospital Addiction Unit", "Nakuru", "Showground Road, Nakuru", "0512215121", "Medical Detoxification & Mental Health Support", "Public Hospital", 1);

        // Seed Resources
        insertResource(db, "Alcohol", "Understanding Alcohol Dependence & Recovery",
                "Alcohol is a widely used depressant that affects the central nervous system.",
                "Frequent heavy drinking can lead to physical dependence, liver complications, memory impairment, and severe withdrawal risks. Professional support and structured therapy facilitate effective recovery.",
                "Liver disease, cardiovascular issues, cognitive decline, elevated risk of accidents and mental health disorders.",
                "Seek medical supervision for withdrawal, join support groups, stay hydrated, and replace drinking triggers with healthy routines.");

        insertResource(db, "Cannabis", "Cannabis Use, Health Impact & Harm Reduction",
                "Cannabis contains THC which alters mood, perception, and cognitive focus.",
                "Regular high-potency cannabis use can trigger anxiety, paranoia, short-term memory deficits, and motivation changes. Stopping or reducing use gradually restores clarity and mental focus.",
                "Impaired short-term memory, respiratory issues from smoking, risk of triggering anxiety or psychotic episodes in vulnerable individuals.",
                "Set clear limits, avoid mixing with alcohol or tobacco, practice mindfulness, and consult a professional if anxiety occurs.");

        insertResource(db, "Prescription Opioids", "Prescription Painkiller Safety & Opioid Risk",
                "Opioids alter pain perception but carry a high risk of tolerance and dependence.",
                "Using painkillers longer than prescribed or in larger doses can quickly build physical dependence. Overdose risks are high when combined with alcohol or sedatives.",
                "Respiratory depression, severe constipation, risk of fatal overdose, physical tolerance.",
                "Never share prescription medication, follow medical advice closely, and seek immediate help if misuse or craving starts.");

        insertResource(db, "Tobacco & Khat", "Tobacco & Miraa (Khat) Health Awareness",
                "Tobacco contains addictive nicotine, while Khat contains cathinone stimulants.",
                "Habitual use leads to dependency, dental problems, sleep disturbances, and elevated cardiovascular risks. Quitting significantly improves health within weeks.",
                "Cardiovascular disease, oral cancers, sleep disturbances, gum disease, and chronic anxiety.",
                "Identify usage triggers, try nicotine replacement therapies, chew sugar-free gum, and seek peer support.");

        insertResource(db, "Polysubstance", "Polysubstance Use & High-Risk Combinations",
                "Combining multiple substances significantly increases health risks and overdose likelihood.",
                "Mixing alcohol with sedatives or opioids can cause life-threatening respiratory failure. Combining stimulants with depressants strains the cardiovascular system unpredictably.",
                "Unpredictable toxicity, severe heart strain, organ damage, and extremely high risk of accidental overdose.",
                "Avoid mixing substances, keep emergency contacts handy, educate loved ones on overdose signs, and reach out to 1192.");
    }

    private void insertFacility(SQLiteDatabase db, String name, String county, String location, String phone, String services, String type, int isVerified) {
        ContentValues values = new ContentValues();
        values.put(AfyaMsaadaContract.FacilityEntry.COLUMN_NAME, name);
        values.put(AfyaMsaadaContract.FacilityEntry.COLUMN_COUNTY, county);
        values.put(AfyaMsaadaContract.FacilityEntry.COLUMN_LOCATION, location);
        values.put(AfyaMsaadaContract.FacilityEntry.COLUMN_CONTACT_PHONE, phone);
        values.put(AfyaMsaadaContract.FacilityEntry.COLUMN_SERVICES, services);
        values.put(AfyaMsaadaContract.FacilityEntry.COLUMN_TYPE, type);
        values.put(AfyaMsaadaContract.FacilityEntry.COLUMN_IS_VERIFIED, isVerified);
        db.insert(AfyaMsaadaContract.FacilityEntry.TABLE_NAME, null, values);
    }

    private void insertResource(SQLiteDatabase db, String category, String title, String summary, String fullContent, String healthRisks, String supportTips) {
        ContentValues values = new ContentValues();
        values.put(AfyaMsaadaContract.ResourceEntry.COLUMN_CATEGORY, category);
        values.put(AfyaMsaadaContract.ResourceEntry.COLUMN_TITLE, title);
        values.put(AfyaMsaadaContract.ResourceEntry.COLUMN_SUMMARY, summary);
        values.put(AfyaMsaadaContract.ResourceEntry.COLUMN_FULL_CONTENT, fullContent);
        values.put(AfyaMsaadaContract.ResourceEntry.COLUMN_HEALTH_RISKS, healthRisks);
        values.put(AfyaMsaadaContract.ResourceEntry.COLUMN_SUPPORT_TIPS, supportTips);
        db.insert(AfyaMsaadaContract.ResourceEntry.TABLE_NAME, null, values);
    }
}
