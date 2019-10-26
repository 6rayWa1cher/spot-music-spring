package com.a6raywa1cher.spotmusicspring.rest.converter;

import com.a6raywa1cher.spotmusicspring.models.Event;
import com.a6raywa1cher.spotmusicspring.rest.modelview.EventView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EventConverter implements Converter<Event, EventView> {
	@Autowired
	UserConverter userConverter;

	@Autowired
	SongConverter songConverter;

	@Override
	public EventView convert(Event source) {
		EventView eventView = new EventView();
		eventView.setId(source.getId());
		eventView.setSongs(source.getSongs().stream().map(songConverter::convert).collect(Collectors.toList()));
		eventView.setUser(userConverter.convert(source.getUser()));
		eventView.setLatitude(source.getLatitude());
		eventView.setLongitude(source.getLongitude());
		eventView.setDateTime(source.getDateTime());
		eventView.setDuration(source.getDuration());
		eventView.setMoney(source.getMoney());
		return eventView;
	}
}
