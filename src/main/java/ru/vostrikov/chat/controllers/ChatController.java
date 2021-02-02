package ru.vostrikov.chat.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vostrikov.chat.dao.MessageDAO;
import ru.vostrikov.chat.model.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    private final MessageDAO messageDAO;

    public ChatController(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }


    @GetMapping("/chat")
    public String authorization(HttpServletRequest request, Model model)
    {
        String login = getLoginFromSession(request);
        if (login == null)
        {
            LOGGER.info("Redirection to the authorization form.");
            return "authorization";
        }
        else {
            LOGGER.info("Displaying chat - {}",login);
            addDataToPage(model, login);
            return "/chat";
        }
    }

    private void addDataToPage(Model model, String login) {
        model.addAttribute("chatMessages", messageDAO.getMessages());
        model.addAttribute("login",login);
    }

    private String getLoginFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        return login;
    }

    @GetMapping("/authorization")
    public String showAuthorizationForm() {
        return "authorization";
    }

    @PostMapping("/auth")
    public String handlerAuthorizationRequest(@RequestParam("login") String login,
                                              HttpServletRequest request, Model model) {
        if (login != null) {
            HttpSession session = request.getSession();
            session.setAttribute("login", login);
            LOGGER.info("Displaying chat - {}",login);
            addDataToPage(model, login);
            return "redirect:/chat";
        }
        else
            return "authorization";
    }

    @PostMapping("/add-message")
    public String addMessageToChatDAO(@RequestParam("message") String msg,
                                      HttpServletRequest request,
                                      Model model) {
        LOGGER.info("Add message");

        Message messageToChat = new Message(getLoginFromSession(request), msg, new Date());
        messageDAO.addMessage(messageToChat);

        String login = getLoginFromSession(request);
        addDataToPage(model, login);

        return "redirect:/chat";

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String removeLogin = getLoginFromSession(request);
        session.removeAttribute("login");
        LOGGER.info("Exit from chat - {}", removeLogin);
        return "authorization";
    }
}
