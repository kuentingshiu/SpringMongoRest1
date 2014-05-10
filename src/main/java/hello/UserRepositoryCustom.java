package hello;

import org.springframework.stereotype.Service;

@Service
public interface UserRepositoryCustom {

//  REST API: /user/id
//	HTTP verb: GET
	public User GetUserById(int id) throws Exception;
	
	//public void AddUser(int FamilyId, User newUser);
}
