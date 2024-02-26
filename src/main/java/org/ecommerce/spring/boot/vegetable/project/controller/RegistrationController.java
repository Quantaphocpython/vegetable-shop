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
    public String registerUser(@Valid @ModelAttribute UserDto userDto,
                               BindingResult result, Model model, HttpServletRequest request) {
        if(!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return "redirect:/registration/register-form?invalidc_f";
        }
        if(result.hasErrors()) {
            model.addAttribute("user", userDto);
        }
        System.out.println(result);
        User user = userService.registerUser(userDto);
        publisher.publishEvent(new RegistrationCompleteEvent(user, ApplicationUrl.getUrl(request)));
        return "redirect:/registration/register-form?success";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam String token) {
        Optional<VerificationToken> verificationTokenOptional =
                verificationTokenService.findByToken(token);
        if(verificationTokenOptional.isPresent() && verificationTokenOptional.get().getUser().getEnabled()) {
            return "redirect:/login?verified";
        }
        String result = verificationTokenService.validateVerificationToken(token);
        switch (result.toLowerCase()) {
            case "valid":
                return "redirect:/login?valid";
            case "invalid":
                return "redirect:/error?invalid";
            default:
                return "redirect:/error?expired";
        }
    }

}
