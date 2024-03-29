package pl.edu.pw.zes2.geoedu.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JTextField;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.Test;
import static pl.edu.pw.zes2.geoedu.ui.AddPanel.*;
import static org.junit.Assert.*;

public class AddPanelTest {

    public AddPanelTest() {
    }

    @Test
    public void getCorrectAnswerNumber_ShouldThrowIllegalArgumentException_WhenLetterOtherThanABCD() {
        String message = "Zła wartość w polu 'Poprawna odpowiedź'";
        String answerLetter = "W";

        assertThatThrownBy(() -> getCorrectAnswerNumber(answerLetter))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(message);
    }

    @Test
    public void writeQuestionToFile_ShouldWriteQuestionDataToFile_InTheCorrectFormatABCD() throws Exception {
        //Given
        String testFileName = "Data\\test2.txt";
        String imgPath = "Data\\photos\\Qmark.png";

        JTextField[] textFieldsArray = {
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20)
        };

        textFieldsArray[0].setText("Pytanie testowe");
        textFieldsArray[1].setText("odpA");
        textFieldsArray[2].setText("odpB");
        textFieldsArray[3].setText("odpC");
        textFieldsArray[4].setText("odpD");
        textFieldsArray[5].setText("A");

        //When
        writeQuestionToFile(textFieldsArray, imgPath, testFileName, false);

        String result="";
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(testFileName));
            reader.readLine();
            result = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String expected = "Pytanie testowe;;1;;Data\\photos\\Qmark.png;;false;;odpA;;odpB;;odpC;;odpD";
        
        //Then
        assertEquals(expected, result);   
        File test2 = new File(testFileName);
        test2.delete();
    }
    
    @Test
    public void writeQuestionToFile_ShouldWriteQuestionDataToFile_InTheCorrectFormatOpen() throws Exception {
        //Given
        String testFileName = "Data\\test3.txt";
        String imgPath = "Data\\photos\\Qmark.png";

        JTextField[] textFieldsArray = {
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20)
        };

        textFieldsArray[0].setText("Pytanie testowe");
        textFieldsArray[1].setText("");
        textFieldsArray[2].setText("");
        textFieldsArray[3].setText("");
        textFieldsArray[4].setText("");
        textFieldsArray[5].setText("Poprawna odpowiedz");

        //When
        writeQuestionToFile(textFieldsArray, imgPath, testFileName, true);

        String result="";
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(testFileName));
            reader.readLine();
            result = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String expected = "Pytanie testowe;;1;;Data\\photos\\Qmark.png;;true;;Poprawna odpowiedz";
        
        //Then
        assertEquals(expected, result);   
        File test3 = new File(testFileName);
        test3.delete();
    }
    
    @Test
    public void writeQuestionToFile_ShouldThrowRuntimeException_WhenRequiredFieldsEmpty() throws Exception {
        //Given
        String testFileName = "Data\\test4.txt";
        String imgPath = "Data\\photos\\Qmark.png";

        JTextField[] textFieldsArray = {
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20),
            new JTextField(20)
        };

        textFieldsArray[0].setText("Pytanie testowe");
        textFieldsArray[1].setText("odpA");
        textFieldsArray[2].setText("");
        textFieldsArray[3].setText("odpC");
        textFieldsArray[4].setText("");
        textFieldsArray[5].setText("C");
        
        String message = "Wszystkie pola muszą być wypełnione!";
        
        //Then
        assertThatThrownBy(() -> writeQuestionToFile(textFieldsArray, imgPath, testFileName, false))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(message);
    }

}
