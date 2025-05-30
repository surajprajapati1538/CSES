import java.util.*;
import java.io.*;

public class StaticRangeMinimumQuery {
    static int n, q;
    static int[] arr;
    static int[] log;
    static int[][] st;

    public static void main(String[] args) throws IOException {	
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        StringTokenizer stt = new StringTokenizer(br.readLine());
        n = Integer.parseInt(stt.nextToken());
        q = Integer.parseInt(stt.nextToken());

        arr = new int[n];
        stt = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(stt.nextToken());
        }

        calcLog();
        buildSparseTable();

        while (q-- > 0) {
            stt = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(stt.nextToken()) - 1;
            int r = Integer.parseInt(stt.nextToken()) - 1;
            out.println(queryRes(l, r));
        }

        out.flush();
    }

    public static int queryRes(int l, int r) {
        int len = r - l + 1;
        int k = log[len];
        return Math.min(st[l][k], st[r - (1 << k) + 1][k]);
    }

    public static void calcLog() {
        log = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            log[i] = log[i / 2] + 1;
        }
    }

    public static void buildSparseTable() {
        int maxLog = log[n] + 1;
        st = new int[n][maxLog];

        for (int i = 0; i < n; i++) {
            st[i][0] = arr[i];
        }

        for (int j = 1; (1 << j) <= n; j++) {
            for (int i = 0; i + (1 << j) <= n; i++) {
                st[i][j] = Math.min(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
            }
        }
    }
}
