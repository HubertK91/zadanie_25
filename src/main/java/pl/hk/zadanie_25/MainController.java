package pl.hk.zadanie_25;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {
    private TaskRepository taskRepository;

    public MainController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Task> all = taskRepository.findAll();
        model.addAttribute("tasks", all);
        return "home";
    }

    @GetMapping("/add")
    public String addTask(Model model) {
        model.addAttribute("taskToAdd", new Task());
        model.addAttribute("categories", Category.values());
        return "addTask";
    }

    @PostMapping("/add")
    public String addTask(Task task) {
        taskRepository.save(task);
        return "redirect:/";
    }

//    @GetMapping("/archives")
//    public String printListOfTasks(Model model, Task task) {
//        if (task.isFinished() == false) {
//            List<Task> tasks = taskRepository.findByCategory(category);
//            model.addAttribute("tasks", tasks);
//            return "products";
//        } else {
//            List<Task> allProducts = taskRepository.findAll();
//            model.addAttribute("products", allProducts);
//            model.addAttribute("sumProducts", allProducts.stream().mapToDouble(Product::getPrice)
//                    .sum());
//            return "products";
//        }
//    }
}
