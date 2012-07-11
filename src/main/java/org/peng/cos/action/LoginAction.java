package org.peng.cos.action;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import org.peng.cos.manager.MemberManager;
import org.peng.cos.model.Member;

@Named("login") // or @ManagedBean(name="user")
@RequestScoped
public class LoginAction extends BaseAction  implements Serializable{
   private String name;
   private String password;

   @Inject
   private Member InputMember;

   @EJB
   private MemberManager memberManager;
   
   public String adminlogin()
   {
	  if(name.equals("pw79461@gmail.com") && password.equals("Wlqswp794460"))
	  {
		   return "adminindex";
	  }
	  else
	  {
		  this.addI18FaceMessage("error_login",null);
		  return null;
	  }
   }
   
   public String login()
   {
	 //  this.addErrorMessage("error_userId_invalid", null);
	   return null;
   }
   
   public String register()
   {
	   if(!this.InputMember.getConfirmedPassword().equals(this.InputMember.getPassword()))
	   {
		   this.addI18FaceMessage( "error_userId_pwdmismatch", null);
		   return null;
	   }
	   
	   memberManager.addMember(InputMember);
	   
	   return "index";
	  
   }
   
   public void validateUserId(FacesContext fc, UIComponent c, Object value) {
	   if (!memberManager.isUserIdUsed(value.toString()))
	   throw new ValidatorException(
	   new FacesMessage("User Id is not unique"));
	}
   
	public Member getInputMember() {
		return InputMember;
	}
	public void setInputMember(Member inputMember) {
		InputMember = inputMember;
	}
	public String getName() { return name; }   
	public void setName(String newValue) { name = newValue; }
	
	public String getPassword() { return password; }
	public void setPassword(String newValue) { password = newValue; }   

	
   
}
