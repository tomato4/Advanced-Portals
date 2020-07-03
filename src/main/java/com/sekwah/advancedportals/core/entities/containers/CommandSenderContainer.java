package com.sekwah.advancedportals.core.entities.containers;

public interface CommandSenderContainer {

    void sendMessage(String message);

    boolean isOp();

    /**
     * @return null if there isnt a player e.g. the console
     */
    PlayerContainer getPlayerContainer();

    boolean hasPermission(String permission);

}
