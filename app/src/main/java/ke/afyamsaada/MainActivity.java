package ke.afyamsaada;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Banner Call Button
        View bannerCallBtn = findViewById(R.id.button_banner_call);
        if (bannerCallBtn != null) {
            bannerCallBtn.setOnClickListener(v -> dialPhoneNumber("1192"));
        }

        // Section Cards
        View learnClick = findViewById(R.id.layout_learn_click);
        if (learnClick != null) {
            learnClick.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LearnActivity.class)));
        }

        View selfCheckClick = findViewById(R.id.layout_self_check_click);
        if (selfCheckClick != null) {
            selfCheckClick.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SelfCheckActivity.class)));
        }

        View findHelpClick = findViewById(R.id.layout_find_help_click);
        if (findHelpClick != null) {
            findHelpClick.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HelpDirectoryActivity.class)));
        }

        View getHelpClick = findViewById(R.id.layout_get_help_click);
        if (getHelpClick != null) {
            getHelpClick.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HelplineActivity.class)));
        }

        View helpSomeoneClick = findViewById(R.id.layout_help_someone_click);
        if (helpSomeoneClick != null) {
            helpSomeoneClick.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, LearnActivity.class);
                intent.putExtra("CATEGORY", "Support Tips");
                startActivity(intent);
            });
        }
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
}
