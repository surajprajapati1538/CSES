import java.util.Scanner;

public class DNA {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();

        int n = s.length();

        int i = 0;
        int max = 0;
        while(i < n) {
            int j = i;
            while(j < n && s.charAt(i) == s.charAt(j)) {
                j++;
            }

            max = Math.max(max, j - i);
            i = j;
        }

        System.out.print(max);
    }
}
