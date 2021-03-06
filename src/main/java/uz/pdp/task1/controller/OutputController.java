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
import uz.pdp.task1.entity.Output;
import uz.pdp.task1.repository.CardRepository;
import uz.pdp.task1.repository.OutputRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/output")
public class OutputController {
    @Autowired
    OutputRepository outputRepository;
    @Autowired
    CardRepository cardRepository;
    @GetMapping
    public HttpEntity<?> getOutput(){
        List<Output> listOfUserOutputs=new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        Optional<Card> byUsername = cardRepository.findByUsername(username);
        if (byUsername.isPresent()){
            Card card = byUsername.get();
            List<Output> all = outputRepository.findAll();
            for (Output output : all) {
                if (output.getFromCard() == card){
                    listOfUserOutputs.add(output);
                }
            }
            return ResponseEntity.ok(listOfUserOutputs);
        }
        return ResponseEntity.notFound().build();
    }
}
