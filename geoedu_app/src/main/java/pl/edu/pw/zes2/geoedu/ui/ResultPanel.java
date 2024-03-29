package pl.edu.pw.zes2.geoedu.ui;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import pl.edu.pw.zes2.geoedu.core.OpenEndedQuestion;
import pl.edu.pw.zes2.geoedu.core.SingleChoiceQuestion;
import pl.edu.pw.zes2.geoedu.core.Test;

public class ResultPanel extends JPanel {
    private Test test;
    private int currentQuestion = 0;
    private JPanel questionPanel = new JPanel();

    public ResultPanel(Test test) {
        super(new BorderLayout());
        this.test = test;

        setup();
    }

    private void setup() {
        setBackground(Theme.LIGHT_BLUE);

        JOptionPane.showMessageDialog(questionPanel,
            "Rozwiązano poprawnie " + test.getCorrectCount() + " z " + test.getQuestionCount(),
            "Koniec testu",
            JOptionPane.INFORMATION_MESSAGE);

        final var title = new JLabel("Koniec testu", JLabel.CENTER);
        title.setBorder(new EmptyBorder(Theme.STANDARD_UNIT / 2,
                Theme.STANDARD_UNIT,
                Theme.STANDARD_UNIT / 2,
                Theme.STANDARD_UNIT));
        title.setForeground(Theme.WHITE);
        title.setBackground(Theme.DARK_BLUE);
        title.setOpaque(true);
        title.setFont(Theme.LARGE_FONT.deriveFont(Font.BOLD));
        add(title, BorderLayout.PAGE_START);

        questionPanel.setBorder(new EmptyBorder(Theme.STANDARD_UNIT * 2,
                Theme.STANDARD_UNIT * 6,
                Theme.STANDARD_UNIT * 4,
                Theme.STANDARD_UNIT * 6));
        questionPanel.setBackground(Theme.LIGHT_BLUE);
        questionPanel.setOpaque(false);
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.PAGE_AXIS));
        add(questionPanel, BorderLayout.CENTER);

        final var questionPanelControls = new JPanel(new GridLayout(1, 2, Theme.STANDARD_UNIT, 0));
        questionPanelControls.setOpaque(false);
        questionPanelControls.setBorder(new EmptyBorder(Theme.STANDARD_UNIT,
                Theme.STANDARD_UNIT * 2,
                Theme.STANDARD_UNIT,
                Theme.STANDARD_UNIT * 2));
        add(questionPanelControls, BorderLayout.PAGE_END);

        questionPanelControls.add(new JButton(new AbstractAction("Poprzednie") {
            @Override
            public void actionPerformed(ActionEvent event) {
                currentQuestion = Math.max(0, currentQuestion - 1);
                renderQuestion(currentQuestion);
            }
        }));

        questionPanelControls.add(new JButton(new AbstractAction("Następne") {
            @Override
            public void actionPerformed(ActionEvent event) {
                currentQuestion = Math.min(currentQuestion + 1, test.getQuestionCount() - 1);
                renderQuestion(currentQuestion);
            }
        }));

        renderQuestion(0);
    }

    private void renderQuestion(int i) {
        final var question = test.getQuestion(i);

        questionPanel.removeAll();

        final var questionTitle = new JLabel(question.getTitle(), JLabel.CENTER);
        questionTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        questionTitle.setFont(Theme.LARGE_FONT);
        questionPanel.add(questionTitle);
        questionPanel.add(Box.createRigidArea(new Dimension(0, Theme.STANDARD_UNIT * 2)));

        if (question.getImagePath() != null) {
            final var imgLabel = new JLabel();
            ImageIcon icon = new ImageIcon(question.getImagePath().toString());
            imgLabel.setOpaque(false);
            imgLabel.setIcon(icon);
            imgLabel.setAlignmentX(CENTER_ALIGNMENT);
            questionPanel.add(imgLabel);

            questionPanel.add(Box.createRigidArea(new Dimension(0, Theme.STANDARD_UNIT * 2)));
        }

        final var answersPanel = new JPanel();
        answersPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        answersPanel.setOpaque(false);

        if (question instanceof OpenEndedQuestion) {
            answersPanel.setLayout(new BoxLayout(answersPanel, BoxLayout.PAGE_AXIS));

            final var answer = test.getAnswer(i);

            final var givenAnswer = new JLabel("Podano: " + (answer == null ? "" : answer));
            givenAnswer.setFont(Theme.NORMAL_FONT);
            givenAnswer.setForeground(Color.GREEN);

            answersPanel.add(givenAnswer);
            answersPanel.add(Box.createRigidArea(new Dimension(0, Theme.STANDARD_UNIT)));

            if (!test.isCorrect(i)) {
                givenAnswer.setForeground(Color.RED);

                final var correctAnswer = new JLabel("Poprawna odpowiedź: " + test.getQuestion(i).getCorrectAnswer());
                answersPanel.add(correctAnswer);
            }
        } else if (question instanceof SingleChoiceQuestion) {
            answersPanel.setLayout(new GridLayout(2, 2, Theme.STANDARD_UNIT, Theme.STANDARD_UNIT));
            final var answerGroup = new ButtonGroup();
            final var answers = ((SingleChoiceQuestion)question).getAnswers();
            for (final var answer : answers) {
                final var answerRadioButon = new JRadioButton();
                answerRadioButon.setText(answer);

                answerGroup.add(answerRadioButon);
                answersPanel.add(answerRadioButon);

                if (answer.equals(test.getAnswer(i))) {
                    answerRadioButon.setSelected(true);
                    answerRadioButon.setForeground(Color.RED);
                }

                if (answer.equals(question.getCorrectAnswer())) {
                    answerRadioButon.setForeground(Color.GREEN);
                }
            }
        }
        questionPanel.add(answersPanel);

        questionPanel.revalidate();
        questionPanel.repaint();
    }
}
