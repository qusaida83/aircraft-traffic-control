package br.ufrgs.inf.ga.utils;

/**
 * Helper Class used to shuffle a vector of objects.
 * 
 * @author diego
 *
 */
public class ShuffleHelper { 

    // swaps array elements i and j
    public static void swap(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // take as input an array of strings and rearrange them in random order
    public static void shuffle(Object[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + (int) (Math.random() * (N-i));   // between i and N-1
            swap(a, i, r);
        }
    }
}