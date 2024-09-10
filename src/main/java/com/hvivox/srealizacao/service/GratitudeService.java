package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.exception.EntityInUseException;
import com.hvivox.srealizacao.exception.EntityNotFoundException;
import com.hvivox.srealizacao.model.Gratitude;
import com.hvivox.srealizacao.repository.GratitudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GratitudeService {
    @Autowired
    GratitudeRepository gratitudeRepository;

    private static final String MSG_GRATITUDE_IN_USE
            = "The gratitude with the provided information cannot be removed as it is currently in use";

    private static final String MSG_RESTRICTION_NOT_FOUND
            = "No gratitude records found associated with sheet number %d";

    public List<Gratitude> findAllBySheet(Integer sheetId) {
        return gratitudeRepository.findAllGratitudeIntoSheet(sheetId);
    }

    @Transactional
    public Gratitude save(Gratitude gratitude) {
        return gratitudeRepository.save(gratitude);
    }

    @Transactional
    public void deleteAllFromSheet(Integer sheetId) {
        List<Gratitude> gratitudeList = gratitudeRepository.findAllGratitudeIntoSheet(sheetId);

        try {
            gratitudeRepository.deleteAll(gratitudeList);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(String.format(MSG_RESTRICTION_NOT_FOUND, sheetId)) {
            };
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_GRATITUDE_IN_USE));
        }
    }
}
