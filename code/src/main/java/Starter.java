import action.CommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Starter {
    /**
     * Method to start up the bot
     * Gets the discord key from the git ignored config.properties
     * @param args not used
     * @throws IOException if the config.properties file is not found
     */
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("src/main/resources/config.properties"));
        String key = properties.getProperty("token");

        JDA bot = JDABuilder.createDefault(key)
                .setActivity(Activity.playing("MH4U - Carts Incontáveis")).setStatus(OnlineStatus.DO_NOT_DISTURB)
                .addEventListeners(new CommandListener())
                .enableIntents(GatewayIntent.GUILD_MESSAGES).enableIntents(GatewayIntent.DIRECT_MESSAGES).enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
    }
}
