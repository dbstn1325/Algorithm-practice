package DP.P2748;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TopDown {
    public static long[] d;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DP/P2748/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        d = new long[N+1];

        Arrays.fill(d, -1);
        System.out.println(fibo(N));
    }

    public static long fibo(int num) {
        if(num==0) {
            return 0;
        }
        if(num==1) {
            return 1;
        }

        if(d[num] != -1) {
            return d[num];
        }

        return d[num] = fibo(num-1) + fibo(num-2);
    }
}
