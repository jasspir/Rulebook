package you.jass.rulebook;

import co.aikar.commands.PaperCommandManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.map.MinecraftFont;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Rulebook extends JavaPlugin {
    private PaperCommandManager commandManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        commandManager = new PaperCommandManager(this);
        commandManager.enableUnstableAPI("brigadier");
        commandManager.enableUnstableAPI("help");

        commandManager.registerCommand(new Command(getRulebook()));

        Bukkit.getPluginManager().registerEvents(new Event(this), this);
    }

    public ItemStack getRulebook() {
        ItemStack rulebook = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta book = (BookMeta) rulebook.getItemMeta();

        assert book != null;

        book.setTitle(getConfig().getString("Title"));
        book.setAuthor(getConfig().getString("Author"));

        String rules = color(String.join("&r\n", getConfig().getStringList("Rules")));
        book.setPages(rules.split(color("NEXT PAGE&r\n")));

        rulebook.setItemMeta(book);

        return rulebook;
    }

    public String color(String msg) {
        Pattern rgbPattern = Pattern.compile("&#[a-fA-F0-9]{6}");

        if (msg.contains("#")) {
            Matcher rgbMatch = rgbPattern.matcher(msg);

            while (rgbMatch.find()) {
                final String color = msg.substring(rgbMatch.start(), rgbMatch.end());
                msg = msg.replace(color, ChatColor.of(color.substring(1)) + "");
                rgbMatch = rgbPattern.matcher(msg);
            }
        }

        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}