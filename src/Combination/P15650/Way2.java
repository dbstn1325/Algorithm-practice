package Combination.P15650;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Way2 {
    public static int N, M;
    public static boolean[] visited;
    public static int[] arr;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Combination/P15650/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());

        visited = new boolean[N+1];
        arr = new int[M];
        combination(0, 1);
    }

    public static void combination(int depth, int start) {
        if(depth == M) {
            for(int a: arr) {
                System.out.print(a + " ");
            }
            System.out.println();
            return ;
        }

        for(int i=start; i<=N; i++) {
            arr[depth] = i;
            combination(depth + 1, i+1);
        }
    }
}
