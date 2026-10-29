package ke.afyamsaada.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import ke.afyamsaada.model.Facility;
import ke.afyamsaada.model.Resource;
import ke.afyamsaada.provider.AfyaMsaadaContract;

public class SyncRepository {

    private final ContentResolver contentResolver;

    public SyncRepository(Context context) {
        this.contentResolver = context.getContentResolver();
    }

    public List<Facility> getAllFacilities() {
        return getFacilitiesFiltered(null, null);
    }

    public List<Facility> getFacilitiesByCounty(String county) {
        if (county == null || county.isEmpty() || county.equalsIgnoreCase("All Counties")) {
            return getAllFacilities();
        }
        String selection = AfyaMsaadaContract.FacilityEntry.COLUMN_COUNTY + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + county + "%"};
        return getFacilitiesFiltered(selection, selectionArgs);
    }

    public Facility getFacilityById(long id) {
        Uri uri = Uri.withAppendedPath(AfyaMsaadaContract.FacilityEntry.CONTENT_URI, String.valueOf(id));
        try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                return Facility.fromCursor(cursor);
            }
        }
        return null;
    }

    private List<Facility> getFacilitiesFiltered(String selection, String[] selectionArgs) {
        List<Facility> facilities = new ArrayList<>();
        try (Cursor cursor = contentResolver.query(
                AfyaMsaadaContract.FacilityEntry.CONTENT_URI,
                null, selection, selectionArgs,
                AfyaMsaadaContract.FacilityEntry.COLUMN_NAME + " ASC")) {

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    facilities.add(Facility.fromCursor(cursor));
                }
            }
        }
        return facilities;
    }

    public List<Resource> getAllResources() {
        return getResourcesFiltered(null, null);
    }

    public List<Resource> getResourcesByCategory(String category) {
        if (category == null || category.isEmpty() || category.equalsIgnoreCase("All")) {
            return getAllResources();
        }
        String selection = AfyaMsaadaContract.ResourceEntry.COLUMN_CATEGORY + " = ?";
        String[] selectionArgs = new String[]{category};
        return getResourcesFiltered(selection, selectionArgs);
    }

    public Resource getResourceById(long id) {
        Uri uri = Uri.withAppendedPath(AfyaMsaadaContract.ResourceEntry.CONTENT_URI, String.valueOf(id));
        try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                return Resource.fromCursor(cursor);
            }
        }
        return null;
    }

    private List<Resource> getResourcesFiltered(String selection, String[] selectionArgs) {
        List<Resource> resources = new ArrayList<>();
        try (Cursor cursor = contentResolver.query(
                AfyaMsaadaContract.ResourceEntry.CONTENT_URI,
                null, selection, selectionArgs,
                AfyaMsaadaContract.ResourceEntry.COLUMN_TITLE + " ASC")) {

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    resources.add(Resource.fromCursor(cursor));
                }
            }
        }
        return resources;
    }

    public Uri addFacility(Facility facility) {
        ContentValues values = facility.toContentValues();
        return contentResolver.insert(AfyaMsaadaContract.FacilityEntry.CONTENT_URI, values);
    }

    public Uri addResource(Resource resource) {
        ContentValues values = resource.toContentValues();
        return contentResolver.insert(AfyaMsaadaContract.ResourceEntry.CONTENT_URI, values);
    }

    public boolean performBackendSyncVerification() {
        // Simulates verification pass comparing local ContentProvider data against remote schema definitions
        List<Facility> localFacilities = getAllFacilities();
        List<Resource> localResources = getAllResources();
        return !localFacilities.isEmpty() && !localResources.isEmpty();
    }
}
