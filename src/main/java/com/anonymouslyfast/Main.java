package com.anonymouslyfast;

import com.anonymouslyfast.commands.CreateAreaCommand;
import net.minestom.server.Auth;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;

public class Main {

    private static final Pos spawningPosition = new Pos(0, 5, 0);

    static void main(String[] args) {
        // Creating Server
        MinecraftServer server = MinecraftServer.init();

        // Creating the instance (instance = world)
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        // Instance container is basically a container that can hold loads of chunk data.
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        // Generating the world
        // Creating own simple flat world generator.
        instanceContainer.setGenerator(unit -> {
            unit.modifier().fillHeight(0, 1, Block.BEDROCK);
            unit.modifier().fillHeight(1, 3, Block.DIRT);
            unit.modifier().fillHeight(3, 4, Block.GRASS_BLOCK);
        });


        // Adding lightning
        instanceContainer.setChunkSupplier(LightingChunk::new);

        // Event to for player spawning
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(spawningPosition);
        });

        CommandManager commandManager = MinecraftServer.getCommandManager();
        commandManager.register(new CreateAreaCommand());


        server.start("0.0.0.0", 25565); // Starts on localhost with default mc port.
    }

}
