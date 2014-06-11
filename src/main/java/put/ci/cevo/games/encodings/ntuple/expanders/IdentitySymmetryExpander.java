package put.ci.cevo.games.encodings.ntuple.expanders;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class IdentitySymmetryExpander implements SymmetryExpander {

	@Override
	public int[] getSymmetries(int location) {
		return new int[] { location };
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public int numSymmetries() {
		return 1;
	}
}
