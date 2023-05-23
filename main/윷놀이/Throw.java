package org.cis1200.윷놀이;

import java.util.TreeMap;

public class Throw {

    private int[] cast;
    private final TreeMap<Integer, String> numFlatsToCastName;
    private final TreeMap<String, Integer> castNamesToMoves;

    /**
     * The constructor represents a throw represented as an integer array. Each
     * index in the array represents a Yut stick. If the index is 0, then the
     * stick has landed flat side up. If the index is 1, then the stick has landed
     * round side up.
     */

    public Throw() {
        this.cast = makeRandomArray();

        this.numFlatsToCastName = new TreeMap<>();
        numFlatsToCastName.put(1, "도");
        numFlatsToCastName.put(2, "개");
        numFlatsToCastName.put(3, "걸");
        numFlatsToCastName.put(4, "윷");
        numFlatsToCastName.put(0, "모");

        this.castNamesToMoves = new TreeMap<>();
        castNamesToMoves.put("도", 1);
        castNamesToMoves.put("개", 2);
        castNamesToMoves.put("걸", 3);
        castNamesToMoves.put("윷", 4);
        castNamesToMoves.put("모", 5);
    }

    public int[] makeRandomArray() {
        int[] randomCast = new int[4];
        for (int i = 0; i < 3; i++) {
            double numRandom = Math.random();
            if (numRandom > 0.5) {
                randomCast[i] = 1;
            } else {
                randomCast[i] = 0;
            }
        }
        return randomCast;
    }

    /**
     * setCast is a method for convenient testing. It allows the programmer
     * to set the cast of a throw manually.
     *
     * @param stick1 is whether the first stick landed flat or round.
     * @param stick2 is whether the second stick landed flat or round.
     * @param stick3 is whether the third stick landed flat or round.
     * @param stick4 is whether the fourth stick landed flat or round.
     */
    public void setCast(int stick1, int stick2, int stick3, int stick4) {
        this.cast = new int[] {stick1, stick2, stick3, stick4};
    }

    public int getNumFlats() {
        int numFlats = 0;
        for (int i = 0; i < 4; i++) {
            if (this.cast[i] == 0) {
                numFlats++;
            }
        }
        return numFlats;
    }

    public String getCastName(int numFlats) {
        return numFlatsToCastName.get(numFlats);
    }

    public int getMoves(String castName) {
        return castNamesToMoves.get(castName);
    }

}
