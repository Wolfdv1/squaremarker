package dev.sentix.squaremarker.command.commands

import cloud.commandframework.context.CommandContext
import cloud.commandframework.minecraft.extras.MinecraftExtrasMetaKeys
import dev.sentix.squaremarker.Components
import dev.sentix.squaremarker.Lang
import dev.sentix.squaremarker.SquareMarker
import dev.sentix.squaremarker.command.CommandManager
import dev.sentix.squaremarker.command.SquaremarkerCommand
import dev.sentix.squaremarker.marker.Marker
import dev.sentix.squaremarker.marker.MarkerService
import org.bukkit.command.CommandSender

class ListMarkerCommand(plugin: SquareMarker, commandManager: CommandManager) :
    SquaremarkerCommand(
        plugin,
        commandManager
    ) {

    override fun register() {
        this.commandManager.registerSubcommand { builder ->
            builder.literal("list")
                .meta(MinecraftExtrasMetaKeys.DESCRIPTION, Components.parse("List all markers."))
                .permission("squaremarker.list")
                .handler(this::execute)
        }
    }

    private fun execute(context: CommandContext<CommandSender>) {
        val sender = context.sender

        val markerList = MarkerService.getMarkerList()

        if (markerList.isNotEmpty()) {
            sendMarkerList(sender, markerList)
        } else {
            Components.send(sender, Lang.EMPTY)
        }

    }

    private fun sendMarkerList(sender: CommandSender, markerList: MutableList<Marker>) {
        val gradient = Components.gradient("<b>Marker</b>")

        Components.send(sender, "")
        Components.send(
            sender,
            "<dark_gray>» <dark_gray><st>-------------<r> <gray>× $gradient <gray>× <dark_gray><st>-------------<r> <dark_gray>«"
        )
        Components.send(sender, "")

        Components.send(sender, " <gray>× <color:#8411FB>Markers <gray>[<color:#8411FB>" + markerList.size + "<gray>]")
        Components.send(sender, "")
        for (marker in markerList) {
            Components.send(
                sender,
                " <gray>× <color:#8411FB>${marker.id} <color:#8411FB>${
                    Components.clickable(
                        "<dark_gray>[<color:#8411FB>SHOW</color>]",
                        "<color:#8411FB>SHOW",
                        "/squaremarker show ${marker.id}"
                    )
                }"
            )
        }

        Components.send(sender, "")
        Components.send(
            sender,
            "<dark_gray>» <dark_gray><st>-------------<r> <gray>× $gradient <gray>× <dark_gray><st>-------------<r> <dark_gray>«"
        )
        Components.send(sender, "")
    }

}