package ke.afyamsaada;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ke.afyamsaada.data.SyncRepository;
import ke.afyamsaada.model.Resource;
import ke.afyamsaada.ui.ResourceAdapter;

public class LearnActivity extends AppCompatActivity implements ResourceAdapter.OnResourceClickListener {

    private Spinner categorySpinner;
    private RecyclerView recyclerView;
    private ResourceAdapter adapter;
    private SyncRepository repository;

    private final List<String> categories = Arrays.asList("All", "Alcohol", "Cannabis", "Prescription Opioids", "Tobacco & Khat", "Polysubstance");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        repository = new SyncRepository(this);

        categorySpinner = findViewById(R.id.spinner_category);
        recyclerView = findViewById(R.id.recycler_resources);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ResourceAdapter(this);
        recyclerView.setAdapter(adapter);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories.get(position);
                loadResources(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loadResources("All");
            }
        });

        // Pre-select category if passed in intent
        String preselectCategory = getIntent().getStringExtra("CATEGORY");
        if (preselectCategory != null) {
            int pos = categories.indexOf(preselectCategory);
            if (pos >= 0) {
                categorySpinner.setSelection(pos);
            }
        } else {
            loadResources("All");
        }
    }

    private void loadResources(String category) {
        List<Resource> resources = repository.getResourcesByCategory(category);
        adapter.setResources(resources);
    }

    @Override
    public void onResourceClick(Resource resource) {
        Intent intent = new Intent(this, ResourceDetailActivity.class);
        intent.putExtra("RESOURCE_ID", resource.getId());
        startActivity(intent);
    }
}
