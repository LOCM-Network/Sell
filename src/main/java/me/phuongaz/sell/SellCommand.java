package me.phuongaz.sell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;

public class SellCommand extends Command{
    
    public SellCommand(){
        super("sell", "Sell command");
        Map<String, CommandParameter[]> parameters = new HashMap<>();
        parameters.put("set",  new CommandParameter[]{
                new CommandParameter("sell", false, new String[]{"all", "hand"})
            }
        );
        setCommandParameters(parameters);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args){
        if(sender instanceof Player){
            if(args.length == 1){
                if(args[0].equals("hand")){
                    Loader.getInstance().sellHand((Player) sender);
                    return true;
                }
                if(args[0].equals("all")){
                    Loader.getInstance().sellAll((Player) sender);
                    return true;
                }
                if(args[0].equals("reload") && sender.hasPermission("sell.admin")){
                    Loader.getInstance().reload();
                    sender.sendMessage("reload config...");
                    return true;
                }
            }
            return true;
        }
        return false;
    }
}
