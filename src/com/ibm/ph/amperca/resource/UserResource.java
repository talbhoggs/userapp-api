package com.ibm.ph.amperca.resource;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.ibm.ph.amperca.model.User;
import com.ibm.ph.amperca.representation.UsersRepresentation;
import com.ibm.ph.amperca.service.UserService;
import com.ibm.ph.amperca.util.Message;
import com.ibm.ph.amperca.util.Pagination;

/*
 * Note:
 * 
 * I had a problem with dependency injection in JAX-RS. It seems that JAX-RS can't recognize EJB Beans as it properties.
 * I tried using EJB with a simple servlet. I works fine.
 * 
 * Fortunately, I found this link below that solves the issue.
 * 
 * http://stackoverflow.com/questions/3027834/inject-an-ejb-into-jax-rs-restful-service
 * 
 * The solution that I chose was the CDI approach. I really don't know what I'm doing.!! What is important is I had make it worked. 
 * 
 * Next Step
 * ---------
 * The next step is to research about bestpractices for this kind of issue
 * 
 * 
 * Developer : amperca@ph.ibm.com
 * 
 */

@Path("service")
@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserResource {

	@Inject
	UserService us;

	@Context
	UriInfo pUriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {

		// 1) Get query parameters from the url
		String sort = pUriInfo.getQueryParameters().getFirst("sort");
		String perPage = pUriInfo.getQueryParameters().getFirst("per_page");
		String page = pUriInfo.getQueryParameters().getFirst("page");
		String sortDirection = pUriInfo.getQueryParameters().getFirst("sort_direc");
		String searchField = pUriInfo.getQueryParameters().getFirst("search");

		// 2) Set default vaules if no value is set
		if (sort == null)
			sort = "firstName";
		if (page == null)
			page = "1"; // default
		if (perPage == null)
			perPage = "5"; // default
		if (sortDirection == null)
			sortDirection = "desc";
		if (searchField == null)
			searchField = "";
			
		System.out.println("<<<<<<<<<<<<<<<<<camper>>>>>>>>>>>>>>>>>>>");
		System.out.println("Sort : " + sort + "\n Per Page : " + perPage
				+ "\n >>Page : " + page + " \n searchField : " + searchField);
		System.out.println("<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>");

		// 3) Initialize the Pagination object with the input parameters
		// - this part in
		Pagination paginatedList = new Pagination();
		paginatedList.setCurrentPage(Integer.parseInt(page));
		paginatedList.setPageSize(Integer.parseInt(perPage));
		paginatedList.setSortFields(sort);
		paginatedList.setSortDirections(sortDirection);
		
		try {
			
			// 3a) returns the total record of the users table
			int count = us.countUser(searchField);
			System.out.println("count : " + count);
			paginatedList.setTotalResults(count);

			// 4) call the getUser Service Method and make Pagination Object as
			// input
			// - returns the truncated value of the Users table
			int startPosition = paginatedList.getStartPage();

			List<User> fList = us.getUsers(startPosition,
					paginatedList.getPageSize(), paginatedList.getSortFields(),
					paginatedList.getSortDirections(), searchField);
			paginatedList.setList(fList);
			System.out.println(paginatedList.toString());
			// 5) Wrapper object that represent a restful object response
			UsersRepresentation userRest = new UsersRepresentation();
			userRest.setEntries(paginatedList.getList());
			userRest.setTotalRecords(count + "");
			userRest.setNext(paginatedList.hasNext());
			userRest.setPrev(paginatedList.hasPrev());
			userRest.setCurrentPage(paginatedList.getCurrentPage());

			System.out.println(userRest.toString());
			return Response.status(200).entity(userRest).build();
			
		} catch(RuntimeException ex) {
			Message message = new Message();
			message.setCode("503");
			message.setDescription("503 Service Unavailable");
			message.setType("error");
			return Response.status(503).entity(message).build();
			// Sorry this feature is unavilable as of the moment 
			// Please reload the 	Application Temporarily Unavailable
			// 	Service temporarily unavailable. Please contact amperca@ph.ibm.com.
		}
		
	}

	@POST
	public Response addUser(User u) {
		try {
			u = us.add(u);
			// UriBuilder pUriBuilder = pUriInfo.getAbsolutePathBuilder();
			// URI pUri = pUriBuilder.path(String.valueOf(u.getId())).build();
			Message message = new Message();
			message.setCode("201");
			message.setDescription("User successfully Created");
			message.setType("success");
			return Response.status(201).entity(message).build();
		} catch (Exception ex) {
			Message message = new Message();
			message.setCode("404");
			message.setDescription("Unable to add User");
			message.setType("error");
			return Response.status(404).entity(message).build();
		}
	}

	@GET
	@Path("{user_id}")
	public Response getUser(@PathParam("user_id") Long userId) {
		User u = us.findById(userId);
		if (u == null) {
			Message message = new Message();
			message.setCode("404");
			message.setDescription("User not found");
			message.setType("error");
			return Response.status(404).entity(message).build();
		}

		return Response.status(200).entity(u).build();
	}

	@PUT
	@Path("{user_id}")
	public Response updateUser(@PathParam("user_id") Long pId, User pUpdated) {

		pUpdated = us.update(pId, pUpdated);

		if (pUpdated == null) {
			Message message = new Message();
			message.setCode("404");
			message.setDescription("User not found");
			message.setType("error");
			return Response.status(404).entity(message).build();
		}

		Message message = new Message();
		message.setCode("201");
		message.setDescription("User successfully Updated");
		message.setType("success");

		return Response.status(201).entity(message).build();
	}

	@DELETE
	@Path("{user_id}")
	public Response deleteUser(@PathParam("user_id") Long userId) {

		User userToRemove = us.findById(userId);

		if (userToRemove == null) {
			Message message = new Message();
			message.setCode("404");
			message.setDescription("User not found");
			message.setType("error");
			return Response.status(404).entity(message).build();
		}

		us.deleteUser(userToRemove);

		Message message = new Message();
		message.setCode("204");
		message.setDescription("User successfully deleted");
		message.setType("success");
		return Response.status(204).entity(message).build();

	}
}
