package unalcol.js.vc.view;

import unalcol.reflect.plugin.PlugInInstance;
import unalcol.vc.View;

public class JSViewInstance implements PlugInInstance<View>{
	@Override
	public View get(){ return new JSViewTree(); }
}