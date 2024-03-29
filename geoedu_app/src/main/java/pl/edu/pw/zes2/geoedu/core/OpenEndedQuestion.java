package pl.edu.pw.zes2.geoedu.core;

import java.nio.file.Path;

public class OpenEndedQuestion extends Question {
    private String answer;

    public OpenEndedQuestion(String title, Path imagePath, String answer) {
        super(title, imagePath);
        this.answer = answer;
    }

    @Override
    public String getCorrectAnswer() {
        return answer;
    }
}
