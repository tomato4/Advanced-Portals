package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.repository.IConfigurations;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.repository.LangRepository;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.entities.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.entities.containers.PlayerContainer;

import java.util.List;

public class SelectorSubCommand implements SubCommand {

    @Inject
    private PortalServices portalServices;
    @Inject
    private IConfigurations IConfigurations;
    @Inject
    private LangRepository langRepository;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        PlayerContainer player = sender.getPlayerContainer();
        if(player == null) {
            sender.sendMessage(langRepository.translateColor("messageprefix.negative") + langRepository.translate("command.playeronly"));
        }
        else {
            player.giveItem(IConfigurations.getSelectorMaterial(), "\u00A7ePortal Region Selector"
                    , "\u00A7rThis wand with has the power to help", "\u00A7r create portals bistowed upon it!");
            sender.sendMessage(langRepository.translateColor("messageprefix.positive") + langRepository.translate("command.selector"));
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
