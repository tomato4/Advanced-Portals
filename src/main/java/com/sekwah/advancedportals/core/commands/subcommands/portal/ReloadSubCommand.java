package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.repository.ILangRepository;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.entities.containers.CommandSenderContainer;

import java.util.List;

public class ReloadSubCommand implements SubCommand {

    @Inject
    private PortalServices portalServices;
    @Inject
    private ILangRepository langRepository;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        portalServices.loadPortals();
        sender.sendMessage(langRepository.translateColor("messageprefix.positive") + langRepository.translateColor("command.reload.reloaded"));
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || sender.hasPermission("advancedportals.reload");
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return langRepository.translate("command.reload.help");
    }

    @Override
    public String getDetailedHelpText() {
        return langRepository.translate("command.reload.detailedhelp");
    }
}
