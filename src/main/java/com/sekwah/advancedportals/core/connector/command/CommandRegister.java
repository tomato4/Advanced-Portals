package com.sekwah.advancedportals.core.connector.command;

import com.sekwah.advancedportals.core.registry.CommandTemplate;

public interface CommandRegister {

    /**
     * Registers the command to the appropriate system
     * @param commandName
     * @param commandExecutor
     */
    void registerCommand(String commandName, CommandTemplate commandExecutor);

}
