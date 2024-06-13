package utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Utils {
    public static boolean containsAny(String message, List<String> words){
        for(String eachWord : words)
            if (StringUtils.containsIgnoreCase(message,eachWord))
                return true;
        return false;
    }
}
