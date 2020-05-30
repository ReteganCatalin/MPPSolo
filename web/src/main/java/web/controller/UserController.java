package web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import web.dto.CredentialsDto;

@RestController
public class UserController {
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    CredentialsDto getTypeOfCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            currentUser.getAuthorities().forEach(System.out::println);
            return new CredentialsDto(currentUser.getAuthorities().toArray()[0].toString());
        }
        catch(Exception e)
        {
            return new CredentialsDto("NONE");
        }
    }
}
