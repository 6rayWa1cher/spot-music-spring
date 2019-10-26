package com.a6raywa1cher.spotmusicspring.rest.modelview;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserView {
	private Long id;
	private String name;
	private String phoneNumber;
	private String photo;
	private Boolean completeRegistration;
	private String description;
	private String socialUrl;
	private List<Long> songs = new ArrayList<>();
	private List<Long> events = new ArrayList<>();
}
