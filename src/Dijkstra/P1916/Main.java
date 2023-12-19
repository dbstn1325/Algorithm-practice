package Dijkstra.P1916;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static int N, M;
    public static ArrayList<Node>[] city;
    public static int[] dp;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Dijkstra/P1916/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        city = new ArrayList[1001];
        for(int i=0; i<=N; i++){
            city[i] = new ArrayList<>();
        }

        for(int i=0; i<M; i++){
            stk = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(stk.nextToken());
            int end = Integer.parseInt(stk.nextToken());
            int cost = Integer.parseInt(stk.nextToken());

            city[start].add(new Node(end, cost));
        }

        stk = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(stk.nextToken());
        int end = Integer.parseInt(stk.nextToken());

        dp = new int[N+1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        bfs(new Node(start , 0), end);
        System.out.println(dp[end]);
    }

    public static void bfs(Node startNode, int target) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.add(startNode);

        while(!queue.isEmpty()) {
            Node curNode = queue.poll();

            if(curNode.cost > dp[target]) {
                continue;
            }

            for(Node nextNode: city[curNode.node]) {
                if(dp[nextNode.node] > curNode.cost + nextNode.cost) {
                    dp[nextNode.node] = curNode.cost + nextNode.cost;
                    queue.add(new Node(nextNode.node, curNode.cost + nextNode.cost));
                }
            }
        }

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
