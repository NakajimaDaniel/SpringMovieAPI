package com.danielnakajima.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

  private ReviewRepository reviewRepository;
  @Autowired
  private MongoTemplate mongoTemplate;

  public Reviews createReview(String reviewBody, String imdbId) {
    Reviews Review = reviewRepository.insert(new Reviews(reviewBody));

    mongoTemplate.update(Movie.class)
        .matching(Criteria.where("imdbId").is(imdbId))
        .apply(new Update().push("reviewIds").value(Review))
        .first();

    return Review;

  }
}
