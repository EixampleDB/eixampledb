package com.eixampledb.core;

import java.util.Optional;

public interface Eixampledb {

    Optional<EixamplebdEntry> get(String key);

    void set(String key, String value);

    /**
     * Deletes an entry by its key.
     * @param key The key of the entry to delete.
     * @return true if the entry exists, false if the entry did not exist.
     */
    boolean delete(String key);

}
