

class Node {
    // initials value -> identityValue
    // we can use more variables depending on the question requirements
    int v = 0;

    // Identity Node
    public Node() {}

    // custom initialization
    public Node(int value) {
        this.v = value;
    }

    // merge left and right childrens
    // merge logic can change 
    public void merge(Node left, Node right) {
        this.v = left.v + right.v;  // adition operation
    }
}

class Update {
    // initial value -> identity value
    int v = 0;

    // IdentityTransformation / Identity for UnPropUpd
    public Update() {}

    // custom constructor for initialization
    public Update(int val) {
        this.v = val;
    }

    // combine the updates
    // combine logic can change
    public void combine(Update otherUpdate, int tl, int tr) {
        this.v += otherUpdate.v;  // addition operation 
    }

    // apply the updates to a node(child nodes during propagation)
    // apply logic can change
    public void apply(Node node, int tl, int tr) {
        node.v += (tr - tl + 1) * this.v;  // addition operation
    }

}





class SegmentTreeGenericImpl {
    private int n;
    private int[] a; // actual array (0-based indexing)
    private Node[] t;  // segment tree (1-based indexing)
    private Update[] unPropUpd; // unpropagated values for a node
    private boolean[] isLazy;  // tells if a node is having unpropagated values or not
    private final Node identityNode;  // identity element
    private final Update identityUpdate; // identity transformation


   

    public SegmentTreeGenericImpl(int n, int[] a) {
        this.n = n;
        this.a = a;
        this.t = new Node[4*n];
        this.unPropUpd = new Update[4*n];
        this.isLazy = new boolean[4*n];
        this.identityNode = new Node();
        this.identityUpdate = new Update();


        for(int i=0; i<4*n; i++) {
            unPropUpd[i] = new Update();
            isLazy[i] = false;
            t[i] = new Node();
        }
    }



    // build the segment tree
    // v -> tree ind, tl & tr -> range for 'a'
    // call -> buildSegmentTree(1, 0, n-1)
    private void buildSegmentTree(int v, int tl, int tr) {
        // reached the leaf node
        if(tl == tr) {
            t[v] = new Node(a[tl]);
            return;
        }

        // go left and right childrens
        int tm = (tl + tr) / 2;
        buildSegmentTree(2*v, tl, tm);
        buildSegmentTree(2*v+1, tm+1, tr);

        // merge left and right nodes to current node
        t[v] = new Node();
        t[v].merge(t[2*v], t[2*v+1]);
    }


    // range query operation on segment tree
    // call -> rangeQueryOnST(1, 0, n-1, ql, qr)
    private Node rangeQueryOnST(int v, int tl, int tr, int ql, int qr) {
        // if outside the query range -> no overlapping
        if(tr < ql || tl > qr) {
            return identityNode;
        }

        // if complete overlapping
        if(ql <= tl && tr <= qr) {
            return t[v];
        }

        // partial overlapping

        // first, push down the unpropagated values
        pushDown(v, tl, tr);

        int tm = (tl + tr) / 2;
        Node leftNode = rangeQueryOnST(2*v, tl, tm, ql, qr);
        Node rightNode = rangeQueryOnST(2*v+1, tm+1, tr, ql, qr);

        // merge left and right nodes and return answer
        Node ans = new Node();
        ans.merge(leftNode, rightNode);
        return ans;
    }


    // range update on Segment Tree
    // call -> rangeUpdateOnST(1, 0, n-1, ql, qr)
    private void rangeUpdateOnST(int v, int tl, int tr, int ql, int qr, Update upd) {
        // if outside the query range -> no overlapping
        if(tl > qr || tr < ql)
            return;

        // full overlapping
        if(ql <= tl && tr <= qr) {
            apply(v, tl, tr, upd);
            return;
        }

        // partial overlapping

        // first, propagate the unpropagated values
        pushDown(v, tl, tr);

        int tm = (tl + tr) / 2;
        rangeUpdateOnST(2*v, tl, tm, ql, qr, upd);
        rangeUpdateOnST(2*v+1, tm+1, tr, ql, qr, upd);

        // merge left and right node to current node
        t[v].merge(t[2*v], t[2*v+1]);
    }


    // helps propagate the current unpropagated values to it's childrens
    // call -> pushDown(v, tl, tr)
    private void pushDown(int v, int tl, int tr) {
        // if current node in not lazy
        if(!isLazy[v]) {
            return;
        }   

        // if lazy, make it non lazy
        isLazy[v] = false;
        int tm = (tl + tr) / 2;
        apply(2*v, tl, tm, unPropUpd[v]);  // propagate to left child
        apply(2*v+1, tm+1, tr, unPropUpd[v]);  // propagate to right child
        unPropUpd[v] = identityUpdate;  // after after childrens, make it indetityUpdate 
    }

    // apply the updates to a node
    // call -> apply(v, tl, tr, upd)
    private void apply(int v, int tl, int tr, Update upd) {
        // leaf nodes can't be lazy 
        // if this is not leaf node
        if(tl != tr) {
            isLazy[v] = true;
        }
        unPropUpd[v].combine(upd, tl, tr);  // stacking up the unpropagated values to the current node
        // finally, update the tree with unpropagated value at the current node
        unPropUpd[v].apply(t[v], tl, tr);
    }



    // method endpoints

    // build the ST
    public void build() {
        buildSegmentTree(1, 0, n-1);
    }

    // query on ST
    public int query(int ql, int qr) {
        Node ans = rangeQueryOnST(1, 0, n-1, ql, qr);
        return ans.v;
    }

    // range update of ST
    public void rangeUpdate(int l, int r, int val) {
        rangeUpdateOnST(1, 0, n-1, l, r, new Update(val));
    }       
}


public class SegmentTreeGeneric {
    public static void main(String[] args) {
        System.out.println("Hello World");
        int[] a = {1, 3, 5, 7, 9, 11}; // Initial array of length 6

        SegmentTreeGenericImpl st = new SegmentTreeGenericImpl(a.length, a);
        st.build();

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
        System.out.println("Sum at index 5: " + st.query(5, 5)); // Expectated: 11 + 4 = 15

    }
}
