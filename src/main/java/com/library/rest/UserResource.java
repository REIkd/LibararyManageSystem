package com.library.rest;

import com.library.service.UserService;
import com.library.util.GsonUtil;
import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST API endpoints for user management
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    
    private final UserService userService;
    private final Gson gson;
    
    public UserResource() {
        this.userService = new UserService();
        this.gson = GsonUtil.getGson();
    }
    
    /**
     * GET /api/users - Get all users
     */
    @GET
    public Response getAllUsers() {
        try {
            List<Map<String, Object>> users = userService.getAllUsers();
            Map<String, Object> response = Map.of(
                "success", true,
                "data", users
            );
            return Response.ok(gson.toJson(response)).build();
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error fetching users: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * GET /api/users/{id} - Get user by ID
     */
    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        try {
            Optional<Map<String, Object>> userOpt = userService.getUserById(id);
            
            if (userOpt.isPresent()) {
                Map<String, Object> response = Map.of(
                    "success", true,
                    "data", userOpt.get()
                );
                return Response.ok(gson.toJson(response)).build();
            } else {
                Map<String, Object> error = Map.of(
                    "success", false,
                    "message", "User not found"
                );
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(gson.toJson(error)).build();
            }
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error fetching user: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * GET /api/users/search?query={query} - Search users
     */
    @GET
    @Path("/search")
    public Response searchUsers(@QueryParam("query") String query) {
        try {
            if (query == null || query.trim().isEmpty()) {
                Map<String, Object> error = Map.of(
                    "success", false,
                    "message", "Search query is required"
                );
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson(error)).build();
            }
            
            List<Map<String, Object>> users = userService.searchUsers(query);
            Map<String, Object> response = Map.of(
                "success", true,
                "data", users
            );
            return Response.ok(gson.toJson(response)).build();
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error searching users: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * POST /api/users - Create new user
     */
    @POST
    public Response createUser(String jsonPayload) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> data = gson.fromJson(jsonPayload, Map.class);
            
            Map<String, Object> result = userService.createUser(data);
            
            if ((Boolean) result.get("success")) {
                return Response.status(Response.Status.CREATED)
                    .entity(gson.toJson(result)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson(result)).build();
            }
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error creating user: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * PUT /api/users/{id} - Update user
     */
    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, String jsonPayload) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> data = gson.fromJson(jsonPayload, Map.class);
            
            Map<String, Object> result = userService.updateUser(id, data);
            
            if ((Boolean) result.get("success")) {
                return Response.ok(gson.toJson(result)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson(result)).build();
            }
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error updating user: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * DELETE /api/users/{id} - Delete user
     */
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        try {
            Map<String, Object> result = userService.deleteUser(id);
            
            if ((Boolean) result.get("success")) {
                return Response.ok(gson.toJson(result)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(gson.toJson(result)).build();
            }
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error deleting user: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * GET /api/users/statistics - Get user statistics
     */
    @GET
    @Path("/statistics")
    public Response getUserStatistics() {
        try {
            Map<String, Object> stats = userService.getUserStatistics();
            Map<String, Object> response = Map.of(
                "success", true,
                "data", stats
            );
            return Response.ok(gson.toJson(response)).build();
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error fetching statistics: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(gson.toJson(error)).build();
        }
    }
}

