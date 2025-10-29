package com.anonymouslyfast.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.coordinate.Area;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.block.Block;

public class CreateAreaCommand extends Command {

    private enum AreaTypes {
            box,
            cube,
            cuboid,
            line,
            section,
            single,
            sphere
    }

    public CreateAreaCommand() {
        super("createarea", "ca");

        // Set the callback function if no argument is sent.
        setDefaultExecutor((sender, context) -> sender.sendMessage(helpMsg));

        // Creates the arguments
        var stringArg = ArgumentType.String("argument");
        var areaTypeArg = ArgumentType.Enum("shape", AreaTypes.class);

        // Create functionality of the argument
        addSyntax((sender, context) -> {
            final String argument = context.get(stringArg); // Gets the string arg by identifier

            switch (argument) {
                case "list":
                    sender.sendMessage(listMsg);
                    break;
                case "help":
                    sender.sendMessage(helpMsg);
                    break;
            }
        }, stringArg);

        addSyntax((sender, context) -> {
            final AreaTypes shape = context.get(areaTypeArg);
            Area selectedArea;
            int size = 2;
            Player player = (Player) sender;
            Point playerPosition = player.getPosition();
            Point sizePoint = new Vec(size); // default size will change to be an arg once learned.
            switch(shape) {
                case box:
                    selectedArea = Area.box(playerPosition, sizePoint);
                    break;
                case cube:
                    selectedArea = Area.cube(playerPosition, size);
                    break;
                case cuboid:
                    selectedArea = Area.cuboid(playerPosition, sizePoint);
                    break;
                case line:
                    selectedArea = Area.line(playerPosition, sizePoint);
                    break;
                case section:
                    selectedArea = Area.section(playerPosition.sectionX() + 1,
                            playerPosition.sectionY() + 2, playerPosition.sectionZ() +3);
                    break;
                case single:
                    selectedArea = Area.single(playerPosition);
                    break;
                case sphere:
                    selectedArea = Area.sphere(playerPosition.add(new Vec(2)), size);
                    break;
                default:
                    sender.sendMessage(listMsg);
                    return;
            }
            player.getInstance().setBlockArea(selectedArea, Block.COBBLESTONE);
        }, areaTypeArg);
    }

    private final String[] helpMsg = new String[]{
            "== Create Area Help ==",
            "  - /createarea <shape>",
            "  - /createarea list",
            "  - /createarea help"
    };

    private final String[] listMsg = new String[]{
            "== Create Area List ==",
            "  - box",
            "  - cube",
            "  - cuboid",
            "  - line",
            "  - section",
            "  - single",
            "  - sphere"
    };


}
