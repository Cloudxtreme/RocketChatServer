package ru.chikalin.kirill.rocketchat.server;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * TODO
 * Date: 25/09/15
 *
 * @author formularhunter
 */
public interface MessageRepository extends MongoRepository<Message, String> {
}
