package ru.chikalin.kirill.rocketchat.server;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Репозиторий с сообщениями
 * Date: 25/09/15
 *
 * @author formularhunter
 */
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByUsername(String username);
}
