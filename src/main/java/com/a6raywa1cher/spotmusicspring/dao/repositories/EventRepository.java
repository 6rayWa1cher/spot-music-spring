package com.a6raywa1cher.spotmusicspring.dao.repositories;

import com.a6raywa1cher.spotmusicspring.models.Event;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {
}
