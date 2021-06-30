package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll(){
        return authorRepository.findAll();
    }

    @Override
    public Author addAuthor(Author author){
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(long id){
        authorRepository.deleteById(id);
    }

    @Override
    public Optional<Author> findAuthorById(long id){
        return authorRepository.findById(id);
    }
}
