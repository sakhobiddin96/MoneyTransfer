package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.task1.entity.Card;
import uz.pdp.task1.entity.Input;
import uz.pdp.task1.entity.Output;
import uz.pdp.task1.payload.TransferDto;
import uz.pdp.task1.repository.CardRepository;
import uz.pdp.task1.repository.InputRepository;
import uz.pdp.task1.repository.OutputRepository;
import uz.pdp.task1.security.GenerateToken;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {
    @Autowired
    OutputRepository outputRepository;
    @Autowired
    InputRepository inputRepository;
    @Autowired
    GenerateToken generateToken;
    @Autowired
    CardRepository cardRepository;
    @PostMapping
    public HttpEntity<?> transferMoney(@RequestBody TransferDto transferDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        Optional<Card> optionalSenderCard = cardRepository.findByNumberAndUsername(transferDto.getSenderCardNumber(), username);
        if (optionalSenderCard.isPresent()){
            Card senderCard = optionalSenderCard.get();
            Optional<Card> optionalReceiverCard = cardRepository.findByNumber(transferDto.getReceiverCardNumber());
            if (optionalReceiverCard.isPresent()){
                long commissionAmount= (long) (transferDto.getAmount()*(0.01));
                Card receiverCard = optionalReceiverCard.get();
                if (senderCard.getBalance()>= transferDto.getAmount()+commissionAmount){

                    senderCard.setBalance(senderCard.getBalance()- transferDto.getAmount()-commissionAmount);
                    receiverCard.setBalance(receiverCard.getBalance()+ transferDto.getAmount());
                    cardRepository.save(senderCard);
                    cardRepository.save(receiverCard);
                    Output output=new Output();
                    output.setAmount(transferDto.getAmount());
                    output.setCommissionAmount(commissionAmount);
                    output.setFromCard(senderCard);
                    output.setToCard(receiverCard);
                    output.setDate(new Date());
                    outputRepository.save(output);
                    Input input=new Input();
                   input.setAmount(transferDto.getAmount());
                   input.setFromCard(senderCard);
                   input.setToCard(receiverCard);
                   input.setDate(new Date());
                    inputRepository.save(input);

                    return ResponseEntity.ok("Transaction successful ");
                }
                return ResponseEntity.status(401).body("Insufficient balance");

            }
            return ResponseEntity.status(401).body("Receiver card not found");

        }
        return ResponseEntity.status(401).body("Failed to identify card");
    }
}
