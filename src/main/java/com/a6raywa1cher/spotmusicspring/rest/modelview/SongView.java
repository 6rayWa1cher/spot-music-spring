package com.a6raywa1cher.spotmusicspring.rest.modelview;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SongView {
	private Long id;

	private List<Long> events = new ArrayList<>();

	private UserView user;

	private String coverPhotoUrl;

	private String name;

	private String albumName;
}
