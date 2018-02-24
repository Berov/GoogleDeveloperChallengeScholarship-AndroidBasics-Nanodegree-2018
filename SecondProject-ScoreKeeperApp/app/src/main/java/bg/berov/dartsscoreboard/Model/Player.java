package bg.berov.dartsscoreboard.Model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Player implements Serializable {
    private int game;
    private int points;
    private int rounds;


    public Player(int game) {
        this.game = game; // Games X01 - 901, 701, 501, 301
        this.rounds = 0;
        this.points = 0;
    }


    public int getPlayerPoints() {
        return this.game - this.points;
    }

    public int getPlayerRounds() {
        return rounds;
    }

    public String getPlayerAverage() {
        DecimalFormat df = new DecimalFormat("#.#");
        if (this.points == 0) {
            return "0";
        }
        return df.format((double) this.points / rounds);
    }

    public boolean isPlayerFinished() {
        return this.game - this.points == 0;
    }

    public void resetPlayerGame() {
        this.points = 0;
        this.rounds = 0;
    }

    public boolean addPointsToPlayer(int points) {
        if (points <= game - this.points && game - this.points - points != 1) {
            this.points += points;
            rounds++;
            return true;
        }
        return false;
    }
}
