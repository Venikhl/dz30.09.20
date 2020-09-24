package org.step.repository.impl;

import org.springframework.stereotype.Repository;
import org.step.entity.Profile;
import org.step.repository.ProfileRepository;

import java.util.List;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    @Override
    public Profile save(Profile profile) {
        return null;
    }

    @Override
    public List<Profile> findAll() {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
