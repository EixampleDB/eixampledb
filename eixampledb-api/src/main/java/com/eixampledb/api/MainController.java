package com.eixampledb.api;

import com.eixampledb.core.CoreServices;
import com.eixampledb.core.api.EixampleDb;
import com.eixampledb.core.api.Operation;
import com.eixampledb.core.api.ValueType;
import com.eixampledb.core.api.SearchType;
import com.eixampledb.core.api.request.*;
import com.eixampledb.core.api.response.DeleteResponse;
import com.eixampledb.core.api.response.GetResponse;
import com.eixampledb.core.api.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final EixampleDb eixampledb;
    private CoreServices core = new CoreServices();

    @RequestMapping(path = "/{key}", method = RequestMethod.GET)
    public ResponseEntity<String> get(@PathVariable("key") String key) {
        GetResponse getResponse = eixampledb.get(new GetRequest(key));
        if (getResponse.isSuccess()) {
            return ResponseEntity.ok(String.valueOf(getResponse.getEntry().get().getValue()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(path = "/{key}", method = RequestMethod.POST)
    public ResponseEntity set(@PathVariable("key") String key,
                              @RequestBody String value,
                              @RequestHeader(name = "type", defaultValue = "STR", required = false) ValueType valueType,
                              @RequestHeader (name = "search", defaultValue = "DEF", required = false) SearchType searchType ) {
            /*
            String search = header.get("search");
            int searchType = 0;
            if (search == null) search = "";
            if (search.equals("STARTS")) searchType = 1;
            else if (search.equals("REGEX")) searchType = 2;

            */
        eixampledb.set(new SetRequest(key, value, valueType, searchType));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/{key}", method = RequestMethod.PUT)
    public ResponseEntity operation(@PathVariable("key") String key,
                                    @RequestHeader(value = "op") Operation operation) {
        Response<?> response;
        if (operation.isDecrement()) {
            response = eixampledb.decr(new DecrRequest(key));
        } else if (operation.isIncrement()) {
            response = eixampledb.incr(new IncrRequest(key));
        } else {
            throw new UnsupportedOperationException("Unsupported operaton " + operation.name());
        }
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(path = "/{key}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("key") String key,
                                 @RequestHeader (name = "search", defaultValue = "DEF", required = false) SearchType searchType ) {
        DeleteResponse deleteResponse = eixampledb.delete(new DeleteRequest(key, searchType));
        if (deleteResponse.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
