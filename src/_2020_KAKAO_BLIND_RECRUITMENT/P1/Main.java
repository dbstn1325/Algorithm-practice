package _2020_KAKAO_BLIND_RECRUITMENT.P1;

public class Main {

    public static int answer;
    public static void main(String[] args) {
        solution("aabbaccc");
    }

    public static int solution(String s) {
        answer = Integer.MAX_VALUE;
        calculate(s);

        return answer;
    }

    public static void calculate(String s) {

        int count = 1;
        for(int i=1; i<=s.length()/2; i++){
            String prev = s.substring(0, i);
            String cur = "";
            boolean isCorrect = false;
            StringBuilder sb = new StringBuilder(); //두자리 숫자가 나올수 있으므로 완성본을 만들기위해 sb 사용

            for(int j=i; j<=s.length()-i; j+=i) {
                cur = s.substring(j, j + i);

                if(cur.equals(prev)) {
                    isCorrect = true;
                    count++;
                }

                if(!isCorrect) {
                    sb.append(prev);
                    prev = cur;
                }

                if(!cur.equals(prev) && isCorrect) {
                    sb.append(count).append(prev);
                    isCorrect = false;
                    prev = cur;
                    count = 1;
                }

            }
            int leavings = s.length() % i;
            if(isCorrect) {
                sb.append(count).append(prev);
            }
            if(!isCorrect) {
                sb.append(prev);
            }

            System.out.println(sb);
            answer = Math.min(answer, sb.toString().length() + leavings);
            count=1;
        }
        System.out.println(answer);
    }
}
