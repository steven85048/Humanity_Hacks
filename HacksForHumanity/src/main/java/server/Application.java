<<<<<<< HEAD
package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application implements CommandLineRunner {
	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private EventRepository eventRepo;
	
	
	@RestController
	private class UpdateController {
		
		@RequestMapping(value = "/addUser", method = RequestMethod.POST)
		public void addUser(@RequestBody ClientProfile client) {
			clientRepo.save(client);
		}
		
		@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
		public void addEvent(@RequestBody Event event) {
			eventRepo.save(event);
		}
		
		@RequestMapping(value = "/collaboration", method = RequestMethod.POST)
		public void updateInformation(@RequestBody UpdateWrapper update) {
			ClientProfile receiverClient = clientRepo.findById(update.idReceiver);
			ClientProfile senderClient = clientRepo.findById(update.idSender);
			receiverClient.addToPartnerList(senderClient);
			senderClient.addToPartnerList(receiverClient);
			
			Event event = new Event(update.eventName, update.eventContent, 
					senderClient.getLocation(), senderClient);
			eventRepo.save(event);
			event.addToListOfParticipants(receiverClient);
			event.addToListOfParticipants(senderClient);
			receiverClient.addToPastEventList(event);
			senderClient.addToPastEventList(event);
		
			for (ClientProfile client : clientRepo.findAll()) {
				System.out.println(client);
			}
			System.out.println();
			for (Event event1 : eventRepo.findAll()) {
				System.out.println(event1);
			}
		}
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void run(String... arg0) throws Exception {
		clientRepo.deleteAll();
		eventRepo.deleteAll();		
		
		ClientProfile client1 = new ClientProfile("Kill", "Me", "Nevada");
		ClientProfile client2 = new ClientProfile("Mongo", "DB", "BroJo", "Nevada");
		
		Event event1 = new Event("Send Help", "Help me plz", "Nevada", client1);
		Event event2 = new Event("Test", "Potato", "Nevada", client2);
		
		clientRepo.save(client1);
		clientRepo.save(client2);
		
		eventRepo.save(event1);
		eventRepo.save(event2);
		
		System.out.println("Clients found with findAll():");
		System.out.println("-------------------------------");
		for (ClientProfile client : clientRepo.findAll()) {
			System.out.println(client);
		}
		System.out.println();
		
		System.out.println("Clients found with findByFirstName():");
		System.out.println("-------------------------------");
		for (ClientProfile client : clientRepo.findByFirstName("Kill")) {
			System.out.println(client);
		}
		for (ClientProfile client : clientRepo.findByFirstName("Mongo")) {
			System.out.println(client);
		}
		System.out.println();
		
		System.out.println("Clients found with findByLastName():");
		System.out.println("-------------------------------");
		for (ClientProfile client : clientRepo.findByLastName("Me")) {
			System.out.println(client);
		}
		for (ClientProfile client : clientRepo.findByLastName("DB")) {
			System.out.println(client);
		}
		System.out.println();
		
		System.out.println("Client found with findByProfession():");
		System.out.println("-------------------------------");
		System.out.println(clientRepo.findByProfession("BroJo"));
		System.out.println();
		
		System.out.println("Clients found with findByLocation():");
		System.out.println("-------------------------------");
		for (ClientProfile client : clientRepo.findByLocation("Nevada")) {
			System.out.println(client);
		}
		System.out.println();
		
		System.out.println("Events found with findByLocation():");
		System.out.println("-------------------------------");
		for (Event event : eventRepo.findByLocation("Nevada")) {
			System.out.println(event);
		}
		System.out.println();
		
		System.out.println("Events found with findBySender()");
		System.out.println("-------------------------------");
		for(Event event : eventRepo.findBySender(client1))
			System.out.println(event);
		for(Event event : eventRepo.findBySender(client2))
			System.out.println(event); 
		
	}	
	
}
=======
package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application implements CommandLineRunner {
	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private EventRepository eventRepo;
	
	
	@RestController
	private class UpdateController {
		
		@RequestMapping(value = "/addUser", method = RequestMethod.POST)
		public void addUser(@RequestBody ClientProfile client) {
			clientRepo.save(client);
		}
		
		@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
		public void addEvent(@RequestBody Event event) {
			eventRepo.save(event);
		}
		
		@RequestMapping(value = "/collaboration", method = RequestMethod.POST)
		public void updateInformation(@RequestBody UpdateWrapper update) {
			ClientProfile receiverClient = clientRepo.findById(update.idReceiver);
			ClientProfile senderClient = clientRepo.findById(update.idSender);
			receiverClient.addToPartnerList(senderClient);
			senderClient.addToPartnerList(receiverClient);
			
			Event event = new Event(update.eventName, update.eventContent, 
					senderClient.getLocation(), senderClient);
			eventRepo.save(event);
			event.addToListOfParticipants(receiverClient);
			event.addToListOfParticipants(senderClient);
			receiverClient.addToPastEventList(event);
			senderClient.addToPastEventList(event);
		
			for (ClientProfile client : clientRepo.findAll()) {
				System.out.println(client);
			}
			System.out.println();
			for (Event event1 : eventRepo.findAll()) {
				System.out.println(event1);
			}
		}
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void run(String... arg0) throws Exception {
		clientRepo.deleteAll();
		eventRepo.deleteAll();		
		
		ClientProfile client1 = new ClientProfile("Kill", "Me", "Nevada");
		ClientProfile client2 = new ClientProfile("Mongo", "DB", "BroJo", "Nevada");
		
		Event event1 = new Event("Send Help", "Help me plz", "Nevada", client1);
		Event event2 = new Event("Test", "Potato", "Nevada", client2);
		
		clientRepo.save(client1);
		clientRepo.save(client2);
		
		eventRepo.save(event1);
		eventRepo.save(event2);
		
		System.out.println("Clients found with findAll():");
		System.out.println("-------------------------------");
		for (ClientProfile client : clientRepo.findAll()) {
			System.out.println(client);
		}
		System.out.println();
		
		System.out.println("Clients found with findByFirstName():");
		System.out.println("-------------------------------");
		for (ClientProfile client : clientRepo.findByFirstName("Kill")) {
			System.out.println(client);
		}
		for (ClientProfile client : clientRepo.findByFirstName("Mongo")) {
			System.out.println(client);
		}
		System.out.println();
		
		System.out.println("Clients found with findByLastName():");
		System.out.println("-------------------------------");
		for (ClientProfile client : clientRepo.findByLastName("Me")) {
			System.out.println(client);
		}
		for (ClientProfile client : clientRepo.findByLastName("DB")) {
			System.out.println(client);
		}
		System.out.println();
		
		System.out.println("Client found with findByProfession():");
		System.out.println("-------------------------------");
		System.out.println(clientRepo.findByProfession("BroJo"));
		System.out.println();
		
		System.out.println("Clients found with findByLocation():");
		System.out.println("-------------------------------");
		for (ClientProfile client : clientRepo.findByLocation("Nevada")) {
			System.out.println(client);
		}
		System.out.println();
		
		System.out.println("Events found with findByLocation():");
		System.out.println("-------------------------------");
		for (Event event : eventRepo.findByLocation("Nevada")) {
			System.out.println(event);
		}
		System.out.println();
		
		System.out.println("Events found with findBySender()");
		System.out.println("-------------------------------");
		for(Event event : eventRepo.findBySender(client1))
			System.out.println(event);
		for(Event event : eventRepo.findBySender(client2))
			System.out.println(event); 
		
	}	
	
}
>>>>>>> 2468df42d74be7c96fb75c6d84f2657f744b7252
