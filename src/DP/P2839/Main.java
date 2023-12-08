package DP.P2839;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DP/P2839/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        //sugar: 설탕이 N개 있을 때 최소한의 필요한 봉지수
        int[] sugar = new int[5001];
        Arrays.fill(sugar, 10000);
        sugar[3] = 1;
        sugar[5] = 1;
        if(N>=6) {
            for (int i=6; i<=N; i++) {
                if(i%5==0) {
                    sugar[i] = sugar[5]+sugar[i-5];
                }
                if(i%3==0) {
                    sugar[i] = sugar[3]+sugar[i-3];
                }
                if(sugar[i-3]!=10000 && sugar[i-5]!=10000) {
                    sugar[i] = Math.min(sugar[3]+sugar[i-3], sugar[5]+sugar[i-5]);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        if(sugar[N]==10000) {
            sb.append(-1);
        }
        if(sugar[N]!=10000) {
            sb.append(sugar[N]);
        }

        System.out.println(sb);
    }
}
