package Kakao.Y2024.P3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Main {

    static int[][] dices;
    static List<int[]> selectA;
    static boolean[] visited;

    static List<Integer> scoreA;
    static List<Integer> scoreB;

    public static void main(String[] args) {
        int[][] dice = {{1, 2, 3, 4, 5, 6}, {3, 3, 3, 3, 4, 4}, {1, 3, 3, 4, 4, 4}, {1, 1, 4, 4, 5, 5}};
        solution(dice);
    }

    public static int[] solution(int[][] dice) {
        int[] answer;
        dices = dice;

        int[] finalSelect = new int[dice.length/2];

        //1. A가 가져갈 주사위 선택하는 모든 경우를 찾는다. 완전탐색
            //백트래킹 or 조합
                //n개의 주사위 중 n/2개 선택
                    //시간복잡도: 10개 중 5개 선택 = 252
        selectA = new ArrayList<>();
        visited = new boolean[dice.length];
        permutation(0, 0, new int[dice.length / 2]);

        //2. 가져간 주사위를 굴린다 -> 주사위 눈의 총합을 구한다.
            //A와 B의 주사위를 한꺼번에 시뮬레이션
                //6^n: 시간초과

            //A와 B의 주사위를 따로 시뮬레이션 해야한다
            //선택한 n/2에 대해 각각 주사위를 굴린다
                // 6^(n/2)
                //시간복잡도: 6^(5) + 6^5 = 6^5 = 7776 * 252 = 1959552
                    //List (6^5), List (6^5)
                    //각 이중탐색으로 순회 시, O(m) -> 시간 초과
                        //각 이분탐색 or 투 포인터로 순회 시, O(logM)

        int max = Integer.MIN_VALUE;
        for(int[] a: selectA) {
            int[] b = new int[dice.length/2];
            boolean[] isSame = new boolean[dice.length];

            for(int num: a) {
                isSame[num] = true;
            }

            int index = 0;
            for(int i=0; i<isSame.length; i++) {
                if(isSame[i]) continue;

                b[index] = i;
                index++;
            }

            scoreA = new ArrayList<>();
            scoreB = new ArrayList<>();

            combination(0, a, 0, scoreA);
            combination(0, b, 0, scoreB);

            Collections.sort(scoreA);
            Collections.sort(scoreB);

            int totalWin = 0;
            for(Integer score: scoreA) {
                int left = 0;
                int right = scoreB.size();

                while(left+1 < right) {
                    int mid = (left + right) /2;

                    if(score > scoreB.get(mid)) {
                        left = mid;
                        continue;
                    }

                    right = mid;
                }

                totalWin += left;
            }


            if(totalWin > max) {
                max = totalWin;
                finalSelect = a;
            }
        }

        if(dice.length == 2) {
            return new int[]{finalSelect[0] + 1};
        }

        answer = new int[dice.length/2];
        for(int i=0; i<finalSelect.length; i++) {
            answer[i] = finalSelect[i] + 1;
        }
        return answer;
    }

    public static void combination(int depth, int[] select, int sum, List<Integer> score) {
        if(depth == select.length) {
            score.add(sum);
            return ;
        }

        for(int i=0; i<6; i++) {
            combination(depth+1, select, sum + dices[select[depth]][i], score);
        }
    }

    static void permutation(int depth, int start, int[] select) {
        if(depth == dices.length/2) {
            selectA.add(select.clone());
            return ;
        }

        for(int i=start; i<dices.length; i++) {
            if(visited[i]) continue;

            visited[i] = true;
            select[depth] = i;
            permutation(depth+1, i+1, select);
            visited[i] = false;
        }
    }

}
