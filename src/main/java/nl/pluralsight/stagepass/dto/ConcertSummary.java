package nl.pluralsight.stagepass.dto;
import java.math.BigDecimal;

public record ConcertSummary (
    Long concertId,
    String concertTitle,
    int totalSeats,
    int seatsBooked,
    int availableSeats,
    BigDecimal totalRevenue


) {}