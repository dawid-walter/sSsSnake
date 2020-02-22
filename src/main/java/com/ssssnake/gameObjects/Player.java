package com.ssssnake.gameObjects;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player implements Comparable<Player> {
    private final String playerName;
    private final int playerScore;
    private final int playerSnakeSize;
    public static List<Player> playersHighScores = new ArrayList<>();

    public Player(String playerName, int playerScore, int playerSnakeSize) {
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.playerSnakeSize = playerSnakeSize;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public static void displayPlayerHighScores() {
        Collections.sort(playersHighScores, Collections.reverseOrder());
        for (int i = 0; i < playersHighScores.size(); i++) {
            System.out.println((i + 1) + ": " + playersHighScores.get(i));
        }
    }

    public static String playersHighScoresToString() {
        StringBuilder sb = new StringBuilder();
        Collections.sort(playersHighScores, Collections.reverseOrder());
        for (int i = 0; i < playersHighScores.size(); i++) {
            sb.append((i + 1) + ": " + playersHighScores.get(i));
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    public static List<Player> getPlayersHighScores() {
        Collections.sort(playersHighScores, Collections.reverseOrder());
        return playersHighScores;
    }

    public static void addPlayerToHighScores(Player p) {
        playersHighScores.add(p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (playerScore != player.playerScore) return false;
        return playerName != null ? playerName.equals(player.playerName) : player.playerName == null;
    }

    @Override
    public int compareTo(Player player) {
        return (Integer.compare(this.getPlayerScore(), player.getPlayerScore()));
    }

    @Override
    public int hashCode() {
        int result = playerName != null ? playerName.hashCode() : 0;
        result = 31 * result + playerScore;
        return result;
    }

    @Override
    public String toString() {
        return "Player: " + playerName + " --- " + "Score: " + playerScore + " --- " + "Your snake length is: " + playerSnakeSize;
    }
}
