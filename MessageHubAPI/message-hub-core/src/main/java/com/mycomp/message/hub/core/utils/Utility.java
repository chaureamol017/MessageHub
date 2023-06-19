package com.mycomp.message.hub.core.utils;

import com.fasterxml.jackson.databind.util.ArrayIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Utility {


    public static String firstNonEmpty(final CharSequence... items) {
        Iterable iterable = items != null ? new ArrayIterator(items) : new ArrayIterator(new Object[]{});
        Iterator var1 = iterable.iterator();

        CharSequence value;
        do {
            if (!var1.hasNext()) {
                return "";
            }

            value = (CharSequence) var1.next();
        } while (value == null || value.length() == 0);

        return value.toString();
    }

    public static int sizeOf(final CharSequence string) {
        return (string != null) ? string.length() : 0;
    }

    public static int sizeOf(final Collection<?> collection) {
        return (collection != null) ? collection.size() : 0;
    }

    public static <T> int sizeOf(final T[] objectArray) {
        return (objectArray != null) ? objectArray.length : 0;
    }

    public static boolean isEmpty(final CharSequence string) {
        return sizeOf(string) == 0;
    }

    public static boolean notEmpty(final CharSequence string) {
        return sizeOf(string) > 0;
    }
}
