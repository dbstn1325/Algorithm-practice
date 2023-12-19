package Dijkstra.P1753;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Second {
    public static int V, E, K;
    public static int dp[];
    public static List<Node>[] graph;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Dijkstra/P1753/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        stk = new StringTokenizer(br.readLine());
        V = Integer.parseInt(stk.nextToken());
        E = Integer.parseInt(stk.nextToken());

        K = Integer.parseInt(br.readLine());

        graph = new ArrayList[20001];
        for(int i=0; i<=V; i++){
            graph[i] = new ArrayList<>();
        }

        for(int i=0; i<E; i++){
            stk = new StringTokenizer(br.readLine());

            int u = Integer.parseInt(stk.nextToken());
            int v = Integer.parseInt(stk.nextToken());
            int w = Integer.parseInt(stk.nextToken());

            graph[u].add(new Node(v, w));
        }


        dp = new int[V+1];
        Arrays.fill(dp, Integer.MAX_VALUE);


        bfs(new Node(K, 0));
        for(int i=1; i<=V; i++){
            if(i==K) {
                System.out.println(0);
                continue;
            }

            if(dp[i] == Integer.MAX_VALUE) {
                System.out.println("INF");
                continue;
            }
            System.out.println(dp[i]);
        }
    }

    public static void bfs(Node startNode) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(startNode);

        while(!queue.isEmpty()) {
            Node curNode = queue.poll();
            for (Node nextNode : graph[curNode.node]) {
//                System.out.println(nextNode.node);
                if (dp[nextNode.node] > curNode.weight + nextNode.weight) {
                    dp[nextNode.node] = curNode.weight + nextNode.weight;
                    queue.add(new Node(nextNode.node, dp[nextNode.node]));
                }

                if (dp[nextNode.node] == Integer.MAX_VALUE) {
                    dp[nextNode.node] = curNode.weight + nextNode.weight;
                }
            }
        }
    }

    public static class Node {
        int node;
        int weight;

        public Node(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }
}
