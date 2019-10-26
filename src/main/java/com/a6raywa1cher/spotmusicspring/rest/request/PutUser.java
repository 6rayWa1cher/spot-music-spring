package com.a6raywa1cher.spotmusicspring.rest.request;

import lombok.Data;

@Data
public class PutUser {
	private String name;

	private String photo;

	private String description;

	private String socialUrl;
}
