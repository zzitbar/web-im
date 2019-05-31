package com.zzitbar.webim.cache;

/**
 * @auther H2102371
 * @create 2019-05-06 下午 02:31
 * @Description
 */
public class CacheUtil {

    private static MemoryCache memoryCache = new MemoryCache();

    public static Cache getCache() {
        return memoryCache;
    }
}
