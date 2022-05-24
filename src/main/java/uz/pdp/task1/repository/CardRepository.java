package uz.pdp.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1.entity.Card;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Integer> {
   Optional<Card> findByNumberAndUsername(long number, String username);
   Optional<Card> findByNumber(long number);
   Optional<Card> findByUsername(String username);
}
