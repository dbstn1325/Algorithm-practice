package DP.P9655;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DP/P9655/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        //game: 돌이 N개 있을 때 상근이가 우승하는 여부
        boolean[] game = new boolean[1001];
        game[1] = true;
        game[2] = false;
        for(int i=3; i<1001; i++){
            game[i] = game[1] && (!game[i-1]);
        }

        StringBuilder sb = new StringBuilder();
        if(game[N]) {
            sb.append("SK");
        }
        if(!game[N]) {
            sb.append("CY");
        }
        System.out.println(sb);
    }
}
