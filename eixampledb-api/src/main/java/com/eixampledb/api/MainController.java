package com.eixampledb.api;

import com.eixampledb.core.EixamplebdEntry;
import com.eixampledb.core.Eixampledb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private Eixampledb eixampledb;

    @RequestMapping(path = "/{key}", method = RequestMethod.GET)
    public ResponseEntity<String> get(@PathVariable("key") String key) {
        Optional<EixamplebdEntry> eixamplebdEntry = eixampledb.get(key);
        if (eixamplebdEntry.isPresent()) {
            return ResponseEntity.ok(eixamplebdEntry.get().getValue());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(path = "/{key}", method = RequestMethod.POST)
    public ResponseEntity set(@PathVariable("key") String key, @RequestBody String value) {
        eixampledb.set(key, value);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/{key}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("key") String key) {
        boolean existed = eixampledb.delete(key);
        if (existed) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
