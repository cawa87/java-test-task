package com.test.services.base.impl;

import com.test.entities.BaseEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

@RunWith(MockitoJUnitRunner.class)
public class DefaultBaseReadServiceTest {

    @Mock
    private JpaRepository<TestEntity, Long> jpaRepositoryMock;

    private TestEntityService serviceUnderTest;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        serviceUnderTest = new TestEntityService(jpaRepositoryMock);
    }

    @Test
    public void testGet() {
        Long id = 1L;

        serviceUnderTest.get(id);

        Mockito.verify(jpaRepositoryMock).findOne(id);
    }

    private class TestEntityService extends DefaultBaseReadService<TestEntity> {

        protected TestEntityService(JpaRepository<TestEntity, Long> jpaRepository) {
            super(jpaRepository);
        }
    }

    private class TestEntity extends BaseEntity {

    }

}
