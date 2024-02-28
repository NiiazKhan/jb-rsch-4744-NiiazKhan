package org.jetbrains.assignment.config;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import static org.assertj.core.api.Assertions.assertThat;

public class SQLStatementCountValidator {

    private final Statistics statistics;

    public SQLStatementCountValidator(EntityManager entityManager) {
        this.statistics = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class).getStatistics();
    }

    public void reset() {
        statistics.clear();
    }

    public void validateCountEqualsOrLessThen(int expected) {
        long prepareStatementCount = statistics.getPrepareStatementCount();
        assertThat(prepareStatementCount).isLessThanOrEqualTo(expected);
    }

    public void validateCount(int expected) {
        long prepareStatementCount = statistics.getPrepareStatementCount();
        assertThat(prepareStatementCount).isEqualTo(expected);
    }
}
