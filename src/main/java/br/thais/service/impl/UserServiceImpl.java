package br.thais.service.impl;

import br.thais.domain.User;
import br.thais.repository.UserRepository;
import br.thais.service.UserService;
import br.thais.service.exception.ObjectionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectionNotFoundException("Objeto não encontrado"));
    }
}
