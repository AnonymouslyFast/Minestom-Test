package com.anonymouslyfast.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.coordinate.Area;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.block.Block;

public class CreateAreaCommand extends Command {

    public CreateAreaCommand() {
        super("createarea", "ca");

        // Set the callback function if no argument is sent.
        setDefaultExecutor((sender, context) -> sender.sendMessage(helpMsg));

        // Creates the argument with id 'argument' (Will appear above arg in chat like <argument>)
        var stringArg = ArgumentType.String("argument");

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
                default:
                    Area selectedArea;
                    int size = 2;
                    Player player = (Player) sender;
                    Point playerPosition = player.getPosition();
                    Point sizePoint = new Vec(size); // default size will change to be an arg once learned.
                    if (argument.equalsIgnoreCase("box")) {
                        selectedArea = Area.box(playerPosition, sizePoint);
                    } else if (argument.equalsIgnoreCase("cube")) {
                        selectedArea = Area.cube(playerPosition, size);
                    } else if (argument.equalsIgnoreCase("cuboid")) {
                        selectedArea = Area.cuboid(sizePoint, playerPosition); // can change later, will require more args.
                    } else if (argument.equalsIgnoreCase("line")) {
                        selectedArea = Area.line(playerPosition, sizePoint);
                    }  else if (argument.equalsIgnoreCase("section")) {
                        selectedArea = Area.section(playerPosition.sectionX() + 1,
                                playerPosition.sectionY() + 2, playerPosition.sectionZ() +3);
                    } else if (argument.equalsIgnoreCase("single")) {
                        selectedArea = Area.single(playerPosition);
                    } else if (argument.equalsIgnoreCase("sphere")) {
                        selectedArea = Area.sphere(playerPosition.add(new Vec(2)), size);
                    } else {
                        sender.sendMessage(listMsg);
                        return;
                    }

                    // Creating the shape
                    player.getInstance().setBlockArea(selectedArea, Block.COBBLESTONE);
                    break;
            }
        }, stringArg);
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
