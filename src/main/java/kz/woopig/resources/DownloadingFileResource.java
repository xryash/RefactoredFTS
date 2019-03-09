package kz.woopig.resources;

import kz.woopig.entities.LocalFile;
import kz.woopig.handlers.DownloadingFileHandler;
import kz.woopig.handlers.FileListHandler;
import kz.woopig.requests.DownloadingFileRequest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.util.List;

public class DownloadingFileResource {

    @Context
    private SecurityContext sc;

    @GET
    @RolesAllowed({"USER","ADMIN"})
    @Path("download/")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response fileDownload(@QueryParam("id") int id) {
        System.out.println("Запрос получен");
        String login = sc.getUserPrincipal().getName();
        DownloadingFileRequest fileDownloadRequest = new DownloadingFileRequest(id, login);
        DownloadingFileHandler fileDownloadHandler = new DownloadingFileHandler.DownloadingFileHandlerImpl();
        StreamingOutput result = fileDownloadHandler.handle(fileDownloadRequest);

        return Response
                .status(200)
                .entity(result)
                .build();
    }

    @GET
    @RolesAllowed({"USER","ADMIN"})
    @Path("download/list/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFileList() {
        System.out.println("Запрос получен");
        String login = sc.getUserPrincipal().getName();
        FileListHandler handler = new FileListHandler.FileListHandlerImpl();
        List<LocalFile> list = handler.handle(login);

        return Response
                .status(200)
                .entity(list)
                .build();

    }
}
