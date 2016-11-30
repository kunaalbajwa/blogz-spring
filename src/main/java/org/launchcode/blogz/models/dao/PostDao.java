package org.launchcode.blogz.models.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface PostDao extends CrudRepository<Post, Integer> {
    
 	List<Post> findByAuthor(User author);
    
    Post findByUid(int uid);//string or int?
    List<Post> findAll();

//	List<Post> findByTitle (String title);
	List<Post> findByBody(String body);
 	List<Post> findByCreated(Date date);
	List<Post> findByModified(int modified);
	
    
    //Done TODO - add method signatures as needed
	
}
