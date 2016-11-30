package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class AuthenticationController extends AbstractController {

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		HttpSession thisSession= request.getSession();
		//thisSession.setAttribute("user", newUser);
		// doneTODO - implement signup
		String username= request.getParameter("username");
		User Name=userDao.findByUsername(username);

		if((Name)!= null){
			model.addAttribute("username_error","This user already exists");
				
		return "signup";
				}
		String password= request.getParameter("password");
		String verify= request.getParameter("verify");

		//valid username

		if(!User.isValidUsername(username)){
			model.addAttribute("username_error", "This username is invalid.");

			return "signup";
		}
		if(!password.equals(verify)){
			model.addAttribute("verify", "");
			model.addAttribute("verify_error", "Passwords do not match.");
			return "signup";
		}
		if(User.isValidPassword(password)){
			model.addAttribute("password", "");
			model.addAttribute("verify", "");
			model.addAttribute("password_error", "This password is invalid.");
		}

		User newUser= new User(username,password);
		userDao.save(newUser);
		this.setUserInSession(thisSession, newUser);
		return "redirect:blog/newpost";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {

		// DONE TODO - implement login
		//get parameters from request
		//get user by their user name


		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");

		User newUser = userDao.findByUsername(username);
		//unsure what to do here^^
		if(!newUser.isMatchingPassword(password)){
			model.addAttribute("password", "");     
			model.addAttribute("password_error", "This password is invalid for the user.");
			return "login";
		}
		HttpSession thisSession = request.getSession();
		this.setUserInSession(thisSession, newUser );


		//check password is correct
		return "redirect:blog/newpost";
	}


	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
		request.getSession().invalidate();
		return "redirect:/";
	}
}