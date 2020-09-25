package org.step.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.step.configuration.DatabaseConfiguration;
import org.step.entity.Profile;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class})
public class ProfileServiceImplTest {

    @Autowired
    private CrudService<Profile> profileCrudService;

    private Profile profile;

    @Before
    public void setup() {
        profile = Profile
                .builder()
                .graduation("grad")
                .abilities("abil")
                .workExperience("work")
                .fullName("full")
                .build();

        profileCrudService.save(profile);
    }

    @After
    public void clean() {
        profileCrudService.deleteAll();
    }

    @Test
    public void shouldSaveNewProfile() {
        final Profile profile = Profile.builder()
                .build();

        Profile save = profileCrudService.save(profile);

        Assert.assertEquals(profile.getId(), save.getId());
    }

    @Test
    public void shouldDeleteProfile() {
        profileCrudService.delete(profile.getId());
    }

    @Test
    public void findAllProfiles() {
        List<Profile> all = profileCrudService.findAll();

        Assert.assertFalse(all.isEmpty());
    }
}
