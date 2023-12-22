package DP.P13549;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    public static int N, K;
    public static int[] dp;
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DP/P13549/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;
        stk = new StringTokenizer(br.readLine());

        N = Integer.parseInt(stk.nextToken());
        K = Integer.parseInt(stk.nextToken());

        if(N >= K) {
            System.out.println(N-K);
            return ;
        }

        dp = new int[100001];
        Arrays.fill(dp, -1);
        dp[N] = 0;
        System.out.println(bfs(N, K));
    }

    static int bfs(int N, int K) {
        Queue<Integer> queue = new LinkedList();
        queue.add(N);


        while(!queue.isEmpty()) {
            int now = queue.poll();

            if(now == K)
                return dp[now];

            int temp = now * 2;
            while(temp <= 100000 && dp[temp] == -1) {
                dp[temp] = dp[now];
                queue.add(temp);
                temp *= 2;
            }

            for(int i=0; i<2; i++) {
                int next = 0;

                if(i==0) {
                    next = now - 1;
                }
                if(i!=0) {
                    next = now + 1;
                }

                if(0 <= next && next <= 100000) {
                    if(dp[next] == -1) {
                        queue.add(next);
                        dp[next] = dp[now] + 1;
                    }
                }
            }
        }

        return 0;
    }
}
