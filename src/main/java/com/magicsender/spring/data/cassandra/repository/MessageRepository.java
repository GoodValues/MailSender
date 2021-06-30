package com.magicsender.spring.data.cassandra.repository;

import java.util.List;
import java.util.UUID;

import com.magicsender.spring.data.cassandra.model.Message;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface MessageRepository extends CassandraRepository<Message, UUID> {
    @AllowFiltering
    List<Message> findAllByEmail(String email);

    @AllowFiltering
    List<Message> findByMagicnumber(Integer magicnumber);

    @AllowFiltering
    void deleteById(UUID id);

}
