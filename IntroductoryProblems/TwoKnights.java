import java.util.Scanner;

public class TwoKnights {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();

        for(int k = 1; k <= n; k++) {
            System.out.println(calculateWays(k) + " ");
        }
    }

    public static long calculateWays(int k) {
        long ans = 0;

        // calculate total number of ways two knights can be arranged in k*k board
        // divide by 2, because both knights are identical
        long totalWays =  (long) (k*k) * (k*k - 1) / 2;

        // calculate the number of ways two knights can attack each other
        // they will attack each other, when they are in 2*3 or 3*2 positions
        // for every 2*3 or 3*2 position, there are 2 ways to arrange the knights such that they attack each other
        long attackingWays = 2 * (k-1) * (k-2) + 2 * (k-1) * (k-2);

        ans = totalWays - attackingWays;
        return ans;
    }
}
