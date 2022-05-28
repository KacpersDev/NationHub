package club.nationhcf;

import club.nationhcf.board.Board;
import club.nationhcf.listener.PlayerListener;
import club.nationhcf.scoreboard.ScoreboardHandler;
import club.nationhcf.scoreboard.reflection.ScoreboardReflection;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public final class Hub extends JavaPlugin implements PluginMessageListener {

    private Chat chat = null;
    private static Hub instance;
    public Map<String,Integer> playerCount;
    @Override
    public void onEnable() {
        instance = this;
        this.playerCount = new HashMap<>();
        this.bungeecord();
        this.config();
        this.listener();
        this.command();
        this.setupChat();
    }

    public static Hub getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {

    }

    private void config(){
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    private void command(){


    }


    private void listener(){
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new PlayerListener(), this);

        Assemble assemble = new Assemble(this, new Board());

        assemble.setTicks(20);

        assemble.setAssembleStyle(AssembleStyle.VIPER);
    }

    private void bungeecord() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
    }

    public void updateCount() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getServer().getOnlinePlayers().size() > 0) {
                    Hub.this.getCount((Player)Bukkit.getServer().getOnlinePlayers().toArray()[0], null);
                }
            }
        }.runTaskTimerAsynchronously(this, 20L, 20L);
    }

    @Override
    public void onPluginMessageReceived(final String channel, final Player player, final byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        try {
            if (message.length == 0) {
                return;
            }
            final ByteArrayDataInput in = ByteStreams.newDataInput(message);
            final String subchannel = in.readUTF();
            if (subchannel.equals("PlayerCount")) {
                final String server = in.readUTF();
                final int playerCount = in.readInt();
                this.playerCount.put(server, playerCount);
            }
        }
        catch (Exception ex) {}
    }

    private boolean setupChat() {
        RegisteredServiceProvider chatProvider = this.getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            chat = (Chat) chatProvider.getProvider();
        }
        return chat != null;
    }

    public void getCount(final Player player, String server) {
        if (server == null) {
            server = "ALL";
        }
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);
        player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }

    public int getOnlineCount(String server) {
        if (server == null) {
            server = "ALL";
            int online = 0;
            for (final int next : this.playerCount.values()) {
                if (next <= 0) {
                    continue;
                }
                online += next;
            }
            return online;
        }
        this.playerCount.putIfAbsent(server, -1);
        return this.playerCount.get(server);
    }

    public Chat getChat() {
        return chat;
    }
}
