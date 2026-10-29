package ke.afyamsaada;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import ke.afyamsaada.data.SyncRepository;
import ke.afyamsaada.model.Facility;
import ke.afyamsaada.ui.FacilityAdapter;

public class HelpDirectoryActivity extends AppCompatActivity implements FacilityAdapter.OnFacilityClickListener {

    private Spinner countySpinner;
    private RecyclerView recyclerView;
    private FacilityAdapter adapter;
    private SyncRepository repository;

    private final List<String> counties = Arrays.asList(
            "All Counties", "Nairobi", "Mombasa", "Nakuru", "Homabay", "Kisumu", "Kiambu", "Uasin Gishu"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_directory);

        repository = new SyncRepository(this);

        countySpinner = findViewById(R.id.spinner_county);
        recyclerView = findViewById(R.id.recycler_facilities);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FacilityAdapter(this);
        recyclerView.setAdapter(adapter);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, counties);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countySpinner.setAdapter(spinnerAdapter);

        countySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCounty = counties.get(position);
                loadFacilities(selectedCounty);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loadFacilities("All Counties");
            }
        });
    }

    private void loadFacilities(String county) {
        List<Facility> facilities = repository.getFacilitiesByCounty(county);
        adapter.setFacilities(facilities);
    }

    @Override
    public void onFacilityClick(Facility facility) {
        Intent intent = new Intent(this, FacilityDetailActivity.class);
        intent.putExtra("FACILITY_ID", facility.getId());
        startActivity(intent);
    }

    @Override
    public void onCallClick(Facility facility) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + facility.getContactPhone()));
        startActivity(intent);
    }
}
