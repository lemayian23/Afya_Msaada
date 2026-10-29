package ke.afyamsaada.model;

public class Question {
    private int id;
    private String text;
    private String category; // e.g. Alcohol, Cannabis, General
    private String[] options;
    private int[] optionScores;
    private int selectedOptionIndex = -1;

    public Question(int id, String text, String category, String[] options, int[] optionScores) {
        this.id = id;
        this.text = text;
        this.category = category;
        this.options = options;
        this.optionScores = optionScores;
    }

    public int getId() { return id; }
    public String getText() { return text; }
    public String getCategory() { return category; }
    public String[] getOptions() { return options; }
    public int[] getOptionScores() { return optionScores; }

    public int getSelectedOptionIndex() { return selectedOptionIndex; }
    public void setSelectedOptionIndex(int index) { this.selectedOptionIndex = index; }

    public int getSelectedScore() {
        if (selectedOptionIndex >= 0 && selectedOptionIndex < optionScores.length) {
            return optionScores[selectedOptionIndex];
        }
        return 0;
    }
}
