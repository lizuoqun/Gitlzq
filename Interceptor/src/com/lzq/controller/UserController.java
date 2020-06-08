package com.lzq.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lzq.po.User;

@Controller
public class UserController {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String toLogin() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String Login(User user, Model model, HttpSession session) {
		String username = user.getUsername();
		String password = user.getPassword();
		if (username != null & username.equals("huangyueyue") && password != null && password.equals("123456")) {
			session.setAttribute("USER", user);
			return "redirect:main";
		}
		model.addAttribute("msg", "’À∫≈√‹¬Î”–ŒÛ£¨«Î÷ÿ–¬µ«¬º£°");
		return "login";
	}

	@RequestMapping(value = "/main")
	public String toMain() {
		return "main";
	}

	@RequestMapping(value = "/logout")
	public String Logout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
}
