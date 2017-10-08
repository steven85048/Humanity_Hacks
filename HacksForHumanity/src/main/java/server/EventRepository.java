<<<<<<< HEAD
package server;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
	
	public Event findById(String id);
	public List<Event> findByLocation(String location);
	public Event findByEventName(String eventName);
	public List<Event> findBySender(ClientProfile sender);
	
}
=======
package server;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
	
	public Event findById(String id);
	public List<Event> findByLocation(String location);
	public Event findByEventName(String eventName);
	public List<Event> findBySender(ClientProfile sender);
	
}
>>>>>>> 2468df42d74be7c96fb75c6d84f2657f744b7252
