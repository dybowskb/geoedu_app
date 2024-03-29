package pl.edu.pw.zes2.geoedu.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionFileFactory {
    private Path filepath;

    public QuestionFileFactory(Path filepath) {
        this.filepath = filepath;        
    }

    public List<Question> createQuestions() throws IOException {
        if (Files.notExists(filepath)) {
            throw new IOException(new FileNotFoundException("No such file"));
        }

        final var linesNumber = (int)Files.lines(filepath).count();

        List<Question> questions = new ArrayList<>(linesNumber);
        String line;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(Files.newInputStream(filepath), "UTF8"));

            while ((line = reader.readLine()) != null) {
                questions.add(deserializeQuestion(line));
            }
        } catch (IOException exeception) {
        }

        return questions;
    }

    static Question deserializeQuestion(String str) {
        String[] question = str.split(";;");
        
        if (question.length != 8 && question.length != 5) {
            throw new IllegalArgumentException("Wrong file format");
        }

        final var title = question[0];
        final var correctAnswerIndex = Integer.parseInt(question[1]) - 1;
        final var imgPath = Paths.get(question[2]);
        final var isOpen = question[3].equals("true");

        List<String> answers = Arrays.asList(Arrays.copyOfRange(question, 4, question.length));

        if (isOpen) {
            return new OpenEndedQuestion(title, imgPath, answers.get(correctAnswerIndex));
        } else {
            final var q = new SingleChoiceQuestion(title, imgPath, answers, answers.get(correctAnswerIndex));
            q.shuffleAnswers();
            return q;
        }
    }
    
        public static File[] readQuestionFiles(String directoryPath) throws IllegalArgumentException{
        File directory = new File(directoryPath);
        File[] files = null;

        if (directory.isDirectory()) {
            files = directory.listFiles();
        } else {
            throw new IllegalArgumentException("The given path is not a directory.");
        }

        return files;
    }
}
