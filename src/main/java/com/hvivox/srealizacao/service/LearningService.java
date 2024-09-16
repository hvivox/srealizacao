package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.exception.EntityInUseException;
import com.hvivox.srealizacao.exception.EntityNotFoundException;
import com.hvivox.srealizacao.model.Learning;
import com.hvivox.srealizacao.repository.LearningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LearningService {

    private static final String MSG_LEARNING_IN_USE = "The learning records with the provided information cannot be removed as they are currently in use";

    private static final String MSG_LEARNING_NOT_FOUND = "No learning records found associated with sheet number %d";

    @Autowired
    LearningRepository learningRepository;

    public List<Learning> findAllBySheet(Integer sheetId) {
        return learningRepository.findAllLearningIntoSheet(sheetId);
    }

    @Transactional
    public Learning save(Learning learning) {
        return learningRepository.save(learning);
    }

    public void deleteAllFromSheet(Integer sheetId) {
        List<Learning> learningList = learningRepository.findAllLearningIntoSheet(sheetId);

        try {
            learningRepository.deleteAll(learningList);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(String.format(MSG_LEARNING_NOT_FOUND, sheetId)) {
            };
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_LEARNING_IN_USE));
        }
    }
}
