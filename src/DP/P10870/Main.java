package DP.P10870;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DP/P10870/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        long[] p = new long[91];

        p[0] = 0;
        p[1] = 1;
        for(int i=2; i<=N; i++){
            p[i] = p[i-1] + p[i-2];
        }

        System.out.println(p[N]);

    }
}
