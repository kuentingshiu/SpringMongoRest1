package hello;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

@Repository
public class FamilyRepositoryImpl implements FamilyRepositoryCustom {
	
	@Autowired
	private MongoTemplate mongoTemplate;

//	REST API: /family/id
//	HTTP verb: GET
	@Override
	public Family GetFamilyById(int id) throws Exception {

		Query myQuery = new Query();
		myQuery.addCriteria(Criteria.where("id").is(id));
		myQuery.fields().exclude("description");
		myQuery.fields().exclude("member.facebookId");
		
		return mongoTemplate.findOne(myQuery, Family.class,"Family");		
	}		

//	REST API: /family/id/edit
//	HTTP verb: GET		
	@Override
	public Family GetFamilyByIdEdit(int id) throws Exception {

		Query myQuery = new Query();
		myQuery.addCriteria(Criteria.where("id").is(id));
		myQuery.fields().exclude("member");

		return mongoTemplate.findOne(myQuery, Family.class,"Family");		
	}		
	
	
//	REST API: /addfamily
//	HTTP verb: PUT	
	@Override
	public void AddFamily(Family newFamily) throws Exception {
		
		mongoTemplate.save(newFamily,"Family");
	}
	
//	REST API: /family/id/edit
//	HTTP verb: POST		
	@Override
	public void UpdateFamily(int id, Family updatedFamily) throws Exception {
		Query myQuery = new Query();
		myQuery.addCriteria(Criteria.where("id").is(id));

		DBObject updatedFamilyDBObject = (DBObject) mongoTemplate.getConverter().convertToMongoType(updatedFamily);
		updatedFamilyDBObject.removeField("_id");
		updatedFamilyDBObject.removeField("member");
		Update setUpdate = Update.fromDBObject(new BasicDBObject("$set",updatedFamilyDBObject));
		mongoTemplate.updateFirst(myQuery, setUpdate, Family.class);

	}

//	REST API: /family/id/addperson
//	HTTP verb: PUT		
	@Override
	public void AddFamilyPerson(int id, User newUser) throws Exception {
		Query myQuery = new Query();
		myQuery.addCriteria(Criteria.where("id").is(id));
		mongoTemplate.updateFirst(myQuery, new Update().push("member", newUser), Family.class,"Family");
		mongoTemplate.save(newUser, "User");
	}
	
//	REST API: /family/id/person/id/edit
//	HTTP verb: GET	
	@Override
	public User FindPersonByFamilyIdAndUserId(int familyId, int userId) throws Exception {

		Query myQuery = new Query();
		myQuery.addCriteria(Criteria.where("id").is(familyId).andOperator(Criteria.where("member.id").is(userId)));
		Family getFamily = mongoTemplate.findOne(myQuery, Family.class,"Family");
		User thisUser = null;
		Iterator<User> userIterator = getFamily.getMember().iterator();
		while (userIterator.hasNext()) {
			thisUser = (User) userIterator.next();
			if (thisUser.getId() == userId)
				break;
		}
		
		return thisUser;
	}

//	REST API: /family/id/person/id/edit
//	HTTP verb: POST		
	@Override
	public WriteResult UpdatePersonByFamilyIdAndUserId(int familyId, int userId, User updateUser) {
		
		// update Collection "User"
		Query QueryInUser = new Query();
		QueryInUser.addCriteria(Criteria.where("id").is(userId));
		DBObject updatedUserDBObject = (DBObject) mongoTemplate.getConverter().convertToMongoType(updateUser);
		updatedUserDBObject.removeField("_id");
		updatedUserDBObject.removeField("facebookId");
		Update setUserUpdate = Update.fromDBObject(new BasicDBObject("$set",updatedUserDBObject));
		User getUser = mongoTemplate.findAndModify(QueryInUser, setUserUpdate, User.class, "User");
		
		// If not found, return null;
		if (getUser.getId()!= updateUser.getId())
			return null;
		
		// update Collection "Family"
		Query QueryInFamily = new Query();
		QueryInFamily.addCriteria(Criteria.where("id").is(familyId).andOperator(Criteria.where("member.id").is(userId)));
		Update userUpdate = new Update().set("member.$", getUser);
		return mongoTemplate.updateFirst(QueryInFamily, userUpdate, "Family");

	}
	
//	REST API: family/id/person/id/delete
//	HTTP verb: DELETE	
	@Override
	public WriteResult DeletePersonByFamilyIdAndUserId(int familyId, int userId) {

		// Delete user from the collection "User"
		Query queryInUser = new Query();
		queryInUser.addCriteria(Criteria.where("id").is(userId));
		User deleteUser = mongoTemplate.findAndRemove(queryInUser, User.class, "User");
		
		// Return null, if we cannot find this userId in the collectio "User"
		if (deleteUser.getId() != userId) {
			return null;
		}
		
		// Delete user from the collection "Family"
		BasicDBObject chooseFamily = new BasicDBObject("id",familyId);
		BasicDBObject chooseUser = new BasicDBObject("id",userId);
		BasicDBObject chooseMemberElement = new BasicDBObject("member",chooseUser);
		BasicDBObject removeMemberElement = new BasicDBObject("$pull",chooseMemberElement);
		return mongoTemplate.getDb().getCollection("Family").update(chooseFamily, removeMemberElement);		
		
	}
	
// REST API : /family/searchfamily/...
// Parameters : fromAge, toAge,languages
// HTTP verb: GET	
	//@Override
	//public List<Family> SearchFamily1(int fromAge, int toAge, String language) {
		
/*		Calendar cal = Calendar.getInstance();
		
		Date today = new Date();
		cal.setTime(today);
		cal.add(Calendar.YEAR, -fromage);
		Date fromDate = cal.getTime();
		
		cal.setTime(today);
		cal.add(Calendar.YEAR, -toage);
		Date toDate = cal.getTime();
		
		Query myQuery = new Query();
		myQuery.addCriteria(Criteria.where("Kids.birthday").gte(toDate).andOperator(Criteria.where("Kids.birthday").lte(fromDate)));
		return mongoTemplate.find(myQuery, User.class);
	}*/
	
}
