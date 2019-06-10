package com.revolut.takehome.service;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.revolut.takehome.request.TransferRequest;

@Path("/transfer")
@Produces(MediaType.APPLICATION_JSON)
public class TransferHttpService {
	private TransferService service=new TransferService();

	@POST
	public Response transfer(TransferRequest request) {
		TransferResult result=service.processTransfer(request);
		if(result.getSuccess()) {
			return Response.status(Response.Status.OK).build();
		}else {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result.getReason()).build();
		}
	}
}
