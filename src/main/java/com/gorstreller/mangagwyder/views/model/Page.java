package com.gorstreller.mangagwyder.views.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Page {

    private int number;
    private String path;
}
