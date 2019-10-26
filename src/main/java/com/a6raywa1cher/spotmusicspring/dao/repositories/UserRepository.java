package com.a6raywa1cher.spotmusicspring.dao.repositories;

import com.a6raywa1cher.spotmusicspring.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User getById(Long id);

	User findByPhoneNumber(String tel);
}
