package com.hasanalmunawr.book_network.feedback;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBackResponse {

    private Double note;
    private String comment;
    private boolean ownFeedback;
}
