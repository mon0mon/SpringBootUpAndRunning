package xyz.mon0mon.ch03;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Ch03Application {

    public static void main(String[] args) {
        SpringApplication.run(Ch03Application.class, args);
    }

}

class Coffee {
    @JsonSerialize
    private final String id;
    @JsonSerialize
    private String name;

    public Coffee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Coffee(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

@RestController
@RequestMapping("/coffees")
class RestApiDemoController {
    private List<Coffee> coffees = new ArrayList<>();

    public RestApiDemoController() {
        coffees.addAll(List.of(new Coffee("1234-5678-910", "Java"), new Coffee("Café Cereza"),
                new Coffee("Café Ganador"), new Coffee("Café Lareño"),
                new Coffee("Café Três Pontas")));
    }

    //    @RequestMapping(value = "/coffees", method = RequestMethod.GET)
    @GetMapping
    Iterable<Coffee> getCoffees() {
        return coffees;
    }

    @GetMapping("/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable("id") String id) {
        for (Coffee c : coffees) {
            if (c.getId().equals(id)) {
                return Optional.of(c);
            }
        }

        return Optional.empty();
    }

    @PostMapping
    Coffee postCoffee(@RequestBody Coffee coffee) {
        coffees.add(coffee);

        return coffee;
    }

    @PutMapping("/{id}")
    ResponseEntity<Coffee> putCoffee(@PathVariable("id") String id, @RequestBody Coffee coffee) {
        int coffeeIndex = -1;

        for (Coffee c : coffees) {
            if (c.getId().equals(id)) {
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex, coffee);
            }
        }

        return (coffeeIndex == -1) ?
                new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED) :
                new ResponseEntity<>(coffee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteCoffee(@PathVariable String id) {
        coffees.removeIf(c -> c.getId().equals(id));
    }
}
