package utils;

import net.dv8tion.jda.api.entities.Member;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static boolean containsAny(String message, List<String> words){
        for(String eachWord : words)
            if (StringUtils.containsIgnoreCase(message,eachWord))
                return true;
        return false;
    }
    public static List<Member> getNotBotMembers(List<Member> allMembers){
        List<Member> notBots = new ArrayList<>();
        for (Member member : allMembers) {
            if (!member.getUser().isBot()) {notBots.add(member);}
        }
        return notBots;
    }
}
