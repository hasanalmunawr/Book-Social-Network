package com.hasanalmunawr.book_network.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AuditEventPublisher {

    @Autowired
    private ApplicationEventPublisher eventPublisher;
}
