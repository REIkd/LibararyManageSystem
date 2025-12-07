package com.library.rest;

import com.library.service.BookService;
import com.library.util.GsonUtil;
import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST API endpoints for book management
 */
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    
    private final BookService bookService;
    private final Gson gson;
    
    public BookResource() {
        this.bookService = new BookService();
        this.gson = GsonUtil.getGson();
    }
    
    /**
     * GET /api/books - Get all books
     */
    @GET
    public Response getAllBooks() {
        try {
            List<Map<String, Object>> books = bookService.getAllBooks();
            Map<String, Object> response = Map.of(
                "success", true,
                "data", books
            );
            return Response.ok(gson.toJson(response)).build();
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error fetching books: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * GET /api/books/available - Get available books
     */
    @GET
    @Path("/available")
    public Response getAvailableBooks() {
        try {
            List<Map<String, Object>> books = bookService.getAvailableBooks();
            Map<String, Object> response = Map.of(
                "success", true,
                "data", books
            );
            return Response.ok(gson.toJson(response)).build();
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error fetching available books: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * GET /api/books/search?query={query} - Search books
     */
    @GET
    @Path("/search")
    public Response searchBooks(@QueryParam("query") String query) {
        try {
            if (query == null || query.trim().isEmpty()) {
                Map<String, Object> error = Map.of(
                    "success", false,
                    "message", "Search query is required"
                );
                return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(error)).build();
            }
            
            List<Map<String, Object>> books = bookService.searchBooks(query);
            Map<String, Object> response = Map.of(
                "success", true,
                "data", books
            );
            return Response.ok(gson.toJson(response)).build();
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error searching books: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * GET /api/books/{id} - Get book by ID
     */
    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") Long id) {
        try {
            Optional<Map<String, Object>> bookOpt = bookService.getBookById(id);
            
            if (bookOpt.isPresent()) {
                Map<String, Object> response = Map.of(
                    "success", true,
                    "data", bookOpt.get()
                );
                return Response.ok(gson.toJson(response)).build();
            } else {
                Map<String, Object> error = Map.of(
                    "success", false,
                    "message", "Book not found"
                );
                return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(error)).build();
            }
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error fetching book: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * POST /api/books - Create new book
     */
    @POST
    public Response createBook(String jsonPayload) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> data = gson.fromJson(jsonPayload, Map.class);
            
            Map<String, Object> result = bookService.createBook(data);
            
            if ((Boolean) result.get("success")) {
                return Response.status(Response.Status.CREATED).entity(gson.toJson(result)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(result)).build();
            }
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error creating book: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * PUT /api/books/{id} - Update book
     */
    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") Long id, String jsonPayload) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> data = gson.fromJson(jsonPayload, Map.class);
            
            Map<String, Object> result = bookService.updateBook(id, data);
            
            if ((Boolean) result.get("success")) {
                return Response.ok(gson.toJson(result)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(result)).build();
            }
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error updating book: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
    
    /**
     * DELETE /api/books/{id} - Delete book
     */
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        try {
            Map<String, Object> result = bookService.deleteBook(id);
            
            if ((Boolean) result.get("success")) {
                return Response.ok(gson.toJson(result)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity(gson.toJson(result)).build();
            }
        } catch (Exception e) {
            Map<String, Object> error = Map.of(
                "success", false,
                "message", "Error deleting book: " + e.getMessage()
            );
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(error)).build();
        }
    }
}

