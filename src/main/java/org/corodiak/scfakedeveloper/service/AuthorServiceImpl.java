package org.corodiak.scfakedeveloper.service;

import lombok.RequiredArgsConstructor;
import org.corodiak.scfakedeveloper.exception.SearchResultNotExistException;
import org.corodiak.scfakedeveloper.repository.AuthorRepository;
import org.corodiak.scfakedeveloper.type.entity.Author;
import org.corodiak.scfakedeveloper.type.vo.AuthorVo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public boolean addAuthor(String name, String description) {
        Author author = Author.builder()
                .name(name)
                .description(description)
                .build();
        authorRepository.save(author);
        return true;
    }

    @Override
    public List<AuthorVo> findAll() {
        List<Author> authorList = authorRepository.findAll();
        List<AuthorVo> results = authorList.stream()
                .map(e -> new AuthorVo(e))
                .collect(Collectors.toList());
        return results;
    }

    @Override
    public AuthorVo findAuthor(Long seq) {
        Optional<Author> author = authorRepository.findById(seq);
        if (author.isPresent()) {
            return new AuthorVo(author.get());
        }
        throw new SearchResultNotExistException();
    }

    @Override
    public void removeAuthor(Long seq) {
        authorRepository.deleteById(seq);
    }

    @Override
    @Transactional
    public boolean updateAuthor(Long seq, String name, String description) {
        Optional<Author> author = authorRepository.findById(seq);
        if (!author.isPresent()) {
            throw new SearchResultNotExistException();
        }

        Author result = author.get();
        result.setName(name);
        result.setDescription(description);

        return true;
    }

    @Override
    public List<AuthorVo> searchAuthor(String keyword) {
        List<Author> authorList = authorRepository.search(keyword);
        List<AuthorVo> results = authorList.stream()
                .map(e -> new AuthorVo(e))
                .collect(Collectors.toList());
        return results;
    }

}
