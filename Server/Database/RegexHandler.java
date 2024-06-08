package Server.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHandler {

       public static List<String> findTopMatches(List<String> possibleNames, String pattern, int topCount) {
        List<String> matches = new ArrayList<>();

        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

        Map<String, Integer> matchCount = new HashMap<>();

        for (String name : possibleNames) {
            Matcher m = p.matcher(name);

            int count = 0;
            while (m.find()) {
                count++;
            }

            matchCount.put(name, count);
        }

        List<Map.Entry<String, Integer>> sortedMatches = new ArrayList<>(matchCount.entrySet());
        sortedMatches.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        for (int i = 0; i < Math.min(topCount, sortedMatches.size()); i++) {
            matches.add(sortedMatches.get(i).getKey());
        }

        return matches;
    }

}
