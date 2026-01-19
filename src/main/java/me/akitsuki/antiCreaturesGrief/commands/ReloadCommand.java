package me.akitsuki.antiCreaturesGrief.commands;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.akitsuki.antiCreaturesGrief.AntiCreaturesGrief;
import me.akitsuki.antiCreaturesGrief.BaseCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class ReloadCommand extends BaseCommand {
    final private AntiCreaturesGrief plugin;

    public ReloadCommand(AntiCreaturesGrief plugin) {
        super("acg.reload");
        this.plugin = plugin;
    }

    @Override
    public void execute(@NotNull CommandSourceStack source, @NonNull @NotNull String[] args) {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();

        if (plugin.getListener() != null) {
            plugin.getListener().updateConfig();
        }

        source.getSender().sendMessage(
                Component.text( "Config reloaded!", NamedTextColor.GOLD)
        );
    }
}
