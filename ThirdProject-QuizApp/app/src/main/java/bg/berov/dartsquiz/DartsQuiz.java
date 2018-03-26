package bg.berov.dartsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DartsQuiz extends AppCompatActivity {

    private RadioGroup firstQuestion, fourthQuestion, fifthQuestion;
    private EditText secondQuestion;
    private CheckBox thirdQuestion1, thirdQuestion2, thirdQuestion3, thirdQuestion4;
    private RadioButton radioButtonFirstQuestion, radioButtonFourthQuestion, radioButtonFifthQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_darts_quiz);

        Button submit = (Button) findViewById(R.id.button_submit);
        TextView hello = (TextView) findViewById(R.id.textView_hello);

        firstQuestion = (RadioGroup) findViewById(R.id.radio_group_first_question);
        secondQuestion = (EditText) findViewById(R.id.editText_second_qwestion);
        thirdQuestion1 = (CheckBox) findViewById(R.id.checkBox1);
        thirdQuestion2 = (CheckBox) findViewById(R.id.checkBox2);
        thirdQuestion3 = (CheckBox) findViewById(R.id.checkBox3);
        thirdQuestion4 = (CheckBox) findViewById(R.id.checkBox4);
        fourthQuestion = (RadioGroup) findViewById(R.id.radio_group_fourth_question);
        fifthQuestion = (RadioGroup) findViewById(R.id.radio_group_fifth_question);
        radioButtonFirstQuestion = (RadioButton) findViewById(R.id.radioButtonFirstQuestion);
        radioButtonFourthQuestion = (RadioButton) findViewById(R.id.radioButtonFourthQuestion);
        radioButtonFifthQuestion = (RadioButton) findViewById(R.id.radioButtonFifthQuestion);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            String username = (String) bundle.get("name");
            hello.setText(getString(R.string._hi) + username + ". \n" + getString(R.string.answer_please));
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (allQuestionsAreFilledIn()) {
                    checkScores();
                } else {
                    Toast.makeText(DartsQuiz.this, R.string.answer_to_all_please, Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    //  checks if all questions have some answer
    private boolean allQuestionsAreFilledIn() {
        if (firstQuestion.getCheckedRadioButtonId() == -1 || secondQuestion.getText().toString().isEmpty()
                || (!thirdQuestion1.isChecked() && !thirdQuestion2.isChecked() && !thirdQuestion3.isChecked() && !thirdQuestion4.isChecked())
                || fourthQuestion.getCheckedRadioButtonId() == -1 || fifthQuestion.getCheckedRadioButtonId() == -1) {
            return false;
        }

        return true;
    }


    //  checks the right answers and calculates scores
    private void checkScores() {
        int quizScore = 0;

        if (radioButtonFirstQuestion.isChecked()) {
            quizScore++;
        }
        if (secondQuestion.getText().toString().trim().toLowerCase().equals("the power")) {
            quizScore++;
        }
        if (thirdQuestion1.isChecked() && thirdQuestion3.isChecked() && !thirdQuestion2.isChecked() && !thirdQuestion4.isChecked()) {
            quizScore++;
        }
        if (radioButtonFourthQuestion.isChecked()) {
            quizScore++;
        }
        if (radioButtonFifthQuestion.isChecked()) {
            quizScore++;
        }

        displayResult(quizScore);
    }


    //displays the result of the quiz
    private void displayResult(int quizScore) {
        String result;

        if (quizScore == 5) {
            result = getString(R.string.excellent) + quizScore + getString(R.string.from);
        } else if (quizScore == 4) {
            result = getString(R.string.well_done) + quizScore + getString(R.string.from);
        } else {
            result = getString(R.string.not_bad) + quizScore + getString(R.string.tray_again);
        }

        Toast.makeText(DartsQuiz.this, result, Toast.LENGTH_LONG).show();
    }
}
