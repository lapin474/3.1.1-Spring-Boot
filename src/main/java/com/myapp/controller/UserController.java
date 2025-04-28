package com.myapp.controller;

import com.myapp.model.User;
import com.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Получение списка всех пользователей
    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    // Форма для создания нового пользователя
    @GetMapping("/new")
    public String newUserForm() {
        return "user-form";
    }

    // Сохранение нового пользователя
    @PostMapping("/create")
    public String saveUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    // Форма для редактирования пользователя
    @GetMapping("/edit")
    public String editUserForm(@RequestParam("id") Long id, Model model) {
        User user = userService.showUser(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "user-form";
        }
        // Перенаправление, если пользователь не найден
        return "redirect:/users";
    }

    // Обновление данных пользователя
    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        if (user != null && user.getId() != null) {
            userService.updateUser(user.getId(), user);
        }
        return "redirect:/users";
    }

    // Удаление пользователя
    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    // Показать детали пользователя
    @GetMapping("/detail")
    public String showUser(@RequestParam("id") Long id, Model model) {
        User user = userService.showUser(id);
        model.addAttribute("user", user);
        return "user-detail";
    }
}
