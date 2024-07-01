package com.hasanalmunawr.book_network.service;

import com.hasanalmunawr.book_network.common.PageResponse;
import com.hasanalmunawr.book_network.feedback.FeedBackEntity;
import com.hasanalmunawr.book_network.feedback.FeedBackRequest;
import com.hasanalmunawr.book_network.feedback.FeedBackResponse;
import org.springframework.security.core.Authentication;

public interface FeedBackService {

    Integer save(FeedBackRequest request, Authentication connectedUser);
    PageResponse<FeedBackResponse> findAllFeedbacksByBook(Integer bookId, int page, int size, Authentication connectedUser);
}
