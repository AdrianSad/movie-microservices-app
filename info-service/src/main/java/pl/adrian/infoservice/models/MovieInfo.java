package pl.adrian.infoservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieInfo {

    private String movieId;
    private String title;
    private String desc;
    private String director;
    private String country;
}
