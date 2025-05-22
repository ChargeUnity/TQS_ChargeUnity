package tqs.ChargeUnity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import tqs.ChargeUnity.model.User;
import tqs.ChargeUnity.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> findAll() {
    return new ArrayList<>();
  }

  public Optional<User> findById(int id) {
    return Optional.empty();
  }

  public Optional<User> findByName(String name) {
    return Optional.empty();
  }

  public Optional<User> save(User user) {
    return Optional.empty();
  }

  public Optional<User> update(User user) {
    return Optional.empty();
  }

  public void deleteById(int id) {}
}
