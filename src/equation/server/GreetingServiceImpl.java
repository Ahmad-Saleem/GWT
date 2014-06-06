package equation.server;

import equation.client.GreetingService;
import equation.shared.FieldVerifier;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		String[] paras = input.split(",");
		Double a=Double.valueOf(paras[0]);
		Double b=Double.valueOf(paras[1]);
		Double c=Double.valueOf(paras[2]);
		
		Double delta=b*b-4*a*c;
		Double s1=null;
		Double s2=null;
		String msg;
		if(delta>=0){
			s1=(-b+Math.sqrt(delta))/2*a;
			s2=(-b-Math.sqrt(delta))/2*a;
			msg="tow Sulotions";
			if(s1==s2){
				msg="One Sulotion";
			}
		}else {
			s1=null;
			s2=null;
			msg="NO Sulotion";
		}
		return "x1=" + s1 + ", x2=" + s2 +"<br>"+msg;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
