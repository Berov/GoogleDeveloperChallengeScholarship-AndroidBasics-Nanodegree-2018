package bg.berov.dartsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button quiz = (Button) findViewById(R.id.button_startQuiz);
        userName = (EditText) findViewById(R.id.editText_userName);

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userName.getText().toString().isEmpty() && !userName.getText().toString().trim().equals("")) {
                    Intent intent = new Intent(MainActivity.this, DartsQuiz.class);
                    intent.putExtra(getString(R.string.name), userName.getText().toString().trim());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, R.string.enter_name_please, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

