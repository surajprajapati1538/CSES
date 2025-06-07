
import java.util.*;
import java.io.*;


class SegmentTreeMin {
    private int n;
    int[] a;
    int[] t; // 1 based indexing

    public SegmentTreeMin(int n, int[] a) {
        this.n = n;
        this.a = a;
        this.t = new int[4*n];
        this.buildMin(1, 0, n-1);
    }

    // build the segment tree -> build(1, 0, n-1)
    // v -> current node, tl & tr -> current node range, a -> original array 
    public void buildMin(int v, int tl, int tr) {
        // if leaf node is reached
        if(tl == tr) {
            t[v] = a[tl];
            return;
        }

        int tm = (tl + tr) / 2;
        buildMin(2*v, tl, tm);
        buildMin(2*v+1, tm+1, tr);

        int minValue = Math.min(t[2*v], t[2*v + 1]);
        t[v] = minValue;
    }

    // sum query input question -> [ql, qr] : queryMin(1, 0, n-1, ql, qr)
    private int queryMin(int v, int tl, int tr, int ql, int qr) {
        // if not overlapping
        if(tl > qr || tr < ql) 
            return Integer.MAX_VALUE;
        
        // if completely overlapping
        if( tl >= ql && tr <= qr) 
            return t[v];

        // partially overlapping
        int tm = (tl + tr) / 2;
        int leftVal = queryMin(2*v, tl, tm, ql, qr);
        int rightVal = queryMin(2*v+1, tm+1, tr, ql, qr);
        return Math.min(leftVal, rightVal);
    }

    // point update -> pointUpdate(1, 0, n-1, id, value)
    private void pointUpdateMin(int v, int tl, int tr, int id, int value) {
        // if reached leaf node at index 'id'
        if(tl == id && tr == id) {
            t[v] = value;
            return;
        }

        // if outside the current node's range
        if(id > tr || id < tl)
            return;

        int tm = (tl + tr) / 2;
        if (id <= tm) {
            pointUpdateMin(2*v, tl, tm, id, value);
        } else {
            pointUpdateMin(2*v+1, tm+1, tr, id, value);
        }
        t[v] = Math.min(t[2*v], t[2*v+1]);
    }

    // end points function
    public int query(int ql, int qr) {
        return queryMin(1, 0, n-1, ql, qr);
    }

    public void pointUpdate(int id, int value) {
        pointUpdateMin(1, 0, n-1, id, value);
    }
}



public class DynamicRangeMinimumQuery {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);


        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        int[] arr = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }


        SegmentTreeMin seg = new SegmentTreeMin(n, arr);

        while(q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());

            if(type == 1) {
                int k = Integer.parseInt(st.nextToken());
                int u = Integer.parseInt(st.nextToken());

                seg.pointUpdate(k-1, u);
            }
            else {
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                int res = seg.query(a-1, b-1);
                out.println(res);
            }
        }

        out.flush();
        out.close();
        
    }
}
