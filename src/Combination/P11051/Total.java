package Combination.P11051;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Total {
    public static long[][] dp;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Combination/P11051/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(stk.nextToken());
        int k = Integer.parseInt(stk.nextToken());

        dp = new long[n+1][k+1];
        System.out.println(combination(n,k));
    }

    public static long combination(int n, int k) {
        if(dp[n][k] != 0) {
            return dp[n][k];
        }

        if(k==n || k==0) {
            return 1;
        }

        dp[n][k] = combination(n - 1, k - 1) + combination(n-1, k);
        return (dp[n][k]) % 10007;
    }
}
