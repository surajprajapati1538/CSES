import java.io.*;

public class StaticRangeQuery {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        // Read entire input into an array of strings
        String[] firstLine = br.readLine().split(" ");
        int n = Integer.parseInt(firstLine[0]);
        int q = Integer.parseInt(firstLine[1]);

        long[] pref = new long[n + 1];
        String[] arrayLine = br.readLine().split(" ");

        for (int i = 0; i < n; i++) {
            pref[i + 1] = pref[i] + Long.parseLong(arrayLine[i]);
        }

        for (int i = 0; i < q; i++) {
            String[] queryLine = br.readLine().split(" ");
            int l = Integer.parseInt(queryLine[0]);
            int r = Integer.parseInt(queryLine[1]);

            out.println(pref[r] - pref[l - 1]);
        }

        out.flush();
        out.close();
        br.close();
    }
}
