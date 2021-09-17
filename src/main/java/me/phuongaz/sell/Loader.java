package me.phuongaz.sell;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import me.onebone.economyapi.EconomyAPI;

public class Loader extends PluginBase{

    private static Loader _instance;

    @Override
    public void onEnable(){
        _instance = this;
        saveDefaultConfig();
        getServer().getCommandMap().register("Sell", new SellCommand());
    }

    public void reload(){
        getConfig().reload();
        reloadConfig();
    }

    public void sellHand(Player player){
        Item item = player.getInventory().getItemInHand();
        if(canSell(item)){
            Double price = getPrice(item) * item.getCount();
            EconomyAPI.getInstance().addMoney(player, price);
            player.getInventory().setItemInHand(Item.get(Item.AIR));
            player.sendMessage(TextFormat.colorize("&l&eBan vua nhan duoc " + price + " Xu"));
        }
    }

    public void sellAll(Player player){
        Double price = 0D;
        for(Item item : player.getInventory().getContents().values()){
            if(canSell(item)){
                price += getPrice(item) * item.getCount();
                player.getInventory().remove(item);
            }
        }
        if(price != 0D){
            EconomyAPI.getInstance().addMoney(player, price);
            player.sendMessage(TextFormat.colorize("&l&eBan vua nhan duoc " + price + " Xu"));
        }
    }

    public boolean canSell(Item item){
        String id = getKey(item);
        return getConfig().exists("sell." + id);
    }

    public double getPrice(Item item){
        return getConfig().getDouble("sell." + getKey(item));
    }

    public String getKey(Item item){
        String id = String.valueOf(item.getId());
        if(item.getDamage() != 0){
            id += ":" + item.getDamage();
        }
        return id;
    }

    public static Loader getInstance(){
        return _instance;
    }
}