

import java.util.Scanner;

class MissingNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int ans = 0;
        for(int i=0; i<n-1; i++) {
            ans = ans ^ sc.nextInt();
            ans = ans ^ (i + 1);
        }

        ans = ans ^ n; 
        System.out.print(ans);
    }
}