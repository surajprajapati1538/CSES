import java.util.Arrays;

class TreeAncestor {
    int n;
    int[] par; 
    int max; // log(n) + 1
    int[][] table; // size [max][n]


    private void build(int[][] table, int[] par, int n) {
        // let's assume every node's parent at any distance is -1
        for(int[] t : table) {
            Arrays.fill(t, -1);
        }

        // table[row][node] -> (2 ^ row) parent of node 

        // first fill the 1st or (2^0)th parent of each node
        for(int node = 0; node < n; node++) {
            table[0][node] = par[node];
        }

        // fill the remaining places
        for(int row = 1; row < max; row++) {
            for(int node = 0; node < n; node++) {
                // find parent of the current node at distance of (2^(row-1))
                // table[row][node] = table[row-1][node] == -1 ? -1 : table[row-1][table[row-1][node]];

                int parent = table[row-1][node];
                table[row][node] = parent == -1 ? -1 : table[row-1][parent];
            }
        }
    }

    // finds the parent of node at kth distance up
    private int query(int node, int k) {
        for(int row=0; row < max; row++) {
            int mask = 1 << row;
            // find (2^row)th parent if bit is set at row
            // if bit is set at row from right
            if((k & mask) > 0) {
                // find the parent at row for node
                node = table[row][node];

                if(node == -1)
                    break;
            }
        }

        return node;  // kth parent
    }

    public TreeAncestor(int n, int[] parent) {
        this.n = n;
        this.max = 19; // max rows or height of tree
        this.par = parent;
        this.table = new int[max][n];
        build(table, parent, n); // build the sparse table to find the parents of each node at power of 2's distance up
    }
    
    public int getKthAncestor(int node, int k) {
        return query(node, k);
    }
}

/**
 * Your TreeAncestor object will be instantiated and called as such:
 * TreeAncestor obj = new TreeAncestor(n, parent);
 * int param_1 = obj.getKthAncestor(node,k);
 */


public class BinaryLifting {
    public static void main(String[] args) {
        int n = 7; // no. of nodes
        int[] par = new int[] {-1,0,0,1,1,2,2};  // par of nodes

        TreeAncestor treeAncestor = new TreeAncestor(n, par);

        int res1 = treeAncestor.getKthAncestor(3, 1);  // expected : 1
        int res2 = treeAncestor.getKthAncestor(5, 2); // expected : 0
        int res3 = treeAncestor.getKthAncestor(6, 3); // expected : -1

        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }
}