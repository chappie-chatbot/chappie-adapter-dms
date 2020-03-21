package com.chg.hackdays.chappie.dms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChappieResource {

    private final ChappieService chappieService;

    @GetMapping("/foo")
    public ResponseEntity<String> getAllProduct(){
        log.info("working");
        chappieService.polling();
        return ResponseEntity.ok("Hello");
    }

    @PostConstruct
    public void init() {
        chappieService.polling();
    }
}
