package Dijkstra.P1238;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static int N, M, X;
    public static ArrayList<Node>[] graph;
    public static int[][] dp;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Dijkstra/P1238/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        stk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());
        X = Integer.parseInt(stk.nextToken());

        graph = new ArrayList[N+1];
        for(int i=0; i<=N; i++){
            graph[i] = new ArrayList<>();
        }

        for(int i=0; i<M; i++){
            stk = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(stk.nextToken());
            int end = Integer.parseInt(stk.nextToken());
            int time = Integer.parseInt(stk.nextToken());

            graph[start].add(new Node(end, time));
        }

        dp = new int[N+1][N+1];
        Arrays.stream(dp)
                    .forEach(v -> Arrays.fill(v, Integer.MAX_VALUE));

        bfs(new Node(X, 0), X);
        for(int i=1; i<=N; i++){
            if(i == X) continue;
             bfs(new Node(i, 0), i);
        }

        dp[X][X] = 0;
        int max = Integer.MIN_VALUE;
        for(int i=1; i<=N; i++){
            max = Math.max(max, dp[i][X]+dp[X][i]);
        }

        System.out.println(max);
    }

    public static void bfs(Node startNode, int num) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(startNode);

        while(!queue.isEmpty()) {
            Node curNode = queue.poll();

            for (Node nextNode : graph[curNode.node]) {
                if(dp[nextNode.node][num] > curNode.time + nextNode.time) {
                    dp[nextNode.node][num] = curNode.time + nextNode.time;
                    queue.add(new Node(nextNode.node, curNode.time + nextNode.time));
                }
            }
        }
    }


    static class Node implements Comparable<Node>{
        int node;
        int time;

        public Node(int node, int time) {
            this.node = node;
            this.time = time;
        }

        @Override
        public int compareTo(Node o) {
            if(this.time > o.time) {
                return 1;
            }
            return -1;
        }
    }
}
