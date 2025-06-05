import java.util.*;
import java.io.*;

public class DynamicRangeSumQueries {

    private static int n;
    private static int q;
    private static long[] arr;
    private static long[] fenwick;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());

        arr = new long[n + 1];
        fenwick = new long[n + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            arr[i] = Long.parseLong(st.nextToken());
            update(i, arr[i]);
        }

        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                int k = Integer.parseInt(st.nextToken());
                long u = Long.parseLong(st.nextToken());

                update(k, u - arr[k]);
                arr[k] = u;
            } else {
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                long ans = query(b) - query(a - 1);
                out.println(ans);
            }
        }

        out.flush();
        out.close();
    }

    private static void update(int id, long value) {
        while (id <= n) {
            fenwick[id] += value;
            id += (id & -id);
        }
    }

    private static long query(int id) {
        long ans = 0;
        while (id > 0) {
            ans += fenwick[id];
            id -= (id & -id);
        }
        return ans;
    }
}
