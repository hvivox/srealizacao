package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.exception.EntityInUseException;
import com.hvivox.srealizacao.exception.EntityNotFoundException;
import com.hvivox.srealizacao.model.Restriction;
import com.hvivox.srealizacao.repository.SheetRepository;
import com.hvivox.srealizacao.repository.RestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestrictionService {
    @Autowired
    RestrictionRepository restrictionRepository;
    @Autowired
    private SheetRepository sheetRepository;

    private static final String MSG_RESTRICTION_IN_USE
            = "The restrictions with the provided information cannot be removed as they are currently in use";

    private static final String MSG_RESTRICTION_NOT_FOUND
            = "No restriction records found associated with sheet number %d";


    public List<Restriction> findAllBySheet(Integer sheetId) {
        return restrictionRepository.findAllRestrictionIntoSheet(sheetId);
    }

    public Restriction save(Restriction restriction) {
        return restrictionRepository.save(restriction);
    }

    public void deleteAllFromSheet(Integer sheetId) {
        List<Restriction> restrictionList = restrictionRepository.findAllRestrictionIntoSheet(sheetId);

        try {
            restrictionRepository.deleteAll(restrictionList);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(String.format(MSG_RESTRICTION_NOT_FOUND)) {
            };
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_RESTRICTION_IN_USE, sheetId));
        }

       /* if( !restrictionList.isEmpty() ){
            restrictionRepository.deleteAll(restrictionList);
        }*/
    }
}
