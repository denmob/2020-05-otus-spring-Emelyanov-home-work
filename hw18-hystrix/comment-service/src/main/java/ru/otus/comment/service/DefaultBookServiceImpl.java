package ru.otus.comment.service;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.library.model.Book;

import java.util.Objects;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultBookServiceImpl implements DefaultBookService {

  private static final String DATABASE = "hw18_book";
  private static final String COLLECTION = "books";
  private final MongoCollection<Document> mongoCollection =
      MongoClients.create(createMongoClientSettings()).getDatabase(DATABASE).getCollection(COLLECTION);

  @Override
  public Book getBookByTitle(String title) {
    Document query = new Document("title", title);
    Document document = mongoCollection.find(query).first();
    ObjectId objectId = Objects.requireNonNull(document).get("_id",ObjectId.class);
    return Book.builder().id(objectId.toString()).title(document.getString("title")).build();
  }

  private MongoClientSettings createMongoClientSettings() {
    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    return MongoClientSettings.builder().codecRegistry(pojoCodecRegistry).build();
  }
}
