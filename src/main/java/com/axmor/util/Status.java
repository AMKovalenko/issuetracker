package com.axmor.util;

/**
 * Enumeration of available issue statuses.
 */
public enum Status {

    CREATED,
    SIGNED,
    ENDORSED,
    CLOSED,
    RESOLVED,
    CANCELLED;

    /**
     * Returns the information if given condition is final condition for issue.
     *
     * @param condition - String name of given condition
     * @return
     */
    public static boolean isFinalCondition(String condition){

        return  (CLOSED.name().equalsIgnoreCase(condition) || CANCELLED.name().equalsIgnoreCase(condition)
                || RESOLVED.name().equalsIgnoreCase(condition));
    }
}
