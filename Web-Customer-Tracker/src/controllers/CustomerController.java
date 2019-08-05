package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import entities.Customer;
import services.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	//inject the CustomerService into this controller
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCumstomers(Model m) {
		
		//get the customers from the service
		List<Customer> customers = customerService.getCustomers();
		//add the customer to the model
		m.addAttribute("customers", customers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model m) {
		
		//create and add new model attribute to bind form data.
		m.addAttribute("customer", new Customer());
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		
		//save the customer using our service
		customerService.saveCustomer(theCustomer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id, Model m) {
		
		//get the customer from service
		Customer c = customerService.getCustomer(id);
		
		//set customer as a model attribute to pre-populate the form
		m.addAttribute("customer", c);
		
		//send over to our form	
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id) {
		
		customerService.deleteCustomer(id);
			
		//send over to our form	
		return "redirect:/customer/list";
	}
}
