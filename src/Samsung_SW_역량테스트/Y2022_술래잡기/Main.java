package Samsung_SW_역량테스트.Y2022_술래잡기;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main {

    static ArrayList<Runner> runners;
    static Catcher catcher;
    static boolean[][] trees;

    static int n, m, h, k;

    static int[] dr = {1, -1};
    static int[] dc = {0, 0};

    static int[] ddr = {-1, 0, 1, 0};
    static int[] ddc = {0, 1, 0, -1};
    static int threshold = 2;
    static int cnt = 0;
    static int idx = 1;
    static boolean isBack = false;


    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Samsung_SW_역량테스트/Y2022_술래잡기/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk;

        stk = new StringTokenizer(br.readLine());
        n = Integer.parseInt(stk.nextToken());
        m = Integer.parseInt(stk.nextToken());
        h = Integer.parseInt(stk.nextToken());
        k = Integer.parseInt(stk.nextToken());

        runners = new ArrayList<>();
        for(int i=0; i<m; i++) {
            stk = new StringTokenizer(br.readLine());

            int r = Integer.parseInt(stk.nextToken()) - 1;
            int c = Integer.parseInt(stk.nextToken()) - 1;
            int d = Integer.parseInt(stk.nextToken());

            runners.add(new Runner(r, c ,d));
        }

        trees = new boolean[n][n];
        for(int i=0; i<h; i++) {
            stk = new StringTokenizer(br.readLine());

            int r = Integer.parseInt(stk.nextToken()) - 1;
            int c = Integer.parseInt(stk.nextToken()) - 1;
            trees[r][c] = true;
        }

        catcher = new Catcher(n/2, n/2, 0, 0);

        int catchCount = 0;
        int ans = 0;

        int t = 1;
        while(t <= k) {
            runnerMove();
            catcherMove();
            List<int[]> looks = catcher.getLooks();

            for(int[] look: looks) {
                int r = look[0];
                int c = look[1];
                if(outOfBlock(r, c)) continue;
                if(trees[r][c]) continue;

                List<Runner> result = runners.stream()
                        .filter(v -> v.isEqual(r, c))
                        .collect(Collectors.toList());

                catchCount += result.stream().count();
                result.forEach(v-> v.remove());
            }

            ans += t * catchCount;
            catchCount = 0;
            t++;
//            System.out.println(catcher.r + " " + catcher.c + " " + catcher.d + " " + threshold);
        }

        System.out.println(ans);
    }

    private static void catcherMove() {
        catcher.r = catcher.r + ddr[catcher.d]; //catcher.d에 의한
        catcher.c = catcher.c + ddc[catcher.d];

        if(catcher.r == 0 && catcher.c == 0) {
            initBack();
            isBack = true;
            return ;
        }else if(catcher.r == n/2 && catcher.c == n/2) {
            initFront();
            isBack = false;
            return ;
        }

        if(isBack) {
            back();
            return ;
        }

        front();
    }

    private static void initFront() {
        threshold = 2;
        cnt = 0;
        idx = 1;
        catcher.d = (catcher.d + 2) % 4;
    }

    private static void initBack() {
        threshold = 100000;
        cnt = 0;
        idx = n;
        catcher.d = (catcher.d + 2) % 4;
    }

    private static void back() {
        cnt++;
        if(cnt == threshold / 2) {
            catcher.d = (catcher.d + 3) % 4;
        }
        if(cnt == threshold || (catcher.r == n-1 && catcher.c == 0)) {
            idx--;
            threshold = 2*idx;
            cnt = 0;
            catcher.d = (catcher.d + 3) % 4;
        }
    }

    private static void front() {
        cnt++;
        if(cnt == threshold / 2) {
            catcher.d = (catcher.d + 1) % 4;
        }
        if(cnt == threshold) {
            idx++;
            threshold = 2*idx;
            cnt = 0;
            catcher.d = (catcher.d + 1) % 4;
        }
    }

    private static void runnerMove() {
        for(Runner runner: runners) {
            if(runner.r < 0 && runner.c < 0) continue;
            if(Math.abs(runner.r - catcher.r) + Math.abs(runner.c - catcher.c) > 3) continue;

            int[] next = runner.getNext();
            int nr = next[0];
            int nc = next[1];

            if(outOfBlock(nr ,nc)) {
                runner.reverseD();
                int[] next2 = runner.getNext();
                int nr2 = next2[0];
                int nc2 = next2[1];

                if(catcher.r == nr2 && catcher.c == nc2) {
                    continue;
                }

                runner.move(nr2, nc2);
                continue;
            }

            if(catcher.r == nr && catcher.c == nc) {
                continue;
            }
            runner.move(nr, nc);
        }
    }

    static class Catcher {
        int r, c, d, point;

        public Catcher(int r, int c, int d, int point) {
            this.r = r;
            this.c = c;
            this.d = d;
            this.point = point;
        }

        public List<int[]> getLooks() {
            List<int[]> looks = new ArrayList<>();
            looks.add(new int[]{r, c});

            int curR = this.r;
            int curC = this.c;
            for(int i=0; i<2; i++) {
                int nr = curR + ddr[this.d];
                int nc = curC + ddc[this.d];

                looks.add(new int[]{nr, nc});
                curR = nr;
                curC = nc;
            }

            return looks;
        }

    }

    static class Runner {
        int r, c, d;
        int curd = 0;

        public Runner(int r, int c, int d) {
            this.r = r;
            this.c = c;
            this.d = d;
        }

        public int[] getNext() {
            int nr, nc;

            if(d==1) {
                nr = this.r + dc[curd];
                nc = this.c + dr[curd];
                 return new int[]{nr, nc};
            }

            nr = this.r + dr[curd];
            nc = this.c + dc[curd];
            return new int[]{nr, nc};
        }

        public void reverseD() {
            this.curd = (this.curd + 1) % 2;
        }

        public void move(int nr, int nc) {
            this.r = nr;
            this.c = nc;
        }

        public boolean isEqual(int r, int c) {
            return this.r == r && this.c == c;
        }

        public void remove() {
            this.r = -100;
            this.c = -100;
        }
    }

    public static boolean outOfBlock(int nr, int nc) {
        return nr < 0 || nr >= n || nc < 0 || nc >= n;
    }
}
