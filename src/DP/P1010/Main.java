package DP.P1010;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static int T;
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DP/P1010/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        T = Integer.parseInt(br.readLine());

        int[][] load = new int[31][31];

        for(int i=1; i<31; i++){
            load[0][i] = 1;
        }

        for(int i=1; i<31; i++){
            load[i][i] = 1;
            for(int j=i+1; j<31; j++){
                load[i][j] = load[i-1][j-1] + load[i][j-1];
            }
        }

        //load[N][M]: 다리갯수가 N개 있을 때, M개 다리와 연결할 수 있는 최소한의 필요한 다리 갯수
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<T; i++){
            stk = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(stk.nextToken());
            int m = Integer.parseInt(stk.nextToken());

            sb.append(load[n][m]).append("\n");
        }
        System.out.println(sb);

    }
}
