import java.util.Scanner;

public class Permutations {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        if(n == 1) {
            System.out.print("1");
            return;
        }

        if(n <= 3) {
            System.out.print("NO SOLUTION");
            return;
        }

        StringBuilder str = new StringBuilder();

        for(int i=n-1; i>=1; i-=2) {
            str.append(i).append(" ");
        }

        for(int i=n; i>=1; i-=2) {
            str.append(i).append(" ");
        }

        System.out.println(str.toString());
    }
}
