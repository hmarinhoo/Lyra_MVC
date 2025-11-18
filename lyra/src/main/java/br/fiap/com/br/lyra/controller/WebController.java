package br.fiap.com.br.lyra.controller;

import br.fiap.com.br.lyra.model.CareerTrail;
import br.fiap.com.br.lyra.repository.CareerTrailRepository;
import br.fiap.com.br.lyra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {

    private final UserRepository userRepository;
    private final CareerTrailRepository trailRepository;

    @Autowired
    public WebController(UserRepository userRepository, CareerTrailRepository trailRepository) {
        this.userRepository = userRepository;
        this.trailRepository = trailRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/quiz")
    public String quiz() {
        return "quiz";
    }

    @GetMapping("/trails")
    public String trails(@RequestParam Long userId, @RequestParam(defaultValue = "0") int page, Model model) {
        List<CareerTrail> list = trailRepository.findByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, 10)).getContent();
        model.addAttribute("trails", list);
        model.addAttribute("userId", userId);
        return "trails";
    }
}
