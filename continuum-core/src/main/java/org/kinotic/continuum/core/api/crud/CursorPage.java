package org.kinotic.continuum.core.api.crud;

import java.util.List;

/**
 * A {@link Page} that uses a cursor to track the current position in the result set.
 * Created by Navíd Mitchell 🤪 on 11/5/23.
 */
public class CursorPage<T> extends Page<T> {

    private final String cursor;

    public CursorPage(int size, long totalElements, List<T> content, String cursor) {
        super(size, totalElements, content);
        this.cursor = cursor;
    }

    /**
     * @return The cursor to be used for subsequent retrieval of data, or null if this is the last page
     */
    public String getCursor(){
        return cursor;
    }
}
