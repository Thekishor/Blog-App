package com.BlogApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BlogApp.Entities.Category;
import com.BlogApp.Entities.Post;
import com.BlogApp.Entities.User;
@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	
	public List<Post> findByUser(User user);
	public List<Post> findByCategory(Category category);
	
	@Query("select p from Post p where p.title like :key")
	public List<Post> searchByTitle(@Param("key") String title);
}
