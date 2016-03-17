package com.returnsoft.callcenter.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.returnsoft.callcenter.dto.UserDto;
import com.returnsoft.callcenter.exception.SessionTypeInvalidException;
import com.returnsoft.callcenter.service.AgentService;
import com.returnsoft.callcenter.service.SupervisorService;
import com.returnsoft.callcenter.service.UserService;
import com.returnsoft.callcenter.util.FacesUtil;

@ManagedBean
@RequestScoped
public class LoginController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7145177391779082911L;
	/**
	 * 
	 */
	
	private String username;
	private String password;
	
	private String emDetail;
	private String emSummary;
	
	//private CorbaUtil corbaUtil;
	private FacesUtil facesUtil;
	
	@EJB
	private UserService userService;
	@EJB
	private SupervisorService supervisorService;
	@EJB
	private AgentService agentService;

	public LoginController() {
		//System.out.println("Ingreso a loginController");
		//corbaUtil = new CorbaUtil();
		facesUtil = new FacesUtil();
	}
	
	public String checkErrorMessage(){
		
		//System.out.println("Ingreso a checkErrorMessage");
		
		if (emDetail!=null && emDetail.length()>0
				|| emSummary!=null && emSummary.length()>0) {
			
			facesUtil.sendErrorMessage(emSummary,emDetail);
		}
		return null;
		
	}

	public String doLogin() {

		//System.out.println("Ingreso a doLogin");
		try {

			// BUSCA USUARIO
			UserDto user = null;
			
			String usuario = username.substring(0,username.indexOf("/"));
			String anexo =username.substring(username.indexOf("/")+1,username.length());
			System.out.println(usuario);
			System.out.println(anexo);
			user = userService.loginUser(usuario, password);
			
			SessionBean sessionBean = null;

			switch (user.getUserType()) {

			case ADMIN:
			
				sessionBean = new SessionBean();
				sessionBean.setUser(user);
				sessionBean.setIsAdmin(true);
				sessionBean.setIsSupervisor(true);
				sessionBean.setCurrentPage("home");
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("sessionBean", sessionBean);
				return "home?faces-redirect=true";

			case SUPERVISOR:
				
				supervisorService.loginSupervisor(user.getId());
				
				sessionBean = new SessionBean();
				sessionBean.setUser(user);
				sessionBean.setIsSupervisor(true);
				sessionBean.setCurrentPage("supervisorBoard");
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("sessionBean", sessionBean);
				return "supervisorBoard?faces-redirect=true";

			case AGENT:
				/*HttpServletRequest request = (HttpServletRequest) FacesContext
						.getCurrentInstance().getExternalContext().getRequest();
				String host = request.getRemoteAddr();*/
				
				//if (host.equals("127.0.0.1") || host.equals("0:0:0:0:0:0:0:1")) {
					//host = "192.168.23.253";
					//host = "172.28.0.23";
				//}

				agentService.loginAgent2(user.getId(),anexo);

				sessionBean = new SessionBean();
				sessionBean.setUser(user);
				sessionBean.setIsAgent(true);
				sessionBean.setCurrentPage("agentBoard");
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put("sessionBean", sessionBean);
				
				//return null;
				return "agentBoard?faces-redirect=true";


			default:
				
				throw new SessionTypeInvalidException();

			}


		}  catch (Exception e) {
			//System.out.println(e.getMessage());
			e.printStackTrace();
			facesUtil.sendErrorMessage(e.getClass().getSimpleName(),e.getMessage());
			return null;
		}

	}
	

	public String doLogout() {
		//System.out.println("ingreso a doLogout");

		try {

			// OBTIENE LA SESSION
			SessionBean session = (SessionBean) FacesContext
					.getCurrentInstance().getExternalContext().getSessionMap()
					.get("sessionBean");

			UserDto user = session.getUser();
			
			switch (user.getUserType()) {
			case AGENT:
				agentService.logoutAgent(user.getId());
				break;
			default:
				break;
			}

			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();
			return "login?faces-redirect=true";

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().getExternalContext()
			.invalidateSession();
			return "login?faces-redirect=true&emSummary="+e.getClass().getSimpleName()+"&emDetail="+e.getMessage();
		}

	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmDetail() {
		return emDetail;
	}

	public void setEmDetail(String emDetail) {
		this.emDetail = emDetail;
	}

	public String getEmSummary() {
		return emSummary;
	}

	public void setEmSummary(String emSummary) {
		this.emSummary = emSummary;
	}
	
	

}
