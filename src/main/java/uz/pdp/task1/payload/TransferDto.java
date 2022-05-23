package uz.pdp.task1.payload;

import lombok.Data;
import uz.pdp.task1.entity.Card;

import javax.persistence.OneToOne;

@Data

public class TransferDto {
    private long senderCardNumber;
    private long receiverCardNumber;
    private long amount;
}
