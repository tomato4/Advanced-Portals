package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.api.portal.AdvancedPortal;
import com.sekwah.advancedportals.core.repository.ILangRepository;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.entities.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.entities.containers.PlayerContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RemoveSubCommand implements SubCommand {

    @Inject
    private PortalServices portalServices;
    @Inject
    private ILangRepository langRepository;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        if(args.length > 1) {
            boolean portalStatus = portalServices.removePortal(args[1], sender.getPlayerContainer());

            if(portalStatus) {
                sender.sendMessage(langRepository.translateColor("messageprefix.positive") + langRepository.translateColor("command.remove.complete"));
            }
            else {
                sender.sendMessage(langRepository.translateColor("messageprefix.negative")
                        + langRepository.translateColor("command.remove.error"));
            }
        }
        else {
            PlayerContainer player = sender.getPlayerContainer();
            if(player == null) {
                sender.sendMessage(langRepository.translate("command.remove.noname"));
            }
            else {
                if(portalServices.removePlayerSelection(player)) {

                }
                else {
                    sender.sendMessage(langRepository.translateColor("messageprefix.negative")
                            + langRepository.translateColor("command.remove.error"));
                }

            }
        }
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || sender.hasPermission("advancedportals.createportal");
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        List<String> portalNames = new ArrayList<>();
        for(Map.Entry<String, AdvancedPortal> portal : portalServices.getPortals()) {
            portalNames.add(portal.getKey());
        }
        Collections.sort(portalNames);
        return portalNames;
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
