package Graph.P14621;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static Parent[] parent;
    static PriorityQueue<Node> pq = new PriorityQueue<>();

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Graph/P14621/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());

        parent = new Parent[N+1];
        stk = new StringTokenizer(br.readLine());
        for(int i=1; i<=N; i++) {
            parent[i] = new Parent(i, stk.nextToken());
        }

        for(int i=0; i<M; i++) {
            stk = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(stk.nextToken());
            int v = Integer.parseInt(stk.nextToken());
            int d = Integer.parseInt(stk.nextToken());

            pq.offer(new Node(u, v, d));
        }

        kruskal();
    }

    static void kruskal() {
        int weight = 0;
        int connect = 0;

        while(!pq.isEmpty()) {
            Node curNode = pq.poll();

            int uParent = find(curNode.u);
            int vParent = find(curNode.v);

            if(uParent == vParent) continue;
            if(parent[curNode.u].signal.equals(parent[curNode.v].signal)) continue;

            union(curNode.u, curNode.v);
            weight+= curNode.d;
            connect++;
        }

        int ans = connect == N-1 ? weight : -1;
        System.out.println(ans);
    }

    static void union(int uNode, int vNode) {
        int uParent = find(uNode);
        int vParent = find(vNode);

        if(uParent < vParent) {
            parent[vParent].parent = uParent;
            return ;
        }

        parent[uParent].parent = vParent;
    }

    static int find(int targetNode) {
        if(parent[targetNode].parent == targetNode) {
            return targetNode;
        }

        return find(parent[targetNode].parent);
    }

    static class Parent {
        int parent;
        String signal;

        public Parent(int parent, String signal) {
            this.parent = parent;
            this.signal = signal;
        }
    }

    static class Node implements Comparable<Node> {
        int u;
        int v;
        int d;

        public Node(int u, int v, int d) {
            this.u = u;
            this.v = v;
            this.d = d;
        }

        @Override
        public int compareTo(Node o) {
            return this.d - o.d;
        }
    }
}
