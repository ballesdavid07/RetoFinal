package com.example.reto_final.services;

import com.example.reto_final.domain.User;
import com.example.reto_final.domain.repositories.UserRepository;
import com.example.reto_final.exceptions.UserNotFoundException;
import com.example.reto_final.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    public Flux<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public Mono<User> getUserById(Long id){
        return this.userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Usuario no encontrado.")));
    }

    public Mono<User> createUser(User user){
        return this.userRepository.save(user);
    }

    @Override
    public Mono<User> updateBalance(Long id, Double balance) {
        return getUserById(id)
                .flatMap(user -> {
                    user.setBalance(user.getBalance() + balance);
                    return this.userRepository.save(user);
                });
    }
}
