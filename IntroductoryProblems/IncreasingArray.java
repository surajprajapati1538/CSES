
import java.util.*;
class IncreasingArray {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();

        int[] arr = new int[n];
        for(int i=0; i<n; i++) {
            arr[i] = scan.nextInt();
        }

        long op = 0;
        for(int i=1; i<n; i++) {
            if(arr[i-1] - arr[i] > 0) {
                op += arr[i-1] - arr[i];
                arr[i] = arr[i-1];
            }
        }

        System.out.println(op);
    }
}