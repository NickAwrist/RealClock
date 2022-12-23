package me.nicka.realclock.realclock;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class ClockEvent implements Listener {

    final FileConfiguration config = RealClock.getPlugin().getConfig();

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event){
        Player p = event.getPlayer();


        Material clock_item = Material.valueOf(config.getString("clock_item"));
        if (p.getInventory().getItemInMainHand().getType() == clock_item) {

            boolean time_message = config.getBoolean("time_message");
            boolean villager_message = config.getBoolean("villager_message");
            boolean twelve_hour_system = config.getBoolean("twelve_12_hour_system");

            if (time_message) {
                if (event.getClickedBlock() == null && event.getAction().isRightClick()) {
                    if (p.getInventory().getItemInMainHand().getType() == clock_item) {


                        int time = (int) p.getWorld().getTime();
                        int hour = ((time / 1000) + 6) % 24;
                        int hourRef = time / 1000;
                        int minute = (time - (hourRef * 1000)) / 10;

                        String minuteFix = Integer.toString(minute);
                        if (minute < 10)
                            minuteFix = "0" + minute;

                        String HOUR = Integer.toString(hour);
                        String MINUTE = minuteFix;

                        String message = config.getString("Messages.time_message");
                        assert message != null;
                        if (twelve_hour_system) {
                            String MIDDAY;
                            if (hour > 11) {

                                hour = hour % 12;
                                if (hour == 0)
                                    hour = 12;

                                MIDDAY = "PM";
                            } else {

                                if (hour == 0)
                                    hour = 12;

                                MIDDAY = "AM";
                            }

                            HOUR = Integer.toString(hour);
                            Msg.send(p, message.replace("%HOUR%", HOUR).replace("%MINUTE%", MINUTE).replace("%MIDDAY%", MIDDAY));

                        } else {
                            Msg.send(p, message.replace("%HOUR%", HOUR).replace("%MINUTE%", MINUTE));
                        }

                    }
                }
            }

            if (villager_message) {
                if (event.getAction().isLeftClick()) {
                    if (p.getInventory().getItemInMainHand().getType() == clock_item) {

                        int time = (int) p.getWorld().getTime();

                        if (time >= 12000)
                            Msg.send(p, config.getString("Messages.villager_message_asleep"));
                        else
                            Msg.send(p, config.getString("Messages.villager_message_awake"));

                    }
                }
            }
        }
    }

}
