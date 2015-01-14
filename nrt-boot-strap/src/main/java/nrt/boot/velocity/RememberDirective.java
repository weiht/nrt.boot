package nrt.boot.velocity;

import org.apache.shiro.SecurityUtils;

public class RememberDirective extends AuthDirective {
	@Override
	public String getName() {
		return "remember";
	}
	
	@Override
	protected boolean authState() {
		return SecurityUtils.getSubject().isRemembered();
	}
}
