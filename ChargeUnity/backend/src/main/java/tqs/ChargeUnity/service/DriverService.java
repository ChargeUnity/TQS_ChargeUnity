package tqs.ChargeUnity.service;

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
    return driverRepository.findAll();
  }

  public Optional<Driver> findById(int id) {
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
	Optional<Driver> existingDriver = driverRepository.findById(driver.getId());
	
	if (existingDriver.isPresent()) {
	  Driver updatedDriver = existingDriver.get();
	  updatedDriver.setName(driver.getName());
	  updatedDriver.setBalance(driver.getBalance());
	  return Optional.of(driverRepository.save(updatedDriver));
	}
	
	return Optional.empty();
  }

  public void deleteById(int id) {
    driverRepository.deleteById(id);
  }
}
