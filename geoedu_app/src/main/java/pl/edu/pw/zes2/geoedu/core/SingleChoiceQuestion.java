package pl.edu.pw.zes2.geoedu.core;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingleChoiceQuestion extends Question {
    private List<String> answers;
    private String correctAnswer;
    
    public SingleChoiceQuestion(String title, Path imagePath, List<String> answers) {
        this(title, imagePath, answers, answers.get(0));
    }

    public SingleChoiceQuestion(String title, Path imagePath, List<String> answers, String correctAnswer) {
        super(title, imagePath);

        if (answers == null) {
            throw new NullPointerException("Answers must not be null");
        }
 
        if (!answers.contains(correctAnswer)) {
            throw new IllegalArgumentException("Answer list must contain correct answer");
        }

        this.answers = new ArrayList<>(answers);
        this.correctAnswer = correctAnswer;
    }

    public List<String>  getAnswers() {
        return answers;
    }
    
    public void shuffleAnswers() {
        Collections.shuffle(answers);
    }

    @Override
    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
