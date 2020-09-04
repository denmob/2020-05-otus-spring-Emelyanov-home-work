package ru.otus.hw13.security.acls.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw13.security.acls.domain.MongoAcl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface AclRepository extends MongoRepository<MongoAcl, Serializable> {

  Optional<MongoAcl> findById(Serializable id);

  List<MongoAcl> findByInstanceIdAndClassName(Serializable instanceId, String className);

  List<MongoAcl> findByParentId(Serializable parentId);

  void deleteByInstanceId(Serializable instanceId);
}
