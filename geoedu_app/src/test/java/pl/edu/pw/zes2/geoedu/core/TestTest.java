package pl.edu.pw.zes2.geoedu.core;

import java.util.Arrays;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestTest {

    @Test
    public void testGetAnswerAndSetAnswer() {
        Question question1 = new SingleChoiceQuestion("pytanie 1", null, Arrays.asList("odpA", "odpB", "odpC", "odpD") );
        Question question2 = new SingleChoiceQuestion("pytanie 2", null, Arrays.asList("odpA", "odpB", "odpC", "odpD"));
        Question question3 = new SingleChoiceQuestion("pytanie 3", null, Arrays.asList("odpA", "odpB", "odpC", "odpD"));
       
        final var test = new pl.edu.pw.zes2.geoedu.core.Test(Arrays.asList(question1, question2, question3));
        
        test.setAnswer(1, "odpB");
        String answer = test.getAnswer(1);
        assertEquals("odpB", answer);
    }

    @Test
    public void shouldReturnCorrectQuestionCount() {
        final var q1 = new OpenEndedQuestion("Pytanie", null, "Odpowiedź");
        final var q2 = new OpenEndedQuestion("Pytanie", null, "Odpowiedź");
        final var q3 = new OpenEndedQuestion("Pytanie", null, "Odpowiedź");
        final var q4 = new OpenEndedQuestion("Pytanie", null, "Odpowiedź");

        final var test = new pl.edu.pw.zes2.geoedu.core.Test(Arrays.asList(q1, q2, q3, q4));

        assertEquals(4, test.getQuestionCount());
    }
}
