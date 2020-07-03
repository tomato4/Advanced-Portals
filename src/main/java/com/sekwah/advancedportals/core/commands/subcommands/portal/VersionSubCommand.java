package com.sekwah.advancedportals.core.commands.subcommands.portal;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.registry.SubCmd;
import com.sekwah.advancedportals.core.repository.ILangRepository;
import com.sekwah.advancedportals.core.entities.containers.CommandSenderContainer;

import java.util.List;

@SubCmd(name="version", parent=SubCmd.TYPE.PORTAL, minArgs=5, permissions= {"Test"})
public class VersionSubCommand implements SubCommand {
    @Inject
    ILangRepository langRepository;

    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        sender.sendMessage(langRepository.translateColor("messageprefix.positive") + " Advanced Portals v" + AdvancedPortalsCore.version);
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return langRepository.translate("command.version.help");
    }

    @Override
    public String getDetailedHelpText() {
        return langRepository.translate("command.version.help");
    }
}
