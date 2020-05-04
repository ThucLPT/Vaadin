package pkg.backend.security;

import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;

public class SecurityUtils {
	static boolean isInternalRequest(HttpServletRequest request) {
		String param = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
		return param != null && Stream.of(ServletHelper.RequestType.values()).anyMatch(req -> req.getIdentifier().equals(param));
	}

	static boolean isLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}
}
