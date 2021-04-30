package pl.hk.zadanie_25;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    private TaskRepository taskRepository;

    public MainController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String home(Model model, Task task) {
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

    @GetMapping("/edit")
    public String edit(@RequestParam Long id, Model model) {
        Task task = taskRepository.findById(id).orElse(null);
        model.addAttribute("task", task);
        model.addAttribute("categories", Category.values());
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(Task task) {
        Optional<Task> task1 = taskRepository.findById(task.getId());
        if (task1.isPresent()){
            task1.get().setName(task.getName());
            task1.get().setCategory(task.getCategory());
            task1.get().setFinished(task.isFinished());
            taskRepository.save(task1.get());
            return "redirect:/";
        }else {
            throw new RuntimeException();
        }
    }

    @PostMapping("/done")
    public String doneTask(@RequestParam Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()){
            task.get().setFinished(true);
            taskRepository.save(task.get());
            return "redirect:/";
        }else {
            throw new RuntimeException();
        }

    }

    @GetMapping("/archives")
    public String printListOfDoneTasks(Model model, Task task) {
            List<Task> tasks = taskRepository.findAllTasksWhereFinishedIsTrue();
            model.addAttribute("tasks", tasks);
            return "archives";
    }
}
