package com.chitter.server.controller;

import com.chitter.server.model.Peep;
import com.chitter.server.model.User;
import com.chitter.server.payload.request.PeepRequest;
import com.chitter.server.payload.response.MessageResponse;
import com.chitter.server.repository.PeepsRepository;
import com.chitter.server.payload.response.PeepResponse;
import com.chitter.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/peeps")
public class PeepsController {

    @Autowired
    PeepsRepository peepsRepository;

    @Autowired
    UserRepository userRepository;

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
    public ResponseEntity<PeepResponse> createPeep(@RequestBody(required = false) PeepRequest peepRequest){
        try{

            if (peepRequest.getPeep() == null) {
                return new ResponseEntity<>(new PeepResponse("Error: no content supplied", null), HttpStatus.BAD_REQUEST);
            }

            if (peepRequest.getUser() == null) {
                return new ResponseEntity<>(new PeepResponse("Error: no valid user", null), HttpStatus.BAD_REQUEST);
            }

            Optional<User> user = userRepository.findByUsername(peepRequest.getUser().getUsername());

            if (user.isPresent() && peepRequest.getUser().getPassword().equals(user.get().getPassword())){
                Peep savedPeep = peepsRepository.save(
                    new Peep(user.get().getUsername(),
                            user.get().getName(),
                            peepRequest.getPeep().getContent(),
                            LocalDateTime.now().toString()));

                return new ResponseEntity<>(new PeepResponse("Peep posted!", savedPeep), HttpStatus.CREATED);
            }

            return new ResponseEntity<>(new PeepResponse("Error: no valid user", null), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(new PeepResponse(e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
