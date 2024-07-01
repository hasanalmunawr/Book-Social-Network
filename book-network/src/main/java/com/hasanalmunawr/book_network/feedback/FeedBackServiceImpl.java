package com.hasanalmunawr.book_network.feedback;

import com.hasanalmunawr.book_network.book.BookEntity;
import com.hasanalmunawr.book_network.book.BookRepository;
import com.hasanalmunawr.book_network.common.PageResponse;
import com.hasanalmunawr.book_network.exception.OperationNotPermittedException;
import com.hasanalmunawr.book_network.service.FeedBackService;
import com.hasanalmunawr.book_network.user.UserEntity;
import com.hasanalmunawr.book_network.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedBackServiceImpl implements FeedBackService {

    private final FeedBackRepository feedBackRepository;
    private final BookRepository bookRepository;
    private final FeedBackMapper feedBackMapper;

    @Override
    public Integer save(FeedBackRequest request, Authentication connectedUser) {
        BookEntity book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new EntityNotFoundException("No book found with ID:: " + request.bookId()));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("You cannot give a feedback for and archived or not shareable book");
        }

        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You cannot give feedback to your own book");
        }

        FeedBackEntity feedback = feedBackMapper.toFeedback(request);
        return feedBackRepository.save(feedback).getId();
    }

    @Override
    @Transactional
    public PageResponse<FeedBackResponse> findAllFeedbacksByBook(Integer bookId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size);
        UserEntity user = ((UserEntity) connectedUser.getPrincipal());
        Page<FeedBackEntity> feedbacks = feedBackRepository.findAllByBookId(bookId, pageable);
        List<FeedBackResponse> feedbackResponses = feedbacks.stream()
                .map(f -> feedBackMapper.toFeedbackResponse(f, user.getId()))
                .toList();
        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );

    }

}
