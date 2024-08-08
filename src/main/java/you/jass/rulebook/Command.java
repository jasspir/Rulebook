package you.jass.rulebook;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static co.aikar.commands.ACFBukkitUtil.color;

@RequiredArgsConstructor
@CommandAlias("rules")
public class Command extends BaseCommand {
    private final ItemStack rulebook;

    @Default
    @CommandCompletion("@players")
    @CommandPermission("rulebook.open")
    public void rules(final CommandSender sender, @Optional String name) {
        if (!(sender instanceof Player) && name == null) {
            sender.sendMessage(color("&cYou must be a player to see the rules!"));
            return;
        }

        Player player;

        if (name != null) {
            if (!sender.hasPermission("rulebook.show")) {
                sender.sendMessage(color("&cYou don't have permission to show the rules to another player!"));
                return;
            }

            player = Bukkit.getPlayer(name);
        } else {
            player = (Player) sender;
        }

        if (player == null) {
            sender.sendMessage(color("&cUnknown player!"));
            return;
        }

        player.openBook(rulebook);
    }
}