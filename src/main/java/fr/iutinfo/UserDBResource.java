package fr.iutinfo;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

@Path("/userdb")
@Produces(MediaType.APPLICATION_JSON)
public class UserDBResource {
	private static UserDao data = App.dbi.open(UserDao.class);

	public UserDBResource() {
		try {
			data.createUser_Table();
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}
		//data.insert(1,"foo","aaa","coucou","coucou",15,"coucou");
	}
	
	@POST
	public User createUser(User user) {
		int id = data.insertUser(user.getId(),user.getLogin(),user.getMdp(),user.getNom(),user.getPrenom(),user.getType());
		user.setId(id);
		return user;
	}

	@GET
	@Path("/getUserName/{name}")
	public User getUser(@PathParam("name") String name) {
		User out = data.findByName(name);
		if (out == null) {
			throw new WebApplicationException(404);
		}
		return out;
	}
	
	@GET
	@Path("/getUserbyLogin/{login}")
	public User getUserByLogin(@PathParam("login") String login) {
		User out = data.findByName(login);
		if (out == null) {
			throw new WebApplicationException(404);
		}
		return out;
	}
	
	
	@GET
	@Path("/{login}/{mdp}")
	public User verifUserData(@PathParam("login") String pseudo, @PathParam("mdp") String mdp){
		User user = data.verifUser(pseudo, mdp);
		return user;
//		if (user == null){
//			return false;
//		}
//		return true;
	}
	

	
}
