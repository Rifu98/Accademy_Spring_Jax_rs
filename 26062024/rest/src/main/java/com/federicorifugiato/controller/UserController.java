package com.federicorifugiato.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import com.federicorifugiato.model.User;
import com.federicorifugiato.service.UserService;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GET
	@Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers () {
		List<User> users = userService.getUsers();
		return Response.status(Status.OK).entity(users)
                .header("Access-Control-Allow-Origin", "*")  
				.build();
	}
	
	@GET
	@Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getUserById (@PathParam("id") int id) {
		try {
			User user = userService.getUserById(id);
			return Response.status(Status.OK).entity(user)
	                .header("Access-Control-Allow-Origin", "*")  
					.build();
			} catch (Exception e) {
				return Response.status(Status.BAD_REQUEST)
		                .header("Access-Control-Allow-Origin", "*")  
						.build();
			}
	}
	
	@POST
	@Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser (@Valid @RequestBody User user) {
		try {
			if(userService.existsUserByMail(user.getMail())) {
				return Response.status(Status.BAD_REQUEST).entity("già registrato").build();
			}
			userService.registerUtente(user);
			return Response.status(Status.OK).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity("errore").build();
		}
	}
	
	@PUT
	@Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser (@Valid @RequestBody User user) {
		try {
			if(!userService.existsUserByMail(user.getMail())) {
				return Response.status(Status.BAD_REQUEST).entity("non trovato").build();
			}
			userService.updateUtente(user);
			return Response.status(Status.OK).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity("errore").build();
		}
	}
	
	@DELETE
	@Path("/delete/{id}")
	public Response deleteUser (@PathParam("id") int id) {
		try {
			userService.deleteUser(id);
			return Response.status(Status.OK)
					.build();
			} catch (Exception e) {
				return Response.status(Status.BAD_REQUEST)
						.build();
			}
	}
	
}
