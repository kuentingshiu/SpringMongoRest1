package hello;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;

@Service
public interface FamilyRepositoryCustom {

//	REST API: /family/id
//	HTTP verb: GET
	public Family GetFamilyById(int id) throws Exception;
	
//	REST API: /family/id/edit
//	HTTP verb: GET	
	public Family GetFamilyByIdEdit(int id) throws Exception;	

//	REST API: /addfamily
//	HTTP verb: PUT
	public void AddFamily(Family newFamily) throws Exception;
	
//	REST API: /family/id/edit
//	HTTP verb: POST		
	public void UpdateFamily(int id, Family updateFamily) throws Exception;
	
//	REST API: /family/id/addperson
//	HTTP verb: PUT		
	public void AddFamilyPerson(int id, User newUser) throws Exception;
		
//	REST API: /family/id/person/id/edit
//	HTTP verb: GET	
	public User FindPersonByFamilyIdAndUserId(int familyId, int userId) throws Exception;
	
//	REST API: /family/id/person/id/edit
//	HTTP verb: POST		
	public WriteResult UpdatePersonByFamilyIdAndUserId(int familyId, int userId, User updateUser);
	
//	REST API: family/id/person/id/delete
//	HTTP verb: DELETE
	public WriteResult DeletePersonByFamilyIdAndUserId(int familyId, int userId);
	
// REST API : /family/searchfamily/...
// Parameters : fromAge, toAge,languages
// HTTP verb: GET		
	//public List<Family> SearchFamily1(int fromAge, int toAge, String language);
}
