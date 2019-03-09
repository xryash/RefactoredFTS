package kz.woopig.resources;


import kz.woopig.entities.HttpFile;
import kz.woopig.handlers.UploadingFileHandler;
import kz.woopig.requests.UploadingFileRequest;
import kz.woopig.responses.UploadingFileResponse;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.InputStream;



public class UploadingFileResource {

    @Context
    private SecurityContext sc;

    @POST
    @RolesAllowed({"USER","ADMIN"})
    @Path("upload/")
    @Consumes(MediaType.MULTIPART_FORM_DATA )
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(
                            @FormDataParam("file") InputStream stream,
                            @FormDataParam("file") FormDataContentDisposition fileMetaData,
                            @FormDataParam("directory") String directory,
                            @FormDataParam("size") long size) {

        System.out.println("Запрос получен");
        String submittedFileName = fileMetaData.getFileName();
        String login = sc.getUserPrincipal().getName();
        HttpFile httpFile = new HttpFile(submittedFileName, stream ,directory ,size);
        System.out.println(httpFile.toString());
        UploadingFileRequest request = new UploadingFileRequest(httpFile, login);
        UploadingFileHandler.UploadingFileHandlerImpl handler = new UploadingFileHandler.UploadingFileHandlerImpl();
        UploadingFileResponse result = handler.handle(request);

        return Response
                .status(200)
                .entity(result)
                .build();
    }

}
