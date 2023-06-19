package com.mycomp.message.hub.core.fixture;

import com.mycomp.message.hub.core.utils.Utility;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.internal.Iterators;
import org.assertj.core.util.Lists;
import org.springframework.lang.Nullable;

import java.util.List;

public class EnumFixture {

    private EnumFixture() { }

    /**
     * Pick a random enum from an array.
     *
     * @param values    the enum values to pick from.
     * @param <E>       the type of enum.
     *
     * @return a random value from the series, or null if none were passed.
     */
    @Nullable
    public static <E extends Enum<E>> E random(final E... values) {
        final int size = Utility.sizeOf(values);
        if (size == 0) {
            return null;
        }

        return values[RandomUtils.nextInt() % size];
    }


    /**
     * Pick a random enum from a collection.
     *
     * @param values    the enum values to pick from.
     * @param <E>       the type of enum.
     *
     * @return a random value from the series, or null if none were passed.
     */
    @Nullable
    public static <E extends Enum<E>> E random(@Nullable final Iterable<E> values) {
        final List<E> valueList = values != null ? Lists.newArrayList(values) : Lists.newArrayList();
        final int size = Utility.sizeOf(valueList);

        if (size == 0) {
            return null;
        }

        return valueList.get(RandomUtils.nextInt() % size);
    }

    /**
     * Pick a random enum from an enum class.
     *
     * @param cls    the enum class to pick random value from.
     * @param <E>       the type of enum.
     *
     * @return a random value from the class, or null if none exists.
     */
    @Nullable
    public static <E extends Enum<E>> E random(final Class<E> cls) {
        return random(cls.getEnumConstants());
    }
}
