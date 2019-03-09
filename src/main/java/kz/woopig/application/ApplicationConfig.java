package kz.woopig.application;

import kz.woopig.exceptions.AccountExceptionMapper;
import kz.woopig.exceptions.DownloadingFileExceptionMapper;
import kz.woopig.exceptions.ProviderExceptionMapper;
import kz.woopig.exceptions.UploadingFileExceptionMapper;
import kz.woopig.filters.LogFilter;
import kz.woopig.filters.AuthenticationFilter;
import kz.woopig.resources.AccountResource;
import kz.woopig.resources.DownloadingFileResource;
import kz.woopig.resources.UploadingFileResource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        super(
                UploadingFileResource.class, DownloadingFileResource.class, AccountResource.class,
                AuthenticationFilter.class, LogFilter.class,
                RolesAllowedDynamicFeature.class, MultiPartFeature.class,
                ProviderExceptionMapper.class, UploadingFileExceptionMapper.class, DownloadingFileExceptionMapper.class, AccountExceptionMapper.class
        );
    }
}
