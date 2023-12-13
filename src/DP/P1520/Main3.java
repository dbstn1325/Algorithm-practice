package DP.P1520;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main3 {

    public static int N, M;
    public static int[][] graph;
    public static int[][] counts;
    public static int[] dr = {-1, 1, 0, 0};
    public static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DP/P1520/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk;
        stk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());

        graph = new int[N][M];
        for(int i=0; i<N; i++){
            stk = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++){
                graph[i][j] = Integer.parseInt(stk.nextToken());
            }
        }

        counts = new int[N][M];
        Arrays.stream(counts)
                .forEach(count -> Arrays.fill(count, -1));

        System.out.println(dfs(0,0));
    }

    public static int dfs(int startR, int startC) {
        if(validateQuit(startR, startC)) {
            return 1;
        }

        if(counts[startR][startC] != -1) {
            return counts[startR][startC];
        }

        counts[startR][startC]=0;
        for(int i=0; i<4; i++){
            int nextR = startR + dr[i];
            int nextC = startC + dc[i];

            if(validateOutOfRange(nextR, nextC)) continue;

            if(graph[nextR][nextC] < graph[startR][startC]) {
                counts[startR][startC] += dfs(nextR, nextC);
            }
        }

        return counts[startR][startC];
    }

    private static boolean validateOutOfRange(int nextR, int nextC) {
        return nextR < 0 || nextR >= N || nextC < 0 || nextC >= M;
    }

    private static boolean validateQuit(int startR, int startC) {
        return startR == N - 1 && startC == M - 1;
    }
}
