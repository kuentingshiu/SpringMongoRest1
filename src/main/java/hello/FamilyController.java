package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.WriteResult;

@Controller
public class FamilyController {

	@Autowired
	private FamilyRepositoryCustom myFamily;

//	REST API: /family/id
//	HTTP verb: GET
	@RequestMapping(value = "/family/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Family FindByFamilyId(@PathVariable("id") int id) {
		
		try {
			return myFamily.GetFamilyById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error : unable to query Family.");
			return null;
		}
	}
	
//	REST API: /addfamily
//	HTTP verb: PUT
	@RequestMapping(value = "/addFamily", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public Status AddFamily(@RequestBody Family newFamily)
	{
		try {
			myFamily.AddFamily(newFamily);
			return new Status("Success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error : unable to add Family.");
			return new Status("Unable to add Family");
		}
		
	}
	
//	REST API: /family/id/edit
//	HTTP verb: GET
	@RequestMapping(value = "/family/{id}/edit", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Family FindByFamilyEditId(@PathVariable("id") int id) {
		
		try {
			return myFamily.GetFamilyByIdEdit(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error : unable to view Family.");
			return null;
		}
	}
	
//	REST API: /family/id/edit
//	HTTP verb: POST	
	@RequestMapping(value = "/family/{id}/edit", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Status UpdateFamily(@PathVariable("id") int id, @RequestBody Family newFamily) {
		
		try {
			myFamily.UpdateFamily(id, newFamily);
			return new Status("Success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error : unable to add Family.");
			return new Status("Unable to edit Family");
		}

	}
	
//	REST API: /family/id/addperson
//	HTTP verb: PUT
	@RequestMapping(value = "/family/{familyId}/addPerson", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public Status AddPerson(@PathVariable("familyId") int familyId, @RequestBody User newUser) {
		try {
			myFamily.AddFamilyPerson(familyId, newUser);
			return new Status("Success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error : unable to add User.");
			return new Status("Unable to add User");
		}
	}
	
	
//	REST API: /family/id/person/id/edit
//	HTTP verb: GET	
	@RequestMapping (value = "/family/{familyId}/person/{userId}/edit", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public User GetUserByFamilyIdAndPersonId(@PathVariable("familyId") int familyId, @PathVariable("userId") int userId) {
		
		try {
			return myFamily.FindPersonByFamilyIdAndUserId(familyId, userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error : unable to view User.");
			return null;
		}		
	}
	
//	REST API: /family/id/person/id/edit
//	HTTP verb: POST		
	@RequestMapping (value = "/family/{familyId}/person/{userId}/edit", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Status UpdateUserByFamilyIdAndPersonId(@PathVariable ("familyId") int familyId, 
												  @PathVariable ("userId") int userId, 
												  @RequestBody User updateUser) {
		
		WriteResult wr = myFamily.UpdatePersonByFamilyIdAndUserId(familyId, userId, updateUser);
		
		if (wr.getError() == null)
			return new Status("Success");
		else
			return new Status("Unable to update User");
/*		try {
			WriteResult wr = myFamily.UpdatePersonByFamilyIdAndUserId(familyId, userId, updateUser);
			return new Status("Success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error : unable to add User.");
			return new Status("Unable to update User");
		}*/

	}
	
//	REST API: family/id/person/id/delete
//	HTTP verb: DELETE	
	@RequestMapping (value = "/family/{familyId}/person/{userId}/delete", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Status DeleteUserByFamilyIdAndPersonId(@PathVariable("familyId") int familyId, @PathVariable("userId")int userId) {
		
		WriteResult wr = myFamily.DeletePersonByFamilyIdAndUserId(familyId, userId);
		
		if (wr.getError() == null)
			return new Status("Success");
		else
			return new Status("Unable to update User");

	}
	
// REST API : /family/searchfamily/...
// Parameters : fromAge, toAge,languages
// HTTP verb: GET		
}
