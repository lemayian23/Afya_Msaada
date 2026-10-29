package ke.afyamsaada.model;

import android.content.ContentValues;
import android.database.Cursor;

public class Resource {
    private long id;
    private String category;
    private String title;
    private String summary;
    private String fullContent;
    private String healthRisks;
    private String supportTips;

    public Resource() {}

    public Resource(long id, String category, String title, String summary, String fullContent, String healthRisks, String supportTips) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.summary = summary;
        this.fullContent = fullContent;
        this.healthRisks = healthRisks;
        this.supportTips = supportTips;
    }

    public static Resource fromCursor(Cursor cursor) {
        Resource r = new Resource();
        int idIdx = cursor.getColumnIndex("_id");
        int catIdx = cursor.getColumnIndex("category");
        int titleIdx = cursor.getColumnIndex("title");
        int sumIdx = cursor.getColumnIndex("summary");
        int contentIdx = cursor.getColumnIndex("full_content");
        int risksIdx = cursor.getColumnIndex("health_risks");
        int tipsIdx = cursor.getColumnIndex("support_tips");

        if (idIdx != -1) r.id = cursor.getLong(idIdx);
        if (catIdx != -1) r.category = cursor.getString(catIdx);
        if (titleIdx != -1) r.title = cursor.getString(titleIdx);
        if (sumIdx != -1) r.summary = cursor.getString(sumIdx);
        if (contentIdx != -1) r.fullContent = cursor.getString(contentIdx);
        if (risksIdx != -1) r.healthRisks = cursor.getString(risksIdx);
        if (tipsIdx != -1) r.supportTips = cursor.getString(tipsIdx);

        return r;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        if (id > 0) {
            values.put("_id", id);
        }
        values.put("category", category);
        values.put("title", title);
        values.put("summary", summary);
        values.put("full_content", fullContent);
        values.put("health_risks", healthRisks);
        values.put("support_tips", supportTips);
        return values;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getFullContent() { return fullContent; }
    public void setFullContent(String fullContent) { this.fullContent = fullContent; }

    public String getHealthRisks() { return healthRisks; }
    public void setHealthRisks(String healthRisks) { this.healthRisks = healthRisks; }

    public String getSupportTips() { return supportTips; }
    public void setSupportTips(String supportTips) { this.supportTips = supportTips; }
}
