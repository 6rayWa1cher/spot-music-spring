package com.a6raywa1cher.spotmusicspring.rest.converter;

import com.a6raywa1cher.spotmusicspring.models.Event;
import com.a6raywa1cher.spotmusicspring.models.Song;
import com.a6raywa1cher.spotmusicspring.models.User;
import com.a6raywa1cher.spotmusicspring.rest.modelview.UserView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter implements Converter<User, UserView> {
	@Override
	public UserView convert(User user) {
		UserView userView = new UserView();
		userView.setId(user.getId());
		userView.setName(user.getName());
		userView.setPhoneNumber(user.getPhoneNumber());
		userView.setCompleteRegistration(user.getCompleteRegistration());
		userView.setDescription(user.getDescription());
		userView.setSocialUrl(user.getSocialUrl());
		userView.setEvents(user.getEvents().stream().map(Event::getId).collect(Collectors.toList()));
		userView.setSongs(user.getSongs().stream().map(Song::getId).collect(Collectors.toList()));
		userView.setPhoto(user.getPhoto());
		return userView;
	}
}
