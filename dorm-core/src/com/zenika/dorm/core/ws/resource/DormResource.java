package com.zenika.dorm.core.ws.resource;

import com.google.inject.Inject;
import com.zenika.dorm.core.exception.ArtifactException;
import com.zenika.dorm.core.helper.DormFileHelper;
import com.zenika.dorm.core.model.DormArtifact;
import com.zenika.dorm.core.model.DormFile;
import com.zenika.dorm.core.model.DormMetadata;
import com.zenika.dorm.core.model.MetadataExtension;
import com.zenika.dorm.core.service.DormService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Path("dorm")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class DormResource {

    @Inject
    private DormService service;

    @GET
    @Path("bar")
    @Produces("text/plain")
    public Response foo() {

        File file = new File("foobar");

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        createArtifact("foo", "bar", "yoyo.jar", file);

        return Response.status(Response.Status.OK).build();
    }


	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("{name}/{version}/{filename}")
	public Response createArtifactFromPath(@FormParam("file") File file,
			@PathParam("name") String name,
			@PathParam("version") String version,
			@PathParam("filename") String filename) {

		createArtifact(name, version, filename, file);

		return Response.status(Response.Status.OK).build();
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/from-properties")
	public Response createArtifactFromProperties(@FormParam("file") File file,
			@FormParam("properties") File propertiesFile) {

		Properties properties = getPropertiesFromFile(propertiesFile);

		String name;
		String version;
		String filename;

		try {
			name = properties.getProperty("name").toString();
			version = properties.getProperty("version").toString();
			filename = properties.getProperty("filename").toString();
		} catch (NullPointerException e) {
			throw new ArtifactException("Missing artifact metadata")
					.type(ArtifactException.Type.NULL);
		}

		createArtifact(name, version, filename, file);

		return Response.status(Response.Status.OK).build();
	}

	@GET
	@Path("{name}/{version}")
	public DormArtifact<MetadataExtension> getArtifactByMetadata(
			@PathParam("name") String name, @PathParam("version") String version) {

		DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>(
				name, version);

		DormArtifact<MetadataExtension> artifact = service.getArtifact(metadata);

		return null;
	}

	@DELETE
	@Path("{name}/{version}")
	public Response removeArtifactByMetadata(@PathParam("name") String name,
			@PathParam("version") String version) {

		DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>(
				name, version);

		service.removeArtifact(metadata);

		return Response.status(Response.Status.OK).build();
	}

	// @PUT
	// @Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Path("update/{name}/{version}")
	// public Response updateArtifact(@PathParam("name") String name,
	// @PathParam("version") String version, @FormParam("file") File file) {
	//
	// DormArtifact artifact = new DormArtifact(name, version, file);
	//
	// getService().getDormArtifactManager().update(artifact);
	//
	// return Response.status(Response.Status.OK).build();
	// }
	//

	//
	// @GET
	// public List<DormArtifact> getArtifacts() {
	//
	// List<DormArtifact> artifacts = getService().getDormArtifactManager()
	// .getArtifacts();
	//
	// return artifacts;
	// }
	//
	// @GET
	// @Path("{name}")
	// public List<DormArtifact> getArtifactsByName(@PathParam("name") String
	// name)
	// throws Exception {
	//
	// List<DormArtifact> artifacts = getService().getDormArtifactManager()
	// .getArtifactsByName(name);
	//
	// if (artifacts.isEmpty()) {
	// throw new ArtifactException("No artifacts found for name : " + name)
	// .type(ArtifactException.Type.NULL);
	// }
	//
	// return artifacts;
	// }
	//

	protected void createArtifact(String name, String version, String filename,
			File file) {

		DormMetadata<MetadataExtension> metadata = new DormMetadata<MetadataExtension>(
				name, version);

		String extension = DormFileHelper.getExtensionFromFilename(filename);
		DormFile dormFile = new DormFile(filename, extension, file);

        service.pushArtifact(metadata, dormFile, null, null);
	}

	protected Properties getPropertiesFromFile(File file) {

		Properties properties = new Properties();
		FileInputStream stream = null;

		try {
			stream = new FileInputStream(file);
			properties.load(stream);
		} catch (Exception e) {
			throw new ArtifactException("Properties invalid")
					.type(ArtifactException.Type.NULL);
		} finally {
			if (null != stream) {
				try {
					stream.close();
				} catch (IOException e) {

				}
			}
		}

		return properties;
	}
}
