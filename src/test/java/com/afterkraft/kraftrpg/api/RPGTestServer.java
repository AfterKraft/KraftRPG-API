package com.afterkraft.kraftrpg.api;

import java.util.logging.Logger;

import org.easymock.EasyMock;

import org.bukkit.Bukkit;
import org.bukkit.Server;

public class RPGTestServer {
    public static boolean ran;
    public static RPGTestServer instance;

    static {
        Server mockServer = EasyMock.createNiceMock(Server.class);
        EasyMock.expect(mockServer.getName()).andStubReturn("MockServer");
        EasyMock.expect(mockServer.getVersion()).andStubReturn("1");
        EasyMock.expect(mockServer.getBukkitVersion()).andStubReturn("1.7.9");
        EasyMock.expect(mockServer.getLogger()).andStubReturn(Logger.getLogger("MockServer"));

        // Finished! Mark as ready.
        EasyMock.replay(mockServer);

        Bukkit.setServer(mockServer);
    }

    public static void init() {
        // delegated to static {} block
    }
}
