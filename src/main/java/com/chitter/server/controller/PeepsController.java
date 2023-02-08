package com.chitter.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chitter.server.model.Peep;
import com.chitter.server.model.User;
import com.chitter.server.payload.request.DeletePeepRequest;
import com.chitter.server.payload.request.PeepRequest;
import com.chitter.server.payload.request.UpdatePeepRequest;
import com.chitter.server.payload.response.PeepDataTransferObject;
import com.chitter.server.repository.PeepsRepository;
import com.chitter.server.repository.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/peeps")
public class PeepsController {

    @Autowired
    PeepsRepository peepsRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<PeepDataTransferObject>> getAllPeeps(){
        try {
            List<PeepDataTransferObject> peeps = new ArrayList<PeepDataTransferObject>();
            peepsRepository.findAll().forEach(peep -> {
                    Optional<User> foundUser = userRepository.findById(peep.getUserId());
                    User user = foundUser.get();
                    peeps.add(new PeepDataTransferObject(
                            peep.getId(),
                            user.getId(),
                            user.getUsername(),
                            user.getName(),
                            peep.getContent(),
                            peep.getDateCreated()));
            });

            if(peeps.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(peeps, HttpStatus.OK);



        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Peep> createPeep(@RequestBody @Valid PeepRequest request){
        try{

            Optional<User> foundUser = userRepository.findById(request.getUserId());

            if (foundUser.isPresent() && request.getPassword().equals(foundUser.get().getPassword())){

                User user = foundUser.get();

                Peep peep = new Peep(user.getId(),
                        request.getContent(),
                        LocalDateTime.now().toString());

                return new ResponseEntity<>(peepsRepository.save(peep), HttpStatus.CREATED);
            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deletePeep(@RequestBody @Valid DeletePeepRequest request) {
        try {
            Optional<User> user = userRepository.findById(request.getUserId());
            Optional<Peep> peep = peepsRepository.findById(request.getPeepId());

            if (user.isPresent() && peep.isPresent()){
                if (user.get().getPassword().equals(request.getPassword())){
                    peepsRepository.deleteById(request.getPeepId());
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping
    public ResponseEntity<Peep> updatePeep(@RequestBody @Valid UpdatePeepRequest request){
        try{
            Optional<User> foundUser = userRepository.findById(request.getUserId());
            Optional<Peep> foundPeep = peepsRepository.findById(request.getPeepId());

            if (foundUser.isPresent() && foundPeep.isPresent()){
                if (foundUser.get().getPassword().equals(request.getPassword())){
                    Peep peep = foundPeep.get();
                    peep.setContent(request.getContent());
                    return new ResponseEntity<>(peepsRepository.save(peep), HttpStatus.OK);
                }
            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
