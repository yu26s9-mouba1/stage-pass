package nl.pluralsight.stagepass.controller;
import jakarta.validation.Valid;
import nl.pluralsight.stagepass.dto.ConcertSummary;
import nl.pluralsight.stagepass.repository.BookingRepository;
import nl.pluralsight.stagepass.model.Concert;
import nl.pluralsight.stagepass.service.BookingService;
import nl.pluralsight.stagepass.service.ConcertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import nl.pluralsight.stagepass.model.Booking;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;


@RestController
@RequestMapping("/api/concerts")
public class ConcertController {

    private final BookingService bookingService;
    private final ConcertService concertService;


    public ConcertController(ConcertService concertService, BookingService bookingService) {
        this.concertService = concertService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<Concert>> getAllConcerts() {
        return ResponseEntity.ok(concertService.getAllConcerts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Concert> getConcertById(@PathVariable Long id) {
        return concertService.getConcertById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Gets all concerts for a specific artist
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<Concert>> getConcertsByArtistId(@PathVariable Long artistId) {
        return ResponseEntity.ok(concertService.getConcertsByArtist(artistId));
    }

    @PostMapping
    public ResponseEntity<Concert> createConcert(@RequestBody Concert concert) {
        Concert created = concertService.createConcert(concert);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Concert>> getUpcomingConcerts() {
        return ResponseEntity.ok(concertService.getUpcomingConcerts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Concert> updateConcert(@PathVariable Long id, @RequestBody Concert concert) {
        return concertService.updateConcert(id, concert)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcert(@PathVariable Long id) {
        if (concertService.deleteConcert(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    //Gets booking for a specific concert
    @GetMapping("/{id}/summary")
    public ResponseEntity<ConcertSummary> getConcertSummary(@PathVariable Long id)  {
        return concertService.getConcertSummary(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
