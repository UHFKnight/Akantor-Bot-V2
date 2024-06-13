package action;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;
import utils.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class CommandListener extends ListenerAdapter {
    private final String KIKO_USERNAME;
    private final List<String> VARIACOESDEAKANTOR = new ArrayList<>(Arrays.asList("akantor", "akantussy", "akantorussy", "akussy", ":100000carts:"));

    /**
     * Does a funny thing and gets kiko's username from the kiko.properties file
     * @throws IOException if the kiko.properties file is not found
     */
    public CommandListener() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("C:\\Users\\35193\\Desktop\\universidade\\4Semestre\\akantor_bot_v2\\Akantor-Bot-V2\\code\\src\\main\\resources\\kiko.properties"));
        KIKO_USERNAME = properties.getProperty("username");
    }

    /**
     * Everytime someone sends a message, this is checked
     * @param event message content
     */
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event){
        if(event.getAuthor().getName().equalsIgnoreCase(KIKO_USERNAME) && Utils.containsAny(event.getMessage().getContentDisplay(), VARIACOESDEAKANTOR)){
            event.getChannel().sendMessage("Falas de caralho, kiko").queue();
            event.getMessage().addReaction(Emoji.fromUnicode("U+1F5FF")).queue();
        }
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event){
        String command = event.getName();
        switch(command){
            case "kys":
                CommandAction.sendKys(event);
                break;
            case "kill":
                CommandAction.killAMember(event);
                break;
            case "enslave":
                CommandAction.removeAMembersRoles(event);
                break;
//            case "randomdeath":
//                CommandAction.russianRoullette(event);
//                break;
//            case "carts":
//                event.reply(String.format("Carts do Diogo: **%d**%nCarts do Francisco: **%d**%n", cartCounter.getDiogoCarts(), cartCounter.getKikoCarts())).queue();
//            case "setdiogocarts":
//                num = (int) event.getOption("carts").getAsLong();
//                CartCounter.carts.set(0, num);
//                fileReaderSaver.saveToFile();
//                event.reply("Operation Successful").queue();
//            case "setkikocarts":
//                num = (int) event.getOption("carts").getAsLong();
//                CartCounter.carts.set(1, num);
//                fileReaderSaver.saveToFile();
//                event.reply("Operation Successful").queue();
        }
    }
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event){
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("kys", "mandas um lendário 'kys' a um utilizador da tua escolha.").addOption(OptionType.USER, "user", "utilizador a receber a linda mensagem.", true));
        commandData.add(Commands.slash("kill", "dá uma surpresa a um utilizador da tua escolha.").addOption(OptionType.USER, "user", "utilizador a receber a linda prenda.", true));
        commandData.add(Commands.slash("enslave", "remove todos os direitos a um utilizador da tua escolha.").addOption(OptionType.USER, "user", "utilizador a receber a linda prenda.", true));
//        commandData.add(Commands.slash("randomdeath", "dá time-out de 5 minutos a um membro aleatório"));
//        commandData.add(Commands.slash("carts", "printa os carts dos nossos dois amigos"));
//        commandData.add(Commands.slash("setdiogoscarts", "define o nº de carts do Diogo").addOption(OptionType.INTEGER, "carts", "número de carts a ser definido", true));
//        commandData.add(Commands.slash("setfranciscocarts", "define o nº de carts do Francisco").addOption(OptionType.INTEGER, "carts", "número de carts a ser definido", true));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
