package Combination.P15650;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
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
        arr = new int[M+1];
        combination(1);
    }

    public static void combination(int depth) {
        if(depth == M+1) {
            for(int i=1; i<arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
            return ;
        }

        for(int i=1; i<=N; i++) {
            if(!visited[i]) {
                visited[i] = true;
                if(arr[depth-1] < i) {
                    arr[depth] = i;
                    combination(depth + 1);
                }
                visited[i] = false;
            }
        }
    }
}
