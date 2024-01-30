package Graph.P1976;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int N, M;
    static Queue<Node> queue = new LinkedList<>();
    static int[] parent;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Graph/P1976/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        parent = new int[N+1];
        for(int i=1; i<=N; i++) {
            parent[i] = i;
        }

        StringTokenizer stk;
        for(int i=1; i<=N; i++) {
            stk = new StringTokenizer(br.readLine());

            for(int j=1; j<=N; j++) {
                if(Integer.parseInt(stk.nextToken()) == 1) {
                    queue.offer(new Node(i, j));
                };
            }
        }

        while(!queue.isEmpty()) {
            Node curNode = queue.poll();

            if(find(curNode.from) == find(curNode.to)) continue;

            union(curNode.from, curNode.to);
        }

        stk = new StringTokenizer(br.readLine());
        int parent = 0;
        int corret = 1;
        for(int i=0; i<M; i++) { //N인지 M인지 잘 보도록.
            int findP = find(Integer.parseInt(stk.nextToken()));
            if(parent != findP) {
                parent = findP;
                continue;
            }
            corret ++;
        }

        String ans = corret == M ? "YES" : "NO";
        System.out.println(ans);
    }

    static void union(int from, int to) {
        int fromP = find(from);
        int toP = find(to);

        if(fromP < toP) {
            parent[toP] = fromP;
            return ;
        }

        parent[fromP] = toP;
    }

    static int find(int t) {
        if(parent[t] == t) {
            return t;
        }

        return parent[t] = find(parent[t]);
    }

    static class Node {
        int from;
        int to;

        public Node(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }
}
