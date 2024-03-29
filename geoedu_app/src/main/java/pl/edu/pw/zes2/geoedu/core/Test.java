package pl.edu.pw.zes2.geoedu.core;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private List<Question> questions;
    private List<String> answers;

    public Test(List<Question> questions) {
        this.questions = questions;
        this.answers = new ArrayList<>(questions.size());
        for (int i = 0; i < questions.size(); ++i) {
            answers.add(null);
        }
    }

    public Question getQuestion(int index) {
        return questions.get(index);
    }

    public int getQuestionCount() {
        return questions.size();
    }

    public String getAnswer(int index) {
        return answers.get(index);
    }

    public void setAnswer(int index, String answer) {
        answers.set(index, answer);
    }

    public int getCorrectCount() {
        int result = 0;

        for (int i = 0; i < getQuestionCount(); ++i) {
            if (isCorrect(i)) {
                ++result;
            }
        }

        return result;
    }

    public boolean isCorrect(int index) {
        return getQuestion(index).getCorrectAnswer().equals(getAnswer(index));
    }
}
