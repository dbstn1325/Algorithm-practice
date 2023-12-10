package SWEA.P3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SWEA/P3/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;
        int N = Integer.parseInt(br.readLine());

        //work: N번째부터 M번쨰까지의 합
        int[][] work = new int[100][100];
        for(int i=0; i<N; i++) {
            Arrays.fill(work[i], 0);
        }
        for(int i=0; i<N; i++){
            stk = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(stk.nextToken());
            int p = Integer.parseInt(stk.nextToken());

            work[i][i+(t-1)] = p;
        }
//        System.out.println(work[0][1]);
//        System.out.println(work[1][2]);
//        System.out.println(work[2][2]);

        for(int i=1; i<N; i++){
            work[1][i] = Math.max(work[1][i-1] + work[i][i], work[1][i]);
//            work[0][i] = Math.max(work[0][i-1] + work[i][i], Math.max(work[0][0] + work[1][i], work[0][i]));
            int max = work[0][i];
            for(int j=1; j<=i; j++){
                max = Math.max(max, work[0][i-j] + work[i-(j-1)][i]);
            }
            work[0][i] = Math.max(max, work[0][0] + work[1][i]);
        }
//        System.out.println(work[0][1]);
//        System.out.println(work[0][2]);
//        System.out.println(work[3][4]);
        System.out.println(work[0][N-1]);
    }
}
