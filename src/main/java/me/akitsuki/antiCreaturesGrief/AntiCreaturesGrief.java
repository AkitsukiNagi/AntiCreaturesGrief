package me.akitsuki.antiCreaturesGrief;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.akitsuki.antiCreaturesGrief.commands.ReloadCommand;
import me.akitsuki.antiCreaturesGrief.events.GrievingEventListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class AntiCreaturesGrief extends JavaPlugin {
    private static GrievingEventListener listener;

    @Override
    public void onEnable() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();

            ReloadCommand reloadCommand = new ReloadCommand(this);
            commands.register(
                    Commands.literal("acg")
                            .then(Commands.literal("reload")
                                    .requires(source -> source.getSender().hasPermission(reloadCommand.permission()))
                                            .executes(ctx -> {
                                                reloadCommand.execute(ctx.getSource(), null);
                                                return 0;
                                            })
                                    )
                            .build(),
                    "main command of AntiCreaturesGrief"
            );
        });

        this.saveDefaultConfig();
        listener = new GrievingEventListener(this);
        getServer().getPluginManager().registerEvents(listener, this);


        getLogger().log(Level.INFO, "Enabled plugin.");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Disabled plugin.");
    }

    public GrievingEventListener getListener() {
        return listener;
    }
}
