package Samsung_SW_역량테스트.Y2015_바이러스;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static int N;
    public static long ans = 0;
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Samsung_SW_역량테스트/Y2015_바이러스/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        long[] p = new long[N];
        String[] input = br.readLine().split(" ");
        for(int i=0; i<N; i++){
            p[i] = Integer.parseInt(input[i]);
        }

        StringTokenizer stk = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(stk.nextToken());
        int l = Integer.parseInt(stk.nextToken());

        for(int i=0; i<N; i++){
            ans++;
            p[i] -= m;
        }

        for(int i=0; i<N; i++){
            if(p[i] <= 0) {
                continue;
            }

            if(p[i] <= l) {
                ans++;
            }

            if(p[i] > l) {
                long r = 0;
                r += p[i] / l;
                if(p[i] % l > 0) {
                    r++;
                };

                ans += r;
            }
        }

        System.out.println(ans);

    }
}
