package com.hasanalmunawr.book_network.feedback.controller;


import com.hasanalmunawr.book_network.book.model.dto.PageResponse;
import com.hasanalmunawr.book_network.feedback.module.dto.FeedBackRequest;
import com.hasanalmunawr.book_network.feedback.module.dto.FeedBackResponse;
import com.hasanalmunawr.book_network.feedback.service.FeedBackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback")
public class FeedBackController {

    private final FeedBackService service;

    @PostMapping
    public ResponseEntity<Integer> saveFeedback(
            @Valid @RequestBody FeedBackRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.save(request, connectedUser));
    }

    @GetMapping("/book/{book-id}")
    public ResponseEntity<PageResponse<FeedBackResponse>> findAllFeedbacksByBook(
            @PathVariable("book-id") Integer bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllFeedbacksByBook(bookId, page, size, connectedUser));
    }
}
