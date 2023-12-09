package DP.P9095;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DP/P9095/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<N; i++){
            int num = Integer.parseInt(br.readLine());

            //countOfNum: 정수 n을 1,2,3의 합으로 나타낼 수 있는 방법의 수
            int[] countOfNum = new int[num+1];
            if(num == 1) {
                sb.append(1).append("\n");
                continue;
            }
            if(num == 2) {
                sb.append(2).append("\n");
                continue;
            }

            countOfNum[1] = 1;
            countOfNum[2] = 2;
            countOfNum[3] = 4;
            for(int j=4; j<=num; j++){
                countOfNum[j] = countOfNum[j-1] + countOfNum[j-2] + countOfNum[j-3];
            }
            sb.append(countOfNum[num]).append("\n");
        }

        System.out.println(sb);

    }
}
