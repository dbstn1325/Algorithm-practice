package Implementation.prog_lv2_스킬트리;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Solution solution = new Solution();
        int answer = solution.solution("CBD", new String[]{"BACDE", "CBADF", "AECB", "BDA"});
        System.out.println(answer);
    }
}

class Solution {

    static StringBuilder sb;
    static List<String> filterSkill = new ArrayList<>();
    static int answer = 0;
    public int solution(String skill, String[] skill_trees) {
        for(String skillTree: skill_trees) {
            removeNotContainSkill(skill, skillTree);
            checkStartSkill(skill);
        }

        answer = filterSkill.size();

        return answer;
    }

    private void checkStartSkill(String skill) {
        if(skill.startsWith(sb.toString())) {
            filterSkill.add(sb.toString());
        }
    }

    private void removeNotContainSkill(String skill, String skillTree) {
        sb = new StringBuilder();
        for(char ch: skillTree.toCharArray()) {
            if(skill.indexOf(ch) >= 0) {
                sb.append(ch);
            }
        }
    }

}
