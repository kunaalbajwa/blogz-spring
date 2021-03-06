package org.launchcode.blogz.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController extends AbstractController {

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		HttpSession thisSession= request.getSession();
		String Title=request.getParameter("title");
		String Body=request.getParameter("body");				
		User newUser=getUserFromSession(thisSession);
		String username= newUser.getUsername();
		Post newPost=new Post(Title, Body, newUser);
		//save to postdao
		postDao.save(newPost);
		// TODO - done implement newPost
		int uid=newPost.getUid();
		return String.format("redirect:/blog/%s/%s/", username, uid); // TODO this redirect should go to the new post's page  		
	}

	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {

		//   TODO - implement singlePost
Post post = postDao.findByUid(uid);
model.addAttribute(post);
		return "post";
	}

	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {

		// TODO - implement userPosts
		User user=new User();
		user.getPosts();
		User author=userDao.findByUsername(username);
		List<Post> Postz= postDao.findByAuthor(author);
		model.addAttribute("posts", Postz);
		return "blog";
		//done TODO rememeber to throw into template;
//model.addAttribute("posts"
	}

}
