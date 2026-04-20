/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus1.resource;

/**
 *
 * @author w2086907
 */
//PART2
import com.mycompany.smartcampus1.model.Room;
import com.mycompany.smartcampus1.store.DataStore;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.mycompany.smartcampus1.exception.RoomNotEmptyException;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class RoomResource {
    @GET
    public Response getAllRooms() {
        List<Room> rooms = new ArrayList<>(DataStore.rooms.values());
        return Response.ok(rooms).build();
    }

    @POST
    public Response createRoom(Room room, @Context UriInfo uriInfo) {
        if (room == null || room.getId() == null || room.getId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Room id is required\"}")
                    .build();
        }

        if (DataStore.rooms.containsKey(room.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"Room with this id already exists\"}")
                    .build();
        }

        if (room.getSensorIds() == null) {
            room.setSensorIds(new ArrayList<>());
        }

        DataStore.rooms.put(room.getId(), room);

        URI uri = uriInfo.getAbsolutePathBuilder().path(room.getId()).build();
        return Response.created(uri).entity(room).build();
    }

    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Room not found\"}")
                    .build();
        }

        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);
        
        if (room == null) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Room not found\"}")
                .build();
    }

    if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
        throw new RoomNotEmptyException("Room cannot be deleted because it still has sensors assigned.");
    }

    DataStore.rooms.remove(roomId);
    return Response.ok("{\"message\":\"Room deleted successfully\"}").build();
}
    
}
