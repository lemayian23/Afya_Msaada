package ke.afyamsaada;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ke.afyamsaada.data.SyncRepository;
import ke.afyamsaada.model.Resource;

public class ResourceDetailActivity extends AppCompatActivity {

    private SyncRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_detail);

        repository = new SyncRepository(this);

        TextView categoryText = findViewById(R.id.detail_category);
        TextView titleText = findViewById(R.id.detail_title);
        TextView fullContentText = findViewById(R.id.detail_full_content);
        TextView healthRisksText = findViewById(R.id.detail_health_risks);
        TextView supportTipsText = findViewById(R.id.detail_support_tips);

        Button findFacilityBtn = findViewById(R.id.button_find_facility);
        Button call1192Btn = findViewById(R.id.button_call_1192);

        long resourceId = getIntent().getLongExtra("RESOURCE_ID", -1);
        if (resourceId != -1) {
            Resource resource = repository.getResourceById(resourceId);
            if (resource != null) {
                categoryText.setText(resource.getCategory());
                titleText.setText(resource.getTitle());
                fullContentText.setText(resource.getFullContent());
                healthRisksText.setText(resource.getHealthRisks());
                supportTipsText.setText(resource.getSupportTips());
            }
        }

        findFacilityBtn.setOnClickListener(v -> startActivity(new Intent(ResourceDetailActivity.this, HelpDirectoryActivity.class)));

        call1192Btn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:1192"));
            startActivity(intent);
        });
    }
}
