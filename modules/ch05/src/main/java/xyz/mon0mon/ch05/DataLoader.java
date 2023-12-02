package xyz.mon0mon.ch05;

import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {
    private final CoffeeRepository coffeeRepository;

    public DataLoader(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @PostConstruct
    private void loadData() {
        coffeeRepository.saveAll(
                List.of(
                        new Coffee("1234-5678-910", "Java"),
                        new Coffee("Café Cereza"),
                        new Coffee("Café Ganador"),
                        new Coffee("Café Lareño"),
                        new Coffee("Café Três Pontas")
                ));
    }
}
