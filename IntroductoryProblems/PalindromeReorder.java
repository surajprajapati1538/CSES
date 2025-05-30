
import java.util.*;

public class PalindromeReorder {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        String result = reorder(input);
        System.out.println(result);
    }

    public static String reorder(String str) {
        StringBuilder sb = new StringBuilder(str);

        int[] freq = new int[26];
        int oddCount = 0;
       
        for (char c : str.toCharArray()) {
            freq[c - 'A']++;
            if (freq[c - 'A'] % 2 == 0) {
                oddCount--;
            } else {
                oddCount++;
            }
        }

        if(oddCount > 1) {
            return "NO SOLUTION";
        }

        StringBuilder firstHalf = new StringBuilder();
        StringBuilder secondHalf = new StringBuilder();
        char middleChar = '*';
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] % 2 == 1) {
                middleChar = (char) (i + 'A');
            }
            for (int j = 0; j < freq[i] / 2; j++) {
                firstHalf.append((char) (i + 'A'));
            }
        }
        secondHalf.append(firstHalf); 
        secondHalf.reverse();

        if (middleChar != '*') {
            firstHalf.append(middleChar);
        }
        firstHalf.append(secondHalf);

        return firstHalf.toString();
    }
}
