import java.util.Scanner;

public class BitStrings {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int mod = (int) 1e9 + 7;
        long ans = 1;

        for(int i=0; i<n; i++) {
            ans = (ans * 2) % mod;
        }

        System.out.println(ans);
    }
}
