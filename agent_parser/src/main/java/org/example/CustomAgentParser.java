package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mabbasi
 */
public class CustomAgentParser {

    private final List<CUPattern> patterns;

    public CustomAgentParser(List<CUPattern> patterns) {
        this.patterns = patterns;
    }

    /**
     * Constructs a thread-safe UserAgentParser
     * @param configList configure a user-agent parser from a list of regexp hashmaps
     * @return user-agent parser
     */
    public static CustomAgentParser fromList(List<Map<String, String>> configList) {
        List<CUPattern> configPatterns = new ArrayList<>();

        for (Map<String, String> configMap : configList) {
            configPatterns.add(patternFromMap(configMap));
        }
        return new CustomAgentParser(new CopyOnWriteArrayList<>(configPatterns));
    }

    public CustomAgent parse(String agentString) {
        if (agentString == null) {
            return null;
        }

        for (CUPattern p : patterns) {
            CustomAgent match = p.match(agentString);
            if (match != null) {
                return match;
            }
        }
        return CustomAgent.OTHER;
    }

    protected static CUPattern patternFromMap(Map<String, String> configMap) {
        String regex = configMap.get("regex");
        if (regex == null) {
            throw new IllegalArgumentException("User agent is missing regex");
        }

        return (new CUPattern(Pattern.compile(regex)));
    }

    protected static class CUPattern {
        private final Pattern pattern;

        public CUPattern(Pattern pattern) {
            this.pattern = pattern;
        }

        public CustomAgent match(String agentString) {
            String platform ="", platform_version="", app_name="" , app_version="" ;

            Matcher matcher = pattern.matcher(agentString);

            Integer cnt = matcher.groupCount();

            List<String> list = new ArrayList<>();

            while (matcher.find()) {
                list.add(matcher.group());

            }

            if ( list.size()==2 )
            {
                platform= list.get(0).split("/")[0].trim() ;
                platform_version= list.get(0).split("/")[1].replaceAll("\\(","").trim() ;

                app_name= list.get(1).split("/")[0].replaceAll("\\)","").trim() ;
                app_version= list.get(1).split("/")[1].trim() ;




            } else if (list.size()==1)
            {
                platform= list.get(0).split("/")[0].trim() ;
                platform_version= list.get(0).split("/")[1].trim() ;
                app_name= "" ;
                app_version= "" ;

            }

           /* System.out.println(matcher.lookingAt());
            System.out.println(matcher.group());

            if (!matcher.find()) {
                return null;
            }

            int groupCount = matcher.groupCount();*/

            return new CustomAgent(platform,platform_version,app_name,app_version);

            //return null ;
        }

        private boolean isBlank(String value) {
            return value == null || value.isEmpty();
        }
    }

}
