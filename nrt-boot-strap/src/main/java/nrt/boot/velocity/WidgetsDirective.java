package nrt.boot.velocity;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import nrt.jetty.web.VelocityView;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.nutz.resource.NutResource;
import org.nutz.resource.Scans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WidgetsDirective
extends Directive {
	private static final Logger logger = LoggerFactory.getLogger(WidgetsDirective.class);
	
	public static final String KEY_WIDGETS = "widgets";
	private static final String PATH_PREFIX = "widgets";
	
	private HashMap<String, Set<String>> widgetLists = new HashMap<String, Set<String>>();
	private VelocityView viewConfig;
	
	@Override
	public String getName() {
		return "widgets";
	}

	@Override
	public int getType() {
		return BLOCK;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer,
			Node node) throws IOException, ResourceNotFoundException,
			ParseErrorException, MethodInvocationException {
		viewConfig = (VelocityView) context.get(VelocityView.KEY_VIEW_CONFIG);
		String key = getWidgetListKey(context, node);
		logger.trace("Loading widgets for list [{}]...", key);
		if (key == null || key.isEmpty()) {
			doRender(context, writer, node, widgetList(context, ""));
		} else {
			doRender(context, writer, node, widgetList(context, key));
		}
		return false;
	}

	private void doRender(InternalContextAdapter context, Writer writer,
			Node node, Set<String> widgets)
					throws MethodInvocationException, ParseErrorException,
					ResourceNotFoundException, IOException {
		logger.trace("Widgets: {}", widgets);
		Object oldWidgets = context.get(KEY_WIDGETS);
		try {
			SimpleNode block = (SimpleNode) node.jjtGetChild(node.jjtGetNumChildren() - 1);
			block.render(context, writer);
		} finally {
			if (oldWidgets == null) {
				context.remove(KEY_WIDGETS);
			} else {
				context.put(KEY_WIDGETS, oldWidgets);
			}
		}
	}

	private Set<String> widgetList(InternalContextAdapter context, String key) {
		File[] repos = VelocityView.getRepos();
		logger.trace("Repos for resources: {}", (Object)repos);
		if (repos == null || repos.length < 1) {
			return classpathWidgetList(context, key);
		} else {
			return mixedWidgetList(context, key, repos);
		}
	}

	private Set<String> classpathWidgetList(InternalContextAdapter context,
			String key) {
		boolean devMode = VelocityView.isDevMode();
		Set<String> loaded = null;
		if (!devMode) {
			loaded = widgetLists.get(key); 
		}
		if (loaded == null) {
			loaded = new HashSet<String>();
			loadClasspathWidgets(loaded, key);
		}
		if (!devMode) {
			widgetLists.put(key, loaded);
		}
		return loaded;
	}

	private void loadClasspathWidgets(Set<String> loaded, String key) {
		logger.trace("Loading classpath widgets...");
		String prefix = viewConfig.getResourceLocation() + "/" + PATH_PREFIX + (key == null || key.isEmpty() ? "" : ("/" + key));
		for (NutResource res: Scans.me().scan(prefix)) {
			String n = res.getName();
			logger.trace("Classpath widget: /{}/{}/{}", PATH_PREFIX, key, n);
			loaded.add("/" + prefix + "/" + n);
		}
	}

	private Set<String> mixedWidgetList(InternalContextAdapter context,
			String key, File[] repos) {
		boolean devMode = VelocityView.isDevMode();
		Set<String> loaded = null;
		if (!devMode) {
			loaded = widgetLists.get(key); 
		}
		if (loaded == null) {
			loaded = new HashSet<String>();
			loadRepoWidgets(loaded, key, repos);
			loadClasspathWidgets(loaded, key);
		}
		if (!devMode) {
			widgetLists.put(key, loaded);
		}
		return loaded;
	}

	private void loadRepoWidgets(Set<String> loaded, String key, File[] repos) {
		logger.trace("Loading repository widgets...");
		for (File f: repos) {
			File d = new File(f, PATH_PREFIX);
			if (key != null && !key.isEmpty()) {
				d = new File(d, key);
			}
			if (d.exists()) {
				for (File w: d.listFiles()) {
					if (w.isFile()) {
						String n = w.getName();
						logger.trace("Repo widget: /{}/{}/{}", PATH_PREFIX, key, n);
						loaded.add("/" + PATH_PREFIX + "/" + key + "/" + ((d == f) ? (d.getName() + "/") : "") + n);
					}
				}
			}
		}
	}

	private String getWidgetListKey(InternalContextAdapter context, Node node) {
		if (node.jjtGetNumChildren() < 2) return null;
		SimpleNode sn = (SimpleNode) node.jjtGetChild(0);
		return (String) sn.value(context);
	}
}
