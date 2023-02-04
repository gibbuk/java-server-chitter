package com.chitter.server.controller;

import com.chitter.server.model.Peep;
import com.chitter.server.repository.PeepsRepository;
import com.chitter.server.response.ResponseObject;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/peeps")
public class PeepsController {

    @Autowired
    PeepsRepository peepsRepository;

    @GetMapping
    public ResponseEntity<List<Peep>> getAllPeeps(){
        try {
            List<Peep> peeps = new ArrayList<Peep>();
            peepsRepository.findAll().forEach(peep -> peeps.add(peep));

            if(peeps.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(peeps, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createPeep(@RequestBody(required = false) Peep peep){
        try{

            ResponseObject responseObject;

            if (peep == null) {
                responseObject = new ResponseObject("Error: no content supplied", null);
                return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
            }

            Peep savedPeep = peepsRepository.save(
                new Peep(peep.getUsername(),
                        peep.getRealName(),
                        peep.getContent(),
                        peep.getDateCreated()));

            responseObject = new ResponseObject("Peep posted!", savedPeep);

            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
