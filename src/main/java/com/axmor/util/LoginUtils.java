package com.axmor.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * Storage for users, who authentificated successfully.
 * Information about the users stored here for 2 hours.
 * Maximum capacity - 10000 users.
 */
public class LoginUtils {

    private static Cache authentificationCache = CacheUtils.getCache("userCache", 10000, false, false, 2*60*60, 0);


    public static boolean isUserAuthentificated(String userName){

        return authentificationCache.isKeyInCache(userName);
    }

    public static void putAuthentificatedUserToCache(String username){

        if (isUserAuthentificated(username)){
            return;
        }
        authentificationCache.put(new Element(username, username));
    }

    public static void logoutUser(String username){

        if (isUserAuthentificated(username)){
            authentificationCache.remove(username);
        }
    }
}
