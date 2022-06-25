package com.mariodicaprio.mamba.utils;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TruncateTablesService implements InitializingBean {

    @Autowired
    private EntityManager entityManager;

    private List<String> tableNames;

    //////////////////////////////////////////////////////////////////////////////////

    @Override
    public void afterPropertiesSet() {
        tableNames =
                entityManager
                .getMetamodel()
                .getEntities()
                .stream()
                .filter(entityType -> {
                    boolean isEntity = entityType.getJavaType().getAnnotation(Entity.class) != null;
                    boolean isTable = entityType.getJavaType().getAnnotation(Table.class) != null;
                    return isEntity || isTable;
                })
                .map(EntityType::getName)
                .collect(Collectors.toList());
    }

    @Transactional
    public void truncate() {
        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName);
        }
    }

}
