
class SegmentTreeSum {
    private int n;
    int[] a;
    int[] unPropUpd; // unpropagated update
    boolean[] isLazy; // to track lazy nodes
    int[] t; // 1 based indexing
    String rangeUpdateType;

    public SegmentTreeSum(int n, int[] a, String rangeUpdateType) {
        this.n = n;
        this.a = a;
        this.t = new int[4*n];
        this.unPropUpd = new int[4*n];
        this.isLazy = new boolean[4*n];
        this.rangeUpdateType = rangeUpdateType;
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

        // first pushDown the unpropagated values to it's childrens
        pushDown(v, tl, tr);

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

        // if outside the current node's range or not overlapping
        if(id > tr || id < tl)
            return;
        
        int tm = (tl + tr) / 2;
        pointUpdateSum(2*v, tl, tm, id, value);
        pointUpdateSum(2*v+1, tm+1, tr, id, value);
        t[v] = t[2*v] + t[2*v+1];
    }

    // range update
    private void rangeUpdateSum(int v, int tl, int tr, int l, int r, int newValue) {
        // if not overlapping
        if(tr < l || tl > r) {
            return;
        }

        // if completely overlapping
        if(l <= tl && tr <= r) {
            apply(v, tl, tr, newValue);
            return;
        }

        // partially overlapping

        // first pushDown the unpropagated values to it's childrens
        pushDown(v, tl, tr);

        int tm = (tl + tr) / 2;
        rangeUpdateSum(2*v, tl, tm, l, r, newValue);
        rangeUpdateSum(2*v+1, tm+1, tr, l, r, newValue);
        t[v] = t[2*v] + t[2*v+1];
    }

    // push down the unpropagated values to it's childrens
    // pushDown(v, tl, tr)
    private void pushDown(int v, int tl, int tr) {
        // if the current node is not lazy or does not contain any unpropagated value
        if(!isLazy[v]) {
            return;
        }

        // if lazy, invert it to not lazy and propagate the changes to it's childrens
        isLazy[v] = false;
        int tm = (tl + tr) / 2;
        apply(2*v, tl, tm, unPropUpd[v]);
        apply(2*v+1, tm+1, tr, unPropUpd[v]);
        // make the current unpropagated value to 0 after propagating to it's childrens
        unPropUpd[v] = 0;
    }

    // apply the changes to the node (update or upgrade)
    private void apply(int v, int tl, int tr, int newValue) {
        if(rangeUpdateType.equals("update")) {
            update(v, tl, tr, newValue);
        }
        else if(rangeUpdateType.equals("upgrade")) {
            upgrade(v, tl, tr, newValue);
        }
    }

    // in case it's an update operation
    private void update(int v, int tl, int tr, int newValue) {
        // check if it's leaf node or not
        if(tl != tr) {
            isLazy[v] = true;
            unPropUpd[v] += newValue;
        }

        t[v] += (tr - tl + 1) * newValue;
    }

    // in case it's an upgrade/replace operation
    private void upgrade(int v, int tl, int tr, int newValue) {
        // check if it's leaf node or not
        if(tl != tr) {
            isLazy[v] = true;
            unPropUpd[v] = newValue;
        }

        t[v] = (tr - tl + 1) * newValue;
    }



    // end points function
    public int query(int ql, int qr) {
        return querySum(1, 0, n-1, ql, qr);
    }

    public void pointUpdate(int id, int value) {
        pointUpdateSum(1, 0, n-1, id, value);
    }

    public void rangeUpdate(int l, int r, int value) {
        rangeUpdateSum(1, 0, n-1, l, r, value);
    }
}

public class SegmentTreeWithLazyUpdate {
    public static void main(String[] args) {
        // int[] arr = new int[] {3, 2, -1, 4, 0};

        // SegmentTreeSum seg = new SegmentTreeSum(arr.length, arr, "update");


        // System.out.println(seg.query(1, 3)); // 5
        // seg.pointUpdate(2, 5); // {3, 2, 5, 4, 0} -> totalSum = 14
        // System.out.println(seg.query(0, 4)); // 14

        // seg.rangeUpdate(1, 3, 5); // {3, 7, 10, 9, 0} -> totalSum = 29
        // System.out.println(seg.query(2, 4)); // 19






        int[] a = {1, 3, 5, 7, 9, 11}; // Initial array of length 6

        // segment tree
        SegmentTreeSum st = new SegmentTreeSum(a.length, a, "update");

        // Initial queries
        System.out.println("Initial range sum [0, 5]: " + st.query(0, 5)); // Expected: 36
        System.out.println("Initial range sum [1, 3]: " + st.query(1, 3)); // Expected: 15 (3+5+7)

        // Apply range update: add 2 to [1, 3]
        st.rangeUpdate(1, 3, 2);
        System.out.println("After update (+2 on [1,3]), sum [1, 3]: " + st.query(1, 3)); // Expected: 21 (5+7+9)
        System.out.println("Sum [0, 5]: " + st.query(0, 5)); // Expected: 42

        // Apply another update: add 5 to [2, 4]
        st.rangeUpdate(2, 4, 5);
        System.out.println("After update (+5 on [2,4]), sum [2, 4]: " + st.query(2, 4)); // Expected: 26 (12+14+0)
        System.out.println("Sum [0, 5]: " + st.query(0, 5)); // Expected: 42 + (5+5+5) = 57

        // Update edge elements
        st.rangeUpdate(0, 0, 3); // Add 3 to index 0
        st.rangeUpdate(5, 5, 4); // Add 4 to index 5
        System.out.println("After edge updates, sum [0, 5]: " + st.query(0, 5)); // Expected: 57 + 3 + 4 = 64
        System.out.println("Sum at index 0: " + st.query(0, 0)); // Expected: 1 + 3 = 4
        System.out.println("Sum at index 5: " + st.query(5, 5)); // Expected: 11 + 4 = 15


    }
}
