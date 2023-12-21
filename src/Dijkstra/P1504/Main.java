package Dijkstra.P1504;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static int N, E;
    public static int[] dp;
    public static ArrayList<Node>[] graph;
    public static int[] mustNode = new int[2];

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Dijkstra/P1504/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        stk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        E = Integer.parseInt(stk.nextToken());

        graph = new ArrayList[N+1];
        for(int i=0; i<=N; i++){
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < E; i++) {
            stk = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(stk.nextToken());
            int to = Integer.parseInt(stk.nextToken());
            int weight = Integer.parseInt(stk.nextToken());

            graph[from].add(new Node(to, weight));
            graph[to].add(new Node(from, weight));
        }

        stk = new StringTokenizer(br.readLine());
        mustNode[0]=Integer.parseInt(stk.nextToken());
        mustNode[1]=Integer.parseInt(stk.nextToken());

        dp = new int[N+1];
        long first = bfs(new Node(1, 0), mustNode[0]) + bfs(new Node(mustNode[0], 0), mustNode[1]) + bfs(new Node (mustNode[1], 0), N);
        long second = bfs(new Node(1, 0), mustNode[1]) + bfs(new Node(mustNode[1], 0), mustNode[0]) + bfs(new Node (mustNode[0], 0), N);

        if(first >= Integer.MAX_VALUE && second >= Integer.MAX_VALUE) {
            System.out.println(-1);
            return;
        }

        long ans = 0;
        ans = first < second ? first : second;
        System.out.println(ans);
    }

    public static int bfs(Node startNode, int target) {
        if(startNode.node == target) return 0;

        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[startNode.node]=0;
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(startNode);

        while(!queue.isEmpty()) {
            Node curNode = queue.poll();

            if(curNode.node == target) {
                return dp[curNode.node];
            }

            for(Node nextNode: graph[curNode.node]) {
                if(dp[nextNode.node] > dp[curNode.node] + nextNode.cost) {
                    dp[nextNode.node] = dp[curNode.node] + nextNode.cost;
                    queue.add(new Node(nextNode.node, dp[nextNode.node]));
                }
            }
        }

        return Integer.MAX_VALUE;
    }

    static class Node implements Comparable<Node>{
        int node;
        int cost;

        public Node(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            if(this.cost > o.cost) {
                return 1;
            }
            return -1;
        }
    }
}
