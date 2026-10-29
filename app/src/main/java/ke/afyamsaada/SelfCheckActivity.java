package ke.afyamsaada;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import ke.afyamsaada.model.Question;

public class SelfCheckActivity extends AppCompatActivity {

    private LinearLayout questionsContainer;
    private Button submitButton;
    private CardView resultsCard;
    private TextView riskLevelText;
    private TextView riskGuidanceText;
    private Button findFacilityCta;
    private Button call1192Cta;

    private final List<Question> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_check);

        questionsContainer = findViewById(R.id.container_questions);
        submitButton = findViewById(R.id.button_submit_self_check);
        resultsCard = findViewById(R.id.card_results);
        riskLevelText = findViewById(R.id.text_risk_level);
        riskGuidanceText = findViewById(R.id.text_risk_guidance);
        findFacilityCta = findViewById(R.id.button_find_facility_cta);
        call1192Cta = findViewById(R.id.button_call_1192_cta);

        initQuestions();
        renderQuestions();

        submitButton.setOnClickListener(v -> evaluateResults());

        findFacilityCta.setOnClickListener(v -> startActivity(new Intent(SelfCheckActivity.this, HelpDirectoryActivity.class)));

        call1192Cta.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:1192"));
            startActivity(intent);
        });
    }

    private void initQuestions() {
        questionList.add(new Question(1, "How often in the past 3 months have you used alcohol or other substances?", "General",
                new String[]{"Never", "Once or Twice", "Monthly", "Weekly", "Daily / Almost Daily"},
                new int[]{0, 2, 4, 6, 8}));

        questionList.add(new Question(2, "During the past 3 months, how often have you had a strong urge or desire to use?", "Urges",
                new String[]{"Never", "Once or Twice", "Monthly", "Weekly", "Daily"},
                new int[]{0, 3, 5, 7, 9}));

        questionList.add(new Question(3, "During the past 3 months, has substance use led to health, social, or financial problems?", "Impact",
                new String[]{"Never", "Once or Twice", "Monthly", "Weekly", "Daily"},
                new int[]{0, 4, 6, 8, 10}));

        questionList.add(new Question(4, "Has a relative, friend, or doctor ever expressed concern about your substance use?", "Concern",
                new String[]{"No, Never", "Yes, in the past", "Yes, recently"},
                new int[]{0, 3, 6}));
    }

    private void renderQuestions() {
        questionsContainer.removeAllViews();

        for (int i = 0; i < questionList.size(); i++) {
            final Question q = questionList.get(i);
            final int qIndex = i;

            TextView qTitle = new TextView(this);
            qTitle.setText((i + 1) + ". " + q.getText());
            qTitle.setTextSize(16);
            qTitle.setTextColor(0xFF212121);
            qTitle.setTypeface(null, android.graphics.Typeface.BOLD);
            qTitle.setPadding(0, 24, 0, 12);
            questionsContainer.addView(qTitle);

            RadioGroup radioGroup = new RadioGroup(this);
            radioGroup.setOrientation(RadioGroup.VERTICAL);

            String[] options = q.getOptions();
            for (int j = 0; j < options.length; j++) {
                RadioButton rb = new RadioButton(this);
                rb.setId(View.generateViewId());
                rb.setText(options[j]);
                rb.setTextSize(14);
                rb.setTextColor(0xFF424242);
                final int optionIndex = j;

                rb.setOnClickListener(v -> q.setSelectedOptionIndex(optionIndex));

                radioGroup.addView(rb);
            }

            questionsContainer.addView(radioGroup);
        }
    }

    private void evaluateResults() {
        int totalScore = 0;
        for (Question q : questionList) {
            totalScore += q.getSelectedScore();
        }

        resultsCard.setVisibility(View.VISIBLE);

        if (totalScore <= 3) {
            riskLevelText.setText("Risk Evaluation: Low Risk");
            riskLevelText.setTextColor(0xFF2E7D32); // Green
            riskGuidanceText.setText("Your responses indicate a low level of risk. Continue staying informed about substance health risks and maintaining safe health habits.");
        } else if (totalScore <= 12) {
            riskLevelText.setText("Risk Evaluation: Moderate Risk");
            riskLevelText.setTextColor(0xFFE65100); // Orange
            riskGuidanceText.setText("Your responses indicate moderate risk or early signs of problematic substance use. Speaking with a professional counselor or reviewing educational resources is recommended.");
        } else {
            riskLevelText.setText("Risk Evaluation: High Risk / Action Recommended");
            riskLevelText.setTextColor(0xFFC62828); // Red
            riskGuidanceText.setText("Your responses indicate a high likelihood of substance dependence or substantial impact on your health and well-being. Professional assessment and support from NACADA (1192) or a rehabilitation center is strongly advised.");
        }

        // Scroll to results
        resultsCard.getParent().requestChildFocus(resultsCard, resultsCard);
    }
}
