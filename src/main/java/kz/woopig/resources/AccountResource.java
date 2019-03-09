package kz.woopig.resources;


import kz.woopig.handlers.AccountHandler;
import kz.woopig.requests.AccountRequest;
import kz.woopig.responses.AccountResponse;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AccountResource {

    @GET
    @Path("account/")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response accountCreate(@HeaderParam("AccData") String data) {
        System.out.println("Запрос получен");
        System.out.println("AccData "+ data);
        AccountRequest accountRequest = new AccountRequest(data);
        AccountHandler accountCreateHandler = new AccountHandler.AccountHandlerImpl();
        AccountResponse result = accountCreateHandler.handle(accountRequest);
        return Response
                .status(200)
                .entity(result)
                .build();
    }
}
