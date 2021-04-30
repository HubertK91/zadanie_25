package pl.hk.zadanie_25;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t from Task t WHERE t.finished = true ")
    List<Task> findAllTasksWhereFinishedIsTrue();
}
