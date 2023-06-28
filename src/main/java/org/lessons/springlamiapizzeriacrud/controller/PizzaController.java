package org.lessons.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.springlamiapizzeriacrud.Pizza;
import org.lessons.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.Binding;
import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizze")
public class PizzaController {

    @Autowired
    public PizzaRepository pizzaRepository;

    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String searchString, Model model) {
        List<Pizza> pizze;

        if (searchString == null || searchString.isBlank()){
            pizze = pizzaRepository.findAll();
        }else {
            pizze = pizzaRepository.findByNome(searchString);
        }

        model.addAttribute("pizzaList", pizze);
        model.addAttribute("searchInput", searchString);
        return "/pizze/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer pizzaId, Model model){

         Optional<Pizza> result = pizzaRepository.findById(pizzaId);
         if (result.isPresent()){
             model.addAttribute("pizza", result.get());

             return "/pizze/detail";
         }else {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza id" + pizzaId + "not found");
         }
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("pizza", new Pizza());
        return "/pizze/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "/pizze/edit";
        }

        pizzaRepository.save(formPizza);

        return "redirect:/pizze";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Optional<Pizza> result = pizzaRepository.findById(id);

        if (result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, id + "not found");
        }

        model.addAttribute("pizza", result.get());
        return "/pizze/edit";
    }


}
