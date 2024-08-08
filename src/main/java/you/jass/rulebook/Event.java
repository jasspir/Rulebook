package you.jass.rulebook;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class Event implements Listener {
    private final Rulebook rulebook;

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (rulebook.getConfig().getBoolean("Show On Every Join") || (rulebook.getConfig().getBoolean("Show On First Join") && !player.hasPlayedBefore())) {
            player.openBook(rulebook.getRulebook());
        }
    }
}