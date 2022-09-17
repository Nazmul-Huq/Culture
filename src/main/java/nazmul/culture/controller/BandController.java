package nazmul.culture.controller;

import nazmul.culture.domain.Band;
import nazmul.culture.service.IService.IBandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BandController {

    IBandService bandService;

    public BandController(IBandService bandService) {
        this.bandService = bandService;
    }

    @PostMapping("/api/band/save")
    public ResponseEntity<String> createBand(@RequestBody Band band){
        bandService.save(band);
        return new ResponseEntity<>("Band created",  HttpStatus.OK);
    }
}
