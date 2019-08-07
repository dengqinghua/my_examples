package tacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private TacoService tacoService;

    @GetMapping("/")
    public Taco home() {
        tacoService.getOne();
        return new Taco();
    }
}
