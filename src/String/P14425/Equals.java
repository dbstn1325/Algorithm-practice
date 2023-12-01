package String.P14425;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Equals {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/String/P14425/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stk = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(stk.nextToken());
        int M = Integer.parseInt(stk.nextToken());

        String[] nWords = new String[N];
        for(int i=0; i<N; i++){
            nWords[i] = br.readLine();
        }

        int ans = 0;
        for(int i=0; i<M; i++){
            String mWord = br.readLine();
            for(int j=0; j<N; j++) {
                if (nWords[j].equals(mWord)) {
                    ans += 1;
                }
            }
        }
        System.out.println(ans);

    }
}
