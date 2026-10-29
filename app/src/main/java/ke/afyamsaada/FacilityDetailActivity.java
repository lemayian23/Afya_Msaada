package ke.afyamsaada;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ke.afyamsaada.data.SyncRepository;
import ke.afyamsaada.model.Facility;

public class FacilityDetailActivity extends AppCompatActivity {

    private SyncRepository repository;
    private Facility currentFacility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_detail);

        repository = new SyncRepository(this);

        TextView typeText = findViewById(R.id.facility_type);
        TextView nameText = findViewById(R.id.facility_name);
        TextView countyText = findViewById(R.id.facility_county);
        TextView locationText = findViewById(R.id.facility_location);
        TextView servicesText = findViewById(R.id.facility_services);

        Button callFacilityBtn = findViewById(R.id.button_call_facility);
        Button callHelplineAltBtn = findViewById(R.id.button_call_helpline_alt);

        long facilityId = getIntent().getLongExtra("FACILITY_ID", -1);
        if (facilityId != -1) {
            currentFacility = repository.getFacilityById(facilityId);
            if (currentFacility != null) {
                typeText.setText(currentFacility.getType() + (currentFacility.isVerified() ? " • Verified" : ""));
                nameText.setText(currentFacility.getName());
                countyText.setText("County: " + currentFacility.getCounty());
                locationText.setText("Location: " + currentFacility.getLocation());
                servicesText.setText(currentFacility.getServices());
            }
        }

        callFacilityBtn.setOnClickListener(v -> {
            if (currentFacility != null && currentFacility.getContactPhone() != null) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + currentFacility.getContactPhone()));
                startActivity(intent);
            }
        });

        callHelplineAltBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:1192"));
            startActivity(intent);
        });
    }
}
