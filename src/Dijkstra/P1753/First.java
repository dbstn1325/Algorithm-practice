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

public class First {
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


        for(int i=1; i<=V; i++){
            if(i==K) {
                System.out.println(0);
                continue;
            }

            // 1  2 3
            // 2  3 4
            // 3  4
            // 4
            // 5  1

            bfs(i, new Node(K, 0));
            if(dp[i] == Integer.MAX_VALUE) {
                System.out.println("INF");
                continue;
            }
            System.out.println(dp[i]);
        }
    }

    public static void bfs(int target, Node startNode) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(startNode);

        while(!queue.isEmpty()) {
            Node curNode = queue.poll();

            for (Node nextNode : graph[curNode.node]) { // 1  2 3
//                System.out.println(nextNode.node);
                if(curNode.weight + nextNode.weight >= dp[target]) { //1->3->2 (4) (3)
                    continue;
                }

                if (nextNode.node == target) {

                    if (dp[target] == Integer.MAX_VALUE) {
                        dp[target] = curNode.weight + nextNode.weight;
                        continue;
                    }

                    if (dp[target] != Integer.MAX_VALUE) {
                        dp[target] = Math.min(dp[target], curNode.weight + nextNode.weight);
                        continue;
                    }
                }


                queue.add(new Node(nextNode.node, curNode.weight + nextNode.weight));
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
