package org.ecommerce.spring.boot.vegetable.project.event;


import lombok.Getter;
import lombok.Setter;
import org.ecommerce.spring.boot.vegetable.project.entity.User;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;
    private String confirmationUrl;

    public RegistrationCompleteEvent(User user, String confirmationUrl) {
        super(user);
        this.user = user;
        this.confirmationUrl = confirmationUrl;
    }
}
