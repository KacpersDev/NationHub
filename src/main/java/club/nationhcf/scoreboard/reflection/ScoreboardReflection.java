package club.nationhcf.scoreboard.reflection;

import club.nationhcf.Hub;
import club.nationhcf.scoreboard.element.ScoreboardElement;
import club.nationhcf.scoreboard.element.ScoreboardElementHandler;
import club.nationhcf.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardReflection implements ScoreboardElementHandler {
    @Override
    public ScoreboardElement getElement(Player player) {

        final ScoreboardElement element = new ScoreboardElement();
        /*
                String[] titleList = {"&c&lNATION &7[Hub]",
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
         */


        element.setTitle(CC.translate("&2&lSagePvP &7| &fSquads"));
        /*
                new BukkitRunnable(){

            int i = 0;

            @Override
            public void run() {
                i++;

                if (i == 24) {
                    i = 0;
                }
                elementTitle = CC.translate(titleList[i]);

            }
        }.runTaskTimer(Hub.getInstance(), 0L,20L);
         */

        for (final String i : Hub.getInstance().getConfig().getStringList("lines-normal")) {
            element.getLines().add(CC.translate(i)
                    .replace("%online%", String.valueOf(Hub.getInstance().getOnlineCount("ALL"))));
        }

        return element;
    }
}
