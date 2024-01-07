package Permutation.P15655;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    public static int N, M;
    public static boolean[] visited;
    public static int[] arr;

    public static int[] num;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Permutation/P15655/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());

        stk = new StringTokenizer(br.readLine());
        num = new int[N];
        for(int i=0; i<N; i++){
            num[i] = Integer.parseInt(stk.nextToken());
        }
        Arrays.sort(num);

        visited = new boolean[N+1];
        arr = new int[M+1];
        permutation(1);
    }

    public static void permutation(int depth) {
        if(depth == M+1) {
            for(int i=1; i<arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
            return ;
        }

        for(int i=0; i<N; i++) {
            if(!visited[i]) {
                visited[i] = true;
                if(arr[depth-1] < num[i]) {
                    arr[depth]=num[i];
                    permutation(depth+1);
                }
                visited[i] = false;
            }
        }
    }
}
