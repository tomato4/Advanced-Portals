package com.sekwah.advancedportals.spigot.convertolddata;

import com.google.inject.Inject;
import com.sekwah.advancedportals.core.api.commands.SubCommand;
import com.sekwah.advancedportals.core.entities.containers.CommandSenderContainer;
import com.sekwah.advancedportals.core.repository.ILangRepository;
import com.sekwah.advancedportals.core.repository.LangRepository;

import java.util.List;

/**
 * TODO this is for spigot only for a few releases
 */
public class ConvertOldSubCommand implements SubCommand {
    @Inject
    ILangRepository langRepository;
    @Override
    public void onCommand(CommandSenderContainer sender, String[] args) {
        sender.sendMessage(langRepository.translateColor("messageprefix.positive") + langRepository.translateColor(" Old portal data found."));
        sender.sendMessage(langRepository.translateColor("messageprefix.positive") + langRepository.translateColor(" Old portal data successfully converted."));
        sender.sendMessage(langRepository.translateColor("messageprefix.positive") + langRepository.translateColor(" Old desti data found."));
        sender.sendMessage(langRepository.translateColor("messageprefix.positive") + langRepository.translateColor(" Old desti data successfully converted."));
        sender.sendMessage(langRepository.translateColor("messageprefix.positive") + langRepository.translateColor(" Those were just sample outputs, it doesnt work yet."));
    }

    @Override
    public boolean hasPermission(CommandSenderContainer sender) {
        return sender.isOp();
    }

    @Override
    public List<String> onTabComplete(CommandSenderContainer sender, String[] args) {
        return null;
    }

    @Override
    public String getBasicHelpText() {
        return null;
    }

    @Override
    public String getDetailedHelpText() {
        return null;
    }
}
