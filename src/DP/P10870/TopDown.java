package DP.P10870;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TopDown {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DP/P10870/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        System.out.println(fibo(N));
    }

    public static int fibo(int n) {
        if(n==0) return 0;
        if(n==1) return 1;

        return fibo(n-1) + fibo(n-2);
    }
}
