package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OwnerExtraTests {

    @Test
    void addPetShouldLinkOwnerAndPet() {
        // Arrange
        Owner owner = new Owner();
        owner.setFirstName("Anna");
        owner.setLastName("Serra");

        Pet pet = new Pet();
        pet.setName("Fido");

        // Act
        owner.addPet(pet);

        // Assert
        assertThat(owner.getPets()).contains(pet);
        assertThat(pet.getOwner()).isEqualTo(owner);
    }

    @Test
    void getPetShouldReturnPetByName() {
        // Arrange
        Owner owner = new Owner();
        Pet pet = new Pet();
        pet.setName("Nina");
        owner.addPet(pet);

        // Act
        Pet result = owner.getPet("Nina");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Nina");
    }

    @Test
    void getPetShouldReturnNullWhenNameNotFound() {
        // Arrange
        Owner owner = new Owner();
        Pet pet = new Pet();
        pet.setName("Luna");
        owner.addPet(pet);

        // Act
        Pet result = owner.getPet("NoExiste");

        // Assert
        assertThat(result).isNull();
    }
}
