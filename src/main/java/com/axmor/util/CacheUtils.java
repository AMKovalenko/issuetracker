package com.axmor.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CacheUtils {

    private final static CacheManager cacheManager = CacheManager.create();

    private final static Logger logger = LoggerFactory.getLogger(CacheUtils.class);

    /**
     * Returns a cache with given parameters. If cache for given name already exists - will be returned old cache instance.
     *
     * @param name - cache name
     * @param maxElementsInMemory - maximum amount of elements for storaging in memory
     * @param overflowToDisk - sets whether element can overflow to disk when the memory store has reached the maximum limit.
     * @param eternal - sets whether elements can live endlessly
     * @param timeToLiveSeconds - the maximum time between creation time and when an element expires.
     * @param timeToIdleSeconds - the maximum amount of time between accesses before an element expires
     * @return the instance of named cache.
     */

    public static Cache getCache(
            String name,
            int maxElementsInMemory,
            boolean overflowToDisk,
            boolean eternal,
            long timeToLiveSeconds,
            long timeToIdleSeconds
    ) {
        Cache result = cacheManager.getCache(name);
        if (result == null){
            synchronized (cacheManager) {
                result = cacheManager.getCache(name);
                if (result == null) {
                    result = new Cache(name, maxElementsInMemory, overflowToDisk, eternal, timeToLiveSeconds, timeToIdleSeconds);
                    cacheManager.addCache(result);
                    logger.info("New cache {} has been created.", name );
                }
            }
        }
        return result;
    }
}
