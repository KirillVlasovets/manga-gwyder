package com.gorstreller.mangagwyder.entity.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Page {

    private int number;
//    private int chapterNumber;
//    private String title;
    private String path;
}
