package com.a6raywa1cher.spotmusicspring.rest.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostCreateSong {
	private String coverPhotoUrl;

	@NotBlank
	private String name;

	private String albumName;
}
