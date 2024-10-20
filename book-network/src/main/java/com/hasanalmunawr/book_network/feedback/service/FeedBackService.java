package com.hasanalmunawr.book_network.feedback.service;

import com.hasanalmunawr.book_network.book.model.dto.PageResponse;
import com.hasanalmunawr.book_network.feedback.module.dto.FeedBackRequest;
import com.hasanalmunawr.book_network.feedback.module.dto.FeedBackResponse;
import org.springframework.security.core.Authentication;

public interface FeedBackService {

    Integer save(FeedBackRequest request, Authentication connectedUser);
    PageResponse<FeedBackResponse> findAllFeedbacksByBook(Integer bookId, int page, int size, Authentication connectedUser);
}
