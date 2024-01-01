package Combination.P11050;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Combination/P11050/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(stk.nextToken());
        int k = Integer.parseInt(stk.nextToken());

        System.out.println(combination(n,k));
    }

    public static int combination(int n, int k) {
        if(k==n || k==0) {
            return 1;
        }

        return combination(n-1, k-1) + combination(n-1, k);
    }
}
