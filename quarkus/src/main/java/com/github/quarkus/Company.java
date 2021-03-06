package com.github.quarkus;

import java.io.Serializable;
import java.time.Instant;

import javax.validation.constraints.NotBlank;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.quarkus.panache.common.Page;
import io.smallrye.mutiny.Multi;

@MongoEntity(collection = "quarkus_companies")
public class Company extends ReactivePanacheMongoEntity implements Serializable {
	@NotBlank
	public String name;
	@NotBlank
	public String createdByUser;
	public boolean activated = true;
	public Instant createdDate = Instant.now();
	public String lastModifiedByUser;
	public Instant lastModifiedDate = Instant.now();

	public static Multi<Company> findActiveCompanies(Integer pageSize) {
	    return find("activated", true)
            .page(Page.ofSize(pageSize))
            .stream();
	}

	public static Multi<Company> findActiveCompaniesByUser(String user, Integer pageSize) {
		return find("activated = ?1 and createdByUser = ?2", true, user)
            .page(Page.ofSize(pageSize))
            .stream();
	}
}
