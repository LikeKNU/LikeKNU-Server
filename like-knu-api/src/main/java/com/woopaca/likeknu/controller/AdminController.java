package com.woopaca.likeknu.controller;

import com.woopaca.likeknu.collector.menu.scheduler.MenuSchedulingService;
import com.woopaca.likeknu.entity.MainHeaderMessage;
import com.woopaca.likeknu.repository.MainHeaderMessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private final MainHeaderMessageRepository messageRepository;
    private final MenuSchedulingService menuSchedulingService;

    public AdminController(MainHeaderMessageRepository messageRepository, MenuSchedulingService menuSchedulingService) {
        this.messageRepository = messageRepository;
        this.menuSchedulingService = menuSchedulingService;
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false, defaultValue = "0") int error, Model model) {
        if (error == 1) {
            model.addAttribute("error", "로그인 실패");
        }

        return "login";
    }

    @GetMapping("/messages")
    public String registerMessageForm(Model model) {
        List<MainHeaderMessage> messages = messageRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(MainHeaderMessage::getRegisteredAt).reversed())
                .toList();
        model.addAttribute("messages", messages);
        return "registerMessage";
    }

    @PostMapping("/messages")
    public String registerMessage(@ModelAttribute(name = "message") String message) {
        MainHeaderMessage mainHeaderMessage = new MainHeaderMessage(message.trim());
        messageRepository.save(mainHeaderMessage);
        return "redirect:/admin/messages";
    }

    @GetMapping("/analytics")
    public String dailyAnalytics() {
        return "dailyAnalytics";
    }
}
