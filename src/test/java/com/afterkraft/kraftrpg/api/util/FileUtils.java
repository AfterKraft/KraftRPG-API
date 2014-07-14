package com.afterkraft.kraftrpg.api.util;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    protected FileUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Used to delete a folder.
     *
     * @param file The folder to delete.
     * @return true if the folder was successfully deleted.
     */
    public static boolean deleteFolder(File file) {
        try {
            org.apache.commons.io.FileUtils.deleteDirectory(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
