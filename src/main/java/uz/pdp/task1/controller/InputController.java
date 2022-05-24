package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.task1.entity.Card;
import uz.pdp.task1.entity.Input;
import uz.pdp.task1.repository.CardRepository;
import uz.pdp.task1.repository.InputRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/input")
public class InputController {
    @Autowired
    InputRepository inputRepository;
    @Autowired
    CardRepository cardRepository;
    @GetMapping
    public HttpEntity<?> getInputs() {
        List<Input> listOfUserInputs=new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        Optional<Card> byUsername = cardRepository.findByUsername(username);
        if (byUsername.isPresent()){
            Card card = byUsername.get();
            List<Input> inputList = inputRepository.findAll();
            for (Input input : inputList) {
                if (input.getToCard()==card){
                    listOfUserInputs.add(input);
                }
            }
            return ResponseEntity.ok(listOfUserInputs);
        }
        return ResponseEntity.notFound().build();
    }

}
