package club.nationhcf.listener;

import club.nationhcf.Hub;
import club.nationhcf.armor.Armor;
import club.nationhcf.utils.CC;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;

import static org.bukkit.Sound.ORB_PICKUP;

public class PlayerListener implements Listener {

    private final Armor armor = new Armor();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        Hub.getInstance().updateCount();
        //armor.applyOwner(player);
        selector(player);
        enderbutt(player);
        cosmetics(player);
        player.teleport(new Location(Bukkit.getWorld("World"),
                Hub.getInstance().getConfig().getDouble("spawn.x"),
                Hub.getInstance().getConfig().getDouble("spawn.y"),
                Hub.getInstance().getConfig().getDouble("spawn.z")));
        armor.applyToPlayer(player);

        for (final String i : Hub.getInstance().getConfig().getStringList("join.message")) {
            player.sendMessage(CC.translate(i));
        }
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (itemInHand.getType() == Material.ENDER_PEARL) {
                player.setVelocity(player.getLocation().getDirection().normalize().multiply(4));
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 10F);
                player.updateInventory();
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if (player.getLocation().getBlockY() < -30) {
            player.teleport(new Location(Bukkit.getWorld("World"),
                    Hub.getInstance().getConfig().getDouble("spawn.x"),
                    Hub.getInstance().getConfig().getDouble("spawn.y"),
                    Hub.getInstance().getConfig().getDouble("spawn.z")));
        }
    }


    @EventHandler
    public void onPlayerDoubleJump(PlayerToggleFlightEvent e){
        Player p = e.getPlayer();
        if(p.getGameMode() != GameMode.CREATIVE){
            e.setCancelled(true);
            Block b = p.getWorld().getBlockAt(p.getLocation().subtract(0,2,0));
            if(!b.getType().equals(Material.AIR)){
                Vector v = p.getLocation().getDirection().multiply(1).setY(1);
                p.setVelocity(v);
                p.playSound(p.getLocation(), ORB_PICKUP, 1f,1f);
            }
        }
    }

    @EventHandler
    public void onPlayerGround(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
            player.setAllowFlight(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    private void selector(Player player){

        ItemStack selector = new ItemStack(Material.valueOf(Hub.getInstance().getConfig().getString("items.selector.item")));
        ItemMeta meta = selector.getItemMeta();
        meta.setDisplayName(CC.translate(Hub.getInstance().getConfig().getString("items.selector.name")));
        ArrayList<String> lore = new ArrayList<>();
        for (final String i : Hub.getInstance().getConfig().getStringList("items.selector.lore")) {
            lore.add(CC.translate(i));
        }

        meta.setLore(lore);
        selector.setItemMeta(meta);

        player.getInventory().setItem(Hub.getInstance().getConfig().getInt("items.selector.slot"), selector);
    }

    private void enderbutt(Player player){

        ItemStack selector = new ItemStack(Material.valueOf(Hub.getInstance().getConfig().getString("items.enderbutt.item")));
        ItemMeta meta = selector.getItemMeta();
        meta.setDisplayName(CC.translate(Hub.getInstance().getConfig().getString("items.enderbutt.name")));
        ArrayList<String> lore = new ArrayList<>();
        for (final String i : Hub.getInstance().getConfig().getStringList("items.enderbutt.lore")) {
            lore.add(CC.translate(i));
        }

        meta.setLore(lore);
        selector.setItemMeta(meta);

        player.getInventory().setItem(Hub.getInstance().getConfig().getInt("items.enderbutt.slot"), selector);
    }

    private void cosmetics(Player player){

        ItemStack selector = new ItemStack(Material.valueOf(Hub.getInstance().getConfig().getString("items.cosmetics.item")));
        ItemMeta meta = selector.getItemMeta();
        meta.setDisplayName(CC.translate(Hub.getInstance().getConfig().getString("items.cosmetics.name")));
        ArrayList<String> lore = new ArrayList<>();
        for (final String i : Hub.getInstance().getConfig().getStringList("items.cosmetics.lore")) {
            lore.add(CC.translate(i));
        }

        meta.setLore(lore);
        selector.setItemMeta(meta);

        player.getInventory().setItem(Hub.getInstance().getConfig().getInt("items.cosmetics.slot"), selector);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){

        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerPickupItemEvent event){

        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR)
        || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            if (event.getPlayer().getItemInHand() == null) return;
            if (event.getPlayer().getItemInHand() == null ||
            event.getPlayer().getItemInHand().getItemMeta() == null ||
            event.getPlayer().getItemInHand().getItemMeta().getDisplayName() == null) return;
            if (event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
                    .equalsIgnoreCase(CC.translate(Hub.getInstance().getConfig().getString("items.selector.name")))) {

                Inventory inventory = Bukkit.createInventory(event.getPlayer(),
                        Hub.getInstance().getConfig().getInt("selector.size"),
                        CC.translate(Hub.getInstance().getConfig().getString("selector.title")));

                for (final String i : Hub.getInstance().getConfig().getConfigurationSection("selector.items").getKeys(false)) {
                    ItemStack item = new ItemStack(Material.valueOf(Hub.getInstance().getConfig().getString("selector.items." + i + ".item")));
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(CC.translate(Hub.getInstance().getConfig().getString("selector.items." + i + ".name")));
                    ArrayList<String> lore = new ArrayList<>();
                    for (final String l : Hub.getInstance().getConfig().getStringList("selector.items." + i + ".lore")) {
                        lore.add(CC.translate(l));
                    }
                    meta.setLore(lore);
                    item.setItemMeta(meta);

                    inventory.setItem(Hub.getInstance().getConfig().getInt("selector.items." + i + ".slot"),
                            item);
                }

                event.getPlayer().openInventory(inventory);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().getTitle().equalsIgnoreCase(CC.translate(Hub.getInstance().getConfig().getString(
                "selector.title"
        )))) {
            event.setCancelled(true);
        }
        for (final String i : Hub.getInstance().getConfig().getConfigurationSection("selector.items").getKeys(false)) {
            if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null
            || event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate(Hub.getInstance().getConfig().
                    getString("selector.items." + i + ".name")))) {
                String action = Hub.getInstance().getConfig().getString("selector.items." + i + ".action");
                player.performCommand(action);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        event.setDamage(0);
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent event) {
        event.setDamage(0);
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        event.setDamage(0);
        event.setCancelled(true);
    }

    @EventHandler
    public void onType(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String prefix = Hub.getInstance().getChat().getPlayerPrefix(player);

        event.setFormat(CC.translate(Hub.getInstance().getConfig().getString("chat-format")
                .replace("%player%", player.getName())
                .replace("%prefix%", prefix))
                .replace("%message%", event.getMessage())
                .replace("%online%", String.valueOf(Hub.getInstance().getOnlineCount("ALL")))
                .replace("%rank%", Hub.getInstance().getChat().getPlayerPrefix(player)));
    }
}
