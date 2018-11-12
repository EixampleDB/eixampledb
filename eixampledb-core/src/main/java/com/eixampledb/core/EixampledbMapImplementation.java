package com.eixampledb.core;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EixampledbMapImplementation implements Eixampledb {

    private final Map<String, EixamplebdEntry> map = new HashMap<>();

    @Override
    public Optional<EixamplebdEntry> get(String key) {
        return Optional.ofNullable(map.get(key));
    }

    @Override
    public void set(String key, String value) {
        Optional<EixamplebdEntry> entry = get(key);
        if (entry.isPresent()) {
            map.put(key, new EixamplebdEntry(key, value, entry.get().getCreationTimestamp(), System.currentTimeMillis()));
        } else {
            map.put(key, new EixamplebdEntry(key, value, System.currentTimeMillis(), System.currentTimeMillis()));
        }
    }

    @Override
    public boolean delete(String key) {
        return map.remove(key) != null;
    }
}
