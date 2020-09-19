package pl.adrian.commentsservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentRequest {

    private String movieId;
    private String content;
}
