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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final EixampleDb eixampledb;
    private static final int NUM_TYPE = 1;

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

        //Comprobar que si type no existe el programa no peta y devuelve null
        String type = header.get("type");
        int t;
        if (type.equals("NUM")){
            t = 1;
        }else if (type.equals("STR")){
            t = 0;
        }
        else{
            t = 0;
        }
        SetResponse setResponse = eixampledb.set(new SetRequest(key, value, t));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/{key}", method = RequestMethod.PUT)
    public ResponseEntity operation(@PathVariable("key") String key, @RequestHeader("op") String operation) {

        GetResponse getResponse = eixampledb.get(new GetRequest(key));
        String value = getResponse.getEntry().get().getValue(); //Valor de la key
        int type = getResponse.getEntry().get().getType(); // Tipo de la Key

        if (! getResponse.isSuccess()) { //Si la key no existe devolvemos error
            return ResponseEntity.notFound().build();
        }

        if(type == NUM_TYPE) { //Si es un numero
            if (operation.equals("INCR")) { //Si la operacion es INCR incrementamos vigiliando si es float/double o int/long
               operation_increment(key, value);
            } else if (operation.equals("DECR")) {
                operation_decrement(key, value);
            }
        }else{
            SetResponse setResponse = eixampledb.set(new SetRequest(key, value, type));
        }

        return ResponseEntity.ok().build();
    }

    private void operation_increment(String key, String value){

        if(value.contains(".")){
            value = (Double.parseDouble(value)+1.) + "";
        }else{
            value = (Long.parseLong(value)+1) + "";
        }

        SetResponse setResponse = eixampledb.set(new SetRequest(key, value, NUM_TYPE));
    }

    private void operation_decrement(String key, String value){

        if(value.contains(".")){
            value = (Double.parseDouble(value)-1.) + "";
        }else{
            value = (Long.parseLong(value)-1) + "";
        }

        SetResponse setResponse = eixampledb.set(new SetRequest(key, value, NUM_TYPE));
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
