package com.BlogApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BlogApp.Entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
