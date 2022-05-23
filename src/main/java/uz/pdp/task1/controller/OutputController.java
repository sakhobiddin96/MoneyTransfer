package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.task1.repository.OutputRepository;

@RestController
@RequestMapping("/api/output")
public class OutputController {
    @Autowired
    OutputRepository outputRepository;
    @GetMapping
    public HttpEntity<?> getOutput(){
        return ResponseEntity.ok("List of Outputs");
    }
}
