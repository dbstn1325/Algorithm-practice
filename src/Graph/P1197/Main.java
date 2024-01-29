package Graph.P1197;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static int V, E;
    static int[] parent;
    static PriorityQueue<Node> pq;
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Graph/P1197/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());
        V = Integer.parseInt(stk.nextToken());
        E = Integer.parseInt(stk.nextToken());

        parent = new int[V+1];
        pq = new PriorityQueue<>();
        for(int i=1; i<=V; i++) {
            parent[i] = i;
        }

        for(int i=0; i<E; i++) {
            stk = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(stk.nextToken());
            int to = Integer.parseInt(stk.nextToken());
            int weight = Integer.parseInt(stk.nextToken());

            pq.offer(new Node(from, to, weight));
        }

        kruskal();
    }

    static void kruskal() {
        int weight = 0;

        while(!pq.isEmpty()) {
            Node curNode = pq.poll();

            int fromParent = find(curNode.from);
            int toParent = find(curNode.to);
            if(fromParent != toParent) {
                union(curNode.from, curNode.to);
                weight += curNode.weight;
            }
        }

        System.out.println(weight);
    }

    static int find(int targetNode) {
        if(parent[targetNode] == targetNode) {
            return targetNode;
        }

        return find(parent[targetNode]);
    }

    static void union(int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        if(p1 < p2) {
            parent[p2] = p1;
            return ;
        }

        parent[p1] = p2;
    }


    static class Node implements Comparable<Node> {
        int from;
        int to;
        int weight;

        public Node(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node other) {
            return this.weight - other.weight;
        }
    }

}
