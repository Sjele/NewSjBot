package sjbot.commands;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 11.05.13
 * Time: 11:06
 * To change this template use File | Settings | File Templates.
 */

import com.google.common.eventbus.Subscribe;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;
import sjbot.api.Command;
import sjbot.api.commandHandler;
import sjbot.api.pluginRegistry;
import sjbot.core.SjBot;
import sjbot.event.noPermissionEvent;
import sjbot.misc.blacklistManager;
import sjbot.permission.PermissionManager;

import java.util.List;

public class coreCommands {
    @Subscribe
    public void testCommand(MessageEvent event) {
        if(event.getMessage().equals(SjBot.getPrefix()+"test")) {
            if (PermissionManager.checkPerm(event.getUser().getNick(),"test")){
                SjBot.bot.sendNotice(event.getUser(),"This did actually work!");
            }else{
                SjBot.eventBus.post(new noPermissionEvent("test", event.getUser(), "test"));
            }
        }
    }

    @Subscribe
    public void reverse(MessageEvent event) {
        if (event.getMessage().startsWith(SjBot.getPrefix())) {
            String args = utils.getArgsNoPrefix(event.getMessage());
            String[] argsArray = utils.getArgsAsArray(args);

            if (argsArray[0].equalsIgnoreCase("reverse")&&(argsArray.length >= 1)) {
                if (PermissionManager.checkPerm(event.getUser().getNick(),"*")||(PermissionManager.checkPerm(event.getUser().getNick(),"reverse"))){
                System.out.println("Started running command Reverse!");
                String text ="";
                for (int i = 1; i < argsArray.length; i++  ) {
                    text = text+" "+ argsArray[i];
                }

                String rev = "";
                for (int i = text.length()-1; i > 0; i--) {
                   char c = text.charAt(i);
                   rev = rev + c;
                }

                SjBot.bot.sendNotice(event.getUser(), rev);
                System.out.println("Ran command Reverse!");
                }else{
                    SjBot.bot.sendNotice(event.getUser(), "Insufficient perms! You need * or reverse");
                }

            }
        }
    }
    @Subscribe
    public void permission(MessageEvent event) {

        if (event.getMessage().startsWith(SjBot.getPrefix())) {
            String[] args = utils.getArgsAsArray(utils.getArgsNoPrefix(event.getMessage()));
            if (args[0].equalsIgnoreCase("perm")||(args[0].equalsIgnoreCase("permission"))){
                      if (args[1].equalsIgnoreCase("-give")||args[1].equalsIgnoreCase("-g")) {
                          if (PermissionManager.checkPerm(event.getUser().getNick(), "pgive")) {
                            PermissionManager.addPerm(args[2], args[3]);
                            SjBot.bot.sendNotice(event.getUser(), "Gave user permission!");
                            SjBot.bot.sendNotice(args[2],"You just got permission: "+args[3]);
                            System.out.println(event.getUser().getNick()+" just gave permission "+args[3]+" to nick "+args[2]);
                          }else{
                              SjBot.eventBus.post(new noPermissionEvent("perm (give)", event.getUser(), "pgive"));
                          }
                      }else if(args[1].equalsIgnoreCase("-take")||args[1].equalsIgnoreCase("-t")){
                          if (PermissionManager.checkPerm(event.getUser().getNick(), "ptake")) {
                            PermissionManager.removePerm(args[2], args[3]);
                            SjBot.bot.sendNotice(event.getUser(), "took user permission!");
                            SjBot.bot.sendNotice(args[2],"You just lost permission: "+args[3]);
                            System.out.println(event.getUser().getNick()+" just took permission "+args[3]+" from nick "+args[2]);
                          }else{
                              SjBot.eventBus.post(new noPermissionEvent("perm (take)", event.getUser(), "ptake"));
                          }
                      }else if(args[1].equalsIgnoreCase("-check")||args[1].equalsIgnoreCase("-c")) {
                           if (PermissionManager.checkPerm(event.getUser().getNick(), "pcheck")){
                            SjBot.bot.sendNotice(event.getUser(), "The user has the permission: "+PermissionManager.checkPerm(args[2],args[3]));
                           }else{
                               SjBot.eventBus.post(new noPermissionEvent("perm (check)", event.getUser(), "pcheck"));
                           }
                      }else if(args[1].equalsIgnoreCase("-givedefault")||args[1].equalsIgnoreCase("-gd")) {
                            if (PermissionManager.checkPerm(event.getUser().getNick(), "pgive")){
                                PermissionManager.addDefault(args[2]);
                            }else{
                                SjBot.eventBus.post(new noPermissionEvent("perm (give)", event.getUser(), "pgive"));
                            }
                      }else{
                          SjBot.bot.sendNotice(event.getUser(), "Unknown flag!");
                      }
            }
        }

    }

    public void Help(MessageEvent event) {

        if (event.getMessage().startsWith(SjBot.getPrefix()+"help")) {
            String[] args = utils.getArgsAsArray(utils.getArgsNoPrefix(event.getMessage()));

            if (args[1].equalsIgnoreCase("help")&&(args[2].equalsIgnoreCase("-u"))) {
                if (PermissionManager.checkPerm(event.getUser().getNick(), "helpUpdate")) {

                }else{
                    SjBot.eventBus.post(new noPermissionEvent("help (-u)", event.getUser(), "helpUpdate"));
                }
            }else if (args[1].equalsIgnoreCase("help")&&(args[2].equalsIgnoreCase("-a"))) {
                if (PermissionManager.checkPerm(event.getUser().getNick(), "helpAdd")) {

                }else{
                    SjBot.eventBus.post(new noPermissionEvent("help (-a)", event.getUser(), "helpAdd"));
                }
            }else if (args[1].equalsIgnoreCase("help")&&(args[2].equalsIgnoreCase("-r"))) {
                if (PermissionManager.checkPerm(event.getUser().getNick(), "helpRemove")) {

                }else{
                    SjBot.eventBus.post(new noPermissionEvent("help (-r)", event.getUser(), "helpRemove"));
                }
            }else{
                if (PermissionManager.checkPerm(event.getUser().getNick(), "help")) {

                }else{
                    SjBot.eventBus.post(new noPermissionEvent("help", event.getUser(), "help"));
                }
            }

        }

    }
    @Command(name = "blacklist", help = "blacklist users (bot wont respond to)", permission = "blacklist")
    public void blackList(commandHandler handler) {
        //0 name, 1 flag, 2 user/bot/???
        String[] args = handler.getArgs();
        if (args[1].equalsIgnoreCase("+")) {
            blacklistManager.addToList(args[2]);
            SjBot.bot.sendNotice(handler.getUser(),"Added "+args[2]+" to blacklist");
        }else if (args[1].equalsIgnoreCase("-")) {
            blacklistManager.removeFromList(args[2]);
            SjBot.bot.sendNotice(handler.getUser(),"Removed "+args[2]+" from blacklist");
        }else if (args[1].equalsIgnoreCase("-l")) {
            List<String> list = blacklistManager.getList();
            String out = "";
            for (String str : list) {
                out = out+" "+str+" -";
            }
            SjBot.bot.sendNotice(handler.getUser(), out);
        }
    }

    @Command(name = "load", help = "load a plugin", permission = "load")
    public void load(commandHandler handler) {
        String[] args = handler.getArgs();
        pluginRegistry.loadPlugin(args[1]);
        SjBot.bot.sendNotice(handler.getUser(), "Loaded plugin : "+args[1].toUpperCase());
    }

    @Command(name = "unload", help = "unload a plugin", permission = "unload")
    public void unload(commandHandler handler) {
        String[] args = handler.getArgs();
        pluginRegistry.unLoadPlugin(args[1]);
        SjBot.bot.sendNotice(handler.getUser(), "Unloaded plugin : "+args[1].toUpperCase());
    }




}

