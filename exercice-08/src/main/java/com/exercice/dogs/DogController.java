package com.exercice.dogs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dogs")
public class DogController {

    private final DogRepository dogRepository;

    public DogController(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @GetMapping
    public List<Dog> listDogs() {
        return dogRepository.findAll();
    }

    @GetMapping("/{dogId}")
    public ResponseEntity<Dog> getDog(@PathVariable Long dogId) {
        return dogRepository.findById(dogId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Dog createDog(@RequestBody Dog dog) {
        return dogRepository.save(dog);
    }

    @PutMapping("/{dogId}")
    public ResponseEntity<Dog> updateDog(@PathVariable Long dogId, @RequestBody Dog updatedDog) {
        return dogRepository.findById(dogId)
                .map(dog -> {
                    dog.setName(updatedDog.getName());
                    dog.setBreed(updatedDog.getBreed());
                    dog.setAge(updatedDog.getAge());
                    return ResponseEntity.ok(dogRepository.save(dog));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{dogId}")
    public ResponseEntity<Void> deleteDog(@PathVariable Long dogId) {
        if (!dogRepository.existsById(dogId)) {
            return ResponseEntity.notFound().build();
        }
        dogRepository.deleteById(dogId);
        return ResponseEntity.noContent().build();
    }
}
