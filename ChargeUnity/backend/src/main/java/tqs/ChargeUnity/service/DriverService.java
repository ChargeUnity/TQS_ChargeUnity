package tqs.ChargeUnity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import tqs.ChargeUnity.model.Driver;
import tqs.ChargeUnity.repository.DriverRepository;

@Service
public class DriverService {

  private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    //create driver
    public Driver createDriver(int id, String name, double balance) {
        Driver driver = new Driver();

        driver.setId(id);
        driver.setName(name);
        driver.setBalance(balance);
        return driverRepository.save(driver);
    }


  public List<Driver> findAll() {
    return new ArrayList<>();
  }

    public Optional<Driver> findById(int id) {
        //make it work
        return driverRepository.findById(id);
    }


    public Optional<Driver> findByName(String name) {
        return driverRepository.findByName(name);
    }

    public Optional<Driver> save(Driver driver) {
        return Optional.of(driverRepository.save(driver));
    }

    //has to be implemented
    //tem mesmo que melhorar, ainda nao sei como fazer isto
  public Optional<Driver> update(Driver driver) {
        int id = driver.getId();
        // Garante que um ID válido foi fornecido
        if (id <= 0) {
        return Optional.empty();
        }

        // Verifica se o driver com o ID fornecido existe
        return driverRepository.findById(id).map(existingDriver -> {
            // Atualiza os campos, apenas se não forem nulos
            if (driver.getName() != null) {
                existingDriver.setName(driver.getName());
            }
            if (driver.getBalance() != null) {
                existingDriver.setBalance(driver.getBalance());
            }
            return driverRepository.save(existingDriver);
        });
  }

  public void deleteById(int id) {}
}
