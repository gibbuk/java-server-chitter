package com.chitter.server.controller;

import com.chitter.server.model.Peep;
import com.chitter.server.repository.PeepsRepository;
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
    public ResponseEntity<Peep> createPeep(@RequestBody Peep peep){
        Peep savedPeep = peepsRepository.save(
                new Peep(peep.getUsername(),
                        peep.getRealName(),
                        peep.getContent(),
                        peep.getDateCreated()));

        return new ResponseEntity<>(savedPeep, HttpStatus.CREATED);


    }

}
