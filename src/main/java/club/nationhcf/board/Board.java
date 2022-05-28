package club.nationhcf.board;

import club.nationhcf.Hub;
import club.nationhcf.utils.CC;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Board implements AssembleAdapter {

    private String scoreboardTitle = "&cNATION";
    private String[] titles = {"&c&lNATION &7[Hub]",
            "&c&lNATION &7[Hub]",
            "&c&lNATION &7[Hub]",
            "&c&lNATION &7[Hub]",
            "&c&lNATION &7[Hub]",
            "&c&lNATION &7[Hub]",
            "&4&lN&c&LATION &7[Hub]",
            "&f&lN&4&lA&c&lTION &7[Hub]",
            "&f&lNA&4&lT&c&lION &7[Hub]",
            "&f&lNAT&4&LI&c&lON &7[Hub]",
            "&f&lNATI&4&LO&c&lN &7[Hub]",
            "&f&LNATIO&4&lN &7[Hub]",
            "&F&lNATION &7[Hub]",
            "&C&lN&f&LATION &7[Hub]",
            "&4&lN&c&LA&f&lTION &7[Hub]",
            "&4&LNA&c&lT&f&LION &7[Hub]",
            "&4&lNAT&c&LI&f&lON &7[Hub]",
            "&4&LNATI&c&lO&f&lN &7[Hub]",
            "&4&lNATIO&c&LN &7[Hub]",
            "&4&lNATION &7[Hub]",
            "&c&lNATION &7[Hub]",
            "&4&lNATION &7[Hub]",
            "&c&lNATION &7[Hub]",
            "&4&lNATION &7[Hub]",
            "&c&lNATION &7[Hub]",};
    @Override
    public String getTitle(Player player) {

        new BukkitRunnable(){

            int i = 0;

            @Override
            public void run() {
                i++;

                if (i == 24) {
                    i = 0;
                }

                scoreboardTitle = titles[i];

            }
        }.runTaskTimer(Hub.getInstance(), 0L,60L);

        return CC.translate(scoreboardTitle);
    }

    @Override
    public List<String> getLines(Player player) {
        final List<String> elements = new ArrayList<>();

        for (final String i : Hub.getInstance().getConfig().getStringList("lines-normal")){
            elements.add(CC.translate(i)
                    .replace("%player%", player.getName())
                    .replace("%online%", String.valueOf(Hub.getInstance().getOnlineCount("ALL")))
                    .replace("%rank%", Hub.getInstance().getChat().getPrimaryGroup(player)));
        }

        return elements;
    }
}
