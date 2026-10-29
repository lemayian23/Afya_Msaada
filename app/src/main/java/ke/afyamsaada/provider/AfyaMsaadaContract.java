package ke.afyamsaada.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class AfyaMsaadaContract {

    public static final String AUTHORITY = "ke.afyamsaada.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FACILITIES = "facilities";
    public static final String PATH_RESOURCES = "resources";

    public static final class FacilityEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FACILITIES).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + AUTHORITY + "." + PATH_FACILITIES;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + AUTHORITY + "." + PATH_FACILITIES;

        public static final String TABLE_NAME = "facilities";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_COUNTY = "county";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_CONTACT_PHONE = "contact_phone";
        public static final String COLUMN_SERVICES = "services";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_IS_VERIFIED = "is_verified";
    }

    public static final class ResourceEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESOURCES).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + AUTHORITY + "." + PATH_RESOURCES;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + AUTHORITY + "." + PATH_RESOURCES;

        public static final String TABLE_NAME = "resources";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_FULL_CONTENT = "full_content";
        public static final String COLUMN_HEALTH_RISKS = "health_risks";
        public static final String COLUMN_SUPPORT_TIPS = "support_tips";
    }
}
