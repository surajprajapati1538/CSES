import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TwoSets {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();

        long sum = (long) n * (n + 1) / 2;

       

        if(sum % 2 == 1) {
            System.out.println("NO");
        }
        else {
            System.out.println("YES");

            StringBuilder set1 = new StringBuilder();
            StringBuilder set2 = new StringBuilder();
            int set1Size = 1;
            int set2Size = 0;
            int[] vis = new int[n+1];
            long halfSum = sum / 2;
            long max = n;
            long set1Sum = 0;


            while(set1Sum < halfSum) {
                long rem = halfSum - set1Sum;
                
                if(rem > max) {
                    set1.append(max + " ");
                    vis[(int) max] = 1;
                    set1Sum += max;
                    max--;
                }
                else {
                    set1.append(rem);
                    set1Sum += rem;
                    vis[(int) rem] = 1;
                    break;
                }
                set1Size++;
            }


            for(int i=1; i<=n; i++) {
                if(vis[i] == 0) {
                    set2.append(i + " ");
                    set2Size++;
                }
            }

            System.out.println(set1Size);
            System.out.println(set1.toString());
            System.out.println(set2Size);
            System.out.println(set2.toString());
        }


    }
}
