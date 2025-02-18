package app.labs;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/jsphome")
	public String jsphome(Model model) {
		return "home";
	}

	@GetMapping(value = { "/", "/home" })
	public String home() {
		return "thymeleaf/home";
	}
}
