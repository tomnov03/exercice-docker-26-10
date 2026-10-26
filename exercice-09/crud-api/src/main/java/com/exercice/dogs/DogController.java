package com.exercice.dogs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dogs")
public class DogController {

    private final DogRepository dogRepository;
    private final LogClient logClient;

    public DogController(DogRepository dogRepository, LogClient logClient) {
        this.dogRepository = dogRepository;
        this.logClient = logClient;
    }

    @GetMapping
    public List<Dog> listDogs() {
        List<Dog> dogs = dogRepository.findAll();
        logClient.log("INFO", "[CrudAPI] GET /api/v1/dogs", "listed " + dogs.size() + " dogs");
        return dogs;
    }

    @GetMapping("/{dogId}")
    public ResponseEntity<Dog> getDog(@PathVariable Long dogId) {
        return dogRepository.findById(dogId)
                .map(dog -> {
                    logClient.log("INFO", "[CrudAPI] GET /api/v1/dogs/" + dogId, "dog found");
                    return ResponseEntity.ok(dog);
                })
                .orElseGet(() -> {
                    logClient.log("ERR", "[CrudAPI] GET /api/v1/dogs/" + dogId, "dog not found");
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public Dog createDog(@RequestBody Dog dog) {
        Dog saved = dogRepository.save(dog);
        logClient.log("INFO", "[CrudAPI] POST /api/v1/dogs", "dog created with id " + saved.getId());
        return saved;
    }

    @PutMapping("/{dogId}")
    public ResponseEntity<Dog> updateDog(@PathVariable Long dogId, @RequestBody Dog updatedDog) {
        return dogRepository.findById(dogId)
                .map(dog -> {
                    dog.setName(updatedDog.getName());
                    dog.setBreed(updatedDog.getBreed());
                    dog.setAge(updatedDog.getAge());
                    Dog saved = dogRepository.save(dog);
                    logClient.log("INFO", "[CrudAPI] PUT /api/v1/dogs/" + dogId, "dog updated");
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> {
                    logClient.log("ERR", "[CrudAPI] PUT /api/v1/dogs/" + dogId, "dog not found");
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{dogId}")
    public ResponseEntity<Void> deleteDog(@PathVariable Long dogId) {
        if (!dogRepository.existsById(dogId)) {
            logClient.log("ERR", "[CrudAPI] DELETE /api/v1/dogs/" + dogId, "dog not found");
            return ResponseEntity.notFound().build();
        }
        dogRepository.deleteById(dogId);
        logClient.log("INFO", "[CrudAPI] DELETE /api/v1/dogs/" + dogId, "dog deleted");
        return ResponseEntity.noContent().build();
    }
}
