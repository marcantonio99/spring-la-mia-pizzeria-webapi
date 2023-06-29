package org.lessons.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

        /* Optional<Pizza> result = pizzaRepository.findById(pizzaId);
         if (result.isPresent()){
             model.addAttribute("pizza", result.get());

             return "/pizze/detail";
         }else {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza id" + pizzaId + "not found");
         }*/

        Pizza pizza = getPizzaById(pizzaId);
        model.addAttribute("pizza", pizza);

        return "/pizze/detail";
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
        Pizza pizza = getPizzaById(id);

        model.addAttribute("pizza", pizza);
        return "/pizze/edit";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id,
                         @Valid @ModelAttribute("pizza") Pizza formPizza,
                         BindingResult bindingResult){
        Pizza pizzaToEdit = getPizzaById(id);

        formPizza.setId(pizzaToEdit.getId());

        pizzaRepository.save(formPizza);
        return "redirect:/pizze";
    }

    private Pizza getPizzaById(Integer id){
        Optional<Pizza> result = pizzaRepository.findById(id);

        if (result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, id + "not found");
        }
        return result.get();
    }
}
