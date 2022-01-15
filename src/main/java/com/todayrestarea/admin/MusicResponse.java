package com.todayrestarea.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class MusicResponse{
    final private String name;
    final private String artist;
    final private String url;
    final List<Image> image;

}
@Getter
@Setter
@ToString
@RequiredArgsConstructor
class Image{
    @JsonProperty("#text")
    final private String text;
    final private String size;
}

