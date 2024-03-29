package pl.edu.pw.zes2.geoedu.core;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuestionTest {

    @Test
    public void shouldShuffleAnswers() {
        final List<String> answers = Arrays.asList("A", "B", "C", "D");

        for (int i = 0; i < 10; ++i) {
            final var temp = new SingleChoiceQuestion("Title", null, answers);
            temp.shuffleAnswers();
            if (!temp.getAnswers().equals(answers)) {
                return;
            }
        }

        assertTrue("Answers not shuffled (~100% certainty)", false);
    }

    @Test
    public void shouldHaveEqualHashCodeWhenSameTitleAndImagePath() {
        final var q1 = new SingleChoiceQuestion("Title", null, Arrays.asList("A", "B", "C", "D"));
        final var q2 = new SingleChoiceQuestion("Title", null, Arrays.asList("A", "B", "C", "D"));

        assertEquals(q1.hashCode(), q2.hashCode());
    }

    @Test
    public void shouldThrowWhenCorrectAnswerIsNotInList() {
        assertThatThrownBy(() -> new SingleChoiceQuestion(null, null, new ArrayList<>(), "Odpowiedz"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void should_returnQuestion_whenLineGiven() {
        // Given
        String line = "Jaka jest stolica Polski?;;1;;;;;;Warszawa;;Sosnowiec;;Radom;;Białystok";

        // When
        SingleChoiceQuestion result = (SingleChoiceQuestion) QuestionFileFactory.deserializeQuestion(line);

        // Then
        SingleChoiceQuestion expected = new SingleChoiceQuestion("Jaka jest stolica Polski?", null,
                Arrays.asList("Warszawa", "Sosnowiec", "Radom", "Białystok"), "Warszawa");
        assertEquals(expected, result);

    }

    //@Test
    public void Question_ShouldThrowIllegalArgumentException_WhenFileIsWrongFormat() {

        assertThatThrownBy(() -> new QuestionFileFactory(Paths.get("Data\\test.txt")).createQuestions())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Wrong file format");
    }

    @Test
    public void questionsFromFile_ShouldThrowIOException_WhenFileDoesNotExist() {

        assertThatThrownBy(() -> new QuestionFileFactory(Paths.get("String fileName")).createQuestions())
                .isInstanceOf(IOException.class)
                .hasMessageContaining("No such file");
    }

    //@Test
    public void readQuestionFiles_ShouldThrow_IllegalArgumentException_WhenFileDoesNotExist() {
        assertThatThrownBy(() -> new QuestionFileFactory(Paths.get("Data\\test.txt")).createQuestions())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Wrong file format");
    }

}
