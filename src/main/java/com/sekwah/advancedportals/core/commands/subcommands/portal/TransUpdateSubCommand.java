package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.repository.ILangRepository;
import com.sekwah.advancedportals.core.services.PortalServices;
import com.sekwah.advancedportals.core.entities.containers.CommandSenderContainer;

import java.util.List;

public class TransUpdateSubCommand implements SubCommand {

    @Inject
    PortalServices portalServices;
    @Inject
    ILangRepository langRepository;



    public TransUpdateSubCommand() {
    }

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        langRepository.loadTranslations(args[0]);
        sender.sendMessage(langRepository.translateColor("messageprefix.positive") + langRepository.translateColor("translatedata.replaced"));
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp() || sender.hasPermission("advancedportals.transupdate");
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return langRepository.translate("command.trans.help");
    }

    @Override
    public String getDetailedHelpText() {
        return langRepository.translate("command.trans.help");
    }
}
