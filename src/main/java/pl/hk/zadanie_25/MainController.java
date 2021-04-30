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
    public String home(Model model, Task task) {
        List<Task> all = taskRepository.findAll();
        model.addAttribute("taskToEdit", task);
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

    @GetMapping("/edit")
    public String edit(@RequestParam Long id, Model model) {
        Task task = taskRepository.findById(id).orElse(null);
        model.addAttribute("task", task);
        model.addAttribute("categories", Category.values());
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(Task task) {
        Task task1 = taskRepository.findById(task.getId()).orElse(null);
        task1.setName(task.getName());
        task1.setCategory(task.getCategory());
        task1.setFinished(task.isFinished());
        taskRepository.save(task1);
        return "redirect:/";
    }

    @PostMapping("/send")
    public String sendTask(Task task) {
        if (task != null) {
            task.setFinished(true);
            taskRepository.save(task);
        }
        return "redirect:/";
    }

    @GetMapping("/archives")
    public String printListOfDoneTasks(Model model, Task task) {
        if (task.isFinished()) {
            List<Task> tasks = taskRepository.findAll();
            model.addAttribute("tasks", tasks);
            return "archives";
        } else {
            return "noResults";
        }
    }
}
