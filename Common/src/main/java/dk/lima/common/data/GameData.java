package dk.lima.common.data;

import java.time.Duration;

public class GameData {


    // Screen Settings
    private int displayWidth  = 800 ;
    private int displayHeight = 800;
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // This is the actual size of the tile displayed

    private final GameInputs inputs = new GameInputs();
    private boolean isGameRunning = false;
    private Duration duration = Duration.ofSeconds(0);
    private int score = 0;
    private Coordinate mousePosition = new Coordinate(0,0);
    private int highscore = 0;
    private boolean isGamePaused;
    private boolean timeScoring = false;

    public GameInputs getInputs() {
        return inputs;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }


    public synchronized Duration getDuration() {
        return duration;
    }

    public synchronized void setDuration(Duration duration) {
        this.duration = duration;
    }

    public synchronized void addDuration(Duration duration) {
        this.duration = this.duration.plus(duration);
    }

    public synchronized boolean isGameRunning() {
        return isGameRunning;
    }

    public synchronized void setGameRunning(boolean gameRunning) {
        isGameRunning = gameRunning;
    }

    public synchronized boolean isTimeScoring() {
        return timeScoring;
    }

    public synchronized void setTimeScoring(boolean timeScoring) {
        this.timeScoring = timeScoring;
    }

    public synchronized int getScore() {
        return score;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public synchronized void setScore(int score) {
        this.score = score;
    }

    public synchronized void addScore(int score) {
        this.score += score;
    }

    public Coordinate getMousePosition() {
        return mousePosition;
    }

    public void setMousePosition(Coordinate mousePosition) {
        this.mousePosition = mousePosition;
    }
}
