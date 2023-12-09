package DP.P11726;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/DP/P11726/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long ans = 0;

        //block: '2*n 정사각형을 1 * 2, 2 * 1타입으로 채우는 방법의 수' 를 10007로 나눈 나머지
        int[] block = new int[N+1];
        if(N==1) ans=1;
        if(N==2) ans=2;

        if(N>=3) {
            block[1] = 1;
            block[2] = 2;
            for (int i = 3; i <= N; i++) {
                block[i] = (block[i - 1] + block[i - 2]) % 10007;
            }
            ans = block[N];
        }

        System.out.println(ans);
    }

}
