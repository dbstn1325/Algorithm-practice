package DP.P2579;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DP/P2579/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] num = new int[N+1];
        for(int i=1; i<=N; i++) {
            int n = Integer.parseInt(br.readLine());
            num[i] = n;
        }

        //stairs: N번째까지 규칙에 의거해 정해진 최대값
        int[] stairs = new int[N+1];
        for(int i=1; i<=N; i++){
            if(i==1) stairs[1] = num[1];
            if(i==2) stairs[2] = stairs[1] + num[2];

            if(i>=3) {
                stairs[i] = Math.max(stairs[i-3]+num[i-1], stairs[i-2]) + num[i];
            }
        }
        System.out.println(stairs[N]);



    }
}
