package action;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import org.jetbrains.annotations.NotNull;
import utils.Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class CommandAction {
    /**
     * Sends "kys" to a chosen user
     * @param event
     */
    public static void sendKys(@NotNull SlashCommandInteractionEvent event){
        event.getGuild().retrieveMemberById(event.getOption("user").getAsUser().getId()).queue(user ->
                event.reply("kys " + user.getAsMention()).queue()
        );
    }

    /**
     * Server Mutes and Server Deafens a user, as well as removing them from the call
     * @param event
     */
    public static void killAMember(@NotNull SlashCommandInteractionEvent event){
        event.getGuild().retrieveMemberById(event.getOption("user").getAsUser().getId()).queue(user -> {
                    if(user.getIdLong() == 1130162750228791366L){
                        event.reply("**Sou o motherfucker que dá repel a *Death***.").queue();
                        Member sender = event.getMember();
                        sender.mute(true).queue();
                        sender.deafen(true).queue();
                        sender.getGuild().kickVoiceMember(sender).queue();                    }
                    else if(user.getVoiceState().inAudioChannel()) {
                        user.mute(true).queue();
                        user.deafen(true).queue();
                        user.getGuild().kickVoiceMember(user).queue();
                        event.reply(String.format("**\"%s\"** deu faint. lmao", user.getAsMention())).queue();
                    } else {
                        event.reply(String.format("**\"%s\"** não está em chamada, burro", user.getAsMention())).queue();
                    }
                }
        );
    }

    /**
     * Removes all of a user's roles
     * @param event
     */
    public static void removeAMembersRoles(@NotNull SlashCommandInteractionEvent event){
        try{
            event.getGuild().retrieveMemberById(event.getOption("user").getAsUser().getId()).queue(user -> {
                        List<Role> roles = user.getRoles();
                        if (event.getMember().getRoles().isEmpty())
                            event.reply(String.format("Não tens permissões para fazer essa ação seu cromo %s",Emoji.fromUnicode("U+1F5FF"))).queue();
                            // Action can't be performed if sender doesn't have roles
                        else if (!roles.isEmpty()) {
                            for (Role role : roles) user.getGuild().removeRoleFromMember(user, role).queue();
                            event.reply(String.format("Diz adeus às tuas roles meu querido amigo %s", user.getAsMention())).queue();
                        } else event.reply(String.format("O %s já nem roles tem e tu ainda fazes isso? Que rude", user.getAsMention())).queue();
                    }
            );
        } catch (HierarchyException e){
            event.reply("Boa tentativa, mas não boa o suficiente").queue();
        }
    }

    public static void falasDeCaralho(@NotNull MessageReceivedEvent event){
        event.getChannel().sendMessage("Falas de caralho, kiko").queue();
        event.getMessage().addReaction(Emoji.fromUnicode("U+1F5FF")).queue();
    }

    public static void crazy(@NotNull MessageReceivedEvent event){
        List<String> lines = List.of("Crazy?", "I was crazy once", "They locked me in a room", "A rubber room", "A rubber room with rats", "And rats make me crazy");
        for(String line : lines){
            event.getChannel().sendMessage(line).queue();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                event.getChannel().sendMessage("Deixem-me dormir").queue();
            }
        }
    }

    public static void dog(@NotNull MessageReceivedEvent event){
        event.getMessage().addReaction(Emoji.fromUnicode("U+1F415")).queue();
    }
    public static void russianRoullette(@NotNull SlashCommandInteractionEvent event){
        try {
            List<Member> members = event.getGuild().getMembers();
            members = Utils.getNotBotMembers(members);
            Random random = new Random();
            int randomNumber = random.nextInt(members.size());
            if (members.get(randomNumber).getVoiceState().inAudioChannel()) {
                Properties properties = new Properties();
                properties.load(new FileReader("C:\\Users\\35193\\Desktop\\universidade\\4Semestre\\akantor_bot_v2\\Akantor-Bot-V2\\code\\src\\main\\resources\\timeoutchannel.properties"));
                VoiceChannel timeoutChannel = event.getGuild().getVoiceChannelById(properties.getProperty("channel"));
                event.getGuild().moveVoiceMember(members.get(randomNumber), timeoutChannel).queue();
                event.reply("Adeus minha pobre alma " + members.get(randomNumber).getAsMention()).queue();
                //event.reply("Vejo-te daqui a 1 minuto " + members.get(randomNumber).getAsMention()).queue();
                //ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                //List<Member> finalMembers = members;
                //scheduler.schedule(() -> event.getGuild().moveVoiceMember(finalMembers.get(randomNumber), finalMembers.get(randomNumber).getVoiceState().getChannel()).queue(),1, TimeUnit.MINUTES);
            } else {
                event.reply("A vítima escolhida (" + members.get(randomNumber).getAsMention() + ") não está em chamada :(").queue();
            }
        } catch (HierarchyException e){
            event.reply("O java não me permite dar time-out no admin, peço desculpa.").queue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
