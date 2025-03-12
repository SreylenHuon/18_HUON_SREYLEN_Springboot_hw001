
package com.sreylen.springboothw001.controller;

import com.sreylen.springboothw001.ApiResponseTicket;
import com.sreylen.springboothw001.model.PaginationInfo;
import com.sreylen.springboothw001.model.TicketListResponse;
import com.sreylen.springboothw001.model.entity.Ticket;
import com.sreylen.springboothw001.model.request.BulkPaymentUpdateRequest;
import com.sreylen.springboothw001.model.request.TicketRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final List<Ticket> tickets = new ArrayList<>();
    private final static AtomicLong ATOMIC_LONG = new AtomicLong(7L);

    public TicketController() {
        populateSampleTickets();
    }

    private void populateSampleTickets() {
        tickets.add(new Ticket(1L, "John Doe", LocalDate.of(2024, 9, 10),
                "New York", "Los Angeles", 150.00, "Paid", "Confirmed", "A1"));

        tickets.add(new Ticket(2L, "Alice Smith", LocalDate.of(2024, 9, 12),
                "San Francisco", "Chicago", 120.50, "Unpaid", "Pending", "B2"));

        tickets.add(new Ticket(3L, "Bob Johnson", LocalDate.of(2024, 9, 15),
                "Seattle", "Houston", 200.75, "Paid", "Confirmed", "C3"));

        tickets.add(new Ticket(4L, "Emma Brown", LocalDate.of(2024, 9, 20),
                "Boston", "Miami", 180.00, "Unpaid", "Cancelled", "D4"));

        tickets.add(new Ticket(5L, "Michael Wilson", LocalDate.of(2024, 9, 25),
                "Denver", "Atlanta", 140.30, "Paid", "Confirmed", "E5"));

        tickets.add(new Ticket(6L, "Sophia Miller", LocalDate.of(2024, 9, 30),
                "Phoenix", "Las Vegas", 90.99, "Paid", "Confirmed", "F6"));
    }

    @GetMapping
    public ResponseEntity<ApiResponseTicket<TicketListResponse>> getAllTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {

        int totalElements = tickets.size();
        int startIndex = Math.min(page * size, totalElements);
        int endIndex = Math.min(startIndex + size, totalElements);

        List<Ticket> ticketResponses = tickets.subList(startIndex, endIndex)
                .stream()
                .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) totalElements / size);
        PaginationInfo paginationInfo = new PaginationInfo(totalElements, page + 1, size, totalPages);
        TicketListResponse responsePayload = new TicketListResponse(ticketResponses, paginationInfo);

        return ResponseEntity.ok(ApiResponseTicket.createSuccessMessage("Tickets retrieved successfully", responsePayload));
    }

    @PostMapping
    public Ticket saveTicket(@RequestBody TicketRequest ticketRequest) {
        Ticket ticket = new Ticket(
                ATOMIC_LONG.getAndIncrement(),
                ticketRequest.getPassengerName(),
                ticketRequest.getTravelDate(),
                ticketRequest.getSourceStation(),
                ticketRequest.getDestinationStation(),
                ticketRequest.getPrice(),
                ticketRequest.getPaymentStatus(),
                "Pending",
                ticketRequest.getSeatNumber()
        );
        tickets.add(ticket);
        return ticket;
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable("ticketId") Long ticketId) {
        return tickets.stream()
                .filter(ticket -> Objects.equals(ticket.getTicketId(), ticketId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Update an existing ticket")
    @PutMapping("/{ticketId}")
    public Ticket updateTicket(@PathVariable("ticketId") Long ticketId, @RequestBody TicketRequest ticketRequest) {
        return tickets.stream()
                .filter(ticket -> ticket.getTicketId().equals(ticketId))
                .findFirst()
                .map(ticket -> {
                    ticket.setPassengerName(ticketRequest.getPassengerName());
                    ticket.setTravelDate(ticketRequest.getTravelDate());
                    ticket.setSourceStation(ticketRequest.getSourceStation());
                    ticket.setDestinationStation(ticketRequest.getDestinationStation());
                    ticket.setPrice(ticketRequest.getPrice());
                    ticket.setPaymentStatus(ticketRequest.getPaymentStatus());
                    ticket.setSeatNumber(ticketRequest.getSeatNumber());
                    return ticket;
                })
                .orElse(null);
    }

    @Operation(summary = "Delete a ticket by ID")
    @DeleteMapping("/{ticketId}")
    public String deleteTicket(@PathVariable("ticketId") Long id) {
        tickets.removeIf(ticket -> ticket.getTicketId().equals(id));
        return "Ticket deleted successfully";
    }

    @Operation(summary = "Search tickets by passenger name")
    @GetMapping("/search")
    public List<Ticket> searchTickets(@RequestParam String passengerName) {
        return tickets.stream()
                .filter(ticket -> ticket.getPassengerName().toLowerCase().contains(passengerName.toLowerCase()))
                .toList();
    }

    @Operation(summary = "Filter tickets by status and travel date")
    @GetMapping("/filter")
    public List<Ticket> filterTickets(@RequestParam String status, @RequestParam LocalDate travelDate) {
        return tickets.stream()
                .filter(ticket -> ticket.getTicketStatus().equalsIgnoreCase(status) && ticket.getTravelDate().equals(travelDate))
                .toList();
    }

    @Operation(summary = "Bulk update payment status for multiple tickets")
    @PutMapping("/bulk")
    public ResponseEntity<String> bulkUpdatePaymentStatus(@RequestBody BulkPaymentUpdateRequest request) {
        request.getTicketIds().forEach(ticketId ->
                tickets.stream()
                        .filter(ticket -> ticket.getTicketId().equals(ticketId))
                        .findFirst()
                        .ifPresent(ticket -> ticket.setPaymentStatus(request.getNewPaymentStatus()))
        );
        return ResponseEntity.ok("Payment status updated successfully");
    }

    @Operation(summary = "Bulk create tickets")
    @PostMapping("/bulk")
    public List<Ticket> bulkCreateTickets(@RequestBody List<TicketRequest> ticketRequests) {
        List<Ticket> newTickets = ticketRequests.stream()
                .map(request -> new Ticket(
                        ATOMIC_LONG.getAndIncrement(),
                        request.getPassengerName(),
                        request.getTravelDate(),
                        request.getSourceStation(),
                        request.getDestinationStation(),
                        request.getPrice(),
                        request.getPaymentStatus(),
                        "Pending",
                        request.getSeatNumber()
                ))
                .collect(Collectors.toList());
        tickets.addAll(newTickets);
        return newTickets;
    }
}
