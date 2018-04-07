package bg.berov.jukebox.Model;

import java.util.Random;

import bg.berov.jukebox.R;

public class Util {

    public static final int COUNT_OF_SONGS = 200;   //How many songs to be inserted in the JukeBox
    public static final int INITIAL_YEAR = 1980;    //Initial year for the songs
    public static final int FINAL_YEAR = 2017;      //Final year for the songs

    private static int songNameID = 0; //ID to insure the names of the songs are different
    private static String[] songNames = {"64 Thousand Dollar Space", "Disgrace Don't Cost A Penny", "Some Kinda Interference", "Hard Esctasy", "Give Me Strength",
            "Consequence Story", "That's The Power Of Contempt", "Fate Don't Live Here Anymore", "Silver Look", "Burning Wisdom", "Forgotten Bomb",
            "Avalanches Of Thunder", "Dysfuntional Forgiveness", "Seduction Is My Middle Name", "Understanding Mischief", "Good For Nothing Crusade",
            "Alcoholic Soundwave", "Don't Stop The Insanity", "Drowning In Shopping", "Mystery Waste", "Silent Car", "The Shock Of Composure", "Strength For The Masses",
            "Staring At My Party", "Check Out The Beat", "Gotta Get Into Courage", "My Kinda Beast", "Superficial Ghost", "Atlantic Strength", "Step Away From The Safari",
            "2D Cellphone", "Buidling Bridges Over Accountablility", "Facing Rythmn", "Pacific Weakness", "Love Habit", "Searching For Your Soundwave", "Manic Beat",
            "It's Disaster Time!", "Day Of The Ocean", "Happy Vision", "Basic Forest", "Sensationalism Vaccine", "Bright Chick", "Super Demon",
            "You Stole My Saint", "100mph Arrow", "I Want Your Truth", "Ability Tornado", "Hollywood Music", "Silent Sweatshirt", "Rampant Disgrace", "Mystery Lady"};

    private static String[] artistNames = {"Elton", "Tina", "Michael", "Selena", "Joe", "B.B.", "Patti", "Annie"};
    private static String[] artistFamilyNames = {"John", "Jackson", "Bolton", "Gomez", "Cocker", "King", "LaBelle", "Lennox"};

    private static String[] genre = {"Rock", "Pop", "Jazz", "Disco", "Blues", "Country", "Folk", "Electronic", "Hip hop", "Latin", "Electro", "R&B", "Soul"};

    private static int[] images = {R.drawable.cover1, R.drawable.cover2, R.drawable.cover3, R.drawable.cover4, R.drawable.cover5, R.drawable.cover6, R.drawable.cover7, R.drawable.cover8,
            R.drawable.cover9, R.drawable.cover10, R.drawable.cover11, R.drawable.cover12, R.drawable.cover13, R.drawable.cover14, R.drawable.cover15, R.drawable.cover16, R.drawable.cover17,
            R.drawable.cover18, R.drawable.cover19, R.drawable.cover20, R.drawable.cover21, R.drawable.cover22, R.drawable.cover23, R.drawable.cover24, R.drawable.cover25, R.drawable.cover26,
            R.drawable.cover27, R.drawable.cover28, R.drawable.cover29, R.drawable.cover30, R.drawable.cover31, R.drawable.cover32, R.drawable.cover33, R.drawable.cover34};

    //Some links for the songs
    private static String[] links = {"https://www.youtube.com/watch?v=4bElMPdJuNQ", "https://www.youtube.com/watch?v=mJpymZpk-t8", "https://www.youtube.com/watch?v=0mr8WC-QmDk",
            "https://www.youtube.com/watch?v=otna9Pe3jWg", "https://www.youtube.com/watch?v=D4y_acTR0MY", "https://www.youtube.com/watch?v=SraaOCwRnbA",
            "https://www.youtube.com/watch?v=HuSf1UcFRq0", "https://www.youtube.com/watch?v=xYvjwr_ffsM", "https://www.youtube.com/watch?v=bgHUVGwVpJQ",
            "https://www.youtube.com/watch?v=0WN79cA4Cds", "https://www.youtube.com/watch?v=8S8bHhXjqVw", "https://www.youtube.com/watch?v=S37CvSvQN7o"};


    public static String generateSongName() {
        return getRandomString(songNames) + " " + (songNameID++);
    }

    public static String generateArtistName() {
        return getRandomString(artistNames) + " " + getRandomString(artistFamilyNames);
    }

    public static int generateSongYear() {
        Random r = new Random();
        return r.nextInt(FINAL_YEAR - INITIAL_YEAR + 1) + INITIAL_YEAR;
    }

    public static String generateMusicGenre() {
        return getRandomString(genre);
    }

    public static String generateSongLink() {
        return getRandomString(links);
    }

    public static int generateCover() {
        return images[new Random().nextInt(images.length)];
    }


    private static String getRandomString(String[] array) {
        return array[new Random().nextInt(array.length)];
    }
}
