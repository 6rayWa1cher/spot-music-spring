package com.a6raywa1cher.spotmusicspring.rest.converter;

import com.a6raywa1cher.spotmusicspring.models.Event;
import com.a6raywa1cher.spotmusicspring.models.Song;
import com.a6raywa1cher.spotmusicspring.rest.modelview.SongView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SongConverter implements Converter<Song, SongView> {
	@Autowired
	UserConverter userConverter;

	@Override
	public SongView convert(Song source) {
		SongView songView = new SongView();
		songView.setId(source.getId());
		songView.setUser(userConverter.convert(source.getUser()));
		songView.setEvents(source.getEvents().stream()
				.map(Event::getId)
				.collect(Collectors.toList()));
		songView.setAlbumName(source.getAlbumName());
		songView.setName(source.getName());
		songView.setCoverPhotoUrl(source.getCoverPhotoUrl());
		return songView;
	}
}
