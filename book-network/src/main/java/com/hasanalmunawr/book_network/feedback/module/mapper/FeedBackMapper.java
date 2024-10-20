package com.hasanalmunawr.book_network.feedback.module.mapper;

import com.hasanalmunawr.book_network.book.model.entity.BookEntity;
import com.hasanalmunawr.book_network.feedback.module.dto.FeedBackRequest;
import com.hasanalmunawr.book_network.feedback.module.dto.FeedBackResponse;
import com.hasanalmunawr.book_network.feedback.module.entity.FeedBackEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedBackMapper {

    public FeedBackEntity toFeedback(FeedBackRequest request) {
        return FeedBackEntity.builder()
                .note(request.note())
                .comment(request.comment())
                .book(BookEntity.builder()
                        .id(request.bookId())
                        .shareable(false) // Not required and has no impact :: just to satisfy lombok
                        .archived(false) // Not required and has no impact :: just to satisfy lombok
                        .build()
                )
                .build();
    }

    public FeedBackResponse toFeedbackResponse(FeedBackEntity feedback, Integer id) {
        return FeedBackResponse.builder()
                .note(feedback.getNote())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getCreatedBy(), id))
                .build();
    }
}
