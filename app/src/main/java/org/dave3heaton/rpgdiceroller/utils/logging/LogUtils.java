package org.dave3heaton.rpgdiceroller.utils.logging;

import android.util.Log;

/**
 * Utility class to enable easy disabling of logging for production releases
 */
public class LogUtils {

    private static boolean debugLogActive = false;

    public static void debug(String category, String message) {
        if (debugLogActive) {
            Log.d(category, message);
        }
    }
}
