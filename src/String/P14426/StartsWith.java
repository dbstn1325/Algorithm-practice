package String.P14426;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StartsWith {

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/String/P14426/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] input = br.readLine().split(" ");
        int N = Integer.parseInt(input[0]);
        int M = Integer.parseInt(input[1]);

        List<String> nWords = new ArrayList<>();
        for(int i=0; i<N; i++){
            nWords.add(br.readLine());
        }


        List<String> mWords = new ArrayList<>();
        for(int i=0; i<M; i++){
            String mWord = br.readLine();
            mWords.add(mWord);
        }

        int ans = 0;
        for(int i=0; i<mWords.size(); i++){
            String mWord = mWords.get(i);
            for(int j=0; j<nWords.size(); j++){
                String nWord = nWords.get(j);
                if(nWord.startsWith(mWord)) {
                    ans+=1;
                    break;
                };
            }
        }

        System.out.println(ans);
    }
}
