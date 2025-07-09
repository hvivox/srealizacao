package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.exception.*;
import com.hvivox.srealizacao.model.Priority;
import com.hvivox.srealizacao.repository.PriorityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriorityService {
    private static final String MSG_PRIORITY_IN_USE = "The priorities with the provided information cannot be removed as they are currently in use";
    private static final String MSG_PRIORITY_NOT_FOUND = "No priority records found associated with sheet number %d";

    @Autowired
    PriorityRepository priorityRepository;

    public List<Priority> findAllBySheet(Integer sheetId) {
        return priorityRepository.findAllPriorityIntoSheet(sheetId);
    }

    @Transactional
    public Priority save(Priority priority) {
        try {
            return priorityRepository.save(priority);
        } catch (PriorityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteAllFromSheet(Integer sheetId) {
        List<Priority> priorityList = priorityRepository.findAllPriorityIntoSheet(sheetId);
        try {
            priorityRepository.deleteAll(priorityList);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(String.format(MSG_PRIORITY_NOT_FOUND, sheetId)) {
            };
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_PRIORITY_IN_USE));
        }
    }

    @Transactional
    public Priority updatePriority(Priority priority) {
        try {
            return priorityRepository.save(priority);
        } catch (PriorityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
}
