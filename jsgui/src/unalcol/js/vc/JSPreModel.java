package unalcol.js.vc;

import java.net.URL;
import java.net.URLClassLoader;

import unalcol.types.collection.keymap.KeyMap;
import unalcol.vc.BackEnd;
import unalcol.vc.Component;

public interface JSPreModel {
	public BackEnd backend();
	public KeyMap<String, Component> frontend();
	public void use(String url);
	
	public static JSPreModel get(String url, String pack, String model_jar, String model_class ){
		try {
			if(pack.length()>0 ) pack = pack + "/";
			@SuppressWarnings("resource")
			URLClassLoader loader =  new URLClassLoader(new URL[]{new URL(url+"plugins/"+pack+model_jar)}, JSPreModel.class.getClassLoader());
			Class<?> cl = loader.loadClass(model_class);
			System.out.println("[JSPremodel]"+cl.getName());
			JSPreModel model = (JSPreModel)cl.newInstance();
			model.use(url);
			return model;
		} catch(Exception e) { e.printStackTrace(); }
		return null;
	}

	public static JSPreModel get(String url, String pack ){ return get(url, pack, "model.jar", "JSModel"); }

	public static JSPreModel get(String url){ return get(url, "", "model.jar", "JSModel"); }		
}