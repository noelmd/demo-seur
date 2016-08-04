package com.seur.test.contacts;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactRestController {
  
  @Autowired
  private ContactRepository repo;
  
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  
  @RequestMapping(method=RequestMethod.GET)
  public List<Contact> getAll() 
  {
	  LOGGER.info("Operation - List - Required");
	  return repo.findAll();
  }
  
  @RequestMapping(method=RequestMethod.POST)
  public Contact create(@RequestBody Contact contact) 
  {
	  LOGGER.info("Operation - Create - Required");
	  return repo.save(contact);
  }
  
  @RequestMapping(method=RequestMethod.DELETE, value="{id}")
  public void delete(@PathVariable String id) 
  {
	  LOGGER.info("Operation - Delete - Required");
	  repo.delete(id);
  }
  
  @RequestMapping(method=RequestMethod.PUT, value="{id}")
  public Contact update(@PathVariable String id, @RequestBody Contact contact) 
  {
	LOGGER.info("Operation - Update - Required");
	Contact update = repo.findOne(id);
    
    update.setAddress(contact.getAddress());
    update.setEmail(contact.getEmail());
    update.setFacebookProfile(contact.getFacebookProfile());
    update.setFirstName(contact.getFirstName());
    update.setGooglePlusProfile(contact.getGooglePlusProfile());
    update.setLastName(contact.getLastName());
    update.setLinkedInProfile(contact.getLinkedInProfile());
    update.setPhoneNumber(contact.getPhoneNumber());
    update.setTwitterHandle(contact.getTwitterHandle());
    return repo.save(update);
  }

}