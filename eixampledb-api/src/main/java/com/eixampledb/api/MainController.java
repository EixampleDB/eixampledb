package com.eixampledb.api;

import com.eixampledb.core.api.EixampleDb;
import com.eixampledb.core.api.request.DeleteRequest;
import com.eixampledb.core.api.request.GetRequest;
import com.eixampledb.core.api.request.SetRequest;
import com.eixampledb.core.api.response.DeleteResponse;
import com.eixampledb.core.api.response.GetResponse;
import com.eixampledb.core.api.response.SetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final EixampleDb eixampledb;

    @RequestMapping(path = "/{key}", method = RequestMethod.GET)
    public ResponseEntity<String> get(@PathVariable("key") String key) {
        GetResponse getResponse = eixampledb.get(new GetRequest(key));
        if (getResponse.isSuccess()) {
            return ResponseEntity.ok(getResponse.getEntry().get().getValue());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(path = "/{key}", method = RequestMethod.POST)
    public ResponseEntity set(@PathVariable("key") String key, @RequestBody String value) {
        SetResponse setResponse = eixampledb.set(new SetRequest(key, value));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/{key}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("key") String key) {
        DeleteResponse deleteResponse = eixampledb.delete(new DeleteRequest(key));
        if (deleteResponse.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
