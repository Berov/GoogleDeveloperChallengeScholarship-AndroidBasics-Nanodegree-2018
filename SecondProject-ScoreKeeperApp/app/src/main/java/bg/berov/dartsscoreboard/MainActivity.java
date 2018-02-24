package bg.berov.dartsscoreboard;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import bg.berov.dartsscoreboard.Model.Player;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtPointsPlayerA, txtPointsPlayerB, txtAveragePlayerA, txtAveragePlayerB, txtRoundsPlayerA, txtRoundsPlayerB, txtTempPoints, txtPlayerA, txtPlayerB;
    private Player playerA, playerB;
    private int tempPoints;
    private KonfettiView konfettiView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int game = 501;

        konfettiView = (KonfettiView) findViewById(R.id.viewKonfetti);

        Button btn0 = (Button) findViewById(R.id.btn_0);
        Button btn1 = (Button) findViewById(R.id.btn_1);
        Button btn2 = (Button) findViewById(R.id.btn_2);
        Button btn3 = (Button) findViewById(R.id.btn_3);
        Button btn4 = (Button) findViewById(R.id.btn_4);
        Button btn5 = (Button) findViewById(R.id.btn_5);
        Button btn6 = (Button) findViewById(R.id.btn_6);
        Button btn7 = (Button) findViewById(R.id.btn_7);
        Button btn8 = (Button) findViewById(R.id.btn_8);
        Button btn9 = (Button) findViewById(R.id.btn_9);
        Button btnEnter = (Button) findViewById(R.id.btn_enter);
        Button btnClear = (Button) findViewById(R.id.btn_back);
        ImageButton btnReset = (ImageButton) findViewById(R.id.btn_reset);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnEnter.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnReset.setOnClickListener(this);

        txtPlayerA = (TextView) findViewById(R.id.textView_playerA);
        txtPlayerB = (TextView) findViewById(R.id.textView_playerB);
        txtPointsPlayerA = (TextView) findViewById(R.id.textView_playerA_points);
        txtPointsPlayerB = (TextView) findViewById(R.id.textView_playerB_points);
        txtAveragePlayerA = (TextView) findViewById(R.id.textView_playerA_average);
        txtAveragePlayerB = (TextView) findViewById(R.id.textView_playerB_average);
        txtRoundsPlayerA = (TextView) findViewById(R.id.textView_playerA_rounds);
        txtRoundsPlayerB = (TextView) findViewById(R.id.textView_playerB_rounds);
        txtTempPoints = (TextView) findViewById(R.id.textView_tempPoints);

        playerA = new Player(game);
        playerB = new Player(game);

        tempPoints = 0;

        if (savedInstanceState != null) {
            playerA = (Player) savedInstanceState.getSerializable("playerA");
            playerB = (Player) savedInstanceState.getSerializable("playerB");
            tempPoints = savedInstanceState.getInt("tempPoints");

            txtTempPoints.setText("" + tempPoints);
            displayPlayerStats(playerA);
            displayPlayerStats(playerB);

            if (getActivePlayer() == playerA) {
                displayPlayerStats(playerB);
            } else {
                displayPlayerStats(playerA);
            }

            if (playerA.isPlayerFinished()) {
                txtPointsPlayerA.setText(R.string.WON);
                startKonfetti();
            }

            if (playerB.isPlayerFinished()) {
                txtPointsPlayerB.setText(R.string.WON);
                startKonfetti();
            }
        }
    }

    public void addPointsToTemp(int points) {
        if (tempPoints > 0 && (tempPoints * 10 + points) <= 180) {
            tempPoints *= 10;
            tempPoints += points;
        } else if (tempPoints == 0) {
            tempPoints = points;
        }

        txtTempPoints.setText(Integer.toString(tempPoints));
    }

    public Player getActivePlayer() {
        if ((playerA.getPlayerRounds() + playerB.getPlayerRounds()) % 2 == 0 || playerA.getPlayerRounds() == 0) {
            return playerA;
        }
        return playerB;
    }

    public void displayPlayerStats(Player player) {
        if (player == playerA) {
            txtPointsPlayerA.setText(Integer.toString(playerA.getPlayerPoints()));
            txtPointsPlayerA.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));

            txtAveragePlayerA.setText("Average: " + playerA.getPlayerAverage());
            txtRoundsPlayerA.setText("Round " + Integer.toString(playerA.getPlayerRounds()));
            txtPlayerA.setTextColor(ResourcesCompat.getColor(getResources(), R.color.passivePlayer, null));
            txtPlayerB.setTextColor(ResourcesCompat.getColor(getResources(), R.color.activePlayer, null));
            txtPlayerB.setTypeface(null, Typeface.BOLD);
            txtPlayerA.setTypeface(null, Typeface.NORMAL);

        } else {
            txtPointsPlayerB.setText(Integer.toString(playerB.getPlayerPoints()));
            txtPointsPlayerB.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
            txtAveragePlayerB.setText("Average: " + playerB.getPlayerAverage());
            txtRoundsPlayerB.setText("Round " + Integer.toString(playerB.getPlayerRounds()));
            txtPlayerB.setTextColor(ResourcesCompat.getColor(getResources(), R.color.passivePlayer, null));
            txtPlayerA.setTextColor(ResourcesCompat.getColor(getResources(), R.color.activePlayer, null));
            txtPlayerA.setTypeface(null, Typeface.BOLD);
            txtPlayerB.setTypeface(null, Typeface.NORMAL);
        }
    }

    public void clearTempPoints() {
        tempPoints = 0;
        txtTempPoints.setText(Integer.toString(tempPoints));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_0:
                addPointsToTemp(0);
                break;

            case R.id.btn_1:
                addPointsToTemp(1);
                break;

            case R.id.btn_2:
                addPointsToTemp(2);
                break;

            case R.id.btn_3:
                addPointsToTemp(3);
                break;

            case R.id.btn_4:
                addPointsToTemp(4);
                break;

            case R.id.btn_5:
                addPointsToTemp(5);
                break;

            case R.id.btn_6:
                addPointsToTemp(6);
                break;

            case R.id.btn_7:
                addPointsToTemp(7);
                break;

            case R.id.btn_8:
                addPointsToTemp(8);
                break;

            case R.id.btn_9:
                addPointsToTemp(9);
                break;

            case R.id.btn_enter:

                Player player = getActivePlayer();

                if (player.addPointsToPlayer(tempPoints)) {
                    displayPlayerStats(player);
                    if (tempPoints == 0) {
                        Toast.makeText(this, R.string.BUST, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, tempPoints + " is not a valid score", Toast.LENGTH_SHORT).show();
                }

                clearTempPoints();

                if (player.isPlayerFinished()) {
                    if (player == playerA) {
                        txtPointsPlayerA.setText(R.string.WON);
                    } else {
                        txtPointsPlayerB.setText(R.string.WON);
                    }
                    startKonfetti();
                }

                break;

            case R.id.btn_back:
                tempPoints = 0;
                txtTempPoints.setText("0");
                break;

            case R.id.btn_reset:
                playerA.resetPlayerGame();
                playerB.resetPlayerGame();
                displayPlayerStats(playerA);
                displayPlayerStats(playerB);
                txtAveragePlayerA.setText(R.string.Average_0);
                txtAveragePlayerB.setText(R.string.Average_0);
                clearTempPoints();
                break;

            default:
                break;
        }
    }


    public void startKonfetti() {
        konfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                .stream(300, 5000L);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help:
                Toast.makeText(this, "Click on the dart board image to reset or start new game!", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("playerA", playerA);
        outState.putSerializable("playerB", playerB);
        outState.putInt("tempPoints", tempPoints);
        super.onSaveInstanceState(outState);
    }
}