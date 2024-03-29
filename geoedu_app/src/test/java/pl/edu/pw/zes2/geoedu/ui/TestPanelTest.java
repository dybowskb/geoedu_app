package pl.edu.pw.zes2.geoedu.ui;

import static org.junit.Assert.assertEquals;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Test;
import pl.edu.pw.zes2.geoedu.core.Question;
import pl.edu.pw.zes2.geoedu.core.SingleChoiceQuestion;

import java.util.ArrayList;
import java.util.Arrays;


public class TestPanelTest {

    private List<Question> questions;
    private TestPanel testPanel;

    @Test
    public void testCreateQuestionPanels() {
        List<Question> questions = new ArrayList<>();
        questions.add(new SingleChoiceQuestion("Question 1", null, Arrays.asList("Answer 1", "Answer 2", "Answer 3", "Answer 4")));
        questions.add(new SingleChoiceQuestion("Question 2", null, Arrays.asList("Answer 1", "Answer 2", "Answer3 ", "Answer 4"), "Answer 2"));
    
        TestPanel testPanel = new TestPanel(null, new pl.edu.pw.zes2.geoedu.core.Test(questions));
        JPanel questionPanel = (JPanel) testPanel.getComponent(1);

        assertEquals(3, questionPanel.getComponentCount());
    }
    
    @Test
    public void testInitialQuestion() {
        questions = new ArrayList<>();
        Question question1 = new SingleChoiceQuestion("Pytanie 1", null, List.of("Odpowiedź 1", "Odpowiedź 2", "Odpowiedź 3", "Odpowiedź 4"));
        Question question2 = new SingleChoiceQuestion("Pytanie 2", null, List.of("Odpowiedź 1", "Odpowiedź 2", "Odpowiedź 3", "Odpowiedź 4"), "Odpowiedź 2");
        Question question3 = new SingleChoiceQuestion("Pytanie 3", null, List.of("Odpowiedź 1"));
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        testPanel = new TestPanel(null, new pl.edu.pw.zes2.geoedu.core.Test(questions));

        final var questionPanel = (JPanel)testPanel.getComponent(1);
        final var questionTitle = (JLabel)questionPanel.getComponent(0);

        assertEquals("Pytanie 1", questionTitle.getText());
    }
}
