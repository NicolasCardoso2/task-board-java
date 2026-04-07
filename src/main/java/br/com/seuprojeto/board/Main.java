package br.com.seuprojeto.board;

import br.com.seuprojeto.board.controller.TaskController;
import br.com.seuprojeto.board.repository.JsonTaskRepository;
import br.com.seuprojeto.board.service.TaskService;
import br.com.seuprojeto.board.util.InputUtil;

import java.io.PrintStream;

public class Main {

    public static void main(String[] args) throws Exception {
        // Garante que o console exiba acentos corretamente no Windows
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.setErr(new PrintStream(System.err, true, "UTF-8"));

        JsonTaskRepository taskRepository = new JsonTaskRepository();
        TaskService taskService = new TaskService(taskRepository);
        InputUtil inputUtil = new InputUtil();
        TaskController taskController = new TaskController(taskService, inputUtil);

        taskController.run();
    }
}

