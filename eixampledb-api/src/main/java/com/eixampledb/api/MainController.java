package com.eixampledb.api;

import com.eixampledb.core.CoreServices;
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
            return ResponseEntity.ok(getResponse.getEntry().get().getValue());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(path = "/{key}", method = RequestMethod.POST)
    public ResponseEntity set(@PathVariable("key") String key, @RequestBody String value, @RequestHeader Map<String,String> header) {
        String type = header.get("type");
        int t;
        if (type == null){
            type = "";
        }
        if (type.equals("NUM")){
            t = core.NUM_TYPE;
        }else if (type.equals("STR")){
            t = core.STRING;
        }
        else{
            t = core.STRING;
        }
        eixampledb.set(new SetRequest(key, value, t));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/{key}", method = RequestMethod.PUT)
    public ResponseEntity operation(@PathVariable("key") String key, @RequestHeader("op") String operation) {

        GetResponse getResponse = eixampledb.get(new GetRequest(key));
        String value = getResponse.getEntry().get().getValue(); //Value of the key
        int type = getResponse.getEntry().get().getType(); // Type of the key

        if (! getResponse.isSuccess()) { //If the key doesn't exist return error
            return ResponseEntity.notFound().build();
        }

        if(type == core.NUM_TYPE) { //If it's a number
            if (operation.equals("INCR")) { //If op is INCR, increase the value caring if it's int/long or float/double
                value = core.operation_increment(key, value);
            } else if (operation.equals("DECR")) {
                value = core.operation_decrement(key, value);
            }
            SetResponse setResponse = eixampledb.set(new SetRequest(key, value, core.NUM_TYPE));
        }else{
            SetResponse setResponse = eixampledb.set(new SetRequest(key, value, type));
        }

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
