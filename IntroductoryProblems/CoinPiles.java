
import java.util.*;

public class CoinPiles {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        int t = scan.nextInt();
        while(t -- > 0) {
            long a = scan.nextLong();
            long b = scan.nextLong();

            long sum = a + b;

            if(sum % 3 == 0) {
                long x = sum / 3;
                if(x <= a && x <= b) {
                    sb.append("YES\n");
                }
                else {
                    sb.append("NO\n");
                }
            }
            else {
                sb.append("NO\n");
            }
        }

        System.out.print(sb.toString());
        scan.close();
    }
}
