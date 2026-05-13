package com.harsh.myfirstapp;

import com.harsh.myfirstapp.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

// import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication  // Ye project ki main class pr lgta he (iska mtlb he : Yaha se Engine start kro or saari files ko scan kro)
@RestController   // Ye class ko "Web-Ready" bnata he. Ye Spring ko bolta he ki is Class ki methods "JSON" data return kregi koi HTML page nhi
@CrossOrigin(origins = "*") // ye Line IMPORTANT he taaki front end se connect ho ske
public class MyfirstappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyfirstappApplication.class, args);
	}

	//1. Connect the Repository (Dependency Injection)
//	@Autowired   // hmne yaha repository ko controller me Autowired kiya he taaki hum repository k functions use kr ske
//			private TicketRepository ticketRepository;
    @Autowired    // hmne yaha TicketService ko controller me Autowired kiya he taaki hum TicketService k functions use kr ske
	private TicketService ticketService;  // ab hum Service se baat krenge

//	List<SupportTicket> ticketList = new ArrayList<>();
// 1 annotation hota he RequestMapping("/api") - "Ye optional hota he agr hum chahte he hmare URLs /api se start ho" eg - localhost:8080/api/all-tickets

	@GetMapping("/status") // @GetMapping: Data read/get krne k liye
	public String checkStatus(){
		return "Congrats! First SpringBoot server works";
}
@GetMapping("/bpo-to-it")
	public String careerPlan(){
		return"1.5 Years BPO experience + Java Skills = Strong Technical Support or Developer role!";
}
@GetMapping("/greet")
	public String greetUser(@RequestParam(value="name", defaultValue = "Stranger") String name){
		return "Hello "+name+", Welcome!!! to IT Industry";
}
@GetMapping("/calculator")
//  public String calculate(@RequestParam int num1,@RequestParam int num2){
	public String calculate(@RequestParam int num1,int num2){ // @RequestParam - URLs k andar jo choti information aati he use pkdne k liye Eg. - ?id=101
		int sum = num1 + num2;
		return "The total is: "+ sum;
}

//@PostMapping("/create-ticket")     // @PostMapping : Data Save/Create krne k liye
//	public String createTicket(@RequestBody SupportTicket ticket){
//		System.out.println("New Ticket received: "+ticket.issue);
//		return "Ticket #"+ ticket.id + " Successfully Created!";
//}

//@PostMapping("/create-ticket")
//	public String createTicket(@RequestBody SupportTicket ticket){
//		ticketList.add(ticket); // Isse data hmari local list me add ho rha he
//		return "Ticket added successfully, Total Tickets: "+ticketList.size();
//}
//@GetMapping("/all-tickets")
//	public List<SupportTicket> getAll(){
//		return ticketList;
//}

// 2. Data save krne k liye PostMapping
@PostMapping("/create-ticket")
// Kyuki hmne Entity class me Validation add kiya he (@NotBlack, @Size) isliye hmne yaha @Valid ka use kiya he Ticket create k time
public String createTicket(@Valid @RequestBody SupportTicket ticket){     // @RequestBody - Postman se jo JSON data aa rha h BOX me use JAVA Object me convert k liye
		// ticketRepository.save(ticket);// Ye line data sidha MySQL me daal degi
	ticketService.createTicket(ticket);
	return "Ticket #"+ ticket.getId()+ " successfully saved in MySQL Database!";
}

// 3. To view ALL DATA
	@GetMapping("/all-tickets")
	public List<SupportTicket> getAllTickets(){
//		return ticketRepository.findAllByOrderByCreatedAtDesc(); // Ye saare records database se nikal lega
	return ticketService.getAllTickets(); // controller ne sirf service ko order diya
	}

	// @PutMapping - Purane data ko Update krne k liye

	@PutMapping("/update-ticket/{id}")
	public SupportTicket update(@PathVariable int id, @RequestBody SupportTicket ticket){ // hmne @PathVariable se URL se aane wali ID pkdi, @RequestBody se new data liya jo update krna he
		return ticketService.updateTicket(id,ticket);
	}

	// @DeleteMapping - Data ko delete krne k liye
	@DeleteMapping("/delete-ticket/{id}")
	public String deleteTicket(@PathVariable int id) {
		return ticketService.deleteTicket(id);
	}

// 400 bad request message ki jgh, hmne jo Entity class me validation k waha message diya he wo dikhane k liye
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	// Find by Status krne k liye
	@GetMapping("/tickets/status/{statusvalue}")
	public List<SupportTicket> getByStatus(@PathVariable String statusvalue){
		return ticketService.findByStatus(statusvalue);
	}

	// Search Funtionality
	// Find by issue k liye TASK h ye (isme bhi koisa bhi word 1 char bhi agr match ho rha he to wo data aa rha h pura thats correct)
	@GetMapping("/tickets/issue/{keyword}")
	public List<SupportTicket> getByIssue(@PathVariable String keyword){
		return ticketService.searchTickets(keyword);
	}

//	 Search by Keyword - (issue k andar agr 1 bhi word match krega to wo show krega output me)
	// isme URL bht alag tarike se dalna pd rha he - /tickets/search?keyword=laptop (iske uper wala sahi he jo nene bnaya h)
//	@GetMapping("/tickets/search")
//	public List<SupportTicket> searchByKeyword(@RequestParam String keyword){
//		return ticketRepository.findByIssueContaining(keyword);
//	}


}



