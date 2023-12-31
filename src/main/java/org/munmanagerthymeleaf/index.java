package org.munmanagerthymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class index {

    @GetMapping("/index")
    String getIndex(Model model) {
        return "index";
    }

}
