
import java.util.Scanner;   

public class TrailingZeroes {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int count = getCount(n);
        
        
        System.out.println(count);
    }

    public static int getCount(int n) {
        if(n < 5)
            return 0;

        return n / 5 + getCount(n / 5);
    }
}
