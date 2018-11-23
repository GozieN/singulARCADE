package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.Set;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.List;

public class ScoreBoard implements Serializable {
    /**
     * Class ScoreBoard: a scoreboard containing the highest scores for a game or user
     * For a Game's ScoreBoard: Each User can have at most one score on the Game Scoreboard
     * For a User's ScoreBoard: Each Game can have at most one score on the User ScoreBoard
     *
     * TreeMap topScores keeps track of the top scores
     * Key: String - the userid
     * Value: int - the score they achieved in the game
     */
    private HashMap<String, Integer> topScores = new HashMap<>();

    /**
     * The number of scores that get featured on the ScoreBoard
     */
    final static private int TOP_NUM_SCORES = 10;

    /**
     * Return the hashmap containing the specified scores
     * @return the hashmap holding the specified scores
     */
    HashMap<String, Integer> getTopScores() {return topScores;}

    /**
     *
     * @param subject: String of userid who achieved score
     * @param score: Integer score that user achieved playing game
     */
    void takeNewScore(String subject, Integer score) {
        if (topScores.containsKey(subject)) {
            if (topScores.get(subject) < score) {
                replaceScore(subject, score);
            }
        } else { // topScores does not have the subject yet
                replaceScore(subject, score);
        }
    }

    /**
     * Helper method for takeNewScore()
     *
     * @param subject: String of userid who achieved score
     * @param score: Integer score that user achieved playing game
     */
     void replaceScore(String subject, Integer score) {
        if (topScores.size() < TOP_NUM_SCORES) { //IF SCOREBOARD NOT FULL, ADD SCORE
            topScores.put(subject, score);
        } else {
            for (Entry<String, Integer> item : sortValueSet()) {
                //COMPARE VALUES HERE, IF SCORE IS HIGHER THAN ONE OF THE SCOREBOARD SCORES, THEN REPLACE
                if (item.getValue() < score) {
                    topScores.put(subject, score);
                    removeSmallestScore();
                }
            }
        }
    }

    /**
     * Removes the smallest entry in hashmap topScores when a higher one enters the ScoreBoard
     */
     void removeSmallestScore() {
        int length = 0;
        for (Entry<String, Integer> item : sortValueSet()) {
            length += 1;
            if (length == sortValueSet().size()) {
                topScores.remove(item.getKey());
            }
        }
    }

    /**
     * sortValueSet sorts the HashMap topScores by values of descending order
     * @return a Set of sorted entries from topScores. REPEAT SCORES, I.E. USERS
     * WHO GET A SCORE THEY PREVIOUSLY GOT ARE DISREGARDED!!!!!!!!! Only the highest score is kept
     *
     * Adapted from: http://www.java67.com/2015/01/how-to-sort-hashmap-in-java-based-on.html
     *
     */
     Set<Entry<String, Integer>> sortValueSet() {
        //Get a Set of Map Entries from topScores HashMap and put them into a List
        Set<Entry<String, Integer>> setOfScores = topScores.entrySet();
        List<Entry<String, Integer>> scores = new ArrayList<>(setOfScores);

        // We write our own comparator by overriding compare()
        Comparator<Entry<String, Integer>> valueComparator = new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                if (o1.getValue().equals(o2.getValue())) {
                    String i1 = o1.getKey();
                    String i2 = o2.getKey();
                    return i1.compareTo(i2);
                } else {
                    Integer i1 = o1.getValue();
                    Integer i2 = o2.getValue();
                    return -(i1.compareTo(i2)); //ITEMS IN DESCENDING ORDER
                }
            }
        };
        //sort the List of Map Entries by values, using the comparator we defined ourselves
        scores.sort(valueComparator);
        LinkedHashMap<String, Integer> sortValues = new LinkedHashMap<>(scores.size());

        //Copy the entries from List to LinkedListMap, which retains order
        for (Entry<String, Integer> e : scores) {
            sortValues.put(e.getKey(), e.getValue());
        }
        return sortValues.entrySet();
    }

    @Override
    public String toString() {
        if (topScores.isEmpty()) {
            return "NO HIGH SCORES YET!";
        }
        StringBuilder s = new StringBuilder();
        Integer placement = 1;
        for (Entry<String, Integer> item : sortValueSet()) {
            s.append(placement.toString());
            s.append(". ");
            s.append(item.getKey());
            s.append(" : ");
            s.append(item.getValue());
            s.append(System.getProperty("line.separator"));
            placement++;
        }
        return s.toString();
    }
}
