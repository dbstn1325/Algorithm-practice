package DP.P12865;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class TopDown {

    public static int N, K;
    public static int[][] bag, dp;
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DP/P12865/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;
        stk = new StringTokenizer(br.readLine());

        N = Integer.parseInt(stk.nextToken());
        K = Integer.parseInt(stk.nextToken());

        bag = new int[N][2];
        dp = new int[100][100001];
        for(int i=0; i<N; i++){
            stk = new StringTokenizer(br.readLine());
            bag[i][0] = Integer.parseInt(stk.nextToken());
            bag[i][1] = Integer.parseInt(stk.nextToken());
        }

        Arrays.stream(dp)
                .forEach(d -> Arrays.fill(d, -1));

        System.out.println(bagPack(0, 0));
    }

    public static int bagPack(int num, int cap) {
        if(cap > K) {
            return -1000;
        }
        if(num == N) {
            return 0;
        }

        if(dp[num][cap] != -1) {
            return dp[num][cap];
        }

        dp[num][cap] = Math.max(bagPack(num + 1, cap + bag[num][0]) + bag[num][1], bagPack(num + 1, cap));
        return dp[num][cap];
    }
}
