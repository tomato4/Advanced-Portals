package com.sekwah.advancedportals.core.commands.subcommands.desti;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.destination.Destination;
import com.sekwah.advancedportals.core.entities.DataTag;
import com.sekwah.advancedportals.core.entities.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.entities.containers.PlayerContainer;
import com.sekwah.advancedportals.core.repository.ILangRepository;
import com.sekwah.advancedportals.core.services.DestinationServices;

import com.sekwah.advancedportals.core.util.TagReader;

import java.util.ArrayList;
import java.util.List;

public class CreateDestiSubCommand implements SubCommand {
    @Inject
    private DestinationServices destinationServices;
    @Inject
    private ILangRepository langRepository;
    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1) {
            PlayerContainer player = sender.getPlayerContainer();
            if(player == null) {
                sender.sendMessage(langRepository.translateColor("messageprefix.negative") + langRepository.translate("command.createdesti.console"));
                return;
            }
            ArrayList<DataTag> destiTags = TagReader.getTagsFromArgs(args);
            Destination desti = destinationServices.createDesti(args[1], player, player.getLoc(), destiTags);
            if(desti != null) {
                sender.sendMessage(langRepository.translateColor("messageprefix.positive") + langRepository.translateColor("command.createdesti.complete"));
                sender.sendMessage(langRepository.translateColor("command.create.tags"));
                ArrayList<DataTag> destiArgs = desti.getArgs();
                if(destiArgs.size() == 0) {
                    sender.sendMessage(langRepository.translateColor("desti.info.noargs"));
                }
                else {
                    for (DataTag tag : destiArgs) {
                        sender.sendMessage("\u00A7a" + tag.NAME + "\u00A77:\u00A7e" + tag.VALUE);
                    }
                }
            }
            else {
                sender.sendMessage(langRepository.translateColor("messageprefix.negative") + langRepository.translateColor("command.createdesti.error"));
            }
        }
        else {
            sender.sendMessage(langRepository.translateColor("messageprefix.positive") + langRepository.translate("command.error.noname"));
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || sender.hasPermission("advancedportals.createportal");
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return langRepository.translate("command.createdesti.help");
    }

    @Override
    public String getDetailedHelpText() {
        return langRepository.translate("command.createdesti.detailedhelp");
    }
}
