package io.github.lrzeszotarski.landminesapp.api;

import io.github.lrzeszotarski.landminesapp.LandMinesImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LandMinesController {

    private final LandMinesImpl landMines;

    public LandMinesController(LandMinesImpl landMines) {
        this.landMines = landMines;
    }

    @PostMapping("/numClear")
    public int numClear(@RequestBody String[] layout) {
        return landMines.numClear(layout);
    }
}
