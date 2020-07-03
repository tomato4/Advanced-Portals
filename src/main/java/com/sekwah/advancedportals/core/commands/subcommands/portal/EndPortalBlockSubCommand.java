package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.repository.ILangRepository;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.entities.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.entities.containers.PlayerContainer;

import java.util.List;

public class EndPortalBlockSubCommand implements SubCommand {

    @Inject
    private PortalServices portalServices;
    @Inject
    ILangRepository langRepository;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        PlayerContainer player = sender.getPlayerContainer();
        if(player == null) {
            sender.sendMessage(langRepository.translateColor("messageprefix.negative") + langRepository.translate("command.playeronly"));
        }
        else {
            player.giveWool("BLACK", "\u00A78End Portal Block Placer"
                    , "\u00A7rThis wool is made of a magical substance",
                            "\u00A7rRight Click: Place portal block");
            sender.sendMessage(langRepository.translateColor("messageprefix.positive") + langRepository.translate("command.endportalblock"));
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
        return langRepository.translate("command.selector.help");
    }

    @Override
    public String getDetailedHelpText() {
        return langRepository.translate("command.selector.detailedhelp");
    }
}
