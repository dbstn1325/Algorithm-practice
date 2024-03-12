package Kakao.Y2022;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class 두_큐_합_같게_만들기 {

    //차례대로 넣고 뺸다
        //합이 같은지 비교
        //cnt++;

    public static void main(String[] args) {
//        solution(new int[]{3, 2, 7, 2}, new int[]{4, 6, 5, 1});
        solution(new int[]{1,2,1,2}, new int[]{1, 10, 1, 2});
//        solution(new int[]{1,1}, new int[]{1,5});
    }

    public static int solution(int[] queue1, int[] queue2) {
        int answer = -2;

        long queue1Sum = Arrays.stream(queue1).sum();
        long queue2Sum = Arrays.stream(queue2).sum();

        if((queue1Sum + queue2Sum) % 2 != 0) return -1;
        long target = (queue1Sum + queue2Sum) / 2;

        Queue<Integer> q1 = new LinkedList<>();
        Queue<Integer> q2 = new LinkedList<>();
        for(int i=0; i < queue1.length; i++) {
            q1.add(queue1[i]);
            q2.add(queue2[i]);
        }

        int cnt = 0;
        while(true) {
            if(cnt > (queue1.length + queue2.length) * 2) {
                return -1;
            }

            if(queue1Sum == target) {
                break;
            }

            if(queue1Sum > queue2Sum) {
                Integer num = q1.poll();
                q2.add(num);
                queue2Sum += num;
                queue1Sum -= num;
            } else if(queue1Sum < queue2Sum) {
                Integer num = q2.poll();
                q1.add(num);
                queue2Sum -= num;
                queue1Sum += num;
            }

            cnt ++;
        }

        System.out.println(cnt);


        return answer;
    }

}
