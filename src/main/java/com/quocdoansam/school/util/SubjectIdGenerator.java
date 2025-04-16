package com.quocdoansam.school.util;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectIdGenerator implements IdentifierGenerator {
    static final String PREFIX = "MH";

    @SuppressWarnings("deprecation")
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        String query = "SELECT MAX(sj.id) FROM Subject sj";
        String maxId = (String) session.createQuery(query).uniqueResult();

        int nextId = 1;
        if (maxId != null && maxId.startsWith(PREFIX)) {
            int current = Integer.parseInt(maxId.substring(PREFIX.length()));
            nextId = current + 1;
        }

        return PREFIX + String.format("%05d", nextId);
    }
}
