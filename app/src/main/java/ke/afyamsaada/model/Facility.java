package ke.afyamsaada.model;

import android.content.ContentValues;
import android.database.Cursor;

public class Facility {
    private long id;
    private String name;
    private String county;
    private String location;
    private String contactPhone;
    private String services;
    private String type; // e.g., Public, Private, NGO, Rehabilitation Center
    private boolean isVerified;

    public Facility() {}

    public Facility(long id, String name, String county, String location, String contactPhone, String services, String type, boolean isVerified) {
        this.id = id;
        this.name = name;
        this.county = county;
        this.location = location;
        this.contactPhone = contactPhone;
        this.services = services;
        this.type = type;
        this.isVerified = isVerified;
    }

    public static Facility fromCursor(Cursor cursor) {
        Facility f = new Facility();
        int idIdx = cursor.getColumnIndex("_id");
        int nameIdx = cursor.getColumnIndex("name");
        int countyIdx = cursor.getColumnIndex("county");
        int locIdx = cursor.getColumnIndex("location");
        int phoneIdx = cursor.getColumnIndex("contact_phone");
        int servIdx = cursor.getColumnIndex("services");
        int typeIdx = cursor.getColumnIndex("type");
        int verIdx = cursor.getColumnIndex("is_verified");

        if (idIdx != -1) f.id = cursor.getLong(idIdx);
        if (nameIdx != -1) f.name = cursor.getString(nameIdx);
        if (countyIdx != -1) f.county = cursor.getString(countyIdx);
        if (locIdx != -1) f.location = cursor.getString(locIdx);
        if (phoneIdx != -1) f.contactPhone = cursor.getString(phoneIdx);
        if (servIdx != -1) f.services = cursor.getString(servIdx);
        if (typeIdx != -1) f.type = cursor.getString(typeIdx);
        if (verIdx != -1) f.isVerified = cursor.getInt(verIdx) == 1;

        return f;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        if (id > 0) {
            values.put("_id", id);
        }
        values.put("name", name);
        values.put("county", county);
        values.put("location", location);
        values.put("contact_phone", contactPhone);
        values.put("services", services);
        values.put("type", type);
        values.put("is_verified", isVerified ? 1 : 0);
        return values;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCounty() { return county; }
    public void setCounty(String county) { this.county = county; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getServices() { return services; }
    public void setServices(String services) { this.services = services; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { isVerified = verified; }
}
