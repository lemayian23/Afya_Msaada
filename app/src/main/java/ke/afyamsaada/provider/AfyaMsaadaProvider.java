package ke.afyamsaada.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AfyaMsaadaProvider extends ContentProvider {

    private static final int FACILITIES = 100;
    private static final int FACILITIES_ID = 101;
    private static final int RESOURCES = 200;
    private static final int RESOURCES_ID = 201;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AfyaMsaadaContract.AUTHORITY, AfyaMsaadaContract.PATH_FACILITIES, FACILITIES);
        uriMatcher.addURI(AfyaMsaadaContract.AUTHORITY, AfyaMsaadaContract.PATH_FACILITIES + "/#", FACILITIES_ID);
        uriMatcher.addURI(AfyaMsaadaContract.AUTHORITY, AfyaMsaadaContract.PATH_RESOURCES, RESOURCES);
        uriMatcher.addURI(AfyaMsaadaContract.AUTHORITY, AfyaMsaadaContract.PATH_RESOURCES + "/#", RESOURCES_ID);
    }

    private DatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;

        int match = uriMatcher.match(uri);
        switch (match) {
            case FACILITIES:
                cursor = db.query(AfyaMsaadaContract.FacilityEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FACILITIES_ID:
                selection = AfyaMsaadaContract.FacilityEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(AfyaMsaadaContract.FacilityEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case RESOURCES:
                cursor = db.query(AfyaMsaadaContract.ResourceEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case RESOURCES_ID:
                selection = AfyaMsaadaContract.ResourceEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(AfyaMsaadaContract.ResourceEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case FACILITIES:
                return AfyaMsaadaContract.FacilityEntry.CONTENT_TYPE;
            case FACILITIES_ID:
                return AfyaMsaadaContract.FacilityEntry.CONTENT_ITEM_TYPE;
            case RESOURCES:
                return AfyaMsaadaContract.ResourceEntry.CONTENT_TYPE;
            case RESOURCES_ID:
                return AfyaMsaadaContract.ResourceEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id;
        Uri returnUri;

        switch (uriMatcher.match(uri)) {
            case FACILITIES:
                id = db.insert(AfyaMsaadaContract.FacilityEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(AfyaMsaadaContract.FacilityEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case RESOURCES:
                id = db.insert(AfyaMsaadaContract.ResourceEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(AfyaMsaadaContract.ResourceEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted;

        switch (uriMatcher.match(uri)) {
            case FACILITIES:
                rowsDeleted = db.delete(AfyaMsaadaContract.FacilityEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FACILITIES_ID:
                selection = AfyaMsaadaContract.FacilityEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(AfyaMsaadaContract.FacilityEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case RESOURCES:
                rowsDeleted = db.delete(AfyaMsaadaContract.ResourceEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case RESOURCES_ID:
                selection = AfyaMsaadaContract.ResourceEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(AfyaMsaadaContract.ResourceEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (rowsDeleted > 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsUpdated;

        switch (uriMatcher.match(uri)) {
            case FACILITIES:
                rowsUpdated = db.update(AfyaMsaadaContract.FacilityEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case FACILITIES_ID:
                selection = AfyaMsaadaContract.FacilityEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = db.update(AfyaMsaadaContract.FacilityEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case RESOURCES:
                rowsUpdated = db.update(AfyaMsaadaContract.ResourceEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case RESOURCES_ID:
                selection = AfyaMsaadaContract.ResourceEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = db.update(AfyaMsaadaContract.ResourceEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        if (rowsUpdated > 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
