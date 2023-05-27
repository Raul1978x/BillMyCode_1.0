package com.BillMyCode.app.controllers;

import com.BillMyCode.app.services.ImageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/thymeleaf")
public class LoginController {

    private ImageService imageService;

    /**
     * Metodo loginSuccess: En caso de una validacion correcta redirige al Usuario dependiendo de su rol
     *
     * @param request
     * @return: redirect:/thymeleaf/principaldevelopers Si el rol del Usuario es DEV,
     * redirect:/thymeleaf/principalaccounters Si el rol del Usuario es Accounter,
     * redirect:/padmin Si el rol del Usuario es Admin
     */
    @PostMapping("/redilogin")
    public String loginSuccess(HttpSession request, ModelMap model) {
        model.put("request", request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/padmin";
        } else if (roles.contains("ROLE_DEV")) {
            return "redirect:/thymeleaf/principal-developers";
        } else {
            return "redirect:/thymeleaf/principalaccounters";
        }
    }

    /**
     * Metodo accessDenied: En caso de una validacion incorrecta redirige al Usuario
     *
     * @return: acceso-denegado.html
     */
    @GetMapping("/accesoD")
    public String accessDenied() {
        return "acceso-denegado";
    }
}
