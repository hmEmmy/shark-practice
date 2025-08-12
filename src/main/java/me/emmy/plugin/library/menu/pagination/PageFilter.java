package me.emmy.plugin.library.menu.pagination;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.function.Predicate;

@Getter
@Setter
@RequiredArgsConstructor
public class PageFilter<T> {
    private final String name;
    private final Predicate<T> predicate;
    private boolean enabled;

    /**
     * Checks if the filter is enabled and applies the predicate to the given object.
     *
     * @param t the object to test against the predicate.
     * @return true if the filter is not enabled or the predicate returns true for the object, false otherwise.
     */
    public boolean test(T t) {
        return !enabled || predicate.test(t);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof PageFilter && ((PageFilter) object).getName().equals(name);
    }
}