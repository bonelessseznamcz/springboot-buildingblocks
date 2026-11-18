package com.flem.resthello.hello;

import com.flem.resthello.model.UserDetails;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;


@RestController
public class HelloWorldController {

    private final ResourceBundleMessageSource messageSource;

    HelloWorldController(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    //@RequestMapping(method= RequestMethod.GET, path="/hello")
    @GetMapping("/hello1")
    public String hello() {
        return "Hello cunt!";
    }

    @GetMapping("/user-details")
    public UserDetails getUser() {
        return new UserDetails("Lolo", "Koko", "Oklahoma");
    }

    @GetMapping("/hello-int")
    public String getMessageInI18nFormat(@RequestHeader(name = "Accept-Language", required = false) String locale) {
        var message = messageSource.getMessage("label.hello", null, Locale.of(locale != null ? locale : Locale.US.getLanguage()));
        return message;
    }

    @GetMapping("/hello-int2")
    public String getMessageInI18nFormat2(@RequestHeader(name = "Accept-Language", required = false) String locale) {
        var message = messageSource.getMessage("label.hello", null, LocaleContextHolder.getLocale());
        return message;
    }
}
