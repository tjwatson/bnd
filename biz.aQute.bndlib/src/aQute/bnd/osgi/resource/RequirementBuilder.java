package aQute.bnd.osgi.resource;

import org.osgi.resource.Namespace;
import org.osgi.resource.Requirement;
import org.osgi.resource.Resource;

public class RequirementBuilder extends CapReqBuilder {

	public RequirementBuilder(Resource resource, String namespace) {
		super(resource, namespace);
	}

	public RequirementBuilder(String namespace) {
		super(namespace);
	}

	public Requirement build() {
		return super.buildRequirement();
	}

	public Requirement synthetic() {
		return super.buildSyntheticRequirement();
	}

	public RequirementBuilder addFilter(String filter) {
		addDirective(Namespace.REQUIREMENT_FILTER_DIRECTIVE, filter);
		return this;
	}

	public RequirementBuilder addFilter(FilterBuilder filter) {
		return addFilter(filter.toString());
	}
}
