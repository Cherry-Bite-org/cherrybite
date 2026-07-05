package com.cherrybite.repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cherrybite.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, UUID> {

	boolean existsByNameIgnoreCaseAndAddressIgnoreCase(String name, String address);

	@Query("""
			SELECT p
			FROM Place p
			WHERE p.active = true
			AND (
			    LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
			    OR LOWER(p.address) LIKE LOWER(CONCAT('%', :keyword, '%'))
			)
			ORDER BY p.verified DESC,
			         p.name ASC
			""")
	List<Place> searchPlaces(@Param("keyword") String keyword, Pageable pageable);

	@Query("""
			SELECT p
			FROM Place p
			WHERE p.active = true
			""")
	List<Place> findAllActivePlaces();

}
