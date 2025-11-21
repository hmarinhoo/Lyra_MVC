package br.fiap.com.br.lyra.controller;

import br.fiap.com.br.lyra.model.Quiz;
import br.fiap.com.br.lyra.model.User;
import br.fiap.com.br.lyra.repository.QuizRepository;
import br.fiap.com.br.lyra.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final QuizRepository quizRepository;

    // ===== HOME LOGADA =====
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", 1234);
        stats.put("totalJourneys", 5678);
        stats.put("totalSimulations", 3456);
        model.addAttribute("stats", stats);

        return "home";
    }

    // ===== PERFIL =====
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);

        Map<String, Object> profile = new HashMap<>();
        profile.put("learningStyle", "Autodidata, Flexível e Focado");
        profile.put("sector", "Agricultura e Agronegócio");
        profile.put("experience", 85);
        profile.put("maturity", 90);
        profile.put("technology", 60);
        profile.put("adaptability", 80);
        model.addAttribute("profile", profile);

        Map<String, Object> objectives = new HashMap<>();
        objectives.put("clarity", 85);
        objectives.put("commitment", 70);
        model.addAttribute("objectives", objectives);

        List<Map<String, Object>> areas = new ArrayList<>();
        Map<String, Object> area1 = new HashMap<>();
        area1.put("id", 1L);
        area1.put("title", "Especialista em Agricultura de Precisão");
        area1.put("description", "Utilize tecnologia de ponta para otimizar produção agrícola");
        areas.add(area1);

        Map<String, Object> area2 = new HashMap<>();
        area2.put("id", 2L);
        area2.put("title", "Gestor de Sustentabilidade Rural");
        area2.put("description", "Lidere iniciativas sustentáveis no agronegócio");
        areas.add(area2);

        model.addAttribute("recommendedAreas", areas);

        return "profile";
    }

    // ===== QUIZ =====
    @GetMapping("/dash/quiz")
    public String quiz(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "quiz";
    }

    // ===== JORNADA =====
    @GetMapping("/journey")
    public String journey(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);

        Map<String, Object> journey = new HashMap<>();
        journey.put("title", "Especialista em Agricultura de Precisão");
        journey.put("description", "Uma carreira focada em utilizar tecnologia de ponta...");
        journey.put("duration", "18-24 meses");
        journey.put("level", "Intermediário");
        journey.put("roi", "Alto");
        model.addAttribute("journey", journey);

        List<Map<String, Object>> phases = new ArrayList<>();
        Map<String, Object> phase1 = new HashMap<>();
        phase1.put("title", "Fase 1: Fundamentos");
        phase1.put("duration", "3-4 meses");
        phase1.put("description", "Construa uma base sólida em conceitos de agricultura moderna");
        phase1.put("completed", false);
        phase1.put("courses", new ArrayList<>());
        phase1.put("skills", List.of("Sensoriamento Remoto", "GIS Básico", "Análise de Dados"));
        phases.add(phase1);

        model.addAttribute("journey.phases", phases);
        model.addAttribute("featuredCourses", new ArrayList<>());
        model.addAttribute("certifications", new ArrayList<>());
        model.addAttribute("aiRecommendation", "Com base no seu perfil atual, recomendo focar em <strong>análise de dados geoespaciais</strong>...");

        return "journey";
    }

    // ===== SIMULAÇÃO =====
    @GetMapping("/simulation")
    public String simulation(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        model.addAttribute("simulation", null);
        return "simulation";
    }

    // ===== IMPACTO =====
    @GetMapping("/impact")
    public String impact(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPoints", 1247);
        stats.put("odsImpacted", 8);
        stats.put("coursesCompleted", 12);
        stats.put("progressPercentage", 67);
        model.addAttribute("stats", stats);

        List<Map<String, Object>> odsProgress = new ArrayList<>();
        Map<String, Object> ods2 = new HashMap<>();
        ods2.put("id", 2);
        ods2.put("number", "ODS 2");
        ods2.put("title", "Fome Zero e Agricultura Sustentável");
        ods2.put("description", "Acabar com a fome, alcançar a segurança alimentar...");
        ods2.put("icon", "fa-seedling");
        ods2.put("color", "#DDA63A");
        ods2.put("progress", 45);
        ods2.put("actions", 8);
        ods2.put("points", 215);
        odsProgress.add(ods2);

        model.addAttribute("odsProgress", odsProgress);
        model.addAttribute("recentActivities", new ArrayList<>());
        model.addAttribute("badges", new ArrayList<>());
        model.addAttribute("impactLegend", new ArrayList<>());

        Map<String, Object> chartData = new HashMap<>();
        chartData.put("labels", List.of("ODS 2", "ODS 7", "ODS 9", "ODS 12", "ODS 13"));
        chartData.put("values", List.of(28, 22, 18, 17, 15));
        chartData.put("colors", List.of("#DDA63A", "#FCC30B", "#FD6925", "#BF8B2E", "#3F7E44"));
        model.addAttribute("impactChartData", chartData);

        return "impact";
    }

    // ===== DASHBOARD =====
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);

        // Busca o último quiz do usuário
        quizRepository.findTopByUserOrderByCreatedAtDesc(user)
                .ifPresent(quiz -> model.addAttribute("quiz", quiz));

        return "dashboard";
    }
}
