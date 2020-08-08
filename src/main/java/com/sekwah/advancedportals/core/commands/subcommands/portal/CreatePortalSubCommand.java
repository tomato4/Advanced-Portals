package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;

import com.sekwah.advancedportals.core.entities.DataTag;
import com.sekwah.advancedportals.core.entities.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.entities.containers.PlayerContainer;
import com.sekwah.advancedportals.core.repository.ILangRepository;
import com.sekwah.advancedportals.core.repository.IPortalRepository;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.util.TagReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatePortalSubCommand implements SubCommand {
    @Inject
    private ILangRepository langRepository;
    @Inject
    private PortalServices portalServices;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1) {
            PlayerContainer player = sender.getPlayerContainer();
            if(player == null) {
                sender.sendMessage(langRepository.translateColor("messageprefix.negative") + langRepository.translate("command.create.console"));
                return;
            }
            ArrayList<DataTag> portalTags = TagReader.getTagsFromArgs(args);

            AdvancedPortal portal = portalServices.createPortal(args[1], player, portalTags);
            if(portal != null) {
                sender.sendMessage(langRepository.translateColor("messageprefix.positive") + langRepository.translateColor("command.create.complete"));
                sender.sendMessage(langRepository.translateColor("command.create.tags"));
                sender.sendMessage("\u00A7a" + "triggerBlock\u00A77:\u00A7e" + Arrays.toString(portal.getTriggerBlocks()));
                for (DataTag tag: portal.getArgs()) {
                    sender.sendMessage("\u00A7a" + tag.NAME + "\u00A77:\u00A7e" + tag.VALUE);
                }
            }
            sender.sendMessage(langRepository.translateColor("messageprefix.negative") + langRepository.translateColor("command.create.error"));
        }
        else {
            sender.sendMessage(langRepository.translateColor("messageprefix.positive") + langRepository.translate("command.error.noname"));
        }
    }

    protected String getTag(String arg) {
        int splitLoc = arg.indexOf(":");
        if(splitLoc != -1) {
            return arg.substring(0,splitLoc);
        }
        return null;
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
        return langRepository.translate("command.create.help");
    }

    @Override
    public String getDetailedHelpText() {
        return langRepository.translate("command.create.detailedhelp");
    }
}
