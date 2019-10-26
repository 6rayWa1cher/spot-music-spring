package com.a6raywa1cher.spotmusicspring.dao.repositories;

import com.a6raywa1cher.spotmusicspring.models.Song;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends CrudRepository<Song, Long> {
}
