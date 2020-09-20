package pl.adrian.catalogservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {

    private String movieId;
    private String title;
    private String desc;
    private List<Comment> comments;
    private int rating;
}
