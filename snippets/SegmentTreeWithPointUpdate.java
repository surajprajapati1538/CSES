
class SegmentTreeSum {
    private int n;
    int[] a;
    int[] t; // 1 based indexing

    public SegmentTreeSum(int n, int[] a) {
        this.n = n;
        this.a = a;
        this.t = new int[4*n];
        this.buildSum(1, 0, n-1);
    }

    // build the segment tree -> build(1, 0, n-1)
    // v -> current node, tl & tr -> current node range, a -> original array 
    public void buildSum(int v, int tl, int tr) {
        // if leaf node is reached
        if(tl == tr) {
            t[v] = a[tl];
            return;
        }

        int tm = (tl + tr) / 2;
        buildSum(2*v, tl, tm);
        buildSum(2*v+1, tm+1, tr);
        t[v] = t[2*v] + t[2*v + 1];
    }

    // sum query input question -> [ql, qr] : querySum(1, 0, n-1, ql, qr)
    private int querySum(int v, int tl, int tr, int ql, int qr) {
        // if not overlapping
        if(tl > qr || tr < ql) 
            return 0;
        
        // if completely overlapping
        if( tl >= ql && tr <= qr) 
            return t[v];

        // partially overlapping
        int tm = (tl + tr) / 2;
        int leftVal = querySum(2*v, tl, tm, ql, qr);
        int rightVal = querySum(2*v+1, tm+1, tr, ql, qr);
        return leftVal + rightVal;
    }

    // point update -> pointUpdate(1, 0, n-1, id, value)
    private void pointUpdateSum(int v, int tl, int tr, int id, int value) {
        // if reached leaf node at index 'id'
        if(tl == id && tr == id) {
            t[v] = value;
            return;
        }

        // if outside the current node's range
        if(id > tr || id < tl)
            return;

        int tm = (tl + tr) / 2;
        pointUpdateSum(2*v, tl, tm, id, value);
        pointUpdateSum(2*v+1, tm+1, tr, id, value);
        t[v] = t[2*v] + t[2*v+1];
    }

    // end points function
    public int query(int ql, int qr) {
        return querySum(1, 0, n-1, ql, qr);
    }

    public void pointUpdate(int id, int value) {
        pointUpdateSum(1, 0, n-1, id, value);
    }
}

public class SegmentTreeWithPointUpdate {
    public static void main(String[] args) {
        int[] arr = new int[] {3, 2, -1, 4, 0};

        SegmentTreeSum seg = new SegmentTreeSum(arr.length, arr);
        System.out.println(seg.query(1, 3));
        seg.pointUpdate(2, 5);
        System.out.println(seg.query(0, 4));


    }
}
