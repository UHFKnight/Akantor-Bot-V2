package action;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
                    if(user.getVoiceState().inAudioChannel()) {
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
                        if (event.getMember().getRoles().isEmpty()) {
                            event.reply(String.format("Não tens permissões para fazer essa ação seu cromo %s",Emoji.fromUnicode("U+1F5FF"))).queue();
                        }
                            // Action can't be performed if sender doesn't have roles
                        else if (!roles.isEmpty()) {
                            for (Role role : roles) {
                                user.getGuild().removeRoleFromMember(user, role).queue();
                            }
                            event.reply(String.format("Diz adeus às tuas roles meu querido amigo %s", user.getAsMention())).queue();
                        } else {
                            event.reply(String.format("O %s já nem roles tem e tu ainda fazes isso? Que rude", user.getAsMention())).queue();
                        }
                    }
            );
        } catch (HierarchyException e){
            event.reply("Boa tentativa, mas não boa o suficiente").queue();
        }
    }
}
