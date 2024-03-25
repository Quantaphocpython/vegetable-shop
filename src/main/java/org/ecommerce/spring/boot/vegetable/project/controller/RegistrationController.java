package org.ecommerce.spring.boot.vegetable.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ecommerce.spring.boot.vegetable.project.dto.UserDto;
import org.ecommerce.spring.boot.vegetable.project.entity.User;
import org.ecommerce.spring.boot.vegetable.project.entity.VerificationToken;
import org.ecommerce.spring.boot.vegetable.project.event.RegistrationCompleteEvent;
import org.ecommerce.spring.boot.vegetable.project.service.UserService;
import org.ecommerce.spring.boot.vegetable.project.service.VerificationTokenService;
import org.ecommerce.spring.boot.vegetable.project.utility.ApplicationUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.naming.Binding;
import java.util.Optional;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/register-form")
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new UserDto());
        return modelAndView;
    }

    @PostMapping("/register")
    public RedirectView registerUser(@Valid @ModelAttribute UserDto userDto,
                                     BindingResult result, Model model, HttpServletRequest request) {
        if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return new RedirectView("/registration/register-form?invalidc_f");
        }
        if(result.hasErrors()) {
            model.addAttribute("user", userDto);
        }
        System.out.println(result);
        User user = userService.registerUser(userDto);
        if(user == null)
            return new RedirectView("/registration/register-form?used");
        publisher.publishEvent(new RegistrationCompleteEvent(user, ApplicationUrl.getUrl(request)));
        return new RedirectView("/registration/register-form?success");
    }

    @GetMapping("/verifyEmail")
    public RedirectView verifyEmail(@RequestParam String token) {
        Optional<VerificationToken> verificationTokenOptional =
                verificationTokenService.findByToken(token);
        if(verificationTokenOptional.isPresent() && verificationTokenOptional.get().getUser().getEnabled()) {
            return new RedirectView("/login?verified");
        }
        String result = verificationTokenService.validateVerificationToken(token);
        switch (result.toLowerCase()) {
            case "valid":
                return new RedirectView("/login?valid");
            case "invalid":
                return new RedirectView("/error?invalid");
            default:
                return new RedirectView("/error?expired");
        }
    }

}
