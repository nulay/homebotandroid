package com.example.homebotrecorder.ui;

import lombok.Data;

@Data
public class Post {
    private int userId;
    private int id;
    private String title;
//    @SerializedName("body")
    private String body;
}
