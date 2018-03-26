package com.test.services.base.impl;

import com.test.common.exceptions.base.NonPersistedAccountUpdateException;
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
public class DefaultBaseServiceTest {

    @Mock
    private JpaRepository<TestEntity, Long> jpaRepositoryMock;

    private TestEntityService serviceUnderTest;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        serviceUnderTest = new TestEntityService(jpaRepositoryMock);
    }

    @Test
    public void testCreate() {
        Long id = 1L;
        TestEntity testEntity = new TestEntity();
        testEntity.setId(id);

        serviceUnderTest.create(testEntity);

        assert(null == testEntity.getId());
        Mockito.verify(jpaRepositoryMock).save(testEntity);
    }

    @Test
    public void testUpdatePositive() {
        Long id = 1L;
        TestEntity testEntity = new TestEntity();
        testEntity.setId(id);

        serviceUnderTest.update(testEntity);

        assert(id.equals(testEntity.getId()));
        Mockito.verify(jpaRepositoryMock).save(testEntity);
    }

    @Test(expected = NonPersistedAccountUpdateException.class)
    public void testUpdateNegative() {
        TestEntity testEntity = new TestEntity();

        serviceUnderTest.update(testEntity);
    }

    @Test
    public void testDelete() {
        Long id = 1L;

        serviceUnderTest.delete(id);

        Mockito.verify(jpaRepositoryMock).delete(id);
    }

    private class TestEntityService extends DefaultBaseService<TestEntity> {

        protected TestEntityService(JpaRepository<TestEntity, Long> jpaRepository) {
            super(jpaRepository);
        }
    }

    private class TestEntity extends BaseEntity {

    }

}
