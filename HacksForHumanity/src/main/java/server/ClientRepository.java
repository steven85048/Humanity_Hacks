<<<<<<< HEAD
package server;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<ClientProfile, String> {
	
	public List<ClientProfile> findByFirstName(String firstName);
	public List<ClientProfile> findByLastName(String lastName);
	public ClientProfile findById(String id);
	public List<ClientProfile> findByProfession(String profession);
	public List<ClientProfile> findByLocation(String location);
	
}
=======
package server;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<ClientProfile, String> {
	
	public List<ClientProfile> findByFirstName(String firstName);
	public List<ClientProfile> findByLastName(String lastName);
	public ClientProfile findById(String id);
	public List<ClientProfile> findByProfession(String profession);
	public List<ClientProfile> findByLocation(String location);
	
}
>>>>>>> 2468df42d74be7c96fb75c6d84f2657f744b7252
