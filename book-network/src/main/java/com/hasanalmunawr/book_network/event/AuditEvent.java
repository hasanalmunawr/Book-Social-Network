package com.hasanalmunawr.book_network.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class AuditEvent<T> extends ApplicationEvent {

    public AuditEvent(T source) {
        super(source);
    }
}
