package ir.game.models.beans;

import ir.game.models.Game;

public class SliderGame {
    private double Avg;
    private Game game;
    private String  label;

    public SliderGame(Game game, double avg) {
        Avg = avg;
        this.game = game;
        this.label = label;
    }

    public SliderGame(Game game,String label) {
        this.game = game;
        this.label = label;
    }

    public double getAvg() {
        return Avg;
    }

    public void setAvg(double avg) {
        Avg = avg;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
