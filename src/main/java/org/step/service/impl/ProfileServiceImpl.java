package org.step.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.step.entity.Profile;
import org.step.repository.CrudRepository;
import org.step.service.CrudService;

import java.util.List;

@Service
public class ProfileServiceImpl implements CrudService<Profile> {

//    Not recommended
//    @Autowired
//    private CrudRepository<Profile> profileCrudRepository;

    private final CrudRepository<Profile> profileCrudRepository;

    /*
    The best for injecting bean into bean
     */
    @Autowired
    public ProfileServiceImpl(CrudRepository<Profile> profileCrudRepository) {
        this.profileCrudRepository = profileCrudRepository;
    }

    @Override
    @Transactional
    public Profile save(Profile profile) {
        return profileCrudRepository.save(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Profile> findAll() {
        return profileCrudRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        profileCrudRepository.delete(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        profileCrudRepository.deleteAll();
    }

    //    Could be used for self-injection
//    @Autowired
//    public void setProfileCrudRepository(CrudRepository<Profile> profileCrudRepository) {
//        this.profileCrudRepository = profileCrudRepository;
//    }
}
