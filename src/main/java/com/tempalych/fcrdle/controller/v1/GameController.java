package com.tempalych.fcrdle.controller.v1;

import com.tempalych.fcrdle.dto.DailyPuzzleDto;
import com.tempalych.fcrdle.dto.rq.DailyPuzzleRequest;
import com.tempalych.fcrdle.dto.rq.GuessRequest;
import com.tempalych.fcrdle.dto.rs.DailyPuzzleResponse;
import com.tempalych.fcrdle.dto.rs.GuessResponse;
import com.tempalych.fcrdle.service.CsvService;
import com.tempalych.fcrdle.service.GuessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GameController {

    private final GuessService guessService;
    private final CsvService csvService;

    @GetMapping("/test")
    public void test() {
//        csvService.updateCsvFile();
//        return guessService.findLongestDistance();
    }

    @PutMapping("/dailypuzzle")
    public ResponseEntity<DailyPuzzleResponse> getDailyPuzzle(
            @RequestBody @Valid DailyPuzzleRequest request) {
        return ResponseEntity.ok(guessService.getDailyPuzzle(request));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/loadclubs")
    public ResponseEntity<String> loadClubsFromCsv() {
        try {
            csvService.loadFromCsv();
            return ResponseEntity.ok("loaded");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/newpuzzle")
    public ResponseEntity<String> newDailyPuzzle() {
        try {
        DailyPuzzleDto dailyPuzzleDto =
                guessService.newDailyPuzzle(LocalDate.now());
        return ResponseEntity.ok("guessed:" +
                dailyPuzzleDto.getFootballClub().getName());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/guess")
    public ResponseEntity<GuessResponse> guess(
            @RequestBody @Valid GuessRequest request) {
        return ResponseEntity.ok(guessService.processGuess(request));
    }
}
