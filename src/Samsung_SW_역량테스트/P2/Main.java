package Samsung_SW_역량테스트.P2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static int N,M;
    public static Character[][] tmpMap;
    public static Character[][] map;
    public static int[] redCandy = new int[2];
    public static int[] blueCandy = new int[2];
    public static int[] goal = new int[2];
    public static int[] tmpRedCandy = new int[2];
    public static int[] tmpBlueCandy = new int[2];
    public static int[] tmpGoal = new int[2];
    public static int min = Integer.MAX_VALUE;
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Samsung_SW_역량테스트/P2/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(stk.nextToken());
        M = Integer.parseInt(stk.nextToken());

        tmpMap = new Character[N][M];
        map = tmpMap;
        for(int i=0; i<N; i++){
            String input = br.readLine();
            for(int j=0; j<M; j++){
                char ch = input.charAt(j);
                if(ch == 'R') {
                    redCandy[0] = i;
                    redCandy[1] = j;
                    tmpRedCandy = redCandy;
                }
                if(ch == 'B') {
                    blueCandy[0] = i;
                    blueCandy[1] = j;
                    tmpBlueCandy = blueCandy;
                }
                if(ch == 'O') {
                    goal[0] = i;
                    goal[1] = j;
                    tmpGoal = goal;
                }
                map[i][j] = ch;
            }
        }


//        move(redCandy[0], redCandy[1], 2);
//        move(blueCandy[0], blueCandy[1], 2);

        cal(0, 0);
        System.out.println(min);

    }

    public static void cal(int depth, int type) {
        if(map[goal[0]][goal[1]].equals('R')) {
            min = Math.min(min, depth);
            map = tmpMap;
            redCandy = tmpRedCandy;
            blueCandy = tmpBlueCandy;
            goal = tmpGoal;
            return ;
        }

        if(depth == 10 || map[goal[0]][goal[1]].equals('B')) {
            map = tmpMap;
            redCandy = tmpRedCandy;
            blueCandy = tmpBlueCandy;
            goal = tmpGoal;
            return ;
        }

        move(redCandy[0], redCandy[1], 0);
        move(blueCandy[0], blueCandy[1], 0);
        cal(depth + 1, 1);

//        move(redCandy[0], redCandy[1], 1);
//        move(blueCandy[0], blueCandy[1], 1);
//        cal(depth+1, 1);
//
//        move(redCandy[0], redCandy[1], 2);
//        move(blueCandy[0], blueCandy[1], 2);
//        cal(depth + 1, 2);
//
//        move(redCandy[0], redCandy[1], 3);
//        move(blueCandy[0], blueCandy[1], 3);
//        cal(depth+1,3);

//        int[] endRight = move(redCandy[0], redCandy[1], 1);
//        cal(depth+1, endRight[0], endRight[1]);
//
//        int[] endDown = move(redCandy[0], redCandy[1], 2);
//        cal(depth+1, endDown[0], endDown[1]);
//
//        int[] endUp = move(redCandy[0], redCandy[1], 3);
//        cal(depth+1, endUp[0], endUp[1]);
    }

    private static void move(int startX, int startY, int type) {
//        int nx = 0;
//        int ny = 0;

        int i=1;
        System.out.println(startX + " " + startY);
        System.out.println();
        if(type == 0) {
            while(i <= M) {
                if((map[startX][i-1].equals('.') || map[startX][i-1].equals('O')) && (map[startX][i].equals('R') || map[startX][i].equals('B'))) {
                    map[startX][i-1] = map[startX][i];
                    check(startX, i);
                    map[startX][i] = '.';
                }
                i++;
            }
        }

        i=1;
        if(type == 1) {
            while(i <= M-1) {
                if(map[startX][M-i].equals('.') && !map[startX][M-(i+1)].equals('.') && !map[startX][M-(i+1)].equals('#')) {
                    map[startX][M-i] = map[startX][M-(i+1)];
                    check(startX, M-(i+1));
                    map[startX][M-(i+1)] = '.';
                }
                i++;
            }
        }

        i=1;
        if(type == 2) {
            while(i <= N-1) {
                if(map[N-i][startY].equals('.') && !map[N-(i+1)][startY].equals('.') && !map[N-(i+1)][startY].equals('#')) {
                    map[N-i][startY] = map[N-(i+1)][startY];
                    check(N-(i+1), startY);
                    map[N-(i+1)][startY] = '.';
                }
                i++;
            }
        }

        i=1;
        if(type == 3) {
            while(i<=N-1) {
                if(map[i-1][startY].equals('.') && !map[i][startY].equals('.') && !map[i][startY].equals('#')) {
                    map[i-1][startY] = map[i][startY];
                    check(i, startY);
                    map[i][startY] = '.';
                }
                i++;
            }
        }


//        ny = startY;
//        if(type == 0) {
//            while(ny > 1) {
//                ny--;
//                if(!map[startX][ny].equals('.')) {
//                    break;
//                }
//            }
//            map[startX][ny] = map[startX][startY];
//            map[startX][ny] = '.';
//
//            return new int[]{startX, startY};
//        }

//        ny = startY;
//        if(type == 1) {
//            while(ny < M-1) {
//                ny++;
//                if(!map[startX][ny].equals('.')) {
//                    ny--;
//                    break;
//                }
//            }
//            map[startX][ny] = map[startX][startY];
//            map[startX][ny] = '.';
//
//            return new int[]{startX, startY};
//        }
//
//        nx = startX;
//        if(type == 2) {
//            while(nx < N-1) {
//                nx++;
//                if(!map[nx][startY].equals('.')) {
//                    nx--;
//                    break;
//                }
//            }
//            map[nx][startY] = map[startX][startY];
//            map[startX][startY] = '.';
//
//            return new int[]{nx, startY};
//        }
//
//        nx = startX;
//        if(type == 3) {
//            while(nx > 1) {
//                nx--;
//                if(!map[nx][startY].equals('.')) {
//                    break;
//                }
//            }
//            map[nx][startY] = map[startX][startY];
//            map[startX][startY] = '.';
//
//            return new int[]{nx, startY};
//        }


//        return new int[]{startX, startY};
    }

    private static void check(int i, int j) {
        if(map[i][j].equals('R')) {
            redCandy[0] = i;
            redCandy[1] = j;
        }
        if(map[i][j].equals('B')) {
            blueCandy[0] = i;
            blueCandy[1] = j;
        }
    }
}
